<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms"%>
<%@ taglib uri="/platform-tags" prefix="p"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>核算码修改</title>
<script type="text/javascript">
var flag = true;


function pageInit(){
	/* var flag = ${typeBean.useFlag};
	if(flag=='0'){
		$("#useFlagLabelY").removeClass("check-label"); 
		$("#useFlagY").removeAttr("checked");
	}else{
		$("#useFlagLabelN").removeClass("check-label"); 
		$("#useFlagN").removeAttr("checked");
	} */
	if($.trim($('#longPrepaidCode').val())==''){
		$("#fc_select").find("td:gt(0)").remove();
		$("#fc_select").find("td:eq(0)").remove();
		flag = false;
	}
}


function updateSubmit(){
	if(check()){
		if(confirm('确认修改？')){
			var form = $('#cglCodeAddForm')[0];
			form.action = "<%=request.getContextPath()%>/sysmanagement/matrtype/updatePrepaidCode.do?<%=WebConsts.FUNC_ID_KEY%>=01080704";
			App.submit(form);
		}
	}
	
}

function check(){
	if($.trim($('#longPrepaidCode').val())!='')
	{
		if($.trim($('#longPrepaidCode').val()).length!=4){
			App.notyError("长期待摊核算码长度不为4，请检查！");
			return false;
		}
		if($.trim($('#shortPrepaidCode').val())==''||$.trim($('#shortPrepaidCode').val())==null)
		{
			
			App.notyError("若长期待摊核算码不为空，请输入短期待摊核算码");
			return false;
		}
	}
	if($.trim($('#shortPrepaidCode').val())!='')
	{
		if($.trim($('#shortPrepaidCode').val()).length!=4){
			App.notyError("短期待摊核算码长度不为4，请检查！");
			return false;
		}
	}
	if($.trim($('#longPrepaidCodeFee').val())!='')
	{
		if($.trim($('#longPrepaidCodeFee').val()).length!=4){
			App.notyError("长期待摊固定对应的费用核算码长度不为4，请检查！");
			return false;
		}
	}
	
	if($.trim($('#longPrepaidCode').val())==''&&flag){
			
		if(checkCglCode()){
			App.notyError("该费用核算码已被待摊预提类物料使用，不可删除长短期待摊核算码，请检查！");
			return false;
		}
	}
	return true;
} 

function checkCglCode(){
	var list = false;
	var data = {};
	data['cglCode'] =  $("#cglCode").val();
	var url = "sysmanagement/matrtype/checkIsUseCglCode.do?<%=WebConsts.FUNC_ID_KEY%>=01080706";
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
			list = data.isExist;
	});
	return list;
}

function showLongPrepaidCodeFee(){
	var longPrepaidCode = $("#longPrepaidCode").val();
	if ($.trim(longPrepaidCode) != '' ) {
		if($("#fc_select").find("td:eq(1)").attr("colspan")!='1' ){
			
			$("#fc_select").append("<td class='tdLeft' width='25%'>短期待摊核算码</td><td class='tdRight' width='25%' colspan='1'><input type='text' onkeyup='onlyNum(this);' name='shortPrepaidCode' id='shortPrepaidCode' maxlength='4' value='${mt.shortPrepaidCode}' class='base-input-text'></td>");
			$("#fc_select").append("<td class='tdLeft' width='25%'>长期待摊固定对应的费用核算码</td><td class='tdRight' width='25%'><input type='text' onkeyup='onlyNum(this);' name='longPrepaidCodeFee' id='longPrepaidCodeFee' maxlength='4' value='${mt.longPrepaidCodeFee}' class='base-input-text'></td>");
		}	
	} else if($.trim(longPrepaidCode) == '' || $.trim(longPrepaidCode) == null){
		$("#fc_select").find("td:gt(0)").remove();
		$("#fc_select").find("td:eq(0)").remove();
	}
}
function onlyNum(obj){
	obj.value = obj.value.replace(/[^\d]/g,""); // 清除"数字"以外的字符

}
</script>
</head>
<body>
	<form method="post" id="cglCodeAddForm">
	<p:authFunc funcArray="01080704" />
		<table>
			<tr>
				<th colspan="4">核算码更新</th>
			</tr>
			<tr>
				<td class="tdLeft" width="25%"><span class="red">*</span>费用核算码</td>
				<td class="tdRight" width="25%" >
					<span>${mt.cglCode}</span>
					<input type="hidden" id="cglCode" name="cglCode" class="base-input-text" maxlength="4" value="${mt.cglCode}"/>
				</td>
				<td class="tdLeft" width="25%">长期待摊核算码</td>
				<td class="tdRight" width="25%" >
					<input type="text" id="longPrepaidCode" name="longPrepaidCode" onkeyup="onlyNum(this);" class="base-input-text" maxlength="4" value="${mt.longPrepaidCode}" onblur="showLongPrepaidCodeFee();"/>
				</td>
			</tr>
			<tr id="fc_select">
				<td class="tdLeft" width="25%">短期待摊核算码</td>
				<td class="tdRight" width="25%" colspan='1'>
					<input type="text" id="shortPrepaidCode" name="shortPrepaidCode" onkeyup="onlyNum(this);" class="base-input-text" maxlength="4" value="${mt.shortPrepaidCode}"/>
				</td>
				<td class="tdLeft" width="25%">长期待摊固定对应的费用核算码</td>
				<td class="tdRight" width="25%" >
					<input type="text" id="longPrepaidCodeFee" name="longPrepaidCodeFee" onkeyup="onlyNum(this);" class="base-input-text" maxlength="4" value="${mt.longPrepaidCodeFee}"/>
				</td>
			</tr>
			<tr>
				<td colspan="4" class="tdWhite">
					<input type="button" value="提交" onclick="updateSubmit()">
					<input type="button" value="返回" onclick="backToLastPage('${url}');">
				</td>
			</tr>
		</table>
	</form>
	<p:page/>
</body>
</html>