package com.apps.esb.service.bss.app.vo.account.xchange;

import com.unieap.base.vo.BaseVO;

public class XchangeLogVO extends BaseVO{
	private String logId;
	private String tradeTime;
	private String applierNumber;
	private String receiverNumber;
	private String deductAmount;
	private String transferFee;
	private String bonusAmount;
	public String getLogId() {
		return logId;
	}
	public void setLogId(String logId) {
		this.logId = logId;
	}
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getApplierNumber() {
		return applierNumber;
	}
	public void setApplierNumber(String applierNumber) {
		this.applierNumber = applierNumber;
	}
	public String getReceiverNumber() {
		return receiverNumber;
	}
	public void setReceiverNumber(String receiverNumber) {
		this.receiverNumber = receiverNumber;
	}
	public String getDeductAmount() {
		return deductAmount;
	}
	public void setDeductAmount(String deductAmount) {
		this.deductAmount = deductAmount;
	}
	public String getTransferFee() {
		return transferFee;
	}
	public void setTransferFee(String transferFee) {
		this.transferFee = transferFee;
	}
	public String getBonusAmount() {
		return bonusAmount;
	}
	public void setBonusAmount(String bonusAmount) {
		this.bonusAmount = bonusAmount;
	}
	
}
