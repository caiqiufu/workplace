package com.apps.esb.service.bss.app.vo.account.billmedium;

import com.unieap.base.vo.BaseVO;

public class BillMediumVO extends BaseVO{
	private String billMediumId;
	private String billMediumName;
	private String billMediumType;
	private String billMediumTypeDesc;
	private String existFlag;
	public String getBillMediumId() {
		return billMediumId;
	}
	public String getBillMediumName() {
		return billMediumName;
	}
	public String getBillMediumType() {
		return billMediumType;
	}
	public String getBillMediumTypeDesc() {
		return billMediumTypeDesc;
	}
	public String getExistFlag() {
		return existFlag;
	}
	public void setBillMediumId(String billMediumId) {
		this.billMediumId = billMediumId;
	}
	public void setBillMediumName(String billMediumName) {
		this.billMediumName = billMediumName;
	}
	public void setBillMediumType(String billMediumType) {
		this.billMediumType = billMediumType;
	}
	public void setBillMediumTypeDesc(String billMediumTypeDesc) {
		this.billMediumTypeDesc = billMediumTypeDesc;
	}
	public void setExistFlag(String existFlag) {
		this.existFlag = existFlag;
	}
	
}
