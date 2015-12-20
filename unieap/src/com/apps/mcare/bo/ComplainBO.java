package com.apps.mcare.bo;

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
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.file.bo.FileBO;
import com.unieap.mdm.bo.ChangeLogBO;
import com.unieap.pojo.FileArchive;

@Service("complainBO")
public class ComplainBO extends BaseBO {
	private String[] allowFiles = { ".rar", ".doc", ".docx", ".zip", ".pdf", ".txt", ".swf", ".wmv", ".gif", ".png",
			".jpg", ".jpeg", ".bmp" };

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(AppComplain appComplain, List<FileItem> files) throws Exception {
		FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
		appComplain.setId(getSequence(null, null));
		if(files!=null && files.size() >0){
			String shareFolderPath = SYSConfig.getConfig().get("shareFolderPath");
			String mcareComplain = SYSConfig.getConfig().get("mcareComplain");
			String url = "sharefolder/mcare/images/complain";
			/*String uploadPath = fileBO.getRootPath()+ "apps" + File.separator + "mcare" + File.separator + "images" + File.separator
					+ "complain";*/
			//String uploadPath = fileBO.getRootPath()+url;
			String uploadPath = shareFolderPath+mcareComplain;
			List<FileArchive> fileArchiveList = fileBO.upload("complain", appComplain.getId().toString(), files, uploadPath, url);
			if (fileArchiveList != null) {
				appComplain.setFileId(fileArchiveList.get(0).getId());
				appComplain.setUrl(fileArchiveList.get(0).getUrl());
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
		sql.append("SELECT id,evalution,text,url,url as fileName,c.submit_date as submitDate,c.submit_by as submitBy,");
		sql.append(" feedback,status,modify_date as modifyDate,modify_by as modifyBy ");
		sql.append("FROM app_complain c where ");
		sql.append(" submit_date >='").append(vo.getSubmitDateStart()).append("' and submit_date <= '")
				.append(vo.getSubmitDateEnd()).append("' ");
		if (!StringUtils.isEmpty(vo.getSubmitBy())) {
			sql.append(" and c.submit_by ='").append(vo.getSubmitBy()).append("' ");
		}
		sql.append(" order by c.id desc ");
		StringBuffer totalSql = new StringBuffer();
		totalSql.append("SELECT count(*) FROM app_complain where ");
		
		totalSql.append(" submit_date >= '").append(vo.getSubmitDateStart()).append("' and submit_date <= '")
				.append(vo.getSubmitDateEnd()).append("' ");
		if (!StringUtils.isEmpty(vo.getSubmitBy())) {
			totalSql.append(" and submit_by ='").append(vo.getSubmitBy()).append("' ");
		}
		this.getPaginationDataByMysql(ComplainVO.class, sql.toString(), totalSql.toString(), null, page);
	}

	public List<Map<String, Object>> queryComplains(String serviceNumber) {
		StringBuffer sql = new StringBuffer("SELECT ac.id,ac.text,ac.feedback,ac.submit_date,fa.url,ac.modify_date ");
		sql.append("FROM unieap.app_complain ac left join unieap.file_archive fa on  ac.file_id = fa.id ");
		sql.append("where ac.submit_by = ? order by ac.id desc ");
		List<Map<String, Object>> datas = DBManager.getJT(null).queryForList(sql.toString(),
				new Object[] { serviceNumber });
		return datas;
	}
	
	public Map<String, String> complainDeal(String operType, AppComplain vo) throws Exception {
		if (StringUtils.equals(operType, "Modify")) {
			update(vo);
			return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
		}  else {
			throw new Exception(UnieapConstants.getMessage("comm.operation.error", new Object[] { operType }));
		}
	}
	public Map<String, String> update(AppComplain vo) throws Exception {
		AppComplain appComplain = DBManager.getHT(null).get(AppComplain.class,vo.getId());
		appComplain.setFeedback(vo.getFeedback());
		appComplain.setStatus("F");
		appComplain.setModifyDate(UnieapConstants.getDateTime(null));
		appComplain.setModifyBy(UnieapConstants.getUser().getUserCode());
		ChangeLogBO changeLogBO = (ChangeLogBO) ServiceUtils.getBean("changeLogBO");
		changeLogBO.save(appComplain.getId(), vo, "appComplain", UnieapConstants.MODIFY, UnieapConstants.UNIEAP);
		DBManager.getHT(null).update(appComplain);
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

}
