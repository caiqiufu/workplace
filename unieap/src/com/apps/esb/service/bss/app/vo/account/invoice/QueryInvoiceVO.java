package com.apps.esb.service.bss.app.vo.account.invoice;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class QueryInvoiceVO extends BaseVO{
	private List<InvoiceVO> invoiceList;

	public List<InvoiceVO> getInvoiceList() {
		return invoiceList;
	}

	public void setInvoiceList(List<InvoiceVO> invoiceList) {
		this.invoiceList = invoiceList;
	}
	
}
