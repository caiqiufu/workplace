package com.apps.esb.service.bss.handler;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.springframework.util.StringUtils;

import com.apps.esb.service.bss.element.RequestHeader;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;

public class SoapMessageHandler {
	
	public void esbLog(SOAPMessage request, SOAPMessage response, RequestHeader requestHeader,ProcessResult processResult,
			String during,String destSystem) {
		Map<String, Object> data = new HashMap<String, Object>();
		data.put("request", request);
		data.put("response", response);
		data.put("during", during);
		data.put("requestHeader", requestHeader);
		data.put("processResult", processResult);
		data.put("destSystem", destSystem);
		CacheMgt.addEsbSOAPlog(data);
	}

	
	public final static String converDateFormat(String dateTime, String format) {
		if (StringUtils.isEmpty(format)) {
			format = UnieapConstants.TIMEFORMAT;
		}
		Date data = new Date(dateTime);
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		String dt = sdf.format(data);
		return dt;
	}
	public String[] getTimes(int number) {
		Calendar cale = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String firstday, lastday;
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, -number);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime()) + "000000";

		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, -number + 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime()) + "235959";
		return new String[] { firstday, lastday };
	}
}
