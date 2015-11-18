package com.apps.esb.service.bss.app.cbs.vo.recharge;

public class LoanChgInfoVO {
	private String oldLoanAmt;
	private String newLoanAmt;
	private String loanPaymentAmt;
	private String loanInterestAmt;
	public String getOldLoanAmt() {
		return oldLoanAmt;
	}
	public void setOldLoanAmt(String oldLoanAmt) {
		this.oldLoanAmt = oldLoanAmt;
	}
	public String getNewLoanAmt() {
		return newLoanAmt;
	}
	public void setNewLoanAmt(String newLoanAmt) {
		this.newLoanAmt = newLoanAmt;
	}
	public String getLoanPaymentAmt() {
		return loanPaymentAmt;
	}
	public void setLoanPaymentAmt(String loanPaymentAmt) {
		this.loanPaymentAmt = loanPaymentAmt;
	}
	public String getLoanInterestAmt() {
		return loanInterestAmt;
	}
	public void setLoanInterestAmt(String loanInterestAmt) {
		this.loanInterestAmt = loanInterestAmt;
	}
	
}
