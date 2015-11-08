package com.apps.esb.service.bss.app.crm.vo.querysubscriberinfo;

import java.util.List;

public class SubProductInfoVO {
	private String productId;
	private String productName;
	private String status;
	private ResourceInfoVO resourceInfoVO;
	private List<ServiceVO> serviceList;
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public ResourceInfoVO getResourceInfoVO() {
		return resourceInfoVO;
	}
	public void setResourceInfoVO(ResourceInfoVO resourceInfoVO) {
		this.resourceInfoVO = resourceInfoVO;
	}
	public List<ServiceVO> getServiceList() {
		return serviceList;
	}
	public void setServiceList(List<ServiceVO> serviceList) {
		this.serviceList = serviceList;
	}
	
}
