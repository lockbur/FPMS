<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.opensymphony.com/sitemesh/decorator" prefix="decorator"%>
<%@ taglib uri="/platform-tags" prefix="p" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
 		<title><decorator:title default="default title"/></title>
 		<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/common/css/layout2.css">
 		<jsp:include page="../include/common.jsp"></jsp:include>
        <decorator:head/>
        <style>
        	body {background: #fff; width: auto; min-width: 500px;overflow-x:hidden;text-align: center; }
        	.body {padding: 15px 30px 30px 15px;}
        </style>
    </head>
    <body>
   		
  			<decorator:body />
 		
    </body>
</html>