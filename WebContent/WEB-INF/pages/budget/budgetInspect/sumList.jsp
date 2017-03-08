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
<title>下达明细</title>
<script type="text/javascript">
function sumDetail(sdId){
	$.dialog.open(
			'<%=request.getContextPath()%>/budget/budgetInspect/sumDetail.do?<%=WebConsts.FUNC_ID_KEY %>=02070106&sdId='+sdId,
			{
				width: "75%",
				height: "50%",
				lock: true,
			    fixed: true,
			    title:"明细详情 ",
			    id:"dialogCutPage",
				close: function(){
					}		
			}
		 );
}

function exportQueryDataList(bgtId , exportType){
	var form = $("#sumDetailForm")[0];
	form.action="<%=request.getContextPath()%>/budget/budgetInspect/exportBgtInspectQueryData.do?<%=WebConsts.FUNC_ID_KEY %>=02070107&bgtId="+bgtId+"&exportType="+exportType;
	App.submit(form);
}

</script>
</head>
<body>
<p:authFunc funcArray="0207,020701"/>
<form method="post" id="sumDetailForm">
	<p:token/>
		<table>
		<tr class="collspan-control">
			<th colspan="4">下达明细</th>
		</tr>
	</table>
	<table id="listTab" class="tableList" >
		<tr>
			<th width="15%">预算明细编码</th>
			<th width="25%">预算操作类型</th>
			<th width="15%">预算值</th>
			<th width="15%">操作人</th>
			<th width="15%">操作日期</th>
			<th width="15%">详细</th>
		</tr>
		<tr>
			<c:forEach items="${bmList}" var="bm">
				<tr onmousemove="setTrBgClass(this, 'trOnOver2')" onmouseout="setTrBgClass(this, 'trOther')">
					<td style="text-align: center;">${bm.sdId }</td>
					<td style="text-align: center;">${bm.bgtType }</td>
					<td style="text-align: center;">${bm.bgtValue }</td>
					<td style="text-align: center;">${bm.instOper }</td>
					<td style="text-align: center;">${bm.instDate }</td>
					<td>
						<div class="detail"  style="text-align: center;">
					    	<a href="#" onclick="sumDetail('${bm.sdId }');" title="<%=WebUtils.getMessage("button.view")%>"></a>
						</div>
					</td>
				</tr>
			</c:forEach>
			<c:if test="${empty bmList }">
				<tr>
					<td colspan="6" class="red" style="text-align: center;"><span>没有找到相关信息!</span></td>
				</tr>
			</c:if>	
		</tr>
			<tr>
				<td colspan="6" class="tdWhite" style="text-align: center">
					<input type="button" style="display: none" value="导出查询数据" onclick="exportQueryDataList('${bm.bgtId}','2')">  
					<input type="button" value="返回" onclick="backToLastPage('${uri}')">  
				</td>
			</tr>
	</table>
</form>
<p:page/>
</body>
</html>