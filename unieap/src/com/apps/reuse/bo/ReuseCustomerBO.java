package com.apps.reuse.bo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apps.reuse.pojo.ReuseCustomer;
import com.apps.reuse.pojo.ReuseProduct;
import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.mdm.bo.ChangeLogBO;

@Service("reuseCustomerBO")
public class ReuseCustomerBO extends BaseBO {
	public void getCustomerGrid(PaginationSupport page,ReuseCustomer reuseCustomer,String productCode) throws Exception{
		if(StringUtils.isNotEmpty(productCode)){
			DetachedCriteria criteria=DetachedCriteria.forClass(ReuseProduct.class);
			criteria.add(Restrictions.eq("productCode",productCode));
			List datas = DBManager.getHT(null).findByCriteria(criteria);
			if(datas!=null&&datas.size()>0){
				reuseCustomer.setCustomerId(((ReuseProduct)datas.get(0)).getCustomerId());
			}
		}
		DetachedCriteria criteria=DetachedCriteria.forClass(ReuseCustomer.class);
		setCriteria(criteria,reuseCustomer);
		getPaginationDataByDetachedCriteria(criteria,page);
	}
	//@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW) 
	public Map<String, String> customerDeal(String operType,ReuseCustomer vo) throws Exception{
		if(StringUtils.equals(operType, UnieapConstants.ADD)){
			Map result = checkExist(UnieapConstants.getMessage("reuse.customer.customerCode", new Object[]{vo.getCustomerCode()}, null),"customerCode",vo.getCustomerCode(),ReuseCustomer.class,null);
			if(StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(), UnieapConstants.SUCCESS)){
				//return save(vo);
				//save(vo);
				//ReuseCustomer v2 = new ReuseCustomer(124,"tx test",UnieapConstants.getDateTime(null),"tx4");
				//DBManager.getHT(null).save(v2);
				//ReuseCustomer v3 = new ReuseCustomer(125,"tx test",UnieapConstants.getDateTime(null),"tx5");
				//DBManager.getHT(null).save(v3);
				//vo.setTitle("rrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrrr");
				return save(vo);
			}else{
				return result;
			}
		}else if(StringUtils.equals(operType, UnieapConstants.MODIFY)){
			Map result = checkExistForUpdate(UnieapConstants.getMessage("reuse.customer.customerCode", new Object[]{vo.getCustomerCode()}, null),"customerId",vo.getCustomerId(),"customerCode",vo.getCustomerCode(),ReuseCustomer.class,null);
			if(StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(), UnieapConstants.SUCCESS)){
				return update(vo);
			}else{
				return result;
			}
		}else if(StringUtils.equals(operType, UnieapConstants.DELETE)){
			return delete(vo);
		}else if(StringUtils.equals(operType, UnieapConstants.CHECKEXIST)){
			return checkExist(UnieapConstants.getMessage("reuse.customer.customerCode", new Object[]{vo.getCustomerCode()}, null),"customerCode",vo.getCustomerCode(),ReuseCustomer.class,null);
		}else{
			throw new Exception("Operation type["+operType+"] is wrong.");
		}
	}
	
	public Map<String, String> save(ReuseCustomer vo) throws Exception{
		vo.setCustomerId(getSequence(null,"unieap"));
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> update(ReuseCustomer vo) throws Exception{
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		//changeLogBO.save(vo.getCustomerId(), vo, "reuse_customer",UnieapConstants.REUSE);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public Map<String, String> delete(ReuseCustomer vo){
		deleteById(ReuseCustomer.class,vo.getCustomerId(),null);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	
}
