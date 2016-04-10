<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/unieap/unieap.jsp" %>
<%
	String menuHtml = (String) request.getAttribute("1");
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
			var menuHtml = '<%=menuHtml%>';
			function initMenu(){
				if(menuHtml!=null){
					var menuData = JSON.parse(menuHtml);
					if(menuData!=null&&menuData.length >0){
						for(var i = 0 ; i < menuData.length ; i ++){
							var m = menuData[i];
							var menuNode = addAccorDion(m.menuId,m.menuName);
							var children  = m.children;
							if(children!=null&&children.length>0){
								for(var j = 0 ; j < children.length ; j ++){
									var c = children[j];
									addMenu(menuNode,c.menuId,c.menuName,c.href);
								}
							}
						}
					}
				}
			}
			function addAccorDion(menuId,menuName){
				return $('#sysmenu').accordion('add',{id:'level1_'+menuId,title:menuName}); 
			}
			function addMenu(parentMenuNode,menuId,menuName,href){
				var myHref = "DeskAction.do?method=pagearea&"+UNIEAP.util.encodeObjectToURI({locationUrl:href})
				var cmenuHtml = '<a id=level2_'+menuId+' href='+myHref+' target="showPage">'+menuName+'</a>';
				$(cmenuHtml).appendTo(parentMenuNode);
			}
			function addTab(){
				$('#tabss').tabs('add',{
					title:'New Tab ',
					href:'PurchaseAction.do?method=index',
					iconCls:'icon-save',
					closable:true
				});
			}
		</script>
	</head>
	<body onload="initMenu();" class="easyui-layout">
		<div region="north" title="North Title" split="true" style="height:100px;padding:10px;">
			公共信息区域
		</div>
		<div region="south" title="South Title" split="true" style="height:100px;padding:10px;background:#efefef;">
			<div class="easyui-layout" fit="true" style="background:#ccc;">
				<div region="center">sub center</div>
				<div region="east" split="true" style="width:200px;">sub center</div>
			</div>
		</div>
		<div region="east" iconCls="icon-reload" title="Tree Menu" split="true" style="width:180px;">
			<ul class="easyui-tree" url="tree_data.json"></ul>
		</div>
		<div region="west" split="true" title="菜单" style="width:230px;padding1:1px;overflow:hidden;">
			<!--菜单显示区域 ,不能改动-->
			<div id="sysmenu" class="easyui-accordion" fit="false" border="false">
				<a href="#" target="showPage" onclick="addTab()">addmenu</a>
				<input type="button" onclick="addTab()" value='addmenu'>
			</div>
		</div>
		<div region="center" title="主页" style="overflow:hidden;">
			<div id='tabss' class="easyui-tabs" fit="true" border="false">
			</div>
		</div>
	</body>
</html>
