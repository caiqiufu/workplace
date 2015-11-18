package com.unieap.base.vo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import org.apache.commons.lang.builder.ReflectionToStringBuilder;
import org.apache.commons.lang.builder.ToStringStyle;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.json.JSONObject;

import com.unieap.tools.JSONUtils;

/**
 * <p>
 * Description: [查询VO基类]
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
public class BaseVO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public final Log log = LogFactory.getLog(BaseVO.class);
	public String toString() {
		return (this == null ? "" : ReflectionToStringBuilder.toString(this,
				ToStringStyle.DEFAULT_STYLE));
	}
	//深度clone
	public Object clone() {
		try {
			ByteArrayOutputStream bout = new ByteArrayOutputStream();
			ObjectOutputStream out = new ObjectOutputStream(bout);
			out.writeObject(this);
			out.close();
			ByteArrayInputStream bin = new ByteArrayInputStream(bout
					.toByteArray());
			ObjectInputStream in = new ObjectInputStream(bin);
			Object ret = in.readObject();
			in.close();
			return ret;
		} catch (Exception e) {
			log.error("clone is exception,error message:"+e.getMessage());
		}
		return null;
	}
	public String toJsonString() throws Exception{
		JSONObject json = new JSONObject();
		try {
			 json = JSONUtils.convertBean2JSON(this);
		} catch (Exception e) {
			throw new Exception("data conver to json error,error:" + e.getMessage(), e);
		}
		return json.toString();
	}
}
