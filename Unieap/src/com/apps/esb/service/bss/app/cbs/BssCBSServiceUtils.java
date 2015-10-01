package com.apps.esb.service.bss.app.cbs;

import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPMessage;

import com.apps.esb.service.bss.BssServiceUtils;
import com.unieap.base.SYSConfig;

public class BssCBSServiceUtils {
	/**
	 * CBS AR header
	 * @param method
	 * @param message
	 * @throws Exception
	 */
	public static void getCBSARHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("ars", "http://www.huawei.com/bme/cbsinterface/arservices");
		envelope.addNamespaceDeclaration("cbs", "http://www.huawei.com/bme/cbsinterface/cbscommon");
		envelope.addNamespaceDeclaration("arc", "http://cbs.huawei.com/ar/wsservice/arcommon");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method, "ars");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader");
		requestHeaderElement.addChildElement("Version", "cbs").addTextNode("1");
		requestHeaderElement.addChildElement("MessageSeq", "cbs").addTextNode(BssServiceUtils.generateTransactionId());
		SOAPElement accessSecurity = requestHeaderElement.addChildElement("AccessSecurity", "cbs");
		accessSecurity.addChildElement("LoginSystemCode", "cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessUser"));
		accessSecurity.addChildElement("Password", "cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessPwd"));
		requestHeaderElement.addChildElement("AccessMode", "cbs").addTextNode("2");
	}
	public static void getCBSBBHeader(String method, SOAPMessage message) throws Exception {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("bbs", "http://www.huawei.com/bme/cbsinterface/bbservices");
		envelope.addNamespaceDeclaration("cbs", "http://www.huawei.com/bme/cbsinterface/cbscommon");
		SOAPBody body = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement(method, "bbs");
		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader");
		requestHeaderElement.addChildElement("Version", "cbs").addTextNode("1");
		requestHeaderElement.addChildElement("MessageSeq", "cbs").addTextNode(BssServiceUtils.generateTransactionId());
		SOAPElement accessSecurity = requestHeaderElement.addChildElement("AccessSecurity", "cbs");
		accessSecurity.addChildElement("LoginSystemCode", "cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessUser"));
		accessSecurity.addChildElement("Password", "cbs").addTextNode(SYSConfig.getConfig().get("cbs.inf.accessPwd"));
		requestHeaderElement.addChildElement("AccessMode", "cbs").addTextNode("2");
	}
}
