/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.23
 * Generated at: 2016-04-07 19:46:01 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.apps.base.mdm.systemconfig;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.unieap.UnieapConstants;
import com.unieap.pojo.User;
import com.unieap.base.SYSConfig;

public final class systemconfig_jsp extends org.apache.jasper.runtime.HttpJspBase
    implements org.apache.jasper.runtime.JspSourceDependent,
                 org.apache.jasper.runtime.JspSourceImports {

  private static final javax.servlet.jsp.JspFactory _jspxFactory =
          javax.servlet.jsp.JspFactory.getDefaultFactory();

  private static java.util.Map<java.lang.String,java.lang.Long> _jspx_dependants;

  static {
    _jspx_dependants = new java.util.HashMap<java.lang.String,java.lang.Long>(1);
    _jspx_dependants.put("/unieap/unieap.jsp", Long.valueOf(1450633063746L));
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
    _jspx_imports_classes.add("com.unieap.UnieapConstants");
    _jspx_imports_classes.add("com.unieap.base.SYSConfig");
    _jspx_imports_classes.add("com.unieap.pojo.User");
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
      out.write("\r\n");
      out.write("\r\n");

	
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
	        + path + "/";
    User user = UnieapConstants.getUser();
    Object error = request.getSession().getAttribute("SPRING_SECURITY_CONTEXT");
    String errorDesc = "";	
    if(error!=null){
		errorDesc = error.toString();
		if(errorDesc.contains("Bad credentials")||errorDesc.contains("UserDetailsService")){
			errorDesc = UnieapConstants.getMessage("user.login.error.usercode");
		}
	}
    

      out.write("\r\n");
      out.write("<!-- 开源框架文件\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.print(path);
      out.write("/unieap/js/ext-6.0.1/build/ext-all.js\"></script>\r\n");
      out.write("-->\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.print(path);
      out.write("/unieap/js/ext-4.2.1/ext-all.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\" src=\"");
      out.print(path);
      out.write("/unieap/js/ext-4.2.1/locale/ext-lang-");
      out.print(SYSConfig.defaultLanguage);
      out.write(".js\"></script> \r\n");
      out.write("<!-- extjs框架文件 \r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(path);
      out.write("/unieap/js/ext-4.2.1/resources/css/ext-all-neptune.css\" />\r\n");
      out.write("-->\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(path);
      out.write("/unieap/js/ext-4.2.1/resources/css/ext-all-gray.css\" />\r\n");
      out.write("<!-- unieap框架文件 -->\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.print(path);
      out.write("/unieap/js/common/unieap.util.js\"></script>\r\n");
      out.write("<link rel=\"stylesheet\" type=\"text/css\" href=\"");
      out.print(path);
      out.write("/unieap/js/common/css/common.css\" />\r\n");
      out.write("<link rel=\"shortcut icon\" type=\"image/x-icon\" href=\"");
      out.print(path);
      out.write("/unieap/js/common/images/title.png\" media=\"screen\" /> \r\n");
      out.write("<!-- 快码缓存文件,由系统生成-->\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.print(path);
      out.write("/sharefolder/common/");
      out.print(user.getUserCode());
      out.write("_button_constants.js\"></script>\r\n");
      out.write("<script type=\"text/javascript\"\r\n");
      out.write("\tsrc=\"");
      out.print(path);
      out.write("/sharefolder/common/");
      out.print(user.getUserCode());
      out.write("_dicdata_constants.js\"></script>\r\n");
      out.write("<!-- 根目录路径 -->\r\n");
      out.write("<script type=\"text/javascript\">\r\n");
      out.write("\tvar basePathUrl = \"");
      out.print(basePath);
      out.write("\";\r\n");
      out.write("\tvar errorDesc = \"");
      out.print(errorDesc);
      out.write("\";\r\n");
      out.write("\tvar responseText = \"\";\r\n");
      out.write("\tExt.onReady(function(){\r\n");
      out.write("\t\t\r\n");
      out.write("\t\tif(errorDesc.indexOf(\"This session has been expired\")>-1){\r\n");
      out.write("\t\t\tExt.MessageBox.show({title: '");
      out.print(UnieapConstants.getMessage("comm.status"));
      out.write("',msg:'This session has been expired',width:120, height:100,\r\n");
      out.write("\t\t\t\tfn: showResult,buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.WARNING});\r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tExt.Ajax.on('requestcomplete',function(conn, response, options, eOpts){  \r\n");
      out.write("\t\t\tresponseText = response.responseText;\r\n");
      out.write("\t\t\tif(responseText.indexOf('<script type=\"text/javascript\">')>-1){\r\n");
      out.write("\t\t\t\tExt.MessageBox.show({title: '");
      out.print(UnieapConstants.getMessage("comm.status"));
      out.write("',msg:'This session has been expired',width:120, height:100,\r\n");
      out.write("\t\t\t\t\tfn: showTimeOutResult,buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.WARNING});\r\n");
      out.write("\t\t\t\t\r\n");
      out.write("\t\t\t}else if(responseText.indexOf(\"This session has been expired\")>-1){\r\n");
      out.write("\t\t\t\tExt.MessageBox.show({title: '");
      out.print(UnieapConstants.getMessage("comm.status"));
      out.write("',msg:'This session has been expired',width:120, height:100,\r\n");
      out.write("\t\t\t\t\tfn: showResult,buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.WARNING});\r\n");
      out.write("\t\t\t}\r\n");
      out.write("        });\r\n");
      out.write("\t\tfunction showTimeOutResult(){\r\n");
      out.write("\t\t\tdocument.write(responseText); \r\n");
      out.write("\t\t}\r\n");
      out.write("\t\tfunction showResult(btn){\r\n");
      out.write("\t\t\t window.top.location.href = \"");
      out.print(path);
      out.write("/timeout.jsp\";  \r\n");
      out.write("\t\t}\r\n");
      out.write("\t});\r\n");
      out.write("</script>\r\n");
      out.write(" <style type=\"text/css\">\r\n");
      out.write("    .add {\r\n");
      out.write("\t    background-image: url(unieap/js/common/images/add.png) !important;\r\n");
      out.write("\t}\r\n");
      out.write("\t.delete {\r\n");
      out.write("\t    background-image: url(unieap/js/common/images/delete.png ) !important;\r\n");
      out.write("\t}\r\n");
      out.write("\t.edit {\r\n");
      out.write("\t    background-image: url(unieap/js/common/images/edit.png ) !important;\r\n");
      out.write("\t}\r\n");
      out.write("\t.save {\r\n");
      out.write("\t    background-image: url(unieap/js/common/images/save.gif ) !important;\r\n");
      out.write("\t}\r\n");
      out.write("\t.find {\r\n");
      out.write("\t    background-image: url(unieap/js/common/images/find.gif ) !important;\r\n");
      out.write("\t}\r\n");
      out.write("\t.search-trigger {\r\n");
      out.write("\t    background-image: url(unieap/js/common/images/search-trigger.png ) !important;\r\n");
      out.write("\t}\r\n");
      out.write("\t.clear-trigger {\r\n");
      out.write("\t    background-image: url(unieap/js/common/images/clear-trigger.gif ) !important;\r\n");
      out.write("\t}\r\n");
      out.write("\t.view {\r\n");
      out.write("\t    background-image: url(unieap/js/common/images/view.gif ) !important;\r\n");
      out.write("\t}\r\n");
      out.write("\t.money-up {\r\n");
      out.write("\t    background-image: url(unieap/js/common/images/money_add.png ) !important;\r\n");
      out.write("\t}\r\n");
      out.write("\t.money-down {\r\n");
      out.write("\t    background-image: url(unieap/js/common/images/money_delete.png ) !important;\r\n");
      out.write("\t}\r\n");
      out.write("\t.folder_go {\r\n");
      out.write("        background-image: url(unieap/js/common/images/folder_go.png) !important;\r\n");
      out.write("    }\r\n");
      out.write("    .menu-show {\r\n");
      out.write("        background-image: url(unieap/js/common/images/menu-show.gif) !important;\r\n");
      out.write("    }\r\n");
      out.write("    .buttons {\r\n");
      out.write("        background-image: url(unieap/js/common/images/buttons.gif) !important;\r\n");
      out.write("    }\r\n");
      out.write("    .leaf {\r\n");
      out.write("        background-image: url(unieap/js/common/images/leaf.png) !important;\r\n");
      out.write("    }\r\n");
      out.write("    .readonly_field{\r\n");
      out.write("\t\tbackground: white repeat-x 0 0;\r\n");
      out.write("\t\tbackground-color:#eaeaea;\r\n");
      out.write("\t}\r\n");
      out.write("\t.refresh {\r\n");
      out.write("\t    background-image: url(unieap/js/common/images/refresh.png) !important;\r\n");
      out.write("\t}\r\n");
      out.write("\t\r\n");
      out.write("\t\r\n");
      out.write("</style>\r\n");
      out.write("\r\n");
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
      out.write("<html>\r\n");
      out.write("<head>\r\n");
      out.write("<title>system configure management</title>\r\n");
      out.write("    <script type=\"text/javascript\">\r\n");
      out.write("    \r\n");
      out.write("    Ext.onReady(function(){\r\n");
      out.write("    \t\r\n");
      out.write("    \tExt.tip.QuickTipManager.init();\r\n");
      out.write("    \t\r\n");
      out.write("    \t/*** configure begin*********************************************************/\r\n");
      out.write("    \t\r\n");
      out.write("    \tExt.define('datamodel', {\r\n");
      out.write("            extend: 'Ext.data.Model',\r\n");
      out.write("            fields:\r\n");
      out.write("            [\r\n");
      out.write("            \t'id','type','typeDesc','name','value','description'\r\n");
      out.write("            ],\r\n");
      out.write("            idProperty: 'id'\r\n");
      out.write("        });\r\n");
      out.write("    \t\r\n");
      out.write("    \tvar gridstore = Ext.create('Ext.data.Store', {\r\n");
      out.write("            model: 'datamodel',\r\n");
      out.write("            pageSize: ");
      out.print(SYSConfig.getConfig().get("pageSize"));
      out.write(",\r\n");
      out.write("            remoteSort: true,\r\n");
      out.write("            proxy:{ type: 'ajax', url: 'mdmController.do?method=configGrid',\r\n");
      out.write("                reader: \r\n");
      out.write("                {root: 'rows', totalProperty: 'totalCount'},\r\n");
      out.write("                simpleSortMode: true\r\n");
      out.write("            },\r\n");
      out.write("            sorters: [{ property: 'id', direction: 'DESC'}]\r\n");
      out.write("        });\r\n");
      out.write("    \tvar rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {\r\n");
      out.write("    \t\tsaveBtnText: '");
      out.print(UnieapConstants.getMessage("comm.save"));
      out.write("', \r\n");
      out.write("            cancelBtnText: '");
      out.print(UnieapConstants.getMessage("comm.cancel"));
      out.write("', \r\n");
      out.write("            clicksToMoveEditor: 1,\r\n");
      out.write("            autoCancel: false,\r\n");
      out.write("            listeners:{\r\n");
      out.write("            \tedit:function(e){\r\n");
      out.write("            \t\tExt.MessageBox.confirm('");
      out.print(UnieapConstants.getMessage("comm.title.confirm"));
      out.write("', \r\n");
      out.write("            \t\t\t\t'");
      out.print(UnieapConstants.getMessage("comm.confirm.update"));
      out.write("', updateDatas);\r\n");
      out.write("            \t}\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("    \tvar datagrid = Ext.create('Ext.grid.Panel', {\r\n");
      out.write("            store: gridstore,\r\n");
      out.write("            selModel: {\r\n");
      out.write("                selType: 'cellmodel'\r\n");
      out.write("            },\r\n");
      out.write("            viewConfig: {\r\n");
      out.write("                stripeRows: true\r\n");
      out.write("            },\r\n");
      out.write("            plugins: [rowEditing],\r\n");
      out.write("            columns: [\r\n");
      out.write("\t\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\t    text     : '");
      out.print(UnieapConstants.getMessage("comm.id"));
      out.write("',\r\n");
      out.write("\t\t\t\t\t\t    width    : 80,\r\n");
      out.write("\t\t\t\t\t\t    sortable : false,\r\n");
      out.write("\t\t\t\t\t\t    dataIndex: 'id'\r\n");
      out.write("\t\t\t\t\t\t},\r\n");
      out.write("\t\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t\t    text     : '");
      out.print(UnieapConstants.getMessage("comm.type"));
      out.write("',\r\n");
      out.write("\t\t\t\t\t\t    width    : 80,\r\n");
      out.write("\t\t\t\t\t\t    sortable : false,\r\n");
      out.write("\t\t\t\t\t\t    dataIndex: 'typeDesc'\r\n");
      out.write("\t\t\t\t\t\t},{\r\n");
      out.write("\t\t\t\t\t\t    text     : '");
      out.print(UnieapConstants.getMessage("comm.name"));
      out.write("',\r\n");
      out.write("\t\t\t\t\t\t    width    : 150,\r\n");
      out.write("\t\t\t\t\t\t    sortable : false,\r\n");
      out.write("\t\t\t\t\t\t    dataIndex: 'name'\r\n");
      out.write("\t\t\t\t\t\t},{\r\n");
      out.write("\t\t\t\t\t\t    text     : '");
      out.print(UnieapConstants.getMessage("comm.value"));
      out.write("',\r\n");
      out.write("\t\t\t\t\t\t    width    : 200,\r\n");
      out.write("\t\t\t\t\t\t    sortable : false,\r\n");
      out.write("\t\t\t\t\t\t    dataIndex: 'value',\r\n");
      out.write("\t\t\t\t\t\t    editor: {\r\n");
      out.write("\t\t\t\t\t\t        xtype: 'textfield'\r\n");
      out.write("\t\t\t\t\t\t    }\r\n");
      out.write("\t\t\t\t\t\t},{\r\n");
      out.write("\t\t\t\t\t\t    text     : '");
      out.print(UnieapConstants.getMessage("comm.description"));
      out.write("',\r\n");
      out.write("\t\t\t\t\t\t    width    : 300,\r\n");
      out.write("\t\t\t\t\t\t    sortable : false,\r\n");
      out.write("\t\t\t\t\t\t    dataIndex: 'description'\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("            ],\r\n");
      out.write("            flex: true,region: 'center'\r\n");
      out.write("        });\r\n");
      out.write("    \tgridstore.load();\r\n");
      out.write("    \t\r\n");
      out.write("    \tfunction updateDatas(btn){\r\n");
      out.write("        \tif(btn=='yes'){\r\n");
      out.write("        \t\t//var record = datagrid.getSelectionModel().getSelection();\r\n");
      out.write("        \t\tvar record = gridstore.getModifiedRecords();\r\n");
      out.write("        \t\tvar id = record[0].data.id;\r\n");
      out.write("        \t\tvar newValue = record[0].data.value;\r\n");
      out.write("        \t\tExt.Ajax.request({\r\n");
      out.write("\t                url: 'mdmController.do?method=configDeal',\r\n");
      out.write("\t                params:{'operType':'Modify','id':id,'value':newValue},\r\n");
      out.write("\t                success: function(response, opts){\r\n");
      out.write("\t                \tvar result = Ext.JSON.decode(response.responseText);\r\n");
      out.write("\t                \t\r\n");
      out.write("\t                \tif(result.isSuccess == 'failed'){\r\n");
      out.write("\t                    \tExt.MessageBox.show({title: '");
      out.print(UnieapConstants.getMessage("comm.status"));
      out.write("',msg:result.message,\r\n");
      out.write("                     \t\t\tbuttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});\r\n");
      out.write("\t                    }else{\r\n");
      out.write("                         \tExt.MessageBox.show({title: '");
      out.print(UnieapConstants.getMessage("comm.status"));
      out.write("',msg:'");
      out.print(UnieapConstants.getMessage("comm.success.save"));
      out.write("',fn: showResult,\r\n");
      out.write("                        \t\t\tbuttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});\r\n");
      out.write("\t                    }\r\n");
      out.write("\t                },\r\n");
      out.write("\t                failure: function(form, action){\r\n");
      out.write("                   \t Ext.MessageBox.show({title: '");
      out.print(UnieapConstants.getMessage("comm.status"));
      out.write("',msg:action.response.responseText,\r\n");
      out.write("                \t\t\tbuttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});\r\n");
      out.write("                    }\r\n");
      out.write("\t             });\r\n");
      out.write("        \t}\r\n");
      out.write("        }\r\n");
      out.write("    \t\r\n");
      out.write("    \tfunction showResult(btn){\r\n");
      out.write("        \tgridstore.reload();\r\n");
      out.write("        }\r\n");
      out.write("    \t\r\n");
      out.write("    \t/***tab panel***************************************************************/\r\n");
      out.write("    \tvar tabPanel = Ext.create('Ext.tab.Panel',{\r\n");
      out.write("\t     \trenderTo:'tabPanel',activeTab:0,layout: 'fit',\r\n");
      out.write("\t     \tdefaults :{\r\n");
      out.write("\t            autoScroll: true\r\n");
      out.write("\t        },\r\n");
      out.write("\t        items:\r\n");
      out.write("\t        \t[\r\n");
      out.write("\t\t\t\t\t {\r\n");
      out.write("\t\t\t\t\t\txtype: 'panel',layout: 'border',height:550,\r\n");
      out.write("\t\t\t\t\t    title: '");
      out.print(UnieapConstants.getMessage("mdm.systemconfig.display.title"));
      out.write("',\r\n");
      out.write("\t\t\t\t\t    items : [\r\n");
      out.write("\t\t\t\t\t\t\t\t\tdatagrid\r\n");
      out.write("\t\t\t\t\t             ],\r\n");
      out.write("\t\t\t\t\t    closable: false\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t        \t]\r\n");
      out.write("\t   });\r\n");
      out.write("\t});\r\n");
      out.write("    </script>\r\n");
      out.write("</head>\r\n");
      out.write("<body>\r\n");
      out.write("    <div id=\"tabPanel\"></div>\r\n");
      out.write("</body>\r\n");
      out.write("</html>\r\n");
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
