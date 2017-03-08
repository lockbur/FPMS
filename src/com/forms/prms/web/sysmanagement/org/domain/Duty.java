package com.forms.prms.web.sysmanagement.org.domain;

import com.forms.platform.core.util.Tool;

public class Duty extends BaseBean{
	
	private static final long serialVersionUID = 8969125616977265390L;
	
	private static final boolean isContinue = false;
	
	// 责任中心
	private String dutyCode; 
	
	// 责任中心名称
	private String dutyName;
	
	// ou
	private String ouCode;
	
	// ou名称
	private String ouName;
	
	// 机构号
	private String orgCode;
	
	// 机构名称
	private String orgName;
	
	// 二级分行代码
	private String org2Code;
	
	// 二级分行名称
	private String org2Name;
	
	// 一级行代码
	private String org1Code;
	
	// 一级行名称
	private String org1Name;
	
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

	
	// 一级机构对象
	private Node orgFirstNode;
	
	private void setOrgFirstNode()
	{
		if( !Tool.CHECK.isBlank( this.getOrg1Code() ) )
		{
			this.orgFirstNode = new Node();
			this.orgFirstNode.setId( this.getOrg1Code() );
			this.orgFirstNode.setName( this.getOrg1Name() );
			this.orgFirstNode.setLevel( this.getLevel(0, false) );
			this.orgFirstNode.setpId( "" );
			this.orgFirstNode.setIsValid("Y");
			this.orgFirstNode.setLevelBelong("org1");
		}
	}
	
	public Node getOrgFirstNode()
	{
		return this.orgFirstNode;
	}
	
	// 二级机构对象
	private Node orgSecodeNode;
	
	private void setOrgSecodeNode()
	{
		if( !Tool.CHECK.isBlank( this.getOrg2Code() ) )
		{
			this.orgSecodeNode = new Node();
			this.orgSecodeNode.setId( this.getOrg2Code() );
			this.orgSecodeNode.setName( this.getOrg2Name() );
			this.orgSecodeNode.setLevel( this.getLevel(1, false) );
			this.orgSecodeNode.setpId( this.getOrg1Code() );
			this.orgSecodeNode.setIsValid( "Y" );
			this.orgSecodeNode.setLevelBelong("org2");
		}
	}
	
	public Node getOrgSecodeNode()
	{
		return this.orgSecodeNode;
	}
	
	// 一级ou对象
	private Node ouFirstNode;
	
	private void setOuFirstNode()
	{
		if( !Tool.CHECK.isBlank( this.getOrg1Code() ) )
		{
			this.ouFirstNode = new Node();
			this.ouFirstNode.setId( this.getOrg1Code() );
			this.ouFirstNode.setName( this.getOrg1Name() );
			this.ouFirstNode.setLevel( this.getLevel(0, false) );
			this.ouFirstNode.setpId( "00000" );
			this.ouFirstNode.setIsValid("Y");
			this.ouFirstNode.setLevelBelong("org1");
		}
	}
	
	public Node getOuFirstNode()
	{
		return this.ouFirstNode;
	}
	
	// 二级ou对象
	private Node ouSecodeNode;
	
	
	private void setOuSecodeNode()
	{
		if( !Tool.CHECK.isBlank( this.getOrg2Code() ) )
		{
			this.ouSecodeNode = new Node();
			this.ouSecodeNode.setId( this.getOrg2Code() );
			this.ouSecodeNode.setName( this.getOrg2Name() );
			this.ouSecodeNode.setLevel( this.getLevel(1, false) );
			this.ouSecodeNode.setpId( this.getOrg1Code() );
			this.ouSecodeNode.setIsValid( "Y" );
			this.ouSecodeNode.setLevelBelong("org2");
		}
	}
	
	public Node getOuSecodeNode()
	{
		return this.ouSecodeNode;
	}
	
	private Node orgNode;
	
	private void setOrgNode()
	{
		if( !Tool.CHECK.isBlank( this.getOrgCode() ) )
		{
			this.orgNode = new Node();
			this.orgNode.setId( this.getOrgCode() );
			this.orgNode.setName( this.getOrgName() );
			
			if( Tool.CHECK.isBlank( this.getOrg2Code() ) )
			{
				this.orgNode.setLevel( this.getLevel(2, true) );
				this.orgNode.setpId( this.getOrg1Code() );
			}
			else
			{
				this.orgNode.setLevel( this.getLevel(2, false) );
				this.orgNode.setpId( this.getOrg2Code() );
			}
			
			this.orgNode.setIsValid( "Y" );
			this.orgNode.setLevelBelong("org");
		}
	}
	
	public Node getOrgNode()
	{
		return this.orgNode;
	}
	
	private Node ouNode;
	
	private void setOuNode()
	{
		if( !Tool.CHECK.isBlank( this.getOuCode() ) )
		{
			this.ouNode = new Node();
			this.ouNode.setId( this.getOuCode() );
			this.ouNode.setName( this.getOuName() );
			
			if( Tool.CHECK.isBlank( this.getOrg2Code() ) )
			{
				this.ouNode.setLevel( this.getLevel(2, true) );
				this.ouNode.setpId( this.getOrg1Code() );
			}
			else
			{
				this.ouNode.setLevel( this.getLevel(2, false) );
				this.ouNode.setpId( this.getOrg2Code() );
			}
			this.ouNode.setIsValid( "Y" );
			this.ouNode.setLevelBelong("ou");
		}
	}
	
	public Node getOuNode()
	{
		return this.ouNode;
	}
	
	private Node orgDutyNode;
	
	private void setOrgDutyNode()
	{
		if( !Tool.CHECK.isBlank( this.getDutyCode() ) )
		{
			this.orgDutyNode = new Node();
			this.orgDutyNode.setId( this.getDutyCode() );
			this.orgDutyNode.setName( this.getDutyName() );
			
			if( Tool.CHECK.isBlank( this.getOrg2Code() ) )
			{
				this.orgDutyNode.setLevel( this.getLevel(3, true) );
			}
			else
			{
				this.orgDutyNode.setLevel( this.getLevel(3, false) );
			}
			this.orgDutyNode.setpId( this.getOrgCode() );
			this.orgDutyNode.setIsValid( "Y" );
			this.orgDutyNode.setLevelBelong("duty");
		}
	}
	
	public Node getOrgDutyNode()
	{
		return this.orgDutyNode;
	}
	
private Node ouDutyNode;
	
	private void setOuDutyNode()
	{
		if( !Tool.CHECK.isBlank( this.getDutyCode() ) )
		{
			this.ouDutyNode = new Node();
			this.ouDutyNode.setId( this.getDutyCode() );
			this.ouDutyNode.setName( this.getDutyName() );
			
			if( Tool.CHECK.isBlank( this.getOrg2Code() ) )
			{
				this.ouDutyNode.setLevel( this.getLevel(3, true) );
			}
			else
			{
				this.ouDutyNode.setLevel( this.getLevel(3, false) );
			}
			this.ouDutyNode.setpId( this.getOuCode() );
			this.ouDutyNode.setIsValid( "Y" );
			this.ouDutyNode.setLevelBelong("duty");
		}
	}
	
	public Node getOuDutyNode()
	{
		return this.orgDutyNode;
	}
	
	public void getAllNodes()
	{
		this.setOrgFirstNode();
		this.setOrgSecodeNode();
		this.setOrgNode();
		this.setOrgDutyNode();
		
		this.setOuFirstNode();
		this.setOuSecodeNode();
		this.setOuNode();
		this.setOuDutyNode();
	}
	
	/**
	 * @param level
	 * @param isDeal
	 * @return
	 */
	private String getLevel(int level, boolean isDeal)
	{
		if( isDeal )
		{
			if( isContinue )
			{
				level = level - 1;
			}
			return String.valueOf( level );
		}
		else
		{
			return String.valueOf( level );
		}
	}
	
}
