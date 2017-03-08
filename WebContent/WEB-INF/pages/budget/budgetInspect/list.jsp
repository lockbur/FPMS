<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/platform-tags" prefix="p"%>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预算查询</title>
<script type="text/javascript" src="<%=request.getContextPath()%>/common/js/infoHide.js"></script>
<script type="text/javascript">

//页面初始化
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	infoLength(20);
	//级联回显
	$(".erp_cascade_select").combobox();
	
}
//重置查询条件
function initEvent(){
	$("#bgtMatrcode").val("");
	$(":text").val("");
	$("select").val("");
	$("#montName").empty();
	$("#overDrawDiv").find("label").eq('').click();
	$("select").each(function(){
		var id = $(this).attr("id");
		if(id!=""){
			var year = $("#"+id+" option:first").text();
			$(this).val(year);
			 $(this).next().val(year) ;
			$(this).find("option:eq(0)").attr("selected",true);
			$(this).find("option:gt(0)").removeAttr("selected");
		}
		
	});
};
 
//查询明细
function sumList(bgtId){
	var form=$("#projFormSearch")[0];
	form.action="<%=request.getContextPath()%>/budget/budgetInspect/sumList.do?<%=WebConsts.FUNC_ID_KEY%>=02070102&bgtId="+bgtId;
	App.submit(form);
}
function cntDetail(bgtId){
	var form=$("#projFormSearch")[0];
	form.action="<%=request.getContextPath()%>/budget/budgetInspect/toCntList.do?<%=WebConsts.FUNC_ID_KEY%>=02070103&bgtId="+bgtId;
	App.submit(form);
}
//查询付款审批历史记录(弹窗显示)
function bugetAdjustLog(bgtId){
	var url = "<%=request.getContextPath()%>/budget/budgetInspect/bugetAdjustLog.do?<%=WebConsts.FUNC_ID_KEY%>=02070110&bgtId="+bgtId;
	$.dialog.open(
			url,
			{
				width: "80%",
				height: "60%",
				lock: true,
			    fixed: true,
			    title:"预算调整历史记录",
			    id:"dialogCutPage",
				close: function(){
				}
			}
		 );
}
//查询列表
function listMT()
{
 	var form=$("#projFormSearch")[0];
 	var orgType =$("#orgType").val();
 	var menuType = $("#menuType").val();
 	if(orgType =='1'){
 		if(menuType == '1'){
 			form.action="<%=request.getContextPath()%>/budget/budgetInspect/shList.do?<%=WebConsts.FUNC_ID_KEY%>=02070121";
 		}else if(menuType =='2'){
 			form.action="<%=request.getContextPath()%>/budget/budgetInspect/shChangeList.do?<%=WebConsts.FUNC_ID_KEY%>=02090321";	
 		}
 		
 	}else if(orgType == '2'){
 		if(menuType == '1'){
 			form.action="<%=request.getContextPath()%>/budget/budgetInspect/fhList.do?<%=WebConsts.FUNC_ID_KEY%>=02070122";
 		}else if(menuType =='2'){
 			form.action="<%=request.getContextPath()%>/budget/budgetInspect/fhChangeList.do?<%=WebConsts.FUNC_ID_KEY%>=02090322";	
 		}
 		
 	}
	
	App.submit(form);
}
//监控指标二级目录change事件

//刷新页面后三级关联下拉框回显
//弹出物料名称框 
function matrClick(){
	$.dialog.open(
			'<%=request.getContextPath()%>/budget/budgetInspect/getMatrName.do?<%=WebConsts.FUNC_ID_KEY %>=02070105',
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"物料选择 ",
			    id:"dialogCutPage",
				close: function(){
					var object = art.dialog.data('object'); 
					if(object){
						$("#matrname").val(object.matrName);
						$("#bgtMatrcode").val(object.matrCode);
					}
					}		
			}
		 );
}
//监控指标弹出框选择
function montNameClick(){
	if( $("#montType").val()=='0'){
		  App.notyError("请先选择监控指标类型！");
	      return false;
	}
	var montType=$("#montType").val();
	var orgType = $("#orgType").val();
	$.dialog.open(
			'<%=request.getContextPath()%>/budget/budgetInspect/getMontNameList.do?<%=WebConsts.FUNC_ID_KEY %>=02070111&montType='+montType+'&orgType='+orgType,
			{
				width: "75%",
				height: "60%",
				lock: true,
			    fixed: true,
			    title:"监控指标选择 ",
			    id:"dialogCutPage",
				close: function(){
					var object = art.dialog.data('object'); 
					if(object){
						$("#montName").val(object.montName);
					}
					}		
			}
		 );
}
function clearMontName(){
	$("#montName").val("");
}

function exportData(){
	var isPass=true;
	var data = {};
	data['exportType']='1';
	data['bgtId'] =  $("#bgtId").val();
	data['bgtYear'] = $("#bgtYear").val();
	data['overDrawType'] = $("input[type=radio][name='overDrawType']:checked").val(); 
	data['bgtOrgcode'] =  $("#bgtOrgcode").val();
	data['bgtMatrcode'] = $("#bgtMatrcode").val();
	data['montType'] =  $("#montType").val();
	data['montName'] =  $("#montName").val();
	data['orgType'] =  $("#orgType").val();
	App.ajaxSubmit("budget/budgetInspect/exportBgtInspectQueryData.do?<%=WebConsts.FUNC_ID_KEY %>=02070107",
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
function toAdjust(bgtId){
	var form=$("#projFormSearch")[0];
	form.action="<%=request.getContextPath()%>/budget/budgetInspect/toAdjust.do?<%=WebConsts.FUNC_ID_KEY%>=02090301&bgtId="+bgtId;
	App.submit(form);
}
function toValidBgt(bgtId){
	var form=$("#projFormSearch")[0];
	form.action="<%=request.getContextPath()%>/budget/budgetInspect/toValidBgt.do?<%=WebConsts.FUNC_ID_KEY%>=02090301&bgtId="+bgtId;
	App.submit(form);
}
function delBgt(bgtId){
	var data = {};
	 
	data['tzjy'] =  $("#tzjy").val();
	data['bgtId']=bgtId;
	var url = "budget/budgetInspect/checkDelBgt.do?VISIT_FUNC_ID=02090307";
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
		var flag = data.flag;
		if(flag =="W"){
			$( "<div>该预算已被合同使用过，但是已全部释放，删除后将无法看到日志记录，是否确定删除？</div>" ).dialog({
				resizable: false,
				height:180,
				modal: true,
				dialogClass: 'dClass',
				buttons: {
					"确定": function() {
						 
						$( this ).dialog( "close" );
						var form=$("#projFormSearch")[0];
						form.action="<%=request.getContextPath()%>/budget/budgetInspect/delBgt.do?<%=WebConsts.FUNC_ID_KEY%>=02090306&bgtId="+bgtId;
						App.submit(form);
					},
					"取消": function() {
						$( this ).dialog( "close" );
						return false;
					}
				}
			});
		}
		else if(flag =="N"){
			App.notyError(data.msg);
			return false;
		}else{
			$( "<div>是否确定删除该预算？</div>" ).dialog({
				resizable: false,
				height:180,
				modal: true,
				dialogClass: 'dClass',
				buttons: {
					"确定": function() {
						$( this ).dialog( "close" );
						var form=$("#projFormSearch")[0];
						form.action="<%=request.getContextPath()%>/budget/budgetInspect/delBgt.do?<%=WebConsts.FUNC_ID_KEY%>=02090306&bgtId="+bgtId;
						App.submit(form);
					},
					"取消": function() {
						$( this ).dialog( "close" );
						return false;
					}
				}
			});
		}
	});
	
}
</script>
</head>

<p:authFunc funcArray="020701" />
	<form action="" method="post" id="projFormSearch">
	<input type="hidden" name="orgType" id="orgType" value="${queryCondition.orgType }">
	<input type="hidden" name="menuType" id="menuType" value="${queryCondition.menuType }">
		<p:token />
		<table>
			<tr class="collspan-control">
				<th colspan="4">预算查询</th>
			</tr>
			<tr>
				<td class="tdLeft" width="20%">预算ID</td>
				<td class="tdRight" width="30%">
					<input type="text" name="bgtIdTemp"   id="bgtId" value="${queryCondition.bgtId}" class="base-input-text" maxlength="300"/>
				</td>
			</tr>
			<tr>
				<td class="tdLeft" width="20%">预算年份</td>
				<td class="tdRight" width="30%">
					<input type="text" name="bgtYear"   id="bgtYear" value="${queryCondition.bgtYear}" class="base-input-text" maxlength="300"/>
				</td>
				<td class="tdLeft" width="20%">预算是否透支</td>
				<td class="tdRight" width="30%">
					<div class="base-input-radio" id="overDrawDiv">
				    <label for="overDraw" onclick="App.radioCheck(this,'overDrawDiv')"  <c:if test="${'0'==queryCondition.overDrawType}">class="check-label"</c:if>>全部</label><input type="radio" id="overDraw" name="overDrawType" value="0" <c:if test="${'0'==queryCondition.overDrawType or queryCondition.overDrawType==null}">checked="checked"</c:if>>
					<label for="overDraw1" onclick="App.radioCheck(this,'overDrawDiv')" <c:if test="${'1'==queryCondition.overDrawType}">class="check-label"</c:if>>是</label><input type="radio" id="overDraw1" name="overDrawType" value="1" <c:if test="${'1'==queryCondition.overDrawType}">checked="checked"</c:if>>
					<label for="overDraw2" onclick="App.radioCheck(this,'overDrawDiv')" <c:if test="${'2'==queryCondition.overDrawType}">class="check-label"</c:if>>否</label><input type="radio" id="overDraw2" name="overDrawType" value="2" <c:if test="${'2'==queryCondition.overDrawType}">checked="checked"</c:if>>
				</div> 
				</td>
			</tr>
			<tr>
				<td class="tdLeft" width="20%">预算机构代码</td>
				<td class="tdRight" width="30%">
					<input type="text" name="bgtOrgcode"   id="bgtOrgcode" value="${queryCondition.bgtOrgcode}" class="base-input-text" maxlength="300"/>
				</td>
				 <td class="tdLeft" width="20%">物料名称</td>
				<td class="tdRight" width="30%"  >
					<input type="text" readonly id="matrname" name="matrname" class="base-input-text" value="${queryCondition.matrname}" onclick="matrClick()" >
					<input type="hidden" id="bgtMatrcode" value="${queryCondition.bgtMatrcode}" name="bgtMatrcode"/>
				</td>
				
			</tr>
				<tr>
				<td class="tdLeft" width="20%">监控指标类型</td>
				<td class="tdRight" width="80%" >
				<div class="ui-widget" style="float: left;">
					 <select id="montType" name="montType"    onchange="clearMontName()" class="erp_cascade_select" >
							<option value="0">-请选择-</option>
							<c:if test="${queryCondition.orgType eq '1' }">
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='11' or PARAM_VALUE='12')" selectedValue="${queryCondition.montType}"/>
							</c:if>
							<c:if test="${queryCondition.orgType eq '2' }">
								<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME" 
								 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="PARAM_VALUE" orderType="ASC"  conditionStr="CATEGORY_ID='MONT_TYPE' and (PARAM_VALUE='21' or PARAM_VALUE='22')" selectedValue="${queryCondition.montType}"/>
							</c:if>
					</select>
				</div>
				</td>
				<td class="tdLeft" width="20%">监控指标</td>
				<td class="tdRight" width="80%" >
				<input type="text" readonly id="montName" name="montName" class="base-input-text" value="${queryCondition.montName}" onclick="montNameClick()" >
				
			</tr>
			<%-- <tr>
				<td class="tdLeft" width="20%">预算是否透支</td>
				<td class="tdRight" width="80%" colspan="3">
					<div class="base-input-radio" id="overDrawDiv">
				    <label for="overDraw" onclick="App.radioCheck(this,'overDrawDiv')"  <c:if test="${'0'==queryCondition.overDrawType}">class="check-label"</c:if>>全部</label><input type="radio" id="overDraw" name="overDrawType" value="0" <c:if test="${'0'==queryCondition.overDrawType or queryCondition.overDrawType==null}">checked="checked"</c:if>>
					<label for="overDraw1" onclick="App.radioCheck(this,'overDrawDiv')" <c:if test="${'1'==queryCondition.overDrawType}">class="check-label"</c:if>>是</label><input type="radio" id="overDraw1" name="overDrawType" value="1" <c:if test="${'1'==queryCondition.overDrawType}">checked="checked"</c:if>>
					<label for="overDraw2" onclick="App.radioCheck(this,'overDrawDiv')" <c:if test="${'2'==queryCondition.overDrawType}">class="check-label"</c:if>>否</label><input type="radio" id="overDraw2" name="overDrawType" value="2" <c:if test="${'2'==queryCondition.overDrawType}">checked="checked"</c:if>>
				</div> 
				</td>
			</tr> --%>
			<tr>
				<td colspan="4" class="tdWhite">
					<input type="button" value="查找" onclick="listMT();" /> 
					<c:if test="${queryCondition.menuType eq '1' }">
					<input type="button" value="数据导出" title="该操作会根据搜索条件进行查询并将查询结果数据导出到Excel文件" onclick="exportData();" />
					</c:if>
					<input type="button" value="重置" onclick="initEvent();" />
				</td>
			</tr>
		</table>

	</form>
	<form action="" method="post" id="projForm">
		<br />
		<div id="listDiv"
			style="width: 100%; position: relative; float: right">

			<table id="listTab" class="tableList">
				<tr>
					<th width="10%">预算年份</th>
					<th width="14%">预算机构</th>
					<th width="14%">预算监控指标</th>
					<th width="12%">预算物料名称</th>
					<th width="8%">总预算</th>
					<th width="8%">总可用预算</th>
					<th width="8%">总冻结预算</th>
					<th width="8%">总透支预算</th>
					<th width="8%">总占用预算</th>
					<th width="10%">操作</th>
				</tr>
				<c:forEach items="${infoList}" var="info" varStatus="i">
					<tr  onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');" style="cursor: pointer" class="trOther">
						<td class="compare">${info.bgtYear}</td>
						<td class="compare" <%-- id='duty_td_<c:out value="${i.index+1}"/>' rowspan='1' --%>>
						    <%-- <input type="hidden" id='duty_td_h_<c:out value="${i.index+1}"/>' value="${info.dutyName}"> --%>
						    (${info.bgtOrgcode})${info.dutyName}
						</td>
						<td class="compare" <%-- id='mont_td_<c:out value="${i.index+1}"/>' rowspan='1' title="${info.bgtMontcode}" --%>>
						    <%-- <input type="hidden" id='mont_td_h_<c:out value="${i.index+1}"/>' value="${info.montName}"/> --%>
						     (${info.bgtMontcode})${info.montName}
						</td>
						<td   >
							<c:if test="${info.bgtMatrcode == null}">
								-
							</c:if>
							<c:if test="${info.bgtMatrcode != null}">
								(${info.bgtMatrcode})${info.matrName}
							</c:if>
						    
						</td>
						<td class="compare">${info.bgtSum}</td>
						<td class="compare">${info.bgtSumValid}</td>
						<td class="compare">${info.bgtFrozen}</td>
						<td class="compare"><fmt:formatNumber type="number"
								value="${info.bgtOverdraw}" /></td>
						<td class="compare">${info.bgtUsed}</td>
						<td class="tdWhite">
							<c:if test="${queryCondition.menuType eq '1' }">
					  		 	<input type="button" value="使用明细" onclick="cntDetail('${info.bgtId}')" />
					 		 	<input type="button" value="预算调整日志" onclick="bugetAdjustLog('${info.bgtId}')" />
					 		</c:if>
					 		<c:if test="${queryCondition.menuType eq '2' }">
					  		 	<input type="button" value="预算调整" onclick="toAdjust('${info.bgtId}')" />
					  			<input type="button" value="删除预算" onclick="delBgt('${info.bgtId}')" />
					  			<input type="button" value="预算调整日志" onclick="bugetAdjustLog('${info.bgtId}')" />
					 		</c:if> 
						</td>
					</tr>
				</c:forEach>
				<c:if test="${empty infoList}">
					<tr>
						<td colspan="10" style="text-align: center;"><span class="red">没有找到相关信息！</span></td>
					</tr>
				</c:if>
			</table>
		</div>
	</form>
<p:page/>
</body>
</html>