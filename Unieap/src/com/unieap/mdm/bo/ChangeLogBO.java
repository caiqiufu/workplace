package com.unieap.mdm.bo;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;
import com.unieap.pojo.Chglog;

@Service("changeLogBO")
public class ChangeLogBO extends BaseBO {

	public void setDatas(Chglog vo) {
		CacheMgt.addChglog(vo);
		batchSaveMore100();
	}
	/**
	 * save change log
	 * 
	 * @param vo
	 * @return
	 */
	public Map<String, String> save(Chglog vo) {
		vo.setLogId(getSequence(null,UnieapConstants.UNIEAP));
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setUserName(UnieapConstants.getUser().getUserName());
		setDatas(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	/**
	 * save change log
	 * 
	 * @param recordId
	 * @param modifyObj
	 * @param modifyType
	 * @param fieldName
	 * @param displayName
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public Map<String, String> save(Integer recordId, String modifyObj, String modifyType, String fieldName,
			String displayName, String oldValue, String newValue, String app) {
		Chglog vo = new Chglog();
		vo.setLogId(getSequence(null,UnieapConstants.UNIEAP));
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyObj(modifyObj);
		vo.setModifyType(modifyType);
		vo.setUserName(UnieapConstants.getUser().getUserName());
		vo.setFieldName(fieldName);
		vo.setDisplayName(displayName);
		vo.setNewValue(newValue);
		vo.setOldValue(oldValue);
		vo.setRecordId(recordId);
		vo.setApp(app);
		setDatas(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	/**
	 * save updated value and log
	 * 
	 * @param id
	 * @param newObj
	 * @param modifyType
	 * @param app
	 * @return
	 * @throws IntrospectionException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	public Map<String, String> save(Integer id, Object newObj, String modifyObj, String modifyType, String app)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Object oldObj = DBManager.getHT(null).get(newObj.getClass(), id);
		// DBManager.getHT(null).update(newObj);
		Map<String, PropertyDescriptor> beanprops = CacheMgt.getBeanProps(newObj.getClass().getName());
		if (beanprops == null) {
			beanprops = cacheBeanprops(newObj);
		}
		Iterator<String> iter = beanprops.keySet().iterator();
		String snewValue = "", soldValue = "";
		Integer inewValue, ioldValue;
		while (iter.hasNext()) {
			String key = iter.next();
			if (StringUtils.equals(key, "createDate") || StringUtils.equals(key, "modifyDate")
					|| StringUtils.equals(key, "createBy") || StringUtils.equals(key, "modifyBy")) {
				continue;
			} else {
				if (beanprops.containsKey(key)) {
					PropertyDescriptor prop = beanprops.get(key);
					Method getter = prop.getReadMethod();
					Object newValue = getter.invoke(newObj, null);
					Object oldValue = getter.invoke(oldObj, null);
					if (newValue != null || oldValue != null) {
						Class<?> retType = getter.getReturnType();
						if (String.class == retType) {
							if (newValue != null) {
								snewValue = newValue.toString();
							}
							if (oldValue != null) {
								soldValue = oldValue.toString();
							}
							if (!StringUtils.equals(snewValue, soldValue)) {
								save(id, modifyObj, modifyType, key, key, soldValue, snewValue, app);
							}
						} else if (Integer.class == retType) {
							if (newValue != null && oldValue != null) {
								inewValue = (Integer) newValue;
								ioldValue = (Integer) oldValue;
								if (inewValue.intValue() != ioldValue.intValue()) {
									save(id, modifyObj, modifyType, key, key, ioldValue.toString(),
											inewValue.toString(), app);
								}
							} else {
								if (newValue == null) {
									snewValue = "";
								}
								if (oldValue == null) {
									soldValue = "";
								}
								save(id, modifyObj, modifyType, key, key, soldValue, snewValue, app);
							}
						}
					}
				}
			}
		}
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public void batchSaveMore100() {
		List<Chglog> datas = CacheMgt.getChglogDatas();
		List<Chglog> copyDatas = new ArrayList<Chglog>();
		if (datas.size() > 100) {
			copyDatas.addAll(datas);
			datas.clear();
			DBManager.getHT(null).saveOrUpdateAll(copyDatas);

		}
	}
}
