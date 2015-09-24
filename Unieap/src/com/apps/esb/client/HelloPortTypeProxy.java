package com.apps.esb.client;

public class HelloPortTypeProxy implements com.unieap.www.esb.service.hello.definitions.HelloPortType {
  private String _endpoint = null;
  private com.unieap.www.esb.service.hello.definitions.HelloPortType helloPortType = null;
  
  public HelloPortTypeProxy() {
    _initHelloPortTypeProxy();
  }
  
  public HelloPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initHelloPortTypeProxy();
  }
  
  private void _initHelloPortTypeProxy() {
    try {
      helloPortType = (new com.unieap.www.esb.service.hello.definitions.HelloServiceLocator()).getHelloPort();
      if (helloPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)helloPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)helloPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (helloPortType != null)
      ((javax.xml.rpc.Stub)helloPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.unieap.www.esb.service.hello.definitions.HelloPortType getHelloPortType() {
    if (helloPortType == null)
      _initHelloPortTypeProxy();
    return helloPortType;
  }
  
  public java.lang.String sayHello(java.lang.String cRequest) throws java.rmi.RemoteException{
    if (helloPortType == null)
      _initHelloPortTypeProxy();
    return helloPortType.sayHello(cRequest);
  }
  
  
}