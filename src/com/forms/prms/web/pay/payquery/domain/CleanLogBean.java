package com.forms.prms.web.pay.payquery.domain;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 暂收结清LogBean
 * 
 * @author user
 * 
 */
public class CleanLogBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5943573962632274159L;

	private String batchNo;// 批次号
	private BigDecimal seqNo;// 序号
	private String cntNum;// 合同编号
	private String payId;// 付款单号
	private BigDecimal payAmt;// 付款金额
	private String payCancelState;// 发票取消状态 Y/N
	private String payCancelDate;// 发票取消日期
	private String paySeqNo;// 付款流水号
	private String payType;// 付款类型 0：正常付款 1：暂收结清
	private String payTypeName;// 付款类型 0：正常付款 1：暂收结清
	private String operUser;// 操作用户 ERP用户ID或者FMS
	private String operDate;// 操作日期
	private String operTime;// 操作时间
	private String modiUser;// 修改用户 ERP用户ID或者FMS
	private String modiDate;// 修改日期
	private String modiTime;// 修改时间

	private String userId;// 当前登录人的Id
	private String instUser;// 创建人的Id
	private String flag;// 判断是暂收转正常还是正常转暂收
	private String dataFlagPay;// 付款的状态
	private String dataFlag;// 付款的状态

	private int normalPayNum;//正常付款的数量
	
	public String getDataFlag() {
		return dataFlag;
	}

	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}

	public String getDataFlagPay() {
		return dataFlagPay;
	}

	public void setDataFlagPay(String dataFlagPay) {
		this.dataFlagPay = dataFlagPay;
	}

	public int getNormalPayNum() {
		return normalPayNum;
	}

	public void setNormalPayNum(int normalPayNum) {
		this.normalPayNum = normalPayNum;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getPayTypeName() {
		return payTypeName;
	}

	public void setPayTypeName(String payTypeName) {
		this.payTypeName = payTypeName;
	}

	public String getInstUser() {
		return instUser;
	}

	public void setInstUser(String instUser) {
		this.instUser = instUser;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getBatchNo() {
		return batchNo;
	}

	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}

	public BigDecimal getSeqNo() {
		return seqNo;
	}

	public void setSeqNo(BigDecimal seqNo) {
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

	public BigDecimal getPayAmt() {
		return payAmt;
	}

	public void setPayAmt(BigDecimal payAmt) {
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

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
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

	public String getModiUser() {
		return modiUser;
	}

	public void setModiUser(String modiUser) {
		this.modiUser = modiUser;
	}

	public String getModiDate() {
		return modiDate;
	}

	public void setModiDate(String modiDate) {
		this.modiDate = modiDate;
	}

	public String getModiTime() {
		return modiTime;
	}

	public void setModiTime(String modiTime) {
		this.modiTime = modiTime;
	}

}
