package com.forms.prms.web.budget.budgetReply.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class BudgetReplyBean implements Serializable {

	private static final long serialVersionUID = -1017421409264544250L;

	// TABLE TB_BUDGET_TMPLT
	private String tmpltId; // 模板ID 模板的定义是以二级行为单位的
	private String tmpltName; // 模板名称TMPLT_NAME
	private String org21Code; // 模板所属的二级行
	private String org2Name; // 模板所属二级行名称
	private String dataType; // 预算类型 0-年初预算 1-追加预算(对一个二级行而言，年初预算只能有一个，追加预算可以有多个)
	private BigDecimal dataYear; // 有效年份 yyyy
	private String dataAttr; // 预算指标 0-资产类 1-费用类
	private String dataFlag; // 数据状态 数据导入状态：A0-待提交 00-待处理(已提交待处理) 01-处理中 02-处理失败 03-处理完成（可开始预算申报）
	private String sourceFilename; // 原始文件名 文件存储在服务器上的全路径，为避免中文乱码及文件名，其中的文件名为转义后的文件名。
	private String serverFile; // 模板文件全路径
	private String instOper;
	// TABLE TB_BUDGET_TMPLT_DUTY
	private String dutyCode; // 责任中心
	private String dutyName; // 责任中心名称
	// TABLE TB_MONT_NAME
	private String montCode; //监控指标
	private String montName; //监控指标
	// TABLE TD_BUDGET_REPLY_HEADER
	private BigDecimal replyAmt;//批复总金额
	private BigDecimal allotedAmt;//已分配金额
	// TABLE TD_BUDGET_REPLY_DETAIL
	private String replyOper;//批复柜员
	private String replyDate;//批复日期 yyyy-mm-dd
	private String replyTime;//批复时间 hh24:mi:ss
	private String dataAttrType;
	
	
	private String[] montCodes; //++
	private String[] replyFees; //++

	private boolean queryFlag; //++
	private String TmplthaveSplitAmt;//模板已经分解金额
	private String montHaveSplitAmt;//指标已经分解金额
	
	
	public String getTmplthaveSplitAmt() {
		return TmplthaveSplitAmt;
	}

	public void setTmplthaveSplitAmt(String tmplthaveSplitAmt) {
		TmplthaveSplitAmt = tmplthaveSplitAmt;
	}

	public String getMontHaveSplitAmt() {
		return montHaveSplitAmt;
	}

	public void setMontHaveSplitAmt(String montHaveSplitAmt) {
		this.montHaveSplitAmt = montHaveSplitAmt;
	}

	public String getDataAttrType() {
		return dataAttrType;
	}

	public void setDataAttrType(String dataAttrType) {
		this.dataAttrType = dataAttrType;
	}

	public BigDecimal getReplyAmt() {
		return replyAmt;
	}

	public void setReplyAmt(BigDecimal replyAmt) {
		this.replyAmt = replyAmt;
	}

	public BigDecimal getAllotedAmt() {
		return allotedAmt;
	}

	public void setAllotedAmt(BigDecimal allotedAmt) {
		this.allotedAmt = allotedAmt;
	}

	public String getReplyOper() {
		return replyOper;
	}

	public void setReplyOper(String replyOper) {
		this.replyOper = replyOper;
	}

	public String getReplyDate() {
		return replyDate;
	}

	public void setReplyDate(String replyDate) {
		this.replyDate = replyDate;
	}

	public String getReplyTime() {
		return replyTime;
	}

	public void setReplyTime(String replyTime) {
		this.replyTime = replyTime;
	}

	public String[] getMontCodes() {
		return montCodes;
	}

	public void setMontCodes(String[] montCodes) {
		this.montCodes = montCodes;
	}

	public String[] getReplyFees() {
		return replyFees;
	}

	public void setReplyFees(String[] replyFees) {
		this.replyFees = replyFees;
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

	public String getTmpltId() {
		return tmpltId;
	}

	public void setTmpltId(String tmpltId) {
		this.tmpltId = tmpltId;
	}

	public String getTmpltName() {
		return tmpltName;
	}

	public void setTmpltName(String tmpltName) {
		this.tmpltName = tmpltName;
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

	public boolean isQueryFlag() {
		return queryFlag;
	}

	public void setQueryFlag(boolean queryFlag) {
		this.queryFlag = queryFlag;
	}

}
