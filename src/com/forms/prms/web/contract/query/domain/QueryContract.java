package com.forms.prms.web.contract.query.domain;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.forms.prms.web.contract.initiate.domain.ContractInitate;

public class QueryContract extends ContractInitate {
	private static final long serialVersionUID = 1L;

	private List<Map<String,Object>> tenanciesList;
	

	public List<Map<String, Object>> getTenanciesList() {
		return tenanciesList;
	}

	public void setTenanciesList(List<Map<String, Object>> tenanciesList) {
		this.tenanciesList = tenanciesList;
	}

	// 供应商名称
	private String providerName;

	// 签订日期区间：起始日期
	private String befDate;

	// 签订日期区间：结束日期
	private String aftDate;
	
	// 合同录入日期区间：起始日期
	private String beginCreateDate;

	// 合同录入日期区间：结束日期
	private String endCreateDate;

	// 创建部门名称
	private String createDeptName;
	// 付款责任中心名称
	private String payDutyCodeName;

	// 是否预提待摊类
	private String isPrepaidProvision;

	private String id;

	private String icmsPkuuid;//

	private BigDecimal prepaidRemaindAmt;// 待摊上剩余资金

	private String orgType;

	private String cntProjId;

	private String cntMatrCode;

	private String cntAmtgt;

	private String cntAmtlt;

	private String lxjegt;

	private String lxjelt;

	private String notDataFlag;

	private String houseKindIdName;

	private String flag;// 是否关联查询

	private String orgFlag;

	private String[] dataFlags;

	public String[] getDataFlags() {
		return dataFlags;
	}

	public void setDataFlags(String[] dataFlags) {
		this.dataFlags = dataFlags;
	}

	private BigDecimal cntTaxAmt;
	private BigDecimal cntAllTax;

	private String cntDataFlag;

	private String projName;

	private String feeStartDateShow;

	private String feeEndDateShow;

	private String conCglCode;

	private String subIdSz;
	private String matrCodeSz;
	private String matrNameSz;
	private String deviceModelNameSz;
	private BigDecimal execNumSz;
	private BigDecimal execPriceSz;
	private BigDecimal execAmtSz;
	private BigDecimal taxRateSz;
	private String taxAmtSz;
	private String deductFlagSz;
	private BigDecimal warrantySz;
	private String productorSz;
	
	private String checkedFlag;
	private String operateDate;
	
	public String getBeginCreateDate() {
		return beginCreateDate;
	}

	public void setBeginCreateDate(String beginCreateDate) {
		this.beginCreateDate = beginCreateDate;
	}

	public String getEndCreateDate() {
		return endCreateDate;
	}

	public void setEndCreateDate(String endCreateDate) {
		this.endCreateDate = endCreateDate;
	}

	public String getOperateDate() {
		return operateDate;
	}

	public void setOperateDate(String operateDate) {
		this.operateDate = operateDate;
	}

	public String getCheckedFlag() {
		return checkedFlag;
	}

	public void setCheckedFlag(String checkedFlag) {
		this.checkedFlag = checkedFlag;
	}

	public String getSubIdSz() {
		return subIdSz;
	}

	public void setSubIdSz(String subIdSz) {
		this.subIdSz = subIdSz;
	}

	public String getMatrCodeSz() {
		return matrCodeSz;
	}

	public void setMatrCodeSz(String matrCodeSz) {
		this.matrCodeSz = matrCodeSz;
	}

	public String getMatrNameSz() {
		return matrNameSz;
	}

	public void setMatrNameSz(String matrNameSz) {
		this.matrNameSz = matrNameSz;
	}

	public String getDeviceModelNameSz() {
		return deviceModelNameSz;
	}

	public void setDeviceModelNameSz(String deviceModelNameSz) {
		this.deviceModelNameSz = deviceModelNameSz;
	}

	public BigDecimal getExecNumSz() {
		return execNumSz;
	}

	public void setExecNumSz(BigDecimal execNumSz) {
		this.execNumSz = execNumSz;
	}

	public BigDecimal getExecPriceSz() {
		return execPriceSz;
	}

	public void setExecPriceSz(BigDecimal execPriceSz) {
		this.execPriceSz = execPriceSz;
	}

	public BigDecimal getExecAmtSz() {
		return execAmtSz;
	}

	public void setExecAmtSz(BigDecimal execAmtSz) {
		this.execAmtSz = execAmtSz;
	}

	public BigDecimal getTaxRateSz() {
		return taxRateSz;
	}

	public void setTaxRateSz(BigDecimal taxRateSz) {
		this.taxRateSz = taxRateSz;
	}

	public String getTaxAmtSz() {
		return taxAmtSz;
	}

	public void setTaxAmtSz(String taxAmtSz) {
		this.taxAmtSz = taxAmtSz;
	}

	public String getDeductFlagSz() {
		return deductFlagSz;
	}

	public void setDeductFlagSz(String deductFlagSz) {
		this.deductFlagSz = deductFlagSz;
	}

	public BigDecimal getWarrantySz() {
		return warrantySz;
	}

	public void setWarrantySz(BigDecimal warrantySz) {
		this.warrantySz = warrantySz;
	}

	public String getProductorSz() {
		return productorSz;
	}

	public void setProductorSz(String productorSz) {
		this.productorSz = productorSz;
	}

	public String getConCglCode() {
		return conCglCode;
	}

	public void setConCglCode(String conCglCode) {
		this.conCglCode = conCglCode;
	}

	public String getFeeStartDateShow() {
		return feeStartDateShow;
	}

	public void setFeeStartDateShow(String feeStartDateShow) {
		this.feeStartDateShow = feeStartDateShow;
	}

	public String getFeeEndDateShow() {
		return feeEndDateShow;
	}

	public void setFeeEndDateShow(String feeEndDateShow) {
		this.feeEndDateShow = feeEndDateShow;
	}

	public String getProjName() {
		return projName;
	}

	public void setProjName(String projName) {
		this.projName = projName;
	}

	public String getCntDataFlag() {
		return cntDataFlag;
	}

	public void setCntDataFlag(String cntDataFlag) {
		this.cntDataFlag = cntDataFlag;
	}

	public BigDecimal getCntAllTax() {
		return cntAllTax;
	}

	public void setCntAllTax(BigDecimal cntAllTax) {
		this.cntAllTax = cntAllTax;
	}

	public BigDecimal getCntTaxAmt() {
		return cntTaxAmt;
	}

	public void setCntTaxAmt(BigDecimal cntTaxAmt) {
		this.cntTaxAmt = cntTaxAmt;
	}

	public String getOrgFlag() {
		return orgFlag;
	}

	public void setOrgFlag(String orgFlag) {
		this.orgFlag = orgFlag;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getOrgType() {
		return orgType;
	}

	public void setOrgType(String orgType) {
		this.orgType = orgType;
	}

	public String getHouseKindIdName() {
		return houseKindIdName;
	}

	public void setHouseKindIdName(String houseKindIdName) {
		this.houseKindIdName = houseKindIdName;
	}

	public String getNotDataFlag() {
		return notDataFlag;
	}

	public void setNotDataFlag(String notDataFlag) {
		this.notDataFlag = notDataFlag;
	}

	public String getCntAmtgt() {
		return cntAmtgt;
	}

	public void setCntAmtgt(String cntAmtgt) {
		this.cntAmtgt = cntAmtgt;
	}

	public String getCntAmtlt() {
		return cntAmtlt;
	}

	public void setCntAmtlt(String cntAmtlt) {
		this.cntAmtlt = cntAmtlt;
	}

	public String getLxjegt() {
		return lxjegt;
	}

	public void setLxjegt(String lxjegt) {
		this.lxjegt = lxjegt;
	}

	public String getLxjelt() {
		return lxjelt;
	}

	public void setLxjelt(String lxjelt) {
		this.lxjelt = lxjelt;
	}

	public String getCntProjId() {
		return cntProjId;
	}

	public void setCntProjId(String cntProjId) {
		this.cntProjId = cntProjId;
	}

	public String getCntMatrCode() {
		return cntMatrCode;
	}

	public void setCntMatrCode(String cntMatrCode) {
		this.cntMatrCode = cntMatrCode;
	}

	public BigDecimal getPrepaidRemaindAmt() {
		return prepaidRemaindAmt;
	}

	public void setPrepaidRemaindAmt(BigDecimal prepaidRemaindAmt) {
		this.prepaidRemaindAmt = prepaidRemaindAmt;
	}

	public String getIcmsPkuuid() {
		return icmsPkuuid;
	}

	public void setIcmsPkuuid(String icmsPkuuid) {
		this.icmsPkuuid = icmsPkuuid;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getIsPrepaidProvision() {
		return isPrepaidProvision;
	}

	public void setIsPrepaidProvision(String isPrepaidProvision) {
		this.isPrepaidProvision = isPrepaidProvision;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

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

	public String getCreateDeptName() {
		return createDeptName;
	}

	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}

	public String getPayDutyCodeName() {
		return payDutyCodeName;
	}

	public void setPayDutyCodeName(String payDutyCodeName) {
		this.payDutyCodeName = payDutyCodeName;
	}

}
