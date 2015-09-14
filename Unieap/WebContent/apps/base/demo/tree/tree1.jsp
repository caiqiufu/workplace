<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/unieap/unieap.jsp" %>
<%@page import="com.unieap.login.LoginConts"%>
<%
	String menuHtml = (String) request.getAttribute(LoginConts.MENU);
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
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/unieap/js/jquery/plugin/jquery-easyui-1.2/themes/default/easyui.css">
		<link rel="stylesheet" type="text/css"
			href="<%=path%>/unieap/js/jquery/plugin/jquery-easyui-1.2/themes/icon.css">
		<script type="text/javascript"
			src="<%=path%>/unieap/js/jquery/plugin/jquery-easyui-1.2/jquery-1.4.2.min.js"></script>
		<script type="text/javascript"
			src="<%=path%>/unieap/js/jquery/plugin/jquery-easyui-1.2/jquery.easyui.min.js"></script>
		<script type="text/javascript">
			$(function(){
			$('#tt2').tree({
				checkbox: true,
				animate:true,
				url: 'TreeAction.do?method=getTreeData1',
				onBeforeLoad:function(node, param){
					if(node!=null){
						param.aa="assss";
					}
				},
				onClick:function(node){
					$(this).tree('toggle', node.target);
				}
			});
		});
		function reload(){
			$('#tt2').tree('reload');
		}
		function getChildren(){
			var node = $('#tt2').tree('getSelected');
			if (node){
				var children = $('#tt2').tree('getChildren', node.target);
			} else {
				var children = $('#tt2').tree('getChildren');
			}
			var s = '';
			for(var i=0; i<children.length; i++){
				s += children[i].text + ',';
			}
			alert(s);
		}
		function getChecked(){
			var nodes = $('#tt2').tree('getChecked');
			var s = [];
			for(var i=0; i<nodes.length; i++){
				alert(nodes[i].id);
				s.push({dataId:nodes[i].id});
			}
			var str = encodeJSON(s);
			alert('str='+str);
			//return UNIEAP.util.encodeJSON(s);
			
			/*
			var nodes = $('#tt2').tree('getChecked');
			var s = '';
			for(var i=0; i<nodes.length; i++){
				if (s != '') s += ',';
				s += nodes[i].text;
			}
			alert(s);
			*/
		}
		function getSelected(){
			var node = $('#tt2').tree('getSelected');
			alert(node.text);
		}
		function collapse(){
			var node = $('#tt2').tree('getSelected');
			$('#tt2').tree('collapse',node.target);
		}
		function expand(){
			var node = $('#tt2').tree('getSelected');
			$('#tt2').tree('expand',node.target);
			alert('expand');
		}
		function collapseAll(){
			$('#tt2').tree('collapseAll');
		}
		function expandAll(){
			$('#tt2').tree('expandAll');
		}
		function append(){
			var node = $('#tt2').tree('getSelected');
			$('#tt2').tree('append',{
				parent: (node?node.target:null),
				data:[{
					text:'new1',
					checked:true
				},{
					text:'new2',
					state:'closed',
					children:[{
						text:'subnew1'
					},{
						text:'subnew2'
					}]
				}]
			});
		}
		function remove(){
			var node = $('#tt2').tree('getSelected');
			$('#tt2').tree('remove', node.target);
		}
		function update(){
			var node = $('#tt2').tree('getSelected');
			if (node){
				node.text = '<span style="font-weight:bold">new text</span>';
				node.iconCls = 'icon-save';
				$('#tt2').tree('update', node);
				alert('node');
			}
		}
		function isLeaf(){
			var node = $('#tt2').tree('getSelected');
			var b = $('#tt2').tree('isLeaf', node.target);
			alert(b)
		}
		</script>
	</head>
	<body onload="">
		<input type="button" onclick='getChecked()' value="getChecked">
		<ul id="tt2"></ul>
	</body>
</html>
