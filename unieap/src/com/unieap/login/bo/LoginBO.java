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
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.ButtonVO;
import com.unieap.base.vo.MenuVO;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.file.bo.FileBO;
import com.unieap.mdm.vo.DicDataVO;
import com.unieap.pojo.VisitLog;
import com.unieap.tools.JSONUtils;

/**
 * Jan 5, 2011
 */
@Service("loginBO")
public class LoginBO extends BaseBO {

	public Map<String, List<Object>> getUserMenu(Integer userId) throws Exception {
		List<Object> menus;
		StringBuffer sql = new StringBuffer();
		if(StringUtils.equals(UnieapConstants.UNIEAP, UnieapConstants.getUser().getUserCode())){
			sql.append("SELECT dd.dic_id as id, dd.dic_name as text,1 as leaf, dd.dic_name as qtip,dd.icon as iconCls,dd.icon as imgSrc, ");
			sql.append("dd.dic_code as menuCode,dd.dic_name as menuName ,dd.href as href ");
			sql.append(" FROM unieap.dic_data_tree dd where  dd.dic_type = 'M' and  dd.active_flag =? order by dd.seq ASC");
			menus = DBManager.getJT(null).query(sql.toString(), new Object[] { UnieapConstants.YES },
					new EntityRowMapper(MenuVO.class));
		}else{
			sql.append("SELECT dd.dic_id as id, dd.dic_name as text,dd.dic_code as menuCode,dd.dic_name as menuName ,dd.href as href ");
			sql.append("FROM unieap.role_resource rr,unieap.user u,unieap.user_role ur,unieap.dic_data_tree dd ");
			sql.append("where u.user_id = ur.user_id and ur.role_id = rr.role_id and dd.dic_type = 'M' ");
			sql.append("and rr.resource_id = dd.dic_code and u.user_id = ? and dd.active_flag =? order by dd.seq ASC");
			menus = DBManager.getJT(null).query(sql.toString(), new Object[] { userId, UnieapConstants.YES },
					new EntityRowMapper(MenuVO.class));
		}
		Map<String, List<Object>> attributes = new HashMap<String, List<Object>>();
		attributes.put("menus", menus);
		return attributes;
	}

	public void loadLoginUser() throws Exception {
		org.springframework.security.core.userdetails.User u;
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth.getPrincipal() instanceof org.springframework.security.core.userdetails.User) {
            u = (org.springframework.security.core.userdetails.User) auth.getPrincipal();
        } else if (auth.getDetails() instanceof org.springframework.security.core.userdetails.User) {
            u = (org.springframework.security.core.userdetails.User) auth.getDetails();
        } else {
            throw new AccessDeniedException("User not properly authenticated.");
        }
		Property userCode = Property.forName("userCode");
		DetachedCriteria criteria = DetachedCriteria.forClass(com.unieap.pojo.User.class)
				.add(userCode.eq(u.getUsername()));
		com.unieap.pojo.User user = (com.unieap.pojo.User) DBManager.getHT(null).findByCriteria(criteria).get(0);
		UnieapConstants.userList.put(user.getUserCode(), user);
	}

	public void lougoutLog(VisitLog log) {
		log.setLogId(getSequence(null, "unieap"));
		log.setLogoutDate(UnieapConstants.getDateTime(null));
		if(UnieapConstants.getUser()!=null){
			log.setUserCode(UnieapConstants.getUser().getUserCode());
		}else{
			log.setUserCode("system error");
		}
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
			String fileName = UnieapConstants.getUser().getUserCode() + "_dicdata_constants.js";
			createJs(servlet,fileName, code);
		} else {
			String code = "var UnieapDicdata = eval({})";
			String fileName = UnieapConstants.getUser().getUserCode() + "_dicdata_constants.js";
			createJs(servlet,fileName, code);
		}
	}

	public List<Object> getDicdataByUserId(Integer userId) throws Exception {
		List<Object> dicdatas;
		StringBuffer sql = new StringBuffer();
		if(StringUtils.equals(UnieapConstants.UNIEAP, UnieapConstants.getUser().getUserCode())){
			sql.append("SELECT distinct dd.dic_id as dicId,dd.dic_code as dicCode,dd.dic_name as dicName,");
			sql.append(" dd.parent_id as groupId,dd.parent_code as groupCode,dd.parent_name as groupName,dd.dic_type as dicType,");
			sql.append(" dd.active_flag as activeFlag ,dd.seq as seq ");
			sql.append(" FROM unieap.dic_data_tree dd ");
			sql.append(" where  dd.language =? and dd.dic_type = 'D' order by dd.parent_code, dd.seq ASC ");
			dicdatas = DBManager.getJT(null).query(sql.toString(),
					new Object[] { SYSConfig.defaultLanguage },
					new EntityRowMapper(DicDataVO.class));
		}else{
			sql.append("SELECT distinct dd.dic_id as dicId,dd.dic_code as dicCode,dd.dic_name as dicName,");
			sql.append(" dd.parent_id as groupId,dd.parent_code as groupCode,dd.parent_name as groupName,dd.dic_type as dicType,");
			sql.append(" dd.active_flag as activeFlag ,dd.seq as seq ");
			sql.append(" FROM unieap.role_resource rr,unieap.user u,unieap.user_role ur,unieap.dic_data_tree dd ");
			sql.append(" where u.user_id = ur.user_id and ur.role_id = rr.role_id ");
			sql.append(" and rr.resource_id = dd.dic_code and dd.dic_type = 'D' and u.user_id =? and dd.language =? order by dd.parent_code, dd.seq ASC ");
			dicdatas = DBManager.getJT(null).query(sql.toString(),
					new Object[] { userId,SYSConfig.defaultLanguage },
					new EntityRowMapper(DicDataVO.class));
		}
		return dicdatas;
	}
	public void initUserButton(ServletContext servlet) throws Exception {
		List<Object> datas = getButtonsByUserId(UnieapConstants.getUser().getUserId());
		if (datas != null) {
			JSONObject jsonObj = new JSONObject();
			for (Object obj : datas) {
				ButtonVO value = (ButtonVO)obj;
				JSONObject json = JSONUtils.convertBean2JSON(value);
				jsonObj.put(value.getButtonCode(), json);
			}
			String code = "var UnieapButton=eval(" + jsonObj.toString() + ")";
			String fileName = UnieapConstants.getUser().getUserCode() + "_button_constants.js";
			createJs(servlet,fileName, code);
		} else {
			String code = "var UnieapButton = eval({})";
			String fileName = UnieapConstants.getUser().getUserCode() + "_button_constants.js";
			createJs(servlet,fileName, code);
		}
	}
	public List<Object> getButtonsByUserId(Integer userId) throws Exception {
		List<Object> datas;
		StringBuffer sql = new StringBuffer();
		if(StringUtils.equals(UnieapConstants.UNIEAP, UnieapConstants.getUser().getUserCode())){
			sql.append("SELECT distinct dd.dic_id as buttonId,dd.dic_code as buttonCode,dd.dic_name as buttonName,true as abled ");
			sql.append(" FROM unieap.dic_data_tree dd ");
			sql.append(" where  dd.active_flag = 'Y' and dd.dic_type ='B' and dd.language =?");
			datas = DBManager.getJT(null).query(sql.toString(), new Object[] { SYSConfig.defaultLanguage},
					new EntityRowMapper(ButtonVO.class));
		}else{
			sql.append("SELECT distinct dd.dic_id as buttonId,dd.dic_code as buttonCode,dd.dic_name as buttonName,true as abled ");
			sql.append(" FROM unieap.role_resource rr,unieap.user u,unieap.user_role ur,unieap.dic_data_tree dd ");
			sql.append(" where u.user_id = ur.user_id and ur.role_id = rr.role_id ");
			sql.append(" and rr.resource_id = dd.dic_code and dd.active_flag = 'Y' and dd.dic_type ='B' and u.user_id =? and dd.language =?");
			datas = DBManager.getJT(null).query(sql.toString(), new Object[] { userId, SYSConfig.defaultLanguage},
					new EntityRowMapper(ButtonVO.class));
		}
		return datas;
	}
	
	private void createJs(ServletContext servlet, String fileName, String jsStr) throws Exception {
		String shareFolderPath = SYSConfig.getConfig().get("shareFolderPath");
		String mdmCommonPath = SYSConfig.getConfig().get("mdmCommonPath");
		FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
		//String CODELISTJSPATH = "unieap/js/common";
		String uploadPath = shareFolderPath+mdmCommonPath;
		fileBO.write(fileName, uploadPath, true, true, jsStr);
		//FileUtils.write(path, true, true, jsStr);
	}
}
