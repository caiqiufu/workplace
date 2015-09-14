package com.unieap.pojo;

/**
 * MedFileConfig entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MedFileConfig implements java.io.Serializable {

	// Fields

	private Integer fileId;
	private String disSystem;
	private String fileName;
	private String disFilename;
	private String sourceSystem;
	private String configInfo;
	private String disFilenameKey;

	// Constructors

	/** default constructor */
	public MedFileConfig() {
	}

	/** minimal constructor */
	public MedFileConfig(Integer fileId, String disSystem, String fileName,
			String disFilename, String sourceSystem, String disFilenameKey) {
		this.fileId = fileId;
		this.disSystem = disSystem;
		this.fileName = fileName;
		this.disFilename = disFilename;
		this.sourceSystem = sourceSystem;
		this.disFilenameKey = disFilenameKey;
	}

	/** full constructor */
	public MedFileConfig(Integer fileId, String disSystem, String fileName,
			String disFilename, String sourceSystem, String configInfo,
			String disFilenameKey) {
		this.fileId = fileId;
		this.disSystem = disSystem;
		this.fileName = fileName;
		this.disFilename = disFilename;
		this.sourceSystem = sourceSystem;
		this.configInfo = configInfo;
		this.disFilenameKey = disFilenameKey;
	}

	// Property accessors

	public Integer getFileId() {
		return this.fileId;
	}

	public void setFileId(Integer fileId) {
		this.fileId = fileId;
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

	public String getDisFilename() {
		return this.disFilename;
	}

	public void setDisFilename(String disFilename) {
		this.disFilename = disFilename;
	}

	public String getSourceSystem() {
		return this.sourceSystem;
	}

	public void setSourceSystem(String sourceSystem) {
		this.sourceSystem = sourceSystem;
	}

	public String getConfigInfo() {
		return this.configInfo;
	}

	public void setConfigInfo(String configInfo) {
		this.configInfo = configInfo;
	}

	public String getDisFilenameKey() {
		return this.disFilenameKey;
	}

	public void setDisFilenameKey(String disFilenameKey) {
		this.disFilenameKey = disFilenameKey;
	}

}