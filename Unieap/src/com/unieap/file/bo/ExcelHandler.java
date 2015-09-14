package com.unieap.file.bo;

import java.util.Map;

import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public abstract class ExcelHandler {
	public static String FILE_NAME = "0";
	public static String SHEETS = "1";
	public Map<String,Object> getExportData(Map<String,Object> parameters){
		return null;
	}
	public Map<String,Object> importData(Map<String,Object> parameters,XSSFWorkbook workbook){
		return null;
	}
}
