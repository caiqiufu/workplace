<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/unieap/unieap.jsp" %>
<%
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<base href="<%=basePath%>">

		<title>主页</title>
		<script type="text/javascript">
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
			function wf(){
				window.location.href = "DemoAction.do?method=wf";
			}
			function excelImp(){
				window.location.href = "DemoAction.do?method=excelImp";
			}
			function progress(){
				window.location.href = "DemoAction.do?method=progress";
			}
			function mac(){
				window.location.href = "DemoAction.do?method=mac";
			}
			function webService(){
				window.location.href = "DemoAction.do?method=webService";
			}
			function email(){
				window.location.href = "DemoController.do?method=email";
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
		<input id='wf' onclick="wf()" type='button' value='工作流'>
		<input id='excelImp' onclick="excelImp()" type='button' value='excel导入'>
		<input id='progress' onclick="progress()" type='button' value='进度条'>
		<input id='mac' onclick="mac()" type='button' value='获取Mac地址'>
		<input id='webService' onclick="webService()" type='button' value='webService'>
		<input id='email' onclick="email()" type='button' value='email'>
	</body>
</html>
