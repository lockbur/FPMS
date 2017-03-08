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
<title>合同预算列表</title>
<script type="text/javascript">
function detail(scdId){
	url="<%=request.getContextPath()%>/budget/budgetInspect/cntInspectDetail.do?<%=WebConsts.FUNC_ID_KEY%>=02070108&scdId="+scdId;
	$.dialog.open(
		url,
		{width: "75%",
		 height: "50%",
		 lock: true,
		 fixed: true,
		 title:"合同预算详细",
		 id:"dialogCutPage"}
	);
}
</script>
</head>
<body>
<p:authFunc funcArray="020702,02070201"/>
<form action="" method="post" id="BLForm">
	<table id="listTab" class="tableList">
			<tr>
				<th>合同号或付款单号</th>
				<th>操作日期</th>
				<th>预算类型</th>
				<th>预算值</th>
				<th>操作</th>
			</tr>
			<c:forEach items="${sumCntDetailList}" var="sumCntDetail" >
				<tr  onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor: pointer" class="trOther">
					<td >
					    ${sumCntDetail.payId}
					</td>
					<td >
					    ${sumCntDetail.instDate}
					</td>
					<td >
					    ${sumCntDetail.bgtType}
					</td>
					<td >
					     ${sumCntDetail.bgtVal}
					</td>
					<td >
						<div class="detail">
				    		<a href="#" onclick="detail('${sumCntDetail.scdId}');" title="<%=WebUtils.getMessage("button.view")%>"></a>
						</div>
					</td>
				</tr>
			</c:forEach>
				<c:if test="${empty sumCntDetailList }">
					<tr>
						<td colspan="5" class="red" style="text-align: center;"><span>没有找到相关信息!</span></td>
					</tr>
				</c:if>	
				<tr>
				<td rowspan="1" colspan="5" class="tdWhite" style="text-align: center">
					<input type="button" value="返回" onclick="backToLastPage('${uri}')"> 
				</td>
			</tr>
			
		</table>
</form>
</body>
</html>