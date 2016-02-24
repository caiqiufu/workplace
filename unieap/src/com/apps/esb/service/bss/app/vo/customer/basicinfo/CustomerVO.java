package com.apps.esb.service.bss.app.vo.customer.basicinfo;

import java.util.List;

import com.apps.esb.service.bss.app.vo.common.address.AddressVO;
import com.apps.esb.service.bss.app.vo.subscriber.basicinfo.SubscriberVO;
import com.unieap.base.vo.BaseVO;

public class CustomerVO extends BaseVO{
	private String customerId;
	private String certificateType;
	private String certificateTypeDesc;
	private String certificateNumber;
	private String title;
	private String titleDesc;
	private String firstName;
	private String middleName;
	private String lastName;
	private String nationality;
	private String gender;
	private String genderDesc;
	private String dateOfBirth;
	private String customerLevel;
	private String customerLevelDesc;
	private String status;
	private String statusDesc;
	private String email;
	private String language;
	private List<AddressVO> addressList;
	private List<SubscriberVO> subscriberList;
	public String getCertificateType() {
		return certificateType;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public String getCertificateNumber() {
		return certificateNumber;
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getNationality() {
		return nationality;
	}
	public void setNationality(String nationality) {
		this.nationality = nationality;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public List<AddressVO> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<AddressVO> addressList) {
		this.addressList = addressList;
	}
	public String getCertificateTypeDesc() {
		return certificateTypeDesc;
	}
	public void setCertificateTypeDesc(String certificateTypeDesc) {
		this.certificateTypeDesc = certificateTypeDesc;
	}
	public String getCustomerLevel() {
		return customerLevel;
	}
	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}
	public String getCustomerId() {
		return customerId;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStatusDesc() {
		return statusDesc;
	}
	public void setStatusDesc(String statusDesc) {
		this.statusDesc = statusDesc;
	}
	public String getTitleDesc() {
		return titleDesc;
	}
	public String getGenderDesc() {
		return genderDesc;
	}
	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}
	public void setGenderDesc(String genderDesc) {
		this.genderDesc = genderDesc;
	}
	public String getCustomerLevelDesc() {
		return customerLevelDesc;
	}
	public void setCustomerLevelDesc(String customerLevelDesc) {
		this.customerLevelDesc = customerLevelDesc;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public List<SubscriberVO> getSubscriberList() {
		return subscriberList;
	}
	public void setSubscriberList(List<SubscriberVO> subscriberList) {
		this.subscriberList = subscriberList;
	}
	
	
}
