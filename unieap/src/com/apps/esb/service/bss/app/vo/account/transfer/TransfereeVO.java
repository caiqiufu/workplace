package com.apps.esb.service.bss.app.vo.account.transfer;

import java.util.List;

import com.apps.esb.service.bss.app.vo.subscriber.chginfo.BalanceChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.CreditChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.FreeResourceChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.LifeCycleChgInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.chginfo.LoanChgInfoVO;
import com.unieap.base.vo.BaseVO;

public class TransfereeVO extends BaseVO{
	private String handlingChargeAmt;
	private LoanChgInfoVO loanChgInfoVO;
	private LifeCycleChgInfoVO lifeCycleChgInfoVO;
	private List<BalanceChgInfoVO> balanceChgInfoList;
	private List<FreeResourceChgInfoVO> freeResourceChgInfoList;
	private List<CreditChgInfoVO> creditChgInfoList;
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
	
	
}
