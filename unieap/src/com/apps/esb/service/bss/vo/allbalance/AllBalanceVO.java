package com.apps.esb.service.bss.vo.allbalance;

import com.apps.esb.service.bss.vo.databalance.DataBalanceVO;
import com.unieap.base.vo.BaseVO;

public class AllBalanceVO extends BaseVO{
	private VoiceBalanceVO voiceBalance;
	private DataBalanceVO dataBalance;
	private MoneyBalanceVO moneyBalance;
	public VoiceBalanceVO getVoiceBalance() {
		return voiceBalance;
	}
	public void setVoiceBalance(VoiceBalanceVO voiceBalance) {
		this.voiceBalance = voiceBalance;
	}
	public DataBalanceVO getDataBalance() {
		return dataBalance;
	}
	public void setDataBalance(DataBalanceVO dataBalance) {
		this.dataBalance = dataBalance;
	}
	public MoneyBalanceVO getMoneyBalance() {
		return moneyBalance;
	}
	public void setMoneyBalance(MoneyBalanceVO moneyBalance) {
		this.moneyBalance = moneyBalance;
	}
	
	
}
