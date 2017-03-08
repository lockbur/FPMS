package com.forms.prms.tool.fms.parse;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import com.forms.platform.core.logger.CommonLogger;

public class FTPUtils {

	/**
	 * 批量下载ftp文件
	 * @param server
	 * @param username
	 * @param password
	 * @param folder
	 * @param destinationFolder
	 * @param reg
	 * 		文件名称表达式
	 * @return
	 * @throws Exception
	 */
	public static List<String> getAllFile(String server, int port, String username, String password,
			String folder, String destinationFolder, String reg) throws Exception 
	{
		List<String> list = new ArrayList<String>();
		FTPClient ftp = new FTPClient();
		try
		{
			// 连接FTP服务器
			ftp.connect(server, port);
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) 
			{
				ftp.disconnect();
				throw new Exception("连接不到FTP服务器" + server);
			}

			// 登陆FTP服务器
			if (!ftp.login(username, password)) 
			{
				ftp.logout();
				throw new Exception("登陆FTP服务器" + server + "失败");
			}

			// List the files in the directory
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			if (!ftp.changeWorkingDirectory(folder))
			{
				throw new Exception("FTP服务器" + server + "上不存在该文件夹" + folder);
			}

			// 创建文件夹
			File fileFold = new File(destinationFolder);
			if (!fileFold.exists()) 
			{
				fileFold.mkdirs();
			}
			FTPFile[] remoteFiles = ftp.listFiles(reg); 
			if (remoteFiles != null) {
				for (int i = 0; i < remoteFiles.length; i++) { 
					String name = remoteFiles[i].getName();
					//下载  
					File localFile = new File(destinationFolder, name);  
					FileOutputStream fos = new FileOutputStream(localFile);  
					String fileName = folder + name;  
					if(!ftp.retrieveFile(fileName, fos)){
						fos.close();
						localFile.delete();
						return null;
					}
					list.add(name);
					fos.close();
				}
			}
			ftp.logout();
			ftp.disconnect();
			return list;
		} catch (Exception e) {
			throw e;
		} finally {
			if (ftp.isConnected()) {
				ftp.disconnect();
			}
		}
	}
	
	/**
	 * 删除FTP上的文件
	 * @param server ip
	 * @param port	端口
	 * @param username	用户名
	 * @param password 密码
	 * @param folder FTP服务器上的目录
	 * @param fileNames	文件名
	 * @return
	 * @throws Exception
	 */
	public static boolean deleteFile(String server,int port, String username,
			String password, String folder, String fileName) throws Exception {

		boolean rtnFlag = false;
		FTPClient ftp = new FTPClient();
		FileOutputStream fos = null;

		try {
			//文件名中带中文时需要执行此方法
			ftp.setControlEncoding("UTF-8");
			CommonLogger.info("start----------- " + server);
			// 连接FTP服务器
			ftp.connect(server, port);
			CommonLogger
					.info("consss1----------- " + username + ":" + password);
			// 登陆FTP服务器
			if (!ftp.login(username, password)) {
				throw new Exception("登陆FTP服务器" + server + "失败");
			}
			CommonLogger
					.info("consss2----------- " + folder);
			ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			// 设置下载目录
			if (!ftp.changeWorkingDirectory(folder)) {
				throw new Exception("FTP服务器" + server + "上不存在文件夹" + folder);
			}
			rtnFlag = ftp.deleteFile(fileName);
			ftp.logout();
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + "与FTP服务器通讯出错！");
		} finally {
			if (fos != null) {
				fos.close();
			}
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					throw new RuntimeException("关闭FTP连接发生异常！");
				}
			}
		}
		return rtnFlag;
	}
	
	public static List<String> getOrgFile(String server, int port, String username, String password,
			String folder, String reg) throws Exception 
	{
		List<String> list = new ArrayList<String>();
		FTPClient ftp = new FTPClient();
		try
		{
			// 连接FTP服务器
			ftp.connect(server, port);
			int reply = ftp.getReplyCode();
			if (!FTPReply.isPositiveCompletion(reply)) 
			{
				ftp.disconnect();
				throw new Exception("连接不到FTP服务器" + server);
			}

			// 登陆FTP服务器
			if (!ftp.login(username, password)) 
			{
				ftp.logout();
				throw new Exception("登陆FTP服务器" + server + "失败");
			}

			// List the files in the directory
			ftp.setFileType(FTP.BINARY_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			if (!ftp.changeWorkingDirectory(folder))
			{
				throw new Exception("FTP服务器" + server + "上不存在该文件夹" + folder);
			}

			FTPFile[] remoteFiles = ftp.listFiles(reg); 
			if (remoteFiles != null) {
				for (int i = 0; i < remoteFiles.length; i++) { 
					String name = remoteFiles[i].getName();
					list.add(name);
				}
			}
			ftp.logout();
			ftp.disconnect();
			return list;
		} catch (Exception e) {
			throw e;
		} finally {
			if (ftp.isConnected()) {
				ftp.disconnect();
			}
		}
	}
}
