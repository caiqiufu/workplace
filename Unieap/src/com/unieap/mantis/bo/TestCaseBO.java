package com.unieap.mantis.bo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.mantis.vo.MTestcaseVO;
import com.unieap.pojo.MTestcase;
@Service("testCaseBO")
public class TestCaseBO extends BaseBO{
	
	public void getTestCasesGrid(String operType,PaginationSupport page,MTestcaseVO vo){
		DetachedCriteria criteria=DetachedCriteria.forClass(MTestcase.class);
		if(StringUtils.isNotEmpty(vo.getProject())){
			criteria.add(Restrictions.eq("project", vo.getProject()));
		}
		if(StringUtils.isNotEmpty(vo.getSubStream())){
			criteria.add(Restrictions.eq("subStream", vo.getSubStream()));
		}
		if(StringUtils.isNotEmpty(vo.getTcCode())){
			criteria.add(Restrictions.like("tcCode", vo.getTcCode(),MatchMode.START));
		}
		getPaginationDataByDetachedCriteria(criteria,page);
		copyTestcaseToVO(page);
	}
	public void copyTestcaseToVO(PaginationSupport page){
		if(page.getItems()!=null&&page.getItems().size()>0){
			List newItems = new ArrayList();
			MTestcaseVO testCaseVo; 
			for(int i = 0 ; i< page.getItems().size(); i++){
				testCaseVo = new MTestcaseVO();
				BeanUtils.copyProperties((MTestcase)page.getItems().get(i), testCaseVo);
				newItems.add(testCaseVo);
			}
			page.setItems(newItems);
		}
	}
	public Map getTestCase(MTestcaseVO vo) throws Exception{
		MTestcase testcase = DBManager.getHT(null).get(MTestcase.class, vo.getTcId());
		Map attributes = new HashMap();
		if(testcase!=null){
			BeanUtils.copyProperties(testcase, vo);
			attributes.put("tcId",vo.getTcId().toString());
			attributes.put("tcCode",vo.getTcCode());
			attributes.put("tcName",vo.getTcName());
			attributes.put("tcDescription",vo.getTcDescription());
			attributes.put("testStream",vo.getTestStream());
			attributes.put("testStreamDesc",vo.getTestStreamDesc());
			attributes.put("subStream",vo.getSubStream());
			attributes.put("subStreamDesc",vo.getSubStreamDesc());
			attributes.put("tester",vo.getTester());
			attributes.put("status",vo.getStatus());
			attributes.put("statusDesc",vo.getStatusDesc());
			attributes.put("testResult",vo.getTestResult());
			attributes.put("remark",vo.getRemark());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			attributes.put("createDate",df.format(vo.getCreateDate()));
			if(vo.getModifyDate()!=null){
				attributes.put("modifyDate",df.format(vo.getModifyDate()));
			}else{
				attributes.put("modifyDate",vo.getModifyDate());
			}
			attributes.put("modifyBy",vo.getModifyBy());
			attributes.put("modifyByName",vo.getModifyByName());
			attributes.put("createBy",vo.getCreateBy());
			attributes.put("createByName",vo.getCreateByName());
			attributes.put("path","/apps/mantis/tcmodify");
			attributes.put(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		}else{
			attributes.put("path","/apps/mantis/mantiserror");
			attributes.put("exception","Test Case ID:"+vo.getTcCode().toString()+" not exist.");
			attributes.put(UnieapConstants.ISSUCCESS, UnieapConstants.FAILED);
		}
		return attributes;
	}
	public Map<String, String> updateTc(MTestcase vo) throws Exception{
		MTestcase testcase = (MTestcase) findById(MTestcase.class,vo.getTcId(),null);
		testcase.setModifyDate(UnieapConstants.getDateTime(null));
		testcase.setModifyBy(UnieapConstants.getUser().getUserCode());
		testcase.setTcCode(vo.getTcCode());
		testcase.setStatus(vo.getStatus());
		testcase.setTestStream(vo.getTestStream());
		testcase.setSubStream(vo.getSubStream());
		testcase.setTcName(vo.getTcName());
		testcase.setTcDescription(vo.getTcDescription());
		testcase.setTestResult(vo.getTestResult());
		testcase.setRemark(vo.getRemark());
		DBManager.getHT(null).update(testcase);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	} 
}
