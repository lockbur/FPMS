<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=Utf-8">
<title>Insert title here</title>
</head>
<body>
	<form action="example/listMap.do">
		<table>
			<tbody>
				<tr>
					<td>参数代码</td>
					<td>参数值</td>
				</tr>
				<c:forEach var="bean" items="${list }">
				<tr>
					<td><c:out value="${bean.PARAM_CODE }"></c:out></td>
					<td><c:out value="${bean.VALUE }"></c:out></td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
	</form>
</body>
</html>