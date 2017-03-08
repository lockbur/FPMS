package com.forms.prms.web.sysmanagement.orgremanagement.dutyMerge.domain;

import java.io.Serializable;

public class DutyMergeBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5071561289555541800L;

	private String handleDate;// 撤并年月
	private String modiYyyymmS;// 撤并年月
	private String modiYyyymmE;// 撤并年月
	private String codeBef;// 撤并前责任中心
	private String nameBef;// 撤并前责任中心
	private String codeCur;// 撤并后责任中心
	private String nameCur;// 撤并后责任中心
	private String modiUser;// 修改用户
	private String modiDate;// 修改日期
	private String modiTime;// 修改时间
	private String type;// 01责任中心撤并 02 机构撤并
	private String status;// 01：经办 02：复核通过 03：复核不通过
	private String memo;
	private String aprvUser;
	private String aprvDate;
	private String aprvTime;
	private String handleTime;
	private String handleUser;
	private String[] codeBefs;
	private String[] codeCurs;
	private String codeBef2;// 撤并前责任中心2
	private String codeCur2;// 撤并后责任中心2
	private String seq;
	private String id;
	private String menuTag;// 01:经办 02：复核 03：查询
	private String seqP;//
	private String aprvTag;
	private String dutyCode;
	private String dutyName;
	private String invalidDate;
	private String codeBefArray;
	private String codeCurArray;
	private String batchStatus;
	private String dataDate;
	private String batchNo;
	private String changeType;

	private String orgCode;//
	private String orgName;//
	private String lastUpdtime;//
	private String dealFlag;//
	
	private String preStatus;
	private String curStatus;
	private String ipAddress;
	
	
	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getPreStatus() {
		return preStatus;
	}

	public void setPreStatus(String preStatus) {
		this.preStatus = preStatus;
	}

	public String getCurStatus() {
		return curStatus;
	}

	public void setCurStatus(String curStatus) {
		this.curStatus = curStatus;
	}

	public String getDealFlag() {
		return dealFlag;
	}

	public void setDealFlag(String dealFlag) {
		this.dealFlag = dealFlag;
	}

	public String getLastUpdtime() {
		return lastUpdtime;
	}

	public void setLastUpdtime(String lastUpdtime) {
		this.lastUpdtime = lastUpdtime;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getNameBef() {
		return nameBef;
	}

	public void setNameBef(String nameBef) {
		this.nameBef = nameBef;
	}

	public String getNameCur() {
		return nameCur;
	}

	public void setNameCur(String nameCur) {
		this.nameCur = nameCur;
	}

	public String getChangeType() {
		return changeType;
	}

	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public String getDataDate() {
		return dataDate;
	}

	public void setDataDate(String dataDate) {
		this.dataDate = dataDate;
	}

	public String getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(String batchStatus) {
		this.batchStatus = batchStatus;
	}

	public String getCodeBefArray() {
		return codeBefArray;
	}

	public void setCodeBefArray(String codeBefArray) {
		this.codeBefArray = codeBefArray;
	}

	public String getCodeCurArray() {
		return codeCurArray;
	}

	public void setCodeCurArray(String codeCurArray) {
		this.codeCurArray = codeCurArray;
	}

	public String getInvalidDate() {
		return invalidDate;
	}

	public void setInvalidDate(String invalidDate) {
		this.invalidDate = invalidDate;
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

	public String getAprvTag() {
		return aprvTag;
	}

	public void setAprvTag(String aprvTag) {
		this.aprvTag = aprvTag;
	}

	public String getSeqP() {
		return seqP;
	}

	public void setSeqP(String seqP) {
		this.seqP = seqP;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getCodeBef2() {
		return codeBef2;
	}

	public void setCodeBef2(String codeBef2) {
		this.codeBef2 = codeBef2;
	}

	public String getCodeCur2() {
		return codeCur2;
	}

	public void setCodeCur2(String codeCur2) {
		this.codeCur2 = codeCur2;
	}

	public String[] getCodeBefs() {
		return codeBefs;
	}

	public void setCodeBefs(String[] codeBefs) {
		this.codeBefs = codeBefs;
	}

	public String[] getCodeCurs() {
		return codeCurs;
	}

	public void setCodeCurs(String[] codeCurs) {
		this.codeCurs = codeCurs;
	}

	public String getMenuTag() {
		return menuTag;
	}

	public void setMenuTag(String menuTag) {
		this.menuTag = menuTag;
	}

	public String getHandleDate() {
		return handleDate;
	}

	public void setHandleDate(String handleDate) {
		this.handleDate = handleDate;
	}

	public String getHandleTime() {
		return handleTime;
	}

	public void setHandleTime(String handleTime) {
		this.handleTime = handleTime;
	}

	public String getHandleUser() {
		return handleUser;
	}

	public void setHandleUser(String handleUser) {
		this.handleUser = handleUser;
	}

	public String getModiYyyymmS() {
		return modiYyyymmS;
	}

	public void setModiYyyymmS(String modiYyyymmS) {
		this.modiYyyymmS = modiYyyymmS;
	}

	public String getModiYyyymmE() {
		return modiYyyymmE;
	}

	public void setModiYyyymmE(String modiYyyymmE) {
		this.modiYyyymmE = modiYyyymmE;
	}

	public String getCodeBef() {
		return codeBef;
	}

	public void setCodeBef(String codeBef) {
		this.codeBef = codeBef;
	}

	public String getCodeCur() {
		return codeCur;
	}

	public void setCodeCur(String codeCur) {
		this.codeCur = codeCur;
	}

	public String getModiUser() {
		return modiUser;
	}

	public void setModiUser(String modiUser) {
		this.modiUser = modiUser;
	}

	public String getModiDate() {
		return modiDate;
	}

	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	public String getModiTime() {
		return modiTime;
	}

	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getAprvUser() {
		return aprvUser;
	}

	public void setAprvUser(String aprvUser) {
		this.aprvUser = aprvUser;
	}

	public String getAprvDate() {
		return aprvDate;
	}

	public void setAprvDate(String aprvDate) {
		this.aprvDate = aprvDate;
	}

	public String getAprvTime() {
		return aprvTime;
	}

	public void setAprvTime(String aprvTime) {
		this.aprvTime = aprvTime;
	}

}
