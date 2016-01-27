package com.apps.esb.service.bss.vo.mshop.cdr;

import com.unieap.base.vo.BaseVO;

public class MshopCdrVO extends BaseVO {
	private String cdrSeq;
	private String flowType;
	private String serviceNumber;
	private String serviceType;
	private String serviceTypeDesc;
	private String startTime;
	private String endTime;
	private String measureUnit;
	private String measureUnitDesc;
	private String duration;
	private String volume;
	private String chargeAmount;
	private String offeringName;
	public String getCdrSeq() {
		return cdrSeq;
	}
	public String getFlowType() {
		return flowType;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public String getServiceType() {
		return serviceType;
	}
	public String getServiceTypeDesc() {
		return serviceTypeDesc;
	}
	public String getStartTime() {
		return startTime;
	}
	public String getEndTime() {
		return endTime;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public String getMeasureUnitDesc() {
		return measureUnitDesc;
	}
	public String getDuration() {
		return duration;
	}
	public String getVolume() {
		return volume;
	}
	public String getOfferingName() {
		return offeringName;
	}
	public void setCdrSeq(String cdrSeq) {
		this.cdrSeq = cdrSeq;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public void setServiceTypeDesc(String serviceTypeDesc) {
		this.serviceTypeDesc = serviceTypeDesc;
	}
	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}
	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public void setMeasureUnitDesc(String measureUnitDesc) {
		this.measureUnitDesc = measureUnitDesc;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public void setVolume(String volume) {
		this.volume = volume;
	}
	public void setOfferingName(String offeringName) {
		this.offeringName = offeringName;
	}
	public String getChargeAmount() {
		return chargeAmount;
	}
	public void setChargeAmount(String chargeAmount) {
		this.chargeAmount = chargeAmount;
	}

}
