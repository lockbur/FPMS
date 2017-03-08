package com.forms.prms.web.contract.change.domain;

import java.math.BigDecimal;

import com.forms.prms.web.contract.initiate.domain.ContractBean;

public class ChangeForm extends ContractBean {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String waterMemo;// 流水意见

	private String providerName;// 供应商名
	private String cntTypeName;
	private String dataFlagName;
	private String lxlxName;
	private String feeTypeName;
	private String feeSubTypeName;
	private String payTermName;
	private String createDeptName;

	private String startDate;
	private String endDate;

	private String dutyCode;// 所在责任中心

	// 项目
	private String projId;

	// 项目名称
	private String projName;

	// 物料类型
	private String matrCode;

	// 设备型号
	private String deviceModel;

	// 数量
	private int execNum;

	// 单价
	private double execPrice;

	// 执行金额
	private double execAmt;

	// 保修期
	private int warranty;

	// 制造商
	private String productor;

	// 费用承担部门
	private String feeDept;

	private BigDecimal cntTaxAmt;

	private String orgFlag;

	private String org2Code;

	public String getOrg2Code() {
		return org2Code;
	}

	public void setOrg2Code(String org2Code) {
		this.org2Code = org2Code;
	}

	public String getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}

	public BigDecimal getCntTaxAmt() {
		return cntTaxAmt;
	}

	public void setCntTaxAmt(BigDecimal cntTaxAmt) {
		this.cntTaxAmt = cntTaxAmt;
	}

	public String getProjId() {
		return projId;
	}

	public void setProjId(String projId) {
		this.projId = projId;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getMatrCode() {
		return matrCode;
	}

	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}

	public String getDeviceModel() {
		return deviceModel;
	}

	public void setDeviceModel(String deviceModel) {
		this.deviceModel = deviceModel;
	}

	public int getExecNum() {
		return execNum;
	}

	public void setExecNum(int execNum) {
		this.execNum = execNum;
	}

	public double getExecPrice() {
		return execPrice;
	}

	public void setExecPrice(double execPrice) {
		this.execPrice = execPrice;
	}

	public double getExecAmt() {
		return execAmt;
	}

	public void setExecAmt(double execAmt) {
		this.execAmt = execAmt;
	}

	public int getWarranty() {
		return warranty;
	}

	public void setWarranty(int warranty) {
		this.warranty = warranty;
	}

	public String getProductor() {
		return productor;
	}

	public void setProductor(String productor) {
		this.productor = productor;
	}

	public String getFeeDept() {
		return feeDept;
	}

	public void setFeeDept(String feeDept) {
		this.feeDept = feeDept;
	}

	public String getWaterMemo() {
		return waterMemo;
	}

	public void setWaterMemo(String waterMemo) {
		this.waterMemo = waterMemo;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getCntTypeName() {
		return cntTypeName;
	}

	public void setCntTypeName(String cntTypeName) {
		this.cntTypeName = cntTypeName;
	}

	public String getDataFlagName() {
		return dataFlagName;
	}

	public void setDataFlagName(String dataFlagName) {
		this.dataFlagName = dataFlagName;
	}

	public String getLxlxName() {
		return lxlxName;
	}

	public void setLxlxName(String lxlxName) {
		this.lxlxName = lxlxName;
	}

	public String getFeeTypeName() {
		return feeTypeName;
	}

	public void setFeeTypeName(String feeTypeName) {
		this.feeTypeName = feeTypeName;
	}

	public String getFeeSubTypeName() {
		return feeSubTypeName;
	}

	public void setFeeSubTypeName(String feeSubTypeName) {
		this.feeSubTypeName = feeSubTypeName;
	}

	public String getPayTermName() {
		return payTermName;
	}

	public void setPayTermName(String payTermName) {
		this.payTermName = payTermName;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getDutyCode() {
		return dutyCode;
	}

	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}

	public String getCreateDeptName() {
		return createDeptName;
	}

	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}

}
