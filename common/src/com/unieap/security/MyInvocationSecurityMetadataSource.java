package com.unieap.security;

import java.util.ArrayList;
import java.util.Collection;

import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;

/**
 * 在初始化的时候取得所有的资源和对应的角色定义
 * 
 * @author caibo
 * 
 */
public class MyInvocationSecurityMetadataSource implements FilterInvocationSecurityMetadataSource {
	
	
	//private UrlMatcher urlMatcher = new AntUrlPathMatcher();

	/**
	 * 返回说请求资源所需要的权限(即role)
	 */
	public Collection getAttributes(Object object) throws IllegalArgumentException {
		/*if(UnieapConstants.ISUNIEAP){
			Collection<String> roles= new ArrayList<String>();
			roles.add(UnieapConstants.UNIEAP);
			return roles;
		}else{
			String requestUrl = ((FilterInvocation) object).getRequestUrl();
			logger.debug("request URL[" + requestUrl + "]...");
			Collection<String> roles= (Collection<String>) CacheMgt.resourcesRole.get(requestUrl);
			return roles;
		}*/
		Collection<String> roles= new ArrayList<String>();
		roles.add("unieap");
		roles.add("ROLE_MCARE");
		roles.add("ROLE_MSHOP");
		return roles;
	}
	public boolean supports(Class<?> clazz) {
		return true;
	}
	@Override
	public Collection<ConfigAttribute> getAllConfigAttributes() {
		// TODO Auto-generated method stub
		return null;
	}
}
