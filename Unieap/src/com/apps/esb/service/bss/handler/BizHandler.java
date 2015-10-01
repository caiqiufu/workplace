package com.apps.esb.service.bss.handler;

import javax.xml.soap.SOAPMessage;

import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;

public interface BizHandler {
	public ProcessResult process(RequestInfo requestInfo, String serviceNumber, String extParameters, String parameters)
			throws Exception;

	public RequestInfo getRequestInfoFromSOAPMessage(SOAPMessage request) throws Exception;

	public ResponsetInfo getResponseInfoFromSOAPMessage(SOAPMessage response) throws Exception;

	public SOAPMessage getRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo) throws Exception;

	public ProcessResult getResposeResult(SOAPMessage response) throws Exception;
}
