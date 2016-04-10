<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap_unlogin.jsp"%>
<%@page import="com.unieap.UnieapConstants,com.apps.esb.service.bss.vo.macre.customerprofile.CustomerProfileVO"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />

<script type="text/javascript" src="<%=path%>/unieap/js/jquery/plugins/jquery-ui-1.11.4.custom/jquery-ui.min.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/unieap/js/jquery/plugins/jquery-ui-1.11.4.custom/jquery-ui.min.css" />

<script type="text/javascript" src="<%=path%>/unieap/js/jquery/plugins/cssmenu/script.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/unieap/js/jquery/plugins/cssmenu/styles.css" />

<%
	CustomerProfileVO vo = (CustomerProfileVO)session.getAttribute(UnieapConstants.USER);
%>

<title>My Balance</title>

<style>
	.bizPageTitle{
		height:50px;
  		background: #00FFFF;
  		text-align:left;
  		
	}
	.bizPageTitleFont{
		height:50px;
		line-height:48px; 
		font-family: 'Lato', sans-serif;
		font-size: 18px;
		color: #ffffff;
		font-weight:bold;
		display:block;
		text-shadow: 0 1px 1px #000;
		background: #00e1e1;
		background: -moz-linear-gradient(#00e1e1 0%, #2894ff 100%);
		background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #00e1e1), color-stop(100%, #2894ff));
		background: -webkit-linear-gradient(#00e1e1 0%, #2894ff 100%);
		background: linear-gradient(#00e1e1 0%, #2894ff 100%);
	}
	table{
		width:100%;
	}
	table td{
		border:2px solid #FFFFFF;
	}
	.tableDispalyField{
		height:35px;
		width:120px;
		line-height:35px; 
		font-size: 14px;
		color: #000000;
		display:block;
		padding-left:10px;
		background: #CCFFFF;
		background: -moz-linear-gradient(#CCFFFF 0%, #CCFFFF 100%);
		background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #CCFFFF), color-stop(100%, #CCFFFF));
		background: -webkit-linear-gradient(#CCFFFF 0%, #CCFFFF 100%);
		background: linear-gradient(#CCFFFF 0%, #CCFFFF 100%);
		
	}
	.tableDispalyValue{
		height:35px;
		width:100px;
		line-height:35px; 
		font-size: 14px;
		color: #000000;
		display:block;
		padding-left:10px;
		background: #FFFFFF;
		background: -moz-linear-gradient(#FFFFFF 0%, #FFFFFF 100%);
		background: -webkit-gradient(linear, left top, left bottom, color-stop(0%, #FFFFFF), color-stop(100%, #FFFFFF));
		background: -webkit-linear-gradient(#FFFFFF 0%, #FFFFFF 100%);
		background: linear-gradient(#FFFFFF 0%, #FFFFFF 100%);
		
	}
	
</style>


<script type="text/javascript">
	var paymentFlag = "<%=vo.getSubscriberType()%>"
</script>

</head>
<body style='background:#ffffff'>
    <div class="bizPageTitle"><span class="bizPageTitleFont">>>My Balance</span></div>
	<div>
		<table border="1px">
			<tr>
				<td><span class="tableDispalyField">Service Number</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Status</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Status Reason</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
			</tr>
			<tr>
				<td><span class="tableDispalyField">Payment Flag</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Activation Date</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Suspesion Date</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
			</tr>
			<tr>
				<td><span class="tableDispalyField">Payment Flag</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Activation Date</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Suspesion Date</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
			</tr>
			<tr>
				<td><span class="tableDispalyField">Deactivation Date</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Expiry Date</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Language</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
			</tr>
			<tr>
				<td><span class="tableDispalyField">Total Balance</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Bill Cycle</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Language</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
			</tr>
			<tr>
				<td><span class="tableDispalyField">Outstanding Bill</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Payment Due Date</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Initial Credit Limit</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
			</tr>
			<tr>
				<td><span class="tableDispalyField">Temp Credit Limit</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Total Credit Limit</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
				<td><span class="tableDispalyField">Available Amount</span></td>
				<td><span class="tableDispalyValue">${myBalance}</span></td>
			</tr>
		</table>
	</div>
</body>
</html>