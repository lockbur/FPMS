package com.forms.prms.web.budget.budgetResolve.resolve.domain;

import java.math.BigDecimal;

public class BudgetReplyedBean {
	
	private String tmpltId;		//预算模板ID
	private String montCode;	//监控指标编码
	private String montName;	//监控指标名称
	private BigDecimal replyAmt;	//批复金额
	private String replyOper;	//批复柜员
	private String replyDate;	//批复日期
	private String replyTime;	//批复时间
	private BigDecimal splitAmt;	//当次执行的分解金额
	private BigDecimal splitedAmt;	//已分解总金额
	
	private String dutyCode;	//当前用户的责任中心
	private String org21Code;	//查询用户的一级行/二级行(由资产类和费用类区分)
	private String decomposeOrg;//分解部门
	
	//为适应预算分解列表的查询条件而添加
	private String tmpltName;	//预算模板名称
	private BigDecimal dataYear;	//有效年份
	private String dataType;	//预算类型(年初/追加)
	private String dataAttr;	//预算类别(资产类/费用类)
	
	public String getTmpltId() {
		return tmpltId;
	}
	public void setTmpltId(String tmpltId) {
		this.tmpltId = tmpltId;
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
	public String getDutyCode() {
		return dutyCode;
	}
	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}
	public String getOrg21Code() {
		return org21Code;
	}
	public void setOrg21Code(String org21Code) {
		this.org21Code = org21Code;
	}
	public String getDecomposeOrg() {
		return decomposeOrg;
	}
	public void setDecomposeOrg(String decomposeOrg) {
		this.decomposeOrg = decomposeOrg;
	}
	public String getTmpltName() {
		return tmpltName;
	}
	public void setTmpltName(String tmpltName) {
		this.tmpltName = tmpltName;
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
	public BigDecimal getReplyAmt() {
		return replyAmt;
	}
	public void setReplyAmt(BigDecimal replyAmt) {
		this.replyAmt = replyAmt;
	}
	public BigDecimal getSplitAmt() {
		return splitAmt;
	}
	public void setSplitAmt(BigDecimal splitAmt) {
		this.splitAmt = splitAmt;
	}
	public BigDecimal getSplitedAmt() {
		return splitedAmt;
	}
	public void setSplitedAmt(BigDecimal splitedAmt) {
		this.splitedAmt = splitedAmt;
	}
	public BigDecimal getDataYear() {
		return dataYear;
	}
	public void setDataYear(BigDecimal dataYear) {
		this.dataYear = dataYear;
	}
	
}
