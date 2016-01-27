package com.apps.esb.service.bss.vo.macre.recharge;

import com.unieap.base.vo.BaseVO;

public class VoucherRechargeVO extends BaseVO{
	private String balance;
	private String validity;
	public String getBalance() {
		return balance;
	}
	public void setBalance(String balance) {
		this.balance = balance;
	}
	public String getValidity() {
		return validity;
	}
	public void setValidity(String validity) {
		this.validity = validity;
	}
	
}
