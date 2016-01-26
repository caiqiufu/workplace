package com.unieap.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import com.unieap.db.DBManager;
import com.unieap.handler.ConfigHandler;

@Service("loadSystemData")
public class LoadSystemData {
	public final Log log = LogFactory.getLog(LoadSystemData.class);

	public void loadSystemConfigure() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT name,value FROM unieap.sys_config where active_flag ='Y'");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString());
		if (datas != null & !datas.isEmpty()) {
			Map<String, String> config = new HashMap<String, String>();
			String name, value = "";
			Map<String, Object> data;
			for (int i = 0; i < datas.size(); i++) {
				data = datas.get(i);
				if (datas.get(i).get("name") != null) {
					name = data.get("name").toString();
					if (datas.get(i).get("value") != null) {
						value = data.get("value").toString();
					}
					config.put(name, value);
				}
			}
			config.put("bss.encryption.key", "1234567890111110");
			config.put("mcare.encryption.key", "1234567890543210");
			SYSConfig.setConfig(config);
			log.info("loadSystemConfigure end...");
		}
	}

	public void loadBizHandlerData() {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT handler_name as handlerName,app_handler_name as appHandlerName, cust_handler_name as custHandlerName,class_name as className ,parameters FROM unieap.handler_config ");
		sql.append(" where active_flag = 'Y' and tenant_id =?  and handler_type ='B'");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString(),
				new Object[] {SYSConfig.getConfig().get("tenantId") });
		if (datas != null && datas.size() > 0) {
			Map<String, Map<String, String>> bizHandler = new HashMap<String, Map<String, String>>();
			Map<String, Object> data;
			String handlerName = null,appHandlerName = null,custHandlerName =null, className = null, parameters = null;
			for (int i = 0; i < datas.size(); i++) {
				data = datas.get(i);
				if (data.get("handlerName") != null) {
					handlerName = data.get("handlerName").toString();
				}
				if (data.get("appHandlerName") != null) {
					appHandlerName = data.get("appHandlerName").toString();
				}
				if (data.get("custHandlerName") != null) {
					custHandlerName = data.get("custHandlerName").toString();
				}
				if (data.get("className") != null) {
					className = data.get("className").toString();
				}
				if (data.get("parameters") != null) {
					parameters = data.get("parameters").toString();
				}
				Map<String, String> biz = new HashMap<String, String>();
				biz.put("handlerName", handlerName);
				biz.put("appHandlerName", appHandlerName);
				biz.put("custHandlerName", custHandlerName);
				biz.put("className", className);
				biz.put("parameters", parameters);
				bizHandler.put(handlerName, biz);
			}
			SYSConfig.setBizHandler(bizHandler);
			log.info("loadBizHandlerData end...");
		}
	}

	public void loadEsbInfoData() {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT id, biz_code as bizCode, system_name as systemName,inf_name as infName,url,active_flag as activeFlag FROM unieap.esb_info where active_flag='Y' and tenant_id =? ");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString(),new Object[] {SYSConfig.getConfig().get("tenantId") });
		if (datas != null && datas.size() > 0) {
			Map<String, Map<String, String>> infInfos = new HashMap<String, Map<String, String>>();
			Map<String, Object> data;
			String id = null, bizCode = null, infName = null, url = null, systemName = null;
			for (int i = 0; i < datas.size(); i++) {
				data = datas.get(i);
				if (data.get("id") != null) {
					id = data.get("id").toString();
				}
				if (data.get("bizCode") != null) {
					bizCode = data.get("bizCode").toString();
				}
				if (data.get("systemName") != null) {
					systemName = data.get("systemName").toString();
				}
				if (data.get("infName") != null) {
					infName = data.get("infName").toString();
				}
				if (data.get("url") != null) {
					url = data.get("url").toString();
				}
				Map<String, String> infInfo = new HashMap<String, String>();
				infInfo.put("id", id);
				infInfo.put("bizCode", bizCode);
				infInfo.put("systemName", systemName);
				infInfo.put("infName", infName);
				infInfo.put("url", url);
				infInfos.put(bizCode, infInfo);
			}
			SYSConfig.setEsbInfo(infInfos);
			log.info("loadEsbInfoData end...");
		}
	}

	/**
	 * initial system handler deal
	 * 
	 * @throws Exception
	 */
	public void initSystemHandlerDeal() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT handler_name as handlerName, class_name as className FROM unieap.handler_config where active_flag ='Y' and handler_type = 'S' and tenant_id =? ");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString(),new Object[] {SYSConfig.getConfig().get("tenantId") });
		if (datas != null & !datas.isEmpty()) {
			for (int i = 0; i < datas.size(); i++) {
				String handlerName = ((Map<String, Object>) datas.get(i)).get("handlerName").toString();
				ConfigHandler handler = (ConfigHandler) ServiceUtils.getBean(handlerName);
				if (((Map<String, Object>) datas.get(i)).get("parameters") != null) {
					handler.deal(((Map<String, Object>) datas.get(i)).get("parameters").toString());
				} else {
					handler.deal(null);
				}
			}
		}
		log.info("initSystemHandlerDeal end...");
	}

	public void reloadSystemHandlerDeal() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT handler_name as handlerName, class_name as className FROM unieap.handler_config where active_flag ='Y' and handler_type = 'S' and tenant_id =?");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString(),new Object[] {SYSConfig.getConfig().get("tenantId") });
		if (datas != null & !datas.isEmpty()) {
			for (int i = 0; i < datas.size(); i++) {
				String handlerName = ((Map<String, Object>) datas.get(i)).get("handlerName").toString();
				ConfigHandler handler = (ConfigHandler) ServiceUtils.getBean(handlerName);
				if (handler.reload()) {
					if (((Map<String, Object>) datas.get(i)).get("parameters") != null) {
						handler.deal(((Map<String, Object>) datas.get(i)).get("parameters").toString());
					} else {
						handler.deal(null);
					}
				}
			}
		}
		log.info("reloadSystemHandlerDeal end...");
	}
}
