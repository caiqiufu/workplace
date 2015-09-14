<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<%@ include file="/unieap/unieap.jsp" %>
<%
	String locationUrl = (String) request.getAttribute(UnieapConstants.LOCATIONURL);
	String windowName = (String) request.getAttribute(UnieapConstants.WINDOWNAME);
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
	<script type="text/javascript">
		//window.location.href = "<%=locationUrl%>";
		var url = "<%=locationUrl%>";
		var windowName = "<%=windowName%>";
		$(function(){
			$('#bizShow').tabs({});
			addTab(windowName,url)
		});
		function addTab(windowName,url){
			$('#bizShow').tabs('add',{
				title:windowName,
				href:url,
				iconCls:'icon-save',
				closable:true
			});
		}
	</script>
	</head>
	<body>
		<div id="bizShow">
		</div>
	</body>
</html>
