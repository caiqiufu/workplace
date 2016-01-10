package com.apps.esb.service.bss.vo.subscribedofferings;

import java.util.List;

import com.apps.esb.service.bss.vo.offerings.OfferingVO;
import com.unieap.base.vo.BaseVO;

public class SubscribedOfferingsVO extends BaseVO{
	private List<OfferingVO> normalOfferings;
	private List<OfferingVO> vasOfferings;
	private List<OfferingVO> services;
	private List<OfferingVO> products;
	private List<OfferingVO> promotionOfferings;
	public List<OfferingVO> getVasOfferings() {
		return vasOfferings;
	}
	public void setVasOfferings(List<OfferingVO> vasOfferings) {
		this.vasOfferings = vasOfferings;
	}
	public List<OfferingVO> getServices() {
		return services;
	}
	public void setServices(List<OfferingVO> services) {
		this.services = services;
	}
	public List<OfferingVO> getNormalOfferings() {
		return normalOfferings;
	}
	public void setNormalOfferings(List<OfferingVO> normalOfferings) {
		this.normalOfferings = normalOfferings;
	}
	public List<OfferingVO> getPromotionOfferings() {
		return promotionOfferings;
	}
	public void setPromotionOfferings(List<OfferingVO> promotionOfferings) {
		this.promotionOfferings = promotionOfferings;
	}
	public List<OfferingVO> getProducts() {
		return products;
	}
	public void setProducts(List<OfferingVO> products) {
		this.products = products;
	}
	
}
