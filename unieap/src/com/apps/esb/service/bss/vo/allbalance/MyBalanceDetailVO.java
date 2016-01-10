package com.apps.esb.service.bss.vo.allbalance;

import com.unieap.base.vo.BaseVO;

public class MyBalanceDetailVO extends BaseVO{
	private String index;
	private String offeringId;
	private String offeringCode;
	private String offeringName;
	private String offeringDesc;
	private String totalAmount;
	private String remaningAmount ;
	private String usageAmount ;
	private String effectiveTime;
	private String expiryTime;
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getOfferingId() {
		return offeringId;
	}
	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}
	public String getOfferingCode() {
		return offeringCode;
	}
	public void setOfferingCode(String offeringCode) {
		this.offeringCode = offeringCode;
	}
	public String getOfferingName() {
		return offeringName;
	}
	public void setOfferingName(String offeringName) {
		this.offeringName = offeringName;
	}
	public String getOfferingDesc() {
		return offeringDesc;
	}
	public void setOfferingDesc(String offeringDesc) {
		this.offeringDesc = offeringDesc;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getRemaningAmount() {
		return remaningAmount;
	}
	public void setRemaningAmount(String remaningAmount) {
		this.remaningAmount = remaningAmount;
	}
	public String getUsageAmount() {
		return usageAmount;
	}
	public void setUsageAmount(String usageAmount) {
		this.usageAmount = usageAmount;
	}
	public String getExpiryTime() {
		return expiryTime;
	}
	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}
	public String getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	
}
