package com.unieap.db;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.RowMapper;

import com.unieap.base.vo.ExtTreeVO;

public class TreeEntityRowMapper implements RowMapper<Object> {
	public Class<Object> object;
	public String[] extendAttri;
	public TreeEntityRowMapper(Class<Object> object,String[] extendAttri) {
		this.object = object;
		this.extendAttri = extendAttri;
	}
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		return convertMapToResultMap(rs,object);
	}

	public Object convertMapToResultMap(ResultSet rs, Class<Object> object)
			throws SQLException {
		Object vo;
		try {
			vo = object.newInstance();
		} catch (Exception e) {
			throw new SQLException(e.getMessage());
		}
		return getTreeVo(rs, vo);
	}
	/**
	 * SQL should include fields: id,leaf,text,parentId
	 * If SQL include extend field, please put the extend field in extendAttri
	 * @param rs
	 * @param bean
	 * @return
	 * @throws SQLException
	 */
	private Object getTreeVo(ResultSet rs, Object bean) throws SQLException {
		ExtTreeVO vo = (ExtTreeVO) bean;
		vo.setId(rs.getLong("id"));
		vo.setLeaf(rs.getBoolean("leaf"));
		vo.setText(rs.getString("text"));
		vo.setParentId(rs.getLong("parentId"));
		if(extendAttri!=null){
			Map<String,Object> extendAttriMap = new HashMap<String,Object>();
			for(String extAtt: extendAttri){
				if(StringUtils.equals("checked", extAtt)){
					int val = rs.getInt(extAtt);
					extendAttriMap.put(extAtt,val==1);
				}else{
					if(extAtt.indexOf(":")>0){
						String key = extAtt.split(":")[0];
						String type = extAtt.split(":")[1];
						if(StringUtils.equals(type,"Boolean")){
							extendAttriMap.put(key,rs.getBoolean(key));
						}else if(StringUtils.equals(type,"String")){
							extendAttriMap.put(key,rs.getString(key));
						}else{
							extendAttriMap.put(key,rs.getObject(key));
						}
					}else{
						extendAttriMap.put(extAtt,rs.getObject(extAtt));
					}
				}
			}
			vo.setExtendAttri(extendAttriMap);
		}
		vo.setExpanded(false);
		return vo;
	}
}
