package com.apps.esb.service.bss.customize.smart.app.handler.cbs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.customize.smart.app.handler.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;

@Service("queryViewAllBalance_1")
public class SmartQueryViewAllBalance extends CustSoapMessageHandler implements BizHandler{

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {

		BizHandler queryAccountBalance = (BizHandler) ServiceUtils.getBean("queryBalance_1");
		ProcessResult processAccountBalance = queryAccountBalance.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processAccountBalance.getResultCode())) {
			return processAccountBalance;
		}
		BizHandler queryFreeReource = (BizHandler) ServiceUtils.getBean("queryFreeReource_1");
		ProcessResult processFreeReource = queryFreeReource.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processFreeReource.getResultCode())) {
			return processFreeReource;
		}
		/*BizHandler queryAccumulationUsage = (BizHandler) ServiceUtils.getBean("queryAccumulationUsage_1");
		ProcessResult processAccumulationUsage = queryAccumulationUsage.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processAccumulationUsage.getResultCode())) {
			return processAccumulationUsage;
		}*/
		BizHandler queryUsage = (BizHandler) ServiceUtils.getBean("queryUsage_1");
		ProcessResult processQueryUsage = queryUsage.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processQueryUsage.getResultCode())) {
			return processQueryUsage;
		}
		
		List<Object> vos = new ArrayList<Object>();
		vos.add(processAccountBalance.getVo());
		vos.add(processFreeReource.getVo());
		vos.add(processQueryUsage.getVo());
		ProcessResult processResult = new ProcessResult();
		processResult.setVo(vos);
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
