package com.forms.prms.web.budget.budgetplan.domain;

/**
 * 描述: Excel导入操作，保存于数据库信息的Bean，对应表TB_BUDGET_TMPLT_DETAIL；
 * 		  每一个Bean信息对应Excel表中一条行数据
 * @author HQQ
 */
public class BudgetTempDetailBean {
	
	private String tmpltId;		//预算模板ID
	private String rowSeq;		//Excel信息行号
	private String rowInfo;		//Excel信息行数据
	private String matrAuditDept;
	private String matrAuditDeptName;	//物料归口部门
	
	//以下为 为使用不同类型模板所定义的公共属性(包括资产类和费用类模板的所有属性)
	private String montCode;		//预算指标
	private String propertyType;	//资产大类(资产类专有)
	private String jyZm;			//捐益子目(费用类专有)
	private String acCode;			//核算码
	private String columnOne;		//分行自定义1
	private String columnTwo;		//分行自定义2
	private String matrCode;		//物料编码
	private String matrName;		//物料名称
	private int referPrice;		//参考单价(资产类专有)
	private String referPriceHeader;		//参考单价(资产类专有)
	private String referType;		//参考型号(资产类专有)
	private String scrapNum;		//报废数量(资产类专有)
	private String demandNum;		//需求数量(资产类专有)
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
	public String getRowInfo() {
		return rowInfo;
	}
	public void setRowInfo(String rowInfo) {
		this.rowInfo = rowInfo;
	}
	public String getMatrAuditDept() {
		return matrAuditDept;
	}
	public void setMatrAuditDept(String matrAuditDept) {
		this.matrAuditDept = matrAuditDept;
	}
	public String getMatrAuditDeptName() {
		return matrAuditDeptName;
	}
	public void setMatrAuditDeptName(String matrAuditDeptName) {
		this.matrAuditDeptName = matrAuditDeptName;
	}
	public String getMatrCode() {
		return matrCode;
	}
	public void setMatrCode(String matrCode) {
		this.matrCode = matrCode;
	}
	public String getMontCode() {
		return montCode;
	}
	public void setMontCode(String montCode) {
		this.montCode = montCode;
	}
	public String getPropertyType() {
		return propertyType;
	}
	public void setPropertyType(String propertyType) {
		this.propertyType = propertyType;
	}
	public String getJyZm() {
		return jyZm;
	}
	public void setJyZm(String jyZm) {
		this.jyZm = jyZm;
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
	public String getMatrName() {
		return matrName;
	}
	public void setMatrName(String matrName) {
		this.matrName = matrName;
	}
//	public String getReferPrice() {
//		return referPrice;
//	}
//	public void setReferPrice(String referPrice) {
//		this.referPrice = referPrice;
//	}
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
	public String getErrorMemo() {
		return errorMemo;
	}
	public void setErrorMemo(String errorMemo) {
		this.errorMemo = errorMemo;
	}
	//处理Excel保存数值为字符串问题
	public int getReferPrice() {
		return referPrice;
	}
	public void setReferPrice(int referPrice) {
		this.referPrice = referPrice;
	}
	public String getReferPriceHeader() {
		return referPriceHeader;
	}
	public void setReferPriceHeader(String referPriceHeader) {
		this.referPriceHeader = referPriceHeader;
	}

}
