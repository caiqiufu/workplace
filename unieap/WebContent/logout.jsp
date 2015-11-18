<%
	
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	        + path + "/";
%>
<script type="text/javascript">
	window.top.location.href = "<%=path%>/j_spring_security_logout";
</script>

