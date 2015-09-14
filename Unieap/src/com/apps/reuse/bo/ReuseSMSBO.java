package com.apps.reuse.bo;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.springframework.stereotype.Service;

import com.apps.reuse.pojo.ReuseCustomer;
import com.apps.reuse.pojo.ReuseSmsNotify;
import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.db.EntityRowMapper;

@Service("reuseSMSBO")
public class ReuseSMSBO extends BaseBO {
	
	public void getSmsverifyGrid(PaginationSupport page,ReuseSmsNotify vo) throws Exception{
		DetachedCriteria criteria=DetachedCriteria.forClass(ReuseSmsNotify.class);
		setCriteria(criteria,vo);
		getPaginationDataByDetachedCriteria(criteria,page);
	}
	
	public Map<String, String> sendSms(ReuseSmsNotify vo) throws Exception{
		if(StringUtils.equals(vo.getSmsType(),"1")){
			//http://127.0.0.1:8080/Unieap/ExtAction.do?method=sendSms&smsType=1&sendTo=15899785476
			sendVerifySMS(vo);
		}
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public Map<String, String> regisCustomer(ReuseSmsNotify smsVo,ReuseCustomer custVo) throws Exception{
		if(isRegisted(custVo.getCustomerCode())){
			Map res = result(UnieapConstants.ISSUCCESS,UnieapConstants.FAILED);
			return (Map<String, String>) res.put(UnieapConstants.RETURNMESSAGE,UnieapConstants.getMessage("reuse.customer.customerCode", new Object[]{custVo.getCustomerCode()}, null));
		}else{
			ReuseCustomerBO reuseCustomerBO = (ReuseCustomerBO) ServiceUtils.getBean("reuseCustomerBO");
			custVo.setSex("M");
			custVo.setPhone(smsVo.getSendTo());
			reuseCustomerBO.customerDeal(UnieapConstants.ADD,custVo);
			return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
		}
	}
	public boolean isRegisted(String custCode){
		StringBuffer sql = new StringBuffer();
		sql.append("select count(1) from reuse_customer where customer_code = ? ");
		int count = DBManager.getJT(null).queryForInt(sql.toString(),new Object[]{custCode});
		if(count>1){
			return true;
		}else{
			return false;
		}
	}
	public void updateSmsVerify(ReuseSmsNotify vo){
		List<?> items =  getSmsVerify(vo);
		if(items.size()>0){
			ReuseSmsNotify item;
			for(int i = 0 ; i< items.size(); i++){
				item = (ReuseSmsNotify)items.get(i);
				item.setChecked("1");
			}
			DBManager.getHT(null).saveOrUpdateAll(items);
		}
	}
	public Map<String, String> smsVerify(ReuseSmsNotify vo){
		if(StringUtils.equals(vo.getSmsType(),"1")){
			List<?> items =  getSmsVerify(vo);
			if(items.size()==1){
				if(expired(vo)){
					Map res = result(UnieapConstants.ISSUCCESS,UnieapConstants.FAILED);
					return (Map<String, String>) res.put(UnieapConstants.RETURNMESSAGE,UnieapConstants.getMessage("sms.verify.expired"));
				}else{
					return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
				}
			}else if(items.size()==0){
				Map res =result(UnieapConstants.ISSUCCESS,UnieapConstants.FAILED);
				return (Map<String, String>) res.put(UnieapConstants.RETURNMESSAGE,UnieapConstants.getMessage("sms.verify.code.notexisting",new Object[]{vo.getVerifyCode()}));
			}
		}else{
			Map res =result(UnieapConstants.ISSUCCESS,UnieapConstants.FAILED);
			return (Map<String, String>) res.put(UnieapConstants.RETURNMESSAGE,UnieapConstants.getMessage("sms.verify.code.notexisting",new Object[]{vo.getVerifyCode()}));
		}
		Map res =result(UnieapConstants.ISSUCCESS,UnieapConstants.FAILED);
		return (Map<String, String>) res.put(UnieapConstants.RETURNMESSAGE,UnieapConstants.getMessage("sms.verify.code.notexisting",new Object[]{vo.getVerifyCode()}));
	}
	public List<?> getSmsVerify(ReuseSmsNotify vo){
		StringBuffer sql = new StringBuffer();
		sql.append("select id as Id,sms_type as smsType,from_by as fromBy,send_to as sendTo,content,verify_code as verifyCode,send_date as sendDate, ");
		sql.append("checked,check_times as checkTimes,expired,remark ");
		sql.append("from unieap.reuse_sms_notify where sms_type=? and send_to =? and verify_code=? and checked='0' and expired ='0'");
		return DBManager.getJT(null).query(sql.toString(), new Object[]{vo.getSmsType(),vo.getSendTo(),vo.getVerifyCode()}, new EntityRowMapper(ReuseSmsNotify.class));

	}
	public boolean expired(ReuseSmsNotify vo){
		Date currentDate = UnieapConstants.getDateTime(null);
		long during = currentDate.getTime()-vo.getSendDate().getTime();
		if(during>1000*60*30){
			return false;
		}else{
			vo.setExpired("1");
			DBManager.getHT(null).update(vo);
			return true;
		}
	}
	public void sendVerifySMS(ReuseSmsNotify vo) throws Exception{
		vo.setId(getSequence(null,"unieap"));
		vo.setFromBy("unieap");
		String verifyCode = generateVerifyCode();
		vo.setContent(UnieapConstants.getMessage("sms.verify.phonenumber",new Object[]{verifyCode}));
		vo.setVerifyCode(verifyCode);
		vo.setSendDate(UnieapConstants.getDateTime(null));
		vo.setChecked("0");
		vo.setCheckTimes(0);
		vo.setExpired("0");
		sendSMS(vo);
		DBManager.getHT(null).save(vo);
		
	}
	public String generateVerifyCode(){
		long code = Math.round(Math.random()*10000000);
		return StringUtils.substring(Long.toString(code), 1);
	}
	public void sendSMS(ReuseSmsNotify vo) throws Exception{
		String urlstr = UnieapConstants.getMessage("sms.5c",new Object[]{vo.getSendTo(),vo.getContent()});
		URL url = new URL(urlstr);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("POST");
		url.openStream();
		/*BufferedReader in = new BufferedReader(new InputStreamReader(url.openStream()));
		String inputline = in.readLine();
		System.out.println(inputline);*/
	}
}
