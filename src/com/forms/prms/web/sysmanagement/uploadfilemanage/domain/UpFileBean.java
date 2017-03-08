package com.forms.prms.web.sysmanagement.uploadfilemanage.domain;

import java.io.Serializable;

/**
 * Title: UpFileBean Description: 文件上传管理JavaBean，对应表：TB_UPLOAD_FILE_MNGT
 * Copyright: formssi
 * 
 * @author: HQQ
 * @project: ERP
 * @date: 2015-03-20
 * @version: 1.0
 */
public class UpFileBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String fileId; // 文件编号
	private String fileType; // 文件类型
	private String fileTypeDesc; // 文件类型描述
	private String sourceFName; // 源文件原始名称
	private String sourceFPath; // 源文件全路径
	private String deleteFlag; // 是否允许删除标识
	private String fileDesc; // 文件描述
	private String instOper; // 操作人员
	private String instDate; // 操作日期
	private String instTime; // 操作时间

	private String newFName; // 新的上传文件名(用于修改上传文件信息时)
	private String newFPath; // 新的上传文件路径(用于修改上传文件信息时)

	private String startDate;// 下载文件开始日期

	private String endDate;// 下载文件结束日期
	
	private String gzName;
	
	

	public String getGzName() {
		return gzName;
	}

	public void setGzName(String gzName) {
		this.gzName = gzName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileTypeDesc() {
		return fileTypeDesc;
	}

	public void setFileTypeDesc(String fileTypeDesc) {
		this.fileTypeDesc = fileTypeDesc;
	}

	public String getSourceFName() {
		return sourceFName;
	}

	public void setSourceFName(String sourceFName) {
		this.sourceFName = sourceFName;
	}

	public String getSourceFPath() {
		return sourceFPath;
	}

	public void setSourceFPath(String sourceFPath) {
		this.sourceFPath = sourceFPath;
	}

	public String getDeleteFlag() {
		return deleteFlag;
	}

	public void setDeleteFlag(String deleteFlag) {
		this.deleteFlag = deleteFlag;
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

	public String getNewFName() {
		return newFName;
	}

	public void setNewFName(String newFName) {
		this.newFName = newFName;
	}

	public String getNewFPath() {
		return newFPath;
	}

	public void setNewFPath(String newFPath) {
		this.newFPath = newFPath;
	}

}
