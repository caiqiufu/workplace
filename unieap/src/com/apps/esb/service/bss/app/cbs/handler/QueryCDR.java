package com.apps.esb.service.bss.app.cbs.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.cbs.vo.queryCdr.CdrInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.queryCdr.CdrSummaryVO;
import com.apps.esb.service.bss.app.cbs.vo.queryCdr.QueryCdrVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;

@Service("queryCDR")
public class QueryCDR extends SoapMessageHandler implements BizHandler {

	public QueryCDR() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo, String parameters, Map<String, Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryCDR");
		ProcessResult processResult = new ProcessResult();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		QueryCdrVO queryCdrVO = queryCDRSummary(newRequestInfo.getRequestBody().getServiceNumber(),newRequestInfo);
		processResult.setVo(queryCdrVO);
		return  processResult;

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
		return null;
	}

	public SOAPMessage getRequestSOAPMessage(int number, String serviceNumber, RequestInfo requestInfo)
			throws Exception {
		SOAPMessage message = messageFactory.createMessage();
		this.getCBSBbHeader("QueryCDRRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryCDRRequest");
		SOAPElement subAccessCodeElement = reqestElement.addChildElement("SubAccessCode", "bbs");
		SOAPElement primaryIdentityElement = subAccessCodeElement.addChildElement("PrimaryIdentity", "bbc");
		primaryIdentityElement.addTextNode(serviceNumber);
		SOAPElement timePeriodElement = reqestElement.addChildElement("TimePeriod", "bbs");
		// int during =
		// Integer.parseInt(SYSConfig.getConfig().get("cbs.query.cdr.during"));
		String[] times = getTimes(number);
		timePeriodElement.addChildElement("StartTime", "bbs").addTextNode(times[0]);
		timePeriodElement.addChildElement("EndTime", "bbs").addTextNode(times[1]);
		reqestElement.addChildElement("TotalCDRNum", "bbs").addTextNode("0");
		reqestElement.addChildElement("BeginRowNum", "bbs").addTextNode("0");
		// reqestElement.addChildElement("FetchRowNum","bbs").addTextNode(SYSConfig.getConfig().get("cbs.query.cdr.fetchrownum"));
		reqestElement.addChildElement("FetchRowNum", "bbs").addTextNode("0");
		return message;
	}

	public String[] getTimes(int number) {
		Calendar cale = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String firstday, lastday;
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, -number);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime()) + "000000";

		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, -number + 2);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime()) + "235959";
		return new String[] { firstday, lastday };
	}

	public QueryCdrVO queryCDRSummary(String serviceNumber, RequestInfo requestInfo)
			throws Exception {
		SOAPMessage sOAPMessage1 = getRequestSOAPMessage(1, serviceNumber, requestInfo);
		ProcessResult processResult1 = process(sOAPMessage1, requestInfo,
				requestInfo.getRequestBody().getServiceNumber(), requestInfo.getRequestBody().getExtParameters(), null,
				"ws.cbs.query.timeout");
		QueryCdrVO queryCdrVO1 = (QueryCdrVO) processResult1.getVo();
		SOAPMessage sOAPMessage2 = getRequestSOAPMessage(3, serviceNumber, requestInfo);
		ProcessResult processResult2 = process(sOAPMessage2, requestInfo,
				requestInfo.getRequestBody().getServiceNumber(), requestInfo.getRequestBody().getExtParameters(), null,
				"ws.cbs.query.timeout");
		QueryCdrVO queryCdrVO2 = (QueryCdrVO) processResult2.getVo();
		SOAPMessage sOAPMessage3 = getRequestSOAPMessage(5, serviceNumber, requestInfo);
		ProcessResult processResult3 = process(sOAPMessage3, requestInfo,
				requestInfo.getRequestBody().getServiceNumber(), requestInfo.getRequestBody().getExtParameters(), null,
				"ws.cbs.query.timeout");
		QueryCdrVO queryCdrVO3 = (QueryCdrVO) processResult3.getVo();
		QueryCdrVO queryCdrVO = new QueryCdrVO();
		List<CdrSummaryVO> cdrSummaryList = new ArrayList<CdrSummaryVO>();
		queryCdrVO.setCdrSummaryList(cdrSummaryList);
		queryCdrVO.getCdrSummaryList().addAll(queryCdrVO1.getCdrSummaryList());
		queryCdrVO.getCdrSummaryList().addAll(queryCdrVO2.getCdrSummaryList());
		queryCdrVO.getCdrSummaryList().addAll(queryCdrVO3.getCdrSummaryList());
		return queryCdrVO;
	}

	public ProcessResult process(SOAPMessage request, RequestInfo requestInfo, String serviceNumber,
			String extParameters, String parameters, String timeoutName) throws Exception {
		long beginTime = System.currentTimeMillis();
		SOAPMessage response = callService(serviceNumber, requestInfo, request, beginTime, timeoutName);
		ProcessResult processResult = getResposeResult(response);
		processResult.setServiceNumber(serviceNumber);
		exchangeBillingErrorCodeMapping(processResult);
		String responseTime = UnieapConstants.getCurrentTime(null, null);
		requestInfo.getRequestHeader().setResponseTime(responseTime);
		String bizCode = requestInfo.getRequestHeader().getBizCode();
		String systemName = getSystemName(bizCode);
		long endTime = System.currentTimeMillis();
		String during = "" + (endTime - beginTime);
		this.esbLog(request, response, requestInfo.getRequestHeader(), processResult, during, systemName);
		return processResult;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		QueryCdrVO queryCdrVO = new QueryCdrVO();
		List<CdrSummaryVO> cdrSummaryList = new ArrayList<CdrSummaryVO>();
		queryCdrVO.setCdrSummaryList(cdrSummaryList);
		List<CdrInfoVO> cdrInfoList = new ArrayList<CdrInfoVO>();
		queryCdrVO.setCdrInfoList(cdrInfoList);
		ProcessResult result = new ProcessResult();
		result.setVo(queryCdrVO);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}

		if (document.getElementsByTagName("bbs:CDRSummary").getLength() > 0) {
			NodeList nodes = document.getElementsByTagName("bbs:CDRSummary");
			for (int i = 0; i < nodes.getLength(); i++) {
				CdrSummaryVO cdrSummaryVO = new CdrSummaryVO();
				cdrSummaryList.add(cdrSummaryVO);
				Node node = nodes.item(i);
				NodeList cDRSummaryNodes = node.getChildNodes();
				for (int j = 0; j < cDRSummaryNodes.getLength(); j++) {
					Node cDRSummaryNode = cDRSummaryNodes.item(j);
					if ("bbs:ServiceCategory".equals(cDRSummaryNode.getNodeName())) {
						cdrSummaryVO.setServiceCategory(cDRSummaryNode.getTextContent());
						if("4".equals(cDRSummaryNode.getTextContent())){
							cdrSummaryVO.setServiceType("999");
							cdrSummaryVO.setServiceTypeName(UnieapConstants.getDicName("serviceCategory", cdrSummaryVO.getServiceCategory()));
						}
					} else if ("bbs:SeriveType".equals(cDRSummaryNode.getNodeName())) {
						cdrSummaryVO.setServiceType(cDRSummaryNode.getTextContent());
					} else if ("bbs:ServiceType".equals(cDRSummaryNode.getNodeName())) {
						cdrSummaryVO.setServiceType(cDRSummaryNode.getTextContent());
					} else if ("bbs:ServiceTypeName".equals(cDRSummaryNode.getNodeName())) {
						cdrSummaryVO.setServiceTypeName(cDRSummaryNode.getTextContent());
					} else if ("bbs:BillCycleID".equals(cDRSummaryNode.getNodeName())) {
						cdrSummaryVO.setBillCycleID(cDRSummaryNode.getTextContent());
					} else if ("bbs:TotalChargeInfo".equals(cDRSummaryNode.getNodeName())) {
						Node totalChargeInfoNode = cDRSummaryNode.getFirstChild();
						if(totalChargeInfoNode!=null){
							cdrSummaryVO.setTotalChargeAmt(totalChargeInfoNode.getTextContent());
						}
					}
				}
			}
		}

		return result;
	}

}
