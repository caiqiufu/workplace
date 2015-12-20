package com.apps.mcare.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import com.apps.mcare.pojo.AppMessage;
import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.handler.ConfigHandler;

@Service("appMessageHandler")
public class AppMessageHandler extends BaseBO implements ConfigHandler{

	@Override
	public void dealNode(Node node, ServletContext servlet, String appName) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deal(String parameters) throws Exception {
		getPushMessageList();
	}
	
	public void getPushMessageList(){
		/*DetachedCriteria criteria = DetachedCriteria.forClass(AppPushMessage.class);
		Property activeFlag = Property.forName("activeFlag");
		criteria.add(activeFlag.eq(UnieapConstants.YES));
		List<AppPushMessage> datas = DBManager.getHT(null).findByCriteria(criteria);*/
		
		/*StringBuffer sql = new StringBuffer();
		sql.append(" SELECT id,type,name,text,subject,url,hyperlink,group_name as groupName,resolution,active_flag as activeFlag,");
		sql.append(" page_num as pageNum,seq,language,remark,attri1,attri2 ");
		sql.append(" FROM app_resconfig where active_flag = ? ");
		String currentTime = UnieapConstants.getCurrentTime(null, UnieapConstants.TIMEFORMAT);
		sql.append(" and (effective_date >= '"+currentTime+"' and expired_date <= '"+currentTime+"') ");
		sql.append(" or effective_date is null or expired_date is null ");*/
		
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id,type,hyperlink,text,subject FROM unieap.app_message ");
		sql.append(" where active_flag = ? ");
		sql.append(" and ((effective_date <= '").append(UnieapConstants.getCurrentTime(null, UnieapConstants.TIMEFORMAT)).append("'");
		sql.append(" and expired_date >'").append(UnieapConstants.getCurrentTime(null, UnieapConstants.TIMEFORMAT)).append("') or (effective_date is null and expired_date is null))");
		sql.append(" order by id");
		
		List<Object> datas = DBManager.getJT(null).query(sql.toString(), new Object[] { UnieapConstants.YES},
				new EntityRowMapper(AppMessage.class));
		Map<String,List<AppMessage>> messages = new HashMap<String, List<AppMessage>>();
		if(datas!=null&&datas.size()>0){
			List<AppMessage> messageList = null;
			for(int i = 0 ; i < datas.size() ; i++){
				AppMessage appMessage = (AppMessage)datas.get(i);
				if(messages.containsKey(appMessage.getType())){
					messageList = messages.get(appMessage.getType());
				}else{
					messageList = new ArrayList<AppMessage>();
					messages.put(appMessage.getType(),messageList);
				}
				messageList.add(appMessage);
			}
		}
		CacheMgt.getCacheData().put("messages", messages);
	}

	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return true;
	}

}
