package com.apps.esb.service.bss.app.cbs.handler;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPMessage;

import org.springframework.stereotype.Service;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.cbs.vo.queryaggregationamount.AdjustmentVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaggregationamount.AggregationAmountVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaggregationamount.AggregationInfoVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaggregationamount.BillMediumChargeVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaggregationamount.DiscountVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaggregationamount.MinCommitmentChargeVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaggregationamount.NRecurringChargeVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaggregationamount.QueryAccumulateAmountVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaggregationamount.RecurringChargeVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaggregationamount.TaxVO;
import com.apps.esb.service.bss.app.cbs.vo.queryaggregationamount.UsageChargeVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.handler.SoapMessageHandler;
import com.unieap.UnieapConstants;

@Service("queryAggregationAmount")
public class QueryAggregationAmount extends SoapMessageHandler implements BizHandler {

	public QueryAggregationAmount() throws Exception {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public ProcessResult process(RequestInfo requestInfo,String parameters,Map<String,Object> extParameters)
			throws Exception {
		RequestInfo newRequestInfo = BssServiceUtils.copyRequestInfo(requestInfo);
		newRequestInfo.getRequestHeader().setBizCode("queryAggregationAmount");
		return process(this, newRequestInfo, newRequestInfo.getRequestBody().getServiceNumber(), newRequestInfo.getRequestBody().getExtParameters(), parameters, "ws.cbs.query.timeout");
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
		this.getCBSBbHeader("QueryAggregationAmountRequestMsg", message);
		SOAPBodyElement bodyElement = (SOAPBodyElement) message.getSOAPBody().getChildElements().next();
		SOAPElement reqestElement = bodyElement.addChildElement("QueryAggregationAmountRequest");
		SOAPElement objElement = reqestElement.addChildElement("SubAccessCode");
		SOAPElement subAccessCodeElement = objElement.addChildElement("SubAccessCode");
		SOAPElement primaryIdentityElement = subAccessCodeElement.addChildElement("PrimaryIdentity");
		primaryIdentityElement.addTextNode(serviceNumber);
		Date date = UnieapConstants.getDateTime(null);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String endTime = sdf.format(date);
		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.MONTH, -5);
		String billCycleID = new SimpleDateFormat("yyyy").format(rightNow.getTime());
		String startTime = new SimpleDateFormat("yyyyMM").format(rightNow.getTime()) + "000000";
		reqestElement.addChildElement("BillCycleID").addTextNode(billCycleID);
		SOAPElement timePeriodElement = reqestElement.addChildElement("TimePeriod");
		timePeriodElement.addChildElement("StartTime").addTextNode(startTime);
		timePeriodElement.addChildElement("EndTime").addTextNode(endTime);
		return message;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		QueryAccumulateAmountVO queryAccumulateAmountVO = new QueryAccumulateAmountVO();
		List<AggregationAmountVO> aggregationAmountList = new ArrayList<AggregationAmountVO>();
		queryAccumulateAmountVO.setAggregationAmountList(aggregationAmountList);
		ProcessResult result = new ProcessResult();
		result.setVo(queryAccumulateAmountVO);
		org.w3c.dom.Document document = response.getSOAPPart().getEnvelope().getOwnerDocument();
		if (document.getElementsByTagName("cbs:ResultCode").getLength() > 0) {

			String retCode = document.getElementsByTagName("cbs:ResultCode").item(0).getTextContent();
			result.setResultCode(retCode);
		}
		if (document.getElementsByTagName("cbs:ResultDesc").getLength() > 0) {

			String retMsg = document.getElementsByTagName("cbs:ResultDesc").item(0).getTextContent();
			result.setResultDesc(retMsg);
		}
		if (document.getElementsByTagName("bss:AggregationAmountList").getLength() > 0) {
			NodeList nodes = document.getElementsByTagName("bss:AggregationAmountList");
			for (int i = 0; i < nodes.getLength(); i++) {
				Node node = nodes.item(i);
				NodeList aggregationAmountListNodes = node.getChildNodes();
				AggregationAmountVO aggregationAmountVO = new AggregationAmountVO();
				aggregationAmountList.add(aggregationAmountVO);
				List<AggregationInfoVO> aggregationInfoList = new ArrayList<AggregationInfoVO>();
				for (int j = 0; j < aggregationAmountListNodes.getLength(); j++) {
					Node aggregationAmountListNode = aggregationAmountListNodes.item(j);
					if ("bbs:BillChargeID".equals(aggregationAmountListNode.getNodeName())) {
						aggregationAmountVO.setBillChargeID(aggregationAmountListNode.getTextContent());
					} else if ("bbs:BillCycleID".equals(aggregationAmountListNode.getNodeName())) {
						aggregationAmountVO.setBillCycleID(aggregationAmountListNode.getTextContent());
					} else if ("bbs:CustKey".equals(aggregationAmountListNode.getNodeName())) {
						aggregationAmountVO.setCustKey(aggregationAmountListNode.getTextContent());
					} else if ("bbs:AcctKey".equals(aggregationAmountListNode.getNodeName())) {
						aggregationAmountVO.setAcctKey(aggregationAmountListNode.getTextContent());
					} else if ("bbs:ObjType".equals(aggregationAmountListNode.getNodeName())) {
						aggregationAmountVO.setObjType(aggregationAmountListNode.getTextContent());
					} else if ("bbs:ObjKey".equals(aggregationAmountListNode.getNodeName())) {
						aggregationAmountVO.setObjKey(aggregationAmountListNode.getTextContent());
					} else if ("bbs:PrimaryIdentify".equals(aggregationAmountListNode.getNodeName())) {
						aggregationAmountVO.setPrimaryIdentify(aggregationAmountListNode.getTextContent());
					} else if ("bbs:Category".equals(aggregationAmountListNode.getNodeName())) {
						aggregationAmountVO.setCategory(aggregationAmountListNode.getTextContent());
					} else if ("bbs:AggregationInfo".equals(aggregationAmountListNode.getNodeName())) {
						Node aggregationInfoNode = aggregationAmountListNode.getChildNodes().item(0);
						AggregationInfoVO aggregationInfoVO = new AggregationInfoVO();
						aggregationInfoList.add(aggregationInfoVO);
						if ("bbs:RecurringCharge".equals(aggregationInfoNode.getNodeName())) {
							RecurringChargeVO recurringChargeVO = new RecurringChargeVO();
							aggregationInfoVO.setRecurringCharge(recurringChargeVO);
							NodeList recurringChargeNodes = aggregationInfoNode.getChildNodes();
							for (int k = 0; k < recurringChargeNodes.getLength(); k++) {
								Node recurringChargeNode = recurringChargeNodes.item(k);
								if ("bbs:OffeirngID".equals(recurringChargeNode.getNodeName())) {
									recurringChargeVO.setOffeirngID(recurringChargeNode.getTextContent());
								} else if ("bbs:ChargeAmount".equals(recurringChargeNode.getNodeName())) {
									recurringChargeVO.setChargeAmount(recurringChargeNode.getTextContent());
								}
							}
						} else if ("bbs:NRecurringCharge".equals(aggregationInfoNode.getNodeName())) {
							NRecurringChargeVO nRecurringChargeVO = new NRecurringChargeVO();
							aggregationInfoVO.setnRecurringCharge(nRecurringChargeVO);
							NodeList nRecurringChargeVONodes = aggregationInfoNode.getChildNodes();
							for (int k = 0; k < nRecurringChargeVONodes.getLength(); k++) {
								Node nRecurringChargeVONode = nRecurringChargeVONodes.item(k);
								if ("bbs:SubCategory".equals(nRecurringChargeVONode.getNodeName())) {
									nRecurringChargeVO.setSubCategory(nRecurringChargeVONode.getTextContent());
								} else if ("bbs:ChargeCode".equals(nRecurringChargeVONode.getNodeName())) {
									nRecurringChargeVO.setChargeCode(nRecurringChargeVONode.getTextContent());
								} else if ("bbs:ChargeTime".equals(nRecurringChargeVONode.getNodeName())) {
									nRecurringChargeVO.setChargeTime(nRecurringChargeVONode.getTextContent());
								} else if ("bbs:ChargeAmount".equals(nRecurringChargeVONode.getNodeName())) {
									nRecurringChargeVO.setChargeAmount(nRecurringChargeVONode.getTextContent());
								}
							}
						} else if ("bbs:UsageCharge".equals(aggregationInfoNode.getNodeName())) {
							UsageChargeVO usageChargeVO = new UsageChargeVO();
							aggregationInfoVO.setUsageCharge(usageChargeVO);
							NodeList usageChargeVONodes = aggregationInfoNode.getChildNodes();
							for (int k = 0; k < usageChargeVONodes.getLength(); k++) {
								Node usageChargeVONode = usageChargeVONodes.item(k);
								if ("bbs:SubCategory".equals(usageChargeVONode.getNodeName())) {
									usageChargeVO.setSubCategory(usageChargeVONode.getTextContent());
								} else if ("bbs:ChargeCode".equals(usageChargeVONode.getNodeName())) {
									usageChargeVO.setChargeCode(usageChargeVONode.getTextContent());
								} else if ("bbs:ChargeAmount".equals(usageChargeVONode.getNodeName())) {
									usageChargeVO.setChargeAmount(usageChargeVONode.getTextContent());
								} else if ("bbs:ActualVolume".equals(usageChargeVONode.getNodeName())) {
									usageChargeVO.setActualVolume(usageChargeVONode.getTextContent());
								} else if ("bbs:RatingDiscVolume".equals(usageChargeVONode.getNodeName())) {
									usageChargeVO.setRatingDiscVolume(usageChargeVONode.getTextContent());
								} else if ("bbs:FreeVolume".equals(usageChargeVONode.getNodeName())) {
									usageChargeVO.setFreeVolume(usageChargeVONode.getTextContent());
								} else if ("bbs:RatingVolume".equals(usageChargeVONode.getNodeName())) {
									usageChargeVO.setRatingVolume(usageChargeVONode.getTextContent());
								} else if ("bbs:MeasureUnit".equals(usageChargeVONode.getNodeName())) {
									usageChargeVO.setMeasureUnit(usageChargeVONode.getTextContent());
								}
							}
						} else if ("bbs:Adjustment".equals(aggregationInfoNode.getNodeName())) {
							AdjustmentVO adjustmentVO = new AdjustmentVO();
							aggregationInfoVO.setAdjustment(adjustmentVO);
							NodeList adjustmentVONodes = aggregationInfoNode.getChildNodes();
							for (int k = 0; k < adjustmentVONodes.getLength(); k++) {
								Node adjustmentVONode = adjustmentVONodes.item(k);
								if ("bbs:AdjustAmount".equals(adjustmentVONode.getNodeName())) {
									adjustmentVO.setAdjustAmount(adjustmentVONode.getTextContent());
								}
							}
						} else if ("bbs:BillMediumCharge".equals(aggregationInfoNode.getNodeName())) {
							BillMediumChargeVO billMediumChargeVO = new BillMediumChargeVO();
							aggregationInfoVO.setBillMediumCharge(billMediumChargeVO);
							NodeList billMediumChargeVONodes = aggregationInfoNode.getChildNodes();
							for (int k = 0; k < billMediumChargeVONodes.getLength(); k++) {
								Node billMediumChargeVONode = billMediumChargeVONodes.item(k);
								if ("bbs:SubCategory".equals(billMediumChargeVONode.getNodeName())) {
									billMediumChargeVO.setSubCategory(billMediumChargeVONode.getTextContent());
								} else if ("bbs:OfferingID".equals(billMediumChargeVONode.getNodeName())) {
									billMediumChargeVO.setOfferingID(billMediumChargeVONode.getTextContent());
								} else if ("bbs:ChargeAmount".equals(billMediumChargeVONode.getNodeName())) {
									billMediumChargeVO.setChargeAmount(billMediumChargeVONode.getTextContent());
								}
							}
						} else if ("bbs:MinCommitmentCharge".equals(aggregationInfoNode.getNodeName())) {
							MinCommitmentChargeVO minCommitmentChargeVO = new MinCommitmentChargeVO();
							aggregationInfoVO.setMinCommitmentCharge(minCommitmentChargeVO);
							NodeList minCommitmentChargeVONodes = aggregationInfoNode.getChildNodes();
							for (int k = 0; k < minCommitmentChargeVONodes.getLength(); k++) {
								Node minCommitmentChargeVONode = minCommitmentChargeVONodes.item(k);
								if ("bbs:OfferingID".equals(minCommitmentChargeVONode.getNodeName())) {
									minCommitmentChargeVO.setOfferingID(minCommitmentChargeVONode.getTextContent());
								} else if ("bbs:ChargeAmount".equals(minCommitmentChargeVONode.getNodeName())) {
									minCommitmentChargeVO.setChargeAmount(minCommitmentChargeVONode.getTextContent());
								}
							}
						} else if ("bbs:Discount".equals(aggregationInfoNode.getNodeName())) {
							DiscountVO discountVO = new DiscountVO();
							aggregationInfoVO.setDiscount(discountVO);
							NodeList discountVOVONodes = aggregationInfoNode.getChildNodes();
							for (int k = 0; k < discountVOVONodes.getLength(); k++) {
								Node discountVOVONode = discountVOVONodes.item(k);
								if ("bbs:OfferingID".equals(discountVOVONode.getNodeName())) {
									discountVO.setOfferingID(discountVOVONode.getTextContent());
								} else if ("bbs:ChargeAmount".equals(discountVOVONode.getNodeName())) {
									discountVO.setChargeAmount(discountVOVONode.getTextContent());
								}
							}
						} else if ("bbs:Tax".equals(aggregationInfoNode.getNodeName())) {
							TaxVO taxVO = new TaxVO();
							aggregationInfoVO.setTax(taxVO);
							NodeList taxVONodes = aggregationInfoNode.getChildNodes();
							for (int k = 0; k < taxVONodes.getLength(); k++) {
								Node taxVONode = taxVONodes.item(k);
								if ("bbs:TaxCode".equals(taxVONode.getNodeName())) {
									taxVO.setTaxCode(taxVONode.getTextContent());
								} else if ("bbs:TaxAmount".equals(taxVONode.getNodeName())) {
									taxVO.setTaxAmount(taxVONode.getTextContent());
								}
							}
						}
					}
				}
			}
		}

		return result;
	}

}
