package com.apps.esb.service.bss.app.vo;

import java.util.Map;

public class CdrCycleSummaryVO {
	private String billCycleID;
	private String totalChargeAmt;
	private Map<String,CdrCategoryVO> categoryCdrSummarys;
	public String getBillCycleID() {
		return billCycleID;
	}
	public void setBillCycleID(String billCycleID) {
		this.billCycleID = billCycleID;
	}
	public String getTotalChargeAmt() {
		return totalChargeAmt;
	}
	public void setTotalChargeAmt(String totalChargeAmt) {
		this.totalChargeAmt = totalChargeAmt;
	}
	public Map<String, CdrCategoryVO> getCategoryCdrSummarys() {
		return categoryCdrSummarys;
	}
	public void setCategoryCdrSummarys(Map<String, CdrCategoryVO> categoryCdrSummarys) {
		this.categoryCdrSummarys = categoryCdrSummarys;
	}
	
}
