<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="com.forms.prms.tool.ConstantMgr"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta name="decorator" content="dialog">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>监控指标物料维护</title>
<base target="_self">

<script type="text/javascript">

//页面样式初始化
function pageInit(){
	App.jqueryAutocomplete();
	
}

//设置页面关闭时返回值
function getReturnValue(){
	var htmls ="";
	htmls = cloneDiv();
	art.dialog.data('returnValue',htmls);
	art.dialog.close();	   		 
}

function cloneDiv(){
	var html = "";
	$('input[type=checkbox][name!="cglCodeS"]:checked').each(function(){
		var matrCode = $(this).val();
		var matrName = $("#"+matrCode+"_span").attr("title");
		var tagName = $(this).attr("name");
		var seq = $(this).attr("title");
		var cglCode = $("#"+matrCode+"_cgl").val();
		html += "<div id='"+matrCode+"_div' style='border-bottom:1px solid #c5dbf0;border-right:1px solid #c5dbf0;float:left;width:49.5%;height:35px;line-height:35px;'>";
		html +=  "<input type='checkbox' title='"+seq+"' style='display:none;' checked='checked'  value='"+matrCode+"' name='"+tagName+"'/>";
		var showName = "";
		if(matrName.length>20){
			showName = matrName.substring(matrName.length-20);
		}else{
			showName = matrName;
		}
		html+= "<span title='"+matrName+"'>"+cglCode+"-"+matrCode+"-"+showName+"</span>";
		html+="<a style='margin-right:2px; float:right;margin-top:10px;' href='javascript:void(0);' onclick=cancle('"+matrCode+"')><img src='<%=request.getContextPath()%>/common/images/del.gif'/></a>";
		html+= "</div>";
	});
	return html;
}
//查询
function selectForm(){

	var cglCodes="";
	$('input[type=checkbox][name="cglCodeS"]:checked').each(function(){
		var cglCode = $(this).val();
		if(cglCodes==""){
			cglCodes = cglCode;
		}else{
			cglCodes =cglCodes+","+cglCode;
		}
	});
	
	var matrCode = $("#matrCode3").val();
	var matrName = $("#matrName3").val();
	//下面注释的是在页面上循环查询
	if(matrCode !="" ){
		$("input[type=checkbox][name='noUpMatrs']").each(function(){
			var matrCode2 = $(this).val(); 
			if(matrCode == matrCode2){
				$("#"+matrCode2+"_div").show();
			}else{
				$("#"+matrCode2+"_div").hide();
			}
		});
	}
	if (cglCodes!="" && matrCode=="" && matrName=="") {
		$("input[type=checkbox][name='noUpMatrs']").each(function(){
			var id = $(this).val(); 
			var cglCode2=$("#"+id+"_cgl").val();
			if(cglCodes.indexOf(cglCode2)>=0){
				$("#"+id+"_div").show();
			}else{
				$("#"+id+"_div").hide();
			}
		});
	}
	if (cglCodes!="" && matrName!="" && matrCode=="") {
		$("input[type=checkbox][name='noUpMatrs']").each(function(){
			var id = $(this).val(); 
			var cglCode2=$("#"+id+"_cgl").val();
			var matrName2=$("#"+id+"_span").attr("title");
			if(cglCodes.indexOf(cglCode2)>=0 && matrName2.indexOf(matrName)>=0){
				$("#"+id+"_div").show();
			}else{
				$("#"+id+"_div").hide();
			}
		});
	}
	if(matrCode == "" && matrName!="" && cglCodes == ""){
		$("input[type=checkbox][name='noUpMatrs']").each(function(){
			var id = $(this).val();
			var matrName2 = $("#"+id+"_span").attr("title");
			if(matrName2.indexOf(matrName)>=0){
				$("#"+id+"_div").show();
			}else{
				$("#"+id+"_div").hide();
			}
		});
	}
	if(matrCode == "" && matrName == "" && cglCodes==""){
		$("input[type=checkbox][name='noUpMatrs']").each(function(){
			var id = $(this).val();
			$("#"+id+"_div").show();
		});
	}
}
function resetAll(){
	$("#matrCode3").val("");
	$("#matrName3").val("");
	$("#cglCode").val("");
	$('input[type=checkbox][name="cglCodeS"]:checked').each(function(){
		this.checked = false;
	});
}
//全选责任中心
function selectAllDuty(){
	$('input[type=checkbox][name = noUpMatrs]').each(function(){
		if($(this).is(":visible")){
			this.checked = true;
		}
		
	});
}
//反选责任中心
function selectOtherDuty(){
	
	$('input[type=checkbox][name=noUpMatrs]').each(function(){
		if($(this).is(":visible")){
			if(this.checked == false){
				this.checked = true;
			}else{
				this.checked = false;
			}
			
		}
	});
}
</script>
</head>

<body>
<form action="<%=request.getContextPath()%>/sysmanagement/montindex/addMatrs.do?<%=WebConsts.FUNC_ID_KEY %>=0812020102" method="post"  id="matrForm">
<input type="hidden" value="${montBean.selectNoUpMatrs }" name="selectNoUpMatrs" id="selectNoUpMatrs"/>	
<input type="hidden" value="${montBean.montType }" name="montType" id="montType"/>
	<table>
		<tr >
			<th colspan="4">监控指标物料维护</th>
		</tr>
		<tr  >
			<td  class="tdLeft">物料编码</td>
			<td class="tdRight" >
				<input type="text"  name="matrCode" value="${montBean.matrCode }" id="matrCode3" class="base-input-text" />
			</td>
			<td class="tdLeft">物料名称</td>
			<td class="tdRight">
				<input type="text" name="matrName" value="${montBean.matrName }" id="matrName3" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">核算码</td>
			<td class="tdRight" colspan="3">
					<table>
						<c:forEach items="${cglCodeList}" var="cglCodeBean" varStatus="status">
							<c:if test="${(status.index)%8==0}">
								<tr>
							</c:if>
							<td style="border: 0">
								<label><input type="checkbox" name="cglCodeS" value='${cglCodeBean.cglCode}'>${cglCodeBean.cglCode}</label>
							</td>
							<c:if test="${(status.index+1)%8==0}">
								</tr>
							</c:if>
						</c:forEach>
					</table>
			</td>
		</tr>
		<tr>
			<td colspan="4">
				<input type="button"  value="查询"   onclick="selectForm();" />
				<input type="button"  value="选择"  onclick="getReturnValue();" />	
				<input type="button"  value="重置"	onclick="resetAll()"/>
			</td>
		</tr>
	</table>
		 <table>
		 	<tr >
		 		<th>
		 			物料集合<span style="color:red">
						(
						<a style="color:red" href="javascript:void(0)" id="selectAllId" name="1" onclick="selectAllDuty()">全选</a>
						<a style="color:red" href="javascript:void(0)" onclick="selectOtherDuty()">反选</a>
						)
					</span>
		 		</th>
		 	</tr>
		 	<tr>
		 		<td>
		 			 <c:forEach items="${matrsList}" var="matrs" varStatus="status">
						<div id='${matrs.matrCode }_div' style='border-bottom:1px solid #c5dbf0;text-align: left;border-right:1px solid #c5dbf0;float:left;width:33.1%;height:35px;line-height:35px;'>
							<!-- 
								<input type='checkbox' style='margin-left: 0px; display: none;' value='${matr.matrCode }' name='matrs'/>
							 -->
							 <label><input title="${matrs.seq }"  name="noUpMatrs" type="checkbox" value="${matrs.matrCode }" <c:if test="${matrs.isChecked eq '1' }">checked</c:if> />
							 <input type="hidden" id="${matrs.matrCode}_cgl" value="${matrs.cglCode}">
							<span title='${matrs.matrName }' id="${matrs.matrCode }_span"  >
								<forms:StringTag length="18"   value="${matrs.matrName }"/>
							</span></label>
						</div>
					 </c:forEach>
		 		</td>
		 	</tr>
		 </table>
</form>
<p:page/>
</body>
</html>