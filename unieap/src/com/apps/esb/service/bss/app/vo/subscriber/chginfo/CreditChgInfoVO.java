package com.apps.esb.service.bss.app.vo.subscriber.chginfo;

import com.unieap.base.vo.BaseVO;

public class CreditChgInfoVO extends BaseVO{
	private String creditLimitID;
	private String creditLimitType;
	private String creditLimitTypeDesc;
	private String oldLeftCreditAmount;
	private String newLeftCreditAmount;
	private String measureUnit;
	private String measureUnitDesc;
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
	public String getOldLeftCreditAmount() {
		return oldLeftCreditAmount;
	}
	public void setOldLeftCreditAmount(String oldLeftCreditAmount) {
		this.oldLeftCreditAmount = oldLeftCreditAmount;
	}
	public String getNewLeftCreditAmount() {
		return newLeftCreditAmount;
	}
	public void setNewLeftCreditAmount(String newLeftCreditAmount) {
		this.newLeftCreditAmount = newLeftCreditAmount;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public String getCreditLimitTypeDesc() {
		return creditLimitTypeDesc;
	}
	public void setCreditLimitTypeDesc(String creditLimitTypeDesc) {
		this.creditLimitTypeDesc = creditLimitTypeDesc;
	}
	public String getMeasureUnitDesc() {
		return measureUnitDesc;
	}
	public void setMeasureUnitDesc(String measureUnitDesc) {
		this.measureUnitDesc = measureUnitDesc;
	}
	
}
