package com.unieap.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.base.SYSConfig;
import com.unieap.db.DBManager;
import com.unieap.pojo.ErrorCode;

@Service("errorCodeLoadHandler")
public class ErrorCodeLoadHandler extends BaseBO implements ConfigHandler {

	@Override
	public void dealNode(Node node, ServletContext servlet, String appName) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deal(String parameters) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(ErrorCode.class);
		Property language = Property.forName("language");
		criteria.add(language.eq(SYSConfig.defaultLanguage));
		List<ErrorCode> errorCodeDatas = DBManager.getHT(null).findByCriteria(criteria);
		if (errorCodeDatas != null && errorCodeDatas.size() > 0) {
			Map<String, ErrorCode> errorCodeList = new HashMap<String, ErrorCode>();
			for (int i = 0; i < errorCodeDatas.size(); i++) {
				ErrorCode errorCode = (ErrorCode) errorCodeDatas.get(i);
				errorCodeList.put(errorCode.getBillingCode(), errorCode);
			}
			CacheMgt.setErrorCodeList(errorCodeList);
		}
	}

	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return true;
	}

}
