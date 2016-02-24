package com.apps.esb.service.bss.app.vo.subscriber.group;

import java.util.List;

import com.apps.esb.service.bss.app.vo.account.basicinfo.AccountVO;
import com.unieap.base.vo.BaseVO;

public class GroupVO extends BaseVO{
	private String groupId;
	private String groupCode;
	private String groupName;
	private String primaryOfferingName;
	private String paymentFlag;
	private String status;
	private String statusDesc;
	private String groupType;
	private String groupTypeDesc;
	private AccountVO accountVO;
	private String maxMemberNumber;
	private String remainingMemberNumber;
	private List<GroupMemberVO> memberList;
	public String getGroupCode() {
		return groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public String getPrimaryOfferingName() {
		return primaryOfferingName;
	}
	public String getPaymentFlag() {
		return paymentFlag;
	}
	public String getStatus() {
		return status;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setPrimaryOfferingName(String primaryOfferingName) {
		this.primaryOfferingName = primaryOfferingName;
	}
	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public AccountVO getAccountVO() {
		return accountVO;
	}
	public void setAccountVO(AccountVO accountVO) {
		this.accountVO = accountVO;
	}
	public List<GroupMemberVO> getMemberList() {
		return memberList;
	}
	public void setMemberList(List<GroupMemberVO> memberList) {
		this.memberList = memberList;
	}
	public String getMaxMemberNumber() {
		return maxMemberNumber;
	}
	public String getRemainingMemberNumber() {
		return remainingMemberNumber;
	}
	public void setMaxMemberNumber(String maxMemberNumber) {
		this.maxMemberNumber = maxMemberNumber;
	}
	public void setRemainingMemberNumber(String remainingMemberNumber) {
		this.remainingMemberNumber = remainingMemberNumber;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getGroupType() {
		return groupType;
	}
	public String getGroupTypeDesc() {
		return groupTypeDesc;
	}
	public void setGroupType(String groupType) {
		this.groupType = groupType;
	}
	public void setGroupTypeDesc(String groupTypeDesc) {
		this.groupTypeDesc = groupTypeDesc;
	}
	
}
