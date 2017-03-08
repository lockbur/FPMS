	<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="com.forms.platform.web.WebUtils"%>
<%@page import="com.forms.platform.web.consts.WebConsts"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="/platform-tags" prefix="p" %>
<%@ taglib uri="http://www.formssi.com/taglibs/froms" prefix="forms" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预提经办</title>
<style type="text/css">
	.tbHead th{
		text-align: center;
	}
	.tableList tr td{
		text-align: center;
	}
</style>
<script type="text/javascript">
//页面初始化执行方法
function pageInit() {
	App.jqueryAutocomplete();
	$("#dataFlagSelect").combobox();
	$("#provFlagSelect").combobox();
}

//重置按钮执行方法
function resetAll() {
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

//var provQueryIds = "${allQueryIdList}";				//用于接收查询到的当次所有的预提经办ID(使用逗号进行分隔)

$(function(){
// 	console.info('查询到所有的ID：【'+provQueryIds+'】');
	var allPageCheck = "";			//用于处理Check所有查询数据的勾选，取得组内父CheckBox的勾选状态
	var teamChecks = "";			//用于处理Check所有查询数据的勾选，取得组内子CheckBox的勾选状态(字符串格式，逗号分隔)
	
	//给组内父CheckBox绑定Click点击事件(根据父CheckBox的勾选决定子CheckBox是否全勾选)
	$("#allPageData").click(
		function(){
			if($(this).is(":checked")){
// 				console.info('勾了');
				$("input[name='cntNumCheckboxs']").each(
	 				function(){
	 	 				$(this).prop("checked",true);
	 				}
	 			);
			}else{
				$("#allQueryData").removeAttr("checked");
// 				console.info('没勾');
				$("input[name='cntNumCheckboxs']").each(
					function(){
						$(this).removeAttr("checked");
					}
				);
			}
// 			allCheckedRelateFunc();
		}
	);
	
	
	//为提交所有查询数据CheckBox绑定Click点击事件
	$("#allQueryData").click(
		function(){
			if($(this).is(":checked")){
				teamChecks = "";
				//首先获取关联CheckBox的勾选状态
				allPageCheck = $("#allPageData").is(":checked");
// 				console.info('allPageCheck勾选的状况：【'+allPageCheck+'】');
				
				$("input[name='cntNumCheckboxs']").each(
					function(){
						teamChecks += $(this).is(":checked")+",";
					}		
				);
				teamChecks = teamChecks.substr(0,teamChecks.length-1);
// 				console.info('每个勾选的状况：【'+teamChecks+'】');
				
				//如果其勾上，则把关联的也勾选上
				$("input[name='cntNumCheckboxs']").each(
	 				function(){
	 	 				$(this).prop("checked",true);
	 				}
	 			);
				$("#allPageData").prop("checked",true);
			}else{
// 				console.info('待会做');
				//恢复AllPageCheck原来的勾选状态
				if(allPageCheck){
					$("#allPageData").prop("checked",true);
				}else{
					$("#allPageData").removeAttr("checked");
				}
				//恢复cntNumCheckboxs选择框组的原始勾选状态
				var teamCheckArray = teamChecks.split(",");
				for(var i=0;i<teamCheckArray.length;i++){
// 					console.info('每一次：'+teamCheckArray[i]);
					if(teamCheckArray[i] == "true"){
// 						console.info('原来是勾上的');
						$($("input[name='cntNumCheckboxs']")[i]).prop("checked",true);
					}else{
						$($("input[name='cntNumCheckboxs']")[i]).removeAttr("checked");
					}
				}
				
				//恢复原始值
				allPageCheck = "";
				teamChecks = "";
			}
		}		
	);
		
	
	//给组内子CheckBox绑定点击事件(决定子与父CheckBox是否勾选)
	$("input[name='cntNumCheckboxs']").click(
		function(){
			teamChecks = "";
			$("input[name='cntNumCheckboxs']").each(
				function(){
					teamChecks += $(this).is(":checked")+",";
				}		
			);
			teamChecks = teamChecks.substr(0,teamChecks.length-1);
// 			console.info('每个勾选的状况：【'+teamChecks+'】');	
			allCheckedRelateFunc();
		}
	);
	
	
	
	function allCheckedRelateFunc(){
		var checkedAllFlag = true;	//单个勾选操作，如果判断最后一个也被勾选时，则将全选也勾上
		//检查改组中是否所有CheckBox全被勾选上
		$("input[name='cntNumCheckboxs']").each(function(){
			if(!$(this).is(":checked")){
				checkedAllFlag = false;
			}
		});
		//如果最后一个也勾上，则将该CheckBox的组长也勾选上；如果flag为false并且组长为勾选状态时，则将组长的勾选去掉
		if(checkedAllFlag){
			$("#allPageData").prop("checked",true);
		}
		else{
			if($("#allPageData").is(":checked")){
				$("#allPageData").removeAttr("checked");
			}
			if($("#allQueryData").is(":checked")){
				$("#allQueryData").removeAttr("checked");
			}
		}
	}
})

//【工具类】：校验一组CheckBox中是否存在勾选中的，存在勾选的返回true，否则返回false
function judgeCheckboxGroupHasChecked( checkboxGroupName ){
	if($("input[name='"+checkboxGroupName+"']:checked").length < 1){
// 		console.info('No,Return FALSE');
		return false;
	}else{
		return true;
	}
}

//预算经办提交时调用方法
function provHandleSubmit(){
	var provMgrIdList = "";
	
	//1.首先判断必须勾选至少一个CheckBox，否则提示错误信息并且不让提交
	if(!judgeCheckboxGroupHasChecked("cntNumCheckboxs")){
		App.notyError("请选择需要提交的预提经办！");
		return;
	}
	//2.判断[是否需要预提]是否已选择(必须选择才能通过)
	var provFlag = $("#provFlagSelect").val();
	if(null == provFlag || "" == provFlag){
		App.notyError("请决定改选中经办是否需要预提");
		return;
	}
	
	//3.判断是当前页数据提交还是全查询数据提交，再执行相应的处理
	if($("#allQueryData").is(":checked")){
		//3-1.【所有查询到的数据提交】获取后台传过来的所有ID，作为请求的参数调用
// 		console.info('全数据提交');

		provMgrIdList = "";			//provQueryIds为全局变量，用于储存查询到信息数据的所有ID值
// 		console.info('全数据查询得到并且请求提交时的ID：【'+provMgrIdList+'】');
	}else{
		//3-2.【当前页数据提交】拼接需要提交的预提经办ID(以逗号进行各ID的分隔)
// 		console.info('当前页数据提交');
		
		$("input[name='cntNumCheckboxs']").each(
			function(){
				if($(this).is(":checked")){
					provMgrIdList += $(this).val()+",";
				}
			}	
		);
		provMgrIdList = provMgrIdList.substring(0,provMgrIdList.length-1);
		//console.info('最终提交的ID：'+provMgrIdList);
	}
	//4.提交预提经办请求
	var form = $("#submitForm")[0];
   	form.action="<%=request.getContextPath()%>/amortization/provisionMgr/provHandleSubmit.do?<%=WebConsts.FUNC_ID_KEY %>=04050101&provMgrIdList="+provMgrIdList;
   	App.submit(form);
}


/**
 * 测试：
 */
function testFunc(){
	
}

function addDownload(){	
	var cntNum = $("#cntNum").val();
	var createDepts = $("#queryCreateDeptList").val();
	var dataFlag = $("#dataFlagSelect").val();
	var isPass = false;
	var data = {};
	data['cntNum'] =  cntNum;
	data['createDepts'] =  createDepts;
	data['dataFlag'] =  dataFlag;
	App.ajaxSubmit("amortization/provisionMgr/addDownLoad.do?VISIT_FUNC_ID=04050102",
			{data:data,async : false},
			function(data){
				flag = data.pass;
				if(!flag){
					App.notyError("添加下载失败，请重试!");
					isPass =  false;
				}
				else
				{
					$("<div>Excel已成功提交后台处理<br\>是否进入下载页面？</div>").dialog({
						resizable: false,
						width: 290,
						height:'auto',
						modal: true,
						title: "跳转下载页面",
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
			});
	return isPass;
}


</script>
</head>

<body>
<p:authFunc funcArray="040501,04050101"/>
<form action="" method="post" id="queryForm" >
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				预提经办
				<input type="hidden" name="testOrg1Code" value="${provision.org1Code}">
			</th>
		</tr>
		<tr class="collspan-control">
			<th colspan="4" style="fontWeight:bold;color:red;">
				总账接口数据上送截止日期及时间【每月末最后${deadlineDay}个自然日${deadlineTime}时】！
			</th>
		</tr>
		<tr>
			<td class="tdLeft">合同号</td>
			<td class="tdRight">
				<input type="text" name="cntNum" id="cntNum" value="${provision.cntNum}" class="base-input-text" maxlength="80"/>
			</td>
			<td class="tdLeft" title="根节点：${provision.org1Code}">责任中心</td>
			<td class="tdRight" title="${provision.createDept}">
				<forms:OrgSelPlugin rootNodeId="${provision.org1Code}" initValue="${provision.createDept}" jsVarGetValue="createDept"  radioFlag="false" leafOnlyFlag="true"  triggerEle="#queryForm createDept,queryCreateDeptList::name"/>
<%-- 				<forms:OrgSelPlugin suffix="1" radioFlag="false" selHalfFlag="false" dynamicUpdateFlag="true"  --%>
<%--  									jsVarName="treePlugin1" rootNodeId="${provision.org1Code}" leafOnlyFlag="true"  --%>
<%--  									initValue="${provision.createDept}" changeFun="" triggerEle="#queryForm createDept,queryCreateDeptList::name"/>  --%>
 				<input class="base-input-text" type="text" id="createDept" name="createDeptCutShow" readonly="readonly" valid errorMsg="请选择合同创建部门"/>
 				<input type="hidden" id="queryCreateDeptList" name="createDepts">
			</td>
			
<%-- 			<td class="tdRight" title="${selectedDepts}"> --%>
<%-- 				<forms:OrgSelPlugin suffix="1" radioFlag="false" selHalfFlag="false" dynamicUpdateFlag="true"  --%>
<%--  									jsVarName="createDept" rootNodeId="${provision.org1Code}" leafOnlyFlag="true"  --%>
<%--  									initValue="${provision.createDept}" changeFun="" triggerEle="#queryForm createDept,queryCreateDeptList::name"/>  --%>
<!--  				<input class="base-input-text" type="text" id="createDept" name="createDeptCutShow" readonly="readonly" valid errorMsg="请选择合同创建部门"/> -->
<!--  				<input type="hidden" id="queryCreateDeptList" name="createDepts"> -->
<!-- 			</td> -->
			
		</tr>
		<tr>
			<td class="tdLeft">状态</td>
			<td class="tdRight" colspan="3">
				<div class="ui-widget">
					<select id="dataFlagSelect" name="dataFlag" valid errorMsg="请选择预算指标">
						<option value="">请选择</option>
						<option value="0" <c:if test="${provision.dataFlag == '0'}">selected="selected"</c:if> >待处理</option>
						<option value="2" <c:if test="${provision.dataFlag == '2'}">selected="selected"</c:if> >复核退回</option>
						
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="040501" value="查找"/>
<!-- 				<input type="button" value="查找" onclick="queryProvHandle();"> -->
				<input type="button" value="重置" onclick="resetAll();">
<!-- 				<input type="button" value="测试方法" onclick="testFunc();"> -->
				<input type="button" value="添加下载" onclick="addDownload();">
			</td>
		</tr>
	</table>
	<br>
	<div id="queryListAreaDiv" >
		<table class="tableList" id="queryResultTb" >
		   <tr class="tbHead">
				<th width="50%" colspan="4">预提总金额：${provisionAmtSum}</th>
				<th width="50%" colspan="5">待摊总金额：${prepaidAmtSum}</th>
			</tr>
			<tr ><td colspan="9"></td></tr>
			<tr class="tbHead">
				<th width="8%">选择</th>
				<th width="17%">合同号</th>
				<th width="12%">物料编码</th>
				<th width="17%">监控指标</th>
				<th width="9%">核算码</th>
				<!-- <th width="12%">AP发票校验状态</th> -->
				<th width="9%">预提金额</th>
				<th width="9%">待摊金额</th>
				<th width="19%">预提详情</th>
			</tr>
			
			<c:forEach items="${proList}" var="proBean">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td title="${proBean.provMgrId}"><input type="checkbox" name="cntNumCheckboxs" value="${proBean.provMgrId}"> </td>
					<td title="【合同事项：${proBean.cntName}】">${proBean.cntNum}</td>
					<%-- 【受益年月：${proBean.feeYyyymm}】 --%>
					<td>${proBean.matrCode}</td>
					<td>${proBean.montCode}</td>
					<td>${proBean.feeCglCode}</td>
					<%-- <td>${proBean.importStatus}</td> --%>
					<td>${proBean.provisionAmt}</td>
					<td>${proBean.prepaidAmt}</td>
					<td>${proBean.dataFlag}</td>
				</tr>
			</c:forEach>
			
			<c:if test="${empty proList}">
				<tr>
					<td colspan="9" style="text-align: center;"><span class="red">没有找到相关的预提经办信息！</span></td>
				</tr>
			</c:if>
			
		</table>
		
	</div>
</form>
<br/>
<p:page/>
<p></p>
<form method="post" id="submitForm" action="">
	<br/>
	<br/>
	<br/>
	<table>
		<!-- 开始 -->
		<tr>
			<th colspan="4">经办提交</th>
		</tr>
		<tr>
			<td class="tdLeft">提交数据：</td>
			<td class="tdRight" >
				<input type="checkbox" name="queryDataType" id="allPageData">全选本页所有数据 <div style="display: inline;">&nbsp;</div>
				<input type="checkbox" name="queryDataType" id="allQueryData">全选查询到所有数据
				
			</td>
			<td class="tdLeft">预提选择：</td>
			<td class="tdRight">
				<div class="ui-widget" style="display: inline;">
					<select id="provFlagSelect" name="provFlag" valid errorMsg="请选择是否预提">
						<option value="">请选择</option>
						<option value="0">不需要预提</option>
						<option value="1">需要预提</option>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4" style="border-bottom-style: none;border-left-style: none;border-right-style: none; border-top-style: none;">
				<input type="button" value="提交" onclick="provHandleSubmit()">
			</td>
		</tr>
		<!-- 结束 -->
	</table>
</form>
</body>
</html>