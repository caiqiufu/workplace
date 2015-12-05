<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>complain management</title>
    <script type="text/javascript">
    /***file uplaod begin*****************************************************/
    var allImgExt=".jpg|.png|"//全部图片格式类型
    var allowExt=".jpg|.png|" //允许上传的文件类型 ŀ为无限制 每个扩展名后边要加一个"|" 小写字母表示
    function checkFileType(fileOriginalName){
    	var fileExt = fileOriginalName.substr(fileOriginalName.lastIndexOf(".")).toLowerCase();
    	if(allowExt!=0&&allowExt.indexOf(fileExt+"|")==-1){
     	    return false;
     	}else{
     		return true;
     	}
    }
	/***file upload end**********************************************/
    
    
    
    
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
            proxy:{ type: 'ajax', url: 'mcareController.do?method=messageGrid&type=N',
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
                    	dataForm.getForm().reset();
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
                                 url: 'mcareController.do?method=messageDeal',
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
    	/***note begin*********************************************************/
    	var gridstoreForNote = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mcareController.do?method=messageGrid&type=B',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	var gridForNote = Ext.create('Ext.grid.Panel', {
            store: gridstoreForNote,
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
               	   	{ store : gridstoreForNote,displayInfo: true})
        });
    	gridstoreForNote.load();
    	gridForNote.getSelectionModel().on('selectionchange', function(sm, selectedRecord) {
            if (selectedRecord.length) {
            	dataFormNote.getForm().reset();
            	dataFormNote.getForm().setValues(selectedRecord[0].data);
            	dataFormNote.getForm().findField('subject').setReadOnly(true);
            	dataFormNote.getForm().findField('subject').inputEl.addCls('readonly_field');
            	dataFormNote.getForm().findField('text').setReadOnly(true);
            	dataFormNote.getForm().findField('text').inputEl.addCls('readonly_field');
            	dataFormNote.getForm().findField('activeFlag').setReadOnly(true);
            	dataFormNote.getForm().findField('activeFlag').inputEl.addCls('readonly_field');
            	dataFormNote.getForm().findField('effectiveDate').setReadOnly(true);
            	dataFormNote.getForm().findField('effectiveDate').inputEl.addCls('readonly_field');
            	dataFormNote.getForm().findField('expiredDate').setReadOnly(true);
            	dataFormNote.getForm().findField('expiredDate').inputEl.addCls('readonly_field');
            	dataFormNote.getForm().findField('effectiveDate').setValue(Ext.util.Format.date(selectedRecord[0].data.effectiveDate,"Y-m-d h:i:s"));
            	dataFormNote.getForm().findField('expiredDate').setValue(Ext.util.Format.date(selectedRecord[0].data.expiredDate,"Y-m-d h:i:s"));
            	Ext.getCmp('formAddNote').enable();
            	Ext.getCmp('formModifyNote').enable();
            	Ext.getCmp('formSubmitNote').disable();
            }
        });
    	var dataFormNote = Ext.widget('form',
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
                {id:'formAddNote', text: '<%=UnieapConstants.getMessage("comm.add")%>',disabled:false,
                    handler: function(){
                    	operType = 'AddNote';
                    	dataFormNote.getForm().reset();
                    	dataFormNote.getForm().findField('subject').setReadOnly(false);
                    	dataFormNote.getForm().findField('subject').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('text').setReadOnly(false);
                    	dataFormNote.getForm().findField('text').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormNote.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('effectiveDate').setReadOnly(false);
                    	dataFormNote.getForm().findField('effectiveDate').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('expiredDate').setReadOnly(false);
                    	dataFormNote.getForm().findField('expiredDate').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmitNote').enable();
                    	Ext.getCmp('formModifyNote').disable();
                    }
                },{id:'formModifyNote', text: '<%=UnieapConstants.getMessage("comm.modify")%>',disabled:true,
                    handler: function(){
                    	operType = 'ModifyNote';
                    	dataFormNote.getForm().findField('subject').setReadOnly(false);
                    	dataFormNote.getForm().findField('subject').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('text').setReadOnly(false);
                    	dataFormNote.getForm().findField('text').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormNote.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('effectiveDate').setReadOnly(false);
                    	dataFormNote.getForm().findField('effectiveDate').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('expiredDate').setReadOnly(false);
                    	dataFormNote.getForm().findField('expiredDate').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmitNote').enable();
                    }
                }, 
                {id:'formSubmitNote',text: '<%=UnieapConstants.getMessage("comm.submit")%>',disabled:true,
                    handler: function(){
                    	var form = dataFormNote.getForm();
                    	 if (form.isValid()){
                             form.submit({
                                 clientValidation: true,
                                 method: 'POST',
                                 params:{'operType':operType},
                                 waitMsg: '<%=UnieapConstants.getMessage("comm.processing")%>',
                                 url: 'mcareController.do?method=messageDeal',
                                 success: function(form, action) {
                                	var result = Ext.JSON.decode(action.response.responseText);
				                    if(result.isSuccess == 'failed'){
				                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
				                    }else{
	                                    	gridstoreForNote.reload();
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
    	
    	/***pop-up notification begin*********************************************************/
    	var gridstoreForPopup = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mcareController.do?method=messageGrid&type=P',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	var gridForPopup = Ext.create('Ext.grid.Panel', {
            store: gridstoreForPopup,
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
                {text: "<%=UnieapConstants.getMessage("comm.hyperlink")%>", dataIndex: 'hyperlink', sortable: true,width:80},
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
               	   	{ store : gridstoreForPopup,displayInfo: true})
        });
    	gridstoreForPopup.load();
    	gridForPopup.getSelectionModel().on('selectionchange', function(sm, selectedRecord) {
            if (selectedRecord.length) {
            	dataFormPopup.getForm().reset();
            	dataFormPopup.getForm().setValues(selectedRecord[0].data);
            	dataFormPopup.getForm().findField('subject').setReadOnly(true);
            	dataFormPopup.getForm().findField('subject').inputEl.addCls('readonly_field');
            	dataFormPopup.getForm().findField('text').setReadOnly(true);
            	dataFormPopup.getForm().findField('text').inputEl.addCls('readonly_field');
            	dataFormPopup.getForm().findField('hyperlink').setReadOnly(true);
            	dataFormPopup.getForm().findField('hyperlink').inputEl.addCls('readonly_field');
            	dataFormPopup.getForm().findField('activeFlag').setReadOnly(true);
            	dataFormPopup.getForm().findField('activeFlag').inputEl.addCls('readonly_field');
            	dataFormPopup.getForm().findField('effectiveDate').setReadOnly(true);
            	dataFormPopup.getForm().findField('effectiveDate').inputEl.addCls('readonly_field');
            	dataFormPopup.getForm().findField('expiredDate').setReadOnly(true);
            	dataFormPopup.getForm().findField('expiredDate').inputEl.addCls('readonly_field');
            	dataFormPopup.getForm().findField('effectiveDate').setValue(Ext.util.Format.date(selectedRecord[0].data.effectiveDate,"Y-m-d h:i:s"));
            	dataFormPopup.getForm().findField('expiredDate').setValue(Ext.util.Format.date(selectedRecord[0].data.expiredDate,"Y-m-d h:i:s"));
            	Ext.getCmp('formAddPopup').enable();
            	Ext.getCmp('formModifyPopup').enable();
            	Ext.getCmp('formSubmitPopup').disable();
            }
        });
    	var dataFormPopup = Ext.widget('form',
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
						},
                    	{ xtype:'textfield',labelWidth:80, width:450,name:'hyperlink', fieldLabel:'<%=UnieapConstants.getMessage("comm.hyperlink")%>', allowBlank:true}
                    ]
                }
            ],
            buttonAlign: 'center',
            buttons: 
            [
                {id:'formAddPopup', text: '<%=UnieapConstants.getMessage("comm.add")%>',disabled:false,
                    handler: function(){
                    	operType = 'AddPopup';
                    	dataFormPopup.getForm().reset();
                    	dataFormPopup.getForm().findField('subject').setReadOnly(false);
                    	dataFormPopup.getForm().findField('subject').inputEl.removeCls('readonly_field');
                    	dataFormPopup.getForm().findField('text').setReadOnly(false);
                    	dataFormPopup.getForm().findField('text').inputEl.removeCls('readonly_field');
                    	dataFormPopup.getForm().findField('hyperlink').setReadOnly(false);
                    	dataFormPopup.getForm().findField('hyperlink').inputEl.removeCls('readonly_field');
                    	dataFormPopup.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormPopup.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataFormPopup.getForm().findField('effectiveDate').setReadOnly(false);
                    	dataFormPopup.getForm().findField('effectiveDate').inputEl.removeCls('readonly_field');
                    	dataFormPopup.getForm().findField('expiredDate').setReadOnly(false);
                    	dataFormPopup.getForm().findField('expiredDate').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmitPopup').enable();
                    	Ext.getCmp('formModifyPopup').disable();
                    }
                },{id:'formModifyPopup', text: '<%=UnieapConstants.getMessage("comm.modify")%>',disabled:true,
                    handler: function(){
                    	operType = 'ModifyPopup';
                    	dataFormPopup.getForm().findField('subject').setReadOnly(false);
                    	dataFormPopup.getForm().findField('subject').inputEl.removeCls('readonly_field');
                    	dataFormPopup.getForm().findField('text').setReadOnly(false);
                    	dataFormPopup.getForm().findField('text').inputEl.removeCls('readonly_field');
                    	dataFormPopup.getForm().findField('hyperlink').setReadOnly(false);
                    	dataFormPopup.getForm().findField('hyperlink').inputEl.removeCls('readonly_field');
                    	dataFormPopup.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormPopup.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataFormPopup.getForm().findField('effectiveDate').setReadOnly(false);
                    	dataFormPopup.getForm().findField('effectiveDate').inputEl.removeCls('readonly_field');
                    	dataFormPopup.getForm().findField('expiredDate').setReadOnly(false);
                    	dataFormPopup.getForm().findField('expiredDate').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmitPopup').enable();
                    }
                }, 
                {id:'formSubmitPopup',text: '<%=UnieapConstants.getMessage("comm.submit")%>',disabled:true,
                    handler: function(){
                    	var form = dataFormPopup.getForm();
                    	 if (form.isValid()){
                             form.submit({
                                 clientValidation: true,
                                 method: 'POST',
                                 params:{'operType':operType},
                                 waitMsg: '<%=UnieapConstants.getMessage("comm.processing")%>',
                                 url: 'mcareController.do?method=messageDeal',
                                 success: function(form, action) {
                                	var result = Ext.JSON.decode(action.response.responseText);
				                    if(result.isSuccess == 'failed'){
				                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
				                    }else{
	                                    	gridstoreForPopup.reload();
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
    	
    	
    	/***top round pictures*************************************************/
    	var gridstoreForRound = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mcareController.do?method=groupGrid&groupNames=top_round_displays',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	var gridForRound = Ext.create('Ext.grid.Panel', {
            store: gridstoreForRound,
            columns: [
                {text: "<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.text")%>", dataIndex: 'text',sortable: true,width:200,
                	renderer: function (value, meta, record){
						var max = 150;
						meta.tdAttr = 'data-qtip="' + value + '"';
						return value.length < max ? value : value.substring(0, max - 3) + '...';
					}	
                },
                {text: "<%=UnieapConstants.getMessage("comm.hyperlink")%>", dataIndex: 'hyperlink', sortable: true,flex: true,
                	renderer: function (value, meta, record){
						var max = 150;
						meta.tdAttr = 'data-qtip="' + value + '"';
						return value.length < max ? value : value.substring(0, max - 3) + '...';
					}	
                
                },
                { text: "<%=UnieapConstants.getMessage("comm.modifyDate")%>", dataIndex: 'modifyDate',sortable: false,width:150,
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
               	   	{ store : gridstoreForRound,displayInfo: true})
        });
    	gridstoreForRound.load();
    	gridForRound.getSelectionModel().on('selectionchange', function(sm, selectedRecord) {
            if (selectedRecord.length) {
            	dataFormRound.getForm().reset();
            	dataFormRound.getForm().setValues(selectedRecord[0].data);
            	dataFormRound.getForm().findField('resolution').setReadOnly(true);
            	dataFormRound.getForm().findField('resolution').inputEl.addCls('readonly_field');
            	dataFormRound.getForm().findField('hyperlink').setReadOnly(true);
            	dataFormRound.getForm().findField('hyperlink').inputEl.addCls('readonly_field');
            	dataFormRound.getForm().findField('activeFlag').setReadOnly(true);
            	dataFormRound.getForm().findField('activeFlag').inputEl.addCls('readonly_field');
            	dataFormRound.getForm().findField('file').setReadOnly(true);
            	dataFormRound.getForm().findField('file').inputEl.addCls('readonly_field');
            	dataFormRound.getForm().findField('file').hide();
            	Ext.getCmp('formModifyRound').enable();
            	Ext.getCmp('formSubmitRound').disable();
            	Ext.getCmp('previewRound').enable();
            }
        });
    	var previewPictureWinRound;
    	var dataFormRound = Ext.widget('form',
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
                    	{ xtype:'textfield',hidden: true, name:'name'},
                    	{ xtype:'textfield',hidden: true, name:'groupName'},
                    	{ xtype:'textfield',hidden: true, name:'pageNum'},
                    	{ xtype:'textfield',hidden: true, name:'seq'},
                    	{ xtype:'textfield',hidden: true, name:'url'},
                    	{ xtype:'combo', labelWidth:80, width:450,forceSelection: true,editable:false,allowBlank:false,
                            name:'resolution',fieldLabel:'<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.resolution")%>',displayField:'dicName',valueField:'dicCode',value:'2',
                            store:Ext.create('Ext.data.Store', 
                            { fields : ['dicCode', 'dicName'],data:UnieapDicdata._resolution})
						},
                    	{ xtype:'combo', labelWidth:80, width:450,forceSelection: true,editable:false,allowBlank:false,
                            name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'Y',
                            store:Ext.create('Ext.data.Store', 
                            { fields : ['dicCode', 'dicName'],data:UnieapDicdata._activeFlag})
						},
                    	{ xtype:'textfield',labelWidth:80, width:450,name:'hyperlink', fieldLabel:'<%=UnieapConstants.getMessage("comm.hyperlink")%>', allowBlank:true},
                    	{ xtype:'filefield',name: 'file',id:'uploadFileFormRound',labelWidth:80,width:420,fieldLabel:'<%=UnieapConstants.getMessage("comm.uploadfile")%>',msgTarget: 'side',
							listeners : {
			    	        	 change : function(obj, v){
			    	        		 var picPath = obj.getValue();  
			    	                 var url = 'file:///' + picPath;  
			    	                 if(Ext.isIE){  
			    	                      var image = Ext.get('previewImgRound');   
			    	                      image.src = Ext.BLANK_IMAGE_URL;  
			    	                      var fileDom = obj.fileInputEl.dom;
			    	                      fileDom.select();
			    	                      image.imgEl.dom.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = document.selection.createRange().text;
			    	                      image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = url;   
			    	                 }else{ 
			    	                	  var file = obj.fileInputEl.dom.files[0];
			    	                	  url = URL.createObjectURL(file);
			    	                 } 
			    	                 if(previewPictureWinRound!=null){
			    	                	 previewPictureWinRound.destroy();
			    	                 }
			    	                 previewPictureWinRound = Ext.widget('window', 
			    	     	              { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', layout: 'fit', modal: true,
			    	        			 		items: {
			    	        			 			xtype:'panel',border:false,
			    	        			 			html:'<img id="previewImgRound" src='+url+' style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);"></img>'
			    	        			 		} 
			    	        			 });
			    	                 previewPictureWinRound.show();
			    	                 resizePreviewPictureWindowRound();
			    	        	 }
							},
							allowBlank:true,anchor: '100%',buttonText:'<%=UnieapConstants.getMessage("comm.fileupload")%>'
						},
						{ xtype:'button',name: 'url',id:'previewRound',text:'<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.preview")%>',disabled:true,
							handler:function(){
								var url = dataFormRound.getForm().findField('url').getValue();
								window.open(url);
								
							}
						}
                    ]
                }
            ],
            buttonAlign: 'center',
            buttons: 
            [
                {id:'formModifyRound', text: '<%=UnieapConstants.getMessage("comm.modify")%>',disabled:true,
                    handler: function(){
                    	operType = 'ModifyRound';
                    	dataFormRound.getForm().findField('hyperlink').setReadOnly(false);
                    	dataFormRound.getForm().findField('hyperlink').inputEl.removeCls('readonly_field');
                    	dataFormRound.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormRound.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataFormRound.getForm().findField('file').show();
                    	Ext.getCmp('formSubmitRound').enable();
                    }
                }, 
                {id:'formSubmitRound',text: '<%=UnieapConstants.getMessage("comm.submit")%>',disabled:true,
                    handler: function(){
                    	var form = dataFormRound.getForm();
                        var previewImg = document.getElementById('previewImgRound');
                        var fileOriginalName = form.findField('uploadFileFormRound').getValue();
                        var resolution = form.findField('resolution').getValue();
                        if(fileOriginalName != '' && fileOriginalName != null){
                        	if(checkFileType(fileOriginalName)){
                        		var pWidth , pHeight;
                            	if(resolution=='1'){
                            		pWidth = 720;
                            		pHeight = 300;
                            	}else if(resolution=='2'){
                            		pWidth = 720;
                            		pHeight = 300;
                            	}else if(resolution=='3'){
                            		pWidth = 720;
                            		pHeight = 300;
                            	}
                           		if(previewImg.width< pWidth-5 ||previewImg.width >pWidth+5 || previewImg.height< pHeight-5 ||previewImg.height >pHeight+5){
                               		Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',
                               			msg:'<%=UnieapConstants.getMessage("comm.fileupload.filesize","720,300")%>',
                                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                               		return;
                               	}else{
                               		submitRound(form);
                               	}
                           		
                        	}else{
                        		Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',
                        			msg:'<%=UnieapConstants.getMessage("comm.fileupload.filetype",".jpg|.png")%>',
                         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                        	}
                        }else{
                        	submitRound(form);
                        }
                    }
                }
            ]
        });
    	
    	function submitRound(form){
    		var id = form.findField('id').getValue();
        	var hyperlink = form.findField('hyperlink').getValue();
        	var activeFlag = form.findField('activeFlag').getValue();
        	var parameters = Ext.JSON.encode({'id':id,'activeFlag':activeFlag,'hyperlink':hyperlink})
        	if (form.isValid()){
                form.submit({
                    clientValidation: true,
                    method: 'POST',
                    params:{'operType':operType},
                    waitMsg: '<%=UnieapConstants.getMessage("comm.processing")%>',
                    url: 'mcareController.do?method=resourceConfigurePictureDeal&parameters='+parameters,
                    success: function(form, action) {
                   	var result = Ext.JSON.decode(action.response.responseText);
	                    if(result.isSuccess == 'failed'){
	                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
                    		buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                    }else{
	                    	gridstoreForRound.reload();
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
    	
    	
    	/***ad pictures*************************************************/
    	var gridstoreForAd = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mcareController.do?method=groupGrid&names=app_ad_page,chongzhi_ad',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	var gridForAd = Ext.create('Ext.grid.Panel', {
            store: gridstoreForAd,
            columns: [
                {text: "<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.text")%>", dataIndex: 'text',sortable: true,width:200,
                	renderer: function (value, meta, record){
						var max = 150;
						meta.tdAttr = 'data-qtip="' + value + '"';
						return value.length < max ? value : value.substring(0, max - 3) + '...';
					}	
                },
                {text: "<%=UnieapConstants.getMessage("comm.hyperlink")%>", dataIndex: 'hyperlink', sortable: true,flex: true,
                	renderer: function (value, meta, record){
						var max = 150;
						meta.tdAttr = 'data-qtip="' + value + '"';
						return value.length < max ? value : value.substring(0, max - 3) + '...';
					}	
                
                },
                { text: "<%=UnieapConstants.getMessage("comm.modifyDate")%>", dataIndex: 'modifyDate',sortable: false,width:150,
                	renderer: function (value, meta, record){
						var max = 150;
						meta.tdAttr = 'data-qtip="' + value + '"';
						return value.length < max ? value : value.substring(0, max - 3) + '...';
					}	
                },
                { text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>",dataIndex: 'activeFlagDesc',sortable: false,width:60}
            ],
            flex: true,region: 'center',layout:'fit',
            bbar:new Ext.PagingToolbar(
               	   	{ store : gridstoreForAd,displayInfo: true})
        });
    	gridstoreForAd.load();
    	gridForAd.getSelectionModel().on('selectionchange', function(sm, selectedRecord) {
            if (selectedRecord.length) {
            	dataFormAd.getForm().reset();
            	dataFormAd.getForm().setValues(selectedRecord[0].data);
            	dataFormAd.getForm().findField('resolution').setReadOnly(true);
            	dataFormAd.getForm().findField('resolution').inputEl.addCls('readonly_field');
            	dataFormAd.getForm().findField('hyperlink').setReadOnly(true);
            	dataFormAd.getForm().findField('hyperlink').inputEl.addCls('readonly_field');
            	dataFormAd.getForm().findField('activeFlag').setReadOnly(true);
            	dataFormAd.getForm().findField('activeFlag').inputEl.addCls('readonly_field');
            	dataFormAd.getForm().findField('file').setReadOnly(true);
            	dataFormAd.getForm().findField('file').inputEl.addCls('readonly_field');
            	dataFormAd.getForm().findField('file').hide();
            	Ext.getCmp('formModifyAd').enable();
            	Ext.getCmp('formSubmitAd').disable();
            	Ext.getCmp('previewAd').enable();
            }
        });
    	var previewPictureWin;
    	var dataFormAd = Ext.widget('form',
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
                    	{ xtype:'textfield',hidden: true, name:'name'},
                    	{ xtype:'textfield',hidden: true, name:'groupName'},
                    	{ xtype:'textfield',hidden: true, name:'pageNum'},
                    	{ xtype:'textfield',hidden: true, name:'seq'},
                    	{ xtype:'textfield',hidden: true, name:'url'},
                    	{ xtype:'textfield',hidden: true, name:'remark'},
						{ xtype:'combo', labelWidth:80, width:450,forceSelection: true,editable:false,allowBlank:false,
                            name:'resolution',fieldLabel:'<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.resolution")%>',displayField:'dicName',valueField:'dicCode',value:'2',
                            store:Ext.create('Ext.data.Store', 
                            { fields : ['dicCode', 'dicName'],data:UnieapDicdata._resolution})
						},
						{ xtype:'combo', labelWidth:80, width:450,forceSelection: true,editable:false,allowBlank:false,
                            name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'Y',
                            store:Ext.create('Ext.data.Store', 
                            { fields : ['dicCode', 'dicName'],data:UnieapDicdata._activeFlag})
						},
						{ xtype:'textfield',labelWidth:80, width:450,name:'hyperlink', fieldLabel:'<%=UnieapConstants.getMessage("comm.hyperlink")%>', allowBlank:true},
						{ xtype:'filefield',name: 'file',id:'uploadFileFormAd',labelWidth:80,width:420,fieldLabel:'<%=UnieapConstants.getMessage("comm.uploadfile")%>',msgTarget: 'side',
							listeners : {
			    	        	 change : function(obj, v){
			    	        		 var picPath = obj.getValue();  
			    	                 var url = 'file:///' + picPath;  
			    	                 if(Ext.isIE){  
			    	                      var image = Ext.get('previewImg');   
			    	                      image.src = Ext.BLANK_IMAGE_URL;  
			    	                      var fileDom = obj.fileInputEl.dom;
			    	                      fileDom.select();
			    	                      image.imgEl.dom.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = document.selection.createRange().text;
			    	                      image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = url;   
			    	                 }else{ 
			    	                	  var file = obj.fileInputEl.dom.files[0];
			    	                	  url = URL.createObjectURL(file);
			    	                 } 
			    	                 if(previewPictureWin!=null){
				    	                 previewPictureWin.destroy();
			    	                 }
			    	                 previewPictureWin = Ext.widget('window', 
			    	     	              { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', layout: 'fit', modal: true,
			    	        			 		items: {
			    	        			 			xtype:'panel',border:false,
			    	        			 			html:'<img id="previewImg" src='+url+' style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);"></img>'
			    	        			 		} 
			    	        			 });
			    	                 previewPictureWin.show();
			    	                 resizePreviewPictureWindow();
			    	        	 }
							},
							allowBlank:true,anchor: '100%',buttonText:'<%=UnieapConstants.getMessage("comm.fileupload")%>'
						},
						{ xtype:'button',name: 'url',id:'previewAd',text:'<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.preview")%>',disabled:true,
							handler:function(){
								var url = dataFormAd.getForm().findField('url').getValue();
								window.open(url);
							}
						}
                    ]
                }
            ],
            buttonAlign: 'center',
            buttons: 
            [
                {id:'formModifyAd', text: '<%=UnieapConstants.getMessage("comm.modify")%>',disabled:true,
                    handler: function(){
                    	operType = 'ModifyAd';
                    	dataFormAd.getForm().findField('hyperlink').setReadOnly(false);
                    	dataFormAd.getForm().findField('hyperlink').inputEl.removeCls('readonly_field');
                    	dataFormAd.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormAd.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataFormAd.getForm().findField('file').show();
                    	Ext.getCmp('formSubmitAd').enable();
                    }
                }, 
                {id:'formSubmitAd',text: '<%=UnieapConstants.getMessage("comm.submit")%>',disabled:true,
                    handler: function(){
                    	var form = dataFormAd.getForm();
                        var previewImg = document.getElementById('previewImg');
                        var fileOriginalName = form.findField('uploadFileFormAd').getValue();
                        var resolution = form.findField('resolution').getValue();
                        var fileName = form.findField('name').getValue();
                    	if(fileOriginalName != '' && fileOriginalName != null){
                        	if(checkFileType(fileOriginalName)){
                        		var pWidth , pHeight;
                            	if(resolution=='1'){
                            		pWidth = 600;
                            		pHeight = 600;
                            	}else if(resolution=='2'){
                            		pWidth = 600;
                            		pHeight = 600;
                            	}else if(resolution=='3'){
                            		pWidth = 600;
                            		pHeight = 600;
                            	}
                           		if(previewImg.width< pWidth-5 ||previewImg.width >pWidth+5 || previewImg.height< pHeight-5 ||previewImg.height >pHeight+5){
                               		Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',
                               			msg:'<%=UnieapConstants.getMessage("comm.fileupload.filesize","600,600")%>',
                                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                               		return;
                               	}else{
                               		submitAd(form);
                               	}
                           		
                        	}else{
                        		Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',
                        			msg:'<%=UnieapConstants.getMessage("comm.fileupload.filetype",".jpg|.png")%>',
                         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                        	}
                        }else{
                        	submitAd(form);
                        }
                    }
                }
            ]
        });
    	
    	function submitAd(form){
    		var id = form.findField('id').getValue();
        	var hyperlink = form.findField('hyperlink').getValue();
        	var activeFlag = form.findField('activeFlag').getValue();
        	var parameters = Ext.JSON.encode({'id':id,'activeFlag':activeFlag,'hyperlink':hyperlink})
        	if (form.isValid()){
                form.submit({
                    clientValidation: true,
                    method: 'POST',
                    params:{'operType':operType},
                    waitMsg: '<%=UnieapConstants.getMessage("comm.processing")%>',
                    url: 'mcareController.do?method=resourceConfigurePictureDeal&parameters='+parameters,
                    success: function(form, action) {
                   	var result = Ext.JSON.decode(action.response.responseText);
	                    if(result.isSuccess == 'failed'){
	                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
                    		buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                    }else{
	                    	gridstoreForAd.reload();
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
    	/***denomination configure begin*********************************************************/
    	
    	Ext.define('denodatamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'id','seq','type','typeDesc','value','denoValue','handleFee','attri1','attri2','activeFlag','activeFlagDesc','modifyDate','modifyBy','remark',
            	'createDate','createBy','effectiveDate','expiredDate'
            ],
            idProperty: 'id'
        });
    	
    	var gridstoreForDeno = Ext.create('Ext.data.Store', {
            model: 'denodatamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mcareController.do?method=denoGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	var gridForDeno = Ext.create('Ext.grid.Panel', {
            store: gridstoreForDeno,
            columns: [
						{text: "<%=UnieapConstants.getMessage("comm.seq")%>",  dataIndex: 'seq', sortable: false,width:80},
						{text: "<%=UnieapConstants.getMessage("comm.type")%>", dataIndex: 'typeDesc', sortable: false,width:80},
						{text: "<%=UnieapConstants.getMessage("mcare.denomination.display.value")%>", dataIndex: 'value',sortable: false,width:80},
						{text: "<%=UnieapConstants.getMessage("mcare.denomination.display.denoValue")%>", dataIndex: 'denoValue',sortable: false,width:80},
						{ text: "<%=UnieapConstants.getMessage("comm.effectiveDate")%>", dataIndex: 'effectiveDate',sortable: false,width:150,
							renderer: function (value, meta, record){
								var max = 150;
								meta.tdAttr = 'data-qtip="' + value + '"';
								return value.length < max ? value : value.substring(0, max - 3) + '...';
							}	
						},
						{ text: "<%=UnieapConstants.getMessage("comm.expiredDate")%>", dataIndex: 'expiredDate',sortable: false,width:150,
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
               	   	{ store : gridstoreForDeno,displayInfo: true})
        });
    	gridstoreForDeno.load();
    	gridForDeno.getSelectionModel().on('selectionchange', function(sm, selectedRecord) {
            if (selectedRecord.length) {
            	dataFormDeno.getForm().reset();
            	dataFormDeno.getForm().setValues(selectedRecord[0].data);
            	dataFormDeno.getForm().findField('seq').setReadOnly(true);
            	dataFormDeno.getForm().findField('seq').inputEl.addCls('readonly_field');
            	dataFormDeno.getForm().findField('type').setReadOnly(true);
            	dataFormDeno.getForm().findField('type').inputEl.addCls('readonly_field');
            	dataFormDeno.getForm().findField('value').setReadOnly(true);
            	dataFormDeno.getForm().findField('value').inputEl.addCls('readonly_field');
            	dataFormDeno.getForm().findField('denoValue').setReadOnly(true);
            	dataFormDeno.getForm().findField('denoValue').inputEl.addCls('readonly_field');
            	dataFormDeno.getForm().findField('effectiveDate').setReadOnly(true);
            	dataFormDeno.getForm().findField('effectiveDate').inputEl.addCls('readonly_field');
            	dataFormDeno.getForm().findField('expiredDate').setReadOnly(true);
            	dataFormDeno.getForm().findField('expiredDate').inputEl.addCls('readonly_field');
            	dataFormDeno.getForm().findField('effectiveDate').setValue(Ext.util.Format.date(selectedRecord[0].data.effectiveDate,"Y-m-d h:i:s"));
            	dataFormDeno.getForm().findField('expiredDate').setValue(Ext.util.Format.date(selectedRecord[0].data.expiredDate,"Y-m-d h:i:s"));
            	dataFormDeno.getForm().findField('activeFlag').setReadOnly(true);
            	dataFormDeno.getForm().findField('activeFlag').inputEl.addCls('readonly_field');
            	Ext.getCmp('formAddDeno').enable();
            	Ext.getCmp('formModifyDeno').enable();
            	Ext.getCmp('formSubmitDeno').disable();
            }
        });
    	var dataFormDeno = Ext.widget('form',
           {
    		layout:'fit',defaults:{labelAlign: 'left', labelWidth: 90, anchor: '100%'},
            bodyPadding:5,
            items:
            [
            	{xtype:'fieldset', title:'<%=UnieapConstants.getMessage("comm.data")%>',
                    items:
                    [
                    	{ xtype:'textfield',hidden: true, name:'id'},
                    	{ xtype:'textfield',hidden: true, name:'handleFee'},
                    	{ xtype:'textfield',hidden: true, name:'attri1'},
                    	{ xtype:'textfield',hidden: true, name:'attri2'},
                    	{ xtype:'textfield',hidden: true, name:'createDate'},
                    	{ xtype:'textfield',hidden: true, name:'createBy'},
                    	{ xtype:'textfield',hidden: true, name:'remark'},
                    	{ xtype:'numberfield',labelWidth:80, width:300, name:'seq',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("comm.seq")%>',maxLength:45,allowBlank:false},
                    	{ xtype:'combo', labelWidth:80, width:300,forceSelection: true,editable:false,allowBlank:false,
                            name:'type',fieldLabel:'<%=UnieapConstants.getMessage("comm.type")%>',displayField:'dicName',valueField:'dicCode',allowBlank:false,
                            store:Ext.create('Ext.data.Store', 
                            { fields : ['dicCode', 'dicName'],data:UnieapDicdata._denomination})
						},
						{ xtype:'numberfield',labelWidth:80, width:300, name:'value',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.denomination.display.value")%>',maxLength:45,allowBlank:false},
						{ xtype:'textfield',labelWidth:80, width:300,name:'denoValue', fieldLabel:'<%=UnieapConstants.getMessage("mcare.denomination.display.denoValue")%>', allowBlank:true},
                   		{name: 'effectiveDate',labelWidth:80, width:300, fieldLabel: '<%=UnieapConstants.getMessage("comm.effectiveDate")%>',format: 'Y-m-d h:i:s', xtype: 'datefield',allowBlank:false,
                    		value:Ext.util.Format.date(new Date(),"Y-m-d h:i:s")
                   		},
   			            {name: 'expiredDate',labelWidth:80, width:300, fieldLabel: '<%=UnieapConstants.getMessage("comm.expiredDate")%>',format: 'Y-m-d h:i:s', xtype: 'datefield',allowBlank:false,
                   			value:Ext.util.Format.date(Ext.Date.add(new Date(),Ext.Date.DAY,+1000),"Y-m-d h:i:s")
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
                {id:'formAddDeno', text: '<%=UnieapConstants.getMessage("comm.add")%>',disabled:false,
                    handler: function(){
                    	operType = 'AddDeno';
                    	dataFormDeno.getForm().reset();
                    	dataFormDeno.getForm().findField('seq').setReadOnly(false);
                    	dataFormDeno.getForm().findField('seq').inputEl.removeCls('readonly_field');
                    	dataFormDeno.getForm().findField('type').setReadOnly(false);
                    	dataFormDeno.getForm().findField('type').inputEl.removeCls('readonly_field');
                    	dataFormDeno.getForm().findField('value').setReadOnly(false);
                    	dataFormDeno.getForm().findField('value').inputEl.removeCls('readonly_field');
                    	dataFormDeno.getForm().findField('denoValue').setReadOnly(false);
                    	dataFormDeno.getForm().findField('denoValue').inputEl.removeCls('readonly_field');
                    	dataFormDeno.getForm().findField('effectiveDate').setReadOnly(false);
                    	dataFormDeno.getForm().findField('effectiveDate').inputEl.removeCls('readonly_field');
                    	dataFormDeno.getForm().findField('expiredDate').setReadOnly(false);
                    	dataFormDeno.getForm().findField('expiredDate').inputEl.removeCls('readonly_field');
                    	dataFormDeno.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormDeno.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmitDeno').enable();
                    	Ext.getCmp('formModifyDeno').disable();
                    }
                },{id:'formModifyDeno', text: '<%=UnieapConstants.getMessage("comm.modify")%>',disabled:true,
                    handler: function(){
                    	operType = 'ModifyDeno';
                    	dataFormDeno.getForm().findField('seq').setReadOnly(false);
                    	dataFormDeno.getForm().findField('seq').inputEl.removeCls('readonly_field');
                    	dataFormDeno.getForm().findField('type').setReadOnly(false);
                    	dataFormDeno.getForm().findField('type').inputEl.removeCls('readonly_field');
                    	dataFormDeno.getForm().findField('value').setReadOnly(false);
                    	dataFormDeno.getForm().findField('value').inputEl.removeCls('readonly_field');
                    	dataFormDeno.getForm().findField('denoValue').setReadOnly(false);
                    	dataFormDeno.getForm().findField('denoValue').inputEl.removeCls('readonly_field');
                    	dataFormDeno.getForm().findField('effectiveDate').setReadOnly(false);
                    	dataFormDeno.getForm().findField('effectiveDate').inputEl.removeCls('readonly_field');
                    	dataFormDeno.getForm().findField('expiredDate').setReadOnly(false);
                    	dataFormDeno.getForm().findField('expiredDate').inputEl.removeCls('readonly_field');
                    	dataFormDeno.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormDeno.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmitDeno').enable();
                    }
                }, 
                {id:'formSubmitDeno',text: '<%=UnieapConstants.getMessage("comm.submit")%>',disabled:true,
                    handler: function(){
                    	var form = dataFormDeno.getForm();
                    	 if (form.isValid()){
                             form.submit({
                                 clientValidation: true,
                                 method: 'POST',
                                 params:{'operType':operType},
                                 waitMsg: '<%=UnieapConstants.getMessage("comm.processing")%>',
                                 url: 'mcareController.do?method=denoDeal',
                                 success: function(form, action) {
                                	var result = Ext.JSON.decode(action.response.responseText);
				                    if(result.isSuccess == 'failed'){
				                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
				                    }else{
	                                    	gridstoreForDeno.reload();
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
					    title: '<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.notification")%>',
					    items : [
									gridForNotification,
									{
									    region: 'east',
									    width: 500,
									    items: [dataForm]
									}
					             ],
					    closable: false
					}, {
					    xtype: 'panel',layout: 'border',height:530,
					    title: '<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.note")%>',
					    items : [
									gridForNote,
									{
									    region: 'east',
									    width: 500,
									    items: [dataFormNote]
									}
					             ],
					    closable: false
					}, {
					    xtype: 'panel',layout: 'border',height:530,
					    title: '<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.popup")%>',
					    items : [
									gridForPopup,
									{
									    region: 'east',
									    width: 500,
									    items: [dataFormPopup]
									}
					             ],
					    closable: false
					}, {
						xtype: 'panel',layout: 'border',height:530,
					    title: '<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.round")%>',
					    items : [
									gridForRound,
									{
									    region: 'east',
									    width:500,
									    items: [dataFormRound]
									}
					             ],
					    closable: false
					}, {
						xtype: 'panel',layout: 'border',height:550,
					    title: '<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.ad")%>',
					    items : [
									gridForAd,
									{
									    region: 'east',
									    width:500,
									    items: [dataFormAd]
									}
					             ],
					    closable: false
					}, {
						xtype: 'panel',layout: 'border',height:550,
					    title: '<%=UnieapConstants.getMessage("mcare.denomination.display.title")%>',
					    items : [
									gridForDeno,
									{
									    region: 'east',
									    width:500,
									    items: [dataFormDeno]
									}
					             ],
					    closable: false
					}
	        	]
	   });
      
      /***round task********************************************/
      var taskRound;
   	  function resizePreviewPictureWindowRound(){
   		     if(taskRound == null){
   		    	taskRound = {
   			   			run : function(){
		   			   		 var previewImgRound = document.getElementById('previewImgRound');
		   		   	    	 if(previewImgRound!=null){
		   			   	    	 if(previewImgRound.height > 10){
		   			   	    		 if(previewImgRound.width>750){
		   			   	    			previewPictureWinRound.setWidth(750);
		   			   	    		 }else{
		   			   	    			previewPictureWinRound.setWidth(previewImgRound.width);
		   			   	    		 }
		   			   	    		 if(previewImgRound.height>300){
		   			   	    			previewPictureWinRound.setHeight(320);
		   			   	    		 }else{
		   			  	    		 	previewPictureWinRound.setHeight(previewImgRound.height);
		   			   	    		 }
		   			  	    		 previewPictureWinRound.center();
		   			  	    		 Ext.TaskManager.stop(taskRound);   
		   			   	    	 }
		   		   	    	 }
   			   			},
   			   			interval : 500
   			   		}
   		   		Ext.TaskManager.start(taskRound);
   		     }else{
   		    	Ext.TaskManager.start(taskRound);
   		     }
   	    }
   	   /***ad task********************************************/
       var task;
   	   function resizePreviewPictureWindow(){
   		     if(task == null){
   		    	task = {
   			   			run : function(){
		   			   		 var previewImg = document.getElementById('previewImg');
		   		   	    	 if(previewImg!=null){
		   			   	    	 if(previewImg.height > 10){
		   			   	    		 if(previewImg.width>750){
		   			   	    			previewPictureWin.setWidth(750);
		   			   	    		 }else{
		   			   	    			previewPictureWin.setWidth(previewImg.width);
		   			   	    		 }
		   			   	    		 if(previewImg.height>300){
		   			   	    			previewPictureWin.setHeight(320);
		   			   	    		 }else{
		   			  	    		 	previewPictureWin.setHeight(previewImg.height);
		   			   	    		 }
		   			  	    		 previewPictureWin.center();
		   			  	    		 Ext.TaskManager.stop(task);   
		   			   	    	 }
		   		   	    	 }
   			   			},
   			   			interval : 500
   			   		}
   		   		Ext.TaskManager.start(task);
   		     }else{
   		    	Ext.TaskManager.start(task);
   		     }
   	    }
    	
	});
    </script>
</head>
<body>
    <div id="tabPanel"></div>
</body>
</html>
