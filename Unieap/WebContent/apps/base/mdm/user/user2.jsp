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
			fieldDefaults:
			{ labelAlign: 'left', labelWidth: 90 },
	        layout: 'fit',
	        width : '100%',
     	   	frame : true,
     	   	el:'queryform',
     	   	tbar:[
			    { pressed :true,iconCls:'add',
	         		tooltip:'<%=UnieapConstants.getMessage("comm.add")%>',text:'<%=UnieapConstants.getMessage("comm.add")%>',xtype:'button',id:'User_Add',hidden:true,
			        handler : function(){showForm('Add',null);}
				}
		    ],
	        listeners:{
	        	afterRender:function(thisForm, options){
		        	if(UnieapButton.User_Add==null&&UnieapButton.User_Add.abled==false){Ext.getCmp('User_Add').hide();}
		        }
	        },
     	    items:[
     	           { id:'queryFieldset', width: '100%',xtype:'fieldset',title: '<%=UnieapConstants.getMessage("comm.search")%>',
  	                 startCollapsed: true,collapsible: true, defaultType: 'textfield',
  	                	items:
  	                		[
								{ xtype: 'fieldcontainer',layout: 'hbox',
								    items: 
								    [
								        { xtype:'textfield',name: 'userCode',fieldLabel: '<%=UnieapConstants.getMessage("mdm.user.display.userCode")%>'},
								        { xtype:'textfield',name:'userName',fieldLabel: '<%=UnieapConstants.getMessage("mdm.user.display.userName")%>'},
								        { name: 'activeFlag',fieldLabel: '<%=UnieapConstants.getMessage("comm.activeFlag")%>',
					    	                xtype: 'combo', anchor:'95%',
					    	                displayField:   'dicName',
				                            valueField:     'dicCode',
				                            store:          Ext.create('Ext.data.Store', {
				                                fields : ['dicCode', 'dicName'],
				                                data:UnieapButton._activeFlag
				                             })
				    	                }
								    ]
								},
								{xtype: 'fieldcontainer',layout: 'hbox',
					                items: 
					                [
						               	{name: 'createDatetime', fieldLabel: '<%=UnieapConstants.getMessage("comm.createDate")%>',format: 'Y-m-d', xtype: 'datefield'},
						                {name: 'modifyDatetime', fieldLabel: '<%=UnieapConstants.getMessage("comm.modifyDate")%>', format: 'Y-m-d',xtype: 'datefield'},
						                { xtype:'button',text:'<%=UnieapConstants.getMessage("comm.search")%>',iconAlign: 'right',
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
					    	                    gridstore.load({params:queryPara});
					    	                }
						                },
						                { xtype:'button',text:'<%=UnieapConstants.getMessage("comm.reset")%>',iconAlign: 'right',
					    	                handler: function (){
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
            	'userId','userCode','userName','password','isNoLocked','enabled','createBy','createDatetime','modifyBy','modifyDatetime','activeFlag','activeFlagDesc','remark','beId'
            ],
            idProperty: 'userId'
        });
        var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy: 
            { type: 'ajax', url: 'MdMController.do?method=userGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: 
            [
            	{ property: 'userCode', direction: 'DESC'}
            ]
        });
        gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
        var operationItems = [];
        var selectedRecord;
       	if(UnieapButton.User_Modify!=null&&UnieapButton.User_Modify.abled== true){
        	operationItems.push({
        	   iconCls : 'edit',
               tooltip: '<%=UnieapConstants.getMessage("comm.edit")%>',
               handler:function(grid, rowIndex, colIndex)
               {	
               		selectedRecord = grid.getStore().getAt(rowIndex);
            		showForm('Modify',selectedRecord);
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
	   	   		{ text: "<%=UnieapConstants.getMessage("mdm.user.display.userCode")%>", dataIndex: 'userCode',sortable: true},
	   	   		{ text: "<%=UnieapConstants.getMessage("mdm.user.display.userName")%>", dataIndex: 'userName', sortable: true},
	   	   		{ text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>",dataIndex: 'activeFlagDesc',sortable: false},
	   	   		{ text: "<%=UnieapConstants.getMessage("comm.createDate")%>",width: 130, dataIndex: 'createDatetime', sortable: true},
	   	   		{ text: "<%=UnieapConstants.getMessage("comm.modifyDate")%>",width: 130, dataIndex: 'modifyDatetime', sortable: true},
	   	   		{ text: "<%=UnieapConstants.getMessage("comm.remark")%>", dataIndex: 'remark',sortable: false},
	   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:100,items:operationItems}
	   	   	],
    	   	bbar:new Ext.PagingToolbar(
    	   	{ store : gridstore,displayInfo: true})
        	
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
                    	{xtype:'fieldset', title:'<%=UnieapConstants.getMessage("comm.data")%>',
	                        items:
	                        [
	                        	{ xtype:'hiddenfield', name:'userId'},
	                        	{ xtype:'hiddenfield', name:'beId'},
	                        	{ xtype:'hiddenfield', name:'createBy'},
	                        	{ xtype:'hiddenfield', name:'isNoLocked'},
	                        	{ xtype:'hiddenfield', name:'enabled'},
	                        	{ xtype:'hiddenfield', name:'password'},
	                        	{ xtype:'hiddenfield', name:'modifyBy'},
	                        	{ xtype:'hiddenfield', name:'createDatetime'},
	                        	{ xtype:'hiddenfield', name:'modifyDatetime'},
	                        	{ xtype:'textfield', name:'userCode',fieldLabel:'<%=UnieapConstants.getMessage("mdm.user.display.userCode")%>',maxLength:45,allowBlank:false,
	                        		validateOnChange:false, validateOnBlur :true,
								    validator :function(value){
								    			var error = true; 
								    			if(operType == 'Modify' || value==''||value ==null){
								    				return true;
								    			}
									    		Ext.Ajax.request({
									                url: 'MdMController.do?method=userDeal',
									                async:false,
									                params:{'operType':"checkExist","userCode":value},
									                success: function(response, opts){
									                	var result = Ext.JSON.decode(response.responseText);
									                    if(result.isSuccess == 'success'){
									                		error = true; 
									                    }else{
									                    	error = result.message;
									                    }
									                },
									                failure: function(response, opts){
									                	error=response.responseText;
									                }
								             });
								             return error;
								    	}
	                        	},
	                        	{ xtype:'textfield',name:'userName', fieldLabel:'<%=UnieapConstants.getMessage("mdm.user.display.userName")%>',maxLength:45, allowBlank:false},
	                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:true,
	                                name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'Y',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._activeFlag})
								},
	                        	{ xtype:'textareafield', name:'remark',fieldLabel:'<%=UnieapConstants.getMessage("comm.remark")%>',maxLength:255,growMin:60,growMax:100,allowBlank:true}
	                        ]
	                    },
	                    {xtype:'fieldset',title:'<%=UnieapConstants.getMessage("comm.data.detail")%>',
	                        items:
	                        [
		                        { xtype:'textfield',name:'email',fieldLabel: '<%=UnieapConstants.getMessage("mdm.user.display.email")%>',vtype: 'email',maxLength:45},
	                        ]
                    	}
                    ],
                    buttons: 
                    [
	                    {id:'formCancel', text: '<%=UnieapConstants.getMessage("comm.cancel")%>',
	                        handler: function(){
	                        	dataForm.getForm().reset();
	                        	dataWin.hide();
	                        }
	                    }, 
	                    {id:'formSubmit',text: '<%=UnieapConstants.getMessage("comm.submit")%>',
	                        handler: function() {
	                        	var form = dataForm.getForm();
	                        	 if (form.isValid()){
	                                 form.submit({
	                                     clientValidation: true,
	                                     method: 'POST',
	                                     params:{'operType':operType},
	                                     url: 'MdMController.do?method=userDeal',
	                                     success: function(form, action) {
	                                    	 if(result.isSuccess == 'failed'){
							                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
			                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
							                    }else{
	 		                                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.save")%>',fn: showResult,
		 		                               			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
							                    }
	                                     },
	                                     failure: function(form, action) {
	                                    	 Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:action.response.responseText,
		                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                                     }
	                                 });
	                        	 }
	                        }
	                    }
                    ]
                });
                dataWin = Ext.widget('window', 
                { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height: 400, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'userCode' });
            }
            if(operType=='Add'){
            	dataForm.getForm().reset();
            	dataWin.show();
            	dataForm.getForm().findField('userCode').inputEl.removeCls('readonly_field');
            }else if(operType=='Modify'){
            	dataWin.show();
            	dataForm.getForm().setValues(selectedRecord.data);
            	dataForm.getForm().findField('userCode').setReadOnly(true);
            	dataForm.getForm().findField('userCode').inputEl.addCls('readonly_field');
            }else{
            	dataWin.show();
            }
        }
        function removeDatas(btn){
        	if(btn=='yes'){
	        	var userId= selectedRecord.get("userId");
	        	Ext.Ajax.request({
	                url: 'MdMController.do?method=userDeal',
	                params:{'operType':"Delete","userId":userId},
	                success: function(response, opts){
	                	if(result.isSuccess == 'failed'){
	                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
                     			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                    		gridstore.reload();
	                    }else{
                         	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.save")%>',fn: showResult,
                        			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
	                    }
	                },
	                failure: function(response, opts){
	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:response.responseText,
	             			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
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
