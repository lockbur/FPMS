var EventCss = function( selfData, argumentsData )
{
	if(!selfData)
	{
		return;
	}
	if(!argumentsData)
	{
		return;
	}
	this.settingAddView = function()
	{
		selfData.setting.view.fontCss = this.setFontCss;	
		selfData.setting.view.dblClickExpand =  this.dblClickExpand;
	},
	//设置字体颜色样式（f58220=蜜柑色	，A60000=红色）
	this.setFontCss = function(treeId, treeNode)
	{
		var css = {};
		if(treeNode.highlight)
		{
			if( treeNode.equalBy == "id" )		
			{
				css["color"] = "#f58220";
				treeNode.equalBy = null;
			}
			else if( treeNode.equalBy == "name" )
			{
				css["color"] = "#A60000";
			}
			css["font-weight"] = "bold";
		}
		else
		{
			css["color"] = "#333";
			css["font-weight"] = "normal";
			
			if( "N" == treeNode.isValid )
			{
				css["color"] = "#f2eada";
			}
		}	
		return css;
	},
	//双击是否展开节点Func
	this.dblClickExpand = function(treeId, treeNode) {
		return true;
	},
	//节点名称搜索查询
	this.searchNodesByName = function( _obj )
	{
		// 1.0 初始化
		var rootNodes = selfData.treeObj.transformToArray(selfData.treeObj.getNodes());
		for(var i = 0; i < rootNodes.length; i++)
		{
			rootNodes[i].equalBy = null;
			rootNodes[i].lock = false;
			this.updateNodeCss( rootNodes[i], false);
		}
		// 1.1 设置高亮
		if( $(_obj).val() )
		{
			if( selfData.searchMgr.type == "id" )
			{
				if( argumentsData.asyncFlag )
				{
					selfData.commonsFunc.querySearchLoad(true, $(_obj).val());
				}
				// id
				var byIdNodes = selfData.treeObj.getNodesByParam("id", $(_obj).val(), null);
				
//				for(var i = 0; i < byIdNodes.length; i++)
//				{
//					selfData.commonsFunc.nodeAsyncLoad(byIdNodes[i], false);
//				}
//				byIdNodes = selfData.treeObj.getNodesByParam("id", $(_obj).val(), null);
				
				
				for(var i = 0; i < byIdNodes.length; i++)
				{
					byIdNodes[i].equalBy = "id";
					this.updateNodeCss( byIdNodes[i], true);
				}
			}
			
			if( selfData.searchMgr.type == "name" )
			{
				if( argumentsData.asyncFlag )
				{
					selfData.commonsFunc.querySearchLoad(false, $(_obj).val());
				}
				// name --Fuzzy
				var byNameNodes = selfData.treeObj.getNodesByParamFuzzy("name", $(_obj).val(), null);
				
//				for(var i = 0; i < byNameNodes.length; i++)
//				{
//					selfData.commonsFunc.nodeAsyncLoad(byNameNodes[i], false);
//				}
//				byNameNodes = selfData.treeObj.getNodesByParam("name", $(_obj).val(), null);
			
				
				for(var i = 0; i < byNameNodes.length; i++)
				{
					byNameNodes[i].equalBy = "name";
					this.updateNodeCss( byNameNodes[i], true);
				}
			}
		}
		//1.2 关闭不被锁定并且为打开的节点
		var openNodes = selfData.treeObj.getNodesByFilter(selfData.filterFunc.getAllOpenNode);
		for(var i = 0; i < openNodes.length; i++)
		{
			openNodes[i].queryOpen = false;
			selfData.treeObj.expandNode(openNodes[i], false, false, false);
		}
	},
	//修改节点样式
	this.updateNodeCss = function( node, highlight )
	{
		node.highlight = highlight;
		if(node.highlight)
		{
			this.openNodeToRoot( node );
		}
		
		selfData.treeObj.updateNode( node );
	},
	//打开节点
	this.openNodeToRoot = function( node )
	{
		var parentNode = node.getParentNode();
		if(parentNode && parentNode.level >= 0)
		{
			if( !parentNode.open )
			{
				parentNode.queryOpen = true;
				selfData.treeObj.expandNode(parentNode, true, false, false);
			}
			parentNode.lock = true;
			this.openNodeToRoot( parentNode);
		}
	}
}