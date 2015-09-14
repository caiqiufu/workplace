package com.unieap.mdm.vo;

import com.unieap.base.vo.BaseVO;

public class UserDicVO extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private Integer userResId;
	private Integer groupId;
	private String groupName;
	private Integer dicId;
	private String dicCode;
	private String dicName;
	private Integer seq;
	private Integer userId;
	private String resAttri1;
	private String resAttri2;
	
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	public String getGroupName() {
		return groupName;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public Integer getDicId() {
		return dicId;
	}
	public void setDicId(Integer dicId) {
		this.dicId = dicId;
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
	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getResAttri1() {
		return resAttri1;
	}
	public void setResAttri1(String resAttri1) {
		this.resAttri1 = resAttri1;
	}
	public String getResAttri2() {
		return resAttri2;
	}
	public void setResAttri2(String resAttri2) {
		this.resAttri2 = resAttri2;
	}
	public Integer getUserResId() {
		return userResId;
	}
	public void setUserResId(Integer userResId) {
		this.userResId = userResId;
	}
	public String getDicCode() {
		return dicCode;
	}
	public void setDicCode(String dicCode) {
		this.dicCode = dicCode;
	}
	
}
