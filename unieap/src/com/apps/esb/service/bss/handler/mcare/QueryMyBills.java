package com.apps.esb.service.bss.handler.mcare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.BssServiceUtils;
import com.apps.esb.service.bss.app.vo.subscriber.cdr.CdrSummaryVO;
import com.apps.esb.service.bss.app.vo.subscriber.cdr.QueryCdrVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.macre.cdr.CdrCategoryVO;
import com.apps.esb.service.bss.vo.macre.cdr.CdrCycleSummaryVO;
import com.apps.esb.service.bss.vo.macre.mybill.MontlyUsageVO;
import com.apps.esb.service.bss.vo.macre.mybill.MyBillVO;
import com.apps.esb.service.bss.vo.macre.mybill.ServiceCategoryVO;
import com.apps.esb.service.bss.vo.macre.mybill.ServiceUsageVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("queryMyBills")
public class QueryMyBills implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo,Map<String, String> handler, Map<String, Object> extParameters)
			throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		JSONObject customerObj = new JSONObject("{queryType:S}");
		JSONObject newExtParameters = BssServiceUtils
				.modifyExtParameters(requestInfo.getRequestBody().getExtParameters(), customerObj);
		requestInfo.getRequestBody().setExtParameters(newExtParameters.toString());

		BizHandler appHandler = (BizHandler) ServiceUtils.getBeanByTenantId(handler.get("appHandlerName"));
		ProcessResult processResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}
		QueryCdrVO queryCdrVO = (QueryCdrVO) processResult.getVo();
		MyBillVO getMyBillVO = getMyBillVO(queryCdrVO);
		List<MontlyUsageVO> mothBills = getMyBillVO.getMontlyUsages();
		// Collections.reverse(mothBills);
		Collections.sort(mothBills);
		if(handler!=null){
			String custHandlerName = handler.get("custHandlerName");
			if(!StringUtils.isEmpty(custHandlerName)){
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder)ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(queryCdrVO);
				custHandler.process(requestInfo, handler, extParameters, getMyBillVO,originalVOs);
			}
		}
		JSONArray logsJson = JSONUtils.getJSONArray(mothBills);
		JSONObject jsonResult = new JSONObject();
		jsonResult.put("montlyUsages", logsJson);
		processResult.setExtParameters(jsonResult.toString());
		return processResult;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ProcessResult getResposeResult(SOAPMessage response) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	public MyBillVO getMyBillVO(QueryCdrVO queryCdrVO) {
		MyBillVO myBillVO = new MyBillVO();
		List<MontlyUsageVO> montlyUsages = new ArrayList<MontlyUsageVO>();
		myBillVO.setMontlyUsages(montlyUsages);
		List<CdrSummaryVO> cdrSummaryList = queryCdrVO.getCdrSummaryList();
		Map<String, CdrCycleSummaryVO> cdrCycleSummarys = getBillCycleData(cdrSummaryList);
		Iterator<?> iter = cdrCycleSummarys.entrySet().iterator();
		while (iter.hasNext()) {
			MontlyUsageVO montlyUsageVO = new MontlyUsageVO();
			montlyUsages.add(montlyUsageVO);
			List<ServiceCategoryVO> serviceCategorys = new ArrayList<ServiceCategoryVO>();
			montlyUsageVO.setServiceCategorys(serviceCategorys);
			Map.Entry entry = (Map.Entry) iter.next();
			String billCycleID = (String) entry.getKey();
			CdrCycleSummaryVO cdrCycleSummary = cdrCycleSummarys.get(billCycleID);
			montlyUsageVO.setIndex(billCycleID);
			montlyUsageVO.setUsageAmount(BssServiceUtils.moneyFormat(cdrCycleSummary.getTotalChargeAmt()).toString());
			montlyUsageVO.setBillCycleID(billCycleID);
			Map<String, CdrCategoryVO> categorySummarys = cdrCycleSummary.getCategoryCdrSummarys();
			Iterator<?> categoryIter = categorySummarys.entrySet().iterator();
			int j = 0;
			while (categoryIter.hasNext()) {
				ServiceCategoryVO serviceCategoryVO = new ServiceCategoryVO();
				serviceCategorys.add(serviceCategoryVO);
				List<ServiceUsageVO> serviceUsages = new ArrayList<ServiceUsageVO>();
				serviceCategoryVO.setServiceUsages(serviceUsages);
				Map.Entry categoryEntry = (Map.Entry) categoryIter.next();
				String serviceCategory = (String) categoryEntry.getKey();
				CdrCategoryVO cdrCategoryVO = categorySummarys.get(serviceCategory);
				serviceCategoryVO.setCategoryType(cdrCategoryVO.getCategoryType());
				serviceCategoryVO.setCategoryTypeDesc(cdrCategoryVO.getCategoryTypeDesc());
				serviceCategoryVO.setUsageAmount(BssServiceUtils.moneyFormat(cdrCategoryVO.getUsageAmount()));
				serviceCategoryVO.setIndex(j);
				j++;
				List<CdrSummaryVO> summaryList = cdrCategoryVO.getCdrSummaryList();
				if (summaryList != null && summaryList.size() > 0) {
					for (int i = 0; i < summaryList.size(); i++) {
						CdrSummaryVO cdrSummaryVO = summaryList.get(i);
						ServiceUsageVO serviceUsageVO = changeVo(cdrSummaryVO);
						serviceUsageVO.setIndex(Integer.toString(i));
						serviceUsages.add(serviceUsageVO);
					}
				}

			}
		}

		return myBillVO;
	}

	public Map<String, CdrCycleSummaryVO> getBillCycleData(List<CdrSummaryVO> cdrSummaryList) {
		Map<String, CdrCycleSummaryVO> cdrCycleSummarys = new HashMap<String, CdrCycleSummaryVO>();
		if (cdrSummaryList != null && cdrSummaryList.size() > 0) {
			for (int i = 0; i < cdrSummaryList.size(); i++) {
				CdrSummaryVO cdrSummaryVO = cdrSummaryList.get(i);
				CdrCycleSummaryVO cdrCycleSummaryVO = cdrCycleSummarys.get(cdrSummaryVO.getBillCycleID());
				if (cdrCycleSummaryVO == null) {
					cdrCycleSummaryVO = new CdrCycleSummaryVO();
					cdrCycleSummaryVO.setTotalChargeAmt("0");
					Map<String, CdrCategoryVO> categoryCdrSummarys = new HashMap<String, CdrCategoryVO>();
					cdrCycleSummaryVO.setCategoryCdrSummarys(categoryCdrSummarys);
					cdrCycleSummarys.put(cdrSummaryVO.getBillCycleID(), cdrCycleSummaryVO);
				}
				cdrCycleSummaryVO.setBillCycleID(cdrSummaryVO.getBillCycleID());
				if (!StringUtils.isEmpty(cdrSummaryVO.getTotalChargeAmt())
						&& !StringUtils.isEmpty(cdrCycleSummaryVO.getTotalChargeAmt())) {

					long cycleTotal = Long.parseLong(cdrSummaryVO.getTotalChargeAmt())
							+ Long.parseLong(cdrCycleSummaryVO.getTotalChargeAmt());
					cdrCycleSummaryVO.setTotalChargeAmt(Long.toString(cycleTotal));
				}

				CdrCategoryVO cdrCategoryVO = cdrCycleSummaryVO.getCategoryCdrSummarys()
						.get(cdrSummaryVO.getServiceCategory());
				if (cdrCategoryVO == null) {
					cdrCategoryVO = new CdrCategoryVO();
					cdrCategoryVO.setUsageAmount("0");
					List<CdrSummaryVO> categoryCdrSummaryList = new ArrayList<CdrSummaryVO>();
					cdrCategoryVO.setCdrSummaryList(categoryCdrSummaryList);
					cdrCycleSummaryVO.getCategoryCdrSummarys().put(cdrSummaryVO.getServiceCategory(), cdrCategoryVO);
				}
				cdrCategoryVO.setCategoryType(cdrSummaryVO.getServiceCategory());
				cdrCategoryVO.setCategoryTypeDesc(cdrSummaryVO.getServiceCategoryDesc());
				cdrCategoryVO.getCdrSummaryList().add(cdrSummaryVO);
				if (!StringUtils.isEmpty(cdrSummaryVO.getTotalChargeAmt())
						&& !StringUtils.isEmpty(cdrCategoryVO.getUsageAmount())) {

					long categoryTotal = Long.parseLong(cdrSummaryVO.getTotalChargeAmt())
							+ Long.parseLong(cdrCategoryVO.getUsageAmount());
					cdrCategoryVO.setUsageAmount(Long.toString(categoryTotal));
				}
			}
		}
		return cdrCycleSummarys;
	}

	public ServiceUsageVO changeVo(CdrSummaryVO cdrSummaryVO) {
		ServiceUsageVO serviceUsageVO = new ServiceUsageVO();
		serviceUsageVO.setIndex(cdrSummaryVO.getBillCycleID());
		serviceUsageVO.setServiceType(cdrSummaryVO.getServiceType());
		serviceUsageVO.setServiceTypeDesc(cdrSummaryVO.getServiceTypeDesc());
		serviceUsageVO.setUsageAmount(BssServiceUtils.moneyFormat(cdrSummaryVO.getTotalChargeAmt()));
		return serviceUsageVO;
	}

}
