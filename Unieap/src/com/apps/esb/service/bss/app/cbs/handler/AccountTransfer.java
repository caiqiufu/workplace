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
import com.apps.esb.service.bss.app.cbs.vo.recharge.CreditChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LifeCycleChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LifeCycleStatusVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LoanChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.transferbalance.TransferBalanceResponseVO;
import com.apps.esb.service.bss.app.cbs.vo.transferbalance.TransfereeVO;
import com.apps.esb.service.bss.app.cbs.vo.transferbalance.TransferorVO;
import com.apps.esb.service.bss.app.cbs.vo.transferbalancelog.FreeUnitChgInfoVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.base.SYSConfig;

@Service("accountTransfer")
public class AccountTransfer extends SoapMessageHandler implements BizHandler {

	public AccountTransfer() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo,String parameters,Map<String,Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("accountTransfer");
		return process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(),  newRequestInfo.getRequestBody().getExtParameters(), parameters, "ws.cbs.query.timeout");

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
		String transferorNumber = "";
		String transfereeNumber = "";
		String transferAmount = "";
		String transferHandleFee = "0";
		String transfeeHandleFee = "0";
		if (!StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			JSONObject extParametersJson = new JSONObject(requestInfo.getRequestBody().getExtParameters());
			if (extParametersJson.has("transferorNumber")) {
				transferorNumber = extParametersJson.getString("transferorNumber");
			} else {
				throw new Exception("transferorNumber is null");
			}
			if (extParametersJson.has("transfereeNumber")) {
				transfereeNumber = extParametersJson.getString("transfereeNumber");
			} else {
				throw new Exception("transfereeNumber is null");
			}
			if (extParametersJson.has("transferAmount")) {
				transferAmount = extParametersJson.getString("transferAmount");
			} else {
				throw new Exception("transferAmount is null");
			}
		}
		if (SYSConfig.getConfig().get("cbs.transferbalnce.transferhandlefee") != null) {
			transferHandleFee = SYSConfig.getConfig().get("cbs.transferbalnce.transferhandlefee").toString();
		}
		if (SYSConfig.getConfig().get("cbs.transferbalnce.transfeehandlefee") != null) {
			transfeeHandleFee = SYSConfig.getConfig().get("cbs.transferbalnce.transfeehandlefee").toString();
		}

		SOAPMessage message = messageFactory.createMessage();
		this.getCBSArsHeader("TransferBalanceRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("TransferBalanceRequest");
		reqestElement.addChildElement("TransferType").addTextNode("2");
		reqestElement.addChildElement("TransferAmount").addTextNode(transferAmount);
		reqestElement.addChildElement("TransferHandleFee").addTextNode(transferHandleFee);
		reqestElement.addChildElement("TransfeeHandleFee").addTextNode(transfeeHandleFee);
		SOAPElement transferorObjElement = reqestElement.addChildElement("TransferorObj");
		SOAPElement transferorSubAccessCodeElement = transferorObjElement.addChildElement("SubAccessCode");
		SOAPElement transferorPrimaryIdentityElement = transferorSubAccessCodeElement
				.addChildElement("PrimaryIdentity");
		transferorPrimaryIdentityElement.addTextNode(transferorNumber);
		SOAPElement transfereeObjElement = reqestElement.addChildElement("TransfereeObj");
		SOAPElement transfereeSubAccessCodeElement = transfereeObjElement.addChildElement("SubAccessCode");
		SOAPElement transfereePrimaryIdentityElement = transfereeSubAccessCodeElement
				.addChildElement("PrimaryIdentity");
		transfereePrimaryIdentityElement.addTextNode(transfereeNumber);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		TransferBalanceResponseVO transferBalanceResponseVO = getAccountTransferResponse(document);
		result.setVo(transferBalanceResponseVO);
		return result;
	}

	public TransferBalanceResponseVO getAccountTransferResponse(Document document) {
		TransferBalanceResponseVO transferBalanceResponseVO = new TransferBalanceResponseVO();
		TransferorVO transferorVO = new TransferorVO();
		TransfereeVO transfereeVO = new TransfereeVO();
		transferBalanceResponseVO.setTransferorVO(transferorVO);
		transferBalanceResponseVO.setTransferorVO(transferorVO);
		List<BalanceChgInfoVO> balanceChgInfoVOs = new ArrayList<BalanceChgInfoVO>();
		if (document.getElementsByTagName("ars:Transferor").getLength() > 0) {
			List<BalanceChgInfoVO> balanceChgInfos = new ArrayList<BalanceChgInfoVO>();
			LifeCycleChgInfoVO lifeCycleChgInfoVO = new LifeCycleChgInfoVO();
			List<FreeUnitChgInfoVO> freeUnitChgInfos = new ArrayList<FreeUnitChgInfoVO>();
			List<CreditChgInfoVO> creditChgInfos = new ArrayList<CreditChgInfoVO>();
			transferorVO.setBalanceChgInfos(balanceChgInfos);
			transferorVO.setCreditChgInfos(creditChgInfos);
			transferorVO.setFreeUnitChgInfos(freeUnitChgInfos);
			transferorVO.setLifeCycleChgInfoVO(lifeCycleChgInfoVO);
			NodeList transferorNodes = document.getElementsByTagName("ars:Transferor").item(0).getChildNodes();
			for (int i = 0; i < transferorNodes.getLength(); i++) {
				Node transferorNode = transferorNodes.item(i);
				if ("ars:BalanceChgInfo".equals(transferorNode.getNodeName())) {
					BalanceChgInfoVO balanceChgInfoVO = new BalanceChgInfoVO();
					balanceChgInfoVOs.add(balanceChgInfoVO);
					NodeList balanceChgInfoNodes = transferorNode.getChildNodes();
					for (int j = 0; j < balanceChgInfoNodes.getLength(); j++) {
						Node balanceChgInfoNode = balanceChgInfoNodes.item(j);
						if ("arc:BalanceType".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setBalanceType(balanceChgInfoNode.getTextContent());
						} else if ("arc:BalanceID".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setBalanceID(balanceChgInfoNode.getTextContent());
						} else if ("arc:BalanceTypeName".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setBalanceTypeName(balanceChgInfoNode.getTextContent());
						} else if ("arc:OldBalanceAmt".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setOldBalanceAmt(balanceChgInfoNode.getTextContent());
						} else if ("arc:NewBalanceAmt".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setNewBalanceAmt(balanceChgInfoNode.getTextContent());
						}
					}
				} else if ("ars:HandlingChargeAmt".equals(transferorNode.getNodeName())) {
					transferorVO.setHandlingChargeAmt(transferorNode.getTextContent());
				} else if ("ars:LifeCycleChgInfo".equals(transferorNode.getNodeName())) {
					List<LifeCycleStatusVO> oldLifeCycleStatus = new ArrayList<LifeCycleStatusVO>();
					lifeCycleChgInfoVO.setOldLifeCycleStatus(oldLifeCycleStatus);
					List<LifeCycleStatusVO> newLifeCycleStatus = new ArrayList<LifeCycleStatusVO>();
					lifeCycleChgInfoVO.setNewLifeCycleStatus(newLifeCycleStatus);
					NodeList lifeCycleChgInfoNodes = transferorNode.getChildNodes();
					for (int j = 0; j < lifeCycleChgInfoNodes.getLength(); j++) {
						Node lifeCyckeChgInfoNode = lifeCycleChgInfoNodes.item(j);
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

				} else if ("ars:FreeUnitChgInfo".equals(transferorNode.getNodeName())) {
					FreeUnitChgInfoVO freeUnitChgInfoVO = new FreeUnitChgInfoVO();
					freeUnitChgInfos.add(freeUnitChgInfoVO);
					NodeList freeUnitChgInfoNodes = transferorNode.getChildNodes();
					for (int j = 0; j < freeUnitChgInfoNodes.getLength(); j++) {
						Node freeUnitChgInfoNode = freeUnitChgInfoNodes.item(j);
						if ("ars:FreeUnitInstanceID".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setFreeUnitInstanceID(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:FreeUnitType".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setFreeUnitType(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:FreeUnitType".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setFreeUnitType(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:FreeUnitTypeName".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setFreeUnitTypeName(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:MeasureUnit".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setMeasureUnit(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:MeasureUnitName".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setMeasureUnitName(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:OldAmt".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setOldAmt(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:NewAmt".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setNewAmt(freeUnitChgInfoNode.getTextContent());
						}
					}
				} else if ("ars:CreditChgInfo".equals(transferorNode.getNodeName())) {
					CreditChgInfoVO creditChgInfoVO = new CreditChgInfoVO();
					creditChgInfos.add(creditChgInfoVO);
					NodeList creditChgInfoNodes = transferorNode.getChildNodes();
					for (int j = 0; j < creditChgInfoNodes.getLength(); j++) {
						Node creditChgInfoNode = creditChgInfoNodes.item(j);
						if ("arc:CreditLimitID".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setCreditLimitID(creditChgInfoNode.getTextContent());
						} else if ("arc:CreditLimitType".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setCreditLimitType(creditChgInfoNode.getTextContent());
						} else if ("arc:CreditLimitTypeName".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setCreditLimitTypeName(creditChgInfoNode.getTextContent());
						} else if ("arc:OldLeftCreditAmt".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setOldLeftCreditAmt(creditChgInfoNode.getTextContent());
						} else if ("arc:NewLeftCreditAmt".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setNewLeftCreditAmt(creditChgInfoNode.getTextContent());
						} else if ("arc:MeasureUnit".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setMeasureUnit(creditChgInfoNode.getTextContent());
						}
					}
				}
			}
		}
		if (document.getElementsByTagName("ars:Transferee").getLength() > 0) {
			List<BalanceChgInfoVO> balanceChgInfos = new ArrayList<BalanceChgInfoVO>();
			LoanChgInfoVO loanChgInfoVO = new LoanChgInfoVO();
			LifeCycleChgInfoVO lifeCycleChgInfoVO = new LifeCycleChgInfoVO();
			List<FreeUnitChgInfoVO> freeUnitChgInfos = new ArrayList<FreeUnitChgInfoVO>();
			List<CreditChgInfoVO> creditChgInfos = new ArrayList<CreditChgInfoVO>();
			transfereeVO.setBalanceChgInfos(balanceChgInfos);
			transfereeVO.setLoanChgInfoVO(loanChgInfoVO);
			transfereeVO.setCreditChgInfos(creditChgInfos);
			transfereeVO.setFreeUnitChgInfos(freeUnitChgInfos);
			transfereeVO.setLifeCycleChgInfoVO(lifeCycleChgInfoVO);
			NodeList transfereeNodes = document.getElementsByTagName("ars:Transferor").item(0).getChildNodes();
			for (int i = 0; i < transfereeNodes.getLength(); i++) {
				Node transfereeNode = transfereeNodes.item(i);
				if ("ars:BalanceChgInfo".equals(transfereeNode.getNodeName())) {
					BalanceChgInfoVO balanceChgInfoVO = new BalanceChgInfoVO();
					balanceChgInfoVOs.add(balanceChgInfoVO);
					NodeList balanceChgInfoNodes = transfereeNode.getChildNodes();
					for (int j = 0; j < balanceChgInfoNodes.getLength(); j++) {
						Node balanceChgInfoNode = balanceChgInfoNodes.item(j);
						if ("arc:BalanceType".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setBalanceType(balanceChgInfoNode.getTextContent());
						} else if ("arc:BalanceID".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setBalanceID(balanceChgInfoNode.getTextContent());
						} else if ("arc:BalanceTypeName".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setBalanceTypeName(balanceChgInfoNode.getTextContent());
						} else if ("arc:OldBalanceAmt".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setOldBalanceAmt(balanceChgInfoNode.getTextContent());
						} else if ("arc:NewBalanceAmt".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setNewBalanceAmt(balanceChgInfoNode.getTextContent());
						}
					}
				} else if ("ars:HandlingChargeAmt".equals(transfereeNode.getNodeName())) {
					transferorVO.setHandlingChargeAmt(transfereeNode.getTextContent());
				} else if ("ars:LifeCycleChgInfo".equals(transfereeNode.getNodeName())) {
					List<LifeCycleStatusVO> oldLifeCycleStatus = new ArrayList<LifeCycleStatusVO>();
					lifeCycleChgInfoVO.setOldLifeCycleStatus(oldLifeCycleStatus);
					List<LifeCycleStatusVO> newLifeCycleStatus = new ArrayList<LifeCycleStatusVO>();
					lifeCycleChgInfoVO.setNewLifeCycleStatus(newLifeCycleStatus);
					NodeList lifeCycleChgInfoNodes = transfereeNode.getChildNodes();
					for (int j = 0; j < lifeCycleChgInfoNodes.getLength(); j++) {
						Node lifeCyckeChgInfoNode = lifeCycleChgInfoNodes.item(j);
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

				} else if ("ars:FreeUnitChgInfo".equals(transfereeNode.getNodeName())) {
					FreeUnitChgInfoVO freeUnitChgInfoVO = new FreeUnitChgInfoVO();
					freeUnitChgInfos.add(freeUnitChgInfoVO);
					NodeList freeUnitChgInfoNodes = transfereeNode.getChildNodes();
					for (int j = 0; j < freeUnitChgInfoNodes.getLength(); j++) {
						Node freeUnitChgInfoNode = freeUnitChgInfoNodes.item(j);
						if ("ars:FreeUnitInstanceID".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setFreeUnitInstanceID(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:FreeUnitType".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setFreeUnitType(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:FreeUnitType".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setFreeUnitType(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:FreeUnitTypeName".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setFreeUnitTypeName(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:MeasureUnit".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setMeasureUnit(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:MeasureUnitName".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setMeasureUnitName(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:OldAmt".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setOldAmt(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:NewAmt".equals(freeUnitChgInfoNode.getNodeName())) {
							freeUnitChgInfoVO.setNewAmt(freeUnitChgInfoNode.getTextContent());
						}
					}
				} else if ("ars:CreditChgInfo".equals(transfereeNode.getNodeName())) {
					CreditChgInfoVO creditChgInfoVO = new CreditChgInfoVO();
					creditChgInfos.add(creditChgInfoVO);
					NodeList creditChgInfoNodes = transfereeNode.getChildNodes();
					for (int j = 0; j < creditChgInfoNodes.getLength(); j++) {
						Node creditChgInfoNode = creditChgInfoNodes.item(j);
						if ("arc:CreditLimitID".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setCreditLimitID(creditChgInfoNode.getTextContent());
						} else if ("arc:CreditLimitType".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setCreditLimitType(creditChgInfoNode.getTextContent());
						} else if ("arc:CreditLimitTypeName".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setCreditLimitTypeName(creditChgInfoNode.getTextContent());
						} else if ("arc:OldLeftCreditAmt".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setOldLeftCreditAmt(creditChgInfoNode.getTextContent());
						} else if ("arc:NewLeftCreditAmt".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setNewLeftCreditAmt(creditChgInfoNode.getTextContent());
						} else if ("arc:MeasureUnit".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setMeasureUnit(creditChgInfoNode.getTextContent());
						}
					}
				} else if ("ars:LoanChgInfo".equals(transfereeNode.getNodeName())) {
					NodeList loanChgInfoNodes = transfereeNode.getChildNodes();
					for (int j = 0; j < loanChgInfoNodes.getLength(); j++) {
						Node loanChgInfoNode = loanChgInfoNodes.item(j);
						if ("arc:OldLoanAmt".equals(loanChgInfoNode.getNodeName())) {
							loanChgInfoVO.setOldLoanAmt(loanChgInfoNode.getTextContent());
						} else if ("arc:NewLoanAmt".equals(loanChgInfoNode.getNodeName())) {
							loanChgInfoVO.setNewLoanAmt(loanChgInfoNode.getTextContent());
						} else if ("arc:LoanPaymentAmt".equals(loanChgInfoNode.getNodeName())) {
							loanChgInfoVO.setLoanPaymentAmt(loanChgInfoNode.getTextContent());
						} else if ("arc:LoanInterestAmt".equals(loanChgInfoNode.getNodeName())) {
							loanChgInfoVO.setLoanInterestAmt(loanChgInfoNode.getTextContent());
						}
					}
				}
			}
		}
		return transferBalanceResponseVO;
	}
}
