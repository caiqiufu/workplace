package com.unieap.mdm.bo;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
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
import com.unieap.db.EntityRowMapper;
import com.unieap.mdm.vo.DicDataVO;
import com.unieap.pojo.DicDataTree;
import com.unieap.pojo.Role;
import com.unieap.pojo.RoleResource;
import com.unieap.tools.TreeUtils;

@Service("dicBO")
public class DicBO extends BaseBO {
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> save(DicDataTree vo) throws Exception {
		DicDataTree parent = getDic(vo.getParentId());
		vo.setDicId(getSequence(null, UnieapConstants.UNIEAP));
		vo.setParentCode(parent.getDicCode());
		vo.setParentName(parent.getDicName());
		vo.setLanguage(SYSConfig.defaultLanguage);
		vo.setLeaf(UnieapConstants.YES);
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		vo.setTenantId(SYSConfig.getConfig().get("tenantId"));
		DBManager.getHT(null).save(vo);
		parent.setLeaf(UnieapConstants.NO);
		DBManager.getHT(null).update(parent);
		Map<String, String> model = result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		model.put("id", vo.getDicId().toString());
		return model;
	}

	public DicDataTree getDic(Integer dicId) {
		return DBManager.getHT(null).get(DicDataTree.class, dicId);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> update(DicDataTree vo) throws Exception {
		DicDataTree old = getDic(vo.getDicId());
		old.setModifyDate(UnieapConstants.getDateTime(null));
		old.setModifyBy(UnieapConstants.getUser().getUserCode());
		old.setActiveFlag(vo.getActiveFlag());
		old.setDicName(vo.getDicName());
		old.setHref(vo.getHref());
		old.setSeq(vo.getSeq());
		old.setRemark(vo.getRemark());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getDicId(), old, "dic_data_tree", UnieapConstants.MODIFY, UnieapConstants.UNIEAP);
		DBManager.getHT(null).update(old);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> delete(DicDataTree vo) {
		vo = DBManager.getHT(null).get(DicDataTree.class, vo.getDicId());
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		vo.setActiveFlag(UnieapConstants.NO);
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getDicId(), "dic_data_tree", UnieapConstants.MODIFY, "active_flag",
				UnieapConstants.getMessage("comm.activeFlag"), UnieapConstants.YES, UnieapConstants.NO,
				UnieapConstants.UNIEAP);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> dicDataDeal(String operType, DicDataTree vo) throws Exception {
		if (StringUtils.equals(operType, UnieapConstants.ADD)) {
			Map<String, String> result = checkDicCodeExist(vo);
			if (StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(), UnieapConstants.SUCCESS)) {
				saveRoleResource(vo);
				return save(vo);
			} else {
				return result;
			}
		} else if (StringUtils.equals(operType, UnieapConstants.MODIFY)) {
			Map<String, String> result = checkDicCodeExistForUpdate(vo);
			if (StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(), UnieapConstants.SUCCESS)) {
				return update(vo);
			} else {
				return result;
			}
		} else if (StringUtils.equals(operType, UnieapConstants.DELETE)) {
			return delete(vo);
		} else if (StringUtils.equals(operType, UnieapConstants.CHECKEXIST)) {
			return checkExist(UnieapConstants.getMessage("mdm.dic.check.dicCode", new Object[] { vo.getDicCode() }),
					"dicCode", vo.getDicCode(), DicDataTree.class, null);
		} else {
			throw new Exception(UnieapConstants.getMessage("comm.operation.error", new Object[] { operType }));
		}
	}

	public void saveRoleResource(DicDataTree vo) {
		RoleResource rr = new RoleResource();
		rr.setRoleResourceId(getSequence(null, UnieapConstants.UNIEAP));
		rr.setRoleId(Integer.valueOf("1"));
		rr.setResourceId(vo.getDicCode());
		rr.setResourceType(vo.getDicType());
		if (vo.getParentId() != null) {
			rr.setCategory(vo.getParentId().toString());
		}
		rr.setActiveFlag(UnieapConstants.YES);
		rr.setCreateDate(UnieapConstants.getDateTime(null));
		rr.setCreateBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(rr);
	}

	public Map<String, String> checkDicCodeExist(DicDataTree vo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DicDataTree.class);
		Property parentId = Property.forName("parentId");
		criteria.add(parentId.eq(vo.getParentId()));
		Property dicCode = Property.forName("dicCode");
		criteria.add(dicCode.eq(vo.getDicCode()));
		Property language = Property.forName("language");
		criteria.add(language.eq(vo.getLanguage()));
		List<?> resdatas = DBManager.getHT(null).findByCriteria(criteria);
		if (resdatas != null && resdatas.size() > 0) {
			Map<String, String> map = result(UnieapConstants.RETURNMESSAGE,
					UnieapConstants.getMessage("mdm.dic.check.dicCode", new Object[] { vo.getDicCode() }));
			map.put(UnieapConstants.ISSUCCESS, UnieapConstants.FAILED);
			return map;
		} else {
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		}
	}

	public Map<String, String> checkDicCodeExistForUpdate(DicDataTree vo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DicDataTree.class);
		Property parentId = Property.forName("parentId");
		criteria.add(parentId.eq(vo.getParentId()));
		Property dicId = Property.forName("dicId");
		criteria.add(dicId.ne(vo.getDicId()));
		Property dicCode = Property.forName("dicCode");
		criteria.add(dicCode.eq(vo.getDicCode()));
		Property language = Property.forName("language");
		criteria.add(language.eq(vo.getLanguage()));
		List<?> datas = DBManager.getHT(null).findByCriteria(criteria);
		if (datas != null && datas.size() > 0) {
			Map<String, String> map = result(UnieapConstants.RETURNMESSAGE,
					UnieapConstants.getMessage("mdm.dic.check.dicCode", new Object[] { vo.getDicCode() }));
			map.put(UnieapConstants.ISSUCCESS, UnieapConstants.FAILED);
			return map;
		} else {
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		}
	}

	public List<?> getCommDicList(Integer parentCode, String whereby) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select d.dic_id as dicId,d.dic_code as dicCode,d.parent_code as parentCode,");
		sql.append(" d.dic_name as dicName,d.seq as seq from unieap.dic_data_tree d ");
		sql.append(" where d.active_flag = '1' and d.parent_code = ? ");
		if (StringUtils.isNotEmpty(whereby)) {
			sql.append(" and ").append(whereby).append(" ");
		}
		sql.append(" order by seq asc ");
		List<?> datas = DBManager.getJT(null).query(sql.toString(), new Object[] { parentCode },
				new EntityRowMapper(DicDataVO.class));
		return datas;
	}

	public String getDicTreeData(TreeVO treeVO) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select dic_id as dicId, dic_code as dicCode,dic_name as dicName,href,seq ,");
		sql.append("icon as iconCls,active_flag as activeFlag,leaf,parent_id as parentId,");
		sql.append("parent_code as parentCode,parent_name as parentName,");
		sql.append("dic_type as dicType ,remark,create_by as createBy ");
		sql.append("from unieap.dic_data_tree where parent_id =? and language =? order by dic_name");
		List<?> datas = DBManager.getJT(null).query(sql.toString(),
				new Object[] { treeVO.getId(), SYSConfig.defaultLanguage }, new DicTreeEntityRowMapper(TreeVO.class));
		JSONArray jdatas = new JSONArray();
		for (Object value : datas) {
			TreeVO vo = (TreeVO) value;
			JSONObject json = TreeUtils.convertBean2JSON(vo);
			jdatas.put(json);
		}
		return jdatas.toString();
	}

	public void getDicRoleGrid(PaginationSupport page, String dicCode) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT r.role_Id as roleId, r.role_Code as roleCode,r.role_Name as roleName,");
		sql.append("r.create_Date as createDate,r.create_By as createBy,");
		sql.append("r.modify_Date as modifyDate,r.modify_By as modifyBy,r.active_Flag as activeFlag,");
		sql.append("remark FROM role_resource rr, role r where rr.role_id = r.role_id and rr.resource_id = ?");
		List<Object> items = DBManager.getJT(null).query(sql.toString(), new Object[] { dicCode },
				new EntityRowMapper(Role.class));
		page.setItems(items);

	}
}
