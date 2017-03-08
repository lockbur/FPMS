<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预算模板管理页面</title>
<style type="text/css">
.tr-fixed-ie {  
    position: fixed; 
    z-index: 101;
/*     width:80.1%;   由于之前将js对象当作jquery对象对待，width值没赋上，所以这里才起作用；后来将其作为jQuery对待时，这个就可以不用写*/
    /* IE6改为绝对定位，并通过css表达式根据滚动位置更改top的值   absolute\relative*/  
/*     _position: relative; */
/*     _top: expression(eval(document.documentElement.scrollTop));   */
}
</style>
<script type="text/javascript">
//页面初始化加载
function pageInit(){
	//listTabFirstTr
	App.jqueryAutocomplete();
 	$("#dataTypeSelect").combobox();
	$("#dataAttrSelect").combobox();
	$("#valiYearSelect").combobox();

	
	//修复固定tableList的首行TR的位置
	tableFixed( "#listTab" , "#listTabFirstTr" );	//参数为存放TableList的Table对象、该Table对象的第一行tr(参数均为ID取值)、TR的首个TH的宽度百分比取值
}


/**
 * 表格修复方法
 *		描述：用于修复表格头TH，当TableList下拉查看时，将TH固定在主窗体中的顶部
 */
function tableFixed( tableObj , firstTrObj ){
	var listTab = $(tableObj);
	var tableListTop = listTab[0].offsetTop;				//获取TableList对象表格的最顶部TOP值

	window.onscroll = function(){
		//获取window竖向滚动条的滚动距离
		var scrollTop = document.documentElement.scrollTop || document.body.scrollTop;
		//如果滚动距离小于表格的最顶部，则去掉新增样式，采用默认的样式
// 		console.info(scrollTop+":"+tableListTop)
		if(scrollTop <= tableListTop){  
			$(firstTrObj).removeClass("tr-fixed-ie");
		}else{
		//如果滚动距离大于或等于表格的最顶部，则为表头行添加样式、设置表头行的宽度和固定在主窗体的顶部(设置top属性)、并为TR中首个TH赋值，处理TR被无故缩小
			$(firstTrObj).addClass("tr-fixed-ie");
			//width=TableList值列表第一行的长度、top=样式.top+样式.menu1两者的高度相加
			$(firstTrObj).css({"width":$(tableObj).find("tr")[1].offsetWidth, "top": ($(".top")[0].offsetHeight + $(".menu1")[0].offsetHeight)});  		//, "top": 127(需要在整合页面取得 .memu1 的offsetHeight+offsetTop值的和)
// 			console.info("首行宽度："+$(firstTrObj).width());
// 			console.info("内容第一行宽度："+$($(tableObj).find("tr")[1]).find("td")[0].offsetWidth);
			//将TableList首行首TD的宽度赋值给表格头TR的首个TH，以解决该tr被无故缩小,解决方案为：设置样式width于tr的第一个th时(原因不明)
			$(firstTrObj).find("th")[0].width = $($(tableObj).find("tr")[1]).find("td")[0].offsetWidth;	
		}
	}
}

//重置清空所有选中信息
function resetAll(){
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
}

//删除指定预算模板
function deleteBudget( tmpltId ){
	var url = "<%=request.getContextPath()%>/budget/budgetplan/delete.do?<%=WebConsts.FUNC_ID_KEY %>=020104&tmpltId=" + tmpltId;
	var form = $("#budgetPlanListForm")[0];
	form.action = url;
	$( "<div>确认删除?</div>" ).dialog({
		resizable: false,
		height:150,
		modal: true,
		dialogClass: 'dClass',
		buttons: {
			"确定": function() {
				App.submit(form,true);
			},
			"取消": function() {
				$( this ).dialog( "close" );
			}
		}
	});
}

//查看预算详情
function viewOrUpdateBudget(tmpltId , jspName){
// 	alert('In view');
// 	alert(tmpltId+"---"+jspName);
	var form = $("#budgetPlanListForm")[0];
	form.action="<%=request.getContextPath()%>/budget/budgetplan/view.do?<%=WebConsts.FUNC_ID_KEY %>=020105&tmpltId=" + tmpltId+"&jspName=" + jspName;
	App.submit(form);
}

//提交确认预算
function submitBudget(tmpltId){
	var form = $("#budgetPlanListForm")[0];
	form.action="<%=request.getContextPath()%>/budget/budgetplan/submit.do?<%=WebConsts.FUNC_ID_KEY %>=020106&budgetId=" + tmpltId;
	App.submit(form);
}

//预算模板导出操作
function exportBudgetExcel(tmpltId){
	var data = {};
	data['tmpltId'] =  tmpltId;
	App.ajaxSubmit("budget/budgetplan/exportBudgetPlanTempInfo.do?<%=WebConsts.FUNC_ID_KEY %>=02010303",
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
											form.action='<%=request.getContextPath()%>/reportmgr/importAndexport/query/getExportReportList.do?<%=WebConsts.FUNC_ID_KEY%>=060101';
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
					App.notyError("添加下载失败，请重试!");
					isPass =  false;
				}
			});
	return isPass;
}

</script>
</head>
<body>
<p:authFunc funcArray="020103,020104,020105,020106"/>
<form action="" method="post" id="budgetPlanSearchForm" >

	<table id="approveChainTable">
		<tr class="collspan-control">
			<th colspan="4">预算模板查找</th>
		</tr>
		<tr>
			<td class="tdLeft"> 预算类型</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="dataTypeSelect" name="dataType" >
						<option value="">请选择</option>
						<option value="0" <c:if test="${budget.dataType == '0'}">selected="selected"</c:if>>年初预算</option>
						<option value="1" <c:if test="${budget.dataType == '1'}">selected="selected"</c:if>>追加预算</option>
					</select>
				</div>
			</td>
			<td class="tdLeft">有效年份</td>
			<td class="tdRight">
				<div class="ui-widget">
					<select id="valiYearSelect" name="dataYear">
						<option value="">--请选择--</option>		
						<forms:codeTable tableName="TB_BUDGET_TMPLT" selectColumn="DISTINCT (DATA_YEAR)"
					 		valueColumn="DATA_YEAR" textColumn="DATA_YEAR" orderColumn="DATA_YEAR" 
					 		orderType="ASC" selectedValue="${budget.dataYear}"/>
					</select>
				</div>
			</td>
		</tr>
         <tr>
			<td class="tdLeft">预算指标</td>
			<td class="tdRight" >
				<div class="ui-widget">
					<select id="dataAttrSelect" name="dataAttr">
						<option value="">请选择</option>
						<forms:codeTable tableName="SYS_SELECT" selectColumn="PARAM_VALUE,PARAM_NAME"
							 valueColumn="PARAM_VALUE" textColumn="PARAM_NAME" orderColumn="SORT_FLAG" 
							 conditionStr="CATEGORY_ID = 'CNT_TYPE'" orderType="ASC" selectedValue="${budget.dataAttr}"/>
					</select>
				</div>
			</td>
		<tr>
		<tr>
			<td colspan="4" class="tdWhite">
					<p:button funcId="020103"  value="查找" />
					<input type="button" value="重置"  onclick="resetAll()">
			</td>
		</tr>
	</table>
</form>
<form action="" method="post" id="budgetPlanListForm">
	<br/>	
	<table id="listTab" class="tableList">		
		<tr id="listTabFirstTr" >		
		    <th width="20%" style="text-align: center">模板</th>	
			<th width="35%" style="text-align: center">创建部门</th>
			<th width="15%" style="text-align: center">状态</th>
			<th width="30%" style="text-align: center">操作</th>
		</tr>
		
		<c:if test="${!empty budgetList}">
			<c:forEach items="${budgetList}" var="budget" varStatus="status">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">				
				    <td width="20%" style="text-align: center" title="预算模板ID:【${budget.tmpltId}】">
				    	${budget.dataYear} - ${budget.dataAttrName} - 
				    	<c:if test="${budget.dataType=='0'}" >年初预算</c:if>
				    	<c:if test="${budget.dataType=='1'}" >追加预算</c:if>
				    </td>	
					<td width="35%" style="text-align: center" title="${budget.org21Code}">
						${budget.org2Name}
						<input type="hidden" value="${budget.org21Code}"> 
					</td>	
					<td width="15%" style="text-align: center">
						<c:if test="${budget.dataFlag=='00'}" >待处理</c:if>
						<c:if test="${budget.dataFlag=='01'}" >处理中</c:if>
						<c:if test="${budget.dataFlag=='02'}" >处理失败</c:if>
						<c:if test="${budget.dataFlag=='03'}" >处理完成</c:if>
						<c:if test="${budget.dataFlag=='04'}" >已提交</c:if>
					</td>	
					<td width="30%" style="text-align: center;vertical-align: middle">
<%-- 					    <p:button funcId="020105" type="edit" value="修改" onclick="viewOrUpdateBudget('${budget.tmpltId}' , 'update');"/> --%>
<%-- 					    <p:button funcId="020105" type="view" onclick="viewOrUpdateBudget('${budget.tmpltId}' , 'view');"/> --%>
						<c:if test="${budget.dataFlag !='04'}">
					    	<input type="button" value="修改" onclick="viewOrUpdateBudget('${budget.tmpltId}' , 'update');"/>
					    </c:if>
						<c:if test="${budget.dataFlag =='04'}">
					    	<input type="button" value="修改" disabled="disabled" onclick="viewOrUpdateBudget('${budget.tmpltId}' , 'update');"/>
					    </c:if>
					    
						<c:if test="${budget.dataFlag !='04'}">
<%-- 							<p:button funcId="020104" type="delete" value="删除" onclick="deleteBudget('${budget.tmpltId}')" /> --%>
							<input type="button" value="删除" onclick="deleteBudget('${budget.tmpltId}')"/>
						</c:if>
						<c:if test="${budget.dataFlag =='04'}">
							<!-- SQL中已限定不能删除"已提交"状态的TMPLT数据（若需要删除请从Mapper中去掉限制） -->
							<input type="button" value="删除" disabled="disabled" onclick="deleteBudget('${budget.tmpltId}')"/>
						</c:if>
<%-- 						<input type="button" value="导出" onclick="exportBudgetExcel('${budget.tmpltId}')"/> --%>
							<!-- javascript:UploadUtils.downloadFile(参数为：下载路径,下载文件名,项目根路径名称(需要查找该路径下的ERP/fileUtils/fileDownload.do来执行下载操作) -->
							<input type="button" value="导出" onclick="javascript:UploadUtils.downloadFile('${budget.serverFile}','${budget.sourceFileName }','/ERP')"/>
						<c:if test="${budget.dataFlag != '03'}">
							<input type="button" value="提交" disabled="disabled"/>
						</c:if>
						<c:if test="${budget.dataFlag == '03'}">
							<p:button funcId="020106" type="submit" value="提交" onclick="submitBudget('${budget.tmpltId}')" />
						</c:if>
					</td>	
				</tr>
			</c:forEach> 
		</c:if>
		<c:if test="${empty budgetList}">
			<tr>
				<td colspan="4" style="text-align: center;" class="red"><span>没有找到相关信息</span></td>
			</tr>
		</c:if>
	</table>
</form>
<p:page/>

</body>
</html>