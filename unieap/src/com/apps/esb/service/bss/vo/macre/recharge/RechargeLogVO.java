package com.apps.esb.service.bss.vo.macre.recharge;

import com.unieap.base.vo.BaseVO;

public class RechargeLogVO extends BaseVO{
	private String datetime;
	private String channel;
	private String amount;

	public String getDatetime() {
		return datetime;
	}

	public void setDatetime(String datetime) {
		this.datetime = datetime;
	}

	public String getChannel() {
		return channel;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

}
