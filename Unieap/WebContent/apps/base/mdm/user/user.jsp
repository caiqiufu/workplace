<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>User</title>
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
      	    bbar:[
			    { pressed :true,iconCls:'add',
	         		tooltip:'<%=UnieapConstants.getMessage("comm.add")%>',text:'<%=UnieapConstants.getMessage("comm.add")%>',xtype:'button',id:'User_Add',hidden:true,
			        handler : function(){showForm('Add',null);}
				}
		    ],
 	        listeners:{
 	        	afterRender:function(thisForm, options){
 		        	if(UnieapButton.User_Add!=null&&UnieapButton.User_Add.abled== true){
 		        		Ext.getCmp('User_Add').show();}
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
 								        { name: 'enable',fieldLabel: '<%=UnieapConstants.getMessage("comm.activeFlag")%>',
 					    	                xtype: 'combo', anchor:'95%',emptyText: '...',editable:false,allowBlank:true,
 					    	                displayField:   'dicName',
 				                            valueField:     'dicCode',
 				                            store:  Ext.create('Ext.data.Store', {
 				                                fields : ['dicCode', 'dicName'],
 				                                data:UnieapDicdata ._activeFlagOpt
 				                             })
 				    	                }
 								    ]
 								},
 								{xtype: 'fieldcontainer',layout: 'hbox',
 					                items: 
 					                [
 						               	{name: 'createDatetime', fieldLabel: '<%=UnieapConstants.getMessage("comm.createDate")%>',format: 'Y-m-d', xtype: 'datefield'},
 						                {name: 'modifyDatetime', fieldLabel: '<%=UnieapConstants.getMessage("comm.modifyDate")%>', format: 'Y-m-d',xtype: 'datefield'},
 						                { xtype:'button',iconCls:'search-trigger',text:'<%=UnieapConstants.getMessage("comm.search")%>',iconAlign: 'right',
 					    	                handler: function (){
 					    	                	var userCode=queryform.getForm().findField("userCode").getValue(); 
 					    	                    var userName=queryform.getForm().findField("userName").getValue(); 
 					    	                    var createDatetime=dateFormat(queryform.getForm().findField("createDatetime").getValue()); 
 					    	                    var modifyDatetime=dateFormat(queryform.getForm().findField("modifyDatetime").getValue()); 
 					    	                    var enable=queryform.getForm().findField("enable").getValue();
 					    	                    queryPara = 
 					    	                    	{
 					    	                         	userCode:userCode,
 					    	                         	userName:userName,
 					    	                         	createDatetime:createDatetime,
 					    	                         	modifyDatetime:modifyDatetime,
 					    	                         	enable:enable
 					   	                        	};
 					    	                    gridstore.load({params:queryPara});
 					    	                }
 						                },
 						                { xtype:'button',iconCls:'clear-trigger',text:'<%=UnieapConstants.getMessage("comm.reset")%>',iconAlign: 'right',
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
    	 
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'userId','userCode','userName','password','enable','enableDesc','expired','expiredDesc','locked','lockedDesc','createBy','createDate','modifyBy','modifyDate','remark'
            ],
            idProperty: 'userId'
        });
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'MdmController.do?method=userGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'userCode', direction: 'ASC'}]
        });
    	gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
    	var operationItems = [];
        var selectedRecord;
       	if(UnieapButton.User_Modify!=null&&UnieapButton.User_Modify.abled== true){
       		operationItems.push({iconCls :'',tooltip:''});
        	operationItems.push({
        	   iconCls : 'edit',
               tooltip: '<%=UnieapConstants.getMessage("comm.edit")%>',
               handler:function(grid, rowIndex, colIndex)
               {	
               		selectedRecord = grid.getStore().getAt(rowIndex);
            		showForm('Modify',selectedRecord);
               }
           });
       	}
       	var selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single',listeners:{
  			select:function(model,record,index){
  				roleGridStore.reload();
	  			}
			}
		});
    	
        var datagrid = Ext.create('Ext.grid.Panel', 
               {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
       	   	 	selModel:selModel,
          	   	store : gridstore,
       	   	   	columns:
       	   	   	[
       	   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:80,items:operationItems},
       	   	   		{ text: "<%=UnieapConstants.getMessage("mdm.user.display.userCode")%>", dataIndex: 'userCode',sortable: true,width:120},
       	   	   		{ text: "<%=UnieapConstants.getMessage("mdm.user.display.userName")%>", dataIndex: 'userName', sortable: true,width:120},
       	   	   		{ text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>",dataIndex: 'enableDesc',sortable: false},
       	   	   		{ text: "<%=UnieapConstants.getMessage("comm.createDate")%>",width: 150, dataIndex: 'createDate',sortable: false},
       	   	   		{ text: "<%=UnieapConstants.getMessage("comm.modifyDate")%>",width: 150, dataIndex: 'modifyDate',sortable: false},
       	   	   		{ text: "<%=UnieapConstants.getMessage("comm.remark")%>", dataIndex: 'remark',width: 200, sortable: false}
       	   	   	],
	       	   	tbar:[queryform],
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
	                        	{ xtype:'hiddenfield', name:'locked'},
	                        	{ xtype:'hiddenfield', name:'expired'},
	                        	{ xtype:'hiddenfield', name:'createBy'},
	                        	{ xtype:'hiddenfield', name:'modifyBy'},
	                        	{ xtype:'hiddenfield', name:'createDatetime'},
	                        	{ xtype:'hiddenfield', name:'modifyDatetime'},
	                        	{ xtype:'textfield',labelWidth:80, width:350,maxLength:45, name:'userCode',fieldLabel:'<%=UnieapConstants.getMessage("mdm.user.display.userCode")%>',allowBlank:false,
	                        		validateOnChange:false, validateOnBlur :true,
								    validator :function(value){
								    			var error = true; 
								    			if(operType == 'Modify' || value==''||value ==null){
								    				return true;
								    			}
									    		Ext.Ajax.request({
									                url: 'MdmController.do?method=userDeal',
									                params:{'operType':'checkExist','userCode':value},
									                success: function(response, opts){
									                	var result = Ext.JSON.decode(response.responseText);
									                    if(result.isSuccess == 'success'){
									                		error = true; 
									                    }else{
									                    	error = result.message;
									                    }
									                },
									                failure: function(response, opts){
									                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:response.responseText,
									                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
									                }
									             });
								             return error;
								    	}
	                        	},
	                        	{ xtype:'textfield',labelWidth:80, width:350,maxLength:45,name:'userName', fieldLabel:'<%=UnieapConstants.getMessage("mdm.user.display.userName")%>', allowBlank:false},
	                        	{ xtype:'textfield',labelWidth:80, width:350,maxLength:45,name:'password', fieldLabel:'<%=UnieapConstants.getMessage("mdm.user.display.password")%>', allowBlank:false},
	                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, editable:false,allowBlank:false,
	                                name:'enable',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'Y',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._activeFlag})
								},
	                        	{ xtype:'textareafield',labelWidth:80, width:350,maxLength:255, name:'remark',fieldLabel:'<%=UnieapConstants.getMessage("comm.remark")%>',growMin:60,growMax:100,allowBlank:true}
	                        ]
	                    },
	                    {xtype:'fieldset',title:'<%=UnieapConstants.getMessage("comm.data.detail")%>',
	                        items:
	                        [
		                        { xtype:'textfield',labelWidth:80, width:350,maxLength:45,name:'email',fieldLabel: '<%=UnieapConstants.getMessage("mdm.user.display.email")%>',vtype: 'email'},
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
	                        		 if (form.isValid()){
		                                 form.submit({
		                                     clientValidation: true,
		                                     method: 'POST',
		                                     params:{'operType':operType},
		                                     url: 'MdmController.do?method=userDeal',
		                                     success: function(form, action) {
		                                    	var result = Ext.JSON.decode(action.response.responseText);
							                    if(result.isSuccess == 'failed'){
							                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
			                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
							                    }else{
	 		                                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.save")%>',fn: showResult,
		 		                               			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
							                    }
		                                     },
		                                     failure: function(form, action){
		                                    	 Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:action.response.responseText,
		                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		                                     }
		                                 });
		                        	 }
	                        	 }
	                        }
	                    }
                    ]
                });
                dataWin = Ext.widget('window', 
                { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height:320, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'userCode' });
            }
            if(operType=='Add'){
            	dataForm.getForm().reset();
            	dataWin.show();
            	dataForm.getForm().findField('userCode').setReadOnly(false);
            	dataForm.getForm().findField('userCode').inputEl.removeCls('readonly_field');
            	dataForm.getForm().findField('password').show();
            }else if(operType=='Modify'){
            	dataWin.show();
            	if(UnieapButton.User_Password==null){
            		dataForm.getForm().findField('password').hide();
            	}else{
            		dataForm.getForm().findField('password').show();
            	}
            	dataForm.getForm().findField('userCode').setReadOnly(true);
            	dataForm.getForm().findField('userCode').inputEl.addCls('readonly_field');
            	dataForm.getForm().setValues(selectedRecord.data);
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
        
        Ext.define('roleModel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'userRoleId','userId','roleId','roleCode','roleName','createDate','modifyDate','modifyBy','createBy','remark','activeFlag','activeFlagDesc'
            ],
            idProperty: 'roleId'
        });
    	var roleGridStore = Ext.create('Ext.data.Store', {
            model: 'roleModel',
            pageSize: 15,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'MdmController.do?method=userRoleGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'roleCode', direction: 'ASC'}]
        });
    	roleGridStore.on('beforeload', function (store, options){
    		var rec = datagrid.getSelectionModel().getSelection();
    		var userId = -1;
    		if(rec.length>0){
    			userId = rec[0].get("userId");
    		}
            Ext.apply(store.proxy.extraParams,{userId:userId});
        });
    	var roleSelModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single',
    		listeners:{
  				select:function(model,record,index){
  					 dicTreePanel.getStore().load();
  				}
			}
		});
    	var roleUserOperationItems = [];
    	var selectedUserRoleRecord;
    	if(UnieapButton.User_Role_Delete!=null&&UnieapButton.User_Role_Delete.abled==true){
    		roleUserOperationItems.push({iconCls :'',tooltip:''});
    		roleUserOperationItems.push({
	        	iconCls :'delete',
	           	tooltip: '<%=UnieapConstants.getMessage("comm.delete")%>',
	           	handler:function(grid, rowIndex, colIndex){	
	           		selectedUserRoleRecord = grid.getStore().getAt(rowIndex);
		            Ext.MessageBox.confirm('<%=UnieapConstants.getMessage("comm.title.confirm")%>', '<%=UnieapConstants.getMessage("comm.delete.confirm")%>', removeRoleDatas);
	           	}
	        });
    	}
    	var roleDatagrid = Ext.create('Ext.grid.Panel', 
    	        {layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
    		   	 	selModel:roleSelModel,title: '<%=UnieapConstants.getMessage("mdm.role.title.list")%>',
    	   	   		store : roleGridStore,
	    	   	   	listeners:{
		 		   		afterRender:function(thisForm, options){
				        	if(UnieapButton.User_Role_Add!=null&&UnieapButton.User_Role_Add.abled== true){
				        		Ext.getCmp('User_Role_Add').show();
				        	}
			            }
			        },
    		   	   	columns:
    		   	   	[
						{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:80,items:roleUserOperationItems},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleId")%>",dataIndex: 'roleId',width:60},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleCode")%>", dataIndex: 'roleCode',width:120},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleName")%>", dataIndex: 'roleName',width:120},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>",dataIndex: 'activeFlagDesc',sortable: false,width:60}
    		   	   	],
    	 		   	tbar:[{ pressed :true,iconCls:'add',
    		             		tooltip:'<%=UnieapConstants.getMessage("comm.add")%>',text:'<%=UnieapConstants.getMessage("comm.add")%>',xtype:'button',id:'User_Role_Add',hidden:true,
    		            		handler : function(){chooseShowForm();}
    		    	}]
    	        });
    	function removeRoleDatas(btn){
        	if(btn=='yes'){
	        	var userRoleId= selectedUserRoleRecord.get("userRoleId");
	        	Ext.Ajax.request({
	                url: 'MdmController.do?method=userDeal',
	                params:{'operType':"User_Role_Delete","userRoleId":userRoleId},
	                success: function(response, opts){
	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.save")%>',
                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
	                	roleGridStore.reload();
	                },
	                failure: function(response, opts){
	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:response.responseText,
                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                }
	             });
        	}
        }
    	/***choose role list*****************/
    	var chooseUserRoleStore = Ext.create('Ext.data.Store', {
	          model: 'roleModel',
	          pageSize: 1000,
	          remoteSort: true,
	          proxy: 
	          { type: 'ajax', url: 'MdmController.do?method=chooseUserRoleGrid',
	              reader: 
	              {root: 'rows', totalProperty: 'totalCount'},
	              simpleSortMode: false
	          },
	          sorters: [{ property: 'roleCode', direction: 'ASC'}]
	      });
    	chooseUserRoleStore.on('beforeload', function (store, options){
				var rec = datagrid.getSelectionModel().getSelection();
				var userId = -1;
				if(rec.length>0){
					userId = rec[0].get("userId");
	    		}
				Ext.apply(store.proxy.extraParams,{userId:userId});
        });
    	var chooseSelModel = Ext.create('Ext.selection.CheckboxModel',{mode:'multi'});
    	var choosegrid = Ext.create('Ext.grid.Panel', {
	        store: chooseUserRoleStore,
	        selModel:chooseSelModel,
	        columnLines: true,
	        collapsible: true,
	        multiSelect: true,
	        forceFit: true,
	        border: false,
	        header: false,
	        columns: 
	        	[
	            	{text: '<%=UnieapConstants.getMessage("mdm.role.display.roleId")%>',width: 60,sortable : false,dataIndex: 'roleId'},
	            	{text: '<%=UnieapConstants.getMessage("mdm.role.display.roleCode")%>',width: 120,sortable : false,dataIndex: 'roleCode'},
	            	{text: '<%=UnieapConstants.getMessage("mdm.role.display.roleName")%>',width: 120,sortable : false,dataIndex: 'roleName'}
	            ],
	        tbar:
	        	[
					{ xtype:'button',iconCls :'delete',text:'<%=UnieapConstants.getMessage("comm.cancel")%>',iconAlign: 'left',pressed :true,
					    handler: function (){
					    	chooseDataWin.hide();
					    }
					},
					{ xtype:'button',id:"role_save",iconCls :'save',text:'<%=UnieapConstants.getMessage("comm.submit")%>',iconAlign: 'left',pressed :true,
					    handler: function (){
					    	var records = choosegrid.getSelectionModel().getSelection();
					    	if(records.length==0){
					    		Ext.MessageBox.show({
				        	           title: '<%=UnieapConstants.getMessage("comm.tipInfo")%>',
				        	           msg: '<%=UnieapConstants.getMessage("mdm.role.select.oneRoleRecord")%>',
				        	           buttons: Ext.MessageBox.OK,
				        	           icon: Ext.MessageBox.INFO
				        	      });
					    		return;
					    	}
					    	var recUser = datagrid.getSelectionModel().getSelection();
					    	if(recUser.length==0){
				        		Ext.MessageBox.show({
				        	           title: '<%=UnieapConstants.getMessage("comm.tipInfo")%>',
				        	           msg: '<%=UnieapConstants.getMessage("mdm.role.select.oneUserRecord")%>',
				        	           buttons: Ext.MessageBox.OK,
				        	           icon: Ext.MessageBox.INFO
				        	       });
				        		chooseDataWin.hide();
				        	}else{
				        		var userId = recUser[0].get("userId");
				        		var datas = [];
								Ext.Array.each(records, function(record){
									datas.push(record.data); 
								});
						    	Ext.Ajax.request({
					                url: 'MdmController.do?method=assignUserRole',
					                params:{'userId':userId,'datas':Ext.JSON.encode(datas)},
					                success: function(response, opts){
					                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.save")%>',
				                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
					                	roleGridStore.load({params:{userId:userId}});
					                	chooseDataWin.hide();
					                },
					                failure: function(response, opts){
					                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:response.responseText,
					                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
					                }
					             });
				        	}
					    	
					    }
					}
	             ]
	    });
    	var chooseDataWin = null;
  		function chooseShowForm(){
  			var rec = datagrid.getSelectionModel().getSelection();
  			if(rec.length==0){
        		Ext.MessageBox.show({
        	           title: '<%=UnieapConstants.getMessage("comm.tipInfo")%>',
        	           msg: '<%=UnieapConstants.getMessage("comm.select.oneRecord")%>',
        	           buttons: Ext.MessageBox.OK,
        	           icon: Ext.MessageBox.INFO
        	       });
        	}else{
        		chooseUserRoleStore.load({params:{userId:rec[0].get("userId")}});
	  			if (chooseDataWin==null){
	  				chooseDataWin = new Ext.window.Window({
	  			        title: '<%=UnieapConstants.getMessage("mdm.role.select.title.list")%>',
	  			        modal: true,closeAction: 'hide',width: 400, height:300, layout: 'fit',
	  			        items: choosegrid
	  			    });
	  				chooseDataWin.show();
	  			}else{
	  				chooseDataWin.show();
	  			}
        	}
  		}
    	
        /**tree panle***/
    	Ext.define('DataTree', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields: [
				{name: 'id',     type: 'string'},
				{name: 'text',     type: 'string'},
                {name: 'parentId',  type: 'string'},
                {name: 'extendAttri'}
            ]
        });
   	 	var dicTreestore = Ext.create('Ext.data.TreeStore', {
            model: 'DataTree',
            autoLoad : false,
            proxy: {
            	type: 'ajax',
            	reader: 'json',
                url: 'MdmController.do?method=getRoleDicTreeData'
            },
            nodeParam : "id",
            root:{  
                id:1,  
                text:'Root',  
                leaf:false,  
                expanded:false  
            },
            folderSort: true
        });
   	 
	   	var dicTreePanel = Ext.create('Ext.tree.Panel', {
	   		title: '<%=UnieapConstants.getMessage("mdm.dic.data.title.list")%>',
	   		layout:'fit',
	        collapsible: false,
	        useArrows: true,
	        rootVisible: true,
	        store: dicTreestore,
	        //multiSelect: false,
	        listeners:{
	        	beforeload: function (ds, opration, opt){
	        		var rec = roleDatagrid.getSelectionModel().getSelection();
            		if(rec.length>0){
		        		opration.params.roleId = rec[0].get('roleId'); 
            		}else{
            			opration.params.roleId = -1;
            		}
	        	}
	        }
	   	})
        
       
        
        var resourceTabPanel = Ext.create('Ext.tab.Panel',{
	     	renderTo:'resourceTab',activeTab:0,layout: 'fit',
	        bodyStyle: 'padding:0px',
	        items:[
	               	{
	            	   xtype: 'panel',title:'<%=UnieapConstants.getMessage("comm.data")%>',closable: false,
	            	   items : [
		            	            {
		            	            	layout: 'column',
		            	                items: [{
		            	                    columnWidth: 1/2,
		            	                    padding: '5 0 5 5',
		            	                    items:[roleDatagrid]
		            	                },{
		            	                    columnWidth: 1/2,
		            	                    padding: '5 0 5 5',
		            	                    items:[dicTreePanel]
		            	                }]
		            	            }
	            	            ]
	            	}
               ]
	   });
	});
    </script>
</head>
<body>
    <div id="datagrid"></div>
    <div id="resourceTab"></div>
</body>
</html>
