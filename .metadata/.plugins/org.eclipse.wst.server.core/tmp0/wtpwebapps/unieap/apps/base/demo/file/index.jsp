<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
String message = (String) request.getAttribute("message");
String result = (String) request.getAttribute("result");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=basePath%>">
    
    <title>My JSP 'index.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<script type="text/javascript">
		function initData(){
			var mgt = "<%=message%>";
			var result = "<%=result%>";
			alert(result);
		}
	</script>
  </head>
	<body onload ="initData();">
		文件处理测试
		<form action="<%=path%>/FileAction.do?method=upload" enctype="multipart/form-data" method="post">
		    <p>
		        <input type="file" id="file1" name="excel"/><br/>
		        <input type="submit" value="begin upload" id="uploadbutton"/>
		    </p>
		</form>
	</body>
</html>
