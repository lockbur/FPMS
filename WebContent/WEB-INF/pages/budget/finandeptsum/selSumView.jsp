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
<title>本级汇总明细</title>
<style type="text/css">
	.tableList tr th{
		text-align: center
	}
	.tableList tr td{
		text-align: center
	}
</style>
<script src="<%=request.getContextPath()%>/common/js/TableCombine.js"></script>
<script type="text/javascript">
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
	$("#deptId").val("");
	$("#fstdeptIdDiv,#seddeptIdDiv").css("display","none");
	$("#userTypeDiv").find("label").eq(0).click();
	$("#isDeletedDiv").find("label").eq(0).click();
	
	
}
function pageInit(){
	App.jqueryAutocomplete();
	new TableCombine().rowspanTable( "listTab", 2, null, 0, 1, null, null, null);
}

</script>
</head>
<body>
<p:authFunc funcArray="02040103,02040104"/>
<form action="" method="post" id="forms" >
<input type="hidden" name="matrAuditDept" value="${bean.matrAuditDept }"/>
<input type="hidden" name="tmpltId" value="${bean.tmpltId }"/>
<input type="hidden" name="matrCode" value="${bean.matrCode }"/>
	<input type="hidden" id="addAmt" name="addAmt" value="${bean.addAmt }"/>
	<input type="hidden" id="auditAmt" name="auditAmt" value="${bean.auditAmt }"/>
</form>
<div id="tabs" style="border: 0;">

	<div id="tabs-1" style="padding: 0;">
		
		<p:token/>
		<table id="listTab" class="tableList">
			<tr class="collspan-control">
			 	<th   width="20%">责任中心编码</th>
			     <th    >责任中心</th>
				 <th    width="20%">申报金额</th>
			</tr>
			
			<c:forEach items="${list}" var="list" varStatus="status">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				    <td  >
				   		${list.dutyCode}
				    </td>
				    <td>${list.dutyName}</td>
				    <td>${list.inAmt}</td>
				</tr>
			</c:forEach>
			<tr>
				<td colspan="3" style="text-align: center" align="center"><input type="button" value="返回" onclick="backToLastPage('${uri}')"></td>
			</tr>
			<c:if test="${empty list}">
				<tr>
					<td colspan="3" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
			</c:if>
		</table>
		
		<p:page/>
	</div>
</div>
  <jsp:include page="back.jsp" />
</body>
</html>