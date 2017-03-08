<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预算分解明细</title>
<style type="text/css">
	.base-input-text-other{
		width: 200px;
		height: 20px;
		background-color: white;
 		font-family: Arial;
/* 		font-family:msyh; */
		font-size: 12px;
		vertical-align: middle;
		border: #D8D4C4 solid 1px;
		padding-bottom: 0px;
		padding-top: 0px;
		padding-left: 3px;
}
</style>

<script type="text/javascript">
/**
 * 平台页面初始化执行
 */
function pageInit()
{
	App.jqueryAutocomplete();
	//[注释]创建表格合并对象，调用rowCombine方法进行单元格合并
// 	var tc = new TableCombineObj("resolveDetail", 1, null, 0, 2, null, null, null, null);
// 	tc.rowCombine();
}

/**
 *	【暂时单选，未处理多选，待需求确定】 
 *	获取选中物料信息
 *		描述：弹窗选择分解物料信息,并将returnValue信息的值填充到指定input框
 */
function toSelectMatr(montCode){
	App.submitShowProgress();
	var url="<%=request.getContextPath()%>/budget/budgetResolve/resolve/selectMatr.do?<%=WebConsts.FUNC_ID_KEY %>=02060102&montCode="+montCode;
	if($("#selectedMatrCode").val()){
		url+="&selectedMatrCodes="+$("#selectedMatrCode").val();
	}
	
	//发送请求，获取returnValue
	var returnValue = window.showModalDialog(url, null, "dialogHeight=400px;dialogWidth=700px;center=yes;status=no;help:no;scroll:yes;resizable:no");
	if(returnValue == null)
	{
		App.submitFinish();
		return null;
	}
	else
	{
		App.submitFinish();
		//将返回值returnValue写到input框，用于Controller取值接收
		$("#selectedMatrName").val(returnValue["matrName"]);
		$("#selectedMatrCode").val(returnValue["matrCode"]);
// 		console.info('测试returnValue:'+returnValue["matrName"]+"---"+returnValue["matrCode"]);
	}
}

/**
 *	新增预算分解操作
 * 		描述：	1.检查必填项; 2. 校验分解金额的格式以及校验剩余分解额度是否足够分解操作需要; 
 *				3.获取分解信息,进行Ajax提交 ; 4.表格新增分解详情; 5.更新分解数据
 */
function ajaxAddResolve(){
	//1.检查分解操作表单必填项
	if(!App.valid("#resolveForm")){
		return ;
	}
	//2.校验分解金额，校验通过时才执行后续操作
	if(validateSplitAmt($("#sumAmt").val())){		
		//3.获取数据,Ajax提交
		var dutyName = $("#dutyName").attr("title");
		var dutyCode = $("#dutyCode").val();
		var matrName = $("#selectedMatrName").val();
		var matrCode = $("#selectedMatrCode").val();
		var sumAmt = $("#sumAmt").val();	//当次分解金额
		var data = {};
		data["tmpltId"] = $("input[name='tmpltId']").val();
		data["montCode"] = $("input[name='montCode']").val();
		data["dutyCode"] = dutyCode;
		data["matrCode"] = matrCode;
		data["sumAmt"] = sumAmt;
		//Ajax新增预算分解请求
		App.ajaxSubmit("budget/budgetResolve/resolve/addBudgetSplit.do?<%=WebConsts.FUNC_ID_KEY %>=02060103",
			{data:data,async:false}, 
			function(data) {
				var insert = data.insertResult;
// 				console.info('插入预算分解结果：'+insert);
				if(insert){
					//4.jQuery分解详情表格插入新增分解
					var trRow = "<tr>"+
								"<td title='"+dutyCode+"'>"+dutyName+"<input type='hidden' name='dutyCode' value='"+dutyCode+"'></td> "+
								"<td title='"+matrCode+"'>"+matrName+"<input type='hidden' name='matrCode' value='"+matrCode+"'></td> "+
								"<td>"+sumAmt+"<input type='hidden' name='sumAmt' value="+sumAmt+"></td> "+
								"<td><div><a href='#' onclick='removeResolveTbRow(this )' title='删除'><img src='/ERP/common/images/del.gif'></a></div></td>"+
								"</tr>";
					$("#resolveDetail").append(trRow);
				}else{
					App.notyError("新增预算分解操作发生异常");
				}
			}
		);
		//5.更新当前JSP页面中对应的[已分解金额]
		updateSplitedAmt(sumAmt , "+");
	}
}

/*
 * 删除预算分解操作
 *		描述：删除预算分解明细信息(页面表格删除行+调用.do删除split表中相应数据)
 */
function removeResolveTbRow( _obj ){
	//1.获取当前删除分解的金额(将对Reply表中SPLITED_AMT字段产生变化)
	var changeAmt = $(_obj).parent().parent().siblings().find("input[name='sumAmt']").val();
	//2.弹窗提示删除操作确认
	$("<div>确认删除该预算分解？</div>").dialog({
		resizable : false,
		height : 150,
		modal : true,
		dialogClass : 'dClass',
		buttons:{
			"确定" :function(){
				//3.Ajax提交删除分解操作(后台删除SPLIT表中对应记录，更新REPLY表中已分解金额字段)
				var data = {};
				data["tmpltId"]  = $("input[name='tmpltId']").val();
				data["montCode"] = $("input[name='montCode']").val();
				data["dutyCode"] = $(_obj).parent().parent().siblings().find("input[name='dutyCode']").val();
				data["matrCode"] = $(_obj).parent().parent().siblings().find("input[name='matrCode']").val();
				data["sumAmt"]   = changeAmt;
// 				console.info(data["tmpltId"]+":"+data["montCode"]+":"+data["dutyCode"]+":"+data["matrCode"]);
				
				App.ajaxSubmit("budget/budgetResolve/resolve/delBudgetResolve.do?<%=WebConsts.FUNC_ID_KEY %>=02060104",
					{data:data,async:false}, 
					function(data) {
						var delResult = data.delResult;
// 						console.info('删除预算分解操作结果：'+delResult);
						if( delResult ){
							//4.更新该模板当前JSP对应的[已分解金额]信息,jQuery删除分解详情表格中对应的记录
							updateSplitedAmt(changeAmt , "-");
							$(_obj).parent().parent().parent().remove();
						}else{
							App.notyError("删除预算分解操作发生异常！");
						}
					}
				);
				$(this).dialog("close");
			},
			"取消" :function(){
				$(this).dialog("close");
			}
		}
	});
}

/**
 * 预算分解金额校验(分解时校验所分解金额是否足够该次分解操作)
 *	校验通过时，返回true，不通过返回false，并提示警告信息 		
 */
function validateSplitAmt( _splitAmt ){
	var reg = /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;
	//验证输入字符是否正整数
	if( !reg.test(_splitAmt) ){
		App.notyError("请检查：分解金额值不符合格式！");
		$("#sumAmt").focus();
		return false;
	}else{
		//上述验证通过后，再验证金额是否足够分解
		var replyAmt = parseInt($("#replyAmt").val());	//总分配金额
		var splitAmt = parseInt(_splitAmt);				//当次分解金额
		var tbSumUsedAmt = 0;							//该参数用于保存表格中已占用(分解)金额的总数
		var usedAmtInTb = $("#resolveDetail tr").find("input[name='sumAmt']").each(
			function (){
				tbSumUsedAmt += parseInt($(this).val());
			}
		);
		if((replyAmt - tbSumUsedAmt - splitAmt)>=0){	//判断该次分解金额是否足够
			return true;
		}else{
			App.notyError("请检查：分解金额已超出可使用的剩余金额，可分解金额为："+(replyAmt - tbSumUsedAmt));
			$("#sumAmt").focus();
			return false;
		}
	}
}

/**
 * 新增预算分解和删除预算分解时，更新该监控指标对应的已分解金额(新增时加上，删除时减去)
 *		参数：_amt(变化的金额) 、"sign"(+-操作符号，分别代表新增分解和删除分解)
 */
function updateSplitedAmt( _amt , sign ){
	var newSplitedAmt ;
	if("-"==sign){
		newSplitedAmt = parseDouble($("#splitedAmtTd").html()) - parseDouble( _amt );
	}else if("+"==sign){
		newSplitedAmt = parseDouble($("#splitedAmtTd").html()) + parseDouble( _amt );
	}
	//更新已分解金额
	$("#splitedAmtTd").html(newSplitedAmt);
}

function getTestMethod(){
// 	alert("DutyCode:"+$("#dutyCode").val());
// 	alert("DutyName:"+$("#dutyName").attr("title"));

	
	
//测试后台Controller调用MyBatis
// 	var data = {};
<%-- 	App.ajaxSubmit("budget/budgetResolve/resolve/testController.do?<%=WebConsts.FUNC_ID_KEY %>=02060109", --%>
// 		{data:data,async:false}, 
// 		function(data) {
// 			var result = data.jsonResult;
// 			console.info('返回结果对象：');
// 			console.info(result);
// 			alert('操作条数：'+result["result"]);
// 			console.info(result["budget"].tmpltName+"---"+result["budget"].tmpltId);

// 		}
// 	);
}

$(function(){
	
	$("#sumAmt").blur(
		function(){
			var reg = /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/;		//  /^(([0-9]|([1-9][0-9]{0,9}))((\.[0-9]{1,2})?))$/  、 /^[0-9]*[1-9][0-9]*$/
			//验证输入字符是否正整数
			if("" != $("#sumAmt").val()){
				if( !reg.test($("#sumAmt").val()) ){
					App.notyError("请检查：分解金额值不符合格式！");
					$("#sumAmt").focus();
				}
			}
		}
			
	);
	
})


</script>
</head>

<body>
<p:authFunc funcArray="020601"/>
<form action="" method="post" id="resolveForm">
	<table>
		<tr>
			<th colspan="6">
				预算分解信息
<!-- 				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 				【测试用】模板ID：${budgetReplyed.tmpltId} -->
				<input type="hidden" name="tmpltId" value="${budgetReplyed.tmpltId}">
				<input type="hidden" name="montCode" value="${budgetReplyed.montCode}"> 
			</th>
		</tr>
		
		<tr>
			<td class="tdLeft"  style="width:10%;">监控指标</td>
			<td class="tdRight" style="width:20%;" title="${budgetReplyed.montCode}">
				${budgetReplyed.montName}
			</td>
			<td class="tdLeft"  style="width:10%;">批复金额</td>
			<td class="tdRight" style="width:20%;">
				<input type="hidden" id="replyAmt" name="replyAmt" value="${budgetReplyed.replyAmt}"> 
				${budgetReplyed.replyAmt}
			</td>
			<td class="tdLeft"  style="width:10%;">已分解金额</td>
			<td class="tdRight" style="width:30%;" id="splitedAmtTd">
				${budgetReplyed.splitedAmt}
			</td>
		</tr>
	</table>
	
	<br/><br/>
	
	<table>
		<tr>
			<th colspan="7">
				预算分解
			</th>
		</tr>
		
		<tr >
			<td class="tdLeft"  style="width:10%;"><span class="red">*</span>支行/部门</td>
			<td class="tdRight" style="width:20%;">
<!-- 				分解归属部门取值描述：根据选中需要分解的模板，获取预算模板的类型dataAttr： -->
<!-- 					若资产类,dataAttr=0,则后台赋值org21Code=当前登录用户的一级行,前台通过机构树控件的leafOnlyFlag取该值下属所有责任中心; -->
<!-- 					若费用类,dataAttr=1,则后台赋值org21Code=当前登录用户的二级行,前台通过机构树控件的leafOnlyFlag取该值下属所有责任中心。 -->
				<forms:OrgSelPlugin suffix="1" radioFlag="false" selHalfFlag="false" jsVarGetValue="orgTreePlugin1" parentCheckFlag="false"  
									triggerEle="#resolveForm dutyName,dutyCode::name,id" className="base-input-text-other"
									leafOnlyFlag="true" jsVarName="treePlugin1" rootNodeId="${budgetReplyed.org21Code}" />
				<input type="hidden" id="dutyCode" name="dutyCode" />
				<input class="base-input-text-other" type="text" id="dutyName" name="dutyName" readonly="readonly" valid errorMsg="请选择部门" />
			</td>
			<td class="tdLeft"  style="width:10%;"><span class="red">*</span>选择物料</td>
			<td class="tdRight" style="width:20%;">
				<input id="selectedMatrName" class="base-input-text-other" onclick="toSelectMatr('${budgetReplyed.montCode}')" readonly="readonly" valid errorMsg="请选择物料"/>
				<input id="selectedMatrCode" type="hidden" class="base-input-text" name="matrCode" />
			</td>
			<td class="tdLeft"  style="width:10%;"><span class="red">*</span>金额</td>
			<td class="tdRight" style="width:20%;">
				<input class="base-input-text-other" id="sumAmt" name="sumAmt" valid errorMsg="请输入分解金额" maxlength="18"/>
			</td>
			<td  style="width:10%; text-align: center;">
				<input type="button" value="确定" onclick="ajaxAddResolve()">
<!-- 				<input type="button" value="测试btn" onclick="getTestMethod()"> -->
			</td>
		</tr>
	</table>
	
	<br/><br/>
	
	<table class="tableList" id="resolveDetail">
		<tr class="collspan-control">
			<th width="30%">支行/部门</th>
			<th width="40%">物料</th>
			<th width="15%">分解金额</th>
			<th width="15%">操作</th>
		</tr>
		
		<c:forEach items="${resolveDetailList}" var="resolveDetail">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite">
				<td title="${resolveDetail.dutyCode}">
					<input type="hidden" name="dutyCode" value="${resolveDetail.dutyCode}">
					${resolveDetail.dutyName}
				</td>
				<td title="${resolveDetail.matrCode}">
					<input type="hidden" name="matrCode" value="${resolveDetail.matrCode}">
					${resolveDetail.matrName}
				</td>
				<td>
					<input type="hidden" name="sumAmt" value="${resolveDetail.sumAmt}">
					${resolveDetail.sumAmt}
				</td>
				<td><div><a href="#" onclick="removeResolveTbRow(this)" title="删除"><img src="/ERP/common/images/del.gif"></a></div></td>
			</tr>
		</c:forEach>
		
		<c:if test="${ empty resolveDetailList}">
			<tr>
				<td colspan="4" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
		</c:if>
	</table>
	<p></p>
	<p></p>
	<input style="text-align: center" type="button" value="返回" onclick="backToLastPage('getResolveList.do?VISIT_FUNC_ID=020601');">
</form>
</body>
</html>