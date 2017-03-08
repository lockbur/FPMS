package com.forms.prms.web.contract.initiate.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class Tenancy implements Serializable {

	private static final long serialVersionUID = -5175729996007105790L;

	// 房屋租赁信息
	private String cntNum;// 合同编号
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
	private BigDecimal rent; // 月租金
	private String wdjg; // 网点机构
	private String glbmId; // 管理部门ID
	private String wdjgId; // 网点机构号
	private String wydz; // 物业地址
	private BigDecimal wyglf; // 物业管理费
	private BigDecimal yj; // 押金
	private String autoBankName;// 自助银行名称

	// 历史记录参数
	private BigDecimal versionNo;// 版本号
	private String operType; // 操作类型

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

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
}
