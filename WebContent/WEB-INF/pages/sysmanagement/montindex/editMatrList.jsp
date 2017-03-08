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
	 var data={};
		var html = "";
		var count="";
		$('input[type=checkbox][name!="cglCodeS"]:checked').each(function(){
			count=parseInt(count+1);
			var matrCode = $(this).val();
			var matrName = $("#"+matrCode+"_span").attr("title");
			var tagName = $(this).attr("name");
			var cglCode = $("#"+matrCode+"_cgl").val();
			var isValid = $("#"+matrCode+"_isValid").val();
			var seq = $(this).attr("title");
			html += "<div id='"+matrCode+"_div' style='border-bottom:1px solid #c5dbf0;border-right:1px solid #c5dbf0;float:left;width:49.5%;height:35px;line-height:35px;'>";
			html +=  "<input type='checkbox' title='"+seq+"' style='display:none;' checked='checked'  value='"+matrCode+"' name='"+tagName+"'/>";
			html +=  "<input type='checkbox'  style='display:none;' checked='checked'  value='"+isValid+"' name='isValids'/>";
			var showName = "";
			if(matrName.length>20){
				showName = matrName.substring(matrName.length-20);
			}else{
				showName = matrName;
			}
			if(isValid == '1'){
				html+= "<span title='"+matrName+"'  >"+cglCode+"-"+matrCode+"-"+showName+"</span>";
				
			}else{
				html+= "<span title='"+matrName+"' style='text-decoration: line-through;' >"+cglCode+"-"+matrCode+"-"+showName+"</span>";
			}
			
			if($(this).attr("class") == "haveUpDiv"){
				html+="<a style='margin-right:2px; float:right;margin-top:10px;' href='javascript:void(0);' onclick=cancleHaveUp('"+matrCode+"')>";
			}else{
				html+="<a style='margin-right:2px; float:right;margin-top:10px;' href='javascript:void(0);' onclick=cancle('"+matrCode+"')>";
			}
			html+="<img src='<%=request.getContextPath()%>/common/images/del.gif'/></a>";
			html+= "</div>";
		});
		data["count"] = count;
		data["htmls"] = html;
	art.dialog.data('returnValue',data);
	art.dialog.close();	 
}

function cloneDiv(){
   
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
//检查监控指标的物料是否存在于合同设备表中
function checkMontCode(matrCode,obj){
	var isChek = $(obj).prev().prev().prev().attr("checked");
	if(isChek == false || isChek !="checked"){
		$(obj).prev().prev().prev().attr("checked",true);
	}else{
		var data = {};
		data['montCode'] =  "${bean.montCode}";
		data['matrCode'] =  matrCode;
		
		var orgType = $("#orgType").val();
		var url = "";
		if(orgType =="01"){
			 url = "sysmanagement/montindex/checkMontCode.do?VISIT_FUNC_ID=0812020112";
		}else{
			 url = "sysmanagement/montindex/checkMontCode.do?VISIT_FUNC_ID=0812020210";
		}
		
		App.ajaxSubmit(url,{data : data,async:false}, function(data) {
			flag = data.data.flag;
			msg = data.data.msg;	
		
		});
		if (!flag){
			App.notyError(msg);
			return false;
		}else{
			$(obj).prev().prev().prev().attr("checked",false);
		}
	}
	
}
</script>
</head>

<body>
<form action="<%=request.getContextPath()%>/sysmanagement/mont/matrList.do?<%=WebConsts.FUNC_ID_KEY %>=011102" method="post"  id="matrForm">	
<input type="hidden" value="${bean.orgType }" id="orgType"/>
	<table>
		<tr class="collspan-control">
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
			<span style="color:red;font-weight: 500">(
						<a style="color:red" href="javascript:void(0)" id="selectAllId" name="1" onclick="selectAllDuty()">全选物料</a>
						<a style="color:red" href="javascript:void(0)" onclick="selectOtherDuty()">反选物料	</a>
						)</span>
				<input type="button"  value="查询"   onclick="selectForm();" />
				<input type="button"  value="选择"  onclick="getReturnValue();" />	
				<input type="button"  value="重置"	onclick="resetAll()"/>							
			</td>
		</tr>
	</table>
		 <table>
		 	<tr class="collspan-control">
		 		<th>
		 			未维护物料
		 			
		 		</th>
		 	</tr>
		 	<tr>
		 		<td>
		 			 <c:forEach items="${noUpMatrs}" var="noUpMatr" varStatus="status">
						<div id='${noUpMatr.matrCode }_div' style='border-bottom:1px solid #c5dbf0;text-align: left;border-right:1px solid #c5dbf0;float:left;width:33.1%;height:35px;line-height:35px;'>
							 <label>
							 <input  name="noUpMatrs" type="checkbox" title="${noUpMatr.seq }"  value="${noUpMatr.matrCode }" <c:if test="${noUpMatr.isChecked eq '1' }">checked</c:if> />
							<input type="hidden" id="${noUpMatr.matrCode}_cgl" value="${noUpMatr.cglCode}">	
							<input type="hidden" id="${noUpMatr.matrCode}_isValid" value="1">							
							<span title='${noUpMatr.matrName }' id="${noUpMatr.matrCode }_span"  >
								<forms:StringTag length="18"   value="${noUpMatr.matrName }"/>
							</span>
							</label>
						</div>
					 </c:forEach>
		 		</td>
		 	</tr>
		 </table>
		 <table>
		 	<tr class="collspan-control">
		 		<th>已维护物料</th>
		 	</tr>
		 	<tr>
		 		<td>
		 			 <c:forEach items="${haveUpMatrs}" var="haveUpMatr" varStatus="status">
						<div  id='${haveUpMatr.matrCode }_div' style='border-bottom:1px solid #c5dbf0;text-align: left;border-right:1px solid #c5dbf0;float:left;width:33.1%;height:35px;line-height:35px;'>
							<input disabled="disabled"   class="haveUpDiv" style="margin-right:2px;margin-top:10px; float:left;"   name="matrs" type="checkbox" <c:if test="${haveUpMatr.isChecked eq '1' }">checked</c:if> value="${haveUpMatr.matrCode }"/>
							<input type="hidden" id="${haveUpMatr.matrCode}_cgl" value="${haveUpMatr.cglCode}"/>
							<input type="hidden" id="${haveUpMatr.matrCode}_isValid" value="${haveUpMatr.isValid}"/>
							<span   onclick="checkMontCode('${haveUpMatr.matrCode}',this)"  title='${haveUpMatr.matrName }' style='cursor:pointer; text-align: left ;<c:if test="${haveUpMatr.isValid ne '1' }">text-decoration: line-through;" ></c:if>'  id="${haveUpMatr.matrCode }_span">							
								<forms:StringTag length="18"   value="${haveUpMatr.matrName }"/>
							</span>
						</div>
					 </c:forEach>
		 		</td>
		 	</tr>
		 </table>
</form>
<p:page/>
 
</body>
</html>