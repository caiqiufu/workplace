package com.apps.esb.service.bss.app.cbs.vo.querybalance;

public class CreditAmountInfoVO {
	public String creditInstID;
	public String limitClass;
	public String amount;
	public String effectiveTime;
	public String expiryTime;

	public String getCreditInstID() {
		return creditInstID;
	}

	public void setCreditInstID(String creditInstID) {
		this.creditInstID = creditInstID;
	}

	public String getLimitClass() {
		return limitClass;
	}

	public void setLimitClass(String limitClass) {
		this.limitClass = limitClass;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getEffectiveTime() {
		return effectiveTime;
	}

	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}

	public String getExpiryTime() {
		return expiryTime;
	}

	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}

}
