<!-- InstanceBegin template="/Templates/commonJSP.dwt" codeOutsideHTMLIsLocked="false" -->

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
	//第一个参数为报表的Servlet，第二个参数为cookie值
	ReportX1.SetReportServlet("http://" + ip + ":" + port + path + "/common/report/process.do", document.cookie);
	loadParam();
}

function loadParam()
{
    ReportX1.Schema = 'normal';
	ReportX1.FetchReport('PayAddReport', '');
	ReportX1.ShowReport('PayAddReport', 'payid=${payId}');
}
window.onload = setParam;
</script>
<script language="javascript" for="ReportX1"
	event="OnPrintState(PrinterName, ReportID, TaskID, PrintState)">
</script>
<!-- InstanceEndEditable -->



<table width="100%" height="100%" border="0">
	<tr>
		<td height="90%"><object id="ReportX1" name="ReportX1" on
			classid="clsid:E5B15231-0C29-449B-AD12-B4A7382F6BDA" width="800"
			height="500" align=middle hspace=0 vspace=0> </object></td>
	</tr>
</table>

