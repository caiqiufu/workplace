<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.unieap.login.LoginConts"%>
<%@ include file="/unieap/unieap.jsp" %>
<%
	String menuHtml = (String) request.getAttribute(LoginConts.MENU);
	String locationUrl = (String) request.getAttribute(LoginConts.LOCATIONURL);
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>主页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
	</head>
	<body onload="">
		<iframe id="contextPage" name='contextPage' src="<%=locationUrl%>" 
			border="0" frameBorder="0" marginwidth="0" marginheight="0" width="100%" height="100%" scrolling="auto">
		</iframe>
	</body>
</html>
