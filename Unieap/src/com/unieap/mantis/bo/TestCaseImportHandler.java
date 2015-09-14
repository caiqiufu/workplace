package com.unieap.mantis.bo;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;
import com.unieap.file.bo.ExcelHandler;
import com.unieap.pojo.MTestcase;

public class TestCaseImportHandler extends ExcelHandler{
	
	public Map<String,Object> importData(Map<String,Object> parameters,XSSFWorkbook workbook){
		XSSFSheet sheet = workbook.getSheetAt(0); 
		int rows = sheet.getPhysicalNumberOfRows();
		MTestcase tc;
		String tcId = "";
		String tcTitle = "";
		String tcDesc = "";
		String tcStream = "";
		String subStream = "";
		String tester = "";
		StringBuffer error = new StringBuffer();
		if(rows>1){
			for (int r = 1; r < rows; r++){
				XSSFRow row = sheet.getRow(r); 
				 if (row != null) {
					 tc = new MTestcase();
					 int cells = row.getPhysicalNumberOfCells();
					 XSSFCell cell0 = row.getCell(0);
					 if (cell0 != null){
						 tcId = cell0.getStringCellValue();
					 }else{
						 error.append("row number [").append(r).append("][TC_ID]").append(" can not be null").append(System.getProperty("line.separator"));
					 }
					 XSSFCell cell1 = row.getCell(1);
					 if (cell1 != null){
						 tcTitle = cell1.getStringCellValue();
					 }else{
						 error.append("row number [").append(r).append("][TC_Title]").append(" can not be null").append(System.getProperty("line.separator"));
					 }
					 XSSFCell cell2 = row.getCell(2);
					 if (cell2 != null){
						 tcDesc = cell2.getStringCellValue();
					 }else{
						 error.append("row number [").append(r).append("][Description]").append(" can not be null").append(System.getProperty("line.separator"));
					 }
					 XSSFCell cell3 = row.getCell(3);
					 if (cell3 != null){
						 tcStream = cell3.getStringCellValue();
					 }else{
						 error.append("row number [").append(r).append("][Test Stream]").append(" can not be null").append(System.getProperty("line.separator"));
					 }
					 XSSFCell cell4 = row.getCell(4);
					 if (cell4 != null){
						 subStream = cell4.getStringCellValue();
					 }else{
						 error.append("row number [").append(r).append("][Sub Stream]").append(" can not be null").append(System.getProperty("line.separator"));
					 }
					 XSSFCell cell5 = row.getCell(5);
					 if (cell5 != null){
						 tester = cell5.getStringCellValue();
					 }
					 tc.setCreateBy(UnieapConstants.getUser().getUserCode());
					 tc.setCreateDate(UnieapConstants.getDateTime(null));
					 tc.setProject(parameters.get("project").toString());
					 tc.setSubStream(subStream);
					 tc.setStatus("1");
					 tc.setTestStream(tcStream);
					 tc.setTcCode(tcId);
					 tc.setTcDescription(tcDesc);
					 tc.setTcId(UnieapConstants.getSequence(null, "unieap"));
					 tc.setTcName(tcTitle);
					 tc.setTester(tester);
					 DBManager.getHT(null).save(tc);
				 }
			}
		}
		Map result = new HashMap();
		if(StringUtils.isEmpty(error.toString())){
			result.put(UnieapConstants.RETURNMESSAGE,UnieapConstants.SUCCESS);
		}else{
			result.put(UnieapConstants.RETURNMESSAGE, error.toString());
		}
		return result;
	}
}
