package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain;

import java.math.BigDecimal;

/**
 * Title:			TdPayDeviceBean
 * Description:		JavaBean类，用于保存【数据迁移】模块中
 * 								[普通付款数据]Excel模板--Sheet3(预付款合同采购设备)+Sheet4(正常付款合同采购设备)的导入数据
 * 								注意：Sheet3和Sheet4导入数据库表的区别在于表中字段[payType]:		付款类型 0:预付款核销(Sheet3)，1：正常付款(Sheet4)		根据该字段决定表中数据是来自Sheet3/Sheet4的
 * 								对应表：UPLOAD_TD_PAY_DEVICE
 * Copyright: 		formssi
 * @author： 		HQQ
 * @project： 		ERP
 * @date： 			2015-06-26
 * @version： 		1.0
 */
public class TdPayDeviceBean {
	
	private String cntNum;				//合同号
	private String payId;				//正常付款单号
	private String matrCode;			//物料编码
	private String matrName;			//物料名称
	private BigDecimal subInvoiceAmt;	//分配发票金额
	private String ivrowMemo;			//发票行说明
	
	//6-30新增导入批次公共字段
	private String batchNo;				//批次号
	private String dataType;			//数据类型(对应具体模板具体Sheet，如0101)
	private String uploadType;			//上传类型(对应Excel模板编号)
	private String rowNo;				//Sheet中数据的行号
	private String orgId;				//导入操作用户的一级行机构编号
	
	private String payType;				//付款类型(0:预付款核销物料、1:正常付款物料)
	private String table;				//插入表(根据付款类型payType决定，用于区分[付款数据]模板中的Sheet4和Sheet5    UPLOAD_TD_PAY_ADVANCE_DEVICE:预付款核销物料表，UPLOAD_TD_PAY_ADVANCE_DEVICE：正常付款物料
	
	public String getPayType() {
		return payType;
	}
	public void setPayType(String payType) {
		this.payType = payType;
	}
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getTable() {
		return table;
	}
	public void setTable(String table) {
		this.table = table;
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
	public BigDecimal getSubInvoiceAmt() {
		return subInvoiceAmt;
	}
	public void setSubInvoiceAmt(BigDecimal subInvoiceAmt) {
		this.subInvoiceAmt = subInvoiceAmt;
	}
	public String getIvrowMemo() {
		return ivrowMemo;
	}
	public void setIvrowMemo(String ivrowMemo) {
		this.ivrowMemo = ivrowMemo;
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
