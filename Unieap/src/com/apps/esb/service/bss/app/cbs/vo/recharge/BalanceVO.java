package com.apps.esb.service.bss.app.cbs.vo.recharge;

public class BalanceVO {
	private String balanceType;
	private String balanceID;
	private String balanceTypeName;
	private String bonusAmt;
	private String effectiveTime;
	private String expireTime;
	public String getBalanceType() {
		return balanceType;
	}
	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}
	public String getBalanceID() {
		return balanceID;
	}
	public void setBalanceID(String balanceID) {
		this.balanceID = balanceID;
	}
	public String getBalanceTypeName() {
		return balanceTypeName;
	}
	public void setBalanceTypeName(String balanceTypeName) {
		this.balanceTypeName = balanceTypeName;
	}
	public String getBonusAmt() {
		return bonusAmt;
	}
	public void setBonusAmt(String bonusAmt) {
		this.bonusAmt = bonusAmt;
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
