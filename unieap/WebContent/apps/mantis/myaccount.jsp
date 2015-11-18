<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<%
	String project = (String)request.getAttribute("project");
%>
<html>
<head>
<title>My View</title>
    <style type="text/css">
        .td_title {
        	background-color:#F1F1F1;
        	font-weight:bolder;
        	height:30px;
        	width:100px;
        	border-collapse:collapse; 
        	border:1px black solid;
        	
        }
        .td_value {
        	background-color:#FAFAFA;
        	height:30px;
        	width:200px;
        	border-collapse:collapse; 
        	border:1px black solid;
        }
        .tt_textarea{
			height:100px;
            width:90%;
            text-align:left;
		}
    </style>
    <script type="text/javascript">
    var project = "<%=project%>";
    Ext.onReady(function() {
    	Ext.get('submit_b').on('click', function(e){
    		var email = Ext.get("email").dom.value;
    		var pattern = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\.[a-zA-Z0-9_-]{2,3}){1,2})$/;
    		if(!pattern.test(email)||email==null||email==''){
    			Ext.MessageBox.show({title: 'Status',msg:"Email format incorrect.",
         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
    			return;
    		};
    		var password = Ext.get("password").dom.value;
    		var confirmpassword = Ext.get("confirmpassword").dom.value;
    		if(password!=confirmpassword||password==null||password==''){
    			Ext.MessageBox.show({title: 'Status',msg:"Please type in correct password.",
         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
    			return;
    		};
    		var userName = Ext.get("userName").dom.value;
    		if(userName==null||userName==''){
    			Ext.MessageBox.show({title: 'Status',msg:"Please type in real name.",
         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
    			return;
    		};
    		var userId = Ext.get("userId").dom.value;
    		var userCode = Ext.get("userCode").dom.value;
    		var enable = Ext.get("enable").dom.value;
    		var expired = Ext.get("expired").dom.value;
    		var locked = Ext.get("locked").dom.value;
    		var remark = Ext.get("remark").dom.value;
    		Ext.Ajax.request({
                url: 'MdmController.do?method=userDeal',
                params:{
                	'operType':'Modify',
                	'userId':userId,
                	'email':email,
                	'password':password,
                	'userName':userName,
                	'userCode':userCode,
                	'enable':enable,
                	'expired':expired,
                	'locked':locked,
                	'remark':remark
                },
                success: function(response, opts) 
                {
                	Ext.MessageBox.show({title: 'Status',msg:'Successfull',
                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
                },
                failure: function(response, opts) 
                {
                	Ext.MessageBox.show({title: 'Status',msg:response.responseText,
            			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                }
             });
    	});
    });	
    
    </script>
</head>
<body>
	<input type="hidden" id='userId' value="<%=user.getUserId() %>" />
	<input type="hidden" id='userCode' value="<%=user.getUserCode() %>" />
	<input type="hidden" id='enable' value="<%=user.getEnable() %>" />
	<input type="hidden" id='expired' value="<%=user.getExpired() %>" />
	<input type="hidden" id='locked' value="<%=user.getLocked() %>" />
	<input type="hidden" id='remark' value="<%=user.getRemark() %>" />
	<table class="grouble_table"  align="center" width='50%'>
		<tr class="grouble_table_tr">
			<td class="td_title">User Name:</td>
			<td class="td_value"><%= user.getUserCode()%></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title">Password:</td>
			<td class="td_value"><input type='password' id='password' style="width:60%" name='password' value=''/></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title">Confirm Password:</td>
			<td class="td_value"><input type='password' id='confirmpassword' style="width:60%" value=''/></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title">E-mail:</td>
			<td class="td_value"><input type='text' id='email' style="width:60%" name=email value='<%= user.getEmail()%>'/></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title">Real Name:</td>
			<td class="td_value"><input type='text' id='userName' style="width:60%" name='userName' value='<%= user.getUserName()%>'/></td>
		</tr>
		<tr class="grouble_table_tr">
			<td class="td_title">Assigned Projects:</td>
			<td class="td_value"></td>
		</tr>
		<tr>
			<td colspan=2 align="center">
				<input type="button" style ="width:80px" id="submit_b" value="Submit"/>
			</td>
		</tr>
	</table>
</body>
</html>
