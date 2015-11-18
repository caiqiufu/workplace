package com.unieap.db.po;

import java.io.Serializable;
import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;

/**
 * 
 * <p>
 * Description: [BasePO类,其它PO继承此PO类]
 * </p>
 * <p>
 * Copyright: Copyright (c) 2009
 * </p>
 * <p>
 * Company: 东软集团股份有限公司
 * </p>
 * 
 * @author <a href="mailto: leixuebin@neusoft.com">雷学斌</a>
 * @version $Revision: 1.1 $
 */
public class BasePO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1920551635326598294L;

	/**
	 * 
	 */
	public BasePO() {

	}

	public String toString() {
		return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
	}

	public boolean equals(Object o) {
		return EqualsBuilder.reflectionEquals(this, o);
	}

	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}
}