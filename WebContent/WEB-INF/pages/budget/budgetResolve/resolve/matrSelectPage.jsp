<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="com.forms.prms.tool.ConstantMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="dialog">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>分解物料选择页面</title>
<base target="_self">

<script type="text/javascript">

//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	
	//页面初始化将之前选中的值勾选上(多选时使用数组接收参数)
	var selectedMatrArray = '${selectedMatrCodes}'.split(",");
	$("#tList").find("input[type='checkbox']").each(function(){
		for(var i=0;i<selectedMatrArray.length;i++){
			if($(this).val() == selectedMatrArray[i]){
				$(this).prop('checked','true');
				return;
			}
		}
	});
	allCheckedRelateFunc();		//初始化如果全勾选，则把组长也勾选上
	
}


$(function(){
	//CheckBox组长input单独勾选的处理
	$("#allMatrIds").click(function(){
		if($(this).is(":checked")){
			$(this).removeAttr("checked");
		}else{
			$(this).prop("checked",true);
		}
	});
	
	/*
	 *	单个物料组员点击勾选及关联处理
	 */
	$("input[name='checkedMatr']").click(function(){
		
		//单个物料点击勾选处理
		if($(this).is(":checked")){
			$(this).removeAttr("checked");
		}else{
			$(this).prop("checked",true);
		}
		//CheckBox组检查触发全选关联
		allCheckedRelateFunc();
	});
	
	/*
	 *	CheckBox组组长所在TR行勾选处理
	 *		描述：判断组长是否被勾选，判断未勾选时，该点击操作把组长包括组员全勾选上；判断为已勾选时，该点击操作后将组长和组员去掉勾选
	 */
	$("#allMatrIds").parent().parent().children().click(function(){
		if(!$("#allMatrIds").is(":checked")){
			$("input[type='checkbox']").each(function(){
				$(this).prop('checked',true);
			});
		}else{
			$("input[type='checkbox']").each(function(){
				$(this).removeAttr('checked');
			});
		}
// 		console.info("全选值："+$("#allMatrIds").is(":checked"));
	});
	
	/*
	 *	CheckBox组组员所在行的勾选处理
	 *		点击Tr将当前行的CheckBox选中
	 */
	$('table tr td').not(".checkClass").click(function(){
// 		$("table tr :checkbox").removeAttr('checked');		//单选时使用
		if($(this).parent().find(":checkbox").is(":checked")==true){
			$(this).parent().find(":checkbox").removeAttr('checked');
		}else{
			$(this).parent().find(":checkbox").prop('checked','true');
		}
		//CheckBox组检查触发全选关联
		allCheckedRelateFunc();
	});
	
	
});

/**
 *	【勾选方法】CheckBox组单个勾选(全选时)，检查是否全选触发全选关联
 *		描述：该方法包含两个参数--1.单个勾选的CheckBox组("input[name='checkedMatr']")；2.全勾选的CheckBox单个("#allMatrIds")
 */
function allCheckedRelateFunc(){
	var checkedAllFlag = true;	//单个勾选操作，如果判断最后一个也被勾选时，则将全选也勾上
	//检查改组中是否所有CheckBox全被勾选上
	$("input[name='checkedMatr']").each(function(){
		if(!$(this).is(":checked")){
			checkedAllFlag = false;
		}
	});
	//如果最后一个也勾上，则将该CheckBox的组长也勾选上；如果flag为false并且组长为勾选状态时，则将组长的勾选去掉
	if(checkedAllFlag){
		$("#allMatrIds").prop("checked",true);
	}
	else{
		if($("#allMatrIds").is(":checked")){
			$("#allMatrIds").removeAttr("checked");
		}
	}
}

/**
 * 【返回值处理】(字符串接收选中值)设置并返回页面关闭时返回值returnValue
 */
function getReturnValue(){
    var data = {};
    var selMatrCodes = "";
    var selMatrNames = "";
    
    $("input[name='checkedMatr']:checked").each(function(){
    	selMatrCodes += $(this).val()+",";
    	selMatrNames += $(this).parent().parent().find("input[name='checkedMatrName']").val()+",";
    });
    selMatrCodes = selMatrCodes.substr(0,selMatrCodes.length-1);
    selMatrNames = selMatrNames.substr(0,selMatrNames.length-1);

    data["matrCode"] = selMatrCodes;
 	data["matrName"] = selMatrNames;
	window.returnValue = data;
	window.close();
	return;
}

/**
 * 清空选中的值
 */
function resetSelected(){
	$("#tList").find("input[type='checkbox']").each(function(){
		$(this).prop('checked',false);
	});
}

</script>
<script type="text/javascript">
function getDataFromTable()
{
	var dataList = new Array();
	var index = 0;
	$("#findTr").parent().find("input[name='checkbox']:checked").each(function(){
		dataList[index++] = getDataFromTr( $(this).parent().parent() );
	});
	return dataList;
}
function getDataFromTr(obj)
{
	var data = {};
	$(obj).find("input").each(function(){
		data[ $(this).attr("name") ] = $(this).val();
	});
	return data;
}


</script>
</head>

<body>
<form action="" method="post"  id="selMatrForm">
    <table class="tableList" id="tList">
		<tr id="findTr">
<!-- 			<th width="15%" style="text-align: left"> -->
<!-- 				<input type="checkbox" onclick="Tool.toggleCheck(this,'checkedMatr');"/>全选 -->
<!-- 			</th> -->
			<th><input type="checkbox" id="allMatrIds" name="allMatrIds" class="checkClass" onclick="Tool.toggleCheck(this,'checkedMatr');"/></th>
			<th width="20%" style="text-align: center">物料编码</th>
			<th width="65%" style="text-align: center">物料名称</th>
		</tr>
		<c:forEach items="${matrList}" var="matr" varStatus="status">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor:pointer">
				<td style="text-align: left" id="checkBoxId">
					<input type="checkbox" name="checkedMatr" class="checkClass" value="${matr.matrCode}"/>
				</td>
				<td>${matr.matrCode}</td>
				<td>
					<input type="hidden" name="checkedMatrName" value="${matr.matrName}"/>
					${matr.matrName}
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty matrList}">
			<tr>
				<td colspan="3" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
	<br>
	<div>
				<input type="button" value="选择" onclick="getReturnValue();" />
				<input type="button" value="清空" onclick="resetSelected();"/>
				<input type="button" value="返回" onclick="window.close();" />
	</div>
</form>
<p:page/>
</body>
</html>