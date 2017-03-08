<%@page import="com.forms.platform.web.WebUtils"%>
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
<title>省行监控指标编辑</title>
<style type="text/css">
img{
border:none; 
}
</style>
<script type="text/javascript">
//选择物料
function selectMatr()
{
	var montType=$("#montType").val();
	var montCode=$("#montCode").val();
	//已维护dao数据库的
	//未维护dao数据库的
	var selectNoUpMatrs="";
	$('input[type=checkbox][name="noUpMatrs"]').each(function(){
		var matrCode =$(this).attr("title");
		if(selectNoUpMatrs==""){
			selectNoUpMatrs = matrCode;
		}else{
			selectNoUpMatrs =selectNoUpMatrs+matrCode;
		}
	});

	var orgType = $("#orgType").val();
	var url = "";
	if(orgType =="01"){
		 url = "<%=request.getContextPath()%>/sysmanagement/montindex/editMatrs.do?<%=WebConsts.FUNC_ID_KEY %>=0812020106";
	}else{
		 url = "<%=request.getContextPath()%>/sysmanagement/montindex/editMatrs.do?<%=WebConsts.FUNC_ID_KEY %>=0812020206";
	}
	url=url +"&montType="+montType+"&montCode="+montCode+"&selectNoUpMatrs="+selectNoUpMatrs+"&orgType="+orgType;
	$.dialog.open(
			encodeURI(encodeURI(url)),
			{
				width: "75%",
				height: "90%",
				lock: true,
			    fixed: true,
			    title:"物料选择",
			    id:"dialogCutPage",
				close: function(){
					var returnValue = art.dialog.data('returnValue'); 
					
					if(returnValue){
						$("#haveUpCount").html(returnValue.count);
						$("#matrsDiv").html(returnValue.htmls);
					}
				}		
			}
		 );
}
//取消未维护的
function cancle(matrCode){
	$("#"+matrCode+"_div").remove();
}
//删除已维护的
function cancleHaveUp(matrCode){
	var montCode =$("#montCode").val();
	var flag = false;
	var msg ="";
	var data = {};
	data['montCode'] =  montCode;
	data['matrCode'] =  matrCode;
	
	var orgType = $("#orgType").val();
	var url = "";
	if(orgType =="01"){
		 url = "sysmanagement/montindex/checkMontCode.do?VISIT_FUNC_ID=0812020112";
	}else{
		 url = "sysmanagement/montindex/checkMontCode.do?VISIT_FUNC_ID=0812020210";
	}
	
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
			flag = data.data.flag;
			msg = data.data.msg;	
		
	});
	if (!flag){
		App.notyError(msg);
		return false;
	}else{
		$( "<div>确认要删除该物料和监控指标的对应关系?该监控指标对应物料的审批链也会一并删除</div>" ).dialog({
			resizable: false,
			height:180,
			width:500,
			modal: true,
			dialogClass: 'dClass',
			buttons: {
				"确定": function() {
					del(matrCode);
					$( this ).dialog( "close" );
				},
				"取消": function() {
					$( this ).dialog( "close" );
				}
			}
		});
	}
	
}
//删除操作执行
function del(matrCode){
	var montCode =$("#montCode").val();
	var data = {};
	data['montCode'] =  montCode;
	data['matrCode'] =  matrCode;
	data['montType'] =  $("#montType").val();
	data['org21Code'] = $("#org21Code").val();
	
	var orgType = $("#orgType").val();
	var url = "";
	if(orgType =="01"){
		 url = "sysmanagement/montindex/delMontMatr.do?<%=WebConsts.FUNC_ID_KEY%>=0812020107";
	}else{
		 url = "sysmanagement/montindex/delMontMatr.do?<%=WebConsts.FUNC_ID_KEY%>=0812020207";
	}
	
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
		var flag=data.data.flag;
		if(flag == true){
			//删除成功
			$("#"+matrCode+"_div").remove();
		}else{
			App.notyError(data.data.msg);
		}
	});
}
function doValidate() {
	if(!App.valid("#montForm")){
		 return;
	}
	//检查物料是否选择
	var len=$('#matrsDiv input[type="checkbox"]').length;
	if(len<=0||len==null){
		App.notyError("请选择物料!");
		return false;
	}
	if(checkMont()){
		App.notySuccess("该监控指标名称已存在!");
		return false;
	}
	return true;
}
//检查监控指标是否存在
function checkMont(){
	var list = false;
	var data = {};
	data['montName'] =  $("#montName").val();
	data['montCode'] =  $("#montCode").val();
	data['org21Code'] =  $("#org21Code").val();
	var orgType = $("#orgType").val();
	var url = "";
	if(orgType =="01"){
		 url = "sysmanagement/montindex/checkMont.do?VISIT_FUNC_ID=0812020103";
	}else{
		 url = "sysmanagement/montindex/checkMont.do?VISIT_FUNC_ID=0812020203";
	}
	
	App.ajaxSubmit(url,{data : data,async:false}, function(data) {
			list = data.isExist;
	});
	return list;
}
//校验监控指标是否合法 一个资产类是以省行未单位 菲哦你各类是以二级行下面监控指标不能重复，一个物料只能在一个监控指标中
//通过指标类型来判断是否需要项目类型
function changeProjType(){
	var montType=$("#montType").val();
	if(montType!=null &&montType!=''){
		if(montType==11){
			$("#projTypeTd").show();
			$("#projTypeTd1").show();
			$("#projType").attr("valid");
		}
		else{
			$("#projTypeTd").hide();
			$("#projTypeTd1").hide();
			$("select[id='projType']").val("");
			$("select[id='projType']").parent().find("input").val("");
			$("#projType").removeAttr("valid");
			
			
		}
	}
}
</script>
</head>
<body>
<p:authFunc funcArray="011311,011308,011306,0812020109,0812020209"/>
<form action="" method="post" id="montForm">
<input type="hidden" id="org21Code" name="org21Code"  value="${bean.org21Code}">
<input type="hidden" id="montType" name="montType"  value="${bean.montType}">
<input type="hidden" id="orgType" name="orgType"  value="${bean.orgType}">
<p:token/>
	<table><tr><td>
	<table id="approveChainTable">
		<tr>
			<th colspan="4">编辑</th>
		</tr>
		<tr>
			<td class="tdLeft" width="25%">监控指标代码<span style="color: #A41E1E">*</span></td>
			<td class="tdRight" width="25%" id="montCodeTd">
				${bean.montCode }
			<input type="hidden" name="montCode" id="montCode" value="${bean.montCode}">	
			</td>
			<td class="tdLeft" width="25%"> 监控指标名称<span style="color: #A41E1E">*</span></td>
			<td class="tdRight" width="25%">
				<input type="text" id="montName" value="${bean.montName }"  class="base-input-text"  valid  errorMsg="请输入监控指标名称。"  maxlength="300"  name="montName"/>
			</td>
		</tr>
	 
				<tr>
					<td class="tdLeft"> 指标类型<span style="color: #A41E1E">*</span></td>
					<td class="tdRight" id="montNameTd">
							${bean.montTypeName}
					</td>
					<td class="tdLeft">
						物料<span style="color: #A41E1E">*</span>
					</td>
					<td class="tdRight"  >
						<a href="#" onclick="selectMatr()">  
								<img src="<%=request.getContextPath()%>/common/images/search1.jpg" alt="选择物料" style="border:none; cursor:pointer;vertical-align: middle;margin-left:15px;heigth:30px;width:30px;"/>
						</a>
					</td>
			  </tr>	
			  <tr>
				   <td class="tdLeft">年份<span style="color: #A41E1E">*</span>
			       </td>
			       <td class="tdRight"  id="dataYear">
			       ${bean.dataYear}
			       </td>
					<td class="tdLeft">
					</td>
					<td class="tdRight"  >
					</td>
			  </tr>	
		 
	</table>
	</td></tr>
	<tr><td></td></tr>
	<tr><td>
	<table class="tableList" id="budgetTable">
		<tr>
			<th align="center" style="text-align: center">物料列表</th>
		</tr>
		<tr>
			<td class="tdRight">
				<div id="matrsDiv">
				  <c:forEach items="${list}" var="matr" varStatus="status">
					<div id='${matr.matrCode }_div' style='border-bottom:1px solid #c5dbf0;border-right:1px solid #c5dbf0;float:left;width:49.5%;height:35px;line-height:35px;'>
						<input type='checkbox' style='margin-left: 0px; display: none;' checked='checked' value='${matr.matrCode }' name='matrs'/>
						<input type='checkbox' style='margin-left: 0px; display: none;' checked='checked' value='${matr.isValid }' name='isValids'/>
						
						<span title='${matr.matrName }' <c:if test="${matr.isValid ne '1' }">style="text-decoration: line-through;" ></c:if>>
						${matr.cglCode}-${matr.matrCode}
							<forms:StringTag length="25"   value="${matr.matrName }"/>
						</span>
						<a style='margin-right:2px; float:right;margin-top:10px;' href='javascript:void(0);' onclick="cancleHaveUp('${matr.matrCode}')"><img src='<%=request.getContextPath()%>/common/images/del.gif'/></a>
					</div>
				 </c:forEach>
				</div>
				<div id="hiddenMatrsDiv" style="display: none">
				  <c:forEach items="${list}" var="matr" varStatus="status">
					<div id='${matr.matrCode }_div' style='border-bottom:1px solid #c5dbf0;border-right:1px solid #c5dbf0;float:left;width:33.1%;height:35px;line-height:35px;'>
						<input type='checkbox' style='margin-left: 0px; display: none;' checked='checked' value='${matr.matrCode }' name='fristMatrs'/>
						<span title='${matr.matrName }'>
							<forms:StringTag length="25"   value="${matr.matrName }"/>
						</span>
						<a style='margin-right:2px; float:right;margin-top:10px;' href='javascript:void(0);' onclick="cancleHaveUp('${matr.matrCode}')"><img src='<%=request.getContextPath()%>/common/images/del.gif'/></a>
					</div>
				 </c:forEach>
				</div>
			</td>
		</tr>
		<tr>
			<td align="center" style="text-align: center">
				<c:if test="${bean.orgType eq '01'}">
					 <p:button funcId="0812020109" value="提交"/>
				</c:if>
				<c:if test="${bean.orgType eq '02'}">
					 <p:button funcId="0812020209" value="提交"/>
				</c:if>
			   
				<input type="button" value="返回" onclick="backToLastPage('${uri}')">
			</td>
		</tr>
	</table>
	</td></tr></table>
</form>
</body>
</html>