package com.apps.esb.service.bss.app.cbs.vo.transferbalancelog;

public class FreeUnitChgInfoVO {
	private String freeUnitInstanceID;
	private String freeUnitType;
	private String freeUnitTypeName;
	private String measureUnit;
	private String measureUnitName;
	private String oldAmt;
	private String newAmt;
	public String getFreeUnitInstanceID() {
		return freeUnitInstanceID;
	}
	public void setFreeUnitInstanceID(String freeUnitInstanceID) {
		this.freeUnitInstanceID = freeUnitInstanceID;
	}
	public String getFreeUnitType() {
		return freeUnitType;
	}
	public void setFreeUnitType(String freeUnitType) {
		this.freeUnitType = freeUnitType;
	}
	public String getFreeUnitTypeName() {
		return freeUnitTypeName;
	}
	public void setFreeUnitTypeName(String freeUnitTypeName) {
		this.freeUnitTypeName = freeUnitTypeName;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
	}
	public String getMeasureUnitName() {
		return measureUnitName;
	}
	public void setMeasureUnitName(String measureUnitName) {
		this.measureUnitName = measureUnitName;
	}
	public String getOldAmt() {
		return oldAmt;
	}
	public void setOldAmt(String oldAmt) {
		this.oldAmt = oldAmt;
	}
	public String getNewAmt() {
		return newAmt;
	}
	public void setNewAmt(String newAmt) {
		this.newAmt = newAmt;
	}
	
}
