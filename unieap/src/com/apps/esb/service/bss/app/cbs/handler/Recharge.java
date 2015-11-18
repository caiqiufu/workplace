package com.apps.esb.service.bss.app.cbs.handler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.cbs.vo.recharge.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.BalanceVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.CreditChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.FreeUnitItemVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LifeCycleChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LifeCycleStatusVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LoanChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.RechargeBonusVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.RechargeResponseVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.base.SYSConfig;
import com.unieap.encrypt.EncryptionUtils;

@Service("recharge")
public class Recharge extends SoapMessageHandler implements BizHandler {

	public Recharge() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			throw new Exception("Pin Number is null");
		}
		JSONObject extParametersJson = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if(!extParametersJson.has("cardPinNumber")){
			throw new Exception("Pin Number is null");
		}
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("recharge");
		return process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(),
				newRequestInfo.getRequestBody().getExtParameters(), parameters, "ws.cbs.query.timeout");
	}

	@Override
	public RequestInfo getRequestInfoFromSOAPMessage(SOAPMessage request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponsetInfo getResponseInfoFromSOAPMessage(SOAPMessage response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SOAPMessage getRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo) throws Exception {
		SOAPMessage message = messageFactory.createMessage();
		this.getCBSArsHeader("RechargeRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement rechargeReqestElement = bodyElement.addChildElement("RechargeRequest");
		rechargeReqestElement.addChildElement("RechargeSerialNo", "ars")
				.addTextNode(BssServiceUtils.generateTransactionId());
		SOAPElement rechargeObjElement = rechargeReqestElement.addChildElement("RechargeObj", "ars");
		SOAPElement subAccessCodeElement = rechargeObjElement.addChildElement("SubAccessCode", "ars");
		SOAPElement primaryIdentityElement = subAccessCodeElement.addChildElement("PrimaryIdentity", "arc");
		primaryIdentityElement.addTextNode(serviceNumber);

		SOAPElement rechargeInfoElement = rechargeReqestElement.addChildElement("RechargeInfo", "ars");
		SOAPElement cardPaymentElement = rechargeInfoElement.addChildElement("CardPayment", "ars");
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (json.has("cardPinNumber")) {
			String sSrc = json.getString("cardPinNumber");
			String mKey =  SYSConfig.getConfig().get("mcare.encryption.key");
			String dSrc = EncryptionUtils.decryptSun(sSrc, mKey);
			String sKey =  SYSConfig.getConfig().get("bss.encryption.key");
			String enPassword = EncryptionUtils.encryptSunJCE(dSrc, sKey);
			cardPaymentElement.addChildElement("CardPinNumber","ars").addTextNode(enPassword.trim());
		} else {
			throw new Exception("Pin Number is null");
		}
		// SOAPElement cardSequenceElement =
		// cardPaymentElement.addChildElement("CardSequence");
		// SOAPElement cashPaymentElement =
		// rechargeInfoElement.addChildElement("CashPayment");
		/**
		 * 1001 cash 2001 credit card 3001 check
		 */
		/*
		 * SOAPElement paymentMethodElement =
		 * cashPaymentElement.addChildElement("PaymentMethod"); SOAPElement
		 * amountElement = cashPaymentElement.addChildElement("Amount");
		 * SOAPElement bankInfoElement =
		 * cashPaymentElement.addChildElement("BankInfo"); SOAPElement
		 * bankCodeElement = bankInfoElement.addChildElement("BankCode");
		 * SOAPElement bankBranchCodeElement =
		 * bankInfoElement.addChildElement("BankBranchCode"); SOAPElement
		 * acctTypeElement = bankInfoElement.addChildElement("AcctType");
		 * SOAPElement acctNoElement =
		 * bankInfoElement.addChildElement("AcctNo");
		 */

		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		org.w3c.dom.Document document = BssServiceUtils.getSoapMessageDocument(response);
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		RechargeResponseVO rechargeResponseVO = getRechargeResponse(document);
		result.setVo(rechargeResponseVO);
		return result;
	}

	public RechargeResponseVO getRechargeResponse(Document document) {
		RechargeResponseVO rechargeResponseVO = new RechargeResponseVO();
		List<BalanceChgInfoVO> balanceChgInfos = new ArrayList<BalanceChgInfoVO>();
		LoanChgInfoVO loanChgInfoVO = new LoanChgInfoVO();
		RechargeBonusVO rechargeBonusVO = new RechargeBonusVO();
		LifeCycleChgInfoVO lifeCycleChgInfoVO = new LifeCycleChgInfoVO();
		List<CreditChgInfoVO> creditChgInfos = new ArrayList<CreditChgInfoVO>();
		rechargeResponseVO.setBalanceChgInfos(balanceChgInfos);
		rechargeResponseVO.setCreditChgInfos(creditChgInfos);
		rechargeResponseVO.setLifeCycleChgInfoVO(lifeCycleChgInfoVO);
		rechargeResponseVO.setLoanChgInfoVO(loanChgInfoVO);
		rechargeResponseVO.setRechargeBonusVO(rechargeBonusVO);
		if (document.getElementsByTagName("ars:RechargeSerialNo").getLength() > 0) {
			rechargeResponseVO.setRechargeSerialNo(
					document.getElementsByTagName("ars:RechargeSerialNo").item(0).getTextContent());
		}
		if (document.getElementsByTagName("ars:BalanceChgInfo").getLength() > 0) {
			BalanceChgInfoVO balanceChgInfoVO = new BalanceChgInfoVO();
			balanceChgInfos.add(balanceChgInfoVO);
			NodeList balanceChgInfoNodes = document.getElementsByTagName("ars:BalanceChgInfo");
			for (int i = 0; i < balanceChgInfoNodes.getLength(); i++) {
				NodeList nodes = balanceChgInfoNodes.item(i).getChildNodes();
				for (int j = 0; j < nodes.getLength(); j++) {
					Node node = nodes.item(j);
					if ("arc:BalanceType".equals(node.getNodeName())) {
						balanceChgInfoVO.setBalanceType(node.getTextContent());
					} else if ("arc:BalanceID".equals(node.getNodeName())) {
						balanceChgInfoVO.setBalanceID(node.getTextContent());
					} else if ("arc:BalanceTypeName".equals(node.getNodeName())) {
						balanceChgInfoVO.setBalanceTypeName(node.getNodeName());
					} else if ("arc:OldBalanceAmt".equals(node.getNodeName())) {
						balanceChgInfoVO.setOldBalanceAmt(node.getTextContent());
					} else if ("arc:NewBalanceAmt".equals(node.getNodeName())) {
						balanceChgInfoVO.setNewBalanceAmt(node.getTextContent());
					}
				}
			}
		}
		if (document.getElementsByTagName("ars:LoanChgInfo").getLength() > 0) {
			Node loanChgInfoNode = document.getElementsByTagName("ars:LoanChgInfo").item(0);
			NodeList nodes = loanChgInfoNode.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if ("arc:OldLoanAmt".equals(node.getNodeName())) {
					loanChgInfoVO.setOldLoanAmt(node.getTextContent());
				} else if ("arc:NewLoanAmt".equals(node.getNodeName())) {
					loanChgInfoVO.setNewLoanAmt(node.getTextContent());
				} else if ("arc:LoanPaymentAmt".equals(node.getNodeName())) {
					loanChgInfoVO.setLoanPaymentAmt(node.getTextContent());
				} else if ("arc:LoanInterestAmt".equals(node.getNodeName())) {
					loanChgInfoVO.setLoanInterestAmt(node.getTextContent());
				}
			}
		}
		if (document.getElementsByTagName("ars:RechargeBonus").getLength() > 0) {
			Node rechargeBonusNode = document.getElementsByTagName("ars:RechargeBonus").item(0);
			List<FreeUnitItemVO> freeUnitItems = new ArrayList<FreeUnitItemVO>();
			List<BalanceVO> balances = new ArrayList<BalanceVO>();
			rechargeBonusVO.setBalances(balances);
			rechargeBonusVO.setFreeUnitItems(freeUnitItems);
			NodeList nodes = rechargeBonusNode.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if ("ars:FreeUnitItemList".equals(node.getNodeName())) {
					FreeUnitItemVO freeUnitItemVO = new FreeUnitItemVO();
					freeUnitItems.add(freeUnitItemVO);
					NodeList freeUnitItemNodes = node.getChildNodes();
					for (int j = 0; j < freeUnitItemNodes.getLength(); j++) {
						Node freeUnitItemNode = freeUnitItemNodes.item(j);
						if ("ars:FreeUnitID".equals(freeUnitItemNode.getNodeName())) {
							freeUnitItemVO.setFreeUnitID(freeUnitItemNode.getTextContent());
						} else if ("ars:FreeUnitType".equals(freeUnitItemNode.getNodeName())) {
							freeUnitItemVO.setFreeUnitType(freeUnitItemNode.getTextContent());
						} else if ("ars:FreeUnitTypeName".equals(freeUnitItemNode.getNodeName())) {
							freeUnitItemVO.setFreeUnitTypeName(freeUnitItemNode.getTextContent());
						} else if ("ars:MeasureUnit".equals(freeUnitItemNode.getNodeName())) {
							freeUnitItemVO.setMeasureUnit(freeUnitItemNode.getTextContent());
						} else if ("ars:MeasureUnitName".equals(freeUnitItemNode.getNodeName())) {
							freeUnitItemVO.setMeasureUnitName(freeUnitItemNode.getTextContent());
						} else if ("ars:BonusAmt".equals(freeUnitItemNode.getNodeName())) {
							freeUnitItemVO.setBonusAmt(freeUnitItemNode.getTextContent());
						} else if ("ars:BonusAmt".equals(freeUnitItemNode.getNodeName())) {
							freeUnitItemVO.setBonusAmt(freeUnitItemNode.getTextContent());
						} else if ("ars:EffectiveTime".equals(freeUnitItemNode.getNodeName())) {
							freeUnitItemVO.setEffectiveTime(freeUnitItemNode.getTextContent());
						} else if ("ars:ExpoireTime".equals(freeUnitItemNode.getNodeName())) {
							freeUnitItemVO.setExpoireTime(freeUnitItemNode.getTextContent());
						}
					}
				} else if ("ars:BalanceList".equals(node.getNodeName())) {
					BalanceVO balanceVO = new BalanceVO();
					balances.add(balanceVO);
					NodeList balanceNodes = node.getChildNodes();
					for (int j = 0; j < balanceNodes.getLength(); j++) {
						Node balanceNode = balanceNodes.item(j);
						if ("ars:BalanceType".equals(balanceNode.getNodeName())) {
							balanceVO.setBalanceType(balanceNode.getTextContent());
						} else if ("ars:BalanceID".equals(balanceNode.getNodeName())) {
							balanceVO.setBalanceID(balanceNode.getTextContent());
						} else if ("ars:BalanceTypeName".equals(balanceNode.getNodeName())) {
							balanceVO.setBalanceTypeName(balanceNode.getTextContent());
						} else if ("ars:BonusAmt".equals(balanceNode.getNodeName())) {
							balanceVO.setBonusAmt(balanceNode.getTextContent());
						} else if ("ars:EffectiveTime".equals(balanceNode.getNodeName())) {
							balanceVO.setEffectiveTime(balanceNode.getTextContent());
						} else if ("ars:ExpireTime".equals(balanceNode.getNodeName())) {
							balanceVO.setExpireTime(balanceNode.getTextContent());
						}
					}
				}
			}
		}

		if (document.getElementsByTagName("ars:LifeCycleChgInfo").getLength() > 0) {
			List<LifeCycleStatusVO> oldLifeCycleStatus = new ArrayList<LifeCycleStatusVO>();
			lifeCycleChgInfoVO.setOldLifeCycleStatus(oldLifeCycleStatus);
			List<LifeCycleStatusVO> newLifeCycleStatus = new ArrayList<LifeCycleStatusVO>();
			lifeCycleChgInfoVO.setNewLifeCycleStatus(newLifeCycleStatus);
			NodeList lifeCycleChgInfoNodes = document.getElementsByTagName("ars:LifeCycleChgInfo").item(0)
					.getChildNodes();
			for (int i = 0; i < lifeCycleChgInfoNodes.getLength(); i++) {
				Node lifeCyckeChgInfoNode = lifeCycleChgInfoNodes.item(i);
				if ("ars:OldLifeCycleStatus".equals(lifeCyckeChgInfoNode.getNodeName())) {
					LifeCycleStatusVO lifeCycleStatusVO = new LifeCycleStatusVO();
					oldLifeCycleStatus.add(lifeCycleStatusVO);
					NodeList oldLifeCycleStatusNodes = lifeCyckeChgInfoNode.getChildNodes();
					for (int l = 0; l < oldLifeCycleStatusNodes.getLength(); l++) {
						Node oldLifeCycleStatusNode = oldLifeCycleStatusNodes.item(l);
						if ("ars:StatusName".equals(oldLifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setStatusName(oldLifeCycleStatusNode.getTextContent());
						} else if ("ars:StatusExpireTime".equals(oldLifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setStatusExpireTime(oldLifeCycleStatusNode.getTextContent());
						} else if ("ars:StatusIndex".equals(oldLifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setStatusIndex(oldLifeCycleStatusNode.getTextContent());
						}
					}

				} else if ("ars:NewLifeCycleStatus".equals(lifeCyckeChgInfoNode.getNodeName())) {
					NodeList newLifeCycleStatusNodes = lifeCyckeChgInfoNode.getChildNodes();
					LifeCycleStatusVO lifeCycleStatusVO = new LifeCycleStatusVO();
					newLifeCycleStatus.add(lifeCycleStatusVO);
					for (int l = 0; l < newLifeCycleStatusNodes.getLength(); l++) {
						Node newLifeCycleStatusNode = newLifeCycleStatusNodes.item(l);
						if ("ars:StatusName".equals(newLifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setStatusName(newLifeCycleStatusNode.getTextContent());
						} else if ("ars:StatusExpireTime".equals(newLifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setStatusExpireTime(newLifeCycleStatusNode.getTextContent());
						} else if ("ars:StatusIndex".equals(newLifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setStatusIndex(newLifeCycleStatusNode.getTextContent());
						}
					}
				} else if ("ars:AddValidity".equals(lifeCyckeChgInfoNode.getNodeName())) {
					lifeCycleChgInfoVO.setAddValidity(lifeCyckeChgInfoNode.getTextContent());
				}
			}
		}
		if (document.getElementsByTagName("ars:CreditChgInfo").getLength() > 0) {
			NodeList creditChgInfoNodes = document.getElementsByTagName("ars:CreditChgInfo");
			for (int i = 0; i < creditChgInfoNodes.getLength(); i++) {
				CreditChgInfoVO creditChgInfoVO = new CreditChgInfoVO();
				creditChgInfos.add(creditChgInfoVO);
				Node creditChgInfoNode = creditChgInfoNodes.item(i);
				NodeList nodes = creditChgInfoNode.getChildNodes();
				for (int j = 0; j < nodes.getLength(); j++) {
					Node node = nodes.item(j);
					if ("arc:CreditLimitID".equals(node.getNodeName())) {
						creditChgInfoVO.setCreditLimitID(node.getTextContent());
					} else if ("arc:CreditLimitType".equals(node.getNodeName())) {
						creditChgInfoVO.setCreditLimitType(node.getTextContent());
					} else if ("arc:CreditLimitTypeName".equals(node.getNodeName())) {
						creditChgInfoVO.setCreditLimitTypeName(node.getTextContent());
					} else if ("arc:OldLeftCreditAmt".equals(node.getNodeName())) {
						creditChgInfoVO.setOldLeftCreditAmt(node.getTextContent());
					} else if ("arc:NewLeftCreditAmt".equals(node.getNodeName())) {
						creditChgInfoVO.setNewLeftCreditAmt(node.getTextContent());
					} else if ("arc:MeasureUnit".equals(node.getNodeName())) {
						creditChgInfoVO.setMeasureUnit(node.getTextContent());
					}
				}
			}
		}
		return rechargeResponseVO;
	}
}
