<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.unieap.UnieapConstants"%>

<%
	String errorCode = request.getParameter("errorCode");
	String errorDesc = "";
	if("001".equals(errorCode)){
		errorDesc = UnieapConstants.getMessage("user.login.error.usercode");
	}
    String path = request.getContextPath();
%>
<html>
  <head>
    <title>Login</title>
     <script type="text/javascript">
     	function ecare(){
     		window.location.href = "ExtAction.do?method=index";
     	}
     </script>
    
    
  </head>

  <body onload="document.f.j_username.focus();">
    <form name="f" action="j_spring_security_check" method="POST">
      <table border="1" style="margin:10% 0 0 40%;border-collapse:collapse; ">
        <tr><td colspan='2'><h1>Login</h1></td></tr>
        <tr><td style="background-color:B5BCBB"><%=UnieapConstants.getMessage("user.username")%></td><td style="width:250px;"><input type='text' name='j_username' value='unieap'/></td></tr>
        <tr><td style="background-color:B5BCBB"><%=UnieapConstants.getMessage("user.password")%></td><td style="width:250px"><input type='password' name='j_password' value='1'></td></tr>
        <tr><td style="background-color:B5BCBB"><input type="checkbox" name="_spring_security_remember_me"></td><td>Don't ask for my password for two weeks</td></tr>
        <tr><td colspan='2' align="center"><input name=<%=UnieapConstants.getMessage("user.button.login")%> type="submit"></td></tr>
      	<tr><td colspan='2' align="center"><font color="red"><%=errorDesc%></font></td></tr>
      </table>
    </form>
    <input type="button" name='ECare' value="ECare" onclick='ecare()'>
  </body>
</html>
