package com.forms.prms.web.sysmanagement.user.domain;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public class UserInfo implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1215768648604303920L;

	// 员工号
	private String userId;

	// 员工姓名
	private String userName;

	// 电话
	private String phoneNumber;

	// 密码
	private String password;

	// 角色
	private String roleId;

	// 头像源文件
	//private MultipartFile imgFile;

	// 头像字节数组
	private byte[] imgData;

	private String org21Code;

	private String isSuper;

	private String isYg;//是否员工
	
	

	public String getIsYg() {
		return isYg;
	}

	public void setIsYg(String isYg) {
		this.isYg = isYg;
	}

	public String getIsSuper() {
		return isSuper;
	}

	public void setIsSuper(String isSuper) {
		this.isSuper = isSuper;
	}

	public String getOrg21Code() {
		return org21Code;
	}

	public void setOrg21Code(String org21Code) {
		this.org21Code = org21Code;
	}

	private String firstAddUserId;

	private String firstAddDate;

	private String firstAddTime;

	private String lastModifyUserId;

	private String lastModifyDate;

	private String lastModifyTime;

	private String notesId;

	private String memo;

	private String userType;

	private String deptName;

	private String deptType;

	private String upDeptId;

	private String deptLevel;

	private MultipartFile userFile;

	private String orgCode;// 机构代码
	private String orgName;// 机构名称
	private String dutyCode;// 责任中心代码
	private String dutyName;// 责任中心名称
	private String status;// 员工状态
	private String statusName;// 员工状态
	private String leaveDate;// 离职日期
	private String isBuyer;// 是否采购员
	private String org1Code;// 所属一級行
	private String org1Name;// 所属一級行名称

	private String ouCode;// 所属OU
	private String ouName;// 所属OU名称
	private String isSuperAdmin;// 是否超级管理员 1/0

	private String pwdChangeDate;// 密码修改时间

	private String pwdFailTimes;// 密码错误次数

	private String org2CodeOri;// fms原始二级行代码
	private String org2NameOri;// fms原始二级行名称

	private String org2Code;// 二级行或本部
	private String org2Name;// 所属二级行名称

	private String scanPosition;// 扫描岗

	private String pwd;// 密码历史使用表

	private String quitUserId;

	private String pwdValidate;
	private String[] questionSeq;// 问题序列
	private String[] questionAnswer;// 问题答案
	private String[] questionId;// 问题ID
	private String seq;
	private String answer;
	private String id;
	List<Map<String, String>> pwdQuestionList;
	private String confirmPwd;
	private String isLocked;
	private String pwdErrDate;
	private String flag;
	private String haveRole;
	private String roleLevel;
	private String roleLevelName;
	private String roleName;
	
	
	public String getRoleLevel() {
		return roleLevel;
	}

	public void setRoleLevel(String roleLevel) {
		this.roleLevel = roleLevel;
	}

	public String getRoleLevelName() {
		return roleLevelName;
	}

	public void setRoleLevelName(String roleLevelName) {
		this.roleLevelName = roleLevelName;
	}

	public String getRoleName() {
		return roleName;
	}

	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}

	public String getHaveRole() {
		return haveRole;
	}

	public void setHaveRole(String haveRole) {
		this.haveRole = haveRole;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	public String getConfirmPwd() {
		return confirmPwd;
	}

	public void setConfirmPwd(String confirmPwd) {
		this.confirmPwd = confirmPwd;
	}

	public String getIsLocked() {
		return isLocked;
	}

	public void setIsLocked(String isLocked) {
		this.isLocked = isLocked;
	}

	public String getPwdErrDate() {
		return pwdErrDate;
	}

	public void setPwdErrDate(String pwdErrDate) {
		this.pwdErrDate = pwdErrDate;
	}

	public String getSeq() {
		return seq;
	}

	public void setSeq(String seq) {
		this.seq = seq;
	}

	public String getAnswer() {
		return answer;
	}

	public void setAnswer(String answer) {
		this.answer = answer;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public List<Map<String, String>> getPwdQuestionList() {
		return pwdQuestionList;
	}

	public void setPwdQuestionList(List<Map<String, String>> pwdQuestionList) {
		this.pwdQuestionList = pwdQuestionList;
	}

	public String[] getQuestionSeq() {
		return questionSeq;
	}

	public void setQuestionSeq(String[] questionSeq) {
		this.questionSeq = questionSeq;
	}

	public String[] getQuestionAnswer() {
		return questionAnswer;
	}

	public void setQuestionAnswer(String[] questionAnswer) {
		this.questionAnswer = questionAnswer;
	}

	public String[] getQuestionId() {
		return questionId;
	}

	public void setQuestionId(String[] questionId) {
		this.questionId = questionId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getPwdValidate() {
		return pwdValidate;
	}

	public void setPwdValidate(String pwdValidate) {
		this.pwdValidate = pwdValidate;
	}

	public String getQuitUserId() {
		return quitUserId;
	}

	public void setQuitUserId(String quitUserId) {
		this.quitUserId = quitUserId;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getScanPosition() {
		return scanPosition;
	}

	public void setScanPosition(String scanPosition) {
		this.scanPosition = scanPosition;
	}

	public String getOrg2CodeOri() {
		return org2CodeOri;
	}

	public void setOrg2CodeOri(String org2CodeOri) {
		this.org2CodeOri = org2CodeOri;
	}

	public String getOrg2NameOri() {
		return org2NameOri;
	}

	public void setOrg2NameOri(String org2NameOri) {
		this.org2NameOri = org2NameOri;
	}

	public String getOrg2Code() {
		return org2Code;
	}

	public void setOrg2Code(String org2Code) {
		this.org2Code = org2Code;
	}

	public String getOrg2Name() {
		return org2Name;
	}

	public void setOrg2Name(String org2Name) {
		this.org2Name = org2Name;
	}

	public String getStatusName() {
		return statusName;
	}

	public void setStatusName(String statusName) {
		this.statusName = statusName;
	}

	public String getPwdChangeDate() {
		return pwdChangeDate;
	}

	public void setPwdChangeDate(String pwdChangeDate) {
		this.pwdChangeDate = pwdChangeDate;
	}

	public String getPwdFailTimes() {
		return pwdFailTimes;
	}

	public void setPwdFailTimes(String pwdFailTimes) {
		this.pwdFailTimes = pwdFailTimes;
	}

	private String[] delIds;

	private List<String> delIdLst;
	private String sessionId;

	public String getSessionId() {
		return sessionId;
	}

	public void setSessionId(String sessionId) {
		this.sessionId = sessionId;
	}

	public String[] getDelIds() {
		return delIds;
	}

	public void setDelIds(String[] delIds) {
		this.delIds = delIds;
	}

	public List<String> getDelIdLst() {
		return delIdLst;
	}

	public void setDelIdLst(List<String> delIdLst) {
		this.delIdLst = delIdLst;
	}

	public String getIsSuperAdmin() {
		return isSuperAdmin;
	}

	public void setIsSuperAdmin(String isSuperAdmin) {
		this.isSuperAdmin = isSuperAdmin;
	}

	public String getOrg1Code() {
		return org1Code;
	}

	public void setOrg1Code(String org1Code) {
		this.org1Code = org1Code;
	}

	public String getOrg1Name() {
		return org1Name;
	}

	public void setOrg1Name(String org1Name) {
		this.org1Name = org1Name;
	}

	public String getOuCode() {
		return ouCode;
	}

	public void setOuCode(String ouCode) {
		this.ouCode = ouCode;
	}

	public String getOuName() {
		return ouName;
	}

	public void setOuName(String ouName) {
		this.ouName = ouName;
	}

	public String getOrgCode() {
		return orgCode;
	}

	public void setOrgCode(String orgCode) {
		this.orgCode = orgCode;
	}

	public String getOrgName() {
		return orgName;
	}

	public void setOrgName(String orgName) {
		this.orgName = orgName;
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

	public String getLeaveDate() {
		return leaveDate;
	}

	public void setLeaveDate(String leaveDate) {
		this.leaveDate = leaveDate;
	}

	public String getIsBuyer() {
		return isBuyer;
	}

	public void setIsBuyer(String isBuyer) {
		this.isBuyer = isBuyer;
	}

	public MultipartFile getUserFile() {
		return userFile;
	}

	public void setUserFile(MultipartFile userFile) {
		this.userFile = userFile;
	}

	public String getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}

	public String getUpDeptId() {
		return upDeptId;
	}

	public void setUpDeptId(String upDeptId) {
		this.upDeptId = upDeptId;
	}

	public String getDeptType() {
		return deptType;
	}

	public void setDeptType(String deptType) {
		this.deptType = deptType;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public String getNotesId() {
		return notesId;
	}

	public void setNotesId(String notesId) {
		this.notesId = notesId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRoleId() {
		return roleId;
	}

	public void setRoleId(String roleId) {
		this.roleId = roleId;
	}

	/*public MultipartFile getImgFile() {
		return imgFile;
	}

	public void setImgFile(MultipartFile imgFile) {
		this.imgFile = imgFile;
	}*/

	public byte[] getImgData() {
		return imgData;
	}

	public void setImgData(byte[] imgData) {
		this.imgData = imgData;
	}

	public String getFirstAddUserId() {
		return firstAddUserId;
	}

	public void setFirstAddUserId(String firstAddUserId) {
		this.firstAddUserId = firstAddUserId;
	}

	public String getFirstAddDate() {
		return firstAddDate;
	}

	public void setFirstAddDate(String firstAddDate) {
		this.firstAddDate = firstAddDate;
	}

	public String getFirstAddTime() {
		return firstAddTime;
	}

	public void setFirstAddTime(String firstAddTime) {
		this.firstAddTime = firstAddTime;
	}

	public String getLastModifyUserId() {
		return lastModifyUserId;
	}

	public void setLastModifyUserId(String lastModifyUserId) {
		this.lastModifyUserId = lastModifyUserId;
	}

	public String getLastModifyDate() {
		return lastModifyDate;
	}

	public void setLastModifyDate(String lastModifyDate) {
		this.lastModifyDate = lastModifyDate;
	}

	public String getLastModifyTime() {
		return lastModifyTime;
	}

	public void setLastModifyTime(String lastModifyTime) {
		this.lastModifyTime = lastModifyTime;
	}

}
