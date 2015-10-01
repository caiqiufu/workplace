package com.apps.esb.service.bss.app.crm.vo;

import java.util.List;

import com.apps.esb.service.bss.app.vo.CustomerVO;

/**
 * 用户登录后获取的信息
 * @author caiqiufu
 *
 */
public class SubscriberVO {
	
    private CustomerVO customerVO;
	private String serviceNumber;
	private String activeDate;
	/**
	 * 0 prepaid
	 * 1 postpaid
	 * 2 hybrid
	 */
	private String subscriberType;
	private OfferingVO primaryOffering;
	private List<OfferingVO> subOfferings;
	private List<ServiceVO> services;
	/**
	 * B01 Active
	 * B02 Deactive
	 * B03 Suspend (B2W)
	 * B04 Barring (B1W)
	 * B05 Pendin (this status not use in this version)
	 * B06 Idle
	 * B07 Pre-Deactivation
	 */
	private String status;
	/**
	 * 1 customer request
	 * 2 report loss/cancel loss
	 * 3 owe fee
	 * 4 credit control
	 * 5 life cycle
	 * 6 operator request
	 */
	private String statusReason;
	private String extParameters;
	public CustomerVO getCustomerVO() {
		return customerVO;
	}
	public void setCustomerVO(CustomerVO customerVO) {
		this.customerVO = customerVO;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public String getActiveDate() {
		return activeDate;
	}
	public void setActiveDate(String activeDate) {
		this.activeDate = activeDate;
	}
	public String getSubscriberType() {
		return subscriberType;
	}
	public void setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
	}
	public OfferingVO getPrimaryOffering() {
		return primaryOffering;
	}
	public void setPrimaryOffering(OfferingVO primaryOffering) {
		this.primaryOffering = primaryOffering;
	}
	public List<OfferingVO> getSubOfferings() {
		return subOfferings;
	}
	public void setSubOfferings(List<OfferingVO> subOfferings) {
		this.subOfferings = subOfferings;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusReason() {
		return statusReason;
	}
	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}
	public String getExtParameters() {
		return extParameters;
	}
	public void setExtParameters(String extParameters) {
		this.extParameters = extParameters;
	}
	public List<ServiceVO> getServices() {
		return services;
	}
	public void setServices(List<ServiceVO> services) {
		this.services = services;
	}
	
}
