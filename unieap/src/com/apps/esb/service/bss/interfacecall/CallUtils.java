package com.apps.esb.service.bss.interfacecall;

import java.util.HashMap;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import com.apps.esb.service.bss.element.RequestHeader;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.unieap.CacheMgt;

public class CallUtils {
	public static void esbLog(SOAPMessage request, SOAPMessage response, RequestHeader requestHeader,ProcessResult processResult,
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
}
