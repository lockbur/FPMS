<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="decorator" content="none">
<jsp:include page="../../../common/jsp/include/common.jsp"></jsp:include>
<title>请重新登录</title>
<style type="text/css">
body {
	background: #AAA;
}
</style>
<script type="text/javascript">
function gotoLogin() {
	location.href = '<%=request.getContextPath()%>/user/gotoLogin.do';
}
</script>
</head>
<body>
<div class="unloginPanel" style="width: 35%">
	<span>用户登陆已过期，请重新登陆!</span>
	<input type="button" value="登&nbsp;&nbsp;陆" onclick="gotoLogin();"/>
</div>
<!--<div class="container">-->
<!--	<div class="panel ui-widget-content ui-corner-all">-->
<!--		<div class="inner-wrapper">-->
<!--			<h2>您尚未登陆，请先登陆！</h2>-->
<!--		</div>-->
<!--	</div>-->
<!--</div>-->
</body>
</html>