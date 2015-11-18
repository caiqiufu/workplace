package com.apps.esb.service.bss.app.vo;

import java.util.List;

public class MontlyUsageVO implements Comparable<MontlyUsageVO>{
	private String index;
	private String usageAmount;
	private String billCycleID;
	private List<ServiceCategoryVO> serviceCategorys;
	public String getUsageAmount() {
		return usageAmount;
	}
	public void setUsageAmount(String usageAmount) {
		this.usageAmount = usageAmount;
	}
	public List<ServiceCategoryVO> getServiceCategorys() {
		return serviceCategorys;
	}
	public void setServiceCategorys(List<ServiceCategoryVO> serviceCategorys) {
		this.serviceCategorys = serviceCategorys;
	}
	public String getIndex() {
		return index;
	}
	public void setIndex(String index) {
		this.index = index;
	}
	public String getBillCycleID() {
		return billCycleID;
	}
	public void setBillCycleID(String billCycleID) {
		this.billCycleID = billCycleID;
	}
	@Override
	public int compareTo(MontlyUsageVO o) {
		return Integer.parseInt(this.getBillCycleID()) - Integer.parseInt(o.getBillCycleID());
	}
	
	
}
