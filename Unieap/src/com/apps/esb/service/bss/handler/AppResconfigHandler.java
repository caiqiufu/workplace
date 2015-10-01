package com.apps.esb.service.bss.handler;

import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.db.DBManager;
import com.unieap.handler.ConfigHandler;

@Service("appResconfigHandler")
public class AppResconfigHandler extends BaseBO implements ConfigHandler{

	@Override
	public void dealNode(Node node, ServletContext servlet, String appName)
			throws Exception {
	}

	@Override
	public void deal(String parameters) throws Exception {
		getAppPictureResConfig();
		getAppContentResConfig();
	}
	public void getAppPictureResConfig(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT type, name,concat(substring(value,1,CHAR_LENGTH(value)-4),'_',seq,'.png') as value FROM unieap.app_resconfig where active_flag = 'Y' and type = 'P' order by name");
		List<Map<String,Object>> datas = (List<Map<String, Object>>) DBManager.getJT(null).queryForList(sql.toString());
		if (datas != null && datas.size() > 0) {
			CacheMgt.getCacheData().put("appPResConfig", datas);
		}
	}
	public void getAppContentResConfig(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT type,name, value FROM unieap.app_resconfig where active_flag = 'Y' and type = 'C' order by name");
		List<Map<String,Object>> datas = (List<Map<String, Object>>) DBManager.getJT(null).queryForList(sql.toString());
		if (datas != null && datas.size() > 0) {
			CacheMgt.getCacheData().put("appCResConfig", datas);
		}
	}
}
