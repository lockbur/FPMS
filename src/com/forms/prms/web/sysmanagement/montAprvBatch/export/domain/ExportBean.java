package com.forms.prms.web.sysmanagement.montAprvBatch.export.domain;

public class ExportBean {
	
	private String batchNo;//批次号
	private String org21Code;//一级行/二级行/机构  (省行就是一级行  分行就是二级行或机构)
	private String proType;//项目类型  01：监控指标  02：审批链
	private String subType;//子类型
	private String status;//状态
	private String path;//导入的文件原始路径
	private String instUser;//操作人
	private String instDate;//日期
	private String instTime;//时间
	private String excelNo;//行数
	private String dataYear;//年份
	private String sourceFilename;//源文件名
	
	private String rowNo;//行号
	private String errDesc;//错误数据
	private String dataType;//sheet类型
	private String uploadType;//EXCEL模板类型
	private String isValid;//1-有效 0-无效
	private String isValidName;
	private String cglCode;
	private String cglCodeName;
	
	private String montType;
	private String montName;
	private String montCode;
	private String matrCode;
	private String matrName;
	private String montProjType;
	private String lastMontCode;
	private String lastMontName;
	private String lastProjType;
	private String memo;
	private String flag;
	private String feeCode;
	private String matrBuyDept;
	private String matrBuyDeptName;
	private String matrAuditDept;
	private String matrAuditDeptName;
	private String fincDeptS;
	private String fincDeptSName;
	private String fincDept2;
	private String fincDept2Name;
	private String fincDept1;
	private String fincDept1Name;
	private String decomposeOrg;
	private String orgType;
//	private String yearTag; // 导出的时候选择的按钮 0-是导出当年数据重新导入  1-导出当年数据编制下一年数据
	
	private String tb;//审批链对应的监控指标是在正式表还是在FUT表   0-历史表   1-正式表  2-FUT
	private String counts;
	private String orgCode;
	private String orgName;
	private String aprvType;
	
	
	public String getAprvType() {
		return aprvType;
	}
	public void setAprvType(String aprvType) {
		this.aprvType = aprvType;
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
	public String getIsValidName() {
		return isValidName;
	}
	public void setIsValidName(String isValidName) {
		this.isValidName = isValidName;
	}
	private String instMemo;//导入类型备注
	private String instType;//导入类型
	
	private String aprvChainTable;
	private String aprvChainNoSpecTable;
	private String decomposeOrgName;
	private String feeName;
	
	
	public String getAprvChainNoSpecTable() {
		return aprvChainNoSpecTable;
	}
	public void setAprvChainNoSpecTable(String aprvChainNoSpecTable) {
		this.aprvChainNoSpecTable = aprvChainNoSpecTable;
	}
	public String getCglCodeName() {
		return cglCodeName;
	}
	public void setCglCodeName(String cglCodeName) {
		this.cglCodeName = cglCodeName;
	}
	public String getCglCode() {
		return cglCode;
	}
	public void setCglCode(String cglCode) {
		this.cglCode = cglCode;
	}
	public String getIsValid() {
		return isValid;
	}
	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}
	public String getDecomposeOrgName() {
		return decomposeOrgName;
	}
	public void setDecomposeOrgName(String decomposeOrgName) {
		this.decomposeOrgName = decomposeOrgName;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	public String getAprvChainTable() {
		return aprvChainTable;
	}
	public void setAprvChainTable(String aprvChainTable) {
		this.aprvChainTable = aprvChainTable;
	}
	public String getCounts() {
		return counts;
	}
	public void setCounts(String counts) {
		this.counts = counts;
	}
	public String getLastMontCode() {
		return lastMontCode;
	}
	public void setLastMontCode(String lastMontCode) {
		this.lastMontCode = lastMontCode;
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
	public String getTb() {
		return tb;
	}
	public void setTb(String tb) {
		this.tb = tb;
	}
//	public String getYearTag() {
//		return yearTag;
//	}
//	public void setYearTag(String yearTag) {
//		this.yearTag = yearTag;
//	}
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
	public String getSourceFilename() {
		return sourceFilename;
	}
	public void setSourceFilename(String sourceFilename) {
		this.sourceFilename = sourceFilename;
	}
	public String getExcelNo() {
		return excelNo;
	}
	public void setExcelNo(String excelNo) {
		this.excelNo = excelNo;
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
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getMontType() {
		return montType;
	}
	public void setMontType(String montType) {
		this.montType = montType;
	}
	public String getMontName() {
		return montName;
	}
	public void setMontName(String montName) {
		this.montName = montName;
	}
	public String getMontCode() {
		return montCode;
	}
	public void setMontCode(String montCode) {
		this.montCode = montCode;
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
	public String getLastMontName() {
		return lastMontName;
	}
	public void setLastMontName(String lastMontName) {
		this.lastMontName = lastMontName;
	}
	
	public String getLastProjType() {
		return lastProjType;
	}
	public void setLastProjType(String lastProjType) {
		this.lastProjType = lastProjType;
	}
	 
	public String getMontProjType() {
		return montProjType;
	}
	public void setMontProjType(String montProjType) {
		this.montProjType = montProjType;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public String getFeeCode() {
		return feeCode;
	}
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}
	public String getMatrBuyDept() {
		return matrBuyDept;
	}
	public void setMatrBuyDept(String matrBuyDept) {
		this.matrBuyDept = matrBuyDept;
	}
	public String getMatrBuyDeptName() {
		return matrBuyDeptName;
	}
	public void setMatrBuyDeptName(String matrBuyDeptName) {
		this.matrBuyDeptName = matrBuyDeptName;
	}
	public String getMatrAuditDept() {
		return matrAuditDept;
	}
	public void setMatrAuditDept(String matrAuditDept) {
		this.matrAuditDept = matrAuditDept;
	}
	public String getMatrAuditDeptName() {
		return matrAuditDeptName;
	}
	public void setMatrAuditDeptName(String matrAuditDeptName) {
		this.matrAuditDeptName = matrAuditDeptName;
	}
	public String getFincDeptS() {
		return fincDeptS;
	}
	public void setFincDeptS(String fincDeptS) {
		this.fincDeptS = fincDeptS;
	}
	public String getFincDeptSName() {
		return fincDeptSName;
	}
	public void setFincDeptSName(String fincDeptSName) {
		this.fincDeptSName = fincDeptSName;
	}
	public String getFincDept2() {
		return fincDept2;
	}
	public void setFincDept2(String fincDept2) {
		this.fincDept2 = fincDept2;
	}
	public String getFincDept2Name() {
		return fincDept2Name;
	}
	public void setFincDept2Name(String fincDept2Name) {
		this.fincDept2Name = fincDept2Name;
	}
	public String getFincDept1() {
		return fincDept1;
	}
	public void setFincDept1(String fincDept1) {
		this.fincDept1 = fincDept1;
	}
	public String getFincDept1Name() {
		return fincDept1Name;
	}
	public void setFincDept1Name(String fincDept1Name) {
		this.fincDept1Name = fincDept1Name;
	}
	public String getDecomposeOrg() {
		return decomposeOrg;
	}
	public void setDecomposeOrg(String decomposeOrg) {
		this.decomposeOrg = decomposeOrg;
	}
	public String getDataYear() {
		return dataYear;
	}
	public void setDataYear(String dataYear) {
		this.dataYear = dataYear;
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
	public String getPath() {
		return path;
	}
	public void setPath(String path) {
		this.path = path;
	}
	public String getInstUser() {
		return instUser;
	}
	public void setInstUser(String instUser) {
		this.instUser = instUser;
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
