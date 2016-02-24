package com.apps.esb.service.bss.customize.smart.app.handler.cbs;

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
import com.apps.esb.service.bss.app.vo.account.transfer.TransferBalanceVO;
import com.apps.esb.service.bss.app.vo.account.transfer.TransfereeVO;
import com.apps.esb.service.bss.app.vo.account.transfer.TransferorVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.CreditChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.FreeResourceChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.LifeCycleChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.LoanChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.lifecycle.StatusVO;
import com.apps.esb.service.bss.customize.smart.app.handler.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;

@Service("transfer_1")
public class SmartTransfer extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("transfer");
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
		String transferorNumber = "";
		String transfereeNumber = "";
		String transferAmount = "";
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
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCBSArsHeader("TransferBalanceRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("TransferBalanceRequest");
		reqestElement.addChildElement("TransferType", "ars").addTextNode("2");
		SOAPElement transferorObjElement = reqestElement.addChildElement("TransferorAcct", "ars");
		SOAPElement transferorPrimaryIdentityElement = transferorObjElement.addChildElement("PrimaryIdentity", "arc");
		transferorPrimaryIdentityElement.addTextNode(transferorNumber);
		SOAPElement transfereeObjElement = reqestElement.addChildElement("TransfereeAcct", "ars");
		SOAPElement transfereePrimaryIdentityElement = transfereeObjElement.addChildElement("PrimaryIdentity", "arc");
		transfereePrimaryIdentityElement.addTextNode(transfereeNumber);
		reqestElement.addChildElement("TransferAmount", "ars").addTextNode(transferAmount);
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
		TransferBalanceVO transferBalanceVO = getAccountTransferResponse(document);
		result.setVo(transferBalanceVO);
		return result;
	}

	public TransferBalanceVO getAccountTransferResponse(Document document) {
		TransferBalanceVO transferBalanceVO = new TransferBalanceVO();
		TransferorVO transferorVO = new TransferorVO();
		TransfereeVO transfereeVO = new TransfereeVO();
		transferBalanceVO.setTransferorVO(transferorVO);
		transferBalanceVO.setTransferorVO(transferorVO);
		List<BalanceChgInfoVO> balanceChgInfoVOs = new ArrayList<BalanceChgInfoVO>();
		if (document.getElementsByTagName("ars:Transferor").getLength() > 0) {
			List<BalanceChgInfoVO> balanceChgInfoList = new ArrayList<BalanceChgInfoVO>();
			LifeCycleChgInfoVO lifeCycleChgInfoVO = new LifeCycleChgInfoVO();
			List<FreeResourceChgInfoVO> freeResourceChgInfoList = new ArrayList<FreeResourceChgInfoVO>();
			List<CreditChgInfoVO> creditChgInfoList = new ArrayList<CreditChgInfoVO>();
			transferorVO.setBalanceChgInfoList(balanceChgInfoList);
			transferorVO.setCreditChgInfoList(creditChgInfoList);
			transferorVO.setFreeResourceChgInfoList(freeResourceChgInfoList);
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
							balanceChgInfoVO.setBalanceTypeDesc(balanceChgInfoNode.getTextContent());
						} else if ("arc:OldBalanceAmt".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setOldBalanceAmount(balanceChgInfoNode.getTextContent());
						} else if ("arc:NewBalanceAmt".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setNewBalanceAmount(balanceChgInfoNode.getTextContent());
						}
					}
				} else if ("ars:HandlingChargeAmt".equals(transferorNode.getNodeName())) {
					transferorVO.setHandlingChargeAmt(transferorNode.getTextContent());
				} else if ("ars:LifeCycleChgInfo".equals(transferorNode.getNodeName())) {
					List<StatusVO> oldLifeCycleStatusList = new ArrayList<StatusVO>();
					lifeCycleChgInfoVO.setOldLifeCycleStatusList(oldLifeCycleStatusList);
					List<StatusVO> newLifeCycleStatusList = new ArrayList<StatusVO>();
					lifeCycleChgInfoVO.setNewLifeCycleStatusList(newLifeCycleStatusList);
					NodeList lifeCycleChgInfoNodes = transferorNode.getChildNodes();
					for (int j = 0; j < lifeCycleChgInfoNodes.getLength(); j++) {
						Node lifeCyckeChgInfoNode = lifeCycleChgInfoNodes.item(j);
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

				} else if ("ars:FreeUnitChgInfo".equals(transferorNode.getNodeName())) {
					FreeResourceChgInfoVO freeResourceChgInfoVO = new FreeResourceChgInfoVO();
					freeResourceChgInfoList.add(freeResourceChgInfoVO);
					NodeList freeUnitChgInfoNodes = transferorNode.getChildNodes();
					for (int j = 0; j < freeUnitChgInfoNodes.getLength(); j++) {
						Node freeUnitChgInfoNode = freeUnitChgInfoNodes.item(j);
						if ("ars:FreeUnitInstanceID".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setFreeResourceInstanceID(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:FreeUnitType".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setFreeResourceType(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:FreeUnitTypeName".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setFreeResourceTypeDesc(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:MeasureUnit".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setMeasureUnit(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:MeasureUnitName".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setMeasureUnitDesc(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:OldAmt".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setOldAmount(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:NewAmt".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setNewAmount(freeUnitChgInfoNode.getTextContent());
						}
					}
				} else if ("ars:CreditChgInfo".equals(transferorNode.getNodeName())) {
					CreditChgInfoVO creditChgInfoVO = new CreditChgInfoVO();
					creditChgInfoList.add(creditChgInfoVO);
					NodeList creditChgInfoNodes = transferorNode.getChildNodes();
					for (int j = 0; j < creditChgInfoNodes.getLength(); j++) {
						Node creditChgInfoNode = creditChgInfoNodes.item(j);
						if ("arc:CreditLimitID".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setCreditLimitID(creditChgInfoNode.getTextContent());
						} else if ("arc:CreditLimitType".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setCreditLimitType(creditChgInfoNode.getTextContent());
						} else if ("arc:CreditLimitTypeName".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setCreditLimitTypeDesc(creditChgInfoNode.getTextContent());
						} else if ("arc:OldLeftCreditAmt".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setOldLeftCreditAmount(creditChgInfoNode.getTextContent());
						} else if ("arc:NewLeftCreditAmt".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setNewLeftCreditAmount(creditChgInfoNode.getTextContent());
						} else if ("arc:MeasureUnit".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setMeasureUnit(creditChgInfoNode.getTextContent());
						}
					}
				}
			}
		}
		if (document.getElementsByTagName("ars:Transferee").getLength() > 0) {
			List<BalanceChgInfoVO> balanceChgInfoList = new ArrayList<BalanceChgInfoVO>();
			LoanChgInfoVO loanChgInfoVO = new LoanChgInfoVO();
			LifeCycleChgInfoVO lifeCycleChgInfoVO = new LifeCycleChgInfoVO();
			List<FreeResourceChgInfoVO> freeResourceChgInfoList = new ArrayList<FreeResourceChgInfoVO>();
			List<CreditChgInfoVO> creditChgInfoList = new ArrayList<CreditChgInfoVO>();
			transfereeVO.setBalanceChgInfoList(balanceChgInfoList);
			transfereeVO.setLoanChgInfoVO(loanChgInfoVO);
			transfereeVO.setCreditChgInfoList(creditChgInfoList);
			transfereeVO.setFreeResourceChgInfoList(freeResourceChgInfoList);
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
							balanceChgInfoVO.setBalanceTypeDesc(balanceChgInfoNode.getTextContent());
						} else if ("arc:OldBalanceAmt".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setOldBalanceAmount(balanceChgInfoNode.getTextContent());
						} else if ("arc:NewBalanceAmt".equals(balanceChgInfoNode.getNodeName())) {
							balanceChgInfoVO.setNewBalanceAmount(balanceChgInfoNode.getTextContent());
						}
					}
				} else if ("ars:HandlingChargeAmt".equals(transfereeNode.getNodeName())) {
					transferorVO.setHandlingChargeAmt(transfereeNode.getTextContent());
				} else if ("ars:LifeCycleChgInfo".equals(transfereeNode.getNodeName())) {
					List<StatusVO> oldLifeCycleStatusList = new ArrayList<StatusVO>();
					lifeCycleChgInfoVO.setOldLifeCycleStatusList(oldLifeCycleStatusList);
					List<StatusVO> newLifeCycleStatusList = new ArrayList<StatusVO>();
					lifeCycleChgInfoVO.setNewLifeCycleStatusList(newLifeCycleStatusList);
					NodeList lifeCycleChgInfoNodes = transfereeNode.getChildNodes();
					for (int j = 0; j < lifeCycleChgInfoNodes.getLength(); j++) {
						Node lifeCyckeChgInfoNode = lifeCycleChgInfoNodes.item(j);
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

				} else if ("ars:FreeUnitChgInfo".equals(transfereeNode.getNodeName())) {
					FreeResourceChgInfoVO freeResourceChgInfoVO = new FreeResourceChgInfoVO();
					freeResourceChgInfoList.add(freeResourceChgInfoVO);
					NodeList freeUnitChgInfoNodes = transfereeNode.getChildNodes();
					for (int j = 0; j < freeUnitChgInfoNodes.getLength(); j++) {
						Node freeUnitChgInfoNode = freeUnitChgInfoNodes.item(j);
						if ("ars:FreeUnitInstanceID".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setFreeResourceInstanceID(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:FreeUnitType".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setFreeResourceType(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:FreeUnitTypeName".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setFreeResourceTypeDesc(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:MeasureUnit".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setMeasureUnit(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:MeasureUnitName".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setMeasureUnitDesc(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:OldAmt".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setOldAmount(freeUnitChgInfoNode.getTextContent());
						} else if ("arc:NewAmt".equals(freeUnitChgInfoNode.getNodeName())) {
							freeResourceChgInfoVO.setNewAmount(freeUnitChgInfoNode.getTextContent());
						}
					}
				} else if ("ars:CreditChgInfo".equals(transfereeNode.getNodeName())) {
					CreditChgInfoVO creditChgInfoVO = new CreditChgInfoVO();
					creditChgInfoList.add(creditChgInfoVO);
					NodeList creditChgInfoNodes = transfereeNode.getChildNodes();
					for (int j = 0; j < creditChgInfoNodes.getLength(); j++) {
						Node creditChgInfoNode = creditChgInfoNodes.item(j);
						if ("arc:CreditLimitID".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setCreditLimitID(creditChgInfoNode.getTextContent());
						} else if ("arc:CreditLimitType".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setCreditLimitType(creditChgInfoNode.getTextContent());
						} else if ("arc:CreditLimitTypeName".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setCreditLimitTypeDesc(creditChgInfoNode.getTextContent());
						} else if ("arc:OldLeftCreditAmt".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setOldLeftCreditAmount(creditChgInfoNode.getTextContent());
						} else if ("arc:NewLeftCreditAmt".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setNewLeftCreditAmount(creditChgInfoNode.getTextContent());
						} else if ("arc:MeasureUnit".equals(creditChgInfoNode.getNodeName())) {
							creditChgInfoVO.setMeasureUnit(creditChgInfoNode.getTextContent());
						}
					}
				} else if ("ars:LoanChgInfo".equals(transfereeNode.getNodeName())) {
					NodeList loanChgInfoNodes = transfereeNode.getChildNodes();
					for (int j = 0; j < loanChgInfoNodes.getLength(); j++) {
						Node loanChgInfoNode = loanChgInfoNodes.item(j);
						if ("arc:OldLoanAmt".equals(loanChgInfoNode.getNodeName())) {
							loanChgInfoVO.setOldLoanAmount(loanChgInfoNode.getTextContent());
						} else if ("arc:NewLoanAmt".equals(loanChgInfoNode.getNodeName())) {
							loanChgInfoVO.setNewLoanAmount(loanChgInfoNode.getTextContent());
						} else if ("arc:LoanPaymentAmt".equals(loanChgInfoNode.getNodeName())) {
							loanChgInfoVO.setLoanPaymentAmount(loanChgInfoNode.getTextContent());
						} else if ("arc:LoanInterestAmt".equals(loanChgInfoNode.getNodeName())) {
							loanChgInfoVO.setLoanInterestAmount(loanChgInfoNode.getTextContent());
						}
					}
				}
			}
		}
		return transferBalanceVO;
	}
}
