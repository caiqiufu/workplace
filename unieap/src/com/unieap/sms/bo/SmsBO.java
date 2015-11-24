package com.unieap.sms.bo;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;
import com.unieap.pojo.SmsVerfiy;

@Service("smsBO")
public class SmsBO  extends BaseBO{
	public String checkVerifyCode(String type,String serviceNumber,String verifyCode) throws Exception{
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id,send_date>addtime(NOW(),'-0:30:0') as expired FROM unieap.sms_verfiy  where type = ? ");
		sql.append(" and send_to = ? and  verify_code =?  and checked = 'N' ");
		List<Map<String,Object>> datas = (List<Map<String, Object>>) DBManager.getJT(null).queryForList(sql.toString(),new Object[]{type,serviceNumber,verifyCode});
		if(datas!= null&&datas.size()>1){
			throw new Exception("sametime more than one verify code");
		}else if(datas.size()==1){
			Integer id = (Integer)datas.get(0).get("id");
			String expired = datas.get(0).get("expired").toString();
			if("0".equals(expired)){
				return "20002";
			}else{
				SmsVerfiy smsVerfiy = DBManager.getHT(null).get(SmsVerfiy.class, id);
				smsVerfiy.setChecked(UnieapConstants.YES);
				DBManager.getHT(null).update(smsVerfiy);
				return UnieapConstants.C0;
			}
		}else{
			return "20001";
		}
	}
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public SmsVerfiy save(SmsVerfiy smsVerfiy){
		String sql = "update sms_verfiy set checked = ? where type= 'V' and send_to = ? and checked = ?";
		DBManager.getJT(null).update(sql,new Object[]{UnieapConstants.YES,smsVerfiy.getSendTo(),UnieapConstants.NO});
		smsVerfiy.setId(getSequence(null, UnieapConstants.UNIEAP));
		smsVerfiy.setSendDate(UnieapConstants.getDateTime(null));
		DBManager.getHT(null).save(smsVerfiy);
		return smsVerfiy;
	}
}
