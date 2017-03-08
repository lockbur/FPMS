package com.forms.prms.tool.fms.parse.domain;

import java.io.Serializable;
import java.util.List;

import com.thoughtworks.xstream.annotations.XStreamAlias;

@XStreamAlias("File")
public class FileBean implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@XStreamAlias("FileName")
	private String fileName;
	
	@XStreamAlias("CharSet")
	private String charSet;
	
	@XStreamAlias("ProcedureName")
	private String procedureName;
	
	@XStreamAlias("FileSql")
	private String fileSql;
	
	@XStreamAlias("DealBean")
	private String dealBean;
	
	@XStreamAlias("Sep")
	private String sep;
	
	@XStreamAlias("IsZip")
	private boolean isZip;
	
	@XStreamAlias("IsUnZip")
	private boolean isUnZip;
	
	@XStreamAlias("TranTmpSql")
	private String tranTmpSql;
	
	@XStreamAlias("CheckUpdateSql")
	private List<SqlBean> checkUpdateSql;
	
	@XStreamAlias("TruncateTmpSql")
	private String truncateTmpSql;
	
	public String getTruncateTmpSql() {
		return truncateTmpSql;
	}

	public void setTruncateTmpSql(String truncateTmpSql) {
		this.truncateTmpSql = truncateTmpSql;
	}

	public List<SqlBean> getCheckUpdateSql() {
		return checkUpdateSql;
	}

	public void setCheckUpdateSql(List<SqlBean> checkUpdateSql) {
		this.checkUpdateSql = checkUpdateSql;
	}

	public String getTranTmpSql() {
		return tranTmpSql;
	}

	public void setTranTmpSql(String tranTmpSql) {
		this.tranTmpSql = tranTmpSql;
	}

	public String getFileSql() {
		return fileSql;
	}

	public void setFileSql(String fileSql) {
		this.fileSql = fileSql;
	}

	public String getSep() {
		return sep;
	}

	public void setSep(String sep) {
		this.sep = sep;
	}

	public boolean isZip() {
		return isZip;
	}

	public void setZip(boolean isZip) {
		this.isZip = isZip;
	}

	public boolean isUnZip() {
		return isUnZip;
	}

	public void setUnZip(boolean isUnZip) {
		this.isUnZip = isUnZip;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public String getCharSet() {
		return charSet;
	}

	public void setCharSet(String charSet) {
		this.charSet = charSet;
	}

	public String getProcedureName() {
		return procedureName;
	}

	public void setProcedureName(String procedureName) {
		this.procedureName = procedureName;
	}

	public String getDealBean() {
		return dealBean;
	}

	public void setDealBean(String dealBean) {
		this.dealBean = dealBean;
	}

//	public String getLocalUpFolder() {
//		String folder = WebHelp.getSysPara("FMS_UPLOAD_LOCAL_FOLDER");
//		return folder;
//	}

//	public void setLocalUpFolder(String localUpFolder) {
//		this.localUpFolder = localUpFolder;
//	}
//
//	public String getLocalDownFolder() {
//		String folder = WebHelp.getSysPara("FMS_DOWNLOAD_LOCAL_FOLDER");
//		return folder;
//	}
//
//	public void setLocalDownFolder(String localDownFolder) {
//		this.localDownFolder = localDownFolder;
//	}
	
}
