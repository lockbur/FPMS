package com.forms.prms.tool.fileUtils.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.forms.platform.core.exception.Throw;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.util.Tool;
import com.forms.prms.tool.WebHelp;
/**
 * 文件下载工具类
 * @author wangzf
 * @data 2014-05-21
 */
@Controller
@RequestMapping("/fileUtils")
public class DownloadFileUtils {
	protected final Logger logger = Logger.getLogger(DownloadFileUtils.class);
	
	/**
	 * 文件上传模式 true为ftp上传 false为本地上传 
	 */
	//private   boolean FILE_UPLOAD_MODLE=WebHelp.getSysParaBoolean("FILE_UPLOAD_MODLE");
	
	/**
	 * FTP连接
	 */
	private static FTPClient ftpClient = new FTPClient();
	
	/**
	 * 文件的编码格式
	 */
	private static String encoding = System.getProperty("file.encoding");
	
	//static{
		//FILE_UPLOAD_MODLE = Config.getBooleanConfig("file.ftpSwitch");
	//}
	
	@RequestMapping(value="/fileDownload.do")
	public String fileDownload(HttpServletRequest request, HttpServletResponse response) throws IOException{
		String fileShowName = request.getParameter("fileName");   //显示的文件名
		String filePathName = request.getParameter("filePath");  //文件存放路径
		String openModel = request.getParameter("openModel");  //文件的操作方式  在线打开 或 下载本地
		
		try
		{
			if(!Tool.CHECK.isBlank(fileShowName)&&!Tool.CHECK.isBlank(filePathName)){
				//根据文件的操作方式设置消息头
				if(Tool.CHECK.isBlank(openModel)){
					//直接下载
					response.setContentType("application/x-msdownload;charset=UTF-8");
					response.setHeader("Content-Disposition", "attachment;filename=" + new String(fileShowName.getBytes("GB2312"), "ISO-8859-1"));
				}else{
					//在线打开
					//根据文件的类型设置消息头
					this.setResponseHead(response,filePathName,fileShowName);
				}
				if(WebHelp.getSysParaBoolean("FILE_UPLOAD_MODLE")){
					boolean isSuccess = this.downloadFile(filePathName, fileShowName, response);
					if(!isSuccess){
						response.reset();
						response.setCharacterEncoding("gb2312");
						PrintWriter pw = response.getWriter();
						pw.write("<script type='text/javascript'>alert('文件下载异常!');</script>");
						pw.flush();
						pw.close();
					}
				}else{
					this.download(fileShowName,filePathName ,request, response);
				}
			}else{
				response.reset();
				response.setCharacterEncoding("gb2312");
				PrintWriter pw = response.getWriter();
				pw.write("<script type='text/javascript'>alert('文件下载异常!');</script>");
				pw.flush();
				pw.close();
			}
		}
		catch (Exception e)
		{
			logger.error("[FileDownloadController.download() Exception]:" + e);
			e.printStackTrace();
			response.reset();
			response.setCharacterEncoding("gb2312");
			PrintWriter pw = response.getWriter();
			pw.write("<script type='text/javascript'>alert('文件下载异常!');</script>");
			pw.flush();
			pw.close();
		}
		return null;
	}
	
	private void download(String fileName,String filePathName
			,HttpServletRequest request,HttpServletResponse response) throws Exception{
		BufferedOutputStream bos = null;
		FileInputStream fis = null;
		try
		{	
			String fileRealPath = filePathName;
			if(!"/".equals(File.separator)){
				fileRealPath = filePathName.replace("/", File.separator);
			}
			File file = new File(fileRealPath);
			if(!file.exists())
			{
				throw new Exception("找不到指定文件："+fileRealPath);
			}
			// 开始下载
			fis = new FileInputStream(file);
			bos = new BufferedOutputStream(response.getOutputStream());
			byte[] b = new byte[8192];
			int data = 0;
			while ((data = fis.read(b)) != -1)
			{
				bos.write(b, 0, data);
			}
			// 刷新流
			bos.flush();
		}
		catch (Exception e)
		{
			throw e;
		}
		finally
		{
			if (bos != null)
				bos.close();
			if (fis != null)
				fis.close();
		}
	}
	
	/**
	* Description: 从FTP服务器下载文件
	* 
	* @param remotePath
	* FTP服务器上的相对路径
	* @param fileName
	* 要下载的文件名
	* @param response
	* 服务器响应请求
	* @return
	*/
	private boolean downloadFile(String path,String fileName,HttpServletResponse response){
		boolean result = false;
		BufferedOutputStream bos = null;
		InputStream is = null;
		String ftpFileName = ""; //FTP服务器保存的文件名
		try{
			int reply;
			ftpClient.setControlEncoding(encoding);
			
			Map pmap = WebHelp.getParaValueList();
			//创建ftp连接
			ftpClient.connect((String)pmap.get("SYS_ATTACH_FTP_IP"),Integer.parseInt((String)pmap.get("SYS_ATTACH_FTP_PORT")));
			// 登录
			ftpClient.login((String)pmap.get("SYS_ATTACH_UPLOAD_USER"), (String)pmap.get("SYS_ATTACH_UPLOAD_PWD"));
			// 设置文件传输类型为二进制
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
			// 获取ftp登录应答代码
			reply = ftpClient.getReplyCode();
			// 验证是否登陆成功
			if (!FTPReply.isPositiveCompletion(reply)) {
				ftpClient.disconnect();
				logger.error("连接ftp服务器失败");
				return result;
			}
			// 转移到FTP服务器目录至指定的目录下
			path = new String(path.getBytes(encoding),"iso-8859-1");
			if(!"/".equals(File.separator)){
				path = path.replace("/", File.separator);
			}
			if(path.contains(".")){
				ftpFileName = path.substring(path.lastIndexOf(File.separator)+1);
				path = path.substring(0,path.lastIndexOf(File.separator));
			}
			boolean isSuccess = ftpClient.changeWorkingDirectory(path);
			if(isSuccess){
				// 获取文件列表
				FTPFile[] fs = ftpClient.listFiles();
				//fileName = new String(fileName.getBytes(encoding),"iso-8859-1");
				for (FTPFile ff : fs) {
					if (ff.getName().equals(ftpFileName)) {
						// 开始下载
						is = ftpClient.retrieveFileStream(ff.getName());
						bos = new BufferedOutputStream(response.getOutputStream());
						byte[] b = new byte[8192];
						int data = 0;
						while ((data = is.read(b)) != -1)
						{
							bos.write(b, 0, data);
						}
						// 刷新流
						bos.flush();
						break;
					}
				}
				ftpClient.logout();
				result = true;
			}else{
				logger.error("下载文件路径不存在");
				return result;
			}
		}catch(Exception e){
			e.printStackTrace();
			logger.error("下载文件失败");
		}finally{
			try {
				if (bos != null){
					bos.close();
				}
				if (is != null){
						is.close();
				}
				if (ftpClient.isConnected()) {
					ftpClient.disconnect();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return result;
	}
	
	/**
	* Description: 从FTP服务器下载文件
	* 
	* @param response
	* 服务器响应请求
	* @param filePath
	* 文件完整路径
	* @param fileName
	* 文件名
	* @return
	*/
	private void setResponseHead(HttpServletResponse response,String filePath,String fileName){
		String suffix = filePath.contains(".")?filePath.substring(filePath.lastIndexOf(".")+1):fileName.substring(fileName.lastIndexOf(".")+1);
		if("DOC".equals(suffix.toUpperCase())||"DOCX".equals(suffix.toUpperCase())){
			response.setContentType("application/msword;charset=UTF-8");
		}else if("XLS".equals(suffix.toUpperCase())||"XLSX".equals(suffix.toUpperCase())){
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		}else if("TXT".equals(suffix.toUpperCase())){
			response.setContentType("text/plain;charset=UTF-8");
		}else if("PDF".equals(suffix.toUpperCase())){
			response.setContentType("application/pdf;charset=UTF-8");
		}else if("BMP".equals(suffix.toUpperCase())||"GIF".equals(suffix.toUpperCase())||"JPEG".equals(suffix.toUpperCase())){
			response.setContentType("image/"+suffix.toLowerCase()+";charset=UTF-8");
		}else if("HTML".equals(suffix.toUpperCase())||"XML".equals(suffix.toUpperCase())){
			response.setContentType("text/"+suffix.toLowerCase()+";charset=UTF-8");
		}else if("VISIO".equals(suffix.toUpperCase())){
			response.setContentType("application/vnd.visio;charset=UTF-8");
		}
		try {
			response.setHeader("Content-Disposition", "inline;filename=" + new String(fileName.getBytes("GB2312"), "ISO-8859-1"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @methodName download
	 * 
	 * @param showFileName	:显示的文件名称
	 * @param saveFile		:需下载的文件保存的路径
	 * @param response		:
	 * @return	returnMap	:下载操作的结果(包含：operFlag操作结果标识、errorType错误类型、errorMsg错误信息)
	 * @throws Exception
	 */
	public static Map<String , Object> download(String showFileName, String saveFile,HttpServletResponse response) throws Exception
	{
		FileInputStream in = null;
		OutputStream out = null;
		//Map用于保存方法返回值(返回的key：operFlag、errorType、errorMsg)
		Map<String , Object> returnMap = new HashMap<String , Object>();		
		boolean operFlag = false;
		try 
		{
			out = response.getOutputStream();
			
			//1.校验文件存在性
			File inputFile = new File(saveFile);
			if(!inputFile.exists()){
				//如果该文件不存在，直接返回false(都不用再继续进行文件复制下载操作了！)
				out = null;
				returnMap.put("operFlag", operFlag);
				returnMap.put("errorType", 1);			//文件不存在
				//出现该报错信息时，说明服务器上的"saveFile"该路径不存在需要下载的文件，请将文件放置于"saveFile"路径下，或者重新配置SYS_TEMPLATE_INFO表的参数
				returnMap.put("errorMsg", "需要下载的文件不存在于服务器配置路径中，请联系管理员配置该文件！");			
				return returnMap;
			}
			//2.通过校验，进行文件复制下载
			response.setContentType("appliation/octet-stream;charset=GBK");
			response.addHeader("Content-Disposition", "attachment;filename=" + new String(showFileName.getBytes("GBK"), "ISO-8859-1"));
			in = new FileInputStream(inputFile);
			//3.复制下载文件到下载路径,若无异常,则同时返回方法处理结果信息
			Tool.IO.copy(in, out);
			returnMap.put("operFlag", true);
			return returnMap;						//下载操作无报错、报异常，正常结束并返回值
		} catch (Exception e) {
			CommonLogger.error("Download failed in [Class :DownloadFileUtils  Method : download]: " + e.getMessage(), e);
			out = null;								//下载操作出现Exception，重置输出流为null，不再下载文件
			Throw.throwException(e);				//抛出该异常
		} finally {
			Tool.IO.closeQuietly(in);
			Tool.IO.closeQuietly(out);
		}
		return returnMap;
	}
}
