package com.apps.esb.service.bss.app.vo.common.bank;

import com.unieap.base.vo.BaseVO;

public class BankVO extends BaseVO{
	private String bankCode;
	private String bankName;
	private String bankBranchCode;
	private String bankBranchName;
	private String acctType;
	private String acctTypeDesc;
	private String acctNo;
	private String creditCardType;
	private String creditCardTypeDesc;
	private String acctName;
	private String expDate;
	private String cvvNumber;
	private String checkNo;
	public String getBankCode() {
		return bankCode;
	}
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankBranchCode() {
		return bankBranchCode;
	}
	public void setBankBranchCode(String bankBranchCode) {
		this.bankBranchCode = bankBranchCode;
	}
	public String getBankBranchName() {
		return bankBranchName;
	}
	public void setBankBranchName(String bankBranchName) {
		this.bankBranchName = bankBranchName;
	}
	public String getAcctType() {
		return acctType;
	}
	public void setAcctType(String acctType) {
		this.acctType = acctType;
	}
	public String getAcctNo() {
		return acctNo;
	}
	public void setAcctNo(String acctNo) {
		this.acctNo = acctNo;
	}
	public String getCreditCardType() {
		return creditCardType;
	}
	public void setCreditCardType(String creditCardType) {
		this.creditCardType = creditCardType;
	}
	public String getAcctName() {
		return acctName;
	}
	public void setAcctName(String acctName) {
		this.acctName = acctName;
	}
	public String getExpDate() {
		return expDate;
	}
	public void setExpDate(String expDate) {
		this.expDate = expDate;
	}
	public String getCvvNumber() {
		return cvvNumber;
	}
	public void setCvvNumber(String cvvNumber) {
		this.cvvNumber = cvvNumber;
	}
	public String getCheckNo() {
		return checkNo;
	}
	public void setCheckNo(String checkNo) {
		this.checkNo = checkNo;
	}
	public String getAcctTypeDesc() {
		return acctTypeDesc;
	}
	public void setAcctTypeDesc(String acctTypeDesc) {
		this.acctTypeDesc = acctTypeDesc;
	}
	public String getCreditCardTypeDesc() {
		return creditCardTypeDesc;
	}
	public void setCreditCardTypeDesc(String creditCardTypeDesc) {
		this.creditCardTypeDesc = creditCardTypeDesc;
	}
	
	
}
