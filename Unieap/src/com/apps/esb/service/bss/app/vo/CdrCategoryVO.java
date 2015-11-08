package com.apps.esb.service.bss.app.vo;

import java.util.List;

import com.apps.esb.service.bss.app.cbs.vo.queryCdr.CdrSummaryVO;

public class CdrCategoryVO {
	private String categoryType;
	private String categoryTypeDesc;
	private String usageAmount;
	private List<CdrSummaryVO> cdrSummaryList;
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getCategoryTypeDesc() {
		return categoryTypeDesc;
	}
	public void setCategoryTypeDesc(String categoryTypeDesc) {
		this.categoryTypeDesc = categoryTypeDesc;
	}
	public String getUsageAmount() {
		return usageAmount;
	}
	public void setUsageAmount(String usageAmount) {
		this.usageAmount = usageAmount;
	}
	public List<CdrSummaryVO> getCdrSummaryList() {
		return cdrSummaryList;
	}
	public void setCdrSummaryList(List<CdrSummaryVO> cdrSummaryList) {
		this.cdrSummaryList = cdrSummaryList;
	}
	
}
