package com.unieap.task;

import java.util.ArrayList;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.unieap.CacheMgt;
import com.unieap.db.DBManager;
import com.unieap.pojo.Chglog;

@Component
public class TaskSaveLog {
	@Scheduled(cron = "15 * * * * ?")
	public void saveChgLog() {
		List<Chglog> datas = CacheMgt.getChglogDatas();
		if (datas.size() > 0) {
			List<Chglog> copyDatas = new ArrayList<Chglog>();
			copyDatas.addAll(datas);
			datas.clear();
			DBManager.getHT(null).saveOrUpdateAll(copyDatas);
		}
	}
}
