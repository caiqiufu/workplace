/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.23
 * Generated at: 2016-04-10 18:43:37 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.apps.ecare;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.unieap.UnieapConstants;
import com.apps.esb.service.bss.vo.macre.customerprofile.CustomerProfileVO;

public final class homepage_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/unieap/unieap_unlogin.jsp", Long.valueOf(1456419423342L));
  }

  private static final java.util.Set<java.lang.String> _jspx_imports_packages;

  private static final java.util.Set<java.lang.String> _jspx_imports_classes;

  static {
    _jspx_imports_packages = new java.util.HashSet<>();
    _jspx_imports_packages.add("javax.servlet");
    _jspx_imports_packages.add("java.util");
    _jspx_imports_packages.add("javax.servlet.http");
    _jspx_imports_packages.add("javax.servlet.jsp");
    _jspx_imports_classes = new java.util.HashSet<>();
    _jspx_imports_classes.add("com.apps.esb.service.bss.vo.macre.customerprofile.CustomerProfileVO");
    _jspx_imports_classes.add("com.unieap.UnieapConstants");
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
      response.setContentType("text/html");
      pageContext = _jspxFactory.getPageContext(this, request, response,
      			null, true, 8192, true);
      _jspx_page_context = pageContext;
      application = pageContext.getServletContext();
      config = pageContext.getServletConfig();
      session = pageContext.getSession();
      out = pageContext.getOut();
      _jspx_out = out;

      out.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01//EN\" \"http://www.w3.org/TR/html4/strict.dtd\">\r\n");
      out.write('\r');
      out.write('\n');

    String path = request.getContextPath();
	String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	+ path + "/";

      out.write("\r\n");
      out.write("<link href=\"");
      out.print(path);
      out.write("/apps/mcare/mcare.css\" type=\"text/css\" rel=\"stylesheet\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(path);
      out.write("/unieap/js/jquery/jquery-2.2.0.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar basePathUrl = \"");
      out.print(basePath);
      out.write("\";\r\n");
      out.write("\t$(document).ready(function () { \r\n");
      out.write("\t\tShowbo.Msg.hide();\r\n");
      out.write("\t}); \r\n");
      out.write("</script>\r\n");
      out.write("<!-- unieap用户权限文件 -->\r\n");
      out.write("<meta http-equiv=\"pragma\" content=\"no-cache\">\r\n");
      out.write("<meta http-equiv=\"cache-control\" content=\"no-cache\">\r\n");
      out.write("<meta http-equiv=\"expires\" content=\"0\">\r\n");
      out.write("<meta http-equiv=\"keywords\" content=\"mobile care,mobile service,self-service\">\r\n");
      out.write("<meta http-equiv=\"description\" content=\"unieap system\">\t\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\r\n");
      out.write("<meta http-equiv=\"Content-Type\" content=\"text/html; charset=utf-8\" />\r\n");
      out.write("<meta name=\"viewport\" content=\"width=device-width, initial-scale=1, maximum-scale=1, user-scalable=no\">    \r\n");
      out.write("<meta name=\"format-detection\" content=\"telephone=no\" /> \r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<html xmlns=\"http://www.w3.org/1999/xhtml\">\r\n");
      out.write("<head>\r\n");
      out.write("<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\" />\r\n");
      out.write("\r\n");
      out.write("<link href=\"");
      out.print(path);
      out.write("/apps/mcare/mcare.css\" type=\"text/css\" rel=\"stylesheet\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(path);
      out.write("/unieap/js/jquery/jquery-2.2.0.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/tooltipster-master/css/tooltipster.css\" />\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/tooltipster-master/js/jquery.tooltipster.min.js\"></script>\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/showBox/showBox.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/showBox/showBox.css\" />\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/jquery-ui-1.11.4.custom/jquery-ui.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/jquery-ui-1.11.4.custom/jquery-ui.min.css\" />\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/cssmenu/script.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\"\r\n");
      out.write("\thref=\"");
      out.print(path);
      out.write("/unieap/js/jquery/plugins/cssmenu/styles.css\" />\r\n");
      out.write("\r\n");

	CustomerProfileVO vo = (CustomerProfileVO) session.getAttribute(UnieapConstants.USER);

      out.write("\r\n");
      out.write("\r\n");
      out.write("<title>M-Care</title>\r\n");
      out.write("\r\n");
      out.write("<style>\r\n");
      out.write(".bizDisplay {\r\n");
      out.write("\twidth: 758px;\r\n");
      out.write("\tfloat: right;\r\n");
      out.write("}\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
      out.write("\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar paymentFlag = \"");
      out.print(vo.getPaymentFlag());
      out.write("\";\r\n");
      out.write("\t$(document).ready(function() {\r\n");
      out.write("\t\t$(\"#accordion\").accordion();\r\n");
      out.write("\t\tif (paymentFlag == 'PPS') {\r\n");
      out.write("\t\t\t$(\"li.postpaid\").hide(\"slow\");\r\n");
      out.write("\t\t\t//$(\"ul.a01 >li \").show(\"slow\"); \r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("\t$(document).ready(function(){\r\n");
      out.write("\t\t$(\"#myaccount\").click();\r\n");
      out.write("\t});\r\n");
      out.write("\tfunction toFrame(href, title) {\r\n");
      out.write("\t\tvar url = basePathUrl + \"/\" + href+\"&menuTitle=\"+title;\r\n");
      out.write("\t\t//document.getElementById(\"iframepage\").src = url;\r\n");
      out.write("\t\t//$(\"#bizDisplay\").load(encodeURIComponent(url));\r\n");
      out.write("\t\tShowbo.Msg.wait('','Processing');\r\n");
      out.write("\t\t$(\"#bizDisplay\").load(encodeURI(url));\r\n");
      out.write("\t}\r\n");
      out.write("\tfunction iFrameHeight() {\r\n");
      out.write("\t\tvar ifm = document.getElementById(\"iframepage\");\r\n");
      out.write("\t\tvar subWeb = document.frames ? document.frames[\"iframepage\"].document :\r\n");
      out.write("\t\tifm.contentDocument;\r\n");
      out.write("\t\tif (ifm != null && subWeb != null) {\r\n");
      out.write("\t\t\tifm.height = subWeb.body.scrollHeight;\r\n");
      out.write("\t\t}\r\n");
      out.write("\t}\r\n");
      out.write("</script>\r\n");
      out.write("\r\n");
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
      out.write("\t\t\t<div id='cssmenu'>\r\n");
      out.write("\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t<li class='has-sub'><a href='#'><span>My\r\n");
      out.write("\t\t\t\t\t\t\t\tInformation</span></a>\r\n");
      out.write("\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t<li><a a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=changepassword','Change Password')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>Change Password</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li><a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=myprofile','My Profile')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>My Profile</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li class='last'><a a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=mysimcard','My SIM Card')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>My SIM Card</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t</ul></li>\r\n");
      out.write("\t\t\t\t\t<li class='has-sub'><a id=\"myaccount\" href='#'><span>My Account</span></a>\r\n");
      out.write("\t\t\t\t\t\t<ul>\r\n");
      out.write("\t\t\t\t\t\t\t<li><a href=\"####\"\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=mybalance','My Balance')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>My Balance</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li><a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=usagehistory','Usage History')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>Usage History</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li><a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=myprofile','My Profile')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>Deactivate Services</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li><a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=myprofile','My Profile')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>Activate Services</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li><a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=myprofile','My Profile')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>Change Tariff Plan</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li><a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=myprofile','My Profile')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>My Subscriptions</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li><a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=myprofile','My Profile')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>Top Up</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li><a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=myprofile','My Profile')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>My Free Benefits</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li class='postpaid'><a a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=myprofile','My Profile')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>Bill Info</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li class='postpaid'><a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=myprofile','My Profile')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>Apply\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tCredit Limit</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li class='postpaid'><a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=myprofile','My Profile')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>Consupation\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\tNotification</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t\t<li class='postpaid'><a href='####'\r\n");
      out.write("\t\t\t\t\t\t\t\tonclick=\"toFrame('mCareController.do?method=myprofile','My Profile')\"\r\n");
      out.write("\t\t\t\t\t\t\t\ttarget=\"_self\"><span>Contact</span></a></li>\r\n");
      out.write("\t\t\t\t\t\t</ul></li>\r\n");
      out.write("\t\t\t\t\t<li class='last'><a href='#'><span>Contact</span></a></li>\r\n");
      out.write("\t\t\t\t</ul>\r\n");
      out.write("\t\t\t</div>\r\n");
      out.write("\t\t\t<div id=\"bizDisplay\" class='bizDisplay'>\r\n");
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