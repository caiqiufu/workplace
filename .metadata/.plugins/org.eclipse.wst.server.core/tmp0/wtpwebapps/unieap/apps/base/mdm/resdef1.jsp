<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Resource Define</title>
    <script type="text/javascript">
    Ext.onReady(function(){
        Ext.tip.QuickTipManager.init();
        /***query form**********************************/
        var queryPara;
        var selectedResdefId = '';
		var queryform = Ext.create('Ext.form.Panel',{
			tbar:
				[
			      {
			    	  pressed : true,
			    	  xtype:'button',
			    	  text : 'Add',
			    	  handler : function()
			    	  {
			    		  showForm('Add');
			    	  }
			      },'-',{
			    	  pressed : true,
			    	  xtype:'button',
			    	  text : 'Modify',
			    	  handler : function()
			    	  {
			    		  	var rec = datagrid.getSelectionModel().getSelection();
	                		if(rec.length==0){
	                			Ext.MessageBox.alert('Status', 'Please select one row data.');
	                		}else{
	                			showForm('Modify');
	                		}
			    	  }
			      },'-',{
			    	  pressed : true,
			    	  xtype:'button',
			    	  text : 'Delete',
			    	  handler : function()
			    	  {
			    		  	var rec = datagrid.getSelectionModel().getSelection();
		                	if(rec.length==0){
		                		Ext.MessageBox.alert('Status', 'Please select one row data.');
		                	}else{
		                		Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?', removeDatas);
		                	}
			    	  }
			      },'-',{
			    	  	pressed : true,
	                	text:'Search',
	                	xtype:'button',
    	                handler: function (){
    	                    var resType=queryform.getForm().findField("resType").getValue(); 
    	                    var createDatetime=dateFormat(queryform.getForm().findField("createDatetime").getValue()); 
    	                    var modifyDatetime=dateFormat(queryform.getForm().findField("modifyDatetime").getValue()); 
    	                    var activeFlag=queryform.getForm().findField("activeFlag").getValue();
    	                    queryPara = 
    	                    	{
    	                    		resType:resType,
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
	                			var resDefId= rec[0].get("resDefId");
	                			var isTree= rec[0].get("isTree");
	                			showResourceData(resDefId,isTree);
	                		}
    	                }
	                },'-',{
	                	pressed : true,
	                	text:'Sync Resource Data',
	                	xtype:'button',
    	                handler: function (){
    	                	var rec = datagrid.getSelectionModel().getSelection();
	                		if(rec.length==0){
	                			Ext.MessageBox.alert('Status', 'Please select one row data.');
	                		}else{
	                			var resDefId= rec[0].get("resDefId");
	                			SyncResourceData(resDefId);
	                		}
    	                }
	                }
			     ],
			fieldDefaults:{
	            labelAlign: 'left',
	            buttonAlign : 'center',
	            labelWidth: 90
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
								            name      : 'tableName',
								            fieldLabel: 'Table Name'
								        },
								        {
				    	                    name: 'resType',
				    	                    fieldLabel: 'Resource Type',
					    	                xtype: 'combo',
					    	                displayField:   'dicName',
				                            valueField:     'dicCode',
				                            store:          Ext.create('Ext.data.Store', {
				                                fields : ['dicCode', 'dicName'],
				                                data:UnieapDicdata.ResTypeOpt
				                             }),
				    	                    anchor:'95%'
				    	                }
								        ]
								},{
					     	    	xtype: 'fieldcontainer',
					     	    	layout: 'hbox',
					                items: [
				    	                {
				    	                    name: 'isTree',
				    	                    fieldLabel: 'Is Tree',
					    	                xtype: 'combo',
					    	                displayField:   'dicName',
				                            valueField:     'dicCode',
				                            store:          Ext.create('Ext.data.Store', {
				                                fields : ['dicCode', 'dicName'],
				                                data:UnieapDicdata.IsTreeOpt
				                             }),
				    	                    anchor:'95%'
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
                    'resDefId','idField','codeField','nameField','pidField','pcodeField','pnameField','tableName','filterSql','resType','isTree','createDatetime','modifyDatetime','activeFlag','activeFlagDesc','remark'
                ],
                idProperty: 'resDefId'
        });
        
        //var selModel = Ext.create('Ext.selection.CheckboxModel', {
        //	singleSelect:true
        //});
        var gridstore = Ext.create('Ext.data.Store', {
            //pageSize: 10,
            model: 'datamodel',
            remoteSort: true,
            proxy: {
                type: 'ajax',
                url: 'MdMController.do?method=resdefGrid',
                reader: {
                	root: 'rows',
                    totalProperty: 'totalCount'
                },
                simpleSortMode: true
            },
            sorters: [{
            	 property: 'resDefId',
                 direction: 'DESC'
            }]
        });
        /***add query paramter to store*********************/
        gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
        var datagrid = Ext.create('Ext.grid.Panel', {
       		el : 'datagrid',
       		//layout: 'fit',
       		width: '100%',
            height: 200,
   	   		store : gridstore,
   	   		columnLines: true,
	   	   	selModel:
	   	   	{
	            pruneRemoved: false
	        },
   	   	   	region:'center',
   	   	   	autoScroll:true,
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
			},{
	            text: "Table Name",
	            dataIndex: 'tableName',
	            sortable: true
	        },
	   	   	 {
	            text: "Id Field",
	            dataIndex: 'idField',
	            sortable: true
	        },{
	            text: "Code Field",
	            dataIndex: 'codeField',
	            sortable: true
	        },{
	            text: "Name Field",
	            dataIndex: 'nameField',
	            sortable: true
	        },{
	            text: "Filter Sql",
	            dataIndex: 'filterSql',
	            sortable: true
	        },{
	            text: "Resource Type",
	            dataIndex: 'resType',
	            sortable: true
	        }],
	        tbar:[queryform],
    	   	bbar:new Ext.PagingToolbar({ 
    	     	pageSize : 10,
    	     	store : gridstore,
    	     	displayInfo: true,
                displayMsg: 'Displaying topics {0} - {1} of {2}',
                emptyMsg: "No topics to display"
    	   })
        	
        });
        datagrid.render();
        gridstore.loadPage(1);
        /***resource data********************************************/
        var queryResdataPara;
        var gridstoreresdata = Ext.create('Ext.data.Store', {
            //pageSize: 10,
            fields:['resId','resCode','resName','resType'],
            remoteSort: true,
            proxy: {
                type: 'ajax',
                url: 'MdMController.do?method=resdataGrid',
                reader: {
                	root: 'rows',
                    totalProperty: 'totalCount'
                },
                simpleSortMode: true
            },
            sorters: [{
            	 property: 'resDefId',
                 direction: 'DESC'
            }]
        });
        var datagridresdata = Ext.create('Ext.grid.Panel', {
       		el : 'gridresdata',
       		layout: 'fit',
       		//width: '100%',
            //height: 200,
   	   		store : gridstoreresdata,
   	   		columnLines: true,
	   	   	selModel: {
	            pruneRemoved: false
	        },
   	   	   	region:'center',
   	   	   	autoScroll:true,
	   	   	columns:[
			{
			    xtype: 'rownumberer',
			    text: "No.",
			    width: 20,
			    sortable: false
			},
			{
	            text: "Resource Id",
	            dataIndex: 'resId',
	            sortable: true
	        },{
	            text: "resCode Code",
	            dataIndex: 'resCode',
	            sortable: true
	        },{
	            text: "Resource Name",
	            dataIndex: 'resName',
	            sortable: true
	        },{
	            text: "Resource Type",
	            dataIndex: 'resType',
	            sortable: true
	        }],
    	   	bbar:new Ext.PagingToolbar({ 
    	     	pageSize : 10,
    	     	store : gridstoreresdata,
    	     	displayInfo: true,
                displayMsg: 'Displaying topics {0} - {1} of {2}',
                emptyMsg: "No topics to display"
    	   })
        	
        });
        gridstoreresdata.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryResdataPara);
        });
        
        Ext.define('ResDataTree', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields: [
				{name: 'id',     type: 'string'},
				{name: 'text',     type: 'string'},
                {name: 'resDataId',     type: 'string'},
                {name: 'resId',     type: 'string'},
                {name: 'resCode', type: 'string'},
                {name: 'resName',     type: 'string'},
                {name: 'resDefId',     type: 'string'}
            ]
        });
        
        
        var resdataTreestore = Ext.create('Ext.data.TreeStore', {
            model: 'ResDataTree',
            //model: 'Post',
            proxy: {
            	type: 'ajax',
                //reader: 'json',
                //url: 'apps/base/mdm/forum-data1.json'
                //type: 'ajax',
                url: 'MdMController.do?method=resdataTree'
            },
            /*
            beforeitemexpand:function(record,eOpts){
        	var rec = datagrid.getSelectionModel().getSelection();
    		if(rec.length>0){
    			var resDefId= rec[0].get("resDefId");
    			alert('resDefId='+resDefId);
    	       	Ext.apply(store.proxy.extraParams,{resDefId:resDefId});
    		}
        },
            
            nodeParam : "id",
            root:{  
                id:-1,  
                text:'Root',  
                leaf:false,  
                expanded:false  
            }, 
            */
            nodeParam : "id",
            autoLoad : false,
            folderSort: true
        });
        resdataTreestore.on('beforeload', function (store, options){
           	Ext.apply(store.proxy.extraParams,{resDefId:selectedResdefId});
        });
        /*
		resdataTreestore.on('beforeload', function (store, options){
			var rec = datagrid.getSelectionModel().getSelection();
			var resDefId= rec[0].get("resDefId");
           	Ext.apply(store.proxy.extraParams,{resDefId:resDefId});
			//if(rec.length>0){
			//}
        });*/
		
        var resdataTreePanel = Ext.create('Ext.tree.Panel', {
            //title: 'Core Team Projects',
            width: 500,
            height: 300,
            el : 'treeresdata',
       		//layout: 'fit',
            collapsible: true,
            rootVisible:true,
            useArrows: true,
            store: resdataTreestore,
            animate: true
        });
        
        var resdataTabPanel = Ext.create('Ext.tab.Panel',{
        	renderTo:'resdata',
        	activeTab: 0,
       		layout: 'fit',
            bodyStyle: 'padding:5px',
            items:[
						{
						    xtype: 'panel',
				            title: 'Grid resource data',
				            items : [datagridresdata],
						    closable: false
						}, {
						    xtype: 'panel',
						    title: 'Tree resource data',
						    items : [resdataTreePanel],
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
            if (dataWin==null){
            	dataForm = Ext.widget('form',{
                    layout: {
                        type: 'vbox',
                        align: 'stretch'
                    },
                    border: false,
                    bodyPadding: 10,
                    defaults: {
                        //anchor: '100%',
                        labelWidth: 50
                    },
                    items: [
							{
							    xtype     : 'textfield',
							    name      : 'resDefId',
							    fieldLabel: 'resDefId',
							    hidden: true
							},
                            {
                            	xtype: 'fieldcontainer',
							 	layout: 'hbox',
							 	items:[
											{
											    xtype     : 'textfield',
											    name      : 'tableName',
											    fieldLabel: 'Table Name',
											    allowBlank: false
											},{
											    xtype     : 'textfield',
											    name      : 'idField',
											    fieldLabel: 'Id Field',
											    allowBlank: false
											}
							 	       ]
                            },{
                            	xtype: 'fieldcontainer',
							 	layout: 'hbox',
							 	items:[
											{
											    xtype     : 'textfield',
											    name      : 'codeField',
											    fieldLabel: 'Code Field',
											    allowBlank: false
											},{
											    xtype     : 'textfield',
											    name      : 'nameField',
											    fieldLabel: 'Name Field',
											    allowBlank: false
											}
							 	       ]
                            },{
                            	xtype: 'fieldcontainer',
							 	layout: 'hbox',
							 	items:[
											{
											    xtype     : 'textfield',
											    name      : 'pidField',
											    fieldLabel: 'Parent Id Field',
											    allowBlank: true
											},{
											    xtype     : 'textfield',
											    name      : 'pcodeField',
											    fieldLabel: 'Parent Code Field',
											    allowBlank: true
											}
							 	       ]
                            },{
                            	xtype: 'fieldcontainer',
							 	layout: 'hbox',
							 	items:[
											{
											    xtype     : 'textfield',
											    name      : 'pnameField',
											    fieldLabel: 'Parent Name Field',
											    allowBlank: true
											},{
				                                xtype:          'combo',
				                                forceSelection: true,
				                                emptyText: 'Select an Option',
				                                editable:       false,
				                                allowBlank:     false,
				                                fieldLabel:     'Resource Type',
				                                name:           'resType',
				                                displayField:   'dicName',
				                                valueField:     'dicCode',
				                                store:          Ext.create('Ext.data.Store', {
				                                    fields : ['dicCode', 'dicName'],
				                                    data:UnieapDicdata.ResType
				                                 })
											},
							 	       ]
                            },{
                            	xtype: 'fieldcontainer',
							 	layout: 'fit',
							 	items:[
											{
											    xtype     : 'textareafield',
											    name      : 'filterSql',
											    fieldLabel: 'Filter Sql',
											    allowBlank: true
											}
							 	       ]
                            },{
                            	xtype: 'fieldcontainer',
							 	layout: 'hbox',
							 	items:[
											{
				                                xtype:          'combo',
				                                forceSelection: true,
				                                emptyText: 'Select an Option',
				                                editable:       false,
				                                allowBlank:     false,
				                                fieldLabel:     'IsTree',
				                                name:           'isTree',
				                                displayField:   'dicName',
				                                valueField:     'dicCode',
				                                store:          Ext.create('Ext.data.Store', {
				                                    fields : ['dicCode', 'dicName'],
				                                    data:UnieapDicdata.IsTree
				                                 })
											},{
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
											}
							 	       ]
                            },{
                            	xtype: 'fieldcontainer',
                            	layout: 'fit',
							 	items:[
											{
											    xtype     : 'textareafield',
											    name      : 'remark',
											    title     : 'Details',
											    fieldLabel: 'Remark',
											    allowBlank: true,
							                    margins: '0'
											}
							 	       ]
                            }
                            ],

                    buttons: [
                    {
                    	id:'formCancel',
                       	text: 'Cancel',
                       	handler: function() 
                       	{
                       		dataForm.getForm().reset();
                       		dataWin.hide();
                       		Ext.getCmp('formSubmit').show();
                       	}
                    },{
                    	id:'testSelectRes',
                       	text: 'Test Select Resource',
                       	handler: function() 
                       	{
                    	     var form = dataForm.getForm();
	                       	 if (form.isValid()){
	                                form.submit({
	                                    clientValidation: true,
	                                    waitMsg: 'Processing...',
	                                    method: 'POST',
	                                    params:{'operType':'TestResData'},
	                                    url: 'MdMController.do?method=resdefDeal',
	                                    success: function(form, action) {
	                                    	if(action.result.isSuccess =='failed'){
	                                    		Ext.Msg.alert('Click','Test failed, please modify resource define data, error info:'+action.result.errorMessage);
	                                    	}else{
	                                    		Ext.Msg.alert('Click','Test success, SQL='+action.result.SQL);
	                                    	}
	                                    },
	                                    failure: function(form, action) {
	                                    	Ext.Msg.alert('Click','system error');
	                                    }
	                                });
	                       	 }
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
                                     url: 'MdMController.do?method=resdefDeal',
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
                    width: 550,
                    height: 380,
                    layout: 'fit',
                    modal: true,
                    items: dataForm,
                    defaultFocus: 'idField'
                });
            }
            if(operType=='Add'){
            	dataForm.getForm().reset();
            	dataWin.show();
            }else if(operType=='Modify'){
            	var rec = datagrid.getSelectionModel().getSelection();
            	dataWin.show();
            	dataForm.getForm().setValues(rec[0].data);
            	Ext.getCmp('formSubmit').show();
            }else{
            	dataWin.show();
            }
        }
        function removeDatas(btn)
        {
        	if(btn=='yes'){
	        	var rec = datagrid.getSelectionModel().getSelection();
	        	var resDefId= rec[0].get("resDefId");
	        	Ext.Ajax.request({
	                url: 'MdMController.do?method=resdefDeal',
	                params:{'operType':"Delete","resDefId":resDefId},
	                success: function(response, opts) {
	                	Ext.MessageBox.alert('Status', 'Remove data successfully.',showResult);
	                	//gridstore.extraParams = queryPara;
	                	gridstore.remove(rec);
	                	//gridstore.reload();
	                },
	                failure: function(response, opts) {
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
        function showResourceData(resDefId,isTree)
        {
        	selectedResdefId = resDefId;
        	//alert('resDefId='+selectedResdefId);
        	if(isTree==1)
        	{
        		queryResdataPara=
        		{
        			resDefId:selectedResdefId
        		};
        		gridstoreresdata.load({params:queryResdataPara});
        		var tab = resdataTabPanel.items.items[0];
        		tab.show();
        	}else{
        		queryResdataPara=
        		{
        			resDefId:selectedResdefId,
        			id:'-1',
        			text:'Root',
        			expanded:false,
        			leaf:false
        		};
        		var tab = resdataTabPanel.items.items[1];
        		tab.show();
        		resdataTreestore.load({params:queryResdataPara});  
        		
        	}
        }
        function SyncResourceData(resDefId)
        {
        	Ext.Ajax.request({  
                url : 'MdMController.do?method=resdefDeal',  
                params :{'operType':"SyncData","resDefId":resDefId},  
                method : 'POST',  
                success : function(response, options)
                {  
                	Ext.MessageBox.alert("Status",response.responseText); 
                    //var result = Ext.util.JSON.decode(response.responseText);
                },  
                failure : function(response, options) 
                {  
                	Ext.MessageBox.alert("Status","system error:"+response.status);  
                }  
            }); 
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
