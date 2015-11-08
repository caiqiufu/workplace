package com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo;

import java.util.List;

public class QuerySubscriberInfoVO {
	private String subscriberId;
	private String accountId;
	private String customerId;
	private String serviceNumber;
	private String subscriberType;
	private String networkType;
	private String IMEI;
	private String ICCID;
	private String brandId;
	private String language;
	private String writtenLanguage;
	private String effectiveDate;
	private String expireDate;
	private String dealerId;
	private String status;
	private String statusReason;
	private String resignedTimestamp;
	private ActualCustomerVO actualCustomerVO;
	private PrimaryOfferingVO primaryOfferingVO;
	private List<SupplementaryOfferingVO> supplementaryOfferingList;
	private List<ConsumptionLimitVO> consumptionLimitList;
	private List<AddressInfoVO> addressInfoList;
	public String getSubscriberId() {
		return subscriberId;
	}
	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}
	public String getAccountId() {
		return accountId;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	
	
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getServiceNumber() {
		return serviceNumber;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public String getSubscriberType() {
		return subscriberType;
	}
	public void setSubscriberType(String subscriberType) {
		this.subscriberType = subscriberType;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getIMEI() {
		return IMEI;
	}
	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getWrittenLanguage() {
		return writtenLanguage;
	}
	public void setWrittenLanguage(String writtenLanguage) {
		this.writtenLanguage = writtenLanguage;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getExpireDate() {
		return expireDate;
	}
	public void setExpireDate(String expireDate) {
		this.expireDate = expireDate;
	}
	public String getDealerId() {
		return dealerId;
	}
	public void setDealerId(String dealerId) {
		this.dealerId = dealerId;
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
	public String getResignedTimestamp() {
		return resignedTimestamp;
	}
	public void setResignedTimestamp(String resignedTimestamp) {
		this.resignedTimestamp = resignedTimestamp;
	}
	public ActualCustomerVO getActualCustomerVO() {
		return actualCustomerVO;
	}
	public void setActualCustomerVO(ActualCustomerVO actualCustomerVO) {
		this.actualCustomerVO = actualCustomerVO;
	}
	public PrimaryOfferingVO getPrimaryOfferingVO() {
		return primaryOfferingVO;
	}
	public void setPrimaryOfferingVO(PrimaryOfferingVO primaryOfferingVO) {
		this.primaryOfferingVO = primaryOfferingVO;
	}
	public List<SupplementaryOfferingVO> getSupplementaryOfferingList() {
		return supplementaryOfferingList;
	}
	public void setSupplementaryOfferingList(List<SupplementaryOfferingVO> supplementaryOfferingList) {
		this.supplementaryOfferingList = supplementaryOfferingList;
	}
	public List<ConsumptionLimitVO> getConsumptionLimitList() {
		return consumptionLimitList;
	}
	public void setConsumptionLimitList(List<ConsumptionLimitVO> consumptionLimitList) {
		this.consumptionLimitList = consumptionLimitList;
	}
	public List<AddressInfoVO> getAddressInfoList() {
		return addressInfoList;
	}
	public void setAddressInfoList(List<AddressInfoVO> addressInfoList) {
		this.addressInfoList = addressInfoList;
	}
	
	
	
}
