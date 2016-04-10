package com.apps.esb.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apps.esb.pojo.Esblog;
import com.apps.esb.pojo.EsblogDevice;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.db.DBManager;
import com.unieap.pojo.TaskExecuteResult;
import com.unieap.task.TaskBO;
import com.unieap.task.TaskService;

@Component
public class TaskSaveEsbLog extends TaskBO implements TaskService {

	@Override
	public int getTaskTimeDuration() {
		// TODO Auto-generated method stub
		return 60;
	}

	@Override
	public String getTaskCode() {
		// TODO Auto-generated method stub
		return "saveEsbLog";
	}

	@Override
	public void taskExecute() {
		saveEsbLog();
	}

	// @Scheduled(cron = "15 * * * * ?")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void saveEsbLog() {
		if (!this.checkTaskStatus("saveEsbLog")) {
			return;
		}
		Date startTime = UnieapConstants.getDateTime(null);
		List<Esblog> datas = CacheMgt.getEsblogDatas();
		int processRecords1 = datas.size();
		if (datas.size() > 0) {
			List<Esblog> copyDatas = new ArrayList<Esblog>();
			copyDatas.addAll(datas);
			datas.clear();
			DBManager.getHT(null).saveOrUpdateAll(copyDatas);
		}
		List<EsblogDevice> deviceDatas = CacheMgt.getEsblogDeviceDatas();
		if (deviceDatas.size() > 0) {
			List<EsblogDevice> copyDatas = new ArrayList<EsblogDevice>();
			copyDatas.addAll(deviceDatas);
			deviceDatas.clear();
			DBManager.getHT(null).saveOrUpdateAll(copyDatas);
		}
		int processRecords2 = deviceDatas.size();
		Integer processRecords = Integer.valueOf(processRecords1 + processRecords2);
		Date endTime = UnieapConstants.getDateTime(null);
		TaskExecuteResult taskExecuteResult = new TaskExecuteResult();
		taskExecuteResult.setCron("15 * * * * ?");
		taskExecuteResult.setEndTime(endTime);
		taskExecuteResult.setProcessRecord(processRecords);
		taskExecuteResult.setStartTime(startTime);
		taskExecuteResult.setTaskClass(TaskSaveEsbLog.class.getName());
		taskExecuteResult.setTaskCode("saveEsbLog");
		taskExecuteResult.setTaskName("saveEsbLog");
		TaskBO taskBO = (TaskBO) ServiceUtils.getBean("taskBO");
		taskBO.save(taskExecuteResult);
	}

}
