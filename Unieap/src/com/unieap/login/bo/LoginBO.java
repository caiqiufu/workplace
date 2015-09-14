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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

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

	public Map getUserMenu(Integer userId) throws Exception {
		String sql = "SELECT dd.dic_code as menuCode,dd.dic_name as menuName ,dd.href as href " +
				"FROM unieap.role_resource rr,unieap.user u,unieap.user_role ur,unieap.dic_data dd " +
				"where u.user_id = ur.user_id and ur.role_id = rr.role_id and rr.category = 1012 and rr.category = dd.group_id and rr.resource_id = dd.dic_code and u.user_id = ? and dd.active_flag=? order by dd.seq ASC";
		List menus =DBManager.getJT(null).query(sql,new Object[]{userId,UnieapConstants.YES},new EntityRowMapper(MenuVO.class));
		Map attributes = new HashMap();
		attributes.put("menus", menus);
		return attributes;
	}

	public void loadUser() throws Exception {
		org.springframework.security.core.userdetails.User u = (org.springframework.security.core.userdetails.User) SecurityContextHolder
				.getContext().getAuthentication().getPrincipal();
		Property userCode = Property.forName("userCode");
		DetachedCriteria criteria = DetachedCriteria.forClass(
				com.unieap.pojo.User.class).add(userCode.eq(u.getUsername()));
		com.unieap.pojo.User user = (com.unieap.pojo.User) DBManager
				.getHT(null).findByCriteria(criteria).get(0);
		UnieapConstants.user = user;
		/*
		 * CacheMgt.putUser(user.getUsername(), vo);
		 * if(CacheMgt.getUser(user.getUsername())==null){
		 * if(!UnieapConstants.ISUNIEAP){ Property userCode =
		 * Property.forName("userCode"); DetachedCriteria
		 * criteria=DetachedCriteria
		 * .forClass(com.unieap.pojo.User.class).add(userCode
		 * .eq(user.getUsername())); com.unieap.pojo.User u =
		 * (com.unieap.pojo.User)
		 * DBManager.getHT(null).findByCriteria(criteria).get(0); UserVO vo =
		 * new UserVO(); vo.setUserId(u.getUserId());
		 * vo.setUserCode(u.getUserCode()); vo.setUserName(u.getUserName());
		 * vo.setActiveFlag(u.getActiveFlag()); vo.setBeId(u.getBeId());
		 * vo.setOrgIds(getUserOrgIds(getUserOrgs(vo.getUserId())));
		 * vo.setRoles(getUserRoles(vo.getUserId(),vo.getOrgIds()));
		 * CacheMgt.putUser(vo.getUserCode(), vo); }else{ UserVO vo = new
		 * UserVO(); vo.setUserId(Long.valueOf(1));
		 * vo.setUserCode(user.getUsername());
		 * vo.setUserName(user.getUsername());
		 * vo.setActiveFlag(UnieapConstants.Y); vo.setBeId(Long.valueOf(1));
		 * CacheMgt.putUser(user.getUsername(), vo); } }
		 */
	}

	public void lougoutLog(VisitLog log) {
		log.setLogId(getSequence(null,"unieap"));
		log.setLogoutDate(UnieapConstants.getDateTime(null));
		log.setUserCode(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(log);
	}
	public void louginLog(VisitLog log) {
		log.setLogId(getSequence(null,"unieap"));
		log.setLoginDate(UnieapConstants.getDateTime(null));
		log.setUserCode(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(log);
	}
	public MenuVO getMenu(String menuId) throws Exception {
		/*
		 * MenuVO vo = CacheMgt.getMenus(menuId); if(vo==null){ throw new
		 * Exception(ResourceBundleHelper.getMessage("LoginBo.getMenu",new
		 * Object[]{menuId})); } return vo;
		 */
		return null;
	}

	public void initUserDicdata(ServletContext servlet) throws Exception {
		List<DicDataVO> datas = getDicdataByUserId(UnieapConstants.getUser()
				.getUserId());
		if (datas != null) {
			JSONObject jsonObj = new JSONObject();
			JSONArray jagroup = null;
			JSONArray jaoptgroup = null;
			for (DicDataVO vo : datas) {
				String groupId = vo.getGroupId().toString();
				String groupIdOptional = "_" + vo.getGroupId() + "Opt";
				if (jsonObj.isNull(groupIdOptional)) {
					jaoptgroup = new JSONArray();
					JSONObject dic = new JSONObject();
					dic.put("dicId", "");
					dic.put("dicCode", "");
					dic.put("dicName", "");
					dic.put("parentCode", "");
					jaoptgroup.put(dic);
					jsonObj.put(groupIdOptional, jaoptgroup);
				} else {
					jaoptgroup = (JSONArray) jsonObj.get(groupIdOptional);
				}
				JSONObject dic = new JSONObject();
				dic.put("dicId",vo.getDicId());
				dic.put("dicCode", vo.getDicCode());
				dic.put("dicName", vo.getDicName());
				dic.put("parentCode", vo.getParentCode());
				jaoptgroup.put(dic);
				String gid = "_" + groupId;
				if (jsonObj.isNull(gid)) {
					jagroup = new JSONArray();
					jsonObj.put(gid, jagroup);
				} else {
					jagroup = (JSONArray) jsonObj.get(gid);
				}
				jagroup.put(dic);
			}
			String code = "var UnieapDicdata = eval(" + jsonObj.toString()
					+ ")";
			createJs(servlet, UnieapConstants.getUser().getUserCode(),
					"dicdata_constants", code);
		} else {
			String code = "var UnieapDicdata = eval({})";
			createJs(servlet, UnieapConstants.getUser().getUserCode(),
					"dicdata_constants", code);
		}
	}

	@SuppressWarnings("unchecked")
	public List<DicDataVO> getDicdataByUserId(Integer userId) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("select d.dic_id as dicId,d.dic_code as dicCode,d.parent_code as parentCode, d.dic_name as dicName,g.group_id as groupId,g.group_name as groupName,d.seq as seq ");
		sb.append("from unieap.dic_data d,unieap.dic_group g, ");
		sb.append("(select distinct rr.resource_id,rr.category from unieap.user_role ur,unieap.role_resource rr ");
		sb.append("where ur.role_id=rr.role_id and rr.resource_type='0' and ur.user_id = ?) res ");
		sb.append("where d.group_id =g.group_id and res.category = g.group_id and res.resource_id = d.dic_code and d.active_flag=? and d.language =? order by g.group_name , d.seq");
		List dicdatas = DBManager.getJT(null).query(sb.toString(),
				new Object[] { userId,UnieapConstants.YES,SYSConfig.defaultLanguage}, new EntityRowMapper(DicDataVO.class));
		return dicdatas;
	}

	public void initUserButton(ServletContext servlet)
			throws Exception {
		List<ButtonVO> datas = getButtonsByUserId(UnieapConstants.getUser()
				.getUserId());
		if (datas != null) {
			JSONObject jsonObj = new JSONObject();
			for (ButtonVO value : datas) {
				JSONObject json = JSONUtils.convertBean2JSON(value);
				jsonObj.put(value.getButtonCode(), json);
			}
			String code = "var UnieapButton=eval(" + jsonObj.toString() + ")";
			createJs(servlet,UnieapConstants.getUser().getUserCode(), "button_constants", code);
		} else {
			String code = "var UnieapButton = eval({})";
			createJs(servlet,UnieapConstants.getUser().getUserCode(), "button_constants", code);
		}
	}

	@SuppressWarnings("unchecked")
	public List<ButtonVO> getButtonsByUserId(Integer userId)
			throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select bdic.dic_id as buttonId, bdic.dic_code as buttonCode,bdic.dic_name as buttonName,IF(dic.buttonId is null,false,true) as abled ,bdic.remark as href from ");
		sql.append(" (SELECT dd.dic_id as buttonId, dd.dic_code as buttonCode,dd.dic_name as buttonName,true as abled ,dd.remark as href,dd.seq ");
		sql.append(" FROM unieap.role_resource rr,unieap.user u,unieap.user_role ur,unieap.dic_data dd");
		sql.append(" where u.user_id = ur.user_id and ur.role_id = rr.role_id and rr.category = 1013");
		sql.append(" and rr.category = dd.group_id and dd.dic_code = rr.resource_id and u.user_id = ?) as dic");
		sql.append(" RIGHT OUTER JOIN unieap.dic_data bdic on dic.buttonId = bdic.dic_id where bdic.group_id=1013");
		sql.append(" order by dic.seq ASC");
		List datas = DBManager.getJT(null).query(sql.toString(),new Object[]{userId},
				new EntityRowMapper(ButtonVO.class));
		return datas;
	}

	private void createJs(ServletContext servlet, String userId,
			String fileName, String jsStr) throws Exception {
		if (SYSConfig.isDebug) {
			log.info("user " + fileName + " data is:" + jsStr);
		}
		String filePath = servlet.getResource("/") + "";
		if (StringUtils.isNotEmpty(filePath) && filePath.indexOf("file:") != -1) {
			filePath = filePath.substring("file:/".length());
		} else {
			filePath = servlet.getRealPath("/");
		}
		if (StringUtils.isEmpty(filePath)) {
			throw new Exception("cannot get file[" + fileName
					+ "]output path...");
		}
		String path = filePath + CODELISTJSPATH + userId + "_" + fileName
				+ ".js";
		if (SYSConfig.isDebug) {
			log.info("JS path is:" + path);
		}
		FileUtils.write(path, true, true, jsStr);
		if (SYSConfig.isDebug) {
			log.info(fileName + ".js finished...");
		}
	}

	public String getMenuJsonByUserId(Long userId, String appId)
			throws Exception {
		List<MenuVO> roots = getRootMenus(userId, appId);
		return ExtJsTree(roots);
	}

	@SuppressWarnings("unchecked")
	public List<MenuVO> getRootMenus(Long userId, String appId)
			throws Exception {
		if (UnieapConstants.ISUNIEAP) {
			String sql = "select * from menu where menutype='M' and menu.parentid = -1";
			List rootsMenus = DBManager.getJT(null).query(sql,
					new EntityRowMapper(MenuVO.class));
			if (rootsMenus != null && rootsMenus.size() > 0) {
				for (Object menuo : rootsMenus) {
					MenuVO menu = (MenuVO) menuo;
					menu.setChildrenContainer(getChildMenus(menu));
				}
			}
			return rootsMenus;
		} else {
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct menu.* ");
			sql.append(" from rolealloc, resrole, menu ");
			sql.append(" where rolealloc.roleid= resrole.roleid ");
			sql.append(" and resrole.resid = menu.menuid and menu.menutype='M' and menu.parentid = -1");
			// sql.append(" and resrole.roleid in (").append(UnieapConstants.getUser().getRoleIds()).append(")");
			List rootsMenus = DBManager.getJT(null).query(sql.toString(),
					new EntityRowMapper(MenuVO.class));
			if (rootsMenus != null && rootsMenus.size() > 0) {
				for (Object menuo : rootsMenus) {
					MenuVO menu = (MenuVO) menuo;
					menu.setChildrenContainer(getChildMenus(menu));
				}
			}
			return rootsMenus;
		}
	}

	@SuppressWarnings("unchecked")
	public List<MenuVO> getChildMenus(MenuVO menu) throws Exception {
		if (UnieapConstants.ISUNIEAP) {
			StringBuffer sql = new StringBuffer(
					"select * from menu where parentid = ?");
			List menus = DBManager.getJT(null).query(sql.toString(),
					new Object[] { menu.getMenuId() },
					new EntityRowMapper(MenuVO.class));
			return menus;
		} else {
			StringBuffer sql = new StringBuffer();
			sql.append("select distinct menu.* ");
			sql.append(" from rolealloc, resrole, menu ");
			sql.append(" where rolealloc.roleid= resrole.roleid ");
			sql.append(" and resrole.resid = menu.menuid and menu.menutype='M' and menu.parentid = ?");
			// sql.append(" and resrole.roleid in (").append(UnieapConstants.getUser().getRoleIds()).append(")");
			List menus = DBManager.getJT(null).query(sql.toString(),
					new Object[] { menu.getMenuId() },
					new EntityRowMapper(MenuVO.class));
			return menus;
		}
	}

	/*
	 * private void PojoToVo(MenuVO vo,Menu menu){
	 * vo.setActiveFlag(menu.getActiveFlag()); vo.setHref(menu.getHref());
	 * vo.setMenuCode(menu.getMenuCode()); vo.setMenuId(menu.getMenuId());
	 * vo.setMenuName(menu.getMenuName()); vo.setTitle(menu.getTitle()); }
	 */
	/*
	 * public TSmMenu getMenuByMenuId(String menuId){ List<TSmMenu> datas =
	 * DBManager.getHT(null).find("from TSmMenu where activeFlag = ?", "Y");
	 * if(datas!=null&&datas.size()>0){ return datas.get(0); }else{ return null;
	 * } }
	 */
	@SuppressWarnings("unchecked")
	private String ExtJsTree(List<MenuVO> roots) throws Exception {
		JSONArray ja = new JSONArray();
		if (roots != null && roots.size() > 0) {
			JSONObject data = null;
			for (MenuVO root : roots) {
				List<MenuVO> child = root.getChildren();
				// List<MenuVO> child = getChildMenus(root);
				if (child != null && child.size() > 0) {
					JSONArray jac = new JSONArray();
					JSONObject childData = null;
					for (MenuVO c : child) {
						childData = new JSONObject();
						childData.put("id", c.getMenuId());
						childData.put("menuCode", c.getMenuCode());
						childData.put("parentId", c.getParentId());
						childData.put("title", c.getTitle());
						childData.put("text", c.getMenuName());
						childData.put("url", c.getHref());
						childData.put("leaf", "true");
						childData.put("imgSrc", c.getImgSrc());
						jac.put(childData);
					}
					data = new JSONObject();
					data.put("id", root.getMenuId());
					data.put("text", root.getMenuName());
					data.put("menuCode", root.getMenuCode());
					data.put("title", root.getTitle());
					data.put("imgSrc", root.getImgSrc());
					data.put("leaf", "false");
					data.put("children", jac);
					ja.put(data);
				}
			}
		}
		String menu = ja.toString();
		if (SYSConfig.isDebug) {
			log.info("User menu:" + menu);
		}
		return menu;
	}


	// Spring团队的建议是你在具体的类（或类的方法）上使用 @Transactional 注解 readOnly=false 配置为读写事务
	@Transactional(readOnly = false, propagation = Propagation.REQUIRED)
	public void testTra() throws Exception {
		System.out.println("testTra");

	}
}
