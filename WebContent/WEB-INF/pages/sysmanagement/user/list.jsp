<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>用户列表</title>
<script type="text/javascript">
function doValidate() {
	
	//提交前调用
	if(!App.valid("#userForm")){return;}
// 	Utils.checkDeptCascadeSelect("deptId","erp_cascade_select");
	return true;
}

function pageInit() {
	
	App.jqueryAutocomplete();

	$( "#userForm .erp_cascade_select" ).combobox();
//     Utils.initDeptCascadeSelect("deptId","erp_cascade_select");
}

function modify(userId) 
{
	var form = document.forms[0];
	form.userId.value = userId;
	form.action = '<%=request.getContextPath()%>/sysmanagement/user/preEdit.do?<%=WebConsts.FUNC_ID_KEY%>=010304';
	App.submit(form);
}

function resetAll() {
	$("select").val("");
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
		}
		
	});
	$(":text").val("");
	$(":hidden").val("");
	$(":selected").prop("selected",false);
	
	
	$("#haveRoleDiv").find("label").eq(0).click();
	$("#isSuperDiv").find("label").eq(0).click();
	$("#isBuyerDiv").find("label").eq(0).click();
}

function selectRole(){
	$.dialog.open(
			'<%=request.getContextPath()%>/sysmanagement/user/selectRole.do?<%=WebConsts.FUNC_ID_KEY %>=01030405',
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"角色选择",
			    id:"dialogCutPage",
				close: function(){
					var object = art.dialog.data('object'); 
					if(object){
						$("#roleId").val(object.roleId);
						$("#roleName").val(object.roleName);
					}
					}		
			}
		 );
}
function exportData(){
		var isPass=true;
		var data = {};
		data['userId'] =  $("#userId").val();
		data['userName'] = $("#userName").val();
		data['dutyCode'] = $("#dutyCode").val();
		data['dutyName'] =  $("#dutyName").val();
		data['orgCode'] = $("#orgCode").val();
		data['orgName'] =  $("#orgName").val();
		data['ouCode'] =  $("#ouCode").val();
		data['roleId'] =  $("#roleId").val();
		data['haveRole'] = $("input[name='haveRole']:checked").val();
		data['isSuper'] = $("input[name='isSuper']:checked").val();
		data['isBuyer'] = $("input[name='isBuyer']:checked").val();
		App.ajaxSubmit("sysmanagement/user/userExport.do?<%=WebConsts.FUNC_ID_KEY %>=01030206",
				{data:data,async : false},
				function(data){
					flag = data.pass;
					if(flag){
						$("<div>Excel已成功提交后台处理<br\>是否进入下载页面？</div>").dialog({
							resizable: false,
							width: 290,
							height:'auto',
							modal: true,
							dialogClass: 'dClass',
							title: "跳转至下载页面",
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
												form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101&funcId=010302';
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
						isPass =  true;
					}
					else
					{
						App.notyError("添加下载失败，可能是因为表里没有该条数据，请检查后重试!");
						isPass =  false;
					}
				});
		return isPass;
}
</script>
</head>

<body>
<p:authFunc funcArray="010301,010302,010304"/>
<form method="post" id="userForm" action="<%=request.getContextPath()%>/sysmanagement/user/list.do?<%=WebConsts.FUNC_ID_KEY%>=010302">
	<table>
		<tr class="collspan-control">
			<th colspan="4">用户查询</th>
		</tr>	
		<tr>
			<td class="tdLeft">员工号</td>
			<td class="tdRight">
				<input type="text" id="userId" name="userId" value="${selectInfo.userId}" class="base-input-text"/>
			</td>
			<td class="tdLeft">员工名称</td>
			<td class="tdRight">
				<input type="text" id="userName" name="userName" value="${selectInfo.userName}" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">所属OU代码	</td>
			<td class="tdRight" >
				<input type="text" id="ouCode" name="ouCode" value="${selectInfo.ouCode}" class="base-input-text"/>
			</td>
			<td class="tdLeft"></td>
			<td class="tdRight" >
			</td>
		</tr>
		<tr>
			<td class="tdLeft">所属责任中心代码</td>
			<td class="tdRight" >
				<input type="text" id="dutyCode" name="dutyCode" value="${selectInfo.dutyCode}" class="base-input-text"/>
			</td>
			<td class="tdLeft">所属责任中心名称</td>
			<td class="tdRight">
				<input type="text" id="dutyName"  name="dutyName" value="${selectInfo.dutyName}" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">所属机构代码</td>
			<td class="tdRight" >
				<input type="text" id="orgCode" name="orgCode" value="${selectInfo.orgCode}" class="base-input-text"/>
			</td>
			<td class="tdLeft">所属机构名称</td>
			<td class="tdRight">
				<input type="text" id="orgName" name="orgName" value="${selectInfo.orgName}" class="base-input-text"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">是否省行超级管理员</td>
			<td class="tdRight" >
				<div class="base-input-radio" id="isSuperDiv">
				    <label for="isSuper" onclick="App.radioCheck(this,'isSuperDiv')"  <c:if test="${''==selectInfo.isSuper or selectInfo.isSuper==null}">class="check-label"</c:if>>全部</label><input type="radio" id="isSuper" name="isBuyer" value="" <c:if test="${''==selectInfo.isSuper or selectInfo.isSuper==null}">checked="checked"</c:if>>
					<label for="isSuper1" onclick="App.radioCheck(this,'isSuperDiv')" <c:if test="${'1'==selectInfo.isSuper}">class="check-label"</c:if>>是</label><input type="radio" id="isSuper1" name="isSuper" value="1" <c:if test="${'1'==selectInfo.isSuper}">checked="checked"</c:if>>
					<label for="isSuper2" onclick="App.radioCheck(this,'isSuperDiv')" <c:if test="${'0'==selectInfo.isSuper}">class="check-label"</c:if>>否</label><input type="radio" id="isSuper2" name="isSuper" value="0" <c:if test="${'0'==selectInfo.isSuper}">checked="checked"</c:if>>
				</div>
			</td>
			<td class="tdLeft">是否采购员</td>
			<td class="tdRight" >
				<div class="base-input-radio" id="isBuyerDiv">
				    <label for="isBuyer" onclick="App.radioCheck(this,'isBuyerDiv')"  <c:if test="${''==selectInfo.isBuyer or selectInfo.isBuyer==null}">class="check-label"</c:if>>全部</label><input type="radio" id="isBuyer" name="isBuyer" value="" <c:if test="${''==selectInfo.isBuyer or selectInfo.isBuyer==null}">checked="checked"</c:if>>
					<label for="isBuyer1" onclick="App.radioCheck(this,'isBuyerDiv')" <c:if test="${'Y'==selectInfo.isBuyer}">class="check-label"</c:if>>是</label><input type="radio" id="isBuyer1" name="isBuyer" value="Y" <c:if test="${'Y'==selectInfo.isBuyer}">checked="checked"</c:if>>
					<label for="isBuyer2" onclick="App.radioCheck(this,'isBuyerDiv')" <c:if test="${'N'==selectInfo.isBuyer}">class="check-label"</c:if>>否</label><input type="radio" id="isBuyer2" name="isBuyer" value="N" <c:if test="${'N'==selectInfo.isBuyer}">checked="checked"</c:if>>
				</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">角色</td>
			<td class="tdRight" >
				
				<input type="hidden" id="roleId"  name="roleId" value="${selectInfo.roleId}" class="base-input-text"/>
				<input type="text"  id="roleName" name="roleName" value="${selectInfo.roleName}"  class="base-input-text" maxlength="30"/>
				<a href="#" onclick="selectRole();" title="角色查询">
					<img border="0px;" height="100%;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="供应商查询" style="cursor:pointer;vertical-align: middle;margin-left:0px;heigth:30px;width:30px;"/>
				</a>
			</td>
			<td class="tdLeft">是否分配角色</td>
			<td class="tdRight" >
				<div class="base-input-radio" id="haveRoleDiv">
				    <label for="haveRole" onclick="App.radioCheck(this,'haveRoleDiv')"  <c:if test="${'0'==selectInfo.haveRole}">class="check-label"</c:if>>全部</label><input type="radio" id="haveRole" name="haveRole" value="0" <c:if test="${'0'==selectInfo.haveRole or selectInfo.haveRole==null}">checked="checked"</c:if>>
					<label for="haveRole1" onclick="App.radioCheck(this,'haveRoleDiv')" <c:if test="${'1'==selectInfo.haveRole}">class="check-label"</c:if>>是</label><input type="radio" id="haveRole1" name="haveRole" value="1" <c:if test="${'1'==selectInfo.haveRole}">checked="checked"</c:if>>
					<label for="haveRole2" onclick="App.radioCheck(this,'haveRoleDiv')" <c:if test="${'2'==selectInfo.haveRole}">class="check-label"</c:if>>否</label><input type="radio" id="haveRole2" name="haveRole" value="2" <c:if test="${'2'==selectInfo.haveRole}">checked="checked"</c:if>>
				</div>
			</td>
		</tr>
		
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="010302" value="查找"/>
				<!-- 
				<p:button funcId="010301" type="add"/>
				 -->
				<input id="reset" type="button" value="重置" onclick="resetAll();">
				<input  type="button" value="导出" onclick="exportData();">
			</td>
		</tr>
	</table>
	<br/>
	<table id="listTab" class="tableList">
		<tr>
			<th width="10%"><fmt:message key="label.user.userId"/></th>
			<th width="8%"><fmt:message key="label.user.userName"/></th>
			<th width="26%">所属责任中心</th>
			<th width="8%">用户分类</th>
			<th width="24%">所属角色</th>
			<th width="8%">是否采购员</th>
			<th width="8%">是否省行超级管理员</th>
			<th width="8%"><fmt:message key="label.modify"/></th>
			
		</tr>
		<c:forEach items="${userList}" var="userInfo">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>${userInfo.userId}</td>
				<td>${userInfo.userName}</td>
				<td>(${userInfo.dutyCode })${userInfo.dutyName }</td>
				<td>
					<c:if test="${userInfo.userType == '1'}">实名</c:if>
					<c:if test="${userInfo.userType == '2'}">虚拟</c:if>
				</td>
				<td>${userInfo.roleName }</td>
				<td>${userInfo.isBuyer }</td>
				<td>${userInfo.isSuperAdmin }</td>
				<td>
<%-- 					<input type="button" value="修改" onclick="modify('${userInfo.userId}');"> --%>
<%-- 					<input type="button" value="初始化密码" onclick="initPwd('${userInfo.userId}')"> --%>
					<div class="update">
						<a href="#" onclick="modify('${userInfo.userId}');" title="<%=WebUtils.getMessage("button.update")%>"></a>
					</div>
				</td>
				
			</tr>
		</c:forEach>
		<c:if test="${empty userList}">
				<tr>
					<td colspan="8" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
			</c:if>
	</table>
</form>
<p:page/>
</body>
</html>