<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<script type="text/javascript">
	var taskId = "<%=request.getParameter("taskId")%>";
</script>
<title>Resource Data</title>
    <script type="text/javascript">
    Ext.onReady(function(){
        Ext.tip.QuickTipManager.init();
        Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
                'resourceId','taskId','resourceName','resourceType','resourceTypeDesc','digiResource','thirdPartyResource','hwresource',
                'createDatetime','modifyDatetime','activeFlag','activeFlagDesc','remark','beId','createBy','modifyBy'
            ],
            idProperty: 'resourceId'
        });
        var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            remoteSort: true,
            proxy: {
                type: 'ajax',
                url: 'MonitorController.do?method=taskResourceGrid',
                reader: {root: 'rows',totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{property: 'resourceId',direction: 'DESC'}]
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
            		showForm('Modify',selectedRecord);
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
               		Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?', removeDatas);
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
	   	   		{ text: "Resource Name",width:90, dataIndex: 'resourceName',sortable: true},
	   	   		{ text: "Resource Type",width:90, dataIndex: 'resourceTypeDesc', sortable: true},
	   	   		{ text: "DiGi Resource",width:60,dataIndex: 'digiResource',sortable: false},
	   	   		{ text: "3PP Resource",width:60,dataIndex: 'thirdPartyResource',sortable: false},
	   	   		{ text: "Huawei Resource",width:60,dataIndex: 'hwresource',sortable: false},
	   	   		{ text: "Remark", dataIndex: 'remark',sortable: false},
	   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "Operation",width:150,items:operationItems}
	   	   	],
	        tbar:[{ pressed :true,icon:'/Unieap/unieap/js/common/images/add.png',
               		tooltip:'Add',text:'Add',xtype:'button',id:'TaskResource_Add',
		            handler : function()
		            	{
		            		showForm('Add',null);
		            	}
		    }],
		    listeners:
	        {afterRender:function(thisForm, options){
		        	if(UnieapButton.TaskResource_Add!=null&&UnieapButton.TaskResource_Add.disabled== true)
		        		{
		        			Ext.getCmp('TaskResource_Add').hide();
		        		}
		        	}
	        },
    	   	bbar:new Ext.PagingToolbar(
    	   	{ store : gridstore,displayInfo: true, displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})
        	
        });
        datagrid.render();
        gridstore.loadPage(1);
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
	                                     url: 'MonitorController.do?method=taskResourceDeal',
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
                { title: 'Data', closeAction: 'hide', width: 400, height: 400, layout: 'fit', modal: true, 
                items:dataForm,defaultFocus: 'resourceName' });
            }
            if(operType=='Add')
            {
            	dataForm.getForm().reset();
            	dataForm.getForm().setValues({taskId:taskId});
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
	        	var resourceId= selectedRecord.get("resourceId");
	        	Ext.Ajax.request({
	                url: 'MonitorController.do?method=taskResourceDeal',
	                params:{'operType':"Delete","resourceId":resourceId},
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
	<div id="datagrid" style='width:100%;height:100%;'></div>
</body>
</html>
