package com.apps.report.task;

import java.util.Date;

import org.springframework.stereotype.Component;

import com.apps.esb.task.TaskSaveEsbLog;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.db.DBManager;
import com.unieap.pojo.TaskExecuteResult;
import com.unieap.task.TaskBO;
import com.unieap.task.TaskService;

@Component
public class TaskReportServiceRequest extends TaskBO implements TaskService {

	@Override
	public String getTaskCode() {
		// TODO Auto-generated method stub
		return "reportServiceRequest";
	}

	@Override
	public int getTaskTimeDuration() {
		return Integer.parseInt(SYSConfig.getConfig().get("report.service.request.frequency"));
	}

	@Override
	public void taskExecute() throws Exception {
		reportServiceRequest();
		
	}
	public void reportServiceRequest(){
		int frequency = Integer.parseInt(SYSConfig.getConfig().get("report.service.request.frequency"));
		Date startTime = UnieapConstants.getDateTime(null);
		StringBuffer sql = new StringBuffer();
		sql.append(" insert into report_service_request ");
		sql.append(" select NEXTVAL('unieap') as id,log.channel_code,log.channel_code as channel_name,dd.dic_code as biz_code,dd.dic_name as biz_name, ");
		sql.append(" log.dest_system,CURRENT_TIMESTAMP() as create_date,COUNT(1)-1 as request_amount ");
		sql.append("  from dic_data_tree dd left join ");
		sql.append("  ( select * from esblog where request_time>=date_sub(CURRENT_TIMESTAMP(), interval "+frequency+" second)) log ");
		sql.append("  on dd.dic_code = log.biz_code where dd.parent_id = 10817 group by dd.dic_code ");
		int processRecords = DBManager.getJT(null).update(sql.toString());
		Date endTime = UnieapConstants.getDateTime(null);
		TaskExecuteResult taskExecuteResult = new TaskExecuteResult();
		taskExecuteResult.setCron("0 0/5 * * * ?");
		taskExecuteResult.setEndTime(endTime);
		taskExecuteResult.setProcessRecord(processRecords);
		taskExecuteResult.setStartTime(startTime);
		taskExecuteResult.setTaskClass(TaskSaveEsbLog.class.getName());
		taskExecuteResult.setTaskCode("reportServiceRequest");
		taskExecuteResult.setTaskName("reportServiceRequest");
		TaskBO taskBO = (TaskBO) ServiceUtils.getBean("taskBO");
		taskBO.save(taskExecuteResult);
	}
}
