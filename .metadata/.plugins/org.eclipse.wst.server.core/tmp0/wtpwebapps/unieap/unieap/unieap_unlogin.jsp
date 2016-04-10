<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>
<link href="<%=path%>/apps/mcare/mcare.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=path%>/unieap/js/jquery/jquery-2.2.0.js"></script>
<script type="text/javascript">
	var basePathUrl = "<%=basePath%>";
	$(document).ready(function () { 
		Showbo.Msg.hide();
	}); 
</script>
<!-- unieap用户权限文件 -->
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="mobile care,mobile service,self-service">
<meta http-equiv="description" content="unieap system">	
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">    
<meta name="format-detection" content="telephone=no" /> 


