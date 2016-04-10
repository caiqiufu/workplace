<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/unieap/unieap.jsp" %>
<%
	String menuHtml = (String) request.getAttribute("");
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>主页</title>
		<meta http-equiv="pragma" content="no-cache">
		<meta http-equiv="cache-control" content="no-cache">
		<meta http-equiv="expires" content="0">
		<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
		<meta http-equiv="description" content="This is my page">
		
		<script type="text/javascript">
		$(function(){
			$('#test').datagrid({
				title:'大数据量分页测试',
				iconCls:'icon-save',
				width:600,
				height:350,
				nowrap: false,
				striped: true,
				fit: true,
				method:'post',
				url:'GridAction.do?method=getGridData2',
				queryParams:{},
				singleSelect:true,
				sortName: 'prdInstName',
				sortOrder: 'desc',
				remoteSort: false,
				idField:'prdInstId',
				frozenColumns:[[
		               {field:'ck',checkbox:true},
		               {title:'prdInstId',field:'prdInstId',width:80,sortable:true,editor:{type:'numberbox',options:{precision:1}}}
				]],
				columns:[[
					{field:'prdInstId',title:'prdInstId',width:120,sortable:true,editor:{type:'text'}},
					{field:'prdInstName',title:'prdInstName',width:120,rowspan:1,sortable:true,editor:{type:'text'}},
					{field:'prdInstDesc',title:'prdInstDesc',width:120,rowspan:1,sortable:true,editor:{type:'text'}},
					{field:'serviceNbr',title:'serviceNbr',width:120,rowspan:1,sortable:true,editor:{type:'text'}}
				]],
				pagination:true,
				rownumbers:true
			});
		})
		</script>
	</head>
	<body>
		<table id="test"></table>
	</body>
</html>
