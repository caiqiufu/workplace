package com.apps.esb.service.bss.app.cbs.vo.transferbalance;

import java.util.List;

import com.apps.esb.service.bss.app.cbs.vo.recharge.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.CreditChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LifeCycleChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.recharge.LoanChgInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.transferbalancelog.FreeUnitChgInfoVO;

public class TransfereeVO {
	private List<BalanceChgInfoVO> balanceChgInfos;
	private String handlingChargeAmt;
	private LoanChgInfoVO loanChgInfoVO;
	private LifeCycleChgInfoVO lifeCycleChgInfoVO;
	private List<FreeUnitChgInfoVO> freeUnitChgInfos;
	private List<CreditChgInfoVO> creditChgInfos;
	public List<BalanceChgInfoVO> getBalanceChgInfos() {
		return balanceChgInfos;
	}
	public void setBalanceChgInfos(List<BalanceChgInfoVO> balanceChgInfos) {
		this.balanceChgInfos = balanceChgInfos;
	}
	public String getHandlingChargeAmt() {
		return handlingChargeAmt;
	}
	public void setHandlingChargeAmt(String handlingChargeAmt) {
		this.handlingChargeAmt = handlingChargeAmt;
	}
	public LoanChgInfoVO getLoanChgInfoVO() {
		return loanChgInfoVO;
	}
	public void setLoanChgInfoVO(LoanChgInfoVO loanChgInfoVO) {
		this.loanChgInfoVO = loanChgInfoVO;
	}
	public LifeCycleChgInfoVO getLifeCycleChgInfoVO() {
		return lifeCycleChgInfoVO;
	}
	public void setLifeCycleChgInfoVO(LifeCycleChgInfoVO lifeCycleChgInfoVO) {
		this.lifeCycleChgInfoVO = lifeCycleChgInfoVO;
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
