package com.apps.esb.service.bss.vo.mshop.subscriber;

import com.unieap.base.vo.BaseVO;

public class MshopOfferingVO extends BaseVO{
	/**
	 * P: primary offering, 
	 * S: supplementary offering
	 */
	private String offeringType;
	private String offeringTypeDesc;
	private String offeringId;
	private String offeringName;
	private String status;
	private String startTime;
	private String expiryTime;
	private String recurringFee;
	private String oneTimeFee;
	public String getOfferingType() {
		return offeringType;
	}
	public String getOfferingTypeDesc() {
		return offeringTypeDesc;
	}
	public String getOfferingId() {
		return offeringId;
	}
	public String getOfferingName() {
		return offeringName;
	}
	public String getStatus() {
		return status;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getExpiryTime() {
		return expiryTime;
	}
	public String getRecurringFee() {
		return recurringFee;
	}
	public void setOfferingType(String offeringType) {
		this.offeringType = offeringType;
	}
	public void setOfferingTypeDesc(String offeringTypeDesc) {
		this.offeringTypeDesc = offeringTypeDesc;
	}
	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}
	public void setOfferingName(String offeringName) {
		this.offeringName = offeringName;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}
	public void setRecurringFee(String recurringFee) {
		this.recurringFee = recurringFee;
	}
	public String getOneTimeFee() {
		return oneTimeFee;
	}
	public void setOneTimeFee(String oneTimeFee) {
		this.oneTimeFee = oneTimeFee;
	}
	
}
