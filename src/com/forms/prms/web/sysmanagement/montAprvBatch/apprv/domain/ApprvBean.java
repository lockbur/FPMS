package com.forms.prms.web.sysmanagement.montAprvBatch.apprv.domain;

import java.math.BigDecimal;

public class ApprvBean {

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
	private String statusName;
	private String excelStatus;// E0导入中 E1导入失败 E2校验成功 E3校验失败
								// E4待复核（校验成功后，提交。如果没有合同勾选的数据，自己变为E5，合同勾选状态变为C4）
								// E5excel复核通过（仅指excel数据复核通过，但合同勾选可能尚未完成,复核通过后将CNT_DATA_FLAG状态改为C1）
								// E6复核退回
	private String cntStatus;
	// C1待处理
	// C2待复核（待所有明细数据都已勾选完毕后，更改为'待复核'状态）
	// C3复核退回（所有明细都已审核完毕，审核结果中存在退回的数据）
	// C4复核通过（所有明细都已审批通过）
	private String auditFlag;// 01-同意 02-退回


	/** TBL_MONT_SPLIT **/
	private String cntNum;//
	private BigDecimal subId;//
	private String matrCode;//
	private String matrName;//
	private String montCodeOld;// 旧的监控指标
	private String montNameOld;//
	private String montCodeNew;// 合同勾选后有值
	private String montNameNew;//
	private String montSelect;// 监控指标分解时对应的多个监控指标组合，用 |
								// 隔开，例如2015A00021100100001-监控指标1 |
								// 2015A00021100100002-监控指标2"
	private String instOper;//

	private String flag;// 标志是当页的提交还是所有数据的提交

	private String[] cntNumSubId;// 复选框选中的值

	private String isDetail;//
	private String auditMemo;
	
	
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

	public String getIsDetail() {
		return isDetail;
	}

	public void setIsDetail(String isDetail) {
		this.isDetail = isDetail;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public BigDecimal getSubId() {
		return subId;
	}

	public void setSubId(BigDecimal subId) {
		this.subId = subId;
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

	public String getMontSelect() {
		return montSelect;
	}

	public void setMontSelect(String montSelect) {
		this.montSelect = montSelect;
	}

	public String getInstOper() {
		return instOper;
	}

	public void setInstOper(String instOper) {
		this.instOper = instOper;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public String[] getCntNumSubId() {
		return cntNumSubId;
	}

	public void setCntNumSubId(String[] cntNumSubId) {
		this.cntNumSubId = cntNumSubId;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String getOrgType() {
		return orgType;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
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

	public void setOrgType(String orgType) {
		this.orgType = orgType;
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

	public String getDataYear() {
		return dataYear;
	}

	public void setDataYear(String dataYear) {
		this.dataYear = dataYear;
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

	public String getSubType() {
		return subType;
	}

	public void setSubType(String subType) {
		this.subType = subType;
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
