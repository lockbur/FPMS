var TCApporuleCommons = function(selfData, argumentData)
{
	/*
	 * 
	 * 本方法用于---【忽略列id搜索】
	 * 
	 */
	this.ignoreIndexSearch = function( _index )
	{
		var result = false;
		
		if( !selfData.funcComm.argumentTypeCheck(_index, "number") ) return result;
		if( selfData.funcComm.isEmptySpace(_index) ) return result;
		if( !argumentData.ignoreIndex ) return result;
		
		for( var i = 0; i < argumentData.ignoreIndex.length; i++ )
		{
			if( argumentData.ignoreIndex[i] == _index )
			{
				result = true;
				break;
			}
		}
		
		return result;
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【规则指定id搜索】
	 * 
	 */
	this.appoRuleSearch = function( _index )
	{
		var result;
		if( !selfData.funcComm.argumentTypeCheck(_index, "number") ) return result;
		if( selfData.funcComm.isEmptySpace(_index) ) return result;
		if( !argumentData.appoRule || argumentData.appoRule.length == 0 ) return result;
				
		for( var i = 0; i < argumentData.appoRule.length; i++)
		{
			for( var j = 0; j < argumentData.appoRule.appo.length; j++ )
			{
				if( argumentData.appoRule.appo[j] == _index )
				{
					result = argumentData.appoRule[i].rule;
					break;
				}
			}
			
			if( result )
			{
				break;
			}
		}
		
		return result;
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【规则规定判定】
	 * 
	 */
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
	
	
	
	
	/*
	 * 
	 * 本方法用于---【唯一性指定查询】
	 * 
	 */
	this.uniqIndexSearchAppo = function( _index )
	{
		var result = false;
		if( !selfData.funcComm.argumentTypeCheck(_index, "number") ) return result;
		if( selfData.funcComm.isEmptySpace(_index) ) return result;
		if( !argumentData.uniqIndex ) return result;
		
		if( !argumentData.uniqIndex.appo ||  argumentData.uniqIndex.appo.length == 0 ) return true;
		
		for( var i = 0; i < argumentData.uniqIndex.appo.length; i++ )
		{
			if( argumentData.uniqIndex.appo[i] == _index ) 
			{
				result = true;
				break;
			}
		}
		
		return result;
	}

	
}