package com.unieap.base;

/**
 * Jan 25, 2011 系统参数配置
 */
public class SYSConfig {
	/**
	 * <p>描述:调试状态</p>
	 */
	public static boolean isDebug = false;
	/**
	 * <p>描述:记录业务日志</p>
	 */
	public static boolean isBizLog = false;
	/**
	 * <p>描述:数据操作日志</p>
	 */
	public static boolean isDataLog = false;
	/**
	 * <p>描述:查询配置路径</p>
	 */
	public static String queryPath = "/WEB-INF/query";
	/**
	 * <p>描述:工作流配置路径</p>
	 */
	public static String wfPath = "/WEB-INF/workflow";
	/**
	 * <p>描述:报表配置路径</p>
	 */
	public static String rpPath = "/WEB-INF/report";
	/**
	 * <p>描述:filePath</p>
	 */
	public static String filePath = "";
	/**
	 * <p>描述:rootPath</p>
	 */
	public static String rootPath = "";
	/**
	 * <p>描述:数据库类型 MYSQL,ORACLE</p>
	 */
	public static String dbType = "MYSQL";
	/**
	 * <p>描述:系统默认采用isHibernate持久化</p>
	 */
	public static boolean isHibernate = true;
	/**
	 * <p>描述:一次性查询最多纪录数</p>
	 */
	public static int selectMaxNum = 5000;
	/**
	 * <p>描述:分页默认条数</p>
	 */
	public static int pageSize = 20;
	
	/**
	 * 默认语言
	 */
	public static String defaultLanguage = "zh_CN";
}
