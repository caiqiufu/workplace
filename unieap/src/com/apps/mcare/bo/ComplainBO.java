package com.apps.mcare.bo;

import java.io.File;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apps.mcare.pojo.AppComplain;
import com.apps.mcare.vo.ComplainVO;
import com.unieap.BaseBO;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.file.bo.FileBO;

@Service("complainBO")
public class ComplainBO extends BaseBO {
	private String[] allowFiles = { ".rar", ".doc", ".docx", ".zip", ".pdf", ".txt", ".swf", ".wmv", ".gif", ".png",
			".jpg", ".jpeg", ".bmp" };

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(AppComplain appComplain, List<FileItem> files) throws Exception {
		FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
		appComplain.setId(getSequence(null, null));
		if(files!=null && files.size() >0){
			
			String url = File.separator + "apps" + File.separator + "mcare" + File.separator + "images" + File.separator
					+ "complain";
			String uploadPath = fileBO.getRootPath() + url;
			String[] fieldIds = fileBO.upload("complain", appComplain.getId().toString(), files, uploadPath, url);
			if (fieldIds != null) {
				appComplain.setFileId(new Integer(fieldIds[0]));
			}
		}
		DBManager.getHT(null).save(appComplain);
	}

	public boolean checkFileType(String fileType) {
		for (int i = 0; i < allowFiles.length; i++) {
			if (allowFiles[i].equals(fileType)) {
				return true;
			}
		}
		return false;
	}

	public void getComplainList(PaginationSupport page, ComplainVO vo) throws Exception {
		StringBuffer sql = new StringBuffer();
		sql.append("SELECT id,evalution,text,url ,url as fileName,c.submit_date as submitDate,c.submit_by as submitBy ");
		sql.append("FROM app_complain c where ");
		sql.append(" submit_date > '").append(vo.getSubmitDateStart()).append("' and submit_date < '")
				.append(vo.getSubmitDateEnd()).append("' ");
		if (!StringUtils.isEmpty(vo.getSubmitBy())) {
			sql.append(" and c.submit_by ='").append(vo.getSubmitBy()).append("' ");
		}
		sql.append(" order by c.id desc ");
		StringBuffer totalSql = new StringBuffer();
		totalSql.append("SELECT count(*) FROM app_complain where ");
		totalSql.append(" submit_date > '").append(vo.getSubmitDateStart()).append("' and submit_date < '")
				.append(vo.getSubmitDateEnd()).append("' ");
		if (!StringUtils.isEmpty(vo.getSubmitBy())) {
			totalSql.append(" and submit_by ='").append(vo.getSubmitBy()).append("' ");
		}
		this.getPaginationDataByMysql(ComplainVO.class, sql.toString(), totalSql.toString(), null, page);
	}

	public List<Map<String, Object>> queryComplains(String serviceNumber) {
		StringBuffer sql = new StringBuffer("SELECT ac.id,ac.text,ac.feedback,ac.submit_date,fa.url ");
		sql.append("FROM unieap.app_complain ac left join unieap.file_archive fa on  ac.file_id = fa.id ");
		sql.append("where ac.submit_by = ? order by ac.id desc ");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString(),
				new Object[] { serviceNumber });
		return datas;
	}

}
