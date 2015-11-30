package com.apps.mcare.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import com.apps.mcare.pojo.AppSpecialNum;
import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.handler.ConfigHandler;

@Service("appSpecialNumHandler")
public class AppSpecialNumHandler extends BaseBO implements ConfigHandler{

	@Override
	public void dealNode(Node node, ServletContext servlet, String appName) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deal(String parameters) throws Exception {
		getPushMessageList();
	}
	
	public void getPushMessageList(){
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id,type,subject,text,remark FROM unieap.app_special_num ");
		sql.append(" where active_flag = ?  order by id ");
		
		List<Object> datas = DBManager.getJT(null).query(sql.toString(), new Object[] { UnieapConstants.YES },
				new EntityRowMapper(AppSpecialNum.class));
		Map<String,List<AppSpecialNum>> specialNumbers = new HashMap<String, List<AppSpecialNum>>();
		if(datas!=null&&datas.size()>0){
			List<AppSpecialNum> specialNumberList = null;
			for(int i = 0 ; i < datas.size() ; i++){
				AppSpecialNum appSpecialNum = (AppSpecialNum)datas.get(i);
				if(specialNumbers.containsKey(appSpecialNum.getType())){
					specialNumberList = specialNumbers.get(appSpecialNum.getType());
				}else{
					specialNumberList = new ArrayList<AppSpecialNum>();
					specialNumbers.put(appSpecialNum.getType(),specialNumberList);
				}
				specialNumberList.add(appSpecialNum);
			}
		}
		CacheMgt.getCacheData().put("specialNumbers", specialNumbers);
	}

	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return true;
	}

}
