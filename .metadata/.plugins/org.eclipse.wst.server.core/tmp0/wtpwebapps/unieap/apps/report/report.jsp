<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>complain management</title>
    <script type="text/javascript">
    
    Ext.onReady(function(){
    	
    	Ext.tip.QuickTipManager.init();
    	/***notification configure*********************/
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'id','type','name','text','subject','url','hyperlink','groupName','resolution','activeFlag','activeFlagDesc','pageNum','modifyDate','seq','modifyBy','language','remark',
            	'effectiveDate','expiredDate','createDate','createBy'
            ],
            idProperty: 'id'
        });
    	
    	var gridstoreForNotification = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'easyMobileController.do?method=messageGrid&type=N',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	var gridForNotification = Ext.create('Ext.grid.Panel', {
            store: gridstoreForNotification,
            columns: [
                {text: "<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.subject")%>",  dataIndex: 'subject', sortable: false,width:200,
                	renderer: function (value, meta, record){
						var max = 150;
						meta.tdAttr = 'data-qtip="' + value + '"';
						return value.length < max ? value : value.substring(0, max - 3) + '...';
					}	
                },
                {text: "<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.text")%>", dataIndex: 'text',flex: true, sortable: true,
                	renderer: function (value, meta, record){
						var max = 150;
						meta.tdAttr = 'data-qtip="' + value + '"';
						return value.length < max ? value : value.substring(0, max - 3) + '...';
					}	
                },
                { text: "<%=UnieapConstants.getMessage("comm.effectiveDate")%>", dataIndex: 'effectiveDate',sortable: false,width:100,
                	renderer: function (value, meta, record){
						var max = 150;
						meta.tdAttr = 'data-qtip="' + value + '"';
						return value.length < max ? value : value.substring(0, max - 3) + '...';
					}	
                },
                { text: "<%=UnieapConstants.getMessage("comm.expiredDate")%>", dataIndex: 'expiredDate',sortable: false,width:100,
                	renderer: function (value, meta, record){
						var max = 150;
						meta.tdAttr = 'data-qtip="' + value + '"';
						return value.length < max ? value : value.substring(0, max - 3) + '...';
					}	
                },
                { text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>",dataIndex: 'activeFlagDesc',sortable: false,width:60}
            ],
            flex: true,region: 'center',
            bbar:new Ext.PagingToolbar(
               	   	{ store : gridstoreForNotification,displayInfo: true})
        });
    	gridstoreForNotification.load();
    	gridForNotification.getSelectionModel().on('selectionchange', function(sm, selectedRecord) {
            if (selectedRecord.length) {
            	dataForm.getForm().reset();
            	dataForm.getForm().setValues(selectedRecord[0].data);
            	dataForm.getForm().findField('subject').setReadOnly(true);
            	dataForm.getForm().findField('subject').inputEl.addCls('readonly_field');
            	dataForm.getForm().findField('text').setReadOnly(true);
            	dataForm.getForm().findField('text').inputEl.addCls('readonly_field');
            	dataForm.getForm().findField('activeFlag').setReadOnly(true);
            	dataForm.getForm().findField('activeFlag').inputEl.addCls('readonly_field');
            	dataForm.getForm().findField('effectiveDate').setReadOnly(true);
            	dataForm.getForm().findField('effectiveDate').inputEl.addCls('readonly_field');
            	dataForm.getForm().findField('expiredDate').setReadOnly(true);
            	dataForm.getForm().findField('expiredDate').inputEl.addCls('readonly_field');
            	dataForm.getForm().findField('effectiveDate').setValue(Ext.util.Format.date(selectedRecord[0].data.effectiveDate,"Y-m-d h:i:s"));
            	dataForm.getForm().findField('expiredDate').setValue(Ext.util.Format.date(selectedRecord[0].data.expiredDate,"Y-m-d h:i:s"));
            	Ext.getCmp('formAdd').enable();
            	Ext.getCmp('formModify').enable();
            	Ext.getCmp('formSubmit').disable();
            }
        });
    	var operType = '';
    	var dataForm = Ext.widget('form',
           {
    		layout:'fit',defaults:{labelAlign: 'left', labelWidth: 90, anchor: '100%'},
            bodyPadding:5,
            items:
            [
            	{xtype:'fieldset', title:'<%=UnieapConstants.getMessage("comm.data")%>',
                    items:
                    [
                    	{ xtype:'textfield',hidden: true, name:'id'},
                    	{ xtype:'textfield',hidden: true, name:'type'},
                    	{ xtype:'textfield',hidden: true, name:'createDate'},
                    	{ xtype:'textfield',hidden: true, name:'createBy'},
                    	{ xtype:'textfield',hidden: true, name:'name'},
                    	{ xtype:'textfield',hidden: true, name:'groupName'},
                    	{ xtype:'textfield',hidden: true, name:'resolution'},
                    	{ xtype:'textfield',hidden: true, name:'pageNum'},
                    	{ xtype:'textfield',hidden: true, name:'seq'},
                    	{ xtype:'textfield',hidden: true, name:'url'},
                    	{ xtype:'textfield',hidden: true, name:'remark'},
                    	{ xtype:'textfield',labelWidth:80, width:450, name:'subject',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.resourceConfigure.display.subject")%>',maxLength:45,allowBlank:false},
                    	{ xtype:'textareafield',labelWidth:80, width:450, name:'text',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.resourceConfigure.display.text")%>',
                    		preventScrollbars:true,maxLength:1024,height:150,growMin:150,growMax:200,allowBlank:false},
                   		{name: 'effectiveDate',labelWidth:80, width:300, fieldLabel: '<%=UnieapConstants.getMessage("comm.effectiveDate")%>',format: 'Y-m-d h:i:s', xtype: 'datefield',allowBlank:false,
                    			value:Ext.util.Format.date(new Date(),"Y-m-d h:i:s")		
                   		},
			            {name: 'expiredDate',labelWidth:80, width:300, fieldLabel: '<%=UnieapConstants.getMessage("comm.expiredDate")%>',format: 'Y-m-d h:i:s', xtype: 'datefield',allowBlank:false,
                   			value:Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.DAY,+7),"Y-m-d h:i:s")	
			            },
                    	{ xtype:'combo', labelWidth:80, width:300,forceSelection: true,editable:false,allowBlank:false,
                            name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'Y',
                            store:Ext.create('Ext.data.Store', 
                            { fields : ['dicCode', 'dicName'],data:UnieapDicdata._activeFlag})
						}
                    ]
                }
            ],
            buttonAlign: 'center',
            buttons: 
            [
                {id:'formAdd', text: '<%=UnieapConstants.getMessage("comm.add")%>',disabled:false,
                    handler: function(){
                    	operType = 'AddNotification';
                    	//dataForm.getForm().reset();
                    	dataForm.getForm().findField('subject').setReadOnly(false);
                    	dataForm.getForm().findField('subject').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('text').setReadOnly(false);
                    	dataForm.getForm().findField('text').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('activeFlag').setReadOnly(false);
                    	dataForm.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('effectiveDate').setReadOnly(false);
                    	dataForm.getForm().findField('effectiveDate').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('expiredDate').setReadOnly(false);
                    	dataForm.getForm().findField('expiredDate').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmit').enable();
                    	Ext.getCmp('formModify').disable();
                    }
                },{id:'formModify', text: '<%=UnieapConstants.getMessage("comm.modify")%>',disabled:true,
                    handler: function(){
                    	operType = 'ModifyNotification';
                    	dataForm.getForm().findField('subject').setReadOnly(false);
                    	dataForm.getForm().findField('subject').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('text').setReadOnly(false);
                    	dataForm.getForm().findField('text').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('activeFlag').setReadOnly(false);
                    	dataForm.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('effectiveDate').setReadOnly(false);
                    	dataForm.getForm().findField('effectiveDate').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('expiredDate').setReadOnly(false);
                    	dataForm.getForm().findField('expiredDate').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmit').enable();
                    }
                }, 
                {id:'formSubmit',text: '<%=UnieapConstants.getMessage("comm.submit")%>',disabled:true,
                    handler: function(){
                    	var form = dataForm.getForm();
                    	 if (form.isValid()){
                             form.submit({
                                 clientValidation: true,
                                 method: 'POST',
                                 params:{'operType':operType},
                                 waitMsg: '<%=UnieapConstants.getMessage("comm.processing")%>',
                                 url: 'easyMobileController.do?method=messageDeal',
                                 success: function(form, action) {
                                	var result = Ext.JSON.decode(action.response.responseText);
				                    if(result.isSuccess == 'failed'){
				                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
				                    }else{
	                                    	gridstoreForNotification.reload();
	                                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.save")%>',
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

    	
    	
    	
    	
    	var tabPanel = Ext.create('Ext.tab.Panel',{
	     	renderTo:'tabPanel',activeTab:0,layout: 'fit',
	     	defaults :{
	            autoScroll: true
	        },
	        items:
	        	[
					{
					    xtype: 'panel',layout: 'border',height:530,
					    title: '<%=UnieapConstants.getMessage("report.esb.error.title")%>',
					    items : [
									gridForNotification
									
					             ],
					    closable: false
					}
	        	]
	   });
    	
	});
    </script>
</head>
<body>
    <div id="tabPanel"></div>
</body>
</html>
