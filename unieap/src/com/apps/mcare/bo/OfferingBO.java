package com.apps.mcare.bo;

import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Service;

import com.apps.mcare.pojo.AppOffering;
import com.apps.mcare.pojo.AppOfferingCategory;
import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;
import com.unieap.mdm.bo.ChangeLogBO;
import com.unieap.mdm.vo.UserRoleVO;
import com.unieap.pojo.EmailSend;
import com.unieap.pojo.User;
import com.unieap.pojo.UserRole;
import com.unieap.tools.Propertyholder;

@Service("OfferingBO")
public class OfferingBO extends BaseBO {
	public void getOfferingCategoryList(PaginationSupport page, AppOfferingCategory vo) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(AppOfferingCategory.class);
		setCriteria(criteria, vo);
		getPaginationDataByDetachedCriteria(criteria, page);
	}

	public void getOfferingList(PaginationSupport page, AppOffering vo) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(AppOffering.class);
		Property categoryIdProperty = Property.forName("categoryId");
		criteria.add(categoryIdProperty.eq(vo.getCategoryId()));
		getPaginationDataByDetachedCriteria(criteria, page);
	}

	public Map<String, String> offeringDeal(String operType, AppOffering vo) throws Exception {
		if (StringUtils.equals(operType, UnieapConstants.ADD)) {
			return save(vo);
		} else if (StringUtils.equals(operType, UnieapConstants.MODIFY)) {
			return update(vo);
		} else {
			throw new Exception(UnieapConstants.getMessage("comm.operation.error", new Object[] { operType }));
		}
	}

	public Map<String, String> save(AppOffering vo) throws Exception {
		vo.setId(getSequence(null, null));
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		vo.setCreateBy(UnieapConstants.getUser().getUserCode());
		DBManager.getHT(null).save(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public Map<String, String> update(AppOffering vo)
			throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException {
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getId(), vo, "appOffering", UnieapConstants.MODIFY, UnieapConstants.UNIEAP);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public Map<String, String> delete(User vo) {
		vo = DBManager.getHT(null).get(User.class, vo.getUserId());
		vo.setModifyDate(UnieapConstants.getDateTime(null));
		vo.setModifyBy(UnieapConstants.getUser().getUserCode());
		vo.setEnable(UnieapConstants.NO);
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(vo.getUserId(), "user", UnieapConstants.MODIFY, "enable",
				UnieapConstants.getMessage("mdm.user.display.enable"), UnieapConstants.YES, UnieapConstants.NO,
				UnieapConstants.UNIEAP);
		DBManager.getHT(null).update(vo);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public void userRoleGrid(PaginationSupport page, User user) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select ur.user_role_id as userRoleId,ur.role_id as userId,ur.role_id as roleId,");
		sql.append("r.role_code as roleCode,r.role_name as roleName");
		sql.append(" from unieap.user_role ur,unieap.role r where ur.role_id = r.role_id ");
		sql.append("and ur.user_id = ? and ur.active_flag = ?");
		List items = DBManager.getJT(null).query(sql.toString(), new Object[] { user.getUserId(), UnieapConstants.YES },
				new EntityRowMapper(UserRoleVO.class));
		page.setItems(items);
		if (items == null) {
			page.setTotalCount(0);
		} else {
			page.setTotalCount(items.size());
		}
	}

	public void chooseUserRoleGrid(PaginationSupport page, UserRoleVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("select r.role_id as roleId, r.role_code as roleCode, r.role_name as roleName ");
		sql.append(" from unieap.role r where not exists (select ur.role_id from unieap.user_role ur ");
		sql.append(" where  ur.role_id = r.role_id and ur.user_id = ? and ur.active_flag = ?)");
		List items = DBManager.getJT(null).query(sql.toString(), new Object[] { vo.getUserId(), UnieapConstants.YES },
				new EntityRowMapper(UserRoleVO.class));
		page.setItems(items);
		if (items == null) {
			page.setTotalCount(0);
		} else {
			page.setTotalCount(items.size());
		}
	}

	public Map<String, String> deleteUserRole(UserRole ur) throws Exception {
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(ur.getUserRoleId(), "user_role", UnieapConstants.DELETE, "userRoleId",
				UnieapConstants.getMessage("comm.id"), null, null, UnieapConstants.UNIEAP);

		deleteById(UserRole.class, ur.getUserRoleId(), null);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public void sendEmailToNewUser(User vo) throws Exception {
		StringBuffer sb = new StringBuffer();
		sb.append("[").append("unieap system").append("]").append(" please active you new account");
		StringBuffer content = new StringBuffer();
		String en = System.getProperty("line.separator");
		content.append("Hello ").append(vo.getUserName()).append(",").append(en);
		content.append(en);
		content.append("=================================================").append(en);
		content.append("	User Code:").append(vo.getUserCode()).append(en);
		content.append("	Password:").append(vo.getPassword()).append(en);
		content.append("=================================================").append(en);
		content.append("	Please login mantis to change you initial passwors.").append(en);
		content.append("=================================================").append(en);
		content.append("	Mantis system link:").append(Propertyholder.getContextProperty("server_address"))
				.append(en);
		content.append("=================================================").append(en);
		content.append("unieap mantis system.").append(en);
		content.append("Thanks").append(en);
		content.append("caiqiufu@huawei.com").append(en);
		content.append("=================================================").append(en);
		EmailSend emailSend = new EmailSend();
		emailSend.setContent(content.toString());
		emailSend.setCreateDate(UnieapConstants.getDateTime(null));
		emailSend.setDescription(null);
		emailSend.setEmail(vo.getEmail());
		emailSend.setSendId(getSequence(null, "unieap"));
		emailSend.setStatus("2");
		emailSend.setSubject(sb.toString());
		emailSend.setTimes(Integer.valueOf(0));
		DBManager.getHT(null).save(emailSend);
	}

}
