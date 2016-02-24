package com.apps.mcare;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apps.esb.service.bss.vo.macre.customerprofile.CustomerProfileVO;
import com.apps.mcare.bo.McareBO;
import com.unieap.BaseController;
import com.unieap.UnieapConstants;

@Controller
@RequestMapping("mCareController.do")
public class MCareController extends BaseController{
	@Autowired
	public McareBO mcareBO;

	@RequestMapping(params="method=homepage")  
	public ModelAndView homepage(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/mcare/homepage");
		return ma;
	}
	@RequestMapping(params="method=mybalance")  
	public ModelAndView mybalance(String menuTitle,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		CustomerProfileVO vo = (CustomerProfileVO)request.getSession().getAttribute(UnieapConstants.USER);
		ModelAndView ma = new ModelAndView("apps/mcare/360view/mybalance");
		mcareBO.setCommonInfo(vo, ma);
		ma.addObject("menuTitle", menuTitle);
		return mcareBO.getMyBalance(vo, ma);
	}
	@RequestMapping(params="method=myprofile")  
	public ModelAndView myprofile(String menuTitle,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		CustomerProfileVO vo = (CustomerProfileVO)request.getSession().getAttribute(UnieapConstants.USER);
		ModelAndView ma = new ModelAndView("apps/mcare/360view/myprofile");
		mcareBO.setCommonInfo(vo, ma);
		ma.addObject("menuTitle", menuTitle);
		return mcareBO.getMyProfile(vo, ma);
	}
	@RequestMapping(params="method=changepassword")  
	public ModelAndView changepassword(String menuTitle,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		CustomerProfileVO vo = (CustomerProfileVO)request.getSession().getAttribute(UnieapConstants.USER);
		ModelAndView ma = new ModelAndView("apps/mcare/360view/changepassword");
		mcareBO.setCommonInfo(vo, ma);
		ma.addObject("menuTitle", menuTitle);
		ma.addObject("serviceNumber", vo.getServiceNumber());
		return ma;
	}
	@RequestMapping(params="method=saveChangepassword")  
	public Map<String, String> saveChangepassword(String serviceNumber,String oldPassword,String newPassword,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		CustomerProfileVO vo = (CustomerProfileVO)request.getSession().getAttribute(UnieapConstants.USER);
		Map<String, String> model = mcareBO.saveChangepassword(vo,serviceNumber, oldPassword, newPassword);
		return model;
	}
	@RequestMapping(params="method=mysimcard")  
	public ModelAndView mysimcard(String menuTitle,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		CustomerProfileVO vo = (CustomerProfileVO)request.getSession().getAttribute(UnieapConstants.USER);
		ModelAndView ma = new ModelAndView("apps/mcare/360view/mysimcard");
		mcareBO.setCommonInfo(vo, ma);
		ma.addObject("menuTitle", menuTitle);
		ma.addObject("customerName", vo.getCustomerName());
		ma.addObject("serviceNumber", vo.getServiceNumber());
		return ma;
	}
	@RequestMapping(params="method=usagehistory")  
	public ModelAndView usagehistory(String menuTitle,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		CustomerProfileVO vo = (CustomerProfileVO)request.getSession().getAttribute(UnieapConstants.USER);
		ModelAndView ma = new ModelAndView("apps/mcare/360view/usagehistory");
		mcareBO.setCommonInfo(vo, ma);
		ma.addObject("menuTitle", menuTitle);
		return mcareBO.getMySimCard(vo, ma);
	}
	@RequestMapping(params="method=getUsageHistoryList")  
	public @ResponseBody String getUsageHistoryList(String serviceNumber,String page,String rowNum,String totalNum,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		CustomerProfileVO vo = (CustomerProfileVO)request.getSession().getAttribute(UnieapConstants.USER);
		totalNum = (String)request.getSession().getAttribute("totalNum");
		Map<String, String> model = mcareBO.getUsageHistoryList(vo,vo.getServiceNumber(), page, rowNum,totalNum);
		request.getSession().setAttribute("totalNum",  model.get("totalNum"));
		request.getParameterMap();
		return model.get("datas");
	}
}
