<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	        + path + "/";
%>
<html>
  <head>
    <title>Management menu</title>
  </head>
  <script type="text/javascript"
	src="<%=path%>/unieap/js/ext-4.2.1/ext-all.js"></script>
  <!-- extjs框架文件 -->
  <link rel="stylesheet" type="text/css" href="<%=path%>/unieap/js/ext-4.2.1/resources/css/ext-all.css" />
  
<script type="text/javascript">
</script>
  <body>
     <table border="1" width="100%">
        <tr>
        	<td width="100%" align ="center">
        		<a style="text-decoration:underline" href="MdmController.do?method=dic" target="_blank" title="Dic">Dic</a> |
        		<a style="text-decoration:underline" href="void(0);" onclick="toMain()" target="_blank" title="My View">My View</a> |
        		<a style="text-decoration:underline" href="http://www.divcss5.com/" target="_blank" title="View Issues">View Issues</a> |
        		<a style="text-decoration:underline" href="http://www.divcss5.com/" target="_blank" title="Report Issue">Report Issue</a> |
        		<a style="text-decoration:underline" href="http://www.divcss5.com/" target="_blank" title="Change Log">Change Log</a> |
        		<a style="text-decoration:underline" href="http://www.divcss5.com/" target="_blank" title="Summary">Summary</a> |
        		<a style="text-decoration:underline" href="http://www.divcss5.com/" target="_blank" title="Docs">Docs</a> |
        		<a style="text-decoration:underline" href="http://www.divcss5.com/" target="_blank" title="My Account">My Account</a> |
        		<a style="text-decoration:underline" href="http://www.divcss5.com/" target="_blank" title="Logout">Logout</a>
        		<a style="text-decoration:underline" href="MantisController.do?method=myview" target="_blank" title="Management">Management</a>
        	</td>
        </tr>
      </table>
  </body>
</html>