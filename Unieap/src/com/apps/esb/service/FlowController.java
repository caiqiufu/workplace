package com.apps.esb.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.util.StringUtils;

import com.unieap.base.SYSConfig;

public class FlowController {
	public static int currentQueryNumber = 0;
	public static int currentBizHandleNumber = 0;

	public synchronized static void addQueryRequest() {
		currentQueryNumber = currentQueryNumber + 1;
	}

	public synchronized static void deductQueryRequest() {
		currentQueryNumber = currentQueryNumber - 1;
	}

	public synchronized static void addBizhandleRequest() {
		currentQueryNumber = currentQueryNumber + 1;
	}

	public synchronized static void deductBizhandleRequest() {
		currentQueryNumber = currentQueryNumber - 1;
	}

	public static boolean isQueryOverFlow() {
		String queryCountStr = SYSConfig.getConfig().get("licence.flow.controller.query");
		if (StringUtils.isEmpty(queryCountStr)) {
			queryCountStr = "5";
		}
		int queryCount = Integer.parseInt(queryCountStr);
		if (currentQueryNumber > queryCount) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isBizhandleFlow() {
		String bizhandleCountStr = SYSConfig.getConfig().get("licence.flow.controller.bizhandle");
		if (StringUtils.isEmpty(bizhandleCountStr)) {
			bizhandleCountStr = "2";
		}
		int bizhandleCount = Integer.parseInt(bizhandleCountStr);
		if (currentBizHandleNumber > bizhandleCount) {
			return true;
		} else {
			return false;
		}
	}

	public static boolean isExpired() {
		String expiretime = SYSConfig.getConfig().get("licence.expiretime");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long expiredDate = new Date().getTime();
		try {
			expiredDate = sdf.parse(expiretime).getTime();
		} catch (ParseException e) {
			return true;
		}
		long currentTime = System.currentTimeMillis();
		if (expiredDate < currentTime) {
			return true;
		} else {
			return false;
		}
	}
}
