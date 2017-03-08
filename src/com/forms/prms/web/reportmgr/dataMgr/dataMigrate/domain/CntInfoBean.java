package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain;

import java.math.BigDecimal;

public class CntInfoBean {
	
	private String cntNum;				//合同编号
	private String cntName;				//合同事项
	private String payDutyCode;			//付款责任中心编号
	private String payDutyName;			//责任中心名称
	private String signDate;			//签订日期
//	private String providerCode;		//供应商编号【暂不用】
	private String providerName;		//供应商名称
	private String provActNo;			//供应商账号
	private String provActCurr;			//供应商银行币别
	private BigDecimal cntAmt;			//合同金额
	private BigDecimal zbAmt;			//合同质保金(%)
	private BigDecimal lxje;			//审批金额
	private BigDecimal lxsl;			//审批数量
	private String cntType;				//合同类型
	private String isSpec;				//是否专项包		0-是 1-否
	private String isProvinceBuy;		//省行统构项目	0-是 1-否
	private String feeType;				//费用类型		0-金额固定、受益期固定 1-受益期固定、合同金额不固定 2-受益期不固定，付款时确认费用 3-宣传费
	private String feeSubType;			//费用子类型		0-普通费用类 1-房屋租赁类
	private String feeStartDate;		//合同受益起始日期
	private String feeEndDate;			//合同受益终止日期
	private String isOrder;				//是否订单	0-是 1-否
	private BigDecimal feeAmt;			//合同金额确定部分
	private BigDecimal feePenalty;		//合同金额约定罚金
	private String stockNum;			//采集编号
	private String psbh;				//评审编号
	private String lxlx;				//审批类别	1：电子审批	2：签报立项	3：部内审批
	private BigDecimal providerTaxRate;	//增值税率(供应商税率)
	private BigDecimal providerTax;		//增值税金(供应商税)
	private String cntNumRelated;		//关联合同号
	private String payTerm;				//合同付款条件	 0-合同签订后一次性付款 1-货到验收后一次性付款 2-合同完毕后一次性付款 3-分期付款
	private String stageType;			//分期付款类型
	private String memo;				//合同备注
	private String jf;					//甲方名称
//	private String jfId;				//甲方ID
	private String yfId;				//乙方ID
	private String yf;					//乙方名称
	private String wydz;				//物业地址(地址说明)
	private String wdjgId;				//网点机构号
	private String wdjgName;			//网点机构名称
	private String autoBankName;		//自助银行名称
	private BigDecimal area;			//租凭面积
	private String houseKindId;			//房产性质
	private BigDecimal wyglf;			//物业管理费
	private BigDecimal yj;				//押金
	private String beginDate;			//执行开始日期
	private String endDate;				//执行结束日期
	private String fzRemark;			//房租备注
	
	//6-30新增公共字段
	private String batchNo;				//批次号
	private String rowNo;				//Sheet中数据的行号
	private String uploadType;			//上传类型(对应Excel模板编号)
	private String dataType;			//数据类型(对应具体模板具体Sheet，如0101)
	private String orgId;				//导入操作用户的一级行机构编号
	
	
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getWydz() {
		return wydz;
	}
	public void setWydz(String wydz) {
		this.wydz = wydz;
	}
	public String getCntName() {
		return cntName;
	}
	public void setCntName(String cntName) {
		this.cntName = cntName;
	}
	public String getPayDutyCode() {
		return payDutyCode;
	}
	public void setPayDutyCode(String payDutyCode) {
		this.payDutyCode = payDutyCode;
	}
	public String getPayDutyName() {
		return payDutyName;
	}
	public void setPayDutyName(String payDutyName) {
		this.payDutyName = payDutyName;
	}
	public String getSignDate() {
		return signDate;
	}
	public void setSignDate(String signDate) {
		this.signDate = signDate;
	}
//	public String getProviderCode() {
//		return providerCode;
//	}
//	public void setProviderCode(String providerCode) {
//		this.providerCode = providerCode;
//	}
//	public String getJfId() {
//		return jfId;
//	}
//	public void setJfId(String jfId) {
//		this.jfId = jfId;
//	}
	public String getProviderName() {
		return providerName;
	}
	public void setProviderName(String providerName) {
		this.providerName = providerName.trim();
	}
	public BigDecimal getCntAmt() {
		return cntAmt;
	}
	public void setCntAmt(BigDecimal cntAmt) {
		this.cntAmt = cntAmt;
	}
	public BigDecimal getZbAmt() {
		return zbAmt;
	}
	public void setZbAmt(BigDecimal zbAmt) {
		this.zbAmt = zbAmt;
	}
	public BigDecimal getLxje() {
		return lxje;
	}
	public void setLxje(BigDecimal lxje) {
		this.lxje = lxje;
	}
	public BigDecimal getLxsl() {
		return lxsl;
	}
	public void setLxsl(BigDecimal lxsl) {
		this.lxsl = lxsl;
	}
	public String getCntType() {
		if("0".equals(cntType)){
			return "0";
		}else if("1".equals(cntType)){
			return "1";
		}else{
			return cntType;
		}
	}
	public void setCntType(String cntType) {
		if("资产类".equals(cntType)){
			this.cntType = "0";
		}else if("费用类".equals(cntType)){
			this.cntType = "1";
		}else{
			this.cntType = cntType;
		}
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
	public String getStockNum() {
		return stockNum;
	}
	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}
	public String getPsbh() {
		return psbh;
	}
	public void setPsbh(String psbh) {
		this.psbh = psbh;
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
	
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getJf() {
		return jf;
	}
	public void setJf(String jf) {
		this.jf = jf;
	}
	public String getYfId() {
		return yfId;
	}
	public void setYfId(String yfId) {
		this.yfId = yfId;
	}
	public String getYf() {
		return yf;
	}
	public void setYf(String yf) {
		this.yf = yf;
	}
	public String getWdjgId() {
		return wdjgId;
	}
	public void setWdjgId(String wdjgId) {
		this.wdjgId = wdjgId;
	}
	public String getWdjgName() {
		return wdjgName;
	}
	public void setWdjgName(String wdjgName) {
		this.wdjgName = wdjgName;
	}
	public String getAutoBankName() {
		return autoBankName;
	}
	public void setAutoBankName(String autoBankName) {
		this.autoBankName = autoBankName;
	}
	public BigDecimal getArea() {
		return area;
	}
	public void setArea(BigDecimal area) {
		this.area = area;
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
	public String getFzRemark() {
		return fzRemark;
	}
	public void setFzRemark(String fzRemark) {
		this.fzRemark = fzRemark;
	}

	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getDataType() {
		return dataType;
	}
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	public String getUploadType() {
		return uploadType;
	}
	public void setUploadType(String uploadType) {
		this.uploadType = uploadType;
	}
	public String getRowNo() {
		return rowNo;
	}
	public void setRowNo(String rowNo) {
		this.rowNo = rowNo;
	}
	public String getOrgId() {
		return orgId;
	}
	public void setOrgId(String orgId) {
		this.orgId = orgId;
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
	
	//是否专项
	public String getIsSpec() {
		if("是".equals(isSpec)){
			return "0";
		}else if("否".equals(isSpec)){
			//否
			return "1";
		}else{
			return isSpec;
		}
	}
	public void setIsSpec(String isSpec) {
		if("0".equals(isSpec)){
			this.isSpec = "是";
		}else if("1".equals(isSpec)){
			this.isSpec = "否";
		}else{
			this.isSpec = isSpec;
		}
	}
	
	//是否省行统购
	public String getIsProvinceBuy() {
		if("是".equals( isProvinceBuy )){
			return "0";
		}else if("否".equals(isProvinceBuy)){
			return "1";
		}else{
			return isProvinceBuy;
		}
	}
	public void setIsProvinceBuy(String isProvinceBuy) {
		if("0".equals(isProvinceBuy)){
			this.isProvinceBuy = "是";
		}else if("1".equals(isProvinceBuy)){
			this.isProvinceBuy = "否";
		}else{
			this.isProvinceBuy = isProvinceBuy;
		}
	}
	
	//是否订单
	public String getIsOrder() {
		if("是".equals(isOrder)){
			return "0";
		}else if("否".equals(isOrder)){
			return "1";
		}else{
			return isOrder;
		}
	}
	public void setIsOrder(String isOrder) {
		if("0".equals(isOrder)){
			this.isOrder = "是";
		}else if("1".equals(isOrder)){
			this.isOrder = "否";
		}else{
			this.isOrder = isOrder;
		}
	}
	
	
	//费用类型
	public String getFeeType() {
		if("金额固定、受益期固定".equals(feeType)){
			return "0";
		}else if("受益期固定、合同金额不固定".equals(feeType)){
			return "1";
		}else if("受益期不固定，付款时确认费用".equals(feeType)){
			return "2";
		}else if("宣传费".equals(feeType)){
			return "3";
		}else{
			return feeType;
		}
	}
	public void setFeeType(String feeType) {
		if("0".equals(feeType)){
			this.feeType = "金额固定、受益期固定";
		}else if("1".equals(feeType)){
			this.feeType = "受益期固定、合同金额不固定";
		}else if("2".equals(feeType)){
			this.feeType = "受益期不固定，付款时确认费用";
		}else if("3".equals(feeType)){
			this.feeType = "宣传费";
		}else{
			this.feeType = feeType;
		}
	}
	
	//费用子类型
	public String getFeeSubType() {
		if("普通费用类".equals(feeSubType)){
			return "0";
		}else if("房屋租赁类".equals(feeSubType)){
			return "1";
		}else{
			return feeSubType;
		}
	}
	public void setFeeSubType(String feeSubType) {
		if("0".equals(feeSubType)){
			this.feeSubType = "普通费用类";
		}else if("1".equals(feeSubType)){
			this.feeSubType = "房屋租赁类";
		}else{
			this.feeSubType = feeSubType;
		}
	}
	
	//审批类别
	public String getLxlx() {
		if("电子审批".equals(lxlx)){
			return "0";
		}else if("签报立项".equals(lxlx)){
			return "1";
		}else if("部内审批".equals(lxlx)){
			return "2";
		}else{
			return lxlx;
		}
	}
	public void setLxlx(String lxlx) {
		if("0".equals(lxlx)){
			this.lxlx = "电子审批";
		}else if("1".equals(lxlx)){
			this.lxlx = "签报立项";
		}else if("2".equals(lxlx)){
			this.lxlx = "部内审批";
		}else{
			this.lxlx = lxlx;
		}
	}
	
	//付款条件 0-合同签订后一次性付款 1-货到验收后一次性付款 2-合同完毕后一次性付款 3-分期付款
	public String getPayTerm() {
		if("合同签订后一次性付款".equals(payTerm)){
			return "0";
		}else if("货到验收后一次性付款".equals(payTerm)){
			return "1";
		}else if("合同完毕后一次性付款".equals(payTerm)){
			return "2";
		}else if("分期付款".equals(payTerm)){
			return "3";
		}else{
			return payTerm;
		}
	}
	public void setPayTerm(String payTerm) {
		if("0".equals(payTerm)){
			this.payTerm = "合同签订后一次性付款";
		}else if("1".equals(payTerm)){
			this.payTerm = "货到验收后一次性付款";
		}else if("2".equals(payTerm)){
			this.payTerm = "合同完毕后一次性付款";
		}else if("3".equals(payTerm)){
			this.payTerm = "分期付款";
		}else{
			this.payTerm = payTerm;
		}
	}
	
	//分期付款类型(1按进度-2按条件-3按日期)
	public String getStageType() {
		if("按进度".equals(stageType)){
			return "0";
		}else if("按日期".equals(stageType)){
			return "1";
		}else if("按条件".equals(stageType)){
			return "2";
		}else{
			return stageType;
		}
	}
	public void setStageType(String stageType) {
		if("0".equals(stageType)){
			this.stageType = "按进度";
		}else if("1".equals(stageType)){
			this.stageType = "按日期";
		}else if("2".equals(stageType)){
			this.stageType = "按条件";
		}else{
			this.stageType = stageType;
		}
	}
	
	//房产项目性质处理： 0-网点营业用房	1-自助银行用房
	public String getHouseKindId() {
		if("网点营业用房".equals(houseKindId)){
			return "0";
		}else if("自助银行用房".equals(houseKindId)){
			return "1";
		}else{
			return houseKindId;
		}
	}
	public void setHouseKindId(String houseKindId) {
		if("0".equals(houseKindId)){
			this.houseKindId = "网点营业用房";
		}else if("0".equals(houseKindId)){
			this.houseKindId = "自助银行用房";
		}else{
			this.houseKindId = houseKindId;
		}
	}
	
}
