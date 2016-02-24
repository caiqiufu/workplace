package com.apps.esb.service.bss.app.vo.subscriber.cdr;

import com.unieap.base.vo.BaseVO;

public class CdrInfoVO extends BaseVO{
	private String cdrSeq;
	private String serviceCategory;
	private String serviceCategoryDesc;
	private String serviceType;
	private String serviceTypeDesc;
	private String serviceNumber;
	private String otherNumber;
	// 1:MO,2:MT,3:CF,4:Others
	private String flowType;
	/**
	 * 20151001
	 */
	private String billCycleID;
	
	private String startTime;
	private String endTime;
	
	private String actualVolume;
	private String actualChargeAmt;
	private String measureUnit;
	private String measureUnitDesc;
	
	private String chargeDuration;
	private String destinationNetworkName;
	private String mainBalanceDeduct;
	private String mainBalaneLeft;
	private String offeringName;
	public String getCdrSeq() {
		return cdrSeq;
	}
	public void setCdrSeq(String cdrSeq) {
		this.cdrSeq = cdrSeq;
	}
	public String getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getOtherNumber() {
		return otherNumber;
	}
	public void setOtherNumber(String otherNumber) {
		this.otherNumber = otherNumber;
	}
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public String getBillCycleID() {
		return billCycleID;
	}
	public void setBillCycleID(String billCycleID) {
		this.billCycleID = billCycleID;
	}
	public String getStartTime() {
		return startTime;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public String getActualVolume() {
		return actualVolume;
	}
	public void setActualVolume(String actualVolume) {
		this.actualVolume = actualVolume;
	}
	public String getActualChargeAmt() {
		return actualChargeAmt;
	}
	public void setActualChargeAmt(String actualChargeAmt) {
		this.actualChargeAmt = actualChargeAmt;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public String getChargeDuration() {
		return chargeDuration;
	}
	public void setChargeDuration(String chargeDuration) {
		this.chargeDuration = chargeDuration;
	}
	public String getDestinationNetworkName() {
		return destinationNetworkName;
	}
	public void setDestinationNetworkName(String destinationNetworkName) {
		this.destinationNetworkName = destinationNetworkName;
	}
	public String getMainBalanceDeduct() {
		return mainBalanceDeduct;
	}
	public void setMainBalanceDeduct(String mainBalanceDeduct) {
		this.mainBalanceDeduct = mainBalanceDeduct;
	}
	public String getMainBalaneLeft() {
		return mainBalaneLeft;
	}
	public void setMainBalaneLeft(String mainBalaneLeft) {
		this.mainBalaneLeft = mainBalaneLeft;
	}
	public String getServiceCategoryDesc() {
		return serviceCategoryDesc;
	}
	public void setServiceCategoryDesc(String serviceCategoryDesc) {
		this.serviceCategoryDesc = serviceCategoryDesc;
	}
	public String getServiceTypeDesc() {
		return serviceTypeDesc;
	}
	public void setServiceTypeDesc(String serviceTypeDesc) {
		this.serviceTypeDesc = serviceTypeDesc;
	}
	public String getMeasureUnitDesc() {
		return measureUnitDesc;
	}
	public void setMeasureUnitDesc(String measureUnitDesc) {
		this.measureUnitDesc = measureUnitDesc;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public String getOfferingName() {
		return offeringName;
	}
	public void setOfferingName(String offeringName) {
		this.offeringName = offeringName;
	}
	
	
}
