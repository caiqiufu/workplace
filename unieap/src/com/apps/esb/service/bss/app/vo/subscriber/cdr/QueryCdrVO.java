package com.apps.esb.service.bss.app.vo.subscriber.cdr;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class QueryCdrVO extends BaseVO{
	private List<CdrSummaryVO> cdrSummaryList;
	private List<CdrInfoVO> cdrInfoList;
	private String totalNum;
	private String beginNum;
	private String fetchNum;
	public List<CdrSummaryVO> getCdrSummaryList() {
		return cdrSummaryList;
	}
	public void setCdrSummaryList(List<CdrSummaryVO> cdrSummaryList) {
		this.cdrSummaryList = cdrSummaryList;
	}
	public List<CdrInfoVO> getCdrInfoList() {
		return cdrInfoList;
	}
	public void setCdrInfoList(List<CdrInfoVO> cdrInfoList) {
		this.cdrInfoList = cdrInfoList;
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
