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
<title>机构撤并编辑</title>
<script type="text/javascript">
function doValidate() {
	//提交前调用
	if(!App.valid("#orgForm")){
		return;
	}
}
function edit(){
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
 
	var url='<%=request.getContextPath()%>/sysmanagement/orgremanagement/dutyMerge/edit.do?<%=WebConsts.FUNC_ID_KEY%>=0104110303';
	$("#orgForm").attr("action",url);
	//校验撤并前后的责任中心必须在 
	if(!ajaxCheck()){
		return;
	};
	$( "<div>复核后将按照您填写的顺序将项目中撤并前责任中心的数据都转移到相对应的撤并后的责任中心里面，确定提交吗？</div>" ).dialog({
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
	var type =  $("#type").val();
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
//ajax校验 撤并后的责任中心在 交叉表中存在，以及撤并前的责任中心是否已经有了撤并经办数据
function glxx(obj,position){
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
	var type=$("#type").val();
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

function addTr(obj){
	var type=$("#type").val();
	if("01" == type){
		typeName ="责任中心";
 	}else{
 		typeName = "机构";
 	}
	var html = "<tr><td class='tdLeft' id='typeBef' >撤并前"+typeName;
	html+="</td>";
	html+="<td class='tdRight'>";
	html+="<input   name='codeBefs'  onchange='glxx(this,\"1\")'     class='base-input-text' maxlength='5' valid errorMsg='请输入撤并前责任中心代码'/>";
	html+="<a href='#' onclick='mergeDutys()'>";
	html+="<img border='0px' src='<%=request.getContextPath()%>/common/images/search1.jpg' alt='查看撤并的责任中心' style='cursor:pointer;vertical-align: middle;height:30px;width:30px;'/></a>";
	html+="</td><td class='tdLeft'>撤并后"+typeName+"</td>";
	html+="<td class='tdRight'>";
	html+="<input    name='codeCurs'  onchange='glxx(this,\"2\")'   class='base-input-text' maxlength='5' valid errorMsg='请输入撤并后责任中心代码'/>";
	html+="<img id='addlxslImg'   border='0' vaild width='15px' src='<%=request.getContextPath()%>/common/images/add_normal.png' alt='添加' onclick='addTr(this)'>";
	html+="<img border='0' alt='删除'  width='30px' height='30px' src='<%=request.getContextPath()%>/common/images/delete1.gif' onclick='deleteTr(this)' style='vertical-align: middle;'/>";
	html+="</td></tr>";
	$(obj).parent().parent().after(html);
}
function deleteTr(obj){
	$(obj).parent().parent().remove();
}
</script>
</head>
<body>
<p:authFunc funcArray="0104110303"/>
<form action="" method="post" id="orgForm">
<input type="hidden" id="menuTag" name="menuTag" value="${bean.menuTag }"/>
<input type="hidden" id="id" name="id" value="${bean.id }"/>
<input type="hidden" id="invalidDate" name="invalidDate"  />
	<p:token/>
	<table>
		<tr>
			<th colspan="4" class="tdWhite">
				<c:if test="${menuTag =='02' }">
					撤并审批
				</c:if>
				<c:if test="${menuTag !='02' }">
					撤并经办修改
				</c:if>
			</th>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">类型</td>
			<td class="tdRight"  >
				<c:if test="${bean.type =='01' }">责任中心撤并<input id="type" type="hidden" value="${bean.type }"/></c:if>
				<c:if test="${bean.type=='02' }">机构撤并<input id="type" type="hidden" value="${bean.type }"/></c:if>
				<input type="hidden" name="type" id="type" value="${bean.type }"/>
			</td>
			<td class="tdLeft" width="25%">状态</td>
			<td class="tdRight" width="25%">
				<c:if test="${bean.status == '01' }">
					经办待复核					
				</c:if>
				<c:if test="${bean.status == '02' }">
					复核通过						
				</c:if>
				<c:if test="${bean.status == '03' }">
					复核不通过						
				</c:if>
			</td>
		</tr>
		<c:forEach items="${list }" var="beans" varStatus="i">
		<tr>
			<td class="tdLeft" >撤并前</td>
			<td class="tdRight">
				<input    name="codeBefs" value="${beans.codeBef }" onchange="glxx(this,'1')"   class="base-input-text" maxlength="5"  valid errorMsg="请输入撤并前责任中心代码"/>
				
			</td>
			<td class="tdLeft" >撤并后</td>
			<td class="tdRight">
					<input  name="codeCurs" onchange="glxx(this,'2')" value="${beans.codeCur }" class="base-input-text" maxlength="5" valid errorMsg="请输入撤并后责任中心代码"/>
					<img     border="0" vaild width="15px" src='<%=request.getContextPath()%>/common/images/add_normal.png' alt='添加' onclick='addTr(this)'>
					<img    border="0" vaild width="15px" src='<%=request.getContextPath()%>/common/images/delete1.gif' alt='删除' onclick='deleteTr(this)'>
			</td>
		</tr>
		</c:forEach>
		<tr>
			<td class="tdLeft" width="25%">经办用户</td>
			<td class="tdRight" width="25%">
				${bean.handleUser }
			</td>
			<td class="tdLeft" width="25%">经办日期</td>
			<td class="tdRight" width="25%">
				${bean.handleDate }
				<input type="hidden" id="handleDate" name="handleDate" value="${bean.handleDate }" readonly="readonly" class="base-input-text" maxlength="5" valid errorMsg="请输入撤并前责任中心代码"/>
			</td>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">审批用户</td>
			<td class="tdRight" width="25%">
				${bean.aprvUser }
			</td>
			<td class="tdLeft" width="25%">审批时间</td>
			<td class="tdRight" width="25%">
				${bean.aprvTime }
			</td>
			
		</tr>
		<tr>
			<td class="tdLeft" width="25%">审批备注
			<td class="tdRight" width="25%" colspan="3">
					${bean.memo }
			</td>
			
		</tr>
	</table>
	<br/>
			<input type="button"  value="提交" onclick="edit()"/>
   	<input type="button" value="返回" onclick="backToLastPage('${uri}');">
</form>
<br>
</body>
</html>