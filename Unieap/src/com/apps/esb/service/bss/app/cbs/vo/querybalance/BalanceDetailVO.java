package com.apps.esb.service.bss.app.cbs.vo.querybalance;

public class BalanceDetailVO {
	public String balanceInstanceID;
	public String amount;
	public String initialAmount;
	public String effectiveTime;
	public String expiryTime;
	public String getBalanceInstanceID() {
		return balanceInstanceID;
	}
	public void setBalanceInstanceID(String balanceInstanceID) {
		this.balanceInstanceID = balanceInstanceID;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getInitialAmount() {
		return initialAmount;
	}
	public void setInitialAmount(String initialAmount) {
		this.initialAmount = initialAmount;
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
