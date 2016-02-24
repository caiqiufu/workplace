package com.bss;

import java.io.ByteArrayInputStream;
import java.io.StringWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.Iterator;

import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;



public class BssClient {
	public static final String NAMESPACE_URI = "http://www.huawei.com/query/";
	public static final String PREFIX = "quer";

	private SOAPConnectionFactory connectionFactory;

	private MessageFactory messageFactory;

	private URL url;

	public BssClient(String url) throws UnsupportedOperationException, SOAPException, MalformedURLException {
		connectionFactory = SOAPConnectionFactory.newInstance();
		messageFactory = MessageFactory.newInstance();
		this.url = new URL(url);
	}

	private SOAPMessage createRequest(String requestInfo) throws SOAPException {
		SOAPMessage message = messageFactory.createMessage();
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.addNamespaceDeclaration("quer", "http://crm.huawei.com/query/");
		envelope.addNamespaceDeclaration("bas", "http://crm.huawei.com/basetype/");
		SOAPBody body = envelope.getBody();
		SOAPEnvelope envelope2 = message.getSOAPPart().getEnvelope();
		SOAPBody body2 = envelope.getBody();
		SOAPElement bodyElement = body.addChildElement("GetCustomerRequest", "quer");

		SOAPElement requestHeaderElement = bodyElement.addChildElement("RequestHeader", "quer");
		requestHeaderElement.addChildElement("Version", "bas").addTextNode("1.2");
		requestHeaderElement.addChildElement("TransactionId", "bas").addTextNode("111111111111111");
		requestHeaderElement.addChildElement("ChannelId", "bas").addTextNode("18");
		requestHeaderElement.addChildElement("TechnicalChannelId", "bas").addTextNode("53");
		requestHeaderElement.addChildElement("TenantId", "bas").addTextNode("101");
		requestHeaderElement.addChildElement("AccessUser", "bas").addTextNode("ipcc");
		requestHeaderElement.addChildElement("AccessPwd", "bas").addTextNode("r8q0a5WwGNboj9I35XzNcQ==");
		SOAPElement requestGetCustomerBodyElement = bodyElement.addChildElement("GetCustomerBody", "quer");
		requestGetCustomerBodyElement.addChildElement("CustomerId", "quer").addTextNode("112222");
		
		
		//////////////////////////////
		
		//Document doc = (Document) message.getSOAPPart().getEnvelope().getBody().extractContentAsDocument();
		//Element root = doc.getRootElement();  
		//root.elements("GetCustomerResponse"); 
		
		SOAPBody body4 = message.getSOAPBody();
		body2.getChildElements();
		 Iterator<SOAPElement> iterator = body2.getChildElements();
		 while (iterator.hasNext()){
			 
		 }
		
		
		Name helloResponseName = envelope.createName("GetCustomerRequest", "quer", "http://crm.huawei.com/query/");
		Iterator childElements = message.getSOAPBody().getChildElements(helloResponseName);
		SOAPElement helloResponseElement = (SOAPElement) childElements.next();
		
		String value = helloResponseElement.getTextContent();
		System.out.println("GetCustomerRequest [" + value + "]");		
		
		/*
		 * SOAPElement requestHeaderElement =
		 * bodyElement.addChildElement("RequestHeader", "quer",
		 * "http://www.huawei.com/query/");
		 * requestHeaderElement.addChildElement("Version", "bas",
		 * "http://crm.huawei.com/basetype/").addTextNode("1.2");
		 * requestHeaderElement.addChildElement("TransactionId", "bas",
		 * "http://crm.huawei.com/basetype/") .addTextNode("111111111111111"); ;
		 * requestHeaderElement.addChildElement("ChannelId", "bas",
		 * "http://crm.huawei.com/basetype/").addTextNode("18");
		 * requestHeaderElement.addChildElement("TechnicalChannelId", "bas",
		 * "http://crm.huawei.com/basetype/") .addTextNode("53");
		 * requestHeaderElement.addChildElement("TenantId", "bas",
		 * "http://crm.huawei.com/basetype/").addTextNode("101");
		 * requestHeaderElement.addChildElement("AccessUser", "bas",
		 * "http://crm.huawei.com/basetype/") .addTextNode("ipcc");
		 * requestHeaderElement.addChildElement("AccessPwd", "bas",
		 * "http://crm.huawei.com/basetype/")
		 * .addTextNode("r8q0a5WwGNboj9I35XzNcQ=="); SOAPElement
		 * requestGetCustomerBodyElement =
		 * bodyElement.addChildElement("GetCustomerBody", "quer",
		 * "http://www.huawei.com/query/");
		 * requestGetCustomerBodyElement.addChildElement("CustomerId", "quer",
		 * "http://crm.huawei.com/query/") .addTextNode("112222");
		 */
		// Name helloRequestName =
		// envelope.createName("GetCustomerRequest",PREFIX,NAMESPACE_URI);
		// SOAPBodyElement helloRequestElement =
		// message.getSOAPBody().addBodyElement(helloRequestName);
		// helloRequestElement.setValue(requestInfo);
		message.saveChanges();
		System.out.println("Request message:" + message.getSOAPBody().getTextContent());
		return message;
	}

	public String getReuqestString() {
		StringBuffer request = new StringBuffer();
		request.append(
				"<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:quer=\"http://crom.huawei.com/query/\" xmlns:bas=\"http://crm.huawei.com/basetype\">");
		request.append("<soapenv:Header/>");
		request.append("<soapenv:Body>");
		request.append("<quer:GetCustomerRequest>");
		request.append("<quer:RequestHeader>");
		request.append("<bas:Version>1.2</bas:Version>");
		request.append("<bas:TransactionId>1111111111111111111</bas:TransactionId>");
		request.append("<bas:ChannelId>18</bas:ChannelId>");
		request.append("<bas:TechnicalChannelId>53</bas:TechnicalChannelId>");
		request.append("<bas:TenantId>101</bas:TenantId>");
		request.append("<bas:AccessUser>ipcc</bas:AccessUser>");
		request.append("<bas:AccseePwd>r8q0a5WwGNboj9I35XzNcQ==</bas:AccseePwd>");
		request.append("</quer:RequestHeader>");
		request.append("<quer:GetCustomerBody>");
		request.append("<quer:CustomerId>111111223333</quer:CustomerId>");
		request.append("</quer:GetCustomerBody>");
		request.append("</quer:GetCustomerRequest>");
		request.append("</soapenv:Body>");
		request.append("</soapenv:Envelope>");
		return request.toString();
	}

	public String callWebService(String requestInfo) throws Exception {
		//SOAPMessage request = createRequest(requestInfo);
		SOAPMessage request = formatSoapMessage(getReuqestString());
		SOAPBody body1 = request.getSOAPPart().getEnvelope().getBody();
		SOAPBody body2 = request.getSOAPPart().getEnvelope().getBody();
		Document doc = request.getSOAPPart().getEnvelope().getOwnerDocument(); 
		doc.getElementsByTagName("bas:Version").item(0).getTextContent();
		StringWriter output = new StringWriter();  
		TransformerFactory.newInstance().newTransformer().transform( new DOMSource(doc), new StreamResult(output));  
		System.out.println(output.toString()); 
		
		Transformer trans = TransformerFactory.newInstance().newTransformer();
		StringWriter sw = new StringWriter();
		trans.transform(new DOMSource(request.getSOAPPart().getEnvelope()), new StreamResult(sw));
	    sw.flush();
	    sw.close();
	    System.out.println(sw.toString());
		
		
		request.writeTo(System.out);
		Document document = request.getSOAPPart().getEnvelope().getBody().extractContentAsDocument(); 
		if(document.getElementsByTagName("quer:CustomerId").getLength()>0){
			String str = document.getElementsByTagName("quer:CustomerId").item(0).getTextContent(); 
		}
		document.getTextContent();
		Element root = document.getDocumentElement();
		SOAPConnection connection = connectionFactory.createConnection();
		SOAPMessage response = connection.call(request, url);
		response.writeTo(System.out);
		return writeResponse(response);
	}

	public SOAPMessage formatSoapMessage(String soapString) throws Exception {
		SOAPMessage reqMsg = messageFactory.createMessage(new MimeHeaders(),
				new ByteArrayInputStream(soapString.getBytes(Charset.forName("UTF-8"))));
		reqMsg.saveChanges();
		return reqMsg;
	}

	private String writeResponse(SOAPMessage message) throws SOAPException {
		SOAPEnvelope envelope = message.getSOAPPart().getEnvelope();
		envelope.getBody().getTextContent();
		Name responseName = envelope.createName("GetCustomerResponse", "quer", "http://crm.huawei.com/query/");
		Iterator childElements = message.getSOAPBody().getChildElements(responseName);
		SOAPBodyElement responseElement = (SOAPBodyElement) childElements.next();
		return responseElement.getTextContent();
	}

	public static void main(String[] args) throws UnsupportedOperationException, MalformedURLException, Exception {
		String url = "http://10.12.8.21:8060/crm/services/OrderQuery";
		BssClient client = new BssClient(url);
		String reponse = client.callWebService(
				"{RequestHeader:{accessPwd:'accessPwd',accessUser:'accessUser',bizCode:'C001',channelCode:'channelCode',extTransactionId:'extTransactionId',transactionId:'transactionId',requestTime:'requestTime',responseTime:'responseTime',systemCode:'systemCode',version:'version'},RequestBody:{serviceNumber:'serviceNumber',extParameters:'extParameters'}}");
		System.out.println("Response message[" + reponse + "]");
	}
}
