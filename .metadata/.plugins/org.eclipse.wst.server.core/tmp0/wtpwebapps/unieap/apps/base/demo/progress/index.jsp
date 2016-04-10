<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/unieap/unieap.jsp" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>jQuery EasyUI</title>
		<title>jQuery Progress Bar v1.1</title>
		<link rel="stylesheet" type="text/css" href="<%=path%>/unieap/js/jquery/plugin/jquery-progressbar/progressbar.css" />
		<script type="text/javascript" src="<%=path%>/unieap/js/jquery/plugin/jquery-progressbar/jquery.progressbar.js"></script>
		<script>
			function initpro(){
				$.ajax({
				   type: "POST",
				   url:"ProgressAction.do?method=startProgress",
				   data:'',
				   beforeSend:function(){
				   		$("#spaceused1").progressBar(0);
				   },
				   success: function(data){
				   		//setTimeout("showUpload()", 750);
				   },
				   error:function(data){
				   		var result = JSON.parse(data);
				   		$.messager.alert('数据保存','数据保存失败!错误信息:'+result.message,'error');
				   }
				});
			}
			function showUpload(){
				$.ajax({
				   type: "POST",
				   url:"ProgressAction.do?method=getProgress",
				   data:'',
				   success: function(data){
				   		var result = JSON.parse(data);
				   		$("#spaceused1").progressBar(result.message);
				   		setTimeout("showUpload()", 750);
				   },
				   error:function(data){
				   		var result = JSON.parse(data);
				   		$.messager.alert('数据保存','数据保存失败!错误信息:'+result.message,'error');
				   }
				});
			}
	</script>
	<style>
	</style>
	</head>
	<body>
		<input type="button" value="progress" onclick="initpro()">
		<div id="info" class='progress'>
			正在同步数据...
			<span class="progressBar" id="spaceused1"></span>
		</div>
	</body>
</html>