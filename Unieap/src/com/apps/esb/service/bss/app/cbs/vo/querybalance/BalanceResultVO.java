package com.apps.esb.service.bss.app.cbs.vo.querybalance;

import java.util.List;

public class BalanceResultVO {
	public String balanceType;
	public String balanceTypeName;
	public String totalAmount;
	public String depositFlag;
	public String refundFlag;
	public List<BalanceDetailVO> balanceDetailList;
	public String getBalanceType() {
		return balanceType;
	}
	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
	}
	public String getBalanceTypeName() {
		return balanceTypeName;
	}
	public void setBalanceTypeName(String balanceTypeName) {
		this.balanceTypeName = balanceTypeName;
	}
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getDepositFlag() {
		return depositFlag;
	}
	public void setDepositFlag(String depositFlag) {
		this.depositFlag = depositFlag;
	}
	public String getRefundFlag() {
		return refundFlag;
	}
	public void setRefundFlag(String refundFlag) {
		this.refundFlag = refundFlag;
	}
	public List<BalanceDetailVO> getBalanceDetailList() {
		return balanceDetailList;
	}
	public void setBalanceDetailList(List<BalanceDetailVO> balanceDetailList) {
		this.balanceDetailList = balanceDetailList;
	}
	
}
