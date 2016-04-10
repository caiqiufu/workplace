package com.apps.esb.service.bss.customize.smart.app.cbs.vo.queryofferingrentcycle;

public class OfferingRentCycleVO {
	private String offeringOnwer;
	private OfferingKeyVO offeringKeyVO;
	private String openDay;
	private String endDay;
	private String rentAmount;
	private String processedCycleNum;
	private String successCycleNum;
	public String getOfferingOnwer() {
		return offeringOnwer;
	}
	public void setOfferingOnwer(String offeringOnwer) {
		this.offeringOnwer = offeringOnwer;
	}
	public String getOpenDay() {
		return openDay;
	}
	public void setOpenDay(String openDay) {
		this.openDay = openDay;
	}
	public String getEndDay() {
		return endDay;
	}
	public void setEndDay(String endDay) {
		this.endDay = endDay;
	}
	public String getRentAmount() {
		return rentAmount;
	}
	public void setRentAmount(String rentAmount) {
		this.rentAmount = rentAmount;
	}
	public String getProcessedCycleNum() {
		return processedCycleNum;
	}
	public void setProcessedCycleNum(String processedCycleNum) {
		this.processedCycleNum = processedCycleNum;
	}
	public String getSuccessCycleNum() {
		return successCycleNum;
	}
	public void setSuccessCycleNum(String successCycleNum) {
		this.successCycleNum = successCycleNum;
	}
	public OfferingKeyVO getOfferingKeyVO() {
		return offeringKeyVO;
	}
	public void setOfferingKeyVO(OfferingKeyVO offeringKeyVO) {
		this.offeringKeyVO = offeringKeyVO;
	}
	
}
