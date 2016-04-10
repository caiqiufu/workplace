package com.apps.esb.service.bss.customize.smart.app.cbs;

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
import com.apps.esb.service.bss.app.vo.account.recharge.RechargeBonusVO;
import com.apps.esb.service.bss.app.vo.account.recharge.RechargeLogVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.CreditChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.FreeResourceChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.LifeCycleChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.LoanChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.lifecycle.StatusVO;
import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;
import com.unieap.base.SYSConfig;
import com.unieap.encrypt.EncryptionUtils;

@Service("recharge_1")
public class SmartRecharge extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler, Map<String, Object> extParameters)
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
		return SoapCallUtils.process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(),
				newRequestInfo.getRequestBody().getExtParameters(), handler, "ws.cbs.query.timeout");
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
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
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
		RechargeLogVO rechargeLogVO = getRechargeResponse(document);
		result.setVo(rechargeLogVO);
		return result;
	}

	public RechargeLogVO getRechargeResponse(Document document) {
		RechargeLogVO rechargeLogVO = new RechargeLogVO();
		List<BalanceChgInfoVO> balanceChgInfoList = new ArrayList<BalanceChgInfoVO>();
		List<CreditChgInfoVO> creditChgInfoList = new ArrayList<CreditChgInfoVO>();
		List<LoanChgInfoVO> loanChgInfoList = new ArrayList<LoanChgInfoVO>();
		List<RechargeBonusVO> rechargeBonusList = new ArrayList<RechargeBonusVO>();
		List<LifeCycleChgInfoVO> lifeCycleChgInfoList = new ArrayList<LifeCycleChgInfoVO>();
		List<FreeResourceChgInfoVO> freeResourceChgInfoList = new ArrayList<FreeResourceChgInfoVO>();
		
		//RechargeBonusVO rechargeBonusVO = new RechargeBonusVO();
		rechargeLogVO.setBalanceChgInfoList(balanceChgInfoList);
		rechargeLogVO.setCreditChgInfoList(creditChgInfoList);
		rechargeLogVO.setLifeCycleChgInfoList(lifeCycleChgInfoList);
		rechargeLogVO.setLoanChgInfoList(loanChgInfoList);
		rechargeLogVO.setRechargeBonusList(rechargeBonusList);
		rechargeLogVO.setFreeResourceChgInfoList(freeResourceChgInfoList);
		if (document.getElementsByTagName("ars:RechargeSerialNo").getLength() > 0) {
			rechargeLogVO.setRechargeSerialNo(
					document.getElementsByTagName("ars:RechargeSerialNo").item(0).getTextContent());
		}
		if (document.getElementsByTagName("ars:BalanceChgInfo").getLength() > 0) {
			BalanceChgInfoVO balanceChgInfoVO = new BalanceChgInfoVO();
			balanceChgInfoList.add(balanceChgInfoVO);
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
						balanceChgInfoVO.setBalanceTypeDesc(node.getNodeName());
					} else if ("arc:OldBalanceAmt".equals(node.getNodeName())) {
						balanceChgInfoVO.setOldBalanceAmount(node.getTextContent());
					} else if ("arc:NewBalanceAmt".equals(node.getNodeName())) {
						balanceChgInfoVO.setNewBalanceAmount(node.getTextContent());
					}
				}
			}
		}
		if (document.getElementsByTagName("ars:LoanChgInfo").getLength() > 0) {
			Node loanChgInfoNode = document.getElementsByTagName("ars:LoanChgInfo").item(0);
			NodeList nodes = loanChgInfoNode.getChildNodes();
			LoanChgInfoVO loanChgInfoVO = new LoanChgInfoVO();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if ("arc:OldLoanAmt".equals(node.getNodeName())) {
					loanChgInfoVO.setOldLoanAmount(node.getTextContent());
				} else if ("arc:NewLoanAmt".equals(node.getNodeName())) {
					loanChgInfoVO.setNewLoanAmount(node.getTextContent());
				} else if ("arc:LoanPaymentAmt".equals(node.getNodeName())) {
					loanChgInfoVO.setLoanPaymentAmount(node.getTextContent());
				} else if ("arc:LoanInterestAmt".equals(node.getNodeName())) {
					loanChgInfoVO.setLoanInterestAmount(node.getTextContent());
				}
			}
		}
		if (document.getElementsByTagName("ars:RechargeBonus").getLength() > 0) {
			Node rechargeBonusNode = document.getElementsByTagName("ars:RechargeBonus").item(0);
			NodeList nodes = rechargeBonusNode.getChildNodes();
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				if ("ars:FreeUnitItemList".equals(node.getNodeName())) {
					FreeResourceChgInfoVO freeResourceChgInfoVO = new FreeResourceChgInfoVO();
					freeResourceChgInfoList.add(freeResourceChgInfoVO);
					NodeList freeUnitItemNodes = node.getChildNodes();
					for (int j = 0; j < freeUnitItemNodes.getLength(); j++) {
						Node freeUnitItemNode = freeUnitItemNodes.item(j);
						/*if ("ars:FreeUnitID".equals(freeUnitItemNode.getNodeName())) {
							freeResourceChgInfoVO.setMeasureUnit(freeUnitItemNode.getTextContent());
						} else */if ("ars:FreeUnitType".equals(freeUnitItemNode.getNodeName())) {
							freeResourceChgInfoVO.setMeasureUnit(freeUnitItemNode.getTextContent());
						} else if ("ars:FreeUnitTypeName".equals(freeUnitItemNode.getNodeName())) {
							freeResourceChgInfoVO.setMeasureUnitDesc(freeUnitItemNode.getTextContent());
						} /*else if ("ars:MeasureUnit".equals(freeUnitItemNode.getNodeName())) {
							freeResourceChgInfoVO.setMeasureUnit(freeUnitItemNode.getTextContent());
						} else if ("ars:MeasureUnitName".equals(freeUnitItemNode.getNodeName())) {
							freeResourceChgInfoVO.setMeasureUnitName(freeUnitItemNode.getTextContent());
						}*//* else if ("ars:BonusAmt".equals(freeUnitItemNode.getNodeName())) {
							freeUnitItemVO.setBonusAmt(freeUnitItemNode.getTextContent());
						} else if ("ars:BonusAmt".equals(freeUnitItemNode.getNodeName())) {
							freeUnitItemVO.setBonusAmt(freeUnitItemNode.getTextContent());
						} else if ("ars:EffectiveTime".equals(freeUnitItemNode.getNodeName())) {
							freeUnitItemVO.setEffectiveTime(freeUnitItemNode.getTextContent());
						} else if ("ars:ExpoireTime".equals(freeUnitItemNode.getNodeName())) {
							freeUnitItemVO.setExpoireTime(freeUnitItemNode.getTextContent());
						}*/
						else if ("ars:BonusAmt".equals(freeUnitItemNode.getNodeName())) {
							freeResourceChgInfoVO.setNewAmount(freeUnitItemNode.getTextContent());
						}  else if ("ars:EffectiveTime".equals(freeUnitItemNode.getNodeName())) {
							freeResourceChgInfoVO.setEffectiveTime(freeUnitItemNode.getTextContent());
						} else if ("ars:ExpoireTime".equals(freeUnitItemNode.getNodeName())) {
							freeResourceChgInfoVO.setExpiryTime(freeUnitItemNode.getTextContent());
						}
					}
				} else if ("ars:BalanceList".equals(node.getNodeName())) {
					BalanceChgInfoVO balanceChgInfoVO = new BalanceChgInfoVO();
					balanceChgInfoList.add(balanceChgInfoVO);
					NodeList balanceNodes = node.getChildNodes();
					for (int j = 0; j < balanceNodes.getLength(); j++) {
						Node balanceListNode = balanceNodes.item(j);
						/*if ("ars:BalanceType".equals(balanceNode.getNodeName())) {
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
						}*/
						if ("ars:BalanceID".equals(balanceListNode.getNodeName())) {
							balanceChgInfoVO.setBalanceID(balanceListNode.getTextContent());
						} else if ("ars:BonusAmt".equals(balanceListNode.getNodeName())) {
							balanceChgInfoVO.setNewBalanceAmount(balanceListNode.getTextContent());
						} else if ("ars:EffectiveTime".equals(balanceListNode.getNodeName())) {
							balanceChgInfoVO.setEffectiveTime(balanceListNode.getTextContent());
						} else if ("ars:ExpireTime".equals(balanceListNode.getNodeName())) {
							balanceChgInfoVO.setExpiryTime(balanceListNode.getTextContent());
						}
					}
				}
			}
		}

		if (document.getElementsByTagName("ars:LifeCycleChgInfo").getLength() > 0) {
			LifeCycleChgInfoVO lifeCycleChgInfoVO = new LifeCycleChgInfoVO();
			lifeCycleChgInfoList.add(lifeCycleChgInfoVO);
			List<StatusVO> oldLifeCycleStatusList = new ArrayList<StatusVO>();
			List<StatusVO> newLifeCycleStatusList = new ArrayList<StatusVO>();
			lifeCycleChgInfoVO.setOldLifeCycleStatusList(oldLifeCycleStatusList);
			lifeCycleChgInfoVO.setNewLifeCycleStatusList(newLifeCycleStatusList);
			NodeList lifeCycleChgInfoNodes = document.getElementsByTagName("ars:LifeCycleChgInfo").item(0)
					.getChildNodes();
			for (int i = 0; i < lifeCycleChgInfoNodes.getLength(); i++) {
				Node lifeCyckeChgInfoNode = lifeCycleChgInfoNodes.item(i);
				if ("ars:OldLifeCycleStatus".equals(lifeCyckeChgInfoNode.getNodeName())) {
					StatusVO lifeCycleStatusVO = new StatusVO();
					oldLifeCycleStatusList.add(lifeCycleStatusVO);
					NodeList oldLifeCycleStatusNodes = lifeCyckeChgInfoNode.getChildNodes();
					for (int l = 0; l < oldLifeCycleStatusNodes.getLength(); l++) {
						Node oldLifeCycleStatusNode = oldLifeCycleStatusNodes.item(l);
						if ("ars:StatusName".equals(oldLifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setStatusDesc(oldLifeCycleStatusNode.getTextContent());
						} else if ("ars:StatusExpireTime".equals(oldLifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setExpiryTime(oldLifeCycleStatusNode.getTextContent());
						} else if ("ars:StatusIndex".equals(oldLifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setStatusIndex(oldLifeCycleStatusNode.getTextContent());
						}
					}

				} else if ("ars:NewLifeCycleStatus".equals(lifeCyckeChgInfoNode.getNodeName())) {
					NodeList newLifeCycleStatusNodes = lifeCyckeChgInfoNode.getChildNodes();
					StatusVO lifeCycleStatusVO = new StatusVO();
					newLifeCycleStatusList.add(lifeCycleStatusVO);
					for (int l = 0; l < newLifeCycleStatusNodes.getLength(); l++) {
						Node newLifeCycleStatusNode = newLifeCycleStatusNodes.item(l);
						if ("ars:StatusName".equals(newLifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setStatusDesc(newLifeCycleStatusNode.getTextContent());
						} else if ("ars:StatusExpireTime".equals(newLifeCycleStatusNode.getNodeName())) {
							lifeCycleStatusVO.setExpiryTime(newLifeCycleStatusNode.getTextContent());
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
				creditChgInfoList.add(creditChgInfoVO);
				Node creditChgInfoNode = creditChgInfoNodes.item(i);
				NodeList nodes = creditChgInfoNode.getChildNodes();
				for (int j = 0; j < nodes.getLength(); j++) {
					Node node = nodes.item(j);
					if ("arc:CreditLimitID".equals(node.getNodeName())) {
						creditChgInfoVO.setCreditLimitID(node.getTextContent());
					} else if ("arc:CreditLimitType".equals(node.getNodeName())) {
						creditChgInfoVO.setCreditLimitType(node.getTextContent());
					} else if ("arc:CreditLimitTypeName".equals(node.getNodeName())) {
						creditChgInfoVO.setCreditLimitTypeDesc(node.getTextContent());
					} else if ("arc:OldLeftCreditAmt".equals(node.getNodeName())) {
						creditChgInfoVO.setOldLeftCreditAmount(node.getTextContent());
					} else if ("arc:NewLeftCreditAmt".equals(node.getNodeName())) {
						creditChgInfoVO.setNewLeftCreditAmount(node.getTextContent());
					} else if ("arc:MeasureUnit".equals(node.getNodeName())) {
						creditChgInfoVO.setMeasureUnit(node.getTextContent());
					}
				}
			}
		}
		return rechargeLogVO;
	}
}
