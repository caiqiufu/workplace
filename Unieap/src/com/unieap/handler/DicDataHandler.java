package com.unieap.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.mdm.vo.DicDataVO;

@Service("dicDataHandler")
public class DicDataHandler extends BaseBO implements ConfigHandler{

	@Override
	public void dealNode(Node node, ServletContext servlet, String appName)
			throws Exception {
		// TODO Auto-generated method stub
		StringBuffer sql = new StringBuffer();
		sql.append("select d.group_id groupId,g.group_name groupName,d.dic_id dicId,d.dic_code dicCode, d.dic_name dicName,d.seq from")
		.append(" unieap.dic_data d, unieap.dic_group g where g.group_id = d.group_id order by d.seq");
		List<Object> datas = DBManager.getJT(null).query(sql.toString(),new EntityRowMapper(DicDataVO.class));
		if (datas != null && datas.size() > 0) {
			Map<String, Map<String,DicDataVO>> dicData = new HashMap<String, Map<String,DicDataVO>>();
			for (Object data:datas) {
				DicDataVO vo = (DicDataVO)data;
				String groupId  = vo.getGroupId().toString();
				if(dicData.get(groupId)!=null){
					dicData.get(groupId).put(vo.getDicCode(), vo);
				}else{
					Map<String, DicDataVO> dv = new HashMap<String, DicDataVO>();
					dv.put(vo.getDicCode(), vo);
					dicData.put(groupId, dv);
				}
			}
			CacheMgt.setDicData(dicData);
		}
	}
}
