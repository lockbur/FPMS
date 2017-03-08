package com.forms.prms.web.budget.budgetplan.domain;

/**
 * 费用类Excel导入处理Bean
 * @author HQQ
 */
public class FeeBudgetDetailBean {
	
	private String 	tmpltId;			//模板ID
	private String 	rowSeq;				//行号
	private String 	montCode;			//预算指标
	private String 	jyZm;				//捐益子目
	private String 	acCode;				//核算码
	private String 	columnOne;			//分行自定义字段1
	private String 	columnTwo;			//分行自定义字段2
	private String 	matrCode;			//物料编码
	private String 	matrName;			//物料名称
	private String  budgetAmount;		//预算金额
	private String  memoDesr;			//备注说明
	private String 	errorMemo;			//处理错误的原因说明
	
	public String getTmpltId() {
		return tmpltId;
	}
	public void setTmpltId(String tmpltId) {
		this.tmpltId = tmpltId;
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
	public String getRowSeq() {
		return rowSeq;
	}
	public void setRowSeq(String rowSeq) {
		this.rowSeq = rowSeq;
	}
	public String getJyZm() {
		return jyZm;
	}
	public void setJyZm(String jyZm) {
		this.jyZm = jyZm;
	}
	public String getBudgetAmount() {
		return budgetAmount;
	}
	public void setBudgetAmount(String budgetAmount) {
		this.budgetAmount = budgetAmount;
	}
	public String getMemoDesr() {
		return memoDesr;
	}
	public void setMemoDesr(String memoDesr) {
		this.memoDesr = memoDesr;
	}
	
}
