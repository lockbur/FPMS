<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@page import="com.forms.platform.core.util.Tool"%>
<%@page import="com.forms.platform.core.tree.ITree"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">

<meta name="decorator" content="dialog">
<base target="_self">
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/component/dtree/css/dhtmlxtree_std.css">
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/component/dtree/js/dhtmlxtree_std_patch.js"></script>
<title>物料列表</title>
<script type="text/javascript">
function getProvider(){	
	var nodeTR = null; 
	var checkCUS = document.getElementsByName("checkCus");
	if(checkCUS != null)
	{
		if(checkCUS.length == null)
		{	
	 		if(checkCUS.checked)	
	 		 	nodeTR = checkCUS.parentNode.parentNode;	   
		 }
		 else
		 {
			  for(var i = 0 ; i < checkCUS.length; i++)
			  {
				   if(checkCUS[i].checked)
					   
				   {			
					   nodeTR = checkCUS[i].parentNode.parentNode;
				   }
			  }
			 
		  }
	  
		  if(nodeTR == null)
		  {
			  App.notyError("请选择物料 ！");
			    return false;
		  }
		   	var node = nodeTR.children;
		   	var matrCode = node[0].children[0].value;;
			var matrName = node[1].innerText;
			var object={};
			object.matrCode=matrCode;
			object.matrName=matrName;
			art.dialog.data('object',object);
		}
		art.dialog.close();	   		 
}
function matrInfo(){
	var form=$("#tempForm")[0];
	form.action="<%=request.getContextPath()%>/budget/budgetInspect/getMatrName.do?<%=WebConsts.FUNC_ID_KEY%>=02070105";
	App.submit(form);
}
 $(function(){
	$('#tableListRadio tr').click(function(){
		$("#tableListRadio tr :radio").removeAttr('checked');
		$(this).find(":radio").each(function(){
			$(this).prop('checked','true');
		});
	});
}); 
function resetAll(){
	$(":text").val("");
	$("#matrTypeDiv").find("label").eq('').click();
}
</script>
</head>
<body>
<form action="" id="tempForm" method="post">
   	<table>
   	<tr class="collspan-control">
   			<th colspan="2">
				物料列表
			</th>
		</tr>
		<tr>
			<td class="tdLeft" style="width:50%" >物料名称</td>
			<td class="tdRight" style="width: 50%" >
				<input type="text" id="matrName" name="matrName"  class="base-input-text"  value="${matr.matrName}" maxlength="100"/>&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
		    <td class="tdLeft" style="width:50%" >物料编码</td>
			<td class="tdRight" style="width: 50%" >
				<input type="text" id="matrCode" name="matrCode"  class="base-input-text"  value="${matr.matrCode}" maxlength="100"/>&nbsp;&nbsp;
			</td>
		</tr>
		<tr>
			<td class="tdLeft" style="width: 50%">核算码</td>
			<td class="tdRight" style="width: 50%">
				<input type="text" id="cglCode" name="cglCode"  class="base-input-text"  value="${matr.cglCode}" maxlength="100"/>&nbsp;&nbsp;
			</td>		 
		</tr>
		<tr>
		    <td class="tdLeft" style="width: 50%">物料类型</td>
		    <td class="tdRight" style="width: 50%" >
				<div class="base-input-radio" id="matrTypeDiv">
				    <label for="matrType1" onclick="App.radioCheck   (this,'matrTypeDiv')"  <c:if test="${'0'==matr.matrType}">class="check-label"</c:if>>全部</label><input type="radio" id="matrType1" name="matrType" value="0" <c:if test="${'0'==matr.matrType or matr.matrType==null}">checked="checked"</c:if>>
					<label for="matrType2" onclick="App.radioCheck(this,'matrTypeDiv')" <c:if test="${'1'==matr.matrType}">class="check-label"</c:if>>资产</label><input type="radio" id="matrType2" name="matrType" value="1" <c:if test="${'1'==matr.matrType}">checked="checked"</c:if>>
					<label for="matrType3" onclick="App.radioCheck(this,'matrTypeDiv')" <c:if test="${'3'==matr.matrType}">class="check-label"</c:if>>费用</label><input type="radio" id="matrType3" name="matrType" value="3" <c:if test="${'3'==matr.matrType}">checked="checked"</c:if>>
				</div> 
			</td>	
		</tr>
		<tr>
			<td colspan="2" align="center" style="text-align: center;" class="tdWhite">
				<input type="button" value="查找" onclick="matrInfo()"/>
				<input type="button" value="重置" onclick="resetAll()"/>
			</td>
		</tr>
	</table>
	<br>
	<table class="tableList" id="tableListRadio">
			<tr>    
					<th width="12%">选择</th>
					<th width="22%">物料名称</th>
					<th width="22%">物料编码</th>
					<th width="22%">物料类型</th>
					<th width="22%">核算码</th>
			</tr>
		<c:if test="${empty matrList}">
			<tr>
				<td colspan="4" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
		 <c:forEach items="${matrList}" var="list" varStatus="status">
			<tr    ondblclick="return getProvider();" onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor: pointer" class="trOther">
				<td>
					<input  type="radio" name="checkCus"  value="${list.matrCode}"/>
				</td>
				<td><c:out value="${list.matrName}"/></td>
				<td><c:out value="${list.matrCode}"/></td>
				<td><c:out value="${list.matrType}"/></td>
				<td><c:out value="${list.cglCode}"/></td>
			</tr>
		</c:forEach>
	</table>
</form>
<p:page/>
<br><br><br>
	<div style="text-align:center;" >
		<input type="button" name="button" value="确定" class="button" onclick="return getProvider();" />
		<input type="button" value="关闭" onclick="art.dialog.close()" />
	</div>
</body>
</html>