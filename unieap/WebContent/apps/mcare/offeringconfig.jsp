<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>User</title>
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
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'id','categoryType','categoryTypeDesc','categoreName','categoryDesc','pictureUrl','priceDesc','activeFlag','activeFlagDesc','createDate',
            	'createBy','modifyBy','modifyDate','remark','detailUrl','detailHyperlink','planUrl','planHyperlink','questionUrl','questionHyperlink',
            	'noteUrl','noteHyperlink'
            ],
            idProperty: 'id'
        });
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: <%=SYSConfig.getConfig().get("pageSize")%>,
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
       	   	   		{ text: "<%=UnieapConstants.getMessage("mcare.offering.display.categoryType")%>", dataIndex: 'categoryTypeDesc',sortable: false,width:120},
       	   	   		{ text: "<%=UnieapConstants.getMessage("mcare.offering.display.categoreName")%>", dataIndex: 'categoreName', sortable: true,width:120},
       	   	  		{ text: "<%=UnieapConstants.getMessage("mcare.offering.display.categoryDesc")%>", dataIndex: 'categoryDesc', sortable: false,width:120},
       	   	  		{ text: "<%=UnieapConstants.getMessage("mcare.offering.display.priceDesc")%>", dataIndex: 'priceDesc', sortable: false,width:120},
       	   	  		{text: "<%=UnieapConstants.getMessage("comm.hyperlink")%>", dataIndex: 'detailHyperlink', sortable: false,flex: true,width:200,
                    	renderer: function (value, meta, record){
    						var max = 150;
    						meta.tdAttr = 'data-qtip="' + value + '"';
    						return value.length < max ? value : value.substring(0, max - 3) + '...';
    					}	
                    
                    },
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
        var previewPictureWinCategory;
        function showForm(status,record){
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
 	                        	{ xtype:'textfield',labelWidth:120, width:350, name:'priceDesc',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.offering.display.priceDesc")%>',maxLength:255,allowBlank:false},
 	                        	{ xtype:'filefield',name: 'file',id:'uploadFileCategory',labelWidth:120,width:350,fieldLabel:'<%=UnieapConstants.getMessage("comm.uploadfile")%>',msgTarget: 'side',
	 	   							listeners : {
	 	   			    	        	 change : function(obj, v){
	 	   			    	        		 var picPath = obj.getValue();  
	 	   			    	                 var url = 'file:///' + picPath;  
	 	   			    	                 if(Ext.isIE){  
	 	   			    	                      var image = Ext.get('previewImgCategory');   
	 	   			    	                      image.src = Ext.BLANK_IMAGE_URL;  
	 	   			    	                      var fileDom = obj.fileInputEl.dom;
	 	   			    	                      fileDom.select();
	 	   			    	                      image.imgEl.dom.filters.item('DXImageTransform.Microsoft.AlphaImageLoader').src = document.selection.createRange().text;
	 	   			    	                      image.filters.item("DXImageTransform.Microsoft.AlphaImageLoader").src = url;   
	 	   			    	                 }else{ 
	 	   			    	                	  var file = obj.fileInputEl.dom.files[0];
	 	   			    	                	  url = URL.createObjectURL(file);
	 	   			    	                 } 
	 	   			    	                 if(previewPictureWinCategory!=null){
	 	   			    	                	 previewPictureWinCategory.destroy();
	 	   			    	                 }
	 	   			    	                 previewPictureWinCategory = Ext.widget('window', 
	 	   			    	     	              { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', layout: 'fit', modal: true,width: 400, height: 400,
	 	   			    	        			 		items: {
	 	   			    	        			 			xtype:'panel',border:false,
	 	   			    	        			 			html:'<img id="previewImgCategory" src='+url+' style="filter:progid:DXImageTransform.Microsoft.AlphaImageLoader(sizingMethod=scale);"></img>'
	 	   			    	        			 		} 
	 	   			    	        			 });
	 	   			    	                 previewPictureWinCategory.show();
	 	   			    	                 resizePreviewPictureWindowCategory();
	 	   			    	        	 }
	 	   							},
	 	   							allowBlank:true,anchor: '100%',buttonText:'<%=UnieapConstants.getMessage("comm.fileupload")%>'
	 	   						},
	 	   						{ xtype:'textfield',labelWidth:120, width:350,name:'detailHyperlink', fieldLabel:'<%=UnieapConstants.getMessage("comm.hyperlink")%>', allowBlank:true},
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
 	                            var previewImg = document.getElementById('previewImgCategory');
 	                            var fileOriginalName = form.findField('uploadFileCategory').getValue();
 	                            if(fileOriginalName != '' && fileOriginalName != null){
	 	                          	if(checkFileType(fileOriginalName)){
	 	                          		var pWidth =235 , pHeight = 215;
 	                             		if(previewImg.width< pWidth-20 ||previewImg.width >pWidth+20 || previewImg.height< pHeight-20 ||previewImg.height >pHeight+20){
 	                                 		Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',
 	                                 			msg:'<%=UnieapConstants.getMessage("comm.fileupload.filesize","235,215")%>',
 	                                  			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
 	                                 		return;
 	                                 	}else{
 	                                 		submitCategory(form);
 	                                 	}
	 	                             		
	 	                          	}else{
	 	                          		Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',
	 	                          			msg:'<%=UnieapConstants.getMessage("comm.fileupload.filetype",".jpg|.png")%>',
	 	                           			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	 	                          	}
 	                          	}else{
 	                          		submitCategory(form);
 	                          	}
 	                        }
 	                    }
                     ]
                 });
                 dataWin = Ext.widget('window', 
                 { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height: 360, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'roleName' });
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
        
        function submitCategory(form){
        	var id = form.findField('id').getValue();
        	var categoryType = form.findField('categoryType').getValue();
        	var categoreName = form.findField('categoreName').getValue();
        	//var seq = form.findField('seq').getValue();
        	var categoryDesc = form.findField('categoryDesc').getValue();
        	//var pictureUrl = form.findField('pictureUrl').getValue();
        	var priceDesc = form.findField('priceDesc').getValue();
        	var activeFlag = form.findField('activeFlag').getValue();
        	//var createDate = form.findField('createDate').getValue();
        	//var createBy = form.findField('createBy').getValue();
        	//var modifyDate = form.findField('modifyDate').getValue();
        	//var modifyBy = form.findField('modifyBy').getValue();
        	var remark = form.findField('remark').getValue();
        	//var detailUrl = form.findField('detailUrl').getValue();
        	var detailHyperlink = form.findField('detailHyperlink').getValue();
        	//var planUrl = form.findField('planUrl').getValue();
        	//var planHyperlink = form.findField('planHyperlink').getValue();
        	//var questionUrl = form.findField('questionUrl').getValue();
        	//var questionHyperlink = form.findField('questionHyperlink').getValue();
        	//var noteUrl = form.findField('noteUrl').getValue();
        	//var noteHyperlink = form.findField('noteHyperlink').getValue();
        	var parameters = Ext.JSON.encode({
        			'id':id,'categoryType':categoryType,
        			'categoreName':categoreName,'categoryDesc':categoryDesc,
        			'priceDesc':priceDesc,'activeFlag':activeFlag,
        			'remark':remark,'detailHyperlink':detailHyperlink
        		})
        	if (form.isValid()){
                 form.submit({
                     clientValidation: true,
                     method: 'POST',
                     params:{'operType1':operType},
                     waitMsg: '<%=UnieapConstants.getMessage("comm.processing")%>',
                     url: 'mcareController.do?method=offerCategoryDeal&operType='+operType+'&parameters='+encodeURL(parameters),
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
        
 		
 		function showResult(btn){
 	     	dataWin.hide();
 	     	gridstore.reload();
 	    }
    	
    	
        
        Ext.define('offeringModel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'id','offeringId','offeringCode','offeringName','seq','offeringType','offeringTypeDesc','offeringDesc','effectiveType','effectiveTypeDesc','feeAmount',
            	'categoryId','activeFlag','activeFlagDesc','createDate','createBy','modifyDate','modifyBy','remark'
            ],
            idProperty: 'id'
        });
    	var offeringGridStore = Ext.create('Ext.data.Store', {
            model: 'offeringModel',
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
    	var selectedRecordOffering;
    	if(UnieapButton.Offering_Modify!=null&&UnieapButton.Offering_Modify.abled==true){
    		offeringOperationItems.push({iconCls :'',tooltip:''});
    		offeringOperationItems.push({
	    		iconCls : 'edit',
	     	   	tooltip: '<%=UnieapConstants.getMessage("comm.modify")%>',
	           	handler:function(grid, rowIndex, colIndex){	
	           		selectedRecordOffering = grid.getStore().getAt(rowIndex);
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
    		   	   		{ text: "<%=UnieapConstants.getMessage("mcare.offering.display.offeringType")%>",dataIndex: 'offeringTypeDesc',width:120},
    		   	 		{ text: "<%=UnieapConstants.getMessage("mcare.offering.display.feeAmount")%>",dataIndex: 'feeAmount',width:120},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>",dataIndex: 'activeFlagDesc',sortable: false,width:120},
    		   	   	    { text: "<%=UnieapConstants.getMessage("comm.remark")%>", dataIndex: 'remark',width: 200, sortable: false}
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
       function showFormOffering(status,record){
    	   operTypeOffering = status;
    	   var rec = datagrid.getSelectionModel().getSelection();
 			if(rec.length==0){
       		Ext.MessageBox.show({
       	           title: '<%=UnieapConstants.getMessage("comm.tipInfo")%>',
       	           msg: '<%=UnieapConstants.getMessage("comm.select.oneRecord")%>',
       	           buttons: Ext.MessageBox.OK,
       	           icon: Ext.MessageBox.INFO
       	       });
	       	}else{
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
		                        	{ xtype:'textfield',hidden: true, name:'effectiveType'},
		                        	{ xtype:'textfield',labelWidth:120, width:350, name:'offeringId',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.offering.display.offeringId")%>',maxLength:45,allowBlank:false},
		                        	{ xtype:'textfield',labelWidth:120, width:350, name:'offeringName',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.offering.display.offeringName")%>',maxLength:45,allowBlank:false},
		                        	{ xtype:'combo', labelWidth:120, width:350,forceSelection: true,editable:false,allowBlank:false,
		                                name:'offeringType',fieldLabel:'<%=UnieapConstants.getMessage("mcare.offering.display.offeringType")%>',displayField:'dicName',valueField:'dicCode',
		                                store:Ext.create('Ext.data.Store', 
		                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._offeringType})
									},
		                        	{ xtype:'textfield',labelWidth:120, width:350, name:'feeAmount',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mcare.offering.display.feeAmount")%>',maxLength:45,allowBlank:false},
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
		                    {id:'formOfferingCancel', text: '<%=UnieapConstants.getMessage("comm.cancel")%>',
		                        handler: function(){
		                        	dataFormOffering.getForm().reset();
		                        	dataWinOffering.hide();
		                        }
		                    }, 
		                    {id:'formOfferingSubmit',text: '<%=UnieapConstants.getMessage("comm.submit")%>',
		                        handler: function(){
		                        	var form = dataFormOffering.getForm();
		                        	 if (form.isValid()){
		                                 form.submit({
		                                     clientValidation: true,
		                                     method: 'POST',
		                                     params:{'operType':operTypeOffering},
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
	                { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height: 300, layout: 'fit', modal: true, items:dataFormOffering,defaultFocus: 'offeringName' });
	            }
	            if(operTypeOffering=='Add'){
	            	dataFormOffering.getForm().reset();
	            	var rec = datagrid.getSelectionModel().getSelection();
	            	dataFormOffering.getForm().findField('categoryId').setValue(rec[0].data.id);
	            	if(rec[0].data.categoryType=='ct_me'){
	            		dataFormOffering.getForm().findField('offeringType').setReadOnly(true);
	            		dataFormOffering.getForm().findField('offeringType').inputEl.addCls('readonly_field');
		            	dataFormOffering.getForm().findField('offeringType').setValue('P');
	            	}else if(rec[0].data.categoryType=='ct_vas'){
	            		dataFormOffering.getForm().findField('offeringType').setReadOnly(true);
	            		dataFormOffering.getForm().findField('offeringType').inputEl.addCls('readonly_field');
		            	dataFormOffering.getForm().findField('offeringType').setValue('S');
	            	}
	            	dataWinOffering.show();
	            }else if(operTypeOffering=='Modify'){
	            	dataWinOffering.show();
	            	dataFormOffering.getForm().setValues(selectedRecordOffering.data);
	            	if(rec[0].data.categoryType=='ct_me'){
	            		dataFormOffering.getForm().findField('offeringType').setReadOnly(true);
	            		dataFormOffering.getForm().findField('offeringType').inputEl.addCls('readonly_field');
		            	dataFormOffering.getForm().findField('offeringType').setValue('P');
	            	}else if(rec[0].data.categoryType=='ct_vas'){
	            		dataFormOffering.getForm().findField('offeringType').setReadOnly(true);
	            		dataFormOffering.getForm().findField('offeringType').inputEl.addCls('readonly_field');
		            	dataFormOffering.getForm().findField('offeringType').setValue('S');
	            	}
	            }else{
	            	dataWinOffering.show();
	            }
	       	}
	    }
		function showResultOffering(btn){
	     	dataWinOffering.hide();
	     	offeringGridStore.reload();
	    }
		/***round task********************************************/
	      var taskCategory;
	   	  function resizePreviewPictureWindowCategory(){
	   		     if(taskCategory == null){
	   		    	taskCategory = {
	   			   			run : function(){
			   			   		 var previewImgCategory = document.getElementById('previewImgCategory');
			   		   	    	 if(previewImgCategory!=null){
			   			   	    	 if(previewImgCategory.height > 10){
			   			   	    		 if(previewImgCategory.width>300){
			   			   	    			previewPictureWinCategory.setWidth(400);
			   			   	    		 }else{
			   			   	    			previewPictureWinCategory.setWidth(previewImgCategory.width);
			   			   	    		 }
			   			   	    		 if(previewImgCategory.height>300){
			   			   	    			previewPictureWinCategory.setHeight(400);
			   			   	    		 }else{
			   			  	    		 	previewPictureWinCategory.setHeight(previewImgCategory.height);
			   			   	    		 }
			   			  	    		 previewPictureWinCategory.center();
			   			  	    		 Ext.TaskManager.stop(taskCategory);   
			   			   	    	 }
			   		   	    	 }
	   			   			},
	   			   			interval : 500
	   			   		}
	   		   		Ext.TaskManager.start(taskCategory);
	   		     }else{
	   		    	Ext.TaskManager.start(taskCategory);
	   		     }
	   	    }
      
	});
    </script>
</head>
<body>
    <div id="datagrid"></div>
    <div id="offeringgrid"></div>
</body>
</html>
