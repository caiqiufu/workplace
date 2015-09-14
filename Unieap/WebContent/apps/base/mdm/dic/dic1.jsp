<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Dic</title>
    <script type="text/javascript">
    Ext.onReady(function(){
    	Ext.tip.QuickTipManager.init();
    	/*******************dic group begin**********************************/
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'groupId','groupName','createDate','modifyDate','createBy','modifyBy','remark','activeFlag','activeFlagDesc'
            ],
            idProperty: 'groupId'
        });
    	function unableEdit(value, metadata, record, rowIndex, colIndex, store){
    		if(record.get('groupId')=='1012'||record.get('groupId')=='1013'||record.get('groupId')=='1001'){
    			this.items=[];
	    	}else{
	    		this.items=dicOperationItems;
	    	}
    	}
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy: 
            { type: 'ajax', url: 'MdmController.do?method=dicGroupGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: 
            [
            	{ property: 'groupName', direction: 'ASC'}
            ]
        });
    	var operationItems = [];
    	var selectedRecord;
    	if(UnieapButton.Group_Modify!=null&&UnieapButton.Group_Modify.abled==true){
    		operationItems.push({iconCls :'',tooltip:''});
	    	operationItems.push({
	     	   icon   : '/Unieap/unieap/js/common/images/edit.png',
	     	   tooltip: '<%=UnieapConstants.getMessage("comm.modify")%>',
	           handler:function(grid, rowIndex, colIndex)
	           {	
	         	   	selectedRecord = grid.getStore().getAt(rowIndex);
	        		showForm('Modify',selectedRecord);
	           }
	        });
	        
    	}
    	if(UnieapButton.Group_Delete!=null&&UnieapButton.Group_Delete.abled==true){
	        operationItems.push({iconCls :'',tooltip:''});
	        operationItems.push({
	     	   icon   : '/Unieap/unieap/js/common/images/delete.png',
	           tooltip: '<%=UnieapConstants.getMessage("comm.delete")%>',
	           handler:function(grid, rowIndex, colIndex)
	           {	
	         	   selectedRecord = grid.getStore().getAt(rowIndex);
		           Ext.MessageBox.confirm('<%=UnieapConstants.getMessage("comm.title.confirm")%>', '<%=UnieapConstants.getMessage("comm.delete.confirm")%>', removeDatas);
	           }
	        });
    	}
    	var selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single',listeners:{
		  		select:function(model,record,index)
		  			{
		  				dicgridstore.load({params:{groupId:record.get('groupId')}});
		  			}
	  			}
	  		});
    	var datagrid = Ext.create('Ext.grid.Panel', 
    	        {el : 'datadicgroupgrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
    		   	 	selModel:selModel,title: '<%=UnieapConstants.getMessage("mdm.dic.group.title.list")%>',
	    		   	listeners:
				        {afterRender:function(thisForm, options){
					        	if(UnieapButton.Group_Add!=null&&UnieapButton.Group_Add.abled== true){
					        		Ext.getCmp('Group_Add').show();
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
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.dic.display.groupId")%>",dataIndex: 'groupId'},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.dic.display.groupName")%>", dataIndex: 'groupName'},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.createDate")%>",dataIndex: 'createDate',width:120},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.modifyDate")%>",dataIndex: 'modifyDate',width:120},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.createBy")%>",dataIndex: 'createBy',sortable: false},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.modifyBy")%>",dataIndex: 'modifyBy',sortable: false},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>",dataIndex: 'activeFlagDesc',sortable: false},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.remark")%>", dataIndex: 'remark',sortable: false}
    		   	   	],
	    		   	tbar:[{ pressed :true,icon:'/Unieap/unieap/js/common/images/add.png',
	                		tooltip:'<%=UnieapConstants.getMessage("comm.add")%>',text:'<%=UnieapConstants.getMessage("comm.add")%>',xtype:'button',id:'Group_Add',hidden:true,
	 		            handler : function(){showForm('Add',null);}
	 		    	},'-',
   		   	     	{xtype: 'fieldcontainer',layout: 'hbox', id:'queryFields',
					    items: 
					    [
					        { xtype:'textfield',labelWidth:80, width:200,labelAlign: 'right',name: 'groupName',fieldLabel: '<%=UnieapConstants.getMessage("mdm.dic.display.groupName")%>'}
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
	    	                }
		                }],
    	    	   	bbar:new Ext.PagingToolbar(
    	    	   	{ store : gridstore,displayInfo: true})
    	        	
    	        });
    	   datagrid.render();
 	       gridstore.loadPage(1);
 	      function query(){
 	    	 var queryFields = Ext.getCmp('queryFields').items.items;
         	 var groupName=queryFields[0].getValue(); 
             queryPara = 
             	{
           			groupName:groupName
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
 	                    defaults:{labelAlign: 'left', labelWidth: 90, anchor: '100%'},
 	                    bodyPadding:5,
 	                    items:
 	                    [
 	                    	{xtype:'fieldset', title:'<%=UnieapConstants.getMessage("comm.data")%>',
 		                        items:
 		                        [
 		                        	{ xtype:'textfield',hidden: true, name:'groupId'},
 		                        	{ xtype:'textfield',hidden: true, name:'createDate'},
 		                        	{ xtype:'textfield',hidden: true, name:'modifyDate'},
 		                        	{ xtype:'textfield',hidden: true, name:'createBy'},
 		                        	{ xtype:'textfield',hidden: true, name:'modifyBy'},
 		                        	{ xtype:'textfield', labelWidth:80, width:350,name:'groupName',fieldLabel:'<%=UnieapConstants.getMessage("mdm.dic.display.groupName")%><font color=red>*</font>',allowBlank:false},
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
 		                        handler: function() 
 		                        {
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
 		                                     url: 'MdmController.do?method=dicGroupDeal',
 		                                     success: function(form, action) {
 		                                    	var result = Ext.JSON.decode(action.response.responseText);
							                    if(result.isSuccess == 'failed')
							                    {
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
 	                { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height: 220, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'groupName' });
 	            }
 	            if(operType=='Add')
 	            {
 	            	dataForm.getForm().reset();
 	            	dataWin.show();
 	            }else if(operType=='Modify')
 	            {
 	            	dataWin.show();
 	            	dataForm.getForm().setValues(selectedRecord.data);
 	            }else
 	            {
 	            	dataWin.show();
 	            }
 	    }
      	function removeDatas(btn)
        {
        	if(btn=='yes'){
	        	var groupId= selectedRecord.get("groupId");
	        	Ext.Ajax.request({
	                url: 'MdmController.do?method=dicGroupDeal',
	                params:{'operType':"Delete","groupId":groupId},
	                success: function(response, opts) 
	                {
	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.delete")%>',
                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
	                	gridstore.reload();
	                },
	                failure: function(response, opts) 
	                {
	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:response.responseText,
                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                }
	             });
        	}
        }
 	    function showResult(btn)
        {
        	dataWin.hide();
        	gridstore.reload();
        }
 	   /*******************dic group end**********************************/
 	   /*******************dic data begin**********************************/
 	   Ext.define('dicmodel', {
           extend: 'Ext.data.Model',
           fields:
           [
           	'groupId','groupName','dicId','dicCode','dicName','seq','parentId','parentCode','parentName','href','createDate','modifyDate','createBy','modifyBy','remark','activeFlag','activeFlagDesc','language'
           ],
           idProperty: 'dicId'
       });
 	  var dicgridstore = Ext.create('Ext.data.Store', {
          model: 'dicmodel',
          pageSize: 15,
          remoteSort: true,
          proxy: 
          { type: 'ajax', url: 'MdmController.do?method=dicDataGrid',
              reader: 
              {root: 'rows', totalProperty: 'totalCount'},
              simpleSortMode: true
          },
          sorters: 
          [
          	{ property: 'dicName', direction: 'ASC'}
          ]
      });
 	dicgridstore.on('beforeload', function (store, options){
		var rec = datagrid.getSelectionModel().getSelection();
        Ext.apply(store.proxy.extraParams,{'groupId':rec[0].get('groupId')});
     });
 	 
  	 var dicOperationItems = [];
  	 var dicselectedRecord;
  	 if(UnieapButton.Data_Modify!=null&&UnieapButton.Data_Modify.abled==true){
  		 dicOperationItems.push({iconCls :'',tooltip:''});
	  	 dicOperationItems.push({
	   	   icon   : '/Unieap/unieap/js/common/images/edit.png',
	   	   tooltip: '<%=UnieapConstants.getMessage("comm.modify")%>',
	       handler:function(grid, rowIndex, colIndex)
	       {	
	       	   	dicselectedRecord = grid.getStore().getAt(rowIndex);
	       	    dicshowForm('Modify',dicselectedRecord);
	       }
	      });
  	 }
  	if(UnieapButton.Data_Delete!=null&&UnieapButton.Data_Delete.abled==true){
       dicOperationItems.push({iconCls :'',tooltip:''});
       dicOperationItems.push({
	  	  icon   : '/Unieap/unieap/js/common/images/delete.png',
	      tooltip: '<%=UnieapConstants.getMessage("comm.delete")%>',
	      handler:function(grid, rowIndex, colIndex)
	      {	
	    	   dicselectedRecord = grid.getStore().getAt(rowIndex);
	           Ext.MessageBox.confirm('<%=UnieapConstants.getMessage("comm.title.confirm")%>', '<%=UnieapConstants.getMessage("comm.delete.confirm")%>', dicremoveDatas);
	      }
       });
  	 }
  	  var dicselModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single'});
  	  var dicdatagrid = Ext.create('Ext.grid.Panel', 
  	        {el : 'datadicdatagrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
  		   	 	selModel:dicselModel,title: '<%=UnieapConstants.getMessage("mdm.dic.data.title.list")%>',
	  		   	listeners:
		        {afterRender:function(thisForm, options){
			        	if(UnieapButton.Data_Add!=null&&UnieapButton.Data_Add.abled== true){Ext.getCmp('Data_Add').show();}
		            }
		        },
  	   	   		store : dicgridstore,
  		   	   	columns:
  		   	   	[
  		   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:80,items:dicOperationItems},
  		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.dic.display.dicId")%>",dataIndex: 'dicId',sortable: false,width:50},
  		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.dic.display.dicCode")%>", dataIndex: 'dicCode',sortable: false,width:60},
  		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.dic.display.dicName")%>", dataIndex: 'dicName',sortable: false,width:80},
  		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.dic.display.sequence")%>",dataIndex: 'seq',sortable: false,width:60},
  		   	        { text: "<%=UnieapConstants.getMessage("mdm.dic.display.parentId")%>", dataIndex: 'parentId',sortable: false,width:60},
  		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.dic.display.parentCode")%>", dataIndex: 'parentCode',sortable: false,width:80},
  		   	        { text: "<%=UnieapConstants.getMessage("mdm.dic.display.href")%>",dataIndex: 'href',sortable: false,width:60},
  		   	        { text: "<%=UnieapConstants.getMessage("mdm.dic.display.language")%>",dataIndex: 'language',sortable: false,width:60},
  		   	   		{ text: "<%=UnieapConstants.getMessage("comm.createDate")%>",dataIndex: 'createDate',width:120,sortable: false},
  		   	   		{ text: "<%=UnieapConstants.getMessage("comm.modifyDate")%>",dataIndex: 'modifyDate',width:120,sortable: false},
  		   	   		{ text: "<%=UnieapConstants.getMessage("comm.createBy")%>",dataIndex: 'createBy',sortable: false,width:60},
  		   	   		{ text: "<%=UnieapConstants.getMessage("comm.modifyBy")%>",dataIndex: 'modifyBy',sortable: false,width:60},
  		   	   		{ text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>", dataIndex: 'activeFlagDesc',sortable: false,width:80},
  		   	   		{ text: "<%=UnieapConstants.getMessage("comm.remark")%>", dataIndex: 'remark',sortable: false}
  		   	   	],
    		   	tbar:[{ pressed :true,icon:'/Unieap/unieap/js/common/images/add.png',
                		tooltip:'<%=UnieapConstants.getMessage("comm.add")%>',text:'<%=UnieapConstants.getMessage("comm.add")%>',xtype:'button',id:'Data_Add',hidden:true,
 		            handler : function(){dicshowForm('Add',null);}
 		    	}],
  	    	   	bbar:new Ext.PagingToolbar(
  	    	   	{ store : dicgridstore,displayInfo: true})
  	        	
  	        });
	  	   dicdatagrid.render();
		   var dicdataWin = null;
	       var dicdataForm = null;
	       var dicoperType = '';
	       function dicshowForm(status,selectedRecord){
	        	dicoperType = status;
	            if (dicdataWin==null){
	            	dicdataForm = Ext.widget('form',
	            	{
	                    defaults:{labelAlign: 'left', labelWidth: 90, anchor: '100%'},
	                    items:
	                    [
	                    	{xtype:'fieldset', title:'<%=UnieapConstants.getMessage("comm.data")%>',
		                        items:
		                        [
		                        	{ xtype:'textfield',hidden: true, name:'dicId'},
		                        	{ xtype:'textfield',labelWidth:80, width:350,name:'groupId',fieldLabel:'<%=UnieapConstants.getMessage("mdm.dic.display.groupId")%>'},
		                        	{ xtype:'textfield',labelWidth:80, width:350,name:'groupName',fieldLabel:'<%=UnieapConstants.getMessage("mdm.dic.display.groupName")%>'},
		                        	{ xtype:'numberfield',labelWidth:80, width:350,name:'parentId',fieldLabel:'<%=UnieapConstants.getMessage("mdm.dic.display.parentId")%>',listeners:{
		                        		'blur':function(){
		                        			var parentId=dicdataForm.getForm().findField("parentId").getValue(); 
		                        			if(parentId == '' ||parentId ==  null){
		                        				return;
		                        			}
		                    	        	Ext.Ajax.request({
		                    	                url: 'MdmController.do?method=getParentName',
		                    	                params:{"dicId":parentId},
		                    	                success: function(response, opts) 
		                    	                {
		                    	                	var result = Ext.JSON.decode(response.responseText);
		                    	                	if(result.isSuccess == 'success')
									                {
		                    	                		dicdataForm.getForm().findField("parentCode").setValue(result.parentCode); 
								                    }else
								                    {
								                    	dicdataForm.getForm().findField("parentId").setValue(''); 
								                    	dicdataForm.getForm().findField("parentCode").setValue('');
								                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.ststus")%>',msg:'<%=UnieapConstants.getMessage("comm.notExisting")%>',
		                    	                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
								                    }
		                    	                },
		                    	                failure: function(response, opts) 
		                    	                {
		                    	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.ststus")%>',msg:response.responseText,
		                    	                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
		                    	                }
		                    	             });
		                        		}
		                        	}},
		                        	{ xtype:'textfield',id:'parentCodeId',labelWidth:80, width:350,name:'parentCode',fieldLabel:'<%=UnieapConstants.getMessage("mdm.dic.display.parentCode")%>',readOnly:true},
		                        	{ xtype:'textfield',hidden: true, name:'createDate'},
		                        	{ xtype:'textfield',hidden: true, name:'modifyDate'},
		                        	{ xtype:'textfield',hidden: true, name:'createBy'},
		                        	{ xtype:'textfield',hidden: true, name:'modifyBy'},
		                        	{ xtype:'textfield', labelWidth:80, width:350,name:'dicCode',fieldLabel:'<%=UnieapConstants.getMessage("mdm.dic.display.dicCode")%><font color=red>*</font>',allowBlank:false},
		                        	{ xtype:'textfield', labelWidth:80, width:350,name:'dicName',fieldLabel:'<%=UnieapConstants.getMessage("mdm.dic.display.dicName")%>',allowBlank:false},
		                        	{ xtype:'numberfield', labelWidth:80, width:350,name:'seq',fieldLabel:'<%=UnieapConstants.getMessage("mdm.dic.display.sequence")%><font color=red>*</font>',minValue: 0,maxValue:10,allowBlank:false},
		                        	{ xtype:'textfield', labelWidth:80, width:350,name:'href',fieldLabel:'<%=UnieapConstants.getMessage("mdm.dic.display.href")%>'},
		                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true,editable:false,allowBlank:false,
		                                name:'language',fieldLabel:'<%=UnieapConstants.getMessage("mdm.dic.display.language")%>',displayField:'dicName',valueField:'dicCode',value:'zh_CN',
		                                store:Ext.create('Ext.data.Store', 
		                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._5479})
									},
		                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, editable:false,allowBlank:true,
		                                name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'1',
		                                store:Ext.create('Ext.data.Store', 
		                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._1001})
									},
		                        	{ xtype:'textareafield', labelWidth:80, width:350,name:'remark',fieldLabel:'<%=UnieapConstants.getMessage("comm.remark")%>',growMin:60,growMax:100,allowBlank:true}
		                        ]
		                    }
	                    ],
	                    buttons: 
	                    [
		                    {id:'dicformCancel', text: '<%=UnieapConstants.getMessage("comm.cancel")%>',
		                        handler: function() 
		                        {
		                        	dicdataForm.getForm().reset();
		                        	dicdataWin.hide();
		                        }
		                    }, 
		                    {id:'dicformSubmit',text: '<%=UnieapConstants.getMessage("comm.submit")%>',
		                        handler: function() {
		                        	var form = dicdataForm.getForm();
		                        	 if (form.isValid()){
		                                 form.submit({
		                                     clientValidation: true,
		                                     method: 'POST',
		                                     params:{'operType':dicoperType},
		                                     url: 'MdmController.do?method=dicDataDeal',
		                                     success: function(form, action) {
		                                    	var result = Ext.JSON.decode(action.response.responseText);
							                    if(result.isSuccess == 'failed')
							                    {
							                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
			                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
							                    }else{
	 		                                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.save")%>',fn: dicshowResult,
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
	                dicdataWin = Ext.widget('window', 
	                { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height: 450, layout: 'fit',modal: true,  items: dicdataForm,defaultFocus: 'dicName' });
	            }
	            if(dicoperType=='Add')
	            {   
	            	var rec = datagrid.getSelectionModel().getSelection();
	            	if(rec.length==0){
	            		Ext.MessageBox.show({
	            	           title: '<%=UnieapConstants.getMessage("comm.tipInfo")%>',
	            	           msg: '<%=UnieapConstants.getMessage("comm.select.oneRecord")%>',
	            	           buttons: Ext.MessageBox.OK,
	            	           icon: Ext.MessageBox.INFO
	            	       });
	            	}else{
		            	dicdataForm.getForm().reset();
		            	dicdataWin.show();
		            	dicdataForm.getForm().findField('groupId').setValue(rec[0].get("groupId"));
		            	dicdataForm.getForm().findField('groupId').setReadOnly(true);
		            	dicdataForm.getForm().findField('groupId').inputEl.addCls('readonly_field');
		            	dicdataForm.getForm().findField('groupName').setValue(rec[0].get("groupName"));
		            	dicdataForm.getForm().findField('groupName').setReadOnly(true);
		            	dicdataForm.getForm().findField('groupName').inputEl.addCls('readonly_field');
		            	dicdataForm.getForm().findField('parentCode').setReadOnly(true);
		            	dicdataForm.getForm().findField('parentCode').inputEl.addCls('readonly_field');
		            	dicdataForm.getForm().findField('dicCode').setReadOnly(false);
		            	dicdataForm.getForm().findField('dicCode').inputEl.removeCls('readonly_field');
	            	}
	            }else if(dicoperType=='Modify')
	            {
	            	dicdataWin.show();
	            	dicdataForm.getForm().setValues(dicselectedRecord.data);
	            	dicdataForm.getForm().findField('groupId').setReadOnly(true);
	            	dicdataForm.getForm().findField('groupName').setReadOnly(true);
	            	dicdataForm.getForm().findField('dicCode').setReadOnly(true);
	            	dicdataForm.getForm().findField('dicCode').setReadOnly(true);
	            	dicdataForm.getForm().findField('dicCode').inputEl.addCls('readonly_field');
	            }else
	            {
	            	dicdataWin.show();
	            }
	    }
	  	function dicremoveDatas(btn)
	    {
	    	if(btn=='yes'){
	        	var dicId= dicselectedRecord.get("dicId");
	        	Ext.Ajax.request({
	                url: 'MdmController.do?method=dicDataDeal',
	                params:{'operType':"Delete","dicId":dicId},
	                success: function(response, opts) 
	                {
	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.delete")%>',
                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
	                	dicgridstore.reload();
	                },
	                failure: function(response, opts) 
	                {
	                	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:response.responseText,
	                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                }
	             });
	    	}
	    }
	    function dicshowResult(btn)
	    {
	    	dicdataWin.hide();
	    	dicgridstore.reload();
	    }
	    });
    </script>
</head>
<body>
    <div id="datadicgroupgrid"></div>
    <div id="datadicdatagrid"></div>
</body>
</html>
