package com.apps.easymobile.bo;

import java.util.List;
import java.util.Map;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Service;

import com.apps.easymobile.pojo.AppMessage;
import com.apps.easymobile.pojo.AppPushedList;
import com.apps.easymobile.pojo.AppResconfig;
import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;

@Service("pushMessageBO")
public class PushMessageBO extends BaseBO {
	public List<AppMessage> getPushMessage(String serviceNumber, String imei) {
		Map<String,List<AppMessage>> messages = (Map<String,List<AppMessage>>) CacheMgt.getCacheData().get("messages");
		List<AppMessage > datas = messages.get("P");
		StringBuffer pushType = new StringBuffer();
		if (datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				AppMessage appMessage = datas.get(i);
				pushType.append(appMessage.getId()).append(",");
			}
			if (checkPushMessage(imei, pushType.toString())) {
				AppPushedList appPushedList = new AppPushedList();
				appPushedList.setId(UnieapConstants.getSequence(null, null));
				appPushedList.setCreateDate(UnieapConstants.getDateTime(null));
				appPushedList.setImei(imei);
				appPushedList.setPushType(pushType.toString());
				appPushedList.setServiceNumber(serviceNumber);
				DBManager.getHT(null).save(appPushedList);
				return datas;
			} else {
				return null;
			}

		} else {
			return null;
		}

	}

	public boolean checkPushMessage(String imei, String pushType) {
		DetachedCriteria criteria = DetachedCriteria.forClass(AppPushedList.class);
		Property imeiProperty = Property.forName("imei");
		criteria.add(imeiProperty.eq(imei));
		Property pushTypeProperty = Property.forName("pushType");
		criteria.add(pushTypeProperty.eq(pushType));
		List datas = DBManager.getHT(null).findByCriteria(criteria);
		if (datas != null && datas.size() > 0) {
			return false;
		} else {
			return true;
		}
	}
}
