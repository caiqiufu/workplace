package com.apps.esb.service.bss.handler.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssErrorCode;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.CacheMgt;
import com.unieap.base.SYSConfig;

@Service("queryResConfig")
public class QueryResConfig extends SoapMessageHandler implements BizHandler{

	public QueryResConfig() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String serviceNumber, String extParameters, String parameters)
			throws Exception {
		String type = "A";
		if(!StringUtils.isEmpty(extParameters)){
			JSONObject extParametersJson = new JSONObject(extParameters);
			if(extParametersJson.has("type")){
				type = extParametersJson.getString("type");
			}
		}
		List<Map<String, Object>> datas = new ArrayList<Map<String,Object>>();
		if(type.equals("P")){
			datas = CacheMgt.getCacheData().get("appPResConfig");
		}else if(type.equals("C")){
			datas = CacheMgt.getCacheData().get("appCResConfig");
		}else{
			datas.addAll(CacheMgt.getCacheData().get("appPResConfig"));
			datas.addAll(CacheMgt.getCacheData().get("appCResConfig"));
		}
		ProcessResult processResult = new ProcessResult();
		processResult.setResultCode(BssErrorCode.C1);
		processResult.setResultDesc(SYSConfig.getErrorDesc(BssErrorCode.C1));
		if(datas!=null&&datas.size()>0){
			JSONObject jsonResult = new JSONObject();
			JSONObject jsonData;
			Map<String,Object> data;
			for(int i = 0 ; i< datas.size(); i++){
				data = datas.get(i);
				jsonData = new JSONObject();
				jsonData.put("type", data.get("type").toString());
				jsonData.put("name", data.get("name").toString());
				jsonData.put("value", data.get("value").toString());
				jsonResult.put(data.get("name").toString(), jsonData);
			}
			processResult.setExtParameters(jsonResult.toString());
		}else{
			processResult.setExtParameters("");
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

}
