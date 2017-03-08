package com.forms.prms.web.report.domain;

public class ReportItem {

	//报表ID
	private String reportID = null;
	
	//报表名称
	private String reportName = null;
	
	//报表定义文件
	private String reportFile = null;
	
	//表定义文件
	private String tableFile = null;

	//数据获取类
	private String dataClass = null;
	
	public String getDataClass() {
		return dataClass;
	}
	public void setDataClass(String dataClass) {
		this.dataClass = dataClass;
	}
	public String getReportFile() {
		return reportFile;
	}
	public void setReportFile(String reportFile) {
		this.reportFile = reportFile;
	}
	public String getReportID() {
		return reportID;
	}
	public void setReportID(String reportID) {
		this.reportID = reportID;
	}
	public String getReportName() {
		return reportName;
	}
	public void setReportName(String reportName) {
		this.reportName = reportName;
	}
	public String getTableFile() {
		return tableFile;
	}
	public void setTableFile(String tableFile) {
		this.tableFile = tableFile;
	}
}
