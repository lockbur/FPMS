package com.forms.prms.tool.fileUtils.service;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;

import com.forms.platform.core.exception.Throw;
import com.forms.platform.core.logger.CommonLogger;
import com.forms.platform.core.spring.util.SpringUtil;
import com.forms.prms.tool.SFTPTool;
import com.forms.prms.tool.WebHelp;
import com.forms.prms.tool.fileUtils.dao.DBFileOperDAO;
import com.forms.prms.tool.fileUtils.domain.DBFileBean;
import com.forms.prms.web.util.GzipUtil;

/**
 * Copy Right Information : Forms Syntron <br>
 * Project : ERP<br>
 * JDK version used : jdk 1.6.0<br>
 * Description : 文件存储到数据库工具类<br>
 * Comments Name : com.forms.prms.tool.fileUtils.service.DBFileOperUtil.java <br>
 * author : SunKai <br>
 * date : 2015-9-9<br>
 * Version : 1.00 <br>
 * editor : <br>
 * editorDate : <br>
 */

@Service
public class DBFileOperUtil 
{
	
	@Autowired
	private DBFileOperDAO dao;
	
	@Autowired
	private CommonFileUtils cFileUtils;
	
	/**
	 * Spring获取当前class的bean实例 
	 * @return
	 */
	public static DBFileOperUtil getInstance(){
		return SpringUtil.getBean(DBFileOperUtil.class);
	}
	
	/**
	 * 
	 * 根据文件全路径名称 存储文件到数据库
	 * @param filePath 		文件全路径名
	 * 
	 * */
	public void  saveFileToDB(String filePath) throws Exception 
	{
		//saveFileToDB(filePath,false);
	}
	
	
	/**
	 * 
	 * 根据文件全路径名称 存储文件到数据库
	 * @param filePath 		文件全路径名
	 * @param removeable 	是否可删除
	 * */
	public void  saveFileToDB(String filePath,boolean removeable) throws Exception 
	{
		//判断需要保存至数据库的源文件是否存在
//		File locFile=new File(filePath);
//		if(!locFile.exists())
//		{
//			throw new FileNotFoundException();
//		}
//		DBFileBean  dbFileBean=new DBFileBean();
//		dbFileBean.setFileName(locFile.getName().substring(0, locFile.getName().lastIndexOf(".")));		//设置bean的文件名
//		dbFileBean.setFileType(locFile.getName().substring(locFile.getName().lastIndexOf(".")+1));		//设置bean的文件类型
//		dbFileBean.setRemoveFlag(removeable?"1":"0");
//		//判断创建保存文件的路径
//		String fileTempPath = WebHelp.getSysPara("FILE_UPLOAD_BASE_URL")+"/zip/";
//		CommonFileUtils.createFileFolder( null, fileTempPath );						
//		//压缩上传文件
//		GzipUtil.compressGZIP(filePath, fileTempPath);
//		
//	    File zipFile=new File( fileTempPath + dbFileBean.getFileName()+"."+dbFileBean.getFileType()+".gz" );
//		dbFileBean.setDbFile(zipFile);
//		dbFileBean =this.readFileData(dbFileBean);
//		dao.addFile(dbFileBean);
	}
	
	/**
	 * 
	 * 根据文件全路径名称 下载数据库中的文件到服务器磁盘
	 * @param filePath 文件全路径名
	 * 
	 * */
	public void findFileFromDB(String filePath) throws Exception{
		CommonLogger.info("[集群文件操作工具类,根据文件路径从DB中查找并复制该文件,文件路径为:"+filePath+",DBFileOperUtil,findFileFromDB()");
		//通过文件路径截取获取重命名的文件名
		String fileName = filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf("."));
		//将上述文件名作为key值，到DB中查找获取该文件对象
		DBFileBean  dbFileBean = dao.findFile(fileName);
		if(dbFileBean==null||dbFileBean.getFileData()==null)
		{
			CommonLogger.error("[集群文件操作工具类]:从DB中查找该文件，无此文件！请联系管理员检查SYS_FILE_INFO表中是否存在该文件，" +
														"文件名称为:["+fileName+"],文件路径为:["+filePath+"],DBFileOperUtil,findFileFromDB()");
			throw new FileNotFoundException();
		}
		dbFileBean=this.wirteFileData(dbFileBean);
		//将文件从DB中下载，并解压到指定的文件夹路径
		GzipUtil.uncompressGZIP(WebHelp.getSysPara("FILE_UPLOAD_BASE_URL")+"/zip/"+dbFileBean.getFileName()+"."+dbFileBean.getFileType()+".gz", WebHelp.getSysPara("FILE_UPLOAD_BASE_URL")+"/zip/", dbFileBean.getFileName()+"."+dbFileBean.getFileType());
		//将文件复制到用户提供的指定路径
		FileCopyUtils.copy(new File(WebHelp.getSysPara("FILE_UPLOAD_BASE_URL")+"/zip/"+dbFileBean.getFileName()+"."+dbFileBean.getFileType()), new File(filePath));
	}
	
	public void downloadFileFromFTP(String filePath) throws Exception{
		CommonLogger.info("[集群文件操作工具类,根据文件路径从FTP中下载文件,文件路径为:"+filePath+",DBFileOperUtil,downloadFileFromFTP()");
		//通过文件路径截取获取重命名的文件名
		String fileName = filePath.substring(filePath.lastIndexOf("/")+1);
		String fileParentPath = filePath.substring(0,filePath.lastIndexOf("/"));
		String ftpPath = CommonFileUtils.getFtpPath(fileParentPath);
		
		String shareIp = WebHelp.getSysPara("FTP_SHAREFILE_HOSTADD");//FTP文件共享服务器地址
		int sharePort = Integer.parseInt(WebHelp.getSysPara("FTP_SHAREFILE_PORT"));//FTP文件共享服务器端口
		String shareUser = WebHelp.getSysPara("FTP_SHAREFILE_USER");//FTP文件共享服务器用户名
		String sharePwd = WebHelp.getSysPara("FTP_SHAREFILE_PWD");//FTP文件共享服务器密码
		SFTPTool sftpTool = SFTPTool.getNewInstance();
		sftpTool.downloadFile(shareIp, sharePort, shareUser, sharePwd,
				ftpPath, fileParentPath, fileName);
	}
	
	/**
	 * 根据文件全路径和response响应下载文件到客户端
	 * 		内部处理逻辑：
	 * 			1.校验目标下载文件是否存在于本地服务器，如果存在则直接调用downloadFile()执行文件下载；
	 * 			2.当上述校验为文件不存在于本地服务器时，则调用findFileFromDb()将目标文件下载到本地服务器中指定路径，再执行本地文件下载操作；
	 * @param 	filePath
	 * @param 	response
	 * @return	operFlag	true=下载成功/false=下载失败
	 */
	public boolean getFileDownFromPathAndDb(String downFilePath , String downFileName , 
														HttpServletRequest request , HttpServletResponse response) throws Exception{
		CommonLogger.info("[执行使用集成方式进行文件下载操作],下载文件名为:"+downFileName+"下载文件路径为:"+downFilePath+",DBFileOperUtil,getFileDownFromPathAndDb()");
		boolean operFlag = false;
		//1.判断是否需要执行集群方式从DB将文件复制到本地
		if(!new File(downFilePath).exists()){
//			CommonLogger.debug("[集成文件下载操作]：目标文件不存在于本地服务器，将启用集群方式从数据库中	下载该文件。");
			CommonLogger.debug("[集成文件下载操作]：目标文件不存在于本地服务器，将启用集群方式从FTP下载该文件。");
			try {
				//集群方式：从DB中将文件复制到本地Server中
//				findFileFromDB(downFilePath);
				downloadFileFromFTP(downFilePath);
			} catch (Exception e) {
//				CommonLogger.error("[集成文件下载操作]：从集群DB中下载文件到本地服务器时出现【异常】，请检查!	"+this.getClass().getName()+ "	--	findFileFromDB()");
				CommonLogger.error("[集成文件下载操作]：从FTP下载文件到本地服务器时出现【异常】，请检查!	"+this.getClass().getName()+ "	--	findFileFromFTP()");
//				e.printStackTrace();
				Throw.throwException(e);
				return false;
			}
		}
		//2.执行本地文件下载操作
		try {
			if(! new File(downFilePath).exists()){
				CommonLogger.error("[集成文件下载操作]：执行集群下载文件方式处理后有异常，无法下载该文件，请检查！");
				throw new FileNotFoundException();
//				return false;
			}
//			DownloadFileUtils.download(showFileName, saveFile, response);
			cFileUtils.downloadFile(response, downFilePath , downFileName);
		} catch (Exception e) {
			String simplename = e.getClass().getSimpleName();
			//忽略浏览器客户端刷新关闭的异常的信息打印
			if("ClientAbortException".equals(simplename)){
				CommonLogger.debug("忽略客户端被无故关闭的异常 In getFileDownFromPathAndDb -- DbFileOperUtil");
			}else{
				CommonLogger.error("[集成文件下载操作]：执行本地文件下载时出现异常!	In method CommonFileUtils.downloadFile()");
				Throw.throwException(e);
				//return false;
			}
		}
		//4.上述操作若均正确执行，则应该设置operFlag=true,并返回operFlag
		CommonLogger.debug("[完成集成文件下载操作]:"+this.getClass().getName()+ "--getFileDownFromPathAndDb()");
		operFlag = true;
		return operFlag;
	}
	
	
	/**
	 * 
	 * 根据文件全路径名称 删除数据库中的文件
	 * @param filePath 文件全路径名
	 * 
	 * */
	public  void  deleteFileInDB(String filePath)
	{
		String fileName= filePath.substring(filePath.lastIndexOf("/")+1, filePath.lastIndexOf("."));
		dao.deleteFile(fileName);
	}

	
	
	/**
	 * 读取磁盘文件内容
	 * 
	 * @param dbFileBean
	 */
	private DBFileBean readFileData(DBFileBean  dbFileBean) throws Exception
	{
		byte[] fileData=null;
		try 
		{
			fileData = FileCopyUtils.copyToByteArray(dbFileBean.getDbFile());
		}
		catch (IOException e) {
			e.printStackTrace();
			throw new Exception("将文件转换为字节数组时出错："+e.getMessage());
		}
		dbFileBean.setFileData(fileData);
		return dbFileBean;
	}
	
	
	/**
	 * 转换数据库对象到磁盘文件
	 * 
	 * @param dbFileBean
	 */
	private DBFileBean wirteFileData(DBFileBean  dbFileBean) 
	{
		//1.创建解压缩文件的缓存文件夹路径
		String fileTempPath = WebHelp.getSysPara("FILE_UPLOAD_BASE_URL")+"/zip/";
		CommonFileUtils.createFileFolder( null, fileTempPath );						//判断创建保存文件的路径
		//2.从数据库中将解压缩文件复制到缓存文件夹路径
		File zipFile=new File(fileTempPath + dbFileBean.getFileName()+"."+dbFileBean.getFileType()+".gz");
		try 
		{
			FileCopyUtils.copy(dbFileBean.getFileData(), zipFile);
			dbFileBean.setDbFile(zipFile);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		return dbFileBean;
	}
	
}
