package com.apps.esb.service.bss.customize.smart.app.handler;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.base.SYSConfig;

public class CustSoapMessageHandler extends SoapMessageHandler{
	/**
	 * CRM query header
	 * @param method
	 * @param message
	 * @throws Exception
	 */
	public void getCRMQuerHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("quer", "http://crm.huawei.com/query/");
		envelope.addNamespaceDeclaration("bas", "http://crm.huawei.com/basetype/");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method, "quer");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader","quer");
		requestHeaderElement.addChildElement("TransactionId","bas").addTextNode(BssServiceUtils.generateTransactionId());
		requestHeaderElement.addChildElement("ChannelId","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.channelCode"));
		requestHeaderElement.addChildElement("TechnicalChannelId","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.systemCode"));
		requestHeaderElement.addChildElement("AccessUser","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.accessUser"));
		requestHeaderElement.addChildElement("AccessPwd","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.accessPwd"));
	}
	public void getCRMUpdHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("upd", "http://crm.huawei.com/update/");
		envelope.addNamespaceDeclaration("bas", "http://crm.huawei.com/basetype/");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method, "upd");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader","upd");
		requestHeaderElement.addChildElement("TransactionId","bas").addTextNode(BssServiceUtils.generateTransactionId());
		requestHeaderElement.addChildElement("ChannelId","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.channelCode"));
		requestHeaderElement.addChildElement("TechnicalChannelId","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.systemCode"));
		requestHeaderElement.addChildElement("AccessUser","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.accessUser"));
		requestHeaderElement.addChildElement("AccessPwd","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.accessPwd"));
	}
	public void getCRMSerHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("ser", "http://crm.huawei.com/service/");
		envelope.addNamespaceDeclaration("bas", "http://crm.huawei.com/basetype/");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method, "ser");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader","ser");
		requestHeaderElement.addChildElement("TransactionId","bas").addTextNode(BssServiceUtils.generateTransactionId());
		requestHeaderElement.addChildElement("ChannelId","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.channelCode"));
		requestHeaderElement.addChildElement("TechnicalChannelId","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.systemCode"));
		requestHeaderElement.addChildElement("AccessUser","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.accessUser"));
		requestHeaderElement.addChildElement("AccessPwd","bas").addTextNode(SYSConfig.getConfig().get("crm.inf.accessPwd"));
	}
	public void getCBSArsHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("ars", "http://www.huawei.com/bme/cbsinterface/arservices");
		envelope.addNamespaceDeclaration("cbs", "http://www.huawei.com/bme/cbsinterface/cbscommon");
		envelope.addNamespaceDeclaration("arc", "http://cbs.huawei.com/ar/wsservice/arcommon");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method,"ars");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader");
		requestHeaderElement.addChildElement("Version","cbs").addTextNode("1.0");
		requestHeaderElement.addChildElement("MessageSeq","cbs").addTextNode(BssServiceUtils.generateTransactionId());
		
		SOAPElement accessSecurityElement = requestHeaderElement.addChildElement("AccessSecurity","cbs");
		accessSecurityElement.addChildElement("LoginSystemCode","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessUser"));
		accessSecurityElement.addChildElement("Password","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessPwd"));
		requestHeaderElement.addChildElement("AccessMode","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.channelCode"));
	}
	public void getCBSBcHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("bcs", "http://www.huawei.com/bme/cbsinterface/bcservices");
		envelope.addNamespaceDeclaration("cbs", "http://www.huawei.com/bme/cbsinterface/cbscommon");
		envelope.addNamespaceDeclaration("bcc", "http://www.huawei.com/bme/cbsinterface/bccommon");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method,"bcs");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader");
		requestHeaderElement.addChildElement("Version","cbs").addTextNode("1.0");
		requestHeaderElement.addChildElement("MessageSeq","cbs").addTextNode(BssServiceUtils.generateTransactionId());
		
		SOAPElement accessSecurityElement = requestHeaderElement.addChildElement("AccessSecurity","cbs");
		accessSecurityElement.addChildElement("LoginSystemCode","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessUser"));
		accessSecurityElement.addChildElement("Password","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessPwd"));
		requestHeaderElement.addChildElement("AccessMode","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.channelCode"));
	}
	public void getCBSBbHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("bbs", "http://www.huawei.com/bme/cbsinterface/bbservices");
		envelope.addNamespaceDeclaration("cbs", "http://www.huawei.com/bme/cbsinterface/cbscommon");
		envelope.addNamespaceDeclaration("bbc", "http://www.huawei.com/bme/cbsinterface/bbcommon");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method,"bbs");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader");
		requestHeaderElement.addChildElement("Version","cbs").addTextNode("1.0");
		requestHeaderElement.addChildElement("MessageSeq","cbs").addTextNode(BssServiceUtils.generateTransactionId());
		
		SOAPElement accessSecurityElement = requestHeaderElement.addChildElement("AccessSecurity","cbs");
		accessSecurityElement.addChildElement("LoginSystemCode","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessUser"));
		accessSecurityElement.addChildElement("Password","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessPwd"));
		requestHeaderElement.addChildElement("AccessMode","cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.channelCode"));
	}
}
