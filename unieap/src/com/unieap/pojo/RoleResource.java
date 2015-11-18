package com.unieap.pojo;

import java.util.Date;

/**
 * RoleResource entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RoleResource implements java.io.Serializable {

	// Fields

	private Integer roleResourceId;
	private Integer roleId;
	private String resourceId;
	private String resourceType;
	private String category;
	private String resourceAttr1;
	private String resourceAttr2;
	private Date createDate;
	private Date modifyDate;
	private String modifyBy;
	private String createBy;
	private String activeFlag;

	// Constructors

	/** default constructor */
	public RoleResource() {
	}

	/** minimal constructor */
	public RoleResource(Integer roleResourceId, Integer roleId,
			String resourceId, String resourceType,Date createDate, String createBy,String activeFlag) {
		this.roleResourceId = roleResourceId;
		this.roleId = roleId;
		this.resourceId = resourceId;
		this.resourceType = resourceType;
		this.createDate = createDate;
		this.createBy = createBy;
		this.activeFlag = activeFlag;
	}

	/** full constructor */
	public RoleResource(Integer roleResourceId, Integer roleId,
			String resourceId, String resourceType, String category,
			String resourceAttr1, String resourceAttr2,Date createDate, String createBy,String activeFlag,Date modifyDate,
			String modifyBy) {
		this.roleResourceId = roleResourceId;
		this.roleId = roleId;
		this.resourceId = resourceId;
		this.resourceType = resourceType;
		this.category = category;
		this.resourceAttr1 = resourceAttr1;
		this.resourceAttr2 = resourceAttr2;
		this.createDate = createDate;
		this.createBy = createBy;
		this.activeFlag = activeFlag;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
	}

	// Property accessors

	public Integer getRoleResourceId() {
		return this.roleResourceId;
	}

	public void setRoleResourceId(Integer roleResourceId) {
		this.roleResourceId = roleResourceId;
	}

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getResourceId() {
		return this.resourceId;
	}

	public void setResourceId(String resourceId) {
		this.resourceId = resourceId;
	}

	public String getResourceType() {
		return this.resourceType;
	}

	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}

	public String getCategory() {
		return this.category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public String getResourceAttr1() {
		return this.resourceAttr1;
	}

	public void setResourceAttr1(String resourceAttr1) {
		this.resourceAttr1 = resourceAttr1;
	}

	public String getResourceAttr2() {
		return this.resourceAttr2;
	}

	public void setResourceAttr2(String resourceAttr2) {
		this.resourceAttr2 = resourceAttr2;
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