<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Task Owner</title>
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
               		tooltip:'Add',text:'Add',xtype:'button',id:'TaskOwner_Add',
		            handler : function(){showForm('Add',null);}
		    }],
	        listeners:
	        {afterRender:function(thisForm, options){
		        	if(UnieapButton.TaskOwner_Add!=null&&UnieapButton.TaskOwner_Add.disabled== true){Ext.getCmp('TaskOwner_Add').hide();}}
	        },
     	    items:[
     	           { id:'queryFieldset', width: '100%',xtype:'fieldset',title: 'Search',
  	                 startCollapsed: true,collapsible: true, defaultType: 'textfield',
  	                	items:
  	                		[
								{ xtype: 'fieldcontainer',layout: 'hbox',
								    items: 
								    [
								        { xtype:'textfield',name: 'ownerName',fieldLabel: 'Owner Name'},
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
					    	                	var ownerName=queryform.getForm().findField("ownerName").getValue(); 
					    	                    var createDatetime=dateFormat(queryform.getForm().findField("createDatetime").getValue()); 
					    	                    var modifyDatetime=dateFormat(queryform.getForm().findField("modifyDatetime").getValue()); 
					    	                    var activeFlag=queryform.getForm().findField("activeFlag").getValue();
					    	                    queryPara = 
					    	                    	{
					    	                         	ownerName:ownerName,
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
            	'taskOwnerId','ownerName','createBy','createDatetime','modifyBy','modifyDatetime','activeFlag','activeFlagDesc','remark','beId'
            ],
            idProperty: 'taskOwnerId'
        });
        var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            remoteSort: true,
            proxy: 
            { type: 'ajax', url: 'MonitorController.do?method=taskOwnerGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: 
            [
            	{ property: 'ownerName', direction: 'DESC'}
            ]
        });
        gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
        var operationItems = [];
        var selectedRecord;
       	if(UnieapButton.TaskOwner_Modify!=null&&UnieapButton.TaskOwner_Modify.abled== true)
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
       	if(UnieapButton.TaskOwner_Delete!=null&&UnieapButton.TaskOwner_Delete.abled== true)
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
	   	   		{ text: "Owner Name", dataIndex: 'ownerName',sortable: true},
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
	                        	{ xtype:'hiddenfield', name:'taskOwnerId'},
	                        	{ xtype:'hiddenfield', name:'beId'},
	                        	{ xtype:'hiddenfield', name:'createBy'},
	                        	{ xtype:'hiddenfield', name:'modifyBy'},
	                        	{ xtype:'hiddenfield', name:'createDatetime'},
	                        	{ xtype:'hiddenfield', name:'modifyDatetime'},
	                        	{ xtype:'textfield', name:'ownerName',fieldLabel:'Owner Name',allowBlank:false,
	                        		validateOnChange:false, validateOnBlur :true,
								    validator :function(value)
								    	{
								    			var error = true; 
								    			if(operType == 'Modify' || value==''||value ==null)
								    			{
								    				return true;
								    			}
									    		Ext.Ajax.request({
									                url: 'MonitorController.do?method=taskOwnerDeal',
									                async:false,
									                params:{'operType':"checkExist","ownerName":value},
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
									                	error='Check Task Owner exist failed,error message:'+response.responseText;
									                }
								             });
								             return error;
								    	}
	                        	},
	                        	{ xtype:'combo', forceSelection: true, emptyText: 'Select an Option',editable:false,allowBlank:false,
	                                fieldLabel:'Active Flag',name:'activeFlag',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata.ActiveFlag})
								}
	                        ]
	                    },
	                    {xtype:'fieldset',title:'Detail Info',
	                        items:
	                        [
		                        { xtype:'textareafield', name:'remark',fieldLabel:'Remark',growMin:60,growMax:100,allowBlank:true}
	                        ]
                    	}
                    ],
                    buttons: 
                    [
	                    {id:'formCancel', text: 'Cancel',
	                        handler: function() 
	                        {
	                        	dataForm.getForm().reset();
	                        	dataWin.hide();
	                        }
	                    }, 
	                    {id:'formSubmit',text: 'Submit',
	                        handler: function() {
	                        	var form = dataForm.getForm();
	                        	 if (form.isValid()){
	                                 form.submit({
	                                     clientValidation: true,
	                                     waitMsg: 'Processing...',
	                                     method: 'POST',
	                                     params:{'operType':operType},
	                                     url: 'MonitorController.do?method=taskOwnerDeal',
	                                     success: function(form, action) {
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
                { title: 'Data', closeAction: 'hide', width: 400, height: 400, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'ownerName' });
            }
            if(operType=='Add')
            {
            	dataForm.getForm().reset();
            	dataWin.show();
            	dataForm.getForm().findField('ownerName').setReadOnly(false);
            }else if(operType=='Modify')
            {
            	dataWin.show();
            	dataForm.getForm().setValues(selectedRecord.data);
            	dataForm.getForm().findField('ownerName').setReadOnly(true);
            }else
            {
            	dataWin.show();
            }
        }
        function removeDatas(btn)
        {
        	if(btn=='yes'){
	        	var taskOwnerId= selectedRecord.get("taskOwnerId");
	        	Ext.Ajax.request({
	                url: 'MonitorController.do?method=taskOwnerDeal',
	                params:{'operType':"Delete","taskOwnerId":taskOwnerId},
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
</body>
</html>
