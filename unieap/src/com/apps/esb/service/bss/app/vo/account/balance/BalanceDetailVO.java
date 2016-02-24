package com.apps.esb.service.bss.app.vo.account.balance;

import com.unieap.base.vo.BaseVO;

public class BalanceDetailVO extends BaseVO{
	public String balanceInstanceID;
	public String amount;
	public String initialAmount;
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
	
	
}
