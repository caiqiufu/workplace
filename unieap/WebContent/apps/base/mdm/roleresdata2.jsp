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
        var queryResdataPara;
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
	            	var rec = datagrid.getSelectionModel().getSelection();
               		if(rec.length>0){
               			var resDefId = rec[0].get('resDefId');
		            	queryResdataPara=
		        		{
		        			roleId:roleId,
		        			resDefId:resDefId
		        		};
		                gridresdatastore.load({params:queryResdataPara});
               		}
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
	   	   	columns:
	   	   	[
				{ xtype: 'rownumberer', text: "No.", width: 30, sortable: false},
				{ text: "ResDef Code",dataIndex: 'resDefCode', sortable: true},
	        	{ text: "ResDef Name", dataIndex: 'resDefName', sortable: true},
	        	{ text: "Active Flag",dataIndex: 'activeFlagDesc', sortable: false},
	        	{ text: "Resource Type", dataIndex: 'resTypeDesc', sortable: true},
	        	{ text: "Is Tree", dataIndex: 'isTreeDesc',sortable: true
	        }
	        ],
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
        var gridresdatastore = Ext.create('Ext.data.Store', {
            //pageSize: 10,
            fields:['resId','resCode','resName','resType','assigned',{ name: 'isAssign', type: 'bool' }],
            remoteSort: true,
            proxy: {
                type: 'ajax',
                url: 'MdMController.do?method=roleResdataGrid',
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
		        selType: 'checkboxmodel',
		        columns: 
		        [
					{text: "Resource Id", dataIndex: 'resId',sortable: true,
						renderer: function(v, metaData, record, rowIndex, colIndex, store)
							{
							 	if(record.get('assigned') == 'N')
							 	{ 
							 		//alert('rowIndex='+rowIndex);
				                	//gridresdatasgrid.getSelectionModel().select(rowIndex);
				         		}
				         		return v;
							}
					 },
					{text: "resCode Code", dataIndex: 'resCode', sortable: true},
					{text: "Resource Name", dataIndex: 'resName', sortable: true },
					{xtype: 'checkcolumn', header: 'Assigned?', dataIndex: 'assigned',width: 60,
			            editor: { xtype: 'checkbox',cls: 'x-grid-checkheader-editor' }
			        }
				],
		        columnLines: true,
		        enableLocking: true,
		        collapsible: false,
		        animCollapse: false,
		        //title: 'Resource Data',
		        el:'gridresdatasgrid',
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
        /****resurce tree data*****************/
        Ext.define('ResDataTree', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields:
            [
				{name: 'id',     type: 'string'},
				{name: 'text',     type: 'string'},
                {name: 'resDataId',     type: 'string'},
                {name: 'resId',     type: 'string'},
                {name: 'resCode', type: 'string'},
                {name: 'resName',     type: 'string'},
                {name: 'resDefId',     type: 'string'}
            ]
        });
        var resdataTreestore = Ext.create('Ext.data.TreeStore', {
            model: 'ResDataTree',
            proxy: {
            	type: 'ajax',
                url: 'MdMController.do?method=resdataTree&resDefId='
            },
            nodeParam : "id",
            root:{  
                id:-1,  
                text:'Root',  
                leaf:false,  
                expanded:false  
            },
            autoLoad : false,
            folderSort: true
        });
        var resdataTreePanel = Ext.create('Ext.tree.Panel', {
            //width: 500,
            //height: 300,
            el : 'resdataTreePanel',
            //renderTo:'resdataTreePanel',
       		layout: 'fit',
            collapsible: false,
            rootVisible: true,
            useArrows: true,
            store: resdataTreestore,
            animate: true
        });
       
       var resdataTabPanel = Ext.create('Ext.tab.Panel',{
        	renderTo:'resdataTabPanel',
        	activeTab: 0,
       		layout: 'fit',
            bodyStyle: 'padding:5px',
            items:[
						{ xtype: 'panel', title: 'Grid resource data', items : [gridresdatasgrid],closable: false},
						{ xtype: 'panel', title: 'Tree resource data',items : [resdataTreePanel], closable: false}
                   ]
        });
       /***function******************************************/
       
       
    });
    </script>
</head>
<body>
	<div id="datagrid" style='width:100%;height:100%;'></div>
	<div id="gridresdatasgrid" style='width:100%;height:100%;'></div>
	<div id="resdataTreePanel" style='width:100%;height:100%;'></div>
	<div id="resdataTabPanel" style='width:100%;height:100%;'></div>
</body>
</html>
