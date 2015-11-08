package com.apps.esb.service.bss.app.vo;

import java.util.List;

public class OfferCategoryVO {
	private String categoryId;
	private String categoryType;
	private String pictureUrl;
	private String categoreName;
	private String categoryDesc;
	private String priceDesc;
	private String detailUrl;
	private String detailHyperlink;
	private String planUrl;
	private String planHyperlink;
	private String questionUrl;
	private String questionHyperlink;
	private String noteUrl;
	private String noteHyperlink;
		
	private List<OfferingVO> offerings;
	
	public String getCategoryType() {
		return categoryType;
	}
	public void setCategoryType(String categoryType) {
		this.categoryType = categoryType;
	}
	public String getPictureUrl() {
		return pictureUrl;
	}
	public void setPictureUrl(String pictureUrl) {
		this.pictureUrl = pictureUrl;
	}
	public String getCategoreName() {
		return categoreName;
	}
	public void setCategoreName(String categoreName) {
		this.categoreName = categoreName;
	}
	public String getCategoryDesc() {
		return categoryDesc;
	}
	public void setCategoryDesc(String categoryDesc) {
		this.categoryDesc = categoryDesc;
	}
	public String getPriceDesc() {
		return priceDesc;
	}
	public void setPriceDesc(String priceDesc) {
		this.priceDesc = priceDesc;
	}
	public List<OfferingVO> getOfferings() {
		return offerings;
	}
	public void setOfferings(List<OfferingVO> offerings) {
		this.offerings = offerings;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	public String getDetailUrl() {
		return detailUrl;
	}
	public void setDetailUrl(String detailUrl) {
		this.detailUrl = detailUrl;
	}
	public String getDetailHyperlink() {
		return detailHyperlink;
	}
	public void setDetailHyperlink(String detailHyperlink) {
		this.detailHyperlink = detailHyperlink;
	}
	public String getPlanUrl() {
		return planUrl;
	}
	public void setPlanUrl(String planUrl) {
		this.planUrl = planUrl;
	}
	public String getPlanHyperlink() {
		return planHyperlink;
	}
	public void setPlanHyperlink(String planHyperlink) {
		this.planHyperlink = planHyperlink;
	}
	public String getQuestionUrl() {
		return questionUrl;
	}
	public void setQuestionUrl(String questionUrl) {
		this.questionUrl = questionUrl;
	}
	public String getQuestionHyperlink() {
		return questionHyperlink;
	}
	public void setQuestionHyperlink(String questionHyperlink) {
		this.questionHyperlink = questionHyperlink;
	}
	public String getNoteUrl() {
		return noteUrl;
	}
	public void setNoteUrl(String noteUrl) {
		this.noteUrl = noteUrl;
	}
	public String getNoteHyperlink() {
		return noteHyperlink;
	}
	public void setNoteHyperlink(String noteHyperlink) {
		this.noteHyperlink = noteHyperlink;
	}
	
	
}
