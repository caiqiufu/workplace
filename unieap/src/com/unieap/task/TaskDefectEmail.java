package com.unieap.task;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;
import com.unieap.email.SendMaileUtil;
import com.unieap.pojo.EmailSend;
public class TaskDefectEmail implements TaskService {
	/**
	 * send email
	 */
	public String from = "caiqiufu@huawei.com";
	public void execute(){
		DetachedCriteria criteria = DetachedCriteria.forClass(EmailSend.class);
		criteria.add(Restrictions.disjunction().add(Restrictions.eq("status","0")).add(Restrictions.eq("status","2")));
		List emailSends = DBManager.getHT(null).findByCriteria(criteria);
		if(emailSends!=null && emailSends.size()>0){
			EmailSend emailSend;
			String[] tos;
			String emails;
			Integer times;
			for(int i = 0 ; i< emailSends.size() ; i++){
				emailSend = (EmailSend) emailSends.get(i);
				emails = emailSend.getEmail();
				if(!StringUtils.isEmpty(emails)){
					times = emailSend.getTimes();
					if(StringUtils.contains(emails, ";")){
						tos = StringUtils.split(emails,";");
					}else{
						tos = new String[]{emails};
					}
					try{
						SendMaileUtil.sendTextMaile(from, tos, emailSend.getSubject(), emailSend.getContent());
						times = Integer.valueOf(times==null?0:times.intValue()+1);
						//emailSend.setTimes(times==null?0:times.intValue()+1);
						//emailSend.setStatus("1");
						//emailSend.setSendDate(UnieapConstants.getDateTime(null));
						//DBManager.getHT(null).update(emailSend);
						DBManager.getHT(null).bulkUpdate("update EmailSend e set e.times=? , e.status=? , e.sendDate=? where e.sendId=?",new Object[]{times,"1",UnieapConstants.getDateTime(null),emailSend.getSendId()});
					}catch(Exception e){
						if(times!=null&&times.intValue()==2){
							times = Integer.valueOf(times==null?0:times.intValue()+1);
							//emailSend.setStatus("0");
							//emailSend.setSendDate(UnieapConstants.getDateTime(null));
							//DBManager.getHT(null).update(emailSend);
							DBManager.getHT(null).bulkUpdate("update EmailSend e set e.times=? , e.status=? , e.sendDate=? where e.sendId=?",new Object[]{times,"0",UnieapConstants.getDateTime(null),emailSend.getSendId()});
						}else{
							times = Integer.valueOf(times==null?0:times.intValue()+1);
							//emailSend.setStatus("2");
							//emailSend.setSendDate(UnieapConstants.getDateTime(null));
							//DBManager.getHT(null).update(emailSend);
							DBManager.getHT(null).bulkUpdate("update EmailSend e set e.times=? , e.status=? , e.sendDate=? where e.sendId=?",new Object[]{times,"2",UnieapConstants.getDateTime(null),emailSend.getSendId()});
						}
					}
				}
			}
		}
	}
}
