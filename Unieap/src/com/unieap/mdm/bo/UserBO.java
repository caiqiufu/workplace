package com.unieap.mdm.bo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.mdm.vo.UserRoleVO;
import com.unieap.mdm.vo.UserVO;
import com.unieap.pojo.DicData;
import com.unieap.pojo.EmailSend;
import com.unieap.pojo.User;
import com.unieap.pojo.UserRole;
import com.unieap.tools.Propertyholder;

@Service("userBO")
public class UserBO extends BaseBO {
	public void getUserList(PaginationSupport page,UserVO uservo) throws Exception{
		DetachedCriteria criteria=DetachedCriteria.forClass(User.class);
		if(!StringUtils.equals(UnieapConstants.getUser().getUserCode(),UnieapConstants.UNIEAP)){
			criteria.add(Restrictions.eq("createBy",UnieapConstants.getUser().getUserCode()));
		}
		setCriteria(criteria,uservo);
		getPaginationDataByDetachedCriteria(criteria,page);
		page.po2vo(UserVO.class);
	}
	public Map<String, String> userDeal(String operType,User vo) throws Exception{
		if(StringUtils.equals(operType, UnieapConstants.ADD)){
			Map result = checkExist(UnieapConstants.getMessage("mdm.role.check.userCode", new Object[]{vo.getUserCode()}),"userCode",vo.getUserCode(),User.class,null);
			if(StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(), UnieapConstants.SUCCESS)){
				return save(vo);
			}else{
				return result;
			}
		}else if(StringUtils.equals(operType, UnieapConstants.MODIFY)){
			Map result = checkExistForUpdate(UnieapConstants.getMessage("mdm.role.check.userCode", new Object[]{vo.getUserCode()}),"userId",vo.getUserId(),"userCode",vo.getUserCode(),User.class,null);
			if(StringUtils.equals(result.get(UnieapConstants.ISSUCCESS).toString(), UnieapConstants.SUCCESS)){
				return update(vo);
			}else{
				return result;
			}
		}else if(StringUtils.equals(operType, UnieapConstants.DELETE)){
			return delete(vo);
		}else if(StringUtils.equals(operType, UnieapConstants.CHECKEXIST)){
			return checkExist(UnieapConstants.getMessage("mdm.role.check.userCode", new Object[]{vo.getUserCode()}),"userCode",vo.getUserCode(),User.class,null);
		}else{
			throw new Exception("Operation type["+operType+"] is wrong.");
		}
	}
	public Map<String, String> assignUserRole(String datas, User vo) throws Exception{
		JSONArray datasArray = new JSONArray(datas);
		if(datasArray!=null&&datasArray.length()>0){
			JSONObject json = null;
			String roleId = null;
			UserRole ur;
			for(int i = 0 ; i < datasArray.length(); i++){
				ur = new UserRole();
				json = (JSONObject) datasArray.get(i);
				roleId = json.get("roleId").toString();
				ur.setUserRoleId(getSequence(null,"unieap"));
				ur.setUserId(vo.getUserId());
				ur.setRoleId(Integer.valueOf(roleId));
				ur.setActiveFlag(UnieapConstants.YES);
				ur.setCreateDate(UnieapConstants.getDateTime(null));
				ur.setCreateBy(UnieapConstants.getUser().getUserCode());
				DBManager.getHT(null).save(ur);
			}
		}
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	
	public Map<String, String> save(User vo) throws Exception{
		vo.setUserId(getSequence(null,"unieap"));
		vo.setPassword(vo.getUserCode());
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(vo);
		assignDefaultDic(vo);
		sendEmailToNewUser(vo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public void assignDefaultDic(User vo){
		String sql = "INSERT INTO unieap.user_resource (user_res_id,user_id,res_id,res_type,res_attri1,res_attri2) SELECT unieap.NEXTVAL('unieap') user_res_id,"+vo.getUserId().toString()+" as user_id,dd.dic_code,'0' as res_type,dd.group_id as res_attri1,'' as res_attri2 FROM unieap.dic_data dd where dd.group_id <>1012 and dd.group_id<>1013 ";
		DBManager.getJT(null).execute(sql);
	}
	public Map<String, String> update(User vo) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		User ouser = DBManager.getHT(null).get(User.class, vo.getUserId());
		ouser.setEnable(vo.getEnable());
		ouser.setExpired(vo.getExpired());
		ouser.setLocked(vo.getLocked());
		ouser.setEmail(vo.getEmail());
		ouser.setUserCode(vo.getUserCode());
		ouser.setUserName(vo.getUserName());
		ouser.setRemark(vo.getRemark());
		ouser.setModifyDate(UnieapConstants.getDateTime(null));
		ouser.setModifyBy(UnieapConstants.getUser().getUserCode());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(ouser.getUserId(), ouser, "user",UnieapConstants.REUSE);
		DBManager.getHT(null).update(ouser);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public Map<String, String> delete(User vo){
		deleteById(User.class,vo.getUserId(),null);
		
		vo = DBManager.getHT(null).get(User.class,vo.getUserId());
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		vo.setEnable(UnieapConstants.NO);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public void userRoleGrid(PaginationSupport page,User user) throws Exception{
		String sql = "select ur.user_role_id as userRoleId,ur.role_id as userId,ur.role_id as roleId,r.role_code as roleCode,r.role_name as roleName" +
				" from unieap.user_role ur,unieap.role r where ur.role_id = r.role_id and ur.user_id = ? and ur.active_flag = ?";
		List items = DBManager.getJT(null).query(sql,new Object[]{user.getUserId(),UnieapConstants.YES},new EntityRowMapper(UserRoleVO.class));
		page.setItems(items);
		if(items==null){
			page.setTotalCount(0);
		}else{
			page.setTotalCount(items.size());
		}
	}
	public void chooseUserRoleGrid(PaginationSupport page,UserRoleVO vo) throws Exception{
		String sql = "select r.role_id as roleId, r.role_code as roleCode, r.role_name as roleName from unieap.role r where not exists (select ur.role_id from unieap.user_role ur where  ur.role_id = r.role_id and ur.user_id = ? and ur.active_flag = ?)";
		List items = DBManager.getJT(null).query(sql,new Object[]{vo.getUserId(),UnieapConstants.YES},new EntityRowMapper(UserRoleVO.class));
		page.setItems(items);
		if(items==null){
			page.setTotalCount(0);
		}else{
			page.setTotalCount(items.size());
		}
	}
	
	
	public void getDicDataList(PaginationSupport page,DicData dicData) throws Exception{
		DetachedCriteria criteria=DetachedCriteria.forClass(DicData.class);
		setCriteria(criteria,dicData);
		getPaginationDataByDetachedCriteria(criteria,page);
	}
	public Map<String, String> save(DicData vo) throws Exception{
		vo.setDicId(getSequence(null,"unieap"));
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	
	public Map<String, String> update(DicData vo){
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public Map<String, String> delete(DicData vo){
		deleteById(DicData.class,vo.getDicId(),null);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	
	public Map<String, String> deleteUserRole(String operType,UserRole ur) throws Exception{
		deleteById(UserRole.class,ur.getUserRoleId(),null);
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public void sendEmailToNewUser(User vo) throws Exception{
		StringBuffer sb = new StringBuffer();
		sb.append("[").append("Unieap mantis system").append("]").append(" please active you new mantis account");
		StringBuffer content = new StringBuffer();
		String en =System.getProperty("line.separator");
		content.append("Hello ").append(vo.getUserName()).append(",").append(en);
		content.append(en);
		content.append("=================================================").append(en);
		content.append("	User Code:").append(vo.getUserCode()).append(en);
		content.append("	Password:").append(vo.getPassword()).append(en);
		content.append("=================================================").append(en);
		content.append("	Please login mantis to change you initial passwors.").append(en);
		content.append("=================================================").append(en);
		content.append("	Mantis system link:").append(Propertyholder.getContextProperty("server_address")).append(en);
		content.append("=================================================").append(en);
		content.append("Unieap mantis system.").append(en);
		content.append("Thanks").append(en);
		content.append("caiqiufu@huawei.com").append(en);
		content.append("=================================================").append(en);
		EmailSend emailSend = new EmailSend();
		emailSend.setContent(content.toString());
		emailSend.setCreateDate(UnieapConstants.getDateTime(null));
		emailSend.setDescription(null);
		emailSend.setEmail(vo.getEmail());
		emailSend.setSendId(getSequence(null,"unieap"));
		emailSend.setStatus("2");
		emailSend.setSubject(sb.toString());
		emailSend.setTimes(Integer.valueOf(0));
		DBManager.getHT(null).save(emailSend);
	}

}
