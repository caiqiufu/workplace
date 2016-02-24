package com.apps.esb.service.bss.app.vo.subscriber.accumulate;

import com.unieap.base.vo.BaseVO;

public class AccumulateUsageVO extends BaseVO{
	private String accumulateType;
	private String accumulateTypeDesc;
	private String amount;
	private String measureUnit;
	private String measureUnitDesc;
	private String beginDate;
	private String endDate;
	public String getAccumulateType() {
		return accumulateType;
	}
	public void setAccumulateType(String accumulateType) {
		this.accumulateType = accumulateType;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getMeasureUnit() {
		return measureUnit;
	}
	public void setMeasureUnit(String measureUnit) {
		this.measureUnit = measureUnit;
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
	public String getAccumulateTypeDesc() {
		return accumulateTypeDesc;
	}
	public void setAccumulateTypeDesc(String accumulateTypeDesc) {
		this.accumulateTypeDesc = accumulateTypeDesc;
	}
	public String getMeasureUnitDesc() {
		return measureUnitDesc;
	}
	public void setMeasureUnitDesc(String measureUnitDesc) {
		this.measureUnitDesc = measureUnitDesc;
	}
	
	
}
