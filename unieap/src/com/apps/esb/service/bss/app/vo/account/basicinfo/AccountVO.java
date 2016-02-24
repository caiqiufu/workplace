package com.apps.esb.service.bss.app.vo.account.basicinfo;

import java.util.List;

import com.apps.esb.service.bss.app.vo.account.billmedium.BillMediumVO;
import com.apps.esb.service.bss.app.vo.common.address.AddressVO;
import com.apps.esb.service.bss.app.vo.common.personalinfo.PersonalInfoVO;
import com.apps.esb.service.bss.app.vo.subscriber.basicinfo.SubscriberVO;
import com.unieap.base.vo.BaseVO;

public class AccountVO extends BaseVO{
	private String accountId;
	private String accountCode;
	private String paymentFlag;
	private String billCycle;
	private String billCycleDesc;
	private PersonalInfoVO contactPerson;
	private AddressVO address;
	private List<SubscriberVO> subscriberList;
	private List<BillMediumVO> billMediumList;
	public String getAccountId() {
		return accountId;
	}
	public String getAccountCode() {
		return accountCode;
	}
	public String getPaymentFlag() {
		return paymentFlag;
	}
	public String getBillCycle() {
		return billCycle;
	}
	public String getBillCycleDesc() {
		return billCycleDesc;
	}
	public PersonalInfoVO getContactPerson() {
		return contactPerson;
	}
	public AddressVO getAddress() {
		return address;
	}
	public void setAccountId(String accountId) {
		this.accountId = accountId;
	}
	public void setAccountCode(String accountCode) {
		this.accountCode = accountCode;
	}
	public void setPaymentFlag(String paymentFlag) {
		this.paymentFlag = paymentFlag;
	}
	public void setBillCycle(String billCycle) {
		this.billCycle = billCycle;
	}
	public void setBillCycleDesc(String billCycleDesc) {
		this.billCycleDesc = billCycleDesc;
	}
	public void setContactPerson(PersonalInfoVO contactPerson) {
		this.contactPerson = contactPerson;
	}
	public void setAddress(AddressVO address) {
		this.address = address;
	}
	public List<SubscriberVO> getSubscriberList() {
		return subscriberList;
	}
	public void setSubscriberList(List<SubscriberVO> subscriberList) {
		this.subscriberList = subscriberList;
	}
	public List<BillMediumVO> getBillMediumList() {
		return billMediumList;
	}
	public void setBillMediumList(List<BillMediumVO> billMediumList) {
		this.billMediumList = billMediumList;
	}
	
}
