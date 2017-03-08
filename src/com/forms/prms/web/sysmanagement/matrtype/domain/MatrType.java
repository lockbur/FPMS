package com.forms.prms.web.sysmanagement.matrtype.domain;

import java.io.Serializable;

public class MatrType implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String matrCode;// 物料编码
	private String matrName;// 物料名称
	private String cglCode;// 核算码
	private String matrUnit;// 物料单位
	private String lossCode;// 损益子目 ???
	private String matrCode1code;// 物料编码第1位
	// private String matrCode1desc;//物料
	// private String matrCode23code;//物料编码第2、3位代码
	// private String matrCode23desc;//物料编码第2、3位说明
	// private String matrCode45code;//物料编码第4、5位代码
	// private String matrCode45desc;//物料编码第4、5位说明
	// private String matrCode67code;//物料编码第6、7位代码
	// private String matrCode67desc;//物料编码第6、7位说明
	// private String matr47desc;//资产模块使用的资产说明，与物料编码第4到第7位的说明一致
	private String matrType;// 物料类型
	private String provisionCode;// 预提科目
	private String prepaidCode;// 待摊科目
	private String isPublicityPay;// 是否业务宣传费
	private String memo;// 说明
	private String orgCode;

	private String isNotinfee; // 是否为不入库费用 Y-是 N-否
	private String isFcwl; // 是否是房产类物料：Y-是 N-否
	private String isOrder;// 是否生成采购订单： Y-是 N-否
	private String isPrepaidProvision; //是否是待摊预提：Y-是 N-否

	private String feeDept;// 费用承担部门 FEE_DEPT
	private String isProvinceBuy;// 是否省行统购 IS_PROVINCE_BUY
	private String cntType;// 合同类型 CNT_TYPE
	private String feeType;// 费用类型

	private String isSpec;// 是否专项包
	private String montCode;// 监控指标代码
	private String montName;// 监控指标名称
	private String shortPrepaidCode;// 待摊核算码（短期）
	private String longPrepaidCode;// 待摊核算码（长期）
	private String longPrepaidCodeFee;//长期待摊科目固定对应的费用核算码（上一列有值本列才有可能有值）

	private String path;// 上传文件路径

	private String montType;
	private String montTypeName;
	private String allMontType;
	private String matrBuyDept;
	private String matrAuditDept;
	private String matrBuyDeptName;//采购部门名称
	private String matrAuditDeptName;//归口部门名称
	
	private String isGDZC;
	private String org1Code;

	public String getLongPrepaidCodeFee() {
		return longPrepaidCodeFee;
	}

	public void setLongPrepaidCodeFee(String longPrepaidCodeFee) {
		this.longPrepaidCodeFee = longPrepaidCodeFee;
	}

	public String getIsPrepaidProvision() {
		return isPrepaidProvision;
	}

	public void setIsPrepaidProvision(String isPrepaidProvision) {
		this.isPrepaidProvision = isPrepaidProvision;
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

	public String getIsFcwl() {
		return isFcwl;
	}

	public void setIsFcwl(String isFcwl) {
		this.isFcwl = isFcwl;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getIsGDZC() {
		return isGDZC;
	}

	public void setIsGDZC(String isGDZC) {
		this.isGDZC = isGDZC;
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

	public String getAllMontType() {
		return allMontType;
	}

	public void setAllMontType(String allMontType) {
		this.allMontType = allMontType;
	}

	public String getMontType() {
		return montType;
	}

	public void setMontType(String montType) {
		this.montType = montType;
	}

	public String getMontTypeName() {
		return montTypeName;
	}

	public void setMontTypeName(String montTypeName) {
		this.montTypeName = montTypeName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getShortPrepaidCode() {
		return shortPrepaidCode;
	}

	public void setShortPrepaidCode(String shortPrepaidCode) {
		this.shortPrepaidCode = shortPrepaidCode;
	}

	public String getLongPrepaidCode() {
		return longPrepaidCode;
	}

	public void setLongPrepaidCode(String longPrepaidCode) {
		this.longPrepaidCode = longPrepaidCode;
	}

	public String getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}

	public String getMontCode() {
		return montCode;
	}

	public void setMontCode(String montCode) {
		this.montCode = montCode;
	}

	public String getMontName() {
		return montName;
	}

	public void setMontName(String montName) {
		this.montName = montName;
	}

	public String getIsSpec() {
		return isSpec;
	}

	public void setIsSpec(String isSpec) {
		this.isSpec = isSpec;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getCntType() {
		return cntType;
	}

	public void setCntType(String cntType) {
		this.cntType = cntType;
	}

	public String getFeeDept() {
		return feeDept;
	}

	public void setFeeDept(String feeDept) {
		this.feeDept = feeDept;
	}

	public String getIsProvinceBuy() {
		return isProvinceBuy;
	}

	public void setIsProvinceBuy(String isProvinceBuy) {
		this.isProvinceBuy = isProvinceBuy;
	}

	public String getMatrCode() {
		return matrCode;
	}

	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}

	public String getMatrName() {
		return matrName;
	}

	public void setMatrName(String matrName) {
		this.matrName = matrName;
	}

	public String getCglCode() {
		return cglCode;
	}

	public void setCglCode(String cglCode) {
		this.cglCode = cglCode;
	}

	public String getMatrUnit() {
		return matrUnit;
	}

	public void setMatrUnit(String matrUnit) {
		this.matrUnit = matrUnit;
	}

	public String getLossCode() {
		return lossCode;
	}

	public void setLossCode(String lossCode) {
		this.lossCode = lossCode;
	}

	public String getMatrCode1code() {
		return matrCode1code;
	}

	// public void setMatrCode1code(String matrCode1code)
	// {
	// this.matrCode1code = matrCode1code;
	// }
	// public String getMatrCode1desc()
	// {
	// return matrCode1desc;
	// }
	// public void setMatrCode1desc(String matrCode1desc)
	// {
	// this.matrCode1desc = matrCode1desc;
	// }
	// public String getMatrCode23code()
	// {
	// return matrCode23code;
	// }
	// public void setMatrCode23code(String matrCode23code)
	// {
	// this.matrCode23code = matrCode23code;
	// }
	// public String getMatrCode23desc()
	// {
	// return matrCode23desc;
	// }
	// public void setMatrCode23desc(String matrCode23desc)
	// {
	// this.matrCode23desc = matrCode23desc;
	// }
	// public String getMatrCode45code()
	// {
	// return matrCode45code;
	// }
	// public void setMatrCode45code(String matrCode45code)
	// {
	// this.matrCode45code = matrCode45code;
	// }
	// public String getMatrCode45desc()
	// {
	// return matrCode45desc;
	// }
	// public void setMatrCode45desc(String matrCode45desc)
	// {
	// this.matrCode45desc = matrCode45desc;
	// }
	// public String getMatrCode67code()
	// {
	// return matrCode67code;
	// }
	// public void setMatrCode67code(String matrCode67code)
	// {
	// this.matrCode67code = matrCode67code;
	// }
	// public String getMatrCode67desc()
	// {
	// return matrCode67desc;
	// }
	// public void setMatrCode67desc(String matrCode67desc)
	// {
	// this.matrCode67desc = matrCode67desc;
	// }
	// public String getMatr47desc()
	// {
	// return matr47desc;
	// }
	// public void setMatr47desc(String matr47desc)
	// {
	// this.matr47desc = matr47desc;
	// }

	public String getMatrType() {
		return matrType;
	}

	public void setMatrType(String matrType) {
		this.matrType = matrType;
	}

	public String getProvisionCode() {
		return provisionCode;
	}

	public void setProvisionCode(String provisionCode) {
		this.provisionCode = provisionCode;
	}

	public String getPrepaidCode() {
		return prepaidCode;
	}

	public void setPrepaidCode(String prepaidCode) {
		this.prepaidCode = prepaidCode;
	}

	public String getIsPublicityPay() {
		return isPublicityPay;
	}

	public void setIsPublicityPay(String isPublicityPay) {
		this.isPublicityPay = isPublicityPay;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public void setMatrCode1code(String matrCode1code) {
		this.matrCode1code = matrCode1code;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getIsNotinfee() {
		return isNotinfee;
	}

	public void setIsNotinfee(String isNotinfee) {
		this.isNotinfee = isNotinfee;
	}

}
