package com.apps.esb.service.bss.app.vo.account.loan;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class LoanLogVO extends BaseVO{
	private String totalDebtAmount;
	private String blacklistStatus;
	private String blacklistStatusDesc;
	private String blackListDate;
	private List<LoanLogDetailVO> loanLogDetailList;
	public String getTotalDebtAmount() {
		return totalDebtAmount;
	}
	public void setTotalDebtAmount(String totalDebtAmount) {
		this.totalDebtAmount = totalDebtAmount;
	}
	public String getBlacklistStatus() {
		return blacklistStatus;
	}
	public void setBlacklistStatus(String blacklistStatus) {
		this.blacklistStatus = blacklistStatus;
	}
	public String getBlackListDate() {
		return blackListDate;
	}
	public void setBlackListDate(String blackListDate) {
		this.blackListDate = blackListDate;
	}
	public List<LoanLogDetailVO> getLoanLogDetailList() {
		return loanLogDetailList;
	}
	public void setLoanLogDetailList(List<LoanLogDetailVO> loanLogDetailList) {
		this.loanLogDetailList = loanLogDetailList;
	}
	public String getBlacklistStatusDesc() {
		return blacklistStatusDesc;
	}
	public void setBlacklistStatusDesc(String blacklistStatusDesc) {
		this.blacklistStatusDesc = blacklistStatusDesc;
	}
	
}
