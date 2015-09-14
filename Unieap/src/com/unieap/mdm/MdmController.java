package com.unieap.mdm;

import java.beans.PropertyEditor;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.base.vo.TreeVO;
import com.unieap.mdm.bo.DicBO;
import com.unieap.mdm.bo.RoleBO;
import com.unieap.mdm.bo.UserBO;
import com.unieap.mdm.vo.DicDataVO;
import com.unieap.mdm.vo.DicResourceVO;
import com.unieap.mdm.vo.UserRoleVO;
import com.unieap.mdm.vo.UserVO;
import com.unieap.pojo.Area;
import com.unieap.pojo.DicDataTree;
import com.unieap.pojo.DicGroup;
import com.unieap.pojo.Role;
import com.unieap.pojo.RoleResource;
import com.unieap.pojo.User;
import com.unieap.pojo.UserRole;
@RequestMapping(value="MdmController.do") 
public class MdmController extends MultiActionController{
	@InitBinder
	protected void initBinder(HttpServletRequest request,
	                              ServletRequestDataBinder binder) throws Exception {
	    //对于需要转换为Date类型的属性，使用DateEditor进行处理
	    binder.registerCustomEditor(Date.class, (PropertyEditor) new com.unieap.exttools.DateEditor());
	}
	@RequestMapping(value="MdmController.do",params="method=dic",method = RequestMethod.GET)  
	public ModelAndView dicGroupList(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/base/mdm/dic/dic");
		return ma;
	}
	/**
	 * mdm main menu
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MdmController.do",params="method=menu",method = RequestMethod.GET)  
	public ModelAndView menu(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/base/mdm/menu");
		return ma;
	}
	
	@RequestMapping(value="MdmController.do",params="method=dicGroupGrid",method = RequestMethod.GET)  
	public @ResponseBody String dicGroupGrid(PaginationSupport page,DicGroup dicGroup,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DicBO dicBO = (DicBO) ServiceUtils.getBean("dicBO");
		dicBO.getDicGroupList(page, dicGroup);
		return page.getJsonString();
	}
	@RequestMapping(value="MdmController.do",params="method=dicGroupDeal",method = RequestMethod.POST)  
	public @ResponseBody Map dicGroupDeal(String operType,DicGroup dicGroup, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DicBO dicBO = (DicBO) ServiceUtils.getBean("dicBO");
		//Map model = dicBO.dicGroupDeal(operType,dicGroup);
		//model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		//return model;
		return null;
	}
	@RequestMapping(value="MdmController.do",params="method=dicDataGrid",method = RequestMethod.GET)  
	public @ResponseBody String dicDataGrid(PaginationSupport page,DicDataVO vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DicBO dicBO = (DicBO) ServiceUtils.getBean("dicBO");
		dicBO.getDicDataList(page, vo);
		return page.getJsonString();
	}
	@RequestMapping(value="MdmController.do",params="method=dicDataDeal",method = RequestMethod.POST)  
	public @ResponseBody Map dicDataDeal(String operType,DicDataTree dicDataTree, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DicBO dicBO = (DicBO) ServiceUtils.getBean("dicBO");
		Map model = dicBO.dicDataDeal(operType,dicDataTree);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	
	@RequestMapping(value="MdmController.do",params="method=deleteUserRole",method = RequestMethod.POST)  
	public @ResponseBody Map deleteUserRole(String operType,UserRole ur, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO userBO = (UserBO) ServiceUtils.getBean("userBO");
		Map model = userBO.deleteUserRole(operType,ur);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	/**
	 * manage user
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MdmController.do",params="method=user",method = RequestMethod.GET)  
	public ModelAndView user(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/base/mdm/user/user");
		return ma;
	}
	@RequestMapping(value="MdmController.do",params="method=userGrid",method = RequestMethod.GET)  
	public @ResponseBody String userGrid(PaginationSupport page,UserVO uservo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO userBO = (UserBO) ServiceUtils.getBean("userBO");
		userBO.getUserList(page, uservo);
		return page.getJsonString();
	}
	@RequestMapping(value="MdmController.do",params="method=userDeal",method = RequestMethod.POST)  
	public @ResponseBody Map userDeal(String operType,User user, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO userBO = (UserBO) ServiceUtils.getBean("userBO");
		Map model = userBO.userDeal(operType,user);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	
	@RequestMapping(value="MdmController.do",params="method=chooseUserRoleGrid",method = RequestMethod.GET)  
	public @ResponseBody String chooseUserRoleGrid(PaginationSupport page,UserRoleVO vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO bo = (UserBO) ServiceUtils.getBean("userBO");
		bo.chooseUserRoleGrid(page, vo);
		return page.getJsonString();
	}
	
	@RequestMapping(value="MdmController.do",params="method=assignUserRole",method = RequestMethod.POST)  
	public @ResponseBody Map assignUserRole(String datas,User user, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO userBO = (UserBO) ServiceUtils.getBean("userBO");
		Map model = userBO.assignUserRole(datas,user);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	
	@RequestMapping(value="MdmController.do",params="method=userRoleGrid",method = RequestMethod.GET)  
	public @ResponseBody String userRoleGrid(PaginationSupport page,User user,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO userBO = (UserBO) ServiceUtils.getBean("userBO");
		userBO.userRoleGrid(page, user);
		return page.getJsonString();
	}
	
	@RequestMapping(value="MdmController.do",params="method=role",method = RequestMethod.GET)  
	public ModelAndView role(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/base/mdm/role/role");
		return ma;
	}
	@RequestMapping(value="MdmController.do",params="method=roleGrid",method = RequestMethod.GET)  
	public @ResponseBody String roleGrid(PaginationSupport page,Role vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		RoleBO bo = (RoleBO) ServiceUtils.getBean("roleBO");
		bo.getRoleList(page, vo);
		return page.getJsonString();
	}
	@RequestMapping(value="MdmController.do",params="method=roleDeal",method = RequestMethod.POST)  
	public @ResponseBody Map roleDeal(String operType,Role role, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		RoleBO bo = (RoleBO) ServiceUtils.getBean("roleBO");
		Map model = bo.roleDeal(operType,role);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	@RequestMapping(value="MdmController.do",params="method=roleDicDataGrid",method = RequestMethod.GET)  
	public @ResponseBody String roleDicDataGrid(PaginationSupport page,Role role,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		RoleBO bo = (RoleBO) ServiceUtils.getBean("roleBO");
		bo.roleDicDataGrid(page, role);
		return page.getJsonString();
	}
	
	@RequestMapping(value="MdmController.do",params="method=chooseDicDataGrid",method = RequestMethod.GET)  
	public @ResponseBody String chooseDicDataGrid(PaginationSupport page,DicResourceVO vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		RoleBO bo = (RoleBO) ServiceUtils.getBean("roleBO");
		bo.chooseDicDataGrid(page, vo);
		return page.getJsonString();
	}
	
	@RequestMapping(value="MdmController.do",params="method=assignDicResource",method = RequestMethod.POST)  
	public @ResponseBody Map assignDicResource(String datas,Role role, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		RoleBO bo = (RoleBO) ServiceUtils.getBean("roleBO");
		Map model = bo.assignDicResource(datas,role);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	@RequestMapping(value="MdmController.do",params="method=deleteRoleResource",method = RequestMethod.POST)  
	public @ResponseBody Map deleteRoleResource(String operType,RoleResource rr, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		RoleBO bo = (RoleBO) ServiceUtils.getBean("roleBO");
		Map model = bo.deleteRoleResource(operType,rr);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	
	@RequestMapping(value="MdmController.do",params="method=management",method = RequestMethod.GET)  
	public ModelAndView management(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/base/mdm/management");
		return ma;
	}
	
	
	
	@RequestMapping(value="MdmController.do",params="method=getDicData",method = RequestMethod.GET)  
	public @ResponseBody String getDicData(String isOptional,String groupId, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		Map<String,DicDataVO> dic = CacheMgt.getDicData(groupId);
		JSONArray ja = new JSONArray();
		if(StringUtils.equals(isOptional, UnieapConstants.YES)){
			JSONObject jac = new JSONObject();
			jac.put("dicCode","");
			jac.put("dicName","Please select...");
			ja.put(jac);
		}
		if(dic!=null&&!dic.isEmpty()){
			Iterator<String> iter = dic.keySet().iterator();
			while (iter.hasNext()){
				String key = (String) iter.next();
				DicDataVO val = dic.get(key);
				JSONObject jac = new JSONObject();
				jac.put("dicCode", val.getDicId());
				jac.put("dicName", val.getDicName());
				jac.put("groupName", val.getGroupName());
				ja.put(jac);
			}
		}
		String dicString = ja.toString();
		return dicString;
	}
	/**
	 * get Parent Name
	 * @param dicId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MdmController.do",params="method=getParentName",method = RequestMethod.POST)  
	public @ResponseBody Map<?, ?> getParentName(Integer dicId, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DicBO dicBO = (DicBO) ServiceUtils.getBean("dicBO");
		return dicBO.getParentName(dicId);
	}
	/**
	 * get common dic list by ajax
	 * @param groupId
	 * @param whereby
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MdmController.do",params="method=getCommDicList",method = RequestMethod.GET)  
	public @ResponseBody String getCommDicList(Integer groupId,String whereby,String isOptional, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DicBO dicBO = (DicBO) ServiceUtils.getBean("dicBO");
		List<?> datas = dicBO.getCommDicList(groupId,whereby);
		JSONArray ja = new JSONArray();
		if(StringUtils.equals(isOptional, UnieapConstants.YES)){
			JSONObject jac = new JSONObject();
			jac.put("dicId", "");
			jac.put("dicCode", "");
			jac.put("dicName","Please select...");
			jac.put("parentCode", "");
			ja.put(jac);
		}
		if(datas!=null&&datas.size()>0){
			DicDataVO data;
			for(int i = 0 ; i < datas.size() ; i++){
				data = (DicDataVO)datas.get(i);
				JSONObject jac = new JSONObject();
				jac.put("dicId",data.getDicId());
				jac.put("dicCode", data.getDicCode());
				jac.put("dicName", data.getDicName());
				jac.put("parentCode", data.getParentCode());
				ja.put(jac);
			}
		}
		String dicString = ja.toString();
		return dicString;
	}
	/**
	 * get address dic data
	 */
	@RequestMapping(value="MdmController.do",params="method=getAddressDicData",method = RequestMethod.GET)  
	public @ResponseBody String getAddressDicData(Area area,String isOptional, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DicBO dicBO = (DicBO) ServiceUtils.getBean("dicBO");
		List<Area> datas = dicBO.getAddressDicData(area);
		JSONArray ja = new JSONArray();
		if(StringUtils.equals(isOptional, UnieapConstants.YES)){
			JSONObject jac = new JSONObject();
			jac.put("dicCode","");
			jac.put("dicName","Please select...");
			jac.put("level","");
			ja.put(jac);
		}
		if(datas!=null&&datas.size()>0){
			Area data;
			for(int i = 0 ; i < datas.size() ; i++){
				data = datas.get(i);
				JSONObject jac = new JSONObject();
				jac.put("dicCode", data.getCode());
				jac.put("dicName", data.getName());
				jac.put("level", data.getLevel());
				ja.put(jac);
			}
		}
		String dicString = ja.toString();
		return dicString;
	}
	/**
	 * get dic tree data
	 * @param isOptional
	 * @param groupId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MdmController.do",params="method=getDicTreeData",method = RequestMethod.GET)  
	public @ResponseBody String getDicTreeData(TreeVO treeVO, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DicBO dicBO = (DicBO) ServiceUtils.getBean("dicBO");
		return dicBO.getDicTreeData(treeVO);
	}
	/**
	 * query role by dic id
	 * @param page
	 * @param dicId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MdmController.do",params="method=dicRoleGrid",method = RequestMethod.GET)  
	public @ResponseBody String dicRoleGrid(PaginationSupport page,String dicCode,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DicBO dicBO = (DicBO) ServiceUtils.getBean("dicBO");
		dicBO.getDicRoleGrid(page,dicCode);
		return page.getJsonString();
	}
	/**
	 * get dic tree filter by role id
	 * @param treeVO
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MdmController.do",params="method=getRoleDicTreeData",method = RequestMethod.GET)  
	public @ResponseBody String getRoleDicTreeData(TreeVO treeVO,int roleId, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		RoleBO bo = (RoleBO) ServiceUtils.getBean("roleBO");
		return bo.getRoleDicTreeData(treeVO,roleId);
	}
}
