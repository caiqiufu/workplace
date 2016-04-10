package com.demo;

import javax.jws.WebService;

@WebService(serviceName = "BssService", endpointInterface = "com.demo.BssService")
public class BssServiceImpl implements BssService{

	@Override
	public ResponsetInfo queryInfo(RequestInfo qequestInfo) {
		System.out.println("request info");
		return null;
	}

}
