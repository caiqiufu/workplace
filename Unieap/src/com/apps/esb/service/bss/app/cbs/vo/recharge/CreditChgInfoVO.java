package com.apps.esb.service.bss.app.cbs.vo.recharge;

public class CreditChgInfoVO {
	private String creditLimitID;
	private String creditLimitType;
	private String creditLimitTypeName;
	private String oldLeftCreditAmt;
	private String newLeftCreditAmt;
	private String measureUnit;
	public String getCreditLimitID() {
		return creditLimitID;
	}
	public void setCreditLimitID(String creditLimitID) {
		this.creditLimitID = creditLimitID;
	}
	public String getCreditLimitType() {
		return creditLimitType;
	}
	public void setCreditLimitType(String creditLimitType) {
		this.creditLimitType = creditLimitType;
	}
	public String getCreditLimitTypeName() {
		return creditLimitTypeName;
	}
	public void setCreditLimitTypeName(String creditLimitTypeName) {
		this.creditLimitTypeName = creditLimitTypeName;
	}
	public String getOldLeftCreditAmt() {
		return oldLeftCreditAmt;
	}
	public void setOldLeftCreditAmt(String oldLeftCreditAmt) {
		this.oldLeftCreditAmt = oldLeftCreditAmt;
	}
	public String getNewLeftCreditAmt() {
		return newLeftCreditAmt;
	}
	public void setNewLeftCreditAmt(String newLeftCreditAmt) {
		this.newLeftCreditAmt = newLeftCreditAmt;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	
}
