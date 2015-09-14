package com.unieap.mantis.bo;

import java.util.Map;

import org.springframework.stereotype.Service;

import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;
import com.unieap.pojo.MChglog;
@Service("changeLogBOm")
public class ChangeLogBO extends BaseBO{
	
	public static String TAB_MDEFECT = "MDEFECT";
	public static String TAB_MNOTE = "MNOTE";
	
	/**
	 * save change log
	 * @param vo
	 * @return
	 */
	public Map<String, String> save(MChglog vo){
		vo.setLogId(getSequence(null,"unieap"));
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setUserName(UnieapConstants.getUser().getUserName());
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	/**
	 * save change log
	 * @param recordId
	 * @param modifyType
	 * @param fieldName
	 * @param displayName
	 * @param oldValue
	 * @param newValue
	 * @return
	 */
	public Map<String, String> save(Integer recordId,String modifyType,String fieldName,String displayName,String oldValue,String newValue){
		MChglog vo = new MChglog();
		vo.setLogId(getSequence(null,"unieap"));
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyType(modifyType);
		vo.setUserName(UnieapConstants.getUser().getUserName());
		vo.setFieldName(fieldName);
		vo.setDisplayName(displayName);
		vo.setNewValue(newValue);
		vo.setOldValue(oldValue);
		vo.setRecordId(recordId);
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}

}
