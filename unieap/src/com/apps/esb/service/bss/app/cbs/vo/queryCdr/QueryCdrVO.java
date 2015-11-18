package com.apps.esb.service.bss.app.cbs.vo.queryCdr;

import java.util.List;

public class QueryCdrVO {
	private List<CdrSummaryVO> cdrSummaryList;
	private List<CdrInfoVO> cdrInfoList;
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
	
}
