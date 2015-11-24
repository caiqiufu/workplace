package com.apps.esb.service.bss.app.cbs.vo.queryCdr;

public class CdrInfoVO {
	private String cdrSeq;
	private String serviceCategory;
	private String serviceType;
	private String serviceTypeName;
	private String otherNumber;
	/**
	 * 20151001
	 */
	private String billCycleID;
	
	private String startTime;
	private String endTime;
	
	private String actualVolume;
	private String actualChargeAmt;
	
	private String chargeDuration;
	private String destinationNetworkName;
	private String mainBalanceDeduct;
	private String mainBalaneLeft;
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
	
	
	
}
