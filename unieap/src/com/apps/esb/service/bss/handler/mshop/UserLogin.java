package com.apps.esb.service.bss.handler.mshop;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.xml.soap.SOAPMessage;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.json.JSONObject;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.encoding.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.mshop.user.MshopUserVO;
import com.apps.esb.service.bss.vo.mshop.user.PermissionVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.ButtonVO;
import com.unieap.base.vo.MenuVO;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.login.bo.LoginBO;
import com.unieap.mdm.vo.DicDataVO;
import com.unieap.tools.JSONUtils;

@Service("userLogin")
public class UserLogin implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("userCode")) {
			throw new Exception("userCode is null");
		}
		if (!json.has("password")) {
			throw new Exception("password is null");
		}
		ProcessResult processResult = new ProcessResult();
		boolean flag = verifyUser(json.getString("userCode"), json.getString("password"),(HttpServletRequest)extParameters.get("HttpServletRequest"));
		if (flag) {
			MshopUserVO user = new MshopUserVO();
			user.setUserId(UnieapConstants.getUser().getUserId().toString());
			user.setUserCode(UnieapConstants.getUser().getUserCode());
			user.setUserName(UnieapConstants.getUser().getUserName());
			Map<String, PermissionVO> menuList = getMenusByUserId(UnieapConstants.getUser().getUserId());
			user.setMenuList(menuList);
			Map<String, PermissionVO> buttonList = getButtonByUserId(UnieapConstants.getUser().getUserId());
			user.setButtonList(buttonList);
			Map<String, PermissionVO> dicList = getDicByUserId(UnieapConstants.getUser().getUserId());
			user.setDicList(dicList);
			processResult.setResultCode(UnieapConstants.C0);
			processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
			JSONObject jsonResult = JSONUtils.convertBean2JSON(user);
			processResult.setExtParameters(jsonResult.toString());
		} else {
			processResult.setResultCode("20005");
			processResult.setResultDesc(UnieapConstants.getMessage("20005"));
		}
		return processResult;
	}

	@Override
	public RequestInfo getRequestInfoFromSOAPMessage(SOAPMessage request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponsetInfo getResponseInfoFromSOAPMessage(SOAPMessage response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SOAPMessage getRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	private boolean verifyUser(String userCode, String password, HttpServletRequest request) {
		Property userCodeProperty = Property.forName("userCode");
		DetachedCriteria criteria = DetachedCriteria.forClass(com.unieap.pojo.User.class)
				.add(userCodeProperty.eq(userCode));
		List<com.unieap.pojo.User> datas = DBManager.getHT(null).findByCriteria(criteria);
		if (datas != null && datas.size() > 0) {
			com.unieap.pojo.User user = (com.unieap.pojo.User) datas.get(0);
			PasswordEncoder passwordEncoder = (PasswordEncoder) ServiceUtils.getBean("passwordEncoder");
			String encodedPassword = passwordEncoder.encodePassword(password, null);
			if (StringUtils.equals(encodedPassword, user.getPassword())) {
				addUserToAuthenticate(user,"ROLE_MSHOP",request);
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}
	}

	public void addUserToAuthenticate(com.unieap.pojo.User user,String roleName, HttpServletRequest request) {
		UnieapConstants.userList.put(user.getUserCode(), user);
		Collection<GrantedAuthority> rules = new ArrayList<GrantedAuthority>();
		GrantedAuthority auth = new GrantedAuthorityImpl(roleName);
		rules.add(auth);
		org.springframework.security.core.userdetails.User uu = new org.springframework.security.core.userdetails.User(
				user.getUserCode(), user.getPassword(), true, true, true, true, rules);
		Authentication authentication = new UsernamePasswordAuthenticationToken(uu, null, rules);
		SecurityContextHolder.getContext().setAuthentication(authentication);
		HttpSession session = request.getSession(true);  
		//在session中存放security context,方便同一个session中控制用户的其他操作  
		session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext()); 
	}

	public Map<String, PermissionVO> getMenusByUserId(Integer userId) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT dd.dic_id as id, dd.dic_name as text,dd.dic_code as menuCode,dd.dic_name as menuName ,dd.href as href ");
		sql.append("FROM unieap.role_resource rr,unieap.user u,unieap.user_role ur,unieap.dic_data_tree dd ");
		sql.append("where u.user_id = ur.user_id and ur.role_id = rr.role_id and dd.dic_type = 'D' ");
		sql.append(
				"and rr.resource_id = dd.dic_code and dd.parent_code = 'mmenu' and u.user_id = ? and dd.active_flag =? order by dd.seq ASC");
		List<Object> menus = DBManager.getJT(null).query(sql.toString(), new Object[] { userId, UnieapConstants.YES },
				new EntityRowMapper(MenuVO.class));
		if (menus != null && menus.size() > 0) {
			Map<String, PermissionVO> menuList = new HashMap<String, PermissionVO>();
			for (int i = 0; i < menus.size(); i++) {
				MenuVO menuVO = (MenuVO) menus.get(i);
				PermissionVO permissionVO = new PermissionVO();
				menuList.put(menuVO.getMenuCode(), permissionVO);
				permissionVO.setType("M");
				permissionVO.setCode(menuVO.getMenuCode());
				permissionVO.setName(menuVO.getMenuName());
			}
			return menuList;
		} else {
			return null;
		}
	}

	public Map<String, PermissionVO> getButtonByUserId(Integer userId) throws Exception {
		LoginBO loginBO = (LoginBO) ServiceUtils.getBean("loginBO");
		List<Object> datas = loginBO.getButtonsByUserId(UnieapConstants.getUser().getUserId());

		if (datas != null && datas.size() > 0) {
			Map<String, PermissionVO> buttonList = new HashMap<String, PermissionVO>();
			for (int i = 0; i < datas.size(); i++) {
				ButtonVO buttonVO = (ButtonVO) datas.get(i);
				PermissionVO permissionVO = new PermissionVO();
				buttonList.put(buttonVO.getButtonCode(), permissionVO);
				permissionVO.setType("B");
				permissionVO.setCode(buttonVO.getButtonCode());
				permissionVO.setName(buttonVO.getButtonName());
				permissionVO.setAbled(buttonVO.isAbled());
			}
			return buttonList;
		} else {
			return null;
		}
	}

	public Map<String, PermissionVO> getDicByUserId(Integer userId) throws Exception {
		LoginBO loginBO = (LoginBO) ServiceUtils.getBean("loginBO");
		List<Object> datas = loginBO.getDicdataByUserId(UnieapConstants.getUser().getUserId());

		if (datas != null && datas.size() > 0) {
			Map<String, PermissionVO> dicList = new HashMap<String, PermissionVO>();
			for (int i = 0; i < datas.size(); i++) {
				DicDataVO dicDataVO = (DicDataVO) datas.get(i);
				PermissionVO permissionVO = new PermissionVO();
				dicList.put(dicDataVO.getDicCode(), permissionVO);
				permissionVO.setType("B");
				permissionVO.setCode(dicDataVO.getDicCode());
				permissionVO.setName(dicDataVO.getGroupName());
				permissionVO.setAbled(UnieapConstants.YES.equals(dicDataVO.getActiveFlag()));
			}
			return dicList;
		} else {
			return null;
		}
	}
}