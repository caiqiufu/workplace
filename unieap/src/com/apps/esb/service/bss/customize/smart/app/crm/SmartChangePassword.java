package com.apps.esb.service.bss.customize.smart.app.crm;

import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;
import com.unieap.base.SYSConfig;
import com.unieap.encrypt.EncryptionUtils;

@Service("changePassword_1")
public class SmartChangePassword extends CustSoapMessageHandler implements BizHandler{

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			throw new Exception("password is null");
		}
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("oldPassword")) {
			throw new Exception("oldPassword is null");
		}
		if (!json.has("newPassword")) {
			throw new Exception("newPassword is null");
		}
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("changePassword");
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
		String oldPassword = json.getString("oldPassword");
		String newPassword = json.getString("newPassword");
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCRMSerHeader("SubmitOrderRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement requestBodyElement = bodyElement.addChildElement("SubmitRequestBody", "ser");
		SOAPElement orderElement = requestBodyElement.addChildElement("Order", "ser");
		orderElement.addChildElement("OrderType", "bas").addTextNode("CO018");
		SOAPElement orderItemsElement = requestBodyElement.addChildElement("OrderItems", "ser");
		SOAPElement orderItemElement = orderItemsElement.addChildElement("OrderItem", "bas");

		SOAPElement orderItemInfoElement = orderItemElement.addChildElement("OrderItemInfo", "bas");
		SOAPElement orderItemTypeElement = orderItemInfoElement.addChildElement("OrderItemType", "bas");
		orderItemTypeElement.addTextNode("CO018");

		SOAPElement subscriberElement = orderItemElement.addChildElement("Subscriber", "bas");
		SOAPElement passwordInfoListElement = subscriberElement.addChildElement("PasswordInfoList", "bas");
		SOAPElement passwordInfoElement = passwordInfoListElement.addChildElement("PasswordInfo", "bas");
		passwordInfoElement.addChildElement("PasswordType", "bas").addTextNode("2");
		//1: change password, 2: reset password 3: check password
		passwordInfoElement.addChildElement("PwdOperateType", "bas").addTextNode("1");
		String mKey = SYSConfig.getConfig().get("mcare.encryption.key");
		String dOldPassword = EncryptionUtils.decryptSun(oldPassword, mKey);
		String sKey = SYSConfig.getConfig().get("bss.encryption.key");
		String enOldPassword = EncryptionUtils.encryptSunJCE(dOldPassword, sKey);
		
		String dNewPassword = EncryptionUtils.decryptSun(newPassword, mKey);
		String enNewPassword = EncryptionUtils.encryptSunJCE(dNewPassword, sKey);
		
		passwordInfoElement.addChildElement("Password", "bas").addTextNode(enNewPassword);
		passwordInfoElement.addChildElement("OldPassword", "bas").addTextNode(enOldPassword);
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
