<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Role</title>
    <script type="text/javascript">
    Ext.onReady(function(){
    	Ext.tip.QuickTipManager.init();
    	var queryPara;
		var queryform = Ext.create('Ext.form.Panel',{
			fieldDefaults:
			{ labelAlign: 'left', labelWidth: 90 },
	        layout: 'fit',
	        width : '100%',
     	   	frame : true,
     	   	el:'queryform',
     	   	tbar:[{ pressed :true,icon:'/Unieap/unieap/js/common/images/add.png',
               		tooltip:'Add',text:'Add',xtype:'button',id:'Role_Add',
		            handler : function(){showForm('Add',null);}
		    }],
	        listeners:
	        {afterRender:function(thisForm, options){
		        	if(UnieapButton.Role_Add==null||UnieapButton.Role_Add.abled==false){Ext.getCmp('Role_Add').hide();}}
	        },
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
        gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
        var operationItems = [];
        var selectedRecord;
       	if(UnieapButton.Role_Modify!=null&&UnieapButton.Role_Modify.abled== true)
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
       	if(UnieapButton.Role_Delete!=null&&UnieapButton.Role_Delete.abled== true)
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
       	if(UnieapButton.Role_Resource!=null&&UnieapButton.Role_Resource.abled== true)
       	{
        	operationItems.push({
        	   icon   : '/Unieap/unieap/js/common/images/grid.png',
               tooltip: 'Resource Data',
               handler:function(grid, rowIndex, colIndex)
               {	
            	    selectedRecord = grid.getStore().getAt(rowIndex);
               		var roleId= selectedRecord.get("roleId");
               		var windowName = selectedRecord.get("roleName");
               		showResourceData(roleId,windowName);
               }
           });
           operationItems.push({iconCls :'',tooltip:''});
       	};
       	var selModel = Ext.create('Ext.selection.CheckboxModel',{'singleSelect':true});
        var datagrid = Ext.create('Ext.grid.Panel', 
        {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
	   	 	selModel:selModel,
   	   		store : gridstore,
	   	   	columns:
	   	   	[
	   	   		{ text: "Role Code",width:90, dataIndex: 'roleCode',sortable: true},
	   	   		{ text: "Role Name",width:90, dataIndex: 'roleName', sortable: true},
	   	   		{ text: "Active Flag",width:60,dataIndex: 'activeFlagDesc',sortable: false},
	   	   		{ text: "Create Datetime",width: 120, dataIndex: 'createDatetime', sortable: true},
	   	   		{ text: "Modify Datetime",width: 120, dataIndex: 'modifyDatetime', sortable: true},
	   	   		{ text: "Remark", dataIndex: 'remark',sortable: false},
	   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "Operation",width:150,items:operationItems}
	   	   	],
	        tbar:[queryform],
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
                    	{xtype:'fieldset', title:'Role main info',
	                        items:
	                        [
	                        	{ xtype:'hiddenfield', name:'roleId'},
	                        	{ xtype:'hiddenfield', name:'beId'},
	                        	{ xtype:'hiddenfield', name:'createBy'},
	                        	{ xtype:'hiddenfield', name:'modifyBy'},
	                        	{ xtype:'hiddenfield', name:'createDatetime'},
	                        	{ xtype:'hiddenfield', name:'modifyDatetime'},
	                        	{ xtype:'textfield', name:'roleCode',fieldLabel:'Role Code',allowBlank:false,
	                        		validateOnChange:false, validateOnBlur :true,
								    validator :function(value)
								    	{
								    			var error = true; 
								    			if(operType == 'Modify' || value==''||value ==null)
								    			{
								    				return true;
								    			}
								    		Ext.Ajax.request({
								                url: 'MdMController.do?method=roleDeal',
								                async:false,
								                params:{'operType':"checkExist","roleCode":value},
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
								                	error='Check Role Code exist failed,error message:'+response.responseText;
								                }
								             });
								             return error;
								    	}
	                        	},
	                        	{ xtype:'textfield',name:'roleName', fieldLabel:'Role Name', allowBlank:false},
	                        	{ xtype:'combo', forceSelection: true, emptyText: 'Select an Option',editable:false,allowBlank:false,
	                                fieldLabel:     'Active Flag',name:'activeFlag', displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata.ActiveFlag})
								},
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
	                                     url: 'MdMController.do?method=roleDeal',
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
                { title: 'Data', closeAction: 'hide', width: 400, height: 400, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'roleCode' });
            }
            if(operType=='Add')
            {
            	dataForm.getForm().reset();
            	dataWin.show();
            	dataForm.getForm().findField('roleCode').setReadOnly(false);
            }else if(operType=='Modify')
            {
            	dataWin.show();
            	dataForm.getForm().setValues(selectedRecord.data);
            	dataForm.getForm().findField('roleCode').setReadOnly(true);
            }else
            {
            	dataWin.show();
            }
        }
        function removeDatas(btn)
        {
        	if(btn=='yes'){
	        	var roleId= selectedRecord.get("roleId");
	        	Ext.Ajax.request({
	                url: 'MdMController.do?method=roleDeal',
	                params:{'operType':"Delete","roleId":roleId},
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
        	renderTo:'resdataTab',activeTab: 0,layout: 'fit', bodyStyle: 'padding:5px',
            items:[{xtype: 'panel', title: 'Role List',items : [datagrid], height: 520, closable: false}]
        });
        function showResourceData(roleId,windowName)
        {
       		addTab (true,roleId,'MdMController.do?method=roleResdataList',windowName,null);
        }
        function addTab (closable,id,href,text,imgSrc)
        {
	    	var url = basePathUrl+"/"+href+'&roleId='+id;
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
		    			//tab.loader.load();
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
    })
    </script>
</head>
<body>
	<div id="queryform" style='width:100%;height:35;font-size:0;'></div>
	<div id="datagrid" style='width:100%;height:100%;'></div>
	<div id="resdataTab" style='width:100%;height:100%;'></div>
	<div id="gridresdata" style='width:100%;height:100%;'></div>
	<div id="treeresdata" style='width:100%;height:100%;'></div>
</body>
</html>
