<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>apk upgrade</title>
    <script type="text/javascript">
    /***file uplaod begin*****************************************************/
    var allImgExt=".apk|"//全部图片格式类型
    var allowExt=".apk|" //允许上传的文件类型 ŀ为无限制 每个扩展名后边要加一个"|" 小写字母表示
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
    	 
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'id','activeFlag','activeFlagDesc','version','changeDesc','createDate','effectiveDate','remark','fileId','tenantId'
            ],
            idProperty: 'id'
        });
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'easyMobileController.do?method=apkUpgradeGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'id', direction: 'DESC'}]
        });
    	
    	var operationItems = [];
    	var selectedRecord;
    	if(UnieapButton.APK_Modify!=null&&UnieapButton.APK_Modify.abled==true){
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
	        {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
		   	 	selModel:selModel,title: '<%=UnieapConstants.getMessage("comm.title.list")%>',
		 		listeners:{
		 		   		afterRender:function(thisForm, options){
			        	if(UnieapButton.APK_Add!=null&&UnieapButton.APK_Add.abled== true){
			        		Ext.getCmp('APK_Add').show();
			        	}
		            }
		        },
	   	   		store : gridstore,
		   	   	columns:
		   	   	[
					{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "<%=UnieapConstants.getMessage("comm.operation")%>",width:80,items:operationItems},
		   	   		{ text: "<%=UnieapConstants.getMessage("mcare.apkupgrade.display.version")%>", dataIndex: 'version',sortable: false,width:100},
       	   			{ text: "<%=UnieapConstants.getMessage("mcare.apkupgrade.display.changeDesc")%>", dataIndex: 'changeDesc',flex: true, sortable: false,width:500,
		       	   	  	renderer: function (value, meta, record){
							var max = 150;
							meta.tdAttr = 'data-qtip="' + value + '"';
							return value.length < max ? value : value.substring(0, max - 3) + '...';
						}	
       	   			},
       	   			{ text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>",dataIndex: 'activeFlagDesc',sortable: false,width:60},
       	   			{ text: "<%=UnieapConstants.getMessage("comm.createDate")%>",width: 150, dataIndex: 'createDate',sortable: false},
       	   		    { text: "<%=UnieapConstants.getMessage("comm.effectiveDate")%>",width: 150, dataIndex: 'effectiveDate',sortable: false},
       	   			{ text: "<%=UnieapConstants.getMessage("comm.remark")%>", dataIndex: 'remark',flex: true,width: 200, sortable: false,
		       	   	   	renderer: function (value, meta, record){
							var max = 150;
							meta.tdAttr = 'data-qtip="' + value + '"';
							return value.length < max ? value : value.substring(0, max - 3) + '...';
						}	
       	   	   		}
		   	   	],
	 		   	tbar:[{ pressed :true,iconCls:'add',
		             		tooltip:'<%=UnieapConstants.getMessage("comm.add")%>',text:'<%=UnieapConstants.getMessage("comm.add")%>',xtype:'button',id:'APK_Add',hidden:true,
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
	                        	{ xtype:'textfield',hidden: true, name:'fileId'},
	                        	{ xtype:'textfield',hidden: true, name:'tenantId'},
	                        	{ xtype:'textfield',labelWidth:80, width:350, name:'version',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.apkupgrade.display.version")%>',maxLength:45,allowBlank:false},
	                        	{ xtype:'textareafield',labelWidth:80, width:350, name:'changeDesc',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.apkupgrade.display.changeDesc")%>',height:150,
	                        		maxLength:512,growMin:60,growMax:100,allowBlank:false},
	                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true,editable:false,allowBlank:false,
	                                name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'Y',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._activeFlag})
								},
								{name: 'effectiveDate',labelWidth:80, width:300, fieldLabel: '<%=UnieapConstants.getMessage("comm.effectiveDate")%>',format: 'Y-m-d h:i:s', xtype: 'datefield',allowBlank:false,
		                    		value:Ext.util.Format.date(new Date(),"Y-m-d h:i:s")
		                   		},
								{ xtype:'filefield',name: 'file',id:'uploadFileFormApk',labelWidth:80,width:420,fieldLabel:'<%=UnieapConstants.getMessage("mcare.apkupgrade.display.apkfile")%>',msgTarget: 'side',
									allowBlank:true,anchor: '100%',buttonText:'<%=UnieapConstants.getMessage("comm.fileupload")%>'
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
	                        	var fileOriginalName = form.findField('uploadFileFormApk').getValue();
	                        	if(operType=='Add'){
	                        		if(fileOriginalName==''|| fileOriginalName==null){
	                        			Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',
	                            			msg:'<%=UnieapConstants.getMessage("comm.fileupload.filetype",".apk")%>',
	                             			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                        			return;
	                        		}
	                        	}
	                        	if(fileOriginalName != '' && fileOriginalName != null){
	                            	if(checkFileType(fileOriginalName)){
	                                   	submitApk(form);
	                            	}else{
	                            		Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',
	                            			msg:'<%=UnieapConstants.getMessage("comm.fileupload.filetype",".apk")%>',
	                             			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                            	}
	                            }else{
	                            	submitApk(form);
	                            }
	                        }
	                    }
                    ]
                });
                dataWin = Ext.widget('window', 
                { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height: 450, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'version' });
            }
            if(operType=='Add'){
            	dataForm.getForm().reset();
            	dataWin.show();
            	dataForm.getForm().findField('file').show();
            }else if(operType=='Modify'){
            	dataWin.show();
            	dataForm.getForm().setValues(selectedRecord.data);
            	dataForm.getForm().findField('effectiveDate').setValue(Ext.util.Format.date(selectedRecord.data.effectiveDate,"Y-m-d h:i:s"));
            	dataForm.getForm().findField('file').hide();
            }else{
            	dataWin.show();
            }
	   }
	       
       function submitApk(form){
    		var id = form.findField('id').getValue();
        	var version = form.findField('version').getValue();
        	var changeDesc = form.findField('changeDesc').getValue();
        	var activeFlag = form.findField('activeFlag').getValue();
        	var effectiveDate = dateTimeFormat(form.findField('effectiveDate').getValue());
        	var remark = form.findField('remark').getValue();
        	var parameters = Ext.JSON.encode({'id':id,'version':version,'changeDesc':changeDesc,'activeFlag':activeFlag,'effectiveDate':effectiveDate,'remark':remark})
        	if (form.isValid()){
                form.submit({
                    clientValidation: true,
                    method: 'POST',
                    params:{'operType':operType},
                    waitMsg: '<%=UnieapConstants.getMessage("comm.processing")%>',
                    url: 'easyMobileController.do?method=apkUpgradeDeal&operType='+operType+'&parameters='+parameters,
                    success: function(form, action) {
                   	var result = Ext.JSON.decode(action.response.responseText);
	                    if(result.isSuccess == 'failed'){
	                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
                    		buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                    }else{
	                    	gridstore.reload();
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
