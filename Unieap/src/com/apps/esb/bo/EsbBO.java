package com.apps.esb.bo;

import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;

import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.apps.esb.pojo.Esblog;
import com.apps.esb.vo.EsblogVO;
import com.unieap.BaseBO;
import com.unieap.CacheMgt;
import com.unieap.UnieapConstants;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.mdm.vo.DicDataVO;

@Service("esbBO")
public class EsbBO extends BaseBO {
	public void getEsblogList(PaginationSupport page, EsblogVO vo) throws Exception {
		queryEsblogs(page, vo);
	}

	public void queryEsblogs(PaginationSupport page, EsblogVO vo) {
		StringBuffer sql = new StringBuffer();
		sql.append(
				"SELECT log_Id as logId,channel_Code as channelCode,biz_Code as bizCode,service_Number as serviceNumber,");
		sql.append(" transaction_Id as transactionId,DATE_FORMAT(request_Time,'%Y-%m-%d %H:%i:%s') as requestTime,DATE_FORMAT(response_Time,'%Y-%m-%d %H:%i:%s') as responseTime,");
		sql.append(" system_Code as systemCode,extTransaction_Id as extTransactionId,create_Date as createDate,");
		sql.append(" result_Code as resultCode,result_Desc as resultDesc,execute_Time as executeTime,");
		sql.append(" source_System as sourceSystem,dest_System as destSystem FROM unieap.esblog where ");
		sql.append(" request_Time > '").append(vo.getRequestTimeStart()).append("' and request_Time < '")
				.append(vo.getRequestTimeEnd()).append("' ");
		if (!StringUtils.isEmpty(vo.getServiceNumber())) {
			sql.append(" and service_Number ='").append(vo.getServiceNumber()).append("' ");
		}
		if (!StringUtils.isEmpty(vo.getBizCode())) {
			sql.append(" and biz_Code ='").append(vo.getBizCode()).append("' ");
		}
		if (!StringUtils.isEmpty(vo.getResultCode())) {
			sql.append(" and result_Code ='").append(vo.getResultCode()).append("' ");
		}
		sql.append("  order by log_id DESC");
		StringBuffer totalSql = new StringBuffer();
		totalSql.append("SELECT count(*) from unieap.esblog where ");
		totalSql.append(" request_Time > '").append(vo.getRequestTimeStart()).append("' and request_Time < '")
				.append(vo.getRequestTimeEnd()).append("' ");
		if (!StringUtils.isEmpty(vo.getServiceNumber())) {
			totalSql.append(" and service_Number ='").append(vo.getServiceNumber()).append("' ");
		}
		if (!StringUtils.isEmpty(vo.getBizCode())) {
			totalSql.append(" and biz_Code ='").append(vo.getBizCode()).append("' ");
		}
		if (!StringUtils.isEmpty(vo.getResultCode())) {
			totalSql.append(" and result_Code ='").append(vo.getResultCode()).append("' ");
		}
		this.getPaginationDataByMysql(EsblogVO.class, sql.toString(), totalSql.toString(), null, page);
	}

	public String getEsblog(String logId) throws Exception {
		Esblog esblog = DBManager.getHT(null).get(Esblog.class, new Integer(logId));
		JSONObject jsonResult = new JSONObject();
		jsonResult.put(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		jsonResult.put("serviceNumber", esblog.getServiceNumber());
		String bizCodeDesc = esblog.getBizCode();
		if (!StringUtils.isEmpty(esblog.getBizCode())) {
			DicDataVO dic = CacheMgt.getDicData("bizHandler", esblog.getBizCode());
			if (dic != null) {
				bizCodeDesc = dic.getDicName();
			}
		}
		jsonResult.put("bizCodeDesc", bizCodeDesc);
		String requestInfoDetails = formatXML(new String(esblog.getRequestInfo(), "UTF-8"));
		jsonResult.put("requestInfoDetails", requestInfoDetails);
		String responseInfoDetails = formatXML(new String(esblog.getResponseInfo(), "UTF-8"));
		jsonResult.put("responseInfoDetails", responseInfoDetails);
		return jsonResult.toString();
	}

	public String formatXML(String inputXML) throws IOException{
		SAXReader reader = new SAXReader();
		Document document = null;
		try {
			document = reader.read(new StringReader(inputXML));
		} catch (DocumentException e1) {
			return inputXML;
		}
		String requestXML = null;
		XMLWriter xw = null;
		if (document != null) {
			try {
				OutputFormat format = OutputFormat.createPrettyPrint();
				format.setEncoding("UTF-8");
				StringWriter sw = new StringWriter();
				xw = new XMLWriter(sw, format);
				xw.setEscapeText(false);
				xw.write(document);
				requestXML = sw.toString();
				xw.flush();
			} finally {
				if (xw != null) {
					try {
						xw.close();
					} catch (IOException e) {
					}
				}
			}
		}
		return requestXML;
	}
}
