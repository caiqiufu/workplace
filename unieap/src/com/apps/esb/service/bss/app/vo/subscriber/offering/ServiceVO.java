package com.apps.esb.service.bss.app.vo.subscriber.offering;

import com.unieap.base.vo.BaseVO;

public class ServiceVO extends BaseVO{
	private String serviceId;
	private String serviceName;
	private String serviceType;
	private String serviceTypeDesc;
	private String serviceNetworkType;
	private String serviceNetworkTypeDesc;
	private String serviceCategory;
	private String serviceCategoryDesc;
	private String isMainService;
	private String serviceStatus;
	private String serviceStatusDesc;
	public String getServiceId() {
		return serviceId;
	}
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}
	public String getServiceName() {
		return serviceName;
	}
	public void setServiceName(String serviceName) {
		this.serviceName = serviceName;
	}
	public String getServiceType() {
		return serviceType;
	}
	public void setServiceType(String serviceType) {
		this.serviceType = serviceType;
	}
	public String getServiceNetworkType() {
		return serviceNetworkType;
	}
	public void setServiceNetworkType(String serviceNetworkType) {
		this.serviceNetworkType = serviceNetworkType;
	}
	public String getServiceCategory() {
		return serviceCategory;
	}
	public void setServiceCategory(String serviceCategory) {
		this.serviceCategory = serviceCategory;
	}
	public String getIsMainService() {
		return isMainService;
	}
	public void setIsMainService(String isMainService) {
		this.isMainService = isMainService;
	}
	public String getServiceStatus() {
		return serviceStatus;
	}
	public void setServiceStatus(String serviceStatus) {
		this.serviceStatus = serviceStatus;
	}
	public String getServiceTypeDesc() {
		return serviceTypeDesc;
	}
	public void setServiceTypeDesc(String serviceTypeDesc) {
		this.serviceTypeDesc = serviceTypeDesc;
	}
	public String getServiceNetworkTypeDesc() {
		return serviceNetworkTypeDesc;
	}
	public void setServiceNetworkTypeDesc(String serviceNetworkTypeDesc) {
		this.serviceNetworkTypeDesc = serviceNetworkTypeDesc;
	}
	public String getServiceCategoryDesc() {
		return serviceCategoryDesc;
	}
	public void setServiceCategoryDesc(String serviceCategoryDesc) {
		this.serviceCategoryDesc = serviceCategoryDesc;
	}
	public String getServiceStatusDesc() {
		return serviceStatusDesc;
	}
	public void setServiceStatusDesc(String serviceStatusDesc) {
		this.serviceStatusDesc = serviceStatusDesc;
	}
	
	
}
