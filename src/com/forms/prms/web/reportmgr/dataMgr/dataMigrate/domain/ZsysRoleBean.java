package com.forms.prms.web.reportmgr.dataMgr.dataMigrate.domain;

import java.io.Serializable;


/**
 * Title:ZsysRoleBean
 * Description:用于测试数据迁移模块的数据导入TestBean类
 * Copyright: formssi
 * @author HQQ
 * @project ERP
 * @date 2015-06-19
 * @version 1.0
 */
public class ZsysRoleBean implements Serializable{

	/**
	 * 序列化号
	 */
	private static final long serialVersionUID = 1L;

	private String roleId;	
	private String roleName;
	private String memo;
	private String enableDel;
	private String roleLevel;
	
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
	public String getMemo() {
		return memo;
	}
	public void setMemo(String memo) {
		this.memo = memo;
	}
	public String getEnableDel() {
		return enableDel;
	}
	public void setEnableDel(String enableDel) {
		this.enableDel = enableDel;
	}
	public String getRoleLevel() {
		return roleLevel;
	}
	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}
	
}
