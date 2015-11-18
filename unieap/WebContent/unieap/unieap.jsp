<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.unieap.UnieapConstants,com.unieap.pojo.User,com.unieap.base.SYSConfig"%>
<%
	
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	        + path + "/";
    User user = UnieapConstants.getUser();
    Object error = request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
    String errorDesc = "";	
    if(error!=null){
		errorDesc = error.toString();
		if(errorDesc.contains("Bad credentials")||errorDesc.contains("UserDetailsService")){
			errorDesc = UnieapConstants.getMessage("user.login.error.usercode");
		}
	}
    
%>
<!-- 开源框架文件
<script type="text/javascript"
	src="<%=path%>/unieap/js/ext-6.0.1/build/ext-all.js"></script>
-->
<script type="text/javascript"
	src="<%=path%>/unieap/js/ext-4.2.1/ext-all.js"></script>
<script type="text/javascript" src="<%=path%>/unieap/js/ext-4.2.1/locale/ext-lang-<%=SYSConfig.defaultLanguage%>.js"></script> 
<!-- extjs框架文件 
<link rel="stylesheet" type="text/css" href="<%=path%>/unieap/js/ext-4.2.1/resources/css/ext-all-neptune.css" />
-->
<link rel="stylesheet" type="text/css" href="<%=path%>/unieap/js/ext-4.2.1/resources/css/ext-all-gray.css" />
<!-- unieap框架文件 -->
<script type="text/javascript"
	src="<%=path%>/unieap/js/common/unieap.util.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/unieap/js/common/css/common.css" />
<link rel="shortcut icon" type="image/x-icon" href="<%=path%>/unieap/js/common/images/title.png" media="screen" /> 
<!-- 快码缓存文件,由系统生成-->
<script type="text/javascript"
	src="<%=path%>/unieap/js/common/<%=user.getUserCode()%>_button_constants.js"></script>
<script type="text/javascript"
	src="<%=path%>/unieap/js/common/<%=user.getUserCode()%>_dicdata_constants.js"></script>
<!-- 根目录路径 -->
<script type="text/javascript">
	var basePathUrl = "<%=basePath%>";
	var errorDesc = "<%=errorDesc%>";
	var responseText = "";
	Ext.onReady(function(){
		
		if(errorDesc.indexOf("This session has been expired")>-1){
			Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'This session has been expired',width:120, height:100,
				fn: showResult,buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.WARNING});
		}
		Ext.Ajax.on('requestcomplete',function(conn, response, options, eOpts){  
			responseText = response.responseText;
			if(responseText.indexOf('<script type="text/javascript">')>-1){
				Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'This session has been expired',width:120, height:100,
					fn: showTimeOutResult,buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.WARNING});
				
			}else if(responseText.indexOf("This session has been expired")>-1){
				Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'This session has been expired',width:120, height:100,
					fn: showResult,buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.WARNING});
			}
        });
		function showTimeOutResult(){
			document.write(responseText); 
		}
		function showResult(btn){
			 window.top.location.href = "<%=path%>/timeout.jsp";  
		}
	});
</script>
 <style type="text/css">
    .add {
	    background-image: url(unieap/js/common/images/add.png) !important;
	}
	.delete {
	    background-image: url(unieap/js/common/images/delete.png ) !important;
	}
	.edit {
	    background-image: url(unieap/js/common/images/edit.png ) !important;
	}
	.save {
	    background-image: url(unieap/js/common/images/save.gif ) !important;
	}
	.find {
	    background-image: url(unieap/js/common/images/find.gif ) !important;
	}
	.search-trigger {
	    background-image: url(unieap/js/common/images/search-trigger.png ) !important;
	}
	.clear-trigger {
	    background-image: url(unieap/js/common/images/clear-trigger.gif ) !important;
	}
	.view {
	    background-image: url(unieap/js/common/images/view.gif ) !important;
	}
	.money-up {
	    background-image: url(unieap/js/common/images/money_add.png ) !important;
	}
	.money-down {
	    background-image: url(unieap/js/common/images/money_delete.png ) !important;
	}
	.folder_go {
        background-image: url(unieap/js/common/images/folder_go.png) !important;
    }
    .menu-show {
        background-image: url(unieap/js/common/images/menu-show.gif) !important;
    }
    .buttons {
        background-image: url(unieap/js/common/images/buttons.gif) !important;
    }
    .leaf {
        background-image: url(unieap/js/common/images/leaf.png) !important;
    }
    .readonly_field{
		background: white repeat-x 0 0;
		background-color:#eaeaea;
	}
	.refresh {
	    background-image: url(unieap/js/common/images/refresh.png) !important;
	}
	
	
</style>

<!-- unieap用户权限文件 -->
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="mobile care,mobile service,self-service">
<meta http-equiv="description" content="unieap system">	
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">    
<meta name="format-detection" content="telephone=no" /> 


