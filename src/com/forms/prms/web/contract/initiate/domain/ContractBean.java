package com.forms.prms.web.contract.initiate.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class ContractBean implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cntNum;// 合同编号 BOC+一级分行机构号+yyyymmdd+六位序号+_责任中心
	private String org1Code;// 所属一级行机构号
	private String psbh;// 评审编号
	private String stockNum;// 集采编号
	private String qbh;// 签文报号
	private String lxlx;// 审批类别 1：电子审批;2：签报立项;3：部内审批；
	private String lxlxName;// 审批类别 1：电子审批;2：签报立项;3：部内审批；
	private BigDecimal lxsl;// 审批数量 （电子审批数量小计）
	private BigDecimal lxje;// 审批金额 （电子审批金额小计）
	private BigDecimal cntAmt;// 合同金额
	private BigDecimal totalNum;// 采购数量
	private String cntType;// 类别 0-资产类 1-费用类
	private String cntTypeName;// 类别 0-资产类 1-费用类
	private String feeType;// 费用类型 0-金额固定、受益期固定 1-受益期固定、合同金额不固定 2-受益期不固定，付款时确认费用
							// 3-宣传费
	private String feeTypeName;// 费用类型名称
	private String feeSubType;// 费用子类型 0-普通费用类 1-房屋租赁类
	private String feeSubTypeName;// 费用子类型名称
	private String feeStartDate;// 合同受益起始日期
	private String feeEndDate;// 合同受益终止日期
	private BigDecimal feeAmt;// 合同金额确认部分
	private BigDecimal feePenalty;// 合同金额约定罚金
	private String signDate;// 签订日期/采购日期
	private String isProvinceBuy;// 省行统购项目 0-是 1-否
	private BigDecimal zbAmt;// 质保金
	private String payDutyCode;// 付款责任中心 录入时为发起人责任中心，接收移交后更新
	private String deliverDutyCode;// 移交后责任中心 录入时为空，提出移交后更新
	private String providerCode;// 供应商
	private String providerName;// 供应商名称
	private String provActNo;// 银行账户编号
	private String provActCurr;// 银行账户币种 RMB
	private String srcPoviderName;// 原始供应商名称 供应商是内部供应商记录所对应的外部供应商名字，可以录入
	private String payTermName;// 付款条件名称
	private String payTerm;// 付款条件 0-合同签订后一次性付款 1-货到验收后一次性付款 2-合同完毕后一次性付款 3-分期付款
	private String stageType;// 分期类型 付款条件选择分期付款才有效 0-按条件 1-按日期 2-按条件
	private String stageTypeName;// 分期付款名称 
	private String memo;// 备注
	private String currency;// 币别 默认为CNY
	private String isOrder;// 是否生成订单 0-是 1-否
	private String dataFlag;// 合同状态 合同录入:10 合同退回:11 合同待确认:12 合同确认完成:20
							// 变更申请中:21 物料复核:23 合同移交中:25 合同终止:30 合同完成:32
							// 合同冻结:35'
	private String dataFlagName;
	private String createDate;// 创建日期
	private String createDept;// 创建责任中心
	private String createDeptName; // 创建责任中心名称
	private String isPrepaidProvision;// 是否预提待摊 0-是 1-否
	private BigDecimal providerTaxRate;// 增值税率
	private BigDecimal providerTax;// 增值税金
	private String isSpec;// 是否专项包
	private String cntNumRelated;//关联合同号
	private String cntName;//合同事项
	
	// 历史记录参数
	private BigDecimal versionNo;// 版本号
	private String operType;// 操作类型 新增 删除 修改
	private String operMemo;// 操作说明 操作类型为“变更”时填写具体变更内容。
	private String operUser;// 操作人员
	private String operDate;// 操作日期 yyyy-mm-dd
	private String operTime;// 操作时间 hh24:mi:ss
	private String operDutyCode;// 操作责任中心
	private String operDutyName;// 操作责任中心
	
 
	private BigDecimal cntAllAmt;
	private BigDecimal cntTaxAmt;
	
	private String[] taxCode;// 税码
	private BigDecimal[] taxRate;// 税率
	private String[] deductFlag;//是否可抵扣
	private BigDecimal[] taxYamt;// 可抵扣sh
	private BigDecimal[] taxNamt;// 不可抵扣税额
	private BigDecimal[] cntTrAmt;// 不可抵扣税额
	
	private String instUser;
	
	public String getInstUser() {
		return instUser;
	}

	public void setInstUser(String instUser) {
		this.instUser = instUser;
	}

	public BigDecimal[] getCntTrAmt() {
		return cntTrAmt;
	}

	public void setCntTrAmt(BigDecimal[] cntTrAmt) {
		this.cntTrAmt = cntTrAmt;
	}

	public String[] getTaxCode() {
		return taxCode;
	}

	public void setTaxCode(String[] taxCode) {
		this.taxCode = taxCode;
	}

	public BigDecimal[] getTaxRate() {
		return taxRate;
	}

	public void setTaxRate(BigDecimal[] taxRate) {
		this.taxRate = taxRate;
	}

	 

	public String[] getDeductFlag() {
		return deductFlag;
	}

	public void setDeductFlag(String[] deductFlag) {
		this.deductFlag = deductFlag;
	}

	public BigDecimal[] getTaxYamt() {
		return taxYamt;
	}

	public void setTaxYamt(BigDecimal[] taxYamt) {
		this.taxYamt = taxYamt;
	}

	public BigDecimal[] getTaxNamt() {
		return taxNamt;
	}

	public void setTaxNamt(BigDecimal[] taxNamt) {
		this.taxNamt = taxNamt;
	}

	 

	public BigDecimal getCntAllAmt() {
		return cntAllAmt;
	}

	public void setCntAllAmt(BigDecimal cntAllAmt) {
		this.cntAllAmt = cntAllAmt;
	}

	public BigDecimal getCntTaxAmt() {
		return cntTaxAmt;
	}

	public void setCntTaxAmt(BigDecimal cntTaxAmt) {
		this.cntTaxAmt = cntTaxAmt;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getPsbh() {
		return psbh;
	}

	public void setPsbh(String psbh) {
		this.psbh = psbh;
	}

	public String getStockNum() {
		return stockNum;
	}

	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}

	public String getQbh() {
		return qbh;
	}

	public void setQbh(String qbh) {
		this.qbh = qbh;
	}

	public String getLxlx() {
		return lxlx;
	}

	public void setLxlx(String lxlx) {
		this.lxlx = lxlx;
	}

	public BigDecimal getLxsl() {
		return lxsl;
	}

	public void setLxsl(BigDecimal lxsl) {
		this.lxsl = lxsl;
	}

	public BigDecimal getLxje() {
		return lxje;
	}

	public void setLxje(BigDecimal lxje) {
		this.lxje = lxje;
	}

	public BigDecimal getCntAmt() {
		return cntAmt;
	}

	public void setCntAmt(BigDecimal cntAmt) {
		this.cntAmt = cntAmt;
	}

	public BigDecimal getTotalNum() {
		return totalNum;
	}

	public void setTotalNum(BigDecimal totalNum) {
		this.totalNum = totalNum;
	}

	public String getCntType() {
		return cntType;
	}

	public void setCntType(String cntType) {
		this.cntType = cntType;
	}

	public String getFeeType() {
		return feeType;
	}

	public void setFeeType(String feeType) {
		this.feeType = feeType;
	}

	public String getFeeSubType() {
		return feeSubType;
	}

	public void setFeeSubType(String feeSubType) {
		this.feeSubType = feeSubType;
	}

	public String getFeeStartDate() {
		return feeStartDate;
	}

	public void setFeeStartDate(String feeStartDate) {
		this.feeStartDate = feeStartDate;
	}

	public String getFeeEndDate() {
		return feeEndDate;
	}

	public void setFeeEndDate(String feeEndDate) {
		this.feeEndDate = feeEndDate;
	}

	public BigDecimal getFeeAmt() {
		return feeAmt;
	}

	public void setFeeAmt(BigDecimal feeAmt) {
		this.feeAmt = feeAmt;
	}

	public BigDecimal getFeePenalty() {
		return feePenalty;
	}

	public void setFeePenalty(BigDecimal feePenalty) {
		this.feePenalty = feePenalty;
	}

	public String getSignDate() {
		return signDate;
	}

	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}

	public String getIsProvinceBuy() {
		return isProvinceBuy;
	}

	public void setIsProvinceBuy(String isProvinceBuy) {
		this.isProvinceBuy = isProvinceBuy;
	}

	public BigDecimal getZbAmt() {
		return zbAmt;
	}

	public void setZbAmt(BigDecimal zbAmt) {
		this.zbAmt = zbAmt;
	}

	public String getPayDutyCode() {
		return payDutyCode;
	}

	public void setPayDutyCode(String payDutyCode) {
		this.payDutyCode = payDutyCode;
	}

	public String getDeliverDutyCode() {
		return deliverDutyCode;
	}

	public void setDeliverDutyCode(String deliverDutyCode) {
		this.deliverDutyCode = deliverDutyCode;
	}

	public String getProviderCode() {
		return providerCode;
	}

	public void setProviderCode(String providerCode) {
		this.providerCode = providerCode;
	}

	public String getProvActNo() {
		return provActNo;
	}

	public void setProvActNo(String provActNo) {
		this.provActNo = provActNo;
	}

	public String getProvActCurr() {
		return provActCurr;
	}

	public void setProvActCurr(String provActCurr) {
		this.provActCurr = provActCurr;
	}

	public String getSrcPoviderName() {
		return srcPoviderName;
	}

	public void setSrcPoviderName(String srcPoviderName) {
		this.srcPoviderName = srcPoviderName;
	}

	public String getPayTerm() {
		return payTerm;
	}

	public void setPayTerm(String payTerm) {
		this.payTerm = payTerm;
	}

	public String getStageType() {
		return stageType;
	}

	public void setStageType(String stageType) {
		this.stageType = stageType;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getCurrency() {
		return currency;
	}

	public void setCurrency(String currency) {
		this.currency = currency;
	}

	public String getIsOrder() {
		return isOrder;
	}

	public void setIsOrder(String isOrder) {
		this.isOrder = isOrder;
	}

	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateDept() {
		return createDept;
	}

	public void setCreateDept(String createDept) {
		this.createDept = createDept;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}

	public String getOperMemo() {
		return operMemo;
	}

	public void setOperMemo(String operMemo) {
		this.operMemo = operMemo;
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

	public String getOperDutyCode() {
		return operDutyCode;
	}

	public void setOperDutyCode(String operDutyCode) {
		this.operDutyCode = operDutyCode;
	}

	public String getOperDutyName() {
		return operDutyName;
	}

	public void setOperDutyName(String operDutyName) {
		this.operDutyName = operDutyName;
	}

	public String getIsPrepaidProvision() {
		return isPrepaidProvision;
	}

	public void setIsPrepaidProvision(String isPrepaidProvision) {
		this.isPrepaidProvision = isPrepaidProvision;
	}

	public String getIsSpec() {
		return isSpec;
	}

	public void setIsSpec(String isSpec) {
		this.isSpec = isSpec;
	}

	public BigDecimal getProviderTaxRate() {
		return providerTaxRate;
	}

	public void setProviderTaxRate(BigDecimal providerTaxRate) {
		this.providerTaxRate = providerTaxRate;
	}

	public BigDecimal getProviderTax() {
		return providerTax;
	}

	public void setProviderTax(BigDecimal providerTax) {
		this.providerTax = providerTax;
	}

	public String getCntNumRelated() {
		return cntNumRelated;
	}

	public void setCntNumRelated(String cntNumRelated) {
		this.cntNumRelated = cntNumRelated;
	}

	public String getCntName() {
		return cntName;
	}

	public void setCntName(String cntName) {
		this.cntName = cntName;
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

	public String getStageTypeName() {
		return stageTypeName;
	}

	public void setStageTypeName(String stageTypeName) {
		this.stageTypeName = stageTypeName;
	}

	public String getPayTermName() {
		return payTermName;
	}

	public void setPayTermName(String payTermName) {
		this.payTermName = payTermName;
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

	public String getCreateDeptName() {
		return createDeptName;
	}

	public void setCreateDeptName(String createDeptName) {
		this.createDeptName = createDeptName;
	}

}
