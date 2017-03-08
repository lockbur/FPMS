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
<title>省行维护审批链修改</title>
<script type="text/javascript">
function pageInit(){
	App.jqueryAutocomplete();
	new TableCombine().rowspanTable( "listTab", 0, null, 0, 1, null, null, null);
}
//单选按钮初始化
function radioInit(_obj, _name, _val){
	if(!_val)
	{
		_val = $(_obj).find("input[name^='"+ _name +"']:eq(0)").val();
	}
	$(_obj).find("input[name^='"+ _name +"']").attr("checked", false);
	$(_obj).find("input[name^='"+ _name +"']").each(function(){
		if( $(this).val() == _val ){
			$(this).attr("checked", true);
			$(this).parent().find("label[for='" + $(this).attr("id") +"']").click();
		}
	});
}
//选择一个审批链
function selectOneAprv(){
	var changeData="";
	$(":radio[class='selectRadio']:checked").each(function(){
		if("" == changeData){
			changeData = $(this).val();
		}else{
			changeData = changeData +","+$(this).val();
		}
	});
	if("" == changeData){
		App.notyError("请至少选择一条审批链提交");
		return false;
	}
	$("#changeData").val(changeData);
	var url ="";
	if($("#orgType").val()=="01"){
		url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/selectSubmit.do?<%=WebConsts.FUNC_ID_KEY %>=0812010215';	
	}else{
	    url = '<%=request.getContextPath()%>/sysmanagement/approveChainMgr/selectSubmit.do?<%=WebConsts.FUNC_ID_KEY %>=0812010316';
	}
	
	$("#reqtypeForm").attr("action",url);
	$("#reqtypeForm").submit();
	
}
</script>
</head>
<body>
<p:authFunc funcArray="0812010204"/>
<form action="" method="post" id="reqtypeForm">
<input type="hidden" name="orgType" value="${bean.orgType }"/>
<input type="hidden" name="matrCode" value="${bean.matrCode }"/>
<input type="hidden" name="montCodeNew" value="${bean.montCodeNew }"/>
<input type="hidden" name="changeData" id="changeData"/> 
<p:token/>
	<table  class="tableList" id="listTab">
		<tr>
			<th>费用承担（责任中心/机构）</th>
			<th>旧监控指标代码</th>
			<th>旧监控指标名称</th>
			<th>物料采购部门</th>
			<th>物料归口部门</th>
			<th>选择</th>
		</tr>
		<c:forEach items="${list }" var="list">
			<tr onmouseover="setTrBgClass(this, 'trOnOver2');" onmouseout="setTrBgClass(this, 'trOther');">
				<td>
					<c:if test="${bean.aprvType eq '11' }">
						-
					</c:if>
					<c:if test="${bean.aprvType ne '11' }">
						(<c:if test="${list.feeLevl eq '1' }">责任中心${list.feeCode }</c:if><c:if test="${list.feeLevl eq '2' }">机构${list.orgCode }</c:if>)${list.feeNameLevl }
					</c:if>
					
				</td>
				<td>
					${list.montCode }
				</td>
				<td>
					${list.montName }
				</td>
				<td>
					${list.matrBuyName }
				</td>
				<td>
					${list.matrAuditName }
				</td>
				<td>
					<input type="radio" name="${list.groupId}" class="selectRadio" value="${list.groupId }-${list.montCode}-${bean.aprvType}-${bean.dataYear}" >
				</td>
			</tr>
		</c:forEach>
		<tr align="center">
			<td colspan="6" style="text-align: center;" align="center">
				<input type="button" value="提交" onclick="selectOneAprv()"/>
				<input type="button" value="返回" onclick="backToLastPage('${uri}')">
				</td>
		</tr>
	</table>
</form>
</body>
</html>