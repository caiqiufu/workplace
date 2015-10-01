package com.apps.esb.service.bss.app.cbs.vo.queryfreeunit;

public class FreeUnitItemDetailVO {
	public String freeUnitInstanceID;
	public String initialAmount;
	public String currentAmount;
	public String effectiveTime;
	public String expireTime;
	public String getFreeUnitInstanceID() {
		return freeUnitInstanceID;
	}
	public void setFreeUnitInstanceID(String freeUnitInstanceID) {
		this.freeUnitInstanceID = freeUnitInstanceID;
	}
	public String getInitialAmount() {
		return initialAmount;
	}
	public void setInitialAmount(String initialAmount) {
		this.initialAmount = initialAmount;
	}
	public String getCurrentAmount() {
		return currentAmount;
	}
	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}
	public String getEffectiveTime() {
		return effectiveTime;
	}
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public String getExpireTime() {
		return expireTime;
	}
	public void setExpireTime(String expireTime) {
		this.expireTime = expireTime;
	}

}
