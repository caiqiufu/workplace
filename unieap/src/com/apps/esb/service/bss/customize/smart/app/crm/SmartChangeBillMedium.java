package com.apps.esb.service.bss.customize.smart.app.crm;

import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;

@Service("changeBillMedium_1")
public class SmartChangeBillMedium extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("changeBillMedium");
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
		if (!json.has("accountId")) {
			throw new Exception("accountId is null");
		}
		if (!json.has("billMediumList")) {
			throw new Exception("billMediumList is null");
		}
		String accountId = json.getString("accountId");
		JSONArray billMediumList = json.getJSONArray("billMediumList");
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCRMSerHeader("SubmitOrderRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement requestBodyElement = bodyElement.addChildElement("SubmitRequestBody", "ser");
		SOAPElement orderElement = requestBodyElement.addChildElement("Order", "ser");
		orderElement.addChildElement("OrderType", "bas").addTextNode("CO139");
		SOAPElement orderItemsElement = requestBodyElement.addChildElement("OrderItems", "ser");
		SOAPElement orderItemElement = orderItemsElement.addChildElement("OrderItem", "bas");
		SOAPElement orderItemInfoElement = orderItemElement.addChildElement("OrderItemInfo", "bas");
		orderItemInfoElement.addChildElement("OrderItemType", "bas").addTextNode("CO139");
		SOAPElement accountElement = orderItemElement.addChildElement("Account", "bas");
		accountElement.addChildElement("AccountId", "bas").addTextNode(accountId);
		SOAPElement billMediumListElement = accountElement.addChildElement("BillMediumList", "bas");
		if (billMediumList != null && billMediumList.length() > 0) {
			for (int i = 0; i < billMediumList.length(); i++) {
				JSONObject jsonObj = billMediumList.getJSONObject(i);
				SOAPElement billMediumInfoElement = billMediumListElement.addChildElement("BillMediumInfo", "bas");
				billMediumInfoElement.addChildElement("BillMediumId", "bas")
						.addTextNode(jsonObj.getString("billMediumId"));
				billMediumInfoElement.addChildElement("ActionType", "bas").addTextNode(jsonObj.getString("actionType"));
			}
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
