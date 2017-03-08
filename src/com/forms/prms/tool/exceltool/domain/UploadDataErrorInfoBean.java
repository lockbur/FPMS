package com.forms.prms.tool.exceltool.domain;

/**
 * Title:UploadDataErrorInfoBean
 * Description:对应表UPLOAD_DATA_ERROR_INFO
 */
public class UploadDataErrorInfoBean {
	//导入批次号，对应导入的TaskId
	private String batchNo;
	//导入的类型，对应导入Excel的具体Sheet
	private String dataType;
	//Sheet的行号
	private String rowNo;
	//导入Excel类型(Excel模板)
	private String uploadType;
	//导入错误描述
	private String errDesc;
	private String dataNo;//数据行号
	
	//XML配置的校验Section区类型
	private String sectionType;
	
	public String getSectionType() {
		return sectionType;
	}
	public void setSectionType(String sectionType) {
		this.sectionType = sectionType;
	}
	public String getDataNo() {
		return dataNo;
	}
	public void setDataNo(String dataNo) {
		this.dataNo = dataNo;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public String getErrDesc() {
		return errDesc;
	}
	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}
	
}
