package com.unieap.base.vo;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

public class TreeVO extends BaseVO{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public Long id ;
	public Long parentId ;
	public String text ;
	public String iconCls;
	public String qtip;
	public boolean leaf;
	public boolean expanded ;
	public List<TreeVO> children;
	public Map<String,Object> extendAttri;
	
	private boolean checkBoxTree = false;
	private boolean checked;
	
	public Long getParentId() {
		return parentId;
	}
	public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}
	
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public boolean isLeaf() {
		return leaf;
	}
	public void setLeaf(boolean leaf) {
		this.leaf = leaf;
	}
	public boolean isExpanded() {
		return expanded;
	}
	public void setExpanded(boolean expanded) {
		this.expanded = expanded;
	}
	public List<TreeVO> getChildren() {
		return children;
	}
	public void setChildren(List<TreeVO> children) {
		this.children = children;
	}
	public Map<String,Object> getExtendAttri() {
		return extendAttri;
	}
	public void setExtendAttri(Map<String,Object> extendAttri) {
		this.extendAttri = extendAttri;
	}
	
	
	public String getQtip() {
		return qtip;
	}
	public void setQtip(String qtip) {
		this.qtip = qtip;
	}
	public boolean isCheckBoxTree() {
		return checkBoxTree;
	}
	public void setCheckBoxTree(boolean checkBoxTree) {
		this.checkBoxTree = checkBoxTree;
	}
	public boolean isChecked() {
		return checked;
	}
	public void setChecked(boolean checked) {
		this.checked = checked;
	}
	/*public String getJsonString() throws Exception{
		JSONObject jsonObj = new JSONObject();
		jsonObj.put("id", id);
		jsonObj.put("parentId", parentId);
		jsonObj.put("text", text);
		jsonObj.put("leaf", leaf);
		jsonObj.put("iconCls", iconCls);
		jsonObj.put("expanded", expanded);
		putExtendAttri(extendAttri,jsonObj);
		if(children!=null&&children.size()>0){
			JSONArray jarr = new JSONArray();
			for(TreeVO vo:children){
				JSONObject child = new JSONObject();
				child.put("id", vo.getId());
				child.put("parentId", parentId);
				child.put("text", vo.getText());
				child.put("leaf", vo.isLeaf());
				//child.put("activeFlag", vo.getActiveFlag());
				child.put("expanded", vo.isExpanded());
				putExtendAttri(vo.getExtendAttri(),child);
				jarr.put(child);
			}
			jsonObj.put("children", jarr);
		}
		String jsonString = jsonObj.toString();
		if (SYSConfig.isDebug) {
			log.debug("jsonString:" + jsonString);
		}
		return jsonString;
	}*/
	public void putExtendAttri(Map<String,Object> exAttri,JSONObject jsonObj) throws JSONException{
		if(exAttri!=null){
			Iterator<String> iter = exAttri.keySet().iterator();
			while (iter.hasNext()){
				String key = (String) iter.next();
				Object val = exAttri.get(key);
				if (val == null) {
					jsonObj.put(key, JSONObject.NULL);
					continue;
				}else{
					jsonObj.put(key, val);
				}
			}
			
		}
	}
	
}
