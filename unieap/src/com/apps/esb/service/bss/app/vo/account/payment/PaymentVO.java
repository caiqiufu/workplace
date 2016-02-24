package com.apps.esb.service.bss.app.vo.account.payment;

import java.util.List;

import com.apps.esb.service.bss.app.vo.common.bank.BankVO;
import com.unieap.base.vo.BaseVO;

public class PaymentVO extends BaseVO{
	private String customerId;
	private String subscriberId;
	private String accountId;
	private String serviceNumber;
	private String transactionId;
	private String transactionType;
	private String transactionTypeDesc;
	private String paymentTime;
	private String amount;
	private String originalAmount;
	private String paymentMethod;
	private String paymentMethodDesc;
	private String payChannelId;
	private String payChannelName;
	private String accessMode;
	private String status;
	private String statusDesc;
	private String operId;
	private String operName;
	private String deptId;
	private String deptName;
	private String totalPaymentAmount;
	private String currentAmount;
	private String taxAmount;
	private List<PaymentDetailVO> paymentDetailList;
	private BankVO bankVO;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public String getPaymentTime() {
		return paymentTime;
	}
	public void setPaymentTime(String paymentTime) {
		this.paymentTime = paymentTime;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getOriginalAmount() {
		return originalAmount;
	}
	public void setOriginalAmount(String originalAmount) {
		this.originalAmount = originalAmount;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPayChannelId() {
		return payChannelId;
	}
	public void setPayChannelId(String payChannelId) {
		this.payChannelId = payChannelId;
	}
	public String getPayChannelName() {
		return payChannelName;
	}
	public void setPayChannelName(String payChannelName) {
		this.payChannelName = payChannelName;
	}
	public String getAccessMode() {
		return accessMode;
	}
	public void setAccessMode(String accessMode) {
		this.accessMode = accessMode;
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
	public String getTotalPaymentAmount() {
		return totalPaymentAmount;
	}
	public void setTotalPaymentAmount(String totalPaymentAmount) {
		this.totalPaymentAmount = totalPaymentAmount;
	}
	
	public String getCurrentAmount() {
		return currentAmount;
	}
	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}
	public String getTaxAmount() {
		return taxAmount;
	}
	public void setTaxAmount(String taxAmount) {
		this.taxAmount = taxAmount;
	}
	public List<PaymentDetailVO> getPaymentDetailList() {
		return paymentDetailList;
	}
	public void setPaymentDetailList(List<PaymentDetailVO> paymentDetailList) {
		this.paymentDetailList = paymentDetailList;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getPaymentMethodDesc() {
		return paymentMethodDesc;
	}
	public void setPaymentMethodDesc(String paymentMethodDesc) {
		this.paymentMethodDesc = paymentMethodDesc;
	}
	public String getTransactionId() {
		return transactionId;
	}
	public String getTransactionType() {
		return transactionType;
	}
	public String getTransactionTypeDesc() {
		return transactionTypeDesc;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public void setTransactionTypeDesc(String transactionTypeDesc) {
		this.transactionTypeDesc = transactionTypeDesc;
	}
	public BankVO getBankVO() {
		return bankVO;
	}
	public void setBankVO(BankVO bankVO) {
		this.bankVO = bankVO;
	}
	
}
