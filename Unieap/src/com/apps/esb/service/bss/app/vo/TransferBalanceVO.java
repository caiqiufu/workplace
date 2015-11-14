package com.apps.esb.service.bss.app.vo;

public class TransferBalanceVO {
	private BalanceChgInfoVO balanceChgInfoVO;
	private String handingChargeAmt;
	
	
	public BalanceChgInfoVO getBalanceChgInfoVO() {
		return balanceChgInfoVO;
	}
	public void setBalanceChgInfoVO(BalanceChgInfoVO balanceChgInfoVO) {
		this.balanceChgInfoVO = balanceChgInfoVO;
	}
	public String getHandingChargeAmt() {
		return handingChargeAmt;
	}
	public void setHandingChargeAmt(String handingChargeAmt) {
		this.handingChargeAmt = handingChargeAmt;
	}
	
}
