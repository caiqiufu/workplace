package com.unieap.security;

import java.util.Collection;
import java.util.Iterator;

import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.authentication.InsufficientAuthenticationException;
import org.springframework.security.core.Authentication;

/**
 * @author caibo
 * 
 */
public class MyAccessDecisionManager implements AccessDecisionManager {

	/**
	 * Authentication 认证过的票据Authentication，确定了谁正在访问资源 被访问的资源object
	 * 访问资源要求的权限配置ConfigAttributeDefinition
	 */
	public void decide(Authentication authentication, Object object,
			Collection<ConfigAttribute> configAttributes)
			throws AccessDeniedException, InsufficientAuthenticationException {

		if (configAttributes == null) {
			return;
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
		}
		throw new AccessDeniedException("no access right, error!");
	}

	public boolean supports(ConfigAttribute attribute) {
		return true;
	}

	public boolean supports(Class<?> clazz) {
		return true;
	}

}
