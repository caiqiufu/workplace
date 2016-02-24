package com.apps.esb.service.bss.app.vo.subscriber.basicinfo;

import java.util.List;

import org.springframework.util.StringUtils;

import com.apps.esb.service.bss.app.vo.account.basicinfo.AccountVO;
import com.apps.esb.service.bss.app.vo.common.address.AddressVO;
import com.apps.esb.service.bss.app.vo.customer.basicinfo.CorporateVO;
import com.apps.esb.service.bss.app.vo.customer.basicinfo.CustomerVO;
import com.apps.esb.service.bss.app.vo.subscriber.limitation.ConsumptionLimitVO;
import com.apps.esb.service.bss.app.vo.subscriber.offering.OfferingVO;
import com.apps.esb.service.bss.app.vo.subscriber.sim.SimCardVO;
import com.unieap.CacheMgt;
import com.unieap.base.vo.BaseVO;
import com.unieap.mdm.vo.DicDataVO;

public class SubscriberVO extends BaseVO{
	private String subscriberId;
	private String accountId;
	private String customerId;
	private String serviceNumber;
	private String subscriberType;
	private String subscriberTypeDesc;
	private String networkType;
	private String networkTypeDesc;
	private String IMEI;
	private String ICCID;
	private String brandId;
	private String language;
	private String writtenLanguage;
	/**
	 * PPS:prepaid
	 * POS:postpaid
	 */
	private String paymentFlag;
	private String paymentFlagDesc;
	private String status;
	private String statusDesc;
	private String statusReason;
	private String statusReasonDesc;
	private String resignedTimestamp;
	private String activateTime;
	private String barOneWayTime;
	private String barTwoWayTime;
	private String deActivateTime;
	private String supplementFlag;
	private String supplementFlagDesc;
	private CustomerVO customerVO;
	private CorporateVO corporateVO;
	private AccountVO accountVO;
	private OfferingVO primaryOfferingVO;
	private List<OfferingVO> supplementaryOfferingList;
	private List<ConsumptionLimitVO> consumptionLimitList;
	private List<AddressVO> addressList;
	private SimCardVO simCardVO;

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
	public CustomerVO getCustomerVO() {
		return customerVO;
	}
	public void setCustomerVO(CustomerVO customerVO) {
		this.customerVO = customerVO;
	}
	public OfferingVO getPrimaryOfferingVO() {
		return primaryOfferingVO;
	}
	public void setPrimaryOfferingVO(OfferingVO primaryOfferingVO) {
		this.primaryOfferingVO = primaryOfferingVO;
	}
	public List<OfferingVO> getSupplementaryOfferingList() {
		return supplementaryOfferingList;
	}
	public void setSupplementaryOfferingList(List<OfferingVO> supplementaryOfferingList) {
		this.supplementaryOfferingList = supplementaryOfferingList;
	}
	public List<ConsumptionLimitVO> getConsumptionLimitList() {
		return consumptionLimitList;
	}
	public void setConsumptionLimitList(List<ConsumptionLimitVO> consumptionLimitList) {
		this.consumptionLimitList = consumptionLimitList;
	}
	public List<AddressVO> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<AddressVO> addressList) {
		this.addressList = addressList;
	}
	public String getBrandId() {
		return brandId;
	}
	public void setBrandId(String brandId) {
		this.brandId = brandId;
	}
	public CorporateVO getCorporateVO() {
		return corporateVO;
	}
	public void setCorporateVO(CorporateVO corporateVO) {
		this.corporateVO = corporateVO;
	}


	public String getSubscriberTypeDesc() {
		return subscriberTypeDesc;
	}
	public void setSubscriberTypeDesc(String subscriberTypeDesc) {
		this.subscriberTypeDesc = subscriberTypeDesc;
	}
	public String getNetworkTypeDesc() {
		return networkTypeDesc;
	}
	public void setNetworkTypeDesc(String networkTypeDesc) {
		this.networkTypeDesc = networkTypeDesc;
	}
	public String getStatusDesc() {
		if(!StringUtils.isEmpty(this.status)){
			DicDataVO dic =  CacheMgt.getDicData("subscriberStatus",this.status);
			if(dic!=null){
				this.statusDesc = dic.getDicName();
			}
		}
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getStatusReasonDesc() {
		return statusReasonDesc;
	}
	public void setStatusReasonDesc(String statusReasonDesc) {
		this.statusReasonDesc = statusReasonDesc;
	}
	public String getPaymentFlag() {
		return paymentFlag;
	}
	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}
	public String getPaymentFlagDesc() {
		if(!StringUtils.isEmpty(this.paymentFlag)){
			DicDataVO dic =  CacheMgt.getDicData("paymentFlag",paymentFlag);
			if(dic!=null){
				this.paymentFlagDesc = dic.getDicName();
			}
		}
		return paymentFlagDesc;
	}
	public void setPaymentFlagDesc(String paymentFlagDesc) {
		this.paymentFlagDesc = paymentFlagDesc;
	}
	public AccountVO getAccountVO() {
		return accountVO;
	}
	public void setAccountVO(AccountVO accountVO) {
		this.accountVO = accountVO;
	}
	public String getActivateTime() {
		return activateTime;
	}
	public String getBarOneWayTime() {
		return barOneWayTime;
	}
	public String getBarTwoWayTime() {
		return barTwoWayTime;
	}
	public String getDeActivateTime() {
		return deActivateTime;
	}
	public void setActivateTime(String activateTime) {
		this.activateTime = activateTime;
	}
	public void setBarOneWayTime(String barOneWayTime) {
		this.barOneWayTime = barOneWayTime;
	}
	public void setBarTwoWayTime(String barTwoWayTime) {
		this.barTwoWayTime = barTwoWayTime;
	}
	public void setDeActivateTime(String deActivateTime) {
		this.deActivateTime = deActivateTime;
	}
	public String getSupplementFlag() {
		return supplementFlag;
	}
	public String getSupplementFlagDesc() {
		return supplementFlagDesc;
	}
	public void setSupplementFlag(String supplementFlag) {
		this.supplementFlag = supplementFlag;
	}
	public void setSupplementFlagDesc(String supplementFlagDesc) {
		this.supplementFlagDesc = supplementFlagDesc;
	}
	public SimCardVO getSimCardVO() {
		return simCardVO;
	}
	public void setSimCardVO(SimCardVO simCardVO) {
		this.simCardVO = simCardVO;
	}
	public String getICCID() {
		return ICCID;
	}
	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}
	
	
}
