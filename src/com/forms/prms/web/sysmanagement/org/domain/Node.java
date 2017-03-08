package com.forms.prms.web.sysmanagement.org.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.forms.platform.core.util.Tool;

public class Node implements Comparable<Node>,Serializable{

	private static final long serialVersionUID = 1L;
	
	private String id;
	
	private String pId;
	
	private String name;
	
	private String level;
	
	private boolean asyncLoad = false;
	
	private boolean lock = false;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
	private String levelBelong;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
	private List<Node> children;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	private boolean checked = false;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	private boolean halfCheck = false;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	private boolean open = false;
	
	private boolean defaultOpen = false;
	
	private boolean queryOpen = false;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	private boolean isOpenParent = false;
	
	private boolean defaultSelect = false;
	
	private String isValid = "Y";
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
	private String isDeleted;
	
	private String equalBy;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_DEFAULT)
	private boolean isParent = false;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
	private String targetId ;
	
	@JsonSerialize(include=JsonSerialize.Inclusion.NON_EMPTY)
	private String moveType;
	
	@JsonIgnore
	private boolean recurQuery = false; // 是否已经递归查找
	
	
	public boolean isRecurQuery() {
		return recurQuery;
	}

	public void setRecurQuery(boolean recurQuery) {
		this.recurQuery = recurQuery;
	}

	public String getLevelBelong() {
		return levelBelong;
	}

	public void setLevelBelong(String levelBelong) {
		this.levelBelong = levelBelong;
	}

	public boolean isAsyncLoad() {
		return asyncLoad;
	}

	public void setAsyncLoad(boolean asyncLoad) {
		this.asyncLoad = asyncLoad;
	}

	public boolean isParent() {
		return isParent;
	}

	public void setParent(boolean isParent) {
		this.isParent = isParent;
	}

	public boolean isHalfCheck() {
		return halfCheck;
	}

	public void setHalfCheck(boolean halfCheck) {
		this.halfCheck = halfCheck;
	}

	public boolean isChecked() {
		return checked;
	}

	public void setChecked(boolean checked) {
		this.checked = checked;
	}

	public boolean isDefaultSelect() {
		return defaultSelect;
	}

	public void setDefaultSelect(boolean defaultSelect) {
		this.defaultSelect = defaultSelect;
	}

	public boolean isDefaultOpen() {
		return defaultOpen;
	}

	public void setDefaultOpen(boolean defaultOpen) {
		this.defaultOpen = defaultOpen;
	}

	public boolean isQueryOpen() {
		return queryOpen;
	}

	public void setQueryOpen(boolean queryOpen) {
		this.queryOpen = queryOpen;
	}

	public boolean isLock() {
		return lock;
	}

	public void setLock(boolean lock) {
		this.lock = lock;
	}

	public boolean isOpenParent() {
		return isOpenParent;
	}

	public void setOpenParent(boolean isOpenParent) {
		this.isOpenParent = isOpenParent;
	}

	public String getEqualBy() {
		return equalBy;
	}

	public void setEqualBy(String equalBy) {
		this.equalBy = equalBy;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getpId()
	{
		return pId;
	}

	public void setpId(String pId)
	{
		this.pId = pId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public List<Node> getChildren() {
		return children;
	}

	public void setChildren(List<Node> children) {
		this.children = children;
	}

	public boolean isOpen() {
		return open;
	}

	public void setOpen(boolean open) {
		this.open = open;
	}

	public String getIsValid() {
		return isValid;
	}

	public void setIsValid(String isValid) {
		this.isValid = isValid;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getTargetId()
	{
		return targetId;
	}

	public void setTargetId(String targetId)
	{
		this.targetId = targetId;
	}

	public String getMoveType()
	{
		return moveType;
	}

	public void setMoveType(String moveType)
	{
		this.moveType = moveType;
	}
	
	public Node copyNode( Map<String, List<Node>> nodeMap , String limitLevel)
	{
		Node node = new Node();
		
		// 属性拷贝
		node.setId( this.getId() );
		node.setName( this.getName() );
		node.setLevel( this.getLevel() );
		node.setIsValid( this.getIsValid() );
		node.setpId( this.getpId() );
		
		if( Tool.CHECK.isBlank(limitLevel) )
		{
			
			// 子类拷贝
			if( !Tool.CHECK.isEmpty( this.getChildren() )  &&  this.getChildren().size() > 0  )
			{
				node.setChildren( new ArrayList<Node>() );
				for( int i = 0; i < this.getChildren().size(); i++ )
				{
					node.getChildren().add( this.getChildren().get(i).copyNode( nodeMap, limitLevel ) );
				}
			}
			
		}
		else
		{
			
			// 叶子节点限定
			if( node.getLevel().compareTo(limitLevel) < 0 )
			{
				// 子类拷贝
				if( !Tool.CHECK.isEmpty( this.getChildren() )  &&  this.getChildren().size() > 0  )
				{
					node.setChildren( new ArrayList<Node>() );
					for( int i = 0; i < this.getChildren().size(); i++ )
					{
						node.getChildren().add( this.getChildren().get(i).copyNode( nodeMap, limitLevel ) );
					}
				}
			}
						
		}
		
		if( null != nodeMap )
		{
			if( !nodeMap.containsKey( node.getId() ) )
			{
				List<Node> list = new ArrayList<Node>();
				list.add(node);
				nodeMap.put( node.getId(), list);
			}
			else
			{
				nodeMap.get(node.getId()).add(node);
			}
		}
		
		return node;
	}
	
	public Node()
	{
		
	}
	
	
	public Node(String id, String name, String pId, String lvl){
		this.id = id;
		this.name = name;
		this.pId = pId;
		this.level = lvl;
	}
	
	public void show()
	{
		System.out.println("id-name-pId-level:" + this.getId() +  "-" + this.getName() + "-" + this.getpId() + "-" + this.getLevel());
		if( !Tool.CHECK.isEmpty( this.getChildren() ) )
		{
			for( int i = 0; i < this.getChildren().size(); i++)
			{
				this.getChildren().get(i).show();
			}
		}
	}

	@Override
	public int compareTo(Node o) {	
		return this.getId().compareTo( o.getId() );
	}


}
