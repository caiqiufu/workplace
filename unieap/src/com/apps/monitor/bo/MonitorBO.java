package com.apps.monitor.bo;

import java.util.List;

import org.springframework.stereotype.Service;

import com.unieap.BaseBO;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;

@Service("monitorBO")
public class MonitorBO extends BaseBO{
	public void getServiceRequestChart(PaginationSupport page, String bizCode) {
		StringBuffer sql = new StringBuffer();
		sql.append(" select right(create_date,8) as createDate, ");
		sql.append(" MAX(CASE biz_code WHEN 'C002' THEN request_amount ELSE 0 END ) C002, ");
		sql.append(" MAX(CASE biz_code WHEN 'querySubLifeCycle' THEN request_amount ELSE 0 END ) querySubLifeCycle ");
		sql.append(" from report_service_request ");
		sql.append(" where create_date>= date_sub(CURRENT_TIMESTAMP(), interval 60 MINUTE) group by create_date ");
		List datas = DBManager.getJT(null).queryForList(sql.toString());
		page.setItems(datas);
		page.setTotalCount(datas.size());
	}
}
