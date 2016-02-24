package com.apps.esb.service.bss.app.vo.subscriber.freeresource;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class FreeResourceVO extends BaseVO{
	private String freeResourceID;
	/**
	 * 1 money
	 * 2 voice 
	 * 3 data
	 * 4 sms
	 * 5 mms
	 * 6 content
	 * 7 point
	 */
	private String freeResourceType;
	private String freeResourceTypeDesc;
	private String freeResourceCode;
	private String freeResourceName;
	private String measureUnit;
	private String measureUnitDesc;
	private String totalInitialAmount;
	private String totalUnusedAmount;
	private List<FreeResourceDetailVO> freeResourceDetailList;
	public String getFreeResourceType() {
		return freeResourceType;
	}
	public void setFreeResourceType(String freeResourceType) {
		this.freeResourceType = freeResourceType;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public String getTotalInitialAmount() {
		return totalInitialAmount;
	}
	public void setTotalInitialAmount(String totalInitialAmount) {
		this.totalInitialAmount = totalInitialAmount;
	}
	public String getTotalUnusedAmount() {
		return totalUnusedAmount;
	}
	public void setTotalUnusedAmount(String totalUnusedAmount) {
		this.totalUnusedAmount = totalUnusedAmount;
	}
	public List<FreeResourceDetailVO> getFreeResourceDetailList() {
		return freeResourceDetailList;
	}
	public void setFreeResourceDetailList(List<FreeResourceDetailVO> freeResourceDetailList) {
		this.freeResourceDetailList = freeResourceDetailList;
	}
	public String getFreeResourceID() {
		return freeResourceID;
	}
	public void setFreeResourceID(String freeResourceID) {
		this.freeResourceID = freeResourceID;
	}
	public String getFreeResourceTypeDesc() {
		return freeResourceTypeDesc;
	}
	public void setFreeResourceTypeDesc(String freeResourceTypeDesc) {
		this.freeResourceTypeDesc = freeResourceTypeDesc;
	}
	public String getMeasureUnitDesc() {
		return measureUnitDesc;
	}
	public void setMeasureUnitDesc(String measureUnitDesc) {
		this.measureUnitDesc = measureUnitDesc;
	}
	public String getFreeResourceCode() {
		return freeResourceCode;
	}
	public void setFreeResourceCode(String freeResourceCode) {
		this.freeResourceCode = freeResourceCode;
	}
	public String getFreeResourceName() {
		return freeResourceName;
	}
	public void setFreeResourceName(String freeResourceName) {
		this.freeResourceName = freeResourceName;
	}
	
}
