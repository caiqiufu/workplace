<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Monitor</title>
<link type="text/css" rel="stylesheet" href="<%=path%>/unieap/js/dhtmlxGantt/dhtmlxGantt/codebase/dhtmlxgantt.css">
<script type="text/javascript" language="JavaScript" src="<%=path%>/unieap/js/dhtmlxGantt/dhtmlxGantt/codebase/dhtmlxcommon.js"></script>
<script type="text/javascript" language="JavaScript" src="<%=path%>/unieap/js/dhtmlxGantt/dhtmlxGantt/codebase/dhtmlxgantt.js"></script>
<script type="text/javascript" language="JavaScript" src="<%=path%>/apps/base/monitor/monitorMainDisplay.js"></script>

    <script type="text/javascript"> 
    	function linkTaskDetais(taskId)
	    {
	    	window.location.href = "MonitorController.do?method=taskList";
	    	//alert('taskId='+taskId);
	    }
    </script>
    <style>
		body {font-size:12px}
		.{font-family:arial;font-size:12px}
		h1 {cursor:hand;font-size:16px;margin-left:10px;line-height:10px}
		xmp {color:green;font-size:12px;margin:0px;font-family:courier;background-color:#e6e6fa;padding:2px}
		.hdr{
			background-color:lightgrey;
			margin-bottom:10px;
			padding-left:10px;
		}
    </style>
</head>
<body>
	<div style="width:700px;height:500px;" id="GanttDiv"></div>
</body>
</html>
