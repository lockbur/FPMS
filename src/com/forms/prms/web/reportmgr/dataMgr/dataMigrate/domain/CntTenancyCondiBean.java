package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain;

import java.math.BigDecimal;

//【数据迁移】-合同信息Excel-租金递增条件Bean
public class CntTenancyCondiBean {
	private String cntNum;			//合同编号
	private String fromDate;		//租金开始日期
	private String toDate;			//租金结束日期
	
	private String dzlx;			//租金付款类型		1-固定为  2-上浮 3-下调
	
	private BigDecimal dzed;		//租金固定金额值
	private String fdbl;			//租金浮动比例值	
	private BigDecimal glfy;		//管理费
	
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
	public BigDecimal getDzed() {
		return dzed;
	}
	public void setDzed(BigDecimal dzed) {
		this.dzed = dzed;
	}
	public String getFdbl() {
		return fdbl;
	}
	public void setFdbl(String fdbl) {
		this.fdbl = fdbl;
	}
	public BigDecimal getGlfy() {
		return glfy;
	}
	public void setGlfy(BigDecimal glfy) {
		this.glfy = glfy;
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
