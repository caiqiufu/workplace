package com.unieap.base;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;

import com.unieap.security.SignProvider;
import com.unieap.tools.Propertyholder;

public class EAPContextLoaderListener extends ContextLoaderListener {
	public final Log log = LogFactory.getLog(EAPContextLoaderListener.class);

	public void contextInitialized(ServletContextEvent event) {
		super.contextInitialized(event);
		initAppContext();
		LoadSystemData loadSystemData = (LoadSystemData) ServiceUtils.getBean("loadSystemData");
		loadSystemData.loadSystemConfigure();
		loadSystemData.loadBizHandlerData();
		loadSystemData.loadEsbInfoData();
		try {
			loadSystemData.initSystemHandlerDeal();
		} catch (Exception e) {
			log.equals("init handler error, error info:" + e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	private void initAppContext() {
		if (AppContextContainer.getAppContext() == null) {
			WebApplicationContext context = EAPContextLoaderListener.getCurrentWebApplicationContext();
			AppContextContainer.setAppContext(context);
		}
	}

	public void licenceCheck() {
		String pubKey = Propertyholder.getContextProperty("pubKey");
		String licenceType = Propertyholder.getContextProperty("licence.type");
		String licenceExpiretime = Propertyholder.getContextProperty("licence.expiretime");
		String licenceQuery = Propertyholder.getContextProperty("licence.flow.controller.query");
		String licenceBizhandle = Propertyholder.getContextProperty("licence.flow.controller.bizhandle");
		String query = SYSConfig.getConfig().get("licence.flow.controller.query");
		String bizhandle = SYSConfig.getConfig().get("licence.flow.controller.bizhandle");
		String type = SYSConfig.getConfig().get("licence.type");
		String expiretime = SYSConfig.getConfig().get("licence.expiretime");
		if (SignProvider.verify(pubKey, query, licenceQuery)) {
			SYSConfig.getConfig().put("licence.flow.controller.query", query);
		} else {
			SYSConfig.getConfig().put("licence.flow.controller.query", "5");
		}
		if (SignProvider.verify(pubKey, bizhandle, licenceBizhandle)) {
			SYSConfig.getConfig().put("licence.flow.controller.bizhandle", bizhandle);
		} else {
			SYSConfig.getConfig().put("licence.flow.controller.bizhandle", "2");
		}
		if (SignProvider.verify(pubKey, type, licenceType)) {
			SYSConfig.getConfig().put("licence.type", type);
		} else {
			SYSConfig.getConfig().put("licence.type", "T");
		}
		if (SignProvider.verify(pubKey, expiretime, licenceExpiretime)) {
			SYSConfig.getConfig().put("licence.expiretime", expiretime);
		} else {
			SYSConfig.getConfig().put("licence.expiretime", "2015-10-10 00:00:00");
		}

	}
}
