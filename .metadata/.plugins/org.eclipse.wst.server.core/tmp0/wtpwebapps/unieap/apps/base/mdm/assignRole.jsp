<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<script type="text/javascript">
	var roleId = '-1';
</script>
<title>Assign Role Data</title>
    <script type="text/javascript">
    Ext.onReady(function(){
        Ext.tip.QuickTipManager.init();
        var queryPara = {};
        var queryAssignPara={roleId:roleId};
        var selectedRecord;
		var queryform = Ext.create('Ext.form.Panel',{
			fieldDefaults:
			{labelAlign: 'left', labelWidth: 90 },
	        layout: 'fit',
	        width : '100%',
     	   	frame : true,
     	   	el:'queryform',
     	    items:
     	    	[
     	           { id:'queryFieldset', width: '100%',xtype:'fieldset',title: 'Search',
  	                 startCollapsed: true,collapsible: true, defaultType: 'textfield',
  	                	items:
  	                		[
								{ xtype: 'fieldcontainer',layout: 'hbox',
								    items: 
								    [
								        { xtype:'textfield',name: 'roleCode',fieldLabel: 'Role Code'},
								        { xtype:'textfield',name:'roleName',fieldLabel: 'Role Name'},
								        { name: 'activeFlag',fieldLabel: 'Active Flag',
					    	                xtype: 'combo', anchor:'95%',
					    	                displayField:   'dicName',
				                            valueField:     'dicCode',
				                            store:Ext.create('Ext.data.Store', {
				                                fields : ['dicCode', 'dicName'],
				                                data:UnieapDicdata.ActiveFlagOpt
				                             })
				    	                }
								    ]
								},
								{xtype: 'fieldcontainer',layout: 'hbox',
					                items: 
					                [
						               	{name: 'createDatetime', fieldLabel: 'Create Datetime',format: 'Y-m-d', xtype: 'datefield'},
						                {name: 'modifyDatetime', fieldLabel: 'Modify Datetime', format: 'Y-m-d',xtype: 'datefield'},
						                { xtype:'button',text:'Search',iconAlign: 'right',
					    	                handler: function (){
					    	                	var roleCode=queryform.getForm().findField("roleCode").getValue(); 
					    	                    var roleName=queryform.getForm().findField("roleName").getValue(); 
					    	                    var createDatetime=dateFormat(queryform.getForm().findField("createDatetime").getValue()); 
					    	                    var modifyDatetime=dateFormat(queryform.getForm().findField("modifyDatetime").getValue()); 
					    	                    var activeFlag=queryform.getForm().findField("activeFlag").getValue();
					    	                    queryPara = 
					    	                    	{
					    	                         	roleCode:roleCode,
					    	                         	roleName:roleName,
					    	                         	createDatetime:createDatetime,
					    	                         	modifyDatetime:modifyDatetime,
					    	                         	activeFlag:activeFlag
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
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
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
        gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
        var selModel = Ext.create('Ext.selection.CheckboxModel', {
        	mode:'single',
	        listeners: {
	            selectionchange: function(sm, selections)
	            {
	            	var rec = datagrid.getSelectionModel().getSelection();
               		if(rec.length>0)
               		{
		            	roleId = rec[0].get('roleId');
		            	showAssignData(roleId)
               		}
	            }
	        }
	    });
        var datagrid = Ext.create('Ext.grid.Panel', 
        {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
	   	 	selModel: selModel,
   	   		store : gridstore,
	   	   	columns:
	   	   	[
	   	   		{ text: "Role Code",width:90, dataIndex: 'roleCode',sortable: true},
	   	   		{ text: "Role Name",width:90, dataIndex: 'roleName', sortable: true},
	   	   		{ text: "Active Flag",width:60,dataIndex: 'activeFlagDesc',sortable: false},
	   	   		{ text: "Create Datetime",width: 120, dataIndex: 'createDatetime', sortable: true},
	   	   		{ text: "Modify Datetime",width: 120, dataIndex: 'modifyDatetime', sortable: true},
	   	   		{ text: "Remark", dataIndex: 'remark',sortable: false}
	   	   	],
	        tbar:[queryform],
    	   	bbar:new Ext.PagingToolbar(
    	   	{ store : gridstore,displayInfo: true, displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})
        	
        });
        datagrid.render();
        gridstore.loadPage(1);
        
        var griddatastore = Ext.create('Ext.data.Store', {
            fields:['userId','userCode','userName',{name:'assigned',type: 'bool'}],
            remoteSort: true,
            proxy: {
                type: 'ajax',
                url: 'MdMController.do?method=assignUser',
                reader: {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{property: 'userCode',direction: 'DESC'}]
        });
        griddatastore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryAssignPara);
        });
        var griddatasgrid = Ext.create('Ext.grid.Panel', {
       		columnLines: true,autoScroll:true,autoExpandColumn:'action',
	        store: griddatastore,
	        selType: 'checkboxmodel',
	        el:'gridDatasgrid',
	        columns: 
	        [
				{text: "User Id", dataIndex: 'userId',sortable: true},
				{text: "User Code", dataIndex: 'userCode', sortable: true},
				{text: "User Name", dataIndex: 'userName', sortable: true },
				{xtype: 'checkcolumn', header: 'Assigned', dataIndex: 'assigned',width:60,
		            editor: { xtype: 'checkbox'}
		        }
			],
			tbar:[{text: 'Save',pressed :true,icon:'/Unieap/unieap/js/common/images/accept.png',
	            handler:function() 
	            {
					var records = griddatastore.getUpdatedRecords();
					var datas = [];
					Ext.Array.each(records, function(record){
						datas.push(record.data); 
					});
					//var dd = Ext.JSON.encode(datas);
					//alert('dd='+dd);
					Ext.Ajax.request({
		                url: 'MdMController.do?method=roleAssign',
		                params:{'operType':'toUser','roleId':roleId,'datas':Ext.JSON.encode(datas)},
		                success: function(response, opts) 
		                {
		                	Ext.MessageBox.alert('Status', 'Save data successfully.');
		                	griddatastore.reload();
		                },
		                failure: function(response, opts) 
		                {
		                	Ext.MessageBox.alert('Status', 'Save data failed.Error:'+response.status);
		                }
		             });
	            }
	        }],
	        bbar:new Ext.PagingToolbar({store :griddatastore,displayInfo: true,displayMsg: 'Displaying topics {0} - {1} of {2}',emptyMsg: "No topics to display"})
	    });
        
        Ext.define('OrgDataTree', {
            extend: 'Ext.data.Model',idProperty: 'id',
            fields:
            ['id','parentId','path','isLeaf','text','orgId','orgCode','orgName',{name:'assigned',type: 'bool'}]
        });
        var dataTreestore = Ext.create('Ext.data.TreeStore', {
            model: 'OrgDataTree',
            proxy: {
            	type: 'ajax',
                url: 'MdMController.do?method=assignOrgTree'
            },
            root:{id:-1,text:'Root',leaf:false,expanded:true,allowDrag:false},
            nodeParam : "id",autoLoad:true,folderSort:true
        });
        dataTreestore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryAssignPara);
        });
        var dataTreePanel = Ext.create('Ext.tree.Panel', {
            store:dataTreestore,rootVisible:false,
            columns:
            [
            	{xtype: 'treecolumn',text: 'Org List',width: 300,sortable: true,dataIndex: 'text',locked: true},
            	{xtype: 'checkcolumn', header: 'Assign',dataIndex: 'assigned', width: 80, stopSelection: false}
            ],
            tbar:[{text: 'Save',pressed :true,icon:'/Unieap/unieap/js/common/images/accept.png',
	            handler:function() 
	            {
					var records = dataTreestore.getUpdatedRecords();
					var datas = [];
					Ext.Array.each(records, function(record){
						datas.push(record.data); 
					});
					Ext.Ajax.request({
		                url: 'MdMController.do?method=roleAssign',
		                params:{'operType':'toOrg','roleId':roleId,'datas':Ext.JSON.encode(datas)},
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
        
        var dataTabPanel = Ext.create('Ext.tab.Panel',{
        	renderTo:'dataTabPanel',activeTab:0,layout:'fit',bodyStyle:'padding:5px',
            items:
            [
				{xtype: 'panel',height:300, title: 'Assign To Org',items :[dataTreePanel],closable: false},
				{xtype: 'panel',height:300, title: 'Assign To User',items :[griddatasgrid], closable: false}
            ]
        });
        function showAssignData(roleId)
        {
       		queryAssignPara={roleId:roleId};
       		//var tab1 = dataTabPanel.items.items[0];
       		//tab1.loader.load(); 
       		dataTreestore.load({params:queryAssignPara}); 
            griddatastore.load({params:queryAssignPara});
            
        }
    });
    </script>
</head>
<body>
	<div id="queryform" style='width:100%;height:35;font-size:0;'></div>
	<div id="datagrid" style='width:100%;height:100%;'></div>
	<div id="gridDatasgrid" style='width:100%;height:100%;'></div>
	<div id="dataTreePanel" style='width:100%;height:100%;'></div>
	<div id="dataTabPanel" style='width:100%;height:100%;'></div>
</body>
</html>
