package com.apps.esb.service.bss.app.cbs.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.cbs.vo.xchangelog.QueryXchangeLogVO;
import com.apps.esb.service.bss.app.cbs.vo.xchangelog.XchangeInfoVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;

@Service("queryXchangePromotionLogs")
public class QueryXchangePromotionLogs extends SoapMessageHandler implements BizHandler {

	public QueryXchangePromotionLogs() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryXchangeLogs");
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
		this.getCBSArsHeader("QueryXchangeLogRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryXchangeLogRequest");
		reqestElement.addChildElement("QueryNumber", "arc").addTextNode(serviceNumber);
		reqestElement.addChildElement("TotalRowNum", "arc").addTextNode("0");
		reqestElement.addChildElement("BeginRowNum", "arc").addTextNode("0");
		reqestElement.addChildElement("FetchRowNum", "arc").addTextNode("1000");

		Date endTime = UnieapConstants.getDateTime(null);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(endTime);
		rightNow.add(Calendar.DAY_OF_YEAR,
				-Integer.parseInt(SYSConfig.getConfig().get("cbs.query.rechargelog.during")));
		Date beginTime = rightNow.getTime();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
		String beginTimeStr = sdf.format(beginTime);
		String endTimeStr = sdf.format(endTime);
		reqestElement.addChildElement("StartTime", "arc").addTextNode(beginTimeStr);
		reqestElement.addChildElement("EndTime", "arc").addTextNode(endTimeStr);
		return message;
	}

	public void getCBSArsHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("arcl", "http://www.huawei.com/ar/wsservice/arcommon");
		envelope.addNamespaceDeclaration("cbs", "http://www.huawei.com/bme/cbsinterface/cbscommon");
		envelope.addNamespaceDeclaration("arc", "http://www.huawei.com/bme/cbsinterface/arcustomizedservices");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method,"arc");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader");
		requestHeaderElement.addChildElement("Version","cbs").addTextNode("1.0");
		requestHeaderElement.addChildElement("MessageSeq","cbs").addTextNode(BssServiceUtils.generateTransactionId());
		
		SOAPElement accessSecurityElement = requestHeaderElement.addChildElement("AccessSecurity","cbs");
		accessSecurityElement.addChildElement("LoginSystemCode","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessUser"));
		accessSecurityElement.addChildElement("Password","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessPwd"));
		requestHeaderElement.addChildElement("AccessMode","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.channelCode"));
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		QueryXchangeLogVO queryXchangeLogVO = new QueryXchangeLogVO();
		result.setVo(queryXchangeLogVO);
		List<XchangeInfoVO> xchangeInfos = new ArrayList<XchangeInfoVO>();
		queryXchangeLogVO.setXchangeInfos(xchangeInfos);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("arc:TotalRowNum").getLength() > 0) {
			queryXchangeLogVO.setTotalRowNum(document.getElementsByTagName("arc:TotalRowNum").item(0).getTextContent());
		}
		if (document.getElementsByTagName("arc:BeginRowNum").getLength() > 0) {
			queryXchangeLogVO.setBeginRowNum(document.getElementsByTagName("arc:BeginRowNum").item(0).getTextContent());
		}
		if (document.getElementsByTagName("arc:FetchRowNum").getLength() > 0) {
			queryXchangeLogVO.setFetchRowNum(document.getElementsByTagName("arc:FetchRowNum").item(0).getTextContent());
		}
		if (document.getElementsByTagName("arc:XchangeInfo").getLength() > 0) {
			NodeList nodes = document.getElementsByTagName("arc:XchangeInfo");
			for (int i = 0; i < nodes.getLength(); i++) {
				XchangeInfoVO xchangeInfoVO = new XchangeInfoVO();
				xchangeInfos.add(xchangeInfoVO);
				NodeList xchangeInfoNodes = nodes.item(i).getChildNodes();
				for (int j = 0; j < xchangeInfoNodes.getLength(); j++) {
					Node xchangeInfoNode = xchangeInfoNodes.item(j);
					if ("arc:LogId".equals(xchangeInfoNode.getNodeName())) {
						xchangeInfoVO.setLogId(xchangeInfoNode.getTextContent());
					} else if ("arc:TradeTime".equals(xchangeInfoNode.getNodeName())) {
						xchangeInfoVO.setTradeTime(xchangeInfoNode.getTextContent());
					} else if ("arc:applierNumber".equals(xchangeInfoNode.getNodeName())) {
						xchangeInfoVO.setApplierNumber(xchangeInfoNode.getTextContent());
					} else if ("arc:recieverNumber".equals(xchangeInfoNode.getNodeName())) {
						xchangeInfoVO.setReceiverNumber(xchangeInfoNode.getTextContent());
					} else if ("arc:deductAmount".equals(xchangeInfoNode.getNodeName())) {
						xchangeInfoVO.setDeductAmount(xchangeInfoNode.getTextContent());
					} else if ("arc:transferFee".equals(xchangeInfoNode.getNodeName())) {
						xchangeInfoVO.setTransferFee(xchangeInfoNode.getTextContent());
					} else if ("arc:bonusAmount".equals(xchangeInfoNode.getNodeName())) {
						xchangeInfoVO.setBonusAmount(xchangeInfoNode.getTextContent());
					} else if ("arc:bonusExpTime".equals(xchangeInfoNode.getNodeName())) {
						xchangeInfoVO.setBonusExpTime(xchangeInfoNode.getTextContent());
					}
				}
			}
		}

		return result;
	}

}
