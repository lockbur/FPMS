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
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户数据导出</title>
<script type="text/javascript">

function pageInit(){
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
}

function addDownload(){
	App.ajaxSubmitForm("sysmanagement/user/exportUserRoleRln.do?VISIT_FUNC_ID=0103030101", $("#queryForm"),  
    		function(data){
				flag = data.pass;
				if(!flag){
					App.notyError("添加下载失败，请重试!");
				}
				else
				{
					$("<div>Excel已成功提交后台处理<br\>是否进入下载页面？</div>").dialog({
						resizable: false,
						width: 290,
						height:'auto',
						modal: true,
						title: "跳转下载页面",
						buttons:[
						         {
						        	 text:"确认",
										click:function(){
							        	 	var form = document.forms[0];
							        	 	var upStepUrl = '${currentUri}';				//当前页面的uri(无项目根路径)；	可在Controller层添加代码：WebUtils.setRequestAttr("currentUri", WebUtils.getUriWithoutRoot());
							        		var upStepParams = '';							//当前页面需要的过滤参数(当无参数时为空)
							        		$(form).append("<input type='hidden' name='upStepUrl' id='upStepUrl'>");			//为提交跳转的form添加input框记录传递参数upStepUrl(用于查询FuncId)
							        		$(form).append("<input type='hidden' name='upStepParams' id='upStepParams'>");	//为提交跳转的form添加input框记录传递参数upStepParams(用于当当前页面有查询过滤参数时)
							        		$("form #upStepUrl").val(upStepUrl);											//添加传递参数的值
							        		$("form #upStepParams").val(upStepParams);
											form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101';
											App.submit(form);
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
			});
	
}

</script>
</head>
<body>
<p:authFunc funcArray="01030301,0103030101"/>
<form action="" method="post" id="queryForm">
	<p:token/>
		<table>	
			<tr>
				<th colspan="2">导出</th>
			</tr>
			<tr>
				<td colspan="2" class="tdWhite" >
					<div class="ui-widget">
					<select id="flag" name="flag" class="erp_cascade_select" >
						<option value="01">已有职责分配的人员信息</option>
						<option value="02">全部人员信息</option>
					</select>
					</div>
				</td>
			</tr>
			<tr>
				<td colspan="2" class="tdWhite" id="buttonTd">
					 <input  type="button" id="exportButton"  onclick="addDownload()"   value="用户数据导出"/>
				</td>
			</tr>
		</table>
</form>
<p:page/>
</body>
</html>