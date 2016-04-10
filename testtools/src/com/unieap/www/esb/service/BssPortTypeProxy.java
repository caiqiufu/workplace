package com.unieap.www.esb.service;

public class BssPortTypeProxy implements com.unieap.www.esb.service.BssPortType {
  private String _endpoint = null;
  private com.unieap.www.esb.service.BssPortType bssPortType = null;
  
  public BssPortTypeProxy() {
    _initBssPortTypeProxy();
  }
  
  public BssPortTypeProxy(String endpoint) {
    _endpoint = endpoint;
    _initBssPortTypeProxy();
  }
  
  private void _initBssPortTypeProxy() {
    try {
      bssPortType = (new com.unieap.www.esb.service.BssServiceLocator()).getBssPort();
      if (bssPortType != null) {
        if (_endpoint != null)
          ((javax.xml.rpc.Stub)bssPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
        else
          _endpoint = (String)((javax.xml.rpc.Stub)bssPortType)._getProperty("javax.xml.rpc.service.endpoint.address");
      }
      
    }
    catch (javax.xml.rpc.ServiceException serviceException) {}
  }
  
  public String getEndpoint() {
    return _endpoint;
  }
  
  public void setEndpoint(String endpoint) {
    _endpoint = endpoint;
    if (bssPortType != null)
      ((javax.xml.rpc.Stub)bssPortType)._setProperty("javax.xml.rpc.service.endpoint.address", _endpoint);
    
  }
  
  public com.unieap.www.esb.service.BssPortType getBssPortType() {
    if (bssPortType == null)
      _initBssPortTypeProxy();
    return bssPortType;
  }
  
  public java.lang.String queryInfo(java.lang.String cRequest) throws java.rmi.RemoteException{
    if (bssPortType == null)
      _initBssPortTypeProxy();
    return bssPortType.queryInfo(cRequest);
  }
  
  
}