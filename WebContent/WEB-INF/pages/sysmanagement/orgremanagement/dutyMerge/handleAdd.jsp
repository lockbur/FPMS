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
<title>撤并经办</title>
<script type="text/javascript">
function doValidate() {
	//提交前调用
	if(!App.valid("#orgForm")){
		return;
	}
	return true;
}
function add(){
		var flag1 = true;
		$("input[name='codeBefs']").each(function(i){
			if($(this).val()==""){
				App.notyError("撤并前代码不能为空!");
				flag1 = false;
				return;
			}
			var codeBef = $(this).val();
			$("input[name='codeBefs']").each(function(j){
				if(i != j){
					var codeBef2 = $(this).val();
					if(codeBef == codeBef2){
						App.notyError("撤并前代码不能重复!");
						flag1 = false;
						return;
					}
				}
			});
			//校验前后代码不能一样
			var codeCur = $(this).parent().next().next().children().val();
			if(codeBef == codeCur){
				App.notyError("撤并前代码不能和撤并后代码一样!");
				flag1 = false;
				return;
			}
		});
		if(flag1){
			$("input[name='codeCurs']").each(function(){
				if($(this).val()==""){
					App.notyError("撤并后代码不能为空!");
					flag1 = false;
					return;
				}
			});
		}else{
			return false;
		}
		if(!flag1){
			return false;
		}
	 
		var url='<%=request.getContextPath()%>/sysmanagement/orgremanagement/dutyMerge/handleAdd.do?<%=WebConsts.FUNC_ID_KEY%>=0104110102';
		$("#orgForm").attr("action",url);
		//校验撤并前后的责任中心必须在 
		if(ajaxCheck() == "false"){
			return false;
		}
		$( "<div>提交并复核后将按照您填写的顺序将项目中撤并前责任中心的数据都转移到相对应的撤并后的责任中心里面，确定提交吗？</div>" ).dialog({
			resizable: false,
			height:180,
			width:500,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"是": function() {
					$( this ).dialog( "close" );
					App.submit($("#orgForm"));
					return false;
				},
				"否": function() {
					$( this ).dialog( "close" );
				}
			}
		});
		
		 
		
}
//ajax前端校验 主键冲突，校验撤并前的责任中心要在批次表里面
function ajaxCheck()
{
	var codeBefArray="";
	$("input[name='codeBefs']").each(function(){
		if(""==codeBefArray){
			codeBefArray = $(this).val();
		}else{
			codeBefArray = codeBefArray + ","+ $(this).val();
		}
	});
	var codeCurArray="";
	$("input[name='codeCurs']").each(function(){
		if(""==codeCurArray){
			codeCurArray = $(this).val();
		}else{
			codeCurArray = codeCurArray + ","+ $(this).val();
		}
	});
	var type =  $("input[type=radio]:checked").val();
	var data={};
	data['type'] = type;
	data['codeBefArray'] = codeBefArray;
	data['codeCurArray'] = codeCurArray;
	var returnFlag = "";
	App.ajaxSubmit("sysmanagement/orgremanagement/dutyMerge/handleAddAjax.do?VISIT_FUNC_ID=0104110106",
			{data:data,async : false},
			function(data){
				var resultValue = data.data;
				var flag  = resultValue.flag;
				if(!flag){
					App.notyError(resultValue.msg);
					returnFlag = "false";
				}else{
					$("#invalidDate").val(resultValue.invalidDate);
					returnFlag = "true";
				}
			});
	return returnFlag;
}


function change(type){
	$("#tr1").nextAll().remove();
	$("#codeBef").val("");
	$("#codeCur").val("");
	if("01" == type){
		$("#typeBef").html("撤并前责任中心");
		$("#typeCur").html("撤并后责任中心");
	}
	if("02" == type){
		
		$("#typeBef").html("撤并前机构");
		$("#typeCur").html("撤并后机构");
	}
	
}
//ajax校验 撤并后的责任中心在 交叉表中存在，以及撤并前的责任中心是否已经有了撤并经办数据
function glxx(obj,postion){
	var codeBef = "";
	var codeCur = "";
	if("1"==postion){
		//是撤并前
		  codeBef = $(obj).val();
		  codeCur = $(obj).parent().next().next().children().val();
	}else{
		 codeBef = $(obj).parent().prev().prev().children().val();
		 codeCur = $(obj).val();
	}

	var type=$("input[type=radio]:checked").val();
	if("01" == type){
		typeName ="责任中心";
 	}else{
 		typeName = "机构";
 	}
	if(codeBef !="" && codeCur !=""){
		var data = {};
		data['codeBef'] = codeBef;                            
		data['codeCur'] = codeCur;
		data['type'] = type;
		App.ajaxSubmit("sysmanagement/orgremanagement/dutyMerge/glxxAjax.do?VISIT_FUNC_ID=0104110108",
				{data:data,async : false},
				function(data){
					var resultValue = data.data;
					if(resultValue.flag == "N"){
						$("#flag").val(resultValue.flag);
						$("#flag").attr("name",resultValue.msg);
						App.notyError(resultValue.msg);
					}else if(resultValue.flag == "W"){
						var des = resultValue.msg;
						if(""!=des){
							des =des+"<br>是否继续经办";
						}
						$( "<div>"+des+"</div>" ).dialog({
							resizable: false,
							height:180,
							width:450,
							modal: true,
							dialogClass: 'dClass',
							buttons: {
								"是": function() {
									$( this ).dialog( "close" );
									return false;
								},
								"否": function() {
									$( this ).dialog( "close" );
								}
							}
						});
					}
					
					
					
				});
	}
	
}
//撤并前数据
function mergeDutys(obj){
	var type=$("input[type=radio]:checked").val();
	if("01" == type){
		typeName ="责任中心";
 	}else{
 		typeName = "机构";
 	}
	$.dialog.open(
			'<%=request.getContextPath()%>/sysmanagement/orgremanagement/dutyMerge/mergeDutys.do?<%=WebConsts.FUNC_ID_KEY %>=0104110109&type='+type,
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"撤并"+typeName+"选择",
			    id:"dialogCutPage",
				close: function(){
					var returnValue = art.dialog.data('data'); 
					if(returnValue){
						$(obj).prev().val(returnValue.dutyCode);
					}
				}		
			}
		 );
}
 
/**
 * 该栏位默认为 text， 当点击“修改”按钮时 text变为 input同时“修改”按钮名称变更为 "保存、取消”。
 	点保存时前后有变化才允许保存；点取消时，此行数据恢复初始状态（text + 修改）。
 */
//修改
function modify(obj){
	$(obj).parent().parent().parent().find("div").toggle();
	$(obj).parent().parent().parent().find("input[name='codeBef']").show();
	$(obj).parent().parent().parent().find("input[name='codeCur']").show();
}
//取消
function cancel(obj){
	$(obj).parent().parent().parent().find("div").toggle();
}
//保存ajax校验
function save(obj){
	var codeBef = $(obj).parent().parent().parent().find("input[name='codeBef']").val();
	var codeCur = $(obj).parent().parent().parent().find("input[name='codeCur']").val();
	var codeCurBefore = $(obj).parent().parent().parent().find("input[name='codeCurBefore']").val();
	var seq = $(obj).parent().parent().parent().find("input[name='seq']").val();
	var batchNo = $("#batchNo").val();
	if(codeCur==null||codeCur==''){
		App.notyError("撤并后的值不能为空！");
		return ;
	}
	var changeType = $(obj).parent().parent().parent().find("input[name='changeType']").val();
	var data1={};
	data1['codeCur']=codeCur;
	data1['codeBef']=codeBef;
	data1['changeType']=changeType;
	data1['batchNo']=batchNo;
	data1['seq']=seq;
	
	App.ajaxSubmit("sysmanagement/orgremanagement/dutyMerge/ajaxSave.do?<%=WebConsts.FUNC_ID_KEY %>=0104110105",{
		data : data1,
		async : false
	}, function(data) {
		var dmBean = data.dmBean;
	    var dutyName = dmBean.nameCur;
		$(obj).parent().parent().parent().find("div").toggle();
		$(obj).parent().parent().parent().find("input[name='codeCurBefore']").val(dmBean.codeCur);
		if (dutyName =="" || dutyName==null || dutyName =='null'){
			$(obj).parent().parent().parent().find("input[name='codeCur']").parent().prev().html(dmBean.codeCur);
		}else{
			$(obj).parent().parent().parent().find("input[name='codeCur']").parent().prev().html(dmBean.codeCur+"-"+dutyName);
		}
		
	});
	
}
//提交
//  提交前先ajax检查，是否  {batch_no} 的tb_change_detail中的数据中的code_cur字段都已经有值。
//没有则不允许提交，提示原因；提交时，后台将该批次在tb_fndwrr_change表中的记录状态更新为 01-待复核。
function submitOrg(){
	var batchNo = $("#batchNo").val();
	var data1 = {};
	data1['batchNo']=batchNo;
	App.ajaxSubmit("sysmanagement/orgremanagement/dutyMerge/ajaxSubmitValidate.do?<%=WebConsts.FUNC_ID_KEY %>=0104110110",{
		data : data1,
		async : false
	}, function(data) {
		if(data.flag){
			var url='<%=request.getContextPath()%>/sysmanagement/orgremanagement/dutyMerge/submit.do?<%=WebConsts.FUNC_ID_KEY%>=0104110111';
			$("#orgForm").attr("action",url);
			App.submit($("#orgForm"));
		}else{
			$( "<div>"+batchNo+"批次下存在未撤并的数据，请全部撤并完再提交！</div>" ).dialog({
				resizable: false,
				height:'auto',
				modal: true,
				dialogClass: 'dClass',
				buttons: {
					"关闭": function() {
						$( this ).dialog( "close" );
					}
				}
			});
		}
	});
}
</script>
</head>
<body>
<p:authFunc funcArray="0104110102"/>
<form action="" method="post" id="orgForm">
<input type = "hidden" value="${status}" name="status"/>
	<p:token/>
	<table id="table1">
		<tr>
			<th colspan="4">撤并经办</th>
		</tr>
		<tr>
			<td class="tdRight" colspan="4">
			<input type="hidden" value="${batchNo }" id="batchNo" name="batchNo"/>
			待处理的撤并日期：${dealDate}<br><br>
			以下列表的数据日期：${listDate}<br><br>
			数据说明：${memo }<br>
			</td>
		</tr>
	</table>
	<table class="tableList">
		<tr>
			<th width="15%">撤并类型</th>
			<th width="35%">撤并前</th>
			<th width="35%">撤并后</th>
			<th width="15%">操作</th>
		</tr>
		<c:if test="${!empty list }">
			<c:forEach items="${list }" var="bean">
			<tr>
				<td>
					<input type="hidden" name="changeType" value="${bean.changeType}"/>
					<input type="hidden"  name="codeCurBefore" value="${bean.codeCur}"/>
					<input type="hidden" name="seq" value="${bean.seq}"/>
					<c:if test="${bean.changeType == '01' }">责任中心撤并</c:if>
					<c:if test="${bean.changeType == '02' }">机构撤并</c:if>
				</td>
				<td>${bean.codeBef}-${bean.nameBef}<input type="hidden"  name="codeBef" value="${bean.codeBef}"/></td>
				<td>
					<div>${bean.codeCur}-${bean.nameCur}</div>
					<div style="display: none;"><input type="text"  class="base-input-text" name="codeCur" value="${bean.codeCur}"/></div></td>
				<td>
					<div><input type="button" value="修改" onclick="modify(this);"/></div>
					<div style="display: none;"><input type="button" value="保存" onclick="save(this);"/><input type="button" value="取消" onclick="cancel(this);"/></div>
				</td>
			</tr>
			</c:forEach>
		</c:if>
		<c:if test="${empty list }">
			<tr>
				<td colspan="4" style="text-align: center;"><span class="red">没有相关记录</span></td>
			</tr>
		</c:if>
	</table>
	<c:if test="${!empty list }">
	<div align="center" style="margin-top: 10px;">
		 <input type="button" value="提交" onclick="submitOrg()"/>
	</div>
	</c:if>
</form>
<!-- <table> -->
<!-- 	<tr> -->
<!-- 		<td class="tdRight"> -->
<!-- 			<span style="color: red"> -->
<!-- 				**温馨提示： 1:填写的时候请注意按照责任中心或者机构的失效日期的顺序填写。 -->
<!-- 				<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 						2:同一个经办操作里只能填写相同失效日期内的撤并关系。 -->
<!-- 				<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 						3:至少有一个撤并前的责任中心和机构发生了撤并。 -->
<!-- 				<br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 						4:如果在相同失效日期内有关联撤并最好在同一个经办里都填写完，例如A->B,B->C或者B->C,A->B或者A->C,B->C等等。 -->
<!-- 			</span> -->
<!-- 		</td> -->
<!-- 	</tr> -->
<!-- </table> -->
</body>
</html>