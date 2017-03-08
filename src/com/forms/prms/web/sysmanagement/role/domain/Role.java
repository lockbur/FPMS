package com.forms.prms.web.sysmanagement.role.domain;

import java.util.List;

public class Role {

	private String roleId;

	private String roleName;
	
	private String[] funcIdList;
	
	private String enableDel;
	
	private String[] delIds;
	
	private String roleLevel;
	
	private List<String> delIdLst;
	private String instUser;
	private String instDate;
	private String updateUser;
	private String updateDate;
	
	
	public String getInstUser() {
		return instUser;
	}

	public void setInstUser(String instUser) {
		this.instUser = instUser;
	}

	public String getInstDate() {
		return instDate;
	}

	public void setInstDate(String instDate) {
		this.instDate = instDate;
	}

	public String getUpdateUser() {
		return updateUser;
	}

	public void setUpdateUser(String updateUser) {
		this.updateUser = updateUser;
	}

	public String getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(String updateDate) {
		this.updateDate = updateDate;
	}

	public String getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}

	public List<String> getDelIdLst() {
		return delIdLst;
	}

	public void setDelIdLst(List<String> delIdLst) {
		this.delIdLst = delIdLst;
	}

	public String[] getDelIds() {
		return delIds;
	}

	public void setDelIds(String[] delIds) {
		this.delIds = delIds;
	}

	public String getEnableDel() {
		return enableDel;
	}

	public void setEnableDel(String enableDel) {
		this.enableDel = enableDel;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String[] getFuncIdList() {
		return funcIdList;
	}

	public void setFuncIdList(String[] funcIdList) {
		this.funcIdList = funcIdList;
	}

}
