package com.unieap.pojo;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.mdm.vo.DicDataVO;

/**
 * DicGroup entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DicGroup implements java.io.Serializable {

	// Fields

	private Integer groupId;
	private String groupName;
	private Date createDate;
	private Date modifyDate;
	private String createBy;
	private String modifyBy;
	private String remark;
	private String activeFlag;
	private String activeFlagDesc;

	// Constructors

	/** default constructor */
	public DicGroup() {
	}

	/** minimal constructor */
	public DicGroup(Integer groupId, String groupName, Date createDate,
			String createBy,String activeFlag) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.createDate = createDate;
		this.createBy = createBy;
		this.activeFlag = activeFlag;
	}

	/** full constructor */
	public DicGroup(Integer groupId, String groupName, Date createDate,
			Date modifyDate, String createBy, String modifyBy, String remark,String activeFlag) {
		this.groupId = groupId;
		this.groupName = groupName;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.createBy = createBy;
		this.modifyBy = modifyBy;
		this.remark = remark;
		this.activeFlag = activeFlag;
	}

	// Property accessors

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getGroupName() {
		return this.groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
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
			DicDataVO dic =  CacheMgt.getDicData("1001",activeFlag);
			if(dic!=null){
				this.activeFlagDesc = dic.getDicName();
			}
		}
	}

	public String getActiveFlagDesc() {
		return activeFlagDesc;
	}

}