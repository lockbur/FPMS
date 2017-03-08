package com.forms.prms.tool.fileUtils.domain;

import java.io.File;
import java.io.Serializable;


public class DBFileBean implements  Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String fileId;
	private String fileName;
	private String fileType;
	private File dbFile;
	private byte[] fileData;
	private String removeFlag;
	
	public String getRemoveFlag() {
		return removeFlag;
	}

	public void setRemoveFlag(String removeFlag) {
		this.removeFlag = removeFlag;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	public String getFileId() {
		return fileId;
	}

	public void setFileId(String fileId) {
		this.fileId = fileId;
	}

	public File getDbFile() {
		return dbFile;
	}

	public void setDbFile(File dbFile) {
		this.dbFile = dbFile;
	}

	public byte[] getFileData() {
		return fileData;
	}

	public void setFileData(byte[] fileData) {
		this.fileData = fileData;
	}

}
