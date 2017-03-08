package com.forms.prms.web.amortization.abnormalDataMgr.domain;

/**
 * Title:		TidApPayBean
 * Description:	javaBean , 对应表TID_AP_PAY
 * Copyright: 	formssi
 * @author： 	HQQ
 * @project： 	ERP
 * @date： 		2015-04-08
 * @version： 	1.0
 */
public class TidApPayBean {

	private String batchNo;				//批次号
	private String seqNo;				//序号
	private String cntNum;				//合同编号
	private String payId;				//付款单号
	private String payAmt;				//付款金额
	private String payCancelState;		//发票取消状态
	private String payCancelDate;		//发票取消日期
	private String paySeqNo;			//付款流水号
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
	public String getPayAmt() {
		return payAmt;
	}
	public void setPayAmt(String payAmt) {
		this.payAmt = payAmt;
	}
	public String getPayCancelState() {
		return payCancelState;
	}
	public void setPayCancelState(String payCancelState) {
		this.payCancelState = payCancelState;
	}
	public String getPayCancelDate() {
		return payCancelDate;
	}
	public void setPayCancelDate(String payCancelDate) {
		this.payCancelDate = payCancelDate;
	}
	public String getPaySeqNo() {
		return paySeqNo;
	}
	public void setPaySeqNo(String paySeqNo) {
		this.paySeqNo = paySeqNo;
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
