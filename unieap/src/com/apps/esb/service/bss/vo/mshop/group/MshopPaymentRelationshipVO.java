package com.apps.esb.service.bss.vo.mshop.group;

import com.unieap.base.vo.BaseVO;

public class MshopPaymentRelationshipVO extends BaseVO{
	private String accountCode;
	private String serviceNumber;
	private String splitMode;
	private String proateLimit;
	private String serviceType;
	private String timeScheme;
	private String validFrom;
	public String getAccountCode() {
		return accountCode;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public String getSplitMode() {
		return splitMode;
	}
	public String getProateLimit() {
		return proateLimit;
	}
	public String getServiceType() {
		return serviceType;
	}
	public String getTimeScheme() {
		return timeScheme;
	}
	public String getValidFrom() {
		return validFrom;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public void setSplitMode(String splitMode) {
		this.splitMode = splitMode;
	}
	public void setProateLimit(String proateLimit) {
		this.proateLimit = proateLimit;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public void setTimeScheme(String timeScheme) {
		this.timeScheme = timeScheme;
	}
	public void setValidFrom(String validFrom) {
		this.validFrom = validFrom;
	}
	
	
}
