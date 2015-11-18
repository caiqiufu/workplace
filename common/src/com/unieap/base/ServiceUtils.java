package com.unieap.base;

import org.springframework.context.ApplicationContext;

/**
 * <p>
 * Description: [bean管理容器]
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: 深圳市天源迪科技术股份有限公司
 * </p>
 * 
 * @author <a href="mailto: caiqiufu@sohu.com">蔡秋伏</a>
 * @version $Revision$
 */
public abstract class ServiceUtils {

	/**
	 * 得到spring配置管理的业务对象
	 * 
	 * @param beanName
	 * @return
	 */
	public static Object getBean(String beanName) {
		ApplicationContext context = AppContextContainer.getAppContext();
		Object obj = (Object) context.getBean(beanName);
		return obj;
	}
}