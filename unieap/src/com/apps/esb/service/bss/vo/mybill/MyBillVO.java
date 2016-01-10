package com.apps.esb.service.bss.vo.mybill;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class MyBillVO extends BaseVO{
	private List<MontlyUsageVO> montlyUsages;

	public List<MontlyUsageVO> getMontlyUsages() {
		return montlyUsages;
	}

	public void setMontlyUsages(List<MontlyUsageVO> montlyUsages) {
		this.montlyUsages = montlyUsages;
	}
	
}
