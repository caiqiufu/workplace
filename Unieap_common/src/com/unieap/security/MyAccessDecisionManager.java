package com.unieap.security;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

import com.unieap.base.SYSConfig;

/**
 * @author caibo
 * 
 */
public class MyAccessDecisionManager implements AccessDecisionManager {

	/**
	 * Authentication 认证过的票据Authentication，确定了谁正在访问资源 被访问的资源object
	 * 访问资源要求的权限配置ConfigAttributeDefinition
	 */
	public void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {
		String expiretime = SYSConfig.getConfig().get("licence.expiretime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		long expiredDate = 100;
		try {
			expiredDate = sdf.parse(expiretime).getTime();
		} catch (ParseException e) {
			expiredDate = new Date("1900-08-08 18:18:18").getTime();
		}
		long currentTime = System.currentTimeMillis();  
		if(expiredDate<currentTime){
			throw new AccessDeniedException("licence expired");
		}
		if (configAttributes == null) {
			throw new AccessDeniedException("no access right");
		}
		Collection<?> cga = authentication.getAuthorities();
		if (cga != null) {
			Iterator<?> itr = cga.iterator();
			while (itr.hasNext()) {
				String rule = itr.next().toString();
				if (configAttributes.contains(rule)) {
					return;
				}
			}
		}else{
			throw new AccessDeniedException("no access right");
		}
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

}
