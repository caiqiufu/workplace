package com.apps.esb.service.bss.app.vo.account.invoice;

import com.unieap.base.vo.BaseVO;

public class InvoiceDetailVO extends BaseVO{
	private String invoiceId;
	private String subscriberId;
	private String serviceNumber;
	private String serviceCategory;
	private String serviceCategoryDesc;
	private String chargeCode;
	private String openAmount;
	private String disputeAmount;
	private String status;
	private String statusDesc;
	public String getInvoiceId() {
		return invoiceId;
	}
	public void setInvoiceId(String invoiceId) {
		this.invoiceId = invoiceId;
	}
	public String getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	public String getChargeCode() {
		return chargeCode;
	}
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
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
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getServiceCategoryDesc() {
		return serviceCategoryDesc;
	}
	public void setServiceCategoryDesc(String serviceCategoryDesc) {
		this.serviceCategoryDesc = serviceCategoryDesc;
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
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
}
