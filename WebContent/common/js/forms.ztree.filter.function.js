var ZtreeFilterFunc = function( selfData, argumentsData )
{
	if(!selfData)
	{
		return;
	}
	if(!argumentsData)
	{
		return;
	}
	//获得打开的节点集合
	this.getAllOpenNode = function( node )
	{
		return (node.level >= 0 && node.open && !node.lock && node.queryOpen && !node.defaultOpen);
	},
	
	//获取初始化打开
	this.getAllNotDefaultOpenNode = function( node )
	{
		return (!node.defaultOpen && node.open);
	},
	
	//获取默认选择元素
	this.getAllDefaultSelectNode = function( node )
	{
		return (node.defaultSelect);
	},
	
	//获取选中的叶子节点
	this.getCheckedLeafNodes = function( node )
	{
		return ( !node.isParent && node.checked );
	},
	
	//获得全选状态的节点
	this.getCheckedButNoHalfNode = function( node )
	{
		return ( !node.getCheckStatus()['half'] && node.checked );
	},
	//获取父节点
	this.getAllParentNodes = function( node )
	{
		return node.isParent;
	},
	//获取不是勾选但是半勾选的节点
	this.getNoCheckButHalf = function( node )
	{
		return !node.checked && node.halfCheck;
	}

}