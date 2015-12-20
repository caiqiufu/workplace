package com.unieap;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.MessageSource;

import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.db.DBManager;
import com.unieap.mdm.vo.DicDataVO;
import com.unieap.pojo.User;

/**
 * Dec 14, 2010 全局变量,包含WEB层与Action层的数据交互定义
 */
public class UnieapConstants {
	/**
	 * <p>
	 * 描述:保存类型:UPDATE,NEW,DELETE
	 * </p>
	 */
	public final static String ADD = "Add";
	public final static String MODIFY = "Modify";
	public final static String DELETE = "Delete";
	public final static String CHECKEXIST = "checkExist";
	public final static String ISSUCCESS = "isSuccess";
	public final static String RETURNMESSAGE = "message";
	public final static String SUCCESS = "success";
	public final static String FAILED = "failed";
	public final static String UNIEAP = "unieap";
	public final static String THIRDPART = "thirdpart";
	// public final static String ESB = "esb";
	// public final static String REUSE = "reuse";
	public static boolean ISUNIEAP = false;

	public final static String ERRORCODE = "errorCode";
	public final static String ERRORDESC = "errorDesc";

	public static Long MENURESDEFID = Long.valueOf(1);
	/**
	 * <p>
	 * 描述: true
	 * </p>
	 */
	public final static boolean TRUE = true;
	/**
	 * <p>
	 * 描述:false
	 * </p>
	 */
	public final static boolean FALSE = false;
	/**
	 * <p>
	 * Desc:Yes
	 * </p>
	 */
	public final static String YES = "Y";
	/**
	 * <p>
	 * Desc:false
	 * </p>
	 */
	public final static String NO = "N";

	public static String C99999 = "999999";
	/**
	 * success
	 */
	public static String C0 = "0";
	/**
	 * failed
	 */
	public static String C1 = "1";

	/**
	 * 语言
	 */
	public final static String[] LANGUAGE = { "zh_CN", "en_US" };
	public final static String MYSQL = "MYSQL";
	public final static String ORACLE = "ORACLE";
	/**
	 * MENU
	 */
	public final static String MENU = "M";
	/**
	 * MENU
	 */
	public final static String BUTTON = "B";
	/**
	 * USER
	 */
	public final static String USER = "user";
	/**
	 * LOGINCONTEXT
	 */
	public final static String LOGINCONTEXT = "loginContext";
	/**
	 * LOCATIONURL
	 */
	public final static String LOCATIONURL = "locationUrl";
	/**
	 * MODULES
	 */
	public final static String MODULES = "modules";
	public final static Long TREEROOTID = Long.valueOf("-1");
	/**
	 * <p>
	 * WINDOWNAME
	 * </p>
	 */
	public final static String WINDOWNAME = "windowName";
	public final static String DATEFORMAT = "yyyy-MM-dd";
	public final static String TIMEFORMAT = "yyyy-MM-dd hh:mm:ss";
	public final static String TIMEFORMAT2 = "yyyyMMddhhmmss";
	public static User user = null;

	public final static User getUser() {
		return user;
	}

	public final static String getCurrentTime(String dsName, String format) {
		Map<String, Object> obj = DBManager.getJT(dsName).queryForMap("SELECT CURRENT_TIMESTAMP() CURRENTTIME");
		if (StringUtils.isEmpty(format)) {
			format = TIMEFORMAT2;
		}
		Date data = (Date) obj.get("CURRENTTIME");
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dt = sdf.format(data);
		return dt;
	}

	public final static Date getDateTime(String dsName) {
		Map<String, Object> obj = DBManager.getJT(dsName).queryForMap("SELECT CURRENT_TIMESTAMP() CURRENTTIME");
		Date data = (Date) obj.get("CURRENTTIME");
		return data;
	}

	public synchronized final static Integer getSequence(String dsName, String serialName) {
		if (StringUtils.isEmpty(serialName)) {
			serialName = UNIEAP;
		}
		return DBManager.getJT(dsName).queryForInt("SELECT unieap.NEXTVAL('" + serialName + "') SEQ");
	}

	public final static String getMessage(String code) {
		Object[] args = null;
		return getMessage(code, args);
	}

	public final static String getMessage(String code, String args) {
		if (!StringUtils.isEmpty(args)) {
			Object[] argsObj = args.split(",");
			return getMessage(code, argsObj);
		} else {
			return getMessage(code);
		}
	}

	public final static String getMessage(String code, Object[] args) {
		return getMessage(code, args, "");
	}

	public final static String getMessage(String code, Object[] args, String defaultMsg) {
		String lang[] = StringUtils.split(SYSConfig.defaultLanguage, "_");
		if (lang.length == 1) {
			return getMessage(code, args, defaultMsg, new Locale(lang[0]));
		} else {
			return getMessage(code, args, defaultMsg, new Locale(lang[0], lang[1]));
		}
	}

	public final static String getMessage(String code, Object[] args, String defaultMsg, Locale loc) {
		MessageSource message = (MessageSource) ServiceUtils.getBean("messageSource");
		return message.getMessage(code, args, defaultMsg, loc);
	}

	public static String getDicName(String groupCode, String dicCode) {
		Map<String, DicDataVO> group = CacheMgt.getDicData(groupCode);
		if (group == null || !group.containsKey(dicCode)) {
			return dicCode;
		} else {
			DicDataVO dicDataVO = group.get(dicCode);
			return dicDataVO.getDicName();
		}
	}
}
