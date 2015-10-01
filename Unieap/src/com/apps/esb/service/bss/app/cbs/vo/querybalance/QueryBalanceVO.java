package com.apps.esb.service.bss.app.cbs.vo.querybalance;

import java.util.List;

import com.apps.esb.service.bss.handler.ProcessResult;

public class QueryBalanceVO {
	public ProcessResult processResult;
	public List<BalanceAccountVO> balanceAccountList;

	public List<BalanceAccountVO> getBalanceAccountList() {
		return balanceAccountList;
	}

	public void setBalanceAccountList(List<BalanceAccountVO> balanceAccountList) {
		this.balanceAccountList = balanceAccountList;
	}

	public ProcessResult getProcessResult() {
		return processResult;
	}

	public void setProcessResult(ProcessResult processResult) {
		this.processResult = processResult;
	}

	

}
