package com.apps.esb.service.bss.app.vo.account.balance;

import com.unieap.base.vo.BaseVO;

public class CreditLimitDetailVO extends BaseVO{
	private String creditInstID;
	private String limitClass;
	private String amount;
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
	
}
