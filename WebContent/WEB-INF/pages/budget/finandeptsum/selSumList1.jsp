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
<title>一级汇总</title>
<style type="text/css">
	.tableList tr th{
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
	new TableCombine().rowspanTable( "listTab", 0, null, 0, 1, null, null, null);
}
function view(tmpltId ,matrCode ,matrAuditDept){
	$("#tmpltId").val(tmpltId);
	$("#matrCode").val(matrCode);
	$("#matrAuditDept").val(matrAuditDept);
	var url ="<%=request.getContextPath()%>/budget/finandeptsum/firstView.do?<%=WebConsts.FUNC_ID_KEY %>=02040302";
	var form =$("#viewForm");
	form.attr("action",url);
	App.submit(form);
	
}


</script>
</head>
<body>
<p:authFunc funcArray="02040301,02040302"/>
<form action="" id="viewForm" method="post">
		<input type="hidden"  name="tmpltId" value="${finanDeptSum.tmpltId }" id="tmpltId">
		<input type="hidden" name="matrCode" id="matrCode"/>
		<input type="hidden" name="matrAuditDept" id="matrAuditDept"/>
</form>
<div id="tabs" style="border: 0;">

	<div id="tabs-1" style="padding: 0;">
		<form action="" method="post" id="forms" >
		<input type="hidden"  name="tmpltId" value="${finanDeptSum.tmpltId }"  id="tmpltId">
		
		<p:token/>
			<table id="approveChainTable">
				<tr class="collspan-control"><th colspan="4">查询</th></tr>
				<tr>
					<td class="tdLeft"> 物料编码</td>
					<td class="tdRight">
						<input type="text" name=matrCode value="${finanDeptSum.matrCode }"  id="matrCode" class="base-input-text" maxlength="11"/>
					</td>
						<td class="tdLeft"> 物料名称</td>
					<td class="tdRight">
						<input type="text" name=matrName value="${finanDeptSum.matrName }"  id="matrName" class="base-input-text" maxlength="300"/>
					</td>
				</tr>
				<tr>
					<td class="tdLeft">
						部门
					</td>
					<td class="tdRight" colspan="3">
 						<forms:OrgSelPlugin rootLevel="1" rootNodeId="${org1Code}"  jsVarGetValue="matrAuditDept" initValue="${finanDeptSum.matrAuditDept}" jsVarName="" />
					</td>
				<tr>
				<tr>
					<td colspan="4">
						<!-- 查找指定部门的汇总情况 -->
						<p:button funcId="02040301" value="查询"/>
						<input type="button" value="重置" onclick="resetAll();">
						<input type="button" value="返回" onclick="backToLastPage('${uri}')">
					</td>
				</tr>
			</table>
		</form>
		
		<br/>
		
		<table id="listTab" class="tableList">
			<tr>
			   <th rowspan="2" width="20%">物料归口</th>
			     <th rowspan="2"  width="10%">物料编码</th>
			     <th rowspan="2"  width="20%">物料名称</th>
				 <th colspan="3"  width="25%">金额</th>
				 <th rowspan="2" width="15%">状态</th>
			</tr>
			<tr>
				<th>申报金额</th>
				<th>存量预算</th>
				<th>初审金额</th>
			</tr>
			<c:forEach items="${finanSumList}" var="finanSum" varStatus="status">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				    <td title="${finanSum.matrAuditDept}">
				   		${finanSum.matrAuditDeptName}
				    </td>
				    <td>${finanSum.matrCode}</td>
				    <td>${finanSum.matrName}</td>
				    <td>
				    	<a href="javascript:(void)" onclick="view('${finanDeptSum.tmpltId}','${finanSum.matrCode}','${finanSum.matrAuditDept}');">
				    		${finanSum.addAmt}
				    	</a>
				    </td>
				    <td>${finanSum.oldAmt}</td>
				      <td>${finanSum.auditAmt}</td>
				       <td align="center">${finanSum.auditFlagName}</td>
				</tr>
			</c:forEach>
			<c:if test="${empty finanSumList}">
				<tr>
					<td colspan="9" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
			</c:if>
		</table>
		<p:page/>
	
	</div>
</div>
</body>
</html>