package com.unieap.mdm.vo;

import java.util.Date;
import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;

import com.unieap.db.DBManager;
import com.unieap.pojo.User;

public class RoleVO1 {
	private Integer roleId;
	private String roleCode;
	private String roleName;
	private Date createDate;
	private String createBy;
	private Date modifyDate;
	private String modifyBy;
	private String remark;
	private String modifyByName;
	private String createByName;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleCode() {
		return roleCode;
	}
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getCreateBy() {
		return createBy;
	}
	public void setCreateBy(String createBy) {
		this.createBy = createBy;
		Property userCode = Property.forName("userCode");
		DetachedCriteria criteria=DetachedCriteria.forClass(com.unieap.pojo.User.class).add(userCode.eq(createBy));
		List<com.unieap.pojo.User> users = DBManager.getHT(null).findByCriteria(criteria);
		if(users!=null && users.size()>0){
			User user = users.get(0);
			this.createByName = user.getUserName();
		}
	}
	public Date getModifyDate() {
		return modifyDate;
	}
	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}
	public String getModifyBy() {
		return modifyBy;
	}
	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
		Property userCode = Property.forName("userCode");
		DetachedCriteria criteria=DetachedCriteria.forClass(com.unieap.pojo.User.class).add(userCode.eq(modifyBy));
		List<com.unieap.pojo.User> users = DBManager.getHT(null).findByCriteria(criteria);
		if(users!=null && users.size()>0){
			User user = users.get(0);
			this.modifyByName = user.getUserName();
		}
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getModifyByName() {
		return modifyByName;
	}
	public void setModifyByName(String modifyByName) {
		this.modifyByName = modifyByName;
	}
	public String getCreateByName() {
		return createByName;
	}
	public void setCreateByName(String createByName) {
		this.createByName = createByName;
	}
	
}
