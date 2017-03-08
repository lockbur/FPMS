<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>合同取消</title>

<script type="text/javascript">

//页面初始化
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	
	//设置时间插件
	$( "#startDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	});
	
	//设置时间插件
	$( "#endDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	});
	
}

//重置查询条件
function initEvent(){
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
};



//查询列表
function listEnd()
{
	if(!$.checkDate("startDate","endDate")){
		return false;
	}
 	var form=$("#endFormSearch")[0];
 	var orgFlag = '${e.orgFlag}';
 	if(orgFlag == '1')
 	{
 		form.action="<%=request.getContextPath()%>/contract/end/org1List.do?<%=WebConsts.FUNC_ID_KEY%>=03020701";
 	}
 	else if(orgFlag == '2')
 	{
 		form.action="<%=request.getContextPath()%>/contract/end/org2List.do?<%=WebConsts.FUNC_ID_KEY%>=03020703";
 	}
 	else if(orgFlag == '3')
 	{
 		form.action="<%=request.getContextPath()%>/contract/end/dutyCodeList.do?<%=WebConsts.FUNC_ID_KEY%>=03020704";
 	}
	App.submit(form);
}

//详情
function preEdit(cntNum){
	var form=$("#endForm")[0];
	form.action="<%=request.getContextPath()%>/contract/end/cancelView.do?<%=WebConsts.FUNC_ID_KEY%>=0302070103&cntNum="+cntNum;
	App.submit(form);
}

//供应商--跳出页面
function providerTypePage()
{
	$.dialog.open(
			'<%=request.getContextPath()%>/sysmanagement/provider/searchProvider.do?<%=WebConsts.FUNC_ID_KEY %>=010710',
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"供应商选择",
			    id:"dialogCutPage",
				close: function(){
					var object = art.dialog.data('object'); 
					if(object){
						$("#providerName").val(object.providerName);
					}
					}		
			}
		 );
}




</script> 
</head>

<body>
<p:authFunc funcArray="0302070103"/>
<form action="" method="post" id="endFormSearch">
	<p:token/>
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				合同查询
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" id="cntNum" name="cntNum" value="${e.cntNum}"  class="base-input-text" maxlength="80"/>
			</td>
			<td class="tdLeft">合同类型</td>
			<td class="tdRight">
			<div class="ui-widget">
				<select id="cntType" name="cntType">
					<option value="">--请选择--</option>						
					<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
					 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" 
					 conditionStr="CATEGORY_ID = 'CNT_TYPE'"
					 orderType="ASC"  selectedValue="${e.cntType}"/>
				</select>
			</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft">供应商</td>
			<td class="tdRight">
				<input type="text" id="providerName" name="providerName" value="${e.providerName}" onclick="providerTypePage()" class="base-input-text" readonly/>
				<%-- <input type="hidden" id="providerCode" name="providerCode" value="${e.providerCode}"  class="base-input-text" />
				<a href="#" onclick="providerTypePage()">
				<img border="0px;" src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="查看项目类型" style="cursor:pointer;vertical-align: middle;margin-left:15px;heigth:30px;width:30px;"/>
				</a> --%>
			</td>	
			<td class="tdLeft" >签订日期区间</td>
			<td class="tdRight" >
				<input type="text" id="startDate" name="startDate" valid maxlength='10' readonly="readonly" value="${e.startDate}" class="base-input-text" style="width:135px;"/>
				至
				<input type="text" id="endDate" name="endDate" valid maxlength='10' readonly="readonly" value="${e.endDate}" class="base-input-text" style="width:135px;"/>
			</td>	
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" value="查找" onclick="listEnd();"/>
				<input type="button" value="重置" onclick="initEvent();"/>					
			</td>
		</tr>
	</table>
</form>
<form action="" method="post" id="endForm">
	<br/>	
	<div id="listDiv" style="width: 100%; overflow-X: auto; position: relative; float: right">

	<table id="listTab" class="tableList">
		<tr>		
			<th width='13%'>合同号 </th>
			<th width='10%'>合同事项    </th>
			<th width='10%'>合同类型    </th>
			<th width='9%'>供应商  </th>
			<th width='10%'>合同金额(不含税)    </th>
			<th width='10%'>税额    </th>
			<th width='10%'>签订日期    </th>
			<th width='10%'>合同状态  </th>
			<th width='12%'>录入责任中心</th>
			<th width='10%'>录入时间    </th>
			<th width='6%'>操作</th>
		</tr>
		<c:forEach items="${eList}" var="e" varStatus="status">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">				
				<td class="tdc">${e.cntNum}</td>								
				<td class="tdc"><forms:StringTag length="20" value="${e.cntName}"/></td>
				<td class="tdc">${e.cntTypeName}/<c:if test="${e.isOrder == 1}">非订单</c:if><c:if test="${e.isOrder == 0}">订单</c:if></td>
				<td>${e.providerName}</td>					
				<td class="tdr"><fmt:formatNumber type="number" value="${e.cntAmt}" minFractionDigits="2"/></td>
				<td class="tdr"><fmt:formatNumber type="number" value="${e.cntTaxAmt}" minFractionDigits="2"/></td>	
				<td class="tdc">${e.signDate}</td>
				<td>${e.dataFlagName}</td>
				<td>${e.createDeptName}</td>	
				<td class="tdc">${e.createDate}</td>
				<td >
					<div class="update">
					    <a href="#" onclick="preEdit('${e.cntNum}');" title="合同取消"></a>
					</div>
				</td>	
			</tr>
		</c:forEach>
		<c:if test="${empty eList}">
			<tr>
				<td colspan="11" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</div>
</form>
<p:page/>
</body>
</html>