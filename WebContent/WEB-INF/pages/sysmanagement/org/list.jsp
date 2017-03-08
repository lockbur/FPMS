<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jstl/core_rt" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms"%>
<%@ taglib uri="/platform-tags" prefix="p"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>机构列表</title>
<script type="text/javascript">


function resetAll() {
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("#deptId").val("");
	$("#fstdeptIdDiv,#seddeptIdDiv").css("display","none");
	$("#userTypeDiv").find("label").eq(0).click();
	$("#isDeletedDiv").find("label").eq(0).click();
	$("#lockStatueDiv").find("label").eq(0).click();
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
// 	org2CodeTree.afterDynamicBack();
//	org2CodeTree.tagReset();
}
 function org1Change(){
	var selectValue = org1CodeTree.getSelectOrgList();
	
	if(selectValue != null )
	{
		org2CodeTree.getDynamicManager().setRootNodeId(selectValue[0].id);
		org2CodeTree.getDynamicManager().setDialogFlag(false);
		//org2CodeTree.getDynamicManager().setLeafLevel('1');
		org2CodeTree.dynamicInitTagInit();
	}
	
} 
//动态得到机构和ou集合
function org2Change(){
	var selectValue = org2CodeTree.getSelectOrgList();
	if(selectValue != null )
	{
		var ouList = {};
		var orgList = {};
		var data = {};
		data['org2Code'] =  selectValue[0].id;
		var url = "sysmanagement/orgController/getOuList.do?VISIT_FUNC_ID=01040303";
		App.ajaxSubmit(url,{data : data,async:false}, function(data) {
				ouList = data.ouList;
				orgList=data.orgList;
		});
		$("#orgCode").empty();
		$("#orgCode").parent().find("input").val("");
		$("#orgCode").append("<option value='' selected='selected'>-请选择-</option>");
		$("#orgCode").combobox();
		if(orgList){
			for(var i=0;i<orgList.length;i++){
				$("#orgCode").append("<option value="+orgList[i].orgCode+" > " + orgList[i].orgName +"</option>");
			}
		}
		$("#ouCode").empty();
		$("#ouCode").parent().find("input").val("");
		$("#ouCode").append("<option value=''>-请选择-</option>");
		$("#ouCode").combobox();
		if(ouList){
			for(var i=0;i<ouList.length;i++){
				$("#ouCode").append("<option value="+ouList[i].ouCode+" > " + ouList[i].ouName +"</option>");
			}
		}
	}
}
function orgChange(){
	var orgCode=$("#orgCode").val();
	if(orgCode){
		var ouList = {};
		var data = {};
		data['orgCode'] = orgCode;
		var url = "sysmanagement/orgController/getOuList.do?VISIT_FUNC_ID=01040303";
		App.ajaxSubmit(url,{data : data,async:false}, function(data) {
				ouList = data.ouList;
				orgList=data.orgList;
		});
		$("#ouCode").empty();
		$("#ouCode").parent().find("input").val("");
		$("#ouCode").append("<option value=''>-请选择-</option>");
		if(ouList){
			for(var i=0;i<ouList.length;i++){
				$("#ouCode").append("<option value="+ouList[i].ouCode+" > " + ouList[i].ouName +"</option>");
			}
		}
	}
}
function ouChange(){
// 	var selectValue = ouCodeTree.getSelectOrgList();
	
// 	if(selectValue != null )
// 	{
// 		dutyCodeTree.getDynamicManager().setRootNodeId(selectValue[0].id);
// 		dutyCodeTree.getDynamicManager().setLeafLevel('2');
// 		dutyCodeTree.dynamicInitTagInit();
// 	}
	
}
//扫描岗修改
function scanPosition(dutyCode,treeJsVar){
	treeJsVar = eval(treeJsVar);
	var scanPosition = treeJsVar.getSelectOrgList()[0].id;
	var data = {};
	data['dutyCode'] =  dutyCode;
	data['scanPosition'] =  scanPosition;
	App.ajaxSubmit("sysmanagement/orgController/changeScanPosition.do?<%=WebConsts.FUNC_ID_KEY%>=01040302",{data : data,async:false}, function(data) {
		var flag=data.data;
		if(flag == true){
			App.notySuccess('扫描岗修改成功');
		}else{
			App.notyWarning("扫描岗修改失败");
		}
	});
}
function detail(dutyCode){
	$("#hiddenDutyCode").val(dutyCode);
	$("#detailForm").attr("action","<%=request.getContextPath()%>/sysmanagement/orgController/detail.do?<%=WebConsts.FUNC_ID_KEY%>=01040301");
	$("#detailForm").submit();
	}
function pageInit(){
	App.jqueryAutocomplete();
	$("#ouCode").combobox();
	$("#orgCode").combobox();
}	

	//全选
function checkAll(){
	if($("#checkItem").is(":checked")){
		$("input[name='dutyCodes']").prop('checked','true');
	}else{
		$("input[name='dutyCodes']").removeAttr("checked");
	}
}
function lock(){
	if($("input[name='dutyCodes']:checked").size() < 1){
		App.notyError("请至少锁定一个责任中心！");
		return false;
	}
	$( "<div>确认锁定选的的责任中心?</div>" ).dialog({
		resizable: false,
		height:150,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				var form = $("#orgForm")[0];
				form.action="<%=request.getContextPath()%>/sysmanagement/orgController/lock.do?<%=WebConsts.FUNC_ID_KEY%>=01040304";
				App.submit(form);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
}
function openLock(){
	if($("input[name='dutyCodes']:checked").size() < 1){
		App.notyError("请至少解锁一个责任中心！");
		return false;
	}
	$( "<div>确认解锁选的的责任中心?</div>" ).dialog({
		resizable: false,
		height:150,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				var form = $("#orgForm")[0];
				form.action="<%=request.getContextPath()%>/sysmanagement/orgController/openLock.do?<%=WebConsts.FUNC_ID_KEY%>=01040305";
				App.submit(form);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
	
}
</script>
</head>

<body>
	<p:authFunc funcArray="010403,010404,01040301,01040302" />
	<form action="" id="detailForm" method="post">
		<input type="hidden" name="dutyCode" id="hiddenDutyCode">
	</form>
	<form method="post" id="orgForm" action="">
		<table>
			<tr class="collspan-control">
				<th colspan="4">机构查询</th>
			</tr>
			<tr>
				<td class="tdLeft">一级分行代码</td>
				<td class="tdRight">
					<input type="text" id="org1Code" name="org1Code" value="${searchBean.org1Code }" class="base-input-text"/>
				</td>
				<td class="tdLeft">一级分行名称</td>
				<td class="tdRight">
					<input type="text" id="org1Name" name="org1Name" value="${searchBean.org1Name }" class="base-input-text"/>
				</td>
			</tr>
			<tr>
				<td class="tdLeft">二级分行代码</td>
				<td class="tdRight">
					<input type="text" id="org2Code" name="org2Code" value="${searchBean.org2Code }" class="base-input-text"/>
				</td>
				<td class="tdLeft">二级分行名称</td>
				<td class="tdRight">
					<input type="text" id="org2Name" name="org2Name" value="${searchBean.org2Name }" class="base-input-text"/>
				</td>
			</tr>
			<%-- <tr>
				<td class="tdLeft">机构</td>
				<td class="tdRight">
					<div class="ui-widget">
						<select id="orgCode" name="orgCode"   class="erp_cascade_select" onchange="orgChange()">
							<option value="">-请选择-</option>
							<c:forEach items="${orgList}" var="org">
								<option value="${org.orgCode}" <c:if test="${searchBean.orgCode==org.orgCode}">selected="selected"</c:if>>${org.orgName}</option>
							</c:forEach>
						</select>
					</div>	
				</td>
				<td class="tdLeft">OU</td>
				<td class="tdRight">
						<div class="ui-widget">
						<select id="ouCode" name="ouCode"   class="erp_cascade_select">
							<option value="">-请选择-</option>
							<c:forEach items="${ouList}" var="ou">
								<option value="${ou.ouCode}" <c:if test="${searchBean.ouCode==ou.ouCode}">selected="selected"</c:if>>${ou.ouName}</option>
							</c:forEach>
						</select>
					</div>	
				</td>
				
			</tr> --%>
			<tr>
				<td class="tdLeft">责任中心代码</td>
				<td class="tdRight">
						<input type="text" name="dutyCode"  value="${searchBean.dutyCode}" class="base-input-text"/>
				</td>
				<td class="tdLeft">责任中心名称</td>
				<td class="tdRight">
					<input type="text" name="dutyName"  value="${searchBean.dutyName}" class="base-input-text"/>
				</td>
			</tr>
			<tr>
				
				<td class="tdLeft">责任中心锁定状态</td>
				<td class="tdRight">
					<div class="base-input-radio" id="lockStatueDiv">
				 	<label for="lockStatue" onclick="App.radioCheck(this,'lockStatueDiv')"  <c:if test="${'0'eq searchBean.isLocked}">class="check-label"</c:if>>全部</label><input type="radio" id="lockStatue" name="isLocked" value="0" <c:if test="${'0' eq searchBean.isLocked or searchBean.isLocked==null}">checked="checked"</c:if>>
					<label for="lockStatue1" onclick="App.radioCheck(this,'lockStatueDiv')" <c:if test="${'Y'eq searchBean.isLocked}">class="check-label"</c:if>>锁定</label><input type="radio" id="lockStatue1" name="isLocked" value="Y" <c:if test="${'Y'eq searchBean.isLocked}">checked="checked"</c:if>>
					<label for="lockStatue2" onclick="App.radioCheck(this,'lockStatueDiv')" <c:if test="${'N'eq searchBean.isLocked}">class="check-label"</c:if>>未锁定</label><input type="radio" id="lockStatue2" name="isLocked" value="N" <c:if test="${'N'eq searchBean.isLocked}">checked="checked"</c:if>>
				</div>
				
				</td> 
				<td class="tdLeft"></td>
				<td class="tdRight">
				</td>
			</tr>
			<tr>
				<td colspan="4" class="tdWhite"><p:button funcId="010403" value="查找" /> <input
					type="button" value="重置" onclick="resetAll();"></td>
			</tr>
		</table>
		<br />
		<table class="tableList" id="listTab">
			<tr>
				<th width="2%"><input type="checkbox" id="checkItem" onclick="checkAll()"></th>
				<th width="20%">责任中心</th>
				<th width="10%">锁定状态</th>
				<th width="18%">OU</th>
				<th width="18%">机构</th>
				<th width="18%">二级分行</th>
				<th width="18%">一级分行</th>
				<th width="8%">操作</th>
			</tr>
			<c:forEach items="${list}" var="org" varStatus="st">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');"
					onmouseout="setTrBgClass(this, 'trOther');">
					<td><input type="checkbox" name="dutyCodes" value="${org.dutyCode}"></td>		
					<td title="${org.dutyCode }">${org.dutyName }</td>
					 <td >
						<c:if test="${org.isLocked eq 'Y'}">
							锁定
						</c:if>
						<c:if test="${org.isLocked eq 'N' or empty org.isLocked }">
							未锁定
						</c:if>
					</td> 
					<td title="${org.ouCode }">${org.ouName }</td>
					<td title="${org.orgCode }">${org.orgName}</td>
					<td title="${org.org2Code}">${org.org2Name}</td>
					<td title="${org.org1Code}">${org.org1Name}</td>
					<td><input type="button" value="明细"
						onclick="detail('${org.dutyCode}');" /></td>
				</tr>
			</c:forEach>
			<c:if test="${empty list}">
				<tr>
					<td colspan="8" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
			</c:if>
		</table>
	</form>
	
	<p:page />
	<br><br><br>
	<input type="button" value="锁定"  onclick="lock()">
	<input type="button" value="解锁" onclick="openLock()">
</body>
</html>