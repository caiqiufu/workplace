package com.apps.esb.service.bss.handler.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apps.easymobile.pojo.AppShop;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.tools.JSONUtils;

@Service("queryShops")
public class QueryShops extends SoapMessageHandler implements BizHandler {

	public QueryShops() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters,Map<String,Object> extParameters)
			throws Exception {
		String cityId = "";
		String shopName = "";
		if (!StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())) {
			JSONObject extParametersJson = new JSONObject(requestInfo.getRequestBody().getExtParameters());
			if (extParametersJson.has("cityId")) {
				cityId = extParametersJson.getString("cityId");
				if (StringUtils.isEmpty(cityId)) {
					throw new Exception("cityId is null");
				}
			}
			if (extParametersJson.has("shopName")) {
				shopName = extParametersJson.getString("shopName");
			}
		}
		List<AppShop> shopList = (List<AppShop>) CacheMgt.getCacheData().get("AppShopList");
		List<AppShop> selectedList = new ArrayList<AppShop>();
		if (shopList != null) {
			for (int i = 0; i < shopList.size(); i++) {
				AppShop appShop = shopList.get(i);
				if (cityId.equals(appShop.getCityId())) {
					if (StringUtils.isEmpty(shopName)) {
						selectedList.add(appShop);
					} else {
						if (appShop.getAddress().toLowerCase().contains(shopName.toLowerCase())||appShop.getShopName().toLowerCase().contains(shopName.toLowerCase())) {
							selectedList.add(appShop);
						}
					}
				}
			}
		}
		ProcessResult processResult = new ProcessResult();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("shopList",JSONUtils.collectionToJson(selectedList));
		processResult.setExtParameters(jsonResult.toString());
		return processResult;
	}

	@Override
	public RequestInfo getRequestInfoFromSOAPMessage(SOAPMessage request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponsetInfo getResponseInfoFromSOAPMessage(SOAPMessage response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SOAPMessage getRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

}
