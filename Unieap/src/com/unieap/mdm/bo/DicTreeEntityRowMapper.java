package com.unieap.mdm.bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.unieap.UnieapConstants;
import com.unieap.base.vo.TreeVO;
import com.unieap.db.EntityRowMapper;

public class DicTreeEntityRowMapper extends EntityRowMapper {

	public DicTreeEntityRowMapper(Class object) {
		super(object);
		// TODO Auto-generated constructor stub
	}

	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		return convertMapToResultMap(rs, object);
	}

	public Object convertMapToResultMap(ResultSet rs, Class object)
			throws SQLException {
		Object vo;
		try {
			vo = object.newInstance();
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		return getTreeVo(rs, vo);
	}
	private Object getTreeVo(ResultSet rs, Object bean) throws SQLException {
		TreeVO vo = (TreeVO)bean;
		vo.setId(rs.getLong("dicId"));
		vo.setLeaf(UnieapConstants.YES.equals(rs.getObject("leaf").toString()));
		StringBuffer text = new StringBuffer();
		text.append("[").append(rs.getObject("dicCode").toString()).append("]").append(rs.getObject("dicName").toString());
		vo.setText(text.toString());
		vo.setQtip(vo.getText());
		if(rs.getObject("iconCls")!=null){
			vo.setIconCls(rs.getObject("iconCls").toString());
		}
		Map<String,Object> extendAttri = new HashMap<String,Object>();
		extendAttri.put("dicId",rs.getObject("dicId").toString());
		extendAttri.put("dicCode",rs.getObject("dicCode").toString());
		extendAttri.put("dicName",rs.getObject("dicName").toString());
		if(rs.getObject("parentId")!=null){
			extendAttri.put("parentId",rs.getObject("parentId").toString());
			extendAttri.put("parentCode",rs.getObject("parentCode").toString());
			extendAttri.put("parentName",rs.getObject("parentName").toString());
		}
		if(rs.getObject("seq")!=null){
			extendAttri.put("seq",rs.getObject("seq").toString());
		}
		if(rs.getObject("href")!=null){
			extendAttri.put("href",rs.getObject("href").toString());
		}
		if(rs.getObject("remark")!=null){
			extendAttri.put("remark",rs.getObject("remark").toString());
		}
		extendAttri.put("activeFlag",UnieapConstants.YES.equals(rs.getObject("activeFlag").toString()));
		if(StringUtils.equals(rs.getObject("activeFlag").toString(), UnieapConstants.NO)){
			vo.setIconCls("delete");
		}
		extendAttri.put("createBy",rs.getObject("createBy").toString());
		vo.setExtendAttri(extendAttri);
		vo.setExpanded(false);
		return vo;
	}
}
