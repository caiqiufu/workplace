package com.bss;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class TestQueryInfo {
	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	public static void main(String[] args) throws Exception {
		//callQueryService(getResConfigJson());
		/*
		 * callQueryService(sendLoginVerifyCode());
		 * callQueryService(verifyCodeLogin());
		 */
		// callQueryService(voucherCardRecharge());
		// callQueryService(queryOfferingCategory());
		// callQueryService(changeSupplementaryOfferings());
		// callQueryService(changePrimaryOffering());
		// callQueryService(queryOfferings());
		// callQueryService(queryMyBills());
		// callQueryService(queryAllBalance());
		// callQueryService(queryDataBalance());
		// callQueryService(queryMyRechargeLogs());
		// callQueryService(servicePasswordLogin());
	    //callQueryService(servicePasswordLogin());
	    //callQueryService(xchangePromotion());
	    //callQueryService(transferBalance());
		//callQueryService(queryUpgardeInfo());
		//callQueryService(queryPushMessage());
		//callQueryService(queryShops());
		//callQueryService(queryTransferBalanceLogs());
		//callQueryService(transferBalance());
		//callQueryService(queryXchangeLogs());
		//callQueryService(xchangePromotion());
		//callQueryService(changeCustomerInfo());
		//callQueryService(queryDenomination());
		//callQueryService(querySpecialNum());
		
		callQueryService(queryCdr());
		
		
		

	}

	public static void performance() throws Exception {
		callQueryService(getResConfigJson());
		callQueryService(queryOfferingCategory());
		callQueryService(queryOfferings());
		callQueryService(queryMyBills());
		callQueryService(queryAllBalance());
		callQueryService(queryDataBalance());
		callQueryService(queryMyRechargeLogs());
		callQueryService(queryUpgardeInfo());
		callQueryService(queryPushMessage());
		callQueryService(queryShops());
	}

	public static void callQueryService(String json) throws Exception {
		String encoderJson = "parameters=" + URLEncoder.encode(json, HTTP.UTF_8);
		// String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);请求链接
		String url = "http://127.0.0.1:8080/unieap/extAction.do?method=queryInfo&" + encoderJson;
		//String url = "http://10.1.8.158:8080/unieap/extAction.do?method=queryInfo&" + encoderJson;
		//String url ="http://127.0.0.1:8080/UnieapDemo/extAction.do?method=queryInfo&" +encoderJson;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

		// StringEntity se = new StringEntity(encoderJson);
		// se.setContentType(CONTENT_TYPE_TEXT_JSON);
		// se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
		// APPLICATION_JSON));
		// httpPost.setEntity(se);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		try {
			HttpEntity entity = response.getEntity();
			System.out.println(json);
			System.out.println("----------------------------------------");
			//System.out.println(response.getStatusLine());
			if (entity != null) {
				// System.out.println("Response content length: " +
				// entity.getContentLength());
				System.out.println(EntityUtils.toString(entity));
				EntityUtils.consume(entity);
			}
		} finally {
			response.close();
		}
	}

	public static String getRequestHeaderJson(JSONObject jsonBody, String bizCode) throws Exception {
		JSONObject jsonRequest = new JSONObject();
		JSONObject jsonResult = new JSONObject();
		jsonRequest.put("requestHeader", jsonResult);
		jsonResult.put("accessUser", "unieap");
		jsonResult.put("accessPwd", "356a192b7913b04c54574d18c28d46e6395428ab");
		jsonResult.put("bizCode", bizCode);
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dt = sdf.format(date);
		jsonResult.put("requestTime", dt);
		JSONObject jsonExt = new JSONObject();
		jsonExt.put("IMEI", "356952073509324");
		jsonExt.put("OSType", "IOS");
		jsonExt.put("OSVersion", "9.0.1");
		jsonExt.put("APKVersion", "1.0");
		jsonExt.put("networkType", "3");
		jsonExt.put("resolution", "1080x1920");
		jsonExt.put("brand", "huawei");
		jsonExt.put("model", "P8");
		jsonExt.put("extParameters ", "");
		jsonResult.put("deviceInfo", jsonExt);
		// JSONObject jsonBody = new JSONObject();
		// jsonBody.put("serviceNumber", "15899785476");
		// jsonBody.put("extParameters", "");
		jsonRequest.put("requestBody", jsonBody);
		// System.out.println(jsonRequest.toString());
		return jsonRequest.toString();
	}

	public static String getResConfigJson() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "962138364");
		JSONObject password = new JSONObject();
		password.put("page", "-1");
		password.put("type", "A");
		jsonBody.put("extParameters", password);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C001");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String servicePasswordLogin() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "962138386");
		JSONObject password = new JSONObject();
		password.put("password", "123456");
		jsonBody.put("extParameters", password);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C002");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String sendLoginVerifyCode() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "962138364");
		String jstringRequest = getRequestHeaderJson(jsonBody, "C003");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String verifyCodeLogin() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "962138364");
		JSONObject verifyCode = new JSONObject();
		verifyCode.put("verifyCode", "095921");
		jsonBody.put("extParameters", verifyCode);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C004");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String voucherCardRecharge() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "962138364");
		JSONObject cardPinNumber = new JSONObject();
		cardPinNumber.put("cardPinNumber", "XgX0czMIrX2d6rP1mHQ9f3zUO7nsXk43mlWkntZxESI=");
		jsonBody.put("extParameters", cardPinNumber);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C007");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String queryMyRechargeLogs() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "93268659");
		// JSONObject cardPinNumber = new JSONObject();
		// cardPinNumber.put("cardPinNumber", "11122345678877765554");
		// jsonBody.put("extParameters", cardPinNumber);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C008");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String queryOfferingCategory() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "962138364");
		JSONObject categoryType = new JSONObject();
		categoryType.put("categoryType", "ct_Voice,ct_Data");
		jsonBody.put("extParameters", categoryType);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C009");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String queryOfferings() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "962138364");
		JSONObject categoryId = new JSONObject();
		categoryId.put("categoryId", "2");
		jsonBody.put("extParameters", categoryId);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C010");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String queryDataBalance() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "962138364");
		String jstringRequest = getRequestHeaderJson(jsonBody, "C011");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String queryShops() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "962138364");
		JSONObject categoryId = new JSONObject();
		categoryId.put("cityId", "1");
		// categoryId.put("shopName", "西丽镇");
		jsonBody.put("extParameters", categoryId);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C016");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String queryComplains() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "962138364");
		String jstringRequest = getRequestHeaderJson(jsonBody, "C017");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String querySubscribedOfferings() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "962138364");
		String jstringRequest = getRequestHeaderJson(jsonBody, "C013");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String queryAllBalance() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "962138364");
		String jstringRequest = getRequestHeaderJson(jsonBody, "C015");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String queryMyBills() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "93268659");
		String jstringRequest = getRequestHeaderJson(jsonBody, "C012");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String changeSupplementaryOfferings() throws Exception {
		JSONObject offering1 = new JSONObject();
		offering1.put("offeringId", "1290134625");
		offering1.put("offeringType", "S");
		offering1.put("actionType", "1");
		JSONArray supplementaryOfferings = new JSONArray();
		supplementaryOfferings.put(offering1);
		JSONObject offeringInfo = new JSONObject();
		offeringInfo.put("isChangePrimarryOffering", "N");
		offeringInfo.put("supplementaryOfferings", supplementaryOfferings);
		JSONObject jsonBody = new JSONObject();
		JSONObject extParameters = new JSONObject();
		extParameters.put("offeringInfo", offeringInfo);
		jsonBody.put("serviceNumber", "962138364");
		jsonBody.put("extParameters", extParameters);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C014");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String changePrimaryOffering() throws Exception {

		JSONObject offering1 = new JSONObject();
		offering1.put("offeringId", "1290134625");
		offering1.put("offeringType", "S");
		offering1.put("actionType", "2");
		JSONArray supplementaryOfferings = new JSONArray();
		supplementaryOfferings.put(offering1);
		JSONObject offeringInfo = new JSONObject();
		offeringInfo.put("isChangePrimarryOffering", "Y");
		offeringInfo.put("primaryOfferingId", "111222233");
		offeringInfo.put("supplementaryOfferings", supplementaryOfferings);
		JSONObject jsonBody = new JSONObject();
		JSONObject extParameters = new JSONObject();
		extParameters.put("offeringInfo", offeringInfo);
		jsonBody.put("serviceNumber", "962138364");
		jsonBody.put("extParameters", extParameters);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C014");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String queryUpgardeInfo() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "93268659");
		String jstringRequest = getRequestHeaderJson(jsonBody, "C025");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String queryPushMessage() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "93268659");
		JSONObject extParameters = new JSONObject();
		extParameters.put("type", "B");
		jsonBody.put("extParameters", extParameters);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C026");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String queryTransferBalanceLogs() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "10509888");
		String jstringRequest = getRequestHeaderJson(jsonBody, "C022");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}

	public static String transferBalance() throws Exception {
		JSONObject jsonBody = new JSONObject();
		JSONObject extParameters = new JSONObject();
		extParameters.put("transferorNumber", "962138364");
		extParameters.put("transfereeNumber", "962138386");
		extParameters.put("transferAmount", "20000000");
		jsonBody.put("extParameters", extParameters);
		jsonBody.put("serviceNumber", "962138386");
		String jstringRequest = getRequestHeaderJson(jsonBody, "C021");
		return jstringRequest;
	}

	public static String queryXchangeLogs() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "10509888");
		String jstringRequest = getRequestHeaderJson(jsonBody, "C020");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}
	
	public static String xchangePromotion() throws Exception {
		JSONObject jsonBody = new JSONObject();
		JSONObject extParameters = new JSONObject();
		extParameters.put("xChangeType", "0");
		extParameters.put("applierNumber", "962138386");
		extParameters.put("receiverNumber", "962138386");
		extParameters.put("exchangeAmount", "20000000");
		jsonBody.put("extParameters", extParameters);
		jsonBody.put("serviceNumber", "962138386");
		String jstringRequest = getRequestHeaderJson(jsonBody, "C019");
		return jstringRequest;
	}
	
	public static String changeCustomerInfo() throws Exception {

		JSONObject customerInfo = new JSONObject();
		customerInfo.put("certificateType", "1");
		customerInfo.put("certificateNumber", "111222233");
		customerInfo.put("firstName", "Chai");
		customerInfo.put("dateOfBirth", "19820624");
		customerInfo.put("address", "xili nanshan");
		JSONObject jsonBody = new JSONObject();
		JSONObject extParameters = new JSONObject();
		extParameters.put("customerInfo", customerInfo);
		jsonBody.put("serviceNumber", "962138364");
		jsonBody.put("extParameters", extParameters);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C023");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}
	
	public static String queryDenomination() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "93268659");
		JSONObject extParameters = new JSONObject();
		extParameters.put("type", "1");
		jsonBody.put("extParameters", extParameters);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C027");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}
	public static String querySpecialNum() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "93268659");
		JSONObject extParameters = new JSONObject();
		extParameters.put("type", "A");
		jsonBody.put("extParameters", extParameters);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C028");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}
	public static String queryCdr() throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "93268659");
		JSONObject extParameters = new JSONObject();
		extParameters.put("totalNum", "2886");
		extParameters.put("pageNum", "1");
		jsonBody.put("extParameters", extParameters);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C029");
		// System.out.println(jstringRequest);
		return jstringRequest;
	}
}
