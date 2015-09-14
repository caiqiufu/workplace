<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Task</title>
	<style type="text/css"> 
	.textfield-red
	{ 
   		color: #FF0000;  
	} 
</style> 
    <script type="text/javascript">
    Ext.onReady(function(){
    	var operType = '';
        Ext.tip.QuickTipManager.init();
        var queryResourcePara;
        var allTasksStore = Ext.create('Ext.data.Store',{
            fields : ['dicCode', 'dicName','startDate','endDate','startTime','endTime'],
            autoLoad: true,remoteSort: true,
            proxy:{
                type: 'ajax',url: 'MonitorController.do?method=getAllTasks',
                reader:{type: 'json',root: 'rows', totalProperty: 'totalCount'}
            },
            sorters:[{ property: 'dicName', direction: 'DESC'}]
        });
        Ext.define('dataTree', {
		    extend: 'Ext.data.Model',
		    fields: 
		    [   'id','parentId','path','isLeaf','text','taskId','taskName','taskGroup','taskNameRemark','preTaskIds',
		    	'startDate','startTime','status','statusDesc','isMain','isMainDesc','isShow','isShowDesc',
		    	'squNum','duration','completePer','endDate','endTime','activeFlag','activeFlagDesc',
		    	'beId','createBy','modifyBy','createDatetime','modifyDatetime','remark'
            ]
		});
       var taskTreeStore = Ext.create('Ext.data.TreeStore', {
            model: 'dataTree',
            proxy: {
            	type: 'ajax',
                url: 'MonitorController.do?method=taskDisplayTree'
            },
            root:{id:-1,text:'Root',leaf:false,expanded:true,allowDrag:false},
            nodeParam : "id",autoLoad:true,folderSort:true
        });
        var addAction = Ext.create('Ext.Action', {
        	icon   : '/Unieap/unieap/js/common/images/add.png',
	        text: 'Add',
	        disabled: false,
	        handler: function(widget,event) 
	        {
	        	var node = taskTreePanel.getSelectionModel().selected.items[0];
	            showForm('Add',node);
	        }
	    });
	    var modifyAction = Ext.create('Ext.Action', {
	    	icon   : '/Unieap/unieap/js/common/images/edit.png',
	        text: 'Modify',
	        disabled: false,
	        handler: function(widget,event) 
	        {
	        	var node = taskTreePanel.getSelectionModel().selected.items[0];
            	showForm('Modify',node);
	        }
	    });
	    var deleteAction = Ext.create('Ext.Action', {
	    	icon   : '/Unieap/unieap/js/common/images/delete.png',
	        text: 'Delete',
	        disabled: false,
	        handler: function(widget,event) 
	        {
	            var node = taskTreePanel.getSelectionModel().selected.items[0];
	            if(node.get('id')==-1){
	            	Ext.MessageBox.alert('Status','Root object can not remove');
	            	return;
	            }
	            if(node.isLeaf()!=true){
	        		Ext.MessageBox.alert('Status','Include sub task, can not remove');
	        	}else{
               		Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?', removeDatas);
	        	}
	        }
	    });
	    var contextMenu = Ext.create('Ext.menu.Menu', {
	        items: [addAction,modifyAction,deleteAction]
	    });
	    var targetNode = null;
	    var dragData = null;
        var taskTreePanel = Ext.create('Ext.tree.Panel', {
            layout:'fit',
            height: 500,
            store: taskTreeStore,
            viewConfig: {
                listeners:{
	                	itemcontextmenu: function(view, rec, node, index, e){
		                    e.stopEvent();
		                    contextMenu.showAt(e.getXY());
		                    return false;
		                },
	                    itemclick:function (view,rcd,item,idx,event,eOpts){
	                    	dataDisplayForm.getForm().setValues(rcd.data);
	                    	queryResourcePara = {taskId:rcd.get('taskId')};
	                    	resourcegridstore.load({params:queryResourcePara});
	                    }
	            	}
            }
        });
        var dataWin = null;
	    var dataForm = null;
        var operType = '';
        var selectedNode = null;
        function showForm(status,node){
        	operType = status;
        	selectedNode = node;
        	var parentNode = selectedNode.parentNode;
            if (dataWin==null){
            	dataForm = Ext.widget('form',
            	{
                    defaults:{labelAlign: 'left', labelWidth: 90, anchor: '100%'},
                    bodyPadding:5,
                    items:
                    [
                    	{xtype:'fieldset', title:'Basic Info',
	                        items:
	                        [
	                        	{ xtype:'hiddenfield', name:'path'},
	                        	{ xtype:'hiddenfield', name:'parentId'},
	                        	{ xtype:'hiddenfield', name:'isLeaf'},
	                        	{ xtype:'hiddenfield', name:'taskId'},
	                        	{ xtype:'hiddenfield', name:'beId'},
	                        	{ xtype:'hiddenfield', name:'squNum'},
	                        	{ xtype:'hiddenfield', name:'createBy'},
	                        	{ xtype:'hiddenfield', name:'modifyBy'},
	                        	{ xtype:'hiddenfield', name:'createDatetime'},
	                        	{ xtype:'hiddenfield', name:'modifyDatetime'},
	                        	{ xtype:'hiddenfield', name:'activeFlag'},
	                        	{
					                xtype:'fieldcontainer',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                items: [
										{xtype:'textfield', name:'taskName',fieldLabel:'Task Name',allowBlank:false},
										{xtype:'textfield', name:'taskNameRemark',fieldLabel:'Long Name',allowBlank:true}
					                ]
					            },
	                        	{
					                xtype:'fieldcontainer',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                items: [
										{ xtype:'combo', forceSelection: true, emptyText: 'Select an Option',editable:false,allowBlank:false,
			                                fieldLabel:'Task Group',name:'taskGroup',displayField:'dicName',valueField:'dicCode',
			                                store:Ext.create('Ext.data.Store', 
			                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata.TaskGroup})
										},
										{ xtype:'combo', forceSelection: true,emptyText: 'Select an Option', editable:false, allowBlank:false,
			                                fieldLabel:'Status',name:'status',displayField:'dicName',valueField:'dicCode',
			                                store:Ext.create('Ext.data.Store', 
			                                {fields : ['dicCode', 'dicName'],data:UnieapDicdata.TaskStatus})
										}
					                ]
					            },
	                        	{
					                xtype:'fieldcontainer',
					                fieldLabel:'Start Time',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                defaults: {
					                    flex: 1,
					                    hideLabel: true
					                },
					                items: [
					                    {xtype:'datefield',name:'startDate',fieldLabel: 'Start',format:'Y-m-d',width: 80,margin: '0 5 0 0',allowBlank: false,
					                    	listeners: {
								                'blur':function(field){
								                	if(field.getValue()!=''){
									                	dataForm.getForm().findField('endDate').setMinValue(field.getValue());
								                	}
								                }
								            }
					                    },
					                    {xtype: 'timefield',name: 'startTime',minValue: '00:00:00',format:' H:i:s',width: 80,maxValue: '24:00:00',allowBlank: true,
					                    	listeners: {
								                'select':function (combo, record){
								                	if(combo.getValue()!=''){
									                	dataForm.getForm().findField('endTime').setMinValue(combo.getValue());
								                	}
								                }
								            }
								        }
					                ]
					            },
	                        	{
					                xtype:'fieldcontainer',
					                fieldLabel:'End Time',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                defaults: {
					                    flex: 1,
					                    hideLabel: true
					                },
					                items: [
					                    {xtype:'datefield',name:'endDate',fieldLabel: 'Start',format:'Y-m-d',width: 80,margin: '0 5 0 0',allowBlank: false,
					                    	listeners: {
								                'blur':function(field){
								                	if(field.getValue()!=''){
								                		dataForm.getForm().findField('startDate').setMaxValue(field.getValue());
								                	}
								                }
								            }
					                    },
					                    {xtype: 'timefield',name: 'endTime',minValue:'00:00:00',format:' H:i:s',width: 80,maxValue:'24:00:00',allowBlank: true,
					                    	listeners: {
								                'select':function (combo, record){
								                	if(combo.getValue()!=''){
								                		dataForm.getForm().findField('startTime').setMaxValue(combo.getValue());
								                	}
								                }
								            }
					                    }
					                ]
					            },
	                        	{
					                xtype:'fieldcontainer',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                items: [
					                    {xtype:'numberfield',name:'completePer', fieldLabel:'Complete Per',minValue: 0,maxValue:100, allowBlank:true}
					                ]
					            },
	                        	{
					                xtype:'fieldcontainer',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                items: [
					                    { xtype:'combo', forceSelection: true,emptyText: 'Select an Option', editable:false, allowBlank:false,
			                                fieldLabel:'Main Task',name:'isMain',displayField:'dicName',valueField:'dicCode',
			                                store:Ext.create('Ext.data.Store', 
			                                {fields : ['dicCode', 'dicName'],data:UnieapDicdata.IsMainTask})
										},
										{ xtype:'combo', forceSelection: true,emptyText: 'Select an Option', editable:false, allowBlank:false,
			                                fieldLabel:'Show Task',name:'isShow',displayField:'dicName',valueField:'dicCode',
			                                store:Ext.create('Ext.data.Store', 
			                                {fields : ['dicCode', 'dicName'],data:UnieapDicdata.IsShowTask})
										}
					                ]
					            }
	                        ]
	                    },
	                    {xtype:'fieldset',title:'Extend Info',
	                        items:
	                        [
	                        	{
					                xtype:'fieldcontainer',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                items: [
										{ xtype:'textareafield', name:'remark',fieldLabel:'Remark',width: '100%',growMin:60,growMax:100,allowBlank:true}
					                ]
					            }
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
	                                     params:{'operType': operType},
	                                     url: 'MonitorController.do?method=taskDeal',
	                                     success: function(form, action) {
	                                     	//var task = Ext.JSON.decode(Ext.JSON.decode(action.response.responseText).task);
	                                     	//dataForm.getForm().setValues(task);
	                                     	//gridstore.reload();
	                                     	var result = Ext.JSON.decode(action.response.responseText);
	                                     	if(operType=='Add'){
	                                     		if(selectedNode.isLeaf()==true)
		                                     	{
										        	selectedNode.data.leaf=false;
										        	selectedNode.data.expanded = true;
									        	}
		                                     	selectedNode.appendChild(Ext.JSON.decode(result.taskData));
	                                     	} 
	                                     	if(operType=='Modify'){
	                                     		selectedNode.updateInfo(true,Ext.JSON.decode(result.taskData));
	                                     	}
	                                    	Ext.MessageBox.alert('Status', 'Save successfully.',showResult);
	                                     },
	                                     failure: function(form, action) {
	                                    	Ext.MessageBox.alert('Status', 'Save failed, please retry.',showResult);
	                                     }
	                                 });
	                        	 }
	                        }
	                    }
                    ]
                });
                dataWin = Ext.widget('window', 
                { title: 'Data', closeAction: 'hide', width:550, height:360, layout: 'fit', modal: true, 
                items:dataForm,defaultFocus: 'taskName' });
            }
            if(operType=='Add')
            {
            	dataForm.getForm().reset();
            	dataWin.show();
            	dataForm.getForm().findField('parentId').setValue(selectedNode.get('id'));
            	dataForm.getForm().findField('isLeaf').setValue('Y');
            	dataForm.getForm().findField('completePer').setValue(0);
            	dataForm.getForm().findField('startDate').setMinValue(selectedNode.get('startDate'));
            	dataForm.getForm().findField('startDate').setMaxValue(selectedNode.get('endDate'));
            	dataForm.getForm().findField('endDate').setMaxValue(selectedNode.get('endDate'));
            	dataForm.getForm().findField('endDate').setMinValue(selectedNode.get('startDate'));
            	dataForm.getForm().findField('startTime').setMaxValue('24:00:00');
            	dataForm.getForm().findField('endTime').setMaxValue('24:00:00');
            }else if(operType=='Modify')
            {
            	dataWin.show();
            	dataForm.getForm().setValues(selectedNode.data);
            	dataForm.getForm().findField('startDate').setMinValue(parentNode.get('startDate'));
            	dataForm.getForm().findField('startDate').setMaxValue(parentNode.get('endDate'));
            	dataForm.getForm().findField('endDate').setMaxValue(parentNode.get('endDate'));
            	dataForm.getForm().findField('endDate').setMinValue(parentNode.get('startDate'));
            }else
            {
            	dataWin.show();
            }
        }
        function removeDatas(btn)
        {
        	if(btn=='yes'){
	        	var selectedNode = taskTreePanel.getSelectionModel().selected.items[0];
	        	Ext.Ajax.request({
	                url: 'MonitorController.do?method=taskDeal',
	                params:{'operType':"Delete",'taskId':selectedNode.get('taskId'),'parentId':selectedNode.get('parentId')},
	                success: function(response, opts) 
	                {
	                	Ext.MessageBox.alert('Status', 'Remove data successfully.');
						var parentNode = selectedNode.parentNode;
	                	selectedNode.remove();
						if(!parentNode.hasChildNodes()){
							parentNode.updateInfo(true,{leaf:true});
                        }
	                },
	                failure: function(response, opts) 
	                {
	                	Ext.MessageBox.alert('Status', 'Remove data failed.Error:'+response.status);
	                }
	             });
	        	return true;
        	}else{
        		return false;
        	}
        }
        function showResult(btn)
        {
        	dataWin.hide();
        };
        var dataDisplayForm = Ext.create('Ext.form.Panel',
       	{
             fieldDefaults:{labelAlign:'left',labelWidth:90,anchor:'100%'},
             title:'Task Detail Info',width:570,
             items:
             [
             	        {xtype:'hiddenfield', name:'taskId'},
                  		{xtype:'hiddenfield', name:'beId'},
                  		{xtype:'hiddenfield', name:'squNum'},
                  		{xtype:'hiddenfield', name:'createBy'},
                  		{xtype:'hiddenfield', name:'modifyBy'},
                  		{xtype:'hiddenfield', name:'createDatetime'},
                  		{xtype:'hiddenfield', name:'modifyDatetime'},
                  		{xtype:'hiddenfield', name:'taskId'},
                  		{
			                xtype:'fieldcontainer',
			                combineErrors: true,
			                msgTarget :'side',
			                layout: 'hbox',
			                items: [
								{xtype:'textfield', name:'taskName',fieldLabel:'Task Name',readOnly :true, cls:'textfield-red'},
								{xtype:'textfield', name:'taskNameRemark',fieldLabel:'Long Name',readOnly :true}
			                ]
			            },{
			                xtype:'fieldcontainer',
			                combineErrors: true,
			                msgTarget :'side',
			                layout: 'hbox',
			                items: [
								{ xtype:'combo', forceSelection: true, emptyText: 'Select an Option',readOnly :true, editable:false,allowBlank:false,
	                                fieldLabel:'Task Group',name:'taskGroup',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata.TaskGroup})
								},
								{ xtype:'combo', forceSelection: true,emptyText: 'Select an Option',readOnly :true, editable:false, allowBlank:false,
	                                fieldLabel:'Status',name:'status',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                {fields : ['dicCode', 'dicName'],data:UnieapDicdata.TaskStatus})
								}
			                ]
			            },{
			                xtype:'fieldcontainer',
			                combineErrors: true,
			                msgTarget :'side',
			                layout: 'hbox',
			                items: [
								{xtype:'textfield', name:'startDate',fieldLabel:'Start Date',readOnly :true},
								{xtype:'textfield', name:'startTime',fieldLabel:'Start Time',readOnly :true}
			                ]
			            },{
			                xtype:'fieldcontainer',
			                combineErrors: true,
			                msgTarget :'side',
			                layout: 'hbox',
			                items: [
								{xtype:'textfield', name:'endDate',fieldLabel:'End Date',readOnly :true},
								{xtype:'textfield', name:'endTime',fieldLabel:'End Time',readOnly :true}
			                ]
			            },{
			                xtype:'fieldcontainer',
			                combineErrors: true,
			                msgTarget :'side',
			                layout: 'hbox',
			                items: [
								{xtype:'textfield', name:'completePer',fieldLabel:'Complete Per',readOnly :true}
			                ]
			            },{
			                xtype:'fieldcontainer',
			                combineErrors: true,
			                msgTarget :'side',
			                layout: 'hbox',
			                items: 
			                [
			                	{ xtype:'combo', forceSelection: true,emptyText: 'Select an Option',readOnly :true, editable:false, allowBlank:false,
	                                fieldLabel:'Main Task',name:'isMain',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                {fields : ['dicCode', 'dicName'],data:UnieapDicdata.IsMainTask})
								},
								{ xtype:'combo', forceSelection: true,emptyText: 'Select an Option',readOnly :true, editable:false, allowBlank:false,
	                                fieldLabel:'Show Task',name:'isShow',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                {fields : ['dicCode', 'dicName'],data:UnieapDicdata.IsShowTask})
								}
			                ]
			            },{
			                xtype:'fieldcontainer',
			                combineErrors: true,
			                msgTarget :'side',
			                layout: 'hbox',
			                items: [
								{xtype:'textareafield', name:'remark',fieldLabel:'Remark',width:'100%',readOnly :true}
			                ]
			            }
            ]
         });
         Ext.define('dataResourceModel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'resourceId','taskId','resourceName','resourceType','resourceTypeDesc','digiResource',
            	'thirdPartyResource','hwresource','createBy','createDatetime','modifyBy','modifyDatetime',
            	'activeFlag','activeFlagDesc','remark','beId'
            ],
            idProperty: 'resourceId'
        });
        var resourcegridstore = Ext.create('Ext.data.Store', {
            model: 'dataResourceModel',
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
        resourcegridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryResourcePara);
        });
        var operationItems = [];
        var selectedRecord;
       	if(UnieapButton.TaskResource_Modify!=null&&UnieapButton.TaskResource_Modify.abled== true)
       	{
        	operationItems.push({
        	   icon   : '/Unieap/unieap/js/common/images/edit.png',
               tooltip: 'Edit',
               handler:function(grid, rowIndex, colIndex)
               {	
               		selectedRecord = grid.getStore().getAt(rowIndex);
            		showResourceForm('Modify',selectedRecord);
            		
               }
           });
           operationItems.push({iconCls :'',tooltip:''});
       	}
       	if(UnieapButton.TaskResource_Delete!=null&&UnieapButton.TaskResource_Delete.abled== true)
       	{
        	operationItems.push({
        	   icon   : '/Unieap/unieap/js/common/images/delete.png',
               tooltip: 'Delete',
               handler:function(grid, rowIndex, colIndex)
               {	
            	    selectedRecord = grid.getStore().getAt(rowIndex);
               		Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?', removeResourceDatas);
               }
           });
           operationItems.push({iconCls :'',tooltip:''});
       	};
        var selModel = Ext.create('Ext.selection.CheckboxModel');
        var dataResourceGrid = Ext.create('Ext.grid.Panel', 
        {width:570,height:280,columnLines: true,selModel:selModel,title:'Task Resource List',
   	   		store : resourcegridstore,
	   	   	columns:
	   	   	[
	   	   		{text: "Resource Name", dataIndex: 'resourceName',sortable: true},
	   	   		{text: "Resource Type",dataIndex: 'resourceTypeDesc',sortable: false},
	   	   		{text: "DiGi Resource",width: 130, dataIndex: 'digiResource', sortable: true},
	   	   		{text: "3PP Resource",width: 130, dataIndex: 'thirdPartyResource', sortable: true},
	   	   		{text: "Huawei Resource",width: 130, dataIndex: 'hwresource', sortable: true},
	   	   		{text: "Remark", dataIndex: 'remark',sortable: false},
	   	   		{menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "Operation",width:100,items:operationItems}
	   	   	],
	        tbar:[{pressed :true,icon:'/Unieap/unieap/js/common/images/add.png',
               		tooltip:'Add',text:'Add',xtype:'button',id:'TaskResource_Add',
		            handler : function(){showResourceForm('Add',null);}
		    }],
		    listeners:
	        {afterRender:function(thisForm, options){
		        	if(UnieapButton.TaskResource_Add!=null&&UnieapButton.TaskResource_Add.disabled== true){Ext.getCmp('TaskResource_Add').hide();}}
	        },
    	   	bbar:new Ext.PagingToolbar(
    	   	{ store:resourcegridstore,displayInfo: true, displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})
        	
        });
        var dataResourceWin = null;
	    var dataResourceForm = null;
        var operResourceType = '';
        function showResourceForm(status,selectedRecord){
        	operResourceType = status;
            if (dataResourceWin==null){
            	dataResourceForm = Ext.widget('form',
            	{
                    defaults:{labelAlign: 'left', labelWidth: 90, anchor: '100%'},
                    bodyPadding:5,
                    items:
                    [
                    	{xtype:'fieldset', title:'Basic Info',
	                        items:
	                        [
	                        	{ xtype:'hiddenfield', name:'resourceId'},
	                        	{ xtype:'hiddenfield', name:'taskId'},
	                        	{ xtype:'hiddenfield', name:'beId'},
	                        	{ xtype:'hiddenfield', name:'createBy'},
	                        	{ xtype:'hiddenfield', name:'modifyBy'},
	                        	{ xtype:'hiddenfield', name:'createDatetime'},
	                        	{ xtype:'hiddenfield', name:'modifyDatetime'},
	                        	{ xtype:'textfield', name:'resourceName',fieldLabel:'Resource Name',allowBlank:false},
	                        	{xtype:'combo', forceSelection: true,emptyText: 'Select an Option', editable:false, allowBlank:false,
	                                fieldLabel:'Resource Type',name:'resourceType',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                {fields : ['dicCode', 'dicName'],data:UnieapDicdata.TaskResourceType})
								},
	                        	{xtype:'textfield', name:'digiResource',fieldLabel:'DiGi Resource',growMin:60,growMax:100,allowBlank:true},
	                        	{xtype:'textfield', name:'thirdPartyResource',fieldLabel:'3PP Resource',growMin:60,growMax:100,allowBlank:true},
	                        	{xtype:'textfield', name:'hwresource',fieldLabel:'Huawei Resource',growMin:60,growMax:100,allowBlank:true},
	                        	{xtype:'combo', forceSelection: true, emptyText: 'Select an Option',editable:false,allowBlank:false,
	                                fieldLabel:'Active Flag',name:'activeFlag',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata.ActiveFlag})
								}
	                        ]
	                    },
	                    {xtype:'fieldset',title:'Extend Info',
	                        items:
	                        [
	                        	{xtype:'textareafield', name:'remark',fieldLabel:'Remark',growMin:60,growMax:100,allowBlank:true}
	                        ]
                    	}
                    ],
                    buttons: 
                    [
	                    {text: 'Cancel',
	                        handler: function() 
	                        {
	                        	dataResourceForm.getForm().reset();
	                        	dataResourceWin.hide();
	                        }
	                    }, 
	                    {text: 'Submit',
	                        handler: function() {
	                        	var form = dataResourceForm.getForm();
	                        	 if (form.isValid()){
	                                 form.submit({
	                                     clientValidation: true,
	                                     waitMsg: 'Processing...',
	                                     method: 'POST',
	                                     params:{'operType': operResourceType},
	                                     url: 'MonitorController.do?method=taskResourceDeal',
	                                     success: function(form, action) {
	                                    	Ext.MessageBox.alert('Status', 'Save successfully.',showResourceResult);
	                                     },
	                                     failure: function(form, action) {
	                                    	Ext.MessageBox.alert('Status', 'Save failed, please retry.',showResourceResult);
	                                     }
	                                 });
	                        	 }
	                        }
	                    }
                    ]
                });
                dataResourceWin = Ext.widget('window', 
                { title: 'Data', closeAction: 'hide', width:350, height:360, layout: 'fit', modal: true, 
                items:dataResourceForm,defaultFocus: 'resourceName' });
            }
            if(operResourceType=='Add')
            {
            	dataResourceForm.getForm().reset();
            	var taskId=dataDisplayForm.getForm().findField("taskId").getValue(); 
            	if(!taskId){
            		Ext.MessageBox.alert('Status', 'Please select Task First');
            	}else{
            		dataResourceForm.getForm().setValues({taskId:taskId});
            		dataResourceWin.show();
            	}
            }else if(operResourceType=='Modify')
            {
            	dataResourceWin.show();
            	dataResourceForm.getForm().setValues(selectedRecord.data);
            }else
            {
            	dataResourceWin.show();
            }
        }
        function showResourceResult(btn)
        {
        	dataResourceWin.hide();
        	resourcegridstore.reload();
        }
        function removeResourceDatas(btn)
        {
        	if(btn=='yes'){
	        	var resourceId= selectedRecord.get("resourceId");
	        	Ext.Ajax.request({
	                url: 'MonitorController.do?method=taskResourceDeal',
	                params:{'operType':"Delete","resourceId":resourceId},
	                success: function(response, opts) 
	                {
	                	Ext.MessageBox.alert('Status', 'Remove data successfully.');
	                	resourcegridstore.remove(selectedRecord);
	                },
	                failure: function(response, opts) 
	                {
	                	Ext.MessageBox.alert('Status', 'Remove data failed.Error:'+response.status);
	                }
	             });
        	}
        }
        function checkTimeDuration(parentNode,starTime,endTime)
        {
        	//var parentSd = new Date(parentNode.starDate+parentNode.startTime);
        	var parentSd = Date.parse(new Date(parentNode.starDate+parentNode.startTime));
        	//var parentEd = new Date(parentNode.endDate+parentNode.endTime);
        	var parentEd = Date.parse(new Date(parentNode.endDate+parentNode.endTime));
        	var sd = Date.parse(new Date(starTime));
        	var ed = Date.parse(new Date(endTime));
        	if(sd<parentSd||ed>parentEd||sd>parentEd||ed<parentSd)
        	{
        		Ext.MessageBox.alert('Status','Task start time and end time must in parent task time duration');
        	}
        }
        Ext.create('Ext.Viewport', {
	        layout:'column',
	        bodyStyle: 'padding:5px',
            defaults: {bodyStyle:'padding:15px'},
	        items: [{
                title:'Task List',
                columnWidth: 0.25,
                items:[taskTreePanel]
            },{
                title: 'Detail Info',
                columnWidth: 0.75,
                items:[dataDisplayForm,dataResourceGrid]
            }]
	    });
    });
    </script>
</head>
<body>
    <div id="tree-div" style='width:100%;height:35;font-size:0;'></div>
	<div id="datagrid" style='width:100%;height:100%;'></div>
	<div id="resourcegrid" style='width:100%;height:100%;'></div>
	<div id="tabs" style='width:100%;height:100%;'></div>
</body>
</html>
