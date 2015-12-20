package com.apps.esb.service.bss.handler.biz;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.crm.handler.CheckPassword;
import com.apps.esb.service.bss.app.crm.handler.GetCustomerData;
import com.apps.esb.service.bss.app.crm.handler.GetSubscriptionData;
import com.apps.esb.service.bss.app.crm.vo.querycustomerinfo.CustomerInfoVO;
import com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo.QuerySubscriberInfoVO;
import com.apps.esb.service.bss.app.vo.CustomerProfileVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("servicePasswordLogin")
public class ServicePasswordLogin extends SoapMessageHandler implements BizHandler {

	public ServicePasswordLogin() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo,String parameters,Map<String,Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("password")) {
			throw new Exception("password is null");
		}
		if (StringUtils.isEmpty(json.getString("password"))) {
			throw new Exception("password is null");
		}
		CheckPassword checkPassword = (CheckPassword) ServiceUtils.getBean("checkPassword");
		ProcessResult processResultCheckPassword = checkPassword.process(requestInfo,parameters,extParameters);
		ProcessResult processResult = new ProcessResult();
		processResultCheckPassword.setResultCode(UnieapConstants.C0);
		if(UnieapConstants.C0.equals(processResultCheckPassword.getResultCode())){
			GetSubscriptionData getSubscriptionData = (GetSubscriptionData) ServiceUtils.getBean("getSubscriptionData");
			ProcessResult processResultSubscriberData = getSubscriptionData.process(requestInfo, parameters, extParameters);
			QuerySubscriberInfoVO querySubscriberInfoVO = (QuerySubscriberInfoVO) processResultSubscriberData.getVo();
			
			JSONObject customerObj = new JSONObject("{customerId:"+querySubscriberInfoVO.getCustomerId()+"}");
			JSONObject newExtParameters = BssServiceUtils.modifyExtParameters(requestInfo.getRequestBody().getExtParameters(), customerObj);
			requestInfo.getRequestBody().setExtParameters(newExtParameters.toString());
			
			GetCustomerData getCustomerData = (GetCustomerData) ServiceUtils.getBean("getCustomerData");
			ProcessResult processResultGetCustomerData = getCustomerData.process(requestInfo, parameters, extParameters);
			CustomerInfoVO customerInfoVO = (CustomerInfoVO) processResultGetCustomerData.getVo();
			
			VerifyCodeLogin verifyCodeLogin = (VerifyCodeLogin) ServiceUtils.getBean("verifyCodeLogin");
			CustomerProfileVO customerProfileVO = verifyCodeLogin.getCustomerInfo(querySubscriberInfoVO,customerInfoVO);
			customerProfileVO.setContactNo(requestInfo.getRequestBody().getServiceNumber());
			customerProfileVO.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
			JSONObject customerJson = JSONUtils.convertBean2JSON(customerProfileVO);
			JSONObject jsonResult = new JSONObject();
			jsonResult.put("subscriberInfo", customerJson);
			processResult.setExtParameters(jsonResult.toString());
			processResult.setResultCode(UnieapConstants.C0);
			processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
			return processResult;
		}else{
			processResult.setResultCode(processResultCheckPassword.getResultCode());
			processResult.setResultDesc(processResultCheckPassword.getResultDesc());
			processResult.setServiceNumber(requestInfo.getRequestBody().getServiceNumber());
			return processResult;
		}
		/*ProcessResult processResult = new ProcessResult();
		CustomerProfileVO customerProfileVO = new CustomerProfileVO();
		customerProfileVO.setActiveDate("Active");
		customerProfileVO.setContactNo("962138386");
		customerProfileVO.setCustomerName("Chai");
		customerProfileVO.setServiceNumber("962138386");
		customerProfileVO.setEmail("Chai@.sohu.com");
		JSONObject customerJson = JSONUtils.convertBean2JSON(customerProfileVO);
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("subscriberInfo", customerJson);
		processResult.setExtParameters(jsonResult.toString());
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		return processResult;*/
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
		return null;
	}

}
