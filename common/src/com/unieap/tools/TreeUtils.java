package com.unieap.tools;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.unieap.base.vo.TreeVO;

public class TreeUtils {
	public static JSONObject convertBean2JSON(TreeVO vo) throws Exception {
		JSONObject json = new JSONObject();
		json.put("id", vo.getId());
		json.put("parentId", vo.getParentId());
		json.put("text", vo.getText());
		json.put("qtip", vo.getQtip());
		json.put("iconCls", vo.getIconCls());
		json.put("leaf", vo.isLeaf());
		json.put("expanded", vo.isExpanded());
		if(vo.isCheckBoxTree()){
			json.put("checked", vo.isChecked());
		}
		List<TreeVO> child = vo.getChildren(); 
		if(child!=null&&child.size()>0){
			JSONArray jarrchild = new JSONArray();
			for(int i = 0; i < child.size(); i++){
				jarrchild.put(convertBean2JSON(child.get(i)));
			}
			json.put("children", jarrchild);
		}
		Map extAtt = vo.getExtendAttri();
		if(extAtt!=null&&!extAtt.isEmpty()){
			Iterator<String> iter = extAtt.keySet().iterator();
			JSONObject jsonext = new JSONObject();
			while (iter.hasNext()) {
				String key = (String) iter.next();
				Object val = extAtt.get(key);
				jsonext.put(key, val);
			}
			json.put("extendAttri", jsonext);
		}
		return json;
	}
}
