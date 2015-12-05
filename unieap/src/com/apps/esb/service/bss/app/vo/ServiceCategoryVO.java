package com.apps.esb.service.bss.app.vo;

import java.util.List;

public class ServiceCategoryVO {
	private int index;
	private String categoryType;
	private String categoryTypeDesc;
	private String usageAmount;
	private List<ServiceUsageVO> serviceUsages;
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getCategoryTypeDesc() {
		return categoryTypeDesc;
	}
	public void setCategoryTypeDesc(String categoryTypeDesc) {
		this.categoryTypeDesc = categoryTypeDesc;
	}
	public String getUsageAmount() {
		return usageAmount;
	}
	public void setUsageAmount(String usageAmount) {
		this.usageAmount = usageAmount;
	}
	public List<ServiceUsageVO> getServiceUsages() {
		return serviceUsages;
	}
	public void setServiceUsages(List<ServiceUsageVO> serviceUsages) {
		this.serviceUsages = serviceUsages;
	}
	public int getIndex() {
		return index;
	}
	public void setIndex(int index) {
		this.index = index;
	}
	
	
}