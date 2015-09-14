package com.unieap.pojo;

import java.util.Date;

/**
 * RExelog entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RExelog implements java.io.Serializable {

	// Fields

	private Integer id;
	private Date executeDate;
	private String bizDate;
	private String typeCode;
	private String typeName;
	private String result;
	private String resultdesc;

	// Constructors

	/** default constructor */
	public RExelog() {
	}

	/** minimal constructor */
	public RExelog(Integer id, Date executeDate, String typeCode) {
		this.id = id;
		this.executeDate = executeDate;
		this.typeCode = typeCode;
	}

	/** full constructor */
	public RExelog(Integer id, Date executeDate, String bizDate,
			String typeCode, String typeName, String result, String resultdesc) {
		this.id = id;
		this.executeDate = executeDate;
		this.bizDate = bizDate;
		this.typeCode = typeCode;
		this.typeName = typeName;
		this.result = result;
		this.resultdesc = resultdesc;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Date getExecuteDate() {
		return this.executeDate;
	}

	public void setExecuteDate(Date executeDate) {
		this.executeDate = executeDate;
	}

	public String getBizDate() {
		return this.bizDate;
	}

	public void setBizDate(String bizDate) {
		this.bizDate = bizDate;
	}

	public String getTypeCode() {
		return this.typeCode;
	}

	public void setTypeCode(String typeCode) {
		this.typeCode = typeCode;
	}

	public String getTypeName() {
		return this.typeName;
	}

	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getResultdesc() {
		return this.resultdesc;
	}

	public void setResultdesc(String resultdesc) {
		this.resultdesc = resultdesc;
	}

}