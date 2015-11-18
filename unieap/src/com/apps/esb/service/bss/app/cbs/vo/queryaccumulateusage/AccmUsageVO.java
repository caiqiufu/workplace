package com.apps.esb.service.bss.app.cbs.vo.queryaccumulateusage;

public class AccmUsageVO {
	private String accmType;
	private String accmTypeName;
	private String amount = "0";
	private String unitType;
	private String measureID;
	private String beginDate;
	private String endDate;
	public String getAccmType() {
		return accmType;
	}
	public void setAccmType(String accmType) {
		this.accmType = accmType;
	}
	public String getAccmTypeName() {
		return accmTypeName;
	}
	public void setAccmTypeName(String accmTypeName) {
		this.accmTypeName = accmTypeName;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getUnitType() {
		return unitType;
	}
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	public String getMeasureID() {
		return measureID;
	}
	public void setMeasureID(String measureID) {
		this.measureID = measureID;
	}
	public String getBeginDate() {
		return beginDate;
	}
	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	
	
}
