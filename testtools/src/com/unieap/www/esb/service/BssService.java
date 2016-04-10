/**
 * BssService.java
 *
 * This file was auto-generated from WSDL
 * by the Apache Axis 1.4 Apr 22, 2006 (06:55:48 PDT) WSDL2Java emitter.
 */

package com.unieap.www.esb.service;

public interface BssService extends javax.xml.rpc.Service {
    public java.lang.String getBssPortAddress();

    public com.unieap.www.esb.service.BssPortType getBssPort() throws javax.xml.rpc.ServiceException;

    public com.unieap.www.esb.service.BssPortType getBssPort(java.net.URL portAddress) throws javax.xml.rpc.ServiceException;
}
