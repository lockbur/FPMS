package com.forms.prms.web.amortization.unnormalDataMgr.domain;

/**
 * Title:UnnorDataMgrBean
 * Description:	异常数据功能Bean,用于作公共查询条件使用
 * 				(abnormalDataQuery.jsp页面中查询区域，适应各个查询条件用)
 * Copyright: formssi
 * @author 
 * @project ERP
 * @date 2015-07-02
 * @version 1.0
 */
public class UnnorDataMgrBean {
	
	private String queryType;//查询类别
	
	private String poNumber; //PO单号

	private String orderId; //订单号
	
	private String status; //异常订单状态 0-异常  1-正常

	public String getQueryType() {
		return queryType;
	}

	public void setQueryType(String queryType) {
		this.queryType = queryType;
	}

	public String getPoNumber() {
		return poNumber;
	}

	public void setPoNumber(String poNumber) {
		this.poNumber = poNumber;
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
	
}
