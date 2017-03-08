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
<title>预提复核</title>
<style type="text/css">
	.tbHead th{
		text-align: center;
	}
	.tableList tr td{
		text-align: center;
	}
</style>
<script type="text/javascript">
function pageInit() {
	//调整下拉框样式
	App.jqueryAutocomplete();
	$("#cntType").combobox();
	$("#stateSelect").combobox();
	$("#provFlagSelect").combobox();
	$("#provSubmitStatusSelect").combobox();
}


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

// function provRecheckSubmitCheck(){
// 	$("input[name='cntNumCheckboxs']")
// }

//【工具类】：校验一组CheckBox中是否存在勾选中的，存在勾选的返回true，否则返回false
function judgeCheckboxGroupHasChecked( checkboxGroupName ){
	if($("input[name='"+checkboxGroupName+"']:checked").length < 1){
// 		console.info('No,Return FALSE');
		return false;
	}else{
		return true;
	}
}


//预算复核(通过/退回)
function reCheckedProv(){
	var userChooseOper = $("#provSubmitStatusSelect").val();
	if(!userChooseOper){
		//未选择预算复核的具体操作
		App.notyError("请选择预提复核的操作(通过/退回)");
		return;
	}
	var userChooseOperDesc = ( userChooseOper == "3"? "通过":"退回");
	if($("#allQueryData").is(":checked")){		//处理多个复核
// 		console.info('进入全通过');
		//全通过执行
// 		console.info('${queryAllProvList}'.length);
		if("${queryAllProvList}".length > 0){
			$( "<div>确认 "+userChooseOperDesc+" 所有预提?</div>" ).dialog({
				resizable: false,
				height:150,
				modal: true,
				dialogClass: 'dClass',
				buttons: {
					"确定": function() {
						//1.获取当前次查询到所有数据的ID
						var reCheckIdList = "";
						//2.调用复核操作，执行相应的复核通过和复核退回
						var form = $("#queryForm")[0];
			 			form.action="<%=request.getContextPath()%>/amortization/provisionMgr/provRecheckUpdate.do?<%=WebConsts.FUNC_ID_KEY %>=04050201&provMgrIdList="+reCheckIdList+"&dataFlag="+userChooseOper;
						App.submit(form);
					},
					"取消": function() {
						$( this ).dialog( "close" );
					}
				}
			});
		}else{
			App.notyError("没有可以通过复核操作的预提!");
		}
		
	}else{//处理单页复核
		var provMgrIdList = "";
		var reCheckDataFlag	= $("#provSubmitStatusSelect").val();		//取得复核操作(通过/退回，通过页面上的下拉选择框的值获取)
		if(!judgeCheckboxGroupHasChecked("cntNumCheckboxs")){
			App.notyError("请勾选需要进行复核的预提!");
		}else if(reCheckDataFlag.length == 0){
			//reCheckDataFlag.length == 0		//App.valid("#provSubmitStatusSelect")
			App.notyError("请选择预提复核的操作(通过/退回)");
			//定位到下拉框让用户选择
			return ;
		}else{//通过提交校验后，对勾选的预提执行复核操作
			$("input[name='cntNumCheckboxs']").each(
				function(){
					if($(this).is(":checked")){
						provMgrIdList += $(this).val()+",";
					}
				}	
			);
			provMgrIdList = provMgrIdList.substr(0,provMgrIdList.length-1);
//	 		console.info('提交的ID:'+provMgrIdList);
			var form = $("#queryForm")[0];
 			form.action="<%=request.getContextPath()%>/amortization/provisionMgr/provRecheckUpdate.do?<%=WebConsts.FUNC_ID_KEY %>=04050201&provMgrIdList="+provMgrIdList+"&dataFlag="+reCheckDataFlag;
			App.submit(form);
			
		}
	}
}


// function someTest(){
// 	var tableList = $("#queryResultTb").find("tr:gt(0)");
// 	console.info($(tableList[0]).find("td:eq(1)")[0]);
// }



function testGetOrg(){
// 	console.info('选中的创建Org条件为：');
// 	console.info($("#queryCreateDeptList").val());
// 	console.info($("#createDept")[0].title);


// 	console.info($("#queryCreateDeptList").val());

}


var provQueryIds = "${allQueryIdList}";				//用于接收查询到的当次所有的预提经办ID(使用逗号进行分隔)

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



</script>
</head>

<body>
<p:authFunc funcArray="040502"/>
<form action="" method="post" id="queryForm" action="">
	<table>
		<tr class="collspan-control">
			<th colspan="4">
				预提复核
				<input type="hidden" name="org1Code" value="${provision.org1Code}">
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
				<input type="text" name="cntNum" value="${provision.cntNum}" class="base-input-text" maxlength="80"/>
			</td>
			<td class="tdLeft">责任中心</td>
			<td class="tdRight" title="${provision.createDept}">
				<forms:OrgSelPlugin rootNodeId="${provision.org1Code}" initValue="${provision.createDept}" jsVarGetValue="createDept"  radioFlag="false" leafOnlyFlag="true"  triggerEle="#queryForm createDept,queryCreateDeptList::name"/>
<%-- 				<forms:OrgSelPlugin suffix="1" radioFlag="false" selHalfFlag="false" dynamicUpdateFlag="true"  --%>
<%--  									jsVarName="treePlugin1" rootNodeId="${provision.org1Code}" leafOnlyFlag="true"  --%>
<%--  									initValue="${provision.createDept}" changeFun="" triggerEle="#queryForm createDept,queryCreateDeptList::name"/>  --%>
 				<input class="base-input-text" type="text" id="createDept" name="createDeptCutShow" readonly="readonly" valid errorMsg="请选择合同创建部门"/>
 				<input type="hidden" id="queryCreateDeptList" name="createDepts">
			</td>
			
			
			
<!-- 			<td class="tdRight" >	title="${selectedDepts}" -->
<%-- 				<forms:OrgSelPlugin suffix="1" radioFlag="false" selHalfFlag="false" dynamicUpdateFlag="true"  --%>
<%--  									jsVarName="createDept" rootNodeId="${provision.org1Code}" leafOnlyFlag="true"  --%>
<%--  									initValue="${provision.createDept}" changeFun="" triggerEle="#queryForm createDept,queryCreateDeptList::name"/>  --%>
<!--  				<input class="base-input-text" type="text" id="createDept" name="createDeptCutShow" readonly="readonly" valid errorMsg="请选择合同创建部门"/> -->
<!--  				<input type="hidden" id="queryCreateDeptList" name="createDepts"> -->
<!-- 			</td> -->
 				
 				
<!--  				<td class="tdRight" > -->
<%-- 				<forms:OrgSelPlugin suffix="1" radioFlag="false" selHalfFlag="false" parentCheckFlag="true" leafOnlyFlag="true" triggerEle="#addBudgetForm orgIdName,availableOrgList::name" changeFun="writeCheckedValue" jsVarName="treePlugin1" rootNodeId="${currentUserOrg21Code}" /> --%>
<!-- 				<input type="hidden" id="availableOrgList" name="availableOrgList" class="base-input-text" /> -->
<!-- 				<input class="base-input-text" type="text" id="orgIdName" name="orgIdName" readonly="readonly" valid errorMsg="请选择可用机构"/> -->
<!-- 				</td> -->
		</tr>
		<tr>
		
			<td class="tdLeft">是否预提</td>
			<td class="tdRight" colspan="3">
				<div class="ui-widget">
					<select id="provFlagSelect" name="provFlag" valid errorMsg="请选择是否需要预提">
						<option value="">请选择</option>
						<option value="0" <c:if test="${provision.provFlag == '0'}">selected="selected"</c:if> >不需要预提</option>
						<option value="1" <c:if test="${provision.provFlag == '1'}">selected="selected"</c:if> >需要预提</option>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4" class="tdWhite">
				<p:button funcId="040502" value="查找"/>
				<input type="button" value="重置" onclick="resetAll();">
<!-- 				<input type="button" value="TEST机构选择" onclick="testGetOrg()"> -->
<!-- 				<input type="button" value="Test" onclick="someTest();"> -->
				
			</td>
		</tr>
	</table>
	<br>
	<div id="queryListAreaDiv">
		<table class="tableList" id="queryResultTb" width="95%">
			<tr class="tbHead">
				<th width="8%">选择</th>
				<th width="14%">合同号</th>
				<th width="8%">合同事项</th>
				<th width="11%">物料编码</th>
				<th width="17%">监控指标</th>
				<th width="8%">核算码</th>
			<!-- 	<th width="12%">AP发票校验状态</th> -->
				<th width="8%">预提金额</th>
				<th width="8%">待摊金额</th>
				<th width="8%">是否预提</th>
				<th width="5%">经办人 </th>
				<th width="5%">复核人</th>
			</tr>
			
			
			<c:forEach items="${provrecheckList}" var="proRecheckBean">
				<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
					<td title="${proRecheckBean.provMgrId}">
					<c:if test="${proRecheckBean.operUser != loginUser}">
						<input type="checkbox" name="cntNumCheckboxs" value="${proRecheckBean.provMgrId}">
					</c:if> 
					</td>
					<td title="【受益年月：${proRecheckBean.feeYyyymm}——复核状态：${proRecheckBean.dataFlag}】">${proRecheckBean.cntNum}</td>
					<td>${proRecheckBean.cntName}</td>
					<td>${proRecheckBean.matrCode}</td>
					<td>${proRecheckBean.montCode}</td>
					<td>${proRecheckBean.feeCglCode}</td>
					<%-- <td>${proRecheckBean.importStatus}</td> --%>
					<td>${proRecheckBean.provisionAmt}</td>
					<td>${proRecheckBean.prepaidAmt}</td>
					<td>${proRecheckBean.provFlag2}</td>
					<td>${proRecheckBean.operUser}</td>
					<td>${proRecheckBean.checkUser}</td>
				</tr>
			</c:forEach>
			
			<c:if test="${empty provrecheckList}">
				<tr>
					<td colspan="11" style="text-align: center;"><span class="red">没有查询到待复核的预提信息！</span></td>
				</tr>
			</c:if>
			
		</table>
		
	</div>
</form>
<p:page/>
<form method="post" id="submitForm" action="">
	<br/>
	<br/>
	<br/>
	<table>
		<!-- 开始 -->
		<tr>
			<th colspan="4">复核提交</th>
		</tr>
		<tr>
			<td class="tdLeft">选择数据：</td>
			<td class="tdRight" >
				<input type="checkbox" name="queryDataType" id="allPageData">全选本页所有数据 <div style="display: inline;">&nbsp;</div>
				<input type="checkbox" name="queryDataType" id="allQueryData">全选查询到所有数据
				
			</td>
			<td class="tdLeft">预提复核：</td>
			<td class="tdRight">
				<div class="ui-widget" style="display: inline;">
					<select id="provSubmitStatusSelect" name="provSubmitStatus" valid errorMsg="请选择预提复核操作">
						<option value="">请选择</option>
						<option value="3">复核通过</option>
						<option value="2">复核退回</option>
					</select>
				</div>
			</td>
		</tr>
		<tr>
			<td colspan="4" style="border-bottom-style: none;border-left-style: none;border-right-style: none; border-top-style: none;">
				<input type="button" value="提交复核" onclick="reCheckedProv()">
			</td>
		</tr>
		<!-- 结束 -->
	</table>
</form>
</body>
</html>