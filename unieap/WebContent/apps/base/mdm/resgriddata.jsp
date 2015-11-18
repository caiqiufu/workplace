<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<script type="text/javascript">
	var resDefId = "<%=request.getParameter("resDefId")%>";
</script>
<title>User</title>
    <script type="text/javascript">
    Ext.onReady(function(){
        Ext.tip.QuickTipManager.init();
        var queryPara;
		var queryform = Ext.create('Ext.form.Panel',{
			fieldDefaults:
			{ labelAlign: 'left', labelWidth: 90 },
	        layout: 'fit',
	        width : '100%',
     	   	frame : true,
     	   	el:'queryform',
     	    items:[
     	           { id:'queryFieldset', width: '100%',xtype:'fieldset',title: 'Search',
  	                 startCollapsed: true,collapsible: true, defaultType: 'textfield',
  	                	items:
  	                		[
								{ xtype: 'fieldcontainer',layout: 'hbox',
								    items: 
								    [
								        { xtype:'textfield',name: 'resCode',fieldLabel: 'Resource Code'},
								        { xtype:'textfield',name:'resName',fieldLabel: 'Resource Name'},
								        { xtype:'button',text:'Search',iconAlign: 'right',
						    	                handler: function (){
						    	                	var resCode=queryform.getForm().findField("resCode").getValue(); 
						    	                    var resName=queryform.getForm().findField("resName").getValue(); 
						    	                    queryPara = 
						    	                    	{
						    	                         	resCode:resCode,
						    	                         	resName:resName
						   	                        	};
						    	                    gridstore.load({params:queryPara});
						    	                }
							                },
							                { xtype:'button',text:'Reset',iconAlign: 'right',
						    	                handler: function ()
						    	                {
						    	                	queryform.getForm().reset(); 
						    	                }
							                }
								    ]
								}
					  	     ]
					     }
     	           ]
		});
		Ext.getCmp('queryFieldset').collapse(true);
        queryform.render();
    	/***data grid********************************************/
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'resId','resCode','resName','resType','resTypeDesc','createBy','createDatetime','modifyBy','modifyDatetime','activeFlag','activeFlagDesc','remark'
            ],
            idProperty: 'resId'
        });
        var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            remoteSort: true,
            proxy: 
            { type: 'ajax', url:'MdMController.do?method=resdataGrid&resDefId='+resDefId,
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: 
            [
            	{ property: 'resCode', direction: 'DESC'}
            ]
        });
        gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
        var selModel = Ext.create('Ext.selection.CheckboxModel');
        var datagrid = Ext.create('Ext.grid.Panel', 
        {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
	   	 	selModel:selModel,
   	   		store : gridstore,
	   	   	columns:
	   	   	[
	   	   		{ text: "Resource Code", dataIndex: 'resCode',sortable: true},
	   	   		{ text: "Resource Name", dataIndex: 'resName', sortable: true},
	   	   		{ text: "Active Flag",dataIndex: 'activeFlagDesc',sortable: true},
	   	   		{ text: "Create Datetime",width: 130, dataIndex: 'createDatetime', sortable: true},
	   	   		{ text: "Create By", dataIndex: 'createBy', sortable: false},
	   	   		{ text: "Remark", dataIndex: 'remark',sortable: false}
	   	   	],
	        tbar:[queryform],
    	   	bbar:new Ext.PagingToolbar(
    	   	{ store : gridstore,displayInfo: true, displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})
        	
        });
        datagrid.render();
        gridstore.loadPage(1);
    });
    </script>
</head>
<body>
    <div id="queryform" style='width:100%;height:35;font-size:0;'></div>
	<div id="datagrid" style='width:100%;height:100%;'></div>
</body>
</html>
