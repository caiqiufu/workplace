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
			tbar:
				[
			      {
			    	  pressed : true,
			    	  xtype:'button',
			    	  id:'User_Add',
			    	  text : 'Add',
			    	  handler : function()
			    	  {
			    		  showForm('Add');
			    	  }
			      },'-',{
			    	  pressed : true,
			    	  xtype:'button',
			    	  id:'User_Modify',
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
			    	  id:'User_Delete',
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
    	                	var userCode=queryform.getForm().findField("userCode").getValue(); 
    	                    var userName=queryform.getForm().findField("userName").getValue(); 
    	                    var createDatetime=dateFormat(queryform.getForm().findField("createDatetime").getValue()); 
    	                    var modifyDatetime=dateFormat(queryform.getForm().findField("modifyDatetime").getValue()); 
    	                    var activeFlag=queryform.getForm().findField("activeFlag").getValue();
    	                    queryPara = 
    	                    	{
    	                         	userCode:userCode,
    	                         	userName:userName,
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
		        	if(UnieapButton.User_Add!=null&&UnieapButton.User_Add.disabled== true)
		        	{
			        	Ext.getCmp('User_Add').hide();
		        	}
		        	if(UnieapButton.User_Modify!=null&&UnieapButton.User_Modify.disabled== true)
		        	{
			        	Ext.getCmp('User_Modify').hide();
		        	}
		        	if(UnieapButton.User_Delete!=null&&UnieapButton.User_Delete.disabled== true)
		        	{
			        	Ext.getCmp('User_Delete').hide();
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
								            name      : 'userCode',
								            fieldLabel: 'User Code'
								        },
								        {
								            xtype     : 'textfield',
								            name      : 'userName',
								            fieldLabel: 'User Name'
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
                    'userId','userCode','userName','password','createBy','createDatetime','modifyBy','modifyDatetime','activeFlag','activeFlagDesc','remark','beId','createBy','modifyBy'
                ],
                idProperty: 'userId'
        });
        var gridstore = Ext.create('Ext.data.Store', {
            //pageSize: 10,
            model: 'datamodel',
            remoteSort: true,
            proxy: {
                type: 'ajax',
                url: 'MdMController.do?method=userGrid',
                reader: {
                	root: 'rows',
                    totalProperty: 'totalCount'
                },
                simpleSortMode: true
            },
            sorters: [{
            	 property: 'userCode',
                 direction: 'DESC'
            }]
        });
        /***add query paramter to store*********************/
        gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
        var selModel = Ext.create('Ext.selection.CheckboxModel');
        var datagrid = Ext.create('Ext.grid.Panel', {
       		el : 'datagrid',
       		layout: 'fit',
   	   		store : gridstore,
   	   		columnLines: true,
	   	 	selModel:selModel,
   	   	   	autoScroll:true,
   	   	   	autoExpandColumn:'action',
	   	   	columns:[
	   	   	{
	            text: "User Code",
	            dataIndex: 'userCode',
	            sortable: true
	        },{
	            text: "User Name",
	            dataIndex: 'userName',
	            sortable: true
	        },{
	            text: "Active Flag",
	            dataIndex: 'activeFlagDesc',
	            sortable: false
	        },{
	            text: "Remark",
	            dataIndex: 'remark',
	            sortable: false
	        },{
				menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                text: "View",
                width: 60,
                items:
                [
                       {
                    	   icon   : '/Unieap/unieap/js/common/images/add.png',
                           tooltip: 'Add',
                           handler:function(grid, rowIndex, colIndex)
                           {	
                        	    showForm('View');
                        	    var rec = gridstore.getAt(rowIndex);
                           		dataForm.getForm().setValues(rec.data);
                        	   	Ext.getCmp('formSubmit').hide();
                           }
                       }
                ]
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
        /***function******************************************/
        var dataWin = null;
        var dataForm = null;
        var operType = '';
        function showForm(status){
        	operType = status;
            if (dataWin==null){
            	dataForm = Ext.widget('form',{
                    width:320,
                	height:420,
                	layout:'fit', 
                    fieldDefaults: {
                        labelAlign: 'left',
                        labelWidth: 90,
                        anchor: '100%'
                    }
                    bodyPadding:5,
                    items:
                    [
                    	{
	                        xtype:'fieldset',
	                        title:'User main info',
	                        items:
	                        [
	                        	{ xtype:'hiddenfield', name:'userId'},
	                        	{ xtype:'hiddenfield', name:'beId'},
	                        	{ xtype:'hiddenfield', name:'createBy'},
	                        	{ xtype:'hiddenfield', name:'modifyBy'},
	                        	{ xtype:'hiddenfield', name:'createDatetime'},
	                        	{ xtype:'hiddenfield', name:'modifyDatetime'},
	                        	{ xtype:'textfield', name:'userCode',fieldLabel:'User Code',allowBlank:false,
	                        		validateOnChange:false,
								    validateOnBlur :true,
								    validator :function(value){
								    		var error = true; 
								    		if(operType == 'Modify' || value==''||value ==null){
								    			return true;
								    		}
								    		Ext.Ajax.request({
								                url: 'MdMController.do?method=userDeal',
								                async:false,
								                params:{'operType':"checkExist","userCode":value},
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
								                	error='Check user code exist failed,error message:'+response.responseText;
								                }
								             });
								             return error;
								    	}
	                        	},
	                        	{ xtype:'textfield',name:'userName', fieldLabel:'User Name', allowBlank:false},
	                        	{ xtype:'combo', forceSelection: true,
	                        	    emptyText: 'Select an Option',
	                                editable:       false,
	                                allowBlank:     false,
	                                fieldLabel:     'Title',
	                                name:           'title',
	                                displayField:   'dicName',
	                                valueField:     'dicCode',
	                                store:          Ext.create('Ext.data.Store', {
	                                    fields : ['dicCode', 'dicName'],
	                                    data:UnieapDicdata.Title
	                                 })
								},
	                        	{ xtype:'combo', forceSelection: true,
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
	                        	{ xtype:'textareafield', name:'remark',fieldLabel:'Remark',allowBlank:true}
	                        ]
	                    },
	                    {
	                        xtype:'fieldset',
	                        title:'User detail info',
	                        items:
	                        [
		                        { xtype:'textfield',name:'email',fieldLabel: 'Email Address',vtype: 'email'},
		                        { xtype:'numberfield',name:'QQ',fieldLabel: 'QQ',vtype: 'number'}
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
                                     url: 'MdMController.do?method=userDeal',
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
                    defaultFocus: 'userCode'
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
	        	var userId= rec[0].get("userId");
	        	//rec.operType = 'Delete';
	        	//alert(rec.operType);
	        	Ext.Ajax.request({
	                url: 'MdMController.do?method=userDeal',
	                params:{'operType':"Delete","userId":userId},
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
    });
    </script>
</head>
<body>
    <div id="queryform" style='width:100%;height:35;font-size:0;'></div>
	<div id="datagrid" style='width:100%;height:100%;'></div>
</body>
</html>
