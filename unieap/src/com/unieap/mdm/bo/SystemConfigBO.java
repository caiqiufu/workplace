package com.unieap.mdm.bo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.LoadSystemData;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.mdm.vo.SystemConfigVO;

@Service("systemConfigBO")
public class SystemConfigBO  extends BaseBO{
	public void getConfigList(PaginationSupport page) {
		StringBuffer sql = new StringBuffer();
		sql.append(" SELECT id,type,name,value,description FROM unieap.sys_config");
		sql.append(" where modify_flag='Y' order by name asc ");
		List items = DBManager.getJT(null).query(sql.toString(), new EntityRowMapper(SystemConfigVO.class));
		page.setItems(items);
	}
	
	public Map<String, String> configDeal(String operType, SystemConfigVO vo) throws Exception {
		if (StringUtils.equals(operType, UnieapConstants.MODIFY)) {
			return update(vo);
		}  else {
			throw new Exception(UnieapConstants.getMessage("comm.operation.error", new Object[] { operType }));
		}
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> update(SystemConfigVO vo)
			throws Exception, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		StringBuffer sqlGetOldValue = new StringBuffer();
		sqlGetOldValue.append(" SELECT id,type,name,value,description FROM unieap.sys_config");
		sqlGetOldValue.append(" where id=?");
		Map<String, Object> data = DBManager.getJT(null).queryForMap(sqlGetOldValue.toString(), new Object[]{vo.getId()});
		String oldValue = data.get("value")==null?"":data.get("value").toString();
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getId(),"sys_config",UnieapConstants.MODIFY,"value","value",oldValue,vo.getValue(),"mdm");
		String sql = "update sys_config set value  = ? where id = ?";
		DBManager.getJT(null).update(sql, new Object[]{vo.getValue(),vo.getId()});
		LoadSystemData loadSystemData = (LoadSystemData) ServiceUtils.getBean("loadSystemData");
		loadSystemData.loadSystemConfigure();
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}
}
