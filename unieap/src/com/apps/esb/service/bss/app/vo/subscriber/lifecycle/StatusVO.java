package com.apps.esb.service.bss.app.vo.subscriber.lifecycle;

import com.unieap.base.vo.BaseVO;

public class StatusVO extends BaseVO{
	private String statusIndex;
	private String statusDesc;
	public String getStatusIndex() {
		return statusIndex;
	}
	public void setStatusIndex(String statusIndex) {
		this.statusIndex = statusIndex;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	
	
}
