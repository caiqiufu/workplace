package com.apps.esb.service.bss.vo.mshop.recharge;

import com.unieap.base.vo.BaseVO;

public class MshopRechargeLogVO extends BaseVO{
	private String serviceNumber;
	private String rechargeType;
	private String rechargeDate;
	private String cardSequence;
	private String rechargeAmount;
	private String rechargeChannel;
	private String originalBalance;
	private String currentBalance;
	private String barringExpiryTime;
	public String getServiceNumber() {
		return serviceNumber;
	}
	public String getRechargeType() {
		return rechargeType;
	}
	public String getRechargeDate() {
		return rechargeDate;
	}
	public String getCardSequence() {
		return cardSequence;
	}
	public String getRechargeAmount() {
		return rechargeAmount;
	}
	public String getRechargeChannel() {
		return rechargeChannel;
	}
	public String getOriginalBalance() {
		return originalBalance;
	}
	public String getCurrentBalance() {
		return currentBalance;
	}
	public String getBarringExpiryTime() {
		return barringExpiryTime;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}
	public void setRechargeDate(String rechargeDate) {
		this.rechargeDate = rechargeDate;
	}
	public void setCardSequence(String cardSequence) {
		this.cardSequence = cardSequence;
	}
	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public void setRechargeChannel(String rechargeChannel) {
		this.rechargeChannel = rechargeChannel;
	}
	public void setOriginalBalance(String originalBalance) {
		this.originalBalance = originalBalance;
	}
	public void setCurrentBalance(String currentBalance) {
		this.currentBalance = currentBalance;
	}
	public void setBarringExpiryTime(String barringExpiryTime) {
		this.barringExpiryTime = barringExpiryTime;
	}
	
}
