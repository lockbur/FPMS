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
<title>项目修改</title>

<script type="text/javascript">

//页面初始化
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#startYear").combobox();
	
	//设置时间插件
	$( "#endDate" ).datepicker({
		changeMonth: true,
		changeYear: true,
	    dateFormat:"yy-mm-dd",
	    minDate:new Date()
	});
}

//重置查询条件
function initEvent(){
	$("select").val("");
	$(":text").val("");
	$("#projType").val("");
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
function listMT()
{
 	var form=$("#projFormSearch")[0];
	form.action="<%=request.getContextPath()%>/projmanagement/projectMgr/updList.do?<%=WebConsts.FUNC_ID_KEY%>=021003";
	App.submit(form);
}

//详情
function update(projId,jspPage){
	var form=$("#projForm")[0];
	form.action="<%=request.getContextPath()%>/projmanagement/projectMgr/view.do?<%=WebConsts.FUNC_ID_KEY%>=02100201&projId="+projId+"&jspPage="+jspPage;
	App.submit(form);
}

//项目类型--跳出页面
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

function openManagerAuditDialog(projIdList){
	//终止
	var url = "<%=request.getContextPath()%>/projmanagement/projectMgr/endProj.do?<%=WebConsts.FUNC_ID_KEY %>=02100302";
	$("#auditDiv").dialog({
		title:"项目终止",
		closeOnEscape:false,
		autoOpen: true,
		height: 'auto',
		width: 500,
		modal: true,
		zIndex:100,
		dialogClass: 'dClass',
		resizable: false,
		open:function()
		{
			$("#hideProjIdList").html("<input name='projIdList' value='"+projIdList+"'/>");
			$("#endDate").prop("disabled",false);
		},
		buttons: {
			"确定": function() {
				if(!App.valid("#auditForm")){return;}
				var startTime = $("#" + projIdList).val();
				if($('#endDate').val() < startTime){
					App.notyError("终止时间小于项目起始时间");
				} else {
					App.submitShowProgress();//锁屏
					$('#auditForm').attr("action",url);
					$('#auditForm').submit();
				}
			},
			"取消": function() {
				$("#endDate").val("");
				$( this ).dialog( "close" );
			}
		},
		close:function(){
			$("#endDate").val("");
			$("#endDate").prop("disabled",true);
		}
	});
}

//全选
function checkAll(){
	if($("#checkItem").is(":checked")){
		$("input[name='projIdList']").prop('checked','true');
	}else{
		$("input[name='projIdList']").removeAttr("checked");
	}
}


function endBatch(){
	//批量终止
		if($("input[name='projIdList']:checked").size() < 1){
			App.notyError("请选择要终止的项目！");
			return false;
		}else{
			var url = "<%=request.getContextPath()%>/projmanagement/projectMgr/endProj.do?<%=WebConsts.FUNC_ID_KEY %>=02100302";
			$("#auditDiv").dialog({
				title:"项目批量终止",
				autoOpen: true,
				height: 'auto',
				width: 500,
				modal: true,
				zIndex:100,
				dialogClass: 'dClass',
				resizable: false,
				open:function(){
					$("#hideProjIdList").html($("input[name='projIdList']:checked").clone()); 
					$("#endDate").prop("disabled",false);
				},
				buttons: {
					"确定": function() {
						if(!App.valid("#auditForm")){return;}
						var list = $("#hideProjIdList").find("input");
						var startTime = $("#"+$(list[0]).val()).val();
						for(var i=1; i<list.length; i++){
							if(startTime < $("#"+$(list[i]).val()).val()){
								startTime = $("#"+$(list[i]).val()).val();
							}
						}
						if($("#endDate").val() < startTime){
							App.notyError("终止时间小于所选项目中最大的起始时间");
							return;
						} else {
							App.submitShowProgress();//锁屏
							$('#auditForm').attr("action",url);
							$('#auditForm').submit();							
						}
					},
					"取消": function() {
						$("#hideProjIdList").empty();
						$("#endDate").val("");
						$( this ).dialog( "close" );
					}
				},
				close:function(){
					$("#hideProjIdList").empty();
					$("#endDate").val("");
					$("#endDate").prop("disabled",true);
				}
			});
		}
	
}


</script> 
</head>

<body>
<p:authFunc funcArray="021003"/>
<form action="" method="post" id="projFormSearch">
	<p:token/>
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				项目查询 
			</th>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">创建年份</td>
			<td class="tdRight" width="80%" colspan="3">
			<div class="ui-widget">
				<select id="startYear" name="startYear">
					<option value="">--请选择--</option>						
					<forms:codeTable tableName="TD_PROJECT" selectColumn="DISTINCT SUBSTR(START_DATE,0,4) AS START_YEAR"
					 valueColumn="START_YEAR" textColumn="START_YEAR" orderColumn="START_YEAR" 
					 orderType="ASC"  selectedValue="${proj.startYear}"/>
				</select>
			</div>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="20%">项目类型</td>
			<td class="tdRight" width="30%">
				<input id="projTypeName" name="projTypeName"  class="base-input-text" valid errorMsg="项目类型不能为空，请选择项目类型！" type="text" style="width:235px;" readonly="readonly" value="${proj.projTypeName}"   onclick="projTypePage()"/>
				<input id="projType" name="projType" type="hidden" value="${proj.projType}"/>
			</td>	
			<td class="tdLeft" width="20%">项目名称</td>
			<td class="tdRight" width="30%">
				<input type="text" id="projName" name="projName" value="${proj.projName}"  class="base-input-text" maxlength="200"/>
			</td>	
		</tr>	
		<tr>
			<td colspan="4" class="tdWhite">
				<input type="button" value="查找" onclick="listMT();"/>
				<input type="button" value="批量终止" onclick="endBatch()">
				<input type="button" value="重置" onclick="initEvent();"/>					
			</td>
		</tr>
	</table>
</form>
<form action="" method="post" id="projForm">
	<br/>	
	<div id="listDiv" style="width: 100%; overflow-X: auto; position: relative; float: right">

	<table id="listTab" class="tableList">		
		<tr id="sd">	
		   	<th width="3%"><input type="checkbox" id="checkItem" onclick="checkAll()"></th>	
			<th width="9%">创建年份</th>
			<th width="18%">项目编号</th>
			<th width="10%">项目类型</th>
			<th >项目名称</th>
			<th width="10%">项目预算</th>	
			<th width="10%">合同金额</th>	
			<th width="8%">终止日期</th>	
			<th width="10%" >操作</th>
		</tr>
		<c:forEach items="${pList}" var="p" varStatus="status">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
			    <td>
			    <c:if test="${p.endDate == '2099-12-31'||p.endDate == null}">
			    <input type="checkbox" name="projIdList" value="${p.projId}">
			    </c:if>
			    </td>							
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
				<td class="tdc"><input type="hidden" id="${p.projId }" value="${p.startDate }" >${p.endDate}</td>
				<td align="center">
  				    <div class="update">
					    <input type="button" value="修改" onclick="update('${p.projId}','edit');" title="<%=WebUtils.getMessage("button.edit")%>">
					</div> 
					<!--  
				    <c:if test="${p.endDate == '2099-12-31'||p.endDate == null}">
						<div class="stopProj" style="float: left;">
						    <input type="button" value="终止" onclick="openManagerAuditDialog('${p.projId}')"/>
						</div>
					</c:if>
					-->
				</td>	
			</tr>
		</c:forEach>
		<c:if test="${empty pList}">
			<tr>
				<td colspan="9" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
			</tr>
		</c:if>
	</table>
</div>
</form>
<jsp:include page="audit.jsp" />
<p:page/>

</body>
</html>