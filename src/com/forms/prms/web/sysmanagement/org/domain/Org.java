package com.forms.prms.web.sysmanagement.org.domain;

public class Org extends BaseBean {
	
	private static final long serialVersionUID = 8969089316977265390L;
	
	private String deptId;
	private String deptName;
	private String deptType;
	private String upDeptId;
	private String deptLevel;
	private String notesId;
	private String managerUserId;
	private String memo;
	private String firstAddUserId;
	private String firstAddDate;
	private String firstAddTime;
	private String lastModifyUserId;
	private String lastModifyDate;
	private String lastModifyTime;
	private String enableDelete;
	private String isDeleted;
	
	public String getDeptId() {
		return deptId;
	}

	public void setDeptId(String deptId) {
		this.deptId = deptId;
	}

	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
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

	public String getDeptLevel() {
		return deptLevel;
	}

	public void setDeptLevel(String deptLevel) {
		this.deptLevel = deptLevel;
	}

	public String getNotesId() {
		return notesId;
	}

	public void setNotesId(String notesId) {
		this.notesId = notesId;
	}

	public String getManagerUserId() {
		return managerUserId;
	}

	public void setManagerUserId(String managerUserId) {
		this.managerUserId = managerUserId;
	}

	public String getMemo() {
		return memo;
	}

	public void setMemo(String memo) {
		this.memo = memo;
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

	public String getEnableDelete() {
		return enableDelete;
	}

	public void setEnableDelete(String enableDelete) {
		this.enableDelete = enableDelete;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}


	@Override
	public Node changeSelfToNode() {
		// TODO Auto-generated method stub
		Node node = new Node();
		node.setId(this.getDeptId());
		node.setpId(this.getUpDeptId());
		node.setName(this.getDeptName());
		node.setLevel(this.getDeptLevel());
		node.setIsDeleted(this.getIsDeleted());
		return  node;
	}
	
	
}
