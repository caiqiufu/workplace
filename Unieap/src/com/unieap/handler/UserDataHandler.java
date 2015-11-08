package com.unieap.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;
import com.unieap.pojo.User;

@Service("userDataHandler")
public class UserDataHandler extends BaseBO implements ConfigHandler {

	@Override
	public void dealNode(Node node, ServletContext servlet, String appName) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void deal(String parameters) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(com.unieap.pojo.User.class);
		Property enable = Property.forName("enable");
		criteria.add(enable.eq(UnieapConstants.YES));
		Property expired = Property.forName("expired");
		criteria.add(expired.eq(UnieapConstants.NO));
		Property locked = Property.forName("locked");
		criteria.add(locked.eq(UnieapConstants.NO));
		List<User> userDatas = DBManager.getHT(null).findByCriteria(criteria);
		if (userDatas != null && userDatas.size() > 0) {
			Map<String, com.unieap.pojo.User> userList = new HashMap<String, com.unieap.pojo.User>();
			for (int i = 0; i < userDatas.size(); i++) {
				com.unieap.pojo.User user = (User) userDatas.get(i);
				userList.put(user.getUserCode(), user);
			}
			CacheMgt.setUserList(userList);
		}
	}

	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return true;
	}

}
