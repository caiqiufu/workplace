package com.apps.esb.service.bss.customize.smart.app.crm;

import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;

@Service("changeSubscriberStatus_1")
public class SmartChangeSubscriberStatus extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("changeSubscriberStatus");
		return SoapCallUtils.process(this, newRequestInfo, requestInfo.getRequestBody().getServiceNumber(),
				requestInfo.getRequestBody().getExtParameters(), handler, "ws.crm.query.timeout");
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
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("operateType")) {
			throw new Exception("operateType is null");
		}
		if (!json.has("reasonType")) {
			throw new Exception("reasonType is null");
		}
		String operateType = json.getString("operateType");
		String orderCode;
		if("6".equals(operateType)){
			orderCode = "CO020";
		}else if("1".equals(operateType)){
			orderCode = "CO066";
		}else if("3".equals(operateType)){
			orderCode = "CO066";
		}else if("2".equals(operateType)){
			orderCode = "CO067";
		}else if("4".equals(operateType)){
			orderCode = "CO067";
		}else if("5".equals(operateType)){
			orderCode = "CO068";
		}else{
			throw new Exception("operateType not in pre-define list");
		}
		String reasonType = json.getString("reasonType");
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCRMSerHeader("SubmitOrderRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement requestBodyElement = bodyElement.addChildElement("SubmitRequestBody", "ser");
		SOAPElement orderElement = requestBodyElement.addChildElement("Order", "ser");
		orderElement.addChildElement("OrderType", "bas").addTextNode(orderCode);
		SOAPElement orderItemsElement = requestBodyElement.addChildElement("OrderItems", "ser");
		SOAPElement orderItemElement = orderItemsElement.addChildElement("OrderItem", "bas");

		SOAPElement orderItemInfoElement = orderItemElement.addChildElement("OrderItemInfo", "bas");
		SOAPElement orderItemTypeElement = orderItemInfoElement.addChildElement("OrderItemType", "bas");
		orderItemTypeElement.addTextNode(orderCode);

		SOAPElement subscriberElement = orderItemElement.addChildElement("Subscriber", "bas");
		subscriberElement.addChildElement("ServiceNumber", "bas").addTextNode(serviceNumber);
		subscriberElement.addChildElement("OperateType", "bas").addTextNode(operateType);
		subscriberElement.addChildElement("ReasonType", "bas").addTextNode(reasonType);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("bas:RetCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("bas:RetCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("bas:RetMsg").getLength() > 0) {

			String retMsg = document.getElementsByTagName("bas:RetMsg").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		return result;
	}

}
