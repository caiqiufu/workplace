package com.unieap.mdm.vo;

import org.springframework.util.StringUtils;

import com.unieap.CacheMgt;

public class SystemConfigVO {
	private Integer id;
	private String type;
	private String typeDesc;
	private String name;
	private String value;
	private String description;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getTypeDesc() {
		if (!StringUtils.isEmpty(this.type)) {
			DicDataVO dic = CacheMgt.getDicData("systemConfigType", type);
			if (dic != null) {
				this.typeDesc = dic.getDicName();
			}else{
				this.typeDesc = type;
			}
		}
		return typeDesc;
	}
	public void setTypeDesc(String typeDesc) {
		this.typeDesc = typeDesc;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
}
