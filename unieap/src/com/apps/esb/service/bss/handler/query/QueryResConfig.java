package com.apps.esb.service.bss.handler.query;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;

@Service("queryResConfig")
public class QueryResConfig extends SoapMessageHandler implements BizHandler {

	public QueryResConfig() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		String pageNum = "";
		String[] types = new String[] { "A" };
		String resolution = "2";
		if (requestInfo.getRequestHeader().getDeviceInfo() != null) {
			String devresolution = requestInfo.getRequestHeader().getDeviceInfo().getResolution();
			if("480*800".equals(devresolution)){
				resolution = "2";
			}else if("720*1080".equals(devresolution)){
				resolution = "2";
			}else if("1080*1920".equals(devresolution)){
				resolution = "3";
			}else if("1440*2560".equals(devresolution)){
				resolution = "3";
			}else{
				resolution = "2";
			}
		}
		boolean all = false;
		if (!StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			JSONObject extParametersJson = new JSONObject(requestInfo.getRequestBody().getExtParameters());
			if (extParametersJson.has("type")) {
				types = extParametersJson.getString("type").split(",");
				for (int i = 0; i < types.length; i++) {
					if ("A".equals(types[i])) {
						all = true;
						break;
					}
				}
			}else{
				all = true;
			}
			if (extParametersJson.has("page")) {
				pageNum = extParametersJson.getString("page");
				if (StringUtils.isEmpty(pageNum)) {
					throw new Exception("page is null");
				}
			} else {
				throw new Exception("page is null");
			}
		}

		Map<String, Map<String, List<Map<String, Object>>>> pages = (Map<String, Map<String, List<Map<String, Object>>>>) CacheMgt
				.getCacheData().get("AppResConfig");
		Map<String, List<Map<String, Object>>> page = new HashMap<String, List<Map<String, Object>>>();
		List<Map<String, Object>> datas = new ArrayList<Map<String, Object>>();
		if ("-1".equals(pageNum)) {
			Iterator<?> iter = pages.entrySet().iterator();
			while (iter.hasNext()) {
				Map.Entry entry = (Map.Entry) iter.next();
				String key = (String) entry.getKey();
				page.clear();
				page.putAll(pages.get(key));
				if (all) {
					Iterator<?> pageIter = page.entrySet().iterator();
					while (pageIter.hasNext()) {
						Map.Entry pageEntry = (Map.Entry) pageIter.next();
						String pageKey = (String) pageEntry.getKey();
						datas.addAll(page.get(pageKey));
					}
				} else {
					for (int i = 0; i < types.length; i++) {
						if (page.containsKey(types[i])) {
							datas.addAll(page.get(types[i]));
						}
					}
				}
			}
		} else {
			page = pages.get(pageNum);
			if (all) {
				Iterator<?> pageIter = page.entrySet().iterator();
				while (pageIter.hasNext()) {
					Map.Entry pageEntry = (Map.Entry) pageIter.next();
					String pageKey = (String) pageEntry.getKey();
					datas.addAll(page.get(pageKey));
				}

			} else {
				for (int i = 0; i < types.length; i++) {
					if (page.containsKey(types[i])) {
						datas.addAll(page.get(types[i]));
					}
				}
			}
		}

		ProcessResult processResult = new ProcessResult();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		JSONObject jsonResult = new JSONObject();
		if (datas != null && datas.size() > 0) {
			JSONObject resources = new JSONObject();
			JSONArray jsonGroup = null;
			JSONObject jsonData = null;
			String groupName = null;
			Map<String, Object> data = null;
			for (int i = 0; i < datas.size(); i++) {
				data = datas.get(i);
				if (data.get("group_name") != null) {
					groupName = data.get("group_name").toString();
					if (resources.has(groupName)) {
						jsonGroup = resources.getJSONArray(groupName);
					} else {
						jsonGroup = new JSONArray();
						resources.put(groupName, jsonGroup);
					}
					if (data.get("resolution")!=null&&!StringUtils.isEmpty(data.get("resolution").toString())) {
						if (data.get("resolution").toString().equals(resolution)) {
							jsonData = setValues(data);
							jsonGroup.put(jsonData);
						}
					} else {
						jsonData = setValues(data);
						jsonGroup.put(jsonData);
					}
				} else {
					if (data.get("resolution")!=null&&!StringUtils.isEmpty(data.get("resolution").toString())) {
						if (data.get("resolution").toString().equals(resolution)) {
							jsonData = setValues(data);
							resources.put(data.get("name").toString(), jsonData);
						}
					} else {
						jsonData = setValues(data);
						resources.put(data.get("name").toString(), jsonData);
					}
				}
			}
			jsonResult.put("resources", resources);
			processResult.setExtParameters(jsonResult.toString());
		}
		return processResult;
	}

	/**
	 * set values
	 * @param data
	 * @param jsonGroup
	 * @throws Exception
	 */
	public JSONObject setValues(Map<String, Object> data)
			throws Exception {
		JSONObject jsonData = new JSONObject();
		jsonData.put("type", data.get("type").toString());
		jsonData.put("name", data.get("name").toString());
		if (data.get("url") != null) {
			jsonData.put("url", data.get("url").toString());
		} else {
			jsonData.put("url", "");
		}
		if (data.get("text") != null) {
			jsonData.put("text", data.get("text").toString());
		} else {
			jsonData.put("text", "");
		}
		if (data.get("hyperlink") != null) {
			jsonData.put("hyperlink", data.get("hyperlink").toString());
		} else {
			jsonData.put("hyperlink", "");
		}
		if (data.get("subject") != null) {
			jsonData.put("subject", data.get("subject").toString());
		} else {
			jsonData.put("subject", "");
		}
		if (data.get("resolution") != null) {
			jsonData.put("resolution", data.get("resolution").toString());
		} else {
			jsonData.put("resolution", "");
		}
		if (data.get("attri1") != null) {
			jsonData.put("id", data.get("attri1").toString());
		} else {
			jsonData.put("id", "");
		}
		return jsonData;
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
