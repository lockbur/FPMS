var TCProcessCommons = function(selfData, argumentData)
{
	
	
	var removeColArr = new Array();	
	var colspanArr = new Array();
	var removeRowArr = new Array();	
	var rowspanArr = new Array();
	


//////////=======================================   removeColArr  start  ==========================================================//////	
	
	
	
	/*
	 * 
	 * 本方法用于---【清空删除单元格对象】																																																																																																																																																																											
	 * 
	 */
	this.clearRemoveColArr = function()
	{
		removeColArr = new Array();
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【添加删除单元格对象】																																																																																																																																																																											
	 * 
	 */
	this.setRemoveColArr = function( _tdObj )
	{
		if( selfData.funcComm.isEmptySpace(_tdObj)  )
		{
			return;
		}
		if( selfData.funcComm.argumentTypeCheck(_tdObj, "object") )
		{
			removeColArr[ removeColArr.length ] = _tdObj;
		}
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【获取删除单元格对象】																																																																																																																																																																											
	 * 
	 */
	this.getRemoveColArr = function()
	{
		if( !selfData.funcComm.isEmptySpace(removeColArr) && removeColArr.length != 0 )
		{
			for( var i = 0; i < removeColArr.length; i++)
			{
				$(removeColArr[i]).show();
			}
		}
		return removeColArr;
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【删除单元格】
	 * 
	 */
	this.runRemoveColArr = function()
	{
		if( !selfData.funcComm.isEmptySpace(removeColArr) && removeColArr.length != 0 )
		{
			for( var i = 0; i < removeColArr.length; i++)
			{
				if( selfData.simpleFlag )
				{
					$(removeColArr[i]).hide();
				}
				else
				{
					$(removeColArr[i]).remove();
				}
			}
			if( selfData.simpleFlag )
			{
				this.clearRemoveColArr();
			}
		}
	},
	
	
	
//////////=======================================   removeColArr  end  ==========================================================//////	
	
	
	
	
	
//////////=======================================   colspanArr  start  ==========================================================//////	
	
	
	
	/*
	 * 
	 * 本方法用于---【清空合并单元格对象】																																																																																																																																																																											
	 * 
	 */
	this.clearColspanArr = function()
	{
		colspanArr = new Array();
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【添加合并单元格对象】																																																																																																																																																																											
	 * 
	 */
	this.setColspanArr = function( _tdObj, _colspan )
	{
		if( selfData.funcComm.isEmptySpace(_tdObj) || selfData.funcComm.isEmptySpace(_colspan)  )
		{
			return;
		}
		if( selfData.funcComm.argumentTypeCheck(_tdObj, "object") && selfData.funcComm.argumentTypeCheck(_colspan, "number"))
		{
			colspanArr[ colspanArr.length ] = {
					tdObj : _tdObj,
					"colspan" : _colspan
			};
		}
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【获取合并单元格对象】																																																																																																																																																																											
	 * 
	 */
	this.getColspanArr = function()
	{
		if( !selfData.funcComm.isEmptySpace(colspanArr) && colspanArr.length != 0 )
		{
			for( var i = 0; i < colspanArr.length; i++)
			{
				$(colspanArr[i].tdObj).removeAttr("colspan");
			}
		}
	},
	
	
	
	
	/*
	 * 
	 * 本方法用于---【合并单元格】																																																																																																																																																																											
	 * 
	 */
	this.runColspanArr = function()
	{
		if( !selfData.funcComm.isEmptySpace(colspanArr) && colspanArr.length != 0 )
		{
			for( var i = 0; i < colspanArr.length; i++)
			{
				$(colspanArr[i].tdObj).attr("colspan", colspanArr[i].colspan);
			}
			if( selfData.simpleFlag )
			{
				this.clearColspanArr();
			}
		}
	},
	
	
	
//////////=======================================   colspanArr  end  ==========================================================//////	
	
	

	
//////////=======================================   removeRowArr  start  ==========================================================//////	
	
	
	
	/*
	 * 
	 * 本方法用于---【清空删除单元格对象】																																																																																																																																																																											
	 * 
	 */
	this.clearRemoveRowArr = function()
	{
		removeRowArr = new Array();
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【添加删除单元格对象】																																																																																																																																																																											
	 * 
	 */
	this.setRemoveRowArr = function( _tdObj )
	{
		if( selfData.funcComm.isEmptySpace(_tdObj)  )
		{
			return;
		}
		if( selfData.funcComm.argumentTypeCheck(_tdObj, "object") )
		{
			removeRowArr[ removeRowArr.length ] = _tdObj;
		}
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【添加删除单元格对象】																																																																																																																																																																											
	 * 
	 */
	this.getRemoveRowArr = function()
	{
		if( !selfData.funcComm.isEmptySpace(removeRowArr) && removeRowArr.length != 0 )
		{
			for( var i = 0; i < removeRowArr.length; i++)
			{
				$(removeRowArr[i]).show();
			}
		}
	},
	
	
	
	
	/*
	 * 
	 * 本方法用于---【删除单元格】
	 * 
	 */
	this.runRemoveRowArr = function()
	{
		if( !selfData.funcComm.isEmptySpace(removeRowArr) && removeRowArr.length != 0 )
		{
			for( var i = 0; i < removeRowArr.length; i++)
			{
				if( selfData.simpleFlag )
				{
					$(removeRowArr[i]).hide();
				}
				else
				{
					$(removeRowArr[i]).remove();
				}
			}
			if( selfData.simpleFlag )
			{
				this.clearRemoveRowArr();
			}
		}
	},
	
	
	
//////////=======================================   removeRowArr  end  ==========================================================//////		
	
	
	
	
//////////=======================================   rowspanArr  start  ==========================================================//////	
	
	
	
	/*
	 * 
	 * 本方法用于---【清空合并单元格对象】																																																																																																																																																																											
	 * 
	 */
	this.clearRowspanArr = function()
	{
		rowspanArr = new Array();
	},
	
	
	
	
	/*
	 * 
	 * 本方法用于---【添加合并单元格对象】																																																																																																																																																																											
	 * 
	 */
	this.setRowspanArr = function( _tdObj, _rowspan )
	{
		if( selfData.funcComm.isEmptySpace(_tdObj) || selfData.funcComm.isEmptySpace(_rowspan)  )
		{
			return;
		}
		if( selfData.funcComm.argumentTypeCheck(_tdObj, "object") && selfData.funcComm.argumentTypeCheck(_rowspan, "number"))
		{
			rowspanArr[ rowspanArr.length ] = {
					tdObj : _tdObj,
					"rowspan" : _rowspan
			};
		}
	},
	
	
	
	
	/*
	 * 
	 * 本方法用于---【添加合并单元格对象】																																																																																																																																																																											
	 * 
	 */
	this.getRowspanArr = function()
	{
		if( !selfData.funcComm.isEmptySpace(rowspanArr) && rowspanArr.length != 0 )
		{
			for( var i = 0; i < rowspanArr.length; i++)
			{
				$(rowspanArr[i].tdObj).removeAttr("rowspan");
			}
		}
	},
	
	
	
	
	/*
	 * 
	 * 本方法用于---【合并单元格】																																																																																																																																																																											
	 * 
	 */
	this.runRowspanArr = function()
	{
		if( !selfData.funcComm.isEmptySpace(rowspanArr) && rowspanArr.length != 0 )
		{
			for( var i = 0; i < rowspanArr.length; i++)
			{
				$(rowspanArr[i].tdObj).attr("rowspan", rowspanArr[i].rowspan);
			}
			if( selfData.simpleFlag )
			{
				this.clearRowspanArr();
			}
		}
	},
	
	
	
//////////=======================================   rowspanArr  end  ==========================================================//////		
	
	
	
	
//////////=======================================   rowCombine  start  ==========================================================//////		
	
	
	
	/*
	 * 
	 * 本方法用于---控制【合并行】
	 * 
	 */
	this.rowCombine = function( _tdIndex )
	{
		if( _tdIndex < argumentData.startTdIndex )
		{
			this.runRemoveRowArr();
			this.runRowspanArr();
			return;
		}
		else
		{
			this.rowCombineTr( argumentData.startTrIndex, _tdIndex );
			selfData.procComm.rowCombine( _tdIndex - 1 );
		}	
	},
	
	/*
	 * 
	 * 本方法用于---【行合并】
	 * 
	 */
	this.rowCombineTr = function(  _trIndex, _tdIndex )
	{
		if( _trIndex >= argumentData.endTrIndex )
		{
			return;
		}
		
		var checkTr = $(selfData.tableObj).find("tr:eq(" + _trIndex + ")");
		var checkTrList = $(selfData.tableObj).find("tr:gt(" + _trIndex +"):lt(" + (argumentData.endTrIndex + 1) + ")");
		
		var checkNum = $(checkTrList).size();
		if( checkNum > 0 )
		{
			var index = 1;
			for(var i = 0; i < checkNum; i++)
			{
				if( selfData.checComm.twoTrEqual( checkTr, checkTrList[i], argumentData.startTdIndex, _tdIndex ) )
				{
					index++;
					this.setRemoveRowArr( $(checkTrList[i]).find("td:eq(" + _tdIndex + ")") );
				}
				else
				{
					break;
				}
			}
			if( index != 1 )
			{
				this.setRowspanArr($(checkTr).find("td:eq(" + _tdIndex + ")"), index);
			}
			_trIndex = _trIndex + index;
			
			selfData.procComm.rowCombineTr(_trIndex, _tdIndex );
		}
	},
	
	
	
//////////=======================================   rowCombine  end  ==========================================================//////	
	
	
	
//////////=======================================   rowCombineBack  start  ==========================================================//////
	
	
	/*
	 * 
	 * 本方法用于---【行合并回归】
	 * 
	 * 
	 */
	this.rowCombineBack = function( _trIndex )
	{
		if( selfData.simpleFlag )
		{
			this.getRowspanArr();
			this.getRemoveRowArr();
		}
		else
		{
			if( _trIndex > argumentData.endTrIndex )
			{
				return;
			}
			else
			{
				this.rowCombineBackTr( _trIndex );
				selfData.procComm.rowCombineBack( _trIndex + 1 );
			}
		}
	},	
	
	
	
	
	/*
	 * 
	 * 本方法用于---【行合并回归】
	 * 
	 */
	this.rowCombineBackTr = function(  _trIndex  )
	{
		
		var checkTr = $(selfData.tableObj).find("tr:eq(" + _trIndex + ")");
		
		if( selfData.checComm.tdHaveCombine(checkTr) )
		{
			var combineValue;
			
			var index = 0;
			$(checkTr).find("td:gt(" + (argumentData.startTdIndex - 1) + "):lt(" +  (argumentData.endTdIndex + 1 ) + ")").each(function(){
				combineValue = $(this).attr("rowSpan");
				if( !selfData.funcComm.isEmpty( combineValue ) && parseInt(combineValue) > 1  )
				{
					for( var i = 1; i < parseInt(combineValue); i++ )
					{
						
					}
				}
			});
		}
	},
	
	
	
//////////=======================================   rowCombineBack  end  ==========================================================//////
	
	
	
	
//////////=======================================   colCombine  start  ==========================================================//////	
	
	
	
	/*
	 * 本方法用于---【表的列合并】
	 * 
	 */
	this.colCombine = function( _trIndex )
	{
		if( _trIndex > argumentData.endTrIndex )
		{
			this.runRemoveColArr();
			this.runColspanArr();
			return;
		}
		else
		{
			this.colCombineTr(_trIndex, argumentData.startTdIndex);
			selfData.procComm.colCombine( _trIndex + 1 );
		}
	},
	
	
	/*
	 * 
	 * 本方法用于---【实现列的合并】
	 * 
	 * 
	 */
	this.colCombineTr = function( _trIndex,_tdIndex )
	{
		if( _tdIndex > argumentData.endTdIndex )
		{
			return;
		}
		
		var checkTr = $(selfData.tableObj).find("tr:eq(" + _trIndex + ")");
		var checkTd = $(checkTr).find("td:eq(" + _tdIndex + ")");
		var tdList = $(checkTr).find("td:gt(" + _tdIndex + "):lt(" + (argumentData.endTdIndex + 1) + ")");
		var tdNum = $(tdList).size();
		
		var index = 1;
		
		if( tdNum > 0 )
		{
			for( var i = 0; i < tdNum; i++ )
			{
				if( $(checkTd).html() == $(tdList[i]).html() && $(checkTd).attr("rowspan") == $(tdList[i]).attr("rowspan")  )
				{
					index++;
					this.setRemoveColArr( tdList[i] );
				}
				else
				{
					break;
				}
			}
			
			if( index != 1 )
			{
				this.setColspanArr(checkTd, index);
			}
		}
		
		_tdIndex = _tdIndex + index;
		
		selfData.procComm.colCombineTr(_trIndex, _tdIndex);
		
	},
	
	
	
//////////=======================================   colCombine  end  ==========================================================//////		
	
	
	
	
	
//////////=======================================   colCombineBack  start  ==========================================================//////	
	
	
	
	/*
	 *  本方法用于---【表的列合并后的回归】
	 *  _tableObj 用于指定表
	 * 
	 */
	this.colCombineBack = function( _trIndex )
	{
		if( selfData.simpleFlag )
		{
			this.getColspanArr();
			this.getRemoveColArr();
		}
		else
		{
			if( _trIndex > argumentData.endTrIndex )
			{
				return;
			}
			else
			{
				this.colCombineBackTr( _trIndex );
				selfData.procComm.colCombineBack( _trIndex + 1 );
			}
		}
	},
	
	
	
	/*
	 * 
	 * 本方法用于---【行合并回归】
	 * 
	 */
	this.colCombineBackTr = function(  _trIndex  )
	{
		
		var checkTr = $(selfData.tableObj).find("tr:eq(" + _trIndex + ")");
		
		if( selfData.checComm.tdHaveCombine(checkTr) )
		{
			var combineValue;
			
			var index = 0;
			$(checkTr).find("td:gt(" + (argumentData.startTdIndex - 1) + "):lt(" +  (argumentData.endTdIndex + 1 ) + ")").each(function(){
				combineValue = $(this).attr("rowSpan");
				if( !selfData.funcComm.isEmpty( combineValue ) && parseInt(combineValue) > 1  )
				{
					for( var i = 1; i < parseInt(combineValue); i++ )
					{
						
					}
				}
			});
		}
	}
	
	
	
//////////=======================================   colCombineBack  end  ==========================================================//////	
	
}