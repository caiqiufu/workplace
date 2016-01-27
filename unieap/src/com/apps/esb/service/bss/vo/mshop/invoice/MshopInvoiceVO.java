package com.apps.esb.service.bss.vo.mshop.invoice;

import com.unieap.base.vo.BaseVO;

public class MshopInvoiceVO extends BaseVO{
	private String customerId;
	private String accountId;
	private String invoiceId;
	private String invoiceNo;
	private String billCycleId;
	private String billCycleBeginTime;
	private String billCycleEndTime;
	private String invoiceDate;
	private String openAmount;
	private String closeAmount;
	private String disputeAmount;
	private String status;
	private String statusDesc;
	private String dueDate;
	public String getCustomerId() {
		return customerId;
	}
	public String getAccountId() {
		return accountId;
	}
	public String getInvoiceId() {
		return invoiceId;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public String getBillCycleId() {
		return billCycleId;
	}
	public String getBillCycleBeginTime() {
		return billCycleBeginTime;
	}
	public String getBillCycleEndTime() {
		return billCycleEndTime;
	}
	public String getInvoiceDate() {
		return invoiceDate;
	}
	public String getOpenAmount() {
		return openAmount;
	}
	public String getCloseAmount() {
		return closeAmount;
	}
	public String getDisputeAmount() {
		return disputeAmount;
	}
	public String getStatus() {
		return status;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public String getDueDate() {
		return dueDate;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public void setBillCycleId(String billCycleId) {
		this.billCycleId = billCycleId;
	}
	public void setBillCycleBeginTime(String billCycleBeginTime) {
		this.billCycleBeginTime = billCycleBeginTime;
	}
	public void setBillCycleEndTime(String billCycleEndTime) {
		this.billCycleEndTime = billCycleEndTime;
	}
	public void setInvoiceDate(String invoiceDate) {
		this.invoiceDate = invoiceDate;
	}
	public void setOpenAmount(String openAmount) {
		this.openAmount = openAmount;
	}
	public void setCloseAmount(String closeAmount) {
		this.closeAmount = closeAmount;
	}
	public void setDisputeAmount(String disputeAmount) {
		this.disputeAmount = disputeAmount;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public void setDueDate(String dueDate) {
		this.dueDate = dueDate;
	}
	
	
}
