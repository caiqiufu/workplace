package com.apps.esb.service.bss.vo.mshop.user;

import java.util.Map;

import com.unieap.base.vo.BaseVO;

public class MshopUserVO extends BaseVO{
	private String userId;
	private String userCode;
	private String userName;
	private Map<String,PermissionVO> menuList;
	private Map<String,PermissionVO> buttonList;
	private Map<String,PermissionVO> dicList;
	public String getUserId() {
		return userId;
	}
	public String getUserCode() {
		return userCode;
	}
	public String getUserName() {
		return userName;
	}
	public Map<String, PermissionVO> getMenuList() {
		return menuList;
	}
	public Map<String, PermissionVO> getButtonList() {
		return buttonList;
	}
	public Map<String, PermissionVO> getDicList() {
		return dicList;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public void setMenuList(Map<String, PermissionVO> menuList) {
		this.menuList = menuList;
	}
	public void setButtonList(Map<String, PermissionVO> buttonList) {
		this.buttonList = buttonList;
	}
	public void setDicList(Map<String, PermissionVO> dicList) {
		this.dicList = dicList;
	}
	
	
}
