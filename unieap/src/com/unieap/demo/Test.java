package com.unieap.demo;

import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

import com.unieap.tools.JSONUtils;

public class Test {

	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String drap = "1/2/3/4";
		String target = "1/2/7";
		String nodepath1 = "1/2/3/4/8/9";
		String nodepath2 = "1/2/3/4/8/10";
		String replaced = nodepath1.replaceAll(drap,target);
		System.out.print(replaced);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
		String sKey = "1234567890111110";
		String sSrc = "123456";
		//String enString = EncryptionUtils.encrypt(sSrc, sKey);
		//System.out.println("enString=" + enString);
		//String deString = EncryptionUtils.decrypt(enString, sKey);
		//System.out.println("deString=" + deString);
		testVO();
	}
	public static void testVO() throws Exception{
		TestVO vo = new TestVO();
		vo.setName("Chai");
		Map<String,String> extAttris = new HashMap<String,String>();
		extAttris.put("TEST_ATTRIBUTE1", "attribute1");
		extAttris.put("TEST_ATTRIBUTE2", "attribute2");
		//vo.setExtAttris(extAttris);
		JSONObject dataBalanceJson = JSONUtils.convertBean2JSON(vo);
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("dataBalance", dataBalanceJson);
		System.out.print(jsonResult.toString());
	}
}
