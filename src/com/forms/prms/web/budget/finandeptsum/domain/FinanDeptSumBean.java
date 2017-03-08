package com.forms.prms.web.budget.finandeptsum.domain;

import java.io.Serializable;
import java.math.BigDecimal;


public class FinanDeptSumBean implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3355277414766309107L;
	private String tmpltId;			//预算模板ID			(传到本级汇总参数中)
	private String dutyCode;		//本级汇总部门		(传到本级汇总参数中)
	private String dutyName;		//本级汇总部门名称
	private String org1Code;		//一级汇总部门
	private String org2Code;		//二级汇总部门
	private String orgDept;			//物料审批部门行ID
	private String orgDeptName;		//物料审批部门行名称
	private String matrCode; 		//物料编码
	private String matrName;		//物料名称
	private String inAmt;			//申报金额
	private String auditAmt;		//已审金额
	private String matrAuditDept;//物料归口管理部门
	private String matrAuditDeptName;
	private String feeDept;//费用承担部门
	private String feeDeptName;
	private String auditSeq;
	
	private String dataYear;//模板有效年份
	private String tmpltType;//模板类型
	private String orgCode;//统计查询的条件
	private String oldAmt;//存量
	private String addAmt;//申报金额
	private String auditFlag;//审核状态
	private String auditFlagName;//状态名称
	
	private String auditLvl;//位置
	private BigDecimal beforeAmt;//审批前金额
	private BigDecimal afterAmt;//审批后金额
	private String auditOper;//审核人
	private String auditMemo;//备注
	private String type;//是本级 还是一级还是二级
	
	
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public BigDecimal getBeforeAmt() {
		return beforeAmt;
	}
	public void setBeforeAmt(BigDecimal beforeAmt) {
		this.beforeAmt = beforeAmt;
	}
	public BigDecimal getAfterAmt() {
		return afterAmt;
	}
	public void setAfterAmt(BigDecimal afterAmt) {
		this.afterAmt = afterAmt;
	}
	public String getAuditOper() {
		return auditOper;
	}
	public void setAuditOper(String auditOper) {
		this.auditOper = auditOper;
	}
	public String getAuditSeq() {
		return auditSeq;
	}
	public void setAuditSeq(String auditSeq) {
		this.auditSeq = auditSeq;
	}
	public String getAuditLvl() {
		return auditLvl;
	}
	public void setAuditLvl(String auditLvl) {
		this.auditLvl = auditLvl;
	}
	public String getAuditMemo() {
		return auditMemo;
	}
	
	public void setAuditMemo(String auditMemo) {
		this.auditMemo = auditMemo;
	}
	public String getAuditFlag() {
		return auditFlag;
	}
	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}
	public String getAddAmt() {
		return addAmt;
	}
	public void setAddAmt(String addAmt) {
		this.addAmt = addAmt;
	}
	public String getOldAmt() {
		return oldAmt;
	}
	public void setOldAmt(String oldAmt) {
		this.oldAmt = oldAmt;
	}
	public String getFeeDept() {
		return feeDept;
	}
	public void setFeeDept(String feeDept) {
		this.feeDept = feeDept;
	}
	public String getFeeDeptName() {
		return feeDeptName;
	}
	public void setFeeDeptName(String feeDeptName) {
		this.feeDeptName = feeDeptName;
	}
	public String getOrgCode() {
		return orgCode;
	}
	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}
	public String getTmpltType() {
		return tmpltType;
	}
	public void setTmpltType(String tmpltType) {
		this.tmpltType = tmpltType;
	}
	public String getDataYear() {
		return dataYear;
	}
	public void setDataYear(String dataYear) {
		this.dataYear = dataYear;
	}
	public String getMatrAuditDeptName() {
		return matrAuditDeptName;
	}
	public void setMatrAuditDeptName(String matrAuditDeptName) {
		this.matrAuditDeptName = matrAuditDeptName;
	}
	public String getMatrAuditDept() {
		return matrAuditDept;
	}
	public void setMatrAuditDept(String matrAuditDept) {
		this.matrAuditDept = matrAuditDept;
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
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}
	public String getOrg2Code() {
		return org2Code;
	}
	public void setOrg2Code(String org2Code) {
		this.org2Code = org2Code;
	}
	public String getOrgDept() {
		return orgDept;
	}
	public void setOrgDept(String orgDept) {
		this.orgDept = orgDept;
	}
	public String getOrgDeptName() {
		return orgDeptName;
	}
	public void setOrgDeptName(String orgDeptName) {
		this.orgDeptName = orgDeptName;
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
	public String getInAmt() {
		return inAmt;
	}
	public void setInAmt(String inAmt) {
		this.inAmt = inAmt;
	}
	public String getAuditAmt() {
		return auditAmt;
	}
	public void setAuditAmt(String auditAmt) {
		this.auditAmt = auditAmt;
	}
	public String getAuditFlagName() {
		return auditFlagName;
	}
	public void setAuditFlagName(String auditFlagName) {
		this.auditFlagName = auditFlagName;
	}
}
