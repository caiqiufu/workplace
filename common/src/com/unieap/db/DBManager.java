package com.unieap.db;

import org.apache.commons.lang.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.hibernate3.HibernateTemplate;

import com.unieap.base.ServiceUtils;

/**
 * <p>
 * Description: [数据源连接管理]
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: 
 * </p>
 * 
 * @author <a href="mailto: leixuebin@neusoft.com">蔡秋伏</a>
 * @version $Revision$
 */
public final class DBManager {

	/**
	 * <p>
	 * 描述:获取JdbcTemplate
	 * </P>
	 * Dec 2, 2010
	 * 
	 * @param dsName
	 * @return
	 * @throws Exception
	 */
	public static JdbcTemplate getJT(String dsName) {
		if (StringUtils.isEmpty(dsName)) {
			dsName = "jdbcTemplate";
		}
		JdbcTemplate jdbcTemplate = (JdbcTemplate) ServiceUtils.getBean(dsName);
		return jdbcTemplate;
	}

	/**
	 * <p>
	 * 描述:获取HibernateTemplate
	 * </P>
	 * Dec 2, 2010
	 * 
	 * @param dsName
	 * @return
	 * @throws Exception
	 */
	public static HibernateTemplate getHT(String dsName) {
		if (StringUtils.isEmpty(dsName)) {
			dsName = "hibernateTemplate";
		}
		HibernateTemplate hibernateTemplate = (HibernateTemplate) ServiceUtils.getBean(dsName);
		return hibernateTemplate;
	}
}
