<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/infoHide.js"></script>
<title>提示信息列表</title>
<script type="text/javascript">
function pageInit() {
	App.jqueryAutocomplete();
	infoHide(45);
	$( "#editpanel" ).dialog({
		resizable: false,
		modal: true,
		autoOpen: false,
		width: 600,
		dialogClass: 'dClass',
		buttons: [
			{
				text: "提交",
				click: function() {
					if($("#infoName").val() != '') {
						App.ajaxSubmit("sysmanagement/promptinfo/ajaxEdit.do?<%=WebConsts.FUNC_ID_KEY%>=011401",
							{data : {'dispName':$("#showName").text(),'promptInfo':$("#infoName").val()},async : false}, 
							function(data){
								if(data.isDone) {
									App.notySuccess("修改成功！");
									var indexId = $("#hiddenIndex").val();
									$("#"+indexId).text($("#infoName").val());
									$("#"+indexId+"_hi").val($("#infoName").val());
								} else {
									App.notyError("修改失败！");
								}
							});
						infoHide(45);
						$( this ).dialog( "close" );
					}else {
						App.notyError("提示信息不能为空值！");
					}
				}
			},
			{
				text: "取消",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});
	$( "#addpanel" ).dialog({
		resizable: false,
		modal: true,
		autoOpen: false,
		width: 600,
		dialogClass: 'dClass',
		buttons: [
			{
				text: "提交",
				click: function() {
					//alert($("#infoNameAdd").val().length);
					if($("#showNameAdd").val() != '' &&
							$("#infoNameAdd").val() != '' &&
							checkPrimaryKey($("#showNameAdd").val())) {
						var form = $("#addForm");
						form.attr("action","<%=request.getContextPath()%>/sysmanagement/promptinfo/add.do?<%=WebConsts.FUNC_ID_KEY%>=011403");
						App.submit(form);
						$( this ).dialog( "close" );
					}else if($("#showNameAdd").val() == '' || $("#infoNameAdd").val() == '') {
						App.notyError("提示信息不能为空值！");
					}
				}
			},
			{
				text: "取消",
				click: function() {
					$( this ).dialog( "close" );
				}
			}
		]
	});
	//初始化调用textarea统计字数
	//$_showWarnWhenOverLen1($("#editpanel .base-textArea"),100,'authdealCommentSpan2');
	
}
/* $(document).ready(function(){
	$_showWarnWhenOverLen1($("#editpanel .base-textArea"),100,'authdealCommentSpan2');
}); */

function editPromptInfo(index,info) {
	$("#showName").text($("#dispId"+index).text());
	$("#infoName").val($.trim(info));
	$("#hiddenIndex").val("infoId" + index);
	$("#editpanel").dialog( "open" );
	//修改：提交后再次打开修改对话框信息显示错误的问题
	$("#infoName").val($("#infoId"+index+"_hi").val());
	//$_showWarnWhenOverLen1($("#infoName"),100,'authdealCommentSpan2');
	//$_showWarnWhenOverLen1($("#editpanel .base-textArea"),100,'authdealCommentSpan2');
	$_showWarnWhenOverLen1(document.getElementById("infoName"),200,'authdealCommentSpan2');
}
function addPromptInfo() {
	$("#showNameAdd").val("");
	$("#infoNameAdd").val("");
	$("#addpanel").dialog( "open" );
	$_showWarnWhenOverLen1(document.getElementById("infoNameAdd"),200,'authdealCommentSpan1');
}
function checkPrimaryKey(value) {
	var checkExist = false;
	App.ajaxSubmit("sysmanagement/promptinfo/ajaxCheck.do?<%=WebConsts.FUNC_ID_KEY%>=011404",
		{data : {'dispName' : value} , async : false}, 
		function(data){
			checkExist = !data.isExist;
			App.toShowCheckIdIsExist("addForm","showNameAdd","showNameAddTd","该字段名称已经存在！",data.isExist);
		});
	return checkExist;
}
function delPromptInfo(dispName) {
	var form=document.forms[0];
	$( "<div>确认要删除该提示信息?</div>" ).dialog({
		resizable: false,
		height:150,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				form.dispName.value=dispName;
				form.action="<%=request.getContextPath()%>/sysmanagement/promptinfo/delete.do?<%=WebConsts.FUNC_ID_KEY%>=011402";
				App.submit(form);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});

}

//重置查询条件
function resetAll(){
	$(":text").val("");
	$("select").val("");
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}

	

</script>
</head>

<body>
<p:authFunc funcArray="0114"/>
<form method="post" id="promptForm">
	<table>
		<tr class="collspan-control">
			<th colspan="4">提示信息查询</th>
		</tr>	
		<tr>
			<td class="tdLeft">字段名称</td>
			<td class="tdRight" colspan="3">
			<input type="text" id="dispName" name="dispName" class="base-input-text" value="${selectBean.dispName}">
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<input type="hidden" id="hiddenIndex"/>
				<p:button funcId="0114" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
				<!-- <input type="button" value="添加" onclick="addPromptInfo();"> -->
			</td>
		</tr>
	</table>
	<br/>
	<table class="tableList">
		<tr>
			<th width="20%">字段名称</th>
			<th width="60%">对应提示信息</th>
			<th width="20%">操作</th>
		</tr>
		<c:forEach items="${list}" var="prompt" varStatus="promptIndex">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" >
				<td id="dispId${promptIndex.index }">${prompt.dispName }</td>
				<td id="infoId${promptIndex.index }" class="tdInfo1" title="">
					
							${prompt.promptInfo}
				</td>
				<td style="text-align: center;">
					<div class="update" style="padding-left:50px;padding-right: 10px;">
						<input type="hidden" value="${prompt.promptInfo}" id="infoId${promptIndex.index}_hi"/>
						<a href="javascript:void(0)" onclick="editPromptInfo('${promptIndex.index}','${prompt.promptInfo}');" 
								title="修改"></a>
					</div>
					    <a onclick="delPromptInfo('${prompt.dispName }');" 
					    		title="<%=WebUtils.getMessage("button.delete")%>">
					   	<img class="delete imageBtn" border="0" alt="删除" width="25px" height="25px" src="<%=request.getContextPath()%>/common/images/delete1.gif"/></a>
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty list}">
	    <tr>
		<td colspan="3" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
		</tr>
	   </c:if>	
	</table>
</form>
<p:page/>
<div id="editpanel" title="提示信息修改">
	<table class="tableList">
		<tr><td class="tdLeft">字段名称</td>
			<td class="tdRight" id="showName"></td></tr>
		<tr>
			<td >
				提示信息<br>(<span id="authdealCommentSpan2">0/200</span>)：
			</td>
			<td>
				<textarea  class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,200,'authdealCommentSpan2')" id="infoName"></textarea>
			</td>
		</tr>	
	</table>
</div>
<div id="addpanel" title="提示信息添加">
	<form id="addForm" method="post">
	<table class="tableList">
		<tr><td class="tdLeft">字段名称</td>
			<td class="tdRight" id="showNameAddTd">
				<input type="text" id="showNameAdd" class="base-input-text" maxlength="25"
						onblur="checkPrimaryKey(this.value)" name="dispName"/>
			</td>
		</tr>
		<tr>
			<td>
				提示信息<br>(<span id="authdealCommentSpan1">0/200</span>)：
			</td>
			<td>
				<textarea class="base-textArea" onkeyup="$_showWarnWhenOverLen1(this,200,'authdealCommentSpan1')" id="infoNameAdd" name="promptInfo"></textarea>
			</td>
		</tr>
	</table>
	</form>
</div>
</body>
</html>