package com.unieap.pojo;

import java.util.Date;

/**
 * MNote entity.
 * 
 * @author MyEclipse Persistence Tools
 */

public class MNote implements java.io.Serializable {

	// Fields

	private Integer noteId;
	private Integer defectId;
	private String description;
	private Date createDate;
	private Date modifyDate;
	private String modifyBy;
	private String createBy;
	private String enable;

	// Constructors

	/** default constructor */
	public MNote() {
	}

	/** minimal constructor */
	public MNote(Integer noteId, Integer defectId, String description,
			Date createDate, String createBy, String enable) {
		this.noteId = noteId;
		this.defectId = defectId;
		this.description = description;
		this.createDate = createDate;
		this.createBy = createBy;
		this.enable = enable;
	}

	/** full constructor */
	public MNote(Integer noteId, Integer defectId, String description,
			Date createDate, Date modifyDate, String modifyBy, String createBy,
			String enable) {
		this.noteId = noteId;
		this.defectId = defectId;
		this.description = description;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.createBy = createBy;
		this.enable = enable;
	}

	// Property accessors

	public Integer getNoteId() {
		return this.noteId;
	}

	public void setNoteId(Integer noteId) {
		this.noteId = noteId;
	}

	public Integer getDefectId() {
		return this.defectId;
	}

	public void setDefectId(Integer defectId) {
		this.defectId = defectId;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getCreateDate() {
		return this.createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
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

	public String getCreateBy() {
		return this.createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getEnable() {
		return this.enable;
	}

	public void setEnable(String enable) {
		this.enable = enable;
	}

}