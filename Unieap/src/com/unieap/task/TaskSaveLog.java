package com.unieap.task;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apps.esb.pojo.Esblog;
import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.element.RequestBody;
import com.apps.esb.service.bss.element.RequestHeader;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponseBody;
import com.apps.esb.service.bss.element.ResponsetHeader;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.unieap.CacheMgt;
import com.unieap.db.DBManager;
import com.unieap.pojo.Chglog;

@Component
public class TaskSaveLog {
	@Scheduled(cron = "15 * * * * ?")
	public void saveChgLog() {
		List<Chglog> datas = CacheMgt.getChglogDatas();
		if (datas.size() > 0) {
			List<Chglog> copyDatas = new ArrayList<Chglog>();
			copyDatas.addAll(datas);
			datas.clear();
			DBManager.getHT(null).saveOrUpdateAll(copyDatas);
		}
	}

	@Scheduled(cron = "15 * * * * ?")
	public void saveEsbLog() {
		List<Esblog> datas = CacheMgt.getEsblogDatas();
		if (datas.size() > 0) {
			List<Esblog> copyDatas = new ArrayList<Esblog>();
			copyDatas.addAll(datas);
			datas.clear();
			DBManager.getHT(null).saveOrUpdateAll(copyDatas);
		}
	}

	@Scheduled(cron = "15 * * * * ?")
	public void saveEsbSOAPLog() throws Exception {
		List<Map<String, Object>> datas = CacheMgt.getEsblogSOAPDatas();
		if (datas.size() > 0) {
			List<Map<String, Object>> copyDatas = new ArrayList<Map<String, Object>>();
			copyDatas.addAll(datas);
			datas.clear();
			Map<String, Object> data;
			List<Esblog> esblogDatas = new ArrayList<Esblog>();
			SOAPMessage request = null, response = null;
			String sourceSystem = "", destSystem = "", during = "", bizCode = "", requestTime = "", responseTime = "",
					requestInfoString = "", responsetInfoString = "";
			RequestInfo requestInfo = new RequestInfo();
			ResponsetInfo responsetInfo = new ResponsetInfo();
			for (int i = 0; i < copyDatas.size(); i++) {
				data = copyDatas.get(i);

				BizHandler bizHandler = (BizHandler) data.get("bizObj");
				if (data.get("request") != null) {
					request = (SOAPMessage) data.get("request");
					requestInfo = bizHandler.getRequestInfoFromSOAPMessage(request);
					requestInfo.getRequestHeader().setBizCode(bizCode);
					requestInfo.getRequestHeader().setRequestTime(requestTime);
					requestInfoString = BssServiceUtils.getSoapMessageString(request);
				} else {
					RequestHeader requestHeader = new RequestHeader();
					RequestBody requestBody = new RequestBody();
					requestInfo.setRequestHeader(requestHeader);
					requestInfo.setRequestBody(requestBody);
				}
				if (data.get("response") != null) {
					response = (SOAPMessage) data.get("response");
					responsetInfo = bizHandler.getResponseInfoFromSOAPMessage(response);
					responsetInfo.getResponsetHeader().setBizCode(bizCode);
					responsetInfo.getResponsetHeader().setRequestTime(requestTime);
					responsetInfo.getResponsetHeader().setResponseTime(responseTime);
					responsetInfoString = BssServiceUtils.getSoapMessageString(response);
				} else {
					ResponsetHeader responsetHeader = new ResponsetHeader();
					ResponseBody responseBody = new ResponseBody();
					responsetInfo.setResponsetHeader(responsetHeader);
					responsetInfo.setResponseBody(responseBody);
				}
				during = (String) data.get("during");
				if (data.get("requestInfo") != null) {
					requestInfo = (RequestInfo) data.get("requestInfo");
				}
				Esblog esblog = BssServiceUtils.getEsbLog(requestInfo, requestInfoString, responsetInfo,
						responsetInfoString, during, destSystem);
				esblogDatas.add(esblog);
			}
			DBManager.getHT(null).saveOrUpdateAll(esblogDatas);
		}
	}
}
