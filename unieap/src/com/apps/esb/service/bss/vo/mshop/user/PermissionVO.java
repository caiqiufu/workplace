package com.apps.esb.service.bss.vo.mshop.user;

import com.unieap.base.vo.BaseVO;

public class PermissionVO extends BaseVO{
	private String seq;
	/**
	 * M: Menu
	 * B: Button
	 * D: Dictionary 
	 */
	private String type;
	private String code;
	private String name;
	private boolean isAbled; 
	private String href;
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	
	public String getType() {
		return type;
	}
	public String getCode() {
		return code;
	}
	public String getName() {
		return name;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getHref() {
		return href;
	}
	public void setHref(String href) {
		this.href = href;
	}
	public boolean isAbled() {
		return isAbled;
	}
	public void setAbled(boolean isAbled) {
		this.isAbled = isAbled;
	}
	
}
