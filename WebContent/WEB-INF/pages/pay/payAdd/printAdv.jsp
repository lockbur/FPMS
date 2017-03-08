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
<meta name="decorator" content="dialog">
<!-- <base target="_self"> -->
<title>付款封面打印</title>
<%
  String path = request.getContextPath();
%>
<!-- InstanceBeginEditable name="pagehead" -->
<%
  String ip = request.getServerName();
  int port = request.getServerPort();
%>

<script language="javascript1.3">
var ip = '<%=ip%>';
var port = '<%=port%>';
var path = '<%=path%>';
function setParam()
{
	if($.checkPlugins('ReportViewProj','ReportViewProj.ReportViewX')==''){
		return ;
	}else if($.checkPlugins('ReportViewProj','ReportViewProj.ReportViewX')){
		$("#isHide").hide();
	}else{
		$("#isHide").show();
	}
	ReportX1.SetReportServlet("http://" + ip + ":" + port + path + "/common/report/process.do", document.cookie);
	loadParam();
}

function loadParam()
{
   // ReportX1.Schema = 'normal';
   if(${isOrder}=='0'){
	ReportX1.FetchReport('OrderPayAddAdvReport', '');
	ReportX1.ShowReport('OrderPayAddAdvReport', 'payId=${payId}&userId=${userId}');
   }else{
	ReportX1.FetchReport('NotOrderPayAddAdvReport', '');
	ReportX1.ShowReport('NotOrderPayAddAdvReport', 'payId=${payId}&userId=${userId}');
   }
}
window.onload = setParam;

// function printDownload(fileId){
// // 	var form = $("#downloadForm")[0];
// 	var form = $("#down")[0];
<%-- 	var url = '<%=request.getContextPath()%>/pay/payAdd/printDownload.do?<%=WebConsts.FUNC_ID_KEY%>=03030320'; --%>
// 	form.action = url + "&fileId="+fileId;
// 	form.submit();
// }


function printDownload(fileId){
// 	var data = {};
// 	data["fileId"] = fileId;
// 	//Ajax检查文件是否存在校验
<%-- 	App.ajaxSubmit("pay/payAdd/checkDownFileExist.do?<%=WebConsts.FUNC_ID_KEY %>=03030322", --%>
// 		{data:data,async:false}, 
// 		function(data) {
// 			var checkFlag = data.checkFlag;
// 			var checkMsg  = data.checkMsg;
// 			if(checkFlag){
// 				var form = $("#download")[0];
<%-- 				var url = '<%=request.getContextPath()%>/pay/payAdd/printDownload.do?<%=WebConsts.FUNC_ID_KEY%>=03030320'; --%>
// 				form.action = url + "&fileId="+fileId;
// 				form.submit();
// 			}else{
// 				//如果文件不存在，则不执行下载操作，并且将"文件不存在"信息报给用户
// 				alert(checkMsg);
// // 				App.notyError(checkMsg);
// // 				return false;
// 			}
// 		});
	
	var form = $("#download")[0];
	var url = '<%=request.getContextPath()%>/pay/payAdd/printDownload.do?<%=WebConsts.FUNC_ID_KEY%>=03030320';
	form.action = url + "&fileId="+fileId;
	form.submit();
	}

</script>
<script language="javascript" for="ReportX1"
	event="OnPrintState(PrinterName, ReportID, TaskID, PrintState)">
</script>
</head>

<body>
<form id="down" method="post" ></form>
<table width="100%" height="100%" border="0">
	<tr>
		<td height="90%"><object id="ReportX1" name="ReportX1" on
			classid="clsid:E5B15231-0C29-449B-AD12-B4A7382F6BDA" width="800"
			height="500" align=middle hspace=0 vspace=0> </object></td>
	</tr>
</table>
<br/>
	<input type="button" value="下载插件" onclick="printDownload('REPORT_PRINT_PLUGIN')" id="isHide"/>
 	<input type="button" value="关闭" onclick="art.dialog.close()" />
</body>
</html>