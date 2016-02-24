package com.apps.esb.service.bss.app.vo.account.payment;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class PaymentDetailVO extends BaseVO{
	private String applyType;
	private String applyTypeDesc;
	private String applyAmount;
	private String invoiceNo;
	private String inoviceDetailId;
	private String chargeCode;
	private String chargeAmount;
	private String discountAmount;
	private List<TaxVO> taxList;
	public String getApplyType() {
		return applyType;
	}
	public void setApplyType(String applyType) {
		this.applyType = applyType;
	}
	public String getApplyAmount() {
		return applyAmount;
	}
	public void setApplyAmount(String applyAmount) {
		this.applyAmount = applyAmount;
	}
	public String getInvoiceNo() {
		return invoiceNo;
	}
	public void setInvoiceNo(String invoiceNo) {
		this.invoiceNo = invoiceNo;
	}
	public String getInoviceDetailId() {
		return inoviceDetailId;
	}
	public void setInoviceDetailId(String inoviceDetailId) {
		this.inoviceDetailId = inoviceDetailId;
	}
	public String getChargeCode() {
		return chargeCode;
	}
	public void setChargeCode(String chargeCode) {
		this.chargeCode = chargeCode;
	}
	public String getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}
	public String getDiscountAmount() {
		return discountAmount;
	}
	public void setDiscountAmount(String discountAmount) {
		this.discountAmount = discountAmount;
	}
	public List<TaxVO> getTaxList() {
		return taxList;
	}
	public void setTaxList(List<TaxVO> taxList) {
		this.taxList = taxList;
	}
	public String getApplyTypeDesc() {
		return applyTypeDesc;
	}
	public void setApplyTypeDesc(String applyTypeDesc) {
		this.applyTypeDesc = applyTypeDesc;
	}
	
	
}
