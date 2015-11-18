package com.apps.esb.service.bss.app.vo;

public class TransferBalanceLogVO {
	private String tradeTime;
	private String transferChannelID;
	private String transferAmount;
	private String resultCode;
	private String transferorNumber;
	private String transfereeNumber;
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getTransferChannelID() {
		return transferChannelID;
	}
	public void setTransferChannelID(String transferChannelID) {
		this.transferChannelID = transferChannelID;
	}
	public String getTransferAmount() {
		return transferAmount;
	}
	public void setTransferAmount(String transferAmount) {
		this.transferAmount = transferAmount;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getTransferorNumber() {
		return transferorNumber;
	}
	public void setTransferorNumber(String transferorNumber) {
		this.transferorNumber = transferorNumber;
	}
	public String getTransfereeNumber() {
		return transfereeNumber;
	}
	public void setTransfereeNumber(String transfereeNumber) {
		this.transfereeNumber = transfereeNumber;
	}
	
}
