package com.apps.esb.service.bss.vo.macre.offerings;

import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.base.vo.BaseVO;
import com.unieap.mdm.vo.DicDataVO;

public class OfferingVO extends BaseVO{
	private String seq;
	private String offeringId;
	private String offeringCode;
	private String offeringName;
	private String offeringType;
	private String offeringDesc;
	private String effectiveType;
	private String subscribeDate;
	private String effectiveDate;
	private String expiryDate;
	private String feeAmount;
	private String status;
	private OfferCategoryVO offerCategory;
	
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
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
	public String getOfferingName() {
		return offeringName;
	}
	public void setOfferingName(String offeringName) {
		this.offeringName = offeringName;
	}
	public String getOfferingDesc() {
		return offeringDesc;
	}
	public void setOfferingDesc(String offeringDesc) {
		this.offeringDesc = offeringDesc;
	}
	public String getEffectiveDate() {
		return effectiveDate;
	}
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	public String getFeeAmount() {
		return feeAmount;
	}
	public void setFeeAmount(String feeAmount) {
		this.feeAmount = feeAmount;
	}
	public OfferCategoryVO getOfferCategory() {
		return offerCategory;
	}
	public void setOfferCategory(OfferCategoryVO offerCategory) {
		this.offerCategory = offerCategory;
	}
	public String getEffectiveType() {
		if(!StringUtils.isEmpty(this.effectiveType)){
			DicDataVO dic =  CacheMgt.getDicData("effectMode",effectiveType);
			if(dic!=null){
				this.effectiveType = dic.getDicName();
			}
		}
		return effectiveType;
	}
	public void setEffectiveType(String effectiveType) {
		this.effectiveType = effectiveType;
	}
	public String getSubscribeDate() {
		return subscribeDate;
	}
	public void setSubscribeDate(String subscribeDate) {
		this.subscribeDate = subscribeDate;
	}
	public String getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(String expiryDate) {
		this.expiryDate = expiryDate;
	}
	public String getOfferingType() {
		return offeringType;
	}
	public void setOfferingType(String offeringType) {
		this.offeringType = offeringType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	
	
}
