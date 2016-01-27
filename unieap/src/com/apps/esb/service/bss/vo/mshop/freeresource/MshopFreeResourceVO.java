package com.apps.esb.service.bss.vo.mshop.freeresource;

import com.unieap.base.vo.BaseVO;

public class MshopFreeResourceVO extends BaseVO{
	private String freeResourceID;
	private String freeResourceType;
	private String freeResourceTypeDesc;
	private String freeResourceCode;
	private String freeResourceName;
	private String measureUnit;
	private String measureUnitDesc;
	private String totalInitialAmount;
	private String totalUsedAmount;
	private String totalUnusedAmount;
	public String getFreeResourceID() {
		return freeResourceID;
	}
	public String getFreeResourceType() {
		return freeResourceType;
	}
	public String getFreeResourceTypeDesc() {
		return freeResourceTypeDesc;
	}
	public String getFreeResourceCode() {
		return freeResourceCode;
	}
	public String getFreeResourceName() {
		return freeResourceName;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public String getMeasureUnitDesc() {
		return measureUnitDesc;
	}
	public String getTotalInitialAmount() {
		return totalInitialAmount;
	}
	public String getTotalUnusedAmount() {
		return totalUnusedAmount;
	}
	public void setFreeResourceID(String freeResourceID) {
		this.freeResourceID = freeResourceID;
	}
	public void setFreeResourceType(String freeResourceType) {
		this.freeResourceType = freeResourceType;
	}
	public void setFreeResourceTypeDesc(String freeResourceTypeDesc) {
		this.freeResourceTypeDesc = freeResourceTypeDesc;
	}
	public void setFreeResourceCode(String freeResourceCode) {
		this.freeResourceCode = freeResourceCode;
	}
	public void setFreeResourceName(String freeResourceName) {
		this.freeResourceName = freeResourceName;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public void setMeasureUnitDesc(String measureUnitDesc) {
		this.measureUnitDesc = measureUnitDesc;
	}
	public void setTotalInitialAmount(String totalInitialAmount) {
		this.totalInitialAmount = totalInitialAmount;
	}
	public void setTotalUnusedAmount(String totalUnusedAmount) {
		this.totalUnusedAmount = totalUnusedAmount;
	}
	public String getTotalUsedAmount() {
		return totalUsedAmount;
	}
	public void setTotalUsedAmount(String totalUsedAmount) {
		this.totalUsedAmount = totalUsedAmount;
	}
	
	
}
