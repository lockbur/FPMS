<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.forms.platform.web.WebUtils"%>
<%@ page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="tabs">
<title>预付款明细</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/contract.query.common.js"></script>
<script type="text/javascript">
function backToLastPage2(uri)
{
	if(uri != null && uri != '')
	{
		window.top.location.href = uri;
	}
	else
	{
		history.back();
	}
}


//查询付款审批历史记录(弹窗显示)
function queryHis(payId){
	var url = "<%=request.getContextPath()%>/pay/payAdd/queryHis.do?<%=WebConsts.FUNC_ID_KEY%>=03030321&payId="+payId;
// 	App.submitShowProgress();
// 	window.showModalDialog(url,null,"dialogHeight=500px;dialogWidth=800px;center=yes;status=no;");
// 	App.submitFinish();
	$.dialog.open(
			url,
			{
				width: "60%",
				height: "80%",
				lock: true,
			    fixed: true,
			    title:"查看流转日志",
			    id:"dialogCutPage",
				close: function(){
					}		
			}
		 );
}

function printFace(payId,cntNum,isPrePay,isOrder){
	var url = "<%=request.getContextPath()%>/pay/payquery/print.do?<%=WebConsts.FUNC_ID_KEY%>=03030708&payId="+payId+"&cntNum="+cntNum+"&isPrePay="+isPrePay+"&isOrder="+isOrder;
	$.dialog.open(
			url,
			{
				width: "80%",
				height: "80%",
				lock: true,
			    fixed: true,
			    title:"打印封面",
			    id:"dialogCutPage",
				close: function(){
					}		
			}
		 );
}
</script>
</head>
<body>
<form action="" id="print" method="post"></form>
		<jsp:include page="prePayDetailCommon.jsp"></jsp:include>
		<br/>
		<div style="text-align:center;" >
			<c:if test="${!empty payInfo.id}">
				<input type="button" value="查看付款扫描文件" onclick="viewPayScanImg('${payInfo.payId}','${payInfo.icmsPkuuid}')"/>
			</c:if>
			<input type="button" value="查询审批历史记录" onclick="queryHis('${payInfo.payId}');">
			<input type="button" value="打印封面" onclick="printFace('${payInfo.payId}','${payInfo.cntNum}','${payInfo.isPrePay}','${payInfo.isOrder}');">
			<input type="button" value="返回" onclick="backToLastPage2('${contracturi}');">
		</div>	
</body>
</html>