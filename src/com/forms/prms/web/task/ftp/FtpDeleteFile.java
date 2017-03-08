package com.forms.prms.web.task.ftp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.commons.net.ftp.FTPClient;

import com.forms.platform.core.logger.CommonLogger;

public class FtpDeleteFile {

	/**
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
			String password, String folder, List<String> fileNames) throws Exception {

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
			for(String fileName : fileNames){
				ftp.deleteFile(fileName);
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
	public static void main(String[] args) throws Exception {
		FtpDeleteFile.deleteFile("127.0.0.1", 21, "deltftp", "deltftp", "upload", 
			null);
//		FtpDownLoadFile.getFile("127.0.0.1", 21, "xiesh", "xiesh", "测试数据", "C:\\Documents and Settings\\db2admin\\桌面\\test", "0151290D.w01", true);
	}
}
