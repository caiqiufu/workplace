/*
 * Generated by the Jasper component of Apache Tomcat
 * Version: Apache Tomcat/8.0.23
 * Generated at: 2016-04-06 20:21:13 UTC
 * Note: The last modified time of this file was set to
 *       the last modified time of the source file after
 *       generation to assist with modification tracking.
 */
package org.apache.jsp.apps.report;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.jsp.*;
import java.util.*;
import com.unieap.UnieapConstants;
import com.unieap.pojo.User;
import com.unieap.base.SYSConfig;

public final class esbreport_jsp extends org.apache.jasper.runtime.HttpJspBase
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
      out.write("<title>complain management</title>\r\n");
      out.write("    <script type=\"text/javascript\">\r\n");
      out.write("    \r\n");
      out.write("    Ext.onReady(function(){\r\n");
      out.write("    \t\r\n");
      out.write("    \tExt.tip.QuickTipManager.init();\r\n");
      out.write("    \t/***notification configure*********************/\r\n");
      out.write("    \tExt.define('datamodel', {\r\n");
      out.write("            extend: 'Ext.data.Model',\r\n");
      out.write("            fields:\r\n");
      out.write("            [\r\n");
      out.write("            \t'id','type','name','text','subject','url','hyperlink','groupName','resolution','activeFlag','activeFlagDesc','pageNum','modifyDate','seq','modifyBy','language','remark',\r\n");
      out.write("            \t'effectiveDate','expiredDate','createDate','createBy'\r\n");
      out.write("            ],\r\n");
      out.write("            idProperty: 'id'\r\n");
      out.write("        });\r\n");
      out.write("    \t\r\n");
      out.write("    \tvar gridstoreForNotification = Ext.create('Ext.data.Store', {\r\n");
      out.write("            model: 'datamodel',\r\n");
      out.write("            pageSize: ");
      out.print(SYSConfig.getConfig().get("pageSize"));
      out.write(",\r\n");
      out.write("            remoteSort: true,\r\n");
      out.write("            proxy:{ type: 'ajax', url: 'easyMobileController.do?method=messageGrid&type=N',\r\n");
      out.write("                reader: \r\n");
      out.write("                {root: 'rows', totalProperty: 'totalCount'},\r\n");
      out.write("                simpleSortMode: true\r\n");
      out.write("            },\r\n");
      out.write("            sorters: [{ property: 'id', direction: 'DESC'}]\r\n");
      out.write("        });\r\n");
      out.write("    \tvar gridForNotification = Ext.create('Ext.grid.Panel', {\r\n");
      out.write("            store: gridstoreForNotification,\r\n");
      out.write("            columns: [\r\n");
      out.write("                {text: \"");
      out.print(UnieapConstants.getMessage("mcare.resourceConfigure.display.subject"));
      out.write("\",  dataIndex: 'subject', sortable: false,width:200,\r\n");
      out.write("                \trenderer: function (value, meta, record){\r\n");
      out.write("\t\t\t\t\t\tvar max = 150;\r\n");
      out.write("\t\t\t\t\t\tmeta.tdAttr = 'data-qtip=\"' + value + '\"';\r\n");
      out.write("\t\t\t\t\t\treturn value.length < max ? value : value.substring(0, max - 3) + '...';\r\n");
      out.write("\t\t\t\t\t}\t\r\n");
      out.write("                },\r\n");
      out.write("                {text: \"");
      out.print(UnieapConstants.getMessage("mcare.resourceConfigure.display.text"));
      out.write("\", dataIndex: 'text',flex: true, sortable: true,\r\n");
      out.write("                \trenderer: function (value, meta, record){\r\n");
      out.write("\t\t\t\t\t\tvar max = 150;\r\n");
      out.write("\t\t\t\t\t\tmeta.tdAttr = 'data-qtip=\"' + value + '\"';\r\n");
      out.write("\t\t\t\t\t\treturn value.length < max ? value : value.substring(0, max - 3) + '...';\r\n");
      out.write("\t\t\t\t\t}\t\r\n");
      out.write("                },\r\n");
      out.write("                { text: \"");
      out.print(UnieapConstants.getMessage("comm.effectiveDate"));
      out.write("\", dataIndex: 'effectiveDate',sortable: false,width:100,\r\n");
      out.write("                \trenderer: function (value, meta, record){\r\n");
      out.write("\t\t\t\t\t\tvar max = 150;\r\n");
      out.write("\t\t\t\t\t\tmeta.tdAttr = 'data-qtip=\"' + value + '\"';\r\n");
      out.write("\t\t\t\t\t\treturn value.length < max ? value : value.substring(0, max - 3) + '...';\r\n");
      out.write("\t\t\t\t\t}\t\r\n");
      out.write("                },\r\n");
      out.write("                { text: \"");
      out.print(UnieapConstants.getMessage("comm.expiredDate"));
      out.write("\", dataIndex: 'expiredDate',sortable: false,width:100,\r\n");
      out.write("                \trenderer: function (value, meta, record){\r\n");
      out.write("\t\t\t\t\t\tvar max = 150;\r\n");
      out.write("\t\t\t\t\t\tmeta.tdAttr = 'data-qtip=\"' + value + '\"';\r\n");
      out.write("\t\t\t\t\t\treturn value.length < max ? value : value.substring(0, max - 3) + '...';\r\n");
      out.write("\t\t\t\t\t}\t\r\n");
      out.write("                },\r\n");
      out.write("                { text: \"");
      out.print(UnieapConstants.getMessage("comm.activeFlag"));
      out.write("\",dataIndex: 'activeFlagDesc',sortable: false,width:60}\r\n");
      out.write("            ],\r\n");
      out.write("            flex: true,region: 'center',\r\n");
      out.write("            bbar:new Ext.PagingToolbar(\r\n");
      out.write("               \t   \t{ store : gridstoreForNotification,displayInfo: true})\r\n");
      out.write("        });\r\n");
      out.write("    \tgridstoreForNotification.load();\r\n");
      out.write("    \tgridForNotification.getSelectionModel().on('selectionchange', function(sm, selectedRecord) {\r\n");
      out.write("            if (selectedRecord.length) {\r\n");
      out.write("            \tdataForm.getForm().reset();\r\n");
      out.write("            \tdataForm.getForm().setValues(selectedRecord[0].data);\r\n");
      out.write("            \tdataForm.getForm().findField('subject').setReadOnly(true);\r\n");
      out.write("            \tdataForm.getForm().findField('subject').inputEl.addCls('readonly_field');\r\n");
      out.write("            \tdataForm.getForm().findField('text').setReadOnly(true);\r\n");
      out.write("            \tdataForm.getForm().findField('text').inputEl.addCls('readonly_field');\r\n");
      out.write("            \tdataForm.getForm().findField('activeFlag').setReadOnly(true);\r\n");
      out.write("            \tdataForm.getForm().findField('activeFlag').inputEl.addCls('readonly_field');\r\n");
      out.write("            \tdataForm.getForm().findField('effectiveDate').setReadOnly(true);\r\n");
      out.write("            \tdataForm.getForm().findField('effectiveDate').inputEl.addCls('readonly_field');\r\n");
      out.write("            \tdataForm.getForm().findField('expiredDate').setReadOnly(true);\r\n");
      out.write("            \tdataForm.getForm().findField('expiredDate').inputEl.addCls('readonly_field');\r\n");
      out.write("            \tdataForm.getForm().findField('effectiveDate').setValue(Ext.util.Format.date(selectedRecord[0].data.effectiveDate,\"Y-m-d h:i:s\"));\r\n");
      out.write("            \tdataForm.getForm().findField('expiredDate').setValue(Ext.util.Format.date(selectedRecord[0].data.expiredDate,\"Y-m-d h:i:s\"));\r\n");
      out.write("            \tExt.getCmp('formAdd').enable();\r\n");
      out.write("            \tExt.getCmp('formModify').enable();\r\n");
      out.write("            \tExt.getCmp('formSubmit').disable();\r\n");
      out.write("            }\r\n");
      out.write("        });\r\n");
      out.write("    \tvar operType = '';\r\n");
      out.write("    \tvar dataForm = Ext.widget('form',\r\n");
      out.write("           {\r\n");
      out.write("    \t\tlayout:'fit',defaults:{labelAlign: 'left', labelWidth: 90, anchor: '100%'},\r\n");
      out.write("            bodyPadding:5,\r\n");
      out.write("            items:\r\n");
      out.write("            [\r\n");
      out.write("            \t{xtype:'fieldset', title:'");
      out.print(UnieapConstants.getMessage("comm.data"));
      out.write("',\r\n");
      out.write("                    items:\r\n");
      out.write("                    [\r\n");
      out.write("                    \t{ xtype:'textfield',hidden: true, name:'id'},\r\n");
      out.write("                    \t{ xtype:'textfield',hidden: true, name:'type'},\r\n");
      out.write("                    \t{ xtype:'textfield',hidden: true, name:'createDate'},\r\n");
      out.write("                    \t{ xtype:'textfield',hidden: true, name:'createBy'},\r\n");
      out.write("                    \t{ xtype:'textfield',hidden: true, name:'name'},\r\n");
      out.write("                    \t{ xtype:'textfield',hidden: true, name:'groupName'},\r\n");
      out.write("                    \t{ xtype:'textfield',hidden: true, name:'resolution'},\r\n");
      out.write("                    \t{ xtype:'textfield',hidden: true, name:'pageNum'},\r\n");
      out.write("                    \t{ xtype:'textfield',hidden: true, name:'seq'},\r\n");
      out.write("                    \t{ xtype:'textfield',hidden: true, name:'url'},\r\n");
      out.write("                    \t{ xtype:'textfield',hidden: true, name:'remark'},\r\n");
      out.write("                    \t{ xtype:'textfield',labelWidth:80, width:450, name:'subject',fieldLabel:'<font color=red>*</font>");
      out.print(UnieapConstants.getMessage("mcare.resourceConfigure.display.subject"));
      out.write("',maxLength:45,allowBlank:false},\r\n");
      out.write("                    \t{ xtype:'textareafield',labelWidth:80, width:450, name:'text',fieldLabel:'<font color=red>*</font>");
      out.print(UnieapConstants.getMessage("mcare.resourceConfigure.display.text"));
      out.write("',\r\n");
      out.write("                    \t\tpreventScrollbars:true,maxLength:1024,height:150,growMin:150,growMax:200,allowBlank:false},\r\n");
      out.write("                   \t\t{name: 'effectiveDate',labelWidth:80, width:300, fieldLabel: '");
      out.print(UnieapConstants.getMessage("comm.effectiveDate"));
      out.write("',format: 'Y-m-d h:i:s', xtype: 'datefield',allowBlank:false,\r\n");
      out.write("                    \t\t\tvalue:Ext.util.Format.date(new Date(),\"Y-m-d h:i:s\")\t\t\r\n");
      out.write("                   \t\t},\r\n");
      out.write("\t\t\t            {name: 'expiredDate',labelWidth:80, width:300, fieldLabel: '");
      out.print(UnieapConstants.getMessage("comm.expiredDate"));
      out.write("',format: 'Y-m-d h:i:s', xtype: 'datefield',allowBlank:false,\r\n");
      out.write("                   \t\t\tvalue:Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.DAY,+7),\"Y-m-d h:i:s\")\t\r\n");
      out.write("\t\t\t            },\r\n");
      out.write("                    \t{ xtype:'combo', labelWidth:80, width:300,forceSelection: true,editable:false,allowBlank:false,\r\n");
      out.write("                            name:'activeFlag',fieldLabel:'");
      out.print(UnieapConstants.getMessage("comm.activeFlag"));
      out.write("',displayField:'dicName',valueField:'dicCode',value:'Y',\r\n");
      out.write("                            store:Ext.create('Ext.data.Store', \r\n");
      out.write("                            { fields : ['dicCode', 'dicName'],data:UnieapDicdata._activeFlag})\r\n");
      out.write("\t\t\t\t\t\t}\r\n");
      out.write("                    ]\r\n");
      out.write("                }\r\n");
      out.write("            ],\r\n");
      out.write("            buttonAlign: 'center',\r\n");
      out.write("            buttons: \r\n");
      out.write("            [\r\n");
      out.write("                {id:'formAdd', text: '");
      out.print(UnieapConstants.getMessage("comm.add"));
      out.write("',disabled:false,\r\n");
      out.write("                    handler: function(){\r\n");
      out.write("                    \toperType = 'AddNotification';\r\n");
      out.write("                    \t//dataForm.getForm().reset();\r\n");
      out.write("                    \tdataForm.getForm().findField('subject').setReadOnly(false);\r\n");
      out.write("                    \tdataForm.getForm().findField('subject').inputEl.removeCls('readonly_field');\r\n");
      out.write("                    \tdataForm.getForm().findField('text').setReadOnly(false);\r\n");
      out.write("                    \tdataForm.getForm().findField('text').inputEl.removeCls('readonly_field');\r\n");
      out.write("                    \tdataForm.getForm().findField('activeFlag').setReadOnly(false);\r\n");
      out.write("                    \tdataForm.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');\r\n");
      out.write("                    \tdataForm.getForm().findField('effectiveDate').setReadOnly(false);\r\n");
      out.write("                    \tdataForm.getForm().findField('effectiveDate').inputEl.removeCls('readonly_field');\r\n");
      out.write("                    \tdataForm.getForm().findField('expiredDate').setReadOnly(false);\r\n");
      out.write("                    \tdataForm.getForm().findField('expiredDate').inputEl.removeCls('readonly_field');\r\n");
      out.write("                    \tExt.getCmp('formSubmit').enable();\r\n");
      out.write("                    \tExt.getCmp('formModify').disable();\r\n");
      out.write("                    }\r\n");
      out.write("                },{id:'formModify', text: '");
      out.print(UnieapConstants.getMessage("comm.modify"));
      out.write("',disabled:true,\r\n");
      out.write("                    handler: function(){\r\n");
      out.write("                    \toperType = 'ModifyNotification';\r\n");
      out.write("                    \tdataForm.getForm().findField('subject').setReadOnly(false);\r\n");
      out.write("                    \tdataForm.getForm().findField('subject').inputEl.removeCls('readonly_field');\r\n");
      out.write("                    \tdataForm.getForm().findField('text').setReadOnly(false);\r\n");
      out.write("                    \tdataForm.getForm().findField('text').inputEl.removeCls('readonly_field');\r\n");
      out.write("                    \tdataForm.getForm().findField('activeFlag').setReadOnly(false);\r\n");
      out.write("                    \tdataForm.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');\r\n");
      out.write("                    \tdataForm.getForm().findField('effectiveDate').setReadOnly(false);\r\n");
      out.write("                    \tdataForm.getForm().findField('effectiveDate').inputEl.removeCls('readonly_field');\r\n");
      out.write("                    \tdataForm.getForm().findField('expiredDate').setReadOnly(false);\r\n");
      out.write("                    \tdataForm.getForm().findField('expiredDate').inputEl.removeCls('readonly_field');\r\n");
      out.write("                    \tExt.getCmp('formSubmit').enable();\r\n");
      out.write("                    }\r\n");
      out.write("                }, \r\n");
      out.write("                {id:'formSubmit',text: '");
      out.print(UnieapConstants.getMessage("comm.submit"));
      out.write("',disabled:true,\r\n");
      out.write("                    handler: function(){\r\n");
      out.write("                    \tvar form = dataForm.getForm();\r\n");
      out.write("                    \t if (form.isValid()){\r\n");
      out.write("                             form.submit({\r\n");
      out.write("                                 clientValidation: true,\r\n");
      out.write("                                 method: 'POST',\r\n");
      out.write("                                 params:{'operType':operType},\r\n");
      out.write("                                 waitMsg: '");
      out.print(UnieapConstants.getMessage("comm.processing"));
      out.write("',\r\n");
      out.write("                                 url: 'easyMobileController.do?method=messageDeal',\r\n");
      out.write("                                 success: function(form, action) {\r\n");
      out.write("                                \tvar result = Ext.JSON.decode(action.response.responseText);\r\n");
      out.write("\t\t\t\t                    if(result.isSuccess == 'failed'){\r\n");
      out.write("\t\t\t\t                    \tExt.MessageBox.show({title: '");
      out.print(UnieapConstants.getMessage("comm.status"));
      out.write("',msg:result.message,\r\n");
      out.write("                                 \t\t\tbuttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});\r\n");
      out.write("\t\t\t\t                    }else{\r\n");
      out.write("\t                                    \tgridstoreForNotification.reload();\r\n");
      out.write("\t                                    \tExt.MessageBox.show({title: '");
      out.print(UnieapConstants.getMessage("comm.status"));
      out.write("',msg:'");
      out.print(UnieapConstants.getMessage("comm.success.save"));
      out.write("',\r\n");
      out.write("\t\t                               \t\t\tbuttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});\r\n");
      out.write("\t\t\t\t                    }\r\n");
      out.write("                                 },\r\n");
      out.write("                                 failure: function(form, action){\r\n");
      out.write("                                \t Ext.MessageBox.show({title: '");
      out.print(UnieapConstants.getMessage("comm.status"));
      out.write("',msg:action.response.responseText,\r\n");
      out.write("                             \t\t\tbuttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});\r\n");
      out.write("                                 }\r\n");
      out.write("                             });\r\n");
      out.write("                    \t }\r\n");
      out.write("                    }\r\n");
      out.write("                }\r\n");
      out.write("            ]\r\n");
      out.write("        });\r\n");
      out.write("\r\n");
      out.write("    \t\r\n");
      out.write("    \t\r\n");
      out.write("    \t\r\n");
      out.write("    \t\r\n");
      out.write("    \tvar tabPanel = Ext.create('Ext.tab.Panel',{\r\n");
      out.write("\t     \trenderTo:'tabPanel',activeTab:0,layout: 'fit',\r\n");
      out.write("\t     \tdefaults :{\r\n");
      out.write("\t            autoScroll: true\r\n");
      out.write("\t        },\r\n");
      out.write("\t        items:\r\n");
      out.write("\t        \t[\r\n");
      out.write("\t\t\t\t\t{\r\n");
      out.write("\t\t\t\t\t    xtype: 'panel',layout: 'border',height:530,\r\n");
      out.write("\t\t\t\t\t    title: '");
      out.print(UnieapConstants.getMessage("report.esb.error.title"));
      out.write("',\r\n");
      out.write("\t\t\t\t\t    items : [\r\n");
      out.write("\t\t\t\t\t\t\t\t\tgridForNotification\r\n");
      out.write("\t\t\t\t\t\t\t\t\t\r\n");
      out.write("\t\t\t\t\t             ],\r\n");
      out.write("\t\t\t\t\t    closable: false\r\n");
      out.write("\t\t\t\t\t}\r\n");
      out.write("\t        \t]\r\n");
      out.write("\t   });\r\n");
      out.write("    \t\r\n");
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