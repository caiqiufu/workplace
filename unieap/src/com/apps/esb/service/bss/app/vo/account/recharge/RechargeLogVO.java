package com.apps.esb.service.bss.app.vo.account.recharge;

import java.util.List;

import com.apps.esb.service.bss.app.vo.subscriber.chginfo.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.CreditChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.FreeResourceChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.LifeCycleChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.LoanChgInfoVO;
import com.unieap.base.vo.BaseVO;

public class RechargeLogVO extends BaseVO{
	private String tradeTime;
	private String subscriberId;
	private String accountId;
	private String serviceNumber;
	private String transId;
	private String rechargeSerialNo;
	private String rechargeAmount;
	private String originalAmount;
	private String currentAmount;
	private String rechargeTax;
	private String rechargePenalty;
	private String rechargeType;
	private String rechargeTypeDesc;
	private String rechargeChannelID;
	private String rechargeChannelName;
	private String resultCode;
	private List<LifeCycleChgInfoVO> lifeCycleChgInfoList;
	private List<BalanceChgInfoVO> balanceChgInfoList;
	private List<FreeResourceChgInfoVO> freeResourceChgInfoList;
	private List<CreditChgInfoVO> creditChgInfoList;
	private List<RechargeBonusVO> rechargeBonusList;
	private List<LoanChgInfoVO> loanChgInfoList;
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
	
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public String getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public String getOriginalAmount() {
		return originalAmount;
	}
	public void setOriginalAmount(String originalAmount) {
		this.originalAmount = originalAmount;
	}
	public String getRechargeTax() {
		return rechargeTax;
	}
	public void setRechargeTax(String rechargeTax) {
		this.rechargeTax = rechargeTax;
	}
	public String getRechargePenalty() {
		return rechargePenalty;
	}
	public void setRechargePenalty(String rechargePenalty) {
		this.rechargePenalty = rechargePenalty;
	}
	public String getRechargeType() {
		return rechargeType;
	}
	public void setRechargeType(String rechargeType) {
		this.rechargeType = rechargeType;
	}
	public String getRechargeChannelID() {
		return rechargeChannelID;
	}
	public void setRechargeChannelID(String rechargeChannelID) {
		this.rechargeChannelID = rechargeChannelID;
	}
	public String getResultCode() {
		return resultCode;
	}
	public void setResultCode(String resultCode) {
		this.resultCode = resultCode;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public List<LifeCycleChgInfoVO> getLifeCycleChgInfoList() {
		return lifeCycleChgInfoList;
	}
	public void setLifeCycleChgInfoList(List<LifeCycleChgInfoVO> lifeCycleChgInfoList) {
		this.lifeCycleChgInfoList = lifeCycleChgInfoList;
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
	public List<RechargeBonusVO> getRechargeBonusList() {
		return rechargeBonusList;
	}
	public void setRechargeBonusList(List<RechargeBonusVO> rechargeBonusList) {
		this.rechargeBonusList = rechargeBonusList;
	}
	public List<LoanChgInfoVO> getLoanChgInfoList() {
		return loanChgInfoList;
	}
	public void setLoanChgInfoList(List<LoanChgInfoVO> loanChgInfoList) {
		this.loanChgInfoList = loanChgInfoList;
	}
	public String getRechargeSerialNo() {
		return rechargeSerialNo;
	}
	public void setRechargeSerialNo(String rechargeSerialNo) {
		this.rechargeSerialNo = rechargeSerialNo;
	}
	public String getRechargeTypeDesc() {
		return rechargeTypeDesc;
	}
	public String getRechargeChannelName() {
		return rechargeChannelName;
	}
	public void setRechargeTypeDesc(String rechargeTypeDesc) {
		this.rechargeTypeDesc = rechargeTypeDesc;
	}
	public void setRechargeChannelName(String rechargeChannelName) {
		this.rechargeChannelName = rechargeChannelName;
	}
	public String getCurrentAmount() {
		return currentAmount;
	}
	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}
	
}
