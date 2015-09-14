package com.apps.esb.client;

import java.rmi.RemoteException;

import javax.xml.rpc.ServiceException;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		HelloServiceLocator locator = new HelloServiceLocator();
    	try {
			String repnose = locator.getHelloPort().sayHello("Chai Chai");
			System.out.println("Hello Response [" + repnose + "]");
		} catch (RemoteException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
