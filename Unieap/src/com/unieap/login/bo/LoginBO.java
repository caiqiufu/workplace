package com.unieap.login.bo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.vo.ButtonVO;
import com.unieap.base.vo.MenuVO;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.mdm.vo.DicDataVO;
import com.unieap.pojo.VisitLog;
import com.unieap.tools.FileUtils;
import com.unieap.tools.JSONUtils;

/**
 * Jan 5, 2011
 */
@Service("loginBO")
public class LoginBO extends BaseBO {

	private final static String CODELISTJSPATH = "unieap/js/common/";

	public Map<String, List<Object>> getUserMenu(Integer userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT dd.dic_code as menuCode,dd.dic_name as menuName ,dd.href as href ");
		sql.append("FROM unieap.role_resource rr,unieap.user u,unieap.user_role ur,unieap.dic_data_tree dd ");
		sql.append("where u.user_id = ur.user_id and ur.role_id = rr.role_id and dd.dic_type = 'M' ");
		sql.append("and rr.resource_id = dd.dic_code and u.user_id = ? and dd.active_flag =? order by dd.seq ASC");
		List<Object> menus = DBManager.getJT(null).query(sql.toString(), new Object[] { userId, UnieapConstants.YES },
				new EntityRowMapper(MenuVO.class));
		Map<String, List<Object>> attributes = new HashMap<String, List<Object>>();
		attributes.put("menus", menus);
		return attributes;
	}

	public void loadLoginUser() throws Exception {
		org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Property userCode = Property.forName("userCode");
		DetachedCriteria criteria = DetachedCriteria.forClass(com.unieap.pojo.User.class)
				.add(userCode.eq(u.getUsername()));
		com.unieap.pojo.User user = (com.unieap.pojo.User) DBManager.getHT(null).findByCriteria(criteria).get(0);
		UnieapConstants.user = user;
	}

	public void lougoutLog(VisitLog log) {
		log.setLogId(getSequence(null, "unieap"));
		log.setLogoutDate(UnieapConstants.getDateTime(null));
		log.setUserCode(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(log);
	}

	public void louginLog(VisitLog log) {
		log.setLogId(getSequence(null, "unieap"));
		log.setLoginDate(UnieapConstants.getDateTime(null));
		log.setUserCode(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(log);
	}

	public void initUserDicdata(ServletContext servlet) throws Exception {
		List<Object> datas = getDicdataByUserId(UnieapConstants.getUser().getUserId());
		if (datas != null) {
			JSONObject jsonObj = new JSONObject();
			JSONArray jagroup = null;
			JSONArray jaoptgroup = null;
			DicDataVO dicVo;
			for (Object vo : datas) {
				dicVo = (DicDataVO)vo;
				String groupCode =  dicVo.getGroupCode();
				String groupCodeOptional = "_" + dicVo.getGroupCode() + "Opt";
				if (jsonObj.isNull(groupCodeOptional)) {
					jaoptgroup = new JSONArray();
					JSONObject dic = new JSONObject();
					dic.put("dicCode", "");
					dic.put("dicName", "...");
					dic.put("parentCode", "");
					jaoptgroup.put(dic);
					jsonObj.put(groupCodeOptional, jaoptgroup);
				} else {
					jaoptgroup = (JSONArray) jsonObj.get(groupCodeOptional);
				}
				JSONObject dic = new JSONObject();
				dic.put("dicCode", dicVo.getDicCode());
				dic.put("dicName", dicVo.getDicName());
				dic.put("parentCode", dicVo.getGroupCode());
				jaoptgroup.put(dic);
				String gid = "_" + groupCode;
				if (jsonObj.isNull(gid)) {
					jagroup = new JSONArray();
					jsonObj.put(gid, jagroup);
				} else {
					jagroup = (JSONArray) jsonObj.get(gid);
				}
				jagroup.put(dic);
			}
			String code = "var UnieapDicdata = eval(" + jsonObj.toString() + ")";
			createJs(servlet, UnieapConstants.getUser().getUserCode(), "dicdata_constants", code);
		} else {
			String code = "var UnieapDicdata = eval({})";
			createJs(servlet, UnieapConstants.getUser().getUserCode(), "dicdata_constants", code);
		}
	}

	public List<Object> getDicdataByUserId(Integer userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT distinct dd.dic_id as dicId,dd.dic_code as dicCode,dd.dic_name as dicName,");
		sql.append(" dd.parent_id as groupId,dd.parent_code as groupCode,dd.parent_name as groupName,dd.dic_type as dicType,");
		sql.append(" dd.active_flag as activeFlag ,dd.seq as seq ");
		sql.append(" FROM unieap.role_resource rr,unieap.user u,unieap.user_role ur,unieap.dic_data_tree dd ");
		sql.append(" where u.user_id = ur.user_id and ur.role_id = rr.role_id ");
		sql.append(" and rr.resource_id = dd.dic_code and u.user_id =? and dd.language =? order by dd.parent_code, dd.seq ASC ");
		List<Object> dicdatas = DBManager.getJT(null).query(sql.toString(),
				new Object[] { userId,SYSConfig.defaultLanguage },
				new EntityRowMapper(DicDataVO.class));
		return dicdatas;
	}
	public void initUserButton(ServletContext servlet) throws Exception {
		List<ButtonVO> datas = getButtonsByUserId(UnieapConstants.getUser().getUserId());
		if (datas != null) {
			JSONObject jsonObj = new JSONObject();
			for (ButtonVO value : datas) {
				JSONObject json = JSONUtils.convertBean2JSON(value);
				jsonObj.put(value.getButtonCode(), json);
			}
			String code = "var UnieapButton=eval(" + jsonObj.toString() + ")";
			createJs(servlet, UnieapConstants.getUser().getUserCode(), "button_constants", code);
		} else {
			String code = "var UnieapButton = eval({})";
			createJs(servlet, UnieapConstants.getUser().getUserCode(), "button_constants", code);
		}
	}
	public List<ButtonVO> getButtonsByUserId(Integer userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT distinct dd.dic_id as buttonId,dd.dic_code as buttonCode,dd.dic_name as buttonName,true as abled ");
		sql.append(" FROM unieap.role_resource rr,unieap.user u,unieap.user_role ur,unieap.dic_data_tree dd ");
		sql.append(" where u.user_id = ur.user_id and ur.role_id = rr.role_id ");
		sql.append(" and rr.resource_id = dd.dic_code and dd.active_flag = 'Y' and dd.dic_type ='B' and u.user_id =? and dd.language =?");
		List datas = DBManager.getJT(null).query(sql.toString(), new Object[] { userId, SYSConfig.defaultLanguage},
				new EntityRowMapper(ButtonVO.class));
		return datas;
	}
	
	private void createJs(ServletContext servlet, String userId, String fileName, String jsStr) throws Exception {
		String filePath = servlet.getResource("/") + "";
		if (StringUtils.isNotEmpty(filePath) && filePath.indexOf("file:") != -1) {
			filePath = filePath.substring("file:/".length());
		} else {
			filePath = servlet.getRealPath("/");
		}
		if (StringUtils.isEmpty(filePath)) {
			throw new Exception("cannot get file[" + fileName + "]output path...");
		}
		String path = filePath + CODELISTJSPATH + userId + "_" + fileName + ".js";
		FileUtils.write(path, true, true, jsStr);
	}
}
