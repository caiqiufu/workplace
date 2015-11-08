package com.apps.esb.service.bss.app.cbs.vo.transferbalancelog;

import java.util.List;

import com.apps.esb.service.bss.app.cbs.vo.recharge.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.CreditChgInfoVO;

public class TransferInfoVO {
	private String tradeTime;
	private String acctKey;
	private String subKey;
	private String primaryIdentity;
	private String transferChannelID;
	private String transferAmount;
	private String oppositePrimaryIdentity;
	private String oppositeAcctKey;
	private String resultCode;
	private List<BalanceChgInfoVO> balanceChgInfos;
	private List<FreeUnitChgInfoVO> freeUnitChgInfos;
	private List<CreditChgInfoVO> creditChgInfos;
	public String getTradeTime() {
		return tradeTime;
	}
	public void setTradeTime(String tradeTime) {
		this.tradeTime = tradeTime;
	}
	public String getAcctKey() {
		return acctKey;
	}
	public void setAcctKey(String acctKey) {
		this.acctKey = acctKey;
	}
	public String getSubKey() {
		return subKey;
	}
	public void setSubKey(String subKey) {
		this.subKey = subKey;
	}
	public String getPrimaryIdentity() {
		return primaryIdentity;
	}
	public void setPrimaryIdentity(String primaryIdentity) {
		this.primaryIdentity = primaryIdentity;
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
	public String getOppositePrimaryIdentity() {
		return oppositePrimaryIdentity;
	}
	public void setOppositePrimaryIdentity(String oppositePrimaryIdentity) {
		this.oppositePrimaryIdentity = oppositePrimaryIdentity;
	}
	public String getOppositeAcctKey() {
		return oppositeAcctKey;
	}
	public void setOppositeAcctKey(String oppositeAcctKey) {
		this.oppositeAcctKey = oppositeAcctKey;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public List<BalanceChgInfoVO> getBalanceChgInfos() {
		return balanceChgInfos;
	}
	public void setBalanceChgInfos(List<BalanceChgInfoVO> balanceChgInfos) {
		this.balanceChgInfos = balanceChgInfos;
	}
	public List<FreeUnitChgInfoVO> getFreeUnitChgInfos() {
		return freeUnitChgInfos;
	}
	public void setFreeUnitChgInfos(List<FreeUnitChgInfoVO> freeUnitChgInfos) {
		this.freeUnitChgInfos = freeUnitChgInfos;
	}
	public List<CreditChgInfoVO> getCreditChgInfos() {
		return creditChgInfos;
	}
	public void setCreditChgInfos(List<CreditChgInfoVO> creditChgInfos) {
		this.creditChgInfos = creditChgInfos;
	}
	
	
}
