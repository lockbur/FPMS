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
<title>项目查询</title>
<script type="text/javascript">

//页面初始化
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
}

//重置查询条件
function initEvent(){
	$("select").val("");
	$(":text").val("");
	$(":selected").prop("selected",false);
	$("#projType").val("");
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
function listMT()
{
 	var form=$("#projFormSearch")[0];
	form.action="<%=request.getContextPath()%>/projmanagement/projectMgr/list.do?<%=WebConsts.FUNC_ID_KEY%>=021002";
	App.submit(form);
}

//详情
function detail(projId,jspPage){
	var form=$("#projForm")[0];
	form.action="<%=request.getContextPath()%>/projmanagement/projectMgr/view.do?<%=WebConsts.FUNC_ID_KEY%>=02100201&projId="+projId+"&jspPage="+jspPage;
	App.submit(form);
}

//出库设备添加--跳出页面
function projTypePage()
{
	var url="<%=request.getContextPath()%>/projmanagement/projectMgr/projTypePage.do?<%=WebConsts.FUNC_ID_KEY %>=02100102";
	$.dialog.open(
			url,
			{
				width: "60%",
				height: "75%",
				lock: true,
			    fixed: true,
			    title:"项目类型选择",
			    id:"dialogCutPage",
				close: function(){
					var proObj = art.dialog.data('projTypeObject'); 
					if(proObj){
						$("#projType").val(proObj.projType);
						$("#projTypeName").val(proObj.projTypeName);
					}
				}
			}
		 );
}


function exportData(){
	var isPass=true;
	var data = {};
	data['projType'] =  $("#projType").val();
	data['projName'] = $("#projName").val();
	data['startYear'] = $("#startYear").val();
	App.ajaxSubmit("projmanagement/projectMgr/exportData.do?<%=WebConsts.FUNC_ID_KEY %>=02100104",
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
<p:authFunc funcArray="021002"/>
<form action="" method="post" id="projFormSearch">
	<p:token/>
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				项目查询 
			</th>
		</tr>
		<%-- <tr>
			<td class="tdLeft" width="20%">创建年份</td>
			<td class="tdRight" width="80%" colspan="3">
			<div class="ui-widget">
				<select id="startYear" name="startYear">
					<option value="">--请选择--</option>						
					<forms:codeTable tableName="TD_PROJECT" selectColumn="DISTINCT SUBSTR(START_DATE,0,5) AS START_YEAR"
					 valueColumn="START_YEAR" textColumn="START_YEAR" orderColumn="START_YEAR" 
					 orderType="ASC"  selectedValue="${proj.startYear}"/>
				</select>
			</div>
			</td>
		</tr> --%>
		<tr>
			<td class="tdLeft" width="20%">项目类型</td>
			<td class="tdRight" width="30%">
				<input id="projTypeName" name="projTypeName"  class="base-input-text" valid errorMsg="项目类型不能为空，请选择项目类型！" type="text" style="width:235px;" readonly="readonly" value="${proj.projTypeName}"   onclick="projTypePage()"/>
				<input id="projType" name="projType"    type="hidden" value="${proj.projType}"/>
			</td>	
			<td class="tdLeft" width="20%">项目名称</td>
			<td class="tdRight" width="30%">
				<input type="text" id="projName" name="projName" value="${proj.projName}"  class="base-input-text" maxlength="200"/>
			</td>	
		</tr>	
		<tr>
			<td class="tdLeft" width="20%">创建年份</td>
			<td class="tdRight" width="30%" colspan="3">
				<input id="startYear" name="startYear"  class="base-input-text"   type="text" value="${proj.startYear}"  >
			</td>	
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" value="查找" onclick="listMT();"/>
				<input type="button" value="重置" onclick="initEvent();"/>	
				<input type="button" value="导出" onclick="exportData();"/>					
			</td>
		</tr>
	</table>
</form>
<form action="" method="post" id="projForm">
	<br/>	
	<div id="listDiv" style="width: 100%; position: relative; float: right">
	
	<table id="listTab" class="tableList">		
		<tr>		
			<th width="8%">创建年份</th>
			<th width="17%">项目编号</th>
			<th width="8%">项目类型</th>
			<th width="15%">项目名称</th>
			<th width="10%">项目预算</th>	
			<th width="10%">合同金额</th>
			<th width="10%">累计已付款金额</th>	
			<th width="14%">终止日期</th>	
			<th width="7%">操作</th>
		</tr>
		<c:forEach items="${pList}" var="p" varStatus="status">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">				
				<td class="tdc">${p.startYear}</td>								
				<td>${p.projId}</td>	
				<td>${p.projTypeName}</td>					
				<td>${p.projName}</td>	
				<td class="tdr"><fmt:formatNumber type="number" value="${p.budgetAmt}" /></td>
				<td class="tdr">
				<c:choose>
				  <c:when test="${empty p.cntTotalAmt}">
				  0
				  </c:when>
				  <c:otherwise>
				  <fmt:formatNumber type="number" value="${p.cntTotalAmt}" />
				  </c:otherwise>
				</c:choose>
				</td>
				<td class="tdr">
				  <fmt:formatNumber type="number" value="${p.payTotalAmt}" />
				</td>	
				<td class="tdc">${p.endDate}</td>
				<td >
  				    <div class="detail">
					    <a href="#" onclick="detail('${p.projId}','view');" title="<%=WebUtils.getMessage("button.view")%>"></a>
					</div>
				</td>	
			</tr>
		</c:forEach>
		<c:if test="${empty pList}">
			<tr>
				<td colspan="8" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</div>
</form>
<p:page/>
</body>
</html>