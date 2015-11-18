<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Org</title>
    <script type="text/javascript">
    Ext.onReady(function(){
       var queryPara;
       var operType = '';
       Ext.tip.QuickTipManager.init();
       Ext.define('dataTree', {
		    extend: 'Ext.data.Model',
		    fields: ['id','parentId','path','isLeaf','text','menuId','menuCode','menuName','beId']
		});
       var menuTreeStore = Ext.create('Ext.data.TreeStore', {
            model: 'dataTree',
            proxy: {
            	type: 'ajax',
                url: 'MdMController.do?method=menuTree'
            },
            root:{id:-1,text:'Root',leaf:false,checked:false,expanded:true,allowDrag:false},
            nodeParam : "id",autoLoad:false,folderSort:true
        });
        menuTreeStore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
        var menuTreePanel = Ext.create('Ext.tree.Panel', {
	        rootVisible: false,height:500,
	        tbar:[
	        	{ pressed :true,icon:'/Unieap/unieap/js/common/images/add.png',
               		tooltip:"View Role Menu",text:'View',xtype:'button',id:'RoleMenu_View',
		            handler : function(){
		            	var rec = datagrid.getSelectionModel().getSelection();
	                	if(rec.length==0){
	                		Ext.MessageBox.alert('Status', 'Please select one row data.');
	                	}else{
			            	showAssignMenu(rec[0].get('roleId'));
	                	}
		            }
		    	},'-',{ pressed :true,icon:'/Unieap/unieap/js/common/images/save.gif',
               		tooltip:'Save',text:'Save',xtype:'button',id:'RoleMenu_Save',
		            handler : function(){
		            	var rec = datagrid.getSelectionModel().getSelection();
	                	if(rec.length==0){
	                		Ext.MessageBox.alert('Status', 'Please select one row data.');
	                	}else{
			            	saveAssignMenu(rec[0].get('roleId'));
	                	}
		            }
		    	}
		    ],
            store: menuTreeStore
        });
        /**
            半选中状态复选框checkchange事件
         */
        menuTreePanel.on('checkchange', function(node, checked) {
	        var parentNode = node.parentNode;
	        if (parentNode !== null && checked) {
	        	parentCheck(parentNode, checked);
	        	//parentNode.set('checked', checked); 
	        }
        },menuTreePanel);
        Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'roleId','roleCode','roleName','createDatetime','modifyDatetime','activeFlag','activeFlagDesc',
            	'remark','beId','createBy','modifyBy' 
            ],
            idProperty: 'roleId'
        });
        var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy: 
            { type: 'ajax', url: 'MdMController.do?method=roleGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: 
            [
            	{ property: 'roleCode', direction: 'DESC'}
            ]
        });
       	var selModel = Ext.create('Ext.selection.CheckboxModel', {
        	mode:'single',
	        listeners: {
	            selectionchange: function(sm, selections)
	            {
	            	var rec = datagrid.getSelectionModel().getSelection();
               		if(rec.length>0)
               		{
		            	showAssignMenu(rec[0].get('roleId'));
               		}
	            }
	        }
	    });
        var datagrid = Ext.create('Ext.grid.Panel', 
        {columnLines: true,autoScroll:true,selModel:selModel,
   	   		store : gridstore,
	   	   	columns:
	   	   	[
	   	   		{ text: "Role Code",width:90, dataIndex: 'roleCode',sortable: true},
	   	   		{ text: "Role Name",width:90, dataIndex: 'roleName', sortable: true},
	   	   		{ text: "Active Flag",width:60,dataIndex: 'activeFlagDesc',sortable: false},
	   	   		{ text: "Remark", dataIndex: 'remark',sortable: false}
	   	   	],
    	   	bbar:new Ext.PagingToolbar(
    	   	{ store : gridstore,displayInfo: true, displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})
        	
        });
        gridstore.loadPage(1);
        Ext.create('Ext.Viewport', {
	        layout:'column',
	        bodyStyle: 'padding:5px',
            defaults: {bodyStyle:'padding:15px'},
	        items: [{
                title:'Role Info',
                columnWidth: 0.60,
                items:[datagrid]
            },{
                title: 'Menu List',
                columnWidth: 0.40,
                items:[menuTreePanel]
                
            }]
	    });
    	//级联选中父节点
		var parentCheck = function(node, checked) {
			node.set('checked', checked);
			var parentNode = node.parentNode;
			if (parentNode !== null) {
				parentCheck(parentNode, checked);
			}
		};
		function showAssignMenu(roleId)
		{
			queryPara ={roleId:roleId};
            menuTreeStore.load({params:queryPara});
		}
		function saveAssignMenu(roleId)
		{
			var records = menuTreePanel.getChecked();
			var datas = [];
			//var records = menuTreePanel.getSelectionModel().selected;
			if(records.length>0)
			{
				//Ext.Array.each(records.items, function(record){
				Ext.Array.each(records, function(record){
						datas.push(record.data); 
					});
				Ext.Ajax.request({
		                url: 'MdMController.do?method=menuAssignToRole',
		                params:{'operType':'menuAssign','roleId':roleId,'datas':Ext.JSON.encode(datas)},
		                success: function(response, opts) 
		                {
		                	Ext.MessageBox.alert('Status', 'Save data successfully.');
		                },
		                failure: function(response, opts) 
		                {
		                	Ext.MessageBox.alert('Status', 'Save data failed.Error:'+response.status);
		                }
		             });
			}
		}
    });
</script>
</head>
<body>
	<div id="tree-div" style='width:100%;height:100%;'></div>
	<div id="datagrid" style='width:100%;height:100%;'></div>
</body>
</html>
