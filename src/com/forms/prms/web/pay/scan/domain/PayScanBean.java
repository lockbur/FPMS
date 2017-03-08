package com.forms.prms.web.pay.scan.domain;

import com.forms.prms.web.icms.domain.ScanBean;

public class PayScanBean extends ScanBean {
	private String batchNo;
	private String mainCnt;
	private String attachCnt;
	private String attachCnts;
	
	private String mainCntOk;
	private String attachCntOk;
	
	private String payId;
	private String payIds;
	private String innerId;
	private String dataFlag;
	private String memo;
	private String flags;
	private String errorMsgs;
	private String icmsPkuuids;
	private String isPrePay;
	private String instOper;// 操作者Id

	private String auditMemo;// 操作意见
	
	public String getInstOper() {
		return instOper;
	}
	public void setInstOper(String instOper) {
		this.instOper = instOper;
	}
	public String getAuditMemo() {
		return auditMemo;
	}
	public void setAuditMemo(String auditMemo) {
		this.auditMemo = auditMemo;
	}
	public String getIsPrePay() {
		return isPrePay;
	}
	public void setIsPrePay(String isPrePay) {
		this.isPrePay = isPrePay;
	}
	public String getBatchNo() {
		return batchNo;
	}
	public void setBatchNo(String batchNo) {
		this.batchNo = batchNo;
	}
	public String getMainCnt() {
		return mainCnt;
	}
	public void setMainCnt(String mainCnt) {
		this.mainCnt = mainCnt;
	}
	public String getAttachCnt() {
		return attachCnt;
	}
	public void setAttachCnt(String attachCnt) {
		this.attachCnt = attachCnt;
	}
	public String getPayId() {
		return payId;
	}
	public void setPayId(String payId) {
		this.payId = payId;
	}
	public String getInnerId() {
		return innerId;
	}
	public void setInnerId(String innerId) {
		this.innerId = innerId;
	}
	public String getDataFlag() {
		return dataFlag;
	}
	public void setDataFlag(String dataFlag) {
		this.dataFlag = dataFlag;
	}
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getAttachCnts() {
		return attachCnts;
	}
	public void setAttachCnts(String attachCnts) {
		this.attachCnts = attachCnts;
	}
	public String getPayIds() {
		return payIds;
	}
	public void setPayIds(String payIds) {
		this.payIds = payIds;
	}
	public String getFlags() {
		return flags;
	}
	public void setFlags(String flags) {
		this.flags = flags;
	}
	public String getErrorMsgs() {
		return errorMsgs;
	}
	public void setErrorMsgs(String errorMsgs) {
		this.errorMsgs = errorMsgs;
	}
	public String getIcmsPkuuids() {
		return icmsPkuuids;
	}
	public void setIcmsPkuuids(String icmsPkuuids) {
		this.icmsPkuuids = icmsPkuuids;
	}
	public String getMainCntOk() {
		return mainCntOk;
	}
	public void setMainCntOk(String mainCntOk) {
		this.mainCntOk = mainCntOk;
	}
	public String getAttachCntOk() {
		return attachCntOk;
	}
	public void setAttachCntOk(String attachCntOk) {
		this.attachCntOk = attachCntOk;
	}
	
	
}
