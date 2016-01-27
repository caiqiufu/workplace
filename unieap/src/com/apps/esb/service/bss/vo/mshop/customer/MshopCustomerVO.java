package com.apps.esb.service.bss.vo.mshop.customer;

import java.util.List;

import com.apps.esb.service.bss.app.vo.common.attachment.AttachmentVO;
import com.apps.esb.service.bss.vo.mshop.group.MshopGroupVO;
import com.unieap.base.vo.BaseVO;

public class MshopCustomerVO extends BaseVO{
	private String customerId;
	private String certificateType;
	private String certificateTypeDesc;
	private String certificateNumber;
	private String title;
	private String titleDesc;
	private String firstName;
	private String middleName;
	private String lastName;
	private String issuingCountry;
	private String gender;
	private String genderDesc;
	private String dateOfBirth;
	private String customerLevel;
	private String customerLevelDesc;
	private String language;
	private String verifiedCustomer;
	private String remark;
	//for corporate
	private String corporateName;
	private String corporateShortName;
	private String corporateType;
	private String corporateTypeDesc;
	private String bRNExpiryDate;
	private String industry;
	private String industryDesc;
	private String phoneNumber;
	private String email;
	private String sizeLevel;
	private String sizeLevelDesc;
	private String tin;
	
	private List<MshopAddressVO> addressList;
	private List<AttachmentVO> attachmentList;
	private List<MshopGroupVO> groupList;
	public String getCustomerId() {
		return customerId;
	}
	public String getCertificateType() {
		return certificateType;
	}
	public String getCertificateTypeDesc() {
		return certificateTypeDesc;
	}
	public String getCertificateNumber() {
		return certificateNumber;
	}
	public String getTitle() {
		return title;
	}
	public String getTitleDesc() {
		return titleDesc;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getIssuingCountry() {
		return issuingCountry;
	}
	public String getGender() {
		return gender;
	}
	public String getGenderDesc() {
		return genderDesc;
	}
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	public String getCustomerLevel() {
		return customerLevel;
	}
	public String getCustomerLevelDesc() {
		return customerLevelDesc;
	}
	public String getLanguage() {
		return language;
	}
	public String getVerifiedCustomer() {
		return verifiedCustomer;
	}
	public String getRemark() {
		return remark;
	}
	public String getCorporateName() {
		return corporateName;
	}
	public String getCorporateShortName() {
		return corporateShortName;
	}
	public String getCorporateType() {
		return corporateType;
	}
	public String getCorporateTypeDesc() {
		return corporateTypeDesc;
	}
	public String getbRNExpiryDate() {
		return bRNExpiryDate;
	}
	public String getIndustry() {
		return industry;
	}
	public String getIndustryDesc() {
		return industryDesc;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public String getEmail() {
		return email;
	}
	public String getSizeLevel() {
		return sizeLevel;
	}
	public String getSizeLevelDesc() {
		return sizeLevelDesc;
	}
	public String getTin() {
		return tin;
	}
	
	public List<AttachmentVO> getAttachmentList() {
		return attachmentList;
	}
	public void setCustomerId(String customerId) {
		this.customerId = customerId;
	}
	public void setCertificateType(String certificateType) {
		this.certificateType = certificateType;
	}
	public void setCertificateTypeDesc(String certificateTypeDesc) {
		this.certificateTypeDesc = certificateTypeDesc;
	}
	public void setCertificateNumber(String certificateNumber) {
		this.certificateNumber = certificateNumber;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setIssuingCountry(String issuingCountry) {
		this.issuingCountry = issuingCountry;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setGenderDesc(String genderDesc) {
		this.genderDesc = genderDesc;
	}
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	public void setCustomerLevel(String customerLevel) {
		this.customerLevel = customerLevel;
	}
	public void setCustomerLevelDesc(String customerLevelDesc) {
		this.customerLevelDesc = customerLevelDesc;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public void setVerifiedCustomer(String verifiedCustomer) {
		this.verifiedCustomer = verifiedCustomer;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public void setCorporateName(String corporateName) {
		this.corporateName = corporateName;
	}
	public void setCorporateShortName(String corporateShortName) {
		this.corporateShortName = corporateShortName;
	}
	public void setCorporateType(String corporateType) {
		this.corporateType = corporateType;
	}
	public void setCorporateTypeDesc(String corporateTypeDesc) {
		this.corporateTypeDesc = corporateTypeDesc;
	}
	public void setbRNExpiryDate(String bRNExpiryDate) {
		this.bRNExpiryDate = bRNExpiryDate;
	}
	public void setIndustry(String industry) {
		this.industry = industry;
	}
	public void setIndustryDesc(String industryDesc) {
		this.industryDesc = industryDesc;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public void setSizeLevel(String sizeLevel) {
		this.sizeLevel = sizeLevel;
	}
	public void setSizeLevelDesc(String sizeLevelDesc) {
		this.sizeLevelDesc = sizeLevelDesc;
	}
	public void setTin(String tin) {
		this.tin = tin;
	}
	
	public void setAttachmentList(List<AttachmentVO> attachmentList) {
		this.attachmentList = attachmentList;
	}
	public List<MshopAddressVO> getAddressList() {
		return addressList;
	}
	public void setAddressList(List<MshopAddressVO> addressList) {
		this.addressList = addressList;
	}
	public List<MshopGroupVO> getGroupList() {
		return groupList;
	}
	public void setGroupList(List<MshopGroupVO> groupList) {
		this.groupList = groupList;
	}
	
}
