package com.apps.esb.service.bss;

import javax.jws.WebService;

@WebService(name = "BssService", targetNamespace = "http://www.unieap.com/esb/service")
public interface BssService {
	public String queryInfo(String requestInfo);
}
