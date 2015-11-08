<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>complain management</title>
    <script type="text/javascript">
    //显示中文异常
    /***file uplaod begin*****************************************************/
	var imgObj=new Image();   //建立一个图像对象
    var allImgExt=".jpg|.png|"//全部图片格式类型
    var fileObj,imgFileSize,imgWidth,imgHeight,fileExt,errMsg,fileMsg,hasCheked,isImg//全局变量 图片相关属性
  	//以下为限制变量
    var allowExt=".jpg|.gif" //允许上传的文件类型 ŀ为无限制 每个扩展名后边要加一个"|" 小写字母表示
    var allowImgFileSize=70;  //允许上传图片文件的大小 0为无限制  单位：KB 
    var allowImgWidth=500;   //允许上传的图片的宽度 ŀ为无限制　单位：px(像素)
    var allowImgHeight=500;   //允许上传的图片的高度 ŀ为无限制　单位：px(像素)
    hasChecked=false;
    //检测图像属性
    function checkProperty(obj){
	  	  fileObj=obj;
	  	  //检测是否为正确的图像文件　返回出错信息并重置
	   	  if(errMsg!=""){
	   	    	showMsg(errMsg,false);
	   	    	return false;   //返回
	   	  }
	   	  //如果图像是未加载完成进行循环检测
	   	  if(imgObj.readyState!="complete"){
	   	    	setTimeout("checkProperty(fileObj)",500);
	   	    	return false;
	   	  }
	   	  imgFileSize=Math.round(imgObj.fileSize/1024*100)/100;//取得图片文件的大小
	   	  imgWidth=imgObj.width   //取得图片的宽度
	   	  imgHeight=imgObj.height;  //取得图片的高度
	   	  fileMsg="\n图片大小:"+imgWidth+"*"+imgHeight+"px";
	   	  fileMsg=fileMsg+"\n图片文件大小:"+imgFileSize+"Kb";
	   	  fileMsg=FileMsg+"\n图片文件扩展名:"+fileExt;
	   	  if(allowImgWidth!=0&&allowImgWidth<imgWidth){
	   		    errMsg=errMsg+"\n图片宽度超过限制。请上传宽度小于"+allowImgWidth+"px的文件，当前图片宽度为"+imgWidth+"px";
	   	  }
	  		  if(allowImgHeight!=0&&allowImgHeight<imgHeight){
	  		    	errMsg=errMsg+"\n图片高度超过限制。请上传高度小于"+allowImgHeight+"px的文件，当前图片高度为"+imgHeight+"px";
	  		  }
	  		  if(allowImgFileSize!=0&&allowImgFileSize<imgFileSize){
	  		   	 	errMsg=ErrMsg+"\n图片文件大小超过限制。请上传小于"+allowImgFileSize+"KB的文件，当前文件大小为"+imgFileSize+"KB";
	  		  }
	  		  if(errMsg!=""){
	  		    	showMsg(ErrMsg,false);
	  		  }
	  		  else{
	  		    	showMsg(FileMsg,true);
	  		  }
    }
    imgObj.onerror=function(){errMsg='\n图片格式不正确或者图片已损坏!'};
  	//显示提示信息 tf=true 显示文件信息 tf=false 显示错误信息 msg-信息内容
    function showMsg(msg,tf){
    	 msg=msg.replace("\n","<li>");
    	 msg=msg.replace(/\n/gi,"<li>");
    	 if(!tf){
    	    	fileObj.outerHTML=fileObj.outerHTML;
    	    	document.getElementById("preview").innerHTML=msg;
    	   	 	hasChecked=false;
    	  } else{
    	    	if(isImg){
    	       		document.getElementById("preview").innerHTML="<img src='"+imgObj.src+"' width='60' height='60'>"
    	    	} else{
    	     		document.getElementById("preview").innerHTML=msg;
    	    	}
    	     // document.getElementById("preview").innerHTML="非图片文件";
    	    	hasChecked=true;
    	  }
    }
    function checkExt(obj){
 	 	errMsg="";
   	  	fileMsg="";
   	  	fileObj=obj;
   	  	isImg=false;
   	  	hasChecked=false;
   	 	document.getElementById("preview").innerHTML="预览区";
   	 	if(obj.value==""){
   			return false;
   	 	}
     	fileExt=obj.value.substr(obj.value.lastIndexOf(".")).toLowerCase();
     	//判断文件类型是否允许上传
     	if(allowExt!=0&&allowExt.indexOf(fileExt+"|")==-1){
     		errMsg="\n该文件类型不允许上传。请上传 "+allowExt+" 类型的文件，当前文件类型为"+fileExt;
     	    showMsg(errMsg,false);
     	    return false;
     	}
     	//如果图片文件，则进行图片信息处理
     	if(allImgExt.indexOf(fileExt+"|")!=-1){
     		isImg=true;
     	    imgObj.src=obj.value;
     	    checkProperty(obj);
     	    return false;
     	}else{
     		fileMsg="\n文件扩展名:"+fileExt;
     	   	showMsg(fileMsg,true);
     	}
     	
   	 	
    }
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
            	'id','type','name','text','subject','url','hyperlink','groupName','resolution','activeFlag','activeFlagDesc','pageNum','modifyDate','seq','modifyBy','language','remark'
            ],
            idProperty: 'id'
        });
    	
    	var gridstoreForNotification = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mcareController.do?method=groupGrid&groupName=message_center_notification',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	var gridForNotification = Ext.create('Ext.grid.Panel', {
            store: gridstoreForNotification,
            columns: [
                {text: "<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.subject")%>",  dataIndex: 'subject', sortable: false,width:120,
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
                {text: "<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.hyperlink")%>", dataIndex: 'hyperlink', sortable: true,width:80},
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
            	dataForm.getForm().findField('hyperlink').setReadOnly(true);
            	dataForm.getForm().findField('hyperlink').inputEl.addCls('readonly_field');
            	dataForm.getForm().findField('activeFlag').setReadOnly(true);
            	dataForm.getForm().findField('activeFlag').inputEl.addCls('readonly_field');
            	dataForm.getForm().findField('remark').setReadOnly(true);
            	dataForm.getForm().findField('remark').inputEl.addCls('readonly_field');
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
                    	{ xtype:'textfield',hidden: true, name:'name'},
                    	{ xtype:'textfield',hidden: true, name:'groupName'},
                    	{ xtype:'textfield',hidden: true, name:'resolution'},
                    	{ xtype:'textfield',hidden: true, name:'pageNum'},
                    	{ xtype:'textfield',hidden: true, name:'seq'},
                    	{ xtype:'textfield',hidden: true, name:'url'},
                    	{ xtype:'textfield',hidden: true, name:'resolution'},
                    	{ xtype:'textfield',labelWidth:80, width:450, name:'subject',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.resourceConfigure.display.subject")%>',maxLength:45,allowBlank:false},
                    	{ xtype:'textareafield',labelWidth:80, width:450, name:'text',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.resourceConfigure.display.text")%>',
                    		preventScrollbars:true,maxLength:1024,height:150,growMin:150,growMax:200,allowBlank:false},
                    	{ xtype:'textfield',labelWidth:80, width:450,name:'hyperlink', fieldLabel:'<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.hyperlink")%>', allowBlank:true},
                    	{ xtype:'combo', labelWidth:80, width:450,forceSelection: true,editable:false,allowBlank:false,
                            name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'Y',
                            store:Ext.create('Ext.data.Store', 
                            { fields : ['dicCode', 'dicName'],data:UnieapDicdata._activeFlag})
						},
                    	{ xtype:'textareafield',labelWidth:80, width:450, name:'remark',fieldLabel:'<%=UnieapConstants.getMessage("comm.remark")%>',maxLength:255,growMin:60,growMax:100,allowBlank:true}
                    ]
                }
            ],
            buttonAlign: 'center',
            buttons: 
            [
                {id:'formAdd', text: '<%=UnieapConstants.getMessage("comm.add")%>',
                    handler: function(){
                    	operType = 'AddNotification';
                    	dataForm.getForm().reset();
                    	dataForm.getForm().findField('subject').setReadOnly(false);
                    	dataForm.getForm().findField('subject').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('text').setReadOnly(false);
                    	dataForm.getForm().findField('text').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('hyperlink').setReadOnly(false);
                    	dataForm.getForm().findField('hyperlink').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('activeFlag').setReadOnly(false);
                    	dataForm.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('remark').setReadOnly(false);
                    	dataForm.getForm().findField('remark').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmit').enable();
                    }
                },{id:'formModify', text: '<%=UnieapConstants.getMessage("comm.modify")%>',
                    handler: function(){
                    	operType = 'ModifyNotification';
                    	dataForm.getForm().findField('subject').setReadOnly(false);
                    	dataForm.getForm().findField('subject').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('text').setReadOnly(false);
                    	dataForm.getForm().findField('text').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('hyperlink').setReadOnly(false);
                    	dataForm.getForm().findField('hyperlink').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('activeFlag').setReadOnly(false);
                    	dataForm.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataForm.getForm().findField('remark').setReadOnly(false);
                    	dataForm.getForm().findField('remark').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmit').enable();
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
                                 url: 'mcareController.do?method=resourceConfigureDeal',
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
            pageSize: 15,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mcareController.do?method=groupGrid&groupName=message_center_note',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	var gridForNote = Ext.create('Ext.grid.Panel', {
            store: gridstoreForNote,
            columns: [
                {text: "<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.subject")%>",  dataIndex: 'subject', sortable: false,width:120,
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
                {text: "<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.hyperlink")%>", dataIndex: 'hyperlink', sortable: true,width:80},
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
            	dataFormNote.getForm().findField('hyperlink').setReadOnly(true);
            	dataFormNote.getForm().findField('hyperlink').inputEl.addCls('readonly_field');
            	dataFormNote.getForm().findField('activeFlag').setReadOnly(true);
            	dataFormNote.getForm().findField('activeFlag').inputEl.addCls('readonly_field');
            	dataFormNote.getForm().findField('remark').setReadOnly(true);
            	dataFormNote.getForm().findField('remark').inputEl.addCls('readonly_field');
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
                    	{ xtype:'textfield',hidden: true, name:'name'},
                    	{ xtype:'textfield',hidden: true, name:'groupName'},
                    	{ xtype:'textfield',hidden: true, name:'resolution'},
                    	{ xtype:'textfield',hidden: true, name:'pageNum'},
                    	{ xtype:'textfield',hidden: true, name:'seq'},
                    	{ xtype:'textfield',hidden: true, name:'url'},
                    	{ xtype:'textfield',hidden: true, name:'resolution'},
                    	{ xtype:'textfield',labelWidth:80, width:450, name:'subject',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.resourceConfigure.display.subject")%>',maxLength:45,allowBlank:false},
                    	{ xtype:'textareafield',labelWidth:80, width:450, name:'text',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.resourceConfigure.display.text")%>',
                    		preventScrollbars:true,maxLength:1024,height:150,growMin:150,growMax:200,allowBlank:false},
                    	{ xtype:'textfield',labelWidth:80, width:450,name:'hyperlink', fieldLabel:'<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.hyperlink")%>', allowBlank:true},
                    	{ xtype:'combo', labelWidth:80, width:450,forceSelection: true,editable:false,allowBlank:false,
                            name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'Y',
                            store:Ext.create('Ext.data.Store', 
                            { fields : ['dicCode', 'dicName'],data:UnieapDicdata._activeFlag})
						},
                    	{ xtype:'textareafield',labelWidth:80, width:450, name:'remark',fieldLabel:'<%=UnieapConstants.getMessage("comm.remark")%>',maxLength:255,growMin:60,growMax:100,allowBlank:true}
                    ]
                }
            ],
            buttonAlign: 'center',
            buttons: 
            [
                {id:'formAddNote', text: '<%=UnieapConstants.getMessage("comm.add")%>',
                    handler: function(){
                    	operType = 'AddNote';
                    	dataFormNote.getForm().reset();
                    	dataFormNote.getForm().findField('subject').setReadOnly(false);
                    	dataFormNote.getForm().findField('subject').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('text').setReadOnly(false);
                    	dataFormNote.getForm().findField('text').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('hyperlink').setReadOnly(false);
                    	dataFormNote.getForm().findField('hyperlink').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormNote.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('remark').setReadOnly(false);
                    	dataFormNote.getForm().findField('remark').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmitNote').enable();
                    }
                },{id:'formModifyNote', text: '<%=UnieapConstants.getMessage("comm.modify")%>',
                    handler: function(){
                    	operType = 'ModifyNote';
                    	dataFormNote.getForm().findField('subject').setReadOnly(false);
                    	dataFormNote.getForm().findField('subject').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('text').setReadOnly(false);
                    	dataFormNote.getForm().findField('text').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('hyperlink').setReadOnly(false);
                    	dataFormNote.getForm().findField('hyperlink').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormNote.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataFormNote.getForm().findField('remark').setReadOnly(false);
                    	dataFormNote.getForm().findField('remark').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmitNote').enable();
                    }
                }, 
                {id:'formSubmitNote',text: '<%=UnieapConstants.getMessage("comm.submit")%>',
                    handler: function(){
                    	var form = dataFormNote.getForm();
                    	 if (form.isValid()){
                             form.submit({
                                 clientValidation: true,
                                 method: 'POST',
                                 params:{'operType':operType},
                                 waitMsg: '<%=UnieapConstants.getMessage("comm.processing")%>',
                                 url: 'mcareController.do?method=resourceConfigureDeal',
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
    	/***top round pictures*************************************************/
    	var gridstoreForRound = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mcareController.do?method=groupGrid&groupName=top_round_displays',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	var gridForRound = Ext.create('Ext.grid.Panel', {
            store: gridstoreForRound,
            columns: [
                {text: "<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.text")%>", dataIndex: 'text',sortable: true,width:80,
                	renderer: function (value, meta, record){
						var max = 150;
						meta.tdAttr = 'data-qtip="' + value + '"';
						return value.length < max ? value : value.substring(0, max - 3) + '...';
					}	
                },
                {text: "<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.hyperlink")%>", dataIndex: 'hyperlink', sortable: true,flex: true,
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
            	dataFormRound.getForm().findField('subject').setReadOnly(true);
            	dataFormRound.getForm().findField('subject').inputEl.addCls('readonly_field');
            	dataFormRound.getForm().findField('text').setReadOnly(true);
            	dataFormRound.getForm().findField('text').inputEl.addCls('readonly_field');
            	dataFormRound.getForm().findField('hyperlink').setReadOnly(true);
            	dataFormRound.getForm().findField('hyperlink').inputEl.addCls('readonly_field');
            	dataFormRound.getForm().findField('activeFlag').setReadOnly(true);
            	dataFormRound.getForm().findField('activeFlag').inputEl.addCls('readonly_field');
            	dataFormRound.getForm().findField('remark').setReadOnly(true);
            	dataFormRound.getForm().findField('remark').inputEl.addCls('readonly_field');
            	Ext.getCmp('formSubmitRound').disable();
            }
        });
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
                    	{ xtype:'textfield',hidden: true, name:'resolution'},
                    	{ xtype:'textfield',hidden: true, name:'pageNum'},
                    	{ xtype:'textfield',hidden: true, name:'seq'},
                    	{ xtype:'textfield',hidden: true, name:'url'},
                    	{ xtype:'textfield',hidden: true, name:'resolution'},
                    	{ xtype:'textfield',labelWidth:80, width:450, name:'subject',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.resourceConfigure.display.subject")%>',maxLength:45,allowBlank:false},
                    	{ xtype:'textareafield',labelWidth:80, width:450, name:'text',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.resourceConfigure.display.text")%>',
                    		preventScrollbars:true,maxLength:1024,height:150,growMin:150,growMax:200,allowBlank:false},
                    	{ xtype:'textfield',labelWidth:80, width:450,name:'hyperlink', fieldLabel:'<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.hyperlink")%>', allowBlank:true},
                    	{ xtype:'combo', labelWidth:80, width:450,forceSelection: true,editable:false,allowBlank:false,
                            name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'Y',
                            store:Ext.create('Ext.data.Store', 
                            { fields : ['dicCode', 'dicName'],data:UnieapDicdata._activeFlag})
						},
                    	{ xtype:'textareafield',labelWidth:80, width:450, name:'remark',fieldLabel:'<%=UnieapConstants.getMessage("comm.remark")%>',maxLength:255,growMin:60,growMax:100,allowBlank:true}
                    ]
                }
            ],
            buttonAlign: 'center',
            buttons: 
            [
                {id:'formAddRound', text: '<%=UnieapConstants.getMessage("comm.add")%>',
                    handler: function(){
                    	operType = 'AddRound';
                    	dataFormRound.getForm().reset();
                    	dataFormRound.getForm().findField('subject').setReadOnly(false);
                    	dataFormRound.getForm().findField('subject').inputEl.removeCls('readonly_field');
                    	dataFormRound.getForm().findField('text').setReadOnly(false);
                    	dataFormRound.getForm().findField('text').inputEl.removeCls('readonly_field');
                    	dataFormRound.getForm().findField('hyperlink').setReadOnly(false);
                    	dataFormRound.getForm().findField('hyperlink').inputEl.removeCls('readonly_field');
                    	dataFormRound.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormRound.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataFormRound.getForm().findField('remark').setReadOnly(false);
                    	dataFormRound.getForm().findField('remark').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmitRound').enable();
                    }
                },{id:'formModifyRound', text: '<%=UnieapConstants.getMessage("comm.modify")%>',
                    handler: function(){
                    	operType = 'ModifyRound';
                    	dataFormRound.getForm().findField('subject').setReadOnly(false);
                    	dataFormRound.getForm().findField('subject').inputEl.removeCls('readonly_field');
                    	dataFormRound.getForm().findField('text').setReadOnly(false);
                    	dataFormRound.getForm().findField('text').inputEl.removeCls('readonly_field');
                    	dataFormRound.getForm().findField('hyperlink').setReadOnly(false);
                    	dataFormRound.getForm().findField('hyperlink').inputEl.removeCls('readonly_field');
                    	dataFormRound.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormRound.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataFormRound.getForm().findField('remark').setReadOnly(false);
                    	dataFormRound.getForm().findField('remark').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmitRound').enable();
                    }
                }, 
                {id:'formSubmitRound',text: '<%=UnieapConstants.getMessage("comm.submit")%>',
                    handler: function(){
                    	var form = dataFormRound.getForm();
                    	 if (form.isValid()){
                             form.submit({
                                 clientValidation: true,
                                 method: 'POST',
                                 params:{'operType':operType},
                                 waitMsg: '<%=UnieapConstants.getMessage("comm.processing")%>',
                                 url: 'mcareController.do?method=resourceConfigureDeal',
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
                }
            ]
        });
    	
    	/***ad pictures*************************************************/
    	var gridstoreForAd = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'mcareController.do?method=namesGrid&names=app_ad_page,chongzhi_ad',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	var gridForAd = Ext.create('Ext.grid.Panel', {
            store: gridstoreForAd,
            columns: [
                {text: "<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.text")%>", dataIndex: 'text',sortable: true,width:80,
                	renderer: function (value, meta, record){
						var max = 150;
						meta.tdAttr = 'data-qtip="' + value + '"';
						return value.length < max ? value : value.substring(0, max - 3) + '...';
					}	
                },
                {text: "<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.hyperlink")%>", dataIndex: 'hyperlink', sortable: true,flex: true,
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
            	dataFormAd.getForm().findField('file').hide();
            	//dataFormAd.getForm().findField('file').setReadOnly(true);
            	//dataFormAd.getForm().findField('file').inputEl.addCls('readonly_field');
            	Ext.get('adImg').dom.src = dataFormAd.getForm().findField("url").getValue();
            	Ext.get('adImg').dom.width="182";
            	Ext.get('adImg').dom.height="320";
            	Ext.get('adA').dom.href = dataFormAd.getForm().findField("url").getValue();
            	Ext.getCmp('formSubmitAd').disable();
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
						{ xtype:'textfield',labelWidth:80, width:450,name:'hyperlink', fieldLabel:'<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.hyperlink")%>', allowBlank:true},
						{ xtype:'filefield',name: 'file',id:'uploadFileForm',labelWidth:80,width:450,fieldLabel:'<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.uploadfile")%>',msgTarget: 'side',
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
			    	                 var previewImg = document.getElementById('previewImg');
			    	                 resizePreviewPictureWindow();
			    	        	 }
							},
							allowBlank: false,anchor: '100%',buttonText:'<%=UnieapConstants.getMessage("comm.fileupload")%>'},
						{ xtype:'button',name: 'url',text:'<%=UnieapConstants.getMessage("mcare.resourceConfigure.display.preview")%>',
								handler:function(){
									var url = dataFormAd.getForm().findField('url').getValue();
									Ext.widget('window', 
				    	     	              { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'destroy', layout: 'fit', modal: true,
				    	        			 		items: {
				    	        			 			xtype:'panel',border:false,
				    	        			 			html:'<img id="previewImg" src='+url+'></img>'
				    	        			 		} 
				    	        			 });
								}
							},	
                    	{ xtype:'panel',border:false,html:'<a id="adA" href="#" onclick="js()" target="_blank"><img id="adImg" src="" width="220" height="350"></img></a>'}
                    ]
                }
            ],
            buttonAlign: 'center',
            buttons: 
            [
                {id:'formAddAd', text: '<%=UnieapConstants.getMessage("comm.add")%>',
                    handler: function(){
                    	operType = 'AddAd';
                    	dataFormAd.getForm().reset();
                    	dataFormAd.getForm().findField('file').show();
                    	Ext.get('adImg').dom.src = '';
                    	Ext.get('adImg').dom.width="182";
                    	Ext.get('adImg').dom.height="320";
                    	Ext.get('adA').dom.href = '';
                    	dataFormAd.getForm().findField('resolution').setReadOnly(false);
                    	dataFormAd.getForm().findField('resolution').inputEl.removeCls('readonly_field');
                    	dataFormAd.getForm().findField('hyperlink').setReadOnly(false);
                    	dataFormAd.getForm().findField('hyperlink').inputEl.removeCls('readonly_field');
                    	dataFormAd.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormAd.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	dataFormAd.getForm().findField('file').setReadOnly(false);
                    	dataFormAd.getForm().findField('file').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmitAd').enable();
                    }
                },{id:'formModifyAd', text: '<%=UnieapConstants.getMessage("comm.modify")%>',
                    handler: function(){
                    	operType = 'ModifyAd';
                    	//dataFormAd.getForm().findField('resolution').setReadOnly(false);
                    	//dataFormAd.getForm().findField('resolution').inputEl.removeCls('readonly_field');
                    	dataFormAd.getForm().findField('hyperlink').setReadOnly(false);
                    	dataFormAd.getForm().findField('hyperlink').inputEl.removeCls('readonly_field');
                    	dataFormAd.getForm().findField('activeFlag').setReadOnly(false);
                    	dataFormAd.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
                    	//dataFormAd.getForm().findField('file').setReadOnly(false);
                    	//dataFormAd.getForm().findField('file').inputEl.removeCls('readonly_field');
                    	Ext.getCmp('formSubmitAd').enable();
                    }
                }, 
                {id:'formSubmitAd',text: '<%=UnieapConstants.getMessage("comm.submit")%>',
                    handler: function(){
                    	var form = dataFormAd.getForm();
                        var previewImg = document.getElementById('previewImg');
                        var fileOriginalName = form.findField('uploadFileForm').getValue();
                        if(checkFileType(fileOriginalName)){
                        	if(previewImg.width!=720||previewImg.height!=300){
                        		Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',
                        			msg:'<%=UnieapConstants.getMessage("comm.fileupload.filesize","720,300")%>',
                         			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
                        	}else{
                        		if (form.isValid()){
                                    form.submit({
                                        clientValidation: true,
                                        method: 'POST',
                                        params:{'operType':operType},
                                        waitMsg: '<%=UnieapConstants.getMessage("comm.processing")%>',
                                        url: 'mcareController.do?method=resourceConfigureDeal',
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
                        	
                        }else{
                        	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',
                    			msg:'<%=UnieapConstants.getMessage("comm.fileupload.filetype",".jpg|.png")%>',
                     			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
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
					}
	        	]
	   });
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
