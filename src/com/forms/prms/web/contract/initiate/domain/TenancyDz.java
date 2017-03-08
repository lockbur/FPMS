package com.forms.prms.web.contract.initiate.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class TenancyDz implements Serializable {

	private static final long serialVersionUID = 1L;

	private String cntNum;// 合同号
	private String subId;// 子序列号
	private String fromDate;// 递增起始日期
	private String toDate;// 递增结束日期
	private String dzlx;// 递增类型
	private String dzlxName;// 递增类型
	private BigDecimal dzed;// 递增额度
	private String dzdw;// 递增单位
	private String dzdwName;// 递增单位
	private String dzyf;// 递增月份
	private BigDecimal dzfz;// 递增房租
	private BigDecimal glfy;// 管理费用及其他
	private BigDecimal dzhje;// 递增后月租
	private BigDecimal dzhhj;// 递增后每月合计费用

	private String matrCodeFz;// 物料编码
	private String matrNameFz;// 物料编码
	private BigDecimal cntAmtTr;// 合同总金额
	private BigDecimal execAmtTr;// 不含税金额
	private BigDecimal taxAmtTr;// 税额

	// 历史记录参数
	private BigDecimal versionNo;// 版本号
	private String operType; // 操作类型

	public String getMatrNameFz() {
		return matrNameFz;
	}

	public void setMatrNameFz(String matrNameFz) {
		this.matrNameFz = matrNameFz;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public String getSubId() {
		return subId;
	}

	public void setSubId(String subId) {
		this.subId = subId;
	}

	public String getFromDate() {
		return fromDate;
	}

	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}

	public String getToDate() {
		return toDate;
	}

	public void setToDate(String toDate) {
		this.toDate = toDate;
	}

	public String getDzlx() {
		return dzlx;
	}

	public void setDzlx(String dzlx) {
		this.dzlx = dzlx;
	}

	public String getDzlxName() {
		return dzlxName;
	}

	public void setDzlxName(String dzlxName) {
		this.dzlxName = dzlxName;
	}

	public BigDecimal getDzed() {
		return dzed;
	}

	public void setDzed(BigDecimal dzed) {
		this.dzed = dzed;
	}

	public String getDzdw() {
		return dzdw;
	}

	public void setDzdw(String dzdw) {
		this.dzdw = dzdw;
	}

	public String getDzdwName() {
		return dzdwName;
	}

	public void setDzdwName(String dzdwName) {
		this.dzdwName = dzdwName;
	}

	public String getDzyf() {
		return dzyf;
	}

	public void setDzyf(String dzyf) {
		this.dzyf = dzyf;
	}

	public BigDecimal getDzfz() {
		return dzfz;
	}

	public void setDzfz(BigDecimal dzfz) {
		this.dzfz = dzfz;
	}

	public BigDecimal getGlfy() {
		return glfy;
	}

	public void setGlfy(BigDecimal glfy) {
		this.glfy = glfy;
	}

	public BigDecimal getDzhje() {
		return dzhje;
	}

	public void setDzhje(BigDecimal dzhje) {
		this.dzhje = dzhje;
	}

	public BigDecimal getDzhhj() {
		return dzhhj;
	}

	public void setDzhhj(BigDecimal dzhhj) {
		this.dzhhj = dzhhj;
	}

	public String getMatrCodeFz() {
		return matrCodeFz;
	}

	public void setMatrCodeFz(String matrCodeFz) {
		this.matrCodeFz = matrCodeFz;
	}


	public BigDecimal getCntAmtTr() {
		return cntAmtTr;
	}

	public void setCntAmtTr(BigDecimal cntAmtTr) {
		this.cntAmtTr = cntAmtTr;
	}

	public BigDecimal getExecAmtTr() {
		return execAmtTr;
	}

	public void setExecAmtTr(BigDecimal execAmtTr) {
		this.execAmtTr = execAmtTr;
	}

	public BigDecimal getTaxAmtTr() {
		return taxAmtTr;
	}

	public void setTaxAmtTr(BigDecimal taxAmtTr) {
		this.taxAmtTr = taxAmtTr;
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
}
