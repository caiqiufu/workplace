<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Role</title>
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
			tbar:
				[
			     {
			    	  	pressed : true,
	                	text:'Search',
	                	xtype:'button',
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
    	                    //add paramters to store, not be removed
    	                    gridstore.load({params:queryPara});
    	                }
	                },'-',{
	                	pressed : true,
	                	text:'Reset',
	                	xtype:'button',
    	                handler: function (){
    	                	queryform.getForm().reset(); 
    	                }
	                },'-',{
	                	pressed : true,
	                	text:'Resource Data',
	                	xtype:'button',
    	                handler: function (){
    	                	var rec = datagrid.getSelectionModel().getSelection();
	                		if(rec.length==0){
	                			Ext.MessageBox.alert('Status', 'Please select one row data.');
	                		}else{
	                			var roleId= rec[0].get("roleId");
	                			var windowName= 'Resource Data';
	                			showResourceData(roleId,windowName);
	                		}
    	                }
	                },'-',{
	                	pressed : true,
	                	text:'Assign Resource Data',
	                	xtype:'button',
    	                handler: function (){
    	                	var rec = datagrid.getSelectionModel().getSelection();
	                		if(rec.length==0){
	                			Ext.MessageBox.alert('Status', 'Please select one row data.');
	                		}else{
	                			var resDefId= rec[0].get("resDefId");
	                			var resDefName= rec[0].get("resDefName");
	                			var isTree= rec[0].get("isTree");
	                			showResourceData(resDefId,isTree,resDefName);
	                		}
    	                }
	                }
			     ],
			fieldDefaults:{
	            labelAlign: 'left',
	            buttonAlign : 'center',
	            labelWidth: 90
	        },
	        //button display control
	        listeners:
	        {
	        	afterRender:function(thisForm, options)
	        	{
		        	if(UnieapButton.Role_Add!=null&&UnieapButton.Role_Add.disabled== true)
		        	{
			        	Ext.getCmp('Role_Add').hide();
		        	}
		        	if(UnieapButton.Role_Modify!=null&&UnieapButton.Role_Modify.disabled== true)
		        	{
			        	Ext.getCmp('Role_Modify').hide();
		        	}
		        	if(UnieapButton.Role_Delete!=null&&UnieapButton.Role_Modify.disabled== true)
		        	{
			        	Ext.getCmp('Role_Delete').hide();
		        	}
	        	}
	        },
	        width:'100%',
     	   	bodyStyle : 'padding:0px;margin:0px;padding-top:5px;',//padding:1px;padding-top:5px;
     	   	border:0,
     	   	frame : true,
     	   	el:'queryform',
     	    items:[
     	           {
     	        	    id:'queryFieldset',
     	        	    width: '100%',
  	            		xtype:'fieldset',
  	                	title: 'Search',
  	                	startCollapsed: true,
  	                	collapsible: true,
  	                	defaultType: 'textfield',
  	                	items:
  	                		[
								{
								 	xtype: 'fieldcontainer',
								 	layout: 'hbox',
								    items: [
								        {
								            xtype     : 'textfield',
								            name      : 'roleCode',
								            fieldLabel: 'Role Code'
								        },
								        {
								            xtype     : 'textfield',
								            name      : 'roleName',
								            fieldLabel: 'Role Name'
								        },
								        {
				    	                    name: 'activeFlag',
				    	                    fieldLabel: 'Active Flag',
					    	                xtype: 'combo',
					    	                displayField:   'dicName',
				                            valueField:     'dicCode',
				                            store:          Ext.create('Ext.data.Store', {
				                                fields : ['dicCode', 'dicName'],
				                                data:UnieapDicdata.ActiveFlagOpt
				                             }),
				    	                    anchor:'95%'
				    	                }
								        ]
								},{
					     	    	xtype: 'fieldcontainer',
					     	    	layout: 'hbox',
					                items: [
						               	{
						                    name: 'createDatetime',
						                    fieldLabel: 'Create Datetime',
						                    format: 'Y-m-d',
							                xtype: 'datefield'
						                },
						                {
						                    name: 'modifyDatetime',
						                    fieldLabel: 'Modify Datetime',
						                    format: 'Y-m-d',
							                xtype: 'datefield'
						                }
					                ]
					            }
  	                		]
     	           }
     	           ]
		});
		//default is collapsed
		Ext.getCmp('queryFieldset').collapse(true);
        queryform.render();
    	/***data grid********************************************/
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:[
                    'roleId','roleCode','roleName','createDatetime','modifyDatetime','activeFlag','activeFlagDesc','remark','beId','createBy','modifyBy' 
                ],
                idProperty: 'roleId'
        });
        /**
        var selModel = Ext.create('Ext.selection.CheckboxModel',{
        	singleSelect:true,
            listeners: {
                selectionchange: function(sm, selections) {
                	//datagrid.down('#removeButton').setDisabled(selections.length === 0);
                }
            }
        });
        */
        var gridstore = Ext.create('Ext.data.Store', {
            //pageSize: 10,
            model: 'datamodel',
            remoteSort: true,
            proxy: {
                type: 'ajax',
                url: 'MdMController.do?method=roleGrid',
                reader: {
                	root: 'rows',
                    totalProperty: 'totalCount'
                },
                simpleSortMode: true
            },
            sorters: [{
            	 property: 'roleCode',
                 direction: 'DESC'
            }]
        });
        /***add query paramter to store*********************/
        gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
        var datagrid = Ext.create('Ext.grid.Panel', {
       		el : 'datagrid',
       		layout: 'fit',
   	   		store : gridstore,
   	   		columnLines: true,
           	selModel:
           	{
	            pruneRemoved: false
	        },
   	   	   	region:'center', 
   	   	   	bodyStyle:'width:100%;height:100%',
   	   	   	autoScroll:true,
   	   	   	autoExpandColumn:'action',
	   	   	columns:[
			{
			    xtype: 'rownumberer',
			    text: "No.",
			    width: 30,
			    sortable: false
			},
			{
				menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                text: "View",
                width: 50,
                items:[
                       {
                    	   icon   : '/Unieap/unieap/js/common/images/view.gif',
                           tooltip: 'View',
                           handler: function(grid, rowIndex, colIndex)
                           {	
                        	    showForm('View');
                        	    var rec = gridstore.getAt(rowIndex);
                           		dataForm.getForm().setValues(rec.data);
                        	   	Ext.getCmp('formSubmit').hide();
                           }
                       }
                       ]
			},
	   	   	{
	            text: "Role Code",
	            dataIndex: 'roleCode',
	            sortable: true
	        },{
	            text: "Role Name",
	            dataIndex: 'roleName',
	            sortable: true
	        },{
	            text: "Create Datetime",
	            dataIndex: 'createDatetime',
	            width: 130,
	            sortable: true
	        },{
	            text: "Modify Datetime",
	            dataIndex: 'modifyDatetime',
	            width: 130,
	            sortable: true
	        },{
	            text: "Active Flag",
	            dataIndex: 'activeFlagDesc',
	            sortable: false
	        },{
	            text: "Remark",
	            dataIndex: 'remark',
	            sortable: false
	        }],
	        tbar:[queryform],
    	   	bbar:new Ext.PagingToolbar({ 
    	     	//pageSize : 10,
    	     	store : gridstore,
    	     	displayInfo: true,
                displayMsg: 'Displaying topics {0} - {1} of {2}',
                emptyMsg: "No topics to display"
    	   })
        	
        });
        datagrid.render();
        gridstore.loadPage(1);
        var resdataTabPanel = Ext.create('Ext.tab.Panel',{
        	renderTo:'resdata',
        	activeTab: 0,
       		layout: 'fit',
            bodyStyle: 'padding:5px',
            items:[
						{
						    xtype: 'panel',
				            title: 'Role List',
				            items : [datagrid],
				            height: 520,
						    closable: false
						}
                   ]
        });
        /***function******************************************/
        var dataWin = null;
        var dataForm = null;
        var operType = '';
        function showForm(status){
        	operType = status;
            if (dataWin==null)
            {
            	dataForm = Ext.widget('form',{
                    layout: {
                        type: 'vbox',
                        align: 'stretch'
                    },
                    border: false,
                    bodyPadding: 10,
                    defaults: {
                        anchor: '100%',
                        labelWidth: 100
                    },
                    items: [
								{
								    xtype     : 'textfield',
								    name      : 'roleId',
								    fieldLabel: 'roleId',
								    hidden: true
								},{
								    xtype     : 'textfield',
								    name      : 'beId',
								    fieldLabel: 'beId',
								    hidden: true
								},{
								    xtype     : 'textfield',
								    name      : 'createBy',
								    fieldLabel: 'createBy',
								    hidden: true
								},{
								    xtype     : 'textfield',
								    name      : 'modifyBy',
								    fieldLabel: 'modifyBy',
								    hidden: true
								},{
								    xtype     : 'textfield',
								    name      : 'createDatetime',
								    fieldLabel: 'createDatetime',
								    hidden: true
								},{
								    xtype     : 'textfield',
								    name      : 'modifyDatetime',
								    fieldLabel: 'modifyDatetime',
								    hidden: true
								},{
								    xtype     : 'textfield',
								    name      : 'roleCode',
								    fieldLabel: 'Role code',
								    msgTarget : 'side',
								    validateOnChange:false,
								    validateOnBlur :true,
								    validator :function(value){
								    		var error = true; 
								    		if(operType == 'Modify' || value==''||value ==null){
								    			return true;
								    		}
								    		Ext.Ajax.request({
								                url: 'MdMController.do?method=roleDeal',
								                async:false,
								                params:{'operType':"checkExist","roleCode":value},
								                success: function(response, opts) {
								                	var result = Ext.JSON.decode(response.responseText);
								                    if(result.isSuccess == 'success')
								                    {
								                		error = true; 
								                    }else{
								                    	error = value+ ' already exist, please change to another';
								                    }
								                },
								                failure: function(response, opts) {
								                	error='Check role code exist failed,error message:'+response.responseText;
								                }
								             });
								             return error;
								    	},
								    allowBlank: false
								},
								{
								    xtype     : 'textfield',
								    name      : 'roleName',
								    fieldLabel: 'Role name',
								    msgTarget: 'side',
								    allowBlank: false
								},
								{
	                                xtype:          'combo',
	                                forceSelection: true,
	                                emptyText: 'Select an Option',
	                                editable:       false,
	                                allowBlank:     false,
	                                fieldLabel:     'Active Flag',
	                                name:           'activeFlag',
	                                displayField:   'dicName',
	                                valueField:     'dicCode',
	                                store:          Ext.create('Ext.data.Store', {
	                                    fields : ['dicCode', 'dicName'],
	                                    data:UnieapDicdata.ActiveFlag
	                                 })
								},
								{
								    xtype     : 'textareafield',
								    name      : 'remark',
								    title     : 'Details',
								    fieldLabel: 'Remark',
								    msgTarget: 'side',
								    allowBlank: true
								}
                            ],

                    buttons: [
                    {
                    	id:'formCancel',
                        text: 'Cancel',
                        handler: function() {
                        	dataForm.getForm().reset();
                        	dataWin.hide();
                        }
                    }, {
                    	id:'formSubmit',
                        text: 'Submit',
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
                                    	 var rec = datagrid.getSelectionModel().getSelection();
                                    	 Ext.MessageBox.alert('Status', 'Save successfully.',showResult);
                                     },
                                     failure: function(form, action) {
                                    	 Ext.MessageBox.alert('Status', 'Save failed, please retry.',showResult);
                                     }
                                 });
                        	 }
                        }
                    }]
                });
                dataWin = Ext.widget('window', {
                    title: 'Data',
                    closeAction: 'hide',
                    width: 400,
                    height: 400,
                    //minWidth: 300,
                    //minHeight: 300,
                    layout: 'fit',
                    //resizable: true,
                    modal: true,
                    items: dataForm,
                    defaultFocus: 'roleCode'
                });
            }
            if(operType=='Add'){
            	dataForm.getForm().reset();
            	dataWin.show();
            	Ext.getCmp('formSubmit').show();
            }else if(operType=='Modify'){
            	var rec = datagrid.getSelectionModel().getSelection();
            	dataWin.show();
            	dataForm.getForm().setValues(rec[0].data);
            	Ext.getCmp('formSubmit').show();
            }else{
            	dataWin.show();
            }
        }
        function removeDatas(btn){
        	if(btn=='yes'){
	        	var rec = datagrid.getSelectionModel().getSelection();
	        	var roleId= rec[0].get("roleId");
	        	Ext.Ajax.request({
	                url: 'MdMController.do?method=roleDeal',
	                params:{'operType':"Delete","roleId":roleId},
	                success: function(response, opts) {
	                	Ext.MessageBox.alert('Status', 'Remove data successfully.',showResult);
	                	gridstore.remove(rec);
	                },
	                failure: function(response, opts) {
	                	Ext.MessageBox.alert('Status', 'Remove data failed.Error:'+response.status);
	                }
	             });
        	}
        }
        function showResult(btn){
        	dataWin.hide();
        	gridstore.reload();
        }
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
    });
    </script>
</head>
<body>
	<div id="queryform" style='width:100%;height:35;font-size:0;'></div>
	<div id="datagrid" style='width:100%;height:100%;'></div>
	<div id="resdata" style='width:100%;height:100%;'></div>
	<div id="gridresdata" style='width:100%;height:100%;'></div>
	<div id="treeresdata" style='width:100%;height:100%;'></div>
</body>
</html>
