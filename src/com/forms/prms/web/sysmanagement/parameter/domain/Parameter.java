package com.forms.prms.web.sysmanagement.parameter.domain;
/**
 * author : wuqm <br>
 * date : 2013-10-25<br>
 * 
 */
public class Parameter 
{
	
	private static final long serialVersionUID = 1L;
	
	private String categoryId; 			// 分类ID
	private String categoryName; 		// 分类ID名称
	private String paramVarName; 		// 参数变量名
	private String paramDispName; 		// 参数显示名称
	private String paramValue; 			// 参数值
	private String paramDesc; 			// 参数描述
	private String isPwdType; 			// 是否密码类
	private String applyUserId;			// 修改人
	private String applyTime;			// 修改时间
	private String approveUserId;		// 审批人
	private String approveTime;			// 审批时机
	private String applyStatus;			// 审批状态
	private String paramUpdateValue;	// 参数更新后值
	private String paramOrigValue;		// 参数原始值(更新前)
	private String regExp;   			// 正则表达式
	private String regCheckInfo; 		// 正则表达式检查提示信息 
	private String isUseReg; 			// 是否使用正则表达式 (09-24修改为：根据regExp属性是否有值来决定是否需要正则校验		0：不使用正则	1：使用正则)

	public String getRegExp() {
		return regExp;
	}
	public void setRegExp(String regExp) {
		this.regExp = regExp;
	}
	public String getRegCheckInfo() {
		return regCheckInfo;
	}
	public void setRegCheckInfo(String regCheckInfo) {
		this.regCheckInfo = regCheckInfo;
	}
	public String getIsUseReg() {
		return isUseReg;
	}
	public void setIsUseReg(String isUseReg) {
		this.isUseReg = isUseReg;
	}
	
	public String getParamOrigValue() {
		return paramOrigValue;
	}
	public void setParamOrigValue(String paramOrigValue) {
		this.paramOrigValue = paramOrigValue;
	}
	public String getParamUpdateValue() {
		return paramUpdateValue;
	}
	public void setParamUpdateValue(String paramUpdateValue) {
		this.paramUpdateValue = paramUpdateValue;
	}
	public String getCategoryName() {
		return categoryName;
	}
	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}
	public String getCategoryId() {
		return categoryId;
	}
	public void setCategoryId(String categoryId) {
		this.categoryId = categoryId;
	}
	
	public String getParamDispName() {
		return paramDispName;
	}
	public void setParamDispName(String paramDispName) {
		this.paramDispName = paramDispName;
	}
	public String getParamValue() {
		return paramValue;
	}
	public void setParamValue(String paramValue) {
		this.paramValue = paramValue;
	}
	public String getParamDesc() {
		return paramDesc;
	}
	public void setParamDesc(String paramDesc) {
		this.paramDesc = paramDesc;
	}
	public String getIsPwdType() {
		return isPwdType;
	}
	public void setIsPwdType(String isPwdType) {
		this.isPwdType = isPwdType;
	}
	public String getApplyUserId() {
		return applyUserId;
	}
	public void setApplyUserId(String applyUserId) {
		this.applyUserId = applyUserId;
	}
	public String getApplyTime() {
		return applyTime;
	}
	public void setApplyTime(String applyTime) {
		this.applyTime = applyTime;
	}
	public String getApproveUserId() {
		return approveUserId;
	}
	public void setApproveUserId(String approveUserId) {
		this.approveUserId = approveUserId;
	}
	
	public String getApproveTime() {
		return approveTime;
	}
	public void setApproveTime(String approveTime) {
		this.approveTime = approveTime;
	}
	public String getApplyStatus() {
		return applyStatus;
	}
	public void setApplyStatus(String applyStatus) {
		this.applyStatus = applyStatus;
	}
	public String getParamVarName() {
		return paramVarName;
	}
	public void setParamVarName(String paramVarName) {
		this.paramVarName = paramVarName;
	}
	
	

	
	
	
}
