package com.unieap;

import java.beans.PropertyDescriptor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.apps.esb.pojo.Esblog;
import com.unieap.mdm.vo.DicDataVO;
import com.unieap.pojo.Chglog;

/**
 * Feb 19, 2011 缓存管理
 */
public final class CacheMgt {
	private static Map<String, Map<String, PropertyDescriptor>> beanProps = new HashMap<String, Map<String, PropertyDescriptor>>();
	private static Map<String, Map<String, DicDataVO>> DicData = new HashMap<String, Map<String, DicDataVO>>();

	private static List<Chglog> chgLogDatas = Collections.synchronizedList(new ArrayList<Chglog>());

	private static List<Esblog> esblogDatas = Collections.synchronizedList(new ArrayList<Esblog>());
	private static List<Map<String,Object>> esblogSOAPDatas = Collections.synchronizedList(new ArrayList<Map<String,Object>>());
	
	private static Map<String,List<Map<String,Object>>> cacheData = new HashMap<String,List<Map<String,Object>>>();

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

	public static Map<String, List<Map<String, Object>>> getCacheData() {
		return cacheData;
	}

	public static void setCacheData(Map<String, List<Map<String, Object>>> cacheData) {
		CacheMgt.cacheData = cacheData;
	}

	
	
	
	
	
}
