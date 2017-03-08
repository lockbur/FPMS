var TCCheckCommons = function(selfData, argumentData)
{	
	
	var reAppo = false;
	
	
	/*
	 * 
	 * 本方法用于---【获取本次验证是否是重置】
	 * 
	 * 
	 */
	this.getCheckReAppo = function()
	{
		return reAppo;
	},
	
	
	
	/*
	 * 
	 * 本方法用于----【验证合并表的参数是否合法】
	 *  
	 */
	this.combineArgumentCheck = function( checkData )
	{	
		if( !checkData )
		{
			checkData = argumentData;
		}
		else
		{
			reAppo = true;
		}
		// 0 初始化方式结果
		selfData.funcComm.clearResultData();
		
		// 1.0.0  验证传入表id是否为空
		if( !checkData.tableId ) return resultData;
		
		if( $("table[id='" + checkData.tableId + "']").size() == 0 )
		{
			selfData.funcComm.setResultData(false, "传入合并表的id不存在！");
			return;
		}
		else if( $("table[id='" + checkData.tableId + "']").size() > 1 )
		{
			selfData.funcComm.setResultData(false, "传入合并表的id同时指向多个表！！");
			return;
		}
		

		// 1.1.0 startTrIndex 验证
		checkData.startTrIndex =  selfData.funcComm.argumentDefaultValue(checkData.startTrIndex, 0);
		if( !selfData.funcComm.argumentTypeCheck(checkData.startTrIndex, "number") )
		{
			selfData.funcComm.setResultData(false, "传入合并开始【行】下标【数据类型】错误！");
			return;
		} 
		
		// 1.1.1 endTrIndex   验证
		if( !selfData.funcComm.argumentTypeCheck(checkData.endTrIndex, "number") )
		{
			selfData.funcComm.setResultData(false, "传入合并结束【行】下标【数据类型】错误！");
			return;
		}
		
		// 1.2.0 startTdIndex 验证
		checkData.startTdIndex = selfData.funcComm.argumentDefaultValue(checkData.startTdIndex, 0);
		if( !selfData.funcComm.argumentTypeCheck(checkData.startTdIndex, "number") ) 
		{
			selfData.funcComm.setResultData(false, "传入合并开始【列】下标【数据类型】错误！");
			return;
		}
			
		
		// 1.2.1 endTrIndex   验证
		if( !selfData.funcComm.argumentTypeCheck(checkData.endTdIndex, "number") )
		{
			selfData.funcComm.setResultData(false, "传入合并结束【列】下标【数据类型】错误！");
			return;
		}
		
		
		// 1.4.0 忽略列数组
		if( selfData.funcComm.argumentTypeCheck(checkData.ignoreIndex, "object") )
		{
			if( !checkData.ignoreIndex || checkData.ignoreIndex.length == 0 )
			{
				selfData.ignoreIndex = null;
			}
		}
		else
		{
			selfData.funcComm.setResultData(false, "传入忽略列下标不是数组！");
			return;
		}
		
		
		var max = 0;
		var min = 0;
		
		// 1.5.0 指定规则
		if( selfData.funcComm.argumentTypeCheck(checkData.appoRule, "object") )
		{
			if( !checkData.appoRule || checkData.appoRule.length == 0 )
			{
				checkData.appoRule = null;
			}
			else
			{
				
//				for( var i = 0; i < checkData.appoRule.length; i++ )
//				{
//					max = selfData.funcComm.getMaxArrayValue(checkData.appoRule[i].rule);
//					min = selfData.funcComm.getMinArrayValue(checkData.appoRule[i].appo);
//					
//					
//					if( min < max )
//					{
//						selfData.funcComm.setResultData(false, "规则指定时：【" + selfData.funcComm.getStringArrayValue(checkData.appoRule[i].appo) + "】指定的规则【" + selfData.funcComm.getStringArrayValue(checkData.appoRule[i].rule) + "】有误！！"  );
//						return;
//					}
//				}
			}
			
		}
		else
		{
			selfData.funcComm.setResultData( false, "传入忽略列下标不是数组！");
			return;
		}
		
		
		// 1.6.0 唯一性指定
//		if( checkData.uniqIndex )
//		{			
//			max = selfData.funcComm.getMaxArrayValue(checkData.uniqIndex.rule);
//			min = selfData.funcComm.getMinArrayValue(checkData.uniqIndex.appo);
//			
//			if( min < max )
//			{
//				selfData.funcComm.setResultData(false, "唯一性规则指定错误！！！" );
//				return;
//			}
//		}	
		
		// 重置参数时，需要拷贝
		if( this.getCheckReAppo() )
		{
			this.copyAruments(checkData);
		}
		
		// 获取表对象
		selfData.tableObj = $("table[id='" + argumentData.tableId + "']");
		
		this.tableRowAndColUpdate();
				
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【修改表格合并结束行列值】
	 * 
	 * 
	 */
	this.tableRowAndColUpdate = function()
	{
		
//		if( this.trHaveCombine() )
//		{
//			selfData.funcComm.setResultData( false, "需要操作的表格，已经存在合并现象！！");
//			return;
//		}
		
		
		// 结束【行】下表矫正
		if( !argumentData.endTrIndex || $(selfData.tableObj).find("tr").size() < argumentData.endTrIndex  )
		{
			argumentData.endTrIndex = $(selfData.tableObj).find("tr").size();
		}
		
		
		// 结束【列】下表矫正
		if( !argumentData.endTdIndex || $(selfData.tableObj).find("tr:eq(" + argumentData.startTrIndex + ")").find("td").size() < argumentData.endTdIndex  )
		{
			argumentData.endTdIndex = $(selfData.tableObj).find("tr:eq(" + argumentData.startTrIndex + ")").find("td").size();
		}
		
		
		selfData.funcComm.setResultData(true, "初始化验证通过");
	},
	
	
	/*
	 * 
	 * 本方法用于---【检查行是否有合并现象】
	 * 
	 */
	this.trHaveCombine = function()
	{
		var result = false;
		
		var trList;
		if( argumentData.endTrIndex )
		{
			trList = $(selfData.tableObj).find("tr:gt(" + (argumentData.startTrIndex - 1)  + "):lt(" (argumentData.endTrIndex - 1) + ")");
		}
		else
		{
			trList = $(selfData.tableObj).find("tr:gt(" + (argumentData.startTrIndex - 1)  + ")");
		}
		for( var i = 0; i < trList.length; i++ )
		{
			if( this.tdHaveCombine( trList[i]) )
			{
				result = true;
				break;
			}
		}
		
		return result;
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【检查行是否有合并现象】
	 * 
	 */
	this.tdHaveCombine = function( _checkTr )
	{
		var result = false;
		var combineValue;
		
		$(_checkTr).find("td").each(function(){
			
			if( !result )
			{
				combineValue = $(this).attr("rowspan");
				
				if( !selfData.funcComm.isEmpty( combineValue ) && parseInt(combineValue) > 1  )
				{
					result = true;
				}
				
				combineValue = $(this).attr("colspan");
				
				if( !selfData.funcComm.isEmpty( combineValue ) && parseInt(combineValue) > 1   )
				{
					result = true;
				}
			}			
		});
		
		return result;
	}
	
	
	
	
	/*
	 * 
	 * 本方法用于---【参数拷贝】
	 * 
	 * 
	 */
	this.copyAruments = function( checkData )
	{
		if( checkData )
		{
			argumentData.tableId = checkData.tableId;
			argumentData.startTrIndex = checkData.startTrIndex; 
			argumentData.endTrIndex = checkData.endTrIndex; 
			argumentData.startTdIndex = checkData.startTdIndex;
			argumentData.endTdIndex = checkData.endTdIndex; 
			argumentData.ignoreIndex = checkData.ignoreIndex; 
			argumentData.appoRule = checkData.appoRule;
			argumentData.uniqIndex = checkData.uniqIndex; 
			argumentData.appoTotal = checkData.appoTotal;
		}
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【两行数据相等判断】
	 * 
	 * 
	 */
	this.twoTrEqual = function( _oneTr, _twoTr, _startTdIndex, _endTdIndex)
	{
		
		if( $(_oneTr).size() == 0 || $(_twoTr).size() == 0 ) return;
		
		var result = true;
		var tdIndex = _endTdIndex;
		
		// 忽略查询结果
		if( selfData.apruComm.ignoreIndexSearch( tdIndex ) )
		{
			result = false;
		}
		// 自定义规则
		else 
		{
			
			
			// 查询此列合并是否有指定规则
			var data = selfData.apruComm.appoRuleSearch( tdIndex );
			
			if( data )
			{
				
				
				// 规则检索结果
				result =  selfData.apruComm.appoRuleCheck(_oneTr, _twoTr, data);
				
				if( result )
				{
					if( $(_oneTr).find("td:eq(" + tdIndex + ")").html() != $(_twoTr).find("td:eq(" + tdIndex + ")").html() )
					{
						result = false;
					}
				}
				
				
			}
			else
			{
				
				
				if( selfData.apruComm.uniqIndexSearchAppo( tdIndex ) )
				{
					
					
					// 主键唯一判定
					for( var i = 0; i < argumentData.uniqIndex.rule.length; i++ )
					{
						if( !selfData.apruComm.ignoreIndexSearch( argumentData.uniqIndex.rule[i] ) )
						{
							if( tdIndex == argumentData.uniqIndex.rule[i] )
							{
								break;
							}
							if( $(_oneTr).find("td:eq(" + argumentData.uniqIndex.rule[i] + ")").html() != $(_twoTr).find("td:eq(" + argumentData.uniqIndex.rule[i] + ")").html() )
							{
								result = false;
								break;
							}
						}
					}
					// 本行判定
					if( $(_oneTr).find("td:eq(" + tdIndex + ")").html() != $(_twoTr).find("td:eq(" + tdIndex + ")").html() )
					{
						result = false;
					}
					
					
					
				}
				else
				{
					
					
					// 一般比较结果
					for( var i = _startTdIndex; i <= _endTdIndex; i++ )
					{
						if( !selfData.apruComm.ignoreIndexSearch(i) )
						{
							if( $(_oneTr).find("td:eq(" + i + ")").html() != $(_twoTr).find("td:eq(" + i + ")").html() )
							{
								result = false;
								break;
							}
						}
					}
					
					
				}
				
				
			}
		}
				
		return result;
	}
}