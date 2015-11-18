package com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance;

import java.util.List;

public class AccountCreditVO {
	public String creditLimitType;
	public String creditLimitTypeName;
	public String totalCreditAmount;
	public String totalUsageAmount;
	public String totalRemainAmount;
	public List<CreditAmountInfoVO> creditAmountInfoList;

	public String getCreditLimitType() {
		return creditLimitType;
	}

	public void setCreditLimitType(String creditLimitType) {
		this.creditLimitType = creditLimitType;
	}

	public String getCreditLimitTypeName() {
		return creditLimitTypeName;
	}

	public void setCreditLimitTypeName(String creditLimitTypeName) {
		this.creditLimitTypeName = creditLimitTypeName;
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

	public List<CreditAmountInfoVO> getCreditAmountInfoList() {
		return creditAmountInfoList;
	}

	public void setCreditAmountInfoList(List<CreditAmountInfoVO> creditAmountInfoList) {
		this.creditAmountInfoList = creditAmountInfoList;
	}

}
