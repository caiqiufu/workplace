package com.apps.esb.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apps.esb.pojo.Esblog;
import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.element.RequestHeader;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.db.DBManager;
import com.unieap.pojo.TaskExecuteResult;
import com.unieap.task.TaskBO;
import com.unieap.task.TaskService;

@Component
public class TaskSaveSoapLog extends TaskBO implements TaskService {
	@Override
	public int getTaskTimeDuration() {
		// TODO Auto-generated method stub
		return 60;
	}

	@Override
	public String getTaskCode() {
		// TODO Auto-generated method stub
		return "saveEsbSOAPLog";
	}

	@Override
	public void taskExecute() throws Exception {
		saveEsbSOAPLog();

	}

	// @Scheduled(cron = "15 * * * * ?")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void saveEsbSOAPLog() throws Exception {
		if (!this.checkTaskStatus("saveEsbSOAPLog")) {
			return;
		}
		Date startTime = UnieapConstants.getDateTime(null);
		List<Map<String, Object>> datas = CacheMgt.getEsblogSOAPDatas();
		Integer processRecords = Integer.valueOf(datas.size());
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
		Date endTime = UnieapConstants.getDateTime(null);
		TaskExecuteResult taskExecuteResult = new TaskExecuteResult();
		taskExecuteResult.setCron("15 * * * * ?");
		taskExecuteResult.setEndTime(endTime);
		taskExecuteResult.setProcessRecord(processRecords);
		taskExecuteResult.setStartTime(startTime);
		taskExecuteResult.setTaskClass(TaskSaveEsbLog.class.getName());
		taskExecuteResult.setTaskCode("saveEsbSOAPLog");
		taskExecuteResult.setTaskName("saveEsbSOAPLog");
		TaskBO taskBO = (TaskBO) ServiceUtils.getBean("taskBO");
		taskBO.save(taskExecuteResult);
	}

}
