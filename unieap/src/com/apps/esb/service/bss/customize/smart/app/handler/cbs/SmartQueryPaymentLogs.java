package com.apps.esb.service.bss.customize.smart.app.handler.cbs;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.account.payment.PaymentDetailVO;
import com.apps.esb.service.bss.app.vo.account.payment.PaymentVO;
import com.apps.esb.service.bss.app.vo.account.payment.QueryPaymentLogsVO;
import com.apps.esb.service.bss.app.vo.common.bank.BankVO;
import com.apps.esb.service.bss.customize.smart.app.handler.CustSoapMessageHandler;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;
import com.unieap.base.SYSConfig;

@Service("queryPaymentLogs_1")
public class SmartQueryPaymentLogs extends CustSoapMessageHandler implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryPaymentLogs");
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
		this.getCBSArsHeader("QueryPaymentLogRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryPaymentLogRequest");
		SOAPElement acctAccessCodeElement = reqestElement.addChildElement("AcctAccessCode", "ars");
		SOAPElement primaryIdentityElement = acctAccessCodeElement.addChildElement("PrimaryIdentity", "arc");
		primaryIdentityElement.addTextNode(serviceNumber);
		
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if (!json.has("totalNum")) {
			throw new Exception("totalNum is null");
		}
		if (!json.has("page")) {
			throw new Exception("page is null");
		}
		String totalRowNum = json.getString("totalNum");
		if (StringUtils.isEmpty(totalRowNum)) {
			throw new Exception("totalNum is null");
		}
		String pageNum = json.getString("page");
		if (StringUtils.isEmpty(pageNum)) {
			throw new Exception("page is null");
		}
		int pagesize = Integer.parseInt(SYSConfig.getConfig().get("mcare.display.cdr.pagesize"));
		int begin = Integer.parseInt(pageNum) * pagesize;
		
		reqestElement.addChildElement("TotalRowNum", "ars").addTextNode(totalRowNum);
		reqestElement.addChildElement("BeginRowNum", "ars").addTextNode(Integer.toString(begin));
		reqestElement.addChildElement("FetchRowNum", "ars").addTextNode(Integer.toString(pagesize));
		String[] times = getTimes(1);
		reqestElement.addChildElement("StartTime", "ars").addTextNode(times[0]);
		reqestElement.addChildElement("EndTime", "ars").addTextNode(times[1]);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		ProcessResult result = new ProcessResult();
		QueryPaymentLogsVO queryPaymentLogsVO = new QueryPaymentLogsVO();
		result.setVo(queryPaymentLogsVO);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		
		if (document.getElementsByTagName("bbs:TotalRowNum").getLength() > 0) {
			queryPaymentLogsVO.setTotalNum(document.getElementsByTagName("bbs:TotalRowNum").item(0).getTextContent());
		}
		if (document.getElementsByTagName("bbs:BeginRowNum").getLength() > 0) {
			queryPaymentLogsVO.setBeginNum(document.getElementsByTagName("bbs:BeginRowNum").item(0).getTextContent());
		}
		if (document.getElementsByTagName("bbs:FetchRowNum").getLength() > 0) {
			queryPaymentLogsVO.setFetchNum(document.getElementsByTagName("bbs:FetchRowNum").item(0).getTextContent());
		}
		
		if (document.getElementsByTagName("ars:PaymentInfo").getLength() > 0) {
			NodeList paymentInfoNodes = document.getElementsByTagName("ars:PaymentInfo");
			List<PaymentVO> paymentList = new ArrayList<PaymentVO>();
			queryPaymentLogsVO.setPaymentList(paymentList);
			for (int i = 0; i < paymentInfoNodes.getLength(); i++) {
				PaymentVO paymentVO = new PaymentVO();
				paymentList.add(paymentVO);
				Node paymentInfoNode = paymentInfoNodes.item(i);
				NodeList nodes = paymentInfoNode.getChildNodes();
				List<PaymentDetailVO> paymentDetailList = new ArrayList<PaymentDetailVO>();
				paymentVO.setPaymentDetailList(paymentDetailList);
				for (int j = 0; j < nodes.getLength(); j++) {
					Node node = nodes.item(j);
					if ("ars:CustKey".equals(node.getNodeName())) {
						paymentVO.setCustomerId(node.getTextContent());
					} else if ("ars:AcctKey".equals(node.getNodeName())) {
						paymentVO.setAccountId(node.getTextContent());
					} else if ("ars:SubKey".equals(node.getNodeName())) {
						paymentVO.setSubscriberId(node.getTextContent());
					} else if ("ars:PrimaryIdentity".equals(node.getNodeName())) {
						paymentVO.setServiceNumber(node.getTextContent());
					} else if ("ars:TransType".equals(node.getNodeName())) {
						paymentVO.setTransactionType(node.getTextContent());
					} else if ("ars:PaymentTime".equals(node.getNodeName())) {
						paymentVO.setPaymentTime(node.getTextContent());
					} else if ("ars:Amount".equals(node.getNodeName())) {
						paymentVO.setAmount(node.getTextContent());
					} else if ("ars:OriAmount".equals(node.getNodeName())) {
						paymentVO.setOriginalAmount(node.getTextContent());
					}else if ("ars:PaymentMethod".equals(node.getNodeName())) {
						paymentVO.setPaymentMethod(node.getTextContent());
					}else if ("ars:PayChannelId".equals(node.getNodeName())) {
						paymentVO.setPayChannelId(node.getTextContent());
					}else if ("ars:AccessMode".equals(node.getNodeName())) {
						paymentVO.setAccessMode(node.getTextContent());
					}else if ("ars:Status".equals(node.getNodeName())) {
						paymentVO.setStatus(node.getTextContent());
					}else if ("ars:OperID".equals(node.getNodeName())) {
						paymentVO.setOperId(node.getTextContent());
					}else if ("ars:deptID".equals(node.getNodeName())) {
						paymentVO.setDeptId(node.getTextContent());
					}else if ("ars:TotalPaymentAmount".equals(node.getNodeName())) {
						paymentVO.setTotalPaymentAmount(node.getTextContent());
					} else if ("ars:CurAmount".equals(node.getNodeName())) {
						paymentVO.setCurrentAmount(node.getTextContent());
					} else if ("ars:TaxAmount".equals(node.getNodeName())) {
						paymentVO.setTaxAmount(node.getTextContent());
					} else if ("ars:BankInfo".equals(node.getNodeName())) {
						NodeList bankInfoNodes = node.getChildNodes();
						BankVO bankVO = new BankVO();
						paymentVO.setBankVO(bankVO);
						for(int k = 0 ; k < bankInfoNodes.getLength() ;k++){
							Node bankInfoNode = bankInfoNodes.item(k);
							if ("ars:BankCode".equals(bankInfoNode.getNodeName())) {
								bankVO.setBankCode(bankInfoNode.getTextContent());
							} else if ("ars:BankBranchCode".equals(bankInfoNode.getNodeName())) {
								bankVO.setBankBranchCode(bankInfoNode.getTextContent());
							}else if ("ars:AcctType".equals(bankInfoNode.getNodeName())) {
								bankVO.setAcctType(bankInfoNode.getTextContent());
							}else if ("ars:AcctNo".equals(bankInfoNode.getNodeName())) {
								bankVO.setAcctNo(bankInfoNode.getTextContent());
							}else if ("ars:CreditCardType".equals(bankInfoNode.getNodeName())) {
								bankVO.setCreditCardType(bankInfoNode.getTextContent());
							}else if ("ars:AcctName".equals(bankInfoNode.getNodeName())) {
								bankVO.setAcctName(bankInfoNode.getTextContent());
							}else if ("ars:ExpDate".equals(bankInfoNode.getNodeName())) {
								bankVO.setExpDate(bankInfoNode.getTextContent());
							}else if ("ars:CvvNumber".equals(bankInfoNode.getNodeName())) {
								bankVO.setCvvNumber(bankInfoNode.getTextContent());
							}else if ("ars:CheckNo".equals(bankInfoNode.getNodeName())) {
								bankVO.setCheckNo(bankInfoNode.getTextContent());
							}
						}
					}    else if ("ars:PaymentDetail".equals(node.getNodeName())) {
						PaymentDetailVO paymentDetailVO = new PaymentDetailVO();
						paymentDetailList.add(paymentDetailVO);
						NodeList paymentDetailNodes = node.getChildNodes();
						for (int k = 0; k < paymentDetailNodes.getLength(); k++) {
							Node paymentDetailNode = paymentDetailNodes.item(k);
							if ("ars:ApplyType".equals(paymentDetailNode.getNodeName())) {
								paymentDetailVO.setApplyType(paymentDetailNode.getTextContent());
							} else if ("ars:ApplyAmount".equals(paymentDetailNode.getNodeName())) {
								paymentDetailVO.setApplyAmount(paymentDetailNode.getTextContent());
							} else if ("ars:InvoiceNo".equals(paymentDetailNode.getNodeName())) {
								paymentDetailVO.setInvoiceNo(paymentDetailNode.getTextContent());
							} else if ("ars:inoviceDetailID".equals(paymentDetailNode.getNodeName())) {
								paymentDetailVO.setInoviceDetailId(paymentDetailNode.getTextContent());
							} else if ("ars:ChargeCode".equals(paymentDetailNode.getNodeName())) {
								paymentDetailVO.setChargeCode(paymentDetailNode.getTextContent());
							} else if ("ars:ChargeAmount".equals(paymentDetailNode.getNodeName())) {
								paymentDetailVO.setChargeAmount(paymentDetailNode.getTextContent());
							}else if ("ars:DiscountAmount".equals(paymentDetailNode.getNodeName())) {
								paymentDetailVO.setDiscountAmount(paymentDetailNode.getTextContent());
							}
						}
					}
				}
			}
		}
		return result;
	}
}
