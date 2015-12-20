package com.apps.esb.service.bss.handler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.db.DBManager;
import com.unieap.handler.ConfigHandler;

@Service("appResconfigHandler")
public class AppResconfigHandler extends BaseBO implements ConfigHandler {

	@Override
	public void dealNode(Node node, ServletContext servlet, String appName) throws Exception {
	}

	@Override
	public void deal(String parameters) throws Exception {
		getAppPictureResConfigByPage();
	}

	public void getAppPictureResConfigNoGroup() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT type, name,url,hyperlink,text,subject, group_name,resolution,page_num,attri1,attri2  ");
		sql.append("FROM unieap.app_resconfig where active_flag = ? and group_name is null order by group_name,seq");
		List<Map<String, Object>> datas = (List<Map<String, Object>>) DBManager.getJT(null).queryForList(sql.toString(),
				new Object[] { UnieapConstants.YES });
		// include all pages
		Map<String, Map<String, List<Map<String, Object>>>> pages = new HashMap<String, Map<String, List<Map<String, Object>>>>();
		// for each page include P and C
		Map<String, List<Map<String, Object>>> page;
		String pageNum, type;
		if (datas != null && datas.size() > 0) {
			Map<String, Object> data;
			for (int i = 0; i < datas.size(); i++) {
				data = datas.get(i);
				pageNum = (String) data.get("page_num");
				type = (String) data.get("type");
				page = pages.get(pageNum);
				if (page == null) {
					page = new HashMap<String, List<Map<String, Object>>>();
					pages.put(pageNum, page);
				}
				if (page.get(type) != null) {
					page.get(type).add(data);
				} else {
					List<Map<String, Object>> newdata = new ArrayList<Map<String, Object>>();
					newdata.add(data);
					page.put(type, newdata);
				}
			}
		}
		CacheMgt.getCacheData().put("AppResConfigNoGroup", pages);
	}

	public void getAppPictureResConfigWithGroup() {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT type, name,url,hyperlink,text,subject, group_name,resolution,page_num  FROM unieap.app_resconfig where active_flag = 'Y' and group_name <> '' order by name");
		List<Map<String, Object>> datas = (List<Map<String, Object>>) DBManager.getJT(null)
				.queryForList(sql.toString());
		// include all pages
		Map<String, Map<String, List<Map<String, Object>>>> pages = new HashMap<String, Map<String, List<Map<String, Object>>>>();
		// for each page include P and C
		Map<String, List<Map<String, Object>>> page;
		String pageNum, groupName;
		if (datas != null && datas.size() > 0) {
			Map<String, Object> data;
			for (int i = 0; i < datas.size(); i++) {
				data = datas.get(i);
				pageNum = (String) data.get("page_num");
				groupName = (String) data.get("group_name");
				page = pages.get(pageNum);
				if (page == null) {
					page = new HashMap<String, List<Map<String, Object>>>();
					pages.put(pageNum, page);
				}
				if (page.get(groupName) != null) {
					page.get(groupName).add(data);
				} else {
					List<Map<String, Object>> newdata = new ArrayList<Map<String, Object>>();
					newdata.add(data);
					page.put(groupName, newdata);
				}
			}
		}
		CacheMgt.getCacheData().put("AppResConfigWithGroup", pages);
	}

	public void getAppContentResConfig() {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT type, name,url,hyperlink,text,subject, group_name,resolution,page_num  FROM unieap.app_resconfig where active_flag = 'Y' and type = 'C' order by name");
		List<Map<String, Object>> datas = (List<Map<String, Object>>) DBManager.getJT(null)
				.queryForList(sql.toString());
		if (datas != null && datas.size() > 0) {
			CacheMgt.getCacheData().put("appCResConfig", datas);
		}
	}

	public void getAppPictureResConfigByPage() {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT id, type, name,url,hyperlink,text,subject, group_name,resolution,page_num,attri1,attri2 FROM unieap.app_resconfig where active_flag = ? and tenant_id =? ");
		sql.append(" and ((effective_date <= '")
				.append(UnieapConstants.getCurrentTime(null, UnieapConstants.TIMEFORMAT)).append("'");
		sql.append(" and expired_date >'").append(UnieapConstants.getCurrentTime(null, UnieapConstants.TIMEFORMAT))
				.append("') or (effective_date is null and expired_date is null))");
		sql.append(" order by group_name,seq");
		List<Map<String, Object>> datas = (List<Map<String, Object>>) DBManager.getJT(null).queryForList(sql.toString(),
				new Object[] { UnieapConstants.YES, SYSConfig.getConfig().get("tenantId") });
		// include all pages
		Map<String, Map<String, List<Map<String, Object>>>> pages = new HashMap<String, Map<String, List<Map<String, Object>>>>();
		// for each page include P and C
		Map<String, List<Map<String, Object>>> page;
		String pageNum, type;
		if (datas != null && datas.size() > 0) {
			Map<String, Object> data;
			for (int i = 0; i < datas.size(); i++) {
				data = datas.get(i);
				pageNum = (String) data.get("page_num");
				type = (String) data.get("type");
				page = pages.get(pageNum);
				if (page == null) {
					page = new HashMap<String, List<Map<String, Object>>>();
					pages.put(pageNum, page);
				}
				if (page.get(type) != null) {
					page.get(type).add(data);
				} else {
					List<Map<String, Object>> newdata = new ArrayList<Map<String, Object>>();
					newdata.add(data);
					page.put(type, newdata);
				}
			}
		}
		CacheMgt.getCacheData().put("AppResConfig", pages);
	}

	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return true;
	}

}
