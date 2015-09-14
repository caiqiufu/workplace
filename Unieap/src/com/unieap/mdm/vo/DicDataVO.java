package com.unieap.mdm.vo;

import java.util.Date;

import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;

public class DicDataVO {
	private Integer dicId;
	private Integer groupId;
	private String groupName;
	private String dicCode;
	private String dicName;
	private String parentCode;
	private String parentName;
	private Integer seq;
	private Date createDate;
	private Date modifyDate;
	private String modifyBy;
	private String createBy;
	private String href;
	private String remark;
	private String activeFlag;
	private String activeFlagDesc;
	private Integer parentId;
	private String language;
	private String languageDesc;
	public Integer getDicId() {
		return dicId;
	}
	public void setDicId(Integer dicId) {
		this.dicId = dicId;
	}
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getDicCode() {
		return dicCode;
	}
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}
	public String getDicName() {
		return dicName;
	}
	public void setDicName(String dicName) {
		this.dicName = dicName;
	}
	
	public String getParentCode() {
		return parentCode;
	}
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
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
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public String getRemark() {
		return remark;
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
	public void setActiveFlagDesc(String activeFlagDesc) {
		this.activeFlagDesc = activeFlagDesc;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
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
		if(!StringUtils.isEmpty(this.language)){
			DicDataVO dic =  CacheMgt.getDicData("5479",language);
			if(dic!=null){
				this.languageDesc = dic.getDicName();
			}
		}
	}
	public String getLanguageDesc() {
		return languageDesc;
	}
	public void setLanguageDesc(String languageDesc) {
		this.languageDesc = languageDesc;
	}
	
	
	
}
