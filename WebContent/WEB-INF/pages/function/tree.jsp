<%@page import="com.forms.prms.web.util.MenuUtils"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.authority.domain.Function"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/component/dtree/css/dhtmlxtree_std.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std_patch.js"></script>
<title>菜单查询</title>
<style type="text/css">
/* .subDiv { */
/*  	position: relative; */
/*  	top:500px;  */
/* 	background-color: red; */
}
</style>
<script type="text/javascript">
var tree = null;
function pageInit() {
// 	App.jqueryAutocomplete();
// 	$("select").addClass("erp_cascade_select");
// 	$("select").combobox();

	<%
	String jsonData = WebUtils.getRequestAttr("funcTree");
	%>
	var treeObj = <%=Tool.CHECK.isEmpty(jsonData) ? "null" : jsonData%>;
	
	if (treeObj != null) {
		tree = new dhtmlXTreeObject("treepanel","100%","100%",0);
		tree.setImagesPath('<%=request.getContextPath()%>/component/dtree/css/imgs/');
		tree.enableTreeLines(true);
		tree.enableDragAndDrop(true, false);
		tree.setDragBehavior('complex', true);
		tree.makeAllDraggable();
		tree.attachEvent("onDrag",function(sId,tId,id,sObject,tObject){
			
			
// 			return confirm("确认要移动指定功能？");

			return true;
		});
// 			App.submit(form);
		tree.attachEvent("onDrop", function(sId,tId,id){
			var form=$("#funcForm")[0];
			form.action = "<%=request.getContextPath()%>/function/move.do?<%=WebConsts.FUNC_ID_KEY%>=01010104";
// 			form.action = App.ROOT + 'function/move.do?' + App.FUNC_KEY + '=FUNC_MOVE';
			var subItems = tree.getSubItems(tId);
			var subItemsArray = subItems.split(',');
			var targetFuncList = {};
			for (var i = 0; i < subItemsArray.length; i++) {
				$('<input type="hidden" name="funcIds" value="' + subItemsArray[i] + '"/>').appendTo(form);
				$('<input type="hidden" name="funcSeqs" value="' + (i + 1) + '"/>').appendTo(form);
			}
// 			$('#funcId').val(sId);
			$("input[type='hidden'][id='funcId']").val(sId);
			$('#parentId').val(tId);
			form.submit();
// 			console.log('onDrop');			
		});
		tree.enableCheckBoxes(false);
// 		tree.enableThreeStateCheckboxes(true);
		tree.loadJSONObject(treeObj);
		//tree.openAllItems(0);
		//tree. setOnDblClickHandler("treeOnDblClick");
		tree.setOnClickHandler(menuclick);
		
		
// 		selectByOptionVal(selectObj,defaultVal);
	}
}

	

/*
 *菜单点击事件
 */	
function menuclick(id)
{
// 	alert(id);
	getMenuInfoAjax(id);
}

/*
 *菜单点击触发调用后台数据
 */	
function getMenuInfoAjax(id)
{
	var url = "function/getMenuInfoAjax.do?VISIT_FUNC_ID=01010106&menuId=" + id;
	App.ajaxSubmit(
			url, 
			{
				async:false
			}, 
			function(data)
			{
				
				var functionInfo = data.functionInfo;
				
				if(functionInfo=='ROOT'){
					//此循环切换td的隐藏与显示
					$("td.tdRight[id]").each(function(){$(this).attr("style","");$(this).siblings("td:odd").attr("style","display:none");});
					
					$("td.tdRight[id='fun']").html(functionInfo);
					$("td.tdRight[id='parent']").html("");
					$("td.tdRight[id='funcN']").html("");
					$("td.tdRight[id='ur']").html("");
					$("td.tdRight[id='mem']").html("");
					$("td.tdRight[id='isM']").html("");
					$("td.tdRight[id='needL']").html("");
					$("td.tdRight[id='dependP']").html("");
					$("td.tdRight[id='isDis']").html("");
					$("td.tdRight[id='isDef']").html("");
					$("td.tdRight[id='mem']").html("");
					
					$("#xz").attr("style","display:none;");
					$("#gx").attr("style","display:none;");
					return;
				}
				
				//此循环切换td的隐藏与显示
				$("td.tdRight[id]").each(function(){$(this).attr("style","");$(this).siblings("td:odd").attr("style","display:none");});
				
				
					$("#xz").attr("style","display:none;");
					$("#gx").attr("style","display:none;");
				
				$("td.tdRight[id='fun']").html(functionInfo.funcId);
				$("td.tdRight[id='parent']").html(functionInfo.parentFuncId);
				$("td.tdRight[id='funcN']").html(functionInfo.funcName);
				$("td.tdRight[id='ur']").html(functionInfo.url);
				$("td.tdRight[id='mem']").html(functionInfo.memo);
				function temp(tem){
					if(tem=='Y'){
						return '是';
					}else{
						return '否';
					}
				}
				$("td.tdRight[id='isM']").html(temp(functionInfo.isMenu));
				$("td.tdRight[id='needL']").html(temp(functionInfo.needLogin));
				$("td.tdRight[id='dependP']").html(temp(functionInfo.dependParent));
				$("td.tdRight[id='isDis']").html(temp(functionInfo.isDisplay));
				$("td.tdRight[id='isDef']").html(temp(functionInfo.isDefault));
				$("td.tdRight[id='mem']").html(functionInfo.memo);
				
				
				$("input[name='funcId']").val(functionInfo.funcId);//菜单ID
				$("input[name='parentFuncId']").val(functionInfo.parentFuncId);//菜单ID
				$("input[name='funcName']").val(functionInfo.funcName);//菜单ID
				$("input[name='url']").val(functionInfo.url);//菜单ID
				$("input[name='memo']").val(functionInfo.memo);//菜单ID
				$("#isMenu").val(functionInfo.isMenu);
				$("#needLogin").val(functionInfo.needLogin);
				$("#dependParent").val(functionInfo.dependParent);
				$("#isDisplay").val(functionInfo.isDisplay);
				$("#isDefault").val(functionInfo.isDefault);
				$("#memo").val(functionInfo.memo);
			}, 
			function(failureMsg,data)
			{
				alert(failureMsg);
			}
		);
}

//限制描述文字的长度
function getCharNumb(){
	var str=$("#memo").val();;
	var count=0;
	for(var i=0;i<str.length;i++){
		var c = str.charAt(i);
		if(/^[\u0000-\u00ff]$/.test(c)){
			count=count+1;
		}else{
			count +=2;
		}
	}
	if(count>1000){
		return false;
	}else{
		return true;
	}
}

//选中指定select对象，对应value的option
function  selectByOptionVal(selectObj,defaultVal)
{
	var loc_options=selectObj.find("option");
	if(loc_options==null||loc_options==undefine||loc_options.size==0)
	{
		alert("待选中的下拉对象缺失，或者没用可选的Option对象");
	}
	for(var i = 0;i<loc_options.size();i++)
	{
		if(loc_options[i]){
			alert("dfs");
		}
	}
}

//更新功能
function update(button, url) {
	var checkedList = tree.getAllChecked();
	var checkedList1=tree.getSelectedItemId();
	if (checkedList1=="ROOT") {
		$( "<div>不能修改菜单树根节点！</div>" ).dialog({
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				Ok: function() {
					$( this ).dialog( "close" );
				}
			}
		});
		return false;
	}
	if ('' == checkedList1 || checkedList1.split(',').length > 1) {
		var message = '';
		if ('' == checkedList1) {
			message = '请选择要更新的功能';
		} else {
			message = '只能选择一个功能进行更新';
		}
		$('<div>' + message + '</div>').dialog({
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				Ok: function() {
					$( this ).dialog('close');
				}
			}
		});
		return;
	}
	
	menuclick(checkedList1);
	
	$("td.tdRight[id]").each(function(){$(this).attr("style","display:none");$(this).siblings("td:odd").attr("style","");});
	
	$("#funcId").attr("readonly","readonly");
	$("#parentFuncId").attr("readonly","readonly");
	$("#gx").attr("style","");
	$("#xz").attr("style","display:none;");

}

function add(){
	$("#funcId").removeAttr("readonly");
	$("#parentFuncId").removeAttr("readonly");
	
	$("td.tdRight[id]").each(function(){$(this).attr("style","display:none");$(this).siblings("td:odd").attr("style","");})
	
	var checkedList = tree.getSelectedItemId();
	
	$("input[name='funcId']").val("");
	$("input[name='parentFuncId']").val(checkedList);//菜单ID
	$("input[name='funcName']").val("");//菜单ID
	$("input[name='url']").val("");//菜单ID
	$("#isMenu").val('Y');
	$("#needLogin").val('Y');
	$("#dependParent").val('Y');
	$("#isDisplay").val('Y');
	$("#isDefault").val('Y');
	$("#memo").val("");
	
	$("#xz").attr("style","");
	$("#gx").attr("style","display:none;");
	

	return;
}

//新增提交
function subAdd(button, url){
	var _$func=$("#funcId").val();
	var _$fname=$("#funcName").val();
	if(_$func==""||""==_$func){
		App.notyError("功能Id不能为空");
		return false;
	}
	if(_$fname==""||""==_$fname){
		App.notyError("功能名称不能为空");
		return false;
	}
	if(!getCharNumb()){
		App.notyError("功能功能描述过长");
		return false;
	}
	if ($("#parentFuncId").val()===_$func) {
		App.notyError("父功能ID和功能ID不能相同");
		return false;
	}
	if (_$func=="ROOT" ||_$func=="root") {
		App.notyError("功能ID不能为ROOT");
		return false;
	}
	if (!checkFuncId(_$func)) {
		App.notyError("功能ID已存在！");
		return false;
	}
	App.submitForm(button, url);
	return;
}
//更新提交
function upda(button, url){
	var _$fname=$("#funcName").val();
	if(_$fname==""||""==_$fname){
		App.notyError("功能名称不能为空");
		return false;
	}
	if(!getCharNumb()){
		App.notyError("功能功能描述过长");
		return false;
	}
	App.submitForm(button, url);
	return;
}
//删除功能
	function del(button, url) {
		var checkedList = tree.getSelectedItemId();
		var checkedName = tree.getItemText(checkedList);
		if(checkedName=='ROOT'||checkedName=='菜单树'){
			$( "<div>不能删除根节点</div>" ).dialog({
				modal: true,
				dialogClass: 'dClass',
				buttons: {
					"取消": function() {
						$( this ).dialog( "close" );
					}
				}
			});
			return;
		}
		if ('' == checkedList) {
			$( "<div>请选择要删除的功能</div>" ).dialog({
				modal: true,
				dialogClass: 'dClass',
				buttons: {
					Ok: function() {
						$( this ).dialog( "close" );
					}
				}
			});
			return;
		}
		
		$( "<div>确认要删除 "+checkedName+"功能及其子功能？</div>" ).dialog({
			resizable: false,
			height:160,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"删除": function() {
					var tmpList = checkedList.split(",");
					for (var i = 0; i < tmpList.length; i++) {
						tree.setSubChecked(tmpList[i], true);
					}
					checkedList = tree.getSelectedItemId();
					$('#delIds').val(checkedList);
					App.submitForm(button, url);
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		
	}

	function checkFuncId(funcId){
		
		var data={};
		data['funcId']=funcId;
		App.ajaxSubmit("function/check.do?VISIT_FUNC_ID=01010105",
				{data:data,async : false},
				function(data){
					flag = data.pass;
				});
		return flag;
	}
	
	
</script>
</head>
<body>
<p:authFunc funcArray="010101,01010102,01010103,01010101,0101010101,0101010201,01010104"/>
<form method="post" id="funcForm1"></form>
<form action="" method="post" id="funcForm">
	<p:token/>
	<input type="hidden" id="parentId" name="parentId"/>
<!-- 	<input type="hidden" id="funcId" name="funcId"/> -->
	<input type="hidden" id="delIds" name="delIds"/>
	<table>
		<tr>
			<td  width="35%" class="tdWhite">
			<div   style="width:100%;height:450px;overflow: scroll;">
				<div id="treepanel">
				
				</div>
			</div>
			</td>
			<td width="10%" valign="middle" class="tdWhite">
				<table  style="border: 0px;" >
				<tr class="tdWhite" id="ad">
					<td>
						<p:button funcId="01010101" value="新增" onclick="add"/>
					</td>
				</tr>
				
				<tr class="tdWhite" id="upda">
					<td>
						<p:button funcId="01010102" value="更新" onclick="update"/>
					</td>
				</tr>
				
				<tr class="tdWhite" id="dele">
					<td>
						<p:button funcId="01010103" value="删除" onclick="del"/>
					</td>
				</tr>
				
				</table>
			</td>
			<td width="55%"  valign="top" class="tdWhite">
			<table id="subtable">
			<thead>
			<tr>
				<th colspan="2" class="thTop">
					功能详细
				</th>
			</tr>
		</thead>
			<tr>
				<td class="tdLeft"><fmt:message key="label.parentId"/></td>
				<td class="tdRight" style="display: none">
					<input type="text" id="parentFuncId"  name="parentFuncId" maxlength="50" class="base-input-text" valid errorMsg="请输入父功能ID。"/>
				</td>
				<td class="tdRight" id="parent">

				</td>
				
			</tr>
			<tr>
				<td class="tdLeft"><span style="color:red;">*</span><fmt:message key="label.funcId"/></td>
				<td class="tdRight" style="display: none">
					<input type="text" id="funcId" name="funcId" maxlength="50" class="base-input-text" valid errorMsg="请输入功能ID。"/>
				</td>
				<td class="tdRight"  id="fun">
				</td>
			</tr>
			<tr>
				<td class="tdLeft"><span style="color:red;">*</span><fmt:message key="label.funcName"/></td>
				<td class="tdRight" style="display: none">
				<input type="text" id="funcName" name="funcName" maxlength="200" class="base-input-text" valid errorMsg="请输入功能名称。"/>
				</td>
				<td class="tdRight"  id="funcN">
				</td>
			</tr>
			<tr>
				<td class="tdLeft"><fmt:message key="label.iconPath"/></td>
				<td class="tdRight" style="display: none">
				<input type="text" id="iconPath" name="iconPath" maxlength="1000" class="base-input-text"/>
				</td>
				<td class="tdRight"  id="icon">
				</td>
			</tr>
			<tr>
				<td class="tdLeft"><fmt:message key="label.url"/></td>
				<td class="tdRight" style="display: none" >
				<input type="text" id="url" name="url" maxlength="1000" class="base-input-text" valid errorMsg="请输入URL。"/>
				</td>
				<td class="tdRight" id="ur">
				</td>
			</tr>	
			<tr>
				<td class="tdLeft"><fmt:message key="label.isMenu"/></td>
				<td class="tdRight"  style="display: none">
				 <select id="isMenu" name="isMenu">
									<option  value="">请选择</option>
									<option value="Y">是</option>
									<option value="N">否</option>
				</select>
				</td>
				<td class="tdRight" id="isM">
				</td>
			</tr>
			<tr>
				<td class="tdLeft"><fmt:message key="label.needLogin"/></td>
				<td class="tdRight" style="display: none">
					<select id="needLogin" name="needLogin">
									<option  value="">请选择</option>
									<option value="Y">是</option>
									<option value="N">否</option>
				</select>
				</td>
				<td class="tdRight"  id="needL">
					
				</td>
			</tr>	
			<tr>	
				<td class="tdLeft"><fmt:message key="label.dependParent"/></td>
				<td class="tdRight" style="display: none">
				<select id="dependParent" name="dependParent">
									<option  value="">请选择</option>
									<option value="Y">是</option>
									<option value="N">否</option>
				</select>
				</td>
				<td class="tdRight"  id="dependP">
				</td>
			</tr>
			<tr>
				<td class="tdLeft"><fmt:message key="label.isDisplay"/></td>
				<td class="tdRight"  style="display: none" >
				<select id="isDisplay" name="isDisplay">
									<option  value="">请选择</option>
									<option value="Y">是</option>
									<option value="N">否</option>
				</select>
				</td>
				<td class="tdRight"id="isDis">
				</td>
			</tr>
			<tr>	
				<td class="tdLeft"><fmt:message key="label.isDefault"/></td>
				<td class="tdRight" style="display: none">
					<select id="isDefault" name="isDefault">
									<option  value="">请选择</option>
									<option value="Y">是</option>
									<option value="N">否</option>
				</select>
				</td>
				<td class="tdRight"  id="isDef">
					
				</td>
			</tr>
			<tr>
				<td class="tdLeft"><fmt:message key="label.memo"/></td>
				<td class="tdRight" style="display: none">
				<textarea class="base-textArea" rows="5" cols="50" id="memo" name="memo" onkeyup="$_showWarnWhenOverLen1(this,4000,'memoSpan')"></textarea>
				</td>
				<td class="tdRight" id="mem">
				</td>
			</tr>
			<tr>
				<td id="xz" style="display:none;" colspan="2" width="100%" height="100%">
						<p:button funcId="0101010101" value="新增提交" onclick="subAdd"/>
						</td>
					<td id="gx" style="display:none;" colspan="2" width="100%" height="100%">
						<p:button funcId="0101010201" value="更新提交" onclick="upda"/>
					</td>
					</tr>
	</table>
			</td>
		</tr>
		
	</table>
</form>
</body>
</html>