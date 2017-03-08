package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain;

import java.math.BigDecimal;

//【数据迁移】-合同分期付款Bean
public class CntFqfkBean {
	
	private String cntNum;			//合同编号
	
	private String jdDate;			//付款日期
	private BigDecimal jdtj;		//进度(付款比例)
	private BigDecimal jdzf;		//付款金额
	
	private BigDecimal dhzf;		//到货付款
	private BigDecimal yszf;		//验收付款
	private BigDecimal jszf;		//结算付款
	
	private String rqtj;			//合同签订天数
	private BigDecimal rqzf;		//分期付款金额
	
	//6-30新增公共字段
	private String batchNo;			//批次号
	private String dataType;		//数据类型(对应具体模板具体Sheet，如0101)
	private String uploadType;		//上传类型(对应Excel模板编号)
	private String rowNo;			//Sheet中数据的行号
	private String orgId;			//导入操作用户的一级行机构编号
	
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getRqtj() {
		return rqtj;
	}
	public void setRqtj(String rqtj) {
		this.rqtj = rqtj;
	}
	public BigDecimal getJdtj() {
		return jdtj;
	}
	public void setJdtj(BigDecimal jdtj) {
		this.jdtj = jdtj;
	}
	public BigDecimal getJdzf() {
		return jdzf;
	}
	public void setJdzf(BigDecimal jdzf) {
		this.jdzf = jdzf;
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
	public String getJdDate() {
		return jdDate;
	}
	public void setJdDate(String jdDate) {
		this.jdDate = jdDate;
	}
	public BigDecimal getRqzf() {
		return rqzf;
	}
	public void setRqzf(BigDecimal rqzf) {
		this.rqzf = rqzf;
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
	
}
