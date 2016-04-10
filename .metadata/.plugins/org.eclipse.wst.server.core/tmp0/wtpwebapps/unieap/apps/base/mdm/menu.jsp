<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Paging Grid Example</title>
    <style type="text/css">
    </style>
    <script type="text/javascript">
    Ext.onReady(function() {
    	Ext.get('user_b').on('click', function(e){
    		window.location.href = "MdmController.do?method=user";
    	});
    	Ext.get('role_b').on('click', function(e){
    		window.location.href = "MdmController.do?method=role";
    	});
    	Ext.get('dic_b').on('click', function(e){
    		window.location.href = "MdmController.do?method=dic";
    	});
    	
    });
    </script>
</head>
<body>
	<table>
		<tr>
			<td>
				<input type="button" style ="width:120px" id="user_b" value="User Management"/>
			</td>
			<td>
				<input type="button" style ="width:120px" id="role_b" value="Role Management"/>
			</td>
			<td>
				<input type="button" style ="width:120px" id="dic_b" value="Dic Management"/>
			</td>
		</tr>
	</table>
</body>
</html>
