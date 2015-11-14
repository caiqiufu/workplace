package com.apps.esb.service.bss.app.cbs.vo.xchangelog;

import java.util.List;

public class QueryXchangeLogVO {
	private List<XchangeInfoVO> xchangeInfos;
	private String totalRowNum;
	private String beginRowNum;
	private String fetchRowNum;

	public List<XchangeInfoVO> getXchangeInfos() {
		return xchangeInfos;
	}

	public void setXchangeInfos(List<XchangeInfoVO> xchangeInfos) {
		this.xchangeInfos = xchangeInfos;
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
