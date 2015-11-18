package com.unieap.security;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.util.StringUtils;

import com.unieap.base.SYSConfig;

/**
 * @author caibo 自定义过滤器
 */
public class MyFilterSecurityInterceptor extends AbstractSecurityInterceptor implements Filter {

	private final Log logger = LogFactory.getLog(MyFilterSecurityInterceptor.class);

	private FilterInvocationSecurityMetadataSource securityMetadataSource;

	public void doFilter(ServletRequest arg0, ServletResponse arg1, FilterChain arg2)
			throws IOException, ServletException {
		FilterInvocation fi = new FilterInvocation(arg0, arg1, arg2);
		if (!checklicense()) {
			if (SecurityContextHolder.getContext().getAuthentication() == null
					|| "anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getPrincipal())) {
				fi.getHttpResponse().sendRedirect(fi.getHttpRequest().getContextPath() + "/logout.jsp");
			} else {
				invoke(fi);
			}
		} else {
			throw new IOException("licence expired");
		} /*
			 * else if (SecurityContextHolder.getContext().getAuthentication()
			 * == null ||
			 * "anonymousUser".equals(SecurityContextHolder.getContext().
			 * getAuthentication().getPrincipal())) {
			 * fi.getHttpResponse().sendRedirect(fi.getHttpRequest().
			 * getContextPath() + "/logout.jsp"); } else { invoke(fi); }
			 */
		// invoke(fi);
	}

	public boolean checklicense() {
		boolean isExpired = true;
		String isExpiredFlag = SYSConfig.getConfig().get("licence.expired");
		if (StringUtils.isEmpty(isExpiredFlag)) {
			String expiretime = SYSConfig.getConfig().get("licence.expiretime");
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			long expiredDate = 100;
			try {
				expiredDate = sdf.parse(expiretime).getTime();
			} catch (ParseException e) {
				expiredDate = new Date("1900-08-08 18:18:18").getTime();
			}
			long currentTime = System.currentTimeMillis();
			if (expiredDate < currentTime) {
				isExpiredFlag = "Y";
			} else {
				isExpiredFlag = "N";
			}
			SYSConfig.getConfig().put("licence.expired", isExpiredFlag);
		}
		if ("Y".equals(isExpiredFlag)) {
			isExpired = true;
		} else {
			isExpired = false;
		}
		return isExpired;
	}

	/**
	 * @return
	 */
	public FilterInvocationSecurityMetadataSource getSecurityMetadataSource() {
		return securityMetadataSource;
	}

	/**
	 * @param securityMetadataSource
	 */
	public void setSecurityMetadataSource(FilterInvocationSecurityMetadataSource securityMetadataSource) {
		this.securityMetadataSource = securityMetadataSource;
	}

	public Class<FilterInvocation> getSecureObjectClass() {
		return FilterInvocation.class;
	}

	/**
	 * @param filterInvocation
	 * @throws IOException
	 * @throws ServletException
	 */
	public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
		InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
		try {
			filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
		} catch (IOException e) {
			logger.error(e.getLocalizedMessage());
			throw e;
		} finally {
			super.afterInvocation(token, null);
		}
	}

	public SecurityMetadataSource obtainSecurityMetadataSource() {
		return this.securityMetadataSource;
	}

	public void destroy() {

	}

	public void init(FilterConfig arg0) throws ServletException {

	}
}
