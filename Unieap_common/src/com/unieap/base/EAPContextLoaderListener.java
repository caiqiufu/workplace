package com.unieap.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.unieap.db.DBManager;
import com.unieap.handler.ConfigHandler;

public class EAPContextLoaderListener extends ContextLoaderListener {

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		initSysConfig();
		initAppContext();
		try {
			initEapConfig();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void initAppContext() {
		if (AppContextContainer.getAppContext() == null) {
			WebApplicationContext context = EAPContextLoaderListener.getCurrentWebApplicationContext();
			AppContextContainer.setAppContext(context);
		}
	}

	private void initEapConfig() throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT * FROM unieap.handler_config where activate_flag ='Y' and handler_type = 'S'");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString());
		if (datas != null & !datas.isEmpty()) {
			String className;
			ConfigHandler handler;
			for (int i = 0; i < datas.size(); i++) {
				className = ((Map<String, Object>) datas.get(i)).get("name").toString();
				handler = (ConfigHandler) Class.forName(className).newInstance();
				handler.deal(((Map<String, Object>) datas.get(i)).get("parameters").toString());
				;
			}
		}
	}

	private void initSysConfig() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT name,value FROM unieap.sys_config where type = 'S' and active_flag ='Y'");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString());
		if (datas != null & !datas.isEmpty()) {
			Map<String, String> config = new HashMap<String, String>();
			for (int i = 0; i < datas.size(); i++) {
				config.put(((Map<String, Object>) datas.get(i)).get("name").toString(),
						((Map<String, Object>) datas.get(i)).get("value").toString());
			}
			SYSConfig.setConfig(config);
		}
		StringBuffer sqldebug = new StringBuffer();
		sqldebug.append("SELECT name,value FROM unieap.sys_config where type = 'D' and active_flag ='Y'");
		List<Map<String, Object>> debugdatas = DBManager.getJT(null).queryForList(sqldebug.toString());
		if (debugdatas != null & !debugdatas.isEmpty()) {
			Map<String, String> isDebug = new HashMap<String, String>();
			for (int i = 0; i < debugdatas.size(); i++) {
				isDebug.put(((Map<String, Object>) debugdatas.get(i)).get("name").toString(),
						((Map<String, Object>) debugdatas.get(i)).get("value").toString());
			}
			SYSConfig.setIsDebug(isDebug);
		}
	}
}
