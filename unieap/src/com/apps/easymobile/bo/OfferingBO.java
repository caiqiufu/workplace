package com.apps.easymobile.bo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.easymobile.pojo.AppOffering;
import com.apps.easymobile.pojo.AppOfferingCategory;
import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.file.bo.FileBO;
import com.unieap.mdm.bo.ChangeLogBO;

@Service("OfferingBO")
public class OfferingBO extends BaseBO {
	public void getOfferingCategoryList(PaginationSupport page, AppOfferingCategory vo) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(AppOfferingCategory.class);
		setCriteria(criteria, vo);
		getPaginationDataByDetachedCriteria(criteria, page);
	}

	public void getOfferingList(PaginationSupport page, AppOffering vo) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(AppOffering.class);
		Property categoryIdProperty = Property.forName("categoryId");
		criteria.add(categoryIdProperty.eq(vo.getCategoryId()));
		getPaginationDataByDetachedCriteria(criteria, page);
	}

	public Map<String, String> offerCategoryDeal(String operType, AppOfferingCategory vo,String parameters,List<FileItem> items) throws Exception {
		JSONObject json = new JSONObject(parameters);
		String id = json.getString("id");
		String categoryType = json.getString("categoryType");
		String categoreName = json.getString("categoreName");
		//String seq = json.getString("seq");
		String categoryDesc = json.getString("categoryDesc");
		//String pictureUrl = json.getString("pictureUrl");
		String priceDesc = json.getString("priceDesc");
		String activeFlag = json.getString("activeFlag");
		//String createDate = json.getString("createDate");
		//String createBy = json.getString("createBy");
		//String modifyDate = json.getString("modifyDate");
		//String modifyBy = json.getString("modifyBy");
		String remark = json.getString("remark");
		//String detailUrl = json.getString("detailUrl");
		String detailHyperlink = json.getString("detailHyperlink");
		//String planUrl = json.getString("planUrl");
		//String planHyperlink = json.getString("planHyperlink");
		//String questionUrl = json.getString("questionUrl");
		//String questionHyperlink = json.getString("questionHyperlink");
		//String noteUrl = json.getString("noteUrl");
		//String noteHyperlink = json.getString("noteHyperlink");
		vo.setActiveFlag(activeFlag);
		vo.setCategoreName(categoreName);
		vo.setCategoryDesc(categoryDesc);
		vo.setCategoryType(categoryType);
		vo.setDetailHyperlink(detailHyperlink);
		if(StringUtils.isNotEmpty(id)){
			vo.setId(Integer.parseInt(id));
		}
		vo.setNoteHyperlink(detailHyperlink);
		//vo.setPictureUrl(pictureUrl);
		vo.setPlanHyperlink(detailHyperlink);
		vo.setPriceDesc(priceDesc);
		vo.setQuestionHyperlink(detailHyperlink);
		vo.setRemark(remark);
		
		FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
		String url = "apps/mcare/images/app";
		/*
		 * String uploadPath = fileBO.getRootPath() + "apps" + File.separator +
		 * "mcare" + File.separator + "images" + File.separator + "app";
		 */
		String uploadPath = fileBO.getRootPath() + url;
		
		if (StringUtils.equals(operType, UnieapConstants.ADD)) {
			vo.setId(getSequence(null, null));
			fileBO.upload("offering category", vo.getId().toString(), items, uploadPath, url);
			return saveOfferingCategory(vo);
		} else if (StringUtils.equals(operType, UnieapConstants.MODIFY)) {
			fileBO.upload("offering category",  vo.getId().toString(), items, uploadPath, url);
			return updateOfferingCategory(vo);
		} else {
			throw new Exception(UnieapConstants.getMessage("comm.operation.error", new Object[] { operType }));
		}
	}
	
	public Map<String, String> offeringDeal(String operType, AppOffering vo) throws Exception {
		if (StringUtils.equals(operType, UnieapConstants.ADD)) {
			return saveOffering(vo);
		} else if (StringUtils.equals(operType, UnieapConstants.MODIFY)) {
			return updateOffering(vo);
		} else {
			throw new Exception(UnieapConstants.getMessage("comm.operation.error", new Object[] { operType }));
		}
	}

	public Map<String, String> saveOfferingCategory(AppOfferingCategory vo) throws Exception {
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public Map<String, String> updateOfferingCategory(AppOfferingCategory vo)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		
		AppOfferingCategory appOfferingCategory = DBManager.getHT(null).get(AppOfferingCategory.class,vo.getId());
		
		appOfferingCategory.setActiveFlag(vo.getActiveFlag());
		appOfferingCategory.setCategoreName(vo.getCategoreName());
		appOfferingCategory.setCategoryDesc(vo.getCategoryDesc());
		appOfferingCategory.setCategoryType(vo.getCategoryType());
		appOfferingCategory.setDetailHyperlink(vo.getDetailHyperlink());
		appOfferingCategory.setNoteHyperlink(vo.getDetailHyperlink());
		appOfferingCategory.setPictureUrl(vo.getPictureUrl());
		appOfferingCategory.setPlanHyperlink(vo.getDetailHyperlink());
		appOfferingCategory.setPriceDesc(vo.getPriceDesc());
		appOfferingCategory.setQuestionHyperlink(vo.getDetailHyperlink());
		appOfferingCategory.setRemark(vo.getRemark());
		
		
		appOfferingCategory.setModifyDate(UnieapConstants.getDateTime(null));
		appOfferingCategory.setModifyBy(UnieapConstants.getUser().getUserCode());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getId(), vo, "appOfferingCategory", UnieapConstants.MODIFY, UnieapConstants.UNIEAP);
		DBManager.getHT(null).update(appOfferingCategory);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}
	
	public Map<String, String> saveOffering(AppOffering vo) throws Exception {
		vo.setId(getSequence(null, null));
		vo.setEffectiveType("0");
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public Map<String, String> updateOffering(AppOffering vo)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getId(), vo, "appOffering", UnieapConstants.MODIFY, UnieapConstants.UNIEAP);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

}
