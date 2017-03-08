package com.forms.prms.web.sysmanagement.org.cachebean;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import com.forms.platform.core.util.Tool;
import com.forms.prms.web.sysmanagement.org.domain.Node;
import com.sun.star.uno.Exception;

public class CachedZtree implements Serializable{

	private static final long serialVersionUID = 8969125616977265390L;
	
	public static CachedZtree catchedZtree;
	
	// 一级分行存储对象--机构
	private Map<String, Node> orgFirstMap = new HashMap<String, Node>();
	
	// 二级分行存储对象--机构
	private Map<String, Node> orgSecodeMap = new HashMap<String, Node>();
	
	// 一级分行存储对象--ou
	private Map<String, Node> ouFirstMap = new HashMap<String, Node>();
	
	// 二级分行存储对象--ou
	private Map<String, Node> ouSecodeMap = new HashMap<String, Node>();
	
	// 机构存储对象--机构
	private Map<String, Node> orgMap = new HashMap<String, Node>();;
	
	// ou存储对象--ou
	private Map<String, Node> ouMap = new HashMap<String, Node>();;
	
	// 责安中心存储对象--机构
	private Map<String, Node> orgDutyMap = new HashMap<String, Node>();
	
	// 责安中心存储对象--ou
	private Map<String, Node> ouDutyMap = new HashMap<String, Node>();
	
	private CachedZtree()
	{
		
	}
	
	public static CachedZtree getCachedZtree()
	{
		if( catchedZtree == null )
		{
			catchedZtree = new CachedZtree();
		}
		return catchedZtree;
	}

	public Map<String, Node> getOrgFirstMap() {
		return this.orgFirstMap;
	}
	
	/**
	 * @param node
	 */
	public void setOrgFirstMapNode( Node node )
	throws Exception
	{
		this.mapAddNode( this.getOrgFirstMap(), node);
	}
	
	public Map<String, Node> getOrgSecodeMap() {
		return this.orgSecodeMap;
	}
	
	/**
	 * @param node
	 */
	public void setOrgSecodeMapNode( Node node )
	throws Exception
	{
		this.mapAddNode( this.getOrgSecodeMap(), node);
	}

	public Map<String, Node> getOuFirstMap(){
		return this.ouFirstMap;
	}
	
	/**
	 * @param node
	 */
	public void setOuFirstMapNode( Node node )
	throws Exception
	{
		this.mapAddNode( this.getOuFirstMap(), node);
	}
	
	
	public Map<String, Node> getOuSecodeMap(){
		return this.ouSecodeMap;
	}
	
	/**
	 * @param node
	 */
	public void setOuSecodeMapNode( Node node )
	throws Exception
	{
		this.mapAddNode( this.getOuSecodeMap(), node);
	}

	public Map<String, Node> getOrgMap() {
		return orgMap;
	}
	
	/**
	 * @param node
	 */
	public void setOrgMapNode( Node node )
	throws Exception
	{
		this.mapAddNode( this.getOrgMap(), node);
	}

	public Map<String, Node> getOuMap() {
		return ouMap;
	}
	
	/**
	 * @param node
	 */
	public void setOuMapNode( Node node )
	throws Exception
	{
		this.mapAddNode( this.getOuMap(), node);
	}

	public Map<String, Node> getOrgDutyMap() {
		return orgDutyMap;
	};
	
	/**
	 * @param node
	 */
	public void setOrgDutyMapNode( Node node )
	throws Exception
	{
		this.mapAddNode( this.getOrgDutyMap(), node);
	}
	
	public Map<String, Node> getOuDutyMap() {
		return ouDutyMap;
	};
	
	/**
	 * @param node
	 */
	public void setOuDutyMapNode( Node node )
	throws Exception
	{
		this.mapAddNode( this.getOuDutyMap(), node);
	}
	
	private void mapAddNode( Map<String, Node> dataMap , Node node)
	throws Exception
	{
		if( null == dataMap )
		{
			throw new Exception("dataMap为空，多功能树缓存处理失败！！");
		}
		if( !Tool.CHECK.isEmpty(node)  && !Tool.CHECK.isBlank(node.getId()) )
		{
			if( !dataMap.containsKey( node.getId() ) )
			{
				dataMap.put( node.getId(), node);
			}
		}
	}
}