package com.apps.esb.service.bss.handler.query;

import java.util.Iterator;
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
import com.apps.mcare.pojo.AppDenomination;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;

@Service("queryDenomination")
public class QueryDenomination extends SoapMessageHandler implements BizHandler {

	public QueryDenomination() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		String[] types = new String[] { "A" };
		boolean all = false;
		JSONObject extParametersJson = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (extParametersJson.has("denominationType")) {
			types = extParametersJson.getString("denominationType").split(",");
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
		Map<String, List<AppDenomination>> denominations = (Map<String, List<AppDenomination>>) CacheMgt.getCacheData()
				.get("denominations");
		if (all) {
			Iterator<?> denominationIter = denominations.entrySet().iterator();
			List<AppDenomination> datas;
			while (denominationIter.hasNext()) {
				Map.Entry denominationEntry = (Map.Entry) denominationIter.next();
				String denominationKey = (String) denominationEntry.getKey();
				datas = denominations.get(denominationKey);
				JSONArray noteList = new JSONArray();
				jsonResult.put(denominationKey, noteList);
				if (datas != null && datas.size() > 0) {
					for (int i = 0; i < datas.size(); i++) {
						AppDenomination appDenomination = datas.get(i);
						JSONObject nodeDetails = new JSONObject();
						nodeDetails.put("index", appDenomination.getValue());
						nodeDetails.put("value", appDenomination.getDenoValue());
						nodeDetails.put("attri", appDenomination.getAttri1());
						noteList.put(nodeDetails);
					}
				}
			}
		} else {
			List<AppDenomination> datas;
			for (int i = 0; i < types.length; i++) {
				if (denominations.containsKey(types[i])) {
					datas = denominations.get(types[i]);
					JSONArray noteList = new JSONArray();
					jsonResult.put(types[i], noteList);
					if (datas != null && datas.size() > 0) {
						for (int j = 0; j < datas.size(); j++) {
							AppDenomination appDenomination = datas.get(i);
							JSONObject nodeDetails = new JSONObject();
							nodeDetails.put("index", appDenomination.getValue());
							nodeDetails.put("value", appDenomination.getDenoValue());
							nodeDetails.put("attri", appDenomination.getAttri1());
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
