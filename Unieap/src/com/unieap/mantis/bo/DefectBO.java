package com.unieap.mantis.bo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.mantis.vo.MDefectVO;
import com.unieap.mantis.vo.MNoteVO;
import com.unieap.mantis.vo.ViewIssueVO;
import com.unieap.mdm.vo.DicDataVO;
import com.unieap.pojo.EmailSend;
import com.unieap.pojo.MAtta;
import com.unieap.pojo.MChglog;
import com.unieap.pojo.MDefect;
import com.unieap.pojo.MNote;
import com.unieap.pojo.User;
import com.unieap.pojo.VisitLog;
import com.unieap.tools.Propertyholder;
@Service("defectBO")
public class DefectBO extends BaseBO{
	
	/**
	 * get main menu info
	 * @param delectVo
	 * @return
	 * @throws Exception
	 */
	public Map getMain(String project) throws Exception{
		DetachedCriteria criteriaassign =DetachedCriteria.forClass(MDefect.class);
		Property assignto = Property.forName("assignto");
		criteriaassign.add(assignto.eq(UnieapConstants.getUser().getUserCode()));
		Property status = Property.forName("status");
		Collection ss = new ArrayList();
		ss.add("0");
		ss.add("1");
		ss.add("2");
		ss.add("3");
		ss.add("5");
		criteriaassign.add(status.in(ss));
		int assignedtome = getCountByDetachedCriteria(criteriaassign,null,null);
		Map attributes = new HashMap();
		attributes.put("assignedtome",assignedtome);
		DetachedCriteria criteriareport =DetachedCriteria.forClass(MDefect.class);
		Property createBy = Property.forName("createBy");
		criteriareport.add(assignto.eq(UnieapConstants.getUser().getUserCode()));
		criteriareport.add(status.in(ss));
		int reportbyme = getCountByDetachedCriteria(criteriareport,null,null);
		attributes.put("reportbyme",reportbyme);
		DetachedCriteria criteriavisit =DetachedCriteria.forClass(VisitLog.class);
		criteriavisit.addOrder(Order.desc("loginDate"));
		List<Object> datas = DBManager.getHT(null).findByCriteria(criteriavisit,1,1);
		if(datas!=null&&datas.size()>0){
			attributes.put("lastvisitdate",((VisitLog)datas.get(0)).getLoginDate());
		}else{
			attributes.put("lastvisitdate","");
		}
		return attributes;
	}
	
	public Map<String, String> reportIssueDeal(String operType,MDefect vo) throws Exception{
		if(StringUtils.equals(operType, UnieapConstants.ADD)){
			return save(vo);
		}/*else if(StringUtils.equals(operType, UnieapConstants.MODIFY)){
			return update(vo);
		}else if(StringUtils.equals(operType, UnieapConstants.DELETE)){
			return delete(vo);
		}*/else{
			throw new Exception("Operation type["+operType+"] is wrong.");
		}
	}
	public Map<String, String> save(MDefect vo) throws Exception{
		vo.setDefectId(getSequence(null,"defect"));
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		vo.setStatus("0");
		DBManager.getHT(null).save(vo);
		sendEmailForUpdateDefect(vo);
		Map result = result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
		result.put("defectId", vo.getDefectId().toString());
		return result;
	}
	public void viewisGrid(String operType ,PaginationSupport page,ViewIssueVO vo) throws Exception{
		if(StringUtils.equals(operType,"Unassigned")){
			getUnassigned(page,vo);
		}else if(StringUtils.equals(operType,"ReportedbyMe")){
			getReportedbyMe(page,vo);
		}else if(StringUtils.equals(operType,"Resolved")){
			getResolved(page,vo);
		}else if(StringUtils.equals(operType,"RecentlyModified")){
			getRecentlyModified(page,vo);
		}else if(StringUtils.equals(operType,"ViewIssues")){
			getViewIssues(page,vo);
		}else if(StringUtils.equals(operType,"AssignedToMe")){
			getAssignedToMeIssues(page,vo);
		}else{
			throw new Exception("Operation type["+operType+"] is wrong.");
		}
	}
	public void copyPageDefectToVO(PaginationSupport page){
		if(page.getItems()!=null&&page.getItems().size()>0){
			List newItems = new ArrayList();
			MDefectVO delectVo; 
			for(int i = 0 ; i< page.getItems().size(); i++){
				delectVo = new MDefectVO();
				BeanUtils.copyProperties((MDefect)page.getItems().get(i), delectVo);
				newItems.add(delectVo);
			}
			page.setItems(newItems);
		}
	}
	public void getUnassigned(PaginationSupport page,ViewIssueVO vo){
		if(StringUtils.isEmpty(vo.getProject())){
			DetachedCriteria criteria=DetachedCriteria.forClass(MDefect.class);
			Property status = Property.forName("status");
			criteria.add(status.eq("0"));
			getPaginationDataByDetachedCriteria(criteria,page);
		}else{
			Property project = Property.forName("project");
			DetachedCriteria criteria=DetachedCriteria.forClass(MDefect.class).add(project.eq(vo.getProject()));
			Property status = Property.forName("status");
			criteria.add(status.eq("0"));
			getPaginationDataByDetachedCriteria(criteria,page);
		}
		copyPageDefectToVO(page);
	}
	public void getAssignedToMeIssues(PaginationSupport page,ViewIssueVO vo){
		if(StringUtils.isEmpty(vo.getProject())){
			DetachedCriteria criteria=DetachedCriteria.forClass(MDefect.class);
			Property assignto = Property.forName("assignto");
			criteria.add(assignto.eq(UnieapConstants.getUser().getUserCode()));
			getPaginationDataByDetachedCriteria(criteria,page);
		}else{
			Property project = Property.forName("project");
			DetachedCriteria criteria=DetachedCriteria.forClass(MDefect.class).add(project.eq(vo.getProject()));
			Property assignto = Property.forName("assignto");
			criteria.add(assignto.eq(UnieapConstants.getUser().getUserCode()));
			getPaginationDataByDetachedCriteria(criteria,page);
		}
		copyPageDefectToVO(page);
	}
	public void getReportedbyMe(PaginationSupport page,ViewIssueVO vo){
		if(StringUtils.isEmpty(vo.getProject())){
			DetachedCriteria criteria=DetachedCriteria.forClass(MDefect.class);
			Property createBy = Property.forName("createBy");
			criteria.add(createBy.eq(UnieapConstants.getUser().getUserCode()));
			getPaginationDataByDetachedCriteria(criteria,page);
		}else{
			Property project = Property.forName("project");
			DetachedCriteria criteria=DetachedCriteria.forClass(MDefect.class).add(project.eq(vo.getProject()));
			Property createBy = Property.forName("createBy");
			criteria.add(createBy.eq(UnieapConstants.getUser().getUserCode()));
			getPaginationDataByDetachedCriteria(criteria,page);
		}
		copyPageDefectToVO(page);
	}
	public void getResolved(PaginationSupport page,ViewIssueVO vo){
		if(StringUtils.isEmpty(vo.getProject())){
			DetachedCriteria criteria=DetachedCriteria.forClass(MDefect.class);
			Property status = Property.forName("status");
			criteria.add(status.eq("6"));
			getPaginationDataByDetachedCriteria(criteria,page);
		}else{
			Property project = Property.forName("project");
			DetachedCriteria criteria=DetachedCriteria.forClass(MDefect.class).add(project.eq(vo.getProject()));
			Property status = Property.forName("status");
			criteria.add(status.eq("6"));
			getPaginationDataByDetachedCriteria(criteria,page);
		}
		copyPageDefectToVO(page);
	}
	public void getRecentlyModified(PaginationSupport page,ViewIssueVO vo){
		if(StringUtils.isEmpty(vo.getProject())){
			DetachedCriteria criteria=DetachedCriteria.forClass(MDefect.class);
			page.setDir("DESC");
			page.setSort("modifyDate");
			getPaginationDataByDetachedCriteria(criteria,page);
		}else{
			Property project = Property.forName("project");
			DetachedCriteria criteria=DetachedCriteria.forClass(MDefect.class).add(project.eq(vo.getProject()));
			page.setDir("DESC");
			page.setSort("modifyDate");
			getPaginationDataByDetachedCriteria(criteria,page);
		}
		copyPageDefectToVO(page);
	}
	public void getViewIssues(PaginationSupport page,ViewIssueVO vo){
		DetachedCriteria criteria=DetachedCriteria.forClass(MDefect.class);
		if(StringUtils.isNotEmpty(vo.getProject())){
			criteria.add(Restrictions.eq("project", vo.getProject()));
		}
		if(vo.getDefectId()!=null){
			criteria.add(Restrictions.eq("defectId", vo.getDefectId()));
		}
		if(StringUtils.isNotEmpty(vo.getRepoter())){
			criteria.add(Restrictions.eq("createBy", vo.getRepoter()));
		}
		if(StringUtils.isNotEmpty(vo.getProdVersion())){
			criteria.add(Restrictions.eq("prodVersion", vo.getProdVersion()));
		}
		if(StringUtils.isNotEmpty(vo.getTestStream())){
			criteria.add(Restrictions.eq("testStream", vo.getTestStream()));
		}
		if(StringUtils.isNotEmpty(vo.getSeverity())){
			criteria.add(Restrictions.eq("severity", vo.getSeverity()));
		}
		if(StringUtils.isNotEmpty(vo.getPriority())){
			criteria.add(Restrictions.eq("priority", vo.getPriority()));
		}
		if(StringUtils.isNotEmpty(vo.getSubStream())){
			criteria.add(Restrictions.eq("subStream", vo.getSubStream()));
		}
		if(StringUtils.isNotEmpty(vo.getTestPlan())){
			criteria.add(Restrictions.eq("testPlan", vo.getTestPlan()));
		}
		if(StringUtils.isNotEmpty(vo.getTcId())){
			criteria.add(Restrictions.eq("tcId", vo.getTcId()));
		}
		if(StringUtils.isNotEmpty(vo.getTitle())){
			criteria.add(Restrictions.like("title", vo.getTitle()));
		}
		if(StringUtils.isNotEmpty(vo.getStatus())){
			criteria.add(Restrictions.eq("status", vo.getStatus()));
		}
		if(StringUtils.isNotEmpty(vo.getHideStatus())){
			criteria.add(Restrictions.ne("status", vo.getHideStatus()));
		}
		if(StringUtils.isNotEmpty(vo.getAssignto())){
			criteria.add(Restrictions.ne("assignto", vo.getAssignto()));
		}
		getPaginationDataByDetachedCriteria(criteria,page);
		copyPageDefectToVO(page);
	}
	public Map getDefect(MDefectVO delectVo) throws Exception{
		MDefect delect = DBManager.getHT(null).get(MDefect.class, delectVo.getDefectId());
		Map attributes = new HashMap();
		if(delect!=null){
			BeanUtils.copyProperties(delect, delectVo);
			attributes.put("defectId",delectVo.getDefectId().toString());
			attributes.put("prodVersion",delectVo.getProdVersion());
			attributes.put("prodVersionDesc",delectVo.getProdVersionDesc());
			attributes.put("testStream",delectVo.getTestStream());
			attributes.put("testStreamDesc",delectVo.getTestStreamDesc());
			attributes.put("severity",delectVo.getSeverity());
			attributes.put("severityDesc",delectVo.getSeverityDesc());
			attributes.put("priority",delectVo.getPriority());
			attributes.put("priorityDesc",delectVo.getPriorityDesc());
			attributes.put("subStream",delectVo.getSubStream());
			attributes.put("subStreamDesc",delectVo.getSubStreamDesc());
			attributes.put("testPlan",delectVo.getTestPlan());
			attributes.put("testPlanDesc",delectVo.getTestPlanDesc());
			attributes.put("tcId",delectVo.getTcId());
			attributes.put("dts",delectVo.getDts());
			attributes.put("title",delectVo.getTitle());
			attributes.put("descpt",delectVo.getDescpt());
			attributes.put("remark",delectVo.getRemark());
			attributes.put("steps",delectVo.getSteps());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			attributes.put("createDate",df.format(delectVo.getCreateDate()));
			if(delectVo.getModifyDate()!=null){
				attributes.put("modifyDate",df.format(delectVo.getModifyDate()));
			}else{
				attributes.put("modifyDate",delectVo.getModifyDate());
			}
			attributes.put("modifyBy",delectVo.getModifyBy());
			attributes.put("modifyByName",delectVo.getModifyByName());
			attributes.put("createBy",delectVo.getCreateBy());
			attributes.put("createByName",delectVo.getCreateByName());
			attributes.put("status",delectVo.getStatus());
			attributes.put("statusDesc",delectVo.getStatusDesc());
			attributes.put("assignto",delectVo.getAssignto());
			attributes.put("assigntoName",delectVo.getAssigntoName());
			if(delectVo.getTargetDate()!=null&&delectVo.getTargetDate().toString().length()>10){
				String targetDate = StringUtils.substring(delectVo.getTargetDate().toString(), 0, 10);
				attributes.put("targetDate",targetDate);
			}else{
				attributes.put("targetDate",delectVo.getTargetDate());
			}
			attributes.put("attas",getAtt(delectVo));
			attributes.put("notes",getNotes(delectVo));
			attributes.put("project",delectVo.getProject());
			attributes.put("path","/apps/mantis/defectedetail");
			attributes.put(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		}else{
			attributes.put("path","/apps/mantis/mantiserror");
			attributes.put("exception","Defect ID:"+delectVo.getDefectId().toString()+" not exist.");
			attributes.put(UnieapConstants.ISSUCCESS, UnieapConstants.FAILED);
		}
		return attributes;
	}
	public String getRepoterForDefect(Integer defectId) throws Exception{
		MDefect defect = DBManager.getHT(null).get(MDefect.class, defectId);
		MDefectVO delectVo = new MDefectVO();
		BeanUtils.copyProperties(defect, delectVo);
		JSONArray ja = new JSONArray();
		/*JSONObject reporter = new JSONObject();
		reporter.put("dicCode",delectVo.getCreateBy());
		reporter.put("dicName","Reporter");
		ja.put(reporter);*/
		StringBuffer sb = new StringBuffer();
		sb.append("select uu.user_code as dicCode,uu.user_name as dicName from unieap.user uu,");
		sb.append("(select distinct ur.user_id from unieap.user_role ur,unieap.role_resource rr ");
		sb.append("where ur.role_id=rr.role_id and rr.resource_type='0' and rr.category =1014 and rr.resource_id =?) u ");
		sb.append("where uu.user_id = u.user_id");
		List<?> items =  DBManager.getJT(null).query(sb.toString(),new Object[]{defect.getProject()},new EntityRowMapper(DicDataVO.class));
		if(items!=null&&items.size()>0){
			for(int i = 0 ; i < items.size(); i++){
				DicDataVO dicVo = (DicDataVO) items.get(i);
				JSONObject jac = new JSONObject();
				jac.put("dicCode", dicVo.getDicCode());
				jac.put("dicName", dicVo.getDicName());
				ja.put(jac);
			}
		}
		String dicString = ja.toString();
		return dicString;
	}
	public String getRepoterForQuery(String project) throws Exception{
		List<?> items ;
		if(StringUtils.isEmpty(project)){
			String sql = "SELECT  u.user_code as dicCode,u.user_name as dicName FROM unieap.user u order by u.user_name";
			items = DBManager.getJT(null).query(sql,new EntityRowMapper(DicDataVO.class));

		}else{
			StringBuffer sb = new StringBuffer();
			sb.append("select uu.user_code as dicCode,uu.user_name as dicName from unieap.user uu,");
			sb.append("(select distinct ur.user_id from unieap.user_role ur,unieap.role_resource rr ");
			sb.append("where ur.role_id=rr.role_id and rr.resource_type='0' and rr.category =1014 and rr.resource_id =?) u ");
			sb.append("where uu.user_id = u.user_id");

			items =  DBManager.getJT(null).query(sb.toString(),new Object[]{project},new EntityRowMapper(DicDataVO.class));
		}	
		JSONArray ja = new JSONArray();
		if(items!=null&&items.size()>0){
			for(int i = 0 ; i < items.size(); i++){
				DicDataVO dicVo = (DicDataVO) items.get(i);
				JSONObject jac = new JSONObject();
				jac.put("dicCode", dicVo.getDicCode());
				jac.put("dicName", dicVo.getDicName());
				ja.put(jac);
			}
		}
		String dicString = ja.toString();
		return dicString;
	}
	public List getAtt(MDefectVO delectVo){
		Property defectId = Property.forName("defectId");
		DetachedCriteria criteria=DetachedCriteria.forClass(MAtta.class).add(defectId.eq(delectVo.getDefectId()));
		return (List<MAtta>)DBManager.getHT(null).findByCriteria(criteria);
	}
	public List getNotes(MDefectVO delectVo){
		String sql = "SELECT m.note_id as noteId,m.defect_id as defectId,m.description, m.create_date as createDate,m.create_by as createBy,u.user_name as createByName,m.modify_date as modifyDate,m.enable FROM unieap.m_note m , unieap.user u where m.create_by = u.user_code and m.enable='Y' and m.defect_id = ?";
		return DBManager.getJT(null).query(sql,new Object[]{delectVo.getDefectId()},new EntityRowMapper(MNoteVO.class));
	}
	public String getReporterList(String operType,String project,Integer defectId) throws Exception{
		if(StringUtils.equals(operType,"ViewIssue")){
			return getRepoterForQuery(project);
		}else if(StringUtils.equals(operType,"AssignTo")){
			return getRepoterForDefect(defectId);
		}else{
			throw new Exception("Operation type["+operType+"] is wrong.");
		}
	}
	public Map<String, String> deleteAtta(MAtta vo){
		deleteById(MAtta.class,vo.getAttaId(),null);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	
	public Map<String, String> nodeDeal(String operType,MNote vo) throws Exception{
		if(StringUtils.equals(operType, UnieapConstants.ADD)){
			return saveNode(vo);
		}else if(StringUtils.equals(operType, UnieapConstants.MODIFY)){
			return updateNode(vo);
		}else if(StringUtils.equals(operType, UnieapConstants.DELETE)){
			return deleteNode(vo);
		}else{
			throw new Exception("Operation type["+operType+"] is wrong.");
		}
	}
	public Map<String, String> saveNode(MNote vo) throws Exception{
		vo.setNoteId(getSequence(null,"unieap"));
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		vo.setEnable(UnieapConstants.YES);
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public Map<String, String> updateNode(MNote vo){
		MNote mNote = (MNote) findById(MNote.class,vo.getNoteId(),null);
		mNote.setModifyDate(UnieapConstants.getDateTime(null));
		mNote.setModifyBy(UnieapConstants.getUser().getUserCode());
		mNote.setDescription(vo.getDescription());
		DBManager.getHT(null).update(mNote);
		//ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		///changeLogBO.save(vo.getDefectId(),ChangeLogBO.TAB_MNOTE, "description","Description", mNote.getDescription(), vo.getDescription());
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public void sendEmailForUpdateDefect(MDefect defect) throws Exception{
		defect = (MDefect) findById(MDefect.class,defect.getDefectId(),null);
		MDefectVO delectVo = new MDefectVO();
		BeanUtils.copyProperties(defect, delectVo);
		StringBuffer sb = new StringBuffer();
		sb.append("[").append(delectVo.getProjectDesc()).append(" ")
		.append(delectVo.getDefectId().toString()).append(" ] ").append(delectVo.getTitle());
		StringBuffer content = new StringBuffer();
		String en =System.getProperty("line.separator");
		content.append("The following issue have be ").append(StringUtils.upperCase(delectVo.getStatusDesc())).append(en);
		content.append("=================================================").append(en);
		content.append(Propertyholder.getContextProperty("server_address")).append("/MantisController.do?method=issueDetail&defectId=").append(delectVo.getDefectId().toString()).append(en);
		content.append("=================================================").append(en);
		content.append("Reported By:        ").append(delectVo.getCreateByName()).append(en);
		content.append("Assigned To:        ").append(delectVo.getAssigntoName()).append(en);
		content.append("=================================================").append(en);
		content.append("Project:            ").append(delectVo.getProjectDesc()).append(en);
		content.append("Issue ID:           ").append(delectVo.getDefectId().toString()).append(en);
		content.append("Severity:           ").append(delectVo.getSeverityDesc()).append(en);
		content.append("Priority:           ").append(delectVo.getPriorityDesc()).append(en);
		content.append("Status:             ").append(delectVo.getStatusDesc()).append(en);
		content.append("Target Fix Date:    ").append(delectVo.getTargetDate()).append(en);
		content.append("=================================================").append(en);
		content.append("Date Submitted:     ").append(delectVo.getCreateDate()).append(en);
		content.append("Last Modified:      ").append(delectVo.getModifyDate()).append(en);
		content.append("=================================================").append(en);
		content.append("Title:              ").append(delectVo.getTitle()).append(en);
		content.append("Description:        ").append(delectVo.getDescpt()).append(en);
		content.append("=================================================").append(en);
		content.append("Issue History").append(en);
		content.append("Date Modified       ").append("Username       ").append("Field       ").append("Change       ").append(en);
		PaginationSupport page = new PaginationSupport();
		viewIssueHistory(page,defect);
		List datas = page.getItems();
		if(datas!=null&&datas.size()>0){
			MChglog log;
			for(int i = 0 ; i < datas.size() ; i++){
				log = (MChglog)datas.get(i);
				content.append(log.getModifyDate()).append("       ").append(log.getUserName()).append("       ").append(log.getFieldName()).append("       ").append(log.getOldValue()).append(" => ").append(log.getNewValue()).append(en);
			}
		}
		content.append("=================================================").append(en);
		EmailSend emailSend = new EmailSend();
		emailSend.setContent(content.toString());
		emailSend.setCreateDate(UnieapConstants.getDateTime(null));
		emailSend.setDescription(null);
		emailSend.setEmail(getEmailAccounts(delectVo));
		emailSend.setSendId(getSequence(null,"unieap"));
		emailSend.setStatus("2");
		emailSend.setSubject(sb.toString());
		emailSend.setTimes(Integer.valueOf(0));
		DBManager.getHT(null).save(emailSend);
	}
	public String getEmailAccounts(MDefectVO delectVo){
		StringBuffer sb = new StringBuffer();
		sb.append("select uu.email from unieap.user uu,");
		sb.append("(select distinct ur.user_id from unieap.user_role ur,unieap.role_resource rr ");
		sb.append("where ur.role_id=rr.role_id and rr.resource_type='0' and rr.category =1014 and rr.resource_id =?) u ");
		sb.append("where uu.user_id = u.user_id");
		List datas = DBManager.getJT(null).queryForList(sb.toString(),new Object[]{delectVo.getProject()});
		String emails =  UnieapConstants.getUser().getEmail()+";";
		if(datas!=null&&datas.size()>0){
			for(int i = 0 ; i < datas.size() ; i++){
				emails = emails+((Map)datas.get(i)).get("email")+";";
			}
		}
		emails = emails.substring(0,emails.length()-1);
		return emails;
	}
	public Map<String, String> deleteNode(MNote vo){
		MNote mNote = (MNote) findById(MNote.class,vo.getNoteId(),null);
		mNote.setEnable(UnieapConstants.YES);
		DBManager.getHT(null).update(mNote);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	/**
	 * update defect
	 * @param vo
	 * @return
	 * @throws Exception 
	 */
	public Map<String, String> updateDefect(String operType, MDefect vo) throws Exception{
		if(StringUtils.equals(operType,"UpdateIssue")){
			return updateIssue(vo);
		}else if(StringUtils.equals(operType,"AssignTo")){
			return assignTo(vo);
		}else if(StringUtils.equals(operType,"ChangeStatus")){
			return changeStatus(vo);
		}else{
			throw new Exception("Operation type["+operType+"] is wrong.");
		}
	}
	public Map<String, String> updateIssue(MDefect vo) throws Exception{
		boolean changed = false;
		MDefect mDefect = (MDefect) findById(MDefect.class,vo.getDefectId(),null);
		mDefect.setModifyDate(UnieapConstants.getDateTime(null));
		mDefect.setModifyBy(UnieapConstants.getUser().getUserCode());
		
		MDefectVO oldVo = new MDefectVO();
		//oldVo = (MDefectVO) PojoToVo(mDefect,oldVo);
		BeanUtils.copyProperties(mDefect,oldVo);
		MDefectVO newVo = new MDefectVO();
		//newVo = (MDefectVO) PojoToVo(vo,newVo);
		BeanUtils.copyProperties(vo,newVo);
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		
		if(!StringUtils.equals(mDefect.getProdVersion(), vo.getProdVersion())){
			changed = true;
			mDefect.setProdVersion(vo.getProdVersion());
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "prod_version","Product Version",oldVo.getProdVersionDesc(), newVo.getProdVersionDesc());
		}
		if(!StringUtils.equals(mDefect.getTestStream(),vo.getTestStream())){
			changed = true;
			mDefect.setTestStream(vo.getTestStream());
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "test_stream","Test Plan",oldVo.getTestStreamDesc(), newVo.getTestStreamDesc());
		}
		if(!StringUtils.equals(mDefect.getSeverity(),vo.getSeverity())){
			changed = true;
			mDefect.setSeverity(vo.getSeverity());
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "severity","Severity",oldVo.getSeverityDesc(), newVo.getSeverityDesc());
		}
		if(!StringUtils.equals(mDefect.getPriority(),vo.getPriority())){
			changed = true;
			mDefect.setPriority(vo.getPriority());
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "priority","Priority",oldVo.getPriorityDesc(), newVo.getPriorityDesc());
		}
		if(!StringUtils.equals(mDefect.getSubStream(),vo.getSubStream())){
			changed = true;
			mDefect.setSubStream(vo.getSubStream());
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "sub_stream","Sub Stream",oldVo.getSubStreamDesc(), newVo.getSubStreamDesc());
		}
		if(!StringUtils.equals(mDefect.getTestPlan(),vo.getTestPlan())){
			changed = true;
			mDefect.setTestPlan(vo.getTestPlan());
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "test_plan","Test Plan",oldVo.getTestPlanDesc(), newVo.getTestPlanDesc());
		}
		if(vo.getTargetDate()!=null&&(!vo.getTargetDate().equals(mDefect.getTargetDate()))){
			changed = true;
			mDefect.setTargetDate(vo.getTargetDate());
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "targetDate","Target Fix Date",oldVo.getTargetDate()==null?"":df.format(oldVo.getTargetDate()), newVo.getTargetDate()==null?"":df.format(newVo.getTargetDate()));
		}else{
			mDefect.setTargetDate(null);
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "targetDate","Target Fix Date",oldVo.getTargetDate()==null?"":df.format(oldVo.getTargetDate()), "");
		}
		if(!StringUtils.equals(mDefect.getTcId(),vo.getTcId())){
			changed = true;
			mDefect.setTcId(vo.getTcId());
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "tcId","Test Case ID",oldVo.getTcId(), newVo.getTcId());
		}
		if(!StringUtils.equals(mDefect.getDts(),vo.getDts())){
			changed = true;
			mDefect.setDts(vo.getDts());
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "dts","DTS",oldVo.getDts(), newVo.getDts());
		}
		if(!StringUtils.equals(mDefect.getTitle(),vo.getTitle())){
			changed = true;
			mDefect.setTitle(vo.getTitle());
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "title","Title",oldVo.getTitle(), newVo.getTitle());
		}
		if(!StringUtils.equals(mDefect.getRemark(),vo.getRemark())){
			changed = true;
			mDefect.setRemark(vo.getRemark());
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "remark","Remark",oldVo.getRemark(), newVo.getRemark());
		}
		if(!StringUtils.equals(mDefect.getStatus(),vo.getStatus())){
			changed = true;
			mDefect.setStatus(vo.getStatus());
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "status","Status",oldVo.getStatusDesc(), newVo.getStatusDesc());
		}
		if(!StringUtils.equals(mDefect.getAssignto(),vo.getAssignto())){
			changed = true;
			mDefect.setAssignto(vo.getAssignto());
			changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "assignto","Assignto",oldVo.getAssigntoName(), newVo.getAssigntoName());
		}
		if(changed){
			DBManager.getHT(null).update(mDefect);
		}
		sendEmailForUpdateDefect(mDefect);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	} 
	public Map<String, String> assignTo(MDefect vo) throws Exception{
		MDefect mDefect = (MDefect) findById(MDefect.class,vo.getDefectId(),null);
		assignToChangeLog(vo,mDefect);
		mDefect.setModifyDate(UnieapConstants.getDateTime(null));
		mDefect.setModifyBy(UnieapConstants.getUser().getUserCode());
		mDefect.setAssignto(vo.getAssignto());
		DBManager.getHT(null).update(mDefect);
		sendEmailForUpdateDefect(mDefect);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	} 
	public void assignToChangeLog(MDefect newdefect,MDefect olddefect){
		MDefectVO oldVo = new MDefectVO();
		//oldVo = (MDefectVO) PojoToVo(olddefect,oldVo);
		BeanUtils.copyProperties(olddefect,oldVo);
		MDefectVO newVo = new MDefectVO();
		//newVo = (MDefectVO) PojoToVo(newdefect,newVo);
		BeanUtils.copyProperties(newdefect,newVo);
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "assignto","Assignto",oldVo.getAssigntoName(), newVo.getAssigntoName());
	} 
	public Map<String, String> changeStatus(MDefect vo) throws Exception{
		MDefect mDefect = (MDefect) findById(MDefect.class,vo.getDefectId(),null);
		changeStatusChangeLog(vo,mDefect);
		mDefect.setModifyDate(UnieapConstants.getDateTime(null));
		mDefect.setModifyBy(UnieapConstants.getUser().getUserCode());
		mDefect.setStatus(vo.getStatus());
		DBManager.getHT(null).update(mDefect);
		sendEmailForUpdateDefect(mDefect);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	} 
	public void changeStatusChangeLog(MDefect newdefect,MDefect olddefect){
		MDefectVO oldVo = new MDefectVO();
		//oldVo = (MDefectVO) PojoToVo(olddefect,oldVo);
		BeanUtils.copyProperties(olddefect,oldVo);
		MDefectVO newVo = new MDefectVO();
		//newVo = (MDefectVO) PojoToVo(newdefect,newVo);
		BeanUtils.copyProperties(newdefect,newVo);
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(newVo.getDefectId(),ChangeLogBO.TAB_MDEFECT, "status","Status",oldVo.getStatusDesc(), newVo.getStatusDesc());
	} 
	
	/**
	 * View Issue History
	 * @param page
	 * @param defect
	 * @throws Exception
	 */
	public void viewIssueHistory(PaginationSupport page,MDefect defect) throws Exception{
		DetachedCriteria criteria=DetachedCriteria.forClass(MChglog.class);
		Property recordId = Property.forName("recordId");
		criteria.add(recordId.eq(defect.getDefectId()));
		getPaginationDataByDetachedCriteria(criteria,page);
	}
	
	public Map<String, String> batchUpdate(String operType,String datas,String updateValue) throws Exception{
		JSONArray datasArray = new JSONArray(datas);
		if(datasArray!=null&&datasArray.length()>0){
			JSONObject json = null;
			Integer defectId;
			MNote node;
			for(int i = 0 ; i < datasArray.length(); i++){
				json = (JSONObject) datasArray.get(i);
				defectId = Integer.valueOf(json.get("defectId").toString());
				if(StringUtils.equals(operType, "0")){
					String oldprojectDesc = json.get("projectDesc").toString();
					DicDataVO newdic =  CacheMgt.getDicData("1014",updateValue);
					ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
					changeLogBO.save(defectId,ChangeLogBO.TAB_MDEFECT, "project","Project",oldprojectDesc, newdic.getDicName());
					DBManager.getHT(null).bulkUpdate("update MDefect m set m.project=? where m.defectId=?",new Object[]{updateValue,defectId});
				}
				if(StringUtils.equals(operType, "2")){
					Property userCode = Property.forName("userCode");
					DetachedCriteria criteria=DetachedCriteria.forClass(com.unieap.pojo.User.class).add(userCode.eq(updateValue));
					List<com.unieap.pojo.User> users = DBManager.getHT(null).findByCriteria(criteria);
					String oldName = json.get("assigntoName").toString();
					String newName = "";
					if(users!=null && users.size()>0){
						User user = users.get(0);
						newName = user.getUserName();
					}
					ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
					changeLogBO.save(defectId,ChangeLogBO.TAB_MDEFECT, "assignto","Assignto",oldName,newName);
					DBManager.getHT(null).bulkUpdate("update MDefect m set m.assignto=? where m.defectId=?",new Object[]{updateValue,defectId});
				}
				if(StringUtils.equals(operType, "5")){
					String oldseverityDesc = json.get("severityDesc").toString();
					DicDataVO newdic =  CacheMgt.getDicData("1004",updateValue);
					ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
					changeLogBO.save(defectId,ChangeLogBO.TAB_MDEFECT, "severity","Severity",oldseverityDesc, newdic.getDicName());
					DBManager.getHT(null).bulkUpdate("update MDefect m set m.severity=? where m.defectId=?",new Object[]{updateValue,defectId});
				}
				if(StringUtils.equals(operType, "6")){
					String oldpriorityDesc = json.get("priorityDesc").toString();
					DicDataVO newdic =  CacheMgt.getDicData("1004",updateValue);
					ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
					changeLogBO.save(defectId,ChangeLogBO.TAB_MDEFECT, "priority","Priority",oldpriorityDesc, newdic.getDicName());
					DBManager.getHT(null).bulkUpdate("update MDefect m set m.priority=? where m.defectId=?",new Object[]{updateValue,defectId});
				}
				if(StringUtils.equals(operType, "7")){
					String oldstatusDesc = json.get("statusDesc").toString();
					DicDataVO newdic =  CacheMgt.getDicData("1003",updateValue);
					ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
					changeLogBO.save(defectId,ChangeLogBO.TAB_MDEFECT, "status","Status",oldstatusDesc, newdic.getDicName());
					DBManager.getHT(null).bulkUpdate("update MDefect m set m.status=? where m.defectId=?",new Object[]{updateValue,defectId});
				}
				if(StringUtils.equals(operType, "8")){
					node = new MNote();
					node.setDescription(updateValue);
					node.setDefectId(defectId);
					saveNode(node);
				}
			}
		}
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public Map<String, String> batchUpdateProject(String datas,String updateValue) throws Exception{
		if(StringUtils.equals("-1", updateValue)){
			Map result = result(UnieapConstants.ISSUCCESS,UnieapConstants.FAILED);
			result.put("failed", "Can't update to All project.");
			return result;
		}else{
			JSONArray datasArray = new JSONArray(datas);
			if(datasArray!=null&&datasArray.length()>0){
				JSONObject json = null;
				String defectId = null;
				MDefect mDefect;
				for(int i = 0 ; i < datasArray.length(); i++){
					json = (JSONObject) datasArray.get(i);
					defectId = json.get("defectId").toString();
					mDefect = (MDefect) findById(MDefect.class,Integer.valueOf(defectId),null);
					DicDataVO olddic =  CacheMgt.getDicData("1014",mDefect.getProject());
					String oldprojectDesc = olddic.getDicName();
					DicDataVO newdic =  CacheMgt.getDicData("1014",updateValue);
					String newprojectDesc = newdic.getDicName();
					ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
					changeLogBO.save(Integer.valueOf(defectId),ChangeLogBO.TAB_MDEFECT, "project","Project",oldprojectDesc,newprojectDesc);
					mDefect.setProject(updateValue);
					DBManager.getHT(null).save(mDefect);
					
				}
			}
			return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
		}
	}
	public Map<String, String> batchUpdateAssign(String datas,String updateValue) throws Exception{
		JSONArray datasArray = new JSONArray(datas);
		if(datasArray!=null&&datasArray.length()>0){
			JSONObject json = null;
			String defectId = null;
			MDefect mDefect;
			for(int i = 0 ; i < datasArray.length(); i++){
				json = (JSONObject) datasArray.get(i);
				defectId = json.get("defectId").toString();
				mDefect = (MDefect) findById(MDefect.class,Integer.valueOf(defectId),null);
				
				Property userCode = Property.forName("userCode");
				DetachedCriteria oldcriteria=DetachedCriteria.forClass(com.unieap.pojo.User.class).add(userCode.eq(mDefect.getAssignto()));
				List<com.unieap.pojo.User> oldusers = DBManager.getHT(null).findByCriteria(oldcriteria);
				String oldName = "";
				if(oldusers!=null && oldusers.size()>0){
					User user = oldusers.get(0);
					oldName = user.getUserName();
				}
				
				DetachedCriteria newcriteria=DetachedCriteria.forClass(com.unieap.pojo.User.class).add(userCode.eq(mDefect.getAssignto()));
				List<com.unieap.pojo.User> newusers = DBManager.getHT(null).findByCriteria(newcriteria);
				String newName = "";
				if(newusers!=null && newusers.size()>0){
					User user = newusers.get(0);
					newName = user.getUserName();
				}
				ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
				changeLogBO.save(Integer.valueOf(defectId),ChangeLogBO.TAB_MDEFECT, "assignto","Assignto",oldName,newName);
				mDefect.setAssignto(updateValue);
				DBManager.getHT(null).save(mDefect);
				
			}
		}
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public Map<String, String> batchUpdateStatus(String datas,String updateValue) throws Exception{
		JSONArray datasArray = new JSONArray(datas);
		if(datasArray!=null&&datasArray.length()>0){
			JSONObject json = null;
			String defectId = null;
			MDefect mDefect;
			for(int i = 0 ; i < datasArray.length(); i++){
				json = (JSONObject) datasArray.get(i);
				defectId = json.get("defectId").toString();
				mDefect = (MDefect) findById(MDefect.class,Integer.valueOf(defectId),null);
				DicDataVO olddic =  CacheMgt.getDicData("1003",mDefect.getProject());
				String oldprojectDesc = olddic.getDicName();
				DicDataVO newdic =  CacheMgt.getDicData("1003",updateValue);
				String newprojectDesc = newdic.getDicName();
				ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
				changeLogBO.save(Integer.valueOf(defectId),ChangeLogBO.TAB_MDEFECT, "status","Status",oldprojectDesc,newprojectDesc);
				mDefect.setStatus(updateValue);
				DBManager.getHT(null).save(mDefect);
				
			}
		}
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public Map<String, String> batchUpdatePriority(String datas,String updateValue) throws Exception{
		JSONArray datasArray = new JSONArray(datas);
		if(datasArray!=null&&datasArray.length()>0){
			JSONObject json = null;
			String defectId = null;
			MDefect mDefect;
			for(int i = 0 ; i < datasArray.length(); i++){
				json = (JSONObject) datasArray.get(i);
				defectId = json.get("defectId").toString();
				mDefect = (MDefect) findById(MDefect.class,Integer.valueOf(defectId),null);
				DicDataVO olddic =  CacheMgt.getDicData("1004",mDefect.getProject());
				String oldprojectDesc = olddic.getDicName();
				DicDataVO newdic =  CacheMgt.getDicData("1004",updateValue);
				String newprojectDesc = newdic.getDicName();
				ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
				changeLogBO.save(Integer.valueOf(defectId),ChangeLogBO.TAB_MDEFECT, "priority","Priority",oldprojectDesc,newprojectDesc);
				mDefect.setPriority(updateValue);
				DBManager.getHT(null).save(mDefect);
				
			}
		}
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public Map<String, String> batchUpdateNode(String datas,String updateValue) throws Exception{
		JSONArray datasArray = new JSONArray(datas);
		if(datasArray!=null&&datasArray.length()>0){
			JSONObject json = null;
			String defectId = null;
			MNote vo;
			for(int i = 0 ; i < datasArray.length(); i++){
				json = (JSONObject) datasArray.get(i);
				defectId = json.get("defectId").toString();
				vo = new MNote();
				vo.setDefectId(Integer.valueOf(defectId));
				vo.setDescription(updateValue);
				vo.setNoteId(getSequence(null,"unieap"));
				vo.setCreateDate(UnieapConstants.getDateTime(null));
				vo.setCreateBy(UnieapConstants.getUser().getUserCode());
				vo.setEnable(UnieapConstants.YES);
				DBManager.getHT(null).save(vo);
			}
		}
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}

}
