<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/unieap/unieap.jsp" %>
<%
	String menuHtml = (String) request.getAttribute("");
	String mac = (String)request.getAttribute("mac");
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
			var menuHtml = "<%=menuHtml%>";
			function initMenu(){
				//$(menuHtml).appendTo("#pageMenu"); 
			}
			//jQuery().ready(function (){
			//	alert(menuHtml);
			//	$(menuHtml).appendTo("#pageMenu"); 
			//	});
			function grid1(){
				window.location.href = "DemoAction.do?method=grid";
			}
			function grid2(){
				window.location.href = "DemoAction.do?method=grid2";
			}
			function tree1(){
				window.location.href = "DemoAction.do?method=tree";
			}
			function desk1(){
				window.location.href = "DemoAction.do?method=desk";
			}
			function mac(){
				
			}
			function initData(){
				if(mac!='null'&&mac!=''){
					alert("客户端Mac地址为:"+mac);
				}
			}
			//var aa= {"ACTIVE_FLAG":{"Y":"启用","N":"停用"}};
			//var CodeList = eval('{"ACTIVE_FLAG":{"Y":"启用","N":"停用"}');
			//alert('codelist='+CodeList.ACTIVE_FLAG.Y);
		</script>
	</head>
	<body onload="">
		<input id='grid1' onclick="grid1()" type='button' value='grid1'>
		<input id='grid2' onclick="grid2()" type='button' value='大数据分页测试'>
		<input id='tree1' onclick="tree1()" type='button' value='tree1'>
		<input id='desk1' onclick="desk1()" type='button' value='desk1'>
		<input id='mac' onclick="mac()" type='button' value='获取Mac地址'>
		
	</body>
</html>
