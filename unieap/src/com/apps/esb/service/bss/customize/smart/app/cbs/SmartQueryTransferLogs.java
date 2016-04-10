package com.apps.esb.service.bss.customize.smart.app.cbs;

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
import com.apps.esb.service.bss.app.vo.account.transfer.QueryTransferLogsVO;
import com.apps.esb.service.bss.app.vo.account.transfer.TransferLogVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.CreditChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.FreeResourceChgInfoVO;
import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;

@Service("queryTransferLogs_1")
public class SmartQueryTransferLogs extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo,Map<String, String> handler,Map<String,Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryTransferLogs");
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
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCBSArsHeader("QueryTransferLogRequestMsg", message);
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
		QueryTransferLogsVO queryTransferLogsVO = new QueryTransferLogsVO();
		result.setVo(queryTransferLogsVO);
		List<TransferLogVO> transferLogList = new ArrayList<TransferLogVO>();
		queryTransferLogsVO.setTransferLogList(transferLogList);
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
				TransferLogVO transferLogVO = new TransferLogVO();
				transferLogList.add(transferLogVO);
				List<BalanceChgInfoVO> balanceChgInfoList = new ArrayList<BalanceChgInfoVO>();
				List<FreeResourceChgInfoVO> freeResourceChgInfoList = new ArrayList<FreeResourceChgInfoVO>();
				List<CreditChgInfoVO> creditChgInfoList = new ArrayList<CreditChgInfoVO>();
				transferLogVO.setBalanceChgInfoList(balanceChgInfoList);
				transferLogVO.setCreditChgInfoList(creditChgInfoList);
				transferLogVO.setFreeResourceChgInfoList(freeResourceChgInfoList);

				Node transferInfoNode = transferInfoNodes.item(i);
				NodeList nodes = transferInfoNode.getChildNodes();
				for (int j = 0; j < nodes.getLength(); j++) {
					Node node = nodes.item(j);
					if ("ars:TradeTime".equals(node.getNodeName())) {
						transferLogVO.setTradeTime(node.getTextContent());
					} else if ("ars:AcctKey".equals(node.getNodeName())) {
						transferLogVO.setAccountId(node.getTextContent());
					} else if ("ars:SubKey".equals(node.getNodeName())) {
						transferLogVO.setSubscriberId(node.getTextContent());
					} else if ("ars:PrimaryIdentity".equals(node.getNodeName())) {
						transferLogVO.setServiceNumber(node.getTextContent());
					} else if ("ars:TransferChannelID".equals(node.getNodeName())) {
						transferLogVO.setTransferChannelID(node.getTextContent());
					} else if ("ars:TransferAmount".equals(node.getNodeName())) {
						transferLogVO.setTransferAmount(node.getTextContent());
					} else if ("ars:OppositePrimaryIdentity".equals(node.getNodeName())) {
						transferLogVO.setOppositeServiceNumber(node.getTextContent());
					} else if ("ars:OppositeAcctKey".equals(node.getNodeName())) {
						transferLogVO.setOppositeAccountId(node.getTextContent());
					} else if ("ars:ResultCode".equals(node.getNodeName())) {
						transferLogVO.setResultCode(node.getTextContent());
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
					} else if ("ars:FreeUnitChgInfo".equals(node.getNodeName())) {
						FreeResourceChgInfoVO freeResourceChgInfoVO = new FreeResourceChgInfoVO();
						freeResourceChgInfoList.add(freeResourceChgInfoVO);
						NodeList freeUnitChgInfoNodes = node.getChildNodes();
						for (int k = 0; k < freeUnitChgInfoNodes.getLength(); k++) {
							Node freeUnitChgInfoNode = freeUnitChgInfoNodes.item(k);
							if ("arc:FreeUnitInstanceID".equals(freeUnitChgInfoNode.getNodeName())) {
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
					}
				}
			}
		}
		return result;
	}

}
