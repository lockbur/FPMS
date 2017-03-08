<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>占用预算调整</title>
<script type="text/javascript">
//页面初始化加载
function pageInit(){
	//listTabFirstTr
	App.jqueryAutocomplete();
}

function doValidate() {
	//提交前调用
	if(!App.valid("#bgtSumTotalInfo")){
		return;
	}
	
	var tzjy=$("#tzjy").val();
	if(tzjy==0){
		App.notyError("调整金额不能为0！");
		return false;
	}
	if(tzjy.substr(0,1)!='-'){
		if(!$.checkMoney(tzjy)){
			App.notyError("调整金额输入有误，请输入最多含两位小数的18位浮点数！");
			return false;
		}
	}
	else{
		tzjy=tzjy.substr(1);
		if(!$.checkMoney(tzjy)){
			App.notyError("调整金额输入有误，请输入最多含两位小数的18位浮点数！");
			return false;
		}	
	}
	var type = $("input[name='type']:checked").val();
	
	if(""==type || undefined == type || "undefined" == type){
		App.notyError("请选择调整类型");
		return false;
	}
	var passCheck="";
	if("1" == type ){
		//可用调整
		var data = {};
		data['tzjy'] =  $("#tzjy").val();
		data['bgtId']=$("#bgtId").val();
		var url = "budget/budgetInspect/checkValidBgt.do?VISIT_FUNC_ID=02090305";
		App.ajaxSubmit(url,{data : data,async:false}, function(data) {
				var flag = data.flag;
				if(flag =="N"){
					App.notyError(data.msg);
					return false;
				}else{
					passCheck = "true";
				}
		});
	}
	if("2" == type){
		//校验调整的金额是否合理
		var data = {};
		data['tzjy'] =  $("#tzjy").val();
		data['bgtId']=$("#bgtId").val();
		var url = "budget/budgetInspect/checkAdjust.do?VISIT_FUNC_ID=02090302";
		App.ajaxSubmit(url,{data : data,async:false}, function(data) {
			var flag = data.flag;
			if(flag =="N"){
				App.notyError(data.msg);
				return false;
			}else{
				passCheck = "true";
			}
		});
	}
	if(passCheck == "true")
		{
		return  true;
		}
	
}

function checkNoPass(){
	//校验调整的金额是否合理
	var data = {};
	var msg="";
	data['tzjy'] =  $("#tzjy").val();
	data['bgtId']=$("#bgtId").val();
	var url = "budget/bgtImport/checkAdjust.do?VISIT_FUNC_ID=02090302";
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
			msg = data.checkMsg;
	});
	return msg;
}
function checkValidBgt(){
	var data = {};
	data['tzjy'] =  $("#tzjy").val();
	data['bgtId']=$("#bgtId").val();
	var url = "budget/bgtImport/checkAdjust.do?VISIT_FUNC_ID=02090302";
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
			var flag = data.flag;
			if(flag =="false"){
				return data.msg;
			}else{
				return "";
			}
	});
}
</script>
</head>
<body>
<p:authFunc funcArray="02090303"/>
<form action="" method="post" id="bgtSumTotalInfo" >
<input type="hidden" name="orgType" value="${orgType }"/>
<input type="hidden" id="bgtId" value="${bmBean.bgtId}" name="bgtId"/>
<input type="hidden" id="bgtUsed" value="${bmBean.bgtUsed}" name="bgtUsed"/>
	<table id="approveChainTable">
		<tr class="collspan-control">
			<th colspan="4">预算调整</th>
		</tr>
		<tr>
			<td class="tdLeft">预算ID</td>	
			<td class="tdRight">
				${bmBean.bgtId}
			</td>
			<td class="tdLeft">预算年份</td>	
			<td class="tdRight">
				${bmBean.bgtYear}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">预算监控指标</td>
			<td class="tdRight" width="30%">
			${bmBean.montName}
			</td>
			<td class="tdLeft">预算物料名称</td>	
			<td class="tdRight">
			${bmBean.matrName}
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">总预算</td>
			<td class="tdRight" width="30%">
				<fmt:formatNumber type='number' value="${bmBean.bgtSum}" minFractionDigits="2"/>
			</td>
			<td class="tdLeft">总可用预算</td>	
			<td class="tdRight">
				<fmt:formatNumber type='number' value="${bmBean.bgtSumValid}" minFractionDigits="2"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">总透支预算</td>
			<td class="tdRight" width="30%">
				<fmt:formatNumber type='number' value="${bmBean.bgtOverdraw}" minFractionDigits="2"/>
			</td>
			<td class="tdLeft">总冻结预算</td>	
			<td class="tdRight">
				<fmt:formatNumber type='number' value="${bmBean.bgtFrozen}" minFractionDigits="2"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">总占用预算</td>
			<td class="tdRight" width="30%">
				<fmt:formatNumber type='number' value="${bmBean.bgtUsed}" minFractionDigits="2"/>
			</td>
			<td class="tdLeft" width="20%"></td>
			<td class="tdRight" width="30%">
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">调整类型<span style="color: #A41E1E">*</span></td>
			<td class="tdRight" width="30%">
				<div class="base-input-radio" id="typeDiv">
					<label for="type1" onclick="App.radioCheck(this,'typeDiv')" >可用调整</label><input type="radio" id="type1" name="type" value="1" />
					<label for="type2" onclick="App.radioCheck(this,'typeDiv')" >占用调整</label><input type="radio" id="type2" name="type" value="2"/>
				</div>
			</td>
			<td class="tdLeft" width="20%">预算调整值<span style="color: #A41E1E">*</span></td>
			<td class="tdRight" width="30%">
				<input type="text" id="tzjy" name="tzjy"  class="base-input-text" maxlength="18" valid style="width:130px"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">调整原因<br>(<span id="deptDescSpan">0/200</span>)</td>	
			<td class="tdRight" colspan="3">
				<textarea  onkeyup="$_showWarnWhenOverLen1(this,200,'deptDescSpan')" class="base-textArea" id="operReson" name="operReson" rows='5'></textarea>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="02090303" value="提交" />
				<input type="button" value="返回" onclick="backToLastPage('${uri}')">
			</td>
		</tr>
	</table>
</form>
</body>
</html>