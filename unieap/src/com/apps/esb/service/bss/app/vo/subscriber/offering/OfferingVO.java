package com.apps.esb.service.bss.app.vo.subscriber.offering;

import java.util.List;

import com.unieap.base.vo.BaseVO;

public class OfferingVO extends BaseVO{
	/**
	 * P: primary offering, 
	 * S: supplementary offering
	 */
	private String offeringType;
	private String offeringTypeDesc;
	private String offeringId;
	private String offeringCode;
	private String offeringName;
	private String offeringShortName;
	private String networkType;
	private String networkTypeDesc;
	private String purchaseSeq;
	private String parentOfferingId;
	private String parentOfferingCode;
	private String parentPurchaseSeq;
	private String bundleFlag;
	private String bundleFlagDesc;
	private String trialStartDate;
	private String trialEndDate;
	private String amount;
	private String status;
	private String statusDesc;
	private String createDate;
	private String rentFee;
	private String oneTimeFee;
	private String latestActiveDate;
	private String ownerType;
	private String ownerTypeDesc;
	private String sellCatalogueId;
	private String sellCatalogueName;
	private List<OfferingVO> subOfferingList;
	private List<ServiceVO> serviceList;
	public String getOfferingId() {
		return offeringId;
	}
	public void setOfferingId(String offeringId) {
		this.offeringId = offeringId;
	}
	public String getOfferingCode() {
		return offeringCode;
	}
	public void setOfferingCode(String offeringCode) {
		this.offeringCode = offeringCode;
	}
	public String getPurchaseSeq() {
		return purchaseSeq;
	}
	public void setPurchaseSeq(String purchaseSeq) {
		this.purchaseSeq = purchaseSeq;
	}
	public String getParentOfferingId() {
		return parentOfferingId;
	}
	public void setParentOfferingId(String parentOfferingId) {
		this.parentOfferingId = parentOfferingId;
	}
	public String getParentOfferingCode() {
		return parentOfferingCode;
	}
	public void setParentOfferingCode(String parentOfferingCode) {
		this.parentOfferingCode = parentOfferingCode;
	}
	public String getParentPurchaseSeq() {
		return parentPurchaseSeq;
	}
	public void setParentPurchaseSeq(String parentPurchaseSeq) {
		this.parentPurchaseSeq = parentPurchaseSeq;
	}
	public String getOfferingName() {
		return offeringName;
	}
	public void setOfferingName(String offeringName) {
		this.offeringName = offeringName;
	}
	public String getBundleFlag() {
		return bundleFlag;
	}
	public void setBundleFlag(String bundleFlag) {
		this.bundleFlag = bundleFlag;
	}
	public String getTrialStartDate() {
		return trialStartDate;
	}
	public void setTrialStartDate(String trialStartDate) {
		this.trialStartDate = trialStartDate;
	}
	public String getTrialEndDate() {
		return trialEndDate;
	}
	public void setTrialEndDate(String trialEndDate) {
		this.trialEndDate = trialEndDate;
	}
	public String getAmount() {
		return amount;
	}
	public void setAmount(String amount) {
		this.amount = amount;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getCreateDate() {
		return createDate;
	}
	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}
	
	public String getLatestActiveDate() {
		return latestActiveDate;
	}
	public void setLatestActiveDate(String latestActiveDate) {
		this.latestActiveDate = latestActiveDate;
	}
	public String getOfferingType() {
		return offeringType;
	}
	public void setOfferingType(String offeringType) {
		this.offeringType = offeringType;
	}
	public List<ServiceVO> getServiceList() {
		return serviceList;
	}
	public void setServiceList(List<ServiceVO> serviceList) {
		this.serviceList = serviceList;
	}
	public List<OfferingVO> getSubOfferingList() {
		return subOfferingList;
	}
	public void setSubOfferingList(List<OfferingVO> subOfferingList) {
		this.subOfferingList = subOfferingList;
	}
	public String getOfferingShortName() {
		return offeringShortName;
	}
	public void setOfferingShortName(String offeringShortName) {
		this.offeringShortName = offeringShortName;
	}
	public String getNetworkType() {
		return networkType;
	}
	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}
	public String getOneTimeFee() {
		return oneTimeFee;
	}
	public void setOneTimeFee(String oneTimeFee) {
		this.oneTimeFee = oneTimeFee;
	}
	public String getOwnerType() {
		return ownerType;
	}
	public void setOwnerType(String ownerType) {
		this.ownerType = ownerType;
	}
	public String getSellCatalogueId() {
		return sellCatalogueId;
	}
	public void setSellCatalogueId(String sellCatalogueId) {
		this.sellCatalogueId = sellCatalogueId;
	}
	public String getSellCatalogueName() {
		return sellCatalogueName;
	}
	public void setSellCatalogueName(String sellCatalogueName) {
		this.sellCatalogueName = sellCatalogueName;
	}
	public String getRentFee() {
		return rentFee;
	}
	public void setRentFee(String rentFee) {
		this.rentFee = rentFee;
	}
	public String getOfferingTypeDesc() {
		return offeringTypeDesc;
	}
	public void setOfferingTypeDesc(String offeringTypeDesc) {
		this.offeringTypeDesc = offeringTypeDesc;
	}
	public String getNetworkTypeDesc() {
		return networkTypeDesc;
	}
	public void setNetworkTypeDesc(String networkTypeDesc) {
		this.networkTypeDesc = networkTypeDesc;
	}
	public String getBundleFlagDesc() {
		return bundleFlagDesc;
	}
	public void setBundleFlagDesc(String bundleFlagDesc) {
		this.bundleFlagDesc = bundleFlagDesc;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getOwnerTypeDesc() {
		return ownerTypeDesc;
	}
	public void setOwnerTypeDesc(String ownerTypeDesc) {
		this.ownerTypeDesc = ownerTypeDesc;
	}
	
	
}
