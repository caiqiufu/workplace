/**
 * HelloService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.unieap.www.esb.service.hello.definitions;

public interface HelloService extends javax.xml.rpc.Service {
    public java.lang.String getHelloPortAddress();

    public com.unieap.www.esb.service.hello.definitions.HelloPortType getHelloPort() throws javax.xml.rpc.ServiceException;

    public com.unieap.www.esb.service.hello.definitions.HelloPortType getHelloPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
