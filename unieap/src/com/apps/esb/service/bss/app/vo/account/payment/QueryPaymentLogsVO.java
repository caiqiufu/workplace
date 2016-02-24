package com.apps.esb.service.bss.app.vo.account.payment;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class QueryPaymentLogsVO extends BaseVO{
	private List<PaymentVO> paymentList;
	private String totalNum;
	private String beginNum;
	private String fetchNum;
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getBeginNum() {
		return beginNum;
	}
	public void setBeginNum(String beginNum) {
		this.beginNum = beginNum;
	}
	public String getFetchNum() {
		return fetchNum;
	}
	public void setFetchNum(String fetchNum) {
		this.fetchNum = fetchNum;
	}
	public List<PaymentVO> getPaymentList() {
		return paymentList;
	}
	public void setPaymentList(List<PaymentVO> paymentList) {
		this.paymentList = paymentList;
	}
	
}
