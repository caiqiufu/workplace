package com.unieap.pojo;

/**
 * RMedTransfer entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class RMedTransfer implements java.io.Serializable {

	// Fields

	private Integer id;
	private String transTime;
	private String disSystem;
	private String fileName;
	private String fileNum;
	private String sourceSystem;
	private String result;
	private String configInfo;

	// Constructors

	/** default constructor */
	public RMedTransfer() {
	}

	/** minimal constructor */
	public RMedTransfer(Integer id, String transTime, String disSystem,
			String fileName, String fileNum, String sourceSystem, String result) {
		this.id = id;
		this.transTime = transTime;
		this.disSystem = disSystem;
		this.fileName = fileName;
		this.fileNum = fileNum;
		this.sourceSystem = sourceSystem;
		this.result = result;
	}

	/** full constructor */
	public RMedTransfer(Integer id, String transTime, String disSystem,
			String fileName, String fileNum, String sourceSystem,
			String result, String configInfo) {
		this.id = id;
		this.transTime = transTime;
		this.disSystem = disSystem;
		this.fileName = fileName;
		this.fileNum = fileNum;
		this.sourceSystem = sourceSystem;
		this.result = result;
		this.configInfo = configInfo;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTransTime() {
		return this.transTime;
	}

	public void setTransTime(String transTime) {
		this.transTime = transTime;
	}

	public String getDisSystem() {
		return this.disSystem;
	}

	public void setDisSystem(String disSystem) {
		this.disSystem = disSystem;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileNum() {
		return this.fileNum;
	}

	public void setFileNum(String fileNum) {
		this.fileNum = fileNum;
	}

	public String getSourceSystem() {
		return this.sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getResult() {
		return this.result;
	}

	public void setResult(String result) {
		this.result = result;
	}

	public String getConfigInfo() {
		return this.configInfo;
	}

	public void setConfigInfo(String configInfo) {
		this.configInfo = configInfo;
	}

}