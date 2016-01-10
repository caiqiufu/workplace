package com.apps.esb.service.bss.handler.biz;

import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.easymobile.bo.ComplainBO;
import com.apps.easymobile.pojo.AppComplain;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;

@Service("submitComplain")
public class SubmitComplain extends SoapMessageHandler implements BizHandler {

	public SubmitComplain() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo,String parameters,Map<String,Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			throw new Exception("extparameters is null");
		}
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("evalution")) {
			throw new Exception("evalution is null");
		}
		String evalution = json.getString("evalution");
		String text = "";
		if (json.has("text")) {
			text = json.getString("text");
		}
		AppComplain appComplain = new AppComplain();
		appComplain.setEvalution(evalution);
		appComplain.setText(text);
		appComplain.setSubmitBy(requestInfo.getRequestBody().getServiceNumber());
		appComplain.setSubmitDate(UnieapConstants.getDateTime(null));
		appComplain.setStatus("S");
		ComplainBO submitComplainBO = (ComplainBO) ServiceUtils.getBean("complainBO");
		List<FileItem> files = null;
		if(extParameters!=null){
			 files = (List<FileItem>) extParameters.get("files");
		}
		submitComplainBO.save(appComplain,files);
		ProcessResult processResult = new ProcessResult();
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
