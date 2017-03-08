<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="p" uri="/platform-tags" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Utf-8">
<title>Insert title here</title>
</head>
<body>
<p:authFunc funcArray="FILE_UPLOAD,FILE_DOWNLOAD"/>
	<form action="<%=request.getContextPath()%>/example/upload.do" method="post" >
<!-- 		<input type="file" name="file"/> -->
		<input name="user['field1']" value="${user.field1}"/>
		<p:button funcId="FILE_UPLOAD" value="上传"/>
		<p:button funcId="FILE_DOWNLOAD" value="下载"/>
	</form>
</body>
</html>