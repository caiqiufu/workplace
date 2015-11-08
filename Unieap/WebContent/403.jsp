<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<script type="text/javascript">
	window.top.location.href = "<%=path%>/login.jsp";
</script>