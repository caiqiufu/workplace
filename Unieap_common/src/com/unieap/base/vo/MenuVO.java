package com.unieap.base.vo;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;

public class MenuVO extends BaseVO{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long menuId;
    public String menuCode;
    public String menuName;
    public String parentId;
    public String href;
    public String activeFlag;
    public String remark;
    public String seqNum;
    public String imgSrc;
    public MenuVO parent;
    public String title;
    public String isDefault;
    public String menuType;
    public String separateLine;
    public List<MenuVO> childrenContainer = new ArrayList<MenuVO>();
    public List<MenuVO> modules = new ArrayList<MenuVO>();
    public List<MenuVO> getChildrenContainer() {
        return childrenContainer;
    }
    public void setChildrenContainer(List<MenuVO> childrenContainer) {
        this.childrenContainer = childrenContainer;
    }
    public void addChildren(MenuVO menu){
        this.childrenContainer.add(menu);
    }
    public List<MenuVO> getChildren(){
        return this.childrenContainer;
    }
    public MenuVO getParent() {
        return parent;
    }
    public void setParent(MenuVO parent) {
        this.parent = parent;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public MenuVO(Long menuId, String menuCode, String menuName, String title) {
	    super();
	    this.menuId = menuId;
	    this.menuCode = menuCode;
	    this.menuName = menuName;
	    this.title = title;
    }
    public MenuVO(Long menuId, String menuCode, String menuName, String title,String href) {
	    super();
	    this.menuId = menuId;
	    this.menuCode = menuCode;
	    this.menuName = menuName;
	    this.title = title;
	    this.href = href;
    }
	public Long getMenuId() {
    	return menuId;
    }
	public void setMenuId(Long menuId) {
    	this.menuId = menuId;
    }
	public String getMenuCode() {
    	return menuCode;
    }
	public void setMenuCode(String menuCode) {
    	this.menuCode = menuCode;
    }
	public String getMenuName() {
    	return menuName;
    }
	public void setMenuName(String menuName) {
    	this.menuName = menuName;
    }
	public String getParentId() {
    	return parentId;
    }
	public void setParentId(String parentId) {
    	this.parentId = parentId;
    }
	public String getHref() {
    	return href;
    }
	public void setHref(String href) {
    	this.href = href;
    }
	public String getActiveFlag() {
    	return activeFlag;
    }
	public void setActiveFlag(String activeFlag) {
    	this.activeFlag = activeFlag;
    }
	public String getRemark() {
    	return remark;
    }
	public void setRemark(String remark) {
    	this.remark = remark;
    }
	public String getSeqNum() {
    	return seqNum;
    }
	public void setSeqNum(String seqNum) {
    	this.seqNum = seqNum;
    }
	public String getImgSrc() {
    	return imgSrc;
    }
	public void setImgSrc(String imgSrc) {
    	this.imgSrc = imgSrc;
    }
	public boolean isSeparateLine() {
        return StringUtils.equals(separateLine,"Y");
    }
    public void setSeparateLine(String separateLine) {
        this.separateLine = separateLine;
    }
    public boolean isLeafNode() {
        return this.getChildrenContainer()==null||this.getChildrenContainer().size()==0;
    }
	public MenuVO() {
	    super();
    }
	public boolean isHaveChild(){
		if(this.getChildrenContainer()!=null&&this.getChildrenContainer().size()>0){
			return true;
		}else{
			return false;
		}
	}
	public String toString(){
		return "menuId="+menuId+";menuCode="+menuCode+";menuName="+menuName+";parentId="+parentId+";title="+title+";href="+href;
	}
	public List<MenuVO> getModules() {
    	return modules;
    }
	public void setModules(List<MenuVO> modules) {
    	this.modules = modules;
    }
	public String getMenuType() {
    	return menuType;
    }
	public void setMenuType(String menuType) {
    	this.menuType = menuType;
    }
	public String getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(String isDefault) {
		this.isDefault = isDefault;
	}
	public String getSeparateLine() {
		return separateLine;
	}
}
