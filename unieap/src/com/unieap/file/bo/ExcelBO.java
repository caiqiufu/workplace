package com.unieap.file.bo;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;
import com.unieap.file.vo.CellVO;
import com.unieap.file.vo.FileUploadVO;
import com.unieap.file.vo.SheetVO;
import com.unieap.pojo.HandlerConfig;
import com.unieap.tools.JSONUtils;

@Service("excelBO")
public class ExcelBO extends BaseBO{
	public Map<String, String> exportExcel(String parameters, HandlerConfig handlerConfig, HttpServletRequest request,HttpServletResponse response) throws Exception{
		Map paras = JSONUtils.jsonToMap(parameters);
		HandlerConfig handlerconfig = getHandler(handlerConfig.getHandlerId());
		response.setCharacterEncoding("utf-8");    
		response.setContentType("application/vnd.ms-excel");
		XSSFWorkbook workbook = new XSSFWorkbook();
		XSSFSheet sheet = workbook.createSheet();
		ExcelHandler hanlder = (ExcelHandler)Class.forName(handlerconfig.getClassName()).newInstance();
		Map exDatas = hanlder.getExportData(paras);
		String fileName = (String)exDatas.get(ExcelHandler.FILE_NAME);
		response.setHeader("Content-Disposition", "attachment;fileName="+fileName);
		List<SheetVO> sheets = (List<SheetVO>) exDatas.get(ExcelHandler.SHEETS);
		if(sheets!=null&&sheets.size()>0){
			for(int i = 0 ; i < sheets.size(); i++){
				SheetVO svo = sheets.get(i);
				workbook.setSheetName(i,svo.getName());
				List<List<CellVO>> rows = svo.getDatas();
				if(rows!=null&&rows.size()>0){
					for(int j = 0 ; j < rows.size() ; j++){
						XSSFRow row = sheet.createRow(j);
						List<CellVO> cells = rows.get(j);
						if(cells!=null&&cells.size()>0){
							for(int k = 0 ; k < cells.size(); k++){
								CellVO vo = cells.get(k);
								XSSFCell cell = row.createCell(k);
								cell.setCellType(XSSFCell.CELL_TYPE_STRING);
								cell.setCellValue(vo.getCellValue());
							}
						}
					}
				}
			}
	        OutputStream os = null;
	        try {
	        	os=response.getOutputStream();  
	        	workbook.write(os);
		        os.flush();  
		        response.setStatus(HttpServletResponse.SC_OK);  
		        response.flushBuffer();
	        } catch (FileNotFoundException e) {    
	            e.printStackTrace();    
	        } catch (IOException e) {    
	            e.printStackTrace();    
	        } finally {
	        	if (os != null) {
	        		try {
						os.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}  
	        		
	        	}
	        }
		}
		return result(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
	}
	public HandlerConfig getHandler(Integer handlerId){
		return DBManager.getHT(null).get(HandlerConfig.class, handlerId);
	}
	/**
	 * only support office 2007
	 * @param parameters
	 * @param handlerConfig
	 * @param vo
	 * @return
	 * @throws Exception
	 */
	public Map<String, String> importExcel(String parameters, HandlerConfig handlerConfig, FileUploadVO vo) throws Exception{
		Map paras = JSONUtils.jsonToMap(parameters);
		HandlerConfig handlerconfig = getHandler(handlerConfig.getHandlerId());
		XSSFWorkbook workbook = new XSSFWorkbook(vo.getFile().getInputStream());;
        /*try {
            book = new XSSFWorkbook(vo.getFile().getInputStream());
        } catch (Exception ex) {
            book = new HSSFWorkbook(vo.getFile().getInputStream());
        }*/
		//HSSFWorkbook workbook = new HSSFWorkbook(vo.getFile().getInputStream());
		ExcelHandler hanlder = (ExcelHandler)Class.forName(handlerconfig.getClassName()).newInstance();
		Map exDatas =hanlder.importData(paras, workbook);
		exDatas.put(UnieapConstants.ISSUCCESS,UnieapConstants.SUCCESS);
		return exDatas;
	}
}
