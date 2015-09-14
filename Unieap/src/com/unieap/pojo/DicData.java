package com.unieap.pojo;

import java.util.Date;

/**
 * DicData entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class DicData implements java.io.Serializable {

	// Fields

	private Integer dicId;
	private Integer groupId;
	private String dicCode;
	private String dicName;
	private Integer parentId;
	private String parentCode;
	private Integer seq;
	private Date createDate;
	private Date modifyDate;
	private String modifyBy;
	private String createBy;
	private String href;
	private String remark;
	private String activeFlag;
	private String language;

	// Constructors

	/** default constructor */
	public DicData() {
	}

	/** minimal constructor */
	public DicData(Integer dicId, Integer groupId, String dicCode,
			String dicName, Integer seq, Date createDate, String createBy,String activeFlag) {
		this.dicId = dicId;
		this.groupId = groupId;
		this.dicCode = dicCode;
		this.dicName = dicName;
		this.seq = seq;
		this.createDate = createDate;
		this.createBy = createBy;
		this.activeFlag = activeFlag;
	}

	/** full constructor */
	public DicData(Integer dicId, Integer groupId, String dicCode,
			String dicName, Integer seq, Date createDate, Date modifyDate,
			String modifyBy, String createBy, String remark,String href,String activeFlag,String parentCode,Integer parentId,String language) {
		this.dicId = dicId;
		this.groupId = groupId;
		this.dicCode = dicCode;
		this.dicName = dicName;
		this.seq = seq;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.createBy = createBy;
		this.href = href;
		this.remark = remark;
		this.activeFlag = activeFlag;
		this.parentCode = parentCode;
		this.parentId =  parentId;
		this.language = language;
	}

	// Property accessors

	public Integer getDicId() {
		return this.dicId;
	}

	public void setDicId(Integer dicId) {
		this.dicId = dicId;
	}

	public Integer getGroupId() {
		return this.groupId;
	}

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public String getDicCode() {
		return this.dicCode;
	}

	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}

	public String getDicName() {
		return this.dicName;
	}

	public void setDicName(String dicName) {
		this.dicName = dicName;
	}

	public Integer getSeq() {
		return this.seq;
	}

	public void setSeq(Integer seq) {
		this.seq = seq;
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

	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getActiveFlag() {
		return activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}

	public String getParentCode() {
		return parentCode;
	}

	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	
	
}