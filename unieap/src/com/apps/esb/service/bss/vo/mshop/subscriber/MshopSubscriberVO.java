package com.apps.esb.service.bss.vo.mshop.subscriber;

import java.util.List;

import com.apps.esb.service.bss.app.vo.account.billmedium.BillMediumVO;
import com.apps.esb.service.bss.app.vo.subscriber.limitation.ConsumptionLimitVO;
import com.apps.esb.service.bss.app.vo.subscriber.offering.ServiceVO;
import com.apps.esb.service.bss.vo.mshop.customer.MshopAddressVO;
import com.apps.esb.service.bss.vo.mshop.customer.MshopCustomerVO;
import com.unieap.base.vo.BaseVO;

public class MshopSubscriberVO extends BaseVO{
	private String subscriberId;
	private String accountId;
	private String customerId;
	private String serviceNumber;
	private String paymentFlag;
	private String paymentFlagDesc;
	private String IMEI;
	private String ICCID;
	private String activationDate;
	private String suspesionDate;
	private String deactivationDate;
	private String expiryDate;
	private String language;
	private String status;
	private String statusDesc;
	private String statusReason;
	private String statusReasonDesc;
	private String resignedTimestamp;
	private MshopCustomerVO customer;
	private MshopOfferingVO primaryOffering;
	private List<MshopOfferingVO> supplementaryOfferingList;
	private List<ConsumptionLimitVO> consumptionLimitList;
	private List<MshopAddressVO> addressList;
	private List<ServiceVO> serviceList;
	
	private String totalBalance;
	private String billCycle;
	private String billCycleDesc;
	private String outstandingBill;
	private String paymentDueDate;
	private String initinalCreditLimit;
	private String tempCreditLimit;
	private String totalCreditLimit;
	private String availableAmount;
	
	private List<BillMediumVO> billMediumList;

	public String getSubscriberId() {
		return subscriberId;
	}

	public String getAccountId() {
		return accountId;
	}

	public String getCustomerId() {
		return customerId;
	}

	public String getServiceNumber() {
		return serviceNumber;
	}

	public String getIMEI() {
		return IMEI;
	}

	public String getICCID() {
		return ICCID;
	}

	public String getActivationDate() {
		return activationDate;
	}

	public String getSuspesionDate() {
		return suspesionDate;
	}

	public String getDeactivationDate() {
		return deactivationDate;
	}

	public String getExpiryDate() {
		return expiryDate;
	}

	public String getLanguage() {
		return language;
	}

	public String getStatus() {
		return status;
	}

	public String getStatusDesc() {
		return statusDesc;
	}

	public String getStatusReason() {
		return statusReason;
	}

	public String getStatusReasonDesc() {
		return statusReasonDesc;
	}

	public List<ConsumptionLimitVO> getConsumptionLimitList() {
		return consumptionLimitList;
	}

	public List<MshopAddressVO> getAddressList() {
		return addressList;
	}

	public String getTotalBalance() {
		return totalBalance;
	}

	public String getBillCycle() {
		return billCycle;
	}

	public String getOutstandingBill() {
		return outstandingBill;
	}

	public String getPaymentDueDate() {
		return paymentDueDate;
	}

	public String getInitinalCreditLimit() {
		return initinalCreditLimit;
	}

	public String getTempCreditLimit() {
		return tempCreditLimit;
	}

	public String getTotalCreditLimit() {
		return totalCreditLimit;
	}

	public String getAvailableAmount() {
		return availableAmount;
	}

	public List<BillMediumVO> getBillMediumList() {
		return billMediumList;
	}

	public void setSubscriberId(String subscriberId) {
		this.subscriberId = subscriberId;
	}

	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}

	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public void setIMEI(String iMEI) {
		IMEI = iMEI;
	}

	public void setICCID(String iCCID) {
		ICCID = iCCID;
	}

	public void setActivationDate(String activationDate) {
		this.activationDate = activationDate;
	}

	public void setSuspesionDate(String suspesionDate) {
		this.suspesionDate = suspesionDate;
	}

	public void setDeactivationDate(String deactivationDate) {
		this.deactivationDate = deactivationDate;
	}

	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}

	public void setStatusReason(String statusReason) {
		this.statusReason = statusReason;
	}

	public void setStatusReasonDesc(String statusReasonDesc) {
		this.statusReasonDesc = statusReasonDesc;
	}

	public void setResignedTimestamp(String resignedTimestamp) {
		this.resignedTimestamp = resignedTimestamp;
	}

	public void setConsumptionLimitList(List<ConsumptionLimitVO> consumptionLimitList) {
		this.consumptionLimitList = consumptionLimitList;
	}

	public void setAddressList(List<MshopAddressVO> addressList) {
		this.addressList = addressList;
	}

	public void setTotalBalance(String totalBalance) {
		this.totalBalance = totalBalance;
	}

	public void setBillCycle(String billCycle) {
		this.billCycle = billCycle;
	}

	public void setOutstandingBill(String outstandingBill) {
		this.outstandingBill = outstandingBill;
	}

	public void setPaymentDueDate(String paymentDueDate) {
		this.paymentDueDate = paymentDueDate;
	}

	public void setInitinalCreditLimit(String initinalCreditLimit) {
		this.initinalCreditLimit = initinalCreditLimit;
	}

	public void setTempCreditLimit(String tempCreditLimit) {
		this.tempCreditLimit = tempCreditLimit;
	}

	public void setTotalCreditLimit(String totalCreditLimit) {
		this.totalCreditLimit = totalCreditLimit;
	}

	public void setAvailableAmount(String availableAmount) {
		this.availableAmount = availableAmount;
	}

	public void setBillMediumList(List<BillMediumVO> billMediumList) {
		this.billMediumList = billMediumList;
	}

	public String getPaymentFlag() {
		return paymentFlag;
	}

	public String getPaymentFlagDesc() {
		return paymentFlagDesc;
	}

	public String getBillCycleDesc() {
		return billCycleDesc;
	}

	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}

	public void setPaymentFlagDesc(String paymentFlagDesc) {
		this.paymentFlagDesc = paymentFlagDesc;
	}

	public void setBillCycleDesc(String billCycleDesc) {
		this.billCycleDesc = billCycleDesc;
	}

	public String getResignedTimestamp() {
		return resignedTimestamp;
	}

	public MshopCustomerVO getCustomer() {
		return customer;
	}

	public void setCustomer(MshopCustomerVO customer) {
		this.customer = customer;
	}

	public MshopOfferingVO getPrimaryOffering() {
		return primaryOffering;
	}

	public List<MshopOfferingVO> getSupplementaryOfferingList() {
		return supplementaryOfferingList;
	}

	public void setPrimaryOffering(MshopOfferingVO primaryOffering) {
		this.primaryOffering = primaryOffering;
	}

	public void setSupplementaryOfferingList(List<MshopOfferingVO> supplementaryOfferingList) {
		this.supplementaryOfferingList = supplementaryOfferingList;
	}

	public List<ServiceVO> getServiceList() {
		return serviceList;
	}

	public void setServiceList(List<ServiceVO> serviceList) {
		this.serviceList = serviceList;
	}
	
}
