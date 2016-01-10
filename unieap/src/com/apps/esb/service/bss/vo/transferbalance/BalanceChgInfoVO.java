package com.apps.esb.service.bss.vo.transferbalance;

import com.unieap.base.vo.BaseVO;

public class BalanceChgInfoVO extends BaseVO{
	private String balanceType;
	private String balanceId;
	private String balanceTypeName;
	private String oldBalanceAmt;
	private String newBalanceAmt;
	public String getBalanceType() {
		return balanceType;
	}
	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}
	public String getBalanceId() {
		return balanceId;
	}
	public void setBalanceId(String balanceId) {
		this.balanceId = balanceId;
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
