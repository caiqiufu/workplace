package com.apps.esb.pojo;
// Generated 2015-10-21 17:34:50 by Hibernate Tools 4.3.1

import java.util.Date;

/**
 * EsbInfoDevice generated by hbm2java
 */
public class EsblogDevice implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer id;
	private Integer logId;
	private String serviceNumber;
	private String imei;
	private String ostype;
	private String osversion;
	private String apkversion;
	private String networkType;
	private String resolution;
	private String brand;
	private String model;
	private Date createDate;
	private String attribute1;
	private String attribute2;

	public EsblogDevice() {
	}

	public EsblogDevice(Integer id, Integer logId) {
		this.id = id;
		this.logId = logId;
	}

	public EsblogDevice(Integer id, Integer logId, String serviceNumber, String imei, String ostype, String osversion,
			String apkversion, String networkType, String resolution, String brand, String model, Date createDate,
			String attribute1, String attribute2) {
		this.id = id;
		this.logId = logId;
		this.serviceNumber = serviceNumber;
		this.imei = imei;
		this.ostype = ostype;
		this.osversion = osversion;
		this.apkversion = apkversion;
		this.networkType = networkType;
		this.resolution = resolution;
		this.brand = brand;
		this.model = model;
		this.createDate = createDate;
		this.attribute1 = attribute1;
		this.attribute2 = attribute2;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getLogId() {
		return this.logId;
	}

	public void setLogId(Integer logId) {
		this.logId = logId;
	}

	public String getServiceNumber() {
		return this.serviceNumber;
	}

	public void setServiceNumber(String serviceNumber) {
		this.serviceNumber = serviceNumber;
	}

	public String getImei() {
		return this.imei;
	}

	public void setImei(String imei) {
		this.imei = imei;
	}

	public String getOstype() {
		return this.ostype;
	}

	public void setOstype(String ostype) {
		this.ostype = ostype;
	}

	public String getOsversion() {
		return this.osversion;
	}

	public void setOsversion(String osversion) {
		this.osversion = osversion;
	}

	public String getApkversion() {
		return this.apkversion;
	}

	public void setApkversion(String apkversion) {
		this.apkversion = apkversion;
	}

	public String getNetworkType() {
		return this.networkType;
	}

	public void setNetworkType(String networkType) {
		this.networkType = networkType;
	}

	public String getResolution() {
		return this.resolution;
	}

	public void setResolution(String resolution) {
		this.resolution = resolution;
	}

	public String getBrand() {
		return this.brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getModel() {
		return this.model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getAttribute1() {
		return this.attribute1;
	}

	public void setAttribute1(String attribute1) {
		this.attribute1 = attribute1;
	}

	public String getAttribute2() {
		return this.attribute2;
	}

	public void setAttribute2(String attribute2) {
		this.attribute2 = attribute2;
	}

}
