package com.apps.esb.service.bss.app.vo.account.balance;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class BalanceVO extends BaseVO{
	private String balanceID;
	/**
	 * 1 Prepaid mainbalance 
	 * 2 Postpaid available amount
	 */
	private String balanceType;
	private String balanceTypeDesc;
	private String totalAmount;
	private String depositFlag;
	private String refundFlag;
	private String refundFlagDesc;
	public List<BalanceDetailVO> balanceDetailList;
	public String getBalanceType() {
		return balanceType;
	}
	public void setBalanceType(String balanceType) {
		this.balanceType = balanceType;
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
	public String getBalanceID() {
		return balanceID;
	}
	public void setBalanceID(String balanceID) {
		this.balanceID = balanceID;
	}
	
	public List<BalanceDetailVO> getBalanceDetailList() {
		return balanceDetailList;
	}
	public void setBalanceDetailList(List<BalanceDetailVO> balanceDetailList) {
		this.balanceDetailList = balanceDetailList;
	}
	public String getBalanceTypeDesc() {
		return balanceTypeDesc;
	}
	public void setBalanceTypeDesc(String balanceTypeDesc) {
		this.balanceTypeDesc = balanceTypeDesc;
	}
	public String getRefundFlagDesc() {
		return refundFlagDesc;
	}
	public void setRefundFlagDesc(String refundFlagDesc) {
		this.refundFlagDesc = refundFlagDesc;
	}
	
}
