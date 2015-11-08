package com.apps.mcare;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apps.mcare.bo.ComplainBO;
import com.apps.mcare.bo.OfferingCategoryBO;
import com.apps.mcare.bo.ResourceConfigureBO;
import com.apps.mcare.pojo.AppOfferingCategory;
import com.apps.mcare.pojo.AppResconfig;
import com.apps.mcare.vo.ComplainVO;
import com.unieap.BaseController;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;

@Controller
@RequestMapping("mcareController.do")
public class McareController extends BaseController{
	@RequestMapping(params="method=offerConfigure")  
	public ModelAndView user(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/mcare/offeringconfig");
		return ma;
	}
	@RequestMapping(params="method=offerCategoryGrid")  
	public @ResponseBody String offerCategoryGrid(PaginationSupport page,AppOfferingCategory vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		OfferingCategoryBO offeringCategoryBO = (OfferingCategoryBO) ServiceUtils.getBean("offeringCategoryBO");
		offeringCategoryBO.getOfferingCategoryList(page, vo);
		return page.getJsonString();
	}
	@RequestMapping(params="method=complain")  
	public ModelAndView complain(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/mcare/complain");
		return ma;
	}
	@RequestMapping(params="method=complainGrid")  
	public @ResponseBody String complainGrid(PaginationSupport page, ComplainVO vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ComplainBO complainBO = (ComplainBO) ServiceUtils.getBean("complainBO");
		complainBO.getComplainList(page, vo);
		return page.getJsonString();
	}
	
	@RequestMapping(params="method=resourceConfigure")  
	public ModelAndView resourceConfigure(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/mcare/resourceconfigure");
		return ma;
	}
	@RequestMapping(params="method=groupGrid")  
	public @ResponseBody String groupGrid(PaginationSupport page,String groupName,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ResourceConfigureBO resourceConfigureBO = (ResourceConfigureBO) ServiceUtils.getBean("resourceConfigureBO");
		resourceConfigureBO.getGroupList(page, groupName);
		return page.getJsonString();
	}
	@RequestMapping(params="method=namesGrid")  
	public @ResponseBody String namesGrid(PaginationSupport page,String names,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ResourceConfigureBO resourceConfigureBO = (ResourceConfigureBO) ServiceUtils.getBean("resourceConfigureBO");
		resourceConfigureBO.getNamesList(page, names);
		return page.getJsonString();
	}
	@RequestMapping(params="method=resourceConfigureDeal")  
	public @ResponseBody Map<String, String> resourceConfigureDeal(String operType,AppResconfig vo, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ResourceConfigureBO resourceConfigureBO = (ResourceConfigureBO) ServiceUtils.getBean("resourceConfigureBO");
		Map<String, String> model = resourceConfigureBO.resourceConfigureDeal(operType, vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	
}
