package com.unieap.base;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.unieap.db.DBManager;
import com.unieap.handler.ConfigHandler;

public class EAPContextLoaderListener extends ContextLoaderListener {
	public final Log log = LogFactory.getLog(EAPContextLoaderListener.class);
	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		initAppContext();
		initSysConfig();
		loadHandlerDataToCache();
		loadInfInfoDataToCache();
		loadErrorCodeInfoDataToCache();
		try {
			initEapConfig();
		} catch (Exception e) {
			log.equals("init handler error, error info:"+e.getLocalizedMessage());
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
		sql.append("SELECT handler_name as handlerName, class_name as className FROM unieap.handler_config where active_flag ='Y' and handler_type = 'S'");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString());
		if (datas != null & !datas.isEmpty()) {
			String handlerName;
			ConfigHandler handler;
			for (int i = 0; i < datas.size(); i++) {
				handlerName = ((Map<String, Object>) datas.get(i)).get("handlerName").toString();
				handler = (ConfigHandler)ServiceUtils.getBean(handlerName);
				//handler = (ConfigHandler) Class.forName(className).newInstance();
				if(((Map<String, Object>) datas.get(i)).get("parameters")!=null){
					handler.deal(((Map<String, Object>) datas.get(i)).get("parameters").toString());
				}else{
					handler.deal(null);
				}
			}
		}
	}

	private void initSysConfig() {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT name,value FROM unieap.sys_config where type = 'S' and active_flag ='Y'");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString());
		if (datas != null & !datas.isEmpty()) {
			Map<String, String> config = new HashMap<String, String>();
			String name,value = "";
			Map<String, Object> data;
			for (int i = 0; i < datas.size(); i++) {
				data =  datas.get(i);
				if(datas.get(i).get("name")!=null){
					name = data.get("name").toString();
					if(datas.get(i).get("value")!=null){
						value = data.get("value").toString();
					}
					config.put(name,value);
				}
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
	public void loadHandlerDataToCache(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT handler_name as handlerName,class_name as className ,parameters FROM unieap.handler_config ");
		sql.append(" where active_flag ='Y' and handler_type ='B'");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString());
		if(datas!=null&&datas.size()>0){
			Map<String, Map<String, String>> bizHandler = new HashMap<String,Map<String,String>>();
			Map<String, Object> data;
			String handlerName = null,className = null ,parameters = null;
			for(int i = 0; i< datas.size(); i++){
				data = datas.get(i);
				if(data.get("handlerName")!=null){
					handlerName = data.get("handlerName").toString();
				}
				if(data.get("className")!=null){
					className = data.get("className").toString();
				}
				if(data.get("parameters")!=null){
					parameters = data.get("parameters").toString();
				}
				Map<String,String> biz = new HashMap<String,String>();
				biz.put("handlerName", handlerName);
				biz.put("className", className);
				biz.put("parameters", parameters);
				bizHandler.put(handlerName, biz);
			}
			SYSConfig.setBizHandler(bizHandler);
		}
	}
	public void loadInfInfoDataToCache(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id, biz_code as bizCode, system_name as systemName,inf_name as infName,url,active_flag as activeFlag FROM unieap.esb_info where active_flag='Y'");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString());
		if(datas!=null&&datas.size()>0){
			Map<String, Map<String, String>> infInfos = new HashMap<String,Map<String,String>>();
			Map<String, Object> data;
			String id = null,bizCode = null ,infName = null,url = null,systemName = null;
			for(int i = 0; i< datas.size(); i++){
				data = datas.get(i);
				if(data.get("id")!=null){
					id = data.get("id").toString();
				}
				if(data.get("bizCode")!=null){
					bizCode = data.get("bizCode").toString();
				}
				if(data.get("systemName")!=null){
					systemName = data.get("systemName").toString();
				}
				if(data.get("infName")!=null){
					infName = data.get("infName").toString();
				}
				if(data.get("url")!=null){
					url = data.get("url").toString();
				}
				Map<String,String> infInfo = new HashMap<String,String>();
				infInfo.put("id", id);
				infInfo.put("bizCode", bizCode);
				infInfo.put("systemName", systemName);
				infInfo.put("infName", infName);
				infInfo.put("url", url);
				infInfos.put(bizCode, infInfo);
			}
			SYSConfig.setInfInfo(infInfos);
		}
	}
	public void loadErrorCodeInfoDataToCache(){
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT type,error_code as errorCode,error_desc as errorDesc FROM unieap.error_code");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString());
		if(datas!=null&&datas.size()>0){
			Map<String, Map<String, String>> errorCodeInfos = new HashMap<String,Map<String,String>>();
			Map<String, Object> data;
			String type = null,errorCode = null ,errorDesc = null;
			for(int i = 0; i< datas.size(); i++){
				data = datas.get(i);
				if(data.get("type")!=null){
					type = data.get("type").toString();
				}
				if(data.get("errorCode")!=null){
					errorCode = data.get("errorCode").toString();
				}
				if(data.get("errorDesc")!=null){
					errorDesc = data.get("errorDesc").toString();
				}
				Map<String,String> errorCodeInfo = new HashMap<String,String>();
				errorCodeInfo.put("type", type);
				errorCodeInfo.put("errorCode", errorCode);
				errorCodeInfo.put("errorDesc", errorDesc);
				errorCodeInfos.put(errorCode, errorCodeInfo);
			}
			SYSConfig.setErrorCoreInfo(errorCodeInfos);
		}
	}
}
