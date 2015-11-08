package com.unieap;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.apps.esb.pojo.Esblog;
import com.apps.esb.pojo.EsblogDevice;
import com.unieap.mdm.vo.DicDataVO;
import com.unieap.pojo.Chglog;
import com.unieap.pojo.ErrorCode;

/**
 * Feb 19, 2011 缓存管理
 */
public final class CacheMgt {
	/**
	 * 缓存bean 属性
	 */
	private static Map<String, Map<String, PropertyDescriptor>> beanProps = new HashMap<String, Map<String, PropertyDescriptor>>();
	/**
	 * 缓存变更历史对象
	 */
	private static List<Chglog> chgLogDatas = Collections.synchronizedList(new ArrayList<Chglog>());
	/**
	 * 缓存第三方esb调研日志对象
	 */
	private static List<Esblog> esblogDatas = Collections.synchronizedList(new ArrayList<Esblog>());
	/**
	 * 缓存设备信息
	 */
	private static List<EsblogDevice> esblogDeviceDatas = Collections.synchronizedList(new ArrayList<EsblogDevice>());
	/**
	 * 缓存esb调用计费系统日志对象
	 */
	private static List<Map<String,Object>> relaodHandlers = new ArrayList<Map<String,Object>>();
	
	private static List<Map<String,Object>> esblogSOAPDatas = Collections.synchronizedList(new ArrayList<Map<String,Object>>());
	/**
	 * 缓存字典数据
	 */
	private static Map<String, Map<String, DicDataVO>> DicData = new HashMap<String, Map<String, DicDataVO>>();
	/**
	 * 其他缓存数据
	 */
	private static Map<String,Object> cacheData = new HashMap<String,Object>();
	
	private static Map<String,com.unieap.pojo.User> userList = new HashMap<String,com.unieap.pojo.User>();
	
	
	private static Map<String,ErrorCode> errorCodeList = new HashMap<String,ErrorCode>();

	public static void addChglog(Chglog vo) {
		chgLogDatas.add(vo);
	}

	public static List<Chglog> getChglogDatas() {
		return chgLogDatas;
	}

	public static void addEsblog(Esblog vo) {
		esblogDatas.add(vo);
	}

	public static List<Esblog> getEsblogDatas() {
		return esblogDatas;
	}

	public static List<EsblogDevice> getEsblogDeviceDatas() {
		return esblogDeviceDatas;
	}

	public static void addEsblogDeviceData(EsblogDevice esblogDevice) {
		CacheMgt.esblogDeviceDatas.add(esblogDevice);
	}
	
	public static Map<String, Map<String, DicDataVO>> getDicData() {
		return DicData;
	}

	public static Map<String, DicDataVO> getDicData(String groupCode) {
		return DicData.get(groupCode);
	}

	public static DicDataVO getDicData(String groupCode, String dicCode) {
		if (StringUtils.isEmpty(groupCode) || StringUtils.isEmpty(dicCode)) {
			return null;
		}
		if (DicData.get(groupCode) == null) {
			return null;
		} else {
			return DicData.get(groupCode).get(dicCode);
		}
	}

	public static void setDicData(Map<String, Map<String, DicDataVO>> dicData) {
		DicData = dicData;
	}

	public static Map<String, PropertyDescriptor> getBeanProps(String className) {
		return beanProps.get(className);
	}

	public static void setBeanProps(String className, Map<String, PropertyDescriptor> beanProp) {
		beanProps.put(className, beanProp);
	}

	
	public static List<Map<String, Object>> getEsblogSOAPDatas() {
		return esblogSOAPDatas;
	}

	public static void setEsblogSOAPDatas(List<Map<String, Object>> esblogSOAPDatas) {
		CacheMgt.esblogSOAPDatas = esblogSOAPDatas;
	}

	public static void addEsbSOAPlog(Map<String,Object> data) {
		esblogSOAPDatas.add(data);
	}

	public static Map<String,Object> getCacheData() {
		return cacheData;
	}

	public static void setCacheData(Map<String,Object> cacheData) {
		CacheMgt.cacheData = cacheData;
	}

	public static List<Map<String, Object>> getRelaodHandlers() {
		return relaodHandlers;
	}

	public static void setRelaodHandlers(List<Map<String, Object>> relaodHandlers) {
		CacheMgt.relaodHandlers = relaodHandlers;
	}

	public static Map<String, com.unieap.pojo.User> getUserList() {
		return userList;
	}

	public static void setUserList(Map<String, com.unieap.pojo.User> userList) {
		CacheMgt.userList = userList;
	}

	public static com.unieap.pojo.User getUser(String userCode) {
		return userList.get(userCode);
	}

	public static Map<String, ErrorCode> getErrorCodeList() {
		return errorCodeList;
	}

	public static void setErrorCodeList(Map<String, ErrorCode> errorCodeList) {
		CacheMgt.errorCodeList = errorCodeList;
	}
	public static ErrorCode getErrorCode(String errorCode) {
		return errorCodeList.get(errorCode);
	}
}
