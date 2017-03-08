package com.forms.prms.tool.fms.parse.domain;

import java.io.Serializable;

import com.forms.prms.tool.WebHelp;
import com.forms.prms.web.init.SystemParamManage;
import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("Ftp")
public class FTPBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@XStreamAlias("UploadFolder")
	private String uploadFolder;

	@XStreamAlias("DownloadFolder")
	private String downloadFolder;

	public String getUploadHostAddr() {
		return SystemParamManage.getInstance().getParaValue("FTP_UPLOAD_HOSTADD");
	}

	public int getUploadPort() {
		return Integer.parseInt(SystemParamManage.getInstance().getParaValue("FTP_UPLOAD_PORT"));
	}

	public String getUploadUserName() {
		return SystemParamManage.getInstance().getParaValue("FTP_UPLOAD_USER");
	}

	public String getUploadPassword() {
		return SystemParamManage.getInstance().getParaValue("FTP_UPLOAD_PWD");
	}

	public String getUploadFolder() {
		return SystemParamManage.getInstance().getParaValue("FTP_UPLOAD_FOLDER");
	}

	public void setUploadFolder(String uploadFolder) {
		this.uploadFolder = uploadFolder;
	}

	public String getDownloadFolder() {
		String folder = WebHelp.getSysPara("FTP_DOWNLOAD_FOLDER");
		return folder;
	}

	public void setDownloadFolder(String downloadFolder) {
		this.downloadFolder = downloadFolder;
	}

	/*** ftp下载 ***/
	public String getDownloadHostAddr() {
		return SystemParamManage.getInstance().getParaValue("FTP_DOWNLOAD_HOSTADD");
	}

	public int getDownloadPort() {

		return Integer.parseInt(SystemParamManage.getInstance().getParaValue("FTP_DOWNLOAD_PORT"));
	}

	public String getDownloadUserName() {
		return SystemParamManage.getInstance().getParaValue("FTP_DOWNLOAD_USER");
	}

	public String getDownloadPassword() {
		return SystemParamManage.getInstance().getParaValue("FTP_DOWNLOAD_PWD");
	}
	
	//是否本地下载
	public String getDownloadFileLocal(){
		return SystemParamManage.getInstance().getParaValue("DOWNLOAD_FILE_LOCAL");
	}
	
	
	public String getDownloadFolder(String tradeType) throws Exception {
		String folder = WebHelp.getSysPara("FTP_DOWNLOAD_FOLDER_"+tradeType);
		if("".equals(folder) || null == folder)
		{
			throw new Exception("没找到参数FTP_DOWNLOAD_FOLDER_"+tradeType+"对应的值");
		}
		return folder;
	}
	
	public String getUploadFolder(String tradeType) throws Exception {
		String folder = WebHelp.getSysPara("FTP_UPLOAD_FOLDER_"+tradeType);
		if("".equals(folder) || null == folder)
		{
			throw new Exception("没找到参数FTP_UPLOAD_FOLDER_"+tradeType+"对应的值");
		}
		return folder;
	}

}
