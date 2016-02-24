package com.apps.esb.service.bss.app.vo.account.invoice;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class InvoiceVO extends BaseVO{
	private String customerId;
	private String accountId;
	private String subscriberId;
	private String serviceNumber;
	private String transactionType;
	private String transactionTypeDesc;
	private String invoiceId;
	private String invoiceNo;
	private String billCycleId;
	private String billCycleBeginTime;
	private String billCycleEndTime;
	private String invoiceAmount;
	private String openAmount;
	private String closeAmount;
	private String disputeAmount;
	private String invoiceDate;
	private String dueDate;
	private String settleDate;
	private String status;
	private String statusDesc;
	private List<InvoiceDetailVO> invoiceDetailList;
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	public String getTransactionType() {
		return transactionType;
	}
	public String getTransactionTypeDesc() {
		return transactionTypeDesc;
	}
	public void setTransactionType(String transactionType) {
		this.transactionType = transactionType;
	}
	public void setTransactionTypeDesc(String transactionTypeDesc) {
		this.transactionTypeDesc = transactionTypeDesc;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getBillCycleId() {
		return billCycleId;
	}
	public void setBillCycleId(String billCycleId) {
		this.billCycleId = billCycleId;
	}
	public String getBillCycleBeginTime() {
		return billCycleBeginTime;
	}
	public void setBillCycleBeginTime(String billCycleBeginTime) {
		this.billCycleBeginTime = billCycleBeginTime;
	}
	public String getBillCycleEndTime() {
		return billCycleEndTime;
	}
	public void setBillCycleEndTime(String billCycleEndTime) {
		this.billCycleEndTime = billCycleEndTime;
	}
	public String getInvoiceAmount() {
		return invoiceAmount;
	}
	public void setInvoiceAmount(String invoiceAmount) {
		this.invoiceAmount = invoiceAmount;
	}
	public String getOpenAmount() {
		return openAmount;
	}
	public void setOpenAmount(String openAmount) {
		this.openAmount = openAmount;
	}
	public String getDisputeAmount() {
		return disputeAmount;
	}
	public void setDisputeAmount(String disputeAmount) {
		this.disputeAmount = disputeAmount;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	public String getSettleDate() {
		return settleDate;
	}
	public void setSettleDate(String settleDate) {
		this.settleDate = settleDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public List<InvoiceDetailVO> getInvoiceDetailList() {
		return invoiceDetailList;
	}
	public void setInvoiceDetailList(List<InvoiceDetailVO> invoiceDetailList) {
		this.invoiceDetailList = invoiceDetailList;
	}
	public String getCloseAmount() {
		return closeAmount;
	}
	public void setCloseAmount(String closeAmount) {
		this.closeAmount = closeAmount;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	
	
}
