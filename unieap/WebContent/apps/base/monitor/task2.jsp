<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>User</title>
    <script type="text/javascript">
    Ext.onReady(function(){
        Ext.tip.QuickTipManager.init();
        /***query form**********************************/
        var existTasksstore = Ext.create('Ext.data.Store',{
            fields : ['dicCode', 'dicName'],
            autoLoad: true,remoteSort: true,
            proxy:{
                type: 'ajax',url: 'MonitorController.do?method=existTasksDic',
                reader:{type: 'json',root: 'rows', totalProperty: 'totalCount'}
            },
            sorters:[{ property: 'dicName', direction: 'DESC'}]
        });
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
					    	                    var taskOwnerId=queryform.getForm().findField("taskOwnerId").getValue();
					    	                    var createDatetime=dateFormat(queryform.getForm().findField("createDatetime").getValue()); 
					    	                    var modifyDatetime=dateFormat(queryform.getForm().findField("modifyDatetime").getValue()); 
					    	                    var activeFlag=queryform.getForm().findField("activeFlag").getValue();
					    	                    queryPara = 
					    	                    	{
					    	                         	taskName:taskName,
					    	                         	taskOwnerId:taskOwnerId,
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
            	'taskId','taskName','taskNameRemark','taskGroup','startDate','startTime','endDate','endTime',
            	'duration','status','isMain','isMainDesc','isShow','isShowDesc','squNum','statusDesc','completePer',
            	'createBy','createDatetime','modifyBy','modifyDatetime','activeFlag','activeFlagDesc',
            	'remark','beId'
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
       	};
       	if(UnieapButton.Task_Resource!=null&&UnieapButton.Task_Resource.abled== true)
       	{
        	operationItems.push({
        	   icon   : '/Unieap/unieap/js/common/images/grid.png',
               tooltip: 'Resource Data',
               handler:function(grid, rowIndex, colIndex)
               {	
            	    selectedRecord = grid.getStore().getAt(rowIndex);
               		var taskId= selectedRecord.get("taskId");
               		var windowName = selectedRecord.get("taskName");
               		showResourceData(taskId,windowName);
               }
           });
           operationItems.push({iconCls :'',tooltip:''});
       	};
        var selModel = Ext.create('Ext.selection.CheckboxModel');
        var datagrid = Ext.create('Ext.grid.Panel', 
        {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
	   	 	selModel:selModel,
   	   		store : gridstore,
	   	   	columns:
	   	   	[
	   	   		{ text: "Task Name", dataIndex: 'taskName', sortable: true},
	   	   		{ text: "Status", dataIndex: 'statusDesc', sortable: true},
	   	   		{ text: "Task Group", dataIndex: 'taskGroup', sortable: true},
	   	   		{ text: "Start Date",width: 130, dataIndex: 'startDate', sortable: true},
	   	   		{ text: "End Date",width: 130, dataIndex: 'endDate', sortable: true},
	   	   		{ text: "Remark", dataIndex: 'remark',sortable: false},
	   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "Operation",width:100,items:operationItems}
	   	   	],
	        tbar:[queryform],
    	   	bbar:new Ext.PagingToolbar(
    	   	{ store : gridstore,displayInfo: true, displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})
        	
        });
        datagrid.render();
        gridstore.loadPage(1);
        
        /***function******************************************/
        var dataWin = null;
        var dataForm = null;
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
                    	{xtype:'fieldset', title:'Basic Info',
	                        items:
	                        [
	                        	{ xtype:'hiddenfield', name:'taskId'},
	                        	{ xtype:'hiddenfield', name:'beId'},
	                        	{ xtype:'hiddenfield', name:'squNum'},
	                        	{ xtype:'hiddenfield', name:'createBy'},
	                        	{ xtype:'hiddenfield', name:'modifyBy'},
	                        	{ xtype:'hiddenfield', name:'createDatetime'},
	                        	{ xtype:'hiddenfield', name:'modifyDatetime'},
	                        	{ xtype:'textfield', name:'taskName',fieldLabel:'Task Name',allowBlank:false},
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
					                    { xtype:'datefield',name:'startDate',fieldLabel: 'Start',width: 80,margin: '0 5 0 0',allowBlank: false},
					                    {xtype: 'timefield',name: 'startTime',minValue: '00:00 AM',width: 80,maxValue: '24:00 PM',allowBlank: true}
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
					                    {xtype:'datefield',name:'endDate',fieldLabel: 'Start',width: 80,margin: '0 5 0 0',allowBlank: false},
					                    {xtype: 'timefield',name: 'endTime',minValue: '00:00 AM',width: 80,maxValue: '24:00 PM',allowBlank: true}
					                ]
					            },
	                        	{
					                xtype:'fieldcontainer',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                items: [
					                    { xtype:'textfield',name:'completePer', fieldLabel:'Complete Percent', allowBlank:true},
										{xtype:'combo', forceSelection: true, emptyText: 'Select an Option',editable:false,allowBlank:false,
			                                fieldLabel:'Active Flag',name:'activeFlag',displayField:'dicName',valueField:'dicCode',
			                                store:Ext.create('Ext.data.Store', 
			                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata.ActiveFlag})
										}
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
                { title: 'Data', closeAction: 'hide', width: 600, height: 530, layout: 'fit', modal: true, 
                items:dataForm,defaultFocus: 'taskName' });
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
         var resdataTabPanel = Ext.create('Ext.tab.Panel',{
        	renderTo:'tabs',activeTab: 0,layout: 'fit', bodyStyle: 'padding:5px',
            items:[{xtype: 'panel', title: 'Task List',items : [datagrid], height: 520, closable: false}]
        });
        function showResourceData(taskId,windowName)
        {
       		addTab (true,taskId,'MonitorController.do?method=taskResourceList',windowName,null);
        }
        function addTab (closable,id,href,text,imgSrc)
        {
	    	var url = basePathUrl+"/"+href+'&taskId='+id;
	    	var windowName = text;
	    	var imgSrc = imgSrc;
	    	var tabItems = resdataTabPanel.items.items;
	    	if(tabItems.length == 0){
	    		resdataTabPanel.add({
			        	title:windowName,
			            xtype: 'panel',
			            html :'<iframe scrolling="no" frameborder="0" border="0" frameBorder="0" marginwidth="0" marginheight="0" src="'+url+'" style="width:100%;height:100%;"></iframe>',
			        	height: 520,
			        	closable: !!closable
			        }).show();
		        
	    	}else{
	    		var isExist = false;
		    	Ext.each(tabItems,function(tab){
		    		if(tab.title == windowName){
		    			isExist = true;
		    			tab.show();
		    		}
		    	});
		    	if(!isExist){
			        resdataTabPanel.add({
			        	title:windowName,
			            xtype: 'panel',
			            html :'<iframe scrolling="no" frameborder="0" border="0" frameBorder="0" marginwidth="0" marginheight="0" src="'+url+'" style="width:100%;height:100%;"></iframe>',
			        	height: 520,
			        	closable: !!closable
			        }).show();
		    	}
	    	}
	    }
    });
    </script>
</head>
<body>
    <div id="queryform" style='width:100%;height:35;font-size:0;'></div>
	<div id="datagrid" style='width:100%;height:100%;'></div>
	<div id="resourcegrid" style='width:100%;height:100%;'></div>
	<div id="tabs" style='width:100%;height:100%;'></div>
</body>
</html>
