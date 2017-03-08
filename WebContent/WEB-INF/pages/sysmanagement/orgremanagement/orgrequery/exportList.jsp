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
<title>交叉验证表导出</title>
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
	$("#lockStatueDiv").find("label").eq(0).click();
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
function exportData(){
	var isPass=true;
	var data = {};
	data['org1Code'] =  $("#org1Code").val();
	data['org1Name'] = $("#org1Name").val();
	data['org2Code'] = $("#org2Code").val();
	data['org2Name'] = $("#org2Name").val();
	data['dutyCode'] = $("#dutyCode").val();
	data['dutyName'] = $("#dutyName").val();
	
	var temp = document.getElementsByName("isLocked");
	for(var i = 0;i<temp.length;i++){
		if(temp[i].checked){
			data['isLocked'] = temp[i].value;
		}
	}
	App.ajaxSubmit("sysmanagement/orgremanagement/orgrequery/exportData.do?<%=WebConsts.FUNC_ID_KEY %>=01041201",
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
<p:authFunc funcArray="010412"/>
<form action="" method="post" id="orgForm">
	<table>
		<tr class="collspan-control">
				<th colspan="4">交叉验证表查询</th>
			</tr>
			
			<c:if test="${selectInfoHidden.isSuperAdmin == '1'}">
				<c:if test="${selectInfoHidden.org1Code == 'A0001'}">
					<tr>
						<td class="tdLeft">一级分行代码</td>
						<td class="tdRight">
							<input type="text" id="org1Code" maxlength="5" name="org1Code" value="${selectInfo.org1Code }" class="base-input-text"/>
						</td>
						<td class="tdLeft">一级分行名称</td>
						<td class="tdRight">
							<input type="text" id="org1Name" name="org1Name" value="${selectInfo.org1Name }" class="base-input-text"/>
					</td>
					</tr>
					<tr>
						<td class="tdLeft">二级分行代码</td>
						<td class="tdRight">
							<input type="text" id="org2Code" maxlength="5" name="org2Code" value="${selectInfo.org2Code }" class="base-input-text"/>
						</td>
						<td class="tdLeft">二级分行名称</td>
						<td class="tdRight">
							<input type="text" id="org2Name" name="org2Name" value="${selectInfo.org2Name }" class="base-input-text"/>
						</td>
					</tr>
				
				</c:if>
				<c:if test="${selectInfoHidden.org1Code != 'A0001'}">
				
					<tr>
						<td class="tdLeft">二级分行代码</td>
						<td class="tdRight">
							<input type="text" id="org2Code" maxlength="5" name="org2Code" value="${selectInfo.org2Code }" class="base-input-text"/>
						</td>
						<td class="tdLeft">二级分行名称</td>
						<td class="tdRight">
							<input type="text" id="org2Name" name="org2Name" value="${selectInfo.org2Name }" class="base-input-text"/>
						</td>
					</tr>
				</c:if>
			</c:if>
			
			<tr>
				<td class="tdLeft">责任中心代码</td>
				<td class="tdRight">
						<input type="text"  id="dutyCode" maxlength="5" name="dutyCode"  value="${selectInfo.dutyCode}" class="base-input-text"/>
				</td>
				<td class="tdLeft">责任中心名称</td>
				<td class="tdRight">
					<input type="text" id="dutyName" name="dutyName"  value="${selectInfo.dutyName}" class="base-input-text"/>
				</td>
			</tr>
			<tr>
				<td class="tdLeft">责任中心锁定状态</td>
				<td class="tdRight" colspan="3">
					<div class="base-input-radio" id="lockStatueDiv">
					 	<label for="lockStatue" onclick="App.radioCheck(this,'lockStatueDiv')"  <c:if test="${'0'eq selectInfo.isLocked}">class="check-label"</c:if>>全部</label><input type="radio" id="lockStatue" name="isLocked" value="0" <c:if test="${'0' eq selectInfo.isLocked or searchBean.isLocked==null}">checked="checked"</c:if>>
						<label for="lockStatue1" onclick="App.radioCheck(this,'lockStatueDiv')" <c:if test="${'Y'eq selectInfo.isLocked}">class="check-label"</c:if>>锁定</label><input type="radio" id="lockStatue1" name="isLocked" value="Y" <c:if test="${'Y'eq selectInfo.isLocked}">checked="checked"</c:if>>
						<label for="lockStatue2" onclick="App.radioCheck(this,'lockStatueDiv')" <c:if test="${'N'eq selectInfo.isLocked}">class="check-label"</c:if>>未锁定</label><input type="radio" id="lockStatue2" name="isLocked" value="N" <c:if test="${'N'eq selectInfo.isLocked}">checked="checked"</c:if>>
					</div>
				</td> 
			</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="010412" value="查询"/> 
				<input type="button" value="重置" onclick="resetAll();">
				<input type="button" value="导出" onclick="exportData();">
			</td>
		</tr>
	</table>
	<br/>
	<table class="tableList">
		<tr>
			<th width="9%">一级分行代码</th>
			<th width="10%">一级分行名称</th>
			<th width="9%">二级分行代码</th>
			<th width="10%">二级分行名称</th>
			<th width="9%">OU代码</th>
			<th width="10%">OU名称</th>
			<th width="9%">机构号</th>
			<th width="10%">机构名称</th>
			<th width="9%">责任中心代码</th>
			<th width="10%">责任中心名称</th>
			<th width="5%">锁定状态</th>
				
		</tr>
			<c:forEach items="${lists}" var="org" varStatus="st">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');"
					onmouseout="setTrBgClass(this, 'trOther');">
					<td >${org.org1Code}</td>
					<td >${org.org1Name}</td>
					<td >${org.org2Code}</td>
					<td >${org.org2Name}</td>
					<td >${org.ouCode}</td>
					<td >${org.ouName}</td>
					<td >${org.orgCode}</td>
					<td >${org.orgName}</td>
					<td >${org.dutyCode }</td>
					<td >${org.dutyName }</td>
					<td >${org.isLocked }</td>
				</tr>
			</c:forEach>
			<c:if test="${empty lists}">
				<tr>
					<td colspan="11" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
				</tr>
			</c:if>
	</table>
</form>
<p:page/> 
</body>
</html>