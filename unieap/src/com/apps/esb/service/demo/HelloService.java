package com.apps.esb.service.demo;

import javax.jws.WebService;

@WebService(name = "HelloService", targetNamespace = "http://www.unieap.com/esb/service/hello/definitions")
public interface HelloService {
	String sayHello(String name);
}
