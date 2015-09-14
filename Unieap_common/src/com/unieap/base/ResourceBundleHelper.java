package com.unieap.base;

import java.text.MessageFormat;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author caibo
 * 
 */
public final class ResourceBundleHelper {
	/**
	 * LOCALE
	 */
	public static Locale LOCALE = null;
	/**
	 * rb
	 */
	public static ResourceBundle rb = null;
	/**
	 * @param locale
	 */
	public static void setLocal(Locale locale){
		LOCALE = locale ;
		rb = getResourceBundle("com.resource.MessageResource");
	}
	/**
	 * @param baseName
	 * @return ResourceBundle
	 */
	public static ResourceBundle getResourceBundle(String baseName) {
		if (LOCALE == null) {
			LOCALE = Locale.getDefault();
		}
		return ResourceBundle.getBundle(baseName, LOCALE);
	}
	/**
	 * @param key
	 * @return String
	 */
	public static String getMessage(String key) {
		if (rb == null) {
			setLocal(LOCALE);
		}
		return rb.getString(key);
	}
	/**
	 * @param key
	 * @param paras
	 * @return String
	 */
	public static String getMessage(String key, Object[] paras) {
		if (rb == null) {
			setLocal(LOCALE);
		}
		MessageFormat mf = new MessageFormat(rb.getString(key));
		String result = mf.format(paras);
		return result;
	}
}
