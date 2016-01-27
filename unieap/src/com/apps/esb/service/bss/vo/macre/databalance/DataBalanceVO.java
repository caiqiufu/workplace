package com.apps.esb.service.bss.vo.macre.databalance;

import java.util.List;

import com.apps.esb.service.bss.vo.macre.allbalance.MyBalanceDetailVO;
import com.unieap.base.vo.BaseVO;

public class DataBalanceVO extends BaseVO {
	private String totalAmount;
	private String remaningAmount ;
	private String usageAmount ;
	private String dailyUsageAmount;
	private String websiteAmount;
	private String songAmount;
	private String vedioAmount ;
	private String chatAmount;
	private String readAmount ;
	private String emailAmount;
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
	public String getWebsiteAmount() {
		return websiteAmount;
	}
	public void setWebsiteAmount(String websiteAmount) {
		this.websiteAmount = websiteAmount;
	}
	public String getSongAmount() {
		return songAmount;
	}
	public void setSongAmount(String songAmount) {
		this.songAmount = songAmount;
	}
	
	public String getVedioAmount() {
		return vedioAmount;
	}
	public void setVedioAmount(String vedioAmount) {
		this.vedioAmount = vedioAmount;
	}
	public String getChatAmount() {
		return chatAmount;
	}
	public void setChatAmount(String chatAmount) {
		this.chatAmount = chatAmount;
	}
	public String getReadAmount() {
		return readAmount;
	}
	public void setReadAmount(String readAmount) {
		this.readAmount = readAmount;
	}
	public String getEmailAmount() {
		return emailAmount;
	}
	public void setEmailAmount(String emailAmount) {
		this.emailAmount = emailAmount;
	}
	public List<MyBalanceDetailVO> getBalanceDetails() {
		return balanceDetails;
	}
	public void setBalanceDetails(List<MyBalanceDetailVO> balanceDetails) {
		this.balanceDetails = balanceDetails;
	}
	
	
}
