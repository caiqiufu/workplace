package com.apps.mcare.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import com.apps.mcare.pojo.AppDenomination;
import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.handler.ConfigHandler;

@Service("appDenominationHandler")
public class AppDenominationHandler extends BaseBO implements ConfigHandler{

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
		sql.append("SELECT id,seq,type,value,deno_value as denoValue,handle_fee as handleFee FROM unieap.app_denomination ");
		sql.append(" where active_flag = ? ");
		sql.append(" and ((effective_date <= '").append(UnieapConstants.getCurrentTime(null, UnieapConstants.TIMEFORMAT)).append("'");
		sql.append(" and expired_date >'").append(UnieapConstants.getCurrentTime(null, UnieapConstants.TIMEFORMAT)).append("') or (effective_date is null and expired_date is null))");
		sql.append(" order by seq ");
		
		List<Object> datas = DBManager.getJT(null).query(sql.toString(), new Object[] { UnieapConstants.YES },
				new EntityRowMapper(AppDenomination.class));
		Map<String,List<AppDenomination>> denominations = new HashMap<String, List<AppDenomination>>();
		if(datas!=null&&datas.size()>0){
			List<AppDenomination> denominationList = null;
			for(int i = 0 ; i < datas.size() ; i++){
				AppDenomination appDenomination = (AppDenomination)datas.get(i);
				if(denominations.containsKey(appDenomination.getType())){
					denominationList = denominations.get(appDenomination.getType());
				}else{
					denominationList = new ArrayList<AppDenomination>();
					denominations.put(appDenomination.getType(),denominationList);
				}
				denominationList.add(appDenomination);
			}
		}
		CacheMgt.getCacheData().put("denominations", denominations);
	}

	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return true;
	}

}
