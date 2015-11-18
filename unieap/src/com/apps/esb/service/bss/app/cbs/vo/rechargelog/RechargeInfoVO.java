package com.apps.esb.service.bss.app.cbs.vo.rechargelog;

import java.util.List;

import com.apps.esb.service.bss.app.cbs.vo.recharge.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.CreditChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LifeCycleChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.RechargeBonusVO;

public class RechargeInfoVO {
	private String tradeTime;
	private String acctKey;
	private String subKey;
	private String primaryIdentity;
	private String transID;
	private String rechargeAmount;
	private String oriAmount;
	private String rechargeTax;
	private String rechargePenalty;
	private String rechargeType;
	private String rechargeChannelID;
	private String resultCode;
	private List<LifeCycleChgInfoVO> lifeCycleChgInfoList;
	private List<BalanceChgInfoVO> balanceChgInfoList;
	private List<RechargeBonusVO> rechargeBonusList;
	private List<CreditChgInfoVO> creditChgInfoList;
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
	public String getTransID() {
		return transID;
	}
	public void setTransID(String transID) {
		this.transID = transID;
	}
	public String getRechargeAmount() {
		return rechargeAmount;
	}
	public void setRechargeAmount(String rechargeAmount) {
		this.rechargeAmount = rechargeAmount;
	}
	public String getOriAmount() {
		return oriAmount;
	}
	public void setOriAmount(String oriAmount) {
		this.oriAmount = oriAmount;
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
	public List<RechargeBonusVO> getRechargeBonusList() {
		return rechargeBonusList;
	}
	public void setRechargeBonusList(List<RechargeBonusVO> rechargeBonusList) {
		this.rechargeBonusList = rechargeBonusList;
	}
	public List<CreditChgInfoVO> getCreditChgInfoList() {
		return creditChgInfoList;
	}
	public void setCreditChgInfoList(List<CreditChgInfoVO> creditChgInfoList) {
		this.creditChgInfoList = creditChgInfoList;
	}
	
}
