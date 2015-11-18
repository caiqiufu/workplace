package com.unieap.mdm;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.unieap.BaseController;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.base.vo.TreeVO;
import com.unieap.mdm.bo.DicBO;
import com.unieap.mdm.bo.RefreshCacheBO;
import com.unieap.mdm.bo.RoleBO;
import com.unieap.mdm.bo.UserBO;
import com.unieap.mdm.vo.DicDataVO;
import com.unieap.mdm.vo.UserRoleVO;
import com.unieap.pojo.DicDataTree;
import com.unieap.pojo.Role;
import com.unieap.pojo.User;
import com.unieap.pojo.UserRole;
@Controller
@RequestMapping("mdmController.do")
public class MdmController extends BaseController{
	/**
	 * Dictionary management index page
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=dic")  
	public ModelAndView dicGroupList(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/base/mdm/dic/dic");
		Enumeration<String> names = request.getAttributeNames();
		while (names.hasMoreElements()){
		    Object o= names.nextElement();
		    System.out.println(o.toString());
		}
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
	@RequestMapping(params="method=getDicTreeData")  
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
	@RequestMapping(params="method=dicDataDeal")  
	public @ResponseBody Map<String, String> dicDataDeal(String operType,DicDataTree dicDataTree, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		DicBO dicBO = (DicBO) ServiceUtils.getBean("dicBO");
		Map<String, String> model = dicBO.dicDataDeal(operType,dicDataTree);
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
	@RequestMapping(params="method=dicRoleGrid")  
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
	@RequestMapping(params="method=user")  
	public ModelAndView user(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/base/mdm/user/user");
		return ma;
	}
	@RequestMapping(params="method=userGrid")  
	public @ResponseBody String userGrid(PaginationSupport page,User vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO userBO = (UserBO) ServiceUtils.getBean("userBO");
		userBO.getUserList(page, vo);
		return page.getJsonString();
	}
	@RequestMapping(params="method=userDeal")  
	public @ResponseBody Map<String, String> userDeal(String operType,User user,UserRole ur, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO userBO = (UserBO) ServiceUtils.getBean("userBO");
		Map<String, String> model = userBO.userDeal(operType,user,ur);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	
	@RequestMapping(params="method=userRoleGrid")  
	public @ResponseBody String userRoleGrid(PaginationSupport page,User user,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO userBO = (UserBO) ServiceUtils.getBean("userBO");
		userBO.userRoleGrid(page, user);
		return page.getJsonString();
	}
	
	@RequestMapping(params="method=chooseUserRoleGrid")  
	public @ResponseBody String chooseUserRoleGrid(PaginationSupport page,UserRoleVO vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO bo = (UserBO) ServiceUtils.getBean("userBO");
		bo.chooseUserRoleGrid(page, vo);
		return page.getJsonString();
	}
	
	@RequestMapping(params="method=assignUserRole")  
	public @ResponseBody Map<String, String> assignUserRole(String datas,User user, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		UserBO userBO = (UserBO) ServiceUtils.getBean("userBO");
		Map<String, String> model = userBO.assignUserRole(datas,user);
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
	@RequestMapping(params="method=role")  
	public ModelAndView role(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/base/mdm/role/role");
		return ma;
	}
	@RequestMapping(params="method=roleGrid")  
	public @ResponseBody String roleGrid(PaginationSupport page,Role vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		RoleBO bo = (RoleBO) ServiceUtils.getBean("roleBO");
		bo.getRoleList(page, vo);
		return page.getJsonString();
	}
	@RequestMapping(params="method=roleDeal")  
	public @ResponseBody Map<String, String> roleDeal(String operType,Role role, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		RoleBO bo = (RoleBO) ServiceUtils.getBean("roleBO");
		Map<String, String> model = bo.roleDeal(operType,role);
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
	@RequestMapping(params="method=getRoleDicTreeData")  
	public @ResponseBody String getRoleDicTreeData(TreeVO treeVO,int roleId, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		RoleBO bo = (RoleBO) ServiceUtils.getBean("roleBO");
		return bo.getRoleDicTreeData(treeVO,roleId);
	}
	@RequestMapping(params="method=assignDicResource")  
	public @ResponseBody Map<String, String> assignDicResource(String datas,Role role, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		RoleBO bo = (RoleBO) ServiceUtils.getBean("roleBO");
		Map<String, String> model = bo.assignDicResource(datas,role);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	
	@RequestMapping(params="method=management")  
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
	@RequestMapping(params="method=getDicData")  
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
	@RequestMapping(params="method=getCommDicList")  
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
	
	/**
	 * Role management
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(params="method=refreshCache")  
	public ModelAndView refreshCache(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/base/mdm/refreshcache/refreshcache");
		return ma;
	}
	@RequestMapping(params="method=refreshDeal")  
	public @ResponseBody Map<String, String> refreshDeal(String id, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		RefreshCacheBO bo = (RefreshCacheBO) ServiceUtils.getBean("refreshCacheBO");
		Map<String, String> model = bo.refreshDeal(id);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
}
