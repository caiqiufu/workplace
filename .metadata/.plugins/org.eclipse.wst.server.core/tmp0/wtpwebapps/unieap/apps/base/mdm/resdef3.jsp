<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Resdef</title>
    <script type="text/javascript">
    Ext.onReady(function(){
        Ext.tip.QuickTipManager.init();
        /***query form**********************************/
        var queryPara;
		var queryform = Ext.create('Ext.form.Panel',{
			tbar:
				[
			      {
			    	  pressed : true,
			    	  xtype:'button',
			    	  id:'ResDef_Add',
			    	  text : 'Add',
			    	  handler : function()
			    	  {
			    		  showForm('Add');
			    	  }
			      },'-',{
			    	  pressed : true,
			    	  xtype:'button',
			    	  id:'ResDef_Modify',
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
			    	  id:'ResDef_Delete',
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
	                	text:'Resource Data',
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
	                },'-',{
			    	  	pressed : true,
	                	text:'Search',
	                	xtype:'button',
    	                handler: function (){
    	                
    	                	var resDefCode=queryform.getForm().findField("resDefCode").getValue(); 
    	                    var resDefName=queryform.getForm().findField("resDefName").getValue(); 
    	                	var tableName=queryform.getForm().findField("tableName").getValue(); 
    	                    var resType=queryform.getForm().findField("resType").getValue(); 
    	                    var createDatetime=dateFormat(queryform.getForm().findField("createDatetime").getValue()); 
    	                    var modifyDatetime=dateFormat(queryform.getForm().findField("modifyDatetime").getValue()); 
    	                    var activeFlag=queryform.getForm().findField("activeFlag").getValue();
    	                    var isTree=queryform.getForm().findField("isTree").getValue();
    	                    queryPara = 
    	                    	{
    	                    		resDefCode:resDefCode,
    	                    		resDefName:resDefName,
    	                    		tableName:tableName,
    	                    		resType:resType,
    	                         	createDatetime:createDatetime,
    	                         	modifyDatetime:modifyDatetime,
    	                         	activeFlag:activeFlag,
    	                         	isTree:isTree
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
		        	if(UnieapButton.ResDef_Add!=null&&UnieapButton.ResDef_Add.disabled== true)
		        	{
			        	Ext.getCmp('ResDef_Add').hide();
		        	}
		        	if(UnieapButton.ResDef_Modify!=null&&UnieapButton.ResDef_Modify.disabled== true)
		        	{
			        	Ext.getCmp('ResDef_Modify').hide();
		        	}
		        	if(UnieapButton.ResDef_Delete!=null&&UnieapButton.ResDef_Delete.disabled== true)
		        	{
			        	Ext.getCmp('ResDef_Delete').hide();
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
								    items: 
								    [
								        {
								            xtype     : 'textfield',
								            name      : 'resDefCode',
								            fieldLabel: 'ResDef Code'
								        },{
								            xtype     : 'textfield',
								            name      : 'resDefName',
								            fieldLabel: 'ResDef Name'
								        },{
								            xtype     : 'textfield',
								            name      : 'tableName',
								            fieldLabel: 'Table Name'
								        }
								    ]
							    },
							 	{
								 	xtype: 'fieldcontainer',
								 	layout: 'hbox',
								    items:
								    [
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
				    	                    name		: 'isTree',
				    	                    fieldLabel	: 'Is Tree',
					    	                xtype		: 'combo',
					    	                displayField:   'dicName',
				                            valueField	:     'dicCode',
				                            store:          Ext.create('Ext.data.Store', {
				                                fields : ['dicCode', 'dicName'],
				                                data:UnieapDicdata.IsTreeOpt
				                             }),
				    	                    anchor	:'95%'
				    	                },
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
                    'resDefId','resDefCode','resDefName','idField','codeField','nameField','pidField','pcodeField','pnameField','tableName','filterSql','resType','isTree','createDatetime','modifyDatetime','activeFlag','activeFlagDesc','remark','beId','createBy','modifyBy' 
                ],
                idProperty: 'resDefId'
        });
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
       		height: 520,
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
	            text: "ResDef Code",
	            dataIndex: 'resDefCode',
	            sortable: true
	        },
	        {
	            text: "ResDef Name",
	            dataIndex: 'resDefName',
	            sortable: true
	        },
	   	   	{
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
	            text: "Resource Type",
	            dataIndex: 'resType',
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
				            title: 'Resource define',
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
            if (dataWin==null){
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
								    name      : 'resDefId',
								    fieldLabel: 'resDefId',
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
	                            	xtype: 'fieldcontainer',
								 	layout: 'hbox',
								 	items:[
												{
												    xtype     : 'textfield',
												    name      : 'resDefCode',
												    fieldLabel: 'ResDef Code',
												    allowBlank: false
												},{
												    xtype     : 'textfield',
												    name      : 'resDefName',
												    fieldLabel: 'ResDef Name',
												    allowBlank: false
												}
								 	       ]
	                            },{
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
	                            },
	                            {
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
	                            },
	                            {
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
	                            },
	                            {
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
	                            },
	                            {
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
	                            },
	                            {
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
	                            },
	                            {
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
                        handler: function() {
                        	dataForm.getForm().reset();
                        	dataWin.hide();
                        }
                    },{
                    	id:'testSelectRes',
                       	text: 'Test Resource SQL',
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
	                                    		Ext.Msg.alert('Click','Test success');
	                                    	}
	                                    },
	                                    failure: function(form, action) {
	                                    	Ext.Msg.alert('Click','system error');
	                                    }
	                                });
	                       	 }
                       	}
                    },{
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
                    width: 600,
                    height: 400,
                    //minWidth: 300,
                    //minHeight: 300,
                    layout: 'fit',
                    //resizable: true,
                    modal: true,
                    items: dataForm,
                    defaultFocus: 'idField'
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
        function showResult(btn){
        	dataWin.hide();
        	gridstore.reload();
        }
        function showResourceData(resDefId,isTree,resDefName)
        {
        	if(isTree==1)
        	{
        		addTab (true,resDefId,'MdMController.do?method=resdataList',resDefName,null);
        	}else{
        		addTab (true,resDefId,'MdMController.do?method=resdataTreeList',resDefName,null);
        	}
        }
        function addTab (closable,id,href,text,imgSrc)
        {
	    	var url = basePathUrl+"/"+href+'&resDefId='+id;
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
	    function SyncResourceData(resDefId)
        {
        	Ext.Ajax.request({  
                url : 'MdMController.do?method=resdefDeal',  
                params :{'operType':"SyncData","resDefId":resDefId},  
                method : 'POST',  
                success : function(response, options)
                {  
                	Ext.MessageBox.alert('Status', 'Sync resource data successfully.');
                	//Ext.MessageBox.alert("Status",response.responseText); 
                    //var result = Ext.JSON.decode(response.responseText);
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
