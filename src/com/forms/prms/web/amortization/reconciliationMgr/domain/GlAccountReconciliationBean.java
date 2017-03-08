package com.forms.prms.web.amortization.reconciliationMgr.domain;

/**
 * GL经费总账对账Bean
 * Copyright: formssi
 * @author HQQ
 * @project ERP
 * @date 2015-05-09
 * @version 1.0
 */
public class GlAccountReconciliationBean {

	private String	cntNum;				//合同编号
	private String	voucherName;		//凭证名称
	private String	sendValue;			//ERP发送值
	private String	uploadDate;			//ERP发送时间
	private String	checkValue;			//检查文件返回值
	private String	checkDownDate;		//检查文件处理时间
	private String	checkResult;		//检查文件返回结果
	private String	glValue;			//GL待摊预提返回值
	private String	glDate;				//GL待摊预提ERP处理时间
	private String	glResult;			//GL待摊预提返回结果
	
	private String org1Code;            //一级行行号
	
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getVoucherName() {
		return voucherName;
	}
	public void setVoucherName(String voucherName) {
		this.voucherName = voucherName;
	}
	public String getSendValue() {
		return sendValue;
	}
	public void setSendValue(String sendValue) {
		this.sendValue = sendValue;
	}
	public String getUploadDate() {
		return uploadDate;
	}
	public void setUploadDate(String uploadDate) {
		this.uploadDate = uploadDate;
	}
	public String getCheckValue() {
		return checkValue;
	}
	public void setCheckValue(String checkValue) {
		this.checkValue = checkValue;
	}
	public String getCheckDownDate() {
		return checkDownDate;
	}
	public void setCheckDownDate(String checkDownDate) {
		this.checkDownDate = checkDownDate;
	}
	public String getCheckResult() {
		return checkResult;
	}
	public void setCheckResult(String checkResult) {
		this.checkResult = checkResult;
	}
	public String getGlValue() {
		return glValue;
	}
	public void setGlValue(String glValue) {
		this.glValue = glValue;
	}
	public String getGlDate() {
		return glDate;
	}
	public void setGlDate(String glDate) {
		this.glDate = glDate;
	}
	public String getGlResult() {
		return glResult;
	}
	public void setGlResult(String glResult) {
		this.glResult = glResult;
	}
}
