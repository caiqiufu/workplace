package com.apps.esb.service.bss.app.vo.account.xchange;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class QueryXchangeLogsVO extends BaseVO{
	private List<XchangeLogVO> xchangeLogList;
	private String totalNum;
	private String beginNum;
	private String fetchNum;
	public List<XchangeLogVO> getXchangeLogList() {
		return xchangeLogList;
	}
	public void setXchangeLogList(List<XchangeLogVO> xchangeLogList) {
		this.xchangeLogList = xchangeLogList;
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
