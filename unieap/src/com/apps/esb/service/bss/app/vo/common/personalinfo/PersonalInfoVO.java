package com.apps.esb.service.bss.app.vo.common.personalinfo;

import com.unieap.base.vo.BaseVO;

public class PersonalInfoVO extends BaseVO {
	private String id;
	private String firstName;
	private String middleName;
	private String lastName;
	private String gender;
	private String genderDesc;
	private String title;
	private String titleDesc;
	public String getId() {
		return id;
	}
	public String getFirstName() {
		return firstName;
	}
	public String getMiddleName() {
		return middleName;
	}
	public String getLastName() {
		return lastName;
	}
	public String getGender() {
		return gender;
	}
	public String getGenderDesc() {
		return genderDesc;
	}
	public String getTitle() {
		return title;
	}
	public String getTitleDesc() {
		return titleDesc;
	}
	public void setId(String id) {
		this.id = id;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public void setGenderDesc(String genderDesc) {
		this.genderDesc = genderDesc;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setTitleDesc(String titleDesc) {
		this.titleDesc = titleDesc;
	}
	
}
