package com.forms.prms.web.sysmanagement.org.domain;

public class OrgUnit extends BaseBean{
	private static final long serialVersionUID = 1L;
	
	private String dId;
	private String dName;
	private String dLevel;
	private String pId;
	
	public String getdId() {
		return dId;
	}
	public void setdId(String dId) {
		this.dId = dId;
	}
	public String getdName() {
		return dName;
	}
	public void setdName(String dName) {
		this.dName = dName;
	}
	public String getdLevel() {
		return dLevel;
	}
	public void setdLevel(String dLevel) {
		this.dLevel = dLevel;
	}
	public String getpId() {
		return pId;
	}
	public void setpId(String pId) {
		this.pId = pId;
	}
	
	@Override
	public Node changeSelfToNode(){
		Node node = new Node();
		node.setId(this.getdId());
		node.setName(this.getdName());
		node.setLevel(this.getdLevel());
		node.setpId(this.getpId());
		return node;
	}

}
