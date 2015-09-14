package com.apps.esb.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.rmi.RemoteException;
import java.util.Iterator;

import javax.xml.rpc.ServiceException;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFault;
import javax.xml.soap.SOAPMessage;

public class HelloWebServiceClient {

public static final String NAMESPACE_URI = "http://www.unieap.com/esb/service/hello";
    
    public static final String PREFIX = "tns";
    
    private SOAPConnectionFactory connectionFactory;
    
    private MessageFactory messageFactory;
    
    private URL url;
    
    public HelloWebServiceClient(String url) throws UnsupportedOperationException, SOAPException, MalformedURLException{
        connectionFactory = SOAPConnectionFactory.newInstance();
        messageFactory = MessageFactory.newInstance();
        this.url = new URL(url);
    }
    
    private SOAPMessage createHelloRequest() throws SOAPException{
        SOAPMessage message = messageFactory.createMessage();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name helloRequestName = envelope.createName("eRequest",PREFIX,NAMESPACE_URI);
        SOAPBodyElement helloRequestElement = message.getSOAPBody().addBodyElement(helloRequestName);
        helloRequestElement.setValue("ispring");
        return message;
    }
    
    public void callWebService() throws SOAPException{
        SOAPMessage request = createHelloRequest();
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        if(!response.getSOAPBody().hasFault()){
            writeHelloResponse(response);
        }else{
            SOAPFault fault = response.getSOAPBody().getFault();
            System.err.println("Received SOAP Fault");
            System.err.println("SOAP Fault Code : " + fault.getFaultCode());
            System.err.println("SOAP Fault String : " + fault.getFaultString());            
        }
    }
    
    private void writeHelloResponse(SOAPMessage message) throws SOAPException{
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name helloResponseName = envelope.createName("eResponse",PREFIX,NAMESPACE_URI);
        Iterator childElements = message.getSOAPBody().getChildElements(helloResponseName);
        SOAPBodyElement helloResponseElement = (SOAPBodyElement)childElements.next();
        String value = helloResponseElement.getTextContent();
        System.out.println("Hello Response [" + value + "]");
    }
    
    public static void test2(){
    	//BeanFactory f = new ClassPathXmlApplicationContext("spring-ws-servlet.xml");  
    	//HelloService du = (HelloService)f.getBean("simpleService");  
    	//du.sayHello("Chai");
    	HelloServiceLocator locator = new HelloServiceLocator();
    	try {
			String repnose = locator.getHelloPort().sayHello("Chai Chai");
			System.out.println("Hello Response [" + repnose + "]");
		} catch (RemoteException | ServiceException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
    
    public static void main(String[] args) throws UnsupportedOperationException, MalformedURLException, SOAPException {
        String url = "http://localhost:8081/WS/services/hello";
        HelloWebServiceClient helloClient = new HelloWebServiceClient(url);
        helloClient.callWebService();
        //test2();
    }
    
	
}
