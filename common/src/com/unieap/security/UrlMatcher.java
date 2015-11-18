package com.unieap.security;

/**
 * @author caibo
 * URL 验证
 */
public abstract interface UrlMatcher {
	/**
	 * @param para
	 * @return
	 */
	public abstract Object compile(String para);

	/**
	 * @param obj
	 * @param para
	 * @return
	 */
	public abstract boolean pathMatchesUrl(Object obj, String para);

	/**
	 * @return
	 */
	public abstract String getUniversalMatchPattern();

	/**
	 * @return
	 */
	public abstract boolean requireLowerCaseUrl();
}
