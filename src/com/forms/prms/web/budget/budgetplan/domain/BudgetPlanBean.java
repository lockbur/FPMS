package com.forms.prms.web.budget.budgetplan.domain;

import java.io.Serializable;

public class BudgetPlanBean implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private String tmpltId;					//预算模板ID
	private String tmpltName;				//预算模板组合名称
	private String org21Code;				//模板所属二级行Id
	private String org2Name;				//模板所属二级行名称
	private String sourceFileName;			//原始文件名
	private String serverFile;				//模板文件全路径
	private String dataType;				//预算类型(0:年初预算   1:追加预算)
	private String dataYear;				//模板有效年份
	private String dataAttr;				//预算指标(0:资产类预算  1:费用类预算)
	private String dataAttrName;
	private String dataFlag;				//模板提交状态 A0-待提交 00-待处理(已提交待处理) 01-处理中 02-处理失败 03-处理完成（可开始预算申报）
	private String instOper;				//模板操作者(创建者)
	private String instDate;				//模板创建日期
	private String instTime;				//模板创建时间
	private String commitDate;				//模板提交日期
	private String commitTime;				//模板提交时间
	private String memo;					//备注 (保存excel导入失败的简短失败原因)
	
	private String availableOrgList;		//模板可用部门列表
	private String availableOrgNameList;	//模板可用部门列表
	
	private String sumLvl;					//汇总级别
	private String org1Code;				//一级行汇总
	
	//为Excel配置文件标识添加属性configId
	private String configId;
	
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
	public String getSourceFileName() {
		return sourceFileName;
	}
	public void setSourceFileName(String sourceFileName) {
		this.sourceFileName = sourceFileName;
	}
	public String getServerFile() {
		return serverFile;
	}
	public void setServerFile(String serverFile) {
		this.serverFile = serverFile;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getDataYear() {
		return dataYear;
	}
	public void setDataYear(String dataYear) {
		this.dataYear = dataYear;
	}
	public String getDataAttr() {
		return dataAttr;
	}
	public void setDataAttr(String dataAttr) {
		this.dataAttr = dataAttr;
	}
	public String getDataAttrName() {
		return dataAttrName;
	}
	public void setDataAttrName(String dataAttrName) {
		this.dataAttrName = dataAttrName;
	}
	public String getDataFlag() {
		return dataFlag;
	}
	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
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
	public String getCommitDate() {
		return commitDate;
	}
	public void setCommitDate(String commitDate) {
		this.commitDate = commitDate;
	}
	public String getCommitTime() {
		return commitTime;
	}
	public void setCommitTime(String commitTime) {
		this.commitTime = commitTime;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getAvailableOrgList() {
		return availableOrgList;
	}
	public void setAvailableOrgList(String availableOrgList) {
		this.availableOrgList = availableOrgList;
	}
	public String getConfigId() {
		return configId;
	}
	public void setConfigId(String configId) {
		this.configId = configId;
	}
	public String getAvailableOrgNameList() {
		return availableOrgNameList;
	}
	public void setAvailableOrgNameList(String availableOrgNameList) {
		this.availableOrgNameList = availableOrgNameList;
	}
	public String getSumLvl() {
		return sumLvl;
	}
	public void setSumLvl(String sumLvl) {
		this.sumLvl = sumLvl;
	}
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}
	
}
