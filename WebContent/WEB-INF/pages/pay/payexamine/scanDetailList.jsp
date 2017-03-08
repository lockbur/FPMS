<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>扫描付款单明细</title>
<script>
function pageInit()
{   
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	$( "#payDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	});
}
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


function detail(batchNo,innerId,payId){
	var form = $("#tempForm");
	url = '<%=request.getContextPath()%>/pay/payexamine/payDetail.do?<%=WebConsts.FUNC_ID_KEY%>=0303090104';
	url = url + '&batchNo='+batchNo+'&innerId='+innerId+'&payId='+payId
	form.attr('action',url);
	App.submit(form);
	
}
</script>
</head>
<body>
<p:authFunc funcArray="0303050802,03030508,03030504"/>
<form  method="post" id="tempForm">
	<p:token/>
<div id="tabs" style="border: 0;">
	<div id="tabs-1" style="padding: 0;">
		<table class="tableList">
			<tr>    
			 		<th width="18%">付款单号</th>
					<th width="8%">附件张数</th>
					<th width="17%">状态</th>
					<th width="20%">状态说明</th>
					<th width="15%">影像上传日期</th>
					<th width="15%">扫描复核日期</th>
					<th width="10%">操作</th>
			</tr>
			 <c:forEach items="${list}" var="sedList">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td><c:out value="${sedList.payId}"></c:out></td>
					<td><c:out value="${sedList.attachCnt}"/></td>
					<td><c:out value="${sedList.dataFlagName}"/></td>
					<td><c:out value="${sedList.memo}"/></td>
					<td><c:out value="${sedList.uploadTime}"/></td>
					<td><c:out value="${sedList.auditTime}"/></td>
					<td><c:if test="${sedList.isEnable == 'Y'}">
							<input type="button" onclick="detail('${sedList.batchNo}','${sedList.innerId}','${sedList.payId}')" value="明细"/></c:if>
						<c:if test="${sedList.isEnable == 'N'}">
							<input type="button" disabled="disabled" value="明细"/></c:if>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
					<tr><td style="text-align: center;" colspan="5"><span class="red">没有找到相关信息</span></td></tr>
			</c:if>
		</table>
	</div>
</div>
</form>
<p:page />
<br><br>
		<div style="text-align:center;"  >
				<input type="button" value="返回" onclick="backToLastPage('${uri}');">
		</div>	
</body>
</html>