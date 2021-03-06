package com.apps.easymobile.pojo;
// Generated 2015-10-31 16:55:27 by Hibernate Tools 4.3.1

import java.util.Date;

import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;
import com.unieap.mdm.vo.DicDataVO;

/**
 * AppOfferingCategory generated by hbm2java
 */
public class AppOfferingCategory implements java.io.Serializable {

	private Integer id;
	private String categoryType;
	private String categoryTypeDesc;
	private String categoreName;
	private String seq;
	private String categoryDesc;
	private String pictureUrl;
	private String priceDesc;
	private String activeFlag;
	private String activeFlagDesc;
	private Date createDate;
	private String createBy;
	private Date modifyDate;
	private String modifyBy;
	private String remark;
	private String detailUrl;
	private String detailHyperlink;
	private String planUrl;
	private String planHyperlink;
	private String questionUrl;
	private String questionHyperlink;
	private String noteUrl;
	private String noteHyperlink;

	public AppOfferingCategory() {
	}

	public AppOfferingCategory(Integer id, String categoryType, String categoreName, String activeFlag, Date createDate,
			String createBy) {
		this.id = id;
		this.categoryType = categoryType;
		this.categoreName = categoreName;
		this.activeFlag = activeFlag;
		this.createDate = createDate;
		this.createBy = createBy;
	}

	public AppOfferingCategory(Integer id, String categoryType, String categoreName, String seq, String categoryDesc,
			String pictureUrl, String priceDesc, String activeFlag, Date createDate, String createBy, Date modifyDate,
			String modifyBy, String remark, String detailUrl, String detailHyperlink, String planUrl,
			String planHyperlink, String questionUrl, String questionHyperlink, String noteUrl, String noteHyperlink) {
		this.id = id;
		this.categoryType = categoryType;
		this.categoreName = categoreName;
		this.seq = seq;
		this.categoryDesc = categoryDesc;
		this.pictureUrl = pictureUrl;
		this.priceDesc = priceDesc;
		this.activeFlag = activeFlag;
		this.createDate = createDate;
		this.createBy = createBy;
		this.modifyDate = modifyDate;
		this.modifyBy = modifyBy;
		this.remark = remark;
		this.detailUrl = detailUrl;
		this.detailHyperlink = detailHyperlink;
		this.planUrl = planUrl;
		this.planHyperlink = planHyperlink;
		this.questionUrl = questionUrl;
		this.questionHyperlink = questionHyperlink;
		this.noteUrl = noteUrl;
		this.noteHyperlink = noteHyperlink;
	}

	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCategoryType() {
		return this.categoryType;
	}

	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
		if(!StringUtils.isEmpty(this.categoryType)){
			DicDataVO dic =  CacheMgt.getDicData("offerCategoryType",categoryType);
			if(dic!=null){
				this.categoryTypeDesc = dic.getDicName();
			}
		}
	}

	public String getCategoreName() {
		return this.categoreName;
	}

	public void setCategoreName(String categoreName) {
		this.categoreName = categoreName;
	}

	public String getSeq() {
		return this.seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCategoryDesc() {
		return this.categoryDesc;
	}

	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}

	public String getPictureUrl() {
		return this.pictureUrl;
	}

	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}

	public String getPriceDesc() {
		return this.priceDesc;
	}

	public void setPriceDesc(String priceDesc) {
		this.priceDesc = priceDesc;
	}

	public String getActiveFlag() {
		return this.activeFlag;
	}

	public void setActiveFlag(String activeFlag) {
		this.activeFlag = activeFlag;
		if(!StringUtils.isEmpty(this.activeFlag)){
			DicDataVO dic =  CacheMgt.getDicData("activeFlag",activeFlag);
			if(dic!=null){
				this.activeFlagDesc = dic.getDicName();
			}
		}
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

	public String getRemark() {
		return this.remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getDetailUrl() {
		return this.detailUrl;
	}

	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}

	public String getDetailHyperlink() {
		return this.detailHyperlink;
	}

	public void setDetailHyperlink(String detailHyperlink) {
		this.detailHyperlink = detailHyperlink;
	}

	public String getPlanUrl() {
		return this.planUrl;
	}

	public void setPlanUrl(String planUrl) {
		this.planUrl = planUrl;
	}

	public String getPlanHyperlink() {
		return this.planHyperlink;
	}

	public void setPlanHyperlink(String planHyperlink) {
		this.planHyperlink = planHyperlink;
	}

	public String getQuestionUrl() {
		return this.questionUrl;
	}

	public void setQuestionUrl(String questionUrl) {
		this.questionUrl = questionUrl;
	}

	public String getQuestionHyperlink() {
		return this.questionHyperlink;
	}

	public void setQuestionHyperlink(String questionHyperlink) {
		this.questionHyperlink = questionHyperlink;
	}

	public String getNoteUrl() {
		return this.noteUrl;
	}

	public void setNoteUrl(String noteUrl) {
		this.noteUrl = noteUrl;
	}

	public String getNoteHyperlink() {
		return this.noteHyperlink;
	}

	public void setNoteHyperlink(String noteHyperlink) {
		this.noteHyperlink = noteHyperlink;
	}

	public String getCategoryTypeDesc() {
		return categoryTypeDesc;
	}

	public void setCategoryTypeDesc(String categoryTypeDesc) {
		this.categoryTypeDesc = categoryTypeDesc;
	}

	public String getActiveFlagDesc() {
		return activeFlagDesc;
	}

	public void setActiveFlagDesc(String activeFlagDesc) {
		this.activeFlagDesc = activeFlagDesc;
	}

	
}
