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
<title>撤并经办列表</title>
<script type="text/javascript">
function pageInit()
{   
	//设置时间插件
	$( "#modiYyyymmS" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
 	
	//设置时间插件
	$( "#modiYyyymmE" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd"
	});
// 	var tableCombine=new TableCombine();
// 	tableCombine.rowspanTable("listTab1", 0, null, 0, 1, null, null);
// 	table1("aprv_td_h_","aprv_td_");
}
function resetAll(){
	$(":text").val("");
	$("select").val("");
	$("#typeDiv").find("label").eq("").click();
	$("#statusDiv").find("label").eq("").click();
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
}
//修改编辑
function edit(id,seq){
	var menuTag = $("#menuTag").val();
	var url='<%=request.getContextPath()%>/sysmanagement/orgremanagement/dutyMerge/preEdit.do?<%=WebConsts.FUNC_ID_KEY%>=0104110302';
	url += '&id='+id+'&seq='+seq+'&menuTag='+menuTag;
	var form=$("#orgFormTemp")[0];
	form.action=url;
	App.submit(form);
}
//明细
function detail(batchNo,seq){
	var menuTag = $("#menuTag").val();
	var url='<%=request.getContextPath()%>/sysmanagement/orgremanagement/dutyMerge/detail.do?<%=WebConsts.FUNC_ID_KEY%>=0104110304';
	url += '&batchNo='+batchNo+'&seq='+seq+'&menuTag='+menuTag;
	var form=$("#orgFormTemp")[0];
	form.action=url;
	App.submit(form);
}
//删除
function del(id,seq){
	var url='<%=request.getContextPath()%>/sysmanagement/orgremanagement/dutyMerge/del.do?<%=WebConsts.FUNC_ID_KEY%>=0104110301';
	url += '&id='+id+'&seq='+seq;
	
	$( "<div>确认要删除该信息?</div>" ).dialog({
		resizable: false,
		height:140,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				$("#orgFormTemp").attr("action",url);
				$("#orgFormTemp").submit();
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}
//审批页面
function aprv(id,seq){
	var menuTag = $("#menuTag").val();
	var url='<%=request.getContextPath()%>/sysmanagement/orgremanagement/dutyMerge/preAprv.do?<%=WebConsts.FUNC_ID_KEY%>=0104110209';
	url += '&id='+id+'&seq='+seq+'&menuTag='+menuTag;
	var form=$("#orgFormTemp")[0];
	form.action=url;
	App.submit(form);
}
function doValidate(){
	if(!$.checkDate("modiYyyymmS","modiYyyymmE")){
		return false;
	}
	return true;
}

function table1(inputId,tdId){
	var str1="";
	var table2 = $("#listTab1");
	var trs2 = table2[0].rows;
	for(var i=1;i<=trs2.length-1;i++){
		var flag1_1 = true;
		if(str1 == $("#"+inputId+i).val() ){
			//当着两者相同时 就不让他重复循环
			continue;
		}
		str1=$("#"+inputId+i).val();
		for(var j=i+1;j<=trs2.length;j++){
			if(flag1_1 == true){
				//当为true时才继续循环下去
				var val1 = $("#"+inputId+i).val();
				var val2 = $("#"+inputId+j).val();
				if(val1==val2){
					var k = $("#"+tdId+i).attr("rowspan");
					$("#"+tdId+i).attr("rowspan",parseInt(k)+1);
					$("#"+tdId+j).remove();
				}else{
					//当有一个不等时不让他继续循环
					flag1_1 = false;
				}
			}
			
		}
	}
}
</script>
</head>
<body>
<p:authFunc funcArray="01041101,01041102,01041103,0104110101"/>
<form action="" method="post" id="orgFormTemp"><p:token/></form>
<form action="" method="post" id="orgForm">
<input type="hidden" id="menuTag" value="${searchInfo.menuTag }"/>
	<table>
		<tr class="collspan-control">
			<th colspan="4">查询</th>
		</tr>
<!-- 		<tr> -->
<!-- 			<td class="tdLeft" >撤并日期区间</td> -->
<!-- 			<td class="tdRight"> -->
<%-- 				<input id="modiYyyymmS" name="modiYyyymmS" class="base-input-text"  style="width:105px;" value="${searchInfo.modiYyyymmS }"/>至 --%>
<%-- 				<input id="modiYyyymmE" name="modiYyyymmE" class="base-input-text"  style="width:105px;" value="${searchInfo.modiYyyymmE }"/> --%>
<!-- 			</td> -->
			
<!-- 		</tr> -->
		<tr>
			<td class="tdLeft" >撤并前代码</td>
			<td class="tdRight">
				<input id="codeBef" name="codeBef" class="base-input-text" maxlength="5" value="${searchInfo.codeBef }"/>
			</td>
			<td class="tdLeft" >撤并后代码</td>
			<td class="tdRight">
				<input id="codeCur" name="codeCur" class="base-input-text" maxlength="5" value="${searchInfo.codeCur }"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" >撤并类型</td>
			<td class="tdRight">
				<div class="base-input-radio" id="typeDiv">
					<label for="flag" onclick="App.radioCheck(this,'typeDiv')" 
							<c:if test="${''==searchInfo.changeType || null==searchInfo.changeType}">class="check-label"</c:if>>全部</label>
					<input type="radio" id="flag" name="changeType" value="" 
							<c:if test="${''==searchInfo.changeType || null==searchInfo.changeType}">checked="checked"</c:if>>
							
					<label for="flag1" onclick="App.radioCheck(this,'typeDiv')" 
							<c:if test="${'01'==searchInfo.changeType}">class="check-label"</c:if>>责任中心撤并</label>
					<input type="radio" id="flag1" name="changeType" value="01" 
							<c:if test="${'01'==searchInfo.changeType}">checked="checked"</c:if>>
							
					<label for="flag2" onclick="App.radioCheck(this,'typeDiv')" 
							<c:if test="${'02'==searchInfo.changeType}">class="check-label"</c:if>>机构撤并</label>
					<input type="radio" id="flag2" name="changeType" value="02" 
							<c:if test="${'02'==searchInfo.changeType}">checked="checked"</c:if>>
							
				</div>
			</td>
			<td class="tdLeft" >状态</td>
			<td class="tdRight">
				<div class="base-input-radio" id="statusDiv" style="width: 400px;">
					<label for="flag4" onclick="App.radioCheck(this,'statusDiv')" 
							<c:if test="${''==searchInfo.status || null==searchInfo.status}">class="check-label"</c:if>>全部</label>
					<input type="radio" id="flag4" name="status" value="" 
							<c:if test="${''==searchInfo.status || null==searchInfo.status}">checked="checked"</c:if>>
							
					<label for="flag5" onclick="App.radioCheck(this,'statusDiv')" 
							<c:if test="${'01'==searchInfo.status}">class="check-label"</c:if>>待复核</label>
					<input type="radio" id="flag5" name="status" value="01" 
							<c:if test="${'01'==searchInfo.status}">checked="checked"</c:if>>
							
					<label for="flag6" onclick="App.radioCheck(this,'statusDiv')" 
							<c:if test="${'02'==searchInfo.status}">class="check-label"</c:if>>复核通过</label>
					<input type="radio" id="flag6" name="status" value="02" 
							<c:if test="${'02'==searchInfo.status}">checked="checked"</c:if>>
							
							
					<label for="flag7" onclick="App.radioCheck(this,'statusDiv')" 
							<c:if test="${'03'==searchInfo.status}">class="check-label"</c:if>>复核不通过</label>
					<input type="radio" id="flag7" name="status" value="03" 
							<c:if test="${'03'==searchInfo.status}">checked="checked"</c:if>>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">			
				<p:button funcId="01041103" value="查询"/> 
				<input type="button" value="重置" onclick="resetAll();">
			</td>
		</tr>
	</table>
	<br/>
	<table class="tableList" id="listTab1">
		<tr>
			<th>撤并类型</th>
			<th>撤并前</th>
			<th>撤并后</th>
			<th>撤并状态</th>
		</tr>
		<c:if test="${!empty list }">
			<c:forEach items="${list }" var="bean" varStatus="i">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td>
						<c:if test="${bean.changeType == '01' }">
							责任中心撤并						
						</c:if>
						<c:if test="${bean.changeType == '02' }">
							机构撤并						
						</c:if>
					</td>
					<td>${bean.codeBef }-${bean.nameBef}</td>
					<td>${bean.codeCur }-${bean.nameCur}</td>
					<td>
						<c:if test="${bean.status == '00' }">
							待处理 				
						</c:if>
						<c:if test="${bean.status == '01' }">
							待复核					
						</c:if>
						<c:if test="${bean.status == '02' }">
							复核通过						
						</c:if>
						<c:if test="${bean.status == '03' }">
							复核退回					
						</c:if>
						<c:if test="${bean.status == '04' }">
							复核失败					
						</c:if>
						<c:if test="${bean.status == '05' }">
							复核中					
						</c:if>
					</td>
<!-- 					<td> -->
<!-- 						<div class="detail" > -->
<%-- 							<a href="#" onclick="detail('${bean.batchNo }','${bean.seq }');" title="明细"></a> --%>
<!-- 						</div> -->
<!-- 					</td> -->
				</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list }">
			<tr>
				<td colspan="4" style="text-align: center;"><span class="red">没有相关记录</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/> 
</body>
</html>