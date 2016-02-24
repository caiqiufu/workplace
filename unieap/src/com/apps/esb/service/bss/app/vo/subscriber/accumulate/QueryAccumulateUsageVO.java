package com.apps.esb.service.bss.app.vo.subscriber.accumulate;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class QueryAccumulateUsageVO extends BaseVO{
	private List<AccumulateUsageVO> accumulateUsageList;

	public List<AccumulateUsageVO> getAccumulateUsageList() {
		return accumulateUsageList;
	}

	public void setAccumulateUsageList(List<AccumulateUsageVO> accumulateUsageList) {
		this.accumulateUsageList = accumulateUsageList;
	}
	
	
}
