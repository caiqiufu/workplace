package com.apps.esb.service.bss.app.vo.account.invoice;

import com.apps.esb.service.bss.app.vo.common.bank.BankVO;
import com.unieap.base.vo.BaseVO;

public class InvoicePaymentVO extends BaseVO{
	private String paymentTime;
	private String paymentAmount;
	private String applyAmount;
	private String transId;
	private String transType;
	private String transTypeDesc;
	private String extPayType;
	private String extPayTypeDesc;
	private String paymentMethod;
	private String paymentChannelId;
	private String paymentChannelName;
	private String status;
	private String operId;
	private String operName;
	private String deptId;
	private String deptName;
	private BankVO bankVO;
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(String paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getTransType() {
		return transType;
	}
	public void setTransType(String transType) {
		this.transType = transType;
	}
	public String getExtPayType() {
		return extPayType;
	}
	public void setExtPayType(String extPayType) {
		this.extPayType = extPayType;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentChannelId() {
		return paymentChannelId;
	}
	public void setPaymentChannelId(String paymentChannelId) {
		this.paymentChannelId = paymentChannelId;
	}
	public String getPaymentChannelName() {
		return paymentChannelName;
	}
	public void setPaymentChannelName(String paymentChannelName) {
		this.paymentChannelName = paymentChannelName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getOperId() {
		return operId;
	}
	public void setOperId(String operId) {
		this.operId = operId;
	}
	public String getOperName() {
		return operName;
	}
	public void setOperName(String operName) {
		this.operName = operName;
	}
	public String getDeptId() {
		return deptId;
	}
	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}
	public String getDeptName() {
		return deptName;
	}
	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}
	public BankVO getBankVO() {
		return bankVO;
	}
	public void setBankVO(BankVO bankVO) {
		this.bankVO = bankVO;
	}
	public String getTransTypeDesc() {
		return transTypeDesc;
	}
	public void setTransTypeDesc(String transTypeDesc) {
		this.transTypeDesc = transTypeDesc;
	}
	public String getExtPayTypeDesc() {
		return extPayTypeDesc;
	}
	public void setExtPayTypeDesc(String extPayTypeDesc) {
		this.extPayTypeDesc = extPayTypeDesc;
	}
	
}
