package com.unieap.mdm.vo;

import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;

public class DicDataVO {
	private String groupCode;
	private String groupName;
	private String dicCode;
	private String dicName;
	private Integer seq;
	private String activeFlag;
	private String activeFlagDesc;
	
	public String getGroupCode() {
		return groupCode;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
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
	public Integer getSeq() {
		return seq;
	}
	public void setSeq(Integer seq) {
		this.seq = seq;
	}
	public String getActiveFlag() {
		return activeFlag;
	}
	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
	}
	public String getActiveFlagDesc() {
		if(!StringUtils.isEmpty(this.activeFlag)){
			DicDataVO dic =  CacheMgt.getDicData("activeFlag",activeFlag);
			if(dic!=null){
				this.activeFlagDesc = dic.getDicName();
			}
		}
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
}
