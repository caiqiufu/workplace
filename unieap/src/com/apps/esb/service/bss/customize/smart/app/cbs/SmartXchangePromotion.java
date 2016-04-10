package com.apps.esb.service.bss.customize.smart.app.cbs;

import java.util.Map;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.account.xchange.XchangeLogVO;
import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;
import com.unieap.base.SYSConfig;

@Service("xchange_1")
public class SmartXchangePromotion extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("xchangePromotion");
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
		String xChangeType = "";
		String applierNumber = "";
		String receiverNumber = "";
		String exchangeAmount = "";
		if (!StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			JSONObject extParametersJson = new JSONObject(requestInfo.getRequestBody().getExtParameters());
			if (extParametersJson.has("xChangeType")) {
				xChangeType = extParametersJson.getString("xChangeType");
			} else {
				throw new Exception("xChangeType is null");
			}
			if (extParametersJson.has("applierNumber")) {
				applierNumber = extParametersJson.getString("applierNumber");
			} else {
				throw new Exception("applierNumber is null");
			}
			if (extParametersJson.has("receiverNumber")) {
				receiverNumber = extParametersJson.getString("receiverNumber");
			} else {
				throw new Exception("applierNumber is null");
			}
			if (extParametersJson.has("exchangeAmount")) {
				exchangeAmount = extParametersJson.getString("exchangeAmount");
			} else {
				throw new Exception("exchangeAmount is null");
			}
		}

		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCBSArsHeader("XchangePromotionRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("XchangePromotionRequest");
		reqestElement.addChildElement("XchangeType", "arc").addTextNode(xChangeType);
		reqestElement.addChildElement("applierNumber", "arc").addTextNode(applierNumber);
		;
		reqestElement.addChildElement("receiverNumber", "arc").addTextNode(receiverNumber);
		;
		reqestElement.addChildElement("exchangeAmount", "arc").addTextNode(exchangeAmount);
		;
		return message;
	}

	public void getCBSArsHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("arcl", "http://www.huawei.com/ar/wsservice/arcommon");
		envelope.addNamespaceDeclaration("cbs", "http://www.huawei.com/bme/cbsinterface/cbscommon");
		envelope.addNamespaceDeclaration("arc", "http://www.huawei.com/bme/cbsinterface/arcustomizedservices");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method, "arc");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader");
		requestHeaderElement.addChildElement("Version", "cbs").addTextNode("1.0");
		requestHeaderElement.addChildElement("MessageSeq", "cbs").addTextNode(BssServiceUtils.generateTransactionId());

		SOAPElement accessSecurityElement = requestHeaderElement.addChildElement("AccessSecurity", "cbs");
		accessSecurityElement.addChildElement("LoginSystemCode", "cbs")
				.addTextNode(SYSConfig.getConfig().get("cbs.inf.accessUser"));
		accessSecurityElement.addChildElement("Password", "cbs")
				.addTextNode(SYSConfig.getConfig().get("cbs.inf.accessPwd"));
		requestHeaderElement.addChildElement("AccessMode", "cbs")
				.addTextNode(SYSConfig.getConfig().get("cbs.inf.channelCode"));
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		XchangeLogVO xchangeLogVO = new XchangeLogVO();
		result.setVo(xchangeLogVO);
		org.w3c.dom.Document document = BssServiceUtils.getSoapMessageDocument(response);
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("XchangePromotionResult").getLength() > 0) {
			NodeList xchangePromotionResultNodes = document.getElementsByTagName("XchangePromotionResult").item(0)
					.getChildNodes();
			for (int i = 0; i < xchangePromotionResultNodes.getLength(); i++) {
				Node node = xchangePromotionResultNodes.item(i);
				if ("arc:applierNumber".equals(node.getNodeName())) {
					xchangeLogVO.setApplierNumber(node.getTextContent());
				} else if ("arc:receiverNumber".equals(node.getNodeName())) {
					xchangeLogVO.setReceiverNumber(node.getTextContent());
				} else if ("arc:deductAmount".equals(node.getNodeName())) {
					xchangeLogVO.setDeductAmount(node.getTextContent());
				} else if ("arc:transferFee".equals(node.getNodeName())) {
					xchangeLogVO.setTransferFee(node.getTextContent());
				} else if ("arc:bonusAmount".equals(node.getNodeName())) {
					xchangeLogVO.setBonusAmount(node.getTextContent());
				} else if ("arc:bonusExpTime".equals(node.getNodeName())) {
					xchangeLogVO.setExpiryTime(node.getTextContent());
				}
			}
		}
		return result;
	}

}
