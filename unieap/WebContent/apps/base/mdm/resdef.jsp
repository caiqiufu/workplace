<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Resdef</title>
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
               		tooltip:'Add',text:'Add',xtype:'button',id:'ResDef_Add',
		            handler : function(){showForm('Add',null);}
		    }],
	        listeners:
	        {afterRender:function(thisForm, options){
		        	if(UnieapButton.ResDef_Add==null||UnieapButton.ResDef_Add.abled==false){Ext.getCmp('ResDef_Add').hide();}}
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
							        { xtype:'textfield',name: 'resDefCode',fieldLabel: 'Resource Code'},
							        { xtype:'textfield',name:'resDefName',fieldLabel: 'Resource Name'},
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
				    	                	var resDefCode=queryform.getForm().findField("resDefCode").getValue(); 
				    	                    var resDefName=queryform.getForm().findField("resDefName").getValue(); 
				    	                    var createDatetime=dateFormat(queryform.getForm().findField("createDatetime").getValue()); 
				    	                    var modifyDatetime=dateFormat(queryform.getForm().findField("modifyDatetime").getValue()); 
				    	                    var activeFlag=queryform.getForm().findField("activeFlag").getValue();
				    	                    queryPara = 
				    	                    	{
				    	                         	resDefCode:resDefCode,
				    	                         	resDefName:resDefName,
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
            	'resDefId','resDefCode','resDefName','idField','codeField','nameField','pidField','pcodeField','pnameField','tableName','filterSql','resType','isTree','createDatetime','modifyDatetime','activeFlag','activeFlagDesc','remark','beId','createBy','modifyBy' 
            ],
            idProperty: 'resDefId'
        });
        var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy: 
            { type: 'ajax', url: 'MdMController.do?method=resdefGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: 
            [
            	{ property: 'resDefCode', direction: 'DESC'}
            ]
        });
        gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
        var operationItems = [];
        var selectedRecord;
       	if(UnieapButton.ResDef_Modify!=null&&UnieapButton.ResDef_Modify.abled== true)
       	{
        	operationItems.push({
        	   icon   : '/Unieap/unieap/js/common/images/edit.png',
               tooltip: 'Edit',
               handler:function(grid, rowIndex, colIndex)
               {	
               		selectedRecord = grid.getStore().getAt(rowIndex);
               		var resDefId= selectedRecord.get("resDefId");
               		if(resDefId=='1'){
            	    	Ext.Msg.alert('Click',"System default menu,can't modify");
            	    }else{
               			showForm('Modify',selectedRecord);
            	    }
               }
           });
           operationItems.push({iconCls :'',tooltip:''});
       	}
       	if(UnieapButton.ResDef_Delete!=null&&UnieapButton.ResDef_Delete.abled== true)
       	{
        	operationItems.push({
        	   icon   : '/Unieap/unieap/js/common/images/delete.png',
               tooltip: 'Delete',
               handler:function(grid, rowIndex, colIndex)
               {	
            	    selectedRecord = grid.getStore().getAt(rowIndex);
            	    var resDefId= selectedRecord.get("resDefId");
            	    if(resDefId=='1'){
            	    	Ext.Msg.alert('Click',"System default menu,can't delete");
            	    }else{
               			Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?', removeDatas);
            	    }
               }
           });
           operationItems.push({iconCls :'',tooltip:''});
       	}
       	if(UnieapButton.ResDef_ResourceData!=null&&UnieapButton.ResDef_ResourceData.abled== true)
       	{
        	operationItems.push({
        	   icon   : '/Unieap/unieap/js/common/images/grid.png',
               tooltip: 'Resource Data',
                handler:function(grid, rowIndex, colIndex)
               {	
               		selectedRecord = grid.getStore().getAt(rowIndex);
               		var resDefId= selectedRecord.get("resDefId");
            		var resDefName= selectedRecord.get("resDefName");
             		var isTree= selectedRecord.get("isTree");
             		showResourceData(resDefId,isTree,resDefName);
               }
           });
           operationItems.push({iconCls :'',tooltip:''});
       	}
       	if(UnieapButton.ResDef_SyncResourceData!=null&&UnieapButton.ResDef_SyncResourceData.abled== true)
       	{
        	operationItems.push({
        	   icon   : '/Unieap/unieap/js/common/images/table_refresh.png',
               tooltip: 'Sync Resource Data',
               handler:function(grid, rowIndex, colIndex)
               {	
            	    selectedRecord = grid.getStore().getAt(rowIndex);
            	    var resDefId= selectedRecord.get("resDefId");
               		SyncResourceData(resDefId);
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
	   	   		{ text: "Resource Code",width:90, dataIndex: 'resDefCode',sortable: true},
	   	   		{ text: "Resource Name",width:90, dataIndex: 'resDefName', sortable: true},
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
        var resdataTabPanel = Ext.create('Ext.tab.Panel',{
        	renderTo:'resdata',activeTab:0,layout: 'fit',
            bodyStyle: 'padding:5px',
            items:[{xtype: 'panel',title:'Resource define',items : [datagrid],closable: false}]
        });
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
                    	{xtype:'fieldset', title:'Resource Info',
	                        items:
	                        [
	                        	{ xtype:'hiddenfield', name:'resDefId'},
	                        	{ xtype:'hiddenfield', name:'beId'},
	                        	{ xtype:'hiddenfield', name:'createBy'},
	                        	{ xtype:'hiddenfield', name:'modifyBy'},
	                        	{ xtype:'hiddenfield', name:'createDatetime'},
	                        	{ xtype:'hiddenfield', name:'modifyDatetime'},
	                        	{
					                xtype:'fieldcontainer',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                items: [
					                    { xtype:'textfield', name:'resDefCode',fieldLabel:'Resource Code',allowBlank:false,
			                        		validateOnChange:false, validateOnBlur :true,
										    validator :function(value)
										    	{
										    			var error = true; 
										    			if(operType == 'Modify' || value==''||value ==null)
										    			{
										    				return true;
										    			}
										    		Ext.Ajax.request({
										                url: 'MdMController.do?method=userDeal',
										                async:false,
										                params:{'operType':"checkExist","userCode":value},
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
										                	error='Check User Code exist failed,error message:'+response.responseText;
										                }
										             });
										             return error;
										    	}
			                        	},
			                        	{ xtype:'textfield',name:'resDefName', fieldLabel:'Resource Name', allowBlank:false}
							          ]
					            }
	                        ]
	                    },
	                    {xtype:'fieldset',title:'Resource Table Info',
	                        items:
	                        [
	                        	{
					                xtype:'fieldcontainer',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                items: [
					                	{ xtype:'textfield',name:'tableName', fieldLabel:'Table Name', allowBlank:false},
		                        		{ xtype:'textfield',name:'idField',fieldLabel: 'Id Field',allowBlank:false}
					                ]
					            },
		                        {
					                xtype:'fieldcontainer',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                items: [
					                	{ xtype:'textfield',name:'codeField', fieldLabel:'Code Field', allowBlank:false},
		                        		{ xtype:'textfield',name:'nameField', fieldLabel:'Name Field', allowBlank:false}
					                ]
					            },
		                       	{
					                xtype:'fieldcontainer',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                items: [
					                	{ xtype:'textfield',name:'pidField', fieldLabel:'Parent Id Field', allowBlank:true},
		                        		{ xtype:'textfield',name:'pcodeField', fieldLabel:'Parent Code Field', allowBlank:true}
					                ]
					            },
		                        {
					                xtype:'fieldcontainer',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                items: [
					                	{ xtype:'textfield',name:'pnameField', fieldLabel:'Parent Name Field', allowBlank:true},
					                	{ xtype:'combo', forceSelection: true, emptyText: 'Select an Option',editable:false,allowBlank:false,
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
										{ xtype:'textareafield',name:'filterSql', fieldLabel:'Filter Sql',width: '100%', allowBlank:true}
					                ]
					            },
		                        {
					                xtype:'fieldcontainer',
					                combineErrors: true,
					                msgTarget :'side',
					                layout: 'hbox',
					                items: [
					                	{ xtype:'combo',forceSelection:true,emptyText: 'Select an Option',editable:false,allowBlank:false,
							              fieldLabel:'Resource Type',name:'resType',displayField:'dicName',valueField:'dicCode',
				                          store:Ext.create('Ext.data.Store', {fields : ['dicCode', 'dicName'],data:UnieapDicdata.ResType})
										},
										{xtype:'combo',forceSelection: true,emptyText:'Select an Option',editable:false,allowBlank:false,
							              fieldLabel:'IsTree',name:'isTree',displayField:'dicName',valueField:'dicCode',
							              store:Ext.create('Ext.data.Store', {fields : ['dicCode', 'dicName'],data:UnieapDicdata.IsTree})
										}
					                ]
					            },
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
	                    {id:'formCancel', text: 'Cancel',
	                        handler: function() 
	                        {
	                        	dataForm.getForm().reset();
	                        	dataWin.hide();
	                        }
	                    },{
	                    	id:'testSelectRes',	text: 'Test Resource SQL',
	                       	handler: function() 
	                       	{
	                    	     var form = dataForm.getForm();
		                       	 if (form.isValid()){
		                                form.submit({
		                                    clientValidation: true, waitMsg: 'Processing...', method: 'POST',
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
	                                     url: 'MdMController.do?method=resdefDeal',
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
                { title: 'Data',closeAction:'hide',width:600,height:450,layout:'fit',modal:true,items:dataForm,defaultFocus: 'resDefCode' });
            }
            if(operType=='Add')
            {
            	dataForm.getForm().reset();
            	dataWin.show();
            	dataForm.getForm().findField('resDefCode').setReadOnly(false);
            }else if(operType=='Modify')
            {
            	dataWin.show();
            	dataForm.getForm().setValues(selectedRecord.data);
            	dataForm.getForm().findField('resDefCode').setReadOnly(true);
            }else
            {
            	dataWin.show();
            }
        }
        function removeDatas(btn)
        {
        	if(btn=='yes'){
	        	var resDefId= selectedRecord.get("resDefId");
	        	Ext.Ajax.request({
	                url: 'MdMController.do?method=resdefDeal',
	                params:{'operType':"Delete","resDefId":resDefId},
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
        function showResourceData(resDefId,isTree,resDefName)
        {
        	if(isTree=='N')
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
        	Ext.MessageBox.show({
	           title: 'Please wait',msg: 'Syncing resource data...',progressText: 'Syncing...',
	           width:300,progress:true,closable:false,animateTarget: 'datagrid'
	       });
        	Ext.Ajax.request({  
                url : 'MdMController.do?method=resdefDeal',  
                params :{'operType':"SyncData","resDefId":resDefId},  
                method : 'POST',  
                success : function(response, options)
                {  
                	Ext.MessageBox.hide();
                	Ext.MessageBox.alert('Status', 'Sync resource data successfully.');
                },  
                failure : function(response, options) 
                {  
                	Ext.MessageBox.hide();
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
</body>
</html>
