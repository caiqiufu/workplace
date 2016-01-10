package com.apps.esb.service.bss.vo.cdr;

import com.unieap.base.vo.BaseVO;

public class CdrDetailVO extends BaseVO{
	private String serviceTypeName;
	private String otherNumber;
	private String startTime;
	private String endTime;
	private String actualVolume;
	private String actualChargeAmt;
	private String flowType;
	public String getServiceTypeName() {
		return serviceTypeName;
	}
	public void setServiceTypeName(String serviceTypeName) {
		this.serviceTypeName = serviceTypeName;
	}
	public String getOtherNumber() {
		return otherNumber;
	}
	public void setOtherNumber(String otherNumber) {
		this.otherNumber = otherNumber;
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
	public String getFlowType() {
		return flowType;
	}
	public void setFlowType(String flowType) {
		this.flowType = flowType;
	}
	
	
}
