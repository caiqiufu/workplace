package com.apps.esb.service.bss.app.vo.subscriber.phonenumber;

import com.unieap.base.vo.BaseVO;

public class PhoneNumberVO extends BaseVO{
	private String serviceNumber;
	private String level;
	private String price;
	public String getServiceNumber() {
		return serviceNumber;
	}
	public String getLevel() {
		return level;
	}
	public String getPrice() {
		return price;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public void setLevel(String level) {
		this.level = level;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	
	
}
