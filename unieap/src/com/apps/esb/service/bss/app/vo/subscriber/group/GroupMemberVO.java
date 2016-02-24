package com.apps.esb.service.bss.app.vo.subscriber.group;

import java.util.List;

import com.apps.esb.service.bss.app.vo.subscriber.basicinfo.SubscriberVO;
import com.apps.esb.service.bss.vo.mshop.group.MshopPaymentRelationshipVO;
import com.unieap.base.vo.BaseVO;

public class GroupMemberVO extends BaseVO{
	private String groupId;
	private String groupCode;
	private String groupName;
	private String serviceNumber;
	private String paymentFlag;
	private String paymentFlagDesc;
	private String status;
	private String statusDesc;
	private String primaryOffering;
	private String joinTime;
	private String accountCode;
	private SubscriberVO subscriberVO;
	private List<MshopPaymentRelationshipVO> paymentRelationshipList;
	public String getGroupId() {
		return groupId;
	}
	public String getGroupCode() {
		return groupCode;
	}
	public String getGroupName() {
		return groupName;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public String getPaymentFlag() {
		return paymentFlag;
	}
	public String getStatus() {
		return status;
	}
	public String getPrimaryOffering() {
		return primaryOffering;
	}
	public String getJoinTime() {
		return joinTime;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public SubscriberVO getSubscriberVO() {
		return subscriberVO;
	}
	public List<MshopPaymentRelationshipVO> getPaymentRelationshipList() {
		return paymentRelationshipList;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public void setGroupCode(String groupCode) {
		this.groupCode = groupCode;
	}
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setPrimaryOffering(String primaryOffering) {
		this.primaryOffering = primaryOffering;
	}
	public void setJoinTime(String joinTime) {
		this.joinTime = joinTime;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public void setSubscriberVO(SubscriberVO subscriberVO) {
		this.subscriberVO = subscriberVO;
	}
	public void setPaymentRelationshipList(List<MshopPaymentRelationshipVO> paymentRelationshipList) {
		this.paymentRelationshipList = paymentRelationshipList;
	}
	public String getPaymentFlagDesc() {
		return paymentFlagDesc;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setPaymentFlagDesc(String paymentFlagDesc) {
		this.paymentFlagDesc = paymentFlagDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
}
