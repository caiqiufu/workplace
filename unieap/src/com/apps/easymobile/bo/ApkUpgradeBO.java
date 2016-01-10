package com.apps.easymobile.bo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.apps.easymobile.pojo.AppUpgrade;
import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.base.SYSConfig;
import com.unieap.base.ServiceUtils;
import com.unieap.base.vo.PaginationSupport;
import com.unieap.db.DBManager;
import com.unieap.file.bo.FileBO;
import com.unieap.pojo.FileArchive;

@Service("apkUpgradeBO")
public class ApkUpgradeBO extends BaseBO {

	public void getapkUpgradeList(PaginationSupport page) throws Exception {
		DetachedCriteria criteria = DetachedCriteria.forClass(AppUpgrade.class);
		getPaginationDataByDetachedCriteria(criteria, page);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Map<String, String> apkUpgradeDeal(String operType, String parameters, List<FileItem> files)
			throws Exception {
		JSONObject json = new JSONObject(parameters);
		String id = json.getString("id");
		String activeFlag = json.getString("activeFlag");
		String version = json.getString("version");
		String changeDesc = json.getString("changeDesc");
		String effectiveDate = json.getString("effectiveDate");
		SimpleDateFormat sdf = new SimpleDateFormat(UnieapConstants.TIMEFORMAT);
		Date date = sdf.parse(effectiveDate);
		String remark = json.getString("remark");
		if (StringUtils.equals(operType, UnieapConstants.ADD)) {
			AppUpgrade appUpgrade = new AppUpgrade();
			appUpgrade.setId(UnieapConstants.getSequence(null, null));
			appUpgrade.setCreateDate(UnieapConstants.getDateTime(null));
			appUpgrade.setTenantId(SYSConfig.getConfig().get("tenantId"));
			appUpgrade.setActiveFlag(activeFlag);
			appUpgrade.setVersion(version);
			appUpgrade.setChangeDesc(changeDesc);
			appUpgrade.setEffectiveDate(date);
			appUpgrade.setRemark(remark);
			if (files != null && files.size() > 0) {
				FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
				String url = "apps/mcare/apk";
				String uploadPath = fileBO.getRootPath() + url;
				List<FileArchive> fileArchiveList = fileBO.upload("apk", appUpgrade.getId().toString(), files,
						uploadPath, url);
				if (fileArchiveList != null) {
					appUpgrade.setFileId(fileArchiveList.get(0).getId().toString());
					appUpgrade.setUrl(fileArchiveList.get(0).getUrl());
				}
				DBManager.getHT(null).save(appUpgrade);
			}
		} else if (StringUtils.equals(operType, UnieapConstants.MODIFY)) {
			AppUpgrade appUpgrade = DBManager.getHT(null).get(AppUpgrade.class, new Integer(id));
			appUpgrade.setActiveFlag(activeFlag);
			appUpgrade.setVersion(version);
			appUpgrade.setChangeDesc(changeDesc);
			appUpgrade.setEffectiveDate(date);
			appUpgrade.setRemark(remark);
			DBManager.getHT(null).update(appUpgrade);
		} else {
			throw new Exception(UnieapConstants.getMessage("comm.operation.error", new Object[] { operType }));
		}
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public void save(AppUpgrade vo, List<FileItem> files) throws Exception {
		FileBO fileBO = (FileBO) ServiceUtils.getBean("fileBO");
		vo.setId(getSequence(null, null));
		vo.setTenantId(SYSConfig.getConfig().get("tenantId"));
		vo.setActiveFlag(UnieapConstants.YES);
		vo.setCreateDate(UnieapConstants.getDateTime(null));
		if (files != null && files.size() > 0) {

			String shareFolderPath = SYSConfig.getConfig().get("shareFolderPath");
			String mdmCommonPath = SYSConfig.getConfig().get("mdmCommonPath");
			String url = SYSConfig.getConfig().get("url.apk");
			String uploadPath = shareFolderPath + mdmCommonPath+"/apk";

			List<FileArchive> fileArchiveList = fileBO.upload("apk", vo.getId().toString(), files, uploadPath, url);
			if (fileArchiveList != null) {
				vo.setFileId(fileArchiveList.get(0).getId().toString());
				vo.setUrl(fileArchiveList.get(0).getUrl());
			}
		}
		DBManager.getHT(null).save(vo);
	}
}
