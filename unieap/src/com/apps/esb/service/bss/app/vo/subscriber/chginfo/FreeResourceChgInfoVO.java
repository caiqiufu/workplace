package com.apps.esb.service.bss.app.vo.subscriber.chginfo;

import com.unieap.base.vo.BaseVO;

public class FreeResourceChgInfoVO extends BaseVO{
	private String freeResourceInstanceID;
	private String freeResourceType;
	private String freeResourceTypeDesc;
	private String measureUnit;
	private String measureUnitDesc;
	private String oldAmount;
	private String newAmount;
	public String getFreeResourceInstanceID() {
		return freeResourceInstanceID;
	}
	public void setFreeResourceInstanceID(String freeResourceInstanceID) {
		this.freeResourceInstanceID = freeResourceInstanceID;
	}
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
	public String getOldAmount() {
		return oldAmount;
	}
	public void setOldAmount(String oldAmount) {
		this.oldAmount = oldAmount;
	}
	public String getNewAmount() {
		return newAmount;
	}
	public void setNewAmount(String newAmount) {
		this.newAmount = newAmount;
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
	
}
