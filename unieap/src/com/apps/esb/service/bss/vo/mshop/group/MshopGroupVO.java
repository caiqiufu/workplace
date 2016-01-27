package com.apps.esb.service.bss.vo.mshop.group;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class MshopGroupVO extends BaseVO{
	private String groupId;
	private String groupCode;
	private String groupType;
	private String groupName;
	private String primaryOffering;
	private String status;
	private String maxNumber;
	private String remainingNumber;
	private String accountCode;
	private List<MshopGroupMemberVO> memberList;
	public String getGroupCode() {
		return groupCode;
	}
	public String getGroupType() {
		return groupType;
	}
	public String getGroupName() {
		return groupName;
	}
	public String getPrimaryOffering() {
		return primaryOffering;
	}
	public String getStatus() {
		return status;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setPrimaryOffering(String primaryOffering) {
		this.primaryOffering = primaryOffering;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getMaxNumber() {
		return maxNumber;
	}
	public String getRemainingNumber() {
		return remainingNumber;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public void setMaxNumber(String maxNumber) {
		this.maxNumber = maxNumber;
	}
	public void setRemainingNumber(String remainingNumber) {
		this.remainingNumber = remainingNumber;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public List<MshopGroupMemberVO> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<MshopGroupMemberVO> memberList) {
		this.memberList = memberList;
	}
	
}
