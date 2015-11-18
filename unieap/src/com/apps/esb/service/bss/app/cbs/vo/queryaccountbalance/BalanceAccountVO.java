package com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance;

import java.util.List;

public class BalanceAccountVO {
	public String accountKey;
	public List<BalanceResultVO> balanceResultList;
	public List<OutStandingVO> outStandingList;
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

	

	public List<OutStandingVO> getOutStandingList() {
		return outStandingList;
	}

	public void setOutStandingList(List<OutStandingVO> outStandingList) {
		this.outStandingList = outStandingList;
	}

	public List<AccountCreditVO> getAccountCreditList() {
		return accountCreditList;
	}

	public void setAccountCreditList(List<AccountCreditVO> accountCreditList) {
		this.accountCreditList = accountCreditList;
	}

	

}
