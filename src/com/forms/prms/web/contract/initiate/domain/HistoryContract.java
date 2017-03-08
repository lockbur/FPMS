package com.forms.prms.web.contract.initiate.domain;

import java.io.Serializable;
import java.math.BigDecimal;

//合同历史操作
public class HistoryContract implements Serializable {

	private static final long serialVersionUID = 4023938417525147745L;

	private String tableName;// 表名
	private BigDecimal versionNo;// 版本号
	private String cntNum;// 合同编号
	private String operType;// 操作类型

	public HistoryContract() {
		super();
	}

	/**
	 * 含参构造函数
	 * @param tableName
	 * @param cntNum
	 * @param operType
	 */
	public HistoryContract(String tableName, String cntNum, String operType) {
		super();
		this.tableName = tableName;
		this.cntNum = cntNum;
		this.operType = operType;
	}

	public HistoryContract(String tableName, BigDecimal versionNo, String cntNum, String operType) {
		super();
		this.tableName = tableName;
		this.versionNo = versionNo;
		this.cntNum = cntNum;
		this.operType = operType;
	}

	public String getTableName() {
		return tableName;
	}

	public void setTableName(String tableName) {
		this.tableName = tableName;
	}

	public BigDecimal getVersionNo() {
		return versionNo;
	}

	public void setVersionNo(BigDecimal versionNo) {
		this.versionNo = versionNo;
	}

	public String getCntNum() {
		return cntNum;
	}

	public void setCntNum(String cntNum) {
		this.cntNum = cntNum;
	}

	public String getOperType() {
		return operType;
	}

	public void setOperType(String operType) {
		this.operType = operType;
	}
}
