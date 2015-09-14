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
        var selectedResdefId = '';
        var queryResdataPara;
        Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
                'resDefId','resDefCode','resDefName','resType','resTypeDesc','isTree','isTreeDesc','createDatetime','modifyDatetime','activeFlag','activeFlagDesc','remark'
            ],
            idProperty: 'resDefId'
        });
        var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            remoteSort: true,
            proxy: {
                type: 'ajax',
                url: 'MdMController.do?method=resdefGrid',
                reader: {root: 'rows',totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{property: 'resDefId',direction: 'DESC'}]
        });
        var selModel = Ext.create('Ext.selection.CheckboxModel', {
        	mode:'single',
	        listeners: {
	            selectionchange: function(sm, selections)
	            {
	            	var rec = datagrid.getSelectionModel().getSelection();
               		if(rec.length>0)
               		{
		            	var isTree = rec[0].get('isTree');
		            	var resDefId = rec[0].get('resDefId');
		            	showResourceData(resDefId,isTree)
               		}
	            }
	        }
	    });
	    var datagrid = Ext.create('Ext.grid.Panel', {
       		el:'datagrid',layout: 'fit',height: 200,region:'center',autoScroll:true,columnLines: true,
   	   		store : gridstore,
        	selModel: selModel,
	   	   	columns:
	   	   	[
				{ text: "Resource Code",dataIndex: 'resDefCode', sortable: true},
	        	{ text: "Resource Name", dataIndex: 'resDefName', sortable: true},
	        	{ text: "Active Flag",dataIndex: 'activeFlagDesc', sortable: true},
	        	{ text: "Resource Type", dataIndex: 'resTypeDesc', sortable: true},
	        	{ text: "Is Tree", dataIndex: 'isTreeDesc',sortable: true
	        }
	        ],
    	   	bbar:new Ext.PagingToolbar({pageSize : 10,store : gridstore,displayInfo: true,displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})
        });
        datagrid.render();
        gridstore.loadPage(1);
        var gridresdatastore = Ext.create('Ext.data.Store', {
            fields:['resDataId','resId','resCode','resName','resType','resDefId',{ name:'assigned',type: 'bool'},'resRoleId'],
            remoteSort: true,
            proxy: {
                type: 'ajax',
                url: 'MdMController.do?method=roleResdataGrid',
                reader: {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{property: 'resCode',direction: 'DESC'}]
        });
        var gridresdatasgrid = Ext.create('Ext.grid.Panel', {
       		columnLines: true,autoScroll:true,autoExpandColumn:'action',
	        store: gridresdatastore,
	        selType: 'checkboxmodel',
	        el:'gridresdatasgrid',
	        columns: 
	        [
				{text: "Resource Id", dataIndex: 'resId',sortable: true},
				{text: "resCode Code", dataIndex: 'resCode', sortable: true},
				{text: "Resource Name", dataIndex: 'resName', sortable: true },
				{xtype: 'checkcolumn', header: 'Assigned', dataIndex: 'assigned',width:60,
		            editor: { xtype: 'checkbox'}
		        }
			],
			tbar:[{text: 'Save',pressed :true,icon:'/Unieap/unieap/js/common/images/accept.png',
	            handler:function() 
	            {
					var records = gridresdatastore.getUpdatedRecords();
					var datas = [];
					Ext.Array.each(records, function(record){
						datas.push(record.data); 
					});
					//var dd = Ext.JSON.encode(datas);
					//alert('dd='+dd);
					Ext.Ajax.request({
		                url: 'MdMController.do?method=resAssignToRole',
		                params:{'operType':'GridAssign','roleId':roleId,'datas':Ext.JSON.encode(datas)},
		                success: function(response, opts) 
		                {
		                	Ext.MessageBox.alert('Status', 'Save data successfully.');
		                	gridresdatastore.reload();
		                },
		                failure: function(response, opts) 
		                {
		                	Ext.MessageBox.alert('Status', 'Save data failed.Error:'+response.status);
		                }
		             });
	            }
	        }],
	        bbar:new Ext.PagingToolbar({store : gridresdatastore,displayInfo: true,displayMsg: 'Displaying topics {0} - {1} of {2}',emptyMsg: "No topics to display"})
	    });
		gridresdatastore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryResdataPara);
        });
        Ext.define('ResDataTree', {
            extend: 'Ext.data.Model',idProperty: 'id',
            fields:
            [
				{name: 'id',     type: 'string'},
				{name: 'text',     type: 'string'},
                {name: 'resDataId',     type: 'string'},
                {name: 'resId',     type: 'string'},
                {name: 'resCode', type: 'string'},
                {name: 'resName',     type: 'string'},
                {name: 'resDefId',     type: 'string'},
                {name:'assigned',type: 'bool'},
                {name: 'resRoleId',     type: 'string'}
            ]
        });
        var resdataTreestore = Ext.create('Ext.data.TreeStore', {
            model: 'ResDataTree',
            proxy: {
            	type: 'ajax',
                url: 'MdMController.do?method=roleResdataTree'
            },
            nodeParam:"id",folderSort:true,autoLoad:false,
            root:{id:-1,text:'Root',leaf:false,expanded:false}
        });
         resdataTreestore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryResdataPara);
        });
        var resdataTreePanel = Ext.create('Ext.tree.Panel', {
            el:'resdataTreePanel',collapsible: false,rootVisible:true,useArrows:true,animate:true,
            store: resdataTreestore,height:280,
            columns:
            [
            	{xtype: 'treecolumn',text: 'Resource Data',width: 300,sortable: true,dataIndex: 'text',locked: true},
            	{ xtype: 'checkcolumn', header: 'Assign',dataIndex: 'assigned', width: 40, stopSelection: false}
            ],
            tbar:[{text: 'Save',pressed :true,icon:'/Unieap/unieap/js/common/images/accept.png',
	            handler:function() 
	            {
					var records = resdataTreestore.getUpdatedRecords();
					var datas = [];
					Ext.Array.each(records, function(record){
						datas.push(record.data); 
					});
					Ext.Ajax.request({
		                url: 'MdMController.do?method=resAssignToRole',
		                params:{'operType':'TreeAssign','roleId':roleId,'datas':Ext.JSON.encode(datas)},
		                success: function(response, opts) 
		                {
		                	Ext.MessageBox.alert('Status', 'Save data successfully.');
		                	resdataTreestore.reload();
		                },
		                failure: function(response, opts) 
		                {
		                	Ext.MessageBox.alert('Status', 'Save data failed.Error:'+response.status);
		                }
		             });
	            }
	        }]
        });
        var resdataTabPanel = Ext.create('Ext.tab.Panel',{
        	renderTo:'resdataTabPanel',activeTab:0,layout:'fit',bodyStyle:'padding:5px',
            items:
            [
				{xtype: 'panel', title: 'Grid resource data', items : [gridresdatasgrid],closable: false},
				{xtype: 'panel', title: 'Tree resource data',items : [resdataTreePanel], closable: false}
            ]
        });
        function showResourceData(resDefId,isTree)
        {
        	if(isTree=='Y')
           	{
           		var tab = resdataTabPanel.items.items[1];
        		tab.show();
        		queryResdataPara= {roleId:roleId,resDefId:resDefId};
        		resdataTreestore.load({params:queryResdataPara}); 
           	}else
           	{
                var tab = resdataTabPanel.items.items[0];
        		tab.show();
            	queryResdataPara={roleId:roleId,resDefId:resDefId};
                gridresdatastore.load({params:queryResdataPara});
           	}
        }
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
