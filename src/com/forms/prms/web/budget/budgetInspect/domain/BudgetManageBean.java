package com.forms.prms.web.budget.budgetInspect.domain;

import java.math.BigDecimal;
import java.util.List;

public class BudgetManageBean {

	private String bgtYear; // 预算汇总-预算年份
	private String bgtOrgcode; // 预算机构
	private String org1Code; // 页面初始时作为用户一级行来获取预算信息
	private String dutyName; // 机构id对应的责任中心名称
	private String bgtMontcode; // 监控指标
	private String bgtMatrcode; // 物料编码
	private String bgtId; // 唯一id
	private String bgtSum; // 总预算
	private String bgtSumValid; // 总可用预算
	private String bgtOverdraw; // 总透支预算
	private BigDecimal bgtFrozen; // 总冻结预算
	private String bgtUsed; // 总占用预算
	private String bgtTemp;
	private String bgtIdTemp;
	private String matrname; // 物料-物料名称
	private String overDrawType; // 总透支预算标志，不为0则透支
	private String montCode;
	private String org21Code; // 二级行、一级行
	private String montType; // 指标类型: 11-专项包 12-省行统购资产 21-非省行统购类资产 22-非专项包费用类
	private String montName; // 监控指标名称
	private String index;
	private String bgtUsedSum;
	private String bgtUsedThisyear;
	private String bgtFree;
	private String bgtWaitFree;
	private String   frozenbgtFree;
	private String   frozenbgtWaitFree;
	
	private String sdId;
	private String bgtType;
	private String bgtValue;
	private String instOper;
	private String instDate;
	private String instTime;
	private String memo;

	private String scdId;
	
	private List<String> org2List;
	

	private String payId;// 付款单号
	private String cntNum;
	
	private String orgType; //01-省行 02-分行
	private String menuType;//1-查询  2-预算调整
	private String exportType;
	private String org2Code;
	private String  referenceName;
	private String   specialName;
	
	private String tzjy;
	
	private String operReson;
	
	private String operMemo;
	
	private String bgtUsedAfter;
	
	private String operVal;
	
	private String bgtIdSeq;
	
	private String operUser;
	
	private String operDate;
	
	private String operTime;
	private String type;//1-可用  2-占用
	
	private String orgCode;
	private String cglCode;
	private String dutyCode;
	private String matrName;

	private String org2Name;
	private String org1Name;
	private String orgName;
	private String dutyName2;
	private String bgtMatrcode1;
	
	public String getBgtMatrcode1() {
		return bgtMatrcode1;
	}

	public void setBgtMatrcode1(String bgtMatrcode1) {
		this.bgtMatrcode1 = bgtMatrcode1;
	}

	public String getOrg2Name() {
		return org2Name;
	}

	public void setOrg2Name(String org2Name) {
		this.org2Name = org2Name;
	}

	public String getOrg1Name() {
		return org1Name;
	}

	public void setOrg1Name(String org1Name) {
		this.org1Name = org1Name;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
	}

	public String getDutyName2() {
		return dutyName2;
	}

	public void setDutyName2(String dutyName2) {
		this.dutyName2 = dutyName2;
	}

	public String getMatrName() {
		return matrName;
	}

	public void setMatrName(String matrName) {
		this.matrName = matrName;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getCglCode() {
		return cglCode;
	}

	public void setCglCode(String cglCode) {
		this.cglCode = cglCode;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getMenuType() {
		return menuType;
	}

	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}

	public List<String> getOrg2List() {
		return org2List;
	}

	public void setOrg2List(List<String> org2List) {
		this.org2List = org2List;
	}


	public String getTzjy() {
		return tzjy;
	}

	public void setTzjy(String tzjy) {
		this.tzjy = tzjy;
	}

	public String getOperReson() {
		return operReson;
	}

	public void setOperReson(String operReson) {
		this.operReson = operReson;
	}

	public String getOperMemo() {
		return operMemo;
	}

	public void setOperMemo(String operMemo) {
		this.operMemo = operMemo;
	}

	public String getBgtUsedAfter() {
		return bgtUsedAfter;
	}

	public void setBgtUsedAfter(String bgtUsedAfter) {
		this.bgtUsedAfter = bgtUsedAfter;
	}

	public String getOperVal() {
		return operVal;
	}

	public void setOperVal(String operVal) {
		this.operVal = operVal;
	}

	public String getBgtIdSeq() {
		return bgtIdSeq;
	}

	public void setBgtIdSeq(String bgtIdSeq) {
		this.bgtIdSeq = bgtIdSeq;
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

	public String getBgtUsedSum() {
		return bgtUsedSum;
	}

	public void setBgtUsedSum(String bgtUsedSum) {
		this.bgtUsedSum = bgtUsedSum;
	}

	public String getBgtUsedThisyear() {
		return bgtUsedThisyear;
	}

	public void setBgtUsedThisyear(String bgtUsedThisyear) {
		this.bgtUsedThisyear = bgtUsedThisyear;
	}

	public String getBgtFree() {
		return bgtFree;
	}

	public void setBgtFree(String bgtFree) {
		this.bgtFree = bgtFree;
	}

	public String getBgtWaitFree() {
		return bgtWaitFree;
	}

	public void setBgtWaitFree(String bgtWaitFree) {
		this.bgtWaitFree = bgtWaitFree;
	}

	public String getFrozenbgtFree() {
		return frozenbgtFree;
	}

	public void setFrozenbgtFree(String frozenbgtFree) {
		this.frozenbgtFree = frozenbgtFree;
	}

	public String getFrozenbgtWaitFree() {
		return frozenbgtWaitFree;
	}

	public void setFrozenbgtWaitFree(String frozenbgtWaitFree) {
		this.frozenbgtWaitFree = frozenbgtWaitFree;
	}

	public String getReferenceName() {
		return referenceName;
	}

	public void setReferenceName(String referenceName) {
		this.referenceName = referenceName;
	}

	public String getSpecialName() {
		return specialName;
	}

	public void setSpecialName(String specialName) {
		this.specialName = specialName;
	}

	public String getOrg2Code() {
		return org2Code;
	}

	public void setOrg2Code(String org2Code) {
		this.org2Code = org2Code;
	}

	public String getExportType() {
		return exportType;
	}

	public void setExportType(String exportType) {
		this.exportType = exportType;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public String getPayId() {
		return payId;
	}

	public void setPayId(String payId) {
		this.payId = payId;
	}

	public String getScdId() {
		return scdId;
	}

	public void setScdId(String scdId) {
		this.scdId = scdId;
	}

	public String getSdId() {
		return sdId;
	}

	public void setSdId(String sdId) {
		this.sdId = sdId;
	}

	public String getBgtType() {
		return bgtType;
	}

	public void setBgtType(String bgtType) {
		this.bgtType = bgtType;
	}

	public String getBgtValue() {
		return bgtValue;
	}

	public void setBgtValue(String bgtValue) {
		this.bgtValue = bgtValue;
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

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getBgtYear() {
		return bgtYear;
	}

	public void setBgtYear(String bgtYear) {
		this.bgtYear = bgtYear;
	}

	public String getBgtMontcode() {
		return bgtMontcode;
	}

	public void setBgtMontcode(String bgtMontcode) {
		this.bgtMontcode = bgtMontcode;
	}

	public String getBgtMatrcode() {
		return bgtMatrcode;
	}

	public void setBgtMatrcode(String bgtMatrcode) {
		this.bgtMatrcode = bgtMatrcode;
	}

	public String getBgtId() {
		return bgtId;
	}

	public void setBgtId(String bgtId) {
		this.bgtId = bgtId;
	}

	public String getBgtSum() {
		return bgtSum;
	}

	public void setBgtSum(String bgtSum) {
		this.bgtSum = bgtSum;
	}

	public String getBgtSumValid() {
		return bgtSumValid;
	}

	public void setBgtSumValid(String bgtSumValid) {
		this.bgtSumValid = bgtSumValid;
	}

	public String getBgtOverdraw() {
		return bgtOverdraw;
	}

	public void setBgtOverdraw(String bgtOverdraw) {
		this.bgtOverdraw = bgtOverdraw;
	}

	public BigDecimal getBgtFrozen() {
		return bgtFrozen;
	}

	public void setBgtFrozen(BigDecimal bgtFrozen) {
		this.bgtFrozen = bgtFrozen;
	}

	public String getBgtUsed() {
		return bgtUsed;
	}

	public void setBgtUsed(String bgtUsed) {
		this.bgtUsed = bgtUsed;
	}

	public String getBgtTemp() {
		return bgtTemp;
	}

	public void setBgtTemp(String bgtTemp) {
		this.bgtTemp = bgtTemp;
	}

	public String getBgtIdTemp() {
		return bgtIdTemp;
	}

	public void setBgtIdTemp(String bgtIdTemp) {
		this.bgtIdTemp = bgtIdTemp;
	}

	public String getMatrname() {
		return matrname;
	}

	public void setMatrname(String matrname) {
		this.matrname = matrname;
	}

	public String getBgtOrgcode() {
		return bgtOrgcode;
	}

	public void setBgtOrgcode(String bgtOrgcode) {
		this.bgtOrgcode = bgtOrgcode;
	}

	public String getMontType() {
		return montType;
	}

	public void setMontType(String montType) {
		this.montType = montType;
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

	public String getOverDrawType() {
		return overDrawType;
	}

	public void setOverDrawType(String overDrawType) {
		this.overDrawType = overDrawType;
	}

	public String getDutyName() {
		return dutyName;
	}

	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}

	public String getOrg21Code() {
		return org21Code;
	}

	public void setOrg21Code(String org21Code) {
		this.org21Code = org21Code;
	}

	public String getIndex() {
		return index;
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

}
