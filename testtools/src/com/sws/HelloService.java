package com.sws;

import javax.jws.WebService;

@WebService(name = "HelloService", targetNamespace = "http://www.unieap.com/esb/service")
public interface HelloService {
	String sayHello(String name);
}
