package com.apps.esb.service.bss.app.cbs.vo.queryaccumulateusage;

import java.util.ArrayList;
import java.util.List;

public class AccumulationUsageVO {
	private List<AccmUsageVO> accmUsageList = new ArrayList<AccmUsageVO>();

	public List<AccmUsageVO> getAccmUsageList() {
		return accmUsageList;
	}

	public void setAccmUsageList(List<AccmUsageVO> accmUsageList) {
		this.accmUsageList = accmUsageList;
	}
	
}
