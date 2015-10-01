package com.unieap.ext;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.apps.esb.service.bss.BssServiceBO;
import com.apps.reuse.bo.ReuseSMSBO;
import com.apps.reuse.pojo.ReuseCustomer;
import com.apps.reuse.pojo.ReuseSmsNotify;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
@RequestMapping(value="ExtAction.do")  
public class ExtAction extends MultiActionController{
	
	@RequestMapping(value="ExtAction.do",params="method=sendSms",method = RequestMethod.GET)  
	public @ResponseBody String sendSms(ReuseSmsNotify vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ReuseSMSBO reuseSMSBO = (ReuseSMSBO) ServiceUtils.getBean("reuseSMSBO");
		reuseSMSBO.sendSms(vo);
		return UnieapConstants.SUCCESS;
	}
	@RequestMapping(value="ExtAction.do",params="method=smsVerify",method = RequestMethod.GET)  
	public @ResponseBody Map smsVerify(ReuseSmsNotify vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ReuseSMSBO reuseSMSBO = (ReuseSMSBO) ServiceUtils.getBean("reuseSMSBO");
		return reuseSMSBO.smsVerify(vo);
	}
	@RequestMapping(value="ExtAction.do",params="method=regisCustomer",method = RequestMethod.GET)  
	public @ResponseBody Map regisCustomer(ReuseSmsNotify smsVo,ReuseCustomer custVo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ReuseSMSBO reuseSMSBO = (ReuseSMSBO) ServiceUtils.getBean("reuseSMSBO");
		return reuseSMSBO.regisCustomer(smsVo, custVo);
	}
	
	@RequestMapping(value="ExtAction.do",params="method=index",method = RequestMethod.GET)  
	public ModelAndView login(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/ecare/index");
		return ma;
	}
	
	
	@RequestMapping(value="ExtAction.do",params="method=queryInfo",method = RequestMethod.POST)  
	public @ResponseBody String queryInfo(String parameters, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		BssServiceBO bssServiceBO = (BssServiceBO) ServiceUtils.getBean("bssServiceBO");
		String responsetInfoString = bssServiceBO.queryInfo(parameters);
		return responsetInfoString;
	}
	@RequestMapping(value="ExtAction.do",params="method=bizHandle",method = RequestMethod.POST)  
	public @ResponseBody String bizHandle(String parameters, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		BssServiceBO bssServiceBO = (BssServiceBO) ServiceUtils.getBean("bssServiceBO");
		String responsetInfoString = bssServiceBO.queryInfo(parameters);
		return responsetInfoString;
	}
	
}
