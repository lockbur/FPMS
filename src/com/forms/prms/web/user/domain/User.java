package com.forms.prms.web.user.domain;

import com.forms.platform.authority.domain.Function;
import com.forms.platform.core.Common;
import com.forms.platform.core.tree.ITree;
import com.forms.platform.web.base.model.user.IUser;
import com.forms.prms.web.sysmanagement.user.domain.UserInfo;

public class User  extends UserInfo implements IUser{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private String userId;

	private String userName;

	private String password;
	
	private String deptLevel;
	private String deptType;
	private String upDeptId;
	private String phoneNumber;
	private String userType;
	private String memo;
	private String isSysUser;
	private byte[] logoImg;
	private String roleIds;
	private ITree<Function> menuTree;
	//在线信息
	private String sessionId;
	private String loginIp;
	private String loginDateTime;
	private String pwdChangeDate;
	
	private String status;// 员工状态
	private String statusName;// 员工状态
	private String isDeleted;// 是否删除标识
	private String isYg;//是否员工
	
	private String roleName;// 角色名称
	
	
	public String getIsYg() {
		return isYg;
	}

	public void setIsYg(String isYg) {
		this.isYg = isYg;
	}

	public boolean isLocked() {
		return isLocked;
	}

	public void setLocked(boolean isLocked) {
		this.isLocked = isLocked;
	}

	private boolean isLocked;//是否锁住

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}


	public String getPwdChangeDate() {
		return pwdChangeDate;
	}

	public void setPwdChangeDate(String pwdChangeDate) {
		this.pwdChangeDate = pwdChangeDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String getLoginIp() {
		return loginIp;
	}

	public void setLoginIp(String loginIp) {
		this.loginIp = loginIp;
	}

	public String getLoginDateTime() {
		return loginDateTime;
	}

	public void setLoginDateTime(String loginDateTime) {
		this.loginDateTime = loginDateTime;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getUpDeptId() {
		return upDeptId;
	}

	public void setUpDeptId(String upDeptId) {
		this.upDeptId = upDeptId;
	}

	public void setMenuTree(ITree<Function> menuTree) {
		this.menuTree = menuTree;
	}

	public String getRoleIds() {
		return roleIds;
	}

	public void setRoleIds(String roleIds) {
		this.roleIds = roleIds;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
	}

	public String getIsSysUser() {
		return isSysUser;
	}

	public void setIsSysUser(String isSysUser) {
		this.isSysUser = isSysUser;
	}

	public byte[] getLogoImg() {
		return logoImg;
	}

	public void setLogoImg(byte[] logoImg) {
		this.logoImg = logoImg;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public ITree<Function> getMenuTree() {
		if (menuTree == null) {
			menuTree = Common.getAuthoriseComponent().getAuthoriseController().getUserMenuTree(userId);
		}
		return menuTree;
	}

}
