package com.unieap.verifycode;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.unieap.BaseController;
import com.unieap.base.ServiceUtils;
import com.unieap.verifycode.bo.VerifyCodeBO;


@Controller
@RequestMapping("verifyCodeController.do")
public class VerifyCodeController extends BaseController{
	
	@RequestMapping(params = "method=getVerifyCode")
	public @ResponseBody Map<String, String> getVerifyCode(HttpServletRequest request, HttpServletResponse response) throws Exception {
		VerifyCodeBO verifyCodeBO = (VerifyCodeBO) ServiceUtils.getBean("verifyCodeBO");
		return verifyCodeBO.getVerifyCode("1", request, response);
	}
}
