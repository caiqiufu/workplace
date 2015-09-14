<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<%
	String project = (String)request.getAttribute("project");
%>
<html>
<head>
<title>My View</title>
    <style type="text/css">
    </style>
    <script type="text/javascript">
    var project = "<%=project%>";
    Ext.onReady(function() {
	  
    });	
    
    </script>
</head>
<body>
	<table class="grouble_table" width="100%">
		<tr>
			<td>
				Open and assigned to me: ${assignedtome}
			</td>
			<td>
				Open and reported to me: ${reportbyme}
			</td>
			<td>
				Last Visit: ${lastvisitdate}
			</td>
		</tr>
	</table>
</body>
</html>
