package com.apps.esb.service.bss.app.vo.customer.basicinfo;

import com.unieap.base.vo.BaseVO;

public class CorporateVO extends BaseVO{
	private String corpNo;
	private String IDType;
	private String IDTypeDesc;
	private String IDNumber;
	private String IDValidity;
	private String custNo;
	private String custType;
	private String custTypeDesc;
	private String custGrade;
	private String custName;
	private String custShortName;
	private String custPhoneNumber;
	private String custEmail;
	private String custWebSite;
	private String TIN;
	public String getCorpNo() {
		return corpNo;
	}
	public void setCorpNo(String corpNo) {
		this.corpNo = corpNo;
	}
	public String getIDType() {
		return IDType;
	}
	public void setIDType(String iDType) {
		IDType = iDType;
	}
	public String getIDNumber() {
		return IDNumber;
	}
	public void setIDNumber(String iDNumber) {
		IDNumber = iDNumber;
	}
	public String getIDValidity() {
		return IDValidity;
	}
	public void setIDValidity(String iDValidity) {
		IDValidity = iDValidity;
	}
	public String getCustNo() {
		return custNo;
	}
	public void setCustNo(String custNo) {
		this.custNo = custNo;
	}
	public String getCustType() {
		return custType;
	}
	public void setCustType(String custType) {
		this.custType = custType;
	}
	public String getCustGrade() {
		return custGrade;
	}
	public void setCustGrade(String custGrade) {
		this.custGrade = custGrade;
	}
	public String getCustName() {
		return custName;
	}
	public void setCustName(String custName) {
		this.custName = custName;
	}
	public String getCustShortName() {
		return custShortName;
	}
	public void setCustShortName(String custShortName) {
		this.custShortName = custShortName;
	}
	public String getCustPhoneNumber() {
		return custPhoneNumber;
	}
	public void setCustPhoneNumber(String custPhoneNumber) {
		this.custPhoneNumber = custPhoneNumber;
	}
	public String getCustEmail() {
		return custEmail;
	}
	public void setCustEmail(String custEmail) {
		this.custEmail = custEmail;
	}
	public String getCustWebSite() {
		return custWebSite;
	}
	public void setCustWebSite(String custWebSite) {
		this.custWebSite = custWebSite;
	}
	public String getTIN() {
		return TIN;
	}
	public void setTIN(String tIN) {
		TIN = tIN;
	}
	public String getIDTypeDesc() {
		return IDTypeDesc;
	}
	public void setIDTypeDesc(String iDTypeDesc) {
		IDTypeDesc = iDTypeDesc;
	}
	public String getCustTypeDesc() {
		return custTypeDesc;
	}
	public void setCustTypeDesc(String custTypeDesc) {
		this.custTypeDesc = custTypeDesc;
	}
	
}
