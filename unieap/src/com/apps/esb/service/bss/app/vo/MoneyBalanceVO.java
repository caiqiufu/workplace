package com.apps.esb.service.bss.app.vo;

import java.util.List;

public class MoneyBalanceVO {

	/**
	 * main balance remaining
	 */
	private String mainRemaningAmount;
	private String mainUsageAmount;
	private String mainExpiryTime;
	private String creditLimitAmount;
	private List<MyBalanceDetailVO> mainBalanceDetails;
	private String bonusAmount;
	private String bonusRemaningAmount;
	private String bonusUsageAmount;
	private List<MyBalanceDetailVO> bonusBalanceDetails;
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
	public String getBonusAmount() {
		return bonusAmount;
	}
	public void setBonusAmount(String bonusAmount) {
		this.bonusAmount = bonusAmount;
	}
	public String getBonusRemaningAmount() {
		return bonusRemaningAmount;
	}
	public void setBonusRemaningAmount(String bonusRemaningAmount) {
		this.bonusRemaningAmount = bonusRemaningAmount;
	}
	public String getBonusUsageAmount() {
		return bonusUsageAmount;
	}
	public void setBonusUsageAmount(String bonusUsageAmount) {
		this.bonusUsageAmount = bonusUsageAmount;
	}
	public List<MyBalanceDetailVO> getBonusBalanceDetails() {
		return bonusBalanceDetails;
	}
	public void setBonusBalanceDetails(List<MyBalanceDetailVO> bonusBalanceDetails) {
		this.bonusBalanceDetails = bonusBalanceDetails;
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
