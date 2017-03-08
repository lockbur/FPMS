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
<title>预算批复</title>
<script type="text/javascript">

//减法  
Number.prototype.sub = function (arg){  
  return this.add(-arg);  
} ;
//加法  
Number.prototype.add = function(arg){  
  var r1,r2,m;  
  try{r1=this.toString().split(".")[1].length;}catch(e){r1=0;}  
  try{r2=arg.toString().split(".")[1].length;}catch(e){r2=0;}  
  m=Math.pow(10,Math.max(r1,r2))  ;
  return (this*m+arg*m)/m; 
} ;


function pageInit(){
	App.jqueryAutocomplete();
}
//输入数字控制
function onlyNumber(obj){
	var id = $(obj).attr("id");
	var value = $("#"+id).val();
	var tdId = id + "Td";
	if(value == '')
		return false;
	var reg = /^(?:([1-9]\d*|0)((\.\d{1,2})?))$/;
	var flag = !reg.test(value) || 0 > value;
	App.toShowCheckIdIsExist("budgetReplyForm",id,tdId,"请输入不小于0且至多两位小数的有效值",flag);
	if(flag)
		return false;
	return true;
}
//批复明细修改
function detailUpdate(obj){
	onlyNumber(obj);
	//检查大小是否正确
	var objs = $("#replyDetail input[name='replyFees']");
	var allotedAmt = 0;
	$.each(objs, function(i,n) {
		if($(n).val() != '') {
			allotedAmt = eval(allotedAmt) + eval($(n).val());
		}
	});
	if(parseFloat($("#replyAmt").val())< parseFloat(allotedAmt.toFixed(2))){
		App.notyError("明细金额之和不能大于批复总金额!");
		return false;
	}
	$("#allotedAmt").text(allotedAmt.toFixed(2));
	getAllotedAmt();
}
//计算已批复金额
function getAllotedAmt() {
	var objs = $("#replyDetail input[name='replyFees']");
	var allotedAmt = 0;
	$.each(objs, function(i,n) {
		if($(n).val() != '') {
			allotedAmt = eval(allotedAmt) + eval($(n).val());
		}
	});
	$("#allotedAmt").text(allotedAmt.toFixed(2));
}

function doValidate() {
	alert("dfdf")
	//提交前调用
	if(!App.valid("#budgetReplyForm")){return;}
	var replyAmt = $("#replyAmt").val();
	var allotedAmt = $("#allotedAmt").text();
	if(eval(replyAmt) < eval(allotedAmt)){return;}
	return true;
}

function saveResult() {
	var nullCheck = true;
	if($.trim($("#replyAmt").val())==""){
		$("#replyAmt_warnImg").attr("title","批复金额不能为空");
		$("#replyAmt").css("border","1px solid red");
		$("#replyAmt_warnImg").show();
		nullCheck = false;
	}
	
	$(".checkReply").each(function(){
		
		var value = $.trim($(this).val());
		var id = $(this).attr("id");
		if(value == ""){
			nullCheck = false;
			$("#"+id+"_montWarn").attr("title","不能为空");
			$(this).css("border","1px solid red");
			$("#"+id+"_montWarn").show();
		}
	});
	if(parseFloat($("#replyAmt").val())< parseFloat($("#allotedAmt").html())){
		App.notyError("明细金额之和不能大于批复总金额!");
		nullCheck = false;
	}
	//校验模板预算修改合法性
	 var flag = upTmpltReply();
	//校验指标修改合法性
	var flag2 =true;
	var objs = $("#replyDetail input[name='replyFees']");
	$.each(objs, function(i,n) {
		var flag3 =  upMontReply(n);
		if(flag3 == false){
			flag2 = false;
		}
	});
	if(flag ==  false || flag2 == false || nullCheck == false ){
		return false;
	}
	App.ajaxSubmit("budget/budgetReply/reply.do?<%=WebConsts.FUNC_ID_KEY%>=02050102",
	{data : $('#budgetReplyForm').serialize(), async : false}, 
	function(data){
		ret = data.result;
		if(ret = true) {
			App.notySuccess("保存成功！");
			$("#replyAmtHide").val($("#replyAmt").val());
		}else {
			App.notyError(ret);
		}
	});
}
//检查修改预算时 不能小于已经分解的金额
function upTmpltReply(){
	var flag = true;
	$("#replyAmt_warnImg").hide();
	$("#replyAmt").css("boder","1px solid rgb(216, 212, 196)");
	var replyAmt =$("#replyAmt").val();
	var tmpltId =$("#tmpltId").val();
	var data = {};
	data['tmpltId'] =  tmpltId;
	App.ajaxSubmit("budget/budgetReply/upTmpltCheck.do?<%=WebConsts.FUNC_ID_KEY%>=02050103",{data : data,async:false}, function(data) {
		var value=data.result;
		if(parseFloat(replyAmt) < parseFloat(value)){
			$("#replyAmt").css("border","1px solid red");
			$("#replyAmt_warnImg").show();
			flag = false;
		}
	});
	return flag;
}
//检查修改预算时 不能小于已经分解的金额
function upMontReply(obj){
	var flag = true;
	var id = $(obj).attr("id");
	$("#"+id+"_montWarn").hide();
	$(obj).css("boder","1px solid rgb(216, 212, 196)");
	var tmpltId = $("#tmpltId").val();
	var codeId = $(obj).attr("id")+"_code";
	var montCode = $("#"+codeId).val();
	var replyAmt =$(obj).val();
	var data = {};
	data['tmpltId'] =  tmpltId;
	data['montCode'] =  montCode;
	App.ajaxSubmit("budget/budgetReply/upMontCheck.do?<%=WebConsts.FUNC_ID_KEY%>=02050104",{data : data,async:false}, function(data) {
		var value=data.result;
		if(replyAmt < parseFloat(value)){
			$(obj).css("border","1px solid red");
			$("#"+id+"_montWarn").show();
			flag = false;
		}
	});
	return flag ;
}
</script>
</head>
<body>
<p:authFunc funcArray="02050102"/>
<form action="" method="post" id="budgetReplyForm" >
<p:token/>
	<table id="replyTable">
		<tr>
			<th colspan="4" style="text-align: center;">批复</th>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">有效年份</td>
			<td class="tdRight" width="30%">
				${reply.dataYear}
				<input type="hidden" name="tmpltId" id="tmpltId" value="${reply.tmpltId}"/>
			</td>
			<td class="tdLeft" width="20%">预算监控指标</td>
			<td class="tdRight" width="30%">
				${reply.dataAttr}
			</td>
		</tr>
         <tr>
			<td class="tdLeft"> 预算类型</td>
			<td class="tdRight">
				${reply.dataType}
			</td>
			<td class="tdLeft"> 创建部门</td>
			<td class="tdRight">
				${reply.org2Name}
			</td>
		<tr>
	</table>
	<br/>
	<table>
		<tr>
			<th colspan="4">预算指标分配</th>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">批复金额(元)</td>
			<td class="tdRight" width="30%" id="replyAmtTd">
				<input type="text" class="base-input-text" id="replyAmt" name="replyAmt" value="${reply.replyAmt }" 
						valid errorMsg="不能为空" onblur="onlyNumber(this);" maxlength="18"/>
				<img style="display: none" id="replyAmt_warnImg" title="修改金额不能小于该模板已经分解的金额" src="<%=request.getContextPath()%>/common/images/warning_s.gif">
			</td>
			<td class="tdLeft" width="20%">已批复金额(元)</td>
			<td class="tdRight" width="30%">
				<div style="float: left;line-height: 34px;" id="allotedAmt">${reply.allotedAmt }</div>
				<div style="float: right;"><input type="button" value="保存" onclick="saveResult()"/></div>
			</td>
		</tr>
	</table>
	<br/>
	<table id="replyDetail" class="tableList">
		<tr>
			<th colspan="3">批复明细</th>
		</tr>
		<tr>
			<!-- <td>责任中心</td> -->
			<td class="tdLeft" style="text-align: center;" width="20%">监控指标代码</td>
			<td class="tdLeft" style="text-align: center;" width="30%">监控指标名称</td>
			<td class="tdLeft" style="text-align: center;" width="50%">分配金额(元)</td>
		</tr>
		<c:forEach items="${montList }" var="mont" varStatus="montIndex">
			<tr>
				<td>
					${mont.montCode }
					<input type="hidden" name="montCodes" id="replyFees${montIndex.index }_code" value="${mont.montCode }"/>
				</td>
				<td>
					${mont.montName }
				</td>
				<td class="tdRight"  id="replyFees${montIndex.index }Td">
					<input id="replyFees${montIndex.index }" name="replyFees" onblur="detailUpdate(this);" maxlength="11" 
							class="base-input-text checkReply" valid errorMsg="不能为空" value="${mont.replyAmt }"/>
					<img style="display: none"  id="replyFees${montIndex.index }_montWarn" title="修改金额不能大于该指标已经分解的金额" src="<%=request.getContextPath()%>/common/images/warning_s.gif">
				</td>
			</tr>
		</c:forEach>
		<c:if test="${empty montList}">
			<tr><td colspan="3">没有记录</td></tr>
		</c:if>
	</table>
</form>

</body>
</html>