package com.apps.esb.service.bss.app.vo.account.recharge;

import java.util.List;

import com.apps.esb.service.bss.app.vo.account.balance.BalanceVO;
import com.apps.esb.service.bss.app.vo.subscriber.freeresource.FreeResourceVO;
import com.unieap.base.vo.BaseVO;

public class RechargeBonusVO extends BaseVO{
	private List<FreeResourceVO> freeResourceList;
	private List<BalanceVO> balanceList;
	public List<FreeResourceVO> getFreeResourceList() {
		return freeResourceList;
	}
	public void setFreeResourceList(List<FreeResourceVO> freeResourceList) {
		this.freeResourceList = freeResourceList;
	}
	public List<BalanceVO> getBalanceList() {
		return balanceList;
	}
	public void setBalanceList(List<BalanceVO> balanceList) {
		this.balanceList = balanceList;
	}
	
	
}
