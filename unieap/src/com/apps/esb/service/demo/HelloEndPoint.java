package com.apps.esb.service.demo;

import org.springframework.ws.server.endpoint.AbstractDomPayloadEndpoint;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Text;

public class HelloEndPoint extends AbstractDomPayloadEndpoint{
	/**
     * Namespace of both request and response
     */
    public static final String NAMESPACE_URI = "http://www.unieap.com/esb/service/hello";
    
    /**
     * The local name of the expected request
     */
    public static final String HELLO_REQUEST_LOCAL_NAME = "eRequest";
    
    /**
     * The local name of the created response
     */
    public static final String HELLO_RESPONSE_LOCAL_NAME = "eResponse";
    
    private HelloService helloService;
    
    @Override
    protected Element invokeInternal(Element requestElement, Document document)throws Exception {
        //Assert.isTrue(NAMESPACE_URI.equals(requestElement.getNamespaceURI()), "Invalid namespace");
        //Assert.isTrue(HELLO_REQUEST_LOCAL_NAME.equals(requestElement.getLocalName()), "Invalid local name");
        
        NodeList children = requestElement.getChildNodes();
        Text requestText = null;
        for(int i=0; i<children.getLength(); i++){
            if(children.item(i).getNodeType() == Node.TEXT_NODE){
                requestText = (Text) children.item(i);
            }
        }
        
        if(requestText == null){
            throw new IllegalArgumentException("Could not find request text node");
        }
        
        String response = helloService.sayHello(requestText.getNodeValue());
        Element responseElement = document.createElementNS(NAMESPACE_URI, HELLO_RESPONSE_LOCAL_NAME);
        Text responseText = document.createTextNode(response);
        responseElement.appendChild(responseText);
        return responseElement;
    }
    public HelloService getHelloService() {
        return helloService;
    }
    public void setHelloService(HelloService helloService) {
        this.helloService = helloService;
    }
}
