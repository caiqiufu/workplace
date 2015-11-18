<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<script type="text/javascript">
	var roleId = "<%=request.getParameter("roleId")%>";
</script>
<title>Resource Data</title>
    <script type="text/javascript">
    Ext.onReady(function(){
        Ext.tip.QuickTipManager.init();
        /***query form**********************************/
        var selectedResdefId = '';
        /***data grid********************************************/
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:[
                    'resDefId','resDefCode','resDefName','resType','resTypeDesc','isTree','isTreeDesc','createDatetime','modifyDatetime','activeFlag','activeFlagDesc','remark'
                ],
                idProperty: 'resDefId'
        });
        var gridstore = Ext.create('Ext.data.Store', {
            //pageSize: 10,
            model: 'datamodel',
            remoteSort: true,
            proxy: {
                type: 'ajax',
                url: 'MdMController.do?method=resdefGrid',
                reader: {
                	root: 'rows',
                    totalProperty: 'totalCount'
                },
                simpleSortMode: true
            },
            sorters: [{
            	 property: 'resDefId',
                 direction: 'DESC'
            }]
        });
        var selModel = Ext.create('Ext.selection.CheckboxModel', {
	        listeners: {
	            selectionchange: function(sm, selections){
	            	
	            	queryResdataPara=
	        		{
	        			resDefId:''
	        		};
	                gridresdatastore.load({params:queryResdataPara});
	            }
	        }
	    });
        var datagrid = Ext.create('Ext.grid.Panel', {
        	selModel: selModel,
       		el : 'datagrid',
       		//layout: 'fit',
       		width: '100%',
            height: 200,
   	   		store : gridstore,
   	   		columnLines: true,
   	   	   	region:'center',
   	   	   	autoScroll:true,
	   	   	columns:[
			{
			    xtype: 'rownumberer',
			    text: "No.",
			    width: 30,
			    sortable: false
			},
			{
	            text: "ResDef Code",
	            dataIndex: 'resDefCode',
	            sortable: true
	        },
	        {
	            text: "ResDef Name",
	            dataIndex: 'resDefName',
	            sortable: true
	        },{
	            text: "Active Flag",
	            dataIndex: 'activeFlagDesc',
	            sortable: false
	        },{
	            text: "Resource Type",
	            dataIndex: 'resTypeDesc',
	            sortable: true
	        },{
	            text: "Is Tree",
	            dataIndex: 'isTreeDesc',
	            sortable: true
	        }],
    	   	bbar:new Ext.PagingToolbar({ 
    	     	pageSize : 10,
    	     	store : gridstore,
    	     	displayInfo: true,
                displayMsg: 'Displaying topics {0} - {1} of {2}',
                emptyMsg: "No topics to display"
    	   })
        	
        });
        datagrid.render();
        gridstore.loadPage(1);
        /***grid data panel******************************************************/
        var queryResdataPara;
        var gridresdatastore = Ext.create('Ext.data.Store', {
            //pageSize: 10,
            fields:['resId','resCode','resName','resType'],
            remoteSort: true,
            proxy: {
                type: 'ajax',
                url: 'MdMController.do?method=resdataGrid',
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
        var gridresdatasgrid = Ext.create('Ext.grid.Panel', {
		        store: gridresdatastore,
		        columns: 
		        [
					{xtype: 'rownumberer',text: "No.",width: 20,sortable: false},
					{text: "Resource Id", dataIndex: 'resId',sortable: true },
					{text: "resCode Code", dataIndex: 'resCode', sortable: true},
					{text: "Resource Name", dataIndex: 'resName', sortable: true }
				],
		        columnLines: true,
		        enableLocking: true,
		        collapsible: false,
		        animCollapse: false,
		        title: 'All Resource Data',
		        //iconCls: 'icon-grid',
		        //margin: '0 0 20 0',
		        renderTo:'gridresdatasgrid',
		        bbar:new Ext.PagingToolbar({ 
	    	     	pageSize : 10,
	    	     	store : gridresdatastore,
	    	     	displayInfo: true,
	                displayMsg: 'Displaying topics {0} - {1} of {2}',
	                emptyMsg: "No topics to display"
	    	   })
		    });
        /**************************************************/
        gridresdatastore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryResdataPara);
        });
        
        var assignedgridresdatastore = Ext.create('Ext.data.Store', {
            //pageSize: 10,
            fields:['resId','resCode','resName','resType'],
            remoteSort: true,
            proxy: {
                type: 'ajax',
                url: 'MdMController.do?method=resdataGrid',
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
        var assignedgridresdatagrid = Ext.create('Ext.grid.Panel', {
       		el : 'assignedgridresdatagrid',
       		title:'Assigned Resource Data',
       		//layout: 'fit',
       		//width: '100%',
   	   		store : assignedgridresdatastore,
   	   		columnLines: true,
	   	   	selModel: {
	            pruneRemoved: false
	        },
   	   	   	//region:'center',
   	   	   	autoScroll:true,
	   	   	columns:[
			{
			    xtype: 'rownumberer',
			    text: "No.",
			    width: 20,
			    sortable: false
			},
			{
	            text: "Resource Id",
	            dataIndex: 'resId',
	            sortable: true
	        },{
	            text: "resCode Code",
	            dataIndex: 'resCode',
	            sortable: true
	        },{
	            text: "Resource Name",
	            dataIndex: 'resName',
	            sortable: true
	        }],
    	   	bbar:new Ext.PagingToolbar({ 
    	     	pageSize : 10,
    	     	store : assignedgridresdatastore,
    	     	displayInfo: true,
                displayMsg: 'Displaying topics {0} - {1} of {2}',
                emptyMsg: "No topics to display"
    	   })
        	
        });
        assignedgridresdatastore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryResdataPara);
        });
        
        /***grid res data panel**************/
        
        var gridresdatapanel =  Ext.create('Ext.panel.Panel',{
	        renderTo: 'gridresdatapanel',
	        //layout: 'fit',
	        layout: {
	            type: 'hbox',
	            align: 'stretch'
	        },
	        defaultType: 'panel',
	        items: 
	        [
	        	{
				    width: '50%',
				    //html:'griddatalist'
				    items : [gridresdatasgrid]
		        },{
				    width: '50%',
				    //html:'roledatalist'
				    items : [assignedgridresdatagrid]
		       }
		     ]
	    });
        
        var resdataTabPanel = Ext.create('Ext.tab.Panel',{
        	renderTo:'resdataTabPanel',
        	activeTab: 0,
       		layout: 'fit',
            bodyStyle: 'padding:5px',
            items:[
						{
						    xtype: 'panel',
				            title: 'Grid resource data',
				            //html:'gridresdatapanel',
				            items : [gridresdatapanel],
						    closable: false
						}, {
						    xtype: 'panel',
						    title: 'Tree resource data',
						    html:'treerespanel',
						    //items : [treerespanel],
						    closable: false
						}
                   ]
        });
        
       
       /***function******************************************/
       function showResourceData(resDefId,isTree)
        {
        	selectedResdefId = resDefId;
        	//alert('resDefId='+selectedResdefId);
        	if(isTree==1)
        	{
        		queryResdataPara=
        		{
        			resDefId:selectedResdefId
        		};
        		gridresdatastore.load({params:queryResdataPara});
        		var tab = resdataTabPanel.items.items[0];
        		tab.show();
        	}else{
        		queryResdataPara=
        		{
        			resDefId:selectedResdefId,
        			id:'-1',
        			text:'Root',
        			expanded:false,
        			leaf:false
        		};
        		var tab = resdataTabPanel.items.items[1];
        		tab.show();
        		gridresdatastore.load({params:queryResdataPara});  
        		
        	}
        } 
    });
    </script>
</head>
<body>
	<div id="datagrid" style='width:100%;height:100%;'></div>
	<div id="gridresdatasgrid" style='width:100%;height:100%;'></div>
	<div id="assignedgridresdatagrid" style='width:100%;height:100%;'></div>
	<div id="gridresdatapanel" style='width:100%;height:100%;'></div>
	<div id="resdataTabPanel" style='width:100%;height:100%;'></div>
	<div id="treerespanel" style='width:100%;height:100%;'></div>
	<div id="treeresdata" style='width:100%;height:100%;'></div>
	<div id="rolelist" style='width:100%;height:100%;'></div>
</body>
</html>
