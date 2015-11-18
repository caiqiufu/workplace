package com.apps.esb.client;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;

public class BssClient {
	public static final String NAMESPACE_URI = "http://www.unieap.com/esb/service";
	public static final String PREFIX = "tns";

	private SOAPConnectionFactory connectionFactory;

	private MessageFactory messageFactory;

	private URL url;

	public BssClient(String url) throws UnsupportedOperationException, SOAPException, MalformedURLException{
	        connectionFactory = SOAPConnectionFactory.newInstance();
	        messageFactory = MessageFactory.newInstance();
	        this.url = new URL(url);
	    }
	private SOAPMessage createRequest(String requestInfo) throws SOAPException{
        SOAPMessage message = messageFactory.createMessage();
        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
        Name helloRequestName = envelope.createName("eRequest",PREFIX,NAMESPACE_URI);
        SOAPBodyElement helloRequestElement = message.getSOAPBody().addBodyElement(helloRequestName);
        helloRequestElement.setValue(requestInfo);
        return message;
    }
	public String callWebService(String requestInfo) throws SOAPException{
        SOAPMessage request = createRequest(requestInfo);
        SOAPConnection connection = connectionFactory.createConnection();
        SOAPMessage response = connection.call(request, url);
        return writeResponse(response);
    }
	 private String writeResponse(SOAPMessage message) throws SOAPException{
	        SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
	        Name responseName = envelope.createName("eResponse",PREFIX,NAMESPACE_URI);
	        Iterator childElements = message.getSOAPBody().getChildElements(responseName);
	        SOAPBodyElement responseElement = (SOAPBodyElement)childElements.next();
	        return responseElement.getTextContent();
	    }
	 
	 public static void main(String[] args) throws UnsupportedOperationException, MalformedURLException, SOAPException {
	        String url = "http://localhost:8080/Unieap/services/bssservice.wsdl";
	        BssClient client = new BssClient(url);
	        String reponse = client.callWebService("{RequestHeader:{accessPwd:'accessPwd',accessUser:'accessUser',bizCode:'C001',channelCode:'channelCode',extTransactionId:'extTransactionId',transactionId:'transactionId',requestTime:'requestTime',responseTime:'responseTime',systemCode:'systemCode',version:'version'},RequestBody:{serviceNumber:'serviceNumber',extParameters:'extParameters'}}");
	        System.out.println("Hello Response [" + reponse + "]");
	    }
}
