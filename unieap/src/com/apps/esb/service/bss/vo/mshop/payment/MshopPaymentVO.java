package com.apps.esb.service.bss.vo.mshop.payment;

import com.unieap.base.vo.BaseVO;

public class MshopPaymentVO extends BaseVO{
	private String transactionId;
	private String date;
	private String amount;
	private String originalAmount;
	private String currentAmount;
	private String paymentMethod;
	public String getTransactionId() {
		return transactionId;
	}
	public String getDate() {
		return date;
	}
	public String getAmount() {
		return amount;
	}
	public String getOriginalAmount() {
		return originalAmount;
	}
	public String getCurrentAmount() {
		return currentAmount;
	}
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public void setOriginalAmount(String originalAmount) {
		this.originalAmount = originalAmount;
	}
	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	
}
