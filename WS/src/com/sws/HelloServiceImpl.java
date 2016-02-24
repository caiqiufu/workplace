package com.sws;

import javax.jws.WebService;

@WebService(serviceName = "HelloService", endpointInterface = "com.sws.HelloService")
public class HelloServiceImpl implements HelloService {
	@Override
    public String sayHello(String name) { 
		System.out.println("Service return :Hello, " + name + "!");
        return "Hello, " + name + "!";   
    } 
}
