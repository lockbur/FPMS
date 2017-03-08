package com.forms.prms.web.amortization.tradeBackwash.domain;

import java.io.Serializable;

public class BackwashBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String id;//序号
	private String feeYyyymm;//受益年月
	private String cntNum;//合同号
	private String dataFlag;//状态 ‘0’-录入
	private String operUser;//操作用户
	private String operDate;//操作日期
	private String operTime;//操作时间
	
	private String org1Code;//一级行号
	
	private String cntNumList;//合同号列表
	private String[] cntNums;//
	
	private String befDate;//开始日期
	private String aftDate;//结束日期
	
	
	
	
	
	
	public String getBefDate() {
		return befDate;
	}
	public void setBefDate(String befDate) {
		this.befDate = befDate;
	}
	public String getAftDate() {
		return aftDate;
	}
	public void setAftDate(String aftDate) {
		this.aftDate = aftDate;
	}
	public String[] getCntNums() {
		return cntNums;
	}
	public void setCntNums(String[] cntNums) {
		this.cntNums = cntNums;
	}
	public String getCntNumList() {
		return cntNumList;
	}
	public void setCntNumList(String cntNumList) {
		this.cntNumList = cntNumList;
	}
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getFeeYyyymm() {
		return feeYyyymm;
	}
	public void setFeeYyyymm(String feeYyyymm) {
		this.feeYyyymm = feeYyyymm;
	}
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getDataFlag() {
		return dataFlag;
	}
	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}
	
	public String getOperUser() {
		return operUser;
	}
	public void setOperUser(String operUser) {
		this.operUser = operUser;
	}
	public String getOperDate() {
		return operDate;
	}
	public void setOperDate(String operDate) {
		this.operDate = operDate;
	}
	public String getOperTime() {
		return operTime;
	}
	public void setOperTime(String operTime) {
		this.operTime = operTime;
	}
	
	

}
