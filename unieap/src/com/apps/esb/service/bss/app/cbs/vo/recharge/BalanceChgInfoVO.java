package com.apps.esb.service.bss.app.cbs.vo.recharge;

public class BalanceChgInfoVO {
	private String balanceType;
	private String balanceID;
	private String balanceTypeName;
	private String oldBalanceAmt;
	private String newBalanceAmt;
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
	public String getOldBalanceAmt() {
		return oldBalanceAmt;
	}
	public void setOldBalanceAmt(String oldBalanceAmt) {
		this.oldBalanceAmt = oldBalanceAmt;
	}
	public String getNewBalanceAmt() {
		return newBalanceAmt;
	}
	public void setNewBalanceAmt(String newBalanceAmt) {
		this.newBalanceAmt = newBalanceAmt;
	}
	
}
