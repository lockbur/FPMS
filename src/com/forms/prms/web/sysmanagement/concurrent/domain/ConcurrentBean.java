package com.forms.prms.web.sysmanagement.concurrent.domain;

import java.io.Serializable;

public class ConcurrentBean implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 6131255321318968916L;
	private String type;//来源类型  大类  A,B
	private String subType;//子类
	private String ipAddress;
	private String instOper;
	private String memo;
	private String org21Code;
	private String lockNo;
	private String batchNo;
	
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getLockNo() {
		return lockNo;
	}
	public void setLockNo(String lockNo) {
		this.lockNo = lockNo;
	}
	public String getOrg21Code() {
		return org21Code;
	}
	public void setOrg21Code(String org21Code) {
		this.org21Code = org21Code;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getSubType() {
		return subType;
	}
	public void setSubType(String subType) {
		this.subType = subType;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getInstOper() {
		return instOper;
	}
	public void setInstOper(String instOper) {
		this.instOper = instOper;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	
	
	

}
