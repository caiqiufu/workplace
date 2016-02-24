package com.apps.esb.service.bss.app.vo.account.transfer;

import java.util.List;

import com.apps.esb.service.bss.app.vo.subscriber.chginfo.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.CreditChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.FreeResourceChgInfoVO;
import com.unieap.base.vo.BaseVO;

public class TransferLogVO extends BaseVO{
	private String tradeTime;
	private String subscriberId;
	private String accountId;
	private String serviceNumber;
	private String transferChannelID;
	private String transferAmount;
	private String oppositeSubscriberId;
	private String oppositeAccountId;
	private String oppositeServiceNumber;
	private String resultCode;
	private List<BalanceChgInfoVO> balanceChgInfoList;
	private List<FreeResourceChgInfoVO> freeResourceChgInfoList;
	private List<CreditChgInfoVO> creditChgInfoList;
	
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
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
	public String getOppositeSubscriberId() {
		return oppositeSubscriberId;
	}
	public void setOppositeSubscriberId(String oppositeSubscriberId) {
		this.oppositeSubscriberId = oppositeSubscriberId;
	}
	public String getOppositeAccountId() {
		return oppositeAccountId;
	}
	public void setOppositeAccountId(String oppositeAccountId) {
		this.oppositeAccountId = oppositeAccountId;
	}
	public String getOppositeServiceNumber() {
		return oppositeServiceNumber;
	}
	public void setOppositeServiceNumber(String oppositeServiceNumber) {
		this.oppositeServiceNumber = oppositeServiceNumber;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public List<BalanceChgInfoVO> getBalanceChgInfoList() {
		return balanceChgInfoList;
	}
	public void setBalanceChgInfoList(List<BalanceChgInfoVO> balanceChgInfoList) {
		this.balanceChgInfoList = balanceChgInfoList;
	}
	public List<FreeResourceChgInfoVO> getFreeResourceChgInfoList() {
		return freeResourceChgInfoList;
	}
	public void setFreeResourceChgInfoList(List<FreeResourceChgInfoVO> freeResourceChgInfoList) {
		this.freeResourceChgInfoList = freeResourceChgInfoList;
	}
	public List<CreditChgInfoVO> getCreditChgInfoList() {
		return creditChgInfoList;
	}
	public void setCreditChgInfoList(List<CreditChgInfoVO> creditChgInfoList) {
		this.creditChgInfoList = creditChgInfoList;
	}
	
}
