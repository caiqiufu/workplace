package com.apps.esb.service.bss.customize.smart.app.handler.cbs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.account.invoice.InvoiceDetailVO;
import com.apps.esb.service.bss.app.vo.account.invoice.InvoiceVO;
import com.apps.esb.service.bss.app.vo.account.invoice.QueryInvoiceVO;
import com.apps.esb.service.bss.customize.smart.app.handler.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;

@Service("queryInvoice_1")
public class SmartQueryInvoice extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryInvoice");
		return SoapCallUtils.process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(),
				newRequestInfo.getRequestBody().getExtParameters(), handler, "ws.cbs.query.timeout");
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
		SOAPMessage message = SoapCallUtils.getMessageFactory().createMessage();
		this.getCBSArsHeader("QueryInvoiceRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryInvoiceRequest");
		SOAPElement objElement = reqestElement.addChildElement("AcctAccessCode", "arc");
		SOAPElement primaryIdentityElement = objElement.addChildElement("PrimaryIdentity", "arc");
		primaryIdentityElement.addTextNode(serviceNumber);
		SOAPElement timePeriodElement = reqestElement.addChildElement("TimePeriod", "ars");
		String[] times = getTimes(1);
		timePeriodElement.addChildElement("StartTime", "ars").addTextNode(times[0]);
		timePeriodElement.addChildElement("EndTime", "ars").addTextNode(times[1]);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		QueryInvoiceVO queryInvoiceVO = new QueryInvoiceVO();
		result.setVo(queryInvoiceVO);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("ars:InvoiceInfo").getLength() > 0) {
			NodeList invoiceInfoNodes = document.getElementsByTagName("ars:InvoiceInfo");
			List<InvoiceVO> invoiceList = new ArrayList<InvoiceVO>();
			queryInvoiceVO.setInvoiceList(invoiceList);
			for (int i = 0; i < invoiceInfoNodes.getLength(); i++) {
				InvoiceVO invoiceVO = new InvoiceVO();
				invoiceList.add(invoiceVO);
				Node invoiceInfoNode = invoiceInfoNodes.item(i);
				NodeList nodes = invoiceInfoNode.getChildNodes();
				List<InvoiceDetailVO> invoiceDetailList = new ArrayList<InvoiceDetailVO>();
				invoiceVO.setInvoiceDetailList(invoiceDetailList);
				for (int j = 0; j < nodes.getLength(); j++) {
					Node node = nodes.item(j);
					if ("ars:CustKey".equals(node.getNodeName())) {
						invoiceVO.setCustomerId(node.getTextContent());
					} else if ("ars:AcctKey".equals(node.getNodeName())) {
						invoiceVO.setAccountId(node.getTextContent());
					} else if ("ars:SubKey".equals(node.getNodeName())) {
						invoiceVO.setSubscriberId(node.getTextContent());
					} else if ("ars:PrimaryIdentity".equals(node.getNodeName())) {
						invoiceVO.setServiceNumber(node.getTextContent());
					} else if ("ars:TransType".equals(node.getNodeName())) {
						invoiceVO.setTransactionType(node.getTextContent());
					} else if ("ars:InvoiceID".equals(node.getNodeName())) {
						invoiceVO.setInvoiceId(node.getTextContent());
					} else if ("ars:invoiceNo".equals(node.getNodeName())) {
						invoiceVO.setInvoiceNo(node.getTextContent());
					} else if ("ars:BillCycleID".equals(node.getNodeName())) {
						invoiceVO.setBillCycleId(node.getTextContent());
					} else if ("ars:BillCycleBeginTime".equals(node.getNodeName())) {
						invoiceVO.setBillCycleBeginTime(node.getTextContent());
					} else if ("ars:BillCycleEndTime".equals(node.getNodeName())) {
						invoiceVO.setBillCycleEndTime(node.getTextContent());
					} else if ("ars:InvoiceAmount".equals(node.getNodeName())) {
						invoiceVO.setInvoiceAmount(node.getTextContent());
					} else if ("ars:OpenAmount".equals(node.getNodeName())) {
						invoiceVO.setOpenAmount(node.getTextContent());
					} else if ("ars:DisputeAmount".equals(node.getNodeName())) {
						invoiceVO.setDisputeAmount(node.getTextContent());
					} else if ("ars:InvoiceDate".equals(node.getNodeName())) {
						invoiceVO.setInvoiceDate(node.getTextContent());
					} else if ("ars:DueDate".equals(node.getNodeName())) {
						invoiceVO.setDueDate(node.getTextContent());
					} else if ("ars:SettleDate".equals(node.getNodeName())) {
						invoiceVO.setSettleDate(node.getTextContent());
					} else if ("ars:Status".equals(node.getNodeName())) {
						invoiceVO.setStatus(node.getTextContent());
					} else if ("ars:InvoiceDetail".equals(node.getNodeName())) {
						InvoiceDetailVO invoiceDetailVO = new InvoiceDetailVO();
						invoiceDetailList.add(invoiceDetailVO);
						NodeList invoiceDetailNodes = node.getChildNodes();
						for (int k = 0; k < invoiceDetailNodes.getLength(); k++) {
							Node invoiceDetailNode = invoiceDetailNodes.item(k);
							if ("ars:InvoiceId".equals(invoiceDetailNode.getNodeName())) {
								invoiceDetailVO.setInvoiceId(invoiceDetailNode.getTextContent());
							} else if ("ars:ServiceCategory".equals(invoiceDetailNode.getNodeName())) {
								invoiceDetailVO.setServiceCategory(invoiceDetailNode.getTextContent());
							} else if ("ars:ChargeCode".equals(invoiceDetailNode.getNodeName())) {
								invoiceDetailVO.setChargeCode(invoiceDetailNode.getTextContent());
							} else if ("ars:OpenAmount".equals(invoiceDetailNode.getNodeName())) {
								invoiceDetailVO.setOpenAmount(invoiceDetailNode.getTextContent());
							} else if ("ars:DisputeAmount".equals(invoiceDetailNode.getNodeName())) {
								invoiceDetailVO.setDisputeAmount(invoiceDetailNode.getTextContent());
							} else if ("ars:Status".equals(invoiceDetailNode.getNodeName())) {
								invoiceDetailVO.setStatus(invoiceDetailNode.getTextContent());
							}
						}
					}
				}
			}
		}
		return result;
	}
}
