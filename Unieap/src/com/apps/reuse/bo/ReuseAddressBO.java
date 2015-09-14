package com.apps.reuse.bo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apps.reuse.pojo.ReuseCustomerAddress;
import com.apps.reuse.vo.ReuseCustomerAddressVO;
import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.mdm.bo.ChangeLogBO;

@Service("reuseAddressBO")
public class ReuseAddressBO extends BaseBO {
	public void getAddressGrid(PaginationSupport page,ReuseCustomerAddressVO vo) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append(" select ca.address_id as addressId,ca.customer_id as customerId,ca.type,ca.default_flag as defaultFlag,ca.province as province,");
		sb.append(" a1.name as provinceDesc,ca.city as city,a2.name as cityDesc,ca.district as district,a3.name as districtDesc,");
		sb.append(" ca.street as street,a4.name as streetDesc,ca.postcode as postcode,ca.contact,ca.address1,ca.address2,ca.create_date as createDate,");
		sb.append(" ca.create_by as createBy,ca.modify_date as modifyDate,ca.modify_by as modifyBy,ca.phone ");
		sb.append(" from unieap.reuse_customer_address ca, unieap.area a1,unieap.area a2,unieap.area a3,unieap.area a4 ");
		sb.append(" where ca.province = a1.code and ca.city = a2.code and ca.district = a3.code and ca.street = a4.code and ca.customer_id = ? ");
		sb.append(" union all ");
		sb.append(" select ca.address_id as addressId,ca.customer_id as customerId,ca.type,ca.default_flag as defaultFlag,ca.province as province, ");
		sb.append(" a1.name as provinceDesc,ca.city as city,a2.name as cityDesc,ca.district as district,a3.name as districtDesc, ");
		sb.append(" ca.street as street,'' as streetDesc,ca.postcode as postcode,ca.contact,ca.address1,ca.address2,ca.create_date as createDate, ");
		sb.append(" ca.create_by as createBy,ca.modify_date as modifyDate,ca.modify_by as modifyBy,ca.phone ");
		sb.append(" from unieap.reuse_customer_address ca, unieap.area a1,unieap.area a2,unieap.area a3 ");
		sb.append(" where ca.province = a1.code and ca.city = a2.code and ca.district = a3.code and ca.street = '' and ca.customer_id = ? ");
		List<?> items =  DBManager.getJT(null).query(sb.toString(), new Object[]{vo.getCustomerId(),vo.getCustomerId()}, new EntityRowMapper(ReuseCustomerAddressVO.class));
		page.setItems(items);

	}
	public Map<String, String> addressDeal(String operType,ReuseCustomerAddress vo) throws Exception{
		if(StringUtils.equals(operType, UnieapConstants.ADD)){
			return save(vo);
		}else if(StringUtils.equals(operType, UnieapConstants.MODIFY)){
			return update(vo);
		}else if(StringUtils.equals(operType, UnieapConstants.DELETE)){
			return delete(vo);
		}else if(StringUtils.equals(operType, "UpdateDefaultFlag")){
			updateAddressDefaultFlag(vo.getAddressId(),vo.getCustomerId());
			return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
		}else{
			throw new Exception("Operation type["+operType+"] is wrong.");
		}
	}
	
	public Map<String, String> save(ReuseCustomerAddress vo) throws Exception{
		vo.setAddressId(getSequence(null,"unieap"));
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> update(ReuseCustomerAddress vo) throws Exception{
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getAddressId(), vo, "reuse_customer_address",UnieapConstants.REUSE);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW) 
	public void updateAddressDefaultFlag(Integer addressId,Integer customerId){
		String sql0 = "update unieap.reuse_customer_address ca set ca.default_flag = '0' where ca.customer_id = ? and ca.address_id <> ? ";
		String sql1 = "update unieap.reuse_customer_address ca set ca.default_flag = '1' where ca.customer_id = ? and ca.address_id = ? ";
		DBManager.getJT(null).update(sql0,new Object[]{customerId,addressId});
		DBManager.getJT(null).update(sql1,new Object[]{customerId,addressId});
		
	}
	public Map<String, String> delete(ReuseCustomerAddress vo){
		deleteById(ReuseCustomerAddress.class,vo.getAddressId(),null);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
}
