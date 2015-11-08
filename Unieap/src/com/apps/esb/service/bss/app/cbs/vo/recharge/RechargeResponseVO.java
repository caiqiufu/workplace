package com.apps.esb.service.bss.app.cbs.vo.recharge;

import java.util.List;

public class RechargeResponseVO {
	private String rechargeSerialNo;
	private List<BalanceChgInfoVO> balanceChgInfos;
	private LoanChgInfoVO loanChgInfoVO;
	private RechargeBonusVO rechargeBonusVO;
	private LifeCycleChgInfoVO lifeCycleChgInfoVO;
	private List<CreditChgInfoVO> creditChgInfos;
	public String getRechargeSerialNo() {
		return rechargeSerialNo;
	}
	public void setRechargeSerialNo(String rechargeSerialNo) {
		this.rechargeSerialNo = rechargeSerialNo;
	}
	public List<BalanceChgInfoVO> getBalanceChgInfos() {
		return balanceChgInfos;
	}
	public void setBalanceChgInfos(List<BalanceChgInfoVO> balanceChgInfos) {
		this.balanceChgInfos = balanceChgInfos;
	}
	public LoanChgInfoVO getLoanChgInfoVO() {
		return loanChgInfoVO;
	}
	public void setLoanChgInfoVO(LoanChgInfoVO loanChgInfoVO) {
		this.loanChgInfoVO = loanChgInfoVO;
	}
	public RechargeBonusVO getRechargeBonusVO() {
		return rechargeBonusVO;
	}
	public void setRechargeBonusVO(RechargeBonusVO rechargeBonusVO) {
		this.rechargeBonusVO = rechargeBonusVO;
	}
	public LifeCycleChgInfoVO getLifeCycleChgInfoVO() {
		return lifeCycleChgInfoVO;
	}
	public void setLifeCycleChgInfoVO(LifeCycleChgInfoVO lifeCycleChgInfoVO) {
		this.lifeCycleChgInfoVO = lifeCycleChgInfoVO;
	}
	public List<CreditChgInfoVO> getCreditChgInfos() {
		return creditChgInfos;
	}
	public void setCreditChgInfos(List<CreditChgInfoVO> creditChgInfos) {
		this.creditChgInfos = creditChgInfos;
	}
	
	
	
}
