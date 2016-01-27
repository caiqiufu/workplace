package com.apps.esb.service.bss.vo.macre.allbalance;

import com.apps.esb.service.bss.vo.macre.databalance.DataBalanceVO;
import com.unieap.base.vo.BaseVO;

public class AllBalanceVO extends BaseVO{
	private VoiceBalanceVO voiceBalance;
	private DataBalanceVO dataBalance;
	private MoneyBalanceVO moneyBalance;
	private SmsBalanceVO smsBalance;
	private MmsBalanceVO mmsBalance;
	private ContentBalanceVO contentBalance;
	private PointBalanceVO pointBalance;
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
	public SmsBalanceVO getSmsBalance() {
		return smsBalance;
	}
	public void setSmsBalance(SmsBalanceVO smsBalance) {
		this.smsBalance = smsBalance;
	}
	public MmsBalanceVO getMmsBalance() {
		return mmsBalance;
	}
	public void setMmsBalance(MmsBalanceVO mmsBalance) {
		this.mmsBalance = mmsBalance;
	}
	public ContentBalanceVO getContentBalance() {
		return contentBalance;
	}
	public void setContentBalance(ContentBalanceVO contentBalance) {
		this.contentBalance = contentBalance;
	}
	public PointBalanceVO getPointBalance() {
		return pointBalance;
	}
	public void setPointBalance(PointBalanceVO pointBalance) {
		this.pointBalance = pointBalance;
	}
	
	
}
