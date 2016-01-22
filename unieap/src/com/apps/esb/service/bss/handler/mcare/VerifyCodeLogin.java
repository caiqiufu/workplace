package com.apps.esb.service.bss.handler.mcare;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.customer.basicinfo.CustomerVO;
import com.apps.esb.service.bss.app.vo.subscriber.basicinfo.SubscriberVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.encrypt.EncryptionUtils;
import com.unieap.sms.bo.SmsBO;

@Service("verifyCodeLogin_1")
public class VerifyCodeLogin implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			throw new Exception("verifyCode is null");
		}
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("verifyCode")) {
			throw new Exception("verifyCode is null");
		}
		String verifyCodeEn = json.getString("verifyCode");
		String mKey = SYSConfig.getConfig().get("mcare.encryption.key");
		String verifyCode = EncryptionUtils.decryptSun(verifyCodeEn, mKey);

		SmsBO smsBO = (SmsBO) ServiceUtils.getBean("smsBO");
		String returnCode = smsBO.checkVerifyCode("V", requestInfo.getRequestBody().getServiceNumber(), verifyCode);
		ProcessResult processResult = new ProcessResult();
		if (UnieapConstants.C0.equals(returnCode)) {
			BizHandler getSubscriptionData = (BizHandler) ServiceUtils.getBean("getSubscriptionData");
			ProcessResult processResultSubscriberData = getSubscriptionData.process(requestInfo, handler,
					extParameters);
			SubscriberVO subscriberVO = (SubscriberVO) processResultSubscriberData.getVo();

			JSONObject customerObj = new JSONObject("{customerId:" + subscriberVO.getCustomerId() + "}");
			JSONObject newExtParameters = BssServiceUtils
					.modifyExtParameters(requestInfo.getRequestBody().getExtParameters(), customerObj);
			requestInfo.getRequestBody().setExtParameters(newExtParameters.toString());

			BizHandler getCustomerData = (BizHandler) ServiceUtils.getBean("getCustomerData");
			ProcessResult processResultGetCustomerData = getCustomerData.process(requestInfo, handler, extParameters);
			CustomerVO customerVO = (CustomerVO) processResultGetCustomerData.getVo();

			subscriberVO.setCustomerVO(customerVO);
			processResult.setVo(subscriberVO);
			processResult.setResultCode(UnieapConstants.C0);
			processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
			return processResult;
		} else {
			processResult.setResultCode(returnCode);
			processResult.setResultDesc(UnieapConstants.getMessage(returnCode));
			processResult.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
		}
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
