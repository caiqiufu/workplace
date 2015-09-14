package com.unieap.base;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.ServletContextEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.WebApplicationContext;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.unieap.db.DBManager;
import com.unieap.handler.ConfigHandler;
import com.unieap.tools.DOMUtils;

public class EAPContextLoaderListener extends ContextLoaderListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private final static String BASICPATH = "/WEB-INF/classes/EAPConfig.xml";
	private final static String SYSPATH = "/WEB-INF/classes/SYSConfig.xml";

	private static final Log logger = LogFactory
			.getLog(EAPContextLoaderListener.class);

	public void contextInitialized(ServletContextEvent event) {
		logger.info("load EAPContextLoaderListener begin...");
		super.contextInitialized(event);
		initAppContext();
		initSysConfig();
		initEapConfig();
		//initAccessResource();
		logger.info("load EAPContextLoaderListener end...");
	}

	private void initAppContext() {
		if (AppContextContainer.getAppContext() == null) {
			logger.info("bind context begin...");
			WebApplicationContext context = EAPContextLoaderListener
					.getCurrentWebApplicationContext();
			AppContextContainer.setAppContext(context);
			logger.info("bind context finished...");
		}
	}
	private void initAccessResource() {
		logger.info("load resource and role relation begin...");
		Map<String, Collection<String>> resourcesRole = new HashMap<String, Collection<String>>();
		Collection<String> roles = null;
		StringBuffer sql = new StringBuffer();
		sql.append("select resrole.roleid,rolealloc.allocid,menu.menuid,menu.href ");
		sql.append(" from rolealloc, resrole, menu");
		sql.append(" where rolealloc.roleid= resrole.roleid ");
		sql.append(" and resrole.resid = menu.menuid ");
		sql.append(" and menu.HREF is not null");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString());
		/*List<Map<String, Object>> datas = DBManager
				.getJT(null)
				.queryForList(
						"SELECT HREF,RS.ROLEID FROM RESROLE RS, RESDATA RD,MENU WHERE RS.RESID= RD.RESID AND RD.RESID=MENU.MENUID AND MENU.HREF IS NOT NULL AND RD.RESTYPE='1'");*/
		if (datas != null&&datas.size()>0) {
			for (Map<String, Object> data : datas) {
				roles = resourcesRole.get(data.get("href").toString());
				if (roles == null) {
					roles = new ArrayList<String>();
					resourcesRole.put(data.get("href").toString(), roles);
				}
				roles.add(data.get("roleid").toString());
			}
		}
		//CacheMgt.setResourcesRole(resourcesRole);
		logger.info("load resource and role relation end...");
	}

	private void initEapConfig() {
		logger.info("load init config begin...");
		logger.info("EAPConfig file path :" + BASICPATH);
		Document doc = null;
		try {
			InputStream fis = EAPContextLoaderListener.getCurrentWebApplicationContext()
					.getServletContext().getResourceAsStream(BASICPATH);
			doc = DOMUtils.loadDocumentFromFile(fis);
			NodeList nl = DOMUtils.getMultiNodes(doc, "component");
			Node node = null;
			String componentName = null;
			boolean isEnabled = false;
			String handlerName = null;
			//String description = null;
			for (int i = 0; i < nl.getLength(); i++) {
				node = nl.item(i);
				componentName = DOMUtils.getAttributeValue((Element) node,
						"name");
				isEnabled = "true".equals(DOMUtils.getAttributeValue(
						(Element) node, "isEnabled")) ? true : false;
				handlerName = DOMUtils.getAttributeValue((Element) node,
						"handler");
				//description = DOMUtils.getAttributeValue((Element) node,"description");
				if (isEnabled && StringUtils.hasText(handlerName)) {
					ConfigHandler handler = (ConfigHandler) ServiceUtils
							.getBean(handlerName);
					logger.info("component[" + componentName
							+ "]loading...");
					try {
						handler.dealNode(node, EAPContextLoaderListener
								.getCurrentWebApplicationContext()
								.getServletContext(), componentName);
					} catch (Exception e) {
						logger.error("load[" + componentName + "]filed!"
								+ e.toString());
					}
					logger.info("component[" + componentName
							+ "]loading completed...");
				}else {
					logger.info("component[" + componentName
							+ "] disavailable!");
				}
			}

		} catch (Exception e1) {
			logger.warn("system config file [" + BASICPATH + "] not exist");
		}
	}

	private void initSysConfig() {
		logger.info("init sysPara begin...");
		Document doc = null;
		try {
			InputStream fis = EAPContextLoaderListener.getCurrentWebApplicationContext()
					.getServletContext().getResourceAsStream(SYSPATH);
			doc = DOMUtils.loadDocumentFromFile(fis);
			Node node = DOMUtils.getSingleNode(doc, "isDebug");
			if (node != null) {
				SYSConfig.isDebug = "true".equals((String) node
						.getTextContent()) ? true : false;
			}
			logger.info("isDebug >>>" + SYSConfig.isDebug);
			node = DOMUtils.getSingleNode(doc, "isBizLog");
			if (node != null) {
				SYSConfig.isBizLog = "true".equals((String) node
						.getTextContent()) ? true : false;
			}
			logger.info("isBizLog >>>" + SYSConfig.isBizLog);
			node = DOMUtils.getSingleNode(doc, "isDataLog");
			if (node != null) {
				SYSConfig.isDataLog = "true".equals((String) node
						.getTextContent()) ? true : false;
			}
			logger.info("isDataLog >>>" + SYSConfig.isDataLog);
			node = DOMUtils.getSingleNode(doc, "queryPath");
			if (node != null) {
				SYSConfig.queryPath = (String) node.getTextContent();
			}
			logger.info("queryPath >>>" + SYSConfig.queryPath);
			node = DOMUtils.getSingleNode(doc, "wfPath");
			if (node != null) {
				SYSConfig.wfPath = (String) node.getTextContent();
			}
			logger.info("wfPath >>>" + SYSConfig.wfPath);
			node = DOMUtils.getSingleNode(doc, "rpPath");
			if (node != null) {
				SYSConfig.rpPath = (String) node.getTextContent();
			}
			logger.info("rpPath >>>" + SYSConfig.rpPath);
			node = DOMUtils.getSingleNode(doc, "dbType");
			if (node != null) {
				SYSConfig.dbType = (String) node.getTextContent();
			}
			logger.info("dbType >>>" + SYSConfig.dbType);
			node = DOMUtils.getSingleNode(doc, "isHibernate");
			if (node != null) {
				SYSConfig.isHibernate = "true".equals((String) node
						.getTextContent()) ? true : false;
			}
			logger.info("isHibernate >>>" + SYSConfig.isHibernate);
			node = DOMUtils.getSingleNode(doc, "selectMaxNum");
			if (node != null) {
				SYSConfig.selectMaxNum = Integer.parseInt((String) node
						.getTextContent());
			}
			logger.info("selectMaxNum >>>" + SYSConfig.selectMaxNum);
			node = DOMUtils.getSingleNode(doc, "pageSize");
			if (node != null) {
				SYSConfig.pageSize = Integer.parseInt((String) node
						.getTextContent());
			}
			logger.info("pageSize >>>" + SYSConfig.pageSize);

			node = DOMUtils.getSingleNode(doc, "defaultLanguage");
			if (node != null) {
				SYSConfig.defaultLanguage = (String) node.getTextContent();
			}
			node = DOMUtils.getSingleNode(doc, "filePath");
			if (node != null) {
				SYSConfig.filePath = (String) node.getTextContent();
			}
			logger.info("filePath >>>" + SYSConfig.filePath);
			node = DOMUtils.getSingleNode(doc, "rootPath");
			if (node != null) {
				SYSConfig.rootPath = (String) node.getTextContent();
			}
			logger.info("rootPath >>>" + SYSConfig.rootPath);
			Locale currentLocale = new Locale(SYSConfig.defaultLanguage
					.split("_")[0], SYSConfig.defaultLanguage.split("_")[1]);
			ResourceBundleHelper.setLocal(currentLocale);
			logger.info("init sysPara end...");
		} catch (Exception e1) {
			logger.warn("system config file[" + SYSPATH + "] not exist");
		}
	}
}
