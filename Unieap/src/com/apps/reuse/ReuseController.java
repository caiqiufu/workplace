package com.apps.reuse;

import java.beans.PropertyEditor;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apps.reuse.bo.ReuseAddressBO;
import com.apps.reuse.bo.ReuseCustomerBO;
import com.apps.reuse.bo.ReuseProductBO;
import com.apps.reuse.bo.ReuseSMSBO;
import com.apps.reuse.pojo.ReuseCustomer;
import com.apps.reuse.pojo.ReuseCustomerAddress;
import com.apps.reuse.pojo.ReuseProduct;
import com.apps.reuse.pojo.ReuseProductTracking;
import com.apps.reuse.pojo.ReuseSmsNotify;
import com.apps.reuse.vo.ReuseCustomerAddressVO;
import com.unieap.BaseController;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
@RequestMapping(value="ReuseController.do")  
public class ReuseController extends BaseController{
	@InitBinder
	protected void initBinder(HttpServletRequest request,
	                              ServletRequestDataBinder binder) throws Exception {
	    //对于需要转换为Date类型的属性，使用DateEditor进行处理
	    binder.registerCustomEditor(Date.class, (PropertyEditor) new com.unieap.exttools.DateEditor());
	}
	/*
	 * customer register
	 */
	@RequestMapping(value="ReuseController.do",params="method=customer",method = RequestMethod.GET)  
	public ModelAndView customer(String project,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/reuse/customer");
		return ma;
	}
	@RequestMapping(value="ReuseController.do",params="method=customerGrid",method = RequestMethod.GET)  
	public @ResponseBody String customerGrid(PaginationSupport page,ReuseCustomer reuseCustomer,String productCode,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ReuseCustomerBO reuseCustomerBO = (ReuseCustomerBO) ServiceUtils.getBean("reuseCustomerBO");
		reuseCustomerBO.getCustomerGrid(page,reuseCustomer,productCode);
		return page.getJsonString();
	}
	
	@RequestMapping(value="ReuseController.do",params="method=customerDeal",method = RequestMethod.POST)  
	public @ResponseBody Map customerDeal(String operType,ReuseCustomer reuseCustomer, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ReuseCustomerBO reuseCustomerBO = (ReuseCustomerBO) ServiceUtils.getBean("reuseCustomerBO");
		Map model = reuseCustomerBO.customerDeal(operType,reuseCustomer);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	/*
	 * customer product
	 */
	@RequestMapping(value="ReuseController.do",params="method=product",method = RequestMethod.GET)  
	public ModelAndView product(String project,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/reuse/product");
		return ma;
	}
	@RequestMapping(value="ReuseController.do",params="method=productGrid",method = RequestMethod.GET)  
	public @ResponseBody String productGrid(PaginationSupport page,ReuseProduct reuseProduct,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ReuseProductBO reuseProductBO = (ReuseProductBO) ServiceUtils.getBean("reuseProductBO");
		reuseProductBO.getProductGrid(page,reuseProduct);
		return page.getJsonString();
	}
	@RequestMapping(value="ReuseController.do",params="method=productDeal",method = RequestMethod.POST)  
	public @ResponseBody Map productDeal(String operType,ReuseProduct reuseProduct,String referNo, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ReuseProductBO reuseProductBO = (ReuseProductBO) ServiceUtils.getBean("reuseProductBO");
		Map model;
		if(!StringUtils.equals(operType,"Modify_Status")){
			model = reuseProductBO.productDeal(operType,reuseProduct);
		}else{
			model = reuseProductBO.updateStatus(reuseProduct, referNo);
		}
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	@RequestMapping(value="ReuseController.do",params="method=getModel",method = RequestMethod.POST)  
	public @ResponseBody String getModel(Integer groupId, Integer parentId, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ReuseProductBO reuseProductBO = (ReuseProductBO) ServiceUtils.getBean("reuseProductBO");
		return reuseProductBO.getModel(groupId,parentId);
	}
	@RequestMapping(value="ReuseController.do",params="method=producTrackingtGrid",method = RequestMethod.GET)  
	public @ResponseBody String producTrackingtGrid(PaginationSupport page,ReuseProductTracking reuseProductTracking,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ReuseProductBO reuseProductBO = (ReuseProductBO) ServiceUtils.getBean("reuseProductBO");
		reuseProductBO.getProductTrackingGrid(page,reuseProductTracking);
		return page.getJsonString();
	}
	/**
	 * customer address
	 */
	@RequestMapping(value="ReuseController.do",params="method=addressGrid",method = RequestMethod.GET)  
	public @ResponseBody String addressGrid(PaginationSupport page,ReuseCustomerAddressVO vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ReuseAddressBO reuseAddressBO = (ReuseAddressBO) ServiceUtils.getBean("reuseAddressBO");
		reuseAddressBO.getAddressGrid(page,vo);
		return page.getJsonString();
	}
	@RequestMapping(value="ReuseController.do",params="method=addressDeal",method = RequestMethod.POST)  
	public @ResponseBody Map addressDeal(String operType,ReuseCustomerAddress address, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ReuseAddressBO reuseAddressBO = (ReuseAddressBO) ServiceUtils.getBean("reuseAddressBO");
		Map model = reuseAddressBO.addressDeal(operType,address);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	/*
	 * sms verification
	 */
	@RequestMapping(value="ReuseController.do",params="method=smsverify",method = RequestMethod.GET)  
	public ModelAndView smsverify(String project,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/reuse/smsverify");
		return ma;
	}
	@RequestMapping(value="ReuseController.do",params="method=smsverifyGrid",method = RequestMethod.GET)  
	public @ResponseBody String smsverifyGrid(PaginationSupport page,ReuseSmsNotify vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ReuseSMSBO reuseSMSBO = (ReuseSMSBO) ServiceUtils.getBean("reuseSMSBO");
		reuseSMSBO.getSmsverifyGrid(page,vo);
		return page.getJsonString();
	}
	
	
}
