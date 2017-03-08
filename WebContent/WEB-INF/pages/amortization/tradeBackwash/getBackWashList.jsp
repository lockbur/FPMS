	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同列表</title>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	
 	//设置时间插件
	$( "#befDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
 	
	//设置时间插件
	$( "#aftDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
	
}
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
}

function washbackOne(cntNum){
	var form=$("#washbackForm2")[0];
	form.action= "<%=request.getContextPath()%>/amortization/tradeBackwash/washback.do?<%=WebConsts.FUNC_ID_KEY %>=040603&cntNumList="+cntNum;
	App.submit(form);
}

//全选
function checkAll(){
	if($("#checkItem").is(":checked")){
		$("input[name='cntNumList']").prop('checked','true');
	}else{
		$("input[name='cntNumList']").removeAttr("checked");
	}
}

//批量回冲
function washbackAll(){
	if($("input[name='cntNumList']:checked").size() < 1){
		App.notyError("请选择要回冲的合同！");
		return false;
	}else{
		var form=$("#washbackForm")[0];
		form.action= "<%=request.getContextPath()%>/amortization/tradeBackwash/washback.do?<%=WebConsts.FUNC_ID_KEY %>=040603";
		App.submit(form);
	}
}


</script>
</head>

<body>
<p:authFunc funcArray="040601"/>
<form  method="post" id="queryForm" action="<%=request.getContextPath()%>/contract/query/queryList.do?<%=WebConsts.FUNC_ID_KEY%>=030206">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				合同查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" name="cntNum" value="${bean.cntNum}" class="base-input-text"/>
			</td>
			<td class="tdLeft"></td>
			<td class="tdRight">
			</td>
		<tr>
			<td colspan="4" class="tdWhite">
			    <p:button funcId="040601" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
</form>	
	<br>
<form action="" method="post" id="washbackForm" >
	
	<table class="tableList">
			<tr class="collspan-control">
			<th colspan="3" style="fontWeight:bold;color:red;">
				总账接口数据上送截止日期及时间【每月末最后${deadlineDay}个自然日${deadlineTime}时】！
			</th>
		   </tr>
			<tr>
			    <th width="10%"><input type="checkbox" id="checkItem" onclick="checkAll()">选择</th>
				<th width="50%">合同号</th>
				<th width="40%">操作</th>
			</tr>
		<c:forEach items="${cntList}" var="cntItem">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
			    <td><input type="checkbox" name="cntNumList" id="cntNumList" value="${cntItem.cntNum}"> </td>
				<td>${cntItem.cntNum}</td>
				<td>
					<div >
					   <input type="button" value="回冲" class="base-input-button" onclick="washbackOne('${cntItem.cntNum}');"/>
					</div>
				</td>
			</tr>
		</c:forEach>
		
		<c:if test="${empty cntList}">
			<tr>
				<td colspan="3" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
	<table>
		<tr>
		<br/>
		</tr>
		<tr>
			<td colspan="6" style="border-bottom-style: none;border-left-style: none;border-right-style: none; border-top-style: none;">
				<input type="button" value="所勾选的全部回冲" onclick="washbackAll();">
			</td>
		</tr>
		<!-- 结束 -->
	</table>
</form>
<form action="" method="post" id="washbackForm2" >
</form>
<p:page/>
</body>
</html>