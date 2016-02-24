<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap_unlogin.jsp"%>
<%@page
	import="com.unieap.UnieapConstants,com.apps.esb.service.bss.vo.macre.customerprofile.CustomerProfileVO"%>
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />


<link href="<%=path%>/apps/mcare/mcare.css" type="text/css" rel="stylesheet" />
<script type="text/javascript" src="<%=path%>/unieap/js/jquery/jquery-2.2.0.js"></script>

<link rel="stylesheet" type="text/css" href="<%=path%>/unieap/js/jquery/plugins/tooltipster-master/css/tooltipster.css" />
<script type="text/javascript" src="<%=path%>/unieap/js/jquery/plugins/tooltipster-master/js/jquery.tooltipster.min.js"></script>

<script type="text/javascript" src="<%=path%>/unieap/js/jquery/plugins/showBox/showBox.js"></script>
<link rel="stylesheet" type="text/css" href="<%=path%>/unieap/js/jquery/plugins/showBox/showBox.css" />


<script type="text/javascript"
	src="<%=path%>/unieap/js/jquery/plugins/jquery-ui-1.11.4.custom/jquery-ui.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/unieap/js/jquery/plugins/jquery-ui-1.11.4.custom/jquery-ui.min.css" />

<script type="text/javascript"
	src="<%=path%>/unieap/js/jquery/plugins/cssmenu/script.js"></script>
<link rel="stylesheet" type="text/css"
	href="<%=path%>/unieap/js/jquery/plugins/cssmenu/styles.css" />

<%
	CustomerProfileVO vo = (CustomerProfileVO) session.getAttribute(UnieapConstants.USER);
%>

<title>M-Care</title>

<style>
.bizDisplay {
	width: 758px;
	float: right;
}
</style>


<script type="text/javascript">
	var paymentFlag = "<%=vo.getPaymentFlag()%>";
	$(document).ready(function() {
		$("#accordion").accordion();
		if (paymentFlag == 'PPS') {
			$("li.postpaid").hide("slow");
			//$("ul.a01 >li ").show("slow"); 
		}
	});
	$(document).ready(function(){
		$("#myaccount").click();
	});
	function toFrame(href, title) {
		var url = basePathUrl + "/" + href+"&menuTitle="+title;
		//document.getElementById("iframepage").src = url;
		//$("#bizDisplay").load(encodeURIComponent(url));
		Showbo.Msg.wait('','Processing');
		$("#bizDisplay").load(encodeURI(url));
	}
	function iFrameHeight() {
		var ifm = document.getElementById("iframepage");
		var subWeb = document.frames ? document.frames["iframepage"].document :
		ifm.contentDocument;
		if (ifm != null && subWeb != null) {
			ifm.height = subWeb.body.scrollHeight;
		}
	}
</script>

</head>
<body style='background: #ffffff'>
	<div class='topMenu'>
		<div class='bodyWidth'>
			<div class='topMenuLeft'>
				<img alt="mcare-logo" src="/unieap/apps/mcare/images/logo.png">
			</div>
			<div class='topMenuRight'>
				<div class='topMenuRight1'>
					<img alt="mcare-logo"
						src="/unieap/apps/mcare/images/store_finder.png">
				</div>
				<div class='topMenuRight2'>
					<a href='#'>Store Finder</a>
				</div>
				<div class='topMenuRight1'>
					<img alt="mcare-logo"
						src="/unieap/apps/mcare/images/contact_us.png">
				</div>
				<div class='topMenuRight2'>
					<a href='#'>Contact Us</a>
				</div>
			</div>
		</div>
	</div>
	<div class='midContent'>
		<div class=" bodyWidth">
			<div id='cssmenu'>
				<ul>
					<li class='has-sub'><a href='#'><span>My
								Information</span></a>
						<ul>
							<li><a a href='####'
								onclick="toFrame('mCareController.do?method=changepassword','Change Password')"
								target="_self"><span>Change Password</span></a></li>
							<li><a href='####'
								onclick="toFrame('mCareController.do?method=myprofile','My Profile')"
								target="_self"><span>My Profile</span></a></li>
							<li class='last'><a a href='####'
								onclick="toFrame('mCareController.do?method=mysimcard','My SIM Card')"
								target="_self"><span>My SIM Card</span></a></li>
						</ul></li>
					<li class='has-sub'><a id="myaccount" href='#'><span>My Account</span></a>
						<ul>
							<li><a href="####"
								onclick="toFrame('mCareController.do?method=mybalance','My Balance')"
								target="_self"><span>My Balance</span></a></li>
							<li><a href='####'
								onclick="toFrame('mCareController.do?method=usagehistory','Usage History')"
								target="_self"><span>Usage History</span></a></li>
							<li><a href='####'
								onclick="toFrame('mCareController.do?method=myprofile','My Profile')"
								target="_self"><span>Deactivate Services</span></a></li>
							<li><a href='####'
								onclick="toFrame('mCareController.do?method=myprofile','My Profile')"
								target="_self"><span>Activate Services</span></a></li>
							<li><a href='####'
								onclick="toFrame('mCareController.do?method=myprofile','My Profile')"
								target="_self"><span>Change Tariff Plan</span></a></li>
							<li><a href='####'
								onclick="toFrame('mCareController.do?method=myprofile','My Profile')"
								target="_self"><span>My Subscriptions</span></a></li>
							<li><a href='####'
								onclick="toFrame('mCareController.do?method=myprofile','My Profile')"
								target="_self"><span>Top Up</span></a></li>
							<li><a href='####'
								onclick="toFrame('mCareController.do?method=myprofile','My Profile')"
								target="_self"><span>My Free Benefits</span></a></li>
							<li class='postpaid'><a a href='####'
								onclick="toFrame('mCareController.do?method=myprofile','My Profile')"
								target="_self"><span>Bill Info</span></a></li>
							<li class='postpaid'><a href='####'
								onclick="toFrame('mCareController.do?method=myprofile','My Profile')"
								target="_self"><span>Apply
										Credit Limit</span></a></li>
							<li class='postpaid'><a href='####'
								onclick="toFrame('mCareController.do?method=myprofile','My Profile')"
								target="_self"><span>Consupation
										Notification</span></a></li>
							<li class='postpaid'><a href='####'
								onclick="toFrame('mCareController.do?method=myprofile','My Profile')"
								target="_self"><span>Contact</span></a></li>
						</ul></li>
					<li class='last'><a href='#'><span>Contact</span></a></li>
				</ul>
			</div>
			<div id="bizDisplay" class='bizDisplay'>
			</div>
		</div>
	</div>
	<div class='footerHaut'>
		<div class='bodyWidth'>
			<div class="footerCopy">
				&copy;2015 Smart Axiata Co.,Ltd. All rights reserved.
				&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
					href=" http://www.smart.com.kh/legal#terms-conditions">Terms &
					Conditions</a> / <a href="http://www.smart.com.kh/legal#privacy-policy">Privacy
					Policy</a>
			</div>
		</div>
	</div>
</body>
</html>