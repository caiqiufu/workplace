package com.apps.esb.service.bss.app.vo.account.invoice;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class QueryInvoicePaymentVO extends BaseVO{
	private List<InvoicePaymentVO> invoicePaymentList;

	public List<InvoicePaymentVO> getInvoicePaymentList() {
		return invoicePaymentList;
	}

	public void setInvoicePaymentList(List<InvoicePaymentVO> invoicePaymentList) {
		this.invoicePaymentList = invoicePaymentList;
	}
	
}
