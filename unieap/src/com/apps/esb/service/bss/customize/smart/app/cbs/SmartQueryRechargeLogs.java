package com.apps.esb.service.bss.customize.smart.app.cbs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.account.balance.BalanceVO;
import com.apps.esb.service.bss.app.vo.account.recharge.QueryRechargeLogsVO;
import com.apps.esb.service.bss.app.vo.account.recharge.RechargeBonusVO;
import com.apps.esb.service.bss.app.vo.account.recharge.RechargeLogVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.CreditChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.LifeCycleChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.freeresource.FreeResourceVO;
import com.apps.esb.service.bss.app.vo.subscriber.lifecycle.StatusVO;
import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;

@Service("queryRechargeLogs_1")
public class SmartQueryRechargeLogs extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryRechargeLogs");
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
		this.getCBSArsHeader("QueryRechargeLogRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryRechargeLogRequest");
		SOAPElement objElement = reqestElement.addChildElement("QueryObj", "ars");
		SOAPElement subAccessCodeElement = objElement.addChildElement("SubAccessCode", "ars");
		SOAPElement primaryIdentityElement = subAccessCodeElement.addChildElement("PrimaryIdentity", "arc");
		primaryIdentityElement.addTextNode(serviceNumber);
		reqestElement.addChildElement("TotalRowNum", "ars").addTextNode("0");
		reqestElement.addChildElement("BeginRowNum", "ars").addTextNode("0");
		reqestElement.addChildElement("FetchRowNum", "ars").addTextNode("1000");
		String[] times = getTimes(1);
		reqestElement.addChildElement("StartTime", "ars").addTextNode(times[0]);
		reqestElement.addChildElement("EndTime", "ars").addTextNode(times[1]);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		QueryRechargeLogsVO queryRechargeLogsVO = new QueryRechargeLogsVO();
		result.setVo(queryRechargeLogsVO);
		List<RechargeLogVO> rechargeLogList = new ArrayList<RechargeLogVO>();
		queryRechargeLogsVO.setRechargeLogList(rechargeLogList);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("ars:RechargeInfo").getLength() > 0) {
			NodeList rechargeInfoNodes = document.getElementsByTagName("ars:RechargeInfo");
			for (int i = 0; i < rechargeInfoNodes.getLength(); i++) {
				RechargeLogVO rechargeLogVO = new RechargeLogVO();
				rechargeLogList.add(rechargeLogVO);
				List<LifeCycleChgInfoVO> lifeCycleChgInfoList = new ArrayList<LifeCycleChgInfoVO>();
				List<BalanceChgInfoVO> balanceChgInfoList = new ArrayList<BalanceChgInfoVO>();
				List<RechargeBonusVO> rechargeBonusList = new ArrayList<RechargeBonusVO>();
				List<CreditChgInfoVO> creditChgInfoList = new ArrayList<CreditChgInfoVO>();
				rechargeLogVO.setLifeCycleChgInfoList(lifeCycleChgInfoList);
				rechargeLogVO.setBalanceChgInfoList(balanceChgInfoList);
				rechargeLogVO.setCreditChgInfoList(creditChgInfoList);
				rechargeLogVO.setRechargeBonusList(rechargeBonusList);
				Node rechargeInfoNode = rechargeInfoNodes.item(i);
				NodeList nodes = rechargeInfoNode.getChildNodes();
				for (int j = 0; j < nodes.getLength(); j++) {
					Node node = nodes.item(j);
					if ("ars:TradeTime".equals(node.getNodeName())) {
						rechargeLogVO.setTradeTime(node.getTextContent());
					} else if ("ars:AcctKey".equals(node.getNodeName())) {
						rechargeLogVO.setAccountId(node.getTextContent());
					} else if ("ars:SubKey".equals(node.getNodeName())) {
						rechargeLogVO.setSubscriberId(node.getTextContent());
					} else if ("ars:PrimaryIdentity".equals(node.getNodeName())) {
						rechargeLogVO.setServiceNumber(node.getTextContent());
					} else if ("ars:TransID".equals(node.getNodeName())) {
						rechargeLogVO.setTransId(node.getTextContent());
					} else if ("ars:RechargeAmount".equals(node.getNodeName())) {
						rechargeLogVO.setRechargeAmount(node.getTextContent());
					} else if ("ars:OriAmount".equals(node.getNodeName())) {
						rechargeLogVO.setOriginalAmount(node.getTextContent());
					} else if ("ars:RechargeTax".equals(node.getNodeName())) {
						rechargeLogVO.setRechargeTax(node.getTextContent());
					} else if ("ars:RechargePenalty".equals(node.getNodeName())) {
						rechargeLogVO.setRechargePenalty(node.getTextContent());
					} else if ("ars:RechargeType".equals(node.getNodeName())) {
						rechargeLogVO.setRechargeType(node.getTextContent());
					} else if ("ars:RechargeChannelID".equals(node.getNodeName())) {
						rechargeLogVO.setRechargeChannelID(node.getTextContent());
					} else if ("ars:ResultCode".equals(node.getNodeName())) {
						rechargeLogVO.setResultCode(node.getTextContent());
					} else if ("ars:LifeCycleChgInfo".equals(node.getNodeName())) {
						LifeCycleChgInfoVO lifeCycleChgInfoVO = new LifeCycleChgInfoVO();
						lifeCycleChgInfoList.add(lifeCycleChgInfoVO);
						List<StatusVO> oldLifeCycleStatusList = new ArrayList<StatusVO>();
						List<StatusVO> newLifeCycleStatusList = new ArrayList<StatusVO>();
						lifeCycleChgInfoVO.setOldLifeCycleStatusList(oldLifeCycleStatusList);
						lifeCycleChgInfoVO.setNewLifeCycleStatusList(newLifeCycleStatusList);
						NodeList lifeCycleChgInfoNodes = node.getChildNodes();
						for (int k = 0; k < lifeCycleChgInfoNodes.getLength(); k++) {
							Node lifeCycleChgInfoNode = lifeCycleChgInfoNodes.item(k);
							if ("ars:OldLifeCycleStatus".equals(lifeCycleChgInfoNode.getNodeName())) {
								NodeList oldLifeCycleStatusNodes = lifeCycleChgInfoNode.getChildNodes();
								StatusVO oldLifeCycleStatusVO = new StatusVO();
								oldLifeCycleStatusList.add(oldLifeCycleStatusVO);
								for (int l = 0; l < oldLifeCycleStatusNodes.getLength(); l++) {
									Node oldLifeCycleStatusNode = oldLifeCycleStatusNodes.item(l);
									if ("ars:StatusName".equals(oldLifeCycleStatusNode.getNodeName())) {
										oldLifeCycleStatusVO.setStatusDesc(oldLifeCycleStatusNode.getTextContent());
									} else if ("ars:StatusExpireTime".equals(oldLifeCycleStatusNode.getNodeName())) {
										oldLifeCycleStatusVO.setExpiryTime(oldLifeCycleStatusNode.getTextContent());
									} else if ("ars:StatusIndex".equals(oldLifeCycleStatusNode.getNodeName())) {
										oldLifeCycleStatusVO.setStatusIndex(oldLifeCycleStatusNode.getTextContent());
									}
								}
							} else if ("ars:NewLifeCycleStatus".equals(lifeCycleChgInfoNode.getNodeName())) {
								NodeList newLifeCycleStatusNodes = lifeCycleChgInfoNode.getChildNodes();
								StatusVO newLifeCycleStatusVO = new StatusVO();
								newLifeCycleStatusList.add(newLifeCycleStatusVO);
								for (int l = 0; l < newLifeCycleStatusNodes.getLength(); l++) {
									Node oldLifeCycleStatusNode = newLifeCycleStatusNodes.item(l);
									if ("ars:StatusName".equals(oldLifeCycleStatusNode.getNodeName())) {
										newLifeCycleStatusVO.setStatusDesc(oldLifeCycleStatusNode.getTextContent());
									} else if ("ars:StatusExpireTime".equals(oldLifeCycleStatusNode.getNodeName())) {
										newLifeCycleStatusVO.setExpiryTime(oldLifeCycleStatusNode.getTextContent());
									} else if ("ars:StatusIndex".equals(oldLifeCycleStatusNode.getNodeName())) {
										newLifeCycleStatusVO.setStatusIndex(oldLifeCycleStatusNode.getTextContent());
									}
								}

							}
						}

					} else if ("ars:BalanceChgInfo".equals(node.getNodeName())) {
						BalanceChgInfoVO balanceChgInfoVO = new BalanceChgInfoVO();
						balanceChgInfoList.add(balanceChgInfoVO);
						NodeList balanceChgInfoNodes = node.getChildNodes();
						for (int k = 0; k < balanceChgInfoNodes.getLength(); k++) {
							Node balanceChgInfoNode = balanceChgInfoNodes.item(k);
							if ("ars:BalanceType".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setBalanceType(balanceChgInfoNode.getTextContent());
							} else if ("ars:BalanceID".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setBalanceID(balanceChgInfoNode.getTextContent());
							} else if ("ars:BalanceTypeName".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setBalanceTypeDesc(balanceChgInfoNode.getTextContent());
							} else if ("ars:OldBalanceAmt".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setOldBalanceAmount(balanceChgInfoNode.getTextContent());
							} else if ("ars:NewBalanceAmt".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setNewBalanceAmount(balanceChgInfoNode.getTextContent());
							}
						}
					} else if ("ars:RechargeBonus".equals(node.getNodeName())) {
						RechargeBonusVO rechargeBonusVO = new RechargeBonusVO();
						rechargeBonusList.add(rechargeBonusVO);
						List<FreeResourceVO> freeResourceList = new ArrayList<FreeResourceVO>();
						rechargeBonusVO.setFreeResourceList(freeResourceList);
						List<BalanceVO> balanceList = new ArrayList<BalanceVO>();
						rechargeBonusVO.setBalanceList(balanceList);
						NodeList rechargeBonusNodes = node.getChildNodes();
						for (int k = 0; k < rechargeBonusNodes.getLength(); k++) {
							Node rechargeBonusNode = rechargeBonusNodes.item(k);
							if ("ars:FreeUnitItemList".equals(rechargeBonusNode.getTextContent())) {
								NodeList freeUnitItemListNodes = rechargeBonusNode.getChildNodes();
								FreeResourceVO freeResourceVO = new FreeResourceVO();
								freeResourceList.add(freeResourceVO);
								for (int l = 0; l < freeUnitItemListNodes.getLength(); l++) {
									Node freeUnitItemListNode = freeUnitItemListNodes.item(l);
									if ("ars:FreeUnitID".equals(freeUnitItemListNode.getNodeName())) {
										freeResourceVO.setFreeResourceID(freeUnitItemListNode.getTextContent());
									} else if ("ars:FreeUnitType".equals(freeUnitItemListNode.getNodeName())) {
										freeResourceVO.setFreeResourceType(freeUnitItemListNode.getTextContent());
									} else if ("ars:FreeUnitTypeName".equals(freeUnitItemListNode.getNodeName())) {
										freeResourceVO.setFreeResourceTypeDesc(freeUnitItemListNode.getTextContent());
									} else if ("ars:MeasureUnit".equals(freeUnitItemListNode.getNodeName())) {
										freeResourceVO.setMeasureUnit(freeUnitItemListNode.getTextContent());
									} else if ("ars:MeasureUnitName".equals(freeUnitItemListNode.getNodeName())) {
										freeResourceVO.setMeasureUnitDesc(freeUnitItemListNode.getTextContent());
									} /*
										 * else if ("ars:BonusAmt".equals(
										 * freeUnitItemListNode.getNodeName()))
										 * {
										 * freeUnitItemVO.setTotalUnusedAmount(
										 * freeUnitItemListNode.getTextContent()
										 * ); } else if
										 * ("ars:EffectiveTime".equals(
										 * freeUnitItemListNode.getNodeName()))
										 * { freeUnitItemVO.setEffectiveTime(
										 * freeUnitItemListNode.getTextContent()
										 * ); } else if
										 * ("ars:ExpoireTime".equals(
										 * freeUnitItemListNode.getNodeName()))
										 * { freeUnitItemVO.setExpoireTime(
										 * freeUnitItemListNode.getTextContent()
										 * ); }
										 */
								}
							} else if ("ars:BalanceList".equals(rechargeBonusNode.getTextContent())) {
								NodeList balanceListNodes = rechargeBonusNode.getChildNodes();
								BalanceVO balanceVO = new BalanceVO();
								for (int l = 0; l < balanceListNodes.getLength(); l++) {
									Node balanceListNode = balanceListNodes.item(l);
									if ("ars:BalanceType".equals(balanceListNode.getNodeName())) {
										balanceVO.setBalanceType(balanceListNode.getTextContent());
									} else if ("ars:BalanceID".equals(balanceListNode.getNodeName())) {
										balanceVO.setBalanceID(balanceListNode.getTextContent());
									} else if ("ars:BalanceTypeName".equals(balanceListNode.getNodeName())) {
										balanceVO.setBalanceTypeDesc(balanceListNode.getTextContent());
									} else if ("ars:BonusAmt".equals(balanceListNode.getNodeName())) {
										balanceVO.setTotalAmount(balanceListNode.getTextContent());
									} else if ("ars:EffectiveTime".equals(balanceListNode.getNodeName())) {
										balanceVO.setEffectiveTime(balanceListNode.getTextContent());
									} else if ("ars:ExpireTime".equals(balanceListNode.getNodeName())) {
										balanceVO.setExpiryTime(balanceListNode.getTextContent());
									}
								}
							}
						}
					} else if ("ars:CreditChgInfo".equals(node.getNodeName())) {
						CreditChgInfoVO creditChgInfoVO = new CreditChgInfoVO();
						creditChgInfoList.add(creditChgInfoVO);
						NodeList creditChgInfoNodes = node.getChildNodes();
						for (int k = 0; k < creditChgInfoNodes.getLength(); k++) {
							Node creditChgInfoNode = creditChgInfoNodes.item(k);
							if ("arc:CreditLimitID".equals(creditChgInfoNode.getNodeName())) {
								creditChgInfoVO.setCreditLimitID(creditChgInfoNode.getTextContent());
							} else if ("arc:CreditLimitType".equals(creditChgInfoNode.getNodeName())) {
								creditChgInfoVO.setCreditLimitType(creditChgInfoNode.getTextContent());
							} else if ("arc:CreditLimitTypeName".equals(creditChgInfoNode.getNodeName())) {
								creditChgInfoVO.setCreditLimitTypeDesc(creditChgInfoNode.getTextContent());
							} else if ("arc:OldLeftCreditAmt".equals(creditChgInfoNode.getNodeName())) {
								creditChgInfoVO.setOldLeftCreditAmount(creditChgInfoNode.getTextContent());
							} else if ("arc:NewLeftCreditAmt".equals(creditChgInfoNode.getNodeName())) {
								creditChgInfoVO.setCreditLimitType(creditChgInfoNode.getTextContent());
							} else if ("arc:MeasureUnit".equals(creditChgInfoNode.getNodeName())) {
								creditChgInfoVO.setMeasureUnit(creditChgInfoNode.getTextContent());
							}
						}
					}
				}
			}
		}
		return result;
	}

}
