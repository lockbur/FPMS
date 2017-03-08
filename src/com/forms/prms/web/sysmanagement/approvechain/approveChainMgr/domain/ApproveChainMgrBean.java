package com.forms.prms.web.sysmanagement.approvechain.approveChainMgr.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class ApproveChainMgrBean implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 6342571614625391058L;
	private String org1Code;
	private String montCode;
	private String matrCode;
	private String matrBuyDept;
	private String matrAuditDept;
	private String fincDeptS;
	private String fincDept2;
	private String fincDept1;
	private String instOper;
	private String instDate;
	private String instTime;
	private String org1Name;
	private String montName;
	private String matrName;
	private String matrBuyName;
	private String matrBuyDeptName;
	private String matrAuditDeptName;
	private String matrAuditName;
	private String fincDeptSName;
	private String fincDept2Name;
	private String fincDept1Name;
	private String montCodeNew;
	private String montNameNew;
	private String aprvTypName;
	private String noUp;
	private String changeData;
	private String tabsIndex;//tab
	private List<String> containMonts;//物料对应的监控指标 如果物料没监控指标为-1
	private String aprvType; //审批链类型 01：专项包 02：省行统购 03:其他
	private String aprvType2;//解决一个月面两个类型的冲突  用于已维护查询
	private String aprvTypeName;
	private String[] matrs;
	List<Map<String, String>> montMatrList;
	private String thisYear;
	private String feeCode;
	private String feeCodeName;
	private String feeName;
	private String decomposeOrg;//预算分解部门
	private String decomposeOrgName;
	private String org2CodeOri;
	private String org2Code;
	private String org2Name;
	private String isProvinceBuy;
	private String dutyCode;//分行未维护的责任中心
	private String dutyName;
	private String noUpCount;//未维护的物料
	private String changePosition;//机构撤并引起的审批链变化位置
	private String orgType;//01省行 02 分行
	private String groupId;
	
	private String feeLevl;
	private String feeNameLevl;
	
	/*****下面是撤并责任中心的字段     ******/
	private String changeType;//01-新增责任中心 02-撤并责任中心 03-归属机构变化
	private String noWhTag;//省行未维护页面跳转类型01是初始选择页面,02是专项包，03是是否省行统购未维护页面
	private String orgCode;
	private String orgName;
	
	private String[] matrA;
	private String[] dutys;
	private String lvl;//级别是二级行还是二级行本部下的机构
	
	private String updateFeeCode;//修改后费用承担部门
	private String updateMatrBuyDept;
	private String updateMatrAuditDept;
	private String org21Code;//二级行或一级行
	private String dataYear;//所属年份
	private String menuType;//01-省行，02-分行
	
	private String specTableName;
	private String noSpecTableName;
	
	private String isSigle;
	
	
	
	public String getIsSigle() {
		return isSigle;
	}
	public void setIsSigle(String isSigle) {
		this.isSigle = isSigle;
	}
	public String getFeeLevl() {
		return feeLevl;
	}
	public void setFeeLevl(String feeLevl) {
		this.feeLevl = feeLevl;
	}
	public String getFeeNameLevl() {
		return feeNameLevl;
	}
	public void setFeeNameLevl(String feeNameLevl) {
		this.feeNameLevl = feeNameLevl;
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
	public String getThisYear() {
		return thisYear;
	}
	public void setThisYear(String thisYear) {
		this.thisYear = thisYear;
	}
	public String getChangeData() {
		return changeData;
	}
	public void setChangeData(String changeData) {
		this.changeData = changeData;
	}
	public String getGroupId() {
		return groupId;
	}
	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}
	public String getMontCodeNew() {
		return montCodeNew;
	}
	public void setMontCodeNew(String montCodeNew) {
		this.montCodeNew = montCodeNew;
	}
	public String getMontNameNew() {
		return montNameNew;
	}
	public void setMontNameNew(String montNameNew) {
		this.montNameNew = montNameNew;
	}
	public String getAprvTypName() {
		return aprvTypName;
	}
	public void setAprvTypName(String aprvTypName) {
		this.aprvTypName = aprvTypName;
	}
	public String getNoUp() {
		return noUp;
	}
	public void setNoUp(String noUp) {
		this.noUp = noUp;
	}
	public String getOrgType() {
		return orgType;
	}
	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}
	public String getSpecTableName() {
		return specTableName;
	}
	public void setSpecTableName(String specTableName) {
		this.specTableName = specTableName;
	}
	public String getNoSpecTableName() {
		return noSpecTableName;
	}
	public void setNoSpecTableName(String noSpecTableName) {
		this.noSpecTableName = noSpecTableName;
	}
	public String getMenuType() {
		return menuType;
	}
	public void setMenuType(String menuType) {
		this.menuType = menuType;
	}
	public String getAprvTypeName() {
		return aprvTypeName;
	}
	public void setAprvTypeName(String aprvTypeName) {
		this.aprvTypeName = aprvTypeName;
	}
	public String getDataYear() {
		return dataYear;
	}
	public void setDataYear(String dataYear) {
		this.dataYear = dataYear;
	}
	public String getMatrBuyDeptName() {
		return matrBuyDeptName;
	}
	public void setMatrBuyDeptName(String matrBuyDeptName) {
		this.matrBuyDeptName = matrBuyDeptName;
	}
	public String getMatrAuditDeptName() {
		return matrAuditDeptName;
	}
	public void setMatrAuditDeptName(String matrAuditDeptName) {
		this.matrAuditDeptName = matrAuditDeptName;
	}
	public String getFeeCodeName() {
		return feeCodeName;
	}
	public void setFeeCodeName(String feeCodeName) {
		this.feeCodeName = feeCodeName;
	}
	public String getOrg21Code() {
		return org21Code;
	}
	public void setOrg21Code(String org21Code) {
		this.org21Code = org21Code;
	}
	public String getUpdateFeeCode() {
		return updateFeeCode;
	}
	public void setUpdateFeeCode(String updateFeeCode) {
		this.updateFeeCode = updateFeeCode;
	}
	public String getUpdateMatrBuyDept() {
		return updateMatrBuyDept;
	}
	public void setUpdateMatrBuyDept(String updateMatrBuyDept) {
		this.updateMatrBuyDept = updateMatrBuyDept;
	}
	public String getUpdateMatrAuditDept() {
		return updateMatrAuditDept;
	}
	public void setUpdateMatrAuditDept(String updateMatrAuditDept) {
		this.updateMatrAuditDept = updateMatrAuditDept;
	}
	public String getOrg2Name() {
		return org2Name;
	}
	public void setOrg2Name(String org2Name) {
		this.org2Name = org2Name;
	}
	public String getLvl() {
		return lvl;
	}
	public void setLvl(String lvl) {
		this.lvl = lvl;
	}
	public String[] getDutys() {
		return dutys;
	}
	public void setDutys(String[] dutys) {
		this.dutys = dutys;
	}
	public String[] getMatrA() {
		return matrA;
	}
	public void setMatrA(String[] matrA) {
		this.matrA = matrA;
	}
	public String getNoWhTag() {
		return noWhTag;
	}
	public void setNoWhTag(String noWhTag) {
		this.noWhTag = noWhTag;
	}
	private String dutyArray;//批量的时候选择的多个责任中心
	
	public String getAprvType() {
		return aprvType;
	}
	public void setAprvType(String aprvType) {
		this.aprvType = aprvType;
	}
	public String getAprvType2() {
		return aprvType2;
	}
	public void setAprvType2(String aprvType2) {
		this.aprvType2 = aprvType2;
	}
	private List<String> dutyList;
	
	
	public List<String> getDutyList() {
		return dutyList;
	}
	public void setDutyList(List<String> dutyList) {
		this.dutyList = dutyList;
	}
	public String getDutyArray() {
		return dutyArray;
	}
	public void setDutyArray(String dutyArray) {
		this.dutyArray = dutyArray;
	}
	public String getChangeType() {
		return changeType;
	}
	public void setChangeType(String changeType) {
		this.changeType = changeType;
	}
	public String getChangePosition() {
		return changePosition;
	}
	public void setChangePosition(String changePosition) {
		this.changePosition = changePosition;
	}
	public String getNoUpCount() {
		return noUpCount;
	}
	public void setNoUpCount(String noUpCount) {
		this.noUpCount = noUpCount;
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
	public String getIsProvinceBuy() {
		return isProvinceBuy;
	}
	public void setIsProvinceBuy(String isProvinceBuy) {
		this.isProvinceBuy = isProvinceBuy;
	}
	public String getOrg2Code() {
		return org2Code;
	}
	public void setOrg2Code(String org2Code) {
		this.org2Code = org2Code;
	}
	public String getOrg2CodeOri() {
		return org2CodeOri;
	}
	public void setOrg2CodeOri(String org2CodeOri) {
		this.org2CodeOri = org2CodeOri;
	}
	
	public String getFeeCode() {
		return feeCode;
	}
	public void setFeeCode(String feeCode) {
		this.feeCode = feeCode;
	}
	public String getFeeName() {
		return feeName;
	}
	public void setFeeName(String feeName) {
		this.feeName = feeName;
	}
	
	public String getDecomposeOrg() {
		return decomposeOrg;
	}
	public void setDecomposeOrg(String decomposeOrg) {
		this.decomposeOrg = decomposeOrg;
	}
	 
	public String getDecomposeOrgName() {
		return decomposeOrgName;
	}
	public void setDecomposeOrgName(String decomposeOrgName) {
		this.decomposeOrgName = decomposeOrgName;
	}
	public List<Map<String, String>> getMontMatrList() {
		return montMatrList;
	}
	public void setMontMatrList(List<Map<String, String>> montMatrList) {
		this.montMatrList = montMatrList;
	}
	public String[] getMatrs() {
		return matrs;
	}
	public void setMatrs(String[] matrs) {
		this.matrs = matrs;
	}
	public List<String> getContainMonts() {
		return containMonts;
	}
	public void setContainMonts(List<String> containMonts) {
		this.containMonts = containMonts;
	}
	public String getTabsIndex() {
		return tabsIndex;
	}
	public void setTabsIndex(String tabsIndex) {
		this.tabsIndex = tabsIndex;
	}
	public String getOrg1Name() {
		return org1Name;
	}
	public void setOrg1Name(String org1Name) {
		this.org1Name = org1Name;
	}
	public String getMontName() {
		return montName;
	}
	public void setMontName(String montName) {
		this.montName = montName;
	}
	public String getMatrName() {
		return matrName;
	}
	public void setMatrName(String matrName) {
		this.matrName = matrName;
	}
	public String getMatrBuyName() {
		return matrBuyName;
	}
	public void setMatrBuyName(String matrBuyName) {
		this.matrBuyName = matrBuyName;
	}
	public String getMatrAuditName() {
		return matrAuditName;
	}
	public void setMatrAuditName(String matrAuditName) {
		this.matrAuditName = matrAuditName;
	}
	public String getFincDeptSName() {
		return fincDeptSName;
	}
	public void setFincDeptSName(String fincDeptSName) {
		this.fincDeptSName = fincDeptSName;
	}
	public String getFincDept2Name() {
		return fincDept2Name;
	}
	public void setFincDept2Name(String fincDept2Name) {
		this.fincDept2Name = fincDept2Name;
	}
	public String getFincDept1Name() {
		return fincDept1Name;
	}
	public void setFincDept1Name(String fincDept1Name) {
		this.fincDept1Name = fincDept1Name;
	}
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}
	public String getMontCode() {
		return montCode;
	}
	public void setMontCode(String montCode) {
		this.montCode = montCode;
	}
	public String getMatrCode() {
		return matrCode;
	}
	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}
	public String getMatrBuyDept() {
		return matrBuyDept;
	}
	public void setMatrBuyDept(String matrBuyDept) {
		this.matrBuyDept = matrBuyDept;
	}
	public String getMatrAuditDept() {
		return matrAuditDept;
	}
	public void setMatrAuditDept(String matrAuditDept) {
		this.matrAuditDept = matrAuditDept;
	}
	public String getFincDeptS() {
		return fincDeptS;
	}
	public void setFincDeptS(String fincDeptS) {
		this.fincDeptS = fincDeptS;
	}
	public String getFincDept2() {
		return fincDept2;
	}
	public void setFincDept2(String fincDept2) {
		this.fincDept2 = fincDept2;
	}
	public String getFincDept1() {
		return fincDept1;
	}
	public void setFincDept1(String fincDept1) {
		this.fincDept1 = fincDept1;
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
	
	
	
}
