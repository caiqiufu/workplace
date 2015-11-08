package com.apps.esb.service.bss.app.cbs.vo.recharge;

import java.util.List;

public class RechargeBonusVO {
	private List<FreeUnitItemVO> freeUnitItems;
	private List<BalanceVO> balances;
	public List<FreeUnitItemVO> getFreeUnitItems() {
		return freeUnitItems;
	}
	public void setFreeUnitItems(List<FreeUnitItemVO> freeUnitItems) {
		this.freeUnitItems = freeUnitItems;
	}
	public List<BalanceVO> getBalances() {
		return balances;
	}
	public void setBalances(List<BalanceVO> balances) {
		this.balances = balances;
	}
	
}
