package com.unieap.base.vo;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;
import java.util.Map;

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
public class BaseVO {
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
	public Map<String,String> extAttris = new HashMap<String,String>();
	public Map<String, String> getExtAttris() {
		return extAttris;
	}
	public void setExtAttris(Map<String, String> extAttris) {
		this.extAttris = extAttris;
	}
	
	private String effectiveTime;
	private String expiryTime;
	private String remark;
	public String getEffectiveTime() {
		return effectiveTime;
	}
	public String getExpiryTime() {
		return expiryTime;
	}
	public String getRemark() {
		return remark;
	}
	public void setEffectiveTime(String effectiveTime) {
		this.effectiveTime = effectiveTime;
	}
	public void setExpiryTime(String expiryTime) {
		this.expiryTime = expiryTime;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
