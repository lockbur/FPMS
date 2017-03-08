/**
 * _suffix	控件前缀标识, 
 * _rootNodeId	根节点ID, 
 * _rootLevel	根节点级别,
 * _initValue	初始化值,
 */
var OrgSelectDivObj = function( 
								// 	初始值   ||  是否需要验证    ||   此字段有值时，其他字段必须满足要求   ||  开发是否完成（Y/N）	
		_rootPath,
		_suffix, 				//  NULL  || y  ||   N   					||  Y
		_rootNodeId,			//  NULL  || N  ||   N   					||  Y
		_rootLevel,				//  NULL  || N  ||   N   					||  Y
		_initValue, 			//  NULL  || N  ||   N   					||  Y
		_jsVarGetValue, 		//  NULL  || N  ||   N   					||  Y
		_showInputFlag,			//  NULL  || N  ||   N   					||  Y
		_className, 			//  NULL  || N  ||   N   					||  Y
		_changeFun,				//  NULL  || Y  ||   N   					||  Y
		_changeFunParams,		//  NULL  || Y  ||   N   					||  Y
		_radioFlag,				//  TRUE  || N  ||   N   					||  Y
		_dialogFlag,			//  FALSE  || N  ||   N   					||  Y
		_treeType,				//  0  || N  ||   N   						||  Y
		_buttonName,			//  NULL  || N  ||   _dialogFlag = TRUE   	||  Y
		_querySql,				//  NULL  || N  ||   N   					||  N
		_queryParams,			//  NULL  || N  ||   _querySql NOT NULL   	||  N
		_leafLevel,				//  NULL  || N  ||   N   					||  Y
		_selHalfFlag,			//  TRUE  || N  ||   leafOnlyFlag = false   ||  Y
		_ableInputFlag,			//  FALSE  || N  ||  _showInputFlag = TRUE  ||  Y
		_ableQuery,				//  TRUE  || N  ||   N   					||  Y
		_leafOnlyFlag,			//  FALSE  || N  ||   N   					||  Y
		_relayLoadFlag,			//  FALSE  || N  ||   N   					||  Y
		_parentCheckFlag,		//  TRUE  || N  ||   N   					||  Y
		_dynamicUpdateFlag,		//  FALSE  || N  ||   N   					||  Y
		_asyncFlag,				//  FALSE  || N  ||   N   					||  N
		_triggerEle
	)
{
	var selfObj = this;
	// 控件参数封装
	var argumentsData 		= {};
	var selfData 			= {};
	selfData["selfObj"] = selfObj;
	// 控件内置对象
	var treeObj;
	var zTreeId;
	var orgSelectDivObj;
	var tagParent;
	var setting;
	var dialogObj;
	var showElement;
	var searchMgr = { "type" : "name", "desc" : "节点【name模糊匹配】搜索" };
	selfData["searchMgr"] = searchMgr;
	var imgMgr = {
			"rootPath":_rootPath,
			loading:{	
				"path":"/component/ztree/css/img/loading.gif", 
				 css:{
					"vertical-align":"middle",
					"height":"20",
					"width":"20"
				 }
			}, 
			refresh:{
				"path":"/common/images/refresh.gif",
				css:{
					"vertical-align":"middle",
					"height":"20",
					"width":"20",
					"cursor":"pointer"
				}
			}
	};
	selfData["imgMgr"] = imgMgr;
	
	// 目标对象管理类
	var triggerEleMgr = {
		triggerEleStart:false,
		"triggerEleBy":null,
		triggerEle:null,
		"triggerEleFind":null,
		"triggerEleById":null,
		"triggerEleByName":null
	};
	selfData["triggerEleMgr"] = triggerEleMgr;
	// 外部引用--内置控制对象
	var filterFunc 	= new ZtreeFilterFunc(selfData, argumentsData);
	var eventCss 	= new EventCss(selfData, argumentsData);
	var commonsFunc = new CommonsFunction(selfData, argumentsData);
	
	// 动态控制所用(动态参数取值)
	var dynamicBefore 	= {};
	var dynamicAfter 	= {};
	var dynamicManager 	= new DynamicManager(dynamicBefore, dynamicAfter);
	
	// 将树形过滤函数加入内置参数表
	selfData["filterFunc"] 	= filterFunc;
	// 将树形样式对象加入内置参数表
	selfData["eventCss"] 	= eventCss;
	// 将树形公用函数对象加入内置参数表
	selfData["commonsFunc"] = commonsFunc;
	
	_showInputFlag = commonsFunc.getDefaultValueForNull(_showInputFlag, true);
	_radioFlag  = commonsFunc.getDefaultValueForNull(_radioFlag, true);
	_dialogFlag  = commonsFunc.getDefaultValueForNull(_dialogFlag, false);
	_treeType  = commonsFunc.getDefaultValueForNull(_treeType, "0");
	_selHalfFlag  = commonsFunc.getDefaultValueForNull(_selHalfFlag, true);
	_ableInputFlag  = commonsFunc.getDefaultValueForNull(_ableInputFlag, false);
	_ableQuery  = commonsFunc.getDefaultValueForNull(_ableQuery, true);
	_leafOnlyFlag  = commonsFunc.getDefaultValueForNull(_leafOnlyFlag, false);
	_relayLoadFlag  = commonsFunc.getDefaultValueForNull(_relayLoadFlag, false);
	_parentCheckFlag  = commonsFunc.getDefaultValueForNull(_parentCheckFlag, true);
	_dynamicUpdateFlag  = commonsFunc.getDefaultValueForNull(_dynamicUpdateFlag, false);
	_asyncFlag  = commonsFunc.getDefaultValueForNull(_asyncFlag, true);

	
	// 初始化结果
	var initResult = false;
	var relayLoadFlag = false;
	relayLoadFlag = _relayLoadFlag;
	// 重置结果
	var resetResult;
	var dynamicResult = false;
	// 控件状态
	var tagStatus = 0;
	var tagStatusMessage = new Array();
	selfData["tagStatusMessage"] = tagStatusMessage;
	selfData["tagStatus"] = tagStatus;
	tagStatusMessage[0] = "控件实例对象成功,但开始初始化失败！！";
	
	
	
	//将参数替换回去
	this.argumentReplaceBack = function()
	{
		if(_initValue)
		{
			_initValue = _initValue.replace(/;/g, ",");
		}
		if(_changeFunParams)
		{
			_changeFunParams = _changeFunParams.replace(/;/g, ",");
		}
		if(_queryParams)
		{
			_queryParams = _queryParams.replace(/;/g, ",");
		}
		if(_rootNodeId)
		{
			_rootNodeId = _rootNodeId.replace(/;/g, ",");
		}
		if( !_parentCheckFlag )
		{
			_leafOnlyFlag = true;
		}
		if( _triggerEle )
		{
			_triggerEle = _triggerEle.replace(/;/g, ",");
		}
	},
	//动态更新参数
	this.setDynamicArgument = function()
	{
		this.getDataFromArgument(dynamicBefore);
		// 1 保存动态前参数
		_suffix				= dynamicAfter.suffix;				
		_rootNodeId 		= dynamicAfter.rootNodeId;		
		_rootLevel 			= dynamicAfter.rootLevel;	
		_initValue 			= dynamicAfter.initValue;
		_jsVarGetValue 		= dynamicAfter.jsVarGetValue;
		_showInputFlag 		= dynamicAfter.showInputFlag;		
		_className 			= dynamicAfter.className;		
		_changeFun 			= dynamicAfter.changeFun;
		_changeFunParams	= dynamicAfter.changeFunParams;
		_radioFlag 			= dynamicAfter.radioFlag;
		_dialogFlag 		= dynamicAfter.dialogFlag;
		_treeType 			= dynamicAfter.treeType;
		_buttonName 		= dynamicAfter.buttonName;	
		_querySql 			= dynamicAfter.querySql;
		_queryParams 		= dynamicAfter.queryParams;	
		_leafLevel 			= dynamicAfter.leafLevel;	
		_selHalfFlag 		= dynamicAfter.selHalfFlag;		
		_ableInputFlag 		= dynamicAfter.ableInputFlag;		
		_ableQuery 			= dynamicAfter.ableQuery;
		_leafOnlyFlag 		= dynamicAfter.leafOnlyFlag;
		_parentCheckFlag 	= dynamicAfter.parentCheckFlag;
		_dynamicUpdateFlag 	= dynamicAfter.dynamicUpdateFlag;
		_asyncFlag			= dynamicAfter.asyncFlag;
		_triggerEle			= dynamicAfter.triggerEle;
		this.getDataFromArgument(argumentsData);
	},
	
	//获取树的数据源，并初始化树
	this.loadTagList = function(treeType, rootNodeId, rootLevel, leafLevel, initValue)
	{
		var result = true;
		var treeNodes = [];
		var data = {};
		// 加载根节点参数传递
		if( rootNodeId )
		{
			data["rootNodeId"] = rootNodeId; //
		}
		else
		{
			if( _rootNodeId ) data["rootNodeId"] = _rootNodeId; //
		}
		// 加载根节点等级参数传递
		if( rootLevel )
		{
			data["rootLevel"] = rootLevel; //
		}
		else
		{
			if( _rootLevel ) data["rootLevel"] = _rootLevel; //
		}
		// 初始化值参数传递
		if( initValue )
		{
			data["nodeId"] = initValue; //
		}
		else
		{
			if( _initValue ) 
			{
				data["nodeId"] = _initValue; //
				initValue = _initValue;
			}
		}
		// 叶子等级限定传递
		if(leafLevel)
		{
			data["leafLevel"] = leafLevel; //
		}
		else
		{
			if(_leafLevel) data["leafLevel"] = _leafLevel; //
		}
		// 多功能树类型传递
		if(treeType)
		{
			data["treeType"] = treeType; //
		}
		else
		{
			if(_treeType) data["treeType"] = _treeType; //
		}
		// 异步参数传递
		if(_asyncFlag)
		{
			data["async"] = true;
		}
		else
		{
			data["async"] = false;
		}
		// 单选多选
		if( _radioFlag )
		{
			data["radioFlag"] = true;
		}
		else
		{
			data["radioFlag"] = false;
		}
		if(_suffix)
		{
			$.fn.zTree.destroy("zTree" + _suffix);
		}
		else
		{
			$.fn.zTree.destroy("zTree");
		}
		
		commonsFunc.initInputAndSpan();
		
		
		if(!_relayLoadFlag)
		{
			App.ajaxSubmit("sysmanagement/org/getOrgListTag.do?VISIT_FUNC_ID=010410",{data:data, async:false},function(data){
				if(data.orgList)
				{
					treeNodes = data.orgList;
					
					if( dynamicManager.getDynamicResult() )
					{
						if( _suffix )
						{
							$(orgSelectDivObj).find("#" + selfData.zTreeId).attr("id", "zTree" + _suffix);
						}
						else
						{
							$(orgSelectDivObj).find("#" + selfData.zTreeId).attr("id", "zTree");
						}
					}
					if( _suffix )
					{
						zTreeId = "zTree" + _suffix;
					}
					else
					{
						zTreeId = "zTree";
					}
					
					// 初始化树形结构展示
					$.fn.zTree.init( $(orgSelectDivObj).find("#" + zTreeId), setting, treeNodes);
					treeObj = $.fn.zTree.getZTreeObj(zTreeId);
					
					// 将树形控件加入内置参数表
					selfData["treeObj"] = treeObj;
					selfData["zTreeId"] = zTreeId;
					commonsFunc.IEchangeWidth();
					
					if( initValue )
					{
						if( _radioFlag )
						{
							if( initValue.indexOf(",") == -1 )
							{
								var node = treeObj.getNodesByParamFuzzy('id', initValue, null);
								if( node.length > 1 )
								{
									node = treeObj.getNodeByParam('id', initValue, null);
									node = treeObj.getNodeByParam('id', initValue, node);
								}
								else
								{
									node = treeObj.getNodeByParam('id', initValue, null);
								}
								treeObj.selectNode(node);
							}
							else
							{
								commonsFunc.showValidErrorMsg("单选时，只能一个初始化参数值！！！");
								result = false;
							}
						}
						commonsFunc.getCheckNodeValue();
					}
					
					// 机构节点只有一个时的处理
					var nodes = treeObj.transformToArray( treeObj.getNodes() );
					
					if( nodes.length == 1 && !nodes[0].asyncLoad )
					{
						var node = nodes[0];
						$(orgSelectDivObj).find("#orgSelectedPluginNodeNm").val(node.name);
						$(orgSelectDivObj).find("#orgSelectedPluginNodeSpan").html(node.name);
						$(orgSelectDivObj).find("#orgSelectedPluginNodeId").val(node.id);
						
						treeObj.selectNode(node);
						treeObj.checkNode( node, true, true);
						
						commonsFunc.applyTagChangeEvent();
						
						if( !triggerEleMgr.triggerEleStart )
							showElement = $(orgSelectDivObj).find("#orgSelectedPluginNodeSpan");
					}
					else
					{
						
						if( !_parentCheckFlag )
						{
							var parentNodes = treeObj.getNodesByFilter(filterFunc.getAllParentNodes);
							for(var i = 0; i < parentNodes.length; i++)
							{
								parentNodes[i].nocheck = true;
								treeObj.updateNode(parentNodes[i]);
							}
						}
					}
					
					
				}
				else
				{
					commonsFunc.showValidErrorMsg("没有加载到相关信息！！！");
					result = false;
				}
			});
		}
		return result;
	},
	
	//初始化加载方法
	this.tagInit = function()
	{
//		if( initResult )
//		{
//			return;
//		}
		//参数初始化设定
		this.argumentReplaceBack();
		this.getDataFromArgument(argumentsData);
		
		//目标对象设定
		triggerEleMgr.triggerEleStart = false;
		showElement = commonsFunc.triggerEleDeal();
		triggerEleMgr.triggerEle = $(triggerEleMgr.triggerEle)[ $(triggerEleMgr.triggerEle).size() - 1 ];
		if( showElement || _triggerEle )
		{
			triggerEleMgr.triggerEleStart = true;
		}
		
		if( !dynamicManager.getDynamicResult())
		{			
			this.getDataFromArgument(dynamicBefore);
			this.getDataFromArgument(dynamicAfter);
		}
		
		//事件移除
		this.removeAllEvent();
		
		// 加载css样式
		if( dynamicManager.getDynamicResult() )
		{
			// 动态修改树
			if( dynamicManager.getDynamicStyle()  )
			{
				if ( !this.loadTagCss() ) return;
			}
		}
		else
		{
			if( !this.loadTagCss() ) return;
		}
		
		// 加载数据
		if( dynamicManager.getDynamicResult() )
		{
			// 动态修改树
			if( dynamicManager.getDynamicTree()  )
			{
				if( !this.loadTagList() )
				{
					return;
				}
			}
		}
		else
		{
			if( !this.loadTagList() )
			{
				return;
			}
		}
		
		// 加载显示样式
		if( dynamicManager.getDynamicResult() )
		{
			// 动态修改树
			if( dynamicManager.getDynamicStyle()  )
			{
				this.loadTagStyle();
			}
		}
		else
		{
			this.loadTagStyle();
		}
		$(orgSelectDivObj).show();
		$(showElement).show();
		$(orgSelectDivObj).click(function(){
			if( event && event.stopPropagation )
			{
				event.stopPropagation();
			}
			else
			{
				window.event.cancelBubble = true;
			}
		});
		
	},
	this.loadTagCss = function()
	{
		// 开始准备初始化
		initResult = false;	
		tagStatus = 1;
		selfData["tagStatus"] = tagStatus;
		tagStatusMessage[1] = "开始准备初始化,但获取容器失败！！";	
		if( dynamicManager.getDynamicResult() )
		{
			//动态修改后缀
			if( _suffix )
			{
				$(orgSelectDivObj).attr("id", "orgSelectDiv_Plugin" + _suffix);
			}
			else
			{
				$(orgSelectDivObj).attr("id", "orgSelectDiv_Plugin");
			}
		}
		else
		{
			if( _suffix )
			{
				orgSelectDivObj = $("#orgSelectDiv_Plugin" + _suffix);
			}
			else
			{
				orgSelectDivObj = $("#orgSelectDiv_Plugin");
			}
		}
		// 将容器加入内置对象中
		$(orgSelectDivObj).hide();
		tagParent = $(orgSelectDivObj).parent(); 
		selfData["tagParent"] = tagParent;
		
		selfData["orgSelectDivObj"] = orgSelectDivObj;
		selfData["initResult"] = initResult;
		
		//容器唯一验证
		if( $("div[id='"+ $(orgSelectDivObj).attr("id") +"']").size() > 1 )
		{
			orgSelectDivObj = $("div[id='"+ $(orgSelectDivObj).attr("id") +"']");
			selfData["orgSelectDivObj"] = orgSelectDivObj;
			commonsFunc.showValidErrorMsg("容器id引用相冲突！！！");
			return false;
		}
		// 成功获取容器对象
		tagStatus = 2;
		selfData["tagStatus"] = tagStatus;
		tagStatusMessage[2] = "成功获取容器对象,但控件加载样式失败！！";
		
		$(orgSelectDivObj).find("#orgPluginZtreeDiv").css(
			{
				"position" : "absolute",
				"background-color" : "#FFFEF5",
				"text-align" :"left",
				"z-index" : "10000",
				"padding" : "2px",
				"margin-top" : "2px",
				"vertical-align" : "top"
			}
		);
		//初始化属性配置(文本框单选)
		setting = {
			callback:
			{
				onClick: this.getOrgSelectedDetail,
				beforeExpand: null,
				onCheck: null
			},
			view:{
				autoCancelSelected: false,
				showIcon: false,
				fontCss: null,				
				dblClickExpand: null	
			}
		}; 
		//  当是多选择时，新增配置
		if( !_radioFlag )
		{
			setting.check = {
					enable: true,
					chkboxType: {"Y":"ps", "N":"ps"}
			};
			setting.callback.onCheck = this.onCheckSelectValue;
		}
		else
		{
			if( !_parentCheckFlag )
			{
				setting.check = {
						enable: true,
						chkStyle: "radio",
						radioType: "all"
				};
				setting.callback.onCheck = this.onCheckSelectValue;
			}
			else
			{
				setting.check = {
					enable:false
				}
			}
		}
		if( _asyncFlag)
		{
			setting.callback.beforeExpand = this.nodeAsyncLoad;
		}
		// 将树形样式加入内置参数表
		selfData["setting"] = setting;
		eventCss.settingAddView();
		// 控件加载好样式
		tagStatus = 3;
		selfData["tagStatus"] = tagStatus;
		tagStatusMessage[3] = "控件加载好样式,但加载数据失败！！";
		return true;
	},
	this.loadTagStyle = function()
	{
		// 数据加载成功
		tagStatus = 4;
		selfData["tagStatus"] = tagStatus;
		tagStatusMessage[4] = "数据加载成功,但设置最终显示样式失败！！";
		
		// 1.0  隐藏机构显示层
		$(orgSelectDivObj).find("#orgPluginZtreeDivDialog").hide();
		$(orgSelectDivObj).find("#orgSelectedPluginNodeNm").hide();
		$(orgSelectDivObj).find("#orgSelectedPluginNodeSpan").hide();
		$(orgSelectDivObj).find("#orgSelectedPluginNodeId").hide();
		$(orgSelectDivObj).find("#orgSelectedPluginNodeButton").hide();
		
		if(_jsVarGetValue)
		{
			$(orgSelectDivObj).find("#orgSelectedPluginNodeId").attr("name", _jsVarGetValue);
		}
		else
		{
			if( _suffix )
			{
				if(_suffix.indexOf("_") == 0)
				{
					$(orgSelectDivObj).find("#orgSelectedPluginNodeId").attr("name", "orgSelPluginTagId" + _suffix.substring( _suffix.indexOf("_") + 1, _suffix.length) );
				}
				else
				{
					$(orgSelectDivObj).find("#orgSelectedPluginNodeId").attr("name", "orgSelPluginTagId");
				}
			}
			else
			{
				$(orgSelectDivObj).find("#orgSelectedPluginNodeId").attr("name", "orgSelPluginTagId");
			}
			
		}
		// 1.1  添加样式
		if(_className)
		{
			$(orgSelectDivObj).find("#orgSelectedPluginNodeNm").attr("class", _className);
			$(orgSelectDivObj).find("#orgSelectedPluginNodeId").attr("class", _className);
			$(orgSelectDivObj).find("#orgPluginSearchKey").attr("class", _className);
		}
		else
		{
			$(orgSelectDivObj).find("#orgSelectedPluginNodeNm").attr("class", "base-input-text");
			$(orgSelectDivObj).find("#orgSelectedPluginNodeId").attr("class", "base-input-text");
			$(orgSelectDivObj).find("#orgPluginSearchKey").attr("class", "base-input-text");
		}
		$(orgSelectDivObj).find("#orgSelectedPluginNodeSpan").css({"font-size":"12px"});
		
		// 1.2  长度控制  设置ZtreeDiv的宽度(控件DIV宽度、树DIV宽度、模糊查询框的宽高)
		$(orgSelectDivObj).width( $(orgSelectDivObj).find("#orgSelectedPluginNodeNm").width() - 4 );
		
		$(orgSelectDivObj).find("#orgPluginZtreeDiv").width( $(orgSelectDivObj).find("#orgSelectedPluginNodeNm").width() - 4 );
		$(orgSelectDivObj).find("#orgPluginSearchKey").height("20px");
		$(orgSelectDivObj).find("#orgPluginSearchKey").width( $(orgSelectDivObj).find("#orgSelectedPluginNodeNm").width() - 95 );
		$(orgSelectDivObj).find("#orgPluginZtreeDiv").find("img").bind("click",function(){
			
			$(this).attr("src", imgMgr.rootPath + imgMgr.loading.path );
			$(this).css(imgMgr.loading.css);
			window.setTimeout(function(){
				if( searchMgr.type == 'name' )
				{
					searchMgr.type = "id";
					searchMgr.desc = "代码";
				}
				else
				{
					searchMgr.type = "name";
					searchMgr.desc = "名称";
				}
				
				$(orgSelectDivObj).find("#orgPluginZtreeDiv").find("label").html(searchMgr.desc);
				$(orgSelectDivObj).find("#orgPluginZtreeDiv").find("img").attr("src", imgMgr.rootPath + imgMgr.refresh.path);
				$(orgSelectDivObj).find("#orgPluginZtreeDiv").find("img").css(imgMgr.refresh.css);
			}, 1000);
		});
		$(orgSelectDivObj).find("#orgPluginZtreeDiv").find("img").attr("src", imgMgr.rootPath + imgMgr.change);
		$(orgSelectDivObj).find("#orgPluginZtreeDiv").find("img").click();
		
		$(orgSelectDivObj).find("#orgPluginZtreeDiv").find("div").first().height(30);
		$(orgSelectDivObj).find("#orgPluginZtreeDiv").find("div").first().css({
			"line-height":"28px"
		});
		$(orgSelectDivObj).find("#orgPluginZtreeDiv").height( 50 + $(orgSelectDivObj).find("#orgPluginZtreeDiv").find("div").last().height() );
		
		
		
		//弹出对话框时，按钮赋值
		if(_buttonName)
		{
			$(orgSelectDivObj).find("#orgSelectedPluginNodeButton").val(_buttonName);
		}
		else
		{
			$(orgSelectDivObj).find("#orgSelectedPluginNodeButton").val("点击选择");
		}
				
		
		//1.4 显示方式
		if( _dialogFlag )
		{
			if( !triggerEleMgr.triggerEleStart )
				showElement = $(orgSelectDivObj).find("#orgSelectedPluginNodeButton");
		}
		else
		{
			if(_showInputFlag)
			{
				if( !triggerEleMgr.triggerEleStart )
					showElement = $(orgSelectDivObj).find("#orgSelectedPluginNodeNm");
				//是否支持自己输入
				if(!_ableInputFlag)
				{
					$(orgSelectDivObj).find("#orgSelectedPluginNodeNm").attr("readonly", true);
				}
				else
				{
					//输入框聚焦时的处理
					$(orgSelectDivObj).find("#orgSelectedPluginNodeNm").bind("focus", function(){
						$(this).val( $(orgSelectDivObj).find("#orgSelectedPluginNodeSpan").attr("title") );
					});
					
					//但输入框失去焦点时的处理
					$(orgSelectDivObj).find("#orgSelectedPluginNodeNm").bind("blur", function(){
						
						var compareNodeId = $(orgSelectDivObj).find("#orgSelectedPluginNodeId").val();
						var allCheckNodes =  commonsFunc.getAllCheckedNodes();
						if(allCheckNodes && allCheckNodes.length > 0 )
						{
							for(var i = 0; i < allCheckNodes.length; i++)
							{
								if( !commonsFunc.compareNodeIdByNodeId(compareNodeId, allCheckNodes[i].id ) )
								{
									treeObj.checkNode( allCheckNodes[i], false, true);
								}
							}
						}
						commonsFunc.getCheckNodeValue();
					});
					
					//自己输入时，id主键清空
					
					$(orgSelectDivObj).find("#orgSelectedPluginNodeNm").bind("keyup", function(){
						
						var byNameSelNodes;
						$(orgSelectDivObj).find("#orgPluginZtreeDivDialog").hide();
						
						if(_radioFlag)
						{
							byNameSelNodes = treeObj.getNodesByParam("name", $(this).val(), null);
							if(byNameSelNodes && byNameSelNodes.length > 0)
							{
								commonsFunc.getStrFromArray( $(orgSelectDivObj).find("#orgSelectedPluginNodeId"), byNameSelNodes);
							}
							else
							{
								$(orgSelectDivObj).find("#orgSelectedPluginNodeId").val( $(this).val() );
							}
						}
						else
						{
							if( $(this).val().indexOf(",") != -1 )
							{
								var nodes = $(this).val().split(",");
								for(var i=0 ; i < nodes.length ; i++ )
								{								
									byNameSelNodes = commonsFunc.addBetweenArray(byNameSelNodes, treeObj.getNodesByParam("name", nodes[i], null));
								}
								byNameSelNodes = commonsFunc.searchNodesByChecked(byNameSelNodes, true);
								commonsFunc.getStrFromArray($(orgSelectDivObj).find("#orgSelectedPluginNodeId"), byNameSelNodes);
							}
							else
							{
								byNameSelNodes = treeObj.getNodesByParam("name", $(this).val(), null);
								byNameSelNodes = commonsFunc.searchNodesByChecked(byNameSelNodes, true);
								
								if(byNameSelNodes && byNameSelNodes.length > 0)
								{
									commonsFunc.getStrFromArray($(orgSelectDivObj).find("#orgSelectedPluginNodeId"), byNameSelNodes);
								}
								else
								{
									$(orgSelectDivObj).find("#orgSelectedPluginNodeId").val( $(this).val() );
								}
							}
							
						}
					});
				}
			}
			else
			{
				if( !triggerEleMgr.triggerEleStart )
					showElement = $(orgSelectDivObj).find("#orgSelectedPluginNodeSpan");
			}			
		}
		// 不需要检索功能
		if(!_ableQuery)
		{
			$(orgSelectDivObj).find("#orgPluginSearchKey").parent().hide();
		}
		else
		{
			$(orgSelectDivObj).find("#orgPluginSearchKey").parent().css({
				'border' : '1px solid #d9d6c3',
				'padding' : '3px',
				'margin' : '3px',
				'font-size' : '12px'
			});
		}
		
		// 控件最终显示样式设置完成
		tagStatus = 5;
		selfData["tagStatus"] = tagStatus;
		tagStatusMessage[5] = "控件最终显示样式设置完成, 但绑定事件失败！！";
		
		this.loadBindFunInit();
		// 控件事件绑定
		tagStatus = 6;
		
		initResult = true;
		selfData["initResult"] = initResult;
		tagStatus = 0;
		selfData["tagStatus"] = tagStatus;
		tagStatusMessage[0] = "控件初始化完成！！";
		if( !dynamicManager.getDynamicResult() )
		{
			resetResult = true;
		}
		if( _relayLoadFlag )
		{
			_relayLoadFlag = false;
			initResult = false;
		}
	},
	// 移除事件
	this.removeAllEvent = function()
	{
		$(orgSelectDivObj).attr("style", null);
		
		if( dialogObj )
		{
			$(dialogObj).dialog("destroy");
			dialogObj = null;
		} 
		
		$(orgSelectDivObj).find("#orgSelectedPluginNodeNm").unbind("keyup");
		$(orgSelectDivObj).find("#orgSelectedPluginNodeNm").unbind("click");
		
		$(tagParent).find("#orgSelectedPluginNodeButton").unbind("click");
		$(orgSelectDivObj).find("#orgPluginZtreeDiv").find("img").unbind("click");
				
		$(orgSelectDivObj).find("#orgPluginSearchKey").unbind("blur");
		$(orgSelectDivObj).find("#orgPluginSearchKey").unbind("propertychange");
		$(orgSelectDivObj).find("#orgPluginSearchKey").unbind("input");
		$(orgSelectDivObj).find("#orgPluginSearchKey").unbind("focus");
		
		if(triggerEleMgr.triggerEleStart)
		{
			$(showElement).unbind("click");
		}
	},
	//绑定事件
	this.loadBindFunInit = function()
	{	
		$(orgSelectDivObj).find("#orgPluginSearchKey").attr("title", "键入enter执行搜索");
		
		$(orgSelectDivObj).find("#orgPluginSearchKey").bind("keydown", function(){
			if( event.keyCode == 13 )
			{
				eventCss.searchNodesByName(this);
				$(orgSelectDivObj).find("#orgPluginSearchKey").blur();
			}
		});
		
	/*	
		$(orgSelectDivObj).find("#orgPluginSearchKey").bind("blur", function(){
			eventCss.searchNodesByName(this);
		});
		$(orgSelectDivObj).find("#orgPluginSearchKey").bind("propertychange", function(){
			eventCss.searchNodesByName(this);
		});
		$(orgSelectDivObj).find("#orgPluginSearchKey").bind("input", function(){
			eventCss.searchNodesByName(this);
		});
	*/
		
		$(orgSelectDivObj).find("#orgPluginSearchKey").bind("focus", function(){
			if( $(orgSelectDivObj).find("#orgPluginSearchKey").val() )
			{
				$(orgSelectDivObj).find("#orgPluginSearchKey").val("");
				eventCss.searchNodesByName(this);
			}
		});
		//显示方式
		if( _dialogFlag )
		{
			if(triggerEleMgr.triggerEleStart)
			{
				$(showElement).each(function(){
					
					$(this).bind("click", function(){		
						
						if( !resetResult ) return;
						
						triggerEleMgr.triggerEle = $(this);
						var dialogWidth = $(orgSelectDivObj).find("#orgPluginZtreeDiv").width() + 35;
						var dialogHeight = $(orgSelectDivObj).find("#orgPluginZtreeDiv").height() + 135;
						
						$(orgSelectDivObj).find("#orgPluginZtreeDivDialog").show();
						$(orgSelectDivObj).dialog({
							autoOpen: true,
							modal: true,
							width:dialogWidth,
							height:dialogHeight,
							draggable: false,
							resizable: false,
							title: "选择机构",
							position: "center",
							open:function(){
								$(this).find("#orgPluginSearchKey").blur();
								if( !dialogObj )
								{ 
									dialogObj = this;
								}
								commonsFunc.triggerEleInit();
							},
							close:function(){
								if( !_radioFlag )
								{
									commonsFunc.getCheckNodeValue();
								}
							},
							buttons:[
								{
									text:"确认",
									click:function(){
										$(this).dialog("close");
									}
								}
//								,
//								{
//									text:"重置",
//									click:function()
//									{
//										treeObj.checkAllNodes(false);
//										//默认打开元素
//										var notDefaultOpenNodes = treeObj.getNodesByFilter( filterFunc.getAllNotDefaultOpenNode );
//										for(var i = 0; i < notDefaultOpenNodes.length; i++)
//										{
//											treeObj.expandNode(notDefaultOpenNodes[i], false, false, false);
//										}
//										//默认选择元素
//										var defaultSelectNodes = treeObj.getNodesByFilter(filterFunc.getAllDefaultSelectNode);
//										for(var i = 0; i < defaultSelectNodes.length; i++)
//										{
//											treeObj.checkNode(defaultSelectNodes[i], false, false);
//										}
//									}
//								}
							]
						});
					});
					
				});
			}
			else
			{
				$(orgSelectDivObj).find("#orgSelectedPluginNodeButton").bind("click", function(){		
					
					if( !resetResult ) return;
					
					var dialogWidth = $(orgSelectDivObj).find("#orgPluginZtreeDiv").width() + 35;
					var dialogHeight = $(orgSelectDivObj).find("#orgPluginZtreeDiv").height() + 135;
					
					$(orgSelectDivObj).find("#orgPluginZtreeDivDialog").show();
					$(orgSelectDivObj).dialog({
						autoOpen: true,
						modal: true,
						width:dialogWidth,
						height:dialogHeight,
						draggable: false,
						resizable: false,
						title: "选择机构",
						position: "center",
						open:function(){
							$(this).find("#orgPluginSearchKey").blur();
							if( !dialogObj )
							{ 
								dialogObj = this;
							}
						},
						close:function(){
							commonsFunc.getCheckNodeValue();
						},
						buttons:[
							{
								text:"确认",
								click:function(){
									$(this).dialog("close");
								}
							}
//							,
//							{
//								text:"重置",
//								click:function()
//								{
//									treeObj.checkAllNodes(false);
//									//默认打开元素
//									var notDefaultOpenNodes = treeObj.getNodesByFilter( filterFunc.getAllNotDefaultOpenNode );
//									for(var i = 0; i < notDefaultOpenNodes.length; i++)
//									{
//										treeObj.expandNode(notDefaultOpenNodes[i], false, false, false);
//									}
//									//默认选择元素
//									var defaultSelectNodes = treeObj.getNodesByFilter(filterFunc.getAllDefaultSelectNode);
//									for(var i = 0; i < defaultSelectNodes.length; i++)
//									{
//										treeObj.checkNode(defaultSelectNodes[i], true, true);
//									}
//								}
//							}
						]
					});
				});
			}
			
			$(orgSelectDivObj).parent().append( $(orgSelectDivObj).find("#orgSelectedPluginNodeButton"));
		}
		else
		{
			
			if(triggerEleMgr.triggerEleStart)
			{
				$(showElement).each(function(){
					
					$(this).bind("click", function(){
						
						if( !resetResult ) return;
						
						if( event && event.stopPropagation )
						{
							event.stopPropagation();
						}
						else
						{
							window.event.cancelBubble = true;
						}
						
						$(this).after( orgSelectDivObj );
						triggerEleMgr.triggerEle = $(this);
						
						$(orgSelectDivObj).find("#orgPluginZtreeDivDialog").toggle();
						if( $(orgSelectDivObj).find("#orgPluginZtreeDivDialog:hidden").size() == 0 )
						{
							commonsFunc.triggerEleInit();
						}
						//发生源 ：event.fromElement  目标源：event.toElement 
						$(document).bind("click", function(){
							$(orgSelectDivObj).find("#orgPluginZtreeDivDialog").hide();
						});
					});
					
				});
			}
			else
			{
				$(orgSelectDivObj).find("#orgSelectedPluginNodeNm").bind("click", function(){
					
					if( !resetResult ) return;
					$(orgSelectDivObj).find("#orgPluginZtreeDivDialog").toggle();
					
					if( _ableInputFlag )
					{
						if( $(orgSelectDivObj).find("#orgPluginZtreeDivDialog:hidden").size() == 0 )
						{
							var nodeId = $(orgSelectDivObj).find("#orgSelectedPluginNodeId").val();
							if( nodeId )
							{
								selfObj.loadTagList(null, null, null, null, nodeId);
							}
						}
					}
					
					//发生源 ：event.fromElement  目标源：event.toElement 
					$(document).bind("click", function(){
						$(orgSelectDivObj).find("#orgPluginZtreeDivDialog").hide();
					});
				});
			}
		}
		
	},
	
	//获取用户选中值，并隐藏ZtreeDiv
	this.getOrgSelectedDetail = function(event, treeId, treeNode)
	{
		if( _dialogFlag )
		{
			
			treeObj.checkNode( treeNode, !treeNode.checked, true);
			commonsFunc.getCheckNodeValue( treeNode );
			
			if( !_radioFlag )
			{
				commonsFunc.nodeAsyncLoad(treeNode, false, false);
			}
		}
		else
		{
			var data;
			if( _radioFlag )
			{
				if( !_parentCheckFlag )
				{
					treeObj.checkNode( treeNode, !treeNode.checked, true);					
				}
				data = commonsFunc.getCheckNodeValue( treeNode );
			}
			else
			{
				treeObj.checkNode( treeNode, !treeNode.checked, true);
				commonsFunc.nodeAsyncLoad(treeNode, false, false);
				data = commonsFunc.getCheckNodeValue();
			}	
		}
		commonsFunc.clearNoCheckButHalf();
	},
	
	//选定节点时，使值显示
	this.onCheckSelectValue = function( event, treeId, treeNode )
	{
		if(!initResult)
		{
			commonsFunc.showValidErrorMsg("初始化错误，无法进行此操作！！！");
			return;
		}
		commonsFunc.nodeAsyncLoad(treeNode, false, false);
		if(_radioFlag && !_parentCheckFlag )
		{
			data = commonsFunc.getCheckNodeValue( treeNode );
		}
		else
		{
			commonsFunc.getCheckNodeValue();
		}
		commonsFunc.clearNoCheckButHalf();
	},
	
	//获得树对象控件容器
	this.getOrgObj = function()
	{
		if(!initResult)
		{
			commonsFunc.showValidErrorMsg("初始化错误，无法获取容器！！！");
			return;
		}
		return orgSelectDivObj;
	},
	
	//获取选择的节点列表
	this.getSelectOrgList = function(){
		if( !relayLoadFlag )
		{
			if(!initResult)
			{
				commonsFunc.showValidErrorMsg("初始化错误，无法获取选择节点！！！");
				return;
			}
		}
		return commonsFunc.getAllCheckedNodes();
	},
	
	//控件重置方法
	this.tagReset = function()
	{
		if(!initResult)
		{
			commonsFunc.showValidErrorMsg("初始化错误，无法进行重置！！！");
			return;
		}
		if( !resetResult )
		{
			return;
		}
		resetResult = false;
		this.loadTagList();
		commonsFunc.createShowMsgImg("重置成功！！！");
		window.setTimeout(this.removeSiblingsSpan, 1000);
	},
	// 移除控件同辈信息
	this.removeSiblingsSpan = function()
	{
		$(tagParent).find("#" + $(orgSelectDivObj).attr("id") + "MsgImg").remove();
		if( !_dialogFlag )
		{
			$(orgSelectDivObj).css({
				"display" : "block"
			});
		}
		resetResult = true;
	},
	// 获取控件内置对象
	this.getTagSelfData = function()
	{
		if(!initResult)
		{
			commonsFunc.showValidErrorMsg("初始化错误，无法获取内置对象！！！");
			return;
		}
		return selfData;
	},
	// 获取控件动态参数控制对象
	this.getDynamicManager = function()
	{
		if( !relayLoadFlag )
		{
			if(!initResult)
			{
				commonsFunc.showValidErrorMsg("初始化错误，无法获取参数控制对象！！！");
				return;
			}
		}
		
		if( _dynamicUpdateFlag )
		{
			return dynamicManager;
		}
		else
		{
			return null;
		}
	},
	//动态修改控件
	this.dynamicInitTagInit = function()
	{
		if( !relayLoadFlag )
		{
			if(!initResult)
			{
				commonsFunc.showValidErrorMsg("初始化错误，无法进行回归！！！");
				return;
			}
		}
		
		if( !resetResult )
		{
			return;
		}
		resetResult = false;
		if( dynamicManager.getDynamicResult() )
		{
			commonsFunc.showDialogBack();
			this.setDynamicArgument();
			initResult = false;
			this.tagInit();
			dynamicManager.init();
			dynamicResult = true;
		}
		commonsFunc.createShowMsgImg("动态成功！！！");
		window.setTimeout(this.removeSiblingsSpan, 1000);
	},
	// 在实行动态前回归
	this.beforeDynamicBack = function()
	{
		this.getDataFromArgument(dynamicAfter);
		dynamicManager.init();
	},
	// 在实行动态后回归
//	this.afterDynamicBack = function()
//	{
//		if( !relayLoadFlag )
//		{
//			if(!initResult)
//			{
//				commonsFunc.showValidErrorMsg("初始化错误，无法进行回归！！！");
//				return;
//			}
//		}
//		if( !resetResult )
//		{
//			return;
//		}
//		if( dynamicResult )
//		{
//			resetResult = false;
//			commonsFunc.showDialogBack();
//			this.getDataFromArgument(dynamicAfter, dynamicBefore);
//			dynamicManager.dynamicBack();
//		
//			this.setDynamicArgument();
//			initResult = false;
//			this.tagInit();
//			dynamicManager.init();
//			dynamicResult = false;
//			commonsFunc.createShowMsgImg("动态成功！！！");
//			window.setTimeout(this.removeSiblingsSpan, 1000);
//		}
//	},
	// 从参数值获取对象
	this.getDataFromArgument = function(data, fromData)
	{
		if( !data ) return;
		// 控制对象使用参数值
		if(fromData)
		{
			data["suffix"] 				= fromData.suffix;				
			data["rootNodeId"] 			= fromData.rootNodeId;		
			data["rootLevel"] 			= fromData.rootLevel;	
			data["initValue"] 			= fromData.initValue;
			data["jsVarGetValue"] 		= fromData.jsVarGetValue;
			data["showInputFlag"] 		= fromData.showInputFlag;		
			data["className"] 			= fromData.className;		
			data["changeFun"] 			= fromData.changeFun;
			data["changeFunParams"]		= fromData.changeFunParams;
			data["radioFlag"] 			= fromData.radioFlag;		
			data["dialogFlag"] 			= fromData.dialogFlag;		
			data["treeType"] 			= fromData.treeType;
			data["buttonName"] 			= fromData.buttonName;	
			data["querySql"] 			= fromData.querySql;
			data["queryParams"] 		= fromData.queryParams;	
			data["leafLevel"] 			= fromData.leafLevel;	
			data["selHalfFlag"] 		= fromData.selHalfFlag;		
			data["ableInputFlag"] 		= fromData.ableInputFlag;		
			data["ableQuery"] 			= fromData.ableQuery;
			data["leafOnlyFlag"] 		= fromData.leafOnlyFlag;
			data["parentCheckFlag"] 	= fromData.parentCheckFlag;
			data["dynamicUpdateFlag"]	= fromData.dynamicUpdateFlag;
			data["asyncFlag"]			= fromData.asyncFlag;
			data["triggerEle"]			= fromData.triggerEle;
		}
		else
		{
			data["suffix"] 				= _suffix;				
			data["rootNodeId"] 			= _rootNodeId;		
			data["rootLevel"] 			= _rootLevel;	
			data["initValue"] 			= _initValue;
			data["jsVarGetValue"] 		= _jsVarGetValue;
			data["showInputFlag"] 		= _showInputFlag;		
			data["className"] 			= _className;		
			data["changeFun"] 			= _changeFun;
			data["changeFunParams"]		= _changeFunParams;
			data["radioFlag"] 			= _radioFlag;		
			data["dialogFlag"] 			= _dialogFlag;		
			data["treeType"] 			= _treeType;
			data["buttonName"] 			= _buttonName;	
			data["querySql"] 			= _querySql;
			data["queryParams"] 		= _queryParams;	
			data["leafLevel"] 			= _leafLevel;	
			data["selHalfFlag"] 		= _selHalfFlag;		
			data["ableInputFlag"] 		= _ableInputFlag;		
			data["ableQuery"] 			= _ableQuery;
			data["leafOnlyFlag"] 		= _leafOnlyFlag;
			data["parentCheckFlag"] 	= _parentCheckFlag;
			data["dynamicUpdateFlag"]	= _dynamicUpdateFlag;
			data["asyncFlag"]			= _asyncFlag;
			data["triggerEle"]			= _triggerEle;
		}
		
	},
	//异步加载
	this.nodeAsyncLoad = function(treeId, treeNode )
	{
		if(!initResult)
		{
			commonsFunc.showValidErrorMsg("初始化错误，无法进行此操作！！！");
			return;
		}
		commonsFunc.nodeAsyncLoad(treeNode, true, true);
	}
};