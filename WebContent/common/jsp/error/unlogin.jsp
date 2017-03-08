<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<jsp:include page="../../../common/jsp/include/common.jsp"></jsp:include>
<title>尚未登录</title>
<style type="text/css">
body {
	background: #AAA;
}
</style>
<script type="text/javascript">
function gotoLogin() {
	window.top.location.href = '<%=request.getContextPath()%>/user/gotoLogin.do';
	art.dialog.close();
}
</script>
</head>
<body>
<div class="unloginPanel">
	<span>您尚未登陆，请先登陆!</span>
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