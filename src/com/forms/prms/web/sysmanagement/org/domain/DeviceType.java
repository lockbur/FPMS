package com.forms.prms.web.sysmanagement.org.domain;


public class DeviceType extends BaseBean{

	private static final long serialVersionUID = 8969089566977265390L;
		
	//节点id
	private String nodeId;
	
	private String nodeSelfId;
	
	//节点名称
	private String nodeNm;
	
	//上级节点ID
	private String upNodeId;
	
	private String upNodeName;
	
	//级别
	private String lvl;
	
	//是否生效
	private String isValid;
	
	private String isValidName;
	
	//新增人id
	private String firstAddUserId;
	
	private String firstAddUserName;
	
	//新增日期
	private String firstAddDate;
	
	//新增时间
	private String firstAddTime;
	
	//修改人id
	private String lastModifyUserId;
	
	private String lastModifyUserName;
	
	//修改日期
	private String lastModifyDate;
	
	//修改时间
	private String lastModifyTime;
	
	
	public String getNodeId() {
		return nodeId;
	}



	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}



	public String getNodeSelfId() {
		return nodeSelfId;
	}



	public void setNodeSelfId(String nodeSelfId) {
		this.nodeSelfId = nodeSelfId;
	}



	public String getNodeNm() {
		return nodeNm;
	}



	public void setNodeNm(String nodeNm) {
		this.nodeNm = nodeNm;
	}



	public String getUpNodeId() {
		return upNodeId;
	}



	public void setUpNodeId(String upNodeId) {
		this.upNodeId = upNodeId;
	}



	public String getUpNodeName() {
		return upNodeName;
	}



	public void setUpNodeName(String upNodeName) {
		this.upNodeName = upNodeName;
	}



	public String getLvl() {
		return lvl;
	}



	public void setLvl(String lvl) {
		this.lvl = lvl;
	}



	public String getIsValid() {
		return isValid;
	}



	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}



	public String getIsValidName() {
		return isValidName;
	}



	public void setIsValidName(String isValidName) {
		this.isValidName = isValidName;
	}



	public String getFirstAddUserId() {
		return firstAddUserId;
	}



	public void setFirstAddUserId(String firstAddUserId) {
		this.firstAddUserId = firstAddUserId;
	}



	public String getFirstAddUserName() {
		return firstAddUserName;
	}



	public void setFirstAddUserName(String firstAddUserName) {
		this.firstAddUserName = firstAddUserName;
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



	public String getLastModifyUserName() {
		return lastModifyUserName;
	}



	public void setLastModifyUserName(String lastModifyUserName) {
		this.lastModifyUserName = lastModifyUserName;
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



	@Override
	public Node changeSelfToNode() {
		Node node = new Node();
		node.setId(this.getNodeId());
		node.setpId(this.getUpNodeId());
		node.setName(this.getNodeNm());
		node.setLevel(this.getLvl());
		node.setIsValid(this.getIsValid());
		return  node;
	}

}
