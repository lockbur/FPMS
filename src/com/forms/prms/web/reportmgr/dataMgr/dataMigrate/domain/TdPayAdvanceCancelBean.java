package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain;

import java.math.BigDecimal;

/**
 * Title:			TdPayAdvanceCancelBean
 * Description:		JavaBean类，用于保存【数据迁移】模块中
 * 								[普通付款数据]Excel模板--Sheet2(预付款核销信息)的导入数据
 * 								对应表：UPLOAD_TD_PAY_ADVANCE
 * Copyright: 		formssi
 * @author： 		HQQ
 * @project： 		ERP
 * @date： 			2015-06-26
 * @version： 		1.0
 */
public class TdPayAdvanceCancelBean {
	private String cntNum; 				//合同号
	private String normalPayId;			//普通付款单号
	private String advancePayId;		//预付款单号
	private BigDecimal cancelAmt;		//本次核销金额(元)
	
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
	public String getNormalPayId() {
		return normalPayId;
	}
	public void setNormalPayId(String normalPayId) {
		this.normalPayId = normalPayId;
	}
	public String getAdvancePayId() {
		return advancePayId;
	}
	public void setAdvancePayId(String advancePayId) {
		this.advancePayId = advancePayId;
	}
	public BigDecimal getCancelAmt() {
		return cancelAmt;
	}
	public void setCancelAmt(BigDecimal cancelAmt) {
		this.cancelAmt = cancelAmt;
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
