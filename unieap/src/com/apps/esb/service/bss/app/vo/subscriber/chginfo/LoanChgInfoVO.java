package com.apps.esb.service.bss.app.vo.subscriber.chginfo;

import com.unieap.base.vo.BaseVO;

public class LoanChgInfoVO extends BaseVO{
	private String oldLoanAmount;
	private String newLoanAmount;
	private String loanPaymentAmount;
	private String loanInterestAmount;
	public String getOldLoanAmount() {
		return oldLoanAmount;
	}
	public void setOldLoanAmount(String oldLoanAmount) {
		this.oldLoanAmount = oldLoanAmount;
	}
	public String getNewLoanAmount() {
		return newLoanAmount;
	}
	public void setNewLoanAmount(String newLoanAmount) {
		this.newLoanAmount = newLoanAmount;
	}
	public String getLoanPaymentAmount() {
		return loanPaymentAmount;
	}
	public void setLoanPaymentAmount(String loanPaymentAmount) {
		this.loanPaymentAmount = loanPaymentAmount;
	}
	public String getLoanInterestAmount() {
		return loanInterestAmount;
	}
	public void setLoanInterestAmount(String loanInterestAmount) {
		this.loanInterestAmount = loanInterestAmount;
	}
	
}
