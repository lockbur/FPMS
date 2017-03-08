<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>无效数据查询</title>
<script type="text/javascript">

	//全局变量queryType，用于判断查询表下拉框有没有发生改变(使用jQueryUI的.combobox()后onchange()事件有问题)
	var queryTypeValue = "";

/**
 * 	页面初始化执行方法
 */
function pageInit(){
	App.jqueryAutocomplete();
 	$("#queryType").combobox();
 	$("#useFlag").combobox();
 	
 	//初始化决定是否展示查询结果区域
	queryDataResultDivInit();
}

/**
 * 查询结果展示区域框(即查询结果的tableList)初始化方法
 */
function queryDataResultDivInit(){
	//根据queryType下拉选择框的选值决定指定表Table信息的展示
	if( null == $("#queryType").val() || "" == $("#queryType").val()){
		//queryType下拉框无选中值时将数据展示DIV隐藏
		$("#queryDataDiv").hide();
	}else{
		$("#queryDataDiv").show();
		
		//首先将数据展示DIV所有子元素隐藏，随后根据下拉框选中值决定展示的指定子元素xxxTable,根据查询条件进行具体的数据结果展示
		$("#queryDataDiv").children().hide();		
		if("1" == $("#queryType").val()){
			//show 总账表表格查询结果信息
			$("#orderCancelTb").show();
		}
	}
}

/**
 * 	将日期选择框使用jQueryUI的方法进行初始化
 */
function initDatePicker(){
	//设置时间插件
	$( "#befDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yymmdd"
	});
	//设置时间插件
	$( "#aftDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yymmdd"
	});
}

/**
 * 判断查询类别是否已选择(检查[查询类型]框中是否有值，有值返回true，无值返回false)
 */
function checkBeforeSubmit(){
	if( null == $("#queryType").val() || "" == $("#queryType").val()){
		return false;
	}else{
		return true;
	}
}

/**
 * [查询状态]框选择前校验(需先选择查询类别才能选择查询状态)
 */
function checkBeforeSelectState(){
	if( !checkBeforeSubmit() ){
		$("#useFlag").val("");
		App.notyError("请先选择查询类别！");
	}
}

/*
 * 展示查询条件方法：	
 *  描述：根据选择查询的无效数据表(选择[查询类别]操作时触发，文本框id为"queryType")，自动展示该表相关的查询条件
 * 		无效数据表包括：1.总账凭证接口表、2.AP发票接口表、3.AP付款接口表、4.科目余额表、5.订单接口表	
 */
function showSelectCondition(invokeType){		//当invokeType=2时，即Select框onchange事件触发，不往文本框写入值
	
	if(queryTypeValue == $("#queryType").val()){
// 		console.info('选择没有发生改变！');
	}else{
		//选择发生改变，重新给全局变量赋值
		queryTypeValue = $("#queryType").val();
		
		//条件区域框内容
		var addContent = "";
		if($("#queryType").val() == "1"){	 //订单取消类异常数据查询
			//当选择查询表发生变化时，首先将之前同为.tidQueryCondition条件区域TR删除，再在后续添加条件区域框
			$(".tidQueryCondition").remove();
			//拼接条件区域框的html内容
			addContent = 	"<tr class='tidQueryCondition'>"+
							"	<td class='tdLeft'>订单号</td>"+
							"	<td class='tdRight'>"+
							"		<input type='text' id='orderId' name='orderId' value='${unnorDataMgrBean.orderId}' class='base-input-text' maxlength='80'/>"+		
							"	</td>"+
							"	<td class='tdLeft'>PO单号</td>"+
							"	<td class='tdRight'>"+
							"		<input type='text' id='poNumber' name='poNumber' value='${unnorDataMgrBean.poNumber}' class='base-input-text' maxlength='150'/>	"+	
							"	</td>"+
							"</tr>";
			//将组合后的拼接内容添加到查询条件区域表格中第二行TR之后
			$("#queryConditionArea").find("tr").eq(1).after(addContent);
			//添加查询条件表格后，(当查询条件中有日期选择条件的需要)初始化日期插件
			initDatePicker();
			if(invokeType == 2){
				//当onchange事件展示查询条件时，清空条件的文本框值
				$(":text").val("");
			}
			
		}else if("" == $("#queryType").val()){
			//当没有选择[查询类型]时，应该将其他的input清空隐藏！
			$(".tidQueryCondition").remove();
		}

	}
	
	
}

/**
 * 判断起始日期文本框两者的大小
 *		处理逻辑为：	判断开始日期值不能大于结束日期值，befDate<aftDate为判断通过；
 *					当判断为不通过时，提示并清空日期框的值让用户重新选择；
 *		**可以使用平台校验方法checkDate替换：$.checkDate(开始日期ID,结束日期ID)
 */
function dateOnblur(){
	//获取起始结束日期框的值
	var befDate = $("#befDate").val();
	var afterDate = $("#aftDate").val();
	if(!befDate){
		//当判断起始日期为空时，让用户选择开始日期，并清空结束日期
		App.notyError("请先选择开始日期！");
		$("#aftDate").val("");
	}else{
		//否则比较[开始日期]与[结束日期]两者的大小
		if(befDate > afterDate){
			//当判断不通过时，清空结束日期让用户重新选择
			App.notyError("开始日期不能大于结束日期！");
			$("#aftDate").val("");
		}
	}
}

/**
 * 【查询功能】调用后台进行数据查询
 */
function queryAbnormalData(){
	if( !checkBeforeSubmit() ){
		App.notyError("【查询操作】需要先选择异常数据的查询类别");
	}else{
		var form = $("#abnormalDataQueryForm")[0];
		form.action = '<%=request.getContextPath()%>/amortization/fmsMgr/unnormalDataQuery.do?<%=WebConsts.FUNC_ID_KEY%>=040701';
		App.submit(form);
	}
}

/**
 * 【导出功能】调用后台程序进行导出查询条件的无效数据信息(根据不同的选择值导出不同的BeanList，后台使用对应表的Excel模板进行接收)
 */
function exportQueryAbnorData(){
	if( !checkBeforeSubmit() ){
		App.notyError("【导出操作】需要先选择无效数据的查询类别");
	}else{
		//组织导入数据
		var data = {};
		data['queryType'] 	= $("#queryType").val();		//决定后台查询的类别、Excel模板的选择、导出配置的选择
		data['useFlag'] 	= $("#useFlag").val();			//决定后台导出数据的[无效状态]
		data['voucherName'] = $("#voucherName").val();			
		data['cntNum'] 		= $("#cntNum").val();			
		data['debitAmt'] 	= $("#debitAmt").val();			
		data['befDate'] 	= $("#befDate").val();			
		data['aftDate'] 	= $("#aftDate").val();			
		data['payId'] 		= $("#payId").val();			
		data['invoiceNo'] 	= $("#invoiceNo").val();			
		data['invoiceAmt'] 	= $("#invoiceAmt").val();			
		data['payAmt'] 		= $("#payAmt").val();			
		data['orgName'] 	= $("#orgName").val();			
		data['cglName'] 	= $("#cglName").val();			
		data['stockNum'] 	= $("#stockNum").val();			
		data['orderId'] 	= $("#orderId").val();			
		data['poNumber'] 	= $("#poNumber").val();			
		data['productName'] = $("#productName").val();			
		
		App.ajaxSubmit("amortization/abnormalDataMgr/queryAbnorDataExport.do?<%=WebConsts.FUNC_ID_KEY%>=04070301",
				{data:data,async : false},
				function(data){
					flag = data.pass;					//导出操作是否 成功  或  出现异常报错
					if(flag){
						$("<div>Excel已成功提交后台处理<br\>是否进入下载页面？</div>").dialog({
							resizable: false,
							width: 290,
							height:'auto',
							modal: true,
							dialogClass: 'dClass',
							title: "跳转至下载页面",
							buttons:[
							         {
							        	 text:"确认",
											click:function(){
												//跳转至下载页面
								        	 	var form = document.forms[0];
												form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101';
												App.submit(form);
											}   
							         },
									{
										text:"取消",
										click:function(){
											$(this).dialog("close");
										}
									}
							]
						});
						isPass =  true;
					}
					else
					{
						App.notyError("添加下载失败，请重试!");
						isPass =  false;
					}
				});
		return isPass;
	}
}

/**
 * 【重置功能】重置查询条件文件+选择框
 */
function resetAll() {
	queryTypeValue = "";
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
	//重置操作时，将查询条件所有input框删除(隐藏)
	$(".tidQueryCondition").remove();
	
<%-- 	<% request.removeAttribute("unnorDataMgrBean");%> --%>
	//应该清空后台传到前台Request的对象缓存
	
	var flag ;
	var data = {};
	App.ajaxSubmit("amortization/abnormalDataMgr/ajaxToResetButton.do?<%=WebConsts.FUNC_ID_KEY%>=04070302",
				{data:data,async : false},
				function(data){
					flag = data.result;
				});

}



function modifyStatus(orderid)
{
	if(confirm("确定修改为正常状态？"))
	{
		var form = $("#abnormalDataQueryForm")[0];
		var url = '<%=request.getContextPath()%>/amortization/fmsMgr/unnormalDataUpdate.do?<%=WebConsts.FUNC_ID_KEY%>=04070101&orderId='+orderid;
		location.href = url;
	}
	else
	{
		return ;
	}
}
 

 
$(function(){
	//$("#queryDataDiv").children().hide();
	
	showSelectCondition(1);
	
})

</script>
</head>

<body>
<p:authFunc funcArray="040701"/>
<form action="" method="post" id="abnormalDataQueryForm">
	<table id="queryConditionArea">
		<tr>
			<th colspan="4">
				异常数据查询
			</th>
		</tr>
		<!-- 公共的查询条件：1.查询表		2.查询状态 -->
		<tr>
			<td class="tdLeft">查询类别</td>
			<td class="tdRight" colspan="3">
				<div class="ui-widget">
					<select id="queryType" name="queryType"  onchange="showSelectCondition(2)">
						<option value="">--请选择--</option>		
						<option value="1" <c:if test="${unnorDataMgrBean.queryType == '1'}">selected="selected"</c:if>>订单取消类</option>		
						<option value="2" <c:if test="${unnorDataMgrBean.queryType == '2'}">selected="selected"</c:if>>...</option>		
					</select>
				</div>
			</td>
			
		</tr>

		<tr>
			<td colspan="4" class="tdBottom tdWhite">
				<%-- <p:button funcId="040703" type="search"/> --%>
				<input type="button" value="查询" onclick="queryAbnormalData();">
<!--				<input type="button" value="导出" onclick="exportQueryAbnorData();">-->
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
	
	
	<!-- 	查询数据展示DIV	 -->
	<div id="queryDataDiv">
		<!-- 1.订单取消异常数据    -->
		<table id="orderCancelTb" class="tableList" >
			<tr class="collspan-control">
				<th width="30%" style="text-align: center">PO单号</th>
				<th width="30%" style="text-align: center">订单号</th>
				<th width="20%" style="text-align: center">状态</th>
				<th width="20%" style="text-align: center">操作</th>
			</tr>
			<c:forEach items="${orderCancelList}" var="orderCancelInfo">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trWhite');" class="trWhite">
					<td style="text-align: center">${orderCancelInfo.poNumber}</td>
					<td style="text-align: center">${orderCancelInfo.orderId}</td>
					<td style="text-align: center">${orderCancelInfo.status}</td>
					<td style="text-align: center"><input type="button" value="修改状态" class="base-input-button" onclick="modifyStatus('${orderCancelInfo.orderId}');"/></td>
				</tr>
			</c:forEach>
			<c:if test="${empty orderCancelList}">
				<tr>
					<td colspan="8" style="text-align: center;"><span style="color: red">查询不到相应的数据！请检查搜索条件再查询。</span></td>
				</tr>
			</c:if>
		</table>
		
	</div>
	
</form>
<p:page/>
</body>
</html>