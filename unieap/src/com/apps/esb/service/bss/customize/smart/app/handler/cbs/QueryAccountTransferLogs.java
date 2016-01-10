package com.apps.esb.service.bss.customize.smart.app.handler.cbs;

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
import com.apps.esb.service.bss.app.vo.querybalance.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.vo.querybalance.CreditChgInfoVO;
import com.apps.esb.service.bss.app.vo.querybalance.FreeUnitChgInfoVO;
import com.apps.esb.service.bss.app.vo.querytransferbalance.QueryTransferBalanceLogVO;
import com.apps.esb.service.bss.app.vo.querytransferbalance.TransferInfoVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;

@Service("queryAccountTransferLogs")
public class QueryAccountTransferLogs implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo,Map<String, String> handler,Map<String,Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryAccountTransferLogs");
		return SoapCallUtils.process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(), newRequestInfo.getRequestBody().getExtParameters(), handler, "ws.cbs.query.timeout");
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
		SOAPMessage message = SoapCallUtils.messageFactory.createMessage();
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryTransferLogRequest");
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
				-Integer.parseInt(SYSConfig.getConfig().get("cbs.query.transferbalncelog.during")));
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
		QueryTransferBalanceLogVO queryTransferBalanceLogVO = new QueryTransferBalanceLogVO();
		result.setVo(queryTransferBalanceLogVO);
		List<TransferInfoVO> transferInfoList = new ArrayList<TransferInfoVO>();
		queryTransferBalanceLogVO.setTransferInfoList(transferInfoList);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("ars:TransferInfo").getLength() > 0) {
			NodeList transferInfoNodes = document.getElementsByTagName("ars:TransferInfo");
			for (int i = 0; i < transferInfoNodes.getLength(); i++) {
				TransferInfoVO transferInfoVO = new TransferInfoVO();
				transferInfoList.add(transferInfoVO);
				List<BalanceChgInfoVO> balanceChgInfos = new ArrayList<BalanceChgInfoVO>();
				List<FreeUnitChgInfoVO> freeUnitChgInfos = new ArrayList<FreeUnitChgInfoVO>();
				List<CreditChgInfoVO> creditChgInfos = new ArrayList<CreditChgInfoVO>();
				transferInfoVO.setBalanceChgInfos(balanceChgInfos);
				transferInfoVO.setCreditChgInfos(creditChgInfos);
				transferInfoVO.setFreeUnitChgInfos(freeUnitChgInfos);

				Node transferInfoNode = transferInfoNodes.item(i);
				NodeList nodes = transferInfoNode.getChildNodes();
				for (int j = 0; j < nodes.getLength(); j++) {
					Node node = nodes.item(j);
					if ("ars:TradeTime".equals(node.getNodeName())) {
						transferInfoVO.setTradeTime(node.getTextContent());
					} else if ("ars:AcctKey".equals(node.getNodeName())) {
						transferInfoVO.setAcctKey(node.getTextContent());
					} else if ("ars:SubKey".equals(node.getNodeName())) {
						transferInfoVO.setSubKey(node.getTextContent());
					} else if ("ars:PrimaryIdentity".equals(node.getNodeName())) {
						transferInfoVO.setPrimaryIdentity(node.getTextContent());
					} else if ("ars:TransferChannelID".equals(node.getNodeName())) {
						transferInfoVO.setTransferChannelID(node.getTextContent());
					} else if ("ars:TransferAmount".equals(node.getNodeName())) {
						transferInfoVO.setTransferAmount(node.getTextContent());
					} else if ("ars:OppositePrimaryIdentity".equals(node.getNodeName())) {
						transferInfoVO.setOppositePrimaryIdentity(node.getTextContent());
					} else if ("ars:OppositeAcctKey".equals(node.getNodeName())) {
						transferInfoVO.setOppositeAcctKey(node.getTextContent());
					} else if ("ars:ResultCode".equals(node.getNodeName())) {
						transferInfoVO.setResultCode(node.getTextContent());
					} else if ("ars:BalanceChgInfo".equals(node.getNodeName())) {
						BalanceChgInfoVO balanceChgInfoVO = new BalanceChgInfoVO();
						balanceChgInfos.add(balanceChgInfoVO);
						NodeList balanceChgInfoNodes = node.getChildNodes();
						for (int k = 0; k < balanceChgInfoNodes.getLength(); k++) {
							Node balanceChgInfoNode = balanceChgInfoNodes.item(k);
							if ("ars:BalanceType".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setBalanceType(balanceChgInfoNode.getTextContent());
							} else if ("ars:BalanceID".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setBalanceId(balanceChgInfoNode.getTextContent());
							} else if ("ars:BalanceTypeName".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setBalanceTypeName(balanceChgInfoNode.getTextContent());
							} else if ("ars:OldBalanceAmt".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setOldBalanceAmt(balanceChgInfoNode.getTextContent());
							} else if ("ars:NewBalanceAmt".equals(balanceChgInfoNode.getTextContent())) {
								balanceChgInfoVO.setNewBalanceAmt(balanceChgInfoNode.getTextContent());
							}
						}
					} else if ("ars:CreditChgInfo".equals(node.getNodeName())) {
						CreditChgInfoVO creditChgInfoVO = new CreditChgInfoVO();
						creditChgInfos.add(creditChgInfoVO);
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
					} else if ("ars:FreeUnitChgInfo".equals(node.getNodeName())) {
						FreeUnitChgInfoVO freeUnitChgInfoVO = new FreeUnitChgInfoVO();
						freeUnitChgInfos.add(freeUnitChgInfoVO);
						NodeList freeUnitChgInfoNodes = node.getChildNodes();
						for (int k = 0; k < freeUnitChgInfoNodes.getLength(); k++) {
							Node freeUnitChgInfoNode = freeUnitChgInfoNodes.item(k);
							if ("arc:FreeUnitInstanceID".equals(freeUnitChgInfoNode.getNodeName())) {
								freeUnitChgInfoVO.setFreeUnitInstanceID(freeUnitChgInfoNode.getTextContent());
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
					}
				}
			}
		}
		return result;
	}

}
