package com.apps.esb.service.bss.app.vo;

import java.util.List;

import com.apps.esb.service.bss.app.cbs.vo.queryaccountbalance.BalanceResultVO;
import com.apps.esb.service.bss.app.cbs.vo.queryfreeunit.FreeUnitItemVO;

public class BalanceInfoVO {
	public String mainBalance;
	public String availableCreditLimit;
	public String dataBalance;
	public List<FreeUnitItemVO> dataFreeUnitItems;
	public String voiceBalance;
	public List<FreeUnitItemVO> voiceFreeUnitItems;
	public String smsBalance;
	public String mmsBalance;
	public List<BalanceResultVO> balanceResultVOList;
	public List<FreeUnitItemVO> freeUnitItemVOList;
	public String getMainBalance() {
		return mainBalance;
	}
	public void setMainBalance(String mainBalance) {
		this.mainBalance = mainBalance;
	}
	public String getDataBalance() {
		return dataBalance;
	}
	public void setDataBalance(String dataBalance) {
		this.dataBalance = dataBalance;
	}
	public String getVoiceBalance() {
		return voiceBalance;
	}
	public void setVoiceBalance(String voiceBalance) {
		this.voiceBalance = voiceBalance;
	}
	public String getSmsBalance() {
		return smsBalance;
	}
	public void setSmsBalance(String smsBalance) {
		this.smsBalance = smsBalance;
	}
	public String getMmsBalance() {
		return mmsBalance;
	}
	public void setMmsBalance(String mmsBalance) {
		this.mmsBalance = mmsBalance;
	}
	public List<FreeUnitItemVO> getFreeUnitItemVOList() {
		return freeUnitItemVOList;
	}
	public void setFreeUnitItemVOList(List<FreeUnitItemVO> freeUnitItemVOList) {
		this.freeUnitItemVOList = freeUnitItemVOList;
	}
	public List<BalanceResultVO> getBalanceResultVOList() {
		return balanceResultVOList;
	}
	public void setBalanceResultVOList(List<BalanceResultVO> balanceResultVOList) {
		this.balanceResultVOList = balanceResultVOList;
	}
	public String getAvailableCreditLimit() {
		return availableCreditLimit;
	}
	public void setAvailableCreditLimit(String availableCreditLimit) {
		this.availableCreditLimit = availableCreditLimit;
	}
	public List<FreeUnitItemVO> getDataFreeUnitItems() {
		return dataFreeUnitItems;
	}
	public void setDataFreeUnitItems(List<FreeUnitItemVO> dataFreeUnitItems) {
		this.dataFreeUnitItems = dataFreeUnitItems;
	}
	public List<FreeUnitItemVO> getVoiceFreeUnitItems() {
		return voiceFreeUnitItems;
	}
	public void setVoiceFreeUnitItems(List<FreeUnitItemVO> voiceFreeUnitItems) {
		this.voiceFreeUnitItems = voiceFreeUnitItems;
	}
	
}
