/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.23
 * Generated at: 2016-04-10 18:43:26 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.apps.ecare;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;

public final class ecarelogin_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = null;
  }

  private javax.el.ExpressionFactory _el_expressionfactory;
  private org.apache.tomcat.InstanceManager _jsp_instancemanager;

  public java.util.Map<java.lang.String,java.lang.Long> getDependants() {
    return _jspx_dependants;
  }

  public java.util.Set<java.lang.String> getPackageImports() {
    return _jspx_imports_packages;
  }

  public java.util.Set<java.lang.String> getClassImports() {
    return _jspx_imports_classes;
  }

  public void _jspInit() {
    _el_expressionfactory = _jspxFactory.getJspApplicationContext(getServletConfig().getServletContext()).getExpressionFactory();
    _jsp_instancemanager = org.apache.jasper.runtime.InstanceManagerFactory.getInstanceManager(getServletConfig());
  }

  public void _jspDestroy() {
  }

  public void _jspService(final javax.servlet.http.HttpServletRequest request, final javax.servlet.http.HttpServletResponse response)
        throws java.io.IOException, javax.servlet.ServletException {

final java.lang.String _jspx_method = request.getMethod();
if (!"GET".equals(_jspx_method) && !"POST".equals(_jspx_method) && !"HEAD".equals(_jspx_method) && !javax.servlet.DispatcherType.ERROR.equals(request.getDispatcherType())) {
response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED, "JSPs only permit GET POST or HEAD");
return;
}

    final javax.servlet.jsp.PageContext pageContext;
    javax.servlet.http.HttpSession session = null;
    final javax.servlet.ServletContext application;
    final javax.servlet.ServletConfig config;
    javax.servlet.jsp.JspWriter out = null;
    final java.lang.Object page = this;
    javax.servlet.jsp.JspWriter _jspx_out = null;
    javax.servlet.jsp.PageContext _jspx_page_context = null;


    try {
      response.setContentType("text/html;charset=UTF-8");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\r\n");
      out.write("\r\n");

    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	+ path + "/";

      out.write("\r\n");
      out.write("\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n");
      out.write("<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n");
      out.write("<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"0\">\r\n");
      out.write("<meta http-equiv=\"keywords\" content=\"mobile care,mobile service,self-service\">\r\n");
      out.write("<meta http-equiv=\"description\" content=\"unieap system\">\t\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\">    \r\n");
      out.write("<meta name=\"format-detection\" content=\"telephone=no\" /> \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<title>Login</title>\r\n");
      out.write("\r\n");
      out.write("<link href=\"");
      out.print(path);
      out.write("/apps/ecare/ecare.css\" type=\"text/css\"\r\n");
      out.write("\trel=\"stylesheet\" />\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.print(path);
      out.write("/unieap/js/jquery/jquery-2.2.0.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/stylesheets/jquery.rambling.slider.css\"\r\n");
      out.write("\ttype=\"text/css\" media=\"screen\">\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/stylesheets/style.min.css\"\r\n");
      out.write("\ttype=\"text/css\" media=\"screen\">\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/javascripts/jquery.rambling.slider.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script src=\"");
      out.print(path);
      out.write("/unieap/js/CryptoJS v3.1.2/rollups/aes.js\"></script>\r\n");
      out.write("<script src=\"");
      out.print(path);
      out.write("/unieap/js/CryptoJS v3.1.2/components/aes.js\"></script>\r\n");
      out.write("<script\r\n");
      out.write("\tsrc=\"");
      out.print(path);
      out.write("/unieap/js/CryptoJS v3.1.2/components/enc-base64-min.js\"></script>\r\n");
      out.write("<script\r\n");
      out.write("\tsrc=\"");
      out.print(path);
      out.write("/unieap/js/CryptoJS v3.1.2/components/mode-ecb-min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script src=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/showBox/showBox.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\"\r\n");
      out.write("\thref=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/showBox/showBox.css\"\r\n");
      out.write("\ttype=\"text/css\" media=\"screen\">\r\n");
      out.write("\t\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\t$(window).load(function() {\r\n");
      out.write("\t\t$('#slider').ramblingSlider();\r\n");
      out.write("\t});\r\n");
      out.write("\t$(document).ready(function(){\r\n");
      out.write("\t\t$(\"#sbmtButton\").bind(\"click\",function(){login();});\r\n");
      out.write("\t});\r\n");
      out.write("\t\r\n");
      out.write("\t$(document).keypress(function(e) {  \r\n");
      out.write("\t       if(e.which == 13) {  \r\n");
      out.write("\t    \t   login();\r\n");
      out.write("\t       }  \r\n");
      out.write("\t}); \r\n");
      out.write("\t\r\n");
      out.write("\tfunction login(){\r\n");
      out.write("\t\tvar serviceNumber = $('#serviceNumber').val();\r\n");
      out.write("\t\tvar password = $('#password').val();\r\n");
      out.write("\t\tvar checkNum = $('#checkNum').val();\r\n");
      out.write("\t\tif(serviceNumber==''){\r\n");
      out.write("\t\t\tShowbo.Msg.alert('Please type in mobile number');\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}else if(password==''){\r\n");
      out.write("\t\t\tShowbo.Msg.alert('Please type in password');\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}else if(checkNum==''){\r\n");
      out.write("\t\t\tShowbo.Msg.alert('Please type in verification code');\r\n");
      out.write("\t\t\treturn;\r\n");
      out.write("\t\t}else{\r\n");
      out.write("\t\t\tvar keyStr = \"1234567890543210\";\r\n");
      out.write("\t\t\tvar key = CryptoJS.enc.Utf8.parse(keyStr); \r\n");
      out.write("\t\t\tvar encryptedData = CryptoJS.AES.encrypt(password, key, { \r\n");
      out.write("\t\t\t\t\tmode: CryptoJS.mode.ECB,\r\n");
      out.write("\t\t\t\t\tpadding: CryptoJS.pad.Pkcs7\r\n");
      out.write("\t\t\t\t});\r\n");
      out.write("\t\t\tvar encryptedBase64Str = encryptedData.toString();\r\n");
      out.write("\t\t\t\r\n");
      out.write("\t\t\t$.ajax({\r\n");
      out.write("\t\t\t    url: \"");
      out.print(path);
      out.write("/mCareLoginController.do?method=login\",\r\n");
      out.write("\t\t\t    dataType: \"json\",\r\n");
      out.write("\t\t\t    data: { \"serviceNumber\": serviceNumber,'password':encryptedBase64Str,'checkNum':checkNum },\r\n");
      out.write("\t\t\t    beforeSend: function(XMLHttpRequest) {\r\n");
      out.write("\t\t\t    \tShowbo.Msg.wait('','Processing');\r\n");
      out.write("\t\t\t    },\r\n");
      out.write("\t\t\t    success: function(result, textStatus) {\r\n");
      out.write("\t\t\t    \tif(result.isSuccess == 'failed'){\r\n");
      out.write("\t\t\t    \t\t Showbo.Msg.alert(result.errorDesc);\r\n");
      out.write("\t\t\t    \t\t //Showbo.Msg.confirm('Confirmation',ttest);\r\n");
      out.write("\t\t\t    \t\t //Showbo.Msg.prompt('','01','myId',ttest);\r\n");
      out.write("\t\t\t    \t\t Showbo.Msg.show({buttons:{yes:'Ok'},msg:result.errorDesc,title:Showbo.Msg.WARNING,fn:resultFn});\r\n");
      out.write("\t                }else{\r\n");
      out.write("\t                \t window.location.href= \"");
      out.print(path);
      out.write("/eCareController.do?method=homepage\";\r\n");
      out.write("\t                }\r\n");
      out.write("\t\t\t    },\r\n");
      out.write("\t\t\t    complete: function(XMLHttpRequest, textStatus) {\r\n");
      out.write("\t\t\t    \t//Showbo.Msg.hide();\r\n");
      out.write("\t\t\t    },\r\n");
      out.write("\t\t\t    error: function(XMLHttpRequest, textStatus, errorThrown) {\r\n");
      out.write("\t\t\t    \talert(textStatus);\r\n");
      out.write("\t\t\t    }\r\n");
      out.write("\t\t\t});\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\t\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction resultFn(btn){\r\n");
      out.write("\t\t changeImg();\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction aesEncrypt(data, keyStr, ivStr) {\r\n");
      out.write("        var sendData = CryptoJS.enc.Utf8.parse(data);\r\n");
      out.write("        var key = CryptoJS.enc.Utf8.parse(keyStr);\r\n");
      out.write("        var iv  = CryptoJS.enc.Utf8.parse(ivStr);\r\n");
      out.write("        var encrypted = CryptoJS.AES.encrypt(sendData, key,{iv:iv,mode:CryptoJS.mode.CBC,padding:CryptoJS.pad.Iso10126});\r\n");
      out.write("        //return CryptoJS.enc.Base64.stringify(encrypted.toString(CryptoJS.enc.Utf8));\r\n");
      out.write("        return CryptoJS.enc.Base64.stringify(encrypted.ciphertext);\r\n");
      out.write("    };\r\n");
      out.write("    function changeImg() {\r\n");
      out.write("         var s =Math.random();\r\n");
      out.write("         var newSrc = \"");
      out.print(path);
      out.write("/verifyCodeController.do?method=getVerifyCode&radom=\"+s;\r\n");
      out.write("\t\t $(\"#checkNumImg\").attr(\"src\", newSrc);\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("</head>\r\n");
      out.write("<body style='background: #ffffff'>\r\n");
      out.write("\t<div class='topMenu'>\r\n");
      out.write("\t\t<div class='bodyWidth'>\r\n");
      out.write("\t\t\t<div class='topMenuLeft'>\r\n");
      out.write("\t\t\t\t<img alt=\"mcare-logo\" src=\"/unieap/apps/mcare/images/logo.png\">\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class='topMenuRight'>\r\n");
      out.write("\t\t\t\t<div class='topMenuRight1'>\r\n");
      out.write("\t\t\t\t\t<img alt=\"mcare-logo\"\r\n");
      out.write("\t\t\t\t\t\tsrc=\"/unieap/apps/mcare/images/store_finder.png\">\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class='topMenuRight2'>\r\n");
      out.write("\t\t\t\t\t<a href='#'>Store Finder</a>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class='topMenuRight1'>\r\n");
      out.write("\t\t\t\t\t<img alt=\"mcare-logo\"\r\n");
      out.write("\t\t\t\t\t\tsrc=\"/unieap/apps/mcare/images/contact_us.png\">\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t<div class='topMenuRight2'>\r\n");
      out.write("\t\t\t\t\t<a href='#'>Contact Us</a>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class='midContent'>\r\n");
      out.write("\t\t<div class=\" bodyWidth\">\r\n");
      out.write("\t\t\t<div class='midRangeImage'>\r\n");
      out.write("\t\t\t\t<div id=\"wrapper\">\r\n");
      out.write("\t\t\t\t\t<div class=\"slider-wrapper theme-default\">\r\n");
      out.write("\t\t\t\t\t\t<div class=\"ribbon\"></div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"slider\" class=\"ramblingSlider\">\r\n");
      out.write("\t\t\t\t\t\t\t<img\r\n");
      out.write("\t\t\t\t\t\t\t\tsrc=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/images/toystory.jpg\"\r\n");
      out.write("\t\t\t\t\t\t\t\talt=\"\"> <a href=\"http://ramblinglabs.com\"><img\r\n");
      out.write("\t\t\t\t\t\t\t\tsrc=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/images/up.jpg\"\r\n");
      out.write("\t\t\t\t\t\t\t\talt=\"\" title=\"This is an example of a caption\"></a> <img\r\n");
      out.write("\t\t\t\t\t\t\t\tsrc=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/images/walle.jpg\"\r\n");
      out.write("\t\t\t\t\t\t\t\talt=\"\"> <img\r\n");
      out.write("\t\t\t\t\t\t\t\tsrc=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/gonzedge-rambling.slider-7ef738f/assets/images/nemo.jpg\"\r\n");
      out.write("\t\t\t\t\t\t\t\talt=\"\" title=\"#htmlcaption\">\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t<div id=\"htmlcaption\" class=\"rambling-html-caption\">\r\n");
      out.write("\t\t\t\t\t\t\t<strong>This</strong> is an example of a <em>HTML</em> caption\r\n");
      out.write("\t\t\t\t\t\t\twith <a href=\"#\">a link</a>.\r\n");
      out.write("\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div class=\"midLoginArea\">\r\n");
      out.write("\t\t\t\t<div class=\"loginPageBox1\">\r\n");
      out.write("\t\t\t\t\t<div class=\"conTileH1\">\r\n");
      out.write("\t\t\t\t\t\t<img class=\"titleImg\"\r\n");
      out.write("\t\t\t\t\t\t\tsrc=\"https://smartcare.smart.com.kh/ecare/resources/customization/web/images/accessAcc.jpg\" />\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t<div class=\"mb20\">\r\n");
      out.write("\t\t\t\t\t\t<dl class=\"formTableLine\">\r\n");
      out.write("\t\t\t\t\t\t\t<dt>\r\n");
      out.write("\t\t\t\t\t\t\t\t<label for=\"ecid\" id=\"label_for_ecid\">Mobile Number</label>\r\n");
      out.write("\t\t\t\t\t\t\t</dt>\r\n");
      out.write("\t\t\t\t\t\t\t<dd>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"serviceNumber\" name=\"serviceNumber\" class=\"inputText l mr5\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\ttype=\"text\" value=\"0\" maxlength=\"15\" />\r\n");
      out.write("\t\t\t\t\t\t\t</dd>\r\n");
      out.write("\t\t\t\t\t\t</dl>\r\n");
      out.write("\t\t\t\t\t\t<dl class=\"formTableLine\" id=\"smsLable\">\r\n");
      out.write("\t\t\t\t\t\t\t<dt>\r\n");
      out.write("\t\t\t\t\t\t\t\t<label for=\"passwordIn\" id=\"label_for_passwordIn\">\r\n");
      out.write("\t\t\t\t\t\t\t\t\tPassword </label>\r\n");
      out.write("\t\t\t\t\t\t\t</dt>\r\n");
      out.write("\t\t\t\t\t\t\t<dd>\r\n");
      out.write("\t\t\t\t\t\t\t\t<input id=\"password\" name=\"password\" class=\"inputText\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\ttype=\"password\" oncontextmenu=\"return false;\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\tonpaste=\"return false;\" value=\"\" maxlength=\"16\" />\r\n");
      out.write("\t\t\t\t\t\t\t</dd>\r\n");
      out.write("\t\t\t\t\t\t</dl>\r\n");
      out.write("\t\t\t\t\t\t<dl id=\"passwordLable\" class=\"formTableLine\">\r\n");
      out.write("\t\t\t\t\t\t\t<dt>\r\n");
      out.write("\t\t\t\t\t\t\t\t<label for=\"checkNum\" id=\"label_for_checkNum\">Verification\r\n");
      out.write("\t\t\t\t\t\t\t\t\tCode</label>\r\n");
      out.write("\t\t\t\t\t\t\t</dt>\r\n");
      out.write("\t\t\t\t\t\t\t<dd>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class='midLoginAreaVerifyCodeInput'>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input id=\"checkNum\" name=\"checkNum\" class=\"inputTextCheckCode\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tstyleId=\"checkNum\" type=\"text\" value=\"\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tsize=\"config:ECARE.RANDOM.NUMBER.LENGTH\" maxlength=\"4\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class='midLoginAreaVerifyCodeImg'>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<img\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tsrc=\"");
      out.print(path);
      out.write("/verifyCodeController.do?method=getVerifyCode\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tonclick=\"changeImg()\" id=\"checkNumImg\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttitle=\"Unclear? Click me.\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</dd>\r\n");
      out.write("\t\t\t\t\t\t</dl>\r\n");
      out.write("\t\t\t\t\t\t<dl class=\"formTableLine\">\r\n");
      out.write("\t\t\t\t\t\t\t<dd class='inputButAlign'>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class='midLoginAreaVerifyCodeInput'>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input id=\"sbmtButton\" class=\"inputBut\" type=\"button\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttabindex=\"4\" value=\"Login\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t\t<div class='midLoginAreaVerifyCodeImg'>\r\n");
      out.write("\t\t\t\t\t\t\t\t\t<input id=\"registerButton\" class=\"inputBut\" type=\"button\"\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\ttabindex=\"4\" value=\"Register\" />\r\n");
      out.write("\t\t\t\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t\t\t\t</dd>\r\n");
      out.write("\t\t\t\t\t\t</dl>\r\n");
      out.write("\t\t\t\t\t</div>\r\n");
      out.write("\t\t\t\t</div>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("\t<div class='footerHaut'>\r\n");
      out.write("\t\t<div class='bodyWidth'>\r\n");
      out.write("\t\t\t<div class=\"footerCopy\">\r\n");
      out.write("\t\t\t\t&copy;2015 Smart Axiata Co.,Ltd. All rights reserved.\r\n");
      out.write("\t\t\t\t&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a\r\n");
      out.write("\t\t\t\t\thref=\" http://www.smart.com.kh/legal#terms-conditions\">Terms &\r\n");
      out.write("\t\t\t\t\tConditions</a> / <a href=\"http://www.smart.com.kh/legal#privacy-policy\">Privacy\r\n");
      out.write("\t\t\t\t\tPolicy</a>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t</div>\r\n");
      out.write("\t</div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>");
    } catch (java.lang.Throwable t) {
      if (!(t instanceof javax.servlet.jsp.SkipPageException)){
        out = _jspx_out;
        if (out != null && out.getBufferSize() != 0)
          try {
            if (response.isCommitted()) {
              out.flush();
            } else {
              out.clearBuffer();
            }
          } catch (java.io.IOException e) {}
        if (_jspx_page_context != null) _jspx_page_context.handlePageException(t);
        else throw new ServletException(t);
      }
    } finally {
      _jspxFactory.releasePageContext(_jspx_page_context);
    }
  }
}