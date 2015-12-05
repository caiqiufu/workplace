<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>User</title>
    <script type="text/javascript">
    Ext.onReady(function(){
    	Ext.tip.QuickTipManager.init();
    	/*******************dic group begin**********************************/
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:['roleId','roleCode','roleName','createDate','modifyDate','modifyBy','createBy','remark','activeFlag','activeFlagDesc'],
            idProperty: 'roleId'
        });
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'MdmController.do?method=roleGrid',
                reader:{root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'roleCode', direction: 'ASC'}]
        });
    	var operationItems = [];
    	var selectedRecord;
    	if(UnieapButton.Role_Modify!=null&&UnieapButton.Role_Modify.abled==true){
    		operationItems.push({iconCls :'',tooltip:''});
	    	operationItems.push({
	    		iconCls : 'edit',
	     	   	tooltip: '<%=UnieapConstants.getMessage("comm.modify")%>',
	           	handler:function(grid, rowIndex, colIndex){	
	         	   	selectedRecord = grid.getStore().getAt(rowIndex);
	        		showForm('Modify',selectedRecord);
	           	}
	        });
    	}
    	if(UnieapButton.Role_Delete!=null&&UnieapButton.Role_Delete.abled==true){
	        operationItems.push({iconCls :'',tooltip:''});
	        operationItems.push({
	        	iconCls :'delete',
	           	tooltip: '<%=UnieapConstants.getMessage("comm.delete")%>',
	           	handler:function(grid, rowIndex, colIndex){	
	         	   selectedRecord = grid.getStore().getAt(rowIndex);
		           Ext.MessageBox.confirm('<%=UnieapConstants.getMessage("comm.title.confirm")%>', '<%=UnieapConstants.getMessage("comm.confirm.delete")%>', removeDatas);
	           	}
	        });
    	}
    	var selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single',listeners:{
		  		select:function(model,record,index){
		  				dicgridstore.load({params:{roleId:record.get('roleId')}});
		  			}
	  			}
	  		});
    	var datagrid = Ext.create('Ext.grid.Panel', 
    	        {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
    		   	 	selModel:selModel,title: '<%=UnieapConstants.getMessage("mdm.role.title.list")%>',
	    		   	listeners:{
	    		   		afterRender:function(thisForm, options){
	 			        	if(UnieapButton.Role_Add!=null&&UnieapButton.Role_Add.abled== true){Ext.getCmp('Role_Add').show();}
	 		            }
	 		        },
    	   	   		store : gridstore,
    		   	   	columns:
    		   	   	[
    		   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:80,items:operationItems},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleId")%>",dataIndex: 'roleId'},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleCode")%>", dataIndex: 'roleCode'},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleName")%>", dataIndex: 'roleName'},
    		   	 		{ text: "<%=UnieapConstants.getMessage("comm.createDate")%>",dataIndex: 'createDate',width:120},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.createBy")%>",dataIndex: 'createBy',sortable: false},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.modifyDate")%>",dataIndex: 'modifyDate',width:120,sortable: false},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.modifyBy")%>",dataIndex: 'modifyBy',sortable: false},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>",dataIndex: 'activeFlagDesc',sortable: false},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.remark")%>", dataIndex: 'remark',sortable: false}
    		   	   	],
	    		   	tbar:[{ pressed :true,iconCls:'add',
	                		tooltip:'<%=UnieapConstants.getMessage("comm.add")%>',text:'<%=UnieapConstants.getMessage("comm.add")%>',xtype:'button',id:'Role_Add',hidden:true,
	 		            handler : function(){showForm('Add',null);}
	 		    	}],
    	    	   	bbar:new Ext.PagingToolbar(
    	    	   	{ store : gridstore,displayInfo: true})
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
 	                    	{xtype:'fieldset', title:'<%=UnieapConstants.getMessage("comm.data")%>',
 		                        items:
 		                        [
 		                        	{ xtype:'textfield',hidden: true, name:'roleId'},
 		                        	{ xtype:'textfield',hidden: true, name:'createDate'},
 		                        	{ xtype:'textfield',hidden: true, name:'modifyDate'},
 		                        	{ xtype:'textfield',hidden: true, name:'createBy'},
 		                        	{ xtype:'textfield',hidden: true, name:'modifyBy'},
 		                        	{ xtype:'textfield',labelWidth:80, width:350, name:'roleCode',fieldLabel:'<%=UnieapConstants.getMessage("mdm.role.display.roleCode")%><font color=red>*</font>',allowBlank:false},
 		                        	{ xtype:'textfield',labelWidth:80, width:350, name:'roleName',fieldLabel:'<%=UnieapConstants.getMessage("mdm.role.display.roleName")%><font color=red>*</font>',allowBlank:false},
 		                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:true,
		                                name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'1',
		                                store:Ext.create('Ext.data.Store', 
		                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._1001})
									},
 		                        	{ xtype:'textareafield',labelWidth:80, width:350, name:'remark',fieldLabel:'<%=UnieapConstants.getMessage("comm.remark")%>',growMin:60,growMax:100,allowBlank:true}
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
 		                        handler: function(){
 		                        	var form = dataForm.getForm();
 		                        	 if (form.isValid()){
 		                                 form.submit({
 		                                     clientValidation: true,
 		                                     method: 'POST',
 		                                     params:{'operType':operType},
 		                                     url: 'MdmController.do?method=roleDeal',
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
 	                { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height: 280, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'roleName' });
 	            }
 	            if(operType=='Add'){
 	            	dataForm.getForm().reset();
 	            	dataWin.show();
 	            }else if(operType=='Modify'){
 	            	dataWin.show();
 	            	dataForm.getForm().setValues(selectedRecord.data);
 	            }else{
 	            	dataWin.show();
 	            }
 	    }
      	function removeDatas(btn)
        {
        	if(btn=='yes'){
	        	var roleId= selectedRecord.get("roleId");
	        	Ext.Ajax.request({
	                url: 'MdmController.do?method=roleDeal',
	                params:{'operType':"Delete","roleId":roleId},
	                success: function(response, opts){
	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.save")%>',
                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
	                	gridstore.reload();
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
 	   /**role dic resource*****************/
 	   Ext.define('dicmodel', {
           extend: 'Ext.data.Model',
           fields:['roleResourceId','groupId','groupName','dicId','dicCode','dicName','seq'],
           idProperty: 'roleResourceId'
       });
 	  var dicgridstore = Ext.create('Ext.data.Store',{
          model: 'dicmodel',
          pageSize: 15,
          remoteSort: true,
          proxy:{ type: 'ajax', url: 'MdmController.do?method=roleDicDataGrid',
              reader:{root: 'rows', totalProperty: 'totalCount'},
              simpleSortMode: true
          },
          sorters: [{ property: 'dicCode', direction: 'ASC'} ]
      });
 	 dicgridstore.on('beforeload', function (store, options){
		var rec = datagrid.getSelectionModel().getSelection();
        Ext.apply(store.proxy.extraParams,{roleId:rec[0].get("roleId")});
     });
  	 var dicOperationItems = [];
  	 var dicSelectedRecord;
  	 if(UnieapButton.Role_Resource_Delete!=null&&UnieapButton.Role_Resource_Delete.abled==true){
  		 dicOperationItems.push({iconCls :'',tooltip:''});
	  	 dicOperationItems.push({
	  			iconCls : 'delete',
		      	tooltip: '<%=UnieapConstants.getMessage("comm.delete")%>',
		     	 handler:function(grid, rowIndex, colIndex){	
		    	  	dicSelectedRecord = grid.getStore().getAt(rowIndex);
		          	Ext.MessageBox.confirm('<%=UnieapConstants.getMessage("comm.title.confirm")%>', '<%=UnieapConstants.getMessage("comm.confirm.delete")%>', dicremoveDatas);
		      	}
	      });
  	  }
  	  var dicSelModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single'});
  	  var dicgrid = Ext.create('Ext.grid.Panel', 
  	        {   layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
  		   	 	selModel:dicSelModel,
	  		   	listeners:
		        {afterRender:function(thisForm, options){
			        	if(UnieapButton.Role_Resource_Add!=null&&UnieapButton.Role_Resource_Add.abled== true){Ext.getCmp('Role_Resource_Add').show();}
		            }
		        },
  	   	   		store : dicgridstore,
  		   	   	columns:
  		   	   	[
  		   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:80,items:dicOperationItems},
					{ text: "<%=UnieapConstants.getMessage("mdm.dic.display.groupId")%>",dataIndex: 'groupId',sortable: false},
					{ text: "<%=UnieapConstants.getMessage("mdm.dic.display.groupName")%>", dataIndex: 'groupName',sortable: true},
  		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.dic.display.dicCode")%>",dataIndex: 'dicCode',sortable: false},
  		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.dic.display.dicName")%>", dataIndex: 'dicName',sortable: true},
  		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.dic.display.groupIdmdm.dic.display.sequence")%>",dataIndex: 'seq',sortable: false}
  		   	   	],
    		   	tbar:[{ pressed :true,iconCls:'add',
                		tooltip:'<%=UnieapConstants.getMessage("comm.add")%>',text:'<%=UnieapConstants.getMessage("comm.add")%>',xtype:'button',id:'Role_Resource_Add',hidden:true,
 		            handler : function(){chooseShowForm('Add');}
 		    	}],
  	    	   	bbar:new Ext.PagingToolbar(
  	    	    	   	{ store : dicgridstore,displayInfo: true})
  	        });
        function dicremoveDatas(btn){
	    	if(btn=='yes'){
	        	var roleResourceId= dicSelectedRecord.get("roleResourceId");
	        	Ext.Ajax.request({
	                url: 'MdmController.do?method=deleteRoleResource',
	                params:{'operType':"Delete","roleResourceId":roleResourceId},
	                success: function(response, opts) 
	                {
	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.delete")%>',
             			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
	                	var role = datagrid.getSelectionModel().getSelection();
	                	dicgridstore.load({params:{roleId:role[0].get("roleId")}});
	                },
	                failure: function(response, opts) 
	                {
	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:response.responseText,
	                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                }
	             });
	    	}
	    }
	    /***Dic resource********************/
	    var choosestore = Ext.create('Ext.data.Store', {
	          model: 'dicmodel',
	          pageSize: 1000,
	          remoteSort: true,
	          proxy: 
	          { type: 'ajax', url: 'MdmController.do?method=chooseDicDataGrid',
	              reader: 
	              {root: 'rows', totalProperty: 'totalCount'},
	              simpleSortMode: false
	          },
	          sorters: [{ property: 'groupName', direction: 'ASC'}]
	      });
		 choosestore.on('beforeload', function (store, options){
				var rec = datagrid.getSelectionModel().getSelection();
            Ext.apply(store.proxy.extraParams,{roleId:rec[0].get("roleId")});
        });
		var chooseselModel = Ext.create('Ext.selection.CheckboxModel',{mode:'multi'});
 		var choosegrid = Ext.create('Ext.grid.Panel', {
	        store: choosestore,
	        selModel:chooseselModel,
	        collapsible: true,
	        multiSelect: true,
	        forceFit: true,
	        border: false,
	        header: false,
	        columns: 
	        	[
	            	{text: '<%=UnieapConstants.getMessage("mdm.dic.display.groupId")%>',width: 200,sortable : false,dataIndex: 'groupId'},
	            	{text: '<%=UnieapConstants.getMessage("mdm.dic.display.groupName")%>',width: 200,sortable : false,dataIndex: 'groupName'},
	            	{text: '<%=UnieapConstants.getMessage("mdm.dic.display.dicCode")%>',width: 200,sortable : false,dataIndex: 'dicCode'},
	            	{text: '<%=UnieapConstants.getMessage("mdm.dic.display.dicName")%>',width: 200,sortable : false,dataIndex: 'dicName'}
	            ],
	        viewConfig: {
	            stripeRows: true,
	            enableTextSelection: true
	        },
	        tbar:[
					{ xtype:'button',text:'<%=UnieapConstants.getMessage("comm.cancel")%>',iconAlign: 'right',pressed :true,
					    handler: function (){
					    	chooseDataWin.hide();
					    }
					},
					{ xtype:'button',id:"dic_save",text:'<%=UnieapConstants.getMessage("comm.submit")%>',iconAlign: 'right',pressed :true,
					    handler: function (){
					    	chooseDataWin.hide();
					    	var records = choosegrid.getSelectionModel().getSelection();
					    	if(records.length==0){
					    		Ext.MessageBox.show({
				        	           title: '<%=UnieapConstants.getMessage("comm.tipInfo")%>',
				        	           msg: '<%=UnieapConstants.getMessage("comm.select.oneRecord")%>',
				        	           buttons: Ext.MessageBox.OK,
				        	           icon: Ext.MessageBox.INFO
				        	      });
					    	}else{
						    	var role = datagrid.getSelectionModel().getSelection();
						    	if(role.length==0){
					        		Ext.MessageBox.show({
					        			   title: '<%=UnieapConstants.getMessage("comm.tipInfo")%>',
					        	           msg: '<%=UnieapConstants.getMessage("mdm.role.select.oneRoleRecord")%>',
					        	           buttons: Ext.MessageBox.OK,
					        	           icon: Ext.MessageBox.INFO
					        	       });
					        		
					        	}else{
					        		var roleId = role[0].get("roleId");
					        		var datas = [];
									Ext.Array.each(records, function(record){
										datas.push(record.data); 
									});
							    	Ext.Ajax.request({
						                url: 'MdmController.do?method=assignDicResource',
						                params:{'roleId':roleId,'datas':Ext.JSON.encode(datas)},
						                success: function(response, opts){
						                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.save")%>',
					                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
						                	dicgridstore.load({params:{roleId:roleId}});
						                },
						                failure: function(response, opts){
						                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:response.responseText,
						                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
						                }
						             });
					        	}
					    	}
					    }
					}
	             ]
	    });
 		var chooseDataWin = null;
  		function chooseShowForm(status){
  			var role = datagrid.getSelectionModel().getSelection();
  			if(role.length==0){
        		Ext.MessageBox.show({
        	           title: '<%=UnieapConstants.getMessage("comm.tipInfo")%>',
        	           msg: '<%=UnieapConstants.getMessage("comm.select.oneRecord")%>',
        	           buttons: Ext.MessageBox.OK,
        	           icon: Ext.MessageBox.INFO
        	       });
        	}else{
        		choosestore.load({params:{role:role[0].get("roleId")}});
	  			if (chooseDataWin==null){
	  				chooseDataWin = new Ext.window.Window({
	  			        title: '<%=UnieapConstants.getMessage("comm.data")%>',
	  			        modal: true,closeAction: 'hide',width: 400, height: 400, layout: 'fit',
	  			        items: choosegrid
	  			    });
	  				chooseDataWin.show();
	  			}else{
	  				chooseDataWin.show();
	  			}
        	}
  		}
	   /**Display resource****************/
	   var resourceTabPanel = Ext.create('Ext.tab.Panel',{
	     	renderTo:'resourceTab',activeTab:0,layout: 'fit',
	        bodyStyle: 'padding:0px',
	        items:[{xtype: 'panel',title:'<%=UnieapConstants.getMessage("mdm.role.dicResource")%>',items : [dicgrid],closable: false}]
	   });
	});
    </script>
</head>
<body>
    <div id="datagrid"></div>
    <div id="resourceTab"></div>
</body>
</html>
