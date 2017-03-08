package com.forms.prms.web.amortization.provisionMgr.domain;

import java.io.Serializable;
import java.util.List;

/**
 * Title:类名
 * Description:	预提实体类
 * Copyright: 	formssi
 * @author 		HQQ
 * @project 	ERP
 * @date 		2015-05-14
 * @version 	1.0
 */
public class ProvisionBean implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private String provMgrId;			//预提管理BeanID(由受益年月+合同号组成)
	private String provMgrIdList;		//预提管理BeanID集合(用于页面传输给后台时执行批处理提交)
	private String cntNum;				//合同号
	
	private String org1Code;			//机构代码(超级管理员：一级行org1Code； 非超级管理员：二级行org2Code；若无org2Code：则取orgCode ;Else取值dutyCode)
	private String orgFlag;				//需要过滤查询预提的org信息过滤(参考org1Code参数)
	private String dataFlag;			//状态
	private String[] createDepts;		//合同对应的创建部门(多个)
	private String createDeptCutShow;
	private List<String> createDeptList;
	private String createDept;			//合同对应的创建部门(对应表TD_CNT中的CREATE_DEPT字段)
	private String matrCode;			//物料编码(关联预提待摊表TI_TRADE_TOTAL)
	private String montCode;			//监控指标编码(关联预提待摊表TI_TRADE_TOTAL)
	private String feeCglCode;			//费用核算码(关联预提待摊表TI_TRADE_TOTAL)
	private String provisionAmt;		//预提金额
	private String prepaidAmt;			//待摊分录金额
	
	private String feeYyyymm;			//费用受益年月
	private String provFlag;			//是否需要预提
	private String provFlag2;			//是否需要预提
	private String operUser;			//录入用户
	private String operDate;			//录入日期
	private String operTime;			//录入时间
	private String checkUser;			//复核用户
	private String checkDate;			//复核日期
	private String checkTime;			//复核时间
	
	//6-15新增需求：增加显示”AP发票校验文件“未回的状态(根据合同号作关联)、合同事项；	
	private String cntName;				//合同事项(关联合同表TD_CNT获得)
	private String importStatus;		//AP发票校验"回"状态
	
	
	private String provisionAmtSum;		//预提金额汇总
	private String prepaidAmtSum;		//待摊分录金额汇总
	 
	
	
	public String getProvisionAmtSum() {
		return provisionAmtSum;
	}
	public void setProvisionAmtSum(String provisionAmtSum) {
		this.provisionAmtSum = provisionAmtSum;
	}
	public String getPrepaidAmtSum() {
		return prepaidAmtSum;
	}
	public void setPrepaidAmtSum(String prepaidAmtSum) {
		this.prepaidAmtSum = prepaidAmtSum;
	}
	public List<String> getCreateDeptList() {
		return createDeptList;
	}
	public void setCreateDeptList(List<String> createDeptList) {
		this.createDeptList = createDeptList;
	}
	public String getCreateDeptCutShow() {
		return createDeptCutShow;
	}
	public void setCreateDeptCutShow(String createDeptCutShow) {
		this.createDeptCutShow = createDeptCutShow;
	}
	public String getProvFlag2() {
		return provFlag2;
	}
	public void setProvFlag2(String provFlag2) {
		this.provFlag2 = provFlag2;
	}
	public String getProvMgrId() {
		return provMgrId;
	}
	public void setProvMgrId(String provMgrId) {
		this.provMgrId = provMgrId;
	}
	public String getProvMgrIdList() {
		return provMgrIdList;
	}
	public void setProvMgrIdList(String provMgrIdList) {
		this.provMgrIdList = provMgrIdList;
	}
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getCntName() {
		return cntName;
	}
	public void setCntName(String cntName) {
		this.cntName = cntName;
	}
	public String getOrg1Code() {
		return org1Code;
	}
	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}
	public String getOrgFlag() {
		return orgFlag;
	}
	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}
	public String getDataFlag() {
		return dataFlag;
	}
	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}
	public String[] getCreateDepts() {
		return createDepts;
	}
	public void setCreateDepts(String[] createDepts) {
		this.createDepts = createDepts;
	}
	public String getCreateDept() {
		return createDept;
	}
	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}
	public String getMatrCode() {
		return matrCode;
	}
	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}
	public String getMontCode() {
		return montCode;
	}
	public void setMontCode(String montCode) {
		this.montCode = montCode;
	}
	public String getFeeCglCode() {
		return feeCglCode;
	}
	public void setFeeCglCode(String feeCglCode) {
		this.feeCglCode = feeCglCode;
	}
	public String getProvisionAmt() {
		return provisionAmt;
	}
	public void setProvisionAmt(String provisionAmt) {
		this.provisionAmt = provisionAmt;
	}
	public String getPrepaidAmt() {
		return prepaidAmt;
	}
	public void setPrepaidAmt(String prepaidAmt) {
		this.prepaidAmt = prepaidAmt;
	}
	public String getFeeYyyymm() {
		return feeYyyymm;
	}
	public void setFeeYyyymm(String feeYyyymm) {
		this.feeYyyymm = feeYyyymm;
	}
	public String getProvFlag() {
		return provFlag;
	}
	public void setProvFlag(String provFlag) {
		this.provFlag = provFlag;
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
	public String getCheckUser() {
		return checkUser;
	}
	public void setCheckUser(String checkUser) {
		this.checkUser = checkUser;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getCheckTime() {
		return checkTime;
	}
	public void setCheckTime(String checkTime) {
		this.checkTime = checkTime;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getImportStatus() {
		return importStatus;
	}
	public void setImportStatus(String importStatus) {
		this.importStatus = importStatus;
	}
	
}
