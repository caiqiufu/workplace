package com.apps.esb.service.bss.handler.query;

import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.apps.mcare.bo.PushMessageBO;
import com.apps.mcare.pojo.AppResconfig;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;

@Service("queryPushMessage")
public class QueryPushMessage extends SoapMessageHandler implements BizHandler {

	public QueryPushMessage() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		String imei = requestInfo.getRequestHeader().getDeviceInfo().getIMEI();
		String serviceNumber = requestInfo.getRequestBody().getServiceNumber();
		PushMessageBO pushMessageBO = (PushMessageBO) ServiceUtils.getBean("pushMessageBO");
		List<AppResconfig> datas = pushMessageBO.getPushMessage(serviceNumber, imei);
		JSONArray noteList = new JSONArray();
		if(datas!=null &&datas.size()>0){
			for (int i = 0; i < datas.size(); i++) {
				AppResconfig appResconfig = datas.get(i);
				JSONObject nodeDetails = new JSONObject();
				nodeDetails.put("subject", appResconfig.getSubject());
				nodeDetails.put("text", appResconfig.getText());
				nodeDetails.put("hyperlink", appResconfig.getHyperlink());
				noteList.put(nodeDetails);
			}
		}
		ProcessResult processResult = new ProcessResult();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("noteList",noteList);
		processResult.setExtParameters(jsonResult.toString());
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
