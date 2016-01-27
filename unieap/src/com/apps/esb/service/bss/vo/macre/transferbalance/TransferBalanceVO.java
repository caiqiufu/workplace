package com.apps.esb.service.bss.vo.macre.transferbalance;

import com.unieap.base.vo.BaseVO;

public class TransferBalanceVO extends BaseVO{
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
