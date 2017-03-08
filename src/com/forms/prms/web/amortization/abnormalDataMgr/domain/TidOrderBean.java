package com.forms.prms.web.amortization.abnormalDataMgr.domain;

/**
 * Title:		TidOrderBean
 * Description:	javaBean , 对应表TID_ORDER字段
 * Copyright: 	formssi
 * @author： 	HQQ
 * @project：	ERP
 * @date： 		2015-04-08
 * @version： 	1.0
 */
public class TidOrderBean {
	
	private String batchNo;				//批次号
	private String seqNo;				//序号
	private String poNumber;			//PO单号
	private String orderUser;			//采购员工号
	private String stockNum;			//集采编号
	private String cntNum;				//合同或协议编号
	private String orderId;				//订单号
	private String status;				//订单状态
	private String rowSeqno;			//订单行号
	private String dataFlag;			//数据状态标识
	private String useFlag;				//数据异常标识
	
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getSeqNo() {
		return seqNo;
	}
	public void setSeqNo(String seqNo) {
		this.seqNo = seqNo;
	}
	public String getPoNumber() {
		return poNumber;
	}
	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
	}
	public String getOrderUser() {
		return orderUser;
	}
	public void setOrderUser(String orderUser) {
		this.orderUser = orderUser;
	}
	public String getStockNum() {
		return stockNum;
	}
	public void setStockNum(String stockNum) {
		this.stockNum = stockNum;
	}
	public String getCntNum() {
		return cntNum;
	}
	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getRowSeqno() {
		return rowSeqno;
	}
	public void setRowSeqno(String rowSeqno) {
		this.rowSeqno = rowSeqno;
	}
	public String getDataFlag() {
		return dataFlag;
	}
	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}
	public String getUseFlag() {
		return useFlag;
	}
	public void setUseFlag(String useFlag) {
		this.useFlag = useFlag;
	}
	
}
