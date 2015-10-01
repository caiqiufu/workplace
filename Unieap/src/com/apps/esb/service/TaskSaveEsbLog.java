package com.apps.esb.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.apps.esb.pojo.Esblog;
import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.element.RequestHeader;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.CacheMgt;
import com.unieap.db.DBManager;

@Component
public class TaskSaveEsbLog {
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
			String destSystem = "", during = "", requestInfoString = "", responsetInfoString = "";
			for (int i = 0; i < copyDatas.size(); i++) {
				data = copyDatas.get(i);
				RequestHeader requestHeader = new RequestHeader();
				ProcessResult processResult = new ProcessResult();
				if (data.get("request") != null) {
					request = (SOAPMessage) data.get("request");
					requestInfoString = BssServiceUtils.getSoapMessageString(request);
				}
				if (data.get("response") != null) {
					response = (SOAPMessage) data.get("response");
					responsetInfoString = BssServiceUtils.getSoapMessageString(response);
				}
				during = (String) data.get("during");

				requestHeader = (RequestHeader) data.get("requestHeader");
				processResult = (ProcessResult) data.get("processResult");
				destSystem = (String) data.get("destSystem");

				Esblog esblog = BssServiceUtils.getEsbLog(requestHeader, processResult, requestInfoString,
						responsetInfoString, during, destSystem);
				esblogDatas.add(esblog);
			}
			DBManager.getHT(null).saveOrUpdateAll(esblogDatas);
		}
	}
}