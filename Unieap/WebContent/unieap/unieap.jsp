<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="java.util.ResourceBundle,com.unieap.base.ResourceBundleHelper,com.unieap.UnieapConstants,com.unieap.pojo.User,com.unieap.base.SYSConfig"%>
<%
	
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	        + path + "/";
    User user = UnieapConstants.getUser();
%>
<!-- 开源框架文件 -->
<script type="text/javascript"
	src="<%=path%>/unieap/js/ext-4.2.1/ext-all.js"></script>
<script type="text/javascript" src="<%=path%>/unieap/js/ext-4.2.1/locale/ext-lang-<%=SYSConfig.defaultLanguage%>.js"></script> 
<!-- extjs框架文件 -->
<link rel="stylesheet" type="text/css" href="<%=path%>/unieap/js/ext-4.2.1/resources/css/ext-all-gray.css" />
<!-- unieap框架文件 -->
<script type="text/javascript"
	src="<%=path%>/unieap/js/common/unieap.util.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/unieap/js/common/css/toolbar.css" />
<!-- 快码缓存文件,由系统生成-->
<script type="text/javascript"
	src="<%=path%>/unieap/js/common/<%=user.getUserCode()%>_button_constants.js"></script>
<script type="text/javascript"
	src="<%=path%>/unieap/js/common/<%=user.getUserCode()%>_dicdata_constants.js"></script>
<!-- 根目录路径 -->
<script type="text/javascript">
	var basePathUrl = "<%=basePath%>";
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
</style>

<!-- unieap用户权限文件 -->
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="unieap system">	
<meta http-equiv=Content-Type content=texthtml; charset=UTF-8> 



