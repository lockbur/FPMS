<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="none">
<title>操作成功</title>
<jsp:include page="../../common/jsp/include/common.jsp"></jsp:include>
<style>
.exPanel .contentBox .title{
	background: url("<%=request.getContextPath()%>/common/images/deal_success.png") no-repeat;
}
</style>
</head>
<body>
<div class="exPanel">
	<div class="contentBox">
		<span class="title">操作成功</span>
		<div class="exDetail">
			<p class="returnCode">描述:</p>
		</div>
		<p:message className="message-panel" infoCls="info-message" warningCls="warning-message" errorCls="error-message"/>
		<br/>
		<p:returnLink  />
	</div>
</div>
</body>
</html>