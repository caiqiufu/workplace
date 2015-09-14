package com.unieap.task;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;

import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;
import com.unieap.pojo.RExelog;

public class TaskDefectStatus implements TaskService{

	@Override
	@Scheduled(cron="15 * * * * ?")
	public void execute() {
		StringBuffer sb = null;
		int amount = DBManager.getJT(null).queryForInt("select count(1) from unieap.r_exelog where type_code ='defect_status' and biz_date = curdate()");
		if(amount==0){
			List datas = DBManager.getJT(null).queryForList("SELECT dic_code FROM unieap.dic_data where group_id =1014 and dic_code <>'-1'");
			String project = "";
			if(datas!=null&&datas.size()>0){
				for(int i = 0 ; i < datas.size() ; i++){
					project = ((Map)datas.get(i)).get("dic_code").toString();
					if(!StringUtils.equals(project, "-1")){
						sb = new StringBuffer();
						sb.append("insert unieap.r_status(id,datetime,status,amount,project) ");
						sb.append(" select unieap.NEXTVAL('unieap') as id,curdate() as datetime,dd.dic_code as status,count(d.status) as amount,'"+project+"' as project from unieap.dic_data dd ");
						sb.append(" left join (select status,project from unieap.m_defect where project='"+project+"') d on dd.dic_code = d.status ");
						sb.append(" where dd.group_id =1003 group by dd.dic_code order by dd.dic_code");
						DBManager.getJT(null).execute(sb.toString());
					}
				}
			}
			RExelog rExelog = new RExelog();
			rExelog.setId(UnieapConstants.getSequence(null,"unieap"));
			rExelog.setExecuteDate(UnieapConstants.getDateTime(null));
			rExelog.setBizDate(UnieapConstants.getCurrentTime(null,UnieapConstants.DATEFORMAT));
			rExelog.setResult(UnieapConstants.SUCCESS);
			rExelog.setTypeCode("defect_status");
			rExelog.setTypeName("defect status");
			DBManager.getHT(null).save(rExelog);
		}
	}

}
