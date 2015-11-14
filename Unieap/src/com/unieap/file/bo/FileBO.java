package com.unieap.file.bo;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.db.DBManager;
import com.unieap.file.vo.FileUploadVO;
import com.unieap.pojo.FileArchive;

@Service("fileBO")
public class FileBO extends BaseBO {
	
	public Map<String,String> saveFile(String parameters,String handlerId, FileUploadVO vo){
		return null;
	}
	
	
	public List<FileItem> getFileItems(HttpServletRequest request) throws Exception {
		String tempPath = getRootPath() + File.separator + "buffer";
		File tempPathFile = new File(tempPath);
		if (!tempPathFile.exists()) {
			tempPathFile.mkdirs();
		}
		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();
		// Set factory constraints
		factory.setSizeThreshold(4096); // 设置缓冲区大小，这里是4kb
		factory.setRepository(tempPathFile);// 设置缓冲区目录
		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);
		// Set overall request size constraint
		upload.setSizeMax(4194304); // 设置最大文件尺寸，这里是4MB
		List<FileItem> items = upload.parseRequest(request);// 得到所有的文件
		return items;
	}

	public FileArchive getFileArchive(String fileId) throws Exception {
		FileArchive fileArchive = DBManager.getHT(null).get(FileArchive.class, new Integer(fileId));
		if (fileArchive == null) {
			throw new Exception("file id[" + fileId + "] not existing");
		}
		return fileArchive;
	}

	public String[] upload(String fileCategory, String extKey, List<FileItem> files, String uploadPath, String url)
			throws Exception {
		StringBuffer fileIds = new StringBuffer();
		if (files != null && files.size() > 0) {
			DecimalFormat df = new DecimalFormat(".00");
			for (int i = 0; i < files.size(); i++) {
				FileItem fileItem = files.get(i);
				String fileName = fileItem.getName();
				if(!StringUtils.isEmpty(fileName)){
					String fileSize = df.format(fileItem.getSize() / 1024);
					String fileType = StringUtils.split(fileName, ".")[1];
					// String filePath = SYSConfig.filePath +
					// "\\complain\\"+fileName;
					File uploadFile = new File(uploadPath);
					if (!uploadFile.exists()) {
						uploadFile.mkdirs();
					}
					File fullFile = new File(fileItem.getName());
					File savedFile = new File(uploadPath, fullFile.getName());
					fileItem.write(savedFile);
					FileArchive fileArchive = new FileArchive();
					fileArchive.setArchiveDate(UnieapConstants.getDateTime(null));
					fileArchive.setExtKey(extKey);
					fileArchive.setFileCategory(fileCategory);
					fileArchive.setFileName(fileName);
					fileArchive.setFilePath(uploadPath);
					fileArchive.setFileSize(new Double(fileSize));
					fileArchive.setFileType(fileType);
					fileArchive.setId(getSequence(null, null));
					url = url+File.separator+fileName;
					fileArchive.setUrl(url);
					fileIds.append(fileArchive.getId().toString()).append(",");
					DBManager.getHT(null).save(fileArchive);
				}
			}
		}
		return fileIds.toString().split(",");

	}

	public Map<String, String> downLoad(String fileId, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		FileArchive fileArchive = getFileArchive(fileId);
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName=" + "file name");
		response.setHeader("Content-Length", String.valueOf(fileArchive.getFileSize()));
		String filePath = fileArchive.getFilePath();
		FileInputStream fs = null;
		OutputStream os = null;
		try {
			fs = new FileInputStream(new File(filePath));
			os = response.getOutputStream();
			byte[] b = new byte[1024];
			int length;
			while ((length = fs.read(b)) > 0) {
				os.write(b, 0, length);
			}
			os.flush();
			response.setStatus(HttpServletResponse.SC_OK);
			response.flushBuffer();
		} catch (Exception e) {
			throw e;
		} finally {
			if (fs != null) {
				fs.close();
			}
			if (os != null) {
				os.close();
			}
		}
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public String getRootPath() {
		String path = getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF"));
		}
		return path;
		// return SYSConfig.rootPath+File.separator;
	}
}
