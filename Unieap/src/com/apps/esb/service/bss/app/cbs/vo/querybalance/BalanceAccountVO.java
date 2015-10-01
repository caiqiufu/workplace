package com.apps.esb.service.bss.app.cbs.vo.querybalance;

import java.util.List;

public class BalanceAccountVO {
	public String accountKey;
	public List<BalanceResultVO> balanceResultList;
	public List<OutstandingVO> outstandingList;
	public List<AccountCreditVO> accountCreditList;

	public String getAccountKey() {
		return accountKey;
	}

	public void setAccountKey(String accountKey) {
		this.accountKey = accountKey;
	}

	public List<BalanceResultVO> getBalanceResultList() {
		return balanceResultList;
	}

	public void setBalanceResultList(List<BalanceResultVO> balanceResultList) {
		this.balanceResultList = balanceResultList;
	}

	public List<OutstandingVO> getOutstandingList() {
		return outstandingList;
	}

	public void setOutstandingList(List<OutstandingVO> outstandingList) {
		this.outstandingList = outstandingList;
	}

	public List<AccountCreditVO> getAccountCreditList() {
		return accountCreditList;
	}

	public void setAccountCreditList(List<AccountCreditVO> accountCreditList) {
		this.accountCreditList = accountCreditList;
	}

	

}
