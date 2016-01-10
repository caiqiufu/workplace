package com.apps.esb.service.bss.handler.biz;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.customize.smart.app.handler.crm.SendMessageToSubscriber;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.pojo.SmsVerfiy;
import com.unieap.sms.bo.SmsBO;

@Service("sendLoginVerifyCode")
public class SendLoginVerifyCode extends SoapMessageHandler implements BizHandler {

	public SendLoginVerifyCode() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo,String parameters,Map<String,Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		String verifyCode = getVerifyCode();
		String text = UnieapConstants.getMessage("sms.verify.content", new Object[] { verifyCode });
		SmsVerfiy smsVerfiy = new SmsVerfiy();
		smsVerfiy.setType("V");
		smsVerfiy.setFromBy(UnieapConstants.UNIEAP);
		smsVerfiy.setSendTo(requestInfo.getRequestBody().getServiceNumber());
		smsVerfiy.setContent(text);
		smsVerfiy.setVerifyCode(verifyCode);
		smsVerfiy.setChecked(UnieapConstants.NO);
		SmsBO smsBO = (SmsBO) ServiceUtils.getBean("smsBO");
		smsBO.save(smsVerfiy);

		JSONObject extParametersJson = new JSONObject();
		extParametersJson.put("TEXT", text);
		requestInfo.getRequestBody().setExtParameters(extParametersJson.toString());

		SendMessageToSubscriber sendMessageToSubscriber = (SendMessageToSubscriber) ServiceUtils
				.getBean("sendMessageToSubscriber");
		return sendMessageToSubscriber.process(requestInfo, parameters,extParameters);
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
		return null;
	}

	public String getVerifyCode() {
		double code = Math.random();
		String strCode = Double.toString(code);
		return StringUtils.substring(strCode, strCode.length() - 6);
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		return null;
	}

}
