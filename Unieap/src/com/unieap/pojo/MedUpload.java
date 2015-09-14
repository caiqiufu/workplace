package com.unieap.pojo;

/**
 * MedUpload entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MedUpload implements java.io.Serializable {

	// Fields

	private Integer id;
	private Integer fileSize;
	private String fileName;
	private String disbeginTime;
	private String disendTime;
	private String flowId;
	private String dispatchComponentId;
	private String dispatchId;
	private Integer fileNums;
	private String fileLocalname;

	// Constructors

	/** default constructor */
	public MedUpload() {
	}

	/** minimal constructor */
	public MedUpload(Integer id, Integer fileSize) {
		this.id = id;
		this.fileSize = fileSize;
	}

	/** full constructor */
	public MedUpload(Integer id, Integer fileSize, String fileName,
			String disbeginTime, String disendTime, String flowId,
			String dispatchComponentId, String dispatchId, Integer fileNums,
			String fileLocalname) {
		this.id = id;
		this.fileSize = fileSize;
		this.fileName = fileName;
		this.disbeginTime = disbeginTime;
		this.disendTime = disendTime;
		this.flowId = flowId;
		this.dispatchComponentId = dispatchComponentId;
		this.dispatchId = dispatchId;
		this.fileNums = fileNums;
		this.fileLocalname = fileLocalname;
	}

	// Property accessors

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getFileSize() {
		return this.fileSize;
	}

	public void setFileSize(Integer fileSize) {
		this.fileSize = fileSize;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getDisbeginTime() {
		return this.disbeginTime;
	}

	public void setDisbeginTime(String disbeginTime) {
		this.disbeginTime = disbeginTime;
	}

	public String getDisendTime() {
		return this.disendTime;
	}

	public void setDisendTime(String disendTime) {
		this.disendTime = disendTime;
	}

	public String getFlowId() {
		return this.flowId;
	}

	public void setFlowId(String flowId) {
		this.flowId = flowId;
	}

	public String getDispatchComponentId() {
		return this.dispatchComponentId;
	}

	public void setDispatchComponentId(String dispatchComponentId) {
		this.dispatchComponentId = dispatchComponentId;
	}

	public String getDispatchId() {
		return this.dispatchId;
	}

	public void setDispatchId(String dispatchId) {
		this.dispatchId = dispatchId;
	}

	public Integer getFileNums() {
		return this.fileNums;
	}

	public void setFileNums(Integer fileNums) {
		this.fileNums = fileNums;
	}

	public String getFileLocalname() {
		return this.fileLocalname;
	}

	public void setFileLocalname(String fileLocalname) {
		this.fileLocalname = fileLocalname;
	}

}