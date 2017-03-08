/**
 * Copyright 2015 FormsSyntron ShenZhen
 *
 * All right reserved.
 *
 * Create on 2015-3-18 上午10:29:15
 *
 * By ZhengCuixian
 */
package com.forms.prms.web.user.domain;

import java.io.Serializable;

/**
 * @author ZhengCuixian
 *
 */
public class CtrlFileBean implements Serializable{
	
	private static final long serialVersionUID = 4373419618215943747L;
	
	private String fileId;//编号 7位上传人id + 时间戳
	private String fileType;//文件类型 FILE_TYPE
	private String fileTypeDesc;//文件类型描述
	private String sourceFname;//源文件原始名称 setup.exe
	private String sourceFpath;//源文件全路径 /home/admin01yyyyyyy.exe
	private String fileDesc;//文件说明 默认为“源文件原始名称”，可改。
	private String instOper;//
	private String instDate;//
	private String instTime;//
	
	public String getFileId() {
		return fileId;
	}
	public void setFileId(String fileId) {
		this.fileId = fileId;
	}
	public String getFileTypeDesc() {
		return fileTypeDesc;
	}
	public void setFileTypeDesc(String fileTypeDesc) {
		this.fileTypeDesc = fileTypeDesc;
	}
	public String getFileType() {
		return fileType;
	}
	public void setFileType(String fileType) {
		this.fileType = fileType;
	}
	public String getSourceFname() {
		return sourceFname;
	}
	public void setSourceFname(String sourceFname) {
		this.sourceFname = sourceFname;
	}
	public String getSourceFpath() {
		return sourceFpath;
	}
	public void setSourceFpath(String sourceFpath) {
		this.sourceFpath = sourceFpath;
	}
	public String getFileDesc() {
		return fileDesc;
	}
	public void setFileDesc(String fileDesc) {
		this.fileDesc = fileDesc;
	}
	public String getInstOper() {
		return instOper;
	}
	public void setInstOper(String instOper) {
		this.instOper = instOper;
	}
	public String getInstDate() {
		return instDate;
	}
	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}
	public String getInstTime() {
		return instTime;
	}
	public void setInstTime(String instTime) {
		this.instTime = instTime;
	}
}
