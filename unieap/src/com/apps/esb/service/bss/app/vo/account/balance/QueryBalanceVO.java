package com.apps.esb.service.bss.app.vo.account.balance;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class QueryBalanceVO extends BaseVO{
	private String accountId;
	/**
	 * main account balance, advance balance
	 */
	private String totalBalanceAmount;
	/**
	 * advance payment balance+total credit limit
	 */
	private String totalAvailableAmount;
	private String totalOutStandingAmount;
	private String dueDate;
	private String totalCreditLimitAmount;
	private List<BalanceVO> balanceList;
	private List<OutStandingVO> outStandingList;
	private List<CreditLimitVO> creditLimitList;
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public List<BalanceVO> getBalanceList() {
		return balanceList;
	}
	public void setBalanceList(List<BalanceVO> balanceList) {
		this.balanceList = balanceList;
	}
	public List<OutStandingVO> getOutStandingList() {
		return outStandingList;
	}
	public void setOutStandingList(List<OutStandingVO> outStandingList) {
		this.outStandingList = outStandingList;
	}
	public List<CreditLimitVO> getCreditLimitList() {
		return creditLimitList;
	}
	public void setCreditLimitList(List<CreditLimitVO> creditLimitList) {
		this.creditLimitList = creditLimitList;
	}
	public String getTotalOutStandingAmount() {
		return totalOutStandingAmount;
	}
	public String getTotalCreditLimitAmount() {
		return totalCreditLimitAmount;
	}
	public void setTotalOutStandingAmount(String totalOutStandingAmount) {
		this.totalOutStandingAmount = totalOutStandingAmount;
	}
	public void setTotalCreditLimitAmount(String totalCreditLimitAmount) {
		this.totalCreditLimitAmount = totalCreditLimitAmount;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getTotalAvailableAmount() {
		return totalAvailableAmount;
	}
	public void setTotalAvailableAmount(String totalAvailableAmount) {
		this.totalAvailableAmount = totalAvailableAmount;
	}
	public String getTotalBalanceAmount() {
		return totalBalanceAmount;
	}
	public void setTotalBalanceAmount(String totalBalanceAmount) {
		this.totalBalanceAmount = totalBalanceAmount;
	}
	
}
