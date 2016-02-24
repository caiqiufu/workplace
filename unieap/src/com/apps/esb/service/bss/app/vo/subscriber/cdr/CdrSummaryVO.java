package com.apps.esb.service.bss.app.vo.subscriber.cdr;

import com.unieap.base.vo.BaseVO;

public class CdrSummaryVO extends BaseVO{
	private String serviceCategory;
	private String serviceCategoryDesc;
	private String serviceType;
	private String serviceTypeDesc;
	private String billCycleID;
	private String totalChargeAmt;
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
	public String getBillCycleID() {
		return billCycleID;
	}
	public void setBillCycleID(String billCycleID) {
		this.billCycleID = billCycleID;
	}
	public String getTotalChargeAmt() {
		return totalChargeAmt;
	}
	public void setTotalChargeAmt(String totalChargeAmt) {
		this.totalChargeAmt = totalChargeAmt;
	}
	public String getServiceCategoryDesc() {
		return serviceCategoryDesc;
	}
	public void setServiceCategoryDesc(String serviceCategoryDesc) {
		this.serviceCategoryDesc = serviceCategoryDesc;
	}
	public String getServiceTypeDesc() {
		return serviceTypeDesc;
	}
	public void setServiceTypeDesc(String serviceTypeDesc) {
		this.serviceTypeDesc = serviceTypeDesc;
	}
	
}
