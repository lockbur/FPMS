var DynamicManager = function(dynamicBefore, dynamicAfter)
{
	var dynamicResult = false;
	var dynamicTree = false;
	var dynamicStyle = false;
	
	// 动态重置
	this.init = function()
	{
		dynamicResult = false;
		dynamicTree = false;
		dynamicStyle = false;
	},
	this.dynamicBack = function()
	{
		dynamicResult = true;
		dynamicTree = true;
		dynamicStyle = true;
	},
	// 动态总结果
	this.getDynamicResult = function()
	{
		return dynamicResult;
	},
	// 树是否重加载
	this.getDynamicTree = function()
	{
		return dynamicTree;
	},
	// 显示样式重置
	this.getDynamicStyle = function()
	{
		return dynamicStyle;
	},
	//suffix 字段控制
	this.setSuffix = function( _suffix )
	{
		if( !_suffix || typeof(_suffix) != "string" ) return;
		if(_suffix.indexOf("_") == 0)
		{
			dynamicAfter["suffix"] = _suffix;
		}
		else
		{
			dynamicAfter["suffix"] = "_" + _suffix;
		}
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getSuffix = function()
	{
		return dynamicBefore.suffix;
	},
	//RootNodeId字段控制
	this.setRootNodeId = function( _rootNodeId )
	{
		if( !_rootNodeId || typeof(_rootNodeId) != "string" ) return;
		dynamicAfter["rootNodeId"] = _rootNodeId;
		dynamicResult = true;
		dynamicTree = true;
		dynamicStyle = true;
	},
	this.getRootNodeId = function()
	{
		return dynamicBefore.rootNodeId;
	},
	//RootLevel字段控制
	this.setRootLevel = function( _rootLevel )
	{
		if( !_rootLevel || typeof(_rootLevel) != "string" ) return;
		dynamicAfter["rootLevel"] = _rootLevel;
		dynamicResult = true;
		dynamicTree = true;
		dynamicStyle = true;
	},
	this.getRootLevel = function()
	{
		return dynamicBefore.rootLevel;
	},
	//InitValue字段控制
	this.setInitValue = function( _initValue )
	{
		if( !_initValue || typeof(_initValue) != "string" ) return;
		dynamicAfter["initValue"] = _initValue;
		dynamicResult = true;
		dynamicTree = true;
		dynamicStyle = true;
	},
	this.getInitValue = function()
	{
		return dynamicBefore.initValue;
	},
	//JsVarGetValue字段控制
	this.setJsVarGetValue = function( _jsVarGetValue )
	{
		if( !_jsVarGetValue || typeof(_jsVarGetValue) != "string" ) return;
		dynamicAfter["jsVarGetValue"] = _jsVarGetValue;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getJsVarGetValue = function()
	{
		return dynamicBefore.jsVarGetValue;
	},
	//ShowInputFlag字段控制
	this.setShowInputFlag = function( _showInputFlag )
	{
		if( (_showInputFlag == "undefined" || _showInputFlag == null ) || typeof(_showInputFlag) != "boolean" ) return;
		dynamicAfter["showInputFlag"] = _showInputFlag;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getShowInputFlag = function()
	{
		return dynamicBefore.showInputFlag;
	},
	//ClassName字段控制
	this.setClassName = function( _className )
	{
		if( !_className || typeof(_className) != "string" ) return;
		dynamicAfter["className"] = _className;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getClassName = function()
	{
		return dynamicBefore.className;
	},
	//ChangeFun字段控制
	this.setChangeFun = function( _changeFun )
	{
		if( !_changeFun || typeof(_changeFun) != "string" ) return;
		dynamicAfter["changeFun"] = _changeFun;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getChangeFun = function()
	{
		return dynamicBefore.changeFun;
	},
	//ChangeFunParams字段控制
	this.setChangeFunParams = function( _changeFunParams )
	{
		if( !_changeFunParams || typeof(_changeFunParams) != "string" ) return;
		dynamicAfter["changeFunParams"] = _changeFunParams;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getChangeFunParams = function()
	{
		return dynamicBefore.changeFunParams;
	},
	//RadioFlag字段控制
	this.setRadioFlag = function( _radioFlag )
	{
		if( (_radioFlag == "undefined" || _radioFlag == null) || typeof(_radioFlag) != "boolean" ) return;
		dynamicAfter["radioFlag"] = _radioFlag;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getRadioFlag = function()
	{
		return dynamicBefore.radioFlag;
	},
	//DialogFlag字段控制
	this.setDialogFlag = function( _dialogFlag )
	{
		if( (_dialogFlag == "undefined" || _dialogFlag == null) || typeof(_dialogFlag) != "boolean" ) return;
		dynamicAfter["dialogFlag"] = _dialogFlag;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getDialogFlag = function()
	{
		return dynamicBefore.dialogFlag;
	},
	//TreeType字段控制
	this.setTreeType = function( _treeType )
	{
		if( (_treeType == 'undefined' || _treeType == null) || typeof(_treeType) != "number" ) return;
		dynamicAfter["treeType"] = _treeType;
		dynamicResult = true;
		dynamicTree = true;
		dynamicStyle = true;
	},
	this.getTreeType = function()
	{
		return dynamicBefore.treeType;
	},
	//ButtonName字段控制
	this.setButtonName = function( _buttonName )
	{
		if( !_buttonName || typeof(_buttonName) != "string" ) return;
		dynamicAfter["buttonName"] = _buttonName;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getButtonName = function()
	{
		return dynamicBefore.buttonName;
	},
	//QuerySql字段控制
	this.setQuerySql = function( _querySql )
	{
		if( !_querySql || typeof(_querySql) != "string" ) return;
		dynamicAfter["querySql"] = _querySql;
		dynamicResult = true;
		dynamicTree = true;
		dynamicStyle = true;
	},
	this.getQuerySql = function()
	{
		return dynamicBefore.querySql;
	},
	//QueryParams字段控制
	this.setQueryParams = function( _queryParams )
	{
		if( !_queryParams || typeof(_queryParams) != "string" ) return;
		dynamicAfter["queryParams"] = _queryParams;
		dynamicResult = true;
		dynamicTree = true;
		dynamicStyle = true;
	},
	this.getQueryParams = function()
	{
		return dynamicBefore.queryParams;
	},
	//LeafLevel字段控制
	this.setLeafLevel = function( _leafLevel )
	{
		if( !_leafLevel || typeof(_leafLevel) != "string" ) return;
		dynamicAfter["leafLevel"] = _leafLevel;
		dynamicResult = true;
		dynamicTree = true;
		dynamicStyle = true;
	},
	this.getLeafLevel = function()
	{
		return dynamicBefore.leafLevel;
	},
	//SelHalfFlag字段控制
	this.setSelHalfFlag = function( _selHalfFlag )
	{
		if( (_selHalfFlag == "undefined" || _selHalfFlag == null) || typeof(_selHalfFlag) != "boolean" ) return;
		dynamicAfter["selHalfFlag"] = _selHalfFlag;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getSelHalfFlag = function()
	{
		return dynamicBefore.selHalfFlag;
	},
	//AbleInputFlag字段控制
	this.setAbleInputFlag = function( _ableInputFlag )
	{
		if( (_ableInputFlag == "undefined" || _ableInputFlag == null) || typeof(_ableInputFlag) != "boolean" ) return;
		dynamicAfter["ableInputFlag"] = _ableInputFlag;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getAbleInputFlag = function()
	{
		return dynamicBefore.ableInputFlag;
	},
	//AbleQuery字段控制
	this.setAbleQuery = function( _ableQuery )
	{
		if( (_ableQuery == "undefined" || _ableQuery == null) || typeof(_ableQuery) != "boolean" ) return;
		dynamicAfter["ableQuery"] = _ableQuery;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getAbleQuery = function()
	{
		return dynamicBefore.ableQuery;
	},
	//LeafOnlyFlag字段控制
	this.setLeafOnlyFlag = function( _leafOnlyFlag )
	{
		if( (_leafOnlyFlag == "undefined" || _leafOnlyFlag == null) || typeof(_leafOnlyFlag) != "boolean" ) return;
		dynamicAfter["leafOnlyFlag"] = _leafOnlyFlag;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getLeafOnlyFlag = function()
	{
		return dynamicBefore.leafOnlyFlag;
	},
	//ParentCheckFlag字段控制
	this.setParentCheckFlag = function( _parentCheckFlag )
	{
		if( (_parentCheckFlag == "undefined" || _parentCheckFlag == null) || typeof(_parentCheckFlag) != "boolean" ) return;
		dynamicAfter["parentCheckFlag"] = _parentCheckFlag;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getParentCheckFlag = function()
	{
		return dynamicBefore.parentCheckFlag;
	},
	// triggerEle 字段控制
	this.setTriggerEle = function(_triggerEle)
	{
		if( !_triggerEle || typeof(_triggerEle) != "string" ) return;
		dynamicAfter["triggerEle"] = _triggerEle;
		dynamicResult = true;
		dynamicStyle = true;
		dynamicTree = true;
	},
	this.getTriggerEle = function()
	{
		return dynamicBefore.triggerEle;
	}
}