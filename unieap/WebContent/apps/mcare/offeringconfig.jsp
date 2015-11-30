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
            	'id','categoryType','categoryTypeDesc','categoreName','categoryDesc','pictureUrl','priceDesc','activeFlag','activeFlagDesc','createDate',
            	'createBy','modifyBy','modifyDate','remark'
            ],
            idProperty: 'id'
        });
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mcareController.do?method=offerCategoryGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'categoreName', direction: 'ASC'}]
        });
    	var operationItems = [];
    	var selectedRecord;
    	if(UnieapButton.Offering_Category_Modify!=null&&UnieapButton.Offering_Category_Modify.abled==true){
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
       	var selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single',listeners:{
  			select:function(model,record,index){
  					offeringGridStore.reload();
	  			}
			}
		});
    	
        var datagrid = Ext.create('Ext.grid.Panel', 
               {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
       	   	 	selModel:selModel,
          	   	store : gridstore,
	          	listeners:{
		 		   		afterRender:function(thisForm, options){
			        	if(UnieapButton.Offering_Category_Add!=null&&UnieapButton.Offering_Category_Add.abled== true){
			        		Ext.getCmp('Offering_Category_Add').show();
			        	}
		            }
		        },
       	   	   	columns:
       	   	   	[
       	   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:80,items:operationItems},
       	   	   		{ text: "<%=UnieapConstants.getMessage("app.offeringconfig.categoryType")%>", dataIndex: 'categoryTypeDesc',sortable: true,width:120},
       	   	   		{ text: "<%=UnieapConstants.getMessage("app.offeringconfig.categoryName")%>", dataIndex: 'categoreName', sortable: true,width:120},
       	   	  		{ text: "<%=UnieapConstants.getMessage("app.offeringconfig.priceDesc")%>", dataIndex: 'priceDesc', sortable: false,width:120},
       	   	   		{ text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>",dataIndex: 'activeFlagDesc',sortable: false},
       	   	   		{ text: "<%=UnieapConstants.getMessage("comm.createDate")%>",width: 150, dataIndex: 'createDate',sortable: false},
       	   	   		{ text: "<%=UnieapConstants.getMessage("comm.modifyDate")%>",width: 150, dataIndex: 'modifyDate',sortable: false},
       	   	   		{ text: "<%=UnieapConstants.getMessage("comm.remark")%>", dataIndex: 'remark',width: 200, sortable: false}
       	   	   	],
	       	   	tbar:[{ pressed :true,iconCls:'add',
	         		tooltip:'<%=UnieapConstants.getMessage("comm.add")%>',text:'<%=UnieapConstants.getMessage("comm.add")%>',xtype:'button',id:'Offering_Category_Add',hidden:true,
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
 	                        	{ xtype:'textfield',hidden: true, name:'id'},
 	                        	{ xtype:'textfield',hidden: true, name:'createDate'},
 	                        	{ xtype:'textfield',hidden: true, name:'modifyDate'},
 	                        	{ xtype:'textfield',hidden: true, name:'createBy'},
 	                        	{ xtype:'textfield',hidden: true, name:'modifyBy'},
 	                        	{ xtype:'textfield',hidden: true, name:'seq'},
 	                        	{ xtype:'textfield',hidden: true, name:'detailUrl'},
 	                        	{ xtype:'textfield',hidden: true, name:'planUrl'},
 	                        	{ xtype:'textfield',hidden: true, name:'planHyperlink'},
 	                        	{ xtype:'textfield',hidden: true, name:'questionUrl'},
 	                        	{ xtype:'textfield',hidden: true, name:'questionHyperlink'},
 	                        	{ xtype:'textfield',hidden: true, name:'noteUrl'},
 	                        	{ xtype:'textfield',hidden: true, name:'noteHyperlink'},
 	                        	{ xtype:'combo', labelWidth:120, width:350,forceSelection: true,editable:false,allowBlank:false,
 	                                name:'categoryType',fieldLabel:'<%=UnieapConstants.getMessage("mcare.offering.display.categoryType")%>',displayField:'dicName',valueField:'dicCode',
 	                                store:Ext.create('Ext.data.Store', 
 	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._offerCategoryType})
 								},
 	                        	{ xtype:'textfield',labelWidth:120, width:350, name:'categoreName',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.offering.display.categoreName")%>',maxLength:45,allowBlank:false},
 	                        	{ xtype:'textfield',labelWidth:120, width:350, name:'categoryDesc',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.offering.display.categoryDesc")%>',maxLength:255,allowBlank:false},
 	                        	{ xtype:'combo', labelWidth:120, width:350,forceSelection: true,editable:false,allowBlank:false,
 	                                name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'Y',
 	                                store:Ext.create('Ext.data.Store', 
 	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._activeFlag})
 								},
 	                        	{ xtype:'textareafield',labelWidth:120, width:350, name:'remark',fieldLabel:'<%=UnieapConstants.getMessage("comm.remark")%>',maxLength:255,growMin:60,growMax:100,allowBlank:true}
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
 	                                     waitMsg: '<%=UnieapConstants.getMessage("comm.processing")%>',
 	                                     url: 'mcareController.do?method=offeringDeal',
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
 		
 		function showResult(btn){
 	     	dataWin.hide();
 	     	gridstore.reload();
 	    }
    	
    	
        
        Ext.define('offeringModel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'id','offeringId','offeringCode','offeringName','seq','offeringType','offeringDesc','effectiveType','effectiveTypeDesc','feeAmount',
            	'categoryId','activeFlag','activeFlagDesc','createDate','createBy','modifyDate','modifyBy','remark'
            ],
            idProperty: 'id'
        });
    	var offeringGridStore = Ext.create('Ext.data.Store', {
            model: 'oferingModel',
            pageSize: 15,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mcareController.do?method=offeringGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'offeringName', direction: 'ASC'}]
        });
    	offeringGridStore.on('beforeload', function (store, options){
    		var rec = datagrid.getSelectionModel().getSelection();
    		var categoryId = -1;
    		if(rec.length>0){
    			categoryId = rec[0].get("id");
    		}
            Ext.apply(store.proxy.extraParams,{categoryId:categoryId});
        });
    	
    	var offeringSelModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single'});
    	var offeringOperationItems = [];
    	var selectedeRecordOffering;
    	if(UnieapButton.Offering_Modify!=null&&UnieapButton.Offering_Modify.abled==true){
    		offeringOperationItems.push({iconCls :'',tooltip:''});
    		offeringOperationItems.push({
	    		iconCls : 'edit',
	     	   	tooltip: '<%=UnieapConstants.getMessage("comm.modify")%>',
	           	handler:function(grid, rowIndex, colIndex){	
	           		selectedeRecordOffering = grid.getStore().getAt(rowIndex);
	        		showFormOffering('Modify',selectedRecord);
	           	}
	        });
    	}
    	
    	var offeringDatagrid = Ext.create('Ext.grid.Panel', 
    	        {el : 'offeringgrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
    		   	 	selModel:offeringSelModel,title: '<%=UnieapConstants.getMessage("comm.list")%>',
    	   	   		store : offeringGridStore,
	    	   	   	listeners:{
		 		   		afterRender:function(thisForm, options){
				        	if(UnieapButton.Offering_Add!=null&&UnieapButton.Offering_Add.abled== true){
				        		Ext.getCmp('Offering_Add').show();
				        	}
			            }
			        },
    		   	   	columns:
    		   	   	[
						{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:80,items:offeringOperationItems},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mcare.offering.display.offeringId")%>",dataIndex: 'offeringId',width:120},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mcare.offering.display.offeringName")%>",dataIndex: 'offeringName',width:120},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mcare.offering.display.offeringType")%>",dataIndex: 'offeringType',width:120},
    		   	 		{ text: "<%=UnieapConstants.getMessage("mcare.offering.display.effectiveType")%>",dataIndex: 'effectiveTypeDesc',width:120},
    		   	 		{ text: "<%=UnieapConstants.getMessage("mcare.offering.display.feeAmount")%>",dataIndex: 'feeAmount',width:120},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>",dataIndex: 'activeFlagDesc',sortable: false,width:120}
    		   	   	],
    	 		   	tbar:[{ pressed :true,iconCls:'add',
    		             		tooltip:'<%=UnieapConstants.getMessage("comm.add")%>',text:'<%=UnieapConstants.getMessage("comm.add")%>',xtype:'button',id:'Offering_Add',hidden:true,
    		            		handler : function(){showFormOffering("Add",null);}
    		    	}],
               	   	bbar:new Ext.PagingToolbar(
                       	   	{ store : offeringGridStore,displayInfo: true})
    	        });
    	offeringDatagrid.render();
    	//offeringGridStore.loadPage(1);
       var dataWinOffering = null;
       var dataFormOffering = null;
       var operTypeOffering = '';
       function showFormOffering(status,selectedRecord){
        	operTypeOffering = status;
            if (dataWinOffering==null){
            	dataFormOffering = Ext.widget('form',
            	{
                    defaults:{labelAlign: 'left', labelWidth: 90, anchor: '100%'},
                    bodyPadding:5,
                    items:
                    [
                    	{xtype:'fieldset', title:'<%=UnieapConstants.getMessage("comm.data")%>',
	                        items:
	                        [
	                        	{ xtype:'textfield',hidden: true, name:'id'},
	                        	{ xtype:'textfield',hidden: true, name:'createDate'},
	                        	{ xtype:'textfield',hidden: true, name:'modifyDate'},
	                        	{ xtype:'textfield',hidden: true, name:'createBy'},
	                        	{ xtype:'textfield',hidden: true, name:'modifyBy'},
	                        	{ xtype:'textfield',hidden: true, name:'offeringCode'},
	                        	{ xtype:'textfield',hidden: true, name:'seq'},
	                        	{ xtype:'textfield',hidden: true, name:'offeringDesc'},
	                        	{ xtype:'textfield',hidden: true, name:'categoryId'},
	                        	{ xtype:'textfield',labelWidth:80, width:350, name:'offeringId',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.offering.display.offeringId")%>',maxLength:45,allowBlank:false},
	                        	{ xtype:'textfield',labelWidth:80, width:350, name:'offeringName',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.offering.display.offeringName")%>',maxLength:45,allowBlank:false},
	                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true,editable:false,allowBlank:false,
	                                name:'offeringType',fieldLabel:'<%=UnieapConstants.getMessage("mcare.offering.display.offeringType")%>',displayField:'dicName',valueField:'dicCode',value:'Y',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._offeringType})
								},
	                        	{ xtype:'textfield',labelWidth:80, width:350, name:'feeAmount',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.offering.display.feeAmount")%>',maxLength:45,allowBlank:false},
	                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true,editable:false,allowBlank:false,
	                                name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'Y',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._activeFlag})
								},
	                        	{ xtype:'textareafield',labelWidth:80, width:350, name:'remark',fieldLabel:'<%=UnieapConstants.getMessage("comm.remark")%>',maxLength:255,growMin:60,growMax:100,allowBlank:true}
	                        ]
	                    }
                    ],
                    buttons: 
                    [
	                    {id:'formCancel', text: '<%=UnieapConstants.getMessage("comm.cancel")%>',
	                        handler: function(){
	                        	dataFormOffering.getForm().reset();
	                        	dataWinOffering.hide();
	                        }
	                    }, 
	                    {id:'formSubmit',text: '<%=UnieapConstants.getMessage("comm.submit")%>',
	                        handler: function(){
	                        	var form = dataFormOffering.getForm();
	                        	 if (form.isValid()){
	                                 form.submit({
	                                     clientValidation: true,
	                                     method: 'POST',
	                                     params:{'operType':operType},
	                                     waitMsg: '<%=UnieapConstants.getMessage("comm.processing")%>',
	                                     url: 'mcareController.do?method=offeringDeal',
	                                     success: function(form, action) {
	                                    	var result = Ext.JSON.decode(action.response.responseText);
						                    if(result.isSuccess == 'failed'){
						                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
		                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
						                    }else{
 		                                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.save")%>',fn: showResultOffering,
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
                dataWinOffering = Ext.widget('window', 
                { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height: 260, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'offeringName' });
            }
            if(operTypeOffering=='Add'){
            	dataFormOffering.getForm().reset();
            	dataWinOffering.show();
            }else if(operTypeOffering=='Modify'){
            	dataWinOffering.show();
            	dataFormOffering.getForm().setValues(selectedRecordOffering.data);
            }else{
            	dataWinOffering.show();
            }
	    }
		function showResultOffering(btn){
	     	dataWinOffering.hide();
	     	offeringGridStore.reload();
	    }
    	
      
	});
    </script>
</head>
<body>
    <div id="datagrid"></div>
    <div id="offeringgrid"></div>
</body>
</html>
