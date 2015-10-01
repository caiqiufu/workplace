package com.apps.esb.service.bss.handler.biz;

import java.util.Iterator;

import javax.xml.soap.Name;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;

@Service("recharge")
public class Recharge extends SoapMessageHandler implements BizHandler{

	public Recharge() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String serviceNumber, String extParameters, String parameters)
			throws Exception {
		if(StringUtils.isEmpty(serviceNumber)){
			throw new Exception("serviceNumber is null");
		}
		return process(this, requestInfo, serviceNumber, extParameters, parameters,"ws.cbs.query.timeout");
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
		this.getCBSArsHeader("RechargeRequestMsg", message);
		//Name requstHeaderName = message.getSOAPPart().getEnvelope().createName("RechargeRequestMsg","ars","http://www.huawei.com/bme/cbsinterface/arservices");
		//Iterator childElements = message.getSOAPBody().getChildElements(requstHeaderName);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement rechargeReqestElement = bodyElement.addChildElement("RechargeReqest");
		rechargeReqestElement.addChildElement("RechargeSerialNo").addTextNode(BssServiceUtils.generateTransactionId());
		SOAPElement rechargeObjElement = rechargeReqestElement.addChildElement("RechargeObj");
		SOAPElement subAccessCodeElement = rechargeObjElement.addChildElement("SubAccessCode");
		SOAPElement primaryIdentityElement = subAccessCodeElement.addChildElement("PrimaryIdentity");
		primaryIdentityElement.addTextNode(serviceNumber);
		
		SOAPElement rechargeInfoElement = rechargeReqestElement.addChildElement("RechargeInfo");
		String cardPinNumber = "";
		if(!StringUtils.isEmpty(requestInfo.getRequestBody().getExtParameters())){
			JSONObject extParametersJson = new JSONObject(requestInfo.getRequestBody().getExtParameters());
			if(extParametersJson.has("cardPinNumber")){
				 cardPinNumber = extParametersJson.getString("cardPinNumber");
			}else{
				throw new Exception("cardPinNumber is null");
			}
		}
		if(StringUtils.isEmpty(cardPinNumber)){
			throw new Exception("cardPinNumber is null");
		}
		SOAPElement cardPaymentElement = rechargeInfoElement.addChildElement("CardPayment");
		SOAPElement cardPinNumberElement = cardPaymentElement.addChildElement("CardPinNumber");
		cardPinNumberElement.addTextNode(cardPinNumber);
		//SOAPElement cardSequenceElement = cardPaymentElement.addChildElement("CardSequence");
		
		//SOAPElement cashPaymentElement = rechargeInfoElement.addChildElement("CashPayment");
		/**
		 * 1001 cash
		 * 2001 credit card
		 * 3001 check
		 */
		/*SOAPElement paymentMethodElement = cashPaymentElement.addChildElement("PaymentMethod");
		SOAPElement amountElement = cashPaymentElement.addChildElement("Amount");
		SOAPElement bankInfoElement = cashPaymentElement.addChildElement("BankInfo");
		SOAPElement bankCodeElement = bankInfoElement.addChildElement("BankCode");
		SOAPElement bankBranchCodeElement = bankInfoElement.addChildElement("BankBranchCode");
		SOAPElement acctTypeElement = bankInfoElement.addChildElement("AcctType");
		SOAPElement acctNoElement = bankInfoElement.addChildElement("AcctNo");*/
		
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		return result;
	}

}
