package com.apps.esb.service.bss.app.vo.account.loan;

import com.unieap.base.vo.BaseVO;

public class LoanLogDetailVO extends BaseVO{
	private String loanId;
	private String loanAmount;
	private String loanDate;
	private String loanPoundage;
	private String repaymentAmount;
	private String loanStatus;
	private String paidAmount;
	private String remainingAmount;
	private String graceDate;
	public String getLoanId() {
		return loanId;
	}
	public void setLoanId(String loanId) {
		this.loanId = loanId;
	}
	public String getLoanAmount() {
		return loanAmount;
	}
	public void setLoanAmount(String loanAmount) {
		this.loanAmount = loanAmount;
	}
	public String getLoanDate() {
		return loanDate;
	}
	public void setLoanDate(String loanDate) {
		this.loanDate = loanDate;
	}
	public String getLoanPoundage() {
		return loanPoundage;
	}
	public void setLoanPoundage(String loanPoundage) {
		this.loanPoundage = loanPoundage;
	}
	public String getRepaymentAmount() {
		return repaymentAmount;
	}
	public void setRepaymentAmount(String repaymentAmount) {
		this.repaymentAmount = repaymentAmount;
	}
	public String getLoanStatus() {
		return loanStatus;
	}
	public void setLoanStatus(String loanStatus) {
		this.loanStatus = loanStatus;
	}
	public String getPaidAmount() {
		return paidAmount;
	}
	public void setPaidAmount(String paidAmount) {
		this.paidAmount = paidAmount;
	}
	public String getRemainingAmount() {
		return remainingAmount;
	}
	public void setRemainingAmount(String remainingAmount) {
		this.remainingAmount = remainingAmount;
	}
	public String getGraceDate() {
		return graceDate;
	}
	public void setGraceDate(String graceDate) {
		this.graceDate = graceDate;
	}
	
	
}
