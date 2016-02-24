package com.apps.esb.service.bss.app.vo.account.recharge;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class QueryRechargeLogsVO extends BaseVO{
	private List<RechargeLogVO> rechargeLogList;
	private String totalNum;
	private String beginNum;
	private String fetchNum;
	public List<RechargeLogVO> getRechargeLogList() {
		return rechargeLogList;
	}
	public void setRechargeLogList(List<RechargeLogVO> rechargeLogList) {
		this.rechargeLogList = rechargeLogList;
	}
	public String getTotalNum() {
		return totalNum;
	}
	public void setTotalNum(String totalNum) {
		this.totalNum = totalNum;
	}
	public String getBeginNum() {
		return beginNum;
	}
	public void setBeginNum(String beginNum) {
		this.beginNum = beginNum;
	}
	public String getFetchNum() {
		return fetchNum;
	}
	public void setFetchNum(String fetchNum) {
		this.fetchNum = fetchNum;
	}
	
}
