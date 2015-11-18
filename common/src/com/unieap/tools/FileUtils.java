package com.unieap.tools;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.URL;
import java.net.URLConnection;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * Dec 3, 2010
 */
public class FileUtils {
	Log log = LogFactory.getLog(FileUtils.class);

	/**
	 * <p>
	 * 描述:获取输入流
	 * </P>
	 * Jan 24, 2011
	 * 
	 * @param path
	 * @return
	 * @throws Exception
	 */
	public static InputStream getInputStream(String path) throws Exception {
		URL url = FileUtils.class.getResource(path);
		if (url == null) {
			throw new Exception("文件路径[" + path + "]不正确!");
		}
		URLConnection uc = url.openConnection();
		InputStream input = uc.getInputStream();
		return input;
	}

	/**
	 *<p>描述:创建文件</P>
	 * Jan 26, 2011
	 * @param path
	 * @param isCover
	 * @param isEnter
	 * @param str
	 * @throws Exception
	 */
	public static void write(String path, boolean isCover, boolean isEnter, String str) throws Exception {
		create(path);
		File file = new File(path);
		OutputStreamWriter out = null;
		String feed = str;
		if (isEnter) {
			feed = feed + '\n';
		}
		try {
			out = new OutputStreamWriter(new FileOutputStream(file), "UTF-8");
			out.write(feed);
			out.flush();
		} catch (IOException e) {
			throw new Exception("write file [" + path + "] failure!", e);
		} finally {
			out.close();
		}
	}
	/**
	 *<p>描述:创建文件夹</P>
	 * Jan 26, 2011
	 * @param name
	 * @return
	 * @throws Exception
	 */
	public static boolean create(String name) throws Exception {
		File f = new File(name);
		if (f.exists()) {
			return true;
		}
		String path = StringUtils.substringBeforeLast(name, "/");
		createDir(path);
		File file = new File(name);
		try {
			file.createNewFile();
		} catch (IOException e) {
			throw new Exception("create file [" + name + "] failure,message:" + e.getMessage(), e);
		}
		return true;
	}
	/**
	 *<p>描述:创建默认</P>
	 * Jan 26, 2011
	 * @param path
	 * @return
	 */
	public static boolean createDir(String path) {
		File filepath = new File(path);
		if (!filepath.exists()) {
			return filepath.mkdirs();
		}
		return true;
	}
	/**
	 *<p>描述:读取资源文件</P>
	 * Jan 27, 2011
	 * @param fis
	 * @return
	 * @throws Exception
	 */
	public static Properties readProperties(InputStream fis) throws Exception {
		Properties properties = new Properties(); 
		properties.load(fis); 
		return properties;
		//System.out.println("姓名："+properties.getProperty("package")+" 性别: "+properties.getProperty("sex")); 
		//return null;

	}
}
