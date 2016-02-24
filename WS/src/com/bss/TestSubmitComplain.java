package com.bss;

import java.io.File;
import java.net.URLEncoder;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.multipart.FilePart;
import org.apache.commons.httpclient.methods.multipart.MultipartRequestEntity;
import org.apache.commons.httpclient.methods.multipart.Part;
import org.apache.http.protocol.HTTP;
import org.json.JSONObject;

public class TestSubmitComplain {

	public static void main(String[] args) throws Exception {
		JSONObject jsonBody = new JSONObject();
		jsonBody.put("serviceNumber", "15899785476");
		JSONObject categoryId = new JSONObject();
		categoryId.put("evalution", "5");
		categoryId.put("text", "投诉");
		jsonBody.put("extParameters", categoryId);
		String jstringRequest = getRequestHeaderJson(jsonBody, "C018");
		String encoderJson = "parameters=" + URLEncoder.encode(jstringRequest, HTTP.UTF_8);
		System.out.println(jstringRequest);
		String targetURL = null; // -- 指定URL
		File targetFile = null; // -- 指定上传文件
		targetFile = new File("C:\\caiqiufu\\App\\test\\20150916_045742000_iOS.png");
		//targetURL = "http://localhost:7081/netWorkServer/testServlet"; // servleturl
		targetURL = "http://127.0.0.1:8080/Unieap/extAction.do?method=submitComplain&" + encoderJson;
		PostMethod filePost = new PostMethod(targetURL);
		try {
			// 通过以下方法可以模拟页面参数提交
			// filePost.setParameter("name", "中文");
			//filePost.setParameter("pass", "1234");
			Part[] parts = { new FilePart(targetFile.getName(), targetFile) };
			filePost.setRequestEntity(new MultipartRequestEntity(parts, filePost.getParams()));
			HttpClient client = new HttpClient();
			client.getHttpConnectionManager().getParams().setConnectionTimeout(5000);
			int status = client.executeMethod(filePost);
			if (status == HttpStatus.SC_OK) {
				System.out.println("上传成功");
				// 上传成功
			} else {
				System.out.println("上传失败");
				// 上传失败
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			filePost.releaseConnection();
		}
	}
	public static String getRequestHeaderJson(JSONObject jsonBody, String bizCode) throws Exception {
		JSONObject jsonRequest = new JSONObject();
		JSONObject jsonResult = new JSONObject();
		jsonRequest.put("requestHeader", jsonResult);
		jsonResult.put("accessUser", "unieap");
		jsonResult.put("accessPwd", "unieap");
		jsonResult.put("bizCode", bizCode);
		jsonResult.put("requestTime", "20150927083325");
		JSONObject jsonExt = new JSONObject();
		jsonExt.put("IMEI", "356952073509324");
		jsonExt.put("OSType", "IOS");
		jsonExt.put("version", "9.0.1");
		jsonExt.put("APKVersion", "1.0");
		jsonExt.put("networkType", "3");
		jsonExt.put("resolution", "1920×1080");
		jsonExt.put("brand", "huawei");
		jsonExt.put("model ", "P8");
		jsonExt.put("extParameters ", "");
		jsonResult.put("deviceInfo", jsonExt);
		// JSONObject jsonBody = new JSONObject();
		// jsonBody.put("serviceNumber", "15899785476");
		// jsonBody.put("extParameters", "");
		jsonRequest.put("requestBody", jsonBody);
		System.out.println(jsonRequest.toString());
		return jsonRequest.toString();
	}
}
