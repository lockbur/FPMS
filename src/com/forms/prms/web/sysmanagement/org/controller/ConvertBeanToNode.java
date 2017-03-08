package com.forms.prms.web.sysmanagement.org.controller;

import java.util.ArrayList;
import java.util.List;
import com.forms.platform.core.util.Tool;
import com.forms.prms.web.sysmanagement.org.domain.BaseBean;
import com.forms.prms.web.sysmanagement.org.domain.Node;

public class ConvertBeanToNode {

	
	/**
	 * 将单个设备类型对象转化为node节点
	 * @param devType
	 * @return
	 */
	public static Node fromBeanToNode(BaseBean baseBean)
	{
		return baseBean.changeSelfToNode();
	}
	
	/**
	 * 将设备列表转化为node列表
	 * @param list
	 * @return
	 */
	public static List<Node> fromBeanListToNodeList(List<BaseBean> list, BaseBean baseBean)
	{
		List<Node> nodeList = null;
		boolean isDefault = false;
		
		String[] nodeIds =  null;
		if( !Tool.CHECK.isBlank(baseBean.getNodeId()) )
		{
			if( baseBean.getNodeId().indexOf(",") == -1 )
			{
				nodeIds = new String[1];
				nodeIds[0] = baseBean.getNodeId();
			}
			else
			{
				nodeIds = baseBean.getNodeId().split(",");
			}
		}
		
		if(null != list && list.size() != 0)
		{
			nodeList = new ArrayList<Node>();
			for(int i = 0; i < list.size(); i++)
			{
				Node node = fromBeanToNode(list.get(i));
				if( !Tool.CHECK.isEmpty(nodeIds) )
 				{
					if(!isDefault)
					{
						isDefault = true;
					}
					for(int j = 0; j < nodeIds.length; j++)
					{
						if( nodeIds[j].equals( node.getId() ) )
						{
							node.setDefaultSelect(true);
							node.setOpenParent(true);
							node.setChecked(true);
						}
					}
				}
				nodeList.add( node );
			}
		}
		return fromMaxAddNodes(nodeList, isDefault);
	}
	
	/**
	 * 查询到最大节点
	 * @param dataNode
	 * @param lvl
	 * @return
	 */
	private static List<Node> fromMaxAddNodes(List<Node> dataNode, boolean isDefault)
	{
		//获得根节点列表
		int maxLvl = getMaxLevel(dataNode);
		int minLvl = getMinLevel(dataNode);
		return addNodes(dataNode, maxLvl, minLvl, isDefault);
	} 
	
	
	/**
	 * 为节点添加子节点
	 * @param dataNode
	 * @param level
	 * @return
	 */
	private static List<Node> addNodes(List<Node> dataNode, int level, int minLevel, boolean isDefault)
	{
		List<Node> lvlList = selectLvlList(dataNode, level, minLevel);
		if(null != lvlList && lvlList.size() != 0)
		{
			for (int i = 0; i < lvlList.size(); i++) 
			{
				Node node = lvlList.get(i);
				Node parent = selectNodeById(dataNode, node);
				if(null != parent)
				{
					if( null == parent.getChildren() )
					{
						parent.setChildren( new ArrayList<Node>());
						if( node.isChecked() || node.isHalfCheck())
						{
							parent.setChecked(true);
						}
						if( !node.isChecked() )
						{
							parent.setHalfCheck(true);
						}
					}
					else
					{
						if( node.isChecked() || node.isHalfCheck())
						{
							parent.setChecked(true);
						}
						if( !node.isChecked() )
						{
							parent.setHalfCheck(true);
						}
					}
					
					//当节点不是选择时，就没有所谓 半选状态
					if(!node.isChecked())
					{
						parent.setHalfCheck(false);
					}
					parent.getChildren().add(node);
				}
				else
				{
					if( level != minLevel)
					{
						dataNode.add(node);
					}
				}
			}
		}
		if( level > minLevel )
		{
			addNodes(dataNode, level-1, minLevel, isDefault);
		}
		else
		{
			defaultOpen(dataNode);
		}
		return dataNode;
	}
	
	/**
	 * 设定默认打开功能
	 * @param dataNode
	 */
	private static void defaultOpen(List<Node> dataNode)
	{
		if( !Tool.CHECK.isEmpty(dataNode) )
		{
			if( dataNode.size() <= 5 )
			{
				for(int i = 0; i < dataNode.size(); i++)
				{
					Node node = dataNode.get(i);
					if( !Tool.CHECK.isEmpty( node.getChildren() ) )
					{
						node.setDefaultOpen(true);
						node.setOpen(true);
						break;
					}
				}
			}
		}
	}
	
	/**
	 *  筛选指定等级的node列表
	 * @param dataNode
	 * @param lvl
	 * @return
	 */
	private static List<Node> selectLvlList( List<Node> dataNode, int lvl, int minLevel)
	{
		List<Node> selectResult = null;
		if( null != dataNode && dataNode.size() != 0)
		{
			selectResult = new ArrayList<Node>();
			for (int i = 0; i < dataNode.size(); i++) 
			{
				Node node = dataNode.get(i);
				if( String.valueOf(lvl).equals( node.getLevel() ) )
				{
					selectResult.add(node);
				}
			}
			if(lvl > minLevel)
			{
				dataNode.removeAll(selectResult);
			}
		}
		return selectResult;
	}
	
	/**
	 *  筛选指定父类节点下的node列表
	 * @param dataNode
	 * @param lvl
	 * @return
	 */
	private static Node selectNodeById( List<Node> dataNode, Node child)
	{
		Node selectResult = null;
		if( null != dataNode && dataNode.size() != 0)
		{
			for (int i = 0; i < dataNode.size(); i++) 
			{
				Node node = dataNode.get(i);
				if( child.getpId().equals( node.getId() ) )
				{
					selectResult = node;
					if( child.isOpen() || child.isOpenParent() )
					{
						selectResult.setOpen(true);
						selectResult.setDefaultOpen(true);
					}
					break;
				}
			}
		}
		return selectResult;
	}
	
	/**
	 * 获取节点列表中最大节点等级
	 * @param dataNode
	 * @return
	 */
	private static int getMaxLevel(List<Node> dataNode)
	{
		int level = 0;
		if(null != dataNode && dataNode.size() != 0)
		{
			for (int i = 0; i < dataNode.size(); i++) 
			{
				Node node = dataNode.get(i);
				if( Integer.parseInt(node.getLevel()) > level )
				{
					level = Integer.parseInt(node.getLevel());
				}
			}
		}
		return level;
	}
	
	/**
	 * 获取最小节点等级
	 * @param dataNode
	 * @return
	 */
	private static int getMinLevel(List<Node> dataNode)
	{
		int level = 0;
		if(null != dataNode && dataNode.size() != 0)
		{
			level = Integer.parseInt(dataNode.get(0).getLevel());
			for (int i = 0; i < dataNode.size(); i++)
			{
				Node node = dataNode.get(i);
				if( Integer.parseInt(node.getLevel()) < level )
				{
					level = Integer.parseInt(node.getLevel());
				}
			}
		}
		return level;
	}
}
