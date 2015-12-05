package com.apps.reuse.pojo;
// default package
// Generated 2015-7-14 21:38:33 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * ReuseCustomerAddress generated by hbm2java
 */
public class ReuseCustomerAddress implements java.io.Serializable {

	private Integer addressId;
	private Integer customerId;
	private String type;
	private String defaultFlag;
	private String province;
	private String city;
	private String district;
	private String street;
	private String postcode;
	private String contact;
	private String address1;
	private String address2;
	private Date createDate;
	private String createBy;
	private Date modifyDate;
	private String modifyBy;
	private String phone;

	public ReuseCustomerAddress() {
	}

	public ReuseCustomerAddress(Integer addressId, Integer customerId, String type, String defaultFlag, String contact,
			Date createDate, String createBy, String phone) {
		this.addressId = addressId;
		this.customerId = customerId;
		this.type = type;
		this.defaultFlag = defaultFlag;
		this.contact = contact;
		this.createDate = createDate;
		this.createBy = createBy;
		this.phone = phone;
	}

	public ReuseCustomerAddress(Integer addressId, Integer customerId, String type, String defaultFlag, String province,
			String city, String district, String street, String postcode, String contact, String address1,
			String address2, Date createDate, String createBy, Date modifyDate, String modifyBy, String phone) {
		this.addressId = addressId;
		this.customerId = customerId;
		this.type = type;
		this.defaultFlag = defaultFlag;
		this.province = province;
		this.city = city;
		this.district = district;
		this.street = street;
		this.postcode = postcode;
		this.contact = contact;
		this.address1 = address1;
		this.address2 = address2;
		this.createDate = createDate;
		this.createBy = createBy;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.phone = phone;
	}

	public Integer getAddressId() {
		return this.addressId;
	}

	public void setAddressId(Integer addressId) {
		this.addressId = addressId;
	}

	public Integer getCustomerId() {
		return this.customerId;
	}

	public void setCustomerId(Integer customerId) {
		this.customerId = customerId;
	}

	public String getType() {
		return this.type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDefaultFlag() {
		return this.defaultFlag;
	}

	public void setDefaultFlag(String defaultFlag) {
		this.defaultFlag = defaultFlag;
	}

	public String getProvince() {
		return this.province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCity() {
		return this.city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDistrict() {
		return this.district;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public String getStreet() {
		return this.street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPostcode() {
		return this.postcode;
	}

	public void setPostcode(String postcode) {
		this.postcode = postcode;
	}

	public String getContact() {
		return this.contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getAddress1() {
		return this.address1;
	}

	public void setAddress1(String address1) {
		this.address1 = address1;
	}

	public String getAddress2() {
		return this.address2;
	}

	public void setAddress2(String address2) {
		this.address2 = address2;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public Date getModifyDate() {
		return this.modifyDate;
	}

	public void setModifyDate(Date modifyDate) {
		this.modifyDate = modifyDate;
	}

	public String getModifyBy() {
		return this.modifyBy;
	}

	public void setModifyBy(String modifyBy) {
		this.modifyBy = modifyBy;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}