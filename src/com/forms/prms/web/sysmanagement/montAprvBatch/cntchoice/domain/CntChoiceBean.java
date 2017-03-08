package com.forms.prms.web.sysmanagement.montAprvBatch.cntchoice.domain;

public class CntChoiceBean {
	private String batchNo;// 批次号
	private String org21Code;// 一级行/二级行/机构 (省行就是一级行 分行就是二级行或机构)
	private String proType;// 项目类型 01：监控指标 02：审批链
	private String subType;
	private String subTypeName;
	private String status;// 状态

	private String path;// 导入的文件原始路径
	private String instUser;// 操作人
	private String instDate;// 日期
	private String instTime;// 时间
	private String sourceFileName;// 文件名
	private String memo;// 文件名
	private String montName;
	private String montCode;
	private String dataYear;
	private String orgType;
	private String instMemo;
	private String instType;

	private String cntStatus;// 合同状态

	private String cntStatusName;// 合同状态名称

	private String montCodeOld;// 旧监控指标代码

	private String montNameOld;// 旧监控指标名称

	private String montCodeNew;// 新监控指标代码

	private String montNameNew;// 新监控指标名称

	private String montSelect;// 监控指标集合

	private String cntNum;// 合同号

	private String[] cntNums;// 合同数组


	private String[] subIds;//

	private String[] montSelects;// 监控指标数组

	private String matrCode;// 物料编码

	private String matrName;// 物料名称

	private String tabsIndex;
	private String statusName;

	private String isCheck;
	private String auditMemo;
	private String montSelectNew;
	
	
	public String getMontSelectNew() {
		return montSelectNew;
	}

	public void setMontSelectNew(String montSelectNew) {
		this.montSelectNew = montSelectNew;
	}

	public String getSubTypeName() {
		return subTypeName;
	}

	public void setSubTypeName(String subTypeName) {
		this.subTypeName = subTypeName;
	}

	public String getAuditMemo() {
		return auditMemo;
	}

	public void setAuditMemo(String auditMemo) {
		this.auditMemo = auditMemo;
	}

	public String getIsCheck() {
		return isCheck;
	}

	public void setIsCheck(String isCheck) {
		this.isCheck = isCheck;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	private String[] cntNumSubIdMont;// 合同号子序号监控指标

	public String[] getCntNumSubIdMont() {
		return cntNumSubIdMont;
	}

	public void setCntNumSubIdMont(String[] cntNumSubIdMont) {
		this.cntNumSubIdMont = cntNumSubIdMont;
	}

	public String getMontCodeNew() {
		return montCodeNew;
	}

	public void setMontCodeNew(String montCodeNew) {
		this.montCodeNew = montCodeNew;
	}

	public String getMontNameNew() {
		return montNameNew;
	}

	public void setMontNameNew(String montNameNew) {
		this.montNameNew = montNameNew;
	}

	public String[] getCntNums() {
		return cntNums;
	}

	public void setCntNums(String[] cntNums) {
		this.cntNums = cntNums;
	}

	public String[] getSubIds() {
		return subIds;
	}

	public void setSubIds(String[] subIds) {
		this.subIds = subIds;
	}

	public String[] getMontSelects() {
		return montSelects;
	}

	public void setMontSelects(String[] montSelects) {
		this.montSelects = montSelects;
	}

	public String getTabsIndex() {
		return tabsIndex;
	}

	public void setTabsIndex(String tabsIndex) {
		this.tabsIndex = tabsIndex;
	}

	public String getMontCodeOld() {
		return montCodeOld;
	}

	public void setMontCodeOld(String montCodeOld) {
		this.montCodeOld = montCodeOld;
	}

	public String getMontNameOld() {
		return montNameOld;
	}

	public void setMontNameOld(String montNameOld) {
		this.montNameOld = montNameOld;
	}

	public String getMontSelect() {
		return montSelect;
	}

	public void setMontSelect(String montSelect) {
		this.montSelect = montSelect;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
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

	public String getCntStatusName() {
		return cntStatusName;
	}

	public void setCntStatusName(String cntStatusName) {
		this.cntStatusName = cntStatusName;
	}

	public String getCntStatus() {
		return cntStatus;
	}

	public void setCntStatus(String cntStatus) {
		this.cntStatus = cntStatus;
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

	public String getSourceFileName() {
		return sourceFileName;
	}

	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getInstMemo() {
		return instMemo;
	}

	public void setInstMemo(String instMemo) {
		this.instMemo = instMemo;
	}

	public String getInstType() {
		return instType;
	}

	public void setInstType(String instType) {
		this.instType = instType;
	}

}
