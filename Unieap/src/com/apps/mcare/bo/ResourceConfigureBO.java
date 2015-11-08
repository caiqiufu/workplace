package com.apps.mcare.bo;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Service;

import com.apps.mcare.pojo.AppResconfig;
import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.mdm.bo.ChangeLogBO;

@Service("resourceConfigureBO")
public class ResourceConfigureBO extends BaseBO {

	public void getGroupList(PaginationSupport page,String groupName) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(AppResconfig.class);
		Property groupNameProperty = Property.forName("groupName");
		criteria.add(groupNameProperty.eq(groupName));
		getPaginationDataByDetachedCriteria(criteria, page);
	}
	public void getNamesList(PaginationSupport page,String names) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(AppResconfig.class);
		Property nameProperty = Property.forName("name");
		criteria.add(nameProperty.in(names.split(",")));
		getPaginationDataByDetachedCriteria(criteria, page);
	}

	public Map<String, String> resourceConfigureDeal(String operType, AppResconfig vo) throws Exception {
		if (StringUtils.equals(operType,"AddNotification")) {
			return saveNotification(vo);
		} else if (StringUtils.equals(operType, "ModifyNotification")) {
			return updateNotification(vo);
		}else if (StringUtils.equals(operType, "AddNote")) {
			return saveNote(vo);
		} else if (StringUtils.equals(operType, "ModifyNote")) {
			return updateNote(vo);
		} else {
			throw new Exception(UnieapConstants.getMessage("comm.operation.error", new Object[] { operType }));
		}
	}

	public Map<String, String> saveNotification(AppResconfig vo) throws Exception {
		vo.setId(getSequence(null, "unieap"));
		vo.setType("C");
		vo.setName(vo.getId().toString());
		vo.setLanguage(SYSConfig.defaultLanguage);
		vo.setGroupName("message_center_notification");
		vo.setPageNum("2");
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		vo.setSeq(vo.getId().toString());
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public Map<String, String> updateNotification(AppResconfig vo) throws Exception {
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getId(), vo, "resourceConfigure", UnieapConstants.MODIFY, UnieapConstants.UNIEAP);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}
	
	public Map<String, String> saveNote(AppResconfig vo) throws Exception {
		vo.setId(getSequence(null, "unieap"));
		vo.setType("C");
		vo.setName(vo.getId().toString());
		vo.setLanguage(SYSConfig.defaultLanguage);
		vo.setGroupName("message_center_note");
		vo.setPageNum("2");
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		vo.setSeq(vo.getId().toString());
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}
	
	public Map<String, String> updateNote(AppResconfig vo) throws Exception {
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getId(), vo, "resourceConfigure", UnieapConstants.MODIFY, UnieapConstants.UNIEAP);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}
}
