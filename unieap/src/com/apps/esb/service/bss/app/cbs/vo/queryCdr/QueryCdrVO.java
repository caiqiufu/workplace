package com.apps.esb.service.bss.app.cbs.vo.queryCdr;

import java.util.List;

public class QueryCdrVO {
	private List<CdrSummaryVO> cdrSummaryList;
	private List<CdrInfoVO> cdrInfoList;
	private String totalCDRNum;
	private String beginRowNum;
	private String fetchRowNum;
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
	public String getTotalCDRNum() {
		return totalCDRNum;
	}
	public void setTotalCDRNum(String totalCDRNum) {
		this.totalCDRNum = totalCDRNum;
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
