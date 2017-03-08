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
 *  _appoRule		  特殊列合并的规则 	 appoRule[0] = {"appo":4, rule:[0]};
 *  _uniqIndex		  唯一性列下标数组		 uniqIndex = { appo:[1,2,3], rule:[0,1]};
 *  
 */

var TableCombine = function()
{
	var varTc = this;
	var ignoreIndex;
	var appoRule;
	var uniqIndex;
	
	// 多表合并
	this.rowspanTable = function(_tableId, _startTrIndex, _endTrIndex, _startTdIndex, _endTdIndex, _ignoreIndex, _appoRule, _uniqIndex)
	{
		if( !_tableId ) return;
		
		var tableObj;
		
		// id查找table
		if( !tableObj )
		{
			tableObj = $("table[id='" + _tableId + "']");
		}
		
		if( !tableObj )
		{
			return;
		}

		// _startTrIndex 验证
		_startTrIndex =  this.argumentDefaultValue(_startTrIndex, 0);
		if( !this.argumentTypeCheck(_startTrIndex, "number") )
		{
			App.notyError("传入合并开始【行】下标【数据类型】错误！");
			return;
		} 
		
		// _endTrIndex   验证
		if( !this.argumentTypeCheck(_endTrIndex, "number") )
		{
			App.notyError("传入合并结束【行】下标【数据类型】错误！");
			return;
		}
		
		// _startTdIndex 验证
		_startTdIndex = this.argumentDefaultValue(_startTdIndex, 0);
		if( !this.argumentTypeCheck(_startTdIndex, "number") ) 
		{
			App.notyError("传入合并开始【列】下标【数据类型】错误！");
			return;
		}
			
		
		// _endTrIndex   验证
		if( !this.argumentTypeCheck(_endTdIndex, "number") )
		{
			App.notyError("传入合并结束【列】下标【数据类型】错误！");
			return;
		}
		
		// 忽略列数组
		if( _ignoreIndex && _ignoreIndex.length > 0 )
		{
			ignoreIndex = _ignoreIndex;
		}
		
		// 指定规则
		if( _appoRule && _appoRule.length > 0 )
		{
			appoRule = _appoRule;
		}
		
		// 唯一性指定
		if( _uniqIndex )
		{
			uniqIndex = _uniqIndex;
		}
		
		$(tableObj).each(function(){
			
			// 结束【行】下表矫正
			if( !_endTrIndex || $(this).find("tr").size() < _endTrIndex  )
			{
				_endTrIndex = $(this).find("tr").size();
			}
			// 结束【列】下表矫正
			if( !_endTdIndex || $(this).find("tr:eq(" + _startTrIndex + ")").find("td").size() < _endTdIndex  )
			{
				_endTdIndex = $(this).find("tr:eq(" + _startTrIndex + ")").find("td").size();
			}
			varTc.tableCombine( this, _startTrIndex, _endTrIndex, _startTdIndex, _endTdIndex);
		});
	},
	// 单个table对象验证
	this.tableCombine = function(_tableObj, _startTrIndex, _endTrIndex, _startTdIndex, _endTdIndex)
	{
		if( $(_tableObj).size()  == 0  ) return;
		
		// 递归出口 【开始列】大于【结束列】
		if( _startTdIndex > _endTdIndex )
		{
			return;
		}
		else
		{
			this.trCombine(_tableObj, _startTrIndex, _endTrIndex, _startTdIndex, _endTdIndex);
			varTc.tableCombine(_tableObj, _startTrIndex, _endTrIndex, _startTdIndex, _endTdIndex - 1);
		}	
	},
	this.trCombine = function( _tableObj, _trIndex, _endTrIndex, _startTdIndex, _endTdIndex )
	{
		if( _trIndex == _endTrIndex )
		{
			return;
		}
		
		var checkTr = $(_tableObj).find("tr:eq(" + _trIndex + ")");
		var checkTrList = $(_tableObj).find("tr:gt(" + _trIndex +"):lt(" + (_endTrIndex + 1) + ")");
		
		var checkNum = $(checkTrList).size();
		
		if( checkNum > 0 )
		{
			var index = 1;
			for(var i = 0; i < checkNum; i++)
			{
				if( this.twoTrEqual( checkTr, checkTrList[i], _startTdIndex, _endTdIndex ) )
				{
					index++;
					$(checkTrList[i]).find("td:eq(" + _endTdIndex + ")").remove();
				}
				else
				{
					break;
				}
			}
			if( index != 1 )
			{
				$(checkTr).find("td:eq(" + _endTdIndex + ")").attr("rowspan", index);
			}
			_trIndex = _trIndex + index;
			varTc.trCombine( _tableObj, _trIndex, _endTrIndex, _startTdIndex, _endTdIndex);
		}
	},
	// 两行数据相等判断
	this.twoTrEqual = function( _oneTr, _twoTr, _startTdIndex, _endTdIndex)
	{
		if( $(_oneTr).size() == 0 || $(_twoTr).size() == 0 ) return;
		
		var result = true;
		
		// 忽略查询结果
		if( this.ignoreIndexSearch(_endTdIndex) )
		{
			result = false;
		}
		// 自定义规则
		else 
		{
			// 查询此列合并是否有指定规则
			var data = this.appoRuleSearch( _endTdIndex );
			
			if( data )
			{
				// 规则检索结果
				result =  this.appoRuleCheck(_oneTr, _twoTr, data);
				if( result )
				{
					if( $(_oneTr).find("td:eq(" + _endTdIndex + ")").html() != $(_twoTr).find("td:eq(" + _endTdIndex + ")").html() )
					{
						result = false;
					}
				}
			}
			else
			{
				if( this.uniqIndexSearch( _endTdIndex ) )
				{
					// 主键唯一判定
					for( var i = 0; i < uniqIndex.rule.length; i++ )
					{
						if( !this.ignoreIndexSearch( uniqIndex.rule[i] ) )
						{
							if( _endTdIndex == uniqIndex.rule[i] )
							{
								break;
							}
							if( $(_oneTr).find("td:eq(" + uniqIndex.rule[i] + ")").html() != $(_twoTr).find("td:eq(" + uniqIndex.rule[i] + ")").html() )
							{
								result = false;
								break;
							}
						}
					}
					// 本行判定
					if( $(_oneTr).find("td:eq(" + _endTdIndex + ")").html() != $(_twoTr).find("td:eq(" + _endTdIndex + ")").html() )
					{
						result = false;
					}
				}
				else
				{
					// 一般比较结果
					for( var i = _startTdIndex; i <= _endTdIndex; i++ )
					{
						if( !this.ignoreIndexSearch(i) )
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
	},
	// 查询类型判定
	this.argumentTypeCheck = function( _arg, _argType)
	{
		if( _arg == null || typeof(_arg) == 'undefined' ) 
		{
			return true;
		}
		else
		{
			if( typeof(_arg) == _argType )
			{
				return true;
			}
			else
			{
				return false;
			}
		}
	},
	// 获取参数默认值
	this.argumentDefaultValue = function(_arg, _defaultVal)
	{
		if( _arg == null || typeof(_arg) == 'undefined' )
		{
			return _defaultVal;
		}
		else
		{
			return _arg;
		}
	},
	// 忽略列id搜索
	this.ignoreIndexSearch = function( _index )
	{
		var result = false;
		
		if( !_index ) return result;
		if( !ignoreIndex ) return result;
		
		for( var i = 0; i < ignoreIndex.length; i++ )
		{
			if( ignoreIndex[i] == _index )
			{
				result = true;
				break;
			}
		}
		
		return result;
	},
	//规则搜索
	this.appoRuleSearch = function( _index )
	{
		var result;
		if( !_index ) return result;
		if( !appoRule || appoRule.length == 0 ) return result;
				
		for( var i = 0; i < appoRule.length; i++)
		{
			if( appoRule[i].appo == _index )
			{
				result = appoRule[i].rule;
				break;
			}
		}
		
		return result;
	},
	// 规则判定
	this.appoRuleCheck = function(_oneTr, _twoTr, _data)
	{
		if( !_data || _data.length == 0 ) return result;
		
		result = true;
		
		for( var i = 0; i < _data.length; i++ )
		{
			if( !this.ignoreIndexSearch( _data[i] ) )
			{
				if( $(_oneTr).find("td:eq(" + _data[i] + ")").html() != $(_twoTr).find("td:eq(" + _data[i] + ")").html() )
				{
					result = false;
					break;
				}
			}
		}
		
		return result;
	},
	// 唯一性指定查询
	this.uniqIndexSearch = function( _index )
	{
		var result = false;
		if( !_index ) return result;
		if( !uniqIndex ) return result;
		
		if( !uniqIndex.appo ||  uniqIndex.appo.length == 0 ) return true;
		
		for( var i = 0; i < uniqIndex.appo.length; i++ )
		{
			if( uniqIndex.appo[i] == _index ) 
			{
				result = true;
				break;
			}
		}
		
		return result;
	}
}