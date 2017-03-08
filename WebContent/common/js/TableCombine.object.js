/*
 * 
 *  表合并调用方法  rowspanTable
 *  参数说明
 *  _tableIdOrName   合并表的id或者name或者class指定
 *  _startTrIndex	  合并开始行下标  默认为0
 *  _endTrIndex		  合并结束行下标   默认到结束行
 *  _startTdIndex	  合并开始列下标    默认为0
 *  _endTdIndex		  合并结束列下标    默认为开始列下标
 *  _ignoreIndex	  需要忽略的列下标数组
 *  _appoRule		  特殊列合并的规则 	 appoRule[0] = {appo:[4], rule:[0]};
 *  _uniqIndex		  唯一性列下标数组		 uniqIndex 	 = {appo:[1,2,3], rule:[0,1]};
 *  _totalIndex		  求和指定			 totalIndex  = {appo:[1], rule:[0], add:[], suffix:[], append:true };
 *  
 */

var TableCombineObj = function( _tableId, _startTrIndex, _endTrIndex, _startTdIndex, _endTdIndex, _ignoreIndex, _appoRule, _uniqIndex, _appoTotal )
{
	// 自我参数对象
	var selfData = {
		tableObj: null,
		simpleFlag:true,
		resultData:{
				result:false,
				"message":null
		},
		initFlag: false
	};
	
	// 传入参数对象
	var argumentData = {
			"tableId":_tableId, 
			"startTrIndex":_startTrIndex, 
			"endTrIndex":_endTrIndex, 
			"startTdIndex":_startTdIndex, 
			"endTdIndex":_endTdIndex, 
			ignoreIndex:_ignoreIndex, 
			appoRule:_appoRule, 
			uniqIndex:_uniqIndex, 
			appoTotal:_appoTotal
	};
	
	
	// 0 封装本身对象
	var tableCombineObj = this;
	
	selfData["tableCombineObj"] = tableCombineObj;
	
	
	// 1 封装辅助对象
	var procComm = new TCProcessCommons(selfData, argumentData);
	var funcComm = new TCFunctionCommons(selfData, argumentData);
	var checComm = new TCCheckCommons(selfData, argumentData);
	var apruComm = new TCApporuleCommons(selfData, argumentData);
	
	selfData["procComm"] = procComm;
	selfData["funcComm"] = funcComm;
	selfData["checComm"] = checComm;
	selfData["apruComm"] = apruComm;
	
	checComm.combineArgumentCheck();
	
	if( selfData.resultData.result )
	{
		selfData.initFlag = true;
	}
	else
	{
		funcComm.showErrorMessage(selfData.resultData.message);
		return;
	}
	
	
	
	/*
	 * 本方法用于---【表格参数重定】
	 * 
	 */
	this.argumentReAppoint = function( _tableId, _startTrIndex, _endTrIndex, _startTdIndex, _endTdIndex,_ignoreIndex, _appoRule, _uniqIndex, _appoTotal )
	{
		var reArgumentData = {
				"tableId":_tableId, 
				"startTrIndex":_startTrIndex, 
				"endTrIndex":_endTrIndex, 
				"startTdIndex":_startTdIndex, 
				"endTdIndex":_endTdIndex, 
				ignoreIndex:_ignoreIndex, 
				appoRule:_appoRule, 
				uniqIndex:_uniqIndex, 
				appoTotal:_appoTotal
		};
		
		checComm.combineArgumentCheck( reArgumentData );
		
		if( selfData.resultData.result )
		{
			selfData.initFlag = true;
		}
		else
		{
			funcComm.showErrorMessage(selfData.resultData.message);
			return;
		}
		
	},
	
	
	
	/*
	 *  本方法用于---【表行的合并】
	 * 
	 */
	this.rowCombine = function()
	{
		if( !selfData.initFlag )
		{
			funcComm.showErrorMessage("表格合并初始化失败！行合并方法无法调用");
			return;
		}
		
		procComm.rowCombine( argumentData.endTdIndex );
	},
	
	
	
	/*
	 * 本方法用于---【表行合并的回归】
	 * _tableObj 用于指定表
	 * 
	 */
	
	this.rowCombineBack = function()
	{
		if( !selfData.initFlag )
		{
			funcComm.showErrorMessage("表格合并初始化失败！行合并回归方法无法调用");
			return;
		}
		procComm.rowCombineBack( argumentData.startTrIndex);
	},
	
	
	
	/*
	 * 本方法用于---【表的列合并】
	 * 
	 */
	this.colCombine = function()
	{
		if( !selfData.initFlag )
		{
			funcComm.showErrorMessage("表格合并初始化失败！列合并方法无法调用");
			return;
		}
		procComm.colCombine( argumentData.startTdIndex );
	},
	
	
	
	/*
	 *  本方法用于---【表的列合并后的回归】
	 *  _tableObj 用于指定表
	 * 
	 */
	this.colCombineBack = function()
	{
		if( !selfData.initFlag )
		{
			funcComm.showErrorMessage("表格合并初始化失败！列合并回归方法无法调用");
			return;
		}
		procComm.colCombineBack( argumentData.startTrIndex );
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【表的行列合并】
	 * _tableObj 用于指定表
	 * 
	 */
	this.combine = function()
	{
		if( !selfData.initFlag )
		{
			funcComm.showErrorMessage("表格合并初始化失败！行列合并方法无法调用");
			return;
		}
		this.rowCombine();
		this.colCombine();
	},
	
	
	
	/*
	 * 
	 *  本方法用于---【表行列合并回归】
	 *  _tableObj 用于指定表
	 * 
	 */
	this.combineBack = function()
	{
		if( !selfData.initFlag )
		{
			funcComm.showErrorMessage("表格合并初始化失败！行列合并回归方法无法调用");
			return;
		}
		this.rowCombineBack();
		this.colCombineBack();
	}
	
	
	
}