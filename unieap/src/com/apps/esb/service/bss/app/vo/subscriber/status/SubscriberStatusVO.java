package com.apps.esb.service.bss.app.vo.subscriber.status;

import com.unieap.base.vo.BaseVO;

public class SubscriberStatusVO extends BaseVO{
	private String statusType;
	private String statusTypeDesc;
	private String status;
	private String statusDesc;
	private String statusReason;
	private String statusReasonDesc;
	public String getStatusType() {
		return statusType;
	}
	public String getStatusTypeDesc() {
		return statusTypeDesc;
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
	public void setStatusType(String statusType) {
		this.statusType = statusType;
	}
	public void setStatusTypeDesc(String statusTypeDesc) {
		this.statusTypeDesc = statusTypeDesc;
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
	
}
