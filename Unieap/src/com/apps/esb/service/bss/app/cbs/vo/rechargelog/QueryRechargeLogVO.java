package com.apps.esb.service.bss.app.cbs.vo.rechargelog;

import java.util.List;

public class QueryRechargeLogVO {
	private String totalRowNum;
	private String beginRowNum;
	private String fetchRowNum;
	public List<RechargeInfoVO> rechargeInfoList;

	public List<RechargeInfoVO> getRechargeInfoList() {
		return rechargeInfoList;
	}

	public void setRechargeInfoList(List<RechargeInfoVO> rechargeInfoList) {
		this.rechargeInfoList = rechargeInfoList;
	}

	public String getTotalRowNum() {
		return totalRowNum;
	}

	public void setTotalRowNum(String totalRowNum) {
		this.totalRowNum = totalRowNum;
	}

	public String getBeginRowNum() {
		return beginRowNum;
	}

	public void setBeginRowNum(String beginRowNum) {
		this.beginRowNum = beginRowNum;
	}

	public String getFetchRowNum() {
		return fetchRowNum;
	}

	public void setFetchRowNum(String fetchRowNum) {
		this.fetchRowNum = fetchRowNum;
	}
	
}
