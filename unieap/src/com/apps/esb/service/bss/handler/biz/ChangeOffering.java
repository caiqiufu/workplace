package com.apps.esb.service.bss.handler.biz;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.customize.smart.app.handler.crm.ChangePrimaryOffering;
import com.apps.esb.service.bss.customize.smart.app.handler.crm.ChangeSupplementaryOffering;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;

@Service("changeOffering")
public class ChangeOffering extends SoapMessageHandler implements BizHandler{

	public ChangeOffering() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			throw new Exception("offeringInfo is null");
		}
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("offeringInfo")) {
			throw new Exception("offeringInfo is null");
		}
		if(!json.has("offeringInfo")){
			throw new Exception("offeringId is null");
		}
		JSONObject offeringInfo = json.getJSONObject("offeringInfo");
		if(!offeringInfo.has("isChangePrimarryOffering")){
			throw new Exception("isChangePrimarryOffering is null");
		}
		String isChangePrimarryOffering = offeringInfo.getString("isChangePrimarryOffering");
		if(StringUtils.isEmpty(isChangePrimarryOffering)){
			throw new Exception("isChangePrimarryOffering is null");
		}
		if(UnieapConstants.YES.equals(isChangePrimarryOffering)){
			ChangePrimaryOffering changePrimaryOffering = (ChangePrimaryOffering) ServiceUtils.getBean("changePrimaryOffering");
			ProcessResult processResult = changePrimaryOffering.process(requestInfo,parameters,extParameters);
			return processResult;
		}else if(UnieapConstants.NO.equals(isChangePrimarryOffering)){
			ChangeSupplementaryOffering changeSupplementaryOffering = (ChangeSupplementaryOffering) ServiceUtils.getBean("changeSupplementaryOffering");
			ProcessResult processResult = changeSupplementaryOffering.process(requestInfo,parameters,extParameters);
			return processResult;
		}else{
			ProcessResult processResult = new ProcessResult();
			processResult.setResultCode(UnieapConstants.C1);
			processResult.setResultDesc("isChangePrimarryOffering enumeration vlaue is Y or N");
			return processResult;
		}
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
