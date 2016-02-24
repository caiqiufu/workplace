package com.apps.esb.service.bss.app.vo.subscriber.chginfo;

import com.unieap.base.vo.BaseVO;

public class BalanceChgInfoVO extends BaseVO{
	private String balanceID;
	private String balanceType;
	private String balanceTypeDesc;
	private String oldBalanceAmount;
	private String newBalanceAmount;
	public String getBalanceID() {
		return balanceID;
	}
	public void setBalanceID(String balanceID) {
		this.balanceID = balanceID;
	}
	public String getBalanceType() {
		return balanceType;
	}
	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}
	
	public String getBalanceTypeDesc() {
		return balanceTypeDesc;
	}
	public void setBalanceTypeDesc(String balanceTypeDesc) {
		this.balanceTypeDesc = balanceTypeDesc;
	}
	public String getOldBalanceAmount() {
		return oldBalanceAmount;
	}
	public void setOldBalanceAmount(String oldBalanceAmount) {
		this.oldBalanceAmount = oldBalanceAmount;
	}
	public String getNewBalanceAmount() {
		return newBalanceAmount;
	}
	public void setNewBalanceAmount(String newBalanceAmount) {
		this.newBalanceAmount = newBalanceAmount;
	}
}
