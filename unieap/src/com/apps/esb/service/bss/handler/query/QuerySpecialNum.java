package com.apps.esb.service.bss.handler.query;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apps.easymobile.pojo.AppSpecialNum;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;

@Service("querySpecialNum")
public class QuerySpecialNum extends SoapMessageHandler implements BizHandler {

	public QuerySpecialNum() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		if(StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())){
			throw new Exception("type is null");
		}
		String[] types = new String[] { "A" };
		boolean all = false;
		JSONObject extParametersJson = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (extParametersJson.has("type")) {
			types = extParametersJson.getString("type").split(",");
			for (int i = 0; i < types.length; i++) {
				if ("A".equals(types[i])) {
					all = true;
					break;
				}
			}
		} else {
			all = true;
		}
		JSONObject jsonResult = new JSONObject();
		Map<String,List<AppSpecialNum>> specialNumbers = (Map<String,List<AppSpecialNum>>) CacheMgt.getCacheData().get("specialNumbers");
		if(all){
			Iterator<?> specialNumberIter = specialNumbers.entrySet().iterator();
			List<AppSpecialNum> datas;
			while (specialNumberIter.hasNext()) {
				Map.Entry specialNumberEntry = (Map.Entry) specialNumberIter.next();
				String specialNumberKey = (String) specialNumberEntry.getKey();
				datas = specialNumbers.get(specialNumberKey);
				JSONArray noteList = new JSONArray();
				jsonResult.put(specialNumberKey,noteList);
				if(datas!=null &&datas.size()>0){
					for (int i = 0; i < datas.size(); i++) {
						AppSpecialNum appSpecialNum = datas.get(i);
						JSONObject nodeDetails = new JSONObject();
						nodeDetails.put("id", appSpecialNum.getId());
						nodeDetails.put("subject", appSpecialNum.getSubject());
						nodeDetails.put("text", appSpecialNum.getText());
						nodeDetails.put("remark", appSpecialNum.getRemark());
						noteList.put(nodeDetails);
					}
				}
			}
		}else{
			List<AppSpecialNum> datas;
			for (int i = 0; i < types.length; i++) {
				if (specialNumbers.containsKey(types[i])) {
					datas = specialNumbers.get(types[i]);
					JSONArray noteList = new JSONArray();
					jsonResult.put(types[i],noteList);
					if(datas!=null &&datas.size()>0){
						for (int j = 0; j < datas.size(); j++) {
							AppSpecialNum appSpecialNum = datas.get(j);
							JSONObject nodeDetails = new JSONObject();
							nodeDetails.put("id", appSpecialNum.getId());
							nodeDetails.put("subject", appSpecialNum.getSubject());
							nodeDetails.put("text", appSpecialNum.getText());
							nodeDetails.put("remark", appSpecialNum.getRemark());
							noteList.put(nodeDetails);
						}
					}
				}
			}
		}
		
		ProcessResult processResult = new ProcessResult();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
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
