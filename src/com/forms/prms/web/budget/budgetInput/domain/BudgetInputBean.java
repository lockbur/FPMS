package com.forms.prms.web.budget.budgetInput.domain;

import java.io.Serializable;

public class BudgetInputBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 6869633009464205727L;
	private String tmpltId;// 模板ID 对应TB_BUDGET_TMPLT中的ID。
	private String dutyCode;// 申报的责任中心
	private String sourceFilename;// 原始文件名 可支持中文文件名
	private String serverFile;// 文件全路径 文件存储在服务器上的全路径，为避免中文乱码及文件名，其中的文件名为转义后的文件名。
	private String dataFlag;// 数据状态 数据导入状态：00-待处理 01-处理中 02-处理失败 03-处理完成(待提交)
							// 04-已提交
	private String instOper;// 创建柜员
	private String instDate;// 创建日期 yyyy-mm-dd
	private String instTime;// 创建时间 hh24:mi:ss
	private String commitDate;// 提交日期 当A0->00时，需更新本字段。
	private String commitTime;// 提交时间 当A0->00时，需更新本字段。
	private String memo;// 备注
	private String dataYear;
	private String dataType;
	private String dataTypeName;
	private String dataAttr;
	private String dataAttrName;
	private String Org21Code;
	private String org21Name;
	private String dataFlagName;
	//===============
	private String rowInfo;
//	private String matrAuditDeptCode;
	private String matrAuditDeptName;
	
	private String inAmt; //预算金额
	private String inMemo;
	private String inNouseCnt;//报废数量 资产类专用
	private String inNeedCnt;//需求数量 资产类专用
	
	private String userOrg1Code;//登录用户的一级行
	private String isSuperAdmin;//是否超级管理员
	private String matrAuditDept;//物料归口部门
	private String dutyName;//申报的费用承担部门名称
	
	
	//下面是excel要导出的数据
	private String montName;		//预算指标
	private String propertyType;	//资产大类(资产类专有)
	private String acCode;			//核算码
	private String columnOne;		//分行自定义1
	private String columnTwo;		//分行自定义2
	private String matrCode;		//物料编码
	private String matrName;		//物料名称
	private int referPrice;		//参考单价(资产类专有)
	private String referType;		//参考型号(资产类专有)
	private String budgetAmount;	//预算金额
	private String memoDescr;		//备注说明
	
	public String getMontName() {
		return montName;
	}
	public void setMontName(String montName) {
		this.montName = montName;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getAcCode() {
		return acCode;
	}
	public void setAcCode(String acCode) {
		this.acCode = acCode;
	}
	public String getColumnOne() {
		return columnOne;
	}
	public void setColumnOne(String columnOne) {
		this.columnOne = columnOne;
	}
	public String getColumnTwo() {
		return columnTwo;
	}
	public void setColumnTwo(String columnTwo) {
		this.columnTwo = columnTwo;
	}
	public int getReferPrice() {
		return referPrice;
	}
	public void setReferPrice(int referPrice) {
		this.referPrice = referPrice;
	}
	public String getReferType() {
		return referType;
	}
	public void setReferType(String referType) {
		this.referType = referType;
	}
	public String getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(String budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	public String getMemoDescr() {
		return memoDescr;
	}
	public void setMemoDescr(String memoDescr) {
		this.memoDescr = memoDescr;
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
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	public String getMatrAuditDept() {
		return matrAuditDept;
	}
	public void setMatrAuditDept(String matrAuditDept) {
		this.matrAuditDept = matrAuditDept;
	}
	public String getUserOrg1Code() {
		return userOrg1Code;
	}
	public void setUserOrg1Code(String userOrg1Code) {
		this.userOrg1Code = userOrg1Code;
	}
	public String getIsSuperAdmin() {
		return isSuperAdmin;
	}
	public void setIsSuperAdmin(String isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}
	public String getInAmt() {
		return inAmt;
	}
	public void setInAmt(String inAmt) {
		this.inAmt = inAmt;
	}
	public String getInMemo() {
		return inMemo;
	}
	public void setInMemo(String inMemo) {
		this.inMemo = inMemo;
	}
	public String getInNouseCnt() {
		return inNouseCnt;
	}
	public void setInNouseCnt(String inNouseCnt) {
		this.inNouseCnt = inNouseCnt;
	}
	public String getInNeedCnt() {
		return inNeedCnt;
	}
	public void setInNeedCnt(String inNeedCnt) {
		this.inNeedCnt = inNeedCnt;
	}
	public String getRowInfo() {
		return rowInfo;
	}
	public void setRowInfo(String rowInfo) {
		this.rowInfo = rowInfo;
	}
	
	public String getDataFlagName() {
		return dataFlagName;
	}
	public void setDataFlagName(String dataFlagName) {
		this.dataFlagName = dataFlagName;
	}
	public String getOrg21Name() {
		return org21Name;
	}
	public void setOrg21Name(String org21Name) {
		this.org21Name = org21Name;
	}
	public String getDataTypeName() {
		return dataTypeName;
	}
	public void setDataTypeName(String dataTypeName) {
		this.dataTypeName = dataTypeName;
	}
	public String getDataAttrName() {
		return dataAttrName;
	}
	public void setDataAttrName(String dataAttrName) {
		this.dataAttrName = dataAttrName;
	}
	public String getTmpltId() {
		return tmpltId;
	}
	public void setTmpltId(String tmpltId) {
		this.tmpltId = tmpltId;
	}
	public String getDutyCode() {
		return dutyCode;
	}
	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}
	public String getSourceFilename() {
		return sourceFilename;
	}
	public void setSourceFilename(String sourceFilename) {
		this.sourceFilename = sourceFilename;
	}
	public String getServerFile() {
		return serverFile;
	}
	public void setServerFile(String serverFile) {
		this.serverFile = serverFile;
	}
	public String getDataFlag() {
		return dataFlag;
	}
	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
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
	public String getInstTime() {
		return instTime;
	}
	public void setInstTime(String instTime) {
		this.instTime = instTime;
	}
	public String getCommitDate() {
		return commitDate;
	}
	public void setCommitDate(String commitDate) {
		this.commitDate = commitDate;
	}
	public String getCommitTime() {
		return commitTime;
	}
	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getDataYear() {
		return dataYear;
	}
	public void setDataYear(String dataYear) {
		this.dataYear = dataYear;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataAttr() {
		return dataAttr;
	}
	public void setDataAttr(String dataAttr) {
		this.dataAttr = dataAttr;
	}
	public String getOrg21Code() {
		return Org21Code;
	}
	public void setOrg21Code(String org21Code) {
		Org21Code = org21Code;
	}
	public String getMatrAuditDeptName() {
		return matrAuditDeptName;
	}
	public void setMatrAuditDeptName(String matrAuditDeptName) {
		this.matrAuditDeptName = matrAuditDeptName;
	}

	
}
