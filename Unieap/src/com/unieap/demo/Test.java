package com.unieap.demo;

import java.text.SimpleDateFormat;

public class Test {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String drap = "1/2/3/4";
		String target = "1/2/7";
		String nodepath1 = "1/2/3/4/8/9";
		String nodepath2 = "1/2/3/4/8/10";
		String replaced = nodepath1.replaceAll(drap,target);
		System.out.print(replaced);
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"); 
	}

}
