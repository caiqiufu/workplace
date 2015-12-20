<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>system configure management</title>
    <script type="text/javascript">
    
    Ext.onReady(function(){
    	
    	Ext.tip.QuickTipManager.init();
    	
    	/*** configure begin*********************************************************/
    	
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'id','type','name','value','description'
            ],
            idProperty: 'id'
        });
    	
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mdmController.do?method=configGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
            clicksToMoveEditor: 1,
            autoCancel: false
        });
    	var datagrid = Ext.create('Ext.grid.Panel', {
            store: gridstore,
            selModel: {
                selType: 'cellmodel'
            },
            viewConfig: {
                stripeRows: true
            },
            plugins: [rowEditing],
            columns: [
						{
						    text     : '<%=UnieapConstants.getMessage("comm.id")%>',
						    width    : 100,
						    sortable : false,
						    dataIndex: 'id'
						},
						{
						    text     : '<%=UnieapConstants.getMessage("comm.type")%>',
						    width    : 100,
						    sortable : false,
						    dataIndex: 'type'
						},{
						    text     : '<%=UnieapConstants.getMessage("comm.name")%>',
						    width    : 100,
						    sortable : false,
						    dataIndex: 'name'
						},{
						    text     : '<%=UnieapConstants.getMessage("comm.value")%>',
						    width    : 100,
						    sortable : false,
						    dataIndex: 'value',
						    editor: {
						        xtype: 'textfield'
						    }
						},{
						    text     : '<%=UnieapConstants.getMessage("comm.description")%>',
						    width    : 200,
						    sortable : false,
						    dataIndex: 'description'
						}
            ],
            flex: true,region: 'center'
        });
    	gridstore.load();
    	
    	/***tab panel***************************************************************/
    	var tabPanel = Ext.create('Ext.tab.Panel',{
	     	renderTo:'tabPanel',activeTab:0,layout: 'fit',
	     	defaults :{
	            autoScroll: true
	        },
	        items:
	        	[
					 {
						xtype: 'panel',layout: 'border',height:550,
					    title: '<%=UnieapConstants.getMessage("mdm.systemconfig.display.title")%>',
					    items : [
									datagrid
					             ],
					    closable: false
					}
	        	]
	   });
	});
    </script>
</head>
<body>
    <div id="tabPanel"></div>
</body>
</html>
