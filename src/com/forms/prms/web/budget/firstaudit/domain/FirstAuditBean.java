package com.forms.prms.web.budget.firstaudit.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class FirstAuditBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	//TABLE TB_BUDGET_TMPLT
	private String tmpltName;		//模板名称TMPLT_NAME
	private String org21Code;		//模板所属的二级行
	private String org2Name;		//模板所属二级行名称
	private String dataType;		//预算类型 0-年初预算 1-追加预算(对一个二级行而言，年初预算只能有一个，追加预算可以有多个)
	private BigDecimal dataYear;	//有效年份 yyyy
	private String dataAttr;		//预算指标 0-资产类 1-费用类
	private String dataFlag;		//数据状态 数据导入状态：A0-待提交 00-待处理(已提交待处理) 01-处理中 02-处理失败 03-处理完成（可开始预算申报）
	private String sourceFilename;	//原始文件名 文件存储在服务器上的全路径，为避免中文乱码及文件名，其中的文件名为转义后的文件名。
	private String serverFile;		//模板文件全路径
	private String instOper;
	//TABLE TB_BUDGET_TMPLT_DUTY
	private String dutyName;		//责任中心名称
	
	private String currDutyCode;	//登陆人责任中心
	
	private String waitAudit;		//待审数
	private String haveAudit;		//已审数
	
	private boolean queryFlag;
	
	private String matrName;
	private String stockAmt;        //存
	private String applyAmt;
	private String auditFlag;
	
	
	
	private String isAudit;				//是否已审
	
	private String auditAmt;            //审批金额
	private String rowSeq;
	private String[] auditAmts;
	private String[] matrCodes;
	private String[] rowSeqs;
	
	
	//TD_BUDGET_FIRST_AUDIT_LOG
	private String tmpltId;	      //模板ID 模板的定义是以二级行为单位的
	private String dutyCode;      //责任中心
	private String matrCode;      //物料编码
	private String auditSeq;      //内部序号
	private String auditLvl;      //审批位置
	private String beforeAmt;     //审批前金额
	private String afterAmt;      //审批后金额
	private String auditOper;     //审批柜员
	private String auditDate;     //审批日期
	private String auditTime;     //审批时间
	private String auditMemo;	  //说明
	

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

	public String getBeforeAmt() {
		return beforeAmt;
	}

	public void setBeforeAmt(String beforeAmt) {
		this.beforeAmt = beforeAmt;
	}

	public String getAfterAmt() {
		return afterAmt;
	}

	public void setAfterAmt(String afterAmt) {
		this.afterAmt = afterAmt;
	}

	public String getAuditDate() {
		return auditDate;
	}

	public void setAuditDate(String auditDate) {
		this.auditDate = auditDate;
	}

	public String getAuditTime() {
		return auditTime;
	}

	public void setAuditTime(String auditTime) {
		this.auditTime = auditTime;
	}

	public String getAuditMemo() {
		return auditMemo;
	}

	public void setAuditMemo(String auditMemo) {
		this.auditMemo = auditMemo;
	}

	public String getTmpltName() {
		return tmpltName;
	}

	public void setTmpltName(String tmpltName) {
		this.tmpltName = tmpltName;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getTmpltId() {
		return tmpltId;
	}

	public void setTmpltId(String tmpltId) {
		this.tmpltId = tmpltId;
	}

	public String getOrg21Code() {
		return org21Code;
	}

	public void setOrg21Code(String org21Code) {
		this.org21Code = org21Code;
	}

	public String getOrg2Name() {
		return org2Name;
	}

	public void setOrg2Name(String org2Name) {
		this.org2Name = org2Name;
	}

	public String getDataType() {
		return dataType;
	}

	public void setDataType(String dataType) {
		this.dataType = dataType;
	}

	public BigDecimal getDataYear() {
		return dataYear;
	}

	public void setDataYear(BigDecimal dataYear) {
		this.dataYear = dataYear;
	}

	public String getDataAttr() {
		return dataAttr;
	}

	public void setDataAttr(String dataAttr) {
		this.dataAttr = dataAttr;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
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

	public String getInstOper() {
		return instOper;
	}

	public void setInstOper(String instOper) {
		this.instOper = instOper;
	}


	public String getWaitAudit() {
		return waitAudit;
	}

	public void setWaitAudit(String waitAudit) {
		this.waitAudit = waitAudit;
	}

	public String getHaveAudit() {
		return haveAudit;
	}

	public void setHaveAudit(String haveAudit) {
		this.haveAudit = haveAudit;
	}

	public String getCurrDutyCode() {
		return currDutyCode;
	}

	public void setCurrDutyCode(String currDutyCode) {
		this.currDutyCode = currDutyCode;
	}

	public String getIsAudit() {
		return isAudit;
	}

	public void setIsAudit(String isAudit) {
		this.isAudit = isAudit;
	}


	public String[] getRowSeqs() {
		return rowSeqs;
	}

	public void setRowSeqs(String[] rowSeqs) {
		this.rowSeqs = rowSeqs;
	}

	public String[] getAuditAmts() {
		return auditAmts;
	}

	public void setAuditAmts(String[] auditAmts) {
		this.auditAmts = auditAmts;
	}

	public String getAuditAmt() {
		return auditAmt;
	}

	public void setAuditAmt(String auditAmt) {
		this.auditAmt = auditAmt;
	}

	public String getRowSeq() {
		return rowSeq;
	}

	public void setRowSeq(String rowSeq) {
		this.rowSeq = rowSeq;
	}

	public String getAuditOper() {
		return auditOper;
	}

	public void setAuditOper(String auditOper) {
		this.auditOper = auditOper;
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

	public String getStockAmt() {
		return stockAmt;
	}

	public void setStockAmt(String stockAmt) {
		this.stockAmt = stockAmt;
	}

	public String getApplyAmt() {
		return applyAmt;
	}

	public void setApplyAmt(String applyAmt) {
		this.applyAmt = applyAmt;
	}

	public boolean isQueryFlag() {
		return queryFlag;
	}

	public void setQueryFlag(boolean queryFlag) {
		this.queryFlag = queryFlag;
	}

	public String getAuditFlag() {
		return auditFlag;
	}

	public void setAuditFlag(String auditFlag) {
		this.auditFlag = auditFlag;
	}

	public String[] getMatrCodes() {
		return matrCodes;
	}

	public void setMatrCodes(String[] matrCodes) {
		this.matrCodes = matrCodes;
	}

	
}
