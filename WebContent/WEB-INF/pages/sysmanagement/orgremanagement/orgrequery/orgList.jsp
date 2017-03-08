<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构变动信息查询</title>
<script type="text/javascript">
function pageInit()
{   
	
	App.jqueryAutocomplete();
	$(".erp_cascade_select").combobox();
	
	//设置时间插件
	$( "#instDateS" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
 	
	//设置时间插件
	$( "#instDateE" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
}
function resetAll(){
	$(":text").val("");
	$("select").val("");
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}
function doValidate(){
	if(!$.checkDate("instDateS","instDateE")){
		return false;
	}
	return true;
}
function over(obj,batchNo,dutyCode){
	$( "<div>确认该机构已经完成了撤并?</div>" ).dialog({
		resizable: false,
		height:140,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				$( this ).dialog( "close" );
				var data = {};
				data['dutyCode'] =  dutyCode;
				data['batchNo'] =  batchNo;
				App.ajaxSubmit("sysmanagement/orgremanagement/orgrequery/orgOver.do?<%=WebConsts.FUNC_ID_KEY%>=0104040201",{data : data,async:false}, function(data) {
					var flag=data.data;
					if(flag == true){
						App.notySuccess('机构撤并完结成功');
						$(obj).hide();
					}else{
						App.notyWarning("机构撤并完结失败");
					}
				});
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
$(function(){
	if("02"==$("#changeType").val()){
		$("#tr1").show();
	}else{
		$("#tr1").hide();
	}
})
function changeTypeShow(obj){
	if("02"==$(obj).val()){
		$("#tr1").show();
	}else{
		$("#tr1").hide();
	}
}
</script>
</head>
<body>
<p:authFunc funcArray="01040403"/>
<form action="" method="post" id="orgForm">
	<table>
		<tr class="collspan-control">
			<th colspan="4">撤并查询</th>
		</tr>
		<tr>
			<td class="tdLeft" >机构代码</td>
			<td class="tdRight">
				 <input type="text" id="orgCode" name="orgCode" class="base-input-text" maxlength="5" value="${selectInfo.orgCode }"/>
			</td>
			<td class="tdLeft" >机构名称</td>
			<td class="tdRight">
				<input type="text" id="orgName" name="orgName" class="base-input-text" maxlength="5" value="${selectInfo.orgName }"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >记录日期</td>
			<td class="tdRight">
				<input type="text" id="instDateS" name="instDateS" class="base-input-text"  style="width:105px;" value="${selectInfo.instDateS }"/>至
				<input type="text" id="instDateE" name="instDateE" class="base-input-text"  style="width:105px;" value="${selectInfo.instDateE }"/>
			</td>
			<td class="tdLeft" >类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="changeType" name="changeType" onchange="changeTypeShow(this)" class="erp_cascade_select">
						<option value="">--请选择--</option>
						<option value="01" <c:if test="${selectInfo.changeType == '01' }">selected="selected"</c:if>>新增机构</option>
						<option value="02" <c:if test="${selectInfo.changeType == '02' }">selected="selected"</c:if>>撤并机构</option>
					</select>
				</div>
			</td>
		</tr>
		<tr style="display:none" id="tr1">
			<td class="tdLeft" id="cb1" >撤并状态</td>
			<td class="tdRight" id="cb2" colspan="3" >
				<div class="base-input-radio" style="width:300px;" id="typeDiv">
					<label for="flag" onclick="App.radioCheck(this,'typeDiv')" 
							<c:if test="${''==selectInfo.status || null==selectInfo.status}">class="check-label"</c:if>>全部</label>
					<input type="radio" id="flag" name="status" value="" 
							<c:if test="${''==selectInfo.status || null==selectInfo.status}">checked="checked"</c:if>>
					<label for="flag2" onclick="App.radioCheck(this,'typeDiv')" 
							<c:if test="${'00'==selectInfo.status}">class="check-label"</c:if>>未撤并</label>
					<input type="radio" id="flag2" name="status" value="00" 
							<c:if test="${'00'==selectInfo.status}">checked="checked"</c:if>>	
							
					<label for="flag3" onclick="App.radioCheck(this,'typeDiv')" 
							<c:if test="${'01'==selectInfo.status}">class="check-label"</c:if>>撤并中</label>
					<input type="radio" id="flag3" name="status" value="01" 
							<c:if test="${'01'==selectInfo.status}">checked="checked"</c:if>>									
					<label for="flag1" onclick="App.radioCheck(this,'typeDiv')" 
							<c:if test="${'02'==selectInfo.status}">class="check-label"</c:if>>撤并完成</label>
					<input type="radio" id="flag1" name="status" value="02" 
							<c:if test="${'02'==selectInfo.status}">checked="checked"</c:if>>
									
																
				</div> 
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="01040403" value="查询"/> 
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
	<table class="tableList">
		<tr>
			<th>机构代码</th>
			<th>机构名称</th>
			<th>撤并类型</th>
			<th>备注</th>
			<th>记录日期</th>
			<th>状态</th>
		</tr>
		<c:if test="${!empty lists }">
			<c:forEach items="${lists }" var="bean">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td>${bean.orgCode }</td>
					<td>${bean.orgName }</td>
					<td>
						<c:if test="${bean.changeType == '01' }">新增机构</c:if>
						<c:if test="${bean.changeType == '02' }">撤并机构</c:if>
					</td>
					<td>
						<c:if test="${bean.changeType == '01' }">新增</c:if>
						<c:if test="${bean.changeType == '02' }">
							<c:if test="${bean.status == '00' }">
								撤并
							</c:if>
							<c:if test="${bean.status != '00' }">
								撤并后的机构是:${bean.dutyCodeAfter }-${bean.dutyNameAfter }
							</c:if>
						</c:if>
					</td>
					<td>${bean.instDate }</td>
					<td>
					<c:if test="${bean.changeType == '01' }">新增</c:if>
					<c:if test="${bean.changeType != '01' }">
						<c:if test="${bean.status =='00' }">
							未处理
						</c:if>
						<c:if test="${bean.status =='01' }">
							处理中
						</c:if>
						<c:if test="${bean.status =='02' }">
							处理完成
						</c:if>
					</c:if>
						
					</td>
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty lists }">
			<tr>
				<td colspan="6" style="text-align: center;"><span class="red">没有相关记录</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/> 
</body>
</html>