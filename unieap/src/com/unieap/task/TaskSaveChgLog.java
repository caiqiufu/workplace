package com.unieap.task;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.db.DBManager;
import com.unieap.pojo.Chglog;
import com.unieap.pojo.TaskExecuteResult;

@Component
public class TaskSaveChgLog extends TaskBO implements TaskService {
	@Override
	public int getTaskTimeDuration() {
		// TODO Auto-generated method stub
		return 60;
	}

	// @Scheduled(cron = "15 * * * * ?")
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void saveChgLog() {
		if (!this.checkTaskStatus("saveChgLog")) {
			return;
		}
		/*
		 * if(!this.lockTask("saveChgLog")){ return; }
		 */
		Date startTime = UnieapConstants.getDateTime(null);
		List<Chglog> datas = CacheMgt.getChglogDatas();
		Integer processRecords = Integer.valueOf(datas.size());
		if (datas.size() > 0) {
			List<Chglog> copyDatas = new ArrayList<Chglog>();
			copyDatas.addAll(datas);
			datas.clear();
			DBManager.getHT(null).saveOrUpdateAll(copyDatas);
		}
		Date endTime = UnieapConstants.getDateTime(null);
		TaskExecuteResult taskExecuteResult = new TaskExecuteResult();
		taskExecuteResult.setCron("15 * * * * ?");
		taskExecuteResult.setEndTime(endTime);
		taskExecuteResult.setProcessRecord(processRecords);
		taskExecuteResult.setStartTime(startTime);
		taskExecuteResult.setTaskClass(TaskSaveChgLog.class.getName());
		taskExecuteResult.setTaskCode("saveChgLog");
		taskExecuteResult.setTaskName("saveChgLog");
		TaskBO taskBO = (TaskBO) ServiceUtils.getBean("taskBO");
		taskBO.save(taskExecuteResult);
	}

	@Override
	public String getTaskCode() {
		// TODO Auto-generated method stub
		return "saveChgLog";
	}

	@Override
	public void taskExecute() {
		saveChgLog();
	}
}
