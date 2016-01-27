package com.apps.esb.service.bss.vo.macre.allbalance;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class MoneyBalanceVO extends BaseVO{

	/**
	 * main balance remaining
	 */
	private String mainRemaningAmount;
	private String mainUsageAmount;
	private String mainExpiryTime;
	private String creditLimitAmount;
	private String unbilledAmount;
	private List<MyBalanceDetailVO> mainBalanceDetails;
	public String getMainRemaningAmount() {
		return mainRemaningAmount;
	}
	public void setMainRemaningAmount(String mainRemaningAmount) {
		this.mainRemaningAmount = mainRemaningAmount;
	}
	public String getMainUsageAmount() {
		return mainUsageAmount;
	}
	public void setMainUsageAmount(String mainUsageAmount) {
		this.mainUsageAmount = mainUsageAmount;
	}
	public List<MyBalanceDetailVO> getMainBalanceDetails() {
		return mainBalanceDetails;
	}
	public void setMainBalanceDetails(List<MyBalanceDetailVO> mainBalanceDetails) {
		this.mainBalanceDetails = mainBalanceDetails;
	}
	
	public String getUnbilledAmount() {
		return unbilledAmount;
	}
	public void setUnbilledAmount(String unbilledAmount) {
		this.unbilledAmount = unbilledAmount;
	}
	public String getMainExpiryTime() {
		return mainExpiryTime;
	}
	public void setMainExpiryTime(String mainExpiryTime) {
		this.mainExpiryTime = mainExpiryTime;
	}
	public String getCreditLimitAmount() {
		return creditLimitAmount;
	}
	public void setCreditLimitAmount(String creditLimitAmount) {
		this.creditLimitAmount = creditLimitAmount;
	}
	
}
