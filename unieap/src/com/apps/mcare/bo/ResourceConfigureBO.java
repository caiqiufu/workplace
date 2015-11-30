package com.apps.mcare.bo;

import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apps.esb.service.bss.handler.AppResconfigHandler;
import com.apps.mcare.pojo.AppResconfig;
import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.file.bo.FileBO;
import com.unieap.mdm.bo.ChangeLogBO;

@Service("resourceConfigureBO")
public class ResourceConfigureBO extends BaseBO {

	public void getGroupList(PaginationSupport page, String groupNames, String names) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(AppResconfig.class);
		if (!StringUtils.isEmpty(groupNames)) {
			Property groupNameProperty = Property.forName("groupName");
			if (groupNames.contains(",")) {
				criteria.add(groupNameProperty.in(groupNames.split(",")));
			} else {
				criteria.add(groupNameProperty.eq(groupNames));
			}
		}
		if (!StringUtils.isEmpty(names)) {
			Property nameProperty = Property.forName("name");
			if (names.contains(",")) {
				criteria.add(nameProperty.in(names.split(",")));
			} else {
				criteria.add(nameProperty.eq(names));
			}
		}
		getPaginationDataByDetachedCriteria(criteria, page);
	}

	public Map<String, String> resourceConfigureDeal(String operType, AppResconfig vo) throws Exception {
		AppResconfigHandler appResconfigHandler = (AppResconfigHandler) ServiceUtils.getBean("appResconfigHandler");
		if (StringUtils.equals(operType, "AddNotification")) {
			saveNotification(vo);
			appResconfigHandler.deal(null);
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		} else if (StringUtils.equals(operType, "ModifyNotification")) {
			updateNotification(vo);
			appResconfigHandler.deal(null);
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		} else if (StringUtils.equals(operType, "AddNote")) {
			saveNote(vo);
			appResconfigHandler.deal(null);
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		} else if (StringUtils.equals(operType, "ModifyNote")) {
			updateNote(vo);
			appResconfigHandler.deal(null);
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		} else if (StringUtils.equals(operType, "AddPopup")) {
			savePopup(vo);
			appResconfigHandler.deal(null);
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		} else if (StringUtils.equals(operType, "ModifyPopup")) {
			updatePopup(vo);
			appResconfigHandler.deal(null);
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
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

	public Map<String, String> savePopup(AppResconfig vo) throws Exception {
		vo.setId(getSequence(null, "unieap"));
		vo.setType("C");
		vo.setName(vo.getId().toString());
		vo.setLanguage(SYSConfig.defaultLanguage);
		vo.setGroupName("message_push_message");
		vo.setPageNum("2");
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		vo.setSeq(vo.getId().toString());
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public Map<String, String> updatePopup(AppResconfig vo) throws Exception {
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

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> resourceConfigurePictureDeal(String parameters, List<FileItem> items) throws Exception {
		JSONObject json = new JSONObject(parameters);
		String id = json.getString("id");
		String activeFlag = json.getString("activeFlag");
		String hyperlink = json.getString("hyperlink");
		AppResconfig appResconfig = DBManager.getHT(null).get(AppResconfig.class, new Integer(id));
		appResconfig.setActiveFlag(activeFlag);
		appResconfig.setHyperlink(hyperlink);
		FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
		String url = "apps/mcare/images/app";
		/*
		 * String uploadPath = fileBO.getRootPath() + "apps" + File.separator +
		 * "mcare" + File.separator + "images" + File.separator + "app";
		 */
		String uploadPath = fileBO.getRootPath() + url;
		fileBO.upload("app configure", id, items, uploadPath, url);
		DBManager.getHT(null).update(appResconfig);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}
}
