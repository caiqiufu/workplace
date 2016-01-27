package com.apps.esb.service.bss.customize.smart.app.handler.crm;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.customize.smart.app.handler.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;

@Service("supplementCustomer_1")
public class SmartSupplementCustomerData extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("supplementCustomer");
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
		if (!json.has("customerInfo")) {
			throw new Exception("customerInfo is null");
		}
		JSONObject customerInfo = json.getJSONObject("customerInfo");
		String customerId = customerInfo.getString("customerId");
		String certificateType = customerInfo.getString("certificateType");
		String certificateNumber = customerInfo.getString("certificateNumber");
		String firstName = customerInfo.getString("firstName");
		String dateOfBirth = customerInfo.getString("dateOfBirth");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Date date = sdf.parse(dateOfBirth);
		String formatDateOfBirth = new SimpleDateFormat("yyyyMMdd").format(date);
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCRMSerHeader("SubmitOrderRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement requestBodyElement = bodyElement.addChildElement("SubmitRequestBody", "ser");
		SOAPElement orderElement = requestBodyElement.addChildElement("Order", "ser");
		orderElement.addChildElement("OrderType", "bas").addTextNode("CO062");
		SOAPElement orderItemsElement = requestBodyElement.addChildElement("OrderItems", "ser");
		SOAPElement orderItemElement = orderItemsElement.addChildElement("OrderItem", "bas");

		SOAPElement orderItemInfoElement = orderItemElement.addChildElement("OrderItemInfo", "bas");
		SOAPElement orderItemTypeElement = orderItemInfoElement.addChildElement("OrderItemType", "bas");
		orderItemTypeElement.addTextNode("CO062");

		SOAPElement customerElement = orderItemElement.addChildElement("Customer", "bas");
		SOAPElement custInfoElement = customerElement.addChildElement("CustInfo", "bas");

		SOAPElement custBaseInfoElement = custInfoElement.addChildElement("CustBaseInfo", "bas");
		SOAPElement customerIdElement = custBaseInfoElement.addChildElement("CustomerId", "bas");
		customerIdElement.addTextNode(customerId);

		SOAPElement individualInfoElement = custInfoElement.addChildElement("IndividualInfo", "bas");
		individualInfoElement.addChildElement("CertificateType", "bas").addTextNode(certificateType);
		individualInfoElement.addChildElement("CertificateNumber", "bas").addTextNode(certificateNumber);
		individualInfoElement.addChildElement("FirstName", "bas").addTextNode(firstName);
		individualInfoElement.addChildElement("DateOfBirth", "bas").addTextNode(formatDateOfBirth);
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
