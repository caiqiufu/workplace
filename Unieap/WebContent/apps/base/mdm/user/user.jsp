<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>User</title>
    <script type="text/javascript">
    Ext.onReady(function(){
    	Ext.tip.QuickTipManager.init();
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'userId','userCode','userName','enable','enableDesc','expired','expiredDesc','locked','lockedDesc','createDate','modifyDate','modifyBy','createBy','remark','email'
            ],
            idProperty: 'userId'
        });
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy: 
            { type: 'ajax', url: 'MdmController.do?method=userGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: 
            [
            	{ property: 'userCode', direction: 'ASC'}
            ]
        });
    	var operationItems = [];
    	var selectedRecord;
    	var queryPara;
    	if(UnieapButton.User_Modify!=null&&UnieapButton.User_Modify.abled==true){
	        operationItems.push({iconCls :'',tooltip:''});
	   		operationItems.push({
	   		   iconCls : 'edit',
	      	   tooltip: '<%=UnieapConstants.getMessage("comm.modify")%>',id:'User_Modify',
	            handler:function(grid, rowIndex, colIndex){	
	          	   	selectedRecord = grid.getStore().getAt(rowIndex);
	         		showForm('Modify',selectedRecord);
	            }
	         });
        }
    	if(UnieapButton.User_Delete!=null&&UnieapButton.User_Delete.abled==true){
	        operationItems.push({iconCls :'',tooltip:''});
	        operationItems.push({
	           iconCls :'delete',
	           tooltip: '<%=UnieapConstants.getMessage("comm.delete")%>',
	           handler:function(grid, rowIndex, colIndex){	
	         	   selectedRecord = grid.getStore().getAt(rowIndex);
	         	   Ext.MessageBox.confirm('<%=UnieapConstants.getMessage("comm.title.confirm")%>', '<%=UnieapConstants.getMessage("comm.delete.confirm")%>', removeDatas);
	           }
	        });
    	}
    	var selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single',listeners:{
		  		select:function(model,record,index){
		  				rolegridstore.load({params:{userId:record.get('userId')}});
		  			}
	  			}
	  		});
    	var datagrid = Ext.create('Ext.grid.Panel', 
    	        {el : 'datadicgroupgrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
    		   	 	selModel:selModel,title: '<%=UnieapConstants.getMessage("mdm.user.title.list")%>',
    		   	 	listeners:{
    		   	 		afterRender:function(thisForm, options){
				        	if(UnieapButton.User_Add!=null&&UnieapButton.User_Add.abled== true){
				        		Ext.getCmp('User_Add').show();
				        	}
			        		this.keyNav = Ext.create('Ext.util.KeyNav', this.el,{
			        			enter: function(){
			        				query();
			        			}
			        		})
			            }
			        },
    	   	   		store : gridstore,
    		   	   	columns:
    		   	   	[
    		   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:80,items:operationItems},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.user.display.userId")%>",dataIndex: 'userId',width:80},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.user.display.userCode")%>", dataIndex: 'userCode'},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.user.display.userName")%>", dataIndex: 'userName'},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.user.display.email")%>", dataIndex: 'email',sortable: false,width:120,
	    		   	   		renderer: function(value,metaData,record,colIndex,store,view){
		    		   	         metaData.tdAttr ='data-qtip="'+value+'"';
		    		   	         return value;
		    		   	    }
    		   	   		},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.user.display.enable")%>",dataIndex: 'enableDesc',sortable: false,width:80},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.user.display.expired")%>",dataIndex: 'expiredDesc',sortable: false,width:80},
    		   	 		{ text: "<%=UnieapConstants.getMessage("mdm.user.display.locked")%>",dataIndex: 'lockedDesc',sortable: false,width:80},
    		   	 		{ text: "<%=UnieapConstants.getMessage("comm.createDate")%>",dataIndex: 'createDate',width:120,
	    		   	 		renderer: function(value,metaData,record,colIndex,store,view){
		    		   	         metaData.tdAttr ='data-qtip="'+value+'"';
		    		   	         return value;
		    		   	    }	
    		   	 		},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.createBy")%>",dataIndex: 'createBy',sortable: false,width:60},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.modifyDate")%>",dataIndex: 'modifyDate',width:120,sortable: false,
	    		   	   		renderer: function(value,metaData,record,colIndex,store,view){
		    		   	         metaData.tdAttr ='data-qtip="'+value+'"';
		    		   	         return value;
		    		   	    }		
    		   	   		},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.modifyBy")%>",dataIndex: 'modifyBy',sortable: false,width:60},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.remark")%>", dataIndex: 'remark',sortable: false,
	    		   	   		renderer: function(value,metaData,record,colIndex,store,view){
		    		   	         metaData.tdAttr ='data-qtip="'+value+'"';
		    		   	         return value;
		    		   	    }	
    		   	   		}
    		   	   	],
	    		   	tbar:[{ pressed :true,iconCls:'add',
	                		tooltip:'<%=UnieapConstants.getMessage("comm.add")%>',text:'<%=UnieapConstants.getMessage("comm.add")%>',xtype:'button',id:'User_Add',hidden:true,
	 		            handler : function(){showForm('Add',null);}
	 		    	},'-',
   		   	        {xtype: 'fieldcontainer',layout: 'hbox', id:'queryFields',
					    items: 
					    [
					        { xtype:'textfield',labelWidth:80, width:200,labelAlign: 'right',name: 'userCode',fieldLabel: '<%=UnieapConstants.getMessage("mdm.user.display.userCode")%>'},
					        { xtype:'textfield',labelWidth:80, width:200,labelAlign: 'right',name:'userName',fieldLabel: '<%=UnieapConstants.getMessage("mdm.user.display.userName")%>'}
					    ]
					 },'-',
					 { xtype:'button',text:'<%=UnieapConstants.getMessage("comm.search")%>',iconAlign:'right',pressed :true,icon:'/Unieap/unieap/js/common/images/search-trigger.png',
    	                handler: function (){
    	                	query();
    	                }
	                },'-',
	                { xtype:'button',text:'<%=UnieapConstants.getMessage("comm.reset")%>',iconAlign: 'right',pressed :true,icon :'/Unieap/unieap/js/common/images/clear-trigger.gif',
    	                handler: function ()
    	                {
    	                	var queryFields = Ext.getCmp('queryFields').items.items;
    	                	queryFields[0].setValue('');
    	                	queryFields[1].setValue('');
    	                }
	                }],
    	    	   	bbar:new Ext.PagingToolbar(
    	    	   	{ store : gridstore,displayInfo: true})
    	        	
    	        });
    	   datagrid.render();
 	       gridstore.loadPage(1);
 	       function query(){
 	    	  var queryFields = Ext.getCmp('queryFields').items.items;
          	  var userCode=queryFields[0].getValue(); 
              var userName=queryFields[1].getValue(); 
              queryPara = 
              	{
              		userCode:userCode,
              		userName:userName
                 };
              gridstore.load({params:queryPara});
 	       }
 	       var dataWin = null;
 	       var dataForm = null;
 	       var operType = '';
 	       function showForm(status,selectedRecord){
 	        	operType = status;
 	            if (dataWin==null){
 	            	dataForm = Ext.widget('form',
 	            	{
 	            		id:'form-statusbar',
 	                    defaults:{labelAlign: 'left', labelWidth: 90, anchor: '100%'},
 	                    bodyPadding:5,
 	                    items:
 	                    [
 	                    	{xtype:'fieldset', title:'<%=UnieapConstants.getMessage("comm.data")%>',
 		                        items:
 		                        [
 		                        	{ xtype:'textfield',hidden: true, name:'userId'},
 		                        	{ xtype:'textfield',hidden: true, name:'createDate',width:120},
 		                        	{ xtype:'textfield',hidden: true, name:'modifyDate',width:120},
 		                        	{ xtype:'textfield',hidden: true, name:'createBy'},
 		                        	{ xtype:'textfield',hidden: true, name:'modifyBy'},
 		                        	{ xtype:'textfield',labelWidth:80, width:350, name:'userCode',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mdm.user.display.userCode")%>',maxLength:45,allowBlank:false},
 		                        	{ xtype:'textfield',labelWidth:80, width:350, name:'userName',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mdm.user.display.userName")%>',maxLength:128,allowBlank:false},
 		                        	{ xtype:'textfield',labelWidth:80, width:350, name:'email',fieldLabel:'<%=UnieapConstants.getMessage("mdm.user.display.email")%>',vtype: 'email',allowBlank:true},
 		                        	{ xtype:'combo', forceSelection: true, emptyText: '...',editable:false,allowBlank:false,
		                                fieldLabel:'<%=UnieapConstants.getMessage("mdm.user.display.enable")%>',labelWidth:80, width:350,name:'enable',displayField:'dicName',valueField:'dicCode',
		                                store:Ext.create('Ext.data.Store', 
		                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._1001Opt})
									},
									{ xtype:'combo', forceSelection: true, emptyText: '...',editable:false,allowBlank:false,
		                                fieldLabel:'<%=UnieapConstants.getMessage("mdm.user.display.expired")%>',labelWidth:80, width:350,name:'locked',displayField:'dicName',valueField:'dicCode',
		                                store:Ext.create('Ext.data.Store', 
		                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._1001Opt})
									},
									{ xtype:'combo', forceSelection: true, emptyText: '...',editable:false,allowBlank:false,
		                                fieldLabel:'<%=UnieapConstants.getMessage("mdm.user.display.locked")%>',labelWidth:80, width:350,name:'expired',displayField:'dicName',valueField:'dicCode',
		                                store:Ext.create('Ext.data.Store', 
		                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._1001Opt})
									},
 		                        	{ xtype:'textareafield',labelWidth:80, width:350, name:'Remark',fieldLabel:'<%=UnieapConstants.getMessage("comm.remark")%>',maxLength:255,growMin:60,growMax:100,allowBlank:true}
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
 	                    ]
 	                });
 	                dataWin = Ext.widget('window', 
 	                { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height: 350, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'userName' });
 	            }
 	            if(operType=='Add'){
 	            	dataForm.getForm().reset();
 	            	dataForm.getForm().setValues({enable:'1',locked:'0',expired:'0'});
 	            	dataWin.show();
 	            }else if(operType=='Modify'){
 	            	dataWin.show();
 	            	dataForm.getForm().setValues(selectedRecord.data);
 	            }else{
 	            	dataWin.show();
 	            }
 	    }
      	function removeDatas(btn){
        	if(btn=='yes'){
	        	var userId= selectedRecord.get("userId");
	        	Ext.Ajax.request({
	                url: 'MdmController.do?method=userDeal',
	                params:{'operType':"Delete","userId":userId},
	                success: function(response, opts) {
	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.delete")%>',
                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
	                	gridstore.reload();
	                },
	                failure: function(response, opts) {
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

 	    
		Ext.define('rolemodel', {
		     extend: 'Ext.data.Model',
		     fields:
		     [
		     	'userRoleId','userId','roleId','roleCode','roleName'
		     ],
		     idProperty: 'roleId'
		 });
	 	 var rolegridstore = Ext.create('Ext.data.Store', {
	          model: 'rolemodel',
	          pageSize: 15,
	          remoteSort: false,
	          proxy: 
	          { type: 'ajax', url: 'MdmController.do?method=userRoleGrid',
	              reader: 
	              {root: 'rows', totalProperty: 'totalCount'},
	              simpleSortMode: false
	          },
	          sorters: 
	          [
	          	{ property: 'roleCode', direction: 'DESC'}
	          ]
	      });
	 	 rolegridstore.on('beforeload', function (store, options){
			var rec = datagrid.getSelectionModel().getSelection();
	        Ext.apply(store.proxy.extraParams,{'userId':rec[0].get("userId")});
	     });
		var roleOperationItems = [];
		var roleSelectedRecord;
		if(UnieapButton.User_Role_Delete!=null&&UnieapButton.User_Role_Delete.abled==true){
			roleOperationItems.push({iconCls :'',tooltip:''});
			roleOperationItems.push({
				iconCls :'delete',
		     	tooltip: '<%=UnieapConstants.getMessage("comm.delete")%>',
			 	handler:function(grid, rowIndex, colIndex){	
			  		roleSelectedRecord = grid.getStore().getAt(rowIndex);
			  		Ext.MessageBox.confirm('<%=UnieapConstants.getMessage("comm.title.confirm")%>', '<%=UnieapConstants.getMessage("comm.delete.confirm")%>', roleRemoveDatas);
			    }
		   });
		}
     	function roleRemoveDatas(btn){
	    	if(btn=='yes'){
	        	var userRoleId= roleSelectedRecord.get("userRoleId");
	        	Ext.Ajax.request({
	                url: 'MdmController.do?method=deleteUserRole',
	                params:{'operType':"Delete","userRoleId":userRoleId},
	                success: function(response, opts){
	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.ststus")%>',msg:'<%=UnieapConstants.getMessage("comm.success.delete")%>',
             			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
	                	var recUser = datagrid.getSelectionModel().getSelection();
	                	rolegridstore.load({params:{userId:recUser[0].get("userId")}});
	                },
	                failure: function(response, opts){
	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.ststus")%>',msg:response.responseText,
	                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                }
	             });
	    	}
	    }
     
  	  var roleSelModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single'});
  	  var roledatagrid = Ext.create('Ext.grid.Panel', 
  	        {el : 'datadicdatagrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
  		   	 	selModel:roleSelModel,title: '<%=UnieapConstants.getMessage("mdm.role.title.list")%>',
  	   	   		store : rolegridstore,
  		   	   	columns:
  		   	   	[
  		   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:80,items:roleOperationItems},
  		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleId")%>",dataIndex: 'roleId',sortable: false},
  		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleCode")%>", dataIndex: 'roleCode',sortable: false},
  		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleName")%>",dataIndex: 'roleName',sortable: false}
  		   	   	],
    		   	tbar:[{ pressed :true,iconCls:'add',
                		tooltip:'<%=UnieapConstants.getMessage("comm.add")%>',text:'<%=UnieapConstants.getMessage("comm.add")%>',xtype:'button',
 		            handler : function(){chooseShowForm('Add');}
 		    	}]
  	        });
  	      roledatagrid.render();
 		  var choosestore = Ext.create('Ext.data.Store', {
	          model: 'rolemodel',
	          remoteSort: true,
	          proxy: 
	          { type: 'ajax', url: 'MdmController.do?method=chooseUserRoleGrid',
	              reader: 
	              {root: 'rows', totalProperty: 'totalCount'},
	              simpleSortMode: true
	          },
	          sorters: 
	          [
	          	{ property: 'roleCode', direction: 'DESC'}
	          ]
	      });
 		 choosestore.on('beforeload', function (store, options){
 				var rec = datagrid.getSelectionModel().getSelection();
	            Ext.apply(store.proxy.extraParams,{userId:rec[0].get("userId")});
	        });
 		 var chooseselModel = Ext.create('Ext.selection.CheckboxModel',{mode:'multi'});
 		 var choosegrid = Ext.create('Ext.grid.Panel', {
	        store: choosestore,
	        selModel:chooseselModel,
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
					                	rolegridstore.load({params:{userId:userId}});
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
  		function chooseShowForm(status){
  			var rec = datagrid.getSelectionModel().getSelection();
  			if(rec.length==0){
        		Ext.MessageBox.show({
        	           title: '<%=UnieapConstants.getMessage("comm.tipInfo")%>',
        	           msg: '<%=UnieapConstants.getMessage("comm.select.oneRecord")%>',
        	           buttons: Ext.MessageBox.OK,
        	           icon: Ext.MessageBox.INFO
        	       });
        	}else{
        		choosestore.load({params:{userId:rec[0].get("userId")}});
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
	    });
    </script>
</head>
<body>
    <div id="datadicgroupgrid"></div>
    <div id="datadicdatagrid"></div>
</body>
</html>
