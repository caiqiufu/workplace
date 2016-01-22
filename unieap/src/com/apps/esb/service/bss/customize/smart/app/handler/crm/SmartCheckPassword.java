package com.apps.esb.service.bss.customize.smart.app.handler.crm;

import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

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
import com.unieap.base.SYSConfig;
import com.unieap.encrypt.EncryptionUtils;

@Service("smartCheckPassword")
public class SmartCheckPassword extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			throw new Exception("password is null");
		}
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("password")) {
			throw new Exception("password is null");
		}
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("checkPassword");
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
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCRMQuerHeader("CheckPasswordRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement requestBodyElement = bodyElement.addChildElement("CheckPasswordBody", "quer");
		requestBodyElement.addChildElement("ServiceNumber", "quer").addTextNode(serviceNumber);
		SOAPElement passwordInfoElement = requestBodyElement.addChildElement("PasswordInfo", "quer");
		// 1 customer service password
		// 2 subscriber service password
		// 3 subscriber web password
		// 4 subscriber IVR password
		// 5 corporate admin password
		// 6 dealer web password
		passwordInfoElement.addChildElement("PasswordType", "bas").addTextNode("2");
		// 1 change password
		// 2 reset password
		// 3 check password
		passwordInfoElement.addChildElement("PwdOperateType", "bas").addTextNode("3");
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (json.has("password")) {
			String sSrc = json.getString("password");
			String mKey = SYSConfig.getConfig().get("mcare.encryption.key");
			String dSrc = EncryptionUtils.decryptSun(sSrc, mKey);
			String sKey = SYSConfig.getConfig().get("bss.encryption.key");
			String enPassword = EncryptionUtils.encryptSunJCE(dSrc, sKey);
			passwordInfoElement.addChildElement("Password", "bas").addTextNode(enPassword.trim());
		} else {
			throw new Exception("password is null");
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
