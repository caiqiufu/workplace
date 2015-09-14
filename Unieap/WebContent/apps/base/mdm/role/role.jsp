<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>User</title>
    <script type="text/javascript">
    Ext.onReady(function(){
    	Ext.tip.QuickTipManager.init();
    	
    	Ext.define('roleModel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'roleId','roleCode','roleName','createDate','modifyDate','modifyBy','createBy','remark','activeFlag','activeFlagDesc'
            ],
            idProperty: 'roleId'
        });
    	var roleGridStore = Ext.create('Ext.data.Store', {
            model: 'roleModel',
            pageSize: 15,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'MdmController.do?method=roleGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
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
    	/**
    	if(UnieapButton.Role_Delete!=null&&UnieapButton.Role_Delete.abled==true){
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
    	*/
    	var selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single',listeners:{
	  			select:function(model,record,index){
	  				//dicTreestore.reload();
	  				 dicTreePanel.getStore().load();
	  				 //dicTreePanel.getView().refresh();
	  			}
  			}
  		});
    	
    	var datagrid = Ext.create('Ext.grid.Panel', 
	        {layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
		   	 	selModel:selModel,title: '<%=UnieapConstants.getMessage("mdm.role.title.list")%>',
		 		listeners:{
		 		   		afterRender:function(thisForm, options){
			        	if(UnieapButton.Role_Add!=null&&UnieapButton.Role_Add.abled== true){Ext.getCmp('Role_Add').show();}
		            }
		        },
	   	   		store : roleGridStore,
		   	   	columns:
		   	   	[
		   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:80,items:operationItems},
		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleId")%>",dataIndex: 'roleId',width:60},
		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleCode")%>", dataIndex: 'roleCode',width:120},
		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleName")%>", dataIndex: 'roleName',width:120},
		   	   		{ text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>",dataIndex: 'activeFlagDesc',sortable: false,width:60}
		   	   	],
	 		   	tbar:[{ pressed :true,iconCls:'add',
		             		tooltip:'<%=UnieapConstants.getMessage("comm.add")%>',text:'<%=UnieapConstants.getMessage("comm.add")%>',xtype:'button',id:'Role_Add',hidden:true,
		            handler : function(){showForm('Add',null);}
		    	}],
	    	   	bbar:new Ext.PagingToolbar(
	    	   	{ store : roleGridStore,displayInfo: true})
	        });
    	roleGridStore.loadPage(1);
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
		                        	{ xtype:'textfield',labelWidth:80, width:350, name:'roleCode',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mdm.role.display.roleCode")%>',maxLength:45,allowBlank:false},
		                        	{ xtype:'textfield',labelWidth:80, width:350, name:'roleName',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mdm.role.display.roleName")%>',maxLength:128,allowBlank:false},
		                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:true,
		                                name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'1',
		                                store:Ext.create('Ext.data.Store', 
		                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._1001})
									},
		                        	{ xtype:'textareafield',labelWidth:80, width:350, name:'remark',fieldLabel:'<%=UnieapConstants.getMessage("comm.remark")%>',maxLength:255,growMin:60,growMax:100,allowBlank:true}
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
	                { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height: 260, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'roleName' });
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
   		function removeDatas(btn){
	     	if(btn=='yes'){
		        	var roleId= selectedRecord.get("roleId");
		        	Ext.Ajax.request({
		                url: 'MdmController.do?method=roleDeal',
		                params:{'operType':"Delete","roleId":roleId},
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
		 function showResult(btn){
	     	dataWin.hide();
	     	roleGridStore.reload();
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
	        		var rec = datagrid.getSelectionModel().getSelection();
            		if(rec.length>0){
		        		opration.params.roleId = rec[0].get('roleId'); 
            		}
	        	},
	        	checkchange:function (node,checked,eOpts){
	        		setParentChecked(node,checked);
	        	},
	        	itemclick:function(view, record, node, index, e, eOpts){
	        		//parentnode(node);
	        	}
	        },
	        tbar:[{ pressed :true,iconCls:'save',
			        tooltip:'<%=UnieapConstants.getMessage("comm.save")%>',text:'<%=UnieapConstants.getMessage("comm.save")%>',xtype:'button',id:'Role_Dic_Add',hidden:false,
			        handler : function(){saveRoleDic();}
				}]
	   	})
    	
	   	function setParentChecked(node,checked){
	        node.set({checked:checked});
	        var parentNode = node.parentNode;
	        if(parentNode !=null&parentNode.data.id!=1){
	            var flag = false;
	            parentNode.eachChild(function(child) {
	                if(child.data.checked == true){
	                    flag = true;
	                }
	            });
	            if(checked == false){
	                if(!flag){
	                    setParentChecked(parentNode,checked);
	                }
	            }else{
	                if(flag){
	                    setParentChecked(parentNode,checked);
	                }
	            }
	         }
	    }
	   	function saveRoleDic(){
	   		var rec = datagrid.getSelectionModel().getSelection();
    		if(rec.length>0){
        		roleId = rec[0].get('roleId'); 
    		}else{
    			return false;
    		}
	   		var nodes = dicTreePanel.getChecked();
	   		var datas = [];
			Ext.Array.each(nodes, function(node){
				if(node.data.extendAttri.dicCode!=null){
					datas.push(node.data.extendAttri); 
				}
			});
			Ext.Ajax.request({
                url: 'MdmController.do?method=assignDicResource',
                params:{'roleId':roleId,'datas':Ext.JSON.encode(datas)},
                success: function(response, opts){
                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.save")%>',
            			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
                },
                failure: function(response, opts){
                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:response.responseText,
                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                }
             });
	   	}
    	var viewport = Ext.create('Ext.Viewport', {
    		el : 'datalayou',
            layout: 'column',
            items: [{
                columnWidth: 1/2,
                padding: '5 0 5 5',
                items:[datagrid]
            },{
                columnWidth: 1/2,
                padding: '5 0 5 5',
                items:[dicTreePanel]
            }]
        });
	});
    </script>
</head>
<body>
    <div id="datagrid"></div>
    <div id="resourceTab"></div>
</body>
</html>
