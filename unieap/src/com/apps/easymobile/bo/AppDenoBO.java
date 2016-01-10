package com.apps.easymobile.bo;

import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import com.apps.easymobile.pojo.AppDenomination;
import com.apps.esb.service.bss.handler.AppResconfigHandler;
import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.mdm.bo.ChangeLogBO;

@Service("appDenoBO")
public class AppDenoBO extends BaseBO{
	public void getDenoGrid(PaginationSupport page, String type) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(AppDenomination.class);
		getPaginationDataByDetachedCriteria(criteria, page);
	}
	public Map<String, String> denoDeal(String operType, AppDenomination vo) throws Exception {
		AppResconfigHandler appResconfigHandler = (AppResconfigHandler) ServiceUtils.getBean("appResconfigHandler");
		if (StringUtils.equals(operType, "AddDeno")) {
			vo.setType("N");
			save(vo);
			appResconfigHandler.deal(null);
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		} else if (StringUtils.equals(operType, "ModifyDeno")) {
			update(vo);
			appResconfigHandler.deal(null);
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		} else {
			throw new Exception(UnieapConstants.getMessage("comm.operation.error", new Object[] { operType }));
		}
	}
	
	public Map<String, String> save(AppDenomination vo) throws Exception {
		vo.setId(getSequence(null, "unieap"));
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public Map<String, String> update(AppDenomination vo) throws Exception {
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getId(), vo, "appDenomination", UnieapConstants.MODIFY, UnieapConstants.UNIEAP);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

}
