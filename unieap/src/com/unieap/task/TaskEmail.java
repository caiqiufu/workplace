package com.unieap.task;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Component;

import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;
import com.unieap.email.MailUtils;
import com.unieap.pojo.ExcLog;
import com.unieap.pojo.Notification;

@Component
public class TaskEmail extends TaskBO implements TaskService{
	@Override
	public int getTaskTimeDuration() {
		// TODO Auto-generated method stub
		return 300;
	}
	
	//@Scheduled(cron="30 * * * * ?")
	public void sendEmail() {
		if(!this.checkTaskStatus("task.send.email")){
			return;
		}
		DetachedCriteria criteria = DetachedCriteria.forClass(Notification.class);
		criteria.add(Restrictions.eq("status","T"));
		criteria.add(Restrictions.eq("sendType","EMAIL"));
		List<?> datas = DBManager.getHT(null).findByCriteria(criteria);
		if(datas!=null && datas.size()>0){
			String[] tos;
			for(int i = 0 ; i< datas.size() ; i++){
				Notification emailNotify = (Notification) datas.get(i);
				if(StringUtils.contains(emailNotify.getSendTo(), ";")){
					tos = StringUtils.split(emailNotify.getSendTo(),";");
				}else{
					tos = new String[]{emailNotify.getSendTo()};
				}
				try {
					String content = "";
					if(emailNotify.getContent()!=null){
						content = new String(emailNotify.getContent(), "UTF-8");
					}
					MailUtils.javaSendEmail(emailNotify.getFromBy(), tos,emailNotify.getSubject(), content);
					emailNotify.setStatus("S");
					emailNotify.setSendDate(UnieapConstants.getDateTime(null));
					emailNotify.setResultDesc("success");
					DBManager.getHT(null).update(emailNotify);
				} catch (Exception ex) {
					ExcLog log = new ExcLog();
			        log.setId(UnieapConstants.getSequence(null,"unieap"));
			        log.setBizModule("unieap");
			        log.setExType("Email_exception");
			        log.setExCode("");
			        log.setExInfo(ex.getLocalizedMessage());
			        StringWriter sw = new StringWriter();
			        PrintWriter pw = new PrintWriter(sw);
			        ex.printStackTrace(pw);
			        log.setExTracking(sw.toString().getBytes());
			        if(UnieapConstants.getUser()!=null){
			        	log.setOperator(UnieapConstants.getUser().getUserCode());
			        }else{
			        	log.setOperator("system error");
			        }
			        log.setOperationDate(UnieapConstants.getDateTime(null));
			        DBManager.getHT(null).save(log);
			        emailNotify.setStatus("F");
			        emailNotify.setSendDate(UnieapConstants.getDateTime(null));
			        emailNotify.setResultDesc(ex.getLocalizedMessage());
			        DBManager.getHT(null).update(emailNotify);
				}
			}
		}
	}

	@Override
	public String getTaskCode() {
		// TODO Auto-generated method stub
		return "sendEmail";
	}

	@Override
	public void taskExecute() {
		sendEmail();
	}
}
