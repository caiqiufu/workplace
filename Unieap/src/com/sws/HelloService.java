package com.sws;

import javax.jws.WebService;

@WebService(name = "HelloService", targetNamespace = "http://www.ispring.com/ws/hello/definitions")
public interface HelloService {
	String sayHello(String name);
}
