package com.apps.esb.service.bss.customize.smart.app.handler.cbs;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
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
import com.apps.esb.service.bss.app.vo.querycdr.CdrInfoVO;
import com.apps.esb.service.bss.app.vo.querycdr.CdrSummaryVO;
import com.apps.esb.service.bss.app.vo.querycdr.QueryCdrVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.interfacecall.SoapCallUtils;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;

@Service("queryCDR")
public class QueryCDR implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler, Map<String, Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryCDR");
		
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if(!json.has("queryType")){
			throw new Exception("queryType is null");
		}
		String queryType = json.getString("queryType");
		if("D".equals(queryType)){
			return queryCDRDetail(newRequestInfo.getRequestBody().getServiceNumber(), newRequestInfo);
		}else if("S".equals(queryType)){
			
			return queryCDRSummary(newRequestInfo.getRequestBody().getServiceNumber(), newRequestInfo);
		}else{
			ProcessResult processResult = new ProcessResult();
			processResult.setResultCode(UnieapConstants.C1);
			processResult.setResultDesc(UnieapConstants.getMessage("99997"));
			return processResult;
		}
		

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
		SOAPMessage message = SoapCallUtils.messageFactory.createMessage();
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
		cale.add(Calendar.MONTH, -number + 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime()) + "235959";
		return new String[] { firstday, lastday };
	}

	public ProcessResult queryCDRSummary(String serviceNumber, RequestInfo requestInfo) throws Exception {
		SOAPMessage sOAPMessage1 = getRequestSOAPMessage(0, serviceNumber, requestInfo);
		ProcessResult processResult1 = process(sOAPMessage1, requestInfo,
				requestInfo.getRequestBody().getServiceNumber(), requestInfo.getRequestBody().getExtParameters(), null,
				"ws.cbs.query.timeout");
		QueryCdrVO queryCdrVO1 = (QueryCdrVO) processResult1.getVo();
		SOAPMessage sOAPMessage2 = getRequestSOAPMessage(1, serviceNumber, requestInfo);
		ProcessResult processResult2 = process(sOAPMessage2, requestInfo,
				requestInfo.getRequestBody().getServiceNumber(), requestInfo.getRequestBody().getExtParameters(), null,
				"ws.cbs.query.timeout");
		QueryCdrVO queryCdrVO2 = (QueryCdrVO) processResult2.getVo();
		
		SOAPMessage sOAPMessage3 = getRequestSOAPMessage(2, serviceNumber, requestInfo);
		ProcessResult processResult3 = process(sOAPMessage3, requestInfo,
				requestInfo.getRequestBody().getServiceNumber(), requestInfo.getRequestBody().getExtParameters(), null,
				"ws.cbs.query.timeout");
		QueryCdrVO queryCdrVO3 = (QueryCdrVO) processResult3.getVo();
		
		SOAPMessage sOAPMessage4 = getRequestSOAPMessage(3, serviceNumber, requestInfo);
		ProcessResult processResult4 = process(sOAPMessage4, requestInfo,
				requestInfo.getRequestBody().getServiceNumber(), requestInfo.getRequestBody().getExtParameters(), null,
				"ws.cbs.query.timeout");
		QueryCdrVO queryCdrVO4 = (QueryCdrVO) processResult4.getVo();
		
		SOAPMessage sOAPMessage5 = getRequestSOAPMessage(4, serviceNumber, requestInfo);
		ProcessResult processResult5 = process(sOAPMessage5, requestInfo,
				requestInfo.getRequestBody().getServiceNumber(), requestInfo.getRequestBody().getExtParameters(), null,
				"ws.cbs.query.timeout");
		QueryCdrVO queryCdrVO5 = (QueryCdrVO) processResult5.getVo();
		
		SOAPMessage sOAPMessage6 = getRequestSOAPMessage(5, serviceNumber, requestInfo);
		ProcessResult processResult6 = process(sOAPMessage6, requestInfo,
				requestInfo.getRequestBody().getServiceNumber(), requestInfo.getRequestBody().getExtParameters(), null,
				"ws.cbs.query.timeout");
		QueryCdrVO queryCdrVO6 = (QueryCdrVO) processResult6.getVo();
		
		QueryCdrVO queryCdrVO = new QueryCdrVO();
		List<CdrSummaryVO> cdrSummaryList = new ArrayList<CdrSummaryVO>();
		queryCdrVO.setCdrSummaryList(cdrSummaryList);
		queryCdrVO.getCdrSummaryList().addAll(queryCdrVO1.getCdrSummaryList());
		queryCdrVO.getCdrSummaryList().addAll(queryCdrVO2.getCdrSummaryList());
		queryCdrVO.getCdrSummaryList().addAll(queryCdrVO3.getCdrSummaryList());
		queryCdrVO.getCdrSummaryList().addAll(queryCdrVO4.getCdrSummaryList());
		queryCdrVO.getCdrSummaryList().addAll(queryCdrVO5.getCdrSummaryList());
		queryCdrVO.getCdrSummaryList().addAll(queryCdrVO6.getCdrSummaryList());
		ProcessResult processResult = new ProcessResult();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		processResult.setVo(queryCdrVO);
		return processResult;
	}
	
	public ProcessResult queryCDRDetail(String serviceNumber, RequestInfo requestInfo) throws Exception {
		JSONObject json = new JSONObject(requestInfo.getRequestBody().getExtParameters());
		if(!json.has("totalNum")){
			throw new Exception("totalNum is null");
		}
		if(!json.has("page")){
			throw new Exception("page is null");
		}
		String totalCDRNum = json.getString("totalNum");
		if(StringUtils.isEmpty(totalCDRNum)){
			throw new Exception("totalNum is null");
		}
		String pageNum = json.getString("page");
		if(StringUtils.isEmpty(pageNum)){
			throw new Exception("page is null");
		}
		
		SOAPMessage sOAPMessage = getRequestCDRDetailSOAPMessage(totalCDRNum,pageNum, serviceNumber, requestInfo);
		ProcessResult processResult = process(sOAPMessage, requestInfo,
				requestInfo.getRequestBody().getServiceNumber(), requestInfo.getRequestBody().getExtParameters(), null,
				"ws.cbs.query.timeout");
		return processResult;
	}
	
	
	public SOAPMessage getRequestCDRDetailSOAPMessage(String totalCDRNum,String pageNum, String serviceNumber, RequestInfo requestInfo)
			throws Exception {
		SOAPMessage message = SoapCallUtils.messageFactory.createMessage();
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryCDRRequest");
		SOAPElement subAccessCodeElement = reqestElement.addChildElement("SubAccessCode", "bbs");
		SOAPElement primaryIdentityElement = subAccessCodeElement.addChildElement("PrimaryIdentity", "bbc");
		primaryIdentityElement.addTextNode(serviceNumber);
		SOAPElement timePeriodElement = reqestElement.addChildElement("TimePeriod", "bbs");
		int pagesize = Integer.parseInt(SYSConfig.getConfig().get("mcare.display.cdr.pagesize"));
        int begin = Integer.parseInt(pageNum)*pagesize;
		
		Calendar cale = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String firstday, lastday;
		cale = Calendar.getInstance();
		cale.add(Calendar.DATE, -89);
		firstday = format.format(cale.getTime()) + "000000";
		
		cale = Calendar.getInstance();
		lastday = format.format(cale.getTime()) + "235959";
		
		timePeriodElement.addChildElement("StartTime", "bbs").addTextNode(firstday);
		timePeriodElement.addChildElement("EndTime", "bbs").addTextNode(lastday);
		reqestElement.addChildElement("TotalCDRNum", "bbs").addTextNode(totalCDRNum);
		reqestElement.addChildElement("BeginRowNum", "bbs").addTextNode(Integer.toString(begin));
		reqestElement.addChildElement("FetchRowNum", "bbs").addTextNode(Integer.toString(pagesize));
		return message;
	}

	public ProcessResult process(SOAPMessage request, RequestInfo requestInfo, String serviceNumber,
			String extParameters, String parameters, String timeoutName) throws Exception {
		long beginTime = System.currentTimeMillis();
		SOAPMessage response = SoapCallUtils.callService(serviceNumber, requestInfo, request, beginTime, timeoutName);
		ProcessResult processResult = getResposeResult(response);
		processResult.setServiceNumber(serviceNumber);
		String responseTime = UnieapConstants.getCurrentTime(null, null);
		requestInfo.getRequestHeader().setResponseTime(responseTime);
		String bizCode = requestInfo.getRequestHeader().getBizCode();
		String systemName = SoapCallUtils.getSystemName(bizCode);
		long endTime = System.currentTimeMillis();
		String during = "" + (endTime - beginTime);
		SoapCallUtils.esbLog(request, response, requestInfo.getRequestHeader(), processResult, during, systemName);
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

		if(document.getElementsByTagName("bbs:TotalCDRNum").getLength() > 0){
			queryCdrVO.setTotalCDRNum(document.getElementsByTagName("bbs:TotalCDRNum").item(0).getTextContent());
		}
		if(document.getElementsByTagName("bbs:BeginRowNum").getLength() > 0){
			queryCdrVO.setBeginRowNum(document.getElementsByTagName("bbs:BeginRowNum").item(0).getTextContent());
		}
		if(document.getElementsByTagName("bbs:FetchRowNum").getLength() > 0){
			queryCdrVO.setFetchRowNum(document.getElementsByTagName("bbs:FetchRowNum").item(0).getTextContent());
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
						if ("4".equals(cDRSummaryNode.getTextContent())) {
							cdrSummaryVO.setServiceType("999");
							cdrSummaryVO.setServiceTypeName(
									UnieapConstants.getDicName("serviceCategory", cdrSummaryVO.getServiceCategory()));
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
						if (totalChargeInfoNode != null) {
							cdrSummaryVO.setTotalChargeAmt(totalChargeInfoNode.getTextContent());
						}
					}
				}
			}
		}
		if (document.getElementsByTagName("bbs:CDRInfo").getLength() > 0) {
			NodeList nodes = document.getElementsByTagName("bbs:CDRInfo");
			for (int i = 0; i < nodes.getLength(); i++) {
				CdrInfoVO cdrInfoVO = new CdrInfoVO();
				cdrInfoList.add(cdrInfoVO);
				Node node = nodes.item(i);
				NodeList cDRInfoNodes = node.getChildNodes();
				for (int j = 0; j < cDRInfoNodes.getLength(); j++) {
					Node cDRInfoNode = cDRInfoNodes.item(j);
					if ("bbs:CdrSeq".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setCdrSeq(cDRInfoNode.getTextContent());
					}
					if ("bbs:ServiceCategory".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setServiceCategory(cDRInfoNode.getTextContent());
						if ("4".equals(cDRInfoNode.getTextContent())) {
							cdrInfoVO.setServiceType("999");
							cdrInfoVO.setServiceTypeName(
									UnieapConstants.getDicName("serviceCategory", cdrInfoVO.getServiceCategory()));
						}
					} else if ("bbs:SeriveType".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setServiceType(cDRInfoNode.getTextContent());
					} else if ("bbs:ServiceType".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setServiceType(cDRInfoNode.getTextContent());
					} else if ("bbs:ServiceTypeName".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setServiceTypeName(cDRInfoNode.getTextContent());
					} else if ("bbs:FlowType".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setFlowType(cDRInfoNode.getTextContent());
					}else if ("bbs:OtherNumber".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setOtherNumber(cDRInfoNode.getTextContent());
					} else if ("bbs:BillCycleID".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setBillCycleID(cDRInfoNode.getTextContent());
					} else if ("bbs:StartTime".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setStartTime(cDRInfoNode.getTextContent());
					} else if ("bbs:EndTime".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setEndTime(cDRInfoNode.getTextContent());
					} else if ("bbs:ChargeDuration".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setChargeDuration(cDRInfoNode.getTextContent());
					} else if ("bbs:DestinationNetworkName".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setDestinationNetworkName(cDRInfoNode.getTextContent());
					} else if ("bbs:TotalChargeInfo".equals(cDRInfoNode.getNodeName())) {
						NodeList totalChargeInfoNodes = cDRInfoNode.getChildNodes();
						for (int k = 0; k < totalChargeInfoNodes.getLength(); k++) {
							Node totalChargeInfoNode = totalChargeInfoNodes.item(k);
							if ("bbs:ActualChargeAmt".equals(totalChargeInfoNode.getNodeName())) {
								cdrInfoVO.setActualChargeAmt(totalChargeInfoNode.getTextContent());
							}
						}
					} else if ("bbs:VolumeInfo".equals(cDRInfoNode.getNodeName())) {
						NodeList volumeInfoNodes = cDRInfoNode.getChildNodes();
						for (int k = 0; k < volumeInfoNodes.getLength(); k++) {
							Node volumeInfoNode = volumeInfoNodes.item(k);
							if ("bbs:ActualVolume".equals(volumeInfoNode.getNodeName())) {
								cdrInfoVO.setActualVolume(volumeInfoNode.getTextContent());
							}else if("bbs:MeasureUnit".equals(volumeInfoNode.getNodeName())){
								cdrInfoVO.setMeasureUnit(volumeInfoNode.getTextContent());
							}
						}
					} else if ("bbs:MainBalanceDeduct".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setMainBalanceDeduct(cDRInfoNode.getTextContent());
					} else if ("bbs:MainBalaneLeft".equals(cDRInfoNode.getNodeName())) {
						cdrInfoVO.setMainBalaneLeft(cDRInfoNode.getTextContent());
					}
				}
			}
		}
		return result;
	}

}
