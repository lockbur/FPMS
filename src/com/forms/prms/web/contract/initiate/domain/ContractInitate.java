package com.forms.prms.web.contract.initiate.domain;

import java.math.BigDecimal;
import java.util.List;

public class ContractInitate extends ContractBean {
	private static final long serialVersionUID = 1L;
	// 分期支付
	// 按进度支付
	private BigDecimal[] jdtj;// 付款进度
	private BigDecimal[] jdzf;// 进度支付
	private String[] jdDate;// 付款日期
	// 按日期支付
	private BigDecimal[] rqtj;// 付款进度
	private BigDecimal[] rqzf;// 进度支付
	// 按条件支付
	private BigDecimal dhzf;// 到货支付
	private BigDecimal yszf;// 验收支付
	private BigDecimal jszf;// 结算支付

	// 电子审批
	private String[] abcde;// 缩位码
	private BigDecimal[] abcdeAmt;// 金额
	private BigDecimal[] abcdeNum;// 数量

	// 房屋租赁信息
	private String glbm; // 管理部门
	private String beginDate; // 执行开始日期
	private String endDate; // 执行结束日期
	private BigDecimal area; // 租赁面积
	private BigDecimal unitPrice; // 租赁单价
	private String jf; // 甲方名称
	private String jfId; // 甲方ID
	private String yf; // 乙方名称
	private String yfId; // 乙方ID 默认为一级分行机构号
	private String remark; // 备注
	private String houseKindId; // 房产性质
	private String houseKindName; // 房产性质名称
	private BigDecimal rent; // 月租金
	private String wdjg; // 网点机构
	private String glbmId; // 管理部门ID
	private String wdjgId; // 网点机构号
	private String wydz; // 物业地址
	private BigDecimal wyglf; // 物业管理费
	private BigDecimal yj; // 押金
	private String autoBankName;// 自助银行名称

	// 租金递增
	private String[] fromDate;// 递增起始日期
	private String[] toDate;// 递增结束日期
	private String[] matrCodeFz;// 物料编码
	private BigDecimal[] cntAmtTr;// 合同总金额
	private BigDecimal[] execAmtTr;// 不含税金额
	private BigDecimal[] taxAmtTr;// 税额

	// 费用类型
	private String[] subId;// 子序号
	private String[] cglCode;// 核算码
	private String[] feeYyyymm;// 受益年月
	private BigDecimal[] cglCalAmt;// 测算金额 系统计算的
	private BigDecimal[] cglFeeAmt;// 受益金额 实际录入的

	private String[] dzlx;// 递增类型
	private BigDecimal[] dzed;// 递增额度
	private String[] dzdw;// 递增单位
	private BigDecimal[] glfy;// 管理费用及其他
	private String[] dzyf;//递增总月份
	private BigDecimal[] dzfz;//递增房租

	// 设备物料信息
	private String[] projId;// 合同项目ID 根据项目可见性选择
	private String[] feeDept;// 费用承担部门 默认为录入人责任中心，可选范围为所属二级行(若没有为一级行)下面的责任中心
	private String[] feeDeptOld;//合同变更时应用 费用承担部门取变更前原值
	private String[] matrCode;// 物料编码 根据合同类型、费用类型、审批链(费用承担部门、物料编码和是否省行统购)选择
	private String[] montCode;// 监控指标
	private String[] montName;// 监控指标名称
	private String[] deviceModelName;// 设备型号
	private String[] reference;// 参考
	private String[] special;// 专项
	private BigDecimal[] execNum;// 数量
	private BigDecimal[] execPrice;// 单价
	private BigDecimal[] execAmt;// 执行金额
	private BigDecimal[] warranty;// 保修期
	private String[] productor;// 制造商
	private String[] devDataFlag;// 复核状态

	private String[] auditMemo;// 审批信息

	private List<CntDevice> devices; // 物料信息
	private List<TenancyDz> tenancies; // 租金递增信息
	private String tenanciesStr; // 租金递增信息
	private List<DzspInfo> dzspInfos; // 电子审批信息
	private List<StageInfo> stageInfos;// 分期付款信息
	private List<CntDevice> benefit; // 费用类型受益信息

	// 供应商
	private String providerType;
	private String provActCurr;
	private String provActNo;
	private String providerCode;
	private String providerName;
	private String providerAddr;
	private String actName;
	private String bankInfo;
	private String bankCode;
	private String bankArea;
	private String bankName;
	private String actType;
	private String payMode;

	private String providerAddrCode;
	
	private String actualFeeEndDate;
	
	private String isEnable;

	public String getIsEnable() {
		return isEnable;
	}

	public void setIsEnable(String isEnable) {
		this.isEnable = isEnable;
	}
	
	public List<TenancyDz> getTenancies() {
		return tenancies;
	}

	public void setTenancies(List<TenancyDz> tenancies) {
		this.tenancies = tenancies;
	}

	public String[] getMatrCodeFz() {
		return matrCodeFz;
	}

	public void setMatrCodeFz(String[] matrCodeFz) {
		this.matrCodeFz = matrCodeFz;
	}

	public BigDecimal[] getCntAmtTr() {
		return cntAmtTr;
	}

	public void setCntAmtTr(BigDecimal[] cntAmtTr) {
		this.cntAmtTr = cntAmtTr;
	}

	public BigDecimal[] getExecAmtTr() {
		return execAmtTr;
	}

	public void setExecAmtTr(BigDecimal[] execAmtTr) {
		this.execAmtTr = execAmtTr;
	}

	public BigDecimal[] getTaxAmtTr() {
		return taxAmtTr;
	}

	public void setTaxAmtTr(BigDecimal[] taxAmtTr) {
		this.taxAmtTr = taxAmtTr;
	}

	public String getActualFeeEndDate() {
		return actualFeeEndDate;
	}

	public void setActualFeeEndDate(String actualFeeEndDate) {
		this.actualFeeEndDate = actualFeeEndDate;
	}

	public String[] getFeeDeptOld() {
		return feeDeptOld;
	}

	public void setFeeDeptOld(String[] feeDeptOld) {
		this.feeDeptOld = feeDeptOld;
	}

	public String getProviderAddrCode() {
		return providerAddrCode;
	}

	public void setProviderAddrCode(String providerAddrCode) {
		this.providerAddrCode = providerAddrCode;
	}

	public String getProviderType() {
		return providerType;
	}

	public void setProviderType(String providerType) {
		this.providerType = providerType;
	}

	public String getProvActCurr() {
		return provActCurr;
	}

	public void setProvActCurr(String provActCurr) {
		this.provActCurr = provActCurr;
	}

	public String getProvActNo() {
		return provActNo;
	}

	public void setProvActNo(String provActNo) {
		this.provActNo = provActNo;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getProviderName() {
		return providerName;
	}

	public void setProviderName(String providerName) {
		this.providerName = providerName;
	}

	public String getProviderAddr() {
		return providerAddr;
	}

	public void setProviderAddr(String providerAddr) {
		this.providerAddr = providerAddr;
	}

	public String getActName() {
		return actName;
	}

	public void setActName(String actName) {
		this.actName = actName;
	}

	public String getBankInfo() {
		return bankInfo;
	}

	public void setBankInfo(String bankInfo) {
		this.bankInfo = bankInfo;
	}

	public String getBankCode() {
		return bankCode;
	}

	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}

	public String getBankArea() {
		return bankArea;
	}

	public void setBankArea(String bankArea) {
		this.bankArea = bankArea;
	}

	public String getBankName() {
		return bankName;
	}

	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	public String getActType() {
		return actType;
	}

	public void setActType(String actType) {
		this.actType = actType;
	}

	public String getPayMode() {
		return payMode;
	}

	public void setPayMode(String payMode) {
		this.payMode = payMode;
	}

	public String getTenanciesStr() {
		return tenanciesStr;
	}

	public void setTenanciesStr(String tenanciesStr) {
		this.tenanciesStr = tenanciesStr;
	}

	public BigDecimal[] getJdtj() {
		return jdtj;
	}

	public void setJdtj(BigDecimal[] jdtj) {
		this.jdtj = jdtj;
	}

	public BigDecimal[] getJdzf() {
		return jdzf;
	}

	public void setJdzf(BigDecimal[] jdzf) {
		this.jdzf = jdzf;
	}

	public String[] getJdDate() {
		return jdDate;
	}

	public void setJdDate(String[] jdDate) {
		this.jdDate = jdDate;
	}

	public BigDecimal[] getRqtj() {
		return rqtj;
	}

	public void setRqtj(BigDecimal[] rqtj) {
		this.rqtj = rqtj;
	}

	public BigDecimal[] getRqzf() {
		return rqzf;
	}

	public void setRqzf(BigDecimal[] rqzf) {
		this.rqzf = rqzf;
	}

	public BigDecimal getDhzf() {
		return dhzf;
	}

	public void setDhzf(BigDecimal dhzf) {
		this.dhzf = dhzf;
	}

	public BigDecimal getYszf() {
		return yszf;
	}

	public void setYszf(BigDecimal yszf) {
		this.yszf = yszf;
	}

	public BigDecimal getJszf() {
		return jszf;
	}

	public void setJszf(BigDecimal jszf) {
		this.jszf = jszf;
	}

	public String[] getAbcde() {
		return abcde;
	}

	public void setAbcde(String[] abcde) {
		this.abcde = abcde;
	}

	public BigDecimal[] getAbcdeAmt() {
		return abcdeAmt;
	}

	public void setAbcdeAmt(BigDecimal[] abcdeAmt) {
		this.abcdeAmt = abcdeAmt;
	}

	public BigDecimal[] getAbcdeNum() {
		return abcdeNum;
	}

	public void setAbcdeNum(BigDecimal[] abcdeNum) {
		this.abcdeNum = abcdeNum;
	}

	public String getGlbm() {
		return glbm;
	}

	public void setGlbm(String glbm) {
		this.glbm = glbm;
	}

	public String getBeginDate() {
		return beginDate;
	}

	public void setBeginDate(String beginDate) {
		this.beginDate = beginDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public BigDecimal getArea() {
		return area;
	}

	public void setArea(BigDecimal area) {
		this.area = area;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	public void setUnitPrice(BigDecimal unitPrice) {
		this.unitPrice = unitPrice;
	}

	public String getJf() {
		return jf;
	}

	public void setJf(String jf) {
		this.jf = jf;
	}

	public String getJfId() {
		return jfId;
	}

	public void setJfId(String jfId) {
		this.jfId = jfId;
	}

	public String getYf() {
		return yf;
	}

	public void setYf(String yf) {
		this.yf = yf;
	}

	public String getYfId() {
		return yfId;
	}

	public void setYfId(String yfId) {
		this.yfId = yfId;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getHouseKindId() {
		return houseKindId;
	}

	public void setHouseKindId(String houseKindId) {
		this.houseKindId = houseKindId;
	}

	public BigDecimal getRent() {
		return rent;
	}

	public void setRent(BigDecimal rent) {
		this.rent = rent;
	}

	public String getWdjg() {
		return wdjg;
	}

	public void setWdjg(String wdjg) {
		this.wdjg = wdjg;
	}

	public String getGlbmId() {
		return glbmId;
	}

	public void setGlbmId(String glbmId) {
		this.glbmId = glbmId;
	}

	public String getWdjgId() {
		return wdjgId;
	}

	public void setWdjgId(String wdjgId) {
		this.wdjgId = wdjgId;
	}

	public String getWydz() {
		return wydz;
	}

	public void setWydz(String wydz) {
		this.wydz = wydz;
	}

	public BigDecimal getWyglf() {
		return wyglf;
	}

	public void setWyglf(BigDecimal wyglf) {
		this.wyglf = wyglf;
	}

	public BigDecimal getYj() {
		return yj;
	}

	public void setYj(BigDecimal yj) {
		this.yj = yj;
	}

	public String getAutoBankName() {
		return autoBankName;
	}

	public void setAutoBankName(String autoBankName) {
		this.autoBankName = autoBankName;
	}

	public String[] getFromDate() {
		return fromDate;
	}

	public void setFromDate(String[] fromDate) {
		this.fromDate = fromDate;
	}

	public String[] getToDate() {
		return toDate;
	}

	public void setToDate(String[] toDate) {
		this.toDate = toDate;
	}

	public String[] getSubId() {
		return subId;
	}

	public void setSubId(String[] subId) {
		this.subId = subId;
	}

	public String[] getCglCode() {
		return cglCode;
	}

	public void setCglCode(String[] cglCode) {
		this.cglCode = cglCode;
	}

	public String[] getFeeYyyymm() {
		return feeYyyymm;
	}

	public void setFeeYyyymm(String[] feeYyyymm) {
		this.feeYyyymm = feeYyyymm;
	}

	public BigDecimal[] getCglCalAmt() {
		return cglCalAmt;
	}

	public void setCglCalAmt(BigDecimal[] cglCalAmt) {
		this.cglCalAmt = cglCalAmt;
	}

	public BigDecimal[] getCglFeeAmt() {
		return cglFeeAmt;
	}

	public void setCglFeeAmt(BigDecimal[] cglFeeAmt) {
		this.cglFeeAmt = cglFeeAmt;
	}

	public String[] getDzlx() {
		return dzlx;
	}

	public void setDzlx(String[] dzlx) {
		this.dzlx = dzlx;
	}

	public BigDecimal[] getDzed() {
		return dzed;
	}

	public void setDzed(BigDecimal[] dzed) {
		this.dzed = dzed;
	}

	public String[] getDzdw() {
		return dzdw;
	}

	public void setDzdw(String[] dzdw) {
		this.dzdw = dzdw;
	}

	public BigDecimal[] getGlfy() {
		return glfy;
	}

	public void setGlfy(BigDecimal[] glfy) {
		this.glfy = glfy;
	}

	public String[] getDzyf() {
		return dzyf;
	}

	public void setDzyf(String[] dzyf) {
		this.dzyf = dzyf;
	}

	public BigDecimal[] getDzfz() {
		return dzfz;
	}

	public void setDzfz(BigDecimal[] dzfz) {
		this.dzfz = dzfz;
	}

	public String[] getProjId() {
		return projId;
	}

	public void setProjId(String[] projId) {
		this.projId = projId;
	}

	public String[] getFeeDept() {
		return feeDept;
	}

	public void setFeeDept(String[] feeDept) {
		this.feeDept = feeDept;
	}

	public String[] getMatrCode() {
		return matrCode;
	}

	public void setMatrCode(String[] matrCode) {
		this.matrCode = matrCode;
	}

	public String[] getDeviceModelName() {
		return deviceModelName;
	}

	public void setDeviceModelName(String[] deviceModelName) {
		this.deviceModelName = deviceModelName;
	}

	public String[] getReference() {
		return reference;
	}

	public void setReference(String[] reference) {
		this.reference = reference;
	}

	public String[] getSpecial() {
		return special;
	}

	public void setSpecial(String[] special) {
		this.special = special;
	}

	public BigDecimal[] getExecNum() {
		return execNum;
	}

	public void setExecNum(BigDecimal[] execNum) {
		this.execNum = execNum;
	}

	public BigDecimal[] getExecPrice() {
		return execPrice;
	}

	public void setExecPrice(BigDecimal[] execPrice) {
		this.execPrice = execPrice;
	}

	public BigDecimal[] getExecAmt() {
		return execAmt;
	}

	public void setExecAmt(BigDecimal[] execAmt) {
		this.execAmt = execAmt;
	}

	public BigDecimal[] getWarranty() {
		return warranty;
	}

	public void setWarranty(BigDecimal[] warranty) {
		this.warranty = warranty;
	}

	public String[] getProductor() {
		return productor;
	}

	public void setProductor(String[] productor) {
		this.productor = productor;
	}

	public String[] getDevDataFlag() {
		return devDataFlag;
	}

	public void setDevDataFlag(String[] devDataFlag) {
		this.devDataFlag = devDataFlag;
	}

	public List<CntDevice> getDevices() {
		return devices;
	}

	public void setDevices(List<CntDevice> devices) {
		this.devices = devices;
	}

	public List<DzspInfo> getDzspInfos() {
		return dzspInfos;
	}

	public void setDzspInfos(List<DzspInfo> dzspInfos) {
		this.dzspInfos = dzspInfos;
	}

	public List<StageInfo> getStageInfos() {
		return stageInfos;
	}

	public void setStageInfos(List<StageInfo> stageInfos) {
		this.stageInfos = stageInfos;
	}

	public List<CntDevice> getBenefit() {
		return benefit;
	}

	public void setBenefit(List<CntDevice> benefit) {
		this.benefit = benefit;
	}

	public String[] getMontCode() {
		return montCode;
	}

	public void setMontCode(String[] montCode) {
		this.montCode = montCode;
	}

	public String[] getMontName() {
		return montName;
	}

	public void setMontName(String[] montName) {
		this.montName = montName;
	}

	public String[] getAuditMemo() {
		return auditMemo;
	}

	public void setAuditMemo(String[] auditMemo) {
		this.auditMemo = auditMemo;
	}

	public String getHouseKindName() {
		return houseKindName;
	}

	public void setHouseKindName(String houseKindName) {
		this.houseKindName = houseKindName;
	}

}
