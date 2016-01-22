package com.apps.esb.service.bss.customize.smart.app.handler.crm;

import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.customize.smart.app.handler.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;

@Service("changeSupplementaryOffering_1")
public class SmartChangeSupplementaryOffering extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("changeSupplementaryOffering");
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
		JSONObject offeringInfo = json.getJSONObject("offeringInfo");
		if (!offeringInfo.has("supplementaryOfferings")
				|| offeringInfo.getJSONArray("supplementaryOfferings").length() == 0) {
			throw new Exception("supplementaryOfferings is null");
		}
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCRMSerHeader("SubmitOrderRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement requestBodyElement = bodyElement.addChildElement("SubmitRequestBody", "ser");
		SOAPElement orderElement = requestBodyElement.addChildElement("Order", "ser");
		orderElement.addChildElement("OrderType", "bas").addTextNode("CO025");
		SOAPElement orderItemsElement = requestBodyElement.addChildElement("OrderItems", "ser");
		SOAPElement orderItemElement = orderItemsElement.addChildElement("OrderItem", "bas");
		SOAPElement orderItemInfoElement = orderItemElement.addChildElement("OrderItemInfo", "bas");
		orderItemInfoElement.addChildElement("OrderItemType", "bas").addTextNode("CO025");
		SOAPElement subscriberElement = orderItemElement.addChildElement("Subscriber", "bas");
		subscriberElement.addChildElement("ServiceNumber", "bas").addTextNode(serviceNumber.trim());
		SOAPElement supplementaryOfferingListElement = subscriberElement.addChildElement("SupplementaryOfferingList",
				"bas");
		JSONArray offerings = offeringInfo.getJSONArray("supplementaryOfferings");
		for (int i = 0; i < offerings.length(); i++) {
			JSONObject offeringJSONObject = offerings.getJSONObject(i);
			String offeringId = offeringJSONObject.getString("offeringId");
			if (StringUtils.isEmpty(offeringId)) {
				throw new Exception("offeringId is null");
			}
			String actionType = offeringJSONObject.getString("actionType");
			if (StringUtils.isEmpty(actionType)) {
				throw new Exception("actionType is null");
			}
			SOAPElement offeringInfoElement = supplementaryOfferingListElement.addChildElement("OfferingInfo", "bas");
			offeringInfoElement.addChildElement("ActionType", "bas").addTextNode(actionType.trim());
			SOAPElement offeringIdElement = offeringInfoElement.addChildElement("OfferingId", "bas");
			offeringIdElement.addChildElement("OfferingId", "bas").addTextNode(offeringId.trim());
		}
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