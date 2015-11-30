package com.apps.esb.service.bss.handler.biz;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.crm.handler.ChangeCustomerData;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.base.ServiceUtils;

@Service("changeCustomerInfo")
public class ChangeCustomerInfo extends SoapMessageHandler implements BizHandler{

	public ChangeCustomerInfo() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		/*GetCustomerMetaData getCustomerMetaData = (GetCustomerMetaData) ServiceUtils.getBean("getCustomerMetaData");
		ProcessResult processResultCustomer = getCustomerMetaData.process(requestInfo, parameters, extParameters);
		CustomerInfoVO customerInfoVO = (CustomerInfoVO)processResultCustomer.getVo();
		
		JSONObject customerObj = new JSONObject("{customerId:"+customerInfoVO.getCustomerId()+"}");
		JSONObject newExtParameters = BssServiceUtils.modifyExtParameters(requestInfo.getRequestBody().getExtParameters(), customerObj);
		requestInfo.getRequestBody().setExtParameters(newExtParameters.toString());*/
		
		ChangeCustomerData changeCustomerData = (ChangeCustomerData) ServiceUtils.getBean("changeCustomerData");
		ProcessResult processResult = changeCustomerData.process(requestInfo,parameters,extParameters);
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
