package com.apps.esb.service.bss.handler.biz;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssErrorCode;
import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.CustomerVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.sms.bo.SmsBO;
import com.unieap.tools.JSONUtils;

@Service("checkVerifyCode")
public class CheckVerifyCode extends SoapMessageHandler implements BizHandler {

	public CheckVerifyCode() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String serviceNumber, String extParameters, String parameters)
			throws Exception {
		if (StringUtils.isEmpty(serviceNumber)) {
			throw new Exception("serviceNumber is null");
		}
		if (StringUtils.isEmpty(extParameters)) {
			throw new Exception("verifyCode is null");
		}
		JSONObject json = new JSONObject(extParameters);
		if (!json.has("verifyCode")) {
			throw new Exception("verifyCode is null");
		}
		String verifyCode = json.getString("verifyCode");

		SmsBO smsBO = (SmsBO) ServiceUtils.getBean("smsBO");
		boolean checkVerifyCode = smsBO.checkVerifyCode("V", serviceNumber, verifyCode);
		ProcessResult processResult = new ProcessResult();
		BizHandler handler = (BizHandler) ServiceUtils.getBean("querySubscriberInfo");
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("C005");
		if (checkVerifyCode) {
			processResult = handler.process(newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(),
					requestInfo.getRequestBody().getExtParameters(), "");
			CustomerVO vo = getCustomerInfo(serviceNumber,processResult);
			processResult.setExtParameters(JSONUtils.convertBean2JSON(vo).toString());
			processResult.setResultCode(BssErrorCode.C1);
			processResult.setResultDesc(SYSConfig.getErrorDesc(BssErrorCode.C1));
		} else {
			processResult.setResultCode(BssErrorCode.C0);
			processResult.setResultDesc(SYSConfig.getErrorDesc(BssErrorCode.C0));
			processResult.setServiceNumber(serviceNumber);
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

	public CustomerVO getCustomerInfo(String serviceNumber, ProcessResult processResult) throws Exception {
		CustomerVO vo = new CustomerVO();
		JSONObject json = new JSONObject(processResult.getExtParameters());

		JSONObject custJson = (JSONObject) json.get("customerVO");
		if (custJson.has("activeDate")) {
			vo.setActiveDate(custJson.getString("activeDate"));
		}
		if (custJson.has("address")) {
			vo.setAddress(custJson.getString("address"));
		}
		if (custJson.has("contactNo")) {
			vo.setContactNo(custJson.getString("contactNo"));
		}
		if (custJson.has("corpName")) {
			vo.setCorpName(custJson.getString("corpName"));
		}
		if (custJson.has("corpNo")) {
			vo.setCorpNo(custJson.getString("corpNo"));
		}
		if (custJson.has("email")) {
			vo.setEmail(custJson.getString("email"));
		}
		if (custJson.has("status")) {
			vo.setStatus(custJson.getString("status"));
		}
		if (custJson.has("statusReason")) {
			vo.setStatusReason(custJson.getString("statusReason"));
		}
		if (custJson.has("subscriberType")) {
			vo.setSubscriberType(custJson.getString("subscriberType"));
		}
		vo.setServiceNumber(serviceNumber);
		return vo;
	}
}
