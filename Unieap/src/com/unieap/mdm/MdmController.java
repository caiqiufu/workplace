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

import com.unieap.BaseController;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.base.vo.TreeVO;
import com.unieap.mdm.bo.DicBO;
import com.unieap.mdm.bo.RoleBO;
import com.unieap.mdm.bo.UserBO;
import com.unieap.mdm.vo.DicDataVO;
import com.unieap.mdm.vo.UserRoleVO;
import com.unieap.pojo.DicDataTree;
import com.unieap.pojo.Role;
import com.unieap.pojo.User;
import com.unieap.pojo.UserRole;
@RequestMapping(value="MdmController.do") 
public class MdmController extends BaseController{
	@InitBinder
	protected void initBinder(HttpServletRequest request,
	                              ServletRequestDataBinder binder) throws Exception {
	    //对于需要转换为Date类型的属性，使用DateEditor进行处理
	    binder.registerCustomEditor(Date.class, (PropertyEditor) new com.unieap.exttools.DateEditor());
	}
	/**
	 * Dictionary management index page
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MdmController.do",params="method=dic",method = RequestMethod.GET)  
	public ModelAndView dicGroupList(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/base/mdm/dic/dic");
		return ma;
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
	 * Dictionary management
	 * @param operType
	 * @param dicDataTree
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MdmController.do",params="method=dicDataDeal",method = RequestMethod.POST)  
	public @ResponseBody Map dicDataDeal(String operType,DicDataTree dicDataTree, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DicBO dicBO = (DicBO) ServiceUtils.getBean("dicBO");
		Map model = dicBO.dicDataDeal(operType,dicDataTree);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
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
	 * User management
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
	public @ResponseBody String userGrid(PaginationSupport page,User vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO userBO = (UserBO) ServiceUtils.getBean("userBO");
		userBO.getUserList(page, vo);
		return page.getJsonString();
	}
	@RequestMapping(value="MdmController.do",params="method=userDeal",method = RequestMethod.POST)  
	public @ResponseBody Map userDeal(String operType,User user,UserRole ur, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO userBO = (UserBO) ServiceUtils.getBean("userBO");
		Map model = userBO.userDeal(operType,user,ur);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	
	@RequestMapping(value="MdmController.do",params="method=userRoleGrid",method = RequestMethod.GET)  
	public @ResponseBody String userRoleGrid(PaginationSupport page,User user,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO userBO = (UserBO) ServiceUtils.getBean("userBO");
		userBO.userRoleGrid(page, user);
		return page.getJsonString();
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
	
	
	
	
	/**
	 * Role management
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
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
	@RequestMapping(value="MdmController.do",params="method=assignDicResource",method = RequestMethod.POST)  
	public @ResponseBody Map assignDicResource(String datas,Role role, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		RoleBO bo = (RoleBO) ServiceUtils.getBean("roleBO");
		Map model = bo.assignDicResource(datas,role);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	
	@RequestMapping(value="MdmController.do",params="method=management",method = RequestMethod.GET)  
	public ModelAndView management(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/base/mdm/management");
		return ma;
	}
	
	
	/**
	 * get Dictionary data list by parentId, the dictionary data from cache
	 * @param isOptional
	 * @param parentId
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MdmController.do",params="method=getDicData",method = RequestMethod.GET)  
	public @ResponseBody String getDicData(String isOptional,String parentCode, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		Map<String,DicDataVO> dic = CacheMgt.getDicData(parentCode);
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
				jac.put("dicName", val.getDicName());
				jac.put("groupName", val.getGroupName());
				ja.put(jac);
			}
		}
		String dicString = ja.toString();
		return dicString;
	}
	/**
	 * get common dictionary list by ajax, get dictionary from database
	 * @param groupId
	 * @param whereby
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value="MdmController.do",params="method=getCommDicList",method = RequestMethod.GET)  
	public @ResponseBody String getCommDicList(Integer parentCode,String whereby,String isOptional, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DicBO dicBO = (DicBO) ServiceUtils.getBean("dicBO");
		List<?> datas = dicBO.getCommDicList(parentCode,whereby);
		JSONArray ja = new JSONArray();
		if(StringUtils.equals(isOptional, UnieapConstants.YES)){
			JSONObject jac = new JSONObject();
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
				jac.put("dicCode", data.getDicCode());
				jac.put("dicName", data.getDicName());
				jac.put("parentCode", data.getGroupCode());
				ja.put(jac);
			}
		}
		String dicString = ja.toString();
		return dicString;
	}
}
