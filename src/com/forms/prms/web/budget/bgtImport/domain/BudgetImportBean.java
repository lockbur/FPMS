package com.forms.prms.web.budget.bgtImport.domain;

import java.io.Serializable;
public class BudgetImportBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2373893728348708294L;
	//这个是汇总信息
	private String batchNo;
	private String org21Code;
	private String bgtYear;
	private String bgtType; //00-临时预算（初次下达）；  01-临时预算（追加）  10-正式预算（初次下达）；  11-正式预算（追加）；
	private String subType;//11-专项包 12-省行统购资产 21-非省行统购类资产 22-非专项包费用类）
	private String status;
	private String instOper;
	private String instDate;
	private String filePath;
	private String sourceFilename;
	private String memo;
	private String orgType;//1-省行 2-分行
//	private String operType;//0-临时预算 1-正式预算 
//	private String operSubType;//0-初次下达 1-追加
	private String bgtTypeName;
	private String subTypeName;
	private String statusName;
	private String configId;//导入要用
	private String fileInfoName;//对应错误信息表
	//下面是明细信息
	private String seq;
	private String bgtOrgcode;
	private String bgtOrgname;
	private String bgtMontcode;
	private String bgtMontname;
	private String bgtMatrcode;
	private String bgtMatrname;
	private String bgtSum;
	private String excelNo;
	private String bgtOverdraw;
	private String errDesc;//错误信息描述
	private String rowNo;
	private String currentUri;
	private String bgtFrozenUsed;//冻结和占用的和
	
	
	public String getBgtFrozenUsed() {
		return bgtFrozenUsed;
	}
	public void setBgtFrozenUsed(String bgtFrozenUsed) {
		this.bgtFrozenUsed = bgtFrozenUsed;
	}
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	public String getBgtOverdraw() {
		return bgtOverdraw;
	}
	public void setBgtOverdraw(String bgtOverdraw) {
		this.bgtOverdraw = bgtOverdraw;
	}
	public String getCurrentUri() {
		return currentUri;
	}
	public void setCurrentUri(String currentUri) {
		this.currentUri = currentUri;
	}
	public String getErrDesc() {
		return errDesc;
	}
	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}
	public String getFileInfoName() {
		return fileInfoName;
	}
	public void setFileInfoName(String fileInfoName) {
		this.fileInfoName = fileInfoName;
	}
	public String getSeq() {
		return seq;
	}
	public void setSeq(String seq) {
		this.seq = seq;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getStatusName() {
		return statusName;
	}
	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}
	public String getBgtTypeName() {
		return bgtTypeName;
	}
	public void setBgtTypeName(String bgtTypeName) {
		this.bgtTypeName = bgtTypeName;
	}
	public String getSubTypeName() {
		return subTypeName;
	}
	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getOrg21Code() {
		return org21Code;
	}
	public void setOrg21Code(String org21Code) {
		this.org21Code = org21Code;
	}
	public String getBgtYear() {
		return bgtYear;
	}
	public void setBgtYear(String bgtYear) {
		this.bgtYear = bgtYear;
	}
	public String getBgtType() {
		return bgtType;
	}
	public void setBgtType(String bgtType) {
		this.bgtType = bgtType;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	
	public String getSourceFilename() {
		return sourceFilename;
	}
	public void setSourceFilename(String sourceFilename) {
		this.sourceFilename = sourceFilename;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
//	public String getOperType() {
//		return operType;
//	}
//	public void setOperType(String operType) {
//		this.operType = operType;
//	}
//	public String getOperSubType() {
//		return operSubType;
//	}
//	public void setOperSubType(String operSubType) {
//		this.operSubType = operSubType;
//	}
	public String getBgtOrgcode() {
		return bgtOrgcode;
	}
	public void setBgtOrgcode(String bgtOrgcode) {
		this.bgtOrgcode = bgtOrgcode;
	}
	public String getBgtSum() {
		return bgtSum;
	}
	public void setBgtSum(String bgtSum) {
		this.bgtSum = bgtSum;
	}
	public String getExcelNo() {
		return excelNo;
	}
	public void setExcelNo(String excelNo) {
		this.excelNo = excelNo;
	}
	public String getBgtOrgname() {
		return bgtOrgname;
	}
	public void setBgtOrgname(String bgtOrgname) {
		this.bgtOrgname = bgtOrgname;
	}
	public String getBgtMontcode() {
		return bgtMontcode;
	}
	public void setBgtMontcode(String bgtMontcode) {
		this.bgtMontcode = bgtMontcode;
	}
	public String getBgtMontname() {
		return bgtMontname;
	}
	public void setBgtMontname(String bgtMontname) {
		this.bgtMontname = bgtMontname;
	}
	public String getBgtMatrcode() {
		return bgtMatrcode;
	}
	public void setBgtMatrcode(String bgtMatrcode) {
		this.bgtMatrcode = bgtMatrcode;
	}
	public String getBgtMatrname() {
		return bgtMatrname;
	}
	public void setBgtMatrname(String bgtMatrname) {
		this.bgtMatrname = bgtMatrname;
	}
	
	
	

}
