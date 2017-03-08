package com.forms.prms.web.task.ftp;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.forms.platform.core.logger.CommonLogger;

public class FtpDownLoadFile {

	/**
	 * @param server ip
	 * @param port	端口
	 * @param username	用户名
	 * @param password 密码
	 * @param folder FTP服务器上的目录
	 * @param destinationFolder	下载到本地的目录
	 * @param fileName	文件名
	 * @param isDelete	下载完后是否需要删除FTP上的文件
	 * @return
	 * @throws Exception
	 */
	public static boolean getFile(String server,int port, String username,
			String password, String folder, String destinationFolder,
			String fileName,boolean isDelete) throws Exception {

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
			CommonLogger
					.info("consss3----------- " + destinationFolder);
			// 设置下载目录
			if (!ftp.changeWorkingDirectory(folder)) {
				throw new Exception("FTP服务器" + server + "上不存在文件夹" + folder);
			}
			CommonLogger
					.info("consss4----------- " + fileName);
			// 创建文件夹
			File fileFold = new File(destinationFolder);
			if (!fileFold.exists()) {
				fileFold.mkdirs();
			}
			File file = new File(destinationFolder, fileName);
			fos = new FileOutputStream(file);
			CommonLogger
					.info("consss5----------- " + username + ":" + password);
			// 下载文件
			if (!ftp.retrieveFile(fileName, fos)) {
				fos.close();
				file.delete();
				return false;
			}
			//删除文件
			if(isDelete)
				ftp.deleteFile(new File(folder,fileName).getPath());
			ftp.logout();

			rtnFlag = true;
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
	
	public static boolean checkFile(String server,int port, String username,
			String password, String folder, String fileName) throws Exception {

		boolean rtnFlag = false;
		FTPClient ftp = new FTPClient();
		FileOutputStream fos = null;

		try {
			//文件名中带中文时需要执行此方法
			ftp.setControlEncoding("UTF-8");
			CommonLogger.info("scan----------- " + server);
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
			CommonLogger
					.info("consss4----------- " + fileName);
			
			FTPFile[] files = ftp.listFiles(fileName);
			
			if(files.length!=1){
				return false;
			}
			ftp.logout();

			rtnFlag = true;
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
}