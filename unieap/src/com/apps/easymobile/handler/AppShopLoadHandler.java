package com.apps.easymobile.handler;

import java.util.List;

import javax.servlet.ServletContext;

import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Service;
import org.w3c.dom.Node;

import com.apps.easymobile.pojo.AppShop;
import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.db.DBManager;
import com.unieap.handler.ConfigHandler;

@Service("appShopLoadHandler")
public class AppShopLoadHandler extends BaseBO implements ConfigHandler{

	@Override
	public void dealNode(Node node, ServletContext servlet, String appName) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void deal(String parameters) throws Exception {
		getAppShopList();
	}
	
	public void getAppShopList(){
		DetachedCriteria criteria = DetachedCriteria.forClass(AppShop.class);
		Property activeFlag = Property.forName("activeFlag");
		criteria.add(activeFlag.eq(UnieapConstants.YES));
		Property tenantId = Property.forName("tenantId");
		criteria.add(tenantId.eq(SYSConfig.getConfig().get("tenantId")));
		criteria.addOrder(Order.asc("shopName"));
		List<AppShop> shopLis = DBManager.getHT(null).findByCriteria(criteria);
		CacheMgt.getCacheData().put("AppShopList", shopLis);
	}

	@Override
	public boolean reload() {
		// TODO Auto-generated method stub
		return true;
	}

}
