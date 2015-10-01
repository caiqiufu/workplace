package com.apps.esb.service.bss.handler.biz;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssErrorCode;
import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.pojo.SmsVerfiy;
import com.unieap.sms.bo.SmsBO;

@Service("sendSMS")
public class SendSMS extends SoapMessageHandler implements BizHandler {

	public SendSMS() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String serviceNumber, String extParameters, String parameters)
			throws Exception {
		if (StringUtils.isEmpty(serviceNumber)) {
			throw new Exception("serviceNumber is null");
		}
		ProcessResult processResult = checkSubscriberExisting(requestInfo,serviceNumber);
		if(BssErrorCode.C1.equals(processResult.getResultCode())){
			return process(this, requestInfo, serviceNumber, extParameters, parameters, "ws.crm.query.timeout");
		}else{
			return processResult;
		}
	}
	public ProcessResult checkSubscriberExisting(RequestInfo requestInfo,String serviceNumber) throws Exception{
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("C005");
		BizHandler handler = (BizHandler) ServiceUtils.getBean("checkSubscriberExisting");
		ProcessResult processResult = handler.process(newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(),
				requestInfo.getRequestBody().getExtParameters(), "");
		return processResult;
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
		SOAPElement requestBodyElement = bodyElement.addChildElement("SendMessageToSubsBody");
		requestBodyElement.addChildElement("ServiceNumber").addTextNode(serviceNumber);
		String verifyCode = getVerifyCode();
		String text = UnieapConstants.getMessage("sms.verify.content",new Object[]{verifyCode});
		requestBodyElement.addChildElement("TEXT").addTextNode(text);
		SmsVerfiy smsVerfiy = new SmsVerfiy();
		smsVerfiy.setType("V");
		smsVerfiy.setFromBy(UnieapConstants.UNIEAP);
		smsVerfiy.setSendTo(serviceNumber);
		smsVerfiy.setContent(text);
		smsVerfiy.setVerifyCode(verifyCode);
		smsVerfiy.setChecked(UnieapConstants.NO);
		SmsBO smsBO = (SmsBO) ServiceUtils.getBean("smsBO");
		smsBO.save(smsVerfiy);
		return message;
	}
	public String getVerifyCode() {
		double code = Math.random();
		String strCode = Double.toString(code);
		return StringUtils.substring(strCode, strCode.length() - 6);
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
