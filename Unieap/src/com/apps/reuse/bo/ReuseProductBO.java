package com.apps.reuse.bo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apps.reuse.pojo.Area;
import com.apps.reuse.pojo.ReuseCustomer;
import com.apps.reuse.pojo.ReuseProduct;
import com.apps.reuse.pojo.ReuseProductTracking;
import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.mdm.bo.ChangeLogBO;
import com.unieap.pojo.DicDataTree;

@Service("reuseProductBO")
public class ReuseProductBO extends BaseBO {
	public void getProductGrid(PaginationSupport page,ReuseProduct reuseProduct) throws Exception{
		DetachedCriteria criteria=DetachedCriteria.forClass(ReuseProduct.class);
		setCriteria(criteria,reuseProduct);
		getPaginationDataByDetachedCriteria(criteria,page);
	}
	public Map<String, String> productDeal(String operType,ReuseProduct vo) throws Exception{
		if(StringUtils.equals(operType, UnieapConstants.ADD)){
			Map result = checkExist(UnieapConstants.getMessage("reuse.product.productCode", new Object[]{vo.getProductCode()}, null),"productCode",vo.getProductCode(),ReuseProduct.class,null);
			if(StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(), UnieapConstants.SUCCESS)){
				return save(vo);
			}else{
				return result;
			}
		}else if(StringUtils.equals(operType, UnieapConstants.MODIFY)){
			Map result = checkExistForUpdate(UnieapConstants.getMessage("reuse.product.productCode", new Object[]{vo.getProductCode()}, null),"productId",vo.getProductId(),"productCode",vo.getProductCode(),ReuseProduct.class,null);
			if(StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(), UnieapConstants.SUCCESS)){
				return update(vo);
			}else{
				return result;
			}
		}else if(StringUtils.equals(operType, UnieapConstants.DELETE)){
			return delete(vo);
		}else{
			throw new Exception("Operation type["+operType+"] is wrong.");
		}
	}
	
	public Map<String, String> save(ReuseProduct vo) throws Exception{
		vo.setProductId(getSequence(null,"unieap"));
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> update(ReuseProduct vo) throws Exception{
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		//changeLogBO.save(vo.getProductId(), vo, "reuse_product",UnieapConstants.REUSE);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW) 
	public Map<String, String> updateStatus(ReuseProduct vo,String referNo) throws Exception{
		ReuseProduct newOld = DBManager.getHT(null).get(ReuseProduct.class,vo.getProductId());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		//changeLogBO.save(vo.getProductId(), "reuse_product", "status", "status", newOld.getStatus(), vo.getStatus(), UnieapConstants.REUSE);
		newOld.setModifyDate(UnieapConstants.getDateTime(null));
		newOld.setModifyBy(UnieapConstants.getUser().getUserCode());
		newOld.setStatus(vo.getStatus());
		DBManager.getHT(null).update(newOld);
		saveTracking(vo,referNo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public void saveTracking(ReuseProduct vo,String referNo){
		ReuseProductTracking tracking = new ReuseProductTracking();
		tracking.setTrackingId(getSequence(null,"unieap"));
		tracking.setProductId(vo.getProductId());
		tracking.setStatus(vo.getStatus());
		tracking.setReferNo(referNo);
		tracking.setRemark(vo.getRemark());
		tracking.setCreateDate(UnieapConstants.getDateTime(null));
		tracking.setCreateBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(tracking);
	}
	public Map<String, String> delete(ReuseProduct vo){
		deleteById(ReuseCustomer.class,vo.getCustomerId(),null);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public String getModel(Integer groupId, Integer parentId) throws Exception{
		DetachedCriteria criteria=DetachedCriteria.forClass(DicDataTree.class);
		Property groupIdPro = Property.forName("groupId");
		Property parentIdPro = Property.forName("parentId");
		criteria.add(groupIdPro.eq(groupId));
		criteria.add(parentIdPro.eq(parentId));
		List<Object> datas = DBManager.getHT(null).findByCriteria(criteria);
		JSONArray ja = new JSONArray();
		DicDataTree dicData;
		if(datas!=null&&datas.size()>0){
			for(int i = 0; i< datas.size() ; i++){
				dicData = (DicDataTree)datas.get(i);
				JSONObject jac = new JSONObject();
				jac.put("dicId", dicData.getDicId());
				jac.put("dicName", dicData.getDicName());
				jac.put("groupName","");
				ja.put(jac);
			}
		}
		String dicString = ja.toString();
		return dicString;
	}
	public void getProductTrackingGrid(PaginationSupport page,ReuseProductTracking reuseProductTracking) throws Exception{
		DetachedCriteria criteria=DetachedCriteria.forClass(ReuseProductTracking.class);
		setCriteria(criteria,reuseProductTracking);
		getPaginationDataByDetachedCriteria(criteria,page);
	}
	
	public List<Area> getAddressDicData(Area area) {
		DetachedCriteria criteria = DetachedCriteria.forClass(Area.class);
		Property code = Property.forName("code");
		Property level = Property.forName("level");
		if (StringUtils.isEmpty(area.getCode()) && area.getLevel().intValue() != 1) {
			return null;
		} else if (area.getLevel() == null) {
			return null;
		} else if (StringUtils.isNotEmpty(area.getCode())) {
			String codestr = StringUtils.substring(area.getCode(), 0, 2 * (area.getLevel().intValue() - 1));
			if (StringUtils.isNotEmpty(codestr)) {
				criteria.add(code.like(codestr, MatchMode.START));
			}
			criteria.add(level.eq(area.getLevel()));
		} else {
			criteria.add(level.eq(area.getLevel()));
		}
		List<Area> datas = DBManager.getHT(null).findByCriteria(criteria);
		return datas;
	}
	
}
