package com.apps.esb.service.bss.handler.mshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.vo.subscriber.group.GroupVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.mshop.group.MshopGroupVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;

@Service("mshopQueryGroupMemebers")
public class MshopQueryGroupMemebers implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		BizHandler appHandler = (BizHandler) ServiceUtils.getBeanByTenantId(handler.get("appHandlerName"));
		ProcessResult processResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}

		List<GroupVO> groupList = (List<GroupVO>) processResult.getVo();
		List<MshopGroupVO> groups = getGroupList(groupList);
		if (handler != null) {
			String custHandlerName = handler.get("custHandlerName");
			if (!StringUtils.isEmpty(custHandlerName)) {
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder) ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(groupList);
				custHandler.process(requestInfo, handler, extParameters, groups, originalVOs);
			}
		}

		JSONObject jsonResult = new JSONObject();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		jsonResult.put("groupList", JSONUtils.collectionToJson(groups));
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

	private List<MshopGroupVO> getGroupList(List<GroupVO> groupList) {
		if (groupList != null && groupList.size() > 0) {
			List<MshopGroupVO> groups = new ArrayList<MshopGroupVO>();
			for (int i = 0; i < groupList.size(); i++) {
				GroupVO groupVO = groupList.get(i);
				MshopGroupVO mshopGroupVO = new MshopGroupVO();
				groups.add(mshopGroupVO);
				mshopGroupVO.setAccountCode(groupVO.getAccountVO().getAccountCode());
				mshopGroupVO.setEffectiveTime(groupVO.getEffectiveTime());
				mshopGroupVO.setExpiryTime(groupVO.getExpiryTime());
				mshopGroupVO.setExtAttris(groupVO.getExtAttris());
				mshopGroupVO.setGroupCode(groupVO.getGroupCode());
				mshopGroupVO.setGroupId(groupVO.getGroupId());
				mshopGroupVO.setGroupName(groupVO.getGroupName());
				mshopGroupVO.setGroupType(groupVO.getGroupTypeDesc());
				mshopGroupVO.setRemainingNumber(groupVO.getRemainingMemberNumber());
				mshopGroupVO.setMaxNumber(groupVO.getMaxMemberNumber());
				mshopGroupVO.setPrimaryOffering(groupVO.getPrimaryOfferingName());
				mshopGroupVO.setStatus(groupVO.getStatusDesc());
			}
			return groups;
		}
		return null;
	}
}