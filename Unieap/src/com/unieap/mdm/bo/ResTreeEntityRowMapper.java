package com.unieap.mdm.bo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import com.unieap.UnieapConstants;
import com.unieap.base.vo.TreeVO;
import com.unieap.db.EntityRowMapper;

public class ResTreeEntityRowMapper extends EntityRowMapper {

	public ResTreeEntityRowMapper(Class object) {
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
		vo.setId(rs.getLong("resId"));
		vo.setLeaf(UnieapConstants.YES.equals(rs.getObject("isLeaf").toString()));
		vo.setText(rs.getObject("resName").toString());
		Map<String,Object> extendAttri = new HashMap<String,Object>();
		extendAttri.put("resId",rs.getObject("resId").toString());
		extendAttri.put("resCode",rs.getObject("resCode").toString());
		extendAttri.put("resName", rs.getObject("resName").toString());
		extendAttri.put("resDataId",rs.getObject("resDataId").toString());
		extendAttri.put("resDefId",rs.getObject("resDefId").toString());
		String assign = rs.getObject("assign").toString();
		extendAttri.put("assigned",UnieapConstants.YES.equals(assign));
		extendAttri.put("resRoleId",rs.getObject("resRoleId"));
		vo.setExtendAttri(extendAttri);
		vo.setExpanded(false);
		return vo;
	}
}
