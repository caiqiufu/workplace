package com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo;

import java.util.List;

public class PrimaryOfferingVO {
	private OfferingIdVO offeringIdVO;
	private OfferingIdVO parentOfferingIdVO;
	private String offeringName;
	private String bundleFlag;
	private String trialStartDate;
	private String trialEndDate;
	private String amount;
	private String status;
	private String createDate;
	private String effectiveTime;
	private String expiredTime;
	private String latestActiveDate;
	private List<SubProductInfoVO> subProductInfoList;
	public OfferingIdVO getOfferingIdVO() {
		return offeringIdVO;
	}
	public void setOfferingIdVO(OfferingIdVO offeringIdVO) {
		this.offeringIdVO = offeringIdVO;
	}
	public String getOfferingName() {
		return offeringName;
	}
	public void setOfferingName(String offeringName) {
		this.offeringName = offeringName;
	}
	public String getBundleFlag() {
		return bundleFlag;
	}
	public void setBundleFlag(String bundleFlag) {
		this.bundleFlag = bundleFlag;
	}
	public String getTrialStartDate() {
		return trialStartDate;
	}
	public void setTrialStartDate(String trialStartDate) {
		this.trialStartDate = trialStartDate;
	}
	public String getTrialEndDate() {
		return trialEndDate;
	}
	public void setTrialEndDate(String trialEndDate) {
		this.trialEndDate = trialEndDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	public String getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public String getExpiredTime() {
		return expiredTime;
	}
	public void setExpiredTime(String expiredTime) {
		this.expiredTime = expiredTime;
	}
	public String getLatestActiveDate() {
		return latestActiveDate;
	}
	public void setLatestActiveDate(String latestActiveDate) {
		this.latestActiveDate = latestActiveDate;
	}
	public List<SubProductInfoVO> getSubProductInfoList() {
		return subProductInfoList;
	}
	public void setSubProductInfoList(List<SubProductInfoVO> subProductInfoList) {
		this.subProductInfoList = subProductInfoList;
	}
	public OfferingIdVO getParentOfferingIdVO() {
		return parentOfferingIdVO;
	}
	public void setParentOfferingIdVO(OfferingIdVO parentOfferingIdVO) {
		this.parentOfferingIdVO = parentOfferingIdVO;
	}
	
	
}
