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
<script type="text/javascript">

function monthOver(maxDataFlag,type){
// 	if(maxDataFlag=="") {
// 		maxDataFlag="1";
// 	}
	var form=$("#moForm")[0];
	form.action="<%=request.getContextPath()%>/monthOver/change.do?<%=WebConsts.FUNC_ID_KEY%>=03060101&dataFlag="+maxDataFlag+"&type="+type;
	App.submit(form);
}


//校验冲销任务状态
function ajaxCheckProvision(maxDataFlag,type){
	var data = {};
	App.ajaxSubmit("monthOver/ajaxCheckProvision.do?VISIT_FUNC_ID=03060104",
			{data:data,async : false},
			function(data){
				flag = data.pass;
				if(!flag){
					App.notyError("当月预提冲销任务未执行成功或者存在历史预提待摊任务未执行成功！不允许结束月结。");
					return ;
				}
				else
				{
					monthOver(maxDataFlag,type);
				}
			});
	
	
}

function checkPayStatus(maxDataFlag){
	var data = {};
	App.ajaxSubmit("monthOver/checkPayStatus.do?VISIT_FUNC_ID=03060102",
			{data:data,async : false},
			function(data){
				flag = data.pass;
				if(!flag){
					$("<div>存在未上传的应付发票、预付款核销及po订单数据。<br/>"+
							"是否先上传？</div>").dialog({
						resizable: false,
						width: 290,
						height:'auto',
						modal: true,
						dialogClass: 'dClass',
						buttons:[
						         {
						        	 text:"上传",
										click:function(){
											monthOver(maxDataFlag,"twice");
										}   
						         },
									{
										text:"不上传",
										click:function(){
											monthOver(maxDataFlag,"once");
										}
									}
						]
					});
					return;
				}
				else
				{
					monthOver(maxDataFlag,"once");
				}
			});
}

</script>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>月结</title>
</head>
<body>
<p:authFunc funcArray="030601,03060101,03060102,03060104"/>
<form action="" method="post" id="moForm">
<!--     <input type="hidden" name="dataFlag" id="dataFlag"> -->
    <table style="width:50%;margin: 0 auto;" class="tableList">
    	<tr>
    		<th width="50%">月结状态</th>
    		<th width="50%">操作</th>
    	</tr>
    	<tr>
    	    <td id="moStatus" style="color: red;font-weight: bold;">
    	    	<c:if test="${empty list}">非月结中...</c:if>
    	    	<c:if test="${max=='0'}">月结中</c:if>
    	    	<c:if test="${max=='1'}">月结结束</c:if>
    	    </td>
    	    <td>
    	    	<c:if test="${empty list}">
    	    		<input type="button" id="moFunc" value="开始月结" onclick="checkPayStatus('${max}')"/>
    	    	</c:if>
    	    	<c:if test="${max=='0'}">
    	    		<input type="button" id="moFunc" value="结束月结" onclick="ajaxCheckProvision('${max}','once')"/>
    	    	</c:if>
    	    	<c:if test="${max=='1'}">
    	    		<input type="button" id="moFunc" value="开始月结" onclick="checkPayStatus('${max}')"/>
    	    	</c:if>
    	    </td>
    	</tr>
    </table>
    <br/>
	<table class="tableList">
		<tr>
			<th width="20%">序号</th>
			<th width="20%">月结状态</th>
			<th width="20%">操作柜员</th>
			<th width="20%">操作日期</th>
			<th width="20%">操作时间</th>												
		</tr>
		<c:forEach items="${list}" var="mb" >
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
			<td>${mb.id}</td>
			<td>
				<c:if test="${mb.dataFlag=='0'}">月结中</c:if>
    	    	<c:if test="${mb.dataFlag=='1'}">月结结束</c:if>
    	    </td>
			<td>${mb.instOper}</td>
			<td>${mb.instDate}</td>
			<td>${mb.instTime}</td>
		</c:forEach>
		<c:if test="${empty list}">
	   		<tr>
			<td colspan="5" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
	    </c:if>
</table>
</form>
<p:page/>
</body>
</html>