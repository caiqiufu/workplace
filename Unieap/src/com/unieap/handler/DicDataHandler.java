package com.unieap.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.base.SYSConfig;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.mdm.vo.DicDataVO;

@Service("dicDataHandler")
public class DicDataHandler extends BaseBO implements ConfigHandler{

	@Override
	public void dealNode(Node node, ServletContext servlet, String appName)
			throws Exception {
	}

	@Override
	public void deal(String parameters) throws Exception {
		
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT dic_code as dicCode,dic_name as dicName,parent_code as groupCode,parent_name as groupName,");
		sql.append(" seq, active_flag as activeFlag FROM unieap.dic_data_tree where language= ? and tenant_id = ? or tenant_id is null order by parent_code,seq");
		List<Object> datas = DBManager.getJT(null).query(sql.toString(),new Object[]{SYSConfig.defaultLanguage,SYSConfig.getConfig().get("tenantId")},new EntityRowMapper(DicDataVO.class));
		if (datas != null && datas.size() > 0) {
			Map<String, Map<String,DicDataVO>> dicData = new HashMap<String, Map<String,DicDataVO>>();
			for (Object data:datas) {
				DicDataVO vo = (DicDataVO)data;
				String groupCode  = vo.getGroupCode();
				if(dicData.get(groupCode)!=null){
					dicData.get(groupCode).put(vo.getDicCode(), vo);
				}else{
					Map<String, DicDataVO> dv = new HashMap<String, DicDataVO>();
					dv.put(vo.getDicCode(), vo);
					dicData.put(groupCode, dv);
				}
			}
			CacheMgt.setDicData(dicData);
		}
	}

	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return true;
	}
}
