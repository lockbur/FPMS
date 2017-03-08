package com.forms.prms.web.sysmanagement.org.controller;


import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.forms.platform.core.util.Tool;
import com.forms.prms.web.sysmanagement.org.cachebean.CachedZtree;
import com.forms.prms.web.sysmanagement.org.domain.BaseBean;
import com.forms.prms.web.sysmanagement.org.domain.Node;

public class DutyBeanToNode {

	
	private CachedZtree cachedZtree = null;
	
	private BaseBean baseBean = null;
	
	private Map<String, List<Node>> copyNodeMap = new HashMap<String, List<Node>>();
	
	private List<Node> halfList = new ArrayList<Node>();
	
	private final int asyncMax = 10000;
	
	private int asyncCancel = 0;
	
	private void setAsyncCancel(int asyncCancel)
	{
		this.asyncCancel = asyncCancel;
	}
	
	private int getAsynceCancel()
	{
		return this.asyncCancel;
	}
	
	private int getAsyncMax()
	{
		return this.asyncMax;
	}
	
	private CachedZtree getCachedZtree() {
		return cachedZtree;
	}

	private void setCachedZtree(CachedZtree cachedZtree) 
	throws Exception
	{
		if( Tool.CHECK.isEmpty( cachedZtree ) )
		{
			throw new Exception("多功能树控件因传入空对象而处理失败！！");
		}
		else
		{
			this.cachedZtree = cachedZtree;
		}
	}

	private BaseBean getBaseBean() {
		return baseBean;
	}

	private void setBaseBean(BaseBean baseBean) 
	throws Exception
	{
		if( Tool.CHECK.isEmpty( baseBean ) )
		{
			throw new Exception("多功能树控件因传入空对象而处理失败！！");
		}
		else
		{
			this.baseBean = baseBean;
			String leafLevel = this.baseBean.getLeafLevel();
			// 显示叶子等级限定
			if( !Tool.CHECK.isBlank( leafLevel ) )
			{
				int level = Integer.parseInt(leafLevel);
				// 叶子等级出错时，默认不起作用，即空
				if( level < 0 )
				{
					this.baseBean.setLeafLevel( null );
				}				
			}
		}
	}
	
	private Map<String , List<Node>> getCopyNodeMap()
	{
		return this.copyNodeMap;
	}
	
	private List<Node> getHalfList()
	{
		return this.halfList;
	}

	public DutyBeanToNode( CachedZtree cachedZtree, BaseBean baseBean)
	throws Exception
	{
		this.setCachedZtree(cachedZtree);
		this.setBaseBean(baseBean);
		
	}
	
	/**
	 * 返回检索字段
	 * @return
	 */
	public List<String> getQueryValue()
	{
		if( "1".equals( this.getBaseBean().getTreeType() ) )
		{
			return getQueryValueForOu();
		}
		else
		{
			return getQueryValueForOrg();
		}
	}
	
	private List<String> getQueryValueForOrg()
	{
		List<Node> nodeList = null;
		int runType = -1;
		
		if( Tool.CHECK.isEmpty(nodeList) )
		{
			nodeList = searchByIdForMap( this.getCachedZtree().getOrgDutyMap() );
			runType = 2;
		}
		
		if( Tool.CHECK.isEmpty(nodeList) )
		{
			nodeList = searchByIdForMap( this.getCachedZtree().getOrgMap() );
			runType = 1;
		}
		
		if( Tool.CHECK.isEmpty(nodeList) )
		{
			nodeList = searchByIdForMap( this.getCachedZtree().getOrgSecodeMap() );
			runType = 0;
		}
		
		if( Tool.CHECK.isEmpty(nodeList) )
		{
			nodeList = searchByIdForMap( this.getCachedZtree().getOrgFirstMap() );
			runType = -1;
		}
		
		return getValueForNodeListOrg(nodeList, runType);
	}
	
	private List<String> getQueryValueForOu()
	{
		List<Node> nodeList = null;
		int runType = -1;
		
		if( Tool.CHECK.isEmpty(nodeList) )
		{
			nodeList = searchByIdForMap( this.getCachedZtree().getOuDutyMap() );
			runType = 2;
		}
		
		if( Tool.CHECK.isEmpty(nodeList) )
		{
			nodeList = searchByIdForMap( this.getCachedZtree().getOuMap() );
			runType = 1;
		}
		
		if( Tool.CHECK.isEmpty(nodeList) )
		{
			nodeList = searchByIdForMap( this.getCachedZtree().getOuSecodeMap() );
			runType = 0;
		}
		
		if( Tool.CHECK.isEmpty(nodeList) )
		{
			nodeList = searchByIdForMap( this.getCachedZtree().getOuFirstMap() );
			runType = -1;
		}
		
		return getValueForNodeListOu(nodeList, runType);
	}
	
	
	private List<String> getValueForNodeListOrg(List<Node> dataList, int runType)
	{
		List<String> strList = null;
		if( !Tool.CHECK.isEmpty(dataList) )
		{
			strList = new ArrayList<String>();
			for( int i = 0; i < dataList.size(); i++)
			{
				strList.add( getValueForNodeOrg(dataList.get(i), runType) );
			}
		}
		return strList;
	}
	
	private List<String> getValueForNodeListOu(List<Node> dataList, int runType)
	{
		List<String> strList = null;
		if( !Tool.CHECK.isEmpty(dataList) )
		{
			strList = new ArrayList<String>();
			for( int i = 0; i < dataList.size(); i++)
			{
				strList.add( getValueForNodeOu(dataList.get(i), runType) );
			}
		}
		return strList;
	}
	
	private String getValueForNodeOrg(Node node, int runType)
	{
		String tempStr = null;
		if( !Tool.CHECK.isEmpty(node) )
		{
			tempStr = node.getId();
			Node search  =  null;
			
			if( runType == 2 )
			{
				search = this.getCachedZtree().getOrgMap().get( node.getpId() );
				if( !Tool.CHECK.isEmpty(search) )
				{
					tempStr = getValueForNodeOrg(search, runType-1) +  "-" +  tempStr;
				}
				 
			}
			else if( runType == 1 )
			{
				search = this.getCachedZtree().getOrgSecodeMap().get( node.getpId() );
				if( !Tool.CHECK.isEmpty(search) )
				{
					tempStr = getValueForNodeOrg(search, runType-1) +  "-" +  tempStr;
				}
				else
				{
					search = this.getCachedZtree().getOrgFirstMap().get( node.getpId() );
					tempStr = getValueForNodeOrg(search, runType-2) + "-" + tempStr;
				}
			}
			else if( runType == 0 )
			{
				search = this.getCachedZtree().getOrgFirstMap().get( node.getpId() );
				if( !Tool.CHECK.isEmpty(search) )
				{
					tempStr = getValueForNodeOrg(search, runType-1) + "-" + tempStr;
				}
			}
		}
		else
		{
			tempStr = "";
		}
		return tempStr;
	}
	
	private String getValueForNodeOu(Node node, int runType)
	{
		String tempStr = null;
		if( !Tool.CHECK.isEmpty(node) )
		{
			tempStr = node.getId();
			Node search  =  null;
			
			if( runType == 2 )
			{
				search = this.getCachedZtree().getOuMap().get( node.getpId() );
				if( !Tool.CHECK.isEmpty(search) )
				{
					tempStr += "-" + getValueForNodeOu(search, runType-1);
				}
				 
			}
			else if( runType == 1 )
			{
				search = this.getCachedZtree().getOuSecodeMap().get( node.getpId() );
				if( !Tool.CHECK.isEmpty(search) )
				{
					tempStr += "-" + getValueForNodeOu(search, runType-1);
				}
				else
				{
					search = this.getCachedZtree().getOuFirstMap().get( node.getpId() );
					tempStr += "-" + getValueForNodeOu(search, runType-2);
				}
			}
			else if( runType == 0 )
			{
				search = this.getCachedZtree().getOuFirstMap().get( node.getpId() );
				if( !Tool.CHECK.isEmpty(search) )
				{
					tempStr += "-" + getValueForNodeOu(search, runType-1);
				}
			}
		}
		else
		{
			tempStr = "";
		}
		return tempStr;
	}
	
	/**
	 * 搜索值
	 * @param nodeMap
	 * @return
	 */
	private List<Node> searchByIdForMap( Map<String, Node> nodeMap )
	{
		List<Node> nodeList = null;
		if( !Tool.CHECK.isEmpty(nodeMap) )
		{
			nodeList = new ArrayList<Node>();
			for (String key : nodeMap.keySet())
			{
				if( this.getBaseBean().isQueryById() )
				{
					if( this.getBaseBean().getQueryByValue().equals(key) )
					{
						nodeList.add( nodeMap.get(key) );
					}
				}
				else
				{
					if( !Tool.CHECK.isBlank( nodeMap.get(key).getName() ) && nodeMap.get(key).getName().indexOf( this.getBaseBean().getQueryByValue().trim() ) != -1 )
					{
						nodeList.add( nodeMap.get(key) );
					}
				}
			}
			
		}
		return nodeList;
	}
	
	/**
	 * 获取节点列表
	 * @param cachedZtree
	 * @param baseBean
	 * @return
	 */
	public List<Node> getNodeListFromCached()
	{
		List<Node> nodeList = null;
		long startTime = new Date().getTime();
		nodeList = getNodeList();
		long endTime = new Date().getTime();
		//System.out.println("缓存处理操作时间：" + (endTime - startTime)/ 1000.00 );
		return nodeList;
	}
	

	/**
	 *  半选处理列表
	 */
	private void initHalfList()
	{
		if( !Tool.CHECK.isEmpty( this.getHalfList() ) && !Tool.CHECK.isEmpty( this.getCopyNodeMap() ) )
		{
			for (int i = 0; i < this.getHalfList().size(); i++) 
			{
				initHaftDeal( this.getHalfList().get(i) );
			}
		}
	}
	
	/**
	 * 半选处理单个
	 * @param node
	 */
	private void initHaftDeal( Node node )
	{
		if( !Tool.CHECK.isEmpty( node.getChildren() ) )
		{
			for (int i = 0; i < node.getChildren().size(); i++)
			{
				if( !node.getChildren().get(i).isChecked() )
				{
					node.setHalfCheck(true);
					break;
				}
			}
		}
	}
	
	/**
	 * 初始化操作
	 */
	private void initNodeList()
	{
		if( !Tool.CHECK.isEmpty( this.getCopyNodeMap() ) )
		{
			if( !Tool.CHECK.isBlank( this.getBaseBean().getNodeId() ) )
			{
				if( this.getBaseBean().getNodeId().indexOf(",") == -1 )
				{
					initNode( this.getBaseBean().getNodeId() );
				}
				else
				{
					String[] nodeIds = this.getBaseBean().getNodeId().split(",");
					for (int i = 0; i < nodeIds.length; i++) 
					{
						initNode( nodeIds[i] );
					}
				}
			}
		}
	}
	
	/**
	 *  传入节点类型处理
	 * @param nodeId
	 */
	private void initNode( String nodeId )
	{
		if( !Tool.CHECK.isBlank(nodeId) )
		{
			if( nodeId.indexOf("-") == -1 )
			{
				initNodeById(nodeId, null, true);
			}
			else
			{
				String[] nodeIds = nodeId.split("-");
				initNodeById(nodeIds[0], nodeIds[1], true);
			}
		}
	}
	
	/**
	 * 根据节点和父类节点找到节点
	 * @param nodeId
	 * @param parentId
	 */
	private void initNodeById(String nodeId, String parentId, boolean isFirst)
	{
		Node node = null;
		
		if( this.getCopyNodeMap().containsKey(nodeId)  )
		{
			if( this.getCopyNodeMap().get(nodeId).size() == 1 )
			{
				node = this.getCopyNodeMap().get( nodeId ).get(0);
			}
			else if( Tool.CHECK.isBlank(parentId) )
			{
				
				// 首先查询ip和pIp相同的
				for(int i = 0; i < this.getCopyNodeMap().get(nodeId).size(); i++ )
				{
					if( nodeId.equals( this.getCopyNodeMap().get(nodeId).get(i).getpId() ) )
					{
						node = this.getCopyNodeMap().get(nodeId).get(i);
						break;
					}
				}
				
				// 再次查询ip和pId不同的
				if( node == null || node.isRecurQuery() )
				{
					for(int i = 0; i < this.getCopyNodeMap().get(nodeId).size(); i++ )
					{
						if( !nodeId.equals( this.getCopyNodeMap().get(nodeId).get(i).getpId() ) )
						{
							node = this.getCopyNodeMap().get(nodeId).get(i);
							break;
						}
					}
				}
				
			}
			else if( !Tool.CHECK.isBlank(parentId) )
			{
				for(int i = 0; i < this.getCopyNodeMap().get(nodeId).size(); i++ )
				{
					if( this.getCopyNodeMap().get(nodeId).get(i).getpId().equals( parentId )  )
					{
						node = this.getCopyNodeMap().get(nodeId).get(i);
					}
				}
			}
			
			// 标识本节点递归查询
			if( !Tool.CHECK.isEmpty(node) && !node.isRecurQuery() )
			{
				node.setRecurQuery(true);
			}
			else
			{
				node.setRecurQuery(false);
				node = null;
			}
			
			
			if( !Tool.CHECK.isEmpty(node) )
			{
				if( isFirst )
				{
					node.setOpenParent(true);
					node.setDefaultSelect(true);
					node.setChecked(true);
				}
				else
				{
					
					if( !node.isOpen() )
					{
						node.setOpen(true);
						node.setDefaultOpen(true);
						// 单选时，不进行父子节点勾联效果
						if( !this.getBaseBean().isRadioFlag() )
						{
							node.setChecked(true);
							halfList.add( node );
						}
					}
					
				}
				initNodeById(node.getpId(), null, false);
			}
			
		}
	}
	
	/**
	 * 查询节点列表（主要考虑查询根节点是多个值还是单个）
	 * @param cachedZtree
	 * @param baseBean
	 * @return
	 */
	private  List<Node> getNodeList()
	{
		List<Node> nodeList =  null;
		
		if( Tool.CHECK.isBlank( this.getBaseBean().getRootNodeId() ) )
		{
			// 当查询根节点为空时
			nodeList = getNodeListBySearch( null );
		}
		else
		{
			// 查询根节点不为空，且为单个值时
			if( this.getBaseBean().getRootNodeId().indexOf(",") == -1  )
			{
				nodeList = getNodeListBySearch( this.getBaseBean().getRootNodeId() );
			}
			// 查询根节点不为空，且是多个值时
			else
			{
				nodeList = new ArrayList<Node>();
				String[] nodeIds = this.getBaseBean().getRootNodeId().split(",");
				
				List<Node> nodeIdList =  getRootNodeAbleUseList( nodeIds );
				
				for( int i = 0; i < nodeIdList.size(); i++ )
				{
					List<Node> tempList = getNodeListBySearch( nodeIdList.get(i).getId() );
					if( !Tool.CHECK.isEmpty(tempList) )
					{
						nodeList.addAll( tempList );
					}
				}			
			}
		}
		
		//leafLevelLimit(nodeList, false);
		
		if( !Tool.CHECK.isEmpty( this.getCopyNodeMap() ) )
		{
			// 排序处理
			Collections.sort(nodeList);
			
			// 初始化勾选处理
			initNodeList();
			
			// 半选处理
			initHalfList();
		}
		
		if( this.getBaseBean().isAsync() )
		{
			// 异步加载
			if( this.getCopyNodeMap().size() > this.getAsyncMax() )
			{
				asyncDeal(nodeList);
			}
		}
			
		return nodeList;
	}
	
	/**
	 * @param dataList
	 */
	private void asyncDeal( List<Node> dataList)
	{
		if( !Tool.CHECK.isEmpty(dataList) )
		{
			for( int i = 0; i < dataList.size(); i++)
			{
				this.setAsyncCancel( this.getAsynceCancel() + 1 );
				if( !Tool.CHECK.isEmpty( dataList.get(i).getChildren() ) )
				{
					if( !dataList.get(i).isChecked() )
					{
						dataList.get(i).setParent(true);
						
						if( this.getAsynceCancel() > this.getAsyncMax() )
						{
							dataList.get(i).setAsyncLoad(true);
							dataList.get(i).setChildren(null);
						}
						else
						{
							asyncDeal( dataList.get(i).getChildren() );
						}
					}
					else
					{
						asyncDeal(dataList.get(i).getChildren());
					}
				}
			}
		}
	}
	
	/**
	 * 考虑根据用户查询的情况
	 * @param cachedZtree
	 * @param baseBean
	 * @param nodeId
	 * @return
	 */
	private  List<Node> getNodeListBySearch( String nodeId )
	{
		List<Node> nodeList = null;
		
		// 当查询根节点等级为空时
		if( Tool.CHECK.isBlank( this.getBaseBean().getRootLevel() ) )
		{
			// 当查询根节点id和等级都为空时，按登录用户查询
			if( Tool.CHECK.isBlank( nodeId ) )
			{
				nodeList = getNodeListByIdAndLevel( this.getBaseBean().getLoginUserNodeId(), null );
			}
			// 当查询根节点id不为空时
			else
			{
				nodeList = getNodeListByIdAndLevel( nodeId, null );
			}
		}
		// 当查询根节点等级不为空时
		else
		{
			nodeList = getNodeListByIdAndLevel( nodeId, this.getBaseBean().getRootLevel() );
		}

		return  copyNodeList( nodeList );
	}
	
	/**
	 *  分org和ou 搜索
	 * @param cachedZtree
	 * @param baseBean
	 * @param nodeId
	 * @param nodeLevel
	 * @return
	 */
	private  List<Node> getNodeListByIdAndLevel( String nodeId , String nodeLevel )
	{
		List<Node> nodeList = null;
		nodeLevel = changeNodeLevelForDefault( nodeLevel );
	
		if( "1".equals( this.getBaseBean().getTreeType() ) )
		{
			nodeList = searchOuCachedMap( nodeId, nodeLevel);
		}
		else
		{
			nodeList = searchOrgCachedMap( nodeId, nodeLevel);
		}	
		
		return nodeList;
	}
	
	

	/**
	 *  查询根节点
	 * @param nodeId
	 * @return
	 */
	private  Node getRootNodeIdNode( String nodeId )
	{
		Node node = null;
		if( "1".equals( this.getBaseBean().getTreeType() ) )
		{
			node = searchForOuNodeById(nodeId, 0);
		}
		else
		{
			node = searchForOrgNodeById(nodeId, 0);
		}	
		return node;
	}
	
	/**
	 * 查询根节点有用列表
	 * @param nodeIds
	 * @return
	 */
	private List<Node> getRootNodeAbleUseList( String[] nodeIds )
	{
		List<Node> dataList = null;
		if( !Tool.CHECK.isEmpty(nodeIds) )
		{
			dataList = new ArrayList<Node>();
			Node node = null;
			for( int i = 0; i < nodeIds.length; i++)
			{
				node = getRootNodeIdNode(nodeIds[i]);
				if( !Tool.CHECK.isEmpty(dataList) )
				{
					if( dataList.get(0).getLevel().compareTo( node.getLevel() )  > 0 )
					{
						dataList.clear();
						dataList.add(node);
					}
					else if( dataList.get(0).getLevel().compareTo( node.getLevel() ) == 0 )
					{
						dataList.add(node);
					}
				}
				else
				{
					dataList.add(node);
				}
			}
		}
		return dataList;
	}
	
	/**
	 * org节点查询
	 * @param cachedZtree
	 * @param nodeId
	 * @param nodeLevel
	 * @return
	 */
	private  List<Node> searchOrgCachedMap( String nodeId, String nodeLevel)
	{
		List<Node> nodeList = null;
		Node node = null;
		Map<String, Node> nodeMap = null;
		// 查询等级为空时
		if( Tool.CHECK.isBlank( nodeLevel ) )
		{
			// 查询节点id不为空
			if( !Tool.CHECK.isBlank( nodeId ) )
			{
				node = searchForOrgNodeById( nodeId, 0 );
				
				if( !Tool.CHECK.isEmpty(node) )
				{
					nodeList = new ArrayList<Node>();
					if( this.getBaseBean().isAsyncLoad() )
					{
						if( !Tool.CHECK.isEmpty( node.getChildren() ) )
						{
							nodeList.addAll( node.getChildren() );
						}
					}
					else
					{
						nodeList.add( node );
					}
				}
			}
		}
		// 查询等级不为空时
		else
		{
			// 查询节点id为空时
			if( Tool.CHECK.isBlank( nodeId ) )
			{
				nodeMap = searchForOrgNodeByLevel( nodeLevel );
				
				if( !Tool.CHECK.isEmpty(nodeMap) )
				{
					nodeList = new ArrayList<Node>();
					nodeList.addAll( nodeMap.values() );
				}
			}
			// 查询节点id不为空时
			else
			{
				node = searchForOrgNodeById( nodeId, 0 );
				
				if( !Tool.CHECK.isEmpty(node) )
				{
					nodeList = new ArrayList<Node>();
					nodeList.add( node );
					nodeList = getNodeListByLevel( nodeList, nodeLevel);
				}
			}
		}
		return nodeList;
	}
	
	/**
	 * ou 节点查询
	 * @param cachedZtree
	 * @param nodeId
	 * @param nodeLevel
	 * @return
	 */
	private  List<Node> searchOuCachedMap( String nodeId, String nodeLevel )
	{
		List<Node> nodeList = null;
		Node node = null;
		Map<String, Node> nodeMap = null;
		// 查询等级为空时
		if( Tool.CHECK.isBlank( nodeLevel ) )
		{
			// 查询节点id不为空
			if( !Tool.CHECK.isBlank( nodeId ) )
			{
				node = searchForOuNodeById( nodeId , 0);
				if( !Tool.CHECK.isEmpty(node) )
				{
					nodeList = new ArrayList<Node>();
					if( this.getBaseBean().isAsyncLoad() )
					{
						if( !Tool.CHECK.isEmpty( node.getChildren() ) )
						{
							nodeList.addAll( node.getChildren() );
						}
					}
					else
					{
						nodeList.add( node );
					}
				}
			}
		}
		// 查询等级不为空时
		else
		{
			// 查询节点id为空时
			if( Tool.CHECK.isBlank( nodeId ) )
			{
				nodeMap = searchForOuNodeByLevel( nodeLevel );
				
				if( !Tool.CHECK.isEmpty(nodeMap) )
				{
					nodeList = new ArrayList<Node>();
					nodeList.addAll( nodeMap.values() );
				}
			}
			// 查询节点id不为空时
			else
			{
				node = searchForOuNodeById( nodeId, 0 );	
				
				if( !Tool.CHECK.isEmpty(node) )
				{
					nodeList = new ArrayList<Node>();
					nodeList.add( node );
					nodeList = getNodeListByLevel( nodeList, nodeLevel);
				}
			}
		}
		return nodeList;
	}
	
	/**
	 *  根据id 搜索机构节点
	 * @param cachedZtree
	 * @param nodeId
	 * @return
	 */
	private  Node searchForOrgNodeById( String nodeId , int runType)
	{
		Node node = null;
		
		if( runType <= 0 )
		{
			if( Tool.CHECK.isEmpty(node) )
			{
				// 一级搜索
				node = searchForMapById ( this.getCachedZtree().getOrgFirstMap(), nodeId);
			}
		}
		
		if( runType <= 1 )
		{
			// 二级搜索
			if( Tool.CHECK.isEmpty(node) )
			{
				node = searchForMapById( this.getCachedZtree().getOrgSecodeMap(), nodeId);
			}
		}
		
		if( runType <= 2 )
		{
			// org 搜索
			if( Tool.CHECK.isEmpty(node) )
			{
				node = searchForMapById( this.getCachedZtree().getOrgMap(), nodeId);
			}
		}
		
		if( runType <= 3 )
		{
			// 责任 搜索
			if( Tool.CHECK.isEmpty(node) )
			{
				node = searchForMapById( this.getCachedZtree().getOrgDutyMap(), nodeId);
			}
		}
		
		return node;
	}
	
	/**
	 *  根据等级搜索org节点
	 * @param cachedZtree
	 * @param nodeLevel
	 * @return
	 */
	private  Map<String, Node> searchForOrgNodeByLevel( String nodeLevel )
	{
		Map<String, Node> nodeMap = null;
		
		if( Tool.CHECK.isBlank( nodeLevel ) )
		{
			nodeMap = this.getCachedZtree().getOrgFirstMap();
		}
		else if( "1".equals( nodeLevel ) )
		{
			nodeMap = this.getCachedZtree().getOrgSecodeMap();
		}
		else  if( "2".equals(nodeLevel) )
		{
			nodeMap = this.getCachedZtree().getOrgMap();
		}
		else if( "3".equals(nodeLevel) )
		{
			nodeMap = this.getCachedZtree().getOrgDutyMap();
		}
		else
		{
			nodeMap = this.getCachedZtree().getOrgFirstMap();
		}			
		return nodeMap;
	}
	
	/**
	 * 根据id搜索ou节点
	 * @param cachedZtree
	 * @param nodeId
	 * @return
	 */
	private  Node searchForOuNodeById( String nodeId , int runType)
	{
		Node node = null;
		
		if( runType <= 0 )
		{
			if( Tool.CHECK.isEmpty(node)  )
			{
				// 一级搜索
				node = searchForMapById ( this.getCachedZtree().getOuFirstMap(), nodeId);
			}
		}
		
		if( runType <= 1 )
		{
			// 二级搜索
			if( Tool.CHECK.isEmpty(node) )
			{
				node = searchForMapById( this.getCachedZtree().getOuSecodeMap(), nodeId);
			}
		}
		
		if( runType <= 2 )
		{
			// ou 搜索
			if( Tool.CHECK.isEmpty(node) )
			{
				node = searchForMapById( this.getCachedZtree().getOuMap(), nodeId);
			}
		}
		
		if( runType <= 3 )
		{
			// 责任 搜索
			if( Tool.CHECK.isEmpty(node) )
			{
				node = searchForMapById( this.getCachedZtree().getOuDutyMap(), nodeId);
			}
		}
		
		return node;
	}
	
	/**
	 * 根据等级搜索ou节点
	 * @param cachedZtree
	 * @param nodeLevel
	 * @return
	 */
	private  Map<String, Node> searchForOuNodeByLevel( String nodeLevel )
	{
		Map<String, Node> nodeMap = null;
		
		if( Tool.CHECK.isBlank( nodeLevel ) )
		{
			nodeMap = this.getCachedZtree().getOuFirstMap();
		}
		else if( "1".equals( nodeLevel ) )
		{
			nodeMap = this.getCachedZtree().getOuSecodeMap();
		}
		else  if( "2".equals(nodeLevel) )
		{
			nodeMap = this.getCachedZtree().getOuMap();
		}
		else if( "3".equals(nodeLevel) )
		{
			nodeMap = this.getCachedZtree().getOuDutyMap();
		}
		else
		{
			nodeMap = this.getCachedZtree().getOrgFirstMap();
		}			
		return nodeMap;
	}
	
	/**
	 * 指定map搜索节点
	 * @param dataMap
	 * @param nodeId
	 * @return
	 */
	private  Node searchForMapById( Map<String, Node> dataMap, String nodeId )
	{
		Node node = null;
		if( !Tool.CHECK.isBlank( nodeId ) )
		{
			if( dataMap.containsKey( nodeId ) )
			{
				node = dataMap.get( nodeId );
			}
		}
		return node;
	}
	
	
	/**
	 * List<Node> 拷贝
	 * @param dataList
	 * @return
	 */
	private  List<Node> copyNodeList( List<Node> dataList )
	{
		List<Node> nodeList = null;
		if( !Tool.CHECK.isEmpty( dataList ) )
		{
			nodeList = new ArrayList<Node>();
			for( int i = 0; i < dataList.size(); i++)
			{
				nodeList.add( dataList.get(i).copyNode( this.getCopyNodeMap(), this.getBaseBean().getLeafLevel() ) );
			}
		}
		return nodeList;
	}
	
	/**
	 *  叶子等级限定
	 * @param dataList
	 * @param isLimit
	 */
	private  void  leafLevelLimit( List<Node> dataList , boolean isLimit)
	{
		if( !Tool.CHECK.isBlank( this.getBaseBean().getLeafLevel() ) )
		{
			if( !Tool.CHECK.isEmpty( dataList ) )
			{
				for( int i = 0; i < dataList.size(); i++ )
				{
					if( isLimit )
					{
						if( dataList.get(i).getLevel().compareTo( this.getBaseBean().getLeafLevel() ) < 0 )
						{
							leafLevelLimit( dataList.get(i).getChildren(), true);
						}
						else
						{
							dataList.get(i).setChildren(null);
						}
					}
					else
					{
						if( dataList.get(i).getLevel().compareTo( this.getBaseBean().getLeafLevel() ) < 0 )
						{
							leafLevelLimit( dataList.get(i).getChildren(), true);
						}
						else
						{
							dataList.get(i).setChildren(null);
						}
					}
				}
			}
		}
	}
	/**
	 * 等级限定
	 * @param nodeLevel
	 * @return
	 */
	private  String changeNodeLevelForDefault( String nodeLevel )
	{	
		if( Tool.CHECK.isBlank(nodeLevel) )
		{
			nodeLevel = null;
		}
		else if( "1".equals(nodeLevel) )
		{
			nodeLevel = "1";
		}
		else if( "2".equals(nodeLevel) )
		{
			nodeLevel = "2";
		}
		else if( "3".equals(nodeLevel) )
		{
			nodeLevel = "3";
		}
		else
		{
			nodeLevel = "0";
		}
		return nodeLevel;
	}
	/**
	 * 根据等级搜索list的等级
	 * @param dataList
	 * @param nodeLevel
	 * @return
	 */
	private  List<Node> getNodeListByLevel( List<Node> dataList, String nodeLevel )
	{
		List<Node> nodeList = null;
		if( !Tool.CHECK.isBlank( nodeLevel ) )
		{
			if( !Tool.CHECK.isEmpty( dataList ) && dataList.size() > 0 )
			{
				if( dataList.get(0).getLevel().compareTo( nodeLevel ) >= 0 )
				{
					nodeList = dataList;
				}
				else
				{
					nodeList = new ArrayList<Node>();
					
					for( int i = 0; i < dataList.size(); i++)
					{
						if( !Tool.CHECK.isEmpty( dataList.get(i).getChildren() ) )
						{
							nodeList.addAll( getNodeListByLevel( dataList.get(i).getChildren(), nodeLevel) );
						}
					}
				}
			}
			return nodeList;
		}
		else
		{
			return dataList;
		}
	}
}