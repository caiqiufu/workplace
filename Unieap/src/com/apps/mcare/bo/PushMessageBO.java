package com.apps.mcare.bo;

import java.util.List;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Service;

import com.apps.mcare.pojo.AppPushMessage;
import com.apps.mcare.pojo.AppPushedList;
import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;

@Service("pushMessageBO")
public class PushMessageBO extends BaseBO {
	public List<AppPushMessage> getPushMessage(String serviceNumber, String imei) {
		List<AppPushMessage> datas = (List<AppPushMessage>) CacheMgt.getCacheData().get("PushMessageList");
		StringBuffer pushType = new StringBuffer();
		if (datas != null && datas.size() > 0) {
			for (int i = 0; i < datas.size(); i++) {
				AppPushMessage appPushMessage = datas.get(i);
				pushType.append(appPushMessage.getId()).append(",");
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
