package com.forms.prms.web.sysmanagement.montAprvBatch.dataImport.domain;

import java.io.Serializable;

public class ImportBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 4695586696559673234L;
	private String orgType;//01 一级行  02  分行
	private String org21Code;
	private String proType;
	private String subType;
	private String subTypeName;
	private String status;
	private String dataYear;
	private String batchNo;
	private String path;//导入文件路径
	private String sourceFilename;
	private String instUser;
	private String instDate;
	private String instTime;
	private String configId;
	private String instType;
	private String instMemo;
	private String rowNo;//行号
	private String errDesc;//错误数据
	private String dataType;//sheet类型
	private String uploadType;//EXCEL模板类型
	private String memo ;
	private String isProvinceBuy;
	private String statusName;
	private String excelStatus;//E0导入中 E1导入失败 E2校验成功 E3校验失败 
	                           //E4待复核（校验成功后，提交。如果没有合同勾选的数据，自己变为E5，合同勾选状态变为C4）
	                           //E5excel复核通过（仅指excel数据复核通过，但合同勾选可能尚未完成,复核通过后将CNT_DATA_FLAG状态改为C1）
	                           //E6复核退回
	private String cntStatus;
							//	C1待处理
							//	C2待复核（待所有明细数据都已勾选完毕后，更改为'待复核'状态）
							//	C3复核退回（所有明细都已审核完毕，审核结果中存在退回的数据）
							//	C4复核通过（所有明细都已审批通过）

	
	private String lastMontCode;
	private String lastMontName;
	private String montCode;
	private String montName;
	private String matrCode;
	private String matrName;
	private String feeCode;
	private String feeName;
	private String orgCode;
	private String orgName;
	
	private String userId;
	private String isAdmin;
	private String roleName;

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getIsAdmin() {
		return isAdmin;
	}

	public void setIsAdmin(String isAdmin) {
		this.isAdmin = isAdmin;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public String getLastMontCode() {
		return lastMontCode;
	}

	public void setLastMontCode(String lastMontCode) {
		this.lastMontCode = lastMontCode;
	}

	public String getLastMontName() {
		return lastMontName;
	}

	public void setLastMontName(String lastMontName) {
		this.lastMontName = lastMontName;
	}

	public String getFeeCode() {
		return feeCode;
	}

	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}

	public String getFeeName() {
		return feeName;
	}

	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getMontCode() {
		return montCode;
	}

	public void setMontCode(String montCode) {
		this.montCode = montCode;
	}

	public String getMontName() {
		return montName;
	}

	public void setMontName(String montName) {
		this.montName = montName;
	}

	public String getMatrCode() {
		return matrCode;
	}

	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}

	public String getMatrName() {
		return matrName;
	}

	public void setMatrName(String matrName) {
		this.matrName = matrName;
	}

	public String getIsProvinceBuy() {
		return isProvinceBuy;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getRowNo() {
		return rowNo;
	}

	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}

	public String getErrDesc() {
		return errDesc;
	}

	public void setErrDesc(String errDesc) {
		this.errDesc = errDesc;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public String getUploadType() {
		return uploadType;
	}

	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}

	public String getExcelStatus() {
		return excelStatus;
	}

	public void setExcelStatus(String excelStatus) {
		this.excelStatus = excelStatus;
	}

	public String getCntStatus() {
		return cntStatus;
	}

	public void setCntStatus(String cntStatus) {
		this.cntStatus = cntStatus;
	}

	public void setIsProvinceBuy(String isProvinceBuy) {
		this.isProvinceBuy = isProvinceBuy;
	}

	public String getInstType() {
		return instType;
	}

	public void setInstType(String instType) {
		this.instType = instType;
	}

	public String getInstMemo() {
		return instMemo;
	}

	public void setInstMemo(String instMemo) {
		this.instMemo = instMemo;
	}


	public String getConfigId() {
		return configId;
	}

	public void setConfigId(String configId) {
		this.configId = configId;
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

	public String getInstUser() {
		return instUser;
	}

	public void setInstUser(String instUser) {
		this.instUser = instUser;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getSourceFilename() {
		return sourceFilename;
	}

	public void setSourceFilename(String sourceFilename) {
		this.sourceFilename = sourceFilename;
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

	public String getProType() {
		return proType;
	}

	public void setProType(String proType) {
		this.proType = proType;
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

	public String getDataYear() {
		return dataYear;
	}

	public void setDataYear(String dataYear) {
		this.dataYear = dataYear;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	
}
