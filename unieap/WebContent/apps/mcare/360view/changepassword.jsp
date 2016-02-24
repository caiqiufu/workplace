<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/unieap/unieap_unlogin.jsp"%>
<%@page
	import="com.unieap.UnieapConstants,com.apps.esb.service.bss.vo.macre.customerprofile.CustomerProfileVO"%>
<%
	CustomerProfileVO vo = (CustomerProfileVO) session.getAttribute(UnieapConstants.USER);
%>
<script src="<%=path%>/unieap/js/CryptoJS v3.1.2/rollups/aes.js"></script>
<script src="<%=path%>/unieap/js/CryptoJS v3.1.2/components/aes.js"></script>
<script
	src="<%=path%>/unieap/js/CryptoJS v3.1.2/components/enc-base64-min.js"></script>
<script
	src="<%=path%>/unieap/js/CryptoJS v3.1.2/components/mode-ecb-min.js"></script>
<script type="text/javascript">
	function onlyNum(){
		if(!(event.keyCode==46)&&!(event.keyCode==8)&&!(event.keyCode==37)&&!(event.keyCode==39)){
			if(!((event.keyCode>=48&&event.keyCode<=57)||(event.keyCode>=96&&event.keyCode<=105))){
				event.returnValue=false;
			}
		}
	}
	$(document).ready(function(){
		$("#sbmtButton").bind("click",function(){changePassword();});
	});
	$(document).keypress(function(e) {  
	       if(e.which == 13) {  
	    	   changePassword();
	       }  
	});
	$('#oldPassword').tooltipster({
		content: 'please type in 6 digits',
		trigger: 'click',
		position: 'right'
	});
	//$('#oldPassword').tooltipster('hide');
	
	$('#newPassword').tooltipster({
		content: 'please type in 6 digits',
		trigger: 'click',
		position: 'right'
	});
	$('#confirmNewPassword').tooltipster({
		content: 'please type in 6 digits',
		trigger: 'click',
		position: 'right'
	});
	
	//$('#newPassword').tooltipster('hide');
	function checkValueEmpty(inputId){
		if(inputId=='oldPassword'){
			var oldPassword = $('#oldPassword').val();
			if(oldPassword == null || oldPassword == '' || oldPassword.length != 6){
				$('#oldPassword').tooltipster('show');
				return false;
			}else{
				$('#oldPassword').tooltipster('hide');
				return true;
			}
		}else if(inputId=='newPassword'){
			var newPassword = $('#newPassword').val();
			if(newPassword == null || newPassword == '' || newPassword.length != 6){
				$('#newPassword').tooltipster('show');
				return false;
			}else{
				$('#newPassword').tooltipster('hide');
				return true;
			}
		}else{
			var confirmNewPassword = $('#confirmNewPassword').val();
			if(confirmNewPassword == null || confirmNewPassword == '' || confirmNewPassword.length != 6){
				$('#confirmNewPassword').tooltipster('show');
				return false;
			}else{
				$('#confirmNewPassword').tooltipster('hide');
				return true;
			}
		}
	}
	function checkNewPassword(){
		var serviceNumber = ${serviceNumber};
		var oldPassword = $('#oldPassword').val();
		var newPassword = $('#newPassword').val();
		var confirmNewPassword = $('#confirmNewPassword').val();
		if(oldPassword==newPassword){
			Showbo.Msg.show({msg:'<div class="showBoxContext">The new password must be different <br> from the old password.</div>',title:Showbo.Msg.WARNING});
			return false;
		}else if(newPassword!=confirmNewPassword){
			Showbo.Msg.show({msg:'<div class="showBoxContext">The confirm new password must match <br> the new password.</div>',title:Showbo.Msg.WARNING});
			return false;
		}else{
			return true;
		}
	}
	function changePassword(){
		if(checkValueEmpty('oldPassword')&&checkValueEmpty('newPassword')&&checkNewPassword()){
			Showbo.Msg.confirm('Please confirm to change service password',resultFn);
		}
	}
	function resultFn(btn){
		 if(btn=='yes'){
			var serviceNumber = ${serviceNumber};
			var oldPassword = $('#oldPassword').val();
			var newPassword = $('#newPassword').val();
			
			var keyStr = "1234567890543210";
			var key = CryptoJS.enc.Utf8.parse(keyStr); 
			var encryptedOldPassword = CryptoJS.AES.encrypt(oldPassword, key, { 
				mode: CryptoJS.mode.ECB,
				padding: CryptoJS.pad.Pkcs7
				});
			var encryptedOldPasswordBase64Str = encryptedOldPassword.toString();
			var encryptedNewPassword = CryptoJS.AES.encrypt(newPassword, key, { 
				mode: CryptoJS.mode.ECB,
				padding: CryptoJS.pad.Pkcs7
				});
			var encryptedNewPasswordBase64Str = encryptedNewPassword.toString();
			Showbo.Msg.wait('','Processing');
			$.ajax({
			    url: "<%=path%>/mCareController.do?method=saveChangepassword",
			    dataType: "json",
			    data: { "serviceNumber": serviceNumber,'oldPassword':encryptedOldPasswordBase64Str,'newPassword':encryptedNewPasswordBase64Str },
			    beforeSend: function(XMLHttpRequest) {
			    	//XHR:XMLHttpRequest
			    	//alert('beforeSend');
			    },
			    success: function(result, textStatus) {
			    	if(result.isSuccess == 'failed'){
			    		Showbo.Msg.alert(result.errorDesc);
	                }else{
	                	Showbo.Msg.alert(result.errorDesc);
	                }
			    },
			    complete: function(XMLHttpRequest, textStatus) {
			    	Showbo.Msg.hide();
			    },
			    error: function(XMLHttpRequest, textStatus, errorThrown) {
			    	alert(textStatus);
			    }
			});
		 }
	}
</script>
<div class="bizPageDisplay">
	<div class="bizPageTitle">
		<span class="bizPageTitleFont">>>${menuTitle}</span>
	</div>
	<div class="fl">
		<div class="img">
			<img src="<%=path%>/apps/mcare/images/ACCOUNTS_BALANCE_SEARCH.png"
				alt="my balance" width="120" />
		</div>
	</div>
	<div class="desc">
		<div class="optionsDesc">The password used to identify customer
			for self service, 6 digits included of 0~9.</div>
		<div>
			<span class="color_6">My Name : </span><font class='descTitle1'>${customerName}</font>
		</div>
		<div>
			<span class="color_6">Service Number : </span><font
				class='descTitle1'>${serviceNumber}</font>
		</div>
	</div>
	<div class='cl'></div>
	<div style="border-top: 1px solid #00e1e1"></div>
	<div style="width:300px;height:50px;align:center;margin: 0 auto; ">
		<table>
			<tr>
				<td class="tableDispalyOneField"><span class="tableDispalyField">Service
						Number</span></td>
				<td><span id="serviceNumber" class="tableDispalyValue">${serviceNumber}</span></td>
			</tr>
			<tr>
				<td class="tableDispalyOneField"><span class="tableDispalyField"><font size='4' color=red>*</font>Old
						Password</span></td>
				<td>
					<input id="oldPassword" name="oldPassword" class="inputText l mr5"
									type="password" value="" maxlength="6" onkeydown="onlyNum();" onblur="checkValueEmpty('oldPassword')"/>
				</td>
			</tr>
			<tr>
				<td class="tableDispalyOneField"><span class="tableDispalyField"><font size='4' color=red>*</font>New
						Password</span></td>
				<td>
					<input id="newPassword" name="newPassword"  class="inputText l mr5"
									type="password" value="" maxlength="6" onkeydown="onlyNum();" onblur="checkValueEmpty('newPassword')"/>
				</td>
			</tr>
			<tr>
				<td class="tableDispalyOneField"><span class="tableDispalyField"><font size='4' color=red>*</font>Confirm New Password</span></td>
				<td>
					<input id="confirmNewPassword" name="confirmNewPassword"  class="inputText l mr5"
									type="password" value="" maxlength="6" onkeydown="onlyNum();" onblur="checkValueEmpty('confirmNewPassword')"/>
				</td>
			</tr>
			<tr>
				<td colspan='2' align="center" height="20px">
				</td>
			</tr>
			<tr>
				<td colspan='2' align="right" height="20px">
					<div>
						<input id="sbmtButton" class="inputBut" type="button" tabindex="4"
							value="Submit" />
					</div>
				</td>
			</tr>
		</table>
	</div>
</div>