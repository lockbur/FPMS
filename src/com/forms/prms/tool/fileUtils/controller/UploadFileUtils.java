package com.forms.prms.tool.fileUtils.controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.forms.platform.core.logger.CommonLogger;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fileUtils.service.CommonFileUtils;

/**
 * 文件上传工具类
 * @author wangzf
 * @data 2014-05-21
 */
@Controller
@RequestMapping("/fileUtils")
public class UploadFileUtils{
	protected final Logger logger = Logger.getLogger(UploadFileUtils.class);
	
	/**
	 * 配置文件中文件上传的根路径
	 */
	//private final static String FILE_UPLOAD_BASE_URL=WebHelp.getSysPara("FILE_UPLOAD_BASE_URL");
	
	/**
	 * 配置文件中文件的最大大小
	 */
	//private final static long FILE_MAX_SIZE=Long.parseLong(WebHelp.getSysPara("FILE_MAX_SIZE"));
	
	/**
	 * 文件上传模式 true为ftp上传 false为本地上传 
	 */
	//private final static boolean FILE_UPLOAD_MODLE=WebHelp.getSysParaBoolean("FILE_UPLOAD_MODLE");
	
	/**
	 * FTP连接
	 */
	private static FTPClient ftpClient = new FTPClient();
	
	/**
	 * 文件的编码格式
	 */
	private static String encoding = System.getProperty("file.encoding");
	
	
	/**
	 * 日期格式化对象，将当前日期格式化成yyyyMM格式，用于生成目录。
	 */
	public static final DateFormat pathDf = new SimpleDateFormat("yyyyMMdd");
	
	//public static final DateFormat pathDDf = new SimpleDateFormat("dd");
	
	/**
	 * 日期格式化对象，将当前日期格式化成ddHHmmss格式，用于生成文件名。
	 */
	public static final DateFormat nameDf = new SimpleDateFormat("ddHHmmss");
	
	//static{
		//FILE_UPLOAD_BASE_URL = Config.getConfig("file.dir");
		//FILE_MAX_SIZE = Config.getIntConfig("file.size") * 1024 * 1024;
		//FILE_UPLOAD_MODLE = Config.getBooleanConfig("file.ftpSwitch");
	//}
	
	/**
	 * 文件上传
	 */
	@RequestMapping("/uploadFile.do")
	public String uploadFile(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String originalFilename = null;
		String fileClientName = null;
		String fileUrl = null;
		response.setContentType("text/plain; charset=UTF-8");
		PrintWriter out = response.getWriter();
		CommonsMultipartResolver multipartResolver=new CommonsMultipartResolver(
                request.getSession().getServletContext());
		
        if(multipartResolver.isMultipart(request))
        {
            MultipartHttpServletRequest multiRequest=(MultipartHttpServletRequest)request;
            Iterator iter=multiRequest.getFileNames();
            while(iter.hasNext())
            {
                MultipartFile myfile=multiRequest.getFile(iter.next().toString());
                if(myfile.isEmpty()){
    				out.print("1 | 请选择文件后上传");
    				out.flush();
    				return null;
    			}
    			else if(myfile.getSize()>Long.parseLong(WebHelp.getSysPara("FILE_MAX_SIZE"))* 1024 * 1024){
    				out.print("1 | 选择文件太大");
    				out.flush();
    				return null;
    			}else{
    				originalFilename = myfile.getOriginalFilename();
    				try {
    					// 上传文件的返回地址
    					//fileUrl = getUploadPath(request);
    					fileUrl=WebHelp.getSysPara("FILE_UPLOAD_BASE_URL");//服务器临时文件路径，可以定时清理该目录下文件
    					CommonFileUtils.FileDirCreate(fileUrl);
    					// 为了客户端已经设置好了图片名称在服务器继续能够明确识别，这里对文件进行重名
    					String ext = FilenameUtils.getExtension(originalFilename);
    					fileClientName = this.genFileName(ext);
    					if(WebHelp.getSysParaBoolean("FILE_UPLOAD_MODLE")){
    						boolean isSuccess = this.uploadFile(fileUrl,fileClientName,myfile.getInputStream());
        					if(!isSuccess){
        						out.print("1 | ftp服务器连接失败，请联系系统管理员！！\n");
        						out.flush();
            					return null;
        					}
    					}else{
    						// 构建上传目录
        					File floder = buildFolder(fileUrl);
        					File newfile = new File(floder, fileClientName);
        					FileUtils.copyInputStreamToFile(myfile.getInputStream(), newfile);
    					}
    				} catch (Exception e) {
    					e.printStackTrace();
    					out.print("1 | 文件上传失败，请重试！！\n"+e.getMessage());
    					out.flush();
    					return null;
    				}
    			}
            }
        }
        fileUrl =  fileUrl + File.separator + fileClientName;
        if(!"/".equals(File.separator)){
        	fileUrl = fileUrl.replace(File.separator, "/");
        }
		out.print("0 | "+originalFilename+" | " +  fileUrl);
		out.flush();
		return null;
	}
	
	/**
	 * 创建目录
	 * @return
	 */
	private String getUploadPath(HttpServletRequest request)throws Exception {
		StringBuffer folderdir = null;
		String returnPath = "";
		//文件存放路径为相对路径的时候
		folderdir = new StringBuffer(WebHelp.getSysPara("FILE_UPLOAD_BASE_URL"));
		if(folderdir.lastIndexOf(File.separator)!=folderdir.length()-1){
			folderdir.append(File.separator);
		}
		folderdir.append(this.genPathName());
		if(WebHelp.getSysParaBoolean("FILE_UPLOAD_MODLE")){
			returnPath = folderdir.toString();
		}else{
			if(WebHelp.getSysPara("FILE_UPLOAD_BASE_URL").startsWith(File.separator)&&!WebHelp.getSysPara("FILE_UPLOAD_BASE_URL").contains(":")){
				returnPath = request.getSession().getServletContext().getRealPath(folderdir.toString());
			}else{
				returnPath = folderdir.toString();
			}
		}
		return returnPath;
	}
	

	/**
	 * 创建目录
	 * 
	 * @return
	 */
	private File buildFolder(String fileUrl)throws Exception {
		File floder = new File(fileUrl);
		if (!floder.exists()) {
			if (!floder.mkdirs()) {
				CommonLogger.error("创建文件夹出错！filePath=" + fileUrl);
				throw new Exception("创建文件夹路径出错,传入文件夹路径有误！");
			}
		}
		return floder;
	}
	
	/**
	 * 生成当前年月格式的文件路径
	 * @return
	 */
	public String genPathName() {
		//return pathDf.format(new Date())+File.separator+pathDDf.format(new Date());
		return pathDf.format(new Date());
	}

	/**
	 * 生产以当前日、时间开头加4位随机数的文件名
	 * @return 10位长度文件名
	 */
	public synchronized String genFileName() {
		return nameDf.format(new Date())
				+ RandomStringUtils.randomNumeric(4);
	}

	/**
	 * 生产以当前时间开头加4位随机数的文件名
	 * @return 10位长度文件名+文件后缀
	 */
	public String genFileName(String ext) {
		return genFileName() + "." + ext;
	}
	
	public String getFileSufix(String fileName) {
		int splitIndex = fileName.lastIndexOf(".");
		return fileName.substring(splitIndex + 1);
	}
	
	/**
	* Description: 向FTP服务器上传文件
	* @param path
	* FTP服务器保存目录,如果是根目录则为“/”
	* @param filename
	* 上传到FTP服务器上的文件名
	* @param input
	* 本地文件输入流
	* @return 成功返回true，否则返回false
	*/
	private boolean uploadFile(String path, String filename, InputStream input) {
		boolean result = false;
		try {
			int reply;
			Map pmap = WebHelp.getParaValueList();
			//创建ftp连接
			ftpClient.connect((String)pmap.get("SYS_ATTACH_FTP_IP"),Integer.parseInt((String)pmap.get("SYS_ATTACH_FTP_PORT")));
			// 登录
			ftpClient.login((String)pmap.get("SYS_ATTACH_UPLOAD_USER"), (String)pmap.get("SYS_ATTACH_UPLOAD_PWD"));
			//设置编码格式
			ftpClient.setControlEncoding(encoding);
			// 检验是否连接成功
			reply = ftpClient.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) {
				System.out.println("连接失败");
				logger.error("连接FTP服务器失败！");
				ftpClient.disconnect();
				return result;
			}
			//切换到指定的路径
			boolean change = ftpClient.changeWorkingDirectory(path);
			if(!change){
				//创建目录
				change = ftpClient.makeDirectory(path);
				//切换目录
				change = ftpClient.changeWorkingDirectory(path);
			}
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			if (change) {
				result = ftpClient.storeFile(new String(filename.getBytes(encoding),"iso-8859-1"), input);
				if (result) {
					System.out.println("上传成功!");
				}
			}
			input.close();
			ftpClient.logout();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (ftpClient.isConnected()) {
				try {
					ftpClient.disconnect();
				} catch (IOException ioe) {
				}
			}
		}
		return result;
	}
	
	public File invokeBuildFloder(String fileUrl) throws Exception{
		return buildFolder(fileUrl);
	}
}
