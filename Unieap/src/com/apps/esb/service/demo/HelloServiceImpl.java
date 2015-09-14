package com.apps.esb.service.demo;

import javax.jws.WebService;

@WebService(serviceName = "HelloService", endpointInterface = "com.apps.esb.service.demo.HelloService")
public class HelloServiceImpl implements HelloService {
	@Override
    public String sayHello(String name) { 
		System.out.println("Service return :Hello, " + name + "!");
        return "Hello, " + name + "!";   
    } 
}
