package com.apps.esb.service.bss.app.vo.account.payment;

import com.unieap.base.vo.BaseVO;

public class TaxVO extends BaseVO{
	private String taxCode;
	private String taxAmount;
	public String getTaxCode() {
		return taxCode;
	}
	public void setTaxCode(String taxCode) {
		this.taxCode = taxCode;
	}
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	
}
