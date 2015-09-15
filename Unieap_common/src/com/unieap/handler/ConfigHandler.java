package com.unieap.handler;

import javax.servlet.ServletContext;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.w3c.dom.Node;

/**
 * <p>
 * Description: [描述该类概要功能介绍]
 * </p>
 * 
 * @author <a href="mailto: caiqiufu@sohu.com">蔡秋伏</a>
 * @version $Revision: 1.1 $
 */
public interface ConfigHandler {
	/**
	 * 
	 */
	final Log log = LogFactory.getLog(ConfigHandler.class);

	/**
	 * @param node
	 * @param servlet
	 * @param appName
	 * @throws Exception
	 */
	public void dealNode(Node node, ServletContext servlet, String appName) throws Exception;
	public void deal(String parameters)throws Exception;

}
