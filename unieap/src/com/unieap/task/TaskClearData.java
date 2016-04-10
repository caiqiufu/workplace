package com.unieap.task;

import java.util.List;
import java.util.Map;

import com.unieap.db.DBManager;

public class TaskClearData extends TaskBO implements TaskService {

	@Override
	public String getTaskCode() {
		// TODO Auto-generated method stub
		return "clearData";
	}

	@Override
	public int getTaskTimeDuration() {
		// TODO Auto-generated method stub
		return 30*24*60*60;
	}

	@Override
	public void taskExecute() throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	public void clearData(){
		String sql = "SELECT * FROM unieap.data_clear where active_flag = 'Y'";
		List datas = DBManager.getJT(null).queryForList(sql);
		if(datas.size()>0){
			String clearSql = "";
			for(int i = 0 ; i< datas.size() ; i++){
				Map data = (Map) datas.get(i);
				String clearTableName = (String)data.get("clear_table_name");
				String clearCondition = "";
				if(data.get("clear_condition")!=null){
					clearCondition = (String)data.get("clear_condition");
					clearSql = "delete from "+clearTableName +" where "+clearCondition;
				}else{
					clearSql = "delete from "+clearTableName +" where 1=1";
				}
				DBManager.getJT(null).execute(clearSql);
			}
		}
	}
}
