package com.unieap.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.GrantedAuthorityImpl;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.unieap.db.DBManager;

/**
 * 用户信息获取
 * 
 * @author caibo
 * 
 */
public class MyUserDetailService implements UserDetailsService {

	public UserDetails loadUserByUsername(String userCode) throws UsernameNotFoundException {
		User user = null;
		user = getUser(userCode);
		//user = testUser(username);
		if(user == null){
			return null;
		}
		return user;
	}
	/**
	 * get user info by user name
	 * @param userCode
	 * @return
	 */
	private User getUser(String userCode){
		List<Map<String,Object>> datas = DBManager.getJT(null).queryForList("SELECT USER_ID,USER_CODE,PASSWORD,enable,expired,locked FROM USER WHERE USER_CODE=?",new Object[]{userCode});
		if(datas!=null&&datas.size()>0){
			Map<String,Object> data = (Map<String,Object>) datas.get(0);
			Collection<GrantedAuthority> roles = getRoles(data.get("USER_CODE").toString());
			String enable = data.get("enable").toString();
			String expired = data.get("expired").toString();
			String locked = data.get("locked").toString();
			User user = new User(userCode,data.get("PASSWORD").toString(),enable==null||"1".equals(enable), expired==null||"0".equals(expired), true,locked==null||"0".equals(locked), roles);
			return user;
		}else{
			return null;
		}
	}
	/**
	 * get roles by username
	 * @param username
	 * @return
	 */
	@SuppressWarnings("deprecation")
	private Collection<GrantedAuthority> getRoles(String userCode){
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		GrantedAuthority auth = new GrantedAuthorityImpl("unieap");
		auths.add(auth);
		//StringBuffer allocs = new StringBuffer("'").append(username).append("'");
		/*List<Map<String,Object>> orgs = getUserOrgs(username);
		if(orgs!=null&&orgs.size()>0){
			for(Map org:orgs){
				allocs.append(",'").append(org.get("orgid")).append("'");
			}
		}*/
		/*String sql = "select resrole.roleId,resrole.resid,rolealloc.allocid,rolealloc.alloctype from rolealloc, resrole where rolealloc.roleid= resrole.roleid and rolealloc.allocid in("+allocs+")";
		List roles = DBManager.getJT(null).query(sql,new EntityRowMapper(RoleVO.class));
		if(roles!=null&&roles.size()>0){
			RoleVO r = null;
			for(Object role: roles){
				r = (RoleVO)role;
				GrantedAuthority auth = new GrantedAuthorityImpl(r.getRoleId().toString());
				auths.add(auth);
			}
		}*/
		/*Map<String, Object> data = DBManager.getJT(null).queryForMap("SELECT ROLEID FROM USERROLE WHERE USERID='"+userId+"'");
		if(data!=null){
			GrantedAuthority auth = new GrantedAuthorityImpl(roles);
			auths.add(auth);
		}*/
		return auths;
	}
	@SuppressWarnings("deprecation")
	private User testUser(String userName){
		Collection<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
		GrantedAuthority auth = new GrantedAuthorityImpl("unieap");
		GrantedAuthority auth1 = new GrantedAuthorityImpl("test");
		auths.add(auth);
		auths.add(auth1);
		User user = new User(userName,"1",true, true, true,true, auths);
		return user;
	}
}
