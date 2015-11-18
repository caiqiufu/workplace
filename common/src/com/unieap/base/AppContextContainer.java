package com.unieap.base;

import org.springframework.context.ApplicationContext;

public class AppContextContainer extends ThreadLocal<Object> {
	private static ApplicationContext applicationContext;

	/**
     * 
     */
	public AppContextContainer() {
	}

	/**
	 * @return ApplicationContext
	 */
	public static ApplicationContext getAppContext() {
		return applicationContext;
	}

	protected Object initialValue() {
		return null;
	}

	/**
	 * @param context
	 */
	public static void setAppContext(ApplicationContext context) {
		applicationContext = context;
	}

}
