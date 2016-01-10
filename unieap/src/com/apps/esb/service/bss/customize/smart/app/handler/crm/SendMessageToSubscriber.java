package com.apps.esb.service.bss.customize.smart.app.handler.crm;

import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.base.SYSConfig;
import com.unieap.encrypt.EncryptionUtils;

@Service("sendMessageToSubscriber")
public class SendMessageToSubscriber extends SoapMessageHandler implements BizHandler {

	public SendMessageToSubscriber() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters,Map<String,Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			throw new Exception("SMS text is null");
		}
		JSONObject extParametersJson = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if(!extParametersJson.has("TEXT")){
			throw new Exception("SMS text is null");
		}
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("sendMessageToSubscriber");
		return process(this, newRequestInfo, requestInfo.getRequestBody().getServiceNumber(), requestInfo.getRequestBody().getExtParameters(), parameters,"ws.crm.query.timeout");
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
		this.getCRMUpdHeader("SendMessageToSubscriberRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement requestBodyElement = bodyElement.addChildElement("SendMessageToSubsBody","upd");
		SOAPElement subsInListElement = requestBodyElement.addChildElement("SendMessageToSubsInList","upd");
		SOAPElement subsInInfoElement = subsInListElement.addChildElement("SendMessageToSubsInInfo","upd");
		subsInInfoElement.addChildElement("ServiceNumber","upd").addTextNode(serviceNumber);
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (json.has("TEXT")) {
			String sKey =  SYSConfig.getConfig().get("bss.encryption.key");
			String sSrc = json.getString("TEXT");
			String enPassword = EncryptionUtils.encryptSunJCE(sSrc, sKey);
			subsInInfoElement.addChildElement("TEXT","upd").addTextNode(enPassword.trim());
		} else {
			throw new Exception("SMS text is null");
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
