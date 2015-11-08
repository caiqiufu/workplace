package com.apps.esb.service.bss.app.cbs.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.cbs.vo.recharge.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.BalanceVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.CreditChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.FreeUnitItemVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LifeCycleChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LifeCycleStatusVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.RechargeBonusVO;
import com.apps.esb.service.bss.app.cbs.vo.rechargelog.QueryRechargeLogVO;
import com.apps.esb.service.bss.app.cbs.vo.rechargelog.RechargeInfoVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;

@Service("queryRechargeLogs")
public class QueryRechargeLogs extends SoapMessageHandler implements BizHandler {

	public QueryRechargeLogs() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo,String parameters,Map<String,Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryRechargeLogs");
		return process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(), newRequestInfo.getRequestBody().getExtParameters(), parameters, "ws.cbs.query.timeout");
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
		this.getCBSArsHeader("QueryRechargeLogRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryRechargeLogRequest");
		SOAPElement objElement = reqestElement.addChildElement("QueryObj","ars");
		SOAPElement subAccessCodeElement = objElement.addChildElement("SubAccessCode","ars");
		SOAPElement primaryIdentityElement = subAccessCodeElement.addChildElement("PrimaryIdentity","arc");
		primaryIdentityElement.addTextNode(serviceNumber);
		reqestElement.addChildElement("TotalRowNum","ars").addTextNode("0");
		reqestElement.addChildElement("BeginRowNum","ars").addTextNode("0");
		reqestElement.addChildElement("FetchRowNum","ars").addTextNode("1000");
		Date endTime = UnieapConstants.getDateTime(null);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(endTime);
		rightNow.add(Calendar.DAY_OF_YEAR,
				-Integer.parseInt(SYSConfig.getConfig().get("cbs.query.rechargelog.during")));
		Date beginTime = rightNow.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String beginTimeStr = sdf.format(beginTime);
		String endTimeStr = sdf.format(endTime);
		reqestElement.addChildElement("StartTime","ars").addTextNode(beginTimeStr);
		reqestElement.addChildElement("EndTime","ars").addTextNode(endTimeStr);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		QueryRechargeLogVO queryRechargeLogVO = new QueryRechargeLogVO();
		result.setVo(queryRechargeLogVO);
		List<RechargeInfoVO> rechargeInfoVOs = new ArrayList<RechargeInfoVO>();
		queryRechargeLogVO.setRechargeInfoList(rechargeInfoVOs);
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
				RechargeInfoVO rechargeInfoVO = new RechargeInfoVO();
				rechargeInfoVOs.add(rechargeInfoVO);
				List<LifeCycleChgInfoVO> lifeCycleChgInfoVOs = new ArrayList<LifeCycleChgInfoVO>();
				List<BalanceChgInfoVO> balanceChgInfoVOs = new ArrayList<BalanceChgInfoVO>();
				List<RechargeBonusVO> rechargeBonusVOs = new ArrayList<RechargeBonusVO>();
				List<CreditChgInfoVO> creditChgInfoVOs = new ArrayList<CreditChgInfoVO>();
				rechargeInfoVO.setLifeCycleChgInfoList(lifeCycleChgInfoVOs);
				rechargeInfoVO.setBalanceChgInfoList(balanceChgInfoVOs);
				rechargeInfoVO.setCreditChgInfoList(creditChgInfoVOs);
				Node rechargeInfoNode = rechargeInfoNodes.item(i);
				NodeList nodes = rechargeInfoNode.getChildNodes();
				for (int j = 0; j < nodes.getLength(); j++) {
					Node node = nodes.item(j);
					if ("ars:TradeTime".equals(node.getNodeName())) {
						rechargeInfoVO.setTradeTime(node.getTextContent());
					} else if ("ars:AcctKey".equals(node.getNodeName())) {
						rechargeInfoVO.setAcctKey(node.getTextContent());
					} else if ("ars:SubKey".equals(node.getNodeName())) {
						rechargeInfoVO.setSubKey(node.getTextContent());
					} else if ("ars:PrimaryIdentity".equals(node.getNodeName())) {
						rechargeInfoVO.setPrimaryIdentity(node.getTextContent());
					} else if ("ars:TransID".equals(node.getNodeName())) {
						rechargeInfoVO.setTransID(node.getTextContent());
					} else if ("ars:RechargeAmount".equals(node.getNodeName())) {
						rechargeInfoVO.setRechargeAmount(node.getTextContent());
					} else if ("ars:OriAmount".equals(node.getNodeName())) {
						rechargeInfoVO.setOriAmount(node.getTextContent());
					} else if ("ars:RechargeTax".equals(node.getNodeName())) {
						rechargeInfoVO.setRechargeTax(node.getTextContent());
					} else if ("ars:RechargePenalty".equals(node.getNodeName())) {
						rechargeInfoVO.setRechargePenalty(node.getTextContent());
					} else if ("ars:RechargeType".equals(node.getNodeName())) {
						rechargeInfoVO.setRechargeType(node.getTextContent());
					} else if ("ars:RechargeChannelID".equals(node.getNodeName())) {
						rechargeInfoVO.setRechargeChannelID(node.getTextContent());
					} else if ("ars:ResultCode".equals(node.getNodeName())) {
						rechargeInfoVO.setResultCode(node.getTextContent());
					} else if ("ars:LifeCycleChgInfo".equals(node.getNodeName())) {
						LifeCycleChgInfoVO lifeCycleChgInfoVO = new LifeCycleChgInfoVO();
						lifeCycleChgInfoVOs.add(lifeCycleChgInfoVO);
						List<LifeCycleStatusVO> oldLifeCycleStatusVOs = new ArrayList<LifeCycleStatusVO>();
						List<LifeCycleStatusVO> newLifeCycleStatusVOs = new ArrayList<LifeCycleStatusVO>();
						lifeCycleChgInfoVO.setOldLifeCycleStatus(oldLifeCycleStatusVOs);
						lifeCycleChgInfoVO.setNewLifeCycleStatus(newLifeCycleStatusVOs);
						NodeList lifeCycleChgInfoNodes = node.getChildNodes();
						for (int k = 0; k < lifeCycleChgInfoNodes.getLength(); k++) {
							Node lifeCycleChgInfoNode = lifeCycleChgInfoNodes.item(k);
							if ("ars:OldLifeCycleStatus".equals(lifeCycleChgInfoNode.getNodeName())) {
								NodeList oldLifeCycleStatusNodes = lifeCycleChgInfoNode.getChildNodes();
								LifeCycleStatusVO oldLifeCycleStatusVO = new LifeCycleStatusVO();
								oldLifeCycleStatusVOs.add(oldLifeCycleStatusVO);
								for (int l = 0; l < oldLifeCycleStatusNodes.getLength(); l++) {
									Node oldLifeCycleStatusNode = oldLifeCycleStatusNodes.item(l);
									if ("ars:StatusName".equals(oldLifeCycleStatusNode.getNodeName())) {
										oldLifeCycleStatusVO.setStatusName(oldLifeCycleStatusNode.getTextContent());
									} else if ("ars:StatusExpireTime".equals(oldLifeCycleStatusNode.getNodeName())) {
										oldLifeCycleStatusVO
												.setStatusExpireTime(oldLifeCycleStatusNode.getTextContent());
									} else if ("ars:StatusIndex".equals(oldLifeCycleStatusNode.getNodeName())) {
										oldLifeCycleStatusVO.setStatusIndex(oldLifeCycleStatusNode.getTextContent());
									}
								}
							} else if ("ars:NewLifeCycleStatus".equals(lifeCycleChgInfoNode.getNodeName())) {
								NodeList newLifeCycleStatusNodes = lifeCycleChgInfoNode.getChildNodes();
								LifeCycleStatusVO newLifeCycleStatusVO = new LifeCycleStatusVO();
								newLifeCycleStatusVOs.add(newLifeCycleStatusVO);
								for (int l = 0; l < newLifeCycleStatusNodes.getLength(); l++) {
									Node oldLifeCycleStatusNode = newLifeCycleStatusNodes.item(l);
									if ("ars:StatusName".equals(oldLifeCycleStatusNode.getNodeName())) {
										newLifeCycleStatusVO.setStatusName(oldLifeCycleStatusNode.getTextContent());
									} else if ("ars:StatusExpireTime".equals(oldLifeCycleStatusNode.getNodeName())) {
										newLifeCycleStatusVO
												.setStatusExpireTime(oldLifeCycleStatusNode.getTextContent());
									} else if ("ars:StatusIndex".equals(oldLifeCycleStatusNode.getNodeName())) {
										newLifeCycleStatusVO.setStatusIndex(oldLifeCycleStatusNode.getTextContent());
									}
								}

							}
						}

					} else if ("ars:BalanceChgInfo".equals(node.getNodeName())) {
						BalanceChgInfoVO balanceChgInfoVO = new BalanceChgInfoVO();
						balanceChgInfoVOs.add(balanceChgInfoVO);
						NodeList balanceChgInfoNodes = node.getChildNodes();
						for (int k = 0; k < balanceChgInfoNodes.getLength(); k++) {
							Node balanceChgInfoNode = balanceChgInfoNodes.item(k);
							if ("ars:BalanceType".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setBalanceType(balanceChgInfoNode.getTextContent());
							} else if ("ars:BalanceID".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setBalanceID(balanceChgInfoNode.getTextContent());
							} else if ("ars:BalanceTypeName".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setBalanceTypeName(balanceChgInfoNode.getTextContent());
							} else if ("ars:OldBalanceAmt".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setOldBalanceAmt(balanceChgInfoNode.getTextContent());
							} else if ("ars:NewBalanceAmt".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setNewBalanceAmt(balanceChgInfoNode.getTextContent());
							}
						}
					} else if ("ars:RechargeBonus".equals(node.getNodeName())) {
						RechargeBonusVO rechargeBonusVO = new RechargeBonusVO();
						rechargeBonusVOs.add(rechargeBonusVO);
						List<FreeUnitItemVO> freeUnitItemVOs = new ArrayList<FreeUnitItemVO>();
						rechargeBonusVO.setFreeUnitItems(freeUnitItemVOs);
						List<BalanceVO> balanceVOs = new ArrayList<BalanceVO>();
						rechargeBonusVO.setBalances(balanceVOs);
						NodeList rechargeBonusNodes = node.getChildNodes();
						for (int k = 0; k < rechargeBonusNodes.getLength(); k++) {
							Node rechargeBonusNode = rechargeBonusNodes.item(k);
							if ("ars:FreeUnitItemList".equals(rechargeBonusNode.getTextContent())) {
								NodeList freeUnitItemListNodes = rechargeBonusNode.getChildNodes();
								FreeUnitItemVO freeUnitItemVO = new FreeUnitItemVO();
								freeUnitItemVOs.add(freeUnitItemVO);
								for (int l = 0; l < freeUnitItemListNodes.getLength(); l++) {
									Node freeUnitItemListNode = freeUnitItemListNodes.item(l);
									if ("ars:FreeUnitID".equals(freeUnitItemListNode.getNodeName())) {
										freeUnitItemVO.setFreeUnitID(freeUnitItemListNode.getTextContent());
									} else if ("ars:FreeUnitType".equals(freeUnitItemListNode.getNodeName())) {
										freeUnitItemVO.setFreeUnitType(freeUnitItemListNode.getTextContent());
									} else if ("ars:FreeUnitTypeName".equals(freeUnitItemListNode.getNodeName())) {
										freeUnitItemVO.setFreeUnitTypeName(freeUnitItemListNode.getTextContent());
									} else if ("ars:MeasureUnit".equals(freeUnitItemListNode.getNodeName())) {
										freeUnitItemVO.setMeasureUnit(freeUnitItemListNode.getTextContent());
									} else if ("ars:MeasureUnitName".equals(freeUnitItemListNode.getNodeName())) {
										freeUnitItemVO.setMeasureUnitName(freeUnitItemListNode.getTextContent());
									} else if ("ars:BonusAmt".equals(freeUnitItemListNode.getNodeName())) {
										freeUnitItemVO.setBonusAmt(freeUnitItemListNode.getTextContent());
									} else if ("ars:EffectiveTime".equals(freeUnitItemListNode.getNodeName())) {
										freeUnitItemVO.setEffectiveTime(freeUnitItemListNode.getTextContent());
									} else if ("ars:ExpoireTime".equals(freeUnitItemListNode.getNodeName())) {
										freeUnitItemVO.setExpoireTime(freeUnitItemListNode.getTextContent());
									}
								}
							} else if ("ars:BalanceList".equals(rechargeBonusNode.getTextContent())) {
								NodeList balanceListNodes = rechargeBonusNode.getChildNodes();
								BalanceVO balanceVO = new BalanceVO();
								balanceVOs.add(balanceVO);
								for (int l = 0; l < balanceListNodes.getLength(); l++) {
									Node balanceListNode = balanceListNodes.item(l);
									if ("ars:BalanceType".equals(balanceListNode.getNodeName())) {
										balanceVO.setBalanceType(balanceListNode.getTextContent());
									} else if ("ars:BalanceID".equals(balanceListNode.getNodeName())) {
										balanceVO.setBalanceID(balanceListNode.getTextContent());
									} else if ("ars:BalanceTypeName".equals(balanceListNode.getNodeName())) {
										balanceVO.setBalanceTypeName(balanceListNode.getTextContent());
									} else if ("ars:BonusAmt".equals(balanceListNode.getNodeName())) {
										balanceVO.setBonusAmt(balanceListNode.getTextContent());
									} else if ("ars:EffectiveTime".equals(balanceListNode.getNodeName())) {
										balanceVO.setEffectiveTime(balanceListNode.getTextContent());
									} else if ("ars:ExpireTime".equals(balanceListNode.getNodeName())) {
										balanceVO.setExpireTime(balanceListNode.getTextContent());
									}
								}
							}
						}
					} else if ("ars:CreditChgInfo".equals(node.getNodeName())) {
						CreditChgInfoVO creditChgInfoVO = new CreditChgInfoVO();
						creditChgInfoVOs.add(creditChgInfoVO);
						NodeList creditChgInfoNodes = node.getChildNodes();
						for (int k = 0; k < creditChgInfoNodes.getLength(); k++) {
							Node creditChgInfoNode = creditChgInfoNodes.item(k);
							if ("arc:CreditLimitID".equals(creditChgInfoNode.getNodeName())) {
								creditChgInfoVO.setCreditLimitID(creditChgInfoNode.getTextContent());
							} else if ("arc:CreditLimitType".equals(creditChgInfoNode.getNodeName())) {
								creditChgInfoVO.setCreditLimitType(creditChgInfoNode.getTextContent());
							} else if ("arc:CreditLimitTypeName".equals(creditChgInfoNode.getNodeName())) {
								creditChgInfoVO.setCreditLimitTypeName(creditChgInfoNode.getTextContent());
							} else if ("arc:OldLeftCreditAmt".equals(creditChgInfoNode.getNodeName())) {
								creditChgInfoVO.setOldLeftCreditAmt(creditChgInfoNode.getTextContent());
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
