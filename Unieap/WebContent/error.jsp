<%@ page pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Error Info</title>
</head>
<body>
<c:set value="${exception}" var="ee" />

<jsp:useBean id="ee" type="java.lang.Exception" scope="request"/>

<%=ee.getMessage()%><br>

<%
	ee.printStackTrace(new java.io.PrintWriter(out));
%>
</body>
</html>