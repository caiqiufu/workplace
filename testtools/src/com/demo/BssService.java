package com.demo;

import javax.jws.WebService;

@WebService(name = "BssService", targetNamespace = "http://www.unieap.com/esb/service")
public interface BssService {
	/*
	 * query info 
	 */
	public ResponsetInfo queryInfo(RequestInfo qequestInfo);
}
