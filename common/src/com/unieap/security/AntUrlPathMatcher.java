package com.unieap.security;

import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

/**
 * @author caibo
 * 
 */
public class AntUrlPathMatcher implements UrlMatcher {
	private boolean requireLowerCaseUrl;
	private PathMatcher pathMatcher;

	/**
	 * 构造函数
	 */
	public AntUrlPathMatcher() {
		this(true);
	}

	/**
	 * @param requireLowerCaseUrl
	 */
	public AntUrlPathMatcher(boolean requireLowerCaseUrl) {
		this.requireLowerCaseUrl = requireLowerCaseUrl;
		this.pathMatcher = new AntPathMatcher();
	}

	public Object compile(String path) {
		if (this.requireLowerCaseUrl) {
			return path.toLowerCase();
		}
		return path;
	}

	public String getUniversalMatchPattern() {
		return "/**";
	}

	public boolean pathMatchesUrl(Object path, String url) {
		String npath = path.toString();
		if ("/**".equals(npath) || "**".equals(url)) {
			return true;
		}
		return this.pathMatcher.match(npath, url);
	}

	public boolean requireLowerCaseUrl() {
		return this.requireLowerCaseUrl;
	}
}
