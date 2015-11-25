package com.apps.mcare;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.apps.mcare.bo.ApkUpgradeBO;
import com.apps.mcare.bo.AppDenoBO;
import com.apps.mcare.bo.AppMessageBO;
import com.apps.mcare.bo.ComplainBO;
import com.apps.mcare.bo.OfferingBO;
import com.apps.mcare.bo.ResourceConfigureBO;
import com.apps.mcare.pojo.AppComplain;
import com.apps.mcare.pojo.AppMessage;
import com.apps.mcare.pojo.AppOffering;
import com.apps.mcare.pojo.AppOfferingCategory;
import com.apps.mcare.pojo.AppResconfig;
import com.apps.mcare.vo.ComplainVO;
import com.unieap.BaseController;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.file.bo.FileBO;

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
		OfferingBO offeringBO = (OfferingBO) ServiceUtils.getBean("OfferingBO");
		offeringBO.getOfferingCategoryList(page, vo);
		return page.getJsonString();
	}
	@RequestMapping(params="method=offeringGrid")  
	public @ResponseBody String offeringGrid(PaginationSupport page,AppOfferingCategory vo,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		OfferingBO offeringBO = (OfferingBO) ServiceUtils.getBean("OfferingBO");
		offeringBO.getOfferingList(page, vo);
		return page.getJsonString();
	}
	@RequestMapping(params="method=offeringDeal")  
	public @ResponseBody Map<String, String> offeringDeal(String operType,AppOffering vo, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		OfferingBO offeringBO = (OfferingBO) ServiceUtils.getBean("OfferingBO");
		Map<String, String> model = offeringBO.offeringDeal(operType, vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
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
	
	@RequestMapping(params="method=complainDeal")  
	public @ResponseBody Map<String, String> complainDeal(String operType,AppComplain vo, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ComplainBO complainBO = (ComplainBO) ServiceUtils.getBean("complainBO");
		Map<String, String> model = complainBO.complainDeal(operType, vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
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
	
	@RequestMapping(params="method=messageGrid")  
	public @ResponseBody String messageGrid(PaginationSupport page,String type,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		AppMessageBO appMessageBO = (AppMessageBO) ServiceUtils.getBean("appMessageBO");
		appMessageBO.getMessageGrid(page, type);
		return page.getJsonString();
	}
	
	@RequestMapping(params="method=messageDeal")  
	public @ResponseBody Map<String, String> messageDeal(String operType,AppMessage vo, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		AppMessageBO appMessageBO = (AppMessageBO) ServiceUtils.getBean("appMessageBO");
		Map<String, String> model = appMessageBO.messageDeal(operType, vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	
	@RequestMapping(params="method=groupNamesGrid")  
	public @ResponseBody String groupNamesGrid(PaginationSupport page,String groupNames,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ResourceConfigureBO resourceConfigureBO = (ResourceConfigureBO) ServiceUtils.getBean("resourceConfigureBO");
		resourceConfigureBO.getNamesList(page, groupNames);
		return page.getJsonString();
	}
	@RequestMapping(params="method=resourceConfigureDeal")  
	public @ResponseBody Map<String, String> resourceConfigureDeal(String operType,AppResconfig vo, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ResourceConfigureBO resourceConfigureBO = (ResourceConfigureBO) ServiceUtils.getBean("resourceConfigureBO");
		Map<String, String> model = resourceConfigureBO.resourceConfigureDeal(operType, vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	@RequestMapping(params="method=resourceConfigurePictureDeal")  
	public @ResponseBody Map<String, String> resourceConfigurePictureDeal(String operType,String parameters,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
		List<FileItem> items = fileBO.getFileItems(request);
		ResourceConfigureBO resourceConfigureBO = (ResourceConfigureBO) ServiceUtils.getBean("resourceConfigureBO");
		Map<String, String> model = resourceConfigureBO.resourceConfigurePictureDeal(parameters,items);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	@RequestMapping(params="method=apkUpgrade")  
	public ModelAndView apkUpgrade(HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ModelAndView ma = new ModelAndView("apps/mcare/apkupgrade");
		return ma;
	}
	
	@RequestMapping(params="method=apkUpgradeGrid")  
	public @ResponseBody String apkUpgradeGrid(PaginationSupport page,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		ApkUpgradeBO apkUpgradeBO = (ApkUpgradeBO) ServiceUtils.getBean("apkUpgradeBO");
		apkUpgradeBO.getapkUpgradeList(page);
		return page.getJsonString();
	}
	
	@RequestMapping(params="method=apkUpgradeDeal")  
	public @ResponseBody Map<String, String> apkUpgradeDeal(String operType,String parameters,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
		List<FileItem> files = fileBO.getFileItems(request);
		ApkUpgradeBO apkUpgradeBO = (ApkUpgradeBO) ServiceUtils.getBean("apkUpgradeBO");
		Map<String, String> model = apkUpgradeBO.apkUpgradeDeal(operType, parameters, files);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
	
	
	@RequestMapping(params="method=denoGrid")  
	public @ResponseBody String denoGrid(PaginationSupport page,String type,HttpServletRequest request,HttpServletResponse response) throws Exception { 
		AppDenoBO appDenoBO = (AppDenoBO) ServiceUtils.getBean("appDenoBO");
		appDenoBO.getDenoGrid(page, type);
		return page.getJsonString();
	}
	
	@RequestMapping(params="method=denoDeal")  
	public @ResponseBody Map<String, String> denoDeal(String operType,AppMessage vo, HttpServletRequest request,HttpServletResponse response) throws Exception { 
		AppMessageBO appMessageBO = (AppMessageBO) ServiceUtils.getBean("appMessageBO");
		Map<String, String> model = appMessageBO.messageDeal(operType, vo);
		model.put(UnieapConstants.SUCCESS,UnieapConstants.SUCCESS);
		return model;
	}
}
