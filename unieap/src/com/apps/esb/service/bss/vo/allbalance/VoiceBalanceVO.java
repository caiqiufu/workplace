package com.apps.esb.service.bss.vo.allbalance;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class VoiceBalanceVO extends BaseVO{
	private String totalAmount;
	private String remaningAmount;
	private String usageAmount;
	private String dailyUsageAmount;
	private List<MyBalanceDetailVO> balanceDetails;
	public String getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(String totalAmount) {
		this.totalAmount = totalAmount;
	}
	public String getRemaningAmount() {
		return remaningAmount;
	}
	public void setRemaningAmount(String remaningAmount) {
		this.remaningAmount = remaningAmount;
	}
	public String getUsageAmount() {
		return usageAmount;
	}
	public void setUsageAmount(String usageAmount) {
		this.usageAmount = usageAmount;
	}
	public String getDailyUsageAmount() {
		return dailyUsageAmount;
	}
	public void setDailyUsageAmount(String dailyUsageAmount) {
		this.dailyUsageAmount = dailyUsageAmount;
	}
	public List<MyBalanceDetailVO> getBalanceDetails() {
		return balanceDetails;
	}
	public void setBalanceDetails(List<MyBalanceDetailVO> balanceDetails) {
		this.balanceDetails = balanceDetails;
	}
	
}
