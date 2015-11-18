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
        /***query form**********************************/
        var queryPara;
		var queryform = Ext.create('Ext.form.Panel',{
			tbar:
				[
			      {
			    	  	pressed : true,
	                	text:'Search',
	                	xtype:'button',
    	                handler: function (){
    	                	var resCode=queryform.getForm().findField("resCode").getValue(); 
    	                    var resName=queryform.getForm().findField("resName").getValue(); 
    	                    var resType=queryform.getForm().findField("resType").getValue();
    	                    queryPara = 
    	                    	{
    	                    		resCode:resCode,
    	                         	resName:resName,
    	                         	resType:resType
   	                        	};
    	                    //add paramters to store, not be removed
    	                    gridstore.load({params:queryPara});
    	                }
	                },'-',{
	                	pressed : true,
	                	text:'Reset',
	                	xtype:'button',
    	                handler: function (){
    	                	queryform.getForm().reset(); 
    	                }
	                }
			     ],
			fieldDefaults:{
	            labelAlign: 'left',
	            buttonAlign : 'center',
	            labelWidth: 90
	        },
	        width:'100%',
     	   	bodyStyle : 'padding:0px;margin:0px;padding-top:5px;',//padding:1px;padding-top:5px;
     	   	border:0,
     	   	frame : true,
     	   	el:'queryform',
     	    items:[
     	           {
     	        	    id:'queryFieldset',
     	        	    width: '100%',
  	            		xtype:'fieldset',
  	                	title: 'Search',
  	                	startCollapsed: true,
  	                	collapsible: true,
  	                	defaultType: 'textfield',
  	                	items:
  	                		[
								{
								 	xtype: 'fieldcontainer',
								 	layout: 'hbox',
								    items: [
								        {
								            xtype     : 'textfield',
								            name      : 'resCode',
								            fieldLabel: 'ResCode Code'
								        },
								        {
								            xtype     : 'textfield',
								            name      : 'resName',
								            fieldLabel: 'Resource Name'
								        },
								        {
				    	                    name: 'resType',
				    	                    fieldLabel: 'Resource Type',
					    	                xtype: 'combo',
					    	                displayField:   'dicName',
				                            valueField:     'dicCode',
				                            store:          Ext.create('Ext.data.Store', {
				                                fields : ['dicCode', 'dicName'],
				                                data:UnieapDicdata.ResType
				                             }),
				    	                    anchor:'95%'
				    	                }
							       ]
								}
  	                		]
     	           }
     	           ]
		});
		//default is collapsed
		Ext.getCmp('queryFieldset').collapse(true);
        queryform.render();
    	/***data grid********************************************/
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:['resId','resCode','resName','resType','resTypeDesc'],
                idProperty: 'resId'
        });
        var gridstore = Ext.create('Ext.data.Store', {
            //pageSize: 10,
            model: 'datamodel',
            remoteSort: true,
            proxy: {
                type: 'ajax',
                url: 'MdMController.do?method=resdataGrid&resDefId='+resDefId,
                reader: {
                	root: 'rows',
                    totalProperty: 'totalCount'
                },
                simpleSortMode: true
            },
            sorters: [{
            	 property: 'resCode',
                 direction: 'DESC'
            }]
        });
        /***add query paramter to store*********************/
        gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
        var datagrid = Ext.create('Ext.grid.Panel', {
       		el : 'datagrid',
       		layout: 'fit',
   	   		store : gridstore,
   	   		columnLines: true,
           	selModel:
           	{
	            pruneRemoved: false
	        },
   	   	   	region:'center', 
   	   	   	bodyStyle:'width:100%;height:100%',
   	   	   	autoScroll:true,
   	   	   	autoExpandColumn:'action',
	   	   	columns:[
			{
			    xtype: 'rownumberer',
			    text: "No.",
			    width: 30,
			    sortable: false
			},
	   	   	{
	            text: "Resource Id",
	            dataIndex: 'resId',
	            sortable: true
	        },{
	            text: "ResCode Code",
	            dataIndex: 'resCode',
	            sortable: true
	        },{
	            text: "Resource Name",
	            dataIndex: 'resName',
	            sortable: true
	        },{
	            text: "Resource Type",
	            dataIndex: 'resTypeDesc',
	            sortable: true
	        }],
	        tbar:[queryform],
    	   	bbar:new Ext.PagingToolbar({ 
    	     	//pageSize : 10,
    	     	store : gridstore,
    	     	displayInfo: true,
                displayMsg: 'Displaying topics {0} - {1} of {2}',
                emptyMsg: "No topics to display"
    	   })
        	
        });
        datagrid.render();
        gridstore.loadPage(1);
        /***function******************************************/
    });
    </script>
</head>
<body>
    <div id="queryform" style='width:100%;height:35;font-size:0;'></div>
	<div id="datagrid" style='width:100%;height:100%;'></div>
</body>
</html>
