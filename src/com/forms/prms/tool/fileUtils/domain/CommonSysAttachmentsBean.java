package com.forms.prms.tool.fileUtils.domain;

import java.io.Serializable;
/**
 * 公共的文件附件Bean
 * @author wangzf
 * @data 2014-6-3
 */
public class CommonSysAttachmentsBean implements Serializable {
	private static final long serialVersionUID = -8837741563068722327L;
	
	private String attachmentId;  //附件ID
	
	private String attachmentType;  //附件类型
	
	private String origalFilename;  //源文件名
	
	private String attachmentDesc;  //附件描述
	
	private Integer attachmentSize;  //附件大小
	
	private String uploadUserId;  //上传用户
	
	private String uploadDate;  //上传日期
	
	private String uploadTime;  //上传时间
	
	private String busiId;  //业务表ID
	
	private String isDelete;  //是否删除

	private String filePath;  //上传文件路径
	
	private String isCheckedSwf;  //是否已转换为swf格式文件
	
	public String getIsCheckedSwf() {
		return isCheckedSwf;
	}

	public void setIsCheckedSwf(String isCheckedSwf) {
		this.isCheckedSwf = isCheckedSwf;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getAttachmentId() {
		return attachmentId;
	}

	public void setAttachmentId(String attachmentId) {
		this.attachmentId = attachmentId;
	}

	public String getAttachmentType() {
		return attachmentType;
	}

	public void setAttachmentType(String attachmentType) {
		this.attachmentType = attachmentType;
	}

	public String getOrigalFilename() {
		return origalFilename;
	}

	public void setOrigalFilename(String origalFilename) {
		this.origalFilename = origalFilename;
	}

	public String getAttachmentDesc() {
		return attachmentDesc;
	}

	public void setAttachmentDesc(String attachmentDesc) {
		this.attachmentDesc = attachmentDesc;
	}

	public Integer getAttachmentSize() {
		return attachmentSize;
	}

	public void setAttachmentSize(Integer attachmentSize) {
		this.attachmentSize = attachmentSize;
	}

	public String getUploadUserId() {
		return uploadUserId;
	}

	public void setUploadUserId(String uploadUserId) {
		this.uploadUserId = uploadUserId;
	}

	public String getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadTime() {
		return uploadTime;
	}

	public void setUploadTime(String uploadTime) {
		this.uploadTime = uploadTime;
	}

	public String getBusiId() {
		return busiId;
	}

	public void setBusiId(String busiId) {
		this.busiId = busiId;
	}

	public String getIsDelete() {
		return isDelete;
	}

	public void setIsDelete(String isDelete) {
		this.isDelete = isDelete;
	}
}
