<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<%@page import="java.text.SimpleDateFormat"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
%>
<html>
  <head>
    <title>Main menu</title>
	<script type="text/javascript">
			var projectObj;
			var methodNameV;
			function toFrame(methodName)
			{
				methodNameV = methodName;
				document.getElementById("mainframe").src="MonitorController.do?method="+methodName;
			}
			function toManaement()
			{
				document.getElementById("mainframe").src="MdmController.do?method=menu";
			}
			function logout(){
				window.location.href = "LoginController.do?method=logout";
			}
			Ext.onReady(function() {
		    	Ext.tip.QuickTipManager.init();
		    })
			
	</script>
  </head>
  <body>
     <table class="grouble_table" width="100%" >
        <tr class="grouble_table_tr">
        	<td width="20px">Logged in as:<%=user.getUserName()%></td>
        	<td colspan="2" align ="center"><%=df.format(new Date())%></td>
        </tr>
        <tr class="grouble_table_tr">
        	<td colspan="1" width="90%" align ="center">
        		<c:forEach items="${menus}" var="c">
        			<a style="text-decoration:underline" href="####" onclick="${c.href}" target="_self" title="${c.menuCode}">${c.menuName}</a> |
        		</c:forEach>
        	</td>
        	<td align =right>
        		<input style="width:145px;" type='text' id="jump_number" name='mantis_number' value=''/>
        	</td>
        	<td align ="center">
        		<input id="jump_b" name="Jump" value="Jump" type="button">
        	</td>
        </tr>
        <tr class="grouble_table_tr">
        	<td colspan="3" align ="left">
		      <iframe id="mainframe" src="" frameBorder=0 scrolling=yes width="100%" height='500px' onload="this.height=this.contentWindow.document.documentElement.scrollHeight"></iframe>
        	</td>
        </tr>
      </table>
  </body>
</html>