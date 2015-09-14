package com.unieap.pojo;

import java.util.Date;

/**
 * UserRole entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class UserRole implements java.io.Serializable {

	// Fields

	private Integer userRoleId;
	private Integer userId;
	private Integer roleId;
	private Date createDate;
	private Date modifyDate;
	private String modifyBy;
	private String createBy;
	private String activeFlag;

	// Constructors

	/** default constructor */
	public UserRole() {
	}

	/** full constructor */
	public UserRole(Integer userRoleId, Integer userId, Integer roleId,Date createDate, String createBy,String activeFlag) {
		this.userRoleId = userRoleId;
		this.userId = userId;
		this.roleId = roleId;
		this.createDate = createDate;
		this.createBy = createBy;
		this.activeFlag = activeFlag;
	}

	public UserRole(Integer userRoleId, Integer userId, Integer roleId, Date createDate, Date modifyDate,
			String modifyBy, String createBy, String activeFlag) {
		super();
		this.userRoleId = userRoleId;
		this.userId = userId;
		this.roleId = roleId;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.createBy = createBy;
		this.activeFlag = activeFlag;
	}

	// Property accessors

	public Integer getUserRoleId() {
		return this.userRoleId;
	}

	public void setUserRoleId(Integer userRoleId) {
		this.userRoleId = userRoleId;
	}

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

}