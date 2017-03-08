var TCFunctionCommons = function(selfData, argumentData)
{
	
	/*
	 * 
	 * 本方法用于---【将验证结果返回清除到初始值】
	 * 
	 */
	this.clearResultData = function()
	{
		selfData.resultData.result = false;
		selfData.resultData.message = null;
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【给验证结果赋值】
	 * _result 返回结果
	 * _message 返回信息
	 * 
	 */
	this.setResultData = function( _result, _message)
	{
		if(  !this.isEmptySpace( _result) && !this.argumentTypeCheck(_result, "boolean") )
		{
			selfData.resultData.result = false;
			selfData.resultData.message = "验证结果在赋值时传入非法参数！！";
		}
		else
		{
			selfData.resultData.result = _result;
			selfData.resultData.message = _message;
		}
		
		if( !selfData.resultData.result )
		{
			if( selfData.checComm.getCheckReAppo() )
			{
				this.showErrorMessage("参数重置验证发生，" +  selfData.resultData.message);
			}
			else
			{
				this.showErrorMessage(selfData.resultData.message);
			}
		}
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【空值判定：空格不考虑】
	 * _arg 参数
	 * 
	 */
	this.isEmptySpace = function( _arg )
	{
		var result = false;
		
		if( _arg == null || typeof(_arg) == 'undefined' || ( typeof(_arg) == 'string' && _arg == '') )
		{
			result = true;
		}
		
		return result;
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【空值判定】
	 * _arg 参数
	 * 
	 */
	this.isEmpty = function( _arg )
	{
		var result = false;
		
		if( _arg == null || typeof(_arg) == 'undefined' )
		{
			result = true;
		}
		
		_arg = this.removeSpace(_arg);
		
		if( _arg == '' )
		{
			result = true;
		}
		
		return result;
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【除去参数两边的空格】
	 * _arg 参数
	 * 
	 */
	this.removeSpace = function( _arg )
	{
		if( this.isEmptySpace(_arg) )
		{
			return _arg;
		}
		else
		{
			this.removeLeftSpace(_arg);
			this.removeRightSpace(_arg);
			return _arg;
		}
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【除去左边空格】
	 * _arg 参数
	 * 
	 */
	this.removeLeftSpace = function( _arg )
	{
		if( this.isEmptySpace(_arg) || typeof(_arg) != "string")
		{
			return _arg;
		}
		else
		{
			if( _arg.substr(0, 1) == ' ' )
			{
				_arg = _arg.substr(1);
				return selfData.funcComm.removeLeftSpace(_arg);
			}
			else
			{
				return _arg;
			}
		}
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【除去右边空格】
	 * _arg 参数
	 * 
	 */
	this.removeRightSpace = function( _arg )
	{
		if( this.isEmptySpace(_arg) || typeof(_arg) != "string" )
		{
			return _arg;
		}
		else
		{
			if( _arg.substr( _arg.length - 1, _arg.length ) == ' ' )
			{
				_arg = _arg.substr(0, _arg.length - 1);
				return selfData.funcComm.removeRightSpace(_arg);
			}
			else
			{
				return _arg;
			}
		}
	},
	
	
	
	/*
	 * 
	 * 本方法用于 --- 【参数类型判定】
	 * _arg 参数
	 * _argType 类型
	 * 
	 */
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
	
	
	
	/*
	 * 
	 * 本方法用于---【获取参数默认值】
	 * _arg 参数
	 * _defaultVal 默认值
	 * 
	 */
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
	
	
	
	/* 
	 * 
	 * 本方法用于---【求数组最大值】
	 * _aray 数组列表
	 * 
	 */
	this.getMaxArrayValue = function( _aray )
	{
		var max;
		if( !_aray || _aray.length == 0 )
		{
			this.setResultData(false, "求数组最大值时，传入参数为空！！" );
			return;
		}
		
		if( !this.argumentTypeCheck(_aray, "object") )
		{
			this.setResultData(false, "求数组最大值时，传入参数:类型【" +  _aray + ":" + typeof(_aray) + "】错误！！" );
			return;
		}
		
		max = _aray[0];
		for( var i = 0; i < _aray.length; i++ )
		{
			if( _aray[i] > max )
			{
				max = _aray[i];
			}
		}
		
		return max;
	},
	
	
	
	
	/*
	 * 
	 * 本方法用于---【求数组最小值】
	 * _aray 数组列表
	 * 
	 */
	this.getMinArrayValue = function( _aray )
	{
		var min;
		
		if( !_aray || _aray.length == 0 )
		{
			this.setResultData(false, "求数组最小值时，传入参数为空！！" );
			return;
		}
		
		if( !this.argumentTypeCheck(_aray, "object") )
		{
			this.setResultData(false, "求数组最小值时，传入参数:类型【" +  _aray + ":" + typeof(_aray) + "】错误！！" );
			return;
		}
		
		min = _aray[0];
		for( var i = 0; i < _aray.length; i++ )
		{
			if( _aray[i] < min )
			{
				min = _aray[i];
			}
		}
		
		return min;
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【数组输出成字符串】
	 * _aray 数组列表
	 * 
	 */
	this.getStringArrayValue = function( _aray )
	{
		var temp = "";
		if( !_aray || _aray.length == 0 ) return temp;
		
		for( var i = 0; i < _aray.length; i++ )
		{
			if( 0 == _aray.length - 1 )
			{
				temp += _aray[i];
			}
			else
			{
				temp += _aray[i] + ",";
			}
		}
		
		return temp;
	}
	
	
	
	/*
	 * 
	 * 本方法用于显示错误信息
	 * _message 显示信息
	 * 
	 */
	this.showErrorMessage = function( _message )
	{
		if( this.isEmpty(_message) )
		{
			return;
		}
		
		App.notyError(_message);
	}
	
}