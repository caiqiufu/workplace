package com.apps.esb.service.bss.customize.smart.app.handler.crm;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.customer.basicinfo.CustomerVO;
import com.apps.esb.service.bss.app.vo.subscriber.basicinfo.SubscriberVO;
import com.apps.esb.service.bss.customize.smart.app.handler.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;


@Service("servicePasswordLogin_1")
public class SmartServicePasswordLogin extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
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
		SmartCheckPassword checkPassword = (SmartCheckPassword) ServiceUtils.getBean("smartCheckPassword");
		ProcessResult processResultCheckPassword = checkPassword.process(requestInfo,handler,extParameters);
		ProcessResult processResult = new ProcessResult();
		processResultCheckPassword.setResultCode(UnieapConstants.C0);
		if(!UnieapConstants.C0.equals(processResultCheckPassword.getResultCode())){
			return processResultCheckPassword;
		}
		
		
		SmartGetSubscriptionData getSubscriptionData = (SmartGetSubscriptionData) ServiceUtils.getBean("smartQuerySubscriber");
		ProcessResult processResultSubscriberData = getSubscriptionData.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResultSubscriberData.getResultCode())) {
			return processResultSubscriberData;
		}
		SubscriberVO subscriberVO = (SubscriberVO) processResultSubscriberData.getVo();
		
		JSONObject customerObj = new JSONObject("{customerId:"+subscriberVO.getCustomerId()+"}");
		JSONObject newExtParameters = BssServiceUtils.modifyExtParameters(requestInfo.getRequestBody().getExtParameters(), customerObj);
		requestInfo.getRequestBody().setExtParameters(newExtParameters.toString());
		
		SmartGetCustomerData getCustomerData = (SmartGetCustomerData) ServiceUtils.getBeanByTenantId("queryCustomer");
		ProcessResult processResultGetCustomerData = getCustomerData.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResultGetCustomerData.getResultCode())) {
			return processResultGetCustomerData;
		}
		CustomerVO customerVO = (CustomerVO) processResultGetCustomerData.getVo();
		subscriberVO.setCustomerVO(customerVO);
		
		processResult.setVo(subscriberVO);
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
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
