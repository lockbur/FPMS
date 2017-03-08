package com.forms.prms.web.budget.firstaudit.domain;


public class AssetBudgetDetailBean{
	private String tmpltId;			//模板ID
	private String rowSeq;			//行号
	
	private String montCode;		//预算指标
	private String propertyType;	//资产大类
	private String acCode;			//核算码
	private String columnOne;		//分行自定义1
	private String columnTwo;		//分行自定义2
	private String matrCode;		//物料编码
	private String matrName;		//物料名称
	private String referPrice;		//参考单价
	private String referType;		//参考型号
	private String scrapNum;		//报废数量
	private String demandNum;		//需求数量
	private String budgetAmount;	//预算金额
	private String memoDescr;		//备注说明

	private String errorMemo;		//处理错误的原因说明
	
	public String getTmpltId() {
		return tmpltId;
	}
	public void setTmpltId(String tmpltId) {
		this.tmpltId = tmpltId;
	}
	public String getRowSeq() {
		return rowSeq;
	}
	public void setRowSeq(String rowSeq) {
		this.rowSeq = rowSeq;
	}
	public String getMontCode() {
		return montCode;
	}
	public void setMontCode(String montCode) {
		this.montCode = montCode;
	}
	public String getAcCode() {
		return acCode;
	}
	public void setAcCode(String acCode) {
		this.acCode = acCode;
	}
	public String getColumnOne() {
		return columnOne;
	}
	public void setColumnOne(String columnOne) {
		this.columnOne = columnOne;
	}
	public String getColumnTwo() {
		return columnTwo;
	}
	public void setColumnTwo(String columnTwo) {
		this.columnTwo = columnTwo;
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
	public String getErrorMemo() {
		return errorMemo;
	}
	public void setErrorMemo(String errorMemo) {
		this.errorMemo = errorMemo;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getReferPrice() {
		return referPrice;
	}
	public void setReferPrice(String referPrice) {
		this.referPrice = referPrice;
	}
	public String getReferType() {
		return referType;
	}
	public void setReferType(String referType) {
		this.referType = referType;
	}
	public String getScrapNum() {
		return scrapNum;
	}
	public void setScrapNum(String scrapNum) {
		this.scrapNum = scrapNum;
	}
	public String getDemandNum() {
		return demandNum;
	}
	public void setDemandNum(String demandNum) {
		this.demandNum = demandNum;
	}
	public String getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(String budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	public String getMemoDescr() {
		return memoDescr;
	}
	public void setMemoDescr(String memoDescr) {
		this.memoDescr = memoDescr;
	}
	

}
