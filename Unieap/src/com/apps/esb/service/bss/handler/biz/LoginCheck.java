package com.apps.esb.service.bss.handler.biz;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.apps.esb.service.bss.app.vo.CustomerVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.tools.JSONUtils;

@Service("loginCheck")
public class LoginCheck extends SoapMessageHandler implements BizHandler{

	public LoginCheck() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String serviceNumber, String extParameters, String parameters)
			throws Exception {
		if(StringUtils.isEmpty(serviceNumber)){
			throw new Exception("serviceNumber is null");
		}
		return process(this, requestInfo, serviceNumber, extParameters, parameters,"ws.crm.query.timeout");
	}

	@Override
	public RequestInfo getRequestInfoFromSOAPMessage(SOAPMessage request) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ResponsetInfo getResponseInfoFromSOAPMessage(SOAPMessage response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SOAPMessage getRequestSOAPMessage(String serviceNumber, RequestInfo requestInfo) throws Exception {
		SOAPMessage message = messageFactory.createMessage();
		this.getCRMQuerHeader("CheckPasswordRequest", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement requestBodyElement = bodyElement.addChildElement("CheckPasswordBody");
		requestBodyElement.addChildElement("ServiceNumber").addTextNode(serviceNumber);
		SOAPElement passwordInfoElement = bodyElement.addChildElement("PassowrdInfo");
		// 1 customer service password
		// 2 subscriber service password
		// 3 subscriber web password
		// 4 subscriber IVR password
		// 5 corporate admin password
		// 6 dealer web password
		passwordInfoElement.addChildElement("PasswordType").addTextNode("2");
		// 1 change password
		// 2 reset password
		// 3 check password
		passwordInfoElement.addChildElement("PwdOperateType").addTextNode("3");
		if(StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())){
			throw new Exception("password is null");
		}
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if(json.has("password")){
			passwordInfoElement.addChildElement("Password").addTextNode(json.getString("password"));
		}else{
			throw new Exception("password is null");
		}
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("bas:RetCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("bas:RetCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("bas:RetMsg").getLength() > 0) {

			String retMsg = document.getElementsByTagName("bas:RetMsg").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		CustomerVO vo = new CustomerVO();
		vo.setActiveDate("2015-09-19 12:21:53");
		vo.setServiceNumber("15899785476");
		vo.setStatus("Acitve");
		vo.setSubscriberType("prepaid");
		vo.setAddress("深圳市南山区西丽镇女人世界");
		vo.setContactNo("15814497371");
		vo.setCorpName("中国移动电信有限公司");
		vo.setCorpNo("2233444");
		vo.setCustomerName("Chai");
		vo.setEmail("caiqiufu@sohu.com");
		String extParameters = JSONUtils.convertBean2JSON(vo).toString();
		result.setExtParameters(extParameters);
		return result;
	}

}
