var CommonsFunction = function(selfData, argumentsData)
{
	if(!selfData)
	{
		return;
	}
	if(!argumentsData)
	{
		return;
	}
	//文本框值title全值显示并截取字符串于文本框中显示
	this.setTitleAndSubText = function(textValue , strLength ,strSuffix)
	{
		if( typeof(strLength) != "number")
		{
			return;
		}
		if( parseInt(strLength) <= 0)
		{
			return;
		}
		if(textValue)
		{
			if( strSuffix )
			{
				
				if( parseInt(strLength) <= textValue.length )
				{
					return textValue.substring(0,strLength) + strSuffix;
				}
				else
				{
					return textValue;
				}
				
			}
			else
			{
				if( parseInt(strLength) <= textValue.length )
				{
					return textValue.substring(0,strLength);
				}
				else
				{
					return textValue;
				}
			}
		}
		else
		{
			return "";
		}
	},
	//选择初始值
	this.selectInitValueNode = function( _initVal )
	{
		if(!selfData.treeObj || !_initVal) return;
		var node = selfData.treeObj.getNodesByParamFuzzy('id', _initVal, null);
		if( node.length > 1 )
		{
			node = selfData.treeObj.getNodeByParam('id', _initVal, null);
			node = selfData.treeObj.getNodeByParam('id', _initVal, node);
		}
		else
		{
			node = selfData.treeObj.getNodeByParam('id', _initVal, null);
		}
		if( node && node.getParentNode() )
		{
			selfData.treeObj.expandNode(node.getParentNode(), true, false, false);
		}
		return node;
	},
	//将集合中的数值放入指定目标
	this.getStrFromArray = function(_targetEle , nodes, strSuffix, notSame)
	{
		if(!_targetEle)
		{
			return;
		}
		if(!strSuffix)
		{
			strSuffix = ",";
		}
		if( !notSame )
		{
			notSame = false;
		}
		if(nodes && nodes.length > 0 )
		{
			var resultId = "";
			for(var i = 0; i < nodes.length; i++)
			{
				if(!notSame)
				{
					resultId += nodes[i].id + strSuffix;
				}
				else
				{
					if( resultId.indexOf(nodes[i].id + strSuffix) == -1 )
					{
						resultId += nodes[i].id + strSuffix;
					}
				}
			}
			resultId = resultId.substring(0, resultId.lastIndexOf(strSuffix) );
			$(_targetEle).val( resultId );
		}
	},
	//集合相加
	this.addBetweenArray = function(oneArray, twoArray)
	{
		if(!oneArray) 
		{
			oneArray = twoArray;
		}
		else
		{
			if(twoArray)
			{
				for(var i = 0; i < twoArray.length; i++)
				{
					oneArray[oneArray.length] = twoArray[i];
				}
			}
		}
		return oneArray;
	},
	//获取选定值
	this.getCheckNodeValue = function(_treeNode, _splitStr )
	{
		var resultName = "";
		var resultNameCut = "";
		var resultId = "";
		var data = {};
		
		if( argumentsData.radioFlag )
		{
			if( !_treeNode)
			{
				var checkedNodes = this.getAllCheckedNodes();
				if( checkedNodes && checkedNodes.length > 0 )
				{
					_treeNode = checkedNodes[0];
				}
			}
			if( !_treeNode)
			{
				return;
			}
					
			resultName = _treeNode.name;
			resultNameCut = resultName;
			resultId = _treeNode.id;
			
			// 单选且只能选择叶子时
			if( !argumentsData.parentCheckFlag && !_treeNode.checked )
			{
				resultName = "";
				resultNameCut = "";
				resultId = "";
			}
	
		}
		else
		{
			if(!_splitStr)
			{
				_splitStr = ",";
			}
			//如果是多选状态(文本框复选=[_dialogFlag = false && _radioFlag = false])：
			var checkedNodes = this.getAllCheckedNodes();
			
			if( checkedNodes && checkedNodes.length > 1)
			{
				for(var i=0; i< checkedNodes.length; i++)
				{
					resultName += checkedNodes[i].name + _splitStr;
					resultId += checkedNodes[i].id + _splitStr;
				}
				resultName = resultName.substring(0, resultName.lastIndexOf(_splitStr));
				resultId = resultId.substring(0, resultId.lastIndexOf(_splitStr));
				resultNameCut = selfData.commonsFunc.setTitleAndSubText(resultName, 20, "....");
			}
			else
			{
				if(checkedNodes && checkedNodes.length != 0)
				{
					resultName = checkedNodes[0].name;
					resultId = checkedNodes[0].id;
				}
				resultNameCut = resultName;
			}
		}	
		$(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeNm").val( resultNameCut );
		$(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeNm").attr("title",resultName);
		
		if(selfData.triggerEleMgr.triggerEleStart)
		{
			if(selfData.triggerEleMgr.triggerEleBy == "id")
			{
				if( selfData.triggerEleMgr.triggerEleByName )
				{
					$(selfData.triggerEleMgr.triggerEle).val( resultId );
					$(selfData.triggerEleMgr.triggerEle).attr("title", resultId);
					
					if( $(selfData.triggerEleMgr.triggerEle).parent().find("input[id='" + selfData.triggerEleMgr.triggerEleByName + "']").size() != 0 )
					{
						$(selfData.triggerEleMgr.triggerEle).parent().find("input[id='" + selfData.triggerEleMgr.triggerEleByName + "']").val( resultNameCut );
						$(selfData.triggerEleMgr.triggerEle).parent().find("input[id='" + selfData.triggerEleMgr.triggerEleByName + "']").attr("title", resultName);
					}
					if( $(selfData.triggerEleMgr.triggerEle).parent().find("input[name='" + selfData.triggerEleMgr.triggerEleByName + "']").size() != 0 )
					{
						$(selfData.triggerEleMgr.triggerEle).parent().find("input[name='" + selfData.triggerEleMgr.triggerEleByName + "']").val( resultNameCut );
						$(selfData.triggerEleMgr.triggerEle).parent().find("input[name='" + selfData.triggerEleMgr.triggerEleByName + "']").attr("title", resultName);
					}
				}
				else
				{
					$(selfData.triggerEleMgr.triggerEle).val( resultId );
					$(selfData.triggerEleMgr.triggerEle).attr("title", resultName);
				}
			}
			else
			{
				if( selfData.triggerEleMgr.triggerEleById )
				{
					$(selfData.triggerEleMgr.triggerEle).val( resultNameCut );
					$(selfData.triggerEleMgr.triggerEle).attr("title", resultName);
					
					if( $(selfData.triggerEleMgr.triggerEle).parent().find("input[id='" + selfData.triggerEleMgr.triggerEleById + "']").size() != 0 )
					{
						$(selfData.triggerEleMgr.triggerEle).parent().find("input[id='" + selfData.triggerEleMgr.triggerEleById + "']").val( resultId );
						$(selfData.triggerEleMgr.triggerEle).parent().find("input[id='" + selfData.triggerEleMgr.triggerEleById + "']").attr("title", resultId);
					}
					if( $(selfData.triggerEleMgr.triggerEle).parent().find("input[name='" + selfData.triggerEleMgr.triggerEleById + "']").size() != 0 )
					{
						$(selfData.triggerEleMgr.triggerEle).parent().find("input[name='" + selfData.triggerEleMgr.triggerEleById + "']").val( resultId );
						$(selfData.triggerEleMgr.triggerEle).parent().find("input[name='" + selfData.triggerEleMgr.triggerEleById + "']").attr("title", resultId);
					}
				}
				else
				{
					$(selfData.triggerEleMgr.triggerEle).val( resultName );
					$(selfData.triggerEleMgr.triggerEle).attr("title", resultId);
				}
			}
		}
		
		if( resultId != $(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeId").val() )
		{
			$(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeId").val( resultId );
			this.applyTagChangeEvent();
		}
		$(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeSpan").html( resultNameCut );
		$(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeSpan").attr("title", resultName );
		
		data["name"] = resultName;
		data["nameCut"] = resultNameCut;
		data["id"] = resultId;
		return data;
	},
	//调用事件
	this.applyTagChangeEvent = function()
	{
		if( argumentsData.changeFun )
		{
			var args = null;
			var data = null;
			if(argumentsData.changeFunParams)
			{
				args = argumentsData.changeFunParams.split(";");
				if( args && args.length > 0 )
				{
					data = {};
					for(var i = 0; i < args.length; i++)
					{
						data["argVal" + i] = args[i];
					}
				}
			}
			eval(argumentsData.changeFun).call(this, data);
		}
	},
	//验证错误信息显示
	this.showValidErrorMsg = function(_message)
	{
		if( !_message ) return;
		if( !selfData.orgSelectDivObj )
		{
			App.notyError("获取控件容器失败！！！"); 
			return;
		}
		$(selfData.orgSelectDivObj).children().hide();
		$(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeSpan").html(_message);
		$(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeSpan").attr("title", selfData.tagStatusMessage[selfData.tagStatus]);
		$(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeSpan").css({"color":"red","border":"1px solid red"});
		$(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeSpan").show();
		$(selfData.orgSelectDivObj).show();
	},
	//创建元素显示信息
	this.createShowMsgImg = function(_message, _result)
	{
		if(!_message) return;
		if(_result == "undefined" || _result == null)
		{
			_result = true;
		}
		var _spanEle = document.createElement("img");
		$(_spanEle).attr("alt", _message);
		$(_spanEle).attr("id", $(selfData.orgSelectDivObj).attr("id") + "MsgImg");
		if( !argumentsData.dialogFlag )
		{
			$(selfData.orgSelectDivObj).css({
				"display" : "inline"
			});
		}
		$(_spanEle).css( selfData.imgMgr.loading.css );
		$(_spanEle).attr("src", selfData.imgMgr.rootPath + selfData.imgMgr.loading.path );
		$(selfData.tagParent).find("#" + $(selfData.orgSelectDivObj).attr("id") + "MsgImg" ).remove();
		$(selfData.tagParent).append(_spanEle);
	},
	//弹出框回归
	this.showDialogBack = function()
	{
		if( $(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeButton").size() == 0 )
		{
			$(selfData.orgSelectDivObj).find("input").first().after( $(selfData.tagParent).find("#orgSelectedPluginNodeButton") );
		}
		if( $(selfData.tagParent).find("#" + $(selfData.orgSelectDivObj).attr("id") ).size() == 0 )
		{	
			$(selfData.tagParent).append( $(selfData.orgSelectDivObj) );
		}
	},
	//初始化时，input和span清空
	this.initInputAndSpan = function()
	{
		$(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeNm").val("");
		$(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeId").val("");
		$(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeSpan").html("");
		$(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeSpan").attr("title", "");
	},
	//节点结合筛选
	this.searchNodesByChecked = function( _nodes, _checked )
	{
		if( !_nodes || _nodes.length == 0 || _checked == 'undefined' || _checked == null ) return;
		var tempNodes = new Array();
		var index = 0;
		for( var i = 0; i < _nodes.length; i++)
		{
			if( _checked )
			{
				if( _nodes[i].checked ) tempNodes[index++] = _nodes[i];
			}
			else
			{
				if( !_nodes[i].checked ) tempNodes[index++] = _nodes[i];
			}
		}
		return tempNodes;
	},
	//获取所有选择节点
	this.getAllCheckedNodes = function()
	{
		var checkedNodes;
		
		if( argumentsData.radioFlag )
		{
			var node;
			if( $(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeId").val() )
			{
				node = selfData.treeObj.getNodeByParam("id", $(selfData.orgSelectDivObj).find("#orgSelectedPluginNodeId").val(), null);
				if( node )
				{
					checkedNodes = new Array();
					checkedNodes[0] = node;
				}
			}
			else
			{
				checkedNodes =  selfData.treeObj.getNodesByFilter( selfData.filterFunc.getAllDefaultSelectNode );
			}
		}
		else
		{
			if( argumentsData.leafOnlyFlag )
			{
				checkedNodes = selfData.treeObj.getNodesByFilter( selfData.filterFunc.getCheckedLeafNodes );
			}
			else
			{
				if( argumentsData.selHalfFlag )
				{
					checkedNodes = selfData.treeObj.getCheckedNodes(true);
				}
				else
				{ 
					checkedNodes = selfData.treeObj.getNodesByFilter( selfData.filterFunc.getCheckedButNoHalfNode );
				}
			}
		}
		return checkedNodes;
	},
	//检索id列表中是否存在特定id
	this.compareNodeIdByNodeId = function(_compareNodeId, _nodeId)
	{
		var result = false;
		
		if( !_compareNodeId || !_nodeId ) return result;
		if( _compareNodeId.indexOf(_nodeId) == -1 ) return result;
		
		var nodeIds = _compareNodeId.split(",");
		for(var i = 0; i < nodeIds.length; i++)
		{
			if( nodeIds[i] == _nodeId )
			{
				result = true;
				break;
			}
		}
		return result;
	},
	// 为字段获取默认值
	this.getDefaultValueForNull = function( _argName, _argDefaultValue)
	{
		if( arguments.length != 2 )
		{
			return;
		}
		if( _argName == null || _argName == 'undefined' )
		{
			return _argDefaultValue;
		}
		else
		{
			return _argName;
		}
	},
	// 加载数据
	this.nodeAsyncLoad = function( treeNode, isRefresh, isAsync)
	{
		if( !treeNode )
		{
			return;
		}
		if( argumentsData.asyncFlag )
		{
			if( treeNode.asyncLoad )
			{
				if( isRefresh )
				{
					treeNode.icon = selfData.imgMgr.rootPath + selfData.imgMgr.loading.path;
					selfData.treeObj.updateNode( treeNode );
				}
				
				var data = {};
				
				// 初始化值参数传递
				if( argumentsData.initValue ) 
				{
					data["nodeId"] = argumentsData.initValue; //
				}
				// 叶子等级限定传递
				if(argumentsData.leafLevel)
				{
					data["leafLevel"] = argumentsData.leafLevel; //
				} 
				// 多功能树类型传递
				if(argumentsData.treeType)
				{
					data["treeType"] = argumentsData.treeType; //
				}
				data["rootNodeId"] = treeNode.id;
				
				// 异步参数传递
				if( isAsync )
				{
					data["async"] = isAsync;
				}
				else
				{
					data["async"] = true;
				}
				
				
				data["asyncLoad"] = true;
				App.ajaxSubmit("sysmanagement/org/getOrgListTag.do?VISIT_FUNC_ID=010410",{data:data, async:false},function(data){
					if(data.orgList)
					{
						var treeNodes = data.orgList;
						selfData.treeObj.addNodes( treeNode, treeNodes, true);
						
						treeNode.asyncLoad = false;
//						for( var i = 0; i < treeNodes.length; i++)
//						{
//							selfData.commonsFunc.nodeAsyncLoad(treeNodes[i], false);
//						}
						
						if( isRefresh )
						{
							window.setTimeout(function(){
								treeNode.icon = "";
								selfData.treeObj.updateNode( treeNode );
							}, 1000);
						}
						
						if(treeNode.checked)
						{
							selfData.treeObj.checkNode(treeNode, true, true, true);
						}
					}
				});
			}
		}
	},
	//搜索功能
	this.querySearchLoad = function(_byId, _byValue)
	{
		var data = {};
		
		data["querySearch"] = true;
		if(_byId)
		{
			data["queryById"] = true;
		}
		else
		{
			data["queryById"] = false;
		}
		
		// 多功能树类型传递
		if(argumentsData.treeType)
		{
			data["treeType"] = argumentsData.treeType; //
		}
		
		if( _byValue )
		{
			data["queryByValue"] = _byValue;
		}
		else
		{
			return;
		}
		
		App.ajaxSubmit("sysmanagement/org/getOrgListTag.do?VISIT_FUNC_ID=010410",{data:data, async:false},function(data){
			if(data.searchValue)
			{
				var searchValues = data.searchValue;
				
				for(var i = 0; i < searchValues.length; i++)
				{
					selfData.commonsFunc.searchNodeIsAsyncLoad( searchValues[i] );
				}
			}
		});
	},
	// 查询节点
	this.searchNodeIsAsyncLoad = function(_searchValue)
	{
		if( _searchValue )
		{
			var node = null;
			if( _searchValue.indexOf("-") == -1 )
			{
				node = selfData.treeObj.getNodeByParam("id", _searchValue, null);
			}
			else
			{
				var searchValues = _searchValue.split("-");
				for( var i = 0; i < searchValues.length; i++ )
				{
					node = selfData.treeObj.getNodeByParam("id", searchValues[i], null);
					if( node && node.asyncLoad )
					{
						break;
					}
				}
			}
			selfData.commonsFunc.nodeAsyncLoad( node, false, false);
		}
	},
	// 目标元素
	this.triggerEleDeal = function()
	{
		var findValue = argumentsData.triggerEle;
		
		if( findValue )
		{
			
			// 1.0 出入值样式判定
			if( findValue.indexOf("::") == -1 )
			{
				selfData.triggerEleMgr.triggerEleBy = "id";
			}
			else
			{
				selfData.triggerEleMgr.triggerEleBy = findValue.substr(findValue.indexOf("::") + 2);
				if( selfData.triggerEleMgr.triggerEleBy )
				{
					if( selfData.triggerEleMgr.triggerEleBy.indexOf(",") != -1 )
					{
						selfData.triggerEleMgr.triggerEleBy = selfData.triggerEleMgr.triggerEleBy.split(",")[0];
					}
					
					if( "name" != selfData.triggerEleMgr.triggerEleBy )
					{
						selfData.triggerEleMgr.triggerEleBy = "id";
					}
				}
				else
				{
					selfData.triggerEleMgr.triggerEleBy = "id";
				}
				findValue = findValue.substr(0, findValue.indexOf("::") );
			}
			
			// 1.1 查询元素样式判定
			if(  findValue && findValue.indexOf(" ") != -1 && findValue.indexOf(" ") != findValue.length - 1 )
			{
				selfData.triggerEleMgr.triggerEleFind = findValue.substr(0, findValue.lastIndexOf(" "));
				findValue = findValue.substr( findValue.lastIndexOf(" ") + 1 );
			}
			
			if( findValue.indexOf(",") == -1 )
			{
				if("id" == selfData.triggerEleMgr.triggerEleBy)
				{
					selfData.triggerEleMgr.triggerEleById = findValue;
				}
				else
				{					
					selfData.triggerEleMgr.triggerEleByName = findValue;
				}
			}
			else
			{
				var byVals = findValue.split(",");
				
				if("id" == selfData.triggerEleMgr.triggerEleBy)
				{
					selfData.triggerEleMgr.triggerEleById = byVals[0];
					selfData.triggerEleMgr.triggerEleByName = byVals[1];
				}
				else
				{		
					selfData.triggerEleMgr.triggerEleById = byVals[1];
					selfData.triggerEleMgr.triggerEleByName = byVals[0];
				}
			}
			// 寻找目标元素
			if("id" == selfData.triggerEleMgr.triggerEleBy)
			{
				if( selfData.triggerEleMgr.triggerEleFind )
				{
					if( $(document).find( selfData.triggerEleMgr.triggerEleFind ).find("input[id='" + selfData.triggerEleMgr.triggerEleById + "']").size() != 0 )
					{
						selfData.triggerEleMgr.triggerEle = $(document).find( selfData.triggerEleMgr.triggerEleFind ).find("input[id='" + selfData.triggerEleMgr.triggerEleById + "']");
					}
					else if( $(document).find( selfData.triggerEleMgr.triggerEleFind ).find("input[name='" + selfData.triggerEleMgr.triggerEleById + "']").size() != 0 )
					{
						selfData.triggerEleMgr.triggerEle = $(document).find( selfData.triggerEleMgr.triggerEleFind ).find("input[name='" + selfData.triggerEleMgr.triggerEleById + "']");
					}
				}
				else
				{
					if( $(document).find("input[id='" + selfData.triggerEleMgr.triggerEleById + "']").size() != 0 )
					{
						selfData.triggerEleMgr.triggerEle = $(document).find("input[id='" + selfData.triggerEleMgr.triggerEleById + "']");
					}
					else if( $(document).find("input[name='" + selfData.triggerEleMgr.triggerEleById + "']").size() != 0 )
					{
						selfData.triggerEleMgr.triggerEle = $(document).find("input[name='" + selfData.triggerEleMgr.triggerEleById + "']");
					}
				}
			}
			else
			{
				if( selfData.triggerEleMgr.triggerEleFind )
				{
					if( $(document).find( selfData.triggerEleMgr.triggerEleFind ).find("input[id='" + selfData.triggerEleMgr.triggerEleByName + "']").size() != 0 )
					{
						selfData.triggerEleMgr.triggerEle = $(document).find( selfData.triggerEleMgr.triggerEleFind ).find("input[id='" + selfData.triggerEleMgr.triggerEleByName + "']");
					}
					else if( $(document).find( selfData.triggerEleMgr.triggerEleFind ).find("input[name='" + selfData.triggerEleMgr.triggerEleByName + "']").size() != 0 )
					{
						selfData.triggerEleMgr.triggerEle = $(document).find( selfData.triggerEleMgr.triggerEleFind ).find("input[name='" + selfData.triggerEleMgr.triggerEleByName + "']");
					}
				}
				else
				{
					if( $(document).find("input[id='" + selfData.triggerEleMgr.triggerEleByName + "']").size() != 0 )
					{
						selfData.triggerEleMgr.triggerEle = $(document).find("input[id='" + selfData.triggerEleMgr.triggerEleByName + "']");
					}
					else if( $(document).find("input[name='" + selfData.triggerEleMgr.triggerEleByName + "']").size() != 0 )
					{
						selfData.triggerEleMgr.triggerEle = $(document).find("input[name='" + selfData.triggerEleMgr.triggerEleByName + "']");
					}
				}
			}
			return selfData.triggerEleMgr.triggerEle;
		}
	},
	this.cancelSelectNode = function()
	{
		var nodes = selfData.treeObj.getSelectedNodes();
		if( nodes )
		{
			for(var i = 0; i < nodes.length; i++)
			{
				selfData.treeObj.cancelSelectedNode( nodes[i] );
			}
		}
	},
	this.triggerEleInit = function()
	{
		var nodeId;
		// 1.0 取值
		if( "id" == selfData.triggerEleMgr.triggerEleBy )
		{
			nodeId = $( selfData.triggerEleMgr.triggerEle ).val();
		}
		else
		{
			if( selfData.triggerEleMgr.triggerEleById )
			{
				if( $( selfData.triggerEleMgr.triggerEle ).parent().find("input[id='" + selfData.triggerEleMgr.triggerEleById + "']").size() != 0 )
				{
					nodeId = $($( selfData.triggerEleMgr.triggerEle ).parent().find("input[id='" + selfData.triggerEleMgr.triggerEleById + "']")[0]).val();
				}
				if( $( selfData.triggerEleMgr.triggerEle ).parent().find("input[name='" + selfData.triggerEleMgr.triggerEleById + "']").size() != 0 )
				{
					nodeId = $($( selfData.triggerEleMgr.triggerEle ).parent().find("input[name='" + selfData.triggerEleMgr.triggerEleById + "']")[0]).val();
				}
			}
			else
			{
				nodeId = $( selfData.triggerEleMgr.triggerEle ).attr("title");
			}
		}
		selfData.selfObj.loadTagList(null, null, null, null, nodeId);
	},
	// 消除不勾选却是半选状态的
	this.clearNoCheckButHalf = function()
	{
		var noCheckButHalfs = selfData.treeObj.getNodesByFilter( selfData.filterFunc.getNoCheckButHalf );
		if( !noCheckButHalfs ) return;
		if( noCheckButHalfs.length == 0) return;
		for(var i = 0; i < noCheckButHalfs.length; i++)
		{
			noCheckButHalfs[i].halfCheck = false;
			selfData.treeObj.updateNode( noCheckButHalfs[i] );
		}
	},
	//
	this.IEchangeWidth = function()
	{
		//var scrollWidth = $(selfData.orgSelectDivObj).find("#orgPluginZtreeDiv").find("div").last()[0].scrollWidth - $(selfData.orgSelectDivObj).find("#orgPluginZtreeDiv").width();
		//$(selfData.orgSelectDivObj).find("#" + selfData.zTreeId ).find("li").width( $(selfData.orgSelectDivObj).find("#orgPluginZtreeDiv").width() + scrollWidth );
		$(selfData.orgSelectDivObj).find("#" + selfData.zTreeId ).find("li").width(400);
	}
}