package com.bss;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Test {

	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		/*Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
		String dt = sdf.format(date);
		System.out.println("time=" + dt);

		Calendar rightNow = Calendar.getInstance();
		rightNow.setTime(date);
		rightNow.add(Calendar.MONTH, -5);
		System.out.println(new SimpleDateFormat("yyyyMM").format(date.getTime()) + "01000000");
		System.out.println(new SimpleDateFormat("yyyyMM").format(rightNow.getTime()) + "01000000");

		Calendar cDay1 = Calendar.getInstance();
		cDay1.setTime(date);
		cDay1.add(Calendar.MONTH, -5);
		final int lastDay = cDay1.getActualMaximum(Calendar.DAY_OF_MONTH);
		
		System.out.println("last date ="+new SimpleDateFormat("yyyyMMDD").format(lastDay) + "01000000");
		
		
		Calendar cale = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String firstday, lastday;
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, -5);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime());
		
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, -4);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime());
		System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);

		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, -3);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime());
		
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, -1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime());
		System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);

		
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, -1);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime());
		
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime());
		System.out.println("本月第一天和最后一天分别是 ： " + firstday + " and " + lastday);
		
		
		
	

		String[] types = new String[] {};
		String str = "P,";
		types = str.split(",");

		java.text.DecimalFormat df = new java.text.DecimalFormat("0.00000");
		String money = df.format(115.223344556);
		System.out.println("money=" + money);

		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddhhmmss");
		Date strtodate = sdf2.parse("20151023174545");
		SimpleDateFormat sdf3 = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		String datetime = sdf3.format(strtodate);
		System.out.println("datetime=" + datetime);*/
		String [] times = getTimes();
		System.out.println("firstday=" + times[0]);
		System.out.println("lastday=" + times[1]);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
	    Date date = sdf.parse("2015-11-29");
	    String format = new SimpleDateFormat("yyyyMMdd").format(date);
		System.out.println("format=" + format);
		
		List datas = new ArrayList();
		datas.add("a");
		datas.add("b");
		datas.add("c");
		Integer processRecords = Integer.valueOf(datas.size());
		System.out.println("processRecords="+processRecords.intValue());
		datas.clear();
		System.out.println("after clear,processRecords="+processRecords.intValue());
	}
	
	public static String[] getTimes(int number) {
		Calendar cale = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String firstday, lastday;
		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, -number);
		cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime()) + "000000";

		cale = Calendar.getInstance();
		cale.add(Calendar.MONTH, -number + 1);
		cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime()) + "235959";
		return new String[] { firstday, lastday };
	}
	public static String[] getTimes() {
		Calendar cale = null;
		SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd");
		String firstday, lastday;
		cale = Calendar.getInstance();
		cale.add(Calendar.DATE, -89);
		//cale.set(Calendar.DAY_OF_MONTH, 1);
		firstday = format.format(cale.getTime()) + "000000";
		
		cale = Calendar.getInstance();
		//cale.add(Calendar.MONTH,0);
		//cale.set(Calendar.DAY_OF_MONTH, 0);
		lastday = format.format(cale.getTime()) + "235959";
		return new String[] { firstday, lastday };
	}
	
	

}
