package com.apps.esb.service.bss.vo.mshop.sim;

import com.unieap.base.vo.BaseVO;

public class MshopSimCardVO extends BaseVO{
	private String serviceNumber;
	private String simCardType;
	private String iccid;
	private String imsi;
	private String simCardStatus;
	private String pin1;
	private String pin2;
	private String puk1;
	private String puk2;
	public String getServiceNumber() {
		return serviceNumber;
	}
	public String getSimCardType() {
		return simCardType;
	}
	public String getIccid() {
		return iccid;
	}
	public String getImsi() {
		return imsi;
	}
	public String getSimCardStatus() {
		return simCardStatus;
	}
	public String getPin1() {
		return pin1;
	}
	public String getPin2() {
		return pin2;
	}
	public String getPuk1() {
		return puk1;
	}
	public String getPuk2() {
		return puk2;
	}
	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}
	public void setSimCardType(String simCardType) {
		this.simCardType = simCardType;
	}
	public void setIccid(String iccid) {
		this.iccid = iccid;
	}
	public void setImsi(String imsi) {
		this.imsi = imsi;
	}
	public void setSimCardStatus(String simCardStatus) {
		this.simCardStatus = simCardStatus;
	}
	public void setPin1(String pin1) {
		this.pin1 = pin1;
	}
	public void setPin2(String pin2) {
		this.pin2 = pin2;
	}
	public void setPuk1(String puk1) {
		this.puk1 = puk1;
	}
	public void setPuk2(String puk2) {
		this.puk2 = puk2;
	}
	
	
}
