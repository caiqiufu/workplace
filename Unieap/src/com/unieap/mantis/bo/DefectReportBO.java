package com.unieap.mantis.bo;

import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.apache.commons.lang.StringUtils;

import com.unieap.BaseBO;
import com.unieap.db.DBManager;
import com.unieap.pojo.MDefect;
@Service("defectReportBO")
public class DefectReportBO extends BaseBO{
	
	public String getDefectReport(String operType,String project) throws Exception{
		if(StringUtils.equals(operType,"Status")){
			return getDefectStatusReport(project);
		}else if(StringUtils.equals(operType,"StatusCurrently")){
			return getDefectStatusCurrentlyReport(project);
		}else{
			throw new Exception("Operation type["+operType+"] is wrong.");
		}
	}
	public String getDefectStatusReport(String project) throws Exception{
		String sql = "SELECT datetime,status,sum(amount) as amount FROM unieap.r_status where status=? group by datetime";
		String sqlWithProject = "SELECT datetime,status,sum(amount) as amount FROM unieap.r_status where project=? and status=? group by datetime";
		List datas0 = null;
		List datas1 = null;
		List datas2 = null;
		List datas3 = null;
		List datas4 = null;
		List datas5 = null;
		List datas6 = null;
		List datas7 = null;
		if(StringUtils.isEmpty(project)){
			datas0 = DBManager.getJT(null).queryForList(sql,new Object[]{"0"});
			datas1 = DBManager.getJT(null).queryForList(sql,new Object[]{"1"});
			datas2 = DBManager.getJT(null).queryForList(sql,new Object[]{"2"});
			datas3 = DBManager.getJT(null).queryForList(sql,new Object[]{"3"});
			datas4 = DBManager.getJT(null).queryForList(sql,new Object[]{"4"});
			datas5 = DBManager.getJT(null).queryForList(sql,new Object[]{"5"});
			datas6 = DBManager.getJT(null).queryForList(sql,new Object[]{"6"});
			datas7 = DBManager.getJT(null).queryForList(sql,new Object[]{"7"});
		}else{
			datas0 = DBManager.getJT(null).queryForList(sqlWithProject,new Object[]{Integer.valueOf(project),"0"});
			datas1 = DBManager.getJT(null).queryForList(sqlWithProject,new Object[]{Integer.valueOf(project),"1"});
			datas2 = DBManager.getJT(null).queryForList(sqlWithProject,new Object[]{Integer.valueOf(project),"2"});
			datas3 = DBManager.getJT(null).queryForList(sqlWithProject,new Object[]{Integer.valueOf(project),"3"});
			datas4 = DBManager.getJT(null).queryForList(sqlWithProject,new Object[]{Integer.valueOf(project),"4"});
			datas5 = DBManager.getJT(null).queryForList(sqlWithProject,new Object[]{Integer.valueOf(project),"5"});
			datas6 = DBManager.getJT(null).queryForList(sqlWithProject,new Object[]{Integer.valueOf(project),"6"});
			datas7 = DBManager.getJT(null).queryForList(sqlWithProject,new Object[]{Integer.valueOf(project),"7"});
		}
		JSONArray ja = new JSONArray();
		if(datas0!=null&&datas0.size()>0){
			Map data;
			for(int i = 0 ; i < datas0.size(); i++){
				JSONObject jac = new JSONObject();
				
				data = (Map) datas0.get(i);
				jac.put("datetime",data.get("datetime").toString());
				jac.put("new",Integer.parseInt(data.get("amount").toString()));
				
				data = (Map) datas1.get(i);
				jac.put("datetime",data.get("datetime").toString());
				jac.put("assign",Integer.parseInt(data.get("amount").toString()));
				
				data = (Map) datas2.get(i);
				jac.put("datetime",data.get("datetime").toString());
				jac.put("confirmed",Integer.parseInt(data.get("amount").toString()));
				
				data = (Map) datas3.get(i);
				jac.put("datetime",data.get("datetime").toString());
				jac.put("pendingFix",Integer.parseInt(data.get("amount").toString()));
				
				data = (Map) datas4.get(i);
				jac.put("datetime",data.get("datetime").toString());
				jac.put("reject",Integer.parseInt(data.get("amount").toString()));
				
				data = (Map) datas5.get(i);
				jac.put("datetime",data.get("datetime").toString());
				jac.put("pendingRetest",Integer.parseInt(data.get("amount").toString()));
				
				data = (Map) datas6.get(i);
				jac.put("datetime",data.get("datetime").toString());
				jac.put("resolve",Integer.parseInt(data.get("amount").toString()));
				
				data = (Map) datas7.get(i);
				jac.put("datetime",data.get("datetime").toString());
				jac.put("close",Integer.parseInt(data.get("amount").toString()));
				ja.put(jac);
			}
		}
		return ja.toString();
	}
	public String getDefectStatusCurrentlyReport(String project) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("select dd.dic_name as status , d.status as defect_status,count(d.status) as amount from unieap.dic_data dd ");
		sql.append("left join unieap.m_defect d on dd.dic_code = d.status ");
		sql.append("where dd.group_id =1003 group by dd.dic_code order by dd.dic_code ");
		StringBuffer sqlWithProject = new StringBuffer();
		sqlWithProject.append("select dd.dic_name as status , d.status as defect_status,count(d.status) as amount from unieap.dic_data dd ");
		sqlWithProject.append("left join (select * from unieap.m_defect where project=?) d on dd.dic_code = d.status ");
		sqlWithProject.append("where dd.group_id =1003 group by dd.dic_code order by dd.dic_code");
		List datas;
		if(StringUtils.isEmpty(project)){
			datas = DBManager.getJT(null).queryForList(sql.toString());
		}else{
			datas = DBManager.getJT(null).queryForList(sqlWithProject.toString(),new Object[]{Integer.valueOf(project)});
		}
		JSONArray ja = new JSONArray();
		if(datas!=null&&datas.size()>0){
			Map data;
			for(int i = 0 ; i < datas.size(); i++){
				JSONObject jac = new JSONObject();
				data = (Map) datas.get(i);
				jac.put("status",data.get("status").toString());
				jac.put("amount",Integer.parseInt(data.get("amount").toString()));
				ja.put(jac);
			}
		}
		return ja.toString();
	}
}
