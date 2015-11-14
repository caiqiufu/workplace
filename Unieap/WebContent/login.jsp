<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@page import="com.unieap.UnieapConstants"%>

<%
	String errorDesc = "";
	Object error = request.getSession().getAttribute("SPRING_SECURITY_LAST_EXCEPTION");
	if (error != null) {
		errorDesc = error.toString();
		if (errorDesc.contains("Bad credentials") || errorDesc.contains("UserDetailsService")) {
			errorDesc = UnieapConstants.getMessage("user.login.error.usercode");
		}
	}
	String path = request.getContextPath();
%>
<html>
<head>
<title>Login</title>
<script defer type="text/javascript">
	var errorDesc = "<%=errorDesc%>";
	if(errorDesc==''){
		errorDesc = '<li></li>'
	}
	document.getElementById('message').innerHTML= errorDesc;
	function ecare() {
		window.location.href = "ExtAction.do?method=index";
	}
</script>
<style type="text/css">
body
{
    font: 12px 'Lucida Sans Unicode', 'Trebuchet MS', Arial, Helvetica;    
    margin: 0;
    background-color: #d9dee2;
    background-image: -webkit-gradient(linear, left top, left bottom, from(#ebeef2), to(#d9dee2));
    background-image: -webkit-linear-gradient(top, #ebeef2, #d9dee2);
    background-image: -moz-linear-gradient(top, #ebeef2, #d9dee2);
    background-image: -ms-linear-gradient(top, #ebeef2, #d9dee2);
    background-image: -o-linear-gradient(top, #ebeef2, #d9dee2);
    background-image: linear-gradient(top, #ebeef2, #d9dee2);  
    background-image: url(unieap/web_home-page31.jpg);  
}

.lg-container {
	width: 275px;
	margin: 100px auto;
	padding: 20px 40px;
	border: 1px solid #f4f4f4;
	background: rgba(255, 255, 255, .5);
	-webkit-border-radius: 10px;
	-moz-border-radius: 10px;
	-webkit-box-shadow: 0 0 2px #aaa;
	-moz-box-shadow: 0 0 2px #aaa;
}

.lg-container h1 {
	font-size: 40px;
	text-align: center;
}

#lg-form>div {
	margin: 10px 5px;
	padding: 5px 0;
}

#lg-form label {
	display: none;
	font-size: 20px;
	line-height: 25px;
}

#lg-form input[type="text"], #lg-form input[type="password"] {
	border: 1px solid rgba(51, 51, 51, .5);
	-webkit-border-radius: 10px;
	-moz-border-radius: 10px;
	padding: 5px;
	font-size: 16px;
	line-height: 20px;
	width: 100%;
	font-family: 'Oleo Script', cursive;
	text-align: center;
}

#lg-form div:nth-child(3) {
	text-align: center;
}

#lg-form button {
	font-family: 'Oleo Script', cursive;
	font-size: 18px;
	border: 1px solid #000;
	padding: 5px 10px;
	border: 1px solid rgba(51, 51, 51, .5);
	-webkit-border-radius: 10px;
	-moz-border-radius: 10px;
	-webkit-box-shadow: 2px 1px 1px #aaa;
	-moz-box-shadow: 2px 1px 1px #aaa;
	cursor: pointer;
}

#lg-form button:active {
	-webkit-box-shadow: 0px 0px 1px #aaa;
	-moz-box-shadow: 0px 0px 1px #aaa;
}

#lg-form button:hover {
	background: #f4f4f4;
}

#message {
	width: 100%;
	text-align: center
}

.success {
	color: green;
}

.error {
	color: red;
	text-align: center;
	font-size: 10px;
	line-height: 25px;
}
</style>

</head>

<body>
	<div class="lg-container">
		<h1>Mobile Care</h1>
		<form action="j_spring_security_check" id="lg-form" name="lg-form"
			method="post">
			<div>
				<label for="username">Username:</label> <input type="text"
					name="j_username" id="username" placeholder="user name" />
			</div>
			<div>
				<label for="password">Password:</label> <input type="password"
					name="j_password" id="password" placeholder="password" />
			</div>
			<div>
				<button type="submit" id="login">Login</button>
			</div>
		</form>
		<div class="error" id="message"><%=errorDesc%></div>
	</div>
</body>
</html>
