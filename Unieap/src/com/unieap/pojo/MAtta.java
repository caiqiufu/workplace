package com.unieap.pojo;

import java.util.Date;

/**
 * MAtta entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MAtta implements java.io.Serializable {

	// Fields

	private Integer attaId;
	private Integer defectId;
	private String fileName;
	private String fileSize;
	private String filePath;
	private Date createDate;
	private String fileCont;
	private String fileType;

	// Constructors

	/** default constructor */
	public MAtta() {
	}

	/** minimal constructor */
	public MAtta(Integer attaId, Integer defectId, String fileName,
			String fileSize, String filePath, Date createDate) {
		this.attaId = attaId;
		this.defectId = defectId;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.filePath = filePath;
		this.createDate = createDate;
	}

	/** full constructor */
	public MAtta(Integer attaId, Integer defectId, String fileName,
			String fileSize, String filePath, Date createDate, String fileCont,
			String fileType) {
		this.attaId = attaId;
		this.defectId = defectId;
		this.fileName = fileName;
		this.fileSize = fileSize;
		this.filePath = filePath;
		this.createDate = createDate;
		this.fileCont = fileCont;
		this.fileType = fileType;
	}

	// Property accessors

	public Integer getAttaId() {
		return this.attaId;
	}

	public void setAttaId(Integer attaId) {
		this.attaId = attaId;
	}

	public Integer getDefectId() {
		return this.defectId;
	}

	public void setDefectId(Integer defectId) {
		this.defectId = defectId;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public String getFilePath() {
		return this.filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public String getFileCont() {
		return this.fileCont;
	}

	public void setFileCont(String fileCont) {
		this.fileCont = fileCont;
	}

	public String getFileType() {
		return this.fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

}