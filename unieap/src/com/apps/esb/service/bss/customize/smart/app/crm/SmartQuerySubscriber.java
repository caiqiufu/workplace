package com.apps.esb.service.bss.customize.smart.app.crm;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.customer.basicinfo.CustomerVO;
import com.apps.esb.service.bss.app.vo.subscriber.basicinfo.SubscriberVO;
import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;

@Service("querySubscriber_1")
public class SmartQuerySubscriber extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		BizHandler getSubscriptionData = (BizHandler) ServiceUtils.getBean("smartQuerySubscriber");
		ProcessResult processResultSubscriberData = getSubscriptionData.process(requestInfo, handler,
				extParameters);
		if (!UnieapConstants.C0.equals(processResultSubscriberData.getResultCode())) {
			return processResultSubscriberData;
		}
		SubscriberVO subscriberVO = (SubscriberVO) processResultSubscriberData.getVo();
		JSONObject customerObj = new JSONObject("{customerId:" + subscriberVO.getCustomerId() + "}");
		JSONObject newExtParameters = BssServiceUtils
				.modifyExtParameters(requestInfo.getRequestBody().getExtParameters(), customerObj);
		requestInfo.getRequestBody().setExtParameters(newExtParameters.toString());

		BizHandler getCustomerData = (BizHandler) ServiceUtils.getBeanByTenantId("queryCustomer");
		ProcessResult processResultGetCustomerData = getCustomerData.process(requestInfo, handler, extParameters);
		CustomerVO customerVO = (CustomerVO) processResultGetCustomerData.getVo();
		if (!UnieapConstants.C0.equals(processResultGetCustomerData.getResultCode())) {
			return processResultGetCustomerData;
		}
		subscriberVO.setCustomerVO(customerVO);
		ProcessResult processResult = new ProcessResult();
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
