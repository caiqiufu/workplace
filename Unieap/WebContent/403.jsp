<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
%>
<script type="text/javascript">
		window.location.href = "<%=path%>/login.jsp?errorCode=001";
</script>