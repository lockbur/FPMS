<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>订单付款</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	
 	//设置时间插件
	$( "#befDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	
}

function resetAll() {
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
}




function checkProvision(paynum,count1)
{	
	if(parseInt(count1)==0)
	{
		App.notyError("总笔数为零，没有需要上送的数据");
		return ;
	}
	var data = {};
	
	$("<div id='confirmDiv'>是否确认执行此操作？</div>").dialog({
    	resizable: false,
    	width: 290,
    	height:'auto',
    	modal: true,
    	dialogClass: 'dClass',
    	buttons:[
         {
        	 text:"确认",
				click:function(){
					 App.ajaxSubmit("amortization/fmsMgr/ajaxCheckProvision.do?VISIT_FUNC_ID=04030102",
							{data:data,async : false},
							function(data){
								flag = data.pass;
								if(!flag){
									$("<div>当月冲销预提未执行成功<br/>请先执行冲销预提操作！</div>").dialog({
										resizable: false,
										width: 290,
										height:'auto',
										modal: true,
										dialogClass: 'dClass',
										buttons:[
										         {
										        	 text:"确认",
														click:function(){
															$(this).dialog("close");
															$("#confirmDiv").dialog("close");
														}   
										         }
										]
									});
								}
								else
								{
									dealPayOrder(paynum);
								}
							}); 
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
	
}

function dealPayOrder(paynum){
	var form = $("#queryForm")[0];
	var url = '<%=request.getContextPath()%>/amortization/fmsMgr/payMgr.do?<%=WebConsts.FUNC_ID_KEY%>=04030101&paynum='+paynum;
	form.action = url;
	form.submit();
}
</script>
</head>

<body>
<p:authFunc funcArray="040301,04030101"/>
<form method="post" id="queryForm" action="">
	<table class="tableList">
		<tr>
			<th width="20%">文件类型</th>
			<th width="15%">OU总数</th>
			<th width="15%">总笔数</th>
			<th width="15%">待撤并未发送笔数</th>
			<th width="25%">总金额</th>
			<th width="10%">上送FMS</th>
		</tr>
		<tr>
			<td>应付发票</td>
			<td>${fmsMgr.payOuCnt }</td>
			<td>${fmsMgr.payCnt }</td>
			<td>${fmsMgr.payNotSendCnt }</td>
			<td>
				<c:if test="${fmsMgr.paySumAmt !=0 }">
					${fn:replace(fmsMgr.paySumStr, "&", "<br/>")}
				</c:if>
				<c:if test="${fmsMgr.paySumAmt ==0 }">
					${fmsMgr.paySumAmt}
				</c:if>
			</td>
			<td><input type="button" value="上送" class="base-input-button" onclick="checkProvision('31','${fmsMgr.payCnt }')"/></td>
		</tr>
		<%-- <tr>
			<td>预付款核销</td>
			<td>${fmsMgr.advOuCnt }</td>
			<td>${fmsMgr.advCnt }</td>
			<td>${fmsMgr.advNotSendCnt }</td>
			<td>${fmsMgr.advSumAmt }</td>
		</tr> --%>
		<tr>
			<td>采购订单</td>
			<td>${fmsMgr.orderOuCnt }</td>
			<td>${fmsMgr.orderCnt }</td>
			<td>${fmsMgr.ordNotSendCnt }</td>
			<td>${fmsMgr.orderSumAmt }</td>
			<td><input type="button" value="上送" class="base-input-button" onclick="checkProvision('34','${fmsMgr.orderCnt }','0')"/></td>
		</tr>
		<!-- 
		<tr>
			<td colspan="4">
			    <input type="button" value="应付发票" class="base-input-button" onclick="checkProvision()"/>
			    <input type="button" value="预付款核销" class="base-input-button" onclick="checkProvision()"/>
			    <input type="button" value="采购订单" class="base-input-button" onclick="checkProvision()"/>
			</td>		
		</tr> 
		-->
	</table>
</form>
</body>
</html>