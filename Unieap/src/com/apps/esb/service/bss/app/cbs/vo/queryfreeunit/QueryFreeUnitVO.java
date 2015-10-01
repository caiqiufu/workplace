package com.apps.esb.service.bss.app.cbs.vo.queryfreeunit;

import java.util.List;

import com.apps.esb.service.bss.handler.ProcessResult;

public class QueryFreeUnitVO {
	public ProcessResult processResult;
	public List<FreeUnitItemVO> freeUnitItemVOList;

	public List<FreeUnitItemVO> getFreeUnitItemVOList() {
		return freeUnitItemVOList;
	}

	public void setFreeUnitItemVOList(List<FreeUnitItemVO> freeUnitItemVOList) {
		this.freeUnitItemVOList = freeUnitItemVOList;
	}

	public ProcessResult getProcessResult() {
		return processResult;
	}

	public void setProcessResult(ProcessResult processResult) {
		this.processResult = processResult;
	}

	
	
}
