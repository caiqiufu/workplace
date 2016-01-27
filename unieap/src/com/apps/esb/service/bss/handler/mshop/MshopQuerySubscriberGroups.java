package com.apps.esb.service.bss.handler.mshop;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.xml.soap.SOAPMessage;

import org.apache.axis.utils.StringUtils;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.service.bss.app.vo.subscriber.group.GroupMemberVO;
import com.apps.esb.service.bss.app.vo.subscriber.group.GroupVO;
import com.apps.esb.service.bss.element.RequestInfo;
import com.apps.esb.service.bss.element.ResponsetInfo;
import com.apps.esb.service.bss.handler.BizHandler;
import com.apps.esb.service.bss.handler.CustomizeBizHanlder;
import com.apps.esb.service.bss.handler.ProcessResult;
import com.apps.esb.service.bss.vo.mshop.group.MshopGroupMemberVO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;


@Service("mshopQuerySubscriberGroups")
public class MshopQuerySubscriberGroups implements BizHandler {

	@Override
	public ProcessResult process(RequestInfo requestInfo, Map<String, String> handler,
			Map<String, Object> extParameters) throws Exception {
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		if (StringUtils.isEmpty(requestInfo.getRequestBody().getServiceNumber())) {
			throw new Exception("serviceNumber is null");
		}
		BizHandler appHandler = (BizHandler) ServiceUtils.getBeanByTenantId(handler.get("appHandlerName"));
		ProcessResult processResult = appHandler.process(requestInfo, handler, extParameters);
		if (!UnieapConstants.C0.equals(processResult.getResultCode())) {
			return processResult;
		}

		GroupVO groupVO = (GroupVO) processResult.getVo();
		List<MshopGroupMemberVO> members = getGroupMemeberList(groupVO);
		if (handler != null) {
			String custHandlerName = handler.get("custHandlerName");
			if (!StringUtils.isEmpty(custHandlerName)) {
				CustomizeBizHanlder custHandler = (CustomizeBizHanlder) ServiceUtils.getBean(custHandlerName);
				List<Object> originalVOs = new ArrayList<Object>();
				originalVOs.add(groupVO);
				custHandler.process(requestInfo, handler, extParameters, members, originalVOs);
			}
		}

		JSONObject jsonResult = new JSONObject();
		processResult.setResultCode(UnieapConstants.C0);
		processResult.setResultDesc(UnieapConstants.getMessage(UnieapConstants.C0));
		jsonResult.put("memberList", JSONUtils.collectionToJson(members));
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
	private List<MshopGroupMemberVO> getGroupMemeberList(GroupVO groupVO) {
		List<GroupMemberVO> memberList = groupVO.getMemberList();
		if (memberList != null && memberList.size() > 0) {
			List<MshopGroupMemberVO> memebers = new ArrayList<MshopGroupMemberVO>();
			for (int i = 0; i < memberList.size(); i++) {
				GroupMemberVO groupMemberVO = memberList.get(i);
				MshopGroupMemberVO mshopGroupMemberVO = new MshopGroupMemberVO();
				memebers.add(mshopGroupMemberVO);
				mshopGroupMemberVO.setAccountCode(groupMemberVO.getAccountCode());
				mshopGroupMemberVO.setEffectiveTime(groupMemberVO.getEffectiveTime());
				mshopGroupMemberVO.setExpiryTime(groupMemberVO.getExpiryTime());
				mshopGroupMemberVO.setGroupCode(groupMemberVO.getGroupCode());
				mshopGroupMemberVO.setGroupId(groupMemberVO.getGroupId());
				mshopGroupMemberVO.setGroupName(groupMemberVO.getGroupName());
				mshopGroupMemberVO.setJoinTime(groupMemberVO.getJoinTime());
				mshopGroupMemberVO.setPaymentFlag(groupMemberVO.getPaymentFlagDesc());
				mshopGroupMemberVO.setPrimaryOffering(groupMemberVO.getPrimaryOffering());
				mshopGroupMemberVO.setServiceNumber(groupMemberVO.getServiceNumber());
				mshopGroupMemberVO.setStatus(groupMemberVO.getStatusDesc());
				
				mshopGroupMemberVO.setEffectiveTime(groupMemberVO.getEffectiveTime());
				mshopGroupMemberVO.setExpiryTime(groupMemberVO.getExpiryTime());
				mshopGroupMemberVO.setExtAttris(groupMemberVO.getExtAttris());
			}
			return memebers;
		}
		return null;
	}
}
