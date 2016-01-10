package com.apps.esb.service.bss.handler.query;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apps.easymobile.bo.PushMessageBO;
import com.apps.easymobile.pojo.AppMessage;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;

@Service("queryMessage")
public class QueryMessage extends SoapMessageHandler implements BizHandler {

	public QueryMessage() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		String imei = requestInfo.getRequestHeader().getDeviceInfo().getIMEI();
		String serviceNumber = requestInfo.getRequestBody().getServiceNumber();
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
		Map<String,List<AppMessage>> messages = (Map<String,List<AppMessage>>) CacheMgt.getCacheData().get("messages");
		if(all){
			Iterator<?> messageIter = messages.entrySet().iterator();
			List<AppMessage> datas;
			while (messageIter.hasNext()) {
				Map.Entry messageEntry = (Map.Entry) messageIter.next();
				String messageKey = (String) messageEntry.getKey();
				if("P".equals(messageKey)){
					PushMessageBO pushMessageBO = (PushMessageBO) ServiceUtils.getBean("pushMessageBO");
					datas = pushMessageBO.getPushMessage(serviceNumber, imei);
				}else{
					datas = messages.get(messageKey);
				}
				JSONArray noteList = new JSONArray();
				jsonResult.put(messageKey,noteList);
				if(datas!=null &&datas.size()>0){
					for (int i = 0; i < datas.size(); i++) {
						AppMessage appMessage = datas.get(i);
						JSONObject nodeDetails = new JSONObject();
						nodeDetails.put("id", appMessage.getId());
						nodeDetails.put("subject", appMessage.getSubject());
						nodeDetails.put("text", appMessage.getText());
						nodeDetails.put("hyperlink", appMessage.getHyperlink());
						noteList.put(nodeDetails);
					}
				}
			}
		}else{
			List<AppMessage> datas;
			for (int i = 0; i < types.length; i++) {
				if (messages.containsKey(types[i])) {
					if("P".equals(types[i])){
						PushMessageBO pushMessageBO = (PushMessageBO) ServiceUtils.getBean("pushMessageBO");
						datas = pushMessageBO.getPushMessage(serviceNumber, imei);
					}else{
						datas = messages.get(types[i]);
					}
					JSONArray noteList = new JSONArray();
					jsonResult.put(types[i],noteList);
					if(datas!=null &&datas.size()>0){
						for (int j = 0; j < datas.size(); j++) {
							AppMessage appMessage = datas.get(j);
							JSONObject nodeDetails = new JSONObject();
							nodeDetails.put("id", appMessage.getId());
							nodeDetails.put("subject", appMessage.getSubject());
							nodeDetails.put("text", appMessage.getText());
							nodeDetails.put("hyperlink", appMessage.getHyperlink());
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
