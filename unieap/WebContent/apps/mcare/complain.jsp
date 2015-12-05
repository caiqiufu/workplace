<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>complain management</title>
    <script type="text/javascript">
    Ext.onReady(function(){
    	Ext.tip.QuickTipManager.init();
    	 var queryPara;
    	 var queryform = Ext.create('Ext.form.Panel',{
 			fieldDefaults:
 			{ labelAlign: 'left', labelWidth: 70 },
 	        layout: 'fit',
 	        width : '100%',
      	   	frame : true,
      	    items:[
      	           { id:'queryFieldset', width: '100%',xtype:'fieldset',title: '<%=UnieapConstants.getMessage("comm.search")%>',
   	                 startCollapsed: true,collapsible: true, defaultType: 'textfield',
   	                	items:
   	                		[
 								{ xtype: 'fieldcontainer',layout: 'hbox',
 									defaults : { 
 										margins : "5" 
 									},
 								    items: 
 								    [
 								        { xtype:'textfield',name: 'submitBy',fieldLabel: '<%=UnieapConstants.getMessage("mcare.complain.display.submitBy")%>'},
 								        {name: 'submitDateStart', fieldLabel: '<font color=red>*</font><%=UnieapConstants.getMessage("mcare.complain.display.submitDateStart")%>',format: 'Y-m-d h:i:s', xtype: 'datefield',allowBlank:false,
 						               		value:Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.DAY,-1),"Y-m-d h:i:s")	
 						               	},
 						                {name: 'submitDateEnd', fieldLabel: '<font color=red>*</font><%=UnieapConstants.getMessage("mcare.complain.display.submitDateEnd")%>', format: 'Y-m-d h:i:s',xtype: 'datefield',allowBlank:false,
 						               		value:Ext.util.Format.date(new Date(),"Y-m-d h:i:s")	
 						                },
 						                { xtype:'button',iconCls:'search-trigger',text:'<%=UnieapConstants.getMessage("comm.search")%>',iconAlign: 'right',
 					    	                handler: function (){
 					    	                	var form = queryform.getForm();
 					    	                	if (form.isValid()){
	 					    	                	var submitBy=queryform.getForm().findField("submitBy").getValue(); 
	 					    	                    var submitDateStart=dateFormat(queryform.getForm().findField("submitDateStart").getValue()); 
	 					    	                    var submitDateEnd=dateFormat(queryform.getForm().findField("submitDateEnd").getValue()); 
	 					    	                    queryPara = 
	 					    	                    	{
	 					    	                    		submitBy:submitBy,
	 					    	                    		submitDateStart:submitDateStart,
	 					    	                    		submitDateEnd:submitDateEnd
	 					   	                        	};
	 					    	                   gridstore.load({params:queryPara});
 					    	                	}
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
            	'id','evalution','text','url','fileName','submitDate','submitBy','feedback','status','statusDesc'
            ],
            idProperty: 'id'
        });
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mcareController.do?method=complainGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	gridstore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,queryPara);
        });
    	
    	var operationItems = [];
    	var selectedRecord;
    	if(UnieapButton.Complain_Modify!=null&&UnieapButton.Complain_Modify.abled==true){
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
    	var selModel = Ext.create('Ext.selection.CheckboxModel');
        var datagrid = Ext.create('Ext.grid.Panel', 
               {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,
       	   	 	selModel:selModel,
          	   	store : gridstore,
       	   	   	columns:
       	   	   	[
					{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:80,items:operationItems},
       	   	   		{ text: "<%=UnieapConstants.getMessage("mcare.complain.display.evalution")%>", dataIndex: 'evalution',sortable: false,width:50},
       	   			{ text: "<%=UnieapConstants.getMessage("mcare.complain.display.text")%>", dataIndex: 'text',flex: true, sortable: false,width:500,
		       	   	  	renderer: function (value, meta, record){
							var max = 150;
							meta.tdAttr = 'data-qtip="' + value + '"';
							return value.length < max ? value : value.substring(0, max - 3) + '...';
						}	
       	   			},
			       	{ text: "<%=UnieapConstants.getMessage("mcare.complain.display.url")%>", dataIndex: 'url', sortable: false,width:150,
	       	   			renderer: function (value, meta, record){
	       	   				if(value!=null&&value!=''){
	       	   					var fileName = record.get("fileName");
	       	   					meta.tdAttr = 'data-qtip="' + fileName + '"';
								return '<span style="margin-right:10px"><a href="#"  onclick=showPicture("'+value+'")>'+fileName+'</a></span>';
	       	   				}else{
	       	   					return '';
	       	   				}
						}	
			       	},
			       	{ text: "<%=UnieapConstants.getMessage("mcare.complain.display.submitDate")%>", dataIndex: 'submitDate', sortable: false,width:120},
			       	{ text: "<%=UnieapConstants.getMessage("mcare.complain.display.submitBy")%>", dataIndex: 'submitBy', sortable: false,width:100},
			       	{ text: "<%=UnieapConstants.getMessage("comm.status")%>", dataIndex: 'statusDesc', sortable: false,width:100}
       	   	   	],
	       	   	tbar:[queryform],
           	   	bbar:new Ext.PagingToolbar(
           	   	{ store : gridstore,displayInfo: true})
            });
    	datagrid.render();
	   	function showPicture(url){
	   		window.open(url);
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
	                        	{ xtype:'textfield',hidden: true, name:'id'},
	                        	{ xtype:'textfield',labelWidth:100, width:400, name:'evalution',fieldLabel:'<%=UnieapConstants.getMessage("mcare.complain.display.evalution")%>',maxLength:45,allowBlank:false},
	                        	{ xtype:'textareafield',labelWidth:100, width:400, name:'text',fieldLabel:'<%=UnieapConstants.getMessage("mcare.complain.display.text")%>',maxLength:255,growMin:60,growMax:100,allowBlank:false},
	                        	{ xtype:'textfield',labelWidth:100, width:400, name:'submitDate',fieldLabel:'<%=UnieapConstants.getMessage("mcare.complain.display.submitDate")%>',maxLength:45,allowBlank:false},
	                        	{ xtype:'textfield',labelWidth:100, width:400, name:'submitBy',fieldLabel:'<%=UnieapConstants.getMessage("mcare.complain.display.submitBy")%>',maxLength:45,allowBlank:false},
	                        	{ xtype:'textareafield',labelWidth:100, width:400, name:'feedback',fieldLabel:'<%=UnieapConstants.getMessage("mcare.complain.display.feedback")%>',maxLength:255,growMin:60,growMax:100,allowBlank:false}
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
	                                     url: 'mcareController.do?method=complainDeal',
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
                { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 480, height: 300, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'reply' });
            }
            if(operType=='Add'){
            	dataForm.getForm().reset();
            	dataWin.show();
            }else if(operType=='Modify'){
            	dataWin.show();
            	dataForm.getForm().reset();
            	dataForm.getForm().findField('evalution').setReadOnly(true);
            	dataForm.getForm().findField('evalution').inputEl.addCls('readonly_field');
            	dataForm.getForm().findField('text').setReadOnly(true);
            	dataForm.getForm().findField('text').inputEl.addCls('readonly_field');
            	dataForm.getForm().findField('submitDate').setReadOnly(true);
            	dataForm.getForm().findField('submitDate').inputEl.addCls('readonly_field');
            	dataForm.getForm().findField('submitBy').setReadOnly(true);
            	dataForm.getForm().findField('submitBy').inputEl.addCls('readonly_field');
            	dataForm.getForm().setValues(selectedRecord.data);
            }else{
            	dataWin.show();
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
    <div id="datagrid"></div>
</body>
</html>
