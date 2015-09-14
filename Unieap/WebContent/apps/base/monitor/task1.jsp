<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>User</title>
    <script type="text/javascript">
    Ext.onReady(function(){
        Ext.tip.QuickTipManager.init();
        /***query form**********************************/
        var queryPara;
		var queryform = Ext.create('Ext.form.Panel',{
			fieldDefaults:
			{ labelAlign: 'left', labelWidth: 90 },
	        layout: 'fit',
	        width : '100%',
     	   	frame : true,
     	   	el:'queryform',
     	   	tbar:[{ pressed :true,icon:'/Unieap/unieap/js/common/images/add.png',
               		tooltip:'Add',text:'Add',xtype:'button',id:'Task_Add',
		            handler : function(){showForm('Add',null);}
		    }],
	        listeners:
	        {afterRender:function(thisForm, options){
		        	if(UnieapButton.Task_Add!=null&&UnieapButton.Task_Add.disabled== true){Ext.getCmp('Task_Add').hide();}}
	        },
     	    items:[
     	           { id:'queryFieldset', width: '100%',xtype:'fieldset',title: 'Search',
  	                 startCollapsed: true,collapsible: true, defaultType: 'textfield',
  	                	items:
  	                		[
								{ xtype: 'fieldcontainer',layout: 'hbox',
								    items: 
								    [
								        { xtype:'textfield',name:'taskName',fieldLabel: 'Task Name'},
								        { name: 'activeFlag',fieldLabel: 'Active Flag',
					    	                xtype: 'combo', anchor:'95%',
					    	                displayField:   'dicName',
				                            valueField:     'dicCode',
				                            store:          Ext.create('Ext.data.Store', {
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
					    	                    var taskName=queryform.getForm().findField("taskName").getValue(); 
					    	                    var createDatetime=dateFormat(queryform.getForm().findField("createDatetime").getValue()); 
					    	                    var modifyDatetime=dateFormat(queryform.getForm().findField("modifyDatetime").getValue()); 
					    	                    var activeFlag=queryform.getForm().findField("activeFlag").getValue();
					    	                    queryPara = 
					    	                    	{
					    	                         	taskName:taskName,
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
    	/***data grid********************************************/
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'taskId','taskOwnerId','taskOwnerDesc','taskName','startDate','startTime','endDate','endTime','duration','status','statusDesc','completePer','preTaskId','preTaskName','nextTaskId','nextTaskName','createBy','createDatetime','modifyBy','modifyDatetime','activeFlag','activeFlagDesc','remark','beId'
            ],
            idProperty: 'taskId'
        });
        var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            remoteSort: true,
            proxy: 
            { type: 'ajax', url: 'MonitorController.do?method=taskGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: 
            [
            	{ property: 'taskName', direction: 'DESC'}
            ]
        });
        gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
        var operationItems = [];
        var selectedRecord;
       	if(UnieapButton.Task_Modify!=null&&UnieapButton.Task_Modify.abled== true)
       	{
        	operationItems.push({
        	   icon   : '/Unieap/unieap/js/common/images/edit.png',
               tooltip: 'Edit',
               handler:function(grid, rowIndex, colIndex)
               {	
               		selectedRecord = grid.getStore().getAt(rowIndex);
            		showForm('Modify',selectedRecord);
               }
           });
           operationItems.push({iconCls :'',tooltip:''});
       	}
       	if(UnieapButton.Task_Delete!=null&&UnieapButton.Task_Delete.abled== true)
       	{
        	operationItems.push({
        	   icon   : '/Unieap/unieap/js/common/images/delete.png',
               tooltip: 'Delete',
               handler:function(grid, rowIndex, colIndex)
               {	
            	    selectedRecord = grid.getStore().getAt(rowIndex);
               		Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?', removeDatas);
               }
           });
           operationItems.push({iconCls :'',tooltip:''});
       	}
        var selModel = Ext.create('Ext.selection.CheckboxModel');
        var datagrid = Ext.create('Ext.grid.Panel', 
        {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
	   	 	selModel:selModel,
   	   		store : gridstore,
	   	   	columns:
	   	   	[
	   	   		{ text: "Task Name", dataIndex: 'taskName', sortable: true},
	   	   		{ text: "Active Flag",dataIndex: 'activeFlagDesc',sortable: false},
	   	   		{ text: "Create Datetime",width: 130, dataIndex: 'createDatetime', sortable: true},
	   	   		{ text: "Modify Datetime",width: 130, dataIndex: 'modifyDatetime', sortable: true},
	   	   		{ text: "Remark", dataIndex: 'remark',sortable: false},
	   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "Operation",width:100,items:operationItems}
	   	   	],
	        tbar:[queryform],
    	   	bbar:new Ext.PagingToolbar(
    	   	{ store : gridstore,displayInfo: true, displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})
        	
        });
        datagrid.render();
        gridstore.loadPage(1);
        var taskOwnerstore = Ext.create('Ext.data.Store',{
            fields : ['dicCode', 'dicName'],
            autoLoad: true,remoteSort: true,
            proxy:{
                type: 'ajax',url: 'MonitorController.do?method=taskOwnerDic',
                reader:{type: 'json',root: 'rows', totalProperty: 'totalCount'}
            },
            sorters:[{ property: 'dicName', direction: 'DESC'}]
        });
        /***function******************************************/
        var dataWin = null;
        var dataForm = null;
        var taskResourceForm = null;
        var operType = '';
        function showForm(status,selectedRecord){
        	operType = status;
            if (dataWin==null){
            	dataForm = Ext.widget('form',
            	{
                    defaults:{labelAlign: 'left', labelWidth: 90, anchor: '100%'},
                    bodyPadding:5,
                    items:
                    [
                    	{xtype:'fieldset', title:'Task Basic Info',
	                        items:
	                        [
	                        	{ xtype:'hiddenfield', name:'taskId'},
	                        	{ xtype:'hiddenfield', name:'beId'},
	                        	{ xtype:'hiddenfield', name:'createBy'},
	                        	{ xtype:'hiddenfield', name:'modifyBy'},
	                        	{ xtype:'hiddenfield', name:'createDatetime'},
	                        	{ xtype:'hiddenfield', name:'modifyDatetime'},
	                        	{ xtype:'textfield', name:'taskName',fieldLabel:'Task Name',allowBlank:false,
	                        		validateOnChange:false, validateOnBlur :true,
								    validator :function(value)
								    	{
								    			var error = true; 
								    			if(operType == 'Modify' || value==''||value ==null)
								    			{
								    				return true;
								    			}
									    		Ext.Ajax.request({
									                url: 'MonitorController.do?method=taskDeal',
									                async:false,
									                params:{'operType':"checkExist","taskName":value},
									                success: function(response, opts) 
									                {
									                	var result = Ext.JSON.decode(response.responseText);
									                    if(result.isSuccess == 'success')
									                    {
									                		error = true; 
									                    }else
									                    {
									                    	error = value+ ' already exist, please change to another';
									                    }
									                },
									                failure: function(response, opts) 
									                {
									                	error='Check Task Name exist failed,error message:'+response.responseText;
									                }
								             });
								             return error;
								    	}
	                        	},
	                        	{
					                xtype:'fieldcontainer',
					                fieldLabel:'When',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                defaults: {
					                    flex: 1,
					                    hideLabel: true
					                },
					                items: [
					                    { xtype:'datefield',name:'startDate',fieldLabel: 'Start',margin: '0 5 0 0',allowBlank: false},
					                    {xtype: 'timefield',name: 'startTime',minValue: '00:00 AM',maxValue: '24:00 PM',allowBlank: false}
					                ]
					            },
	                        	{
					                xtype:'fieldcontainer',
					                fieldLabel:'To',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                defaults: {
					                    flex: 1,
					                    hideLabel: true
					                },
					                items: [
					                    { xtype:'datefield',name:'endDate',fieldLabel: 'Start',margin: '0 5 0 0',allowBlank: false},
					                    {xtype: 'timefield',name: 'endTime',minValue: '00:00 AM',maxValue: '24:00 PM',allowBlank: false}
					                ]
					            },
	                        	{ xtype:'combo', forceSelection: true,emptyText: 'Select an Option', editable:false, allowBlank:false,
	                                fieldLabel:'Status',name:'status',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                {fields : ['dicCode', 'dicName'],data:UnieapDicdata.TaskStatus})
								},
								{ xtype:'textfield',name:'completePer', fieldLabel:'Complete Percent', allowBlank:false},
								{ xtype:'combo', forceSelection: true,emptyText: 'Select an Option', editable:false, allowBlank:false,
	                                fieldLabel:'Task Owner',name:'taskOwnerId',displayField:'dicName',valueField:'dicCode',
	                                store:taskOwnerstore
								},
	                        	{ xtype:'combo', forceSelection: true,emptyText: '', editable:false, allowBlank:true,
	                                fieldLabel:'Previous Task',name:'preTaskId',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                {fields : ['dicCode', 'dicName'],data:UnieapDicdata.Title})
								},
								{ xtype:'combo', forceSelection: true, emptyText:'', editable:false, allowBlank:true,
	                                fieldLabel:'Next Task',name:'nextTaskId',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                {fields : ['dicCode', 'dicName'],data:UnieapDicdata.Title})
								},
	                        	{ xtype:'combo', forceSelection: true, emptyText: 'Select an Option',editable:false,allowBlank:false,
	                                fieldLabel:'Active Flag',name:'activeFlag',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata.ActiveFlag})
								}
	                        ]
	                    },
	                    {xtype:'fieldset',title:'Task Extend Info',
	                        items:
	                        [
		                        { xtype:'textareafield', name:'remark',fieldLabel:'Remark',growMin:60,growMax:100,allowBlank:true}
	                        ]
                    	}
                    ],
                    buttons: 
                    [
	                    {text: 'Cancel',
	                        handler: function() 
	                        {
	                        	dataForm.getForm().reset();
	                        	dataWin.hide();
	                        }
	                    }, 
	                    {text: 'Submit',
	                        handler: function() {
	                        	var form = dataForm.getForm();
	                        	 if (form.isValid()){
	                                 form.submit({
	                                     clientValidation: true,
	                                     waitMsg: 'Processing...',
	                                     method: 'POST',
	                                     params:{'operType':'Task'+operType},
	                                     url: 'MonitorController.do?method=taskDeal',
	                                     success: function(form, action) {
	                                     	var task = Ext.JSON.decode(Ext.JSON.decode(action.response.responseText).task);
	                                     	dataForm.getForm().setValues(task);
	                                     	gridstore.reload();
	                                    	Ext.MessageBox.alert('Status', 'Save successfully.');
	                                     },
	                                     failure: function(form, action) {
	                                    	Ext.MessageBox.alert('Status', 'Save failed, please retry.');
	                                     }
	                                 });
	                        	 }
	                        }
	                    }
                    ]
                });
                var gridresourcestore = Ext.create('Ext.data.Store', {
		            fields:['taskResourceId','taskId','resourceName','resourceType','resourceTypeDesc','createBy','createDatetime','modifyBy','modifyDatetime','activeFlag','activeFlagDesc','remark','beId'],
		            remoteSort: true,
		            proxy: 
		            { type: 'ajax', url: 'MonitorController.do?method=taskResourceGrid',
		                reader: 
		                {root: 'rows', totalProperty: 'totalCount'},
		                simpleSortMode: true
		            },
		            sorters: 
		            [
		            	{ property: 'resourceName', direction: 'DESC'}
		            ]
		        });
		        gridresourcestore.on('beforeload', function (store, options){
		            Ext.apply(store.proxy.extraParams,queryPara);
		        });
                 var rowEditing = Ext.create('Ext.grid.plugin.RowEditing', {
				        clicksToMoveEditor: 1,
				        autoCancel: false
				    });
                var resourcesgrid = Ext.create('Ext.grid.Panel', {
				        store: gridresourcestore,
				        columns: [{header: 'Resource Type',dataIndex: 'resourceTypeDesc',width: 150,
				            editor: { xtype:'combo', forceSelection: false,emptyText: 'Human', editable:true, allowBlank:false,
	                                  name:'resourceType',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                {fields : ['dicCode', 'dicName'],data:UnieapDicdata.TaskResourceType})
								}
				        }, {header: 'Resource Name', dataIndex: 'resourceName',width: 200,
				            editor: { allowBlank: false}
				        }],
				        layout: 'fit',
				        tbar: [{
				            text: 'Add',
				            icon:'/Unieap/unieap/js/common/images/add.png',
				            handler : function() {
				                rowEditing.cancelEdit();
				                var taskId = dataForm.getForm().findField("taskId").getValue();
				                var r = {taskId:taskId,resourceType:'Human',resourceName:'Resource Name'};
				                gridresourcestore.insert(0, r);
                				rowEditing.startEdit(0, 0);
				            }
				        }, {
				            id: 'removeresource',
				            text: 'Remove',
				            icon:'/Unieap/unieap/js/common/images/delete.png',
				            handler: function() {
				                var sm = resourcesgrid.getSelectionModel();
				                rowEditing.cancelEdit();
				                gridresourcestore.remove(sm.getSelection());
				                if (gridresourcestore.getCount() > 0) {
				                    sm.select(0);
				                }
				            },
				            disabled: true
				        }],
				        buttons: 
	                    [
		                    {text: 'Cancel',
		                        handler: function() 
		                        {
		                        	dataWin.hide();
		                        }
		                    }, 
		                    {text: 'Submit',
		                        handler: function() {
		                        	var taskId = dataForm.getForm().findField("taskId").getValue();
		                        	if(taskId == null || taskId == ''){
		                        		Ext.MessageBox.alert('Status', 'Please Create Task Info first');
		                        		return;
		                        	} 
		                        	var insertedrecords=[];
		                        	Ext.Array.each(gridresourcestore.getNewRecords(), function(record){
										insertedrecords.push(record.data); 
									});
									var updatedrecords=[];
		                        	Ext.Array.each(gridresourcestore.getUpdatedRecords(), function(record){
										updatedrecords.push(record.data); 
									});
		                        	var removedrecords=[];
		                        	Ext.Array.each(gridresourcestore.getRemovedRecords(), function(record){
										removedrecords.push(record.data); 
									});
		                        	var datas = {insertedrecords:insertedrecords,updatedrecords:updatedrecords,removedrecords:removedrecords};
		                        	Ext.Ajax.request({
						                url: 'MonitorController.do?method=taskDeal',
						                params:{'operType':'Resource'+operType,'taskId':taskId,'datas':Ext.JSON.encode(datas)},
						                success: function(response, opts) 
						                {
						                	Ext.MessageBox.alert('Status', 'Save data successfully.');
						                	//gridresdatastore.reload();
						                },
						                failure: function(response, opts) 
						                {
						                	Ext.MessageBox.alert('Status', 'Save data failed.Error:'+response.status);
						                }
						             });
		                        }
		                    }
	                    ],
	                    listeners: {
				            'selectionchange': function(view, records) {
				            	Ext.getCmp('removeresource').setDisabled(!records.length);
				            },
				            'validateedit': function (editor,e){
				            	var data = e.newValues;
				            	if(data.resourceName=='Resource Name'){
				            		Ext.MessageBox.alert('Status', 'Please key in Resource Type or Resource Name');;
				            		return false;
				            	}else{
				            		return true;
				            	}
				            }
				        },
				        plugins: [rowEditing]
				    });
                dataWin = Ext.widget('window', 
                { title: 'Data', closeAction: 'hide', width: 400, height: 500, layout: 'fit', modal: true, items: 
	                [
	                	{
		                    xtype: 'tabpanel',
		                    items: [{
		                        layout: 'fit',
		                        title: 'Task Info',
		                        items: [dataForm]
		                    },{
		                    	layout: 'fit',
		                        title: 'Task Resource Info',
		                        items: [resourcesgrid]
		                    }]
		                }
	                ]
                ,defaultFocus: 'taskName' });
            }
            if(operType=='Add')
            {
            	dataForm.getForm().reset();
            	dataWin.show();
            }else if(operType=='Modify')
            {
            	dataWin.show();
            	dataForm.getForm().setValues(selectedRecord.data);
            }else
            {
            	dataWin.show();
            }
        }
        function removeDatas(btn)
        {
        	if(btn=='yes'){
	        	var taskId= selectedRecord.get("taskId");
	        	Ext.Ajax.request({
	                url: 'MonitorController.do?method=taskDeal',
	                params:{'operType':"Delete","taskId":taskId},
	                success: function(response, opts) 
	                {
	                	Ext.MessageBox.alert('Status', 'Remove data successfully.');
	                	gridstore.remove(selectedRecord);
	                },
	                failure: function(response, opts) 
	                {
	                	Ext.MessageBox.alert('Status', 'Remove data failed.Error:'+response.status);
	                }
	             });
        	}
        }
        function showResult(btn)
        {
        	dataWin.hide();
        	gridstore.reload();
        }
    });
    </script>
</head>
<body>
    <div id="queryform" style='width:100%;height:35;font-size:0;'></div>
	<div id="datagrid" style='width:100%;height:100%;'></div>
	<div id="editor-grid" style='width:100%;height:100%;'></div>
</body>
</html>
