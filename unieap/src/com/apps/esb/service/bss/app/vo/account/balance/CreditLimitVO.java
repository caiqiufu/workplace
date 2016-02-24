package com.apps.esb.service.bss.app.vo.account.balance;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class CreditLimitVO extends BaseVO{
	/**
	 * 1: initinalCreditLimit
	 * 2: tempCreditLimit
	 */
	private String creditLimitType;
	private String creditLimitTypeDesc;
	private String totalCreditAmount;
	private String totalUsageAmount;
	private String totalRemainAmount;
	private List<CreditLimitDetailVO> creditLimitDetailList;
	
	public String getCreditLimitType() {
		return creditLimitType;
	}
	public void setCreditLimitType(String creditLimitType) {
		this.creditLimitType = creditLimitType;
	}
	
	public String getCreditLimitTypeDesc() {
		return creditLimitTypeDesc;
	}
	public void setCreditLimitTypeDesc(String creditLimitTypeDesc) {
		this.creditLimitTypeDesc = creditLimitTypeDesc;
	}
	public String getTotalCreditAmount() {
		return totalCreditAmount;
	}
	public void setTotalCreditAmount(String totalCreditAmount) {
		this.totalCreditAmount = totalCreditAmount;
	}
	public String getTotalUsageAmount() {
		return totalUsageAmount;
	}
	public void setTotalUsageAmount(String totalUsageAmount) {
		this.totalUsageAmount = totalUsageAmount;
	}
	public String getTotalRemainAmount() {
		return totalRemainAmount;
	}
	public void setTotalRemainAmount(String totalRemainAmount) {
		this.totalRemainAmount = totalRemainAmount;
	}
	public List<CreditLimitDetailVO> getCreditLimitDetailList() {
		return creditLimitDetailList;
	}
	public void setCreditLimitDetailList(List<CreditLimitDetailVO> creditLimitDetailList) {
		this.creditLimitDetailList = creditLimitDetailList;
	}
	
}
