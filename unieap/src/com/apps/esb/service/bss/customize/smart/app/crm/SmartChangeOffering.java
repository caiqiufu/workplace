package com.apps.esb.service.bss.customize.smart.app.crm;

import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.customize.smart.app.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;

@Service("changeOffering_1")
public class SmartChangeOffering extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("offeringInfo")) {
			throw new Exception("offeringInfo is null");
		}
		if (!json.has("offeringInfo")) {
			throw new Exception("offeringId is null");
		}
		JSONObject offeringInfo = json.getJSONObject("offeringInfo");
		if (!offeringInfo.has("isChangePrimarryOffering")) {
			throw new Exception("isChangePrimarryOffering is null");
		}
		String isChangePrimarryOffering = offeringInfo.getString("isChangePrimarryOffering");
		if (StringUtils.isEmpty(isChangePrimarryOffering)) {
			throw new Exception("isChangePrimarryOffering is null");
		}
		if (UnieapConstants.YES.equals(isChangePrimarryOffering)) {
			SmartChangePrimaryOffering changePrimaryOffering = (SmartChangePrimaryOffering) ServiceUtils
					.getBean("changePrimaryOffering");
			ProcessResult processResult = changePrimaryOffering.process(requestInfo, handler, extParameters);
			return processResult;
		} else if (UnieapConstants.NO.equals(isChangePrimarryOffering)) {
			SmartChangeSupplementaryOffering changeSupplementaryOffering = (SmartChangeSupplementaryOffering) ServiceUtils
					.getBean("changeSupplementaryOffering");
			ProcessResult processResult = changeSupplementaryOffering.process(requestInfo, handler, extParameters);
			return processResult;
		} else {
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
