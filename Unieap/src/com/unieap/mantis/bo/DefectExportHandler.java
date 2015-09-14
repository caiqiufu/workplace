package com.unieap.mantis.bo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.BeanUtils;

import com.unieap.UnieapConstants;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.file.bo.ExcelHandler;
import com.unieap.file.vo.CellVO;
import com.unieap.file.vo.SheetVO;
import com.unieap.mantis.vo.MDefectVO;
import com.unieap.pojo.MDefect;

public class DefectExportHandler extends ExcelHandler{
	
	public Map<String,Object> getExportData(Map<String,Object> parameters){
		PaginationSupport page = new PaginationSupport();
		page.setPageSize(5000);
		DetachedCriteria criteria=DetachedCriteria.forClass(MDefect.class);
		if(parameters.get("project")!=null&&StringUtils.isNotEmpty(parameters.get("project").toString())){
			criteria.add(Restrictions.eq("project", parameters.get("project").toString()));
		}
		if(parameters.get("defectId")!=null&&StringUtils.isNotEmpty(parameters.get("defectId").toString())){
			criteria.add(Restrictions.eq("defectId", parameters.get("defectId").toString()));
		}
		if(parameters.get("createBy")!=null&&StringUtils.isNotEmpty(parameters.get("createBy").toString())){
			criteria.add(Restrictions.eq("createBy", parameters.get("createBy").toString()));
		}
		if(parameters.get("repoter")!=null&&StringUtils.isNotEmpty(parameters.get("repoter").toString())){
			criteria.add(Restrictions.eq("createBy", parameters.get("repoter").toString()));
		}
		if(parameters.get("prodVersion")!=null&&StringUtils.isNotEmpty(parameters.get("prodVersion").toString())){
			criteria.add(Restrictions.eq("prodVersion", parameters.get("prodVersion").toString()));
		}
		if(parameters.get("testStream")!=null&&StringUtils.isNotEmpty(parameters.get("testStream").toString())){
			criteria.add(Restrictions.eq("testStream", parameters.get("testStream").toString()));
		}
		if(parameters.get("severity")!=null&&StringUtils.isNotEmpty(parameters.get("severity").toString())){
			criteria.add(Restrictions.eq("severity", parameters.get("severity").toString()));
		}
		if(parameters.get("priority")!=null&&StringUtils.isNotEmpty(parameters.get("priority").toString())){
			criteria.add(Restrictions.eq("priority", parameters.get("priority").toString()));
		}
		if(parameters.get("subStream")!=null&&StringUtils.isNotEmpty(parameters.get("subStream").toString())){
			criteria.add(Restrictions.eq("subStream", parameters.get("subStream").toString()));
		}
		if(parameters.get("testPlan")!=null&&StringUtils.isNotEmpty(parameters.get("testPlan").toString())){
			criteria.add(Restrictions.eq("testPlan", parameters.get("testPlan").toString()));
		}
		if(parameters.get("tcId")!=null&&StringUtils.isNotEmpty(parameters.get("tcId").toString())){
			criteria.add(Restrictions.eq("tcId", parameters.get("tcId").toString()));
		}
		if(parameters.get("title")!=null&&StringUtils.isNotEmpty(parameters.get("title").toString())){
			criteria.add(Restrictions.like("title", parameters.get("title").toString()));
		}
		if(parameters.get("status")!=null&&StringUtils.isNotEmpty(parameters.get("status").toString())){
			criteria.add(Restrictions.eq("status", parameters.get("status").toString()));
		}
		if(parameters.get("hideStatus")!=null&&StringUtils.isNotEmpty(parameters.get("hideStatus").toString())){
			criteria.add(Restrictions.ne("status", parameters.get("hideStatus").toString()));
		}
		if(parameters.get("assignto")!=null&&StringUtils.isNotEmpty(parameters.get("assignto").toString())){
			criteria.add(Restrictions.eq("assignto", parameters.get("assignto").toString()));
		}
		page.setItems(DBManager.getHT(page.getDsName()).findByCriteria(criteria));

		Map exDatas = new HashMap();
		List<List<CellVO>> datas = new ArrayList<List<CellVO>>();
		List<SheetVO> sheets = new ArrayList<SheetVO>();
		SheetVO sheet = new SheetVO(0,UnieapConstants.getCurrentTime(null, UnieapConstants.DATEFORMAT));
		List<CellVO> titles = new ArrayList<CellVO>();
		titles.add(new CellVO(0,"defectId","Defect Id"));
		titles.add(new CellVO(1,"prodVersion","Prod Version"));
		titles.add(new CellVO(2,"testStream","Test Stream"));
		titles.add(new CellVO(3,"severity","Severity"));
		titles.add(new CellVO(4,"priority","Priority"));
		titles.add(new CellVO(5,"subStream","Sub Stream"));
		titles.add(new CellVO(6,"testPlan","Test Plan"));
		titles.add(new CellVO(7,"tcId","TC Id"));
		titles.add(new CellVO(8,"title","Title"));
		titles.add(new CellVO(9,"descpt","Description"));
		titles.add(new CellVO(10,"remark","Remark"));
		titles.add(new CellVO(11,"createDate","Create Date"));
		titles.add(new CellVO(12,"createBy","Create By"));
		titles.add(new CellVO(13,"modifyDate","Modify Date"));
		titles.add(new CellVO(14,"modifyBy","Modify By"));
		titles.add(new CellVO(15,"status","Status"));
		titles.add(new CellVO(16,"assignto","Assignto"));
		titles.add(new CellVO(17,"targetDate","Target Date"));
		titles.add(new CellVO(18,"project","Project"));
		datas.add(titles);
		if(page.getItems()!=null&&page.getItems().size()>0){
			MDefectVO vo;
			List<CellVO> data;
			for(int i = 0 ; i < page.getItems().size(); i++){
				vo = new MDefectVO();
				BeanUtils.copyProperties(page.getItems().get(i), vo);
				data = new ArrayList<CellVO>();
				data.add(new CellVO(0,"defectId",vo.getDefectId().toString()));
				data.add(new CellVO(1,"prodVersion",vo.getProdVersionDesc()));
				data.add(new CellVO(2,"testStream",vo.getTestStreamDesc()));
				data.add(new CellVO(3,"severity",vo.getSeverityDesc()));
				data.add(new CellVO(4,"priority",vo.getPriorityDesc()));
				data.add(new CellVO(5,"subStream",vo.getSubStreamDesc()));
				data.add(new CellVO(6,"testPlan",vo.getTestPlanDesc()));
				data.add(new CellVO(7,"tcId",vo.getTcId()));
				data.add(new CellVO(8,"title",vo.getTitle()));
				data.add(new CellVO(9,"descpt",vo.getDescpt()));
				data.add(new CellVO(10,"remark",vo.getRemark()));
				data.add(new CellVO(11,"createDate",vo.getCreateDate()==null?null:vo.getCreateDate().toString()));
				data.add(new CellVO(12,"createBy",vo.getCreateByName()));
				data.add(new CellVO(13,"modifyDate",vo.getModifyDate()==null?null:vo.getModifyDate().toString()));
				data.add(new CellVO(14,"modifyBy",vo.getModifyByName()));
				data.add(new CellVO(15,"status",vo.getStatusDesc()));
				data.add(new CellVO(16,"assignto",vo.getAssigntoName()));
				data.add(new CellVO(17,"targetDate",vo.getTargetDate()==null?null:vo.getTargetDate().toString()));
				data.add(new CellVO(18,"project",vo.getProjectDesc()));
				datas.add(data);
			}
		}
		sheet.setDatas(datas);
		sheets.add(sheet);
		exDatas.put(this.FILE_NAME,UnieapConstants.getUser().getUserCode());
		exDatas.put(this.SHEETS, sheets);
		return exDatas;
	}
}
