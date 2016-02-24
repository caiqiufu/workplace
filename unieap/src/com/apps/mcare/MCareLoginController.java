package com.apps.mcare;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apps.esb.service.bss.handler.mshop.UserLogin;
import com.apps.esb.service.bss.vo.macre.customerprofile.CustomerProfileVO;
import com.apps.mcare.bo.McareBO;
import com.unieap.BaseController;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.tools.JSONUtils;
import com.unieap.verifycode.bo.VerifyCodeBO;

@Controller
@RequestMapping("mCareLoginController.do")
public class MCareLoginController extends BaseController{
	@RequestMapping(params="method=mcarelogin")  
	public ModelAndView mcareLogin(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/mcare/mcarelogin");
		return ma;
	}
	
	@Autowired
	public McareBO mcareBO;

	@RequestMapping(params = "method=login")
	public @ResponseBody  Map<String, String>  login(String serviceNumber,String password,String checkNum,HttpServletRequest request, HttpServletResponse response) throws Exception {
		VerifyCodeBO verifyCodeBO = (VerifyCodeBO) ServiceUtils.getBean("verifyCodeBO");
		boolean checkFlag = verifyCodeBO.checkVerifyCode(checkNum, request, response);
		if(checkFlag){
			CustomerProfileVO vo = mcareBO.login(serviceNumber, password);
			if(vo==null){
				Map<String, String> model = new HashMap<String, String>();
				model.put(UnieapConstants.SUCCESS, UnieapConstants.SUCCESS);
				model.put(UnieapConstants.ISSUCCESS, UnieapConstants.FAILED);
				model.put(UnieapConstants.ERRORCODE, "20004");
				model.put(UnieapConstants.ERRORDESC, UnieapConstants.getMessage("20004"));
				return model;
			}else{
				request.getSession().setAttribute(UnieapConstants.USER, vo);
				com.unieap.pojo.User user = new com.unieap.pojo.User();
				user.setPassword(password);
				user.setUserCode(vo.getServiceNumber());
				user.setUserId(Integer.getInteger(vo.getCustomerId()));
				user.setEnable(UnieapConstants.YES);
				user.setExpired(UnieapConstants.NO);
				user.setLocked(UnieapConstants.NO);
				user.setUserName(vo.getCustomerName());
				UserLogin userLogin = (UserLogin) ServiceUtils.getBean("userLogin");
				userLogin.addUserToAuthenticate(user,"ROLE_MCARE",request);
				JSONObject jsonResult = new JSONObject();
				JSONObject responseInfo = JSONUtils.convertBean2JSON(vo);
				jsonResult.put("responseInfoString", responseInfo);
				Map<String, String> model = new HashMap<String, String>();
				model.put(UnieapConstants.SUCCESS, UnieapConstants.SUCCESS);
				model.put(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
				model.put("responseInfoString", jsonResult.toString());
				return model;
			}
		}else{
			Map<String, String> model = new HashMap<String, String>();
			model.put(UnieapConstants.SUCCESS, UnieapConstants.SUCCESS);
			model.put(UnieapConstants.ISSUCCESS, UnieapConstants.FAILED);
			model.put(UnieapConstants.ERRORDESC, UnieapConstants.getMessage("comm.verfycode.error"));
			return model;
		}
	}
}
