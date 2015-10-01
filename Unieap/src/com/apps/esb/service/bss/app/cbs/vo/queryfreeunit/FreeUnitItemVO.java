package com.apps.esb.service.bss.app.cbs.vo.queryfreeunit;

import java.util.List;

public class FreeUnitItemVO {
	public String freeUnitType;
	public String freeUnitTypeName;
	public String measureUnit;
	public String measureUnitName;
	public String totalInitialAmount;
	public String totalUnusedAmount;
	public List<FreeUnitItemDetailVO> freeUnitItemDetailList;
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
	public List<FreeUnitItemDetailVO> getFreeUnitItemDetailList() {
		return freeUnitItemDetailList;
	}
	public void setFreeUnitItemDetailList(List<FreeUnitItemDetailVO> freeUnitItemDetailList) {
		this.freeUnitItemDetailList = freeUnitItemDetailList;
	}

}
