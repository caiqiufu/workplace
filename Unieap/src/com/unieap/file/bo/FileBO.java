package com.unieap.file.bo;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Service;

import com.unieap.BaseBO;
import com.unieap.UnieapConstants;
import com.unieap.file.vo.FileUploadVO;
import com.unieap.pojo.HandlerConfig;

@Service("fileBO")
public class FileBO extends BaseBO {
	private String[] allowFiles = { ".rar", ".doc", ".docx", ".zip", ".pdf",
			".txt", ".swf", ".wmv", ".gif", ".png", ".jpg", ".jpeg", ".bmp" };

	public Map<String, String> SaveFile(String parameters,
			HandlerConfig handlerConfig, FileUploadVO vo) throws Exception {
		/*Map paras = JSONUtils.jsonToMap(parameters);
		MAtta atta = new MAtta();
		atta.setAttaId(getSequence(null, "unieap"));
		atta.setCreateDate(UnieapConstants.getDateTime(null));
		atta.setDefectId(Integer.valueOf(paras.get("defectId").toString()));
		atta.setFileName(vo.getFile().getOriginalFilename());
		atta.setFilePath(SYSConfig.filePath + File.separator
				+ vo.getFile().getOriginalFilename());
		DecimalFormat df = new DecimalFormat(".00");
		atta.setFileSize("" + df.format(vo.getFile().getSize() / 1024));
		atta.setFileType(StringUtils.split(vo.getFile().getOriginalFilename(),
				".")[1]);
		DBManager.getHT(null).save(atta);
		String path = getRootPath() + SYSConfig.filePath;
		File dirPath = new File(path);
		if (!dirPath.exists()) {
			dirPath.mkdirs();
		}
		File uploadedFile = new File(dirPath.getPath(), vo.getFile()
				.getOriginalFilename());
		FileCopyUtils.copy(vo.getFile().getBytes(), uploadedFile);*/
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public Map<String, String> downLoad(String parameters,
			HandlerConfig handlerConfig, HttpServletRequest request,
			HttpServletResponse response) throws SQLException {
		//DBManager.getHT(null).get(null,null);
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		response.setHeader("Content-Disposition", "attachment;fileName="
				+ "file name");
		response.setHeader("Content-Length",
				String.valueOf("file size"));
		String filePath = getRootPath() + "file path";
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
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fs != null) {
				try {
					fs.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if (os != null) {
				try {
					os.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

			}
		}
		return result(UnieapConstants.ISSUCCESS, UnieapConstants.SUCCESS);
	}

	public String getRootPath() {
		String path = getClass().getProtectionDomain().getCodeSource()
				.getLocation().getPath();
		if (path.indexOf("WEB-INF") > 0) {
			path = path.substring(0, path.indexOf("WEB-INF/classes"));
		}
		return path;
		// return SYSConfig.rootPath+File.separator;
	}
}
