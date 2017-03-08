package com.forms.prms.web.sysmanagement.org.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.forms.platform.core.util.Tool;
import com.forms.prms.web.sysmanagement.org.cachebean.CachedZtree;
import com.forms.prms.web.sysmanagement.org.domain.BaseBean;
import com.forms.prms.web.sysmanagement.org.domain.Duty;
import com.forms.prms.web.sysmanagement.org.domain.Node;
import com.sun.star.uno.Exception;


public class DutyToolService {
	
	
	
	private CachedZtree cachedZtree = null;
	
	private List<BaseBean> dataList;
	
	private void setCachedZtree( CachedZtree cachedZtree )
	{
		this.cachedZtree = cachedZtree;
	}
	
	public CachedZtree getCachedZtree()
	{
		return this.cachedZtree;
	}
	
	private void setDataList( List<BaseBean> dataList )
	{
		this.dataList = dataList;
	}
	
	private List<BaseBean> getDataList()
	{
		return this.dataList;
	}
	
	public DutyToolService( List<BaseBean> dataList )
	throws Exception
	{
		if( Tool.CHECK.isEmpty(dataList) )
		{
			throw new Exception("责任中心处理类中，处理数据为空！！");
		}
		this.setDataList( dataList );
		CachedZtree.catchedZtree = null;
		this.setCachedZtree( CachedZtree.getCachedZtree() );
		this.setInitMapToCatchedZtree();
	}
	/**
	 * 
	 * @param dataList
	 * @return
	 * @throws Exception
	 */
	private void setInitMapToCatchedZtree()
	throws Exception
	{
		// 获取dutyMap
		getDutyForMap();
		
		// 获取orgMap
		getOrgForMap();
		
		// 获取 ouMap
		getOuForMap();
		
		// 获取 orgSecodeMap
		getOrgSecodeForMap();
		
		// 获取 ouSecodeMap
		getOuSecodeForMap();
		
		// 获取 orgFirstMap
		getOrgFirstForMap();
		
		// 获取 ouFirstMap
		getOuFirstForMap();
		
		// 获取子类信息
		getChildrenForMap( this.getCachedZtree().getOrgDutyMap(), this.getCachedZtree().getOrgMap() );
		
		getChildrenForMap( this.getCachedZtree().getOuDutyMap(), this.getCachedZtree().getOuMap() );
		
		getChildrenForMap( this.getCachedZtree().getOrgMap(), this.getCachedZtree().getOrgSecodeMap(), this.getCachedZtree().getOrgFirstMap() );
		
		getChildrenForMap( this.getCachedZtree().getOuMap(), this.getCachedZtree().getOuSecodeMap(), this.getCachedZtree().getOuFirstMap() );
		
		getChildrenForMap( this.getCachedZtree().getOrgSecodeMap(), this.getCachedZtree().getOrgFirstMap() );
		
		getChildrenForMap( this.getCachedZtree().getOuSecodeMap(), this.getCachedZtree().getOuFirstMap() );
		
	}

	/**
	 * @param dataList
	 * @throws Exception
	 */
	private void getDutyForMap()
	throws Exception
	{		
		for(int i = 0; i < this.getDataList().size(); i++)
		{
			if( this.getDataList().get(i).getClass().equals(Duty.class) )
			{
				Duty duty = ((Duty) this.getDataList().get(i));
				duty.getAllNodes();
				this.getCachedZtree().setOrgDutyMapNode( duty.getOrgDutyNode() );
				this.getCachedZtree().setOuDutyMapNode( duty.getOuDutyNode() );
			}
			else
			{
				throw new Exception("责任中心处理类中，传入非法类型参数");
			}
		}
	}
	
	/**
	 * @param dataList
	 * @throws Exception
	 */
	private void getOrgForMap()
	throws Exception
	{
		for(int i = 0; i < this.getDataList().size(); i++)
		{
			if( this.getDataList().get(i).getClass().equals( Duty.class ) )
			{
				Duty duty = ( (Duty) this.getDataList().get(i) );
				this.getCachedZtree().setOrgMapNode( duty.getOrgNode() );
			}
			else
			{
				throw new Exception("责任中心处理类中，传入非法类型参数");
			}
		}
	}
	
	/**
	 * @param dataList
	 * @throws Exception
	 */
	private void getOuForMap()
	throws Exception
	{
		for(int i = 0; i < this.getDataList().size(); i++)
		{
			if( this.getDataList().get(i).getClass().equals( Duty.class ) )
			{
				Duty duty = ( (Duty) this.getDataList().get(i) );
				this.getCachedZtree().setOuMapNode( duty.getOuNode() );
			}
			else
			{
				throw new Exception("责任中心处理类中，传入非法类型参数");
			}
		}
	}
	
	/**
	 * @param dataList
	 * @throws Exception
	 */
	private void getOrgSecodeForMap()
	throws Exception
	{		
		for(int i = 0; i < this.getDataList().size(); i++)
		{
			if( this.getDataList().get(i).getClass().equals( Duty.class ) )
			{
				Duty duty = ( (Duty) this.getDataList().get(i) );
				this.getCachedZtree().setOrgSecodeMapNode( duty.getOrgSecodeNode() );
			}
			else
			{
				throw new Exception("责任中心处理类中，传入非法类型参数");
			}
		}
	}
	
	/**
	 * @param dataList
	 * @throws Exception
	 */
	private void getOuSecodeForMap()
	throws Exception
	{		
		for(int i = 0; i < this.getDataList().size(); i++)
		{
			if( this.getDataList().get(i).getClass().equals( Duty.class ) )
			{
				Duty duty = ( (Duty) this.getDataList().get(i) );
				this.getCachedZtree().setOuSecodeMapNode( duty.getOuSecodeNode() );
			}
			else
			{
				throw new Exception("责任中心处理类中，传入非法类型参数");
			}
		}
	}
	
	
	/**
	 * @param dataList
	 * @throws Exception
	 */
	private void getOrgFirstForMap()
	throws Exception
	{		
		for(int i = 0; i < this.getDataList().size(); i++)
		{
			if( this.getDataList().get(i).getClass().equals( Duty.class ) )
			{
				Duty duty = ( (Duty) this.getDataList().get(i) );
				this.getCachedZtree().setOrgFirstMapNode( duty.getOrgFirstNode() );
			}
			else
			{
				throw new Exception("责任中心处理类中，传入非法类型参数");
			}
		}
	}
	
	/**
	 * @param dataList
	 * @throws Exception
	 */
	private void getOuFirstForMap()
	throws Exception
	{		
		for(int i = 0; i < this.getDataList().size(); i++)
		{
			if( this.getDataList().get(i).getClass().equals( Duty.class ) )
			{
				Duty duty = ( (Duty) this.getDataList().get(i) );
				this.getCachedZtree().setOuFirstMapNode( duty.getOuFirstNode() );
			}
			else
			{
				throw new Exception("责任中心处理类中，传入非法类型参数");
			}
		}
	}
	
	private void getChildrenForMap( Map<String, Node> childMap, Map<String, Node> parentMap)
	throws Exception
	{
		if( Tool.CHECK.isEmpty( childMap ) || Tool.CHECK.isEmpty( parentMap ) )
		{
			throw new Exception("责任中心处理类中，传入非法类型参数");
		}
		else
		{
			for (String key : childMap.keySet()) 
			{
				Node node = childMap.get(key);
				Node parent = null;
				
				if( parentMap.containsKey( node.getpId() ) )
				{
					parent = parentMap.get( node.getpId() );
					
					if( Tool.CHECK.isEmpty( parent.getChildren() ) )
					{
						parent.setChildren( new ArrayList<Node>() );
					}
					parent.getChildren().add( node );
				}
				
			}
		}
	}
	
	private void getChildrenForMap( Map<String, Node> childMap, Map<String, Node> parentMap, Map<String, Node> parentNextMap)
	throws Exception
	{
		if( Tool.CHECK.isEmpty( childMap ) || Tool.CHECK.isEmpty( parentMap ) || Tool.CHECK.isEmpty( parentNextMap ) )
		{
			throw new Exception("责任中心处理类中，传入非法类型参数");
		}
		else
		{
			for (String key : childMap.keySet()) 
			{
				Node node = childMap.get(key);
				Node parent = null;
				
				if( parentMap.containsKey( node.getpId() ) )
				{
					parent = parentMap.get( node.getpId() );
					if( Tool.CHECK.isEmpty( parent.getChildren() ) )
					{
						parent.setChildren( new ArrayList<Node>() );
					}
					parent.getChildren().add( node );
				}
				else if( parentNextMap.containsKey( node.getpId() ) )
				{
					parent = parentNextMap.get( node.getpId() );
					if( Tool.CHECK.isEmpty( parent.getChildren() ) )
					{
						parent.setChildren( new ArrayList<Node>() );
					}
					parent.getChildren().add( node );
				}
			}
		}
	}
}
