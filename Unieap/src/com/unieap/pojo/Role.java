package com.unieap.pojo;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.mdm.vo.DicDataVO;

/**
 * Role entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class Role implements java.io.Serializable {

	// Fields

	private Integer roleId;
	private String roleCode;
	private String roleName;
	private Date createDate;
	private String createBy;
	private Date modifyDate;
	private String modifyBy;
	private String remark;
	private String activeFlag;
	private String activeFlagDesc;

	// Constructors

	/** default constructor */
	public Role() {
	}

	/** minimal constructor */
	public Role(Integer roleId, String roleCode, String roleName,
			Date createDate, String createBy,String activeFlag) {
		this.roleId = roleId;
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.createDate = createDate;
		this.createBy = createBy;
		this.activeFlag = activeFlag;
	}

	/** full constructor */
	public Role(Integer roleId, String roleCode, String roleName,
			Date createDate, String createBy, Date modifyDate,
			String modifyBy, String remark,String activeFlag) {
		this.roleId = roleId;
		this.roleCode = roleCode;
		this.roleName = roleName;
		this.createDate = createDate;
		this.createBy = createBy;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.remark = remark;
		this.activeFlag = activeFlag;
	}

	// Property accessors

	public Integer getRoleId() {
		return this.roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public String getRoleCode() {
		return this.roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getRoleName() {
		return this.roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
		if(!StringUtils.isEmpty(this.activeFlag)){
			DicDataVO dic =  CacheMgt.getDicData("activeFlag",activeFlag);
			if(dic!=null){
				this.activeFlagDesc = dic.getDicName();
			}
		}
	}
	public String getActiveFlagDesc() {
		return activeFlagDesc;
	}

}