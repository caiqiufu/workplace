package com.unieap.mdm.bo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.base.vo.TreeVO;
import com.unieap.db.DBManager;
import com.unieap.pojo.Role;
import com.unieap.pojo.RoleResource;
import com.unieap.tools.TreeUtils;

@Service("roleBO")
public class RoleBO extends BaseBO {
	public void getRoleList(PaginationSupport page, Role vo) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(Role.class);
		setCriteria(criteria, vo);
		getPaginationDataByDetachedCriteria(criteria, page);
	}

	public Map<String, String> roleDeal(String operType, Role vo) throws Exception {
		if (StringUtils.equals(operType, UnieapConstants.ADD)) {
			Map result = checkExist(
					UnieapConstants.getMessage("mdm.role.check.roleCode", new Object[] { vo.getRoleCode() }),
					"roleCode", vo.getRoleCode(), Role.class, null);
			if (StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(), UnieapConstants.SUCCESS)) {
				return save(vo);
			} else {
				return result;
			}
		} else if (StringUtils.equals(operType, UnieapConstants.MODIFY)) {
			Map result = checkExistForUpdate(
					UnieapConstants.getMessage("mdm.role.check.roleCode", new Object[] { vo.getRoleCode() }), "roleId",
					vo.getRoleId(), "roleCode", vo.getRoleCode(), Role.class, null);
			if (StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(), UnieapConstants.SUCCESS)) {
				return update(vo);
			} else {
				return result;
			}
		} else if (StringUtils.equals(operType, UnieapConstants.DELETE)) {
			return delete(vo);
		} else if (StringUtils.equals(operType, UnieapConstants.CHECKEXIST)) {
			return checkExist(UnieapConstants.getMessage("mdm.role.check.roleCode", new Object[] { vo.getRoleCode() }),
					"roleCode", vo.getRoleCode(), Role.class, null);
		} else {
			throw new Exception(UnieapConstants.getMessage("comm.operation.error", new Object[] { operType }));
		}
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> assignDicResource(String datas, Role vo) throws Exception {
		String deleteSql = "delete from role_resource where role_id = ?";
		DBManager.getJT(null).update(deleteSql, new Object[] { vo.getRoleId() });
		JSONArray datasArray = new JSONArray(datas);
		if (datasArray != null && datasArray.length() > 0) {
			JSONObject json = null;
			String parentId = null;
			String dicCode = null;
			String dicType = null;
			RoleResource rr;
			List<RoleResource> rrs = new ArrayList<RoleResource>();
			for (int i = 0; i < datasArray.length(); i++) {
				rr = new RoleResource();
				json = (JSONObject) datasArray.get(i);
				parentId = json.get("parentId").toString();
				dicCode = json.get("dicCode").toString();
				dicType = json.get("dicType").toString();
				rr.setRoleResourceId(getSequence(null,UnieapConstants.UNIEAP));
				rr.setRoleId(vo.getRoleId());
				rr.setResourceId(dicCode);
				rr.setResourceType(dicType);
				rr.setCategory(parentId);
				rr.setActiveFlag(UnieapConstants.YES);
				rr.setCreateDate(UnieapConstants.getDateTime(null));
				rr.setCreateBy(UnieapConstants.getUser().getUserCode());
				rrs.add(rr);
			}
			DBManager.getHT(null).saveOrUpdateAll(rrs);
		}
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public Map<String, String> save(Role vo) throws Exception {
		vo.setRoleId(getSequence(null,UnieapConstants.UNIEAP));
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public Map<String, String> update(Role vo)
			throws Exception, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		Role role = DBManager.getHT(null).get(Role.class, vo.getRoleId());
		role.setRoleCode(vo.getRoleCode());
		role.setRoleName(vo.getRoleName());
		role.setRemark(vo.getRemark());
		role.setModifyDate(UnieapConstants.getDateTime(null));
		role.setModifyBy(UnieapConstants.getUser().getUserCode());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(role.getRoleId(), role, "role", UnieapConstants.MODIFY, UnieapConstants.UNIEAP);
		DBManager.getHT(null).update(role);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public Map<String, String> delete(Role vo) {
		vo = DBManager.getHT(null).get(Role.class, vo.getRoleId());
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		vo.setActiveFlag(UnieapConstants.NO);
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getRoleId(), "role", UnieapConstants.MODIFY, "active_flag",
				UnieapConstants.getMessage("comm.activeFlag"), UnieapConstants.YES, UnieapConstants.NO,
				UnieapConstants.UNIEAP);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public Map<String, String> deleteRoleResource(String operType, RoleResource vo) throws Exception {
		deleteById(RoleResource.class, vo.getRoleResourceId(), null);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public String getRoleDicTreeData(TreeVO treeVO, int roleId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select dic_id as dicId, dic_code as dicCode,dic_name as dicName,href,seq ,");
		sql.append(" icon as iconCls,active_flag as activeFlag,leaf,parent_id as parentId,");
		sql.append(" parent_code as parentCode,parent_name as parentName,dic_type as dicType,remark, create_by as createBy");
		sql.append(" from unieap.dic_data_tree where parent_id =? and language =? order by dic_name");
		List<?> datas = DBManager.getJT(null).query(sql.toString(),
				new Object[] { treeVO.getId(), SYSConfig.defaultLanguage }, new DicTreeEntityRowMapper(TreeVO.class));
		StringBuffer sqlRoleDic = new StringBuffer();
		sqlRoleDic.append("select resource_id from role_resource where role_id = ?");
		List<String> roleDicdatas = DBManager.getJT(null).queryForList(sqlRoleDic.toString(), new Object[] { roleId },
				String.class);
		JSONArray jdatas = new JSONArray();
		for (Object value : datas) {
			TreeVO vo = (TreeVO) value;
			vo.setCheckBoxTree(true);
			if (roleDicdatas.contains(vo.getExtendAttri().get("dicCode").toString())) {
				vo.setChecked(true);
			} else {
				vo.setChecked(false);
			}
			JSONObject json = TreeUtils.convertBean2JSON(vo);
			jdatas.put(json);
		}
		return jdatas.toString();
	}
}
