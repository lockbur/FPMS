package com.forms.prms.web.amortization.fmsMgr.domain;

import java.io.Serializable;
import java.math.BigDecimal;

public class FmsMgr implements Serializable {

	private static final long serialVersionUID = 1L;

	private BigDecimal payOuCnt; // 正常支付ou总数
	private BigDecimal payCnt; // 正常支付总笔数
	private BigDecimal payNotSendCnt;//正常付款未发送笔数
	private BigDecimal payNotSendOuCnt;//正常付款未发送笔数
	private BigDecimal paySumAmt; // 正常支付总金额

	private BigDecimal advOuCnt; // 预付款核销ou总数
	private BigDecimal advCnt; // 预付款核销总笔数
	private BigDecimal advNotSendCnt;//预付款核销未发送笔数
	private BigDecimal advNotSendOuCnt;//预付款核销未发送笔数
	private BigDecimal advSumAmt; // 预付款核销总金额

	private BigDecimal orderOuCnt; // 订单ou总数
	private BigDecimal orderCnt; // 订单总笔数
	private BigDecimal ordNotSendCnt;//订单未发送笔数
	private BigDecimal ordNotSendOuCnt;//订单未发送笔数
	private BigDecimal orderSumAmt; // 订单总金额

	private String filePath; // 下载文件路径
	private String fileName; // 文件名

	private String invoiceType;// 发票类型
	private String paySumStr;// 总金额字符串
	
	public BigDecimal getPayNotSendOuCnt() {
		return payNotSendOuCnt;
	}

	public void setPayNotSendOuCnt(BigDecimal payNotSendOuCnt) {
		this.payNotSendOuCnt = payNotSendOuCnt;
	}

	public BigDecimal getAdvNotSendOuCnt() {
		return advNotSendOuCnt;
	}

	public void setAdvNotSendOuCnt(BigDecimal advNotSendOuCnt) {
		this.advNotSendOuCnt = advNotSendOuCnt;
	}

	public BigDecimal getOrdNotSendOuCnt() {
		return ordNotSendOuCnt;
	}

	public void setOrdNotSendOuCnt(BigDecimal ordNotSendOuCnt) {
		this.ordNotSendOuCnt = ordNotSendOuCnt;
	}

	public BigDecimal getPayNotSendCnt() {
		return payNotSendCnt;
	}

	public void setPayNotSendCnt(BigDecimal payNotSendCnt) {
		this.payNotSendCnt = payNotSendCnt;
	}

	public BigDecimal getAdvNotSendCnt() {
		return advNotSendCnt;
	}

	public void setAdvNotSendCnt(BigDecimal advNotSendCnt) {
		this.advNotSendCnt = advNotSendCnt;
	}

	public BigDecimal getOrdNotSendCnt() {
		return ordNotSendCnt;
	}

	public void setOrdNotSendCnt(BigDecimal ordNotSendCnt) {
		this.ordNotSendCnt = ordNotSendCnt;
	}

	public String getPaySumStr() {
		return paySumStr;
	}

	public void setPaySumStr(String paySumStr) {
		this.paySumStr = paySumStr;
	}

	public String getInvoiceType() {
		return invoiceType;
	}

	public void setInvoiceType(String invoiceType) {
		this.invoiceType = invoiceType;
	}

	public BigDecimal getPayOuCnt() {
		return payOuCnt;
	}

	public void setPayOuCnt(BigDecimal payOuCnt) {
		this.payOuCnt = payOuCnt;
	}

	public BigDecimal getPayCnt() {
		return payCnt;
	}

	public void setPayCnt(BigDecimal payCnt) {
		this.payCnt = payCnt;
	}

	public BigDecimal getPaySumAmt() {
		return paySumAmt;
	}

	public void setPaySumAmt(BigDecimal paySumAmt) {
		this.paySumAmt = paySumAmt;
	}

	public BigDecimal getAdvOuCnt() {
		return advOuCnt;
	}

	public void setAdvOuCnt(BigDecimal advOuCnt) {
		this.advOuCnt = advOuCnt;
	}

	public BigDecimal getAdvCnt() {
		return advCnt;
	}

	public void setAdvCnt(BigDecimal advCnt) {
		this.advCnt = advCnt;
	}

	public BigDecimal getAdvSumAmt() {
		return advSumAmt;
	}

	public void setAdvSumAmt(BigDecimal advSumAmt) {
		this.advSumAmt = advSumAmt;
	}

	public BigDecimal getOrderOuCnt() {
		return orderOuCnt;
	}

	public void setOrderOuCnt(BigDecimal orderOuCnt) {
		this.orderOuCnt = orderOuCnt;
	}

	public BigDecimal getOrderCnt() {
		return orderCnt;
	}

	public void setOrderCnt(BigDecimal orderCnt) {
		this.orderCnt = orderCnt;
	}

	public BigDecimal getOrderSumAmt() {
		return orderSumAmt;
	}

	public void setOrderSumAmt(BigDecimal orderSumAmt) {
		this.orderSumAmt = orderSumAmt;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

}
