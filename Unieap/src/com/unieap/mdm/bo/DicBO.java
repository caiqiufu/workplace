package com.unieap.mdm.bo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
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
import com.unieap.mdm.vo.DicResourceVO;
import com.unieap.pojo.Area;
import com.unieap.pojo.DicData;
import com.unieap.pojo.DicDataTree;
import com.unieap.pojo.DicGroup;
import com.unieap.pojo.Role;
import com.unieap.tools.TreeUtils;

@Service("dicBO")
public class DicBO extends BaseBO {
	public void getDicGroupList(PaginationSupport page, DicGroup dicGroup) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(DicGroup.class);
		if (!StringUtils.equals(UnieapConstants.getUser().getUserCode(), UnieapConstants.UNIEAP)) {
			criteria.add(Restrictions.eq("createBy", UnieapConstants.getUser().getUserCode()));
		}
		setCriteria(criteria, dicGroup);
		getPaginationDataByDetachedCriteria(criteria, page);
	}

	/*
	 * public Map<String, String> dicGroupDeal(String operType,DicGroup vo)
	 * throws Exception{ if(StringUtils.equals(operType, UnieapConstants.ADD)){
	 * Map result =
	 * checkExist(UnieapConstants.getMessage("mdm.dic.check.groupName", new
	 * Object[]{vo.getGroupName()}),"groupName",vo.getGroupName(),DicGroup.class
	 * ,null);
	 * if(StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(),
	 * UnieapConstants.SUCCESS)){ return save(vo); }else{ return result; } }else
	 * if(StringUtils.equals(operType, UnieapConstants.MODIFY)){ Map result =
	 * checkExistForUpdate(UnieapConstants.getMessage("mdm.dic.check.groupName",
	 * new
	 * Object[]{vo.getGroupName()}),"groupId",vo.getGroupId(),"groupName",vo.
	 * getGroupName(),DicGroup.class,null);
	 * if(StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(),
	 * UnieapConstants.SUCCESS)){ return update(vo); }else{ return result; }
	 * }else if(StringUtils.equals(operType, UnieapConstants.DELETE)){ return
	 * delete(vo); }else if(StringUtils.equals(operType,
	 * UnieapConstants.CHECKEXIST)){ return
	 * checkExist(UnieapConstants.getMessage("mdm.dic.check.groupName", new
	 * Object[]{vo.getGroupName()}),"groupName",vo.getGroupName(),DicGroup.class
	 * ,null); }else{ throw new Exception("Operation type["+operType+
	 * "] is wrong."); } }
	 */
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> save(DicDataTree vo) throws Exception {
		DicDataTree parent = getDic(vo.getParentId());
		vo.setDicId(getSequence(null, "unieap"));
		vo.setParentCode(parent.getDicCode());
		vo.setParentName(parent.getDicName());
		vo.setLanguage(SYSConfig.defaultLanguage);
		vo.setLeaf(UnieapConstants.YES);
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(vo);
		parent.setLeaf(UnieapConstants.NO);
		DBManager.getHT(null).update(parent);
		Map model = result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		model.put("id", vo.getDicId().intValue());
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
		changeLogBO.save(vo.getDicId(), old, "dic_data_tree", UnieapConstants.UNIEAP);
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
		changeLogBO.save(vo.getDicId(), "dic_data_tree", "active_flag", "activeFlag", UnieapConstants.YES,
				UnieapConstants.NO, UnieapConstants.UNIEAP);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public void getDicDataList(PaginationSupport page, DicDataVO vo) throws Exception {

		StringBuffer sb = new StringBuffer();
		sb.append(
				"SELECT d1.dic_id as dicId,d1.group_id as groupId, g.group_name as groupName,d1.dic_code as dicCode,d1.dic_name as dicName,d1.parent_code as parentCode,");
		sb.append(
				"d2.dic_name as parentName,d2.dic_id as parentId,d1.seq as seq,d1.create_date as createDate,d1.modify_date as modifyDate, ");
		sb.append(
				"d1.modify_by as modifyBy,d1.create_by as createBy,d1.href as href,d1.remark as remark,d1.active_flag as activeFlag,d1.language as language ");
		sb.append(
				"FROM unieap.dic_data d1 left join unieap.dic_data d2 on d1.parent_code = d2.dic_code,unieap.dic_group g where d1.group_id = g.group_id and d1.group_id = ? order by d1.dic_name ");
		/*
		 * sb.append(
		 * "SELECT d1.dic_id as dicId,d1.group_id as groupId,d1.dic_code as dicCode,d1.dic_name as dicName,d1.parent_code as parentId,"
		 * ); sb.append(
		 * "d2.dic_name as parentName,d1.seq as seq,d1.create_date as createDate,d1.modify_date as modifyDate, "
		 * ); sb.append(
		 * "d1.modify_by as modifyBy,d1.create_by as createBy,d1.href as href,d1.remark as remark,d1.active_flag as activeFlag "
		 * ); sb.append(
		 * "FROM unieap.dic_data d1 left  join unieap.dic_data d2 on d1.parent_code = d2.dic_id "
		 * ); sb.append(
		 * "where d1.dic_id>=(SELECT d1.dic_id FROM unieap.dic_data d1 left  join unieap.dic_data d2 on d1.parent_code = d2.dic_id "
		 * ); sb.append(
		 * "where d1.group_id = ? order by d1.dic_code limit 10,1) limit 10");
		 */

		StringBuffer sbTotal = new StringBuffer();
		sbTotal.append(
				"SELECT count(1) as total FROM unieap.dic_data d1 left join unieap.dic_data d2 on d1.parent_code = d2.dic_code where d1.group_id = ? ");
		Object[] paramters = new Object[] { vo.getGroupId() };
		getPaginationDataByMysql(vo.getClass(), sb.toString(), sbTotal.toString(), paramters, page);
	}

	public Map<String, String> dicDataDeal(String operType, DicDataTree vo) throws Exception {
		if (StringUtils.equals(operType, UnieapConstants.ADD)) {
			Map result = checkDicCodeExist(vo);
			if (StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(), UnieapConstants.SUCCESS)) {
				return save(vo);
			} else {
				return result;
			}
		} else if (StringUtils.equals(operType, UnieapConstants.MODIFY)) {
			Map result = checkDicCodeExistForUpdate(vo);
			if (StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(), UnieapConstants.SUCCESS)) {
				return update(vo);
			} else {
				return result;
			}
		} else if (StringUtils.equals(operType, UnieapConstants.DELETE)) {
			return delete(vo);
		} else if (StringUtils.equals(operType, UnieapConstants.CHECKEXIST)) {
			return checkExist(UnieapConstants.getMessage("mdm.dic.check.dicCode", new Object[] { vo.getDicCode() }),
					"dicCode", vo.getDicCode(), DicData.class, null);
		} else {
			throw new Exception("Operation type[" + operType + "] is wrong.");
		}
	}

	public Map<String, String> checkDicCodeExist(DicDataTree vo) {
		DetachedCriteria criteria = DetachedCriteria.forClass(DicData.class);
		Property parentId = Property.forName("parentId");
		criteria.add(parentId.eq(vo.getParentId()));
		Property dicCode = Property.forName("dicCode");
		criteria.add(dicCode.eq(vo.getDicCode()));
		Property language = Property.forName("language");
		criteria.add(language.eq(vo.getLanguage()));
		List resdatas = DBManager.getHT(null).findByCriteria(criteria);
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
		DetachedCriteria criteria = DetachedCriteria.forClass(DicData.class);
		Property parentId = Property.forName("parentId");
		criteria.add(parentId.eq(vo.getParentId()));
		Property dicId = Property.forName("dicId");
		criteria.add(dicId.ne(vo.getDicId()));
		Property dicCode = Property.forName("dicCode");
		criteria.add(dicCode.eq(vo.getDicCode()));
		Property language = Property.forName("language");
		criteria.add(language.eq(vo.getLanguage()));
		List datas = DBManager.getHT(null).findByCriteria(criteria);
		if (datas != null && datas.size() > 0) {
			Map<String, String> map = result(UnieapConstants.RETURNMESSAGE,
					UnieapConstants.getMessage("mdm.dic.check.dicCode", new Object[] { vo.getDicCode() }));
			map.put(UnieapConstants.ISSUCCESS, UnieapConstants.FAILED);
			return map;
		} else {
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		}
	}

	public Map<String, String> save(DicData vo) throws Exception {
		vo.setDicId(getSequence(null, "unieap"));
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		vo.setActiveFlag(UnieapConstants.YES);
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> update(DicData vo)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getDicId(), vo, "dic_data", UnieapConstants.REUSE);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> delete(DicData vo) {
		vo = DBManager.getHT(null).get(DicData.class, vo.getDicId());
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		vo.setActiveFlag(UnieapConstants.NO);
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getDicId(), "dic_data", "activeFlag", "activeFlag", UnieapConstants.YES, UnieapConstants.NO,
				UnieapConstants.REUSE);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public Map<String, String> getParentName(Integer dicId) {
		DicData dicData = DBManager.getHT(null).get(DicData.class, dicId);
		Map<String, String> result = result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		if (dicData == null) {
			result.put("parentCode", "");
			result.put("parentName", "");
			result.put(UnieapConstants.ISSUCCESS, UnieapConstants.FAILED);
		} else {
			result.put("parentCode", dicData.getDicCode());
			result.put("parentName", dicData.getDicName());
		}
		return result;
	}

	public List<?> getCommDicList(Integer groupId, String whereby) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				" select d.dic_id as dicId,d.dic_code as dicCode,d.parent_code as parentCode, d.dic_name as dicName,g.group_id as groupId,g.group_name as groupName,d.seq as seq ");
		sql.append(" from unieap.dic_data d,unieap.dic_group g ");
		sql.append(" where d.group_id =g.group_id and d.active_flag = '1' and d.group_id = ? ");
		if (StringUtils.isNotEmpty(whereby)) {
			sql.append(" and ").append(whereby).append(" ");
		}
		sql.append(" order by seq asc ");
		List<?> datas = DBManager.getJT(null).query(sql.toString(), new Object[] { groupId },
				new EntityRowMapper(DicDataVO.class));
		return datas;
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

	public String getDicTreeData(TreeVO treeVO) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"select dic_id as dicId, dic_code as dicCode,dic_name as dicName,href,seq , icon as iconCls,active_flag as activeFlag,leaf,parent_id as parentId,parent_code as parentCode,parent_name as parentName,remark,create_by as createBy from unieap.dic_data_tree where parent_id =? and language =? order by dic_name");
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
		sql.append(
				"SELECT r.role_Id as roleId, r.role_Code as roleCode,r.role_Name as roleName,r.create_Date as createDate,r.create_By as createBy,r.modify_Date as modifyDate,r.modify_By as modifyBy,r.active_Flag as activeFlag, remark FROM role_resource rr, role r where rr.role_id = r.role_id and rr.resource_id = ?");
		List items = DBManager.getJT(null).query(sql.toString(),new Object[]{dicCode},new EntityRowMapper(Role.class));
		page.setItems(items);
		
	}
}
