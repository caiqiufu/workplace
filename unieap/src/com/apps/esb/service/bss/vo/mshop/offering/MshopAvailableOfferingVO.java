package com.apps.esb.service.bss.vo.mshop.offering;

import com.unieap.base.vo.BaseVO;

public class MshopAvailableOfferingVO extends BaseVO{
	/**
	 * P: primary offering, 
	 * S: supplementary offering
	 */
	private String offeringType;
	private String offeringTypeDesc;
	private String offeringId;
	private String offeringName;
	private String recurringFee;
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
	public void setRecurringFee(String recurringFee) {
		this.recurringFee = recurringFee;
	}
	
}
