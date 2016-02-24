package com.apps.esb.service.bss.app.vo.resource;

import com.unieap.base.vo.BaseVO;

public class ResourceVO extends BaseVO{
	private String resourceType;
	private String resourceTypeDesc;
	private String resourceModel;
	private String productId;
	private String priceAmount;
	private String resourceID;
	private String resourceCode;
	private String resourceName;
	private String shippingFlag;
	public String getResourceType() {
		return resourceType;
	}
	public void setResourceType(String resourceType) {
		this.resourceType = resourceType;
	}
	public String getResourceModel() {
		return resourceModel;
	}
	public void setResourceModel(String resourceModel) {
		this.resourceModel = resourceModel;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getPriceAmount() {
		return priceAmount;
	}
	public void setPriceAmount(String priceAmount) {
		this.priceAmount = priceAmount;
	}
	public String getResourceID() {
		return resourceID;
	}
	public void setResourceID(String resourceID) {
		this.resourceID = resourceID;
	}
	public String getResourceCode() {
		return resourceCode;
	}
	public void setResourceCode(String resourceCode) {
		this.resourceCode = resourceCode;
	}
	public String getResourceName() {
		return resourceName;
	}
	public void setResourceName(String resourceName) {
		this.resourceName = resourceName;
	}
	public String getShippingFlag() {
		return shippingFlag;
	}
	public void setShippingFlag(String shippingFlag) {
		this.shippingFlag = shippingFlag;
	}
	public String getResourceTypeDesc() {
		return resourceTypeDesc;
	}
	public void setResourceTypeDesc(String resourceTypeDesc) {
		this.resourceTypeDesc = resourceTypeDesc;
	}
	
	
}
