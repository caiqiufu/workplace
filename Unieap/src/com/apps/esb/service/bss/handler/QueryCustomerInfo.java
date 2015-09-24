package com.apps.esb.service.bss.handler;

import java.io.ByteArrayInputStream;
import java.nio.charset.Charset;
import java.util.Iterator;

import javax.xml.soap.MimeHeaders;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;

import com.apps.esb.pojo.Esblog;
import com.apps.esb.service.bss.BssErrorCode;
import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.crm.vo.CustomerVO;
import com.apps.esb.service.bss.element.RequestHeader;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponseBody;
import com.apps.esb.service.bss.element.ResponsetHeader;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.tools.JSONUtils;

@Service("queryCustomerInfo")
public class QueryCustomerInfo extends SoapMessageHandler implements BizHandler {

	public QueryCustomerInfo() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String serviceNumber, String extParameters, String parameters)
			throws Exception {
		return getCustomerInfo(serviceNumber, requestInfo);

	}

	public ProcessResult getCustomerInfo(String serviceNumber, RequestInfo requestInfo) throws Exception {
		String requestTime = UnieapConstants.getCurrentTime(null, null);
		requestInfo.getRequestHeader().setRequestTime(requestTime);
		requestInfo.getRequestHeader().setSystemCode(UnieapConstants.ESB);
		long beginTime = System.currentTimeMillis();
		SOAPMessage request = getRequestSOAPMessage(serviceNumber, requestInfo);
		String bizCode = requestInfo.getRequestHeader().getBizCode();
		String url = getUrl(bizCode);
		String systemName = getSystemName(bizCode);
		SOAPMessage response;
		try {
			response = this.callWebService(url, request,"ws.crm.query.timeout");
		} catch (Exception e) {
			String requestInfoString = BssServiceUtils.getSoapMessageString(request);
			ResponsetInfo responsetInfo = new ResponsetInfo();
			ResponsetHeader responsetHeader = new ResponsetHeader();
			responsetHeader.setResultCode(BssErrorCode.C99999);
			responsetHeader.setResultDesc(e.getLocalizedMessage());
			ResponseBody responseBody = new ResponseBody();
			responsetInfo.setResponsetHeader(responsetHeader);
			responsetInfo.setResponseBody(responseBody);
			String responsetInfoString = BssServiceUtils.getResposeInfoString(responsetInfo);
			long endTime = System.currentTimeMillis();
			String during = "" + (endTime - beginTime);
			Esblog esblog = BssServiceUtils.getEsbLog(requestInfo, requestInfoString, responsetInfo,
					responsetInfoString, during,systemName);
			CacheMgt.addEsblog(esblog);
			throw e;
		}
		// request.writeTo(System.out);
		// SOAPMessage response = testResponse();
		// response.writeTo(System.out);
		ProcessResult result = getResposeResult(response);
		long endTime = System.currentTimeMillis();
		String during = "" + (endTime - beginTime);
		String responseTime = UnieapConstants.getCurrentTime(null, null);
		requestInfo.getRequestHeader().setResponseTime(responseTime);
		this.esbLog(this, request, response,requestInfo,during, systemName);
		return result;

	}

	private SOAPMessage testResponse() throws Exception {
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
		SOAPMessage reqMsg = messageFactory.createMessage(new MimeHeaders(),
				new ByteArrayInputStream(request.toString().getBytes(Charset.forName("UTF-8"))));
		reqMsg.saveChanges();
		return reqMsg;
	}

	public SOAPMessage getRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo) throws Exception {
		SOAPMessage message = messageFactory.createMessage();
		RequestHeader requestHeader = getCRMRequestHeader(requestInfo);
		this.getCRMQueryHeader("GetCustomerRequest", message, requestHeader);

		Name requstHeaderName = message.getSOAPPart().getEnvelope().createName("GetCustomerRequest", "quer",
				"http://crm.huawei.com/query/");
		Iterator childElements = message.getSOAPBody().getChildElements(requstHeaderName);
		SOAPBodyElement bodyElement = (SOAPBodyElement) childElements.next();
		SOAPElement requestGetCustomerBodyElement = bodyElement.addChildElement("GetCustomerBody", "quer");
		requestGetCustomerBodyElement.addChildElement("ServiceNumber", "quer").addTextNode(serviceNumber);
		return message;
	}

	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		CustomerVO vo = new CustomerVO();
		if (document.getElementsByTagName("bas:RetCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("bas:RetCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("bas:RetMsg").getLength() > 0) {

			String retMsg = document.getElementsByTagName("bas:RetMsg").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		////////////////////
		if (document.getElementsByTagName("quer:FirstName").getLength() > 0) {

			String firstName = document.getElementsByTagName("quer:FirstName").item(0).getTextContent();
			vo.setFirstName(firstName);
		}
		if (document.getElementsByTagName("quer:LastName").getLength() > 0) {

			String lastName = document.getElementsByTagName("quer:LastName").item(0).getTextContent();
			vo.setLastName(lastName);
		}
		if (document.getElementsByTagName("quer:DateOfBirth").getLength() > 0) {

			String dateOfBirth = document.getElementsByTagName("quer:DateOfBirth").item(0).getTextContent();
			vo.setDateOfBirth(dateOfBirth);
		}
		if (document.getElementsByTagName("quer:Gender").getLength() > 0) {

			String gender = document.getElementsByTagName("quer:Gender").item(0).getTextContent();
			vo.setDateOfBirth(gender);
		}
		if (document.getElementsByTagName("quer:Title").getLength() > 0) {

			String title = document.getElementsByTagName("quer:Title").item(0).getTextContent();
			vo.setTitle(title);
		}
		if (document.getElementsByTagName("quer:CertificateType").getLength() > 0) {

			String certificateType = document.getElementsByTagName("quer:CertificateType").item(0).getTextContent();
			vo.setCertificateType(certificateType);
		}
		if (document.getElementsByTagName("quer:CertificateNumber").getLength() > 0) {

			String certificateNumber = document.getElementsByTagName("quer:CertificateNumber").item(0).getTextContent();
			vo.setCertificateNumber(certificateNumber);
		}

		String jsonCustomerInfo = JSONUtils.convertBean2JSON(vo).toString();
		result.setExtParameters(jsonCustomerInfo);
		return result;
	}

	@Override
	public RequestInfo getRequestInfoFromSOAPMessage(SOAPMessage request) throws Exception {
		return super.getRequestInfoFromSOAPMessage(request);
	}

	@Override
	public ResponsetInfo getResponseInfoFromSOAPMessage(SOAPMessage response) throws Exception {
		return super.getResponseInfoFromSOAPMessage(response);
	}
}
