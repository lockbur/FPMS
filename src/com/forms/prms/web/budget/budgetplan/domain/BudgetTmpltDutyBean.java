package com.forms.prms.web.budget.budgetplan.domain;

import java.io.Serializable;

/**
 * 描述: 用于保存预算模板与可用机构关系的Bean，对应表：TB_BUDGET_TMPLT_DUTY ；
 * @author HQQ
 */
public class BudgetTmpltDutyBean implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private String tmpltId;			//预算模板ID
	private String dutyCode;		//机构编号
	private String dutyName;		//机构名称(关联表TB_FNDWRR查询)
	
	public String getTmpltId() {
		return tmpltId;
	}
	public void setTmpltId(String tmpltId) {
		this.tmpltId = tmpltId;
	}
	public String getDutyCode() {
		return dutyCode;
	}
	public void setDutyCode(String dutyCode) {
		this.dutyCode = dutyCode;
	}
	public String getDutyName() {
		return dutyName;
	}
	public void setDutyName(String dutyName) {
		this.dutyName = dutyName;
	}
	
}
