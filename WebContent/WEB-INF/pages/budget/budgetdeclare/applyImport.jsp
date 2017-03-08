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
<title>预算申报导入</title>
<script type="text/javascript">
function pageInit(){
}


function doValidate() {
	
	//提交前调用
	if(!App.valid("#budgetImportForm")){return;}
	
	return true;
}

</script>
</head>
<body>
<p:authFunc funcArray="020202,02020201,02020202,02020203"/>
<form action="" method="post" id="budgetImportForm" enctype="multipart/form-data" >
<p:token/>
	<input type="hidden" name="tmpltId" value="${budgetDeclareBean.tmpltId}"/>
	<input type="hidden" name="dataAttr" value="${budgetDeclareBean.dataAttr}"/>
	<table id="approveChainTable">
		<tr>
			<th colspan="4" style="text-align: center;">预算申报</th>
		</tr>
		<tr>
			<td class="tdLeft"><span class="red">*</span>数据文件</td>
			<td class="tdRight">
				<input type="file" id="impFile" name="impFile" style="width: 300px;" class="base-input-text" valid errorMsg="请选择数据文件。" />
				<a href="#" onclick="dowloadTemplate();"><span class="red" id="templateFileName"></span></a>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				    <p:button funcId="02020203" value="导入"/>
			</td>
		</tr>
	</table>
</form>

<p:page/>

</body>
</html>