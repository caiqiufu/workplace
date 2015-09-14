package com.unieap;

import java.beans.PropertyDescriptor;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.unieap.mdm.vo.DicDataVO;

/**
 * Feb 19, 2011 缓存管理
 */
public final class CacheMgt {
	private static Map<String, Map<String,PropertyDescriptor>> beanProps = new HashMap<String, Map<String,PropertyDescriptor>>();
	private static Map<String, Map<String,DicDataVO>> DicData = new HashMap<String, Map<String,DicDataVO>>();
	
	public static Map<String,Map<String,DicDataVO>> getDicData() {
		return DicData;
	}
	public static Map<String,DicDataVO> getDicData(String groupCode) {
		return DicData.get(groupCode);
	}
	public static DicDataVO getDicData(String groupCode,String dicCode) {
		if(StringUtils.isEmpty(groupCode)||StringUtils.isEmpty(dicCode)){
			return null;
		}
		if(DicData.get(groupCode)==null){
			return null;
		}else{
			return DicData.get(groupCode).get(dicCode);
		}
	}
	public static void setDicData(Map<String, Map<String,DicDataVO>> dicData) {
		DicData = dicData;
	}
	public static Map<String,PropertyDescriptor> getBeanProps(String className){
		return beanProps.get(className);
	}
	public static void setBeanProps(String className,Map<String,PropertyDescriptor> beanProp){
		beanProps.put(className,beanProp);
	}
	/**
	 * get assigned roles
	 * @param userId
	 * @param orgId
	 * @return
	 */
	public static String getAssignedRoles(Long userId, Long orgId){
		return null;
	}
	
}
