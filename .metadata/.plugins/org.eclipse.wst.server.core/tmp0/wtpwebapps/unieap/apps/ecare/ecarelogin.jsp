<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	+ path + "/";
%>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge" />
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="mobile care,mobile service,self-service">
<meta http-equiv="description" content="unieap system">	
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no">    
<meta name="format-detection" content="telephone=no" /> 


<title>Login</title>

<link href="<%=path%>/apps/ecare/ecare.css" type="text/css"
	rel="stylesheet" />
<script type="text/javascript"
	src="<%=path%>/unieap/js/jquery/jquery-2.2.0.js"></script>

<link rel="stylesheet"
	href="<%=path%>/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/stylesheets/jquery.rambling.slider.css"
	type="text/css" media="screen">
<link rel="stylesheet"
	href="<%=path%>/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/stylesheets/style.min.css"
	type="text/css" media="screen">

<script type="text/javascript"
	src="<%=path%>/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/javascripts/jquery.rambling.slider.min.js"></script>

<script src="<%=path%>/unieap/js/CryptoJS v3.1.2/rollups/aes.js"></script>
<script src="<%=path%>/unieap/js/CryptoJS v3.1.2/components/aes.js"></script>
<script
	src="<%=path%>/unieap/js/CryptoJS v3.1.2/components/enc-base64-min.js"></script>
<script
	src="<%=path%>/unieap/js/CryptoJS v3.1.2/components/mode-ecb-min.js"></script>

<script src="<%=path%>/unieap/js/jquery/plugins/showBox/showBox.js"></script>
<link rel="stylesheet"
	href="<%=path%>/unieap/js/jquery/plugins/showBox/showBox.css"
	type="text/css" media="screen">
	

<script type="text/javascript">
	$(window).load(function() {
		$('#slider').ramblingSlider();
	});
	$(document).ready(function(){
		$("#sbmtButton").bind("click",function(){login();});
	});
	
	$(document).keypress(function(e) {  
	       if(e.which == 13) {  
	    	   login();
	       }  
	}); 
	
	function login(){
		var serviceNumber = $('#serviceNumber').val();
		var password = $('#password').val();
		var checkNum = $('#checkNum').val();
		if(serviceNumber==''){
			Showbo.Msg.alert('Please type in mobile number');
			return;
		}else if(password==''){
			Showbo.Msg.alert('Please type in password');
			return;
		}else if(checkNum==''){
			Showbo.Msg.alert('Please type in verification code');
			return;
		}else{
			var keyStr = "1234567890543210";
			var key = CryptoJS.enc.Utf8.parse(keyStr); 
			var encryptedData = CryptoJS.AES.encrypt(password, key, { 
					mode: CryptoJS.mode.ECB,
					padding: CryptoJS.pad.Pkcs7
				});
			var encryptedBase64Str = encryptedData.toString();
			
			$.ajax({
			    url: "<%=path%>/mCareLoginController.do?method=login",
			    dataType: "json",
			    data: { "serviceNumber": serviceNumber,'password':encryptedBase64Str,'checkNum':checkNum },
			    beforeSend: function(XMLHttpRequest) {
			    	Showbo.Msg.wait('','Processing');
			    },
			    success: function(result, textStatus) {
			    	if(result.isSuccess == 'failed'){
			    		 Showbo.Msg.alert(result.errorDesc);
			    		 //Showbo.Msg.confirm('Confirmation',ttest);
			    		 //Showbo.Msg.prompt('','01','myId',ttest);
			    		 Showbo.Msg.show({buttons:{yes:'Ok'},msg:result.errorDesc,title:Showbo.Msg.WARNING,fn:resultFn});
	                }else{
	                	 window.location.href= "<%=path%>/eCareController.do?method=homepage";
	                }
			    },
			    complete: function(XMLHttpRequest, textStatus) {
			    	//Showbo.Msg.hide();
			    },
			    error: function(XMLHttpRequest, textStatus, errorThrown) {
			    	alert(textStatus);
			    }
			});
		}
		
	}
	function resultFn(btn){
		 changeImg();
	}
	function aesEncrypt(data, keyStr, ivStr) {
        var sendData = CryptoJS.enc.Utf8.parse(data);
        var key = CryptoJS.enc.Utf8.parse(keyStr);
        var iv  = CryptoJS.enc.Utf8.parse(ivStr);
        var encrypted = CryptoJS.AES.encrypt(sendData, key,{iv:iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.Iso10126});
        //return CryptoJS.enc.Base64.stringify(encrypted.toString(CryptoJS.enc.Utf8));
        return CryptoJS.enc.Base64.stringify(encrypted.ciphertext);
    };
    function changeImg() {
         var s =Math.random();
         var newSrc = "<%=path%>/verifyCodeController.do?method=getVerifyCode&radom="+s;
		 $("#checkNumImg").attr("src", newSrc);
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
			<div class='midRangeImage'>
				<div id="wrapper">
					<div class="slider-wrapper theme-default">
						<div class="ribbon"></div>
						<div id="slider" class="ramblingSlider">
							<img
								src="<%=path%>/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/images/toystory.jpg"
								alt=""> <a href="http://ramblinglabs.com"><img
								src="<%=path%>/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/images/up.jpg"
								alt="" title="This is an example of a caption"></a> <img
								src="<%=path%>/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/images/walle.jpg"
								alt=""> <img
								src="<%=path%>/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/images/nemo.jpg"
								alt="" title="#htmlcaption">
						</div>
						<div id="htmlcaption" class="rambling-html-caption">
							<strong>This</strong> is an example of a <em>HTML</em> caption
							with <a href="#">a link</a>.
						</div>
					</div>
				</div>
			</div>
			<div class="midLoginArea">
				<div class="loginPageBox1">
					<div class="conTileH1">
						<img class="titleImg"
							src="https://smartcare.smart.com.kh/ecare/resources/customization/web/images/accessAcc.jpg" />
					</div>
					<div class="mb20">
						<dl class="formTableLine">
							<dt>
								<label for="ecid" id="label_for_ecid">Mobile Number</label>
							</dt>
							<dd>
								<input id="serviceNumber" name="serviceNumber" class="inputText l mr5"
									type="text" value="0" maxlength="15" />
							</dd>
						</dl>
						<dl class="formTableLine" id="smsLable">
							<dt>
								<label for="passwordIn" id="label_for_passwordIn">
									Password </label>
							</dt>
							<dd>
								<input id="password" name="password" class="inputText"
									type="password" oncontextmenu="return false;"
									onpaste="return false;" value="" maxlength="16" />
							</dd>
						</dl>
						<dl id="passwordLable" class="formTableLine">
							<dt>
								<label for="checkNum" id="label_for_checkNum">Verification
									Code</label>
							</dt>
							<dd>
								<div class='midLoginAreaVerifyCodeInput'>
									<input id="checkNum" name="checkNum" class="inputTextCheckCode"
										styleId="checkNum" type="text" value=""
										size="config:ECARE.RANDOM.NUMBER.LENGTH" maxlength="4" />
								</div>
								<div class='midLoginAreaVerifyCodeImg'>
									<img
										src="<%=path%>/verifyCodeController.do?method=getVerifyCode"
										onclick="changeImg()" id="checkNumImg"
										title="Unclear? Click me." />
								</div>
							</dd>
						</dl>
						<dl class="formTableLine">
							<dd class='inputButAlign'>
								<div class='midLoginAreaVerifyCodeInput'>
									<input id="sbmtButton" class="inputBut" type="button"
										tabindex="4" value="Login" />
								</div>
								<div class='midLoginAreaVerifyCodeImg'>
									<input id="registerButton" class="inputBut" type="button"
										tabindex="4" value="Register" />
								</div>
							</dd>
						</dl>
					</div>
				</div>
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