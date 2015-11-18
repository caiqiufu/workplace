package com.apps.esb.client;

import java.net.URLEncoder;

/*import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;*/
import org.json.JSONObject;

/**
 * This example demonstrates the use of a local HTTP context populated with
 * custom attributes.
 */
public class HttpRequestTest {

	private static final String APPLICATION_JSON = "application/json";

	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	public final static void main(String[] args) throws Exception {
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("serviceNumber", "1234565");
		JSONObject jsonExt = new JSONObject();
		jsonExt.put("name", "Chai");
		jsonResult.put("extParameters", jsonExt);
		//String json = jsonResult.toString();
		//String json = "{RequestHeader:{accessPwd:'accessPwd',accessUser:'accessUser',bizCode:'C004',channelCode:'APP',extTransactionId:'2015092122120300011',transactionId:'',requestTime:'2015-09-21 10:13:04',responseTime:'',systemCode:'AND',version:'1.0'},RequestBody:{serviceNumber:'93268659',extParameters:'extParameters'}}";
		// HttpGet httpget = new
		// HttpGet("http://127.0.0.1:8080/Unieap/ExtAction.do?method=queryInfo&parameters=123");
		//编码过的json字符串
		//String encoderJson ="parameters="+URLEncoder.encode(json, HTTP.UTF_8);
		//String url = "http://127.0.0.1:8080/Unieap/ExtAction.do?method=queryInfo&"+encoderJson;
		//test(encoderJson, url);
	}

	/*public static void test(String json, String url) throws Exception {
		// 将JSON进行UTF-8编码,以便传输中文
		String encoderJson = URLEncoder.encode(json, HTTP.UTF_8);

		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON);

		StringEntity se = new StringEntity(encoderJson);
		se.setContentType(CONTENT_TYPE_TEXT_JSON);
		se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE, APPLICATION_JSON));
		//httpPost.setEntity(se);
		CloseableHttpResponse response = httpClient.execute(httpPost);
		try {  
            HttpEntity entity = response.getEntity();  
            System.out.println("----------------------------------------");  
            System.out.println(response.getStatusLine());  
            if (entity != null) {  
                System.out.println("Response content length: " + entity.getContentLength());  
                System.out.println(EntityUtils.toString(entity));  
                EntityUtils.consume(entity);  
            }  
        } finally {  
            response.close();  
        }
	}*/
}
