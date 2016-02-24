package com.apps.esb.service.bss.app.vo.subscriber.freeresource;

import com.unieap.base.vo.BaseVO;

public class FreeResourceDetailVO extends BaseVO{
	private String freeResourceInstanceID;
	private String initialAmount;
	private String currentAmount;
	public String getFreeResourceInstanceID() {
		return freeResourceInstanceID;
	}
	public void setFreeResourceInstanceID(String freeResourceInstanceID) {
		this.freeResourceInstanceID = freeResourceInstanceID;
	}
	public String getInitialAmount() {
		return initialAmount;
	}
	public void setInitialAmount(String initialAmount) {
		this.initialAmount = initialAmount;
	}
	public String getCurrentAmount() {
		return currentAmount;
	}
	public void setCurrentAmount(String currentAmount) {
		this.currentAmount = currentAmount;
	}
	
}
