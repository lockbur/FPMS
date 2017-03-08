package com.forms.prms.web.task.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;

import com.forms.platform.core.logger.CommonLogger;

public class FtpUpLoadFile {

	/**
	 * 通过FTP上传文件
	 * @param server
	 * @param port
	 * @param username
	 * @param password
	 * @param folder	ftp上传目录
	 * @param destinationFolder		本地上传目录
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static boolean sendFile(String server,int port, String username,
			String password, String folder, String destinationFolder,
			String fileName) throws Exception {

		boolean rtnFlag = false;
		FTPClient ftp = new FTPClient();
		FileInputStream fis = null;

		try {
			//文件名中带中文时需要执行此方法
			ftp.setControlEncoding("UTF-8");
			CommonLogger.info("start----------- " + server);
			// 连接FTP服务器
			ftp.connect(server,port);
			CommonLogger
					.info("consss1----------- " + username + ":" + password);
			// 登陆FTP服务器
			if (!ftp.login(username, password)) {
				throw new Exception("登陆FTP服务器" + server + "失败");
			}
			CommonLogger.info("consss2----------- from: " + destinationFolder+"  to:  "+folder
			);
			ftp.setFileType(FTPClient.ASCII_FILE_TYPE);
			ftp.enterLocalPassiveMode();

			File srcFile = new File(destinationFolder, fileName);
			fis = new FileInputStream(srcFile);
			CommonLogger
					.info("consss3----------- fileName:" + fileName);
			 
			   // 设置上传目录
			
            if(folder != null && !"".equals(folder.trim())
            		&&!"/".equals(folder) && !"\\".equals(folder)) {
            	//如果不存在目录，则创建目录
            	if(!ftp.changeWorkingDirectory(folder))
                	ftp.makeDirectory(folder);
            	ftp.changeWorkingDirectory(folder);
            }
            
			CommonLogger
					.info("consss4----------- " + username + ":" + password);
			// 上传文件
			if (!ftp.storeFile(fileName, fis)) {
				return false;
			}
			ftp.logout();

			rtnFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + "与FTP服务器通讯出错！");
		} finally {
			if (fis != null) {
				fis.close();
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
	
	/**
	 * 修改FTP上文件的文件名
	 * @param server
	 * @param port
	 * @param username
	 * @param password
	 * @param folder FTP目录
	 * @param from 原文件名
	 * @param to 新文件名
	 * @return
	 * @throws Exception
	 */
	public static boolean renameFile(String server,int port, String username,
			String password, String folder, String from,
			String to) throws Exception {

		boolean rtnFlag = false;
		FTPClient ftp = new FTPClient();
		FileInputStream fis = null;

		try {
			//文件名中带中文时需要执行此方法
			ftp.setControlEncoding("UTF-8");
			CommonLogger.info("start----------- " + server);
			// 连接FTP服务器
			ftp.connect(server,port);
			CommonLogger
					.info("consss1----------- " + username + ":" + password);
			// 登陆FTP服务器
			if (!ftp.login(username, password)) {
				throw new Exception("登陆FTP服务器" + server + "失败");
			}
			ftp.setFileType(FTPClient.ASCII_FILE_TYPE);
			ftp.enterLocalPassiveMode();
			  // 设置工作目录
            if(folder != null && !"".equals(folder.trim())) {
            	if(!ftp.changeWorkingDirectory(folder))
            		throw new Exception("FTP服务器上不存在" + folder + "目录");
            }
            
			CommonLogger
					.info("consss2----------- folder:" + folder);
			// 修改文件名
			if (!ftp.rename(from, to)) {
				return false;
			}
			CommonLogger
			.info("consss3----------- rename file from 【" + from + "】 to 【" + to + "】");
			ftp.logout();

			rtnFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + "与FTP服务器通讯出错！");
		} finally {
			if (fis != null) {
				fis.close();
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
	
	/**
	 * 连接FTP
	 * @param server
	 * @param port
	 * @param username
	 * @param password
	 * @return
	 * @throws Exception
	 */
	public static FTPClient connectFtp(String server,int port, String username,
			String password) throws Exception {

		FTPClient ftp = new FTPClient();

		try {
			//文件名中带中文时需要执行此方法
			ftp.setControlEncoding("UTF-8");
			CommonLogger.info("start----------- " + server);
			// 连接FTP服务器
			ftp.connect(server,port);
			CommonLogger
					.info("consss1----------- " + username + ":" + password);
			// 登陆FTP服务器
			if (!ftp.login(username, password)) {
				throw new Exception("登陆FTP服务器" + server + "失败");
			}
			
			ftp.setFileType(FTPClient.ASCII_FILE_TYPE);
			ftp.enterLocalPassiveMode();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + "与FTP服务器通讯出错！");
		} 
		return ftp;
	}
	
	/**
	 * 通过FTP上传文件
	 * @param username
	 * @param password
	 * @param folder	ftp上传目录
	 * @param destinationFolder		本地上传目录
	 * @param fileName
	 * @return
	 * @throws Exception
	 */
	public static boolean uploadFile(FTPClient ftp, String folder, String destinationFolder,
			String fileName) throws Exception {

		boolean rtnFlag = false;
		FileInputStream fis = null;

		try {
			CommonLogger.info("consss2----------- from: " + destinationFolder+"  to:  "+folder);
			File srcFile = new File(destinationFolder, fileName);
			fis = new FileInputStream(srcFile);
			CommonLogger.info("consss3----------- fileName:" + fileName);
			 
			// 设置上传目录
            if(folder != null && !"".equals(folder.trim())
            		&&!"/".equals(folder) && !"\\".equals(folder)) {
            	//如果不存在目录，则创建目录
            	if(!ftp.changeWorkingDirectory(folder))
                	ftp.makeDirectory(folder);
            	ftp.changeWorkingDirectory(folder);
            }
            
			// 上传文件
			if (!ftp.storeFile(fileName, fis)) {
				return false;
			}
			
			rtnFlag = true;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + "与FTP服务器通讯出错！");
		} finally {
			if (fis != null) {
				fis.close();
			}
		}
		return rtnFlag;
	}
	
	/**
	 * 关闭FTP
	 * @param ftp
	 * @return
	 * @throws Exception
	 */
	public static void close(FTPClient ftp) throws Exception {
		try {
			ftp.logout();

		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e.getMessage() + "关闭FTP连接发生异常！");
		} finally {
			if (ftp.isConnected()) {
				try {
					ftp.disconnect();
				} catch (IOException f) {
					throw new RuntimeException("关闭FTP连接发生异常！");
				}
			}
		}
	}
}