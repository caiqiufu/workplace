<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Product</title>
    <script type="text/javascript">
    Ext.onReady(function(){
    	Ext.tip.QuickTipManager.init();
    	Ext.define('productdatamodel', {
	          extend: 'Ext.data.Model',
	          fields:
	          [
	          	'customerId','productId','productCode','productName','brand','brandDesc','model','modelDesc','color','colorDesc','memery','memeryDesc','version','versionDesc','warranty','warrantyDesc','maintainance','maintainanceDesc','icloudStatus','icloudStatusDesc','productProfile','productProfileDesc','inWater','inWaterDesc','assesAmount',
	          	'saleAmount','status','statusDesc','remark','createDate','modifyDate','modifyBy','createBy'
	          ],
	          idProperty: 'productId'
	      });
	  	  var productgridstore = Ext.create('Ext.data.Store', {
	          model: 'productdatamodel',
	          pageSize: 15,
	          remoteSort: true,
	          proxy: 
	          { type: 'ajax', url: 'ReuseController.do?method=productGrid',
	              reader: 
	              {root: 'rows', totalProperty: 'totalCount'},
	              simpleSortMode: true
	          },
	          sorters: 
	          [
	          	{ property: 'productName', direction: 'ASC'}
	          ]
	      });
	  	var productOperationItems = [];
	  	var productSelectedRecord;
	  	var productQueryPara;
		   	if(UnieapButton.Product_Modify!=null&&UnieapButton.Product_Modify.abled==true){
		   		productOperationItems.push({iconCls :'',tooltip:''});
	 			productOperationItems.push({
		      	   icon   : '/Unieap/unieap/js/common/images/edit.png',
		      	   tooltip: '修改',id:'Product_Modify',
		           handler:function(grid, rowIndex, colIndex)
		           {	
		        	    productSelectedRecord = grid.getStore().getAt(rowIndex);
		         		productShowForm('Modify',productSelectedRecord);
		           }
		         });
	    }
	    var statusModifyWin = null;
	    var statusModifyForm = null;
	    var selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single',listeners:{
	  		select:function(model,record,index)
	  			{
	  				trackinggridstore.load({params:{productId:record.get('productId')}});
	  			}
  			}
  		});
  	    var productdatagrid = Ext.create('Ext.grid.Panel', 
  	        {el : 'datagrid',columnLines:true,autoScroll:true,autoExpandColumn:'action',
		    	listeners:
				        {afterRender:function(thisForm, options){
				        	if(UnieapButton.Product_Modify_Status!=null&&UnieapButton.Product_Modify_Status.abled== true){Ext.getCmp('Product_Modify_Status').show();};
				        	this.keyNav = Ext.create('Ext.util.KeyNav', this.el,{
			        			enter: function(){
			        				query();
			        			}
			        		})
			            }
			        },
  		   	 	selModel:selModel,
  	   	   		store : productgridstore,
  		   	   	columns:
  		   	   	[
  		   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn',width:50, text: "操作",items:productOperationItems},
  		   	   		{ text: "产品ID", dataIndex: 'productId',width:50},
  		   	   		{ text: "编码", dataIndex: 'productCode',sortable:true,width:120},
  		   	   		{ text: "产品名称", dataIndex: 'productName',sortable:true,width:120},
  		   	   		{ text: "品牌",dataIndex: 'brandDesc',sortable: false,width:60},
  		   	   		{ text: "型号",dataIndex: 'modelDesc',sortable: false,width:60},
  		   	  		{ text: "颜色",dataIndex: 'colorDesc',sortable: false,width:60},
  		   			{ text: "内存",dataIndex: 'memeryDesc',sortable: false,width:60},
  		   			{ text: "网络型号",dataIndex: 'versionDesc',sortable: false,width:60},
  		   	   		{ text: "保修期",dataIndex: 'warrantyDesc',sortable: false,width:60},
  		   	   		{ text: "维修情况",dataIndex: 'maintainanceDesc',sortable: false,width:60},
  		   			{ text: "iCloud账号",dataIndex: 'icloudStatusDesc',sortable: false,width:80},
  		   			{ text: "产品品相",dataIndex: 'productProfileDesc',sortable: false,width:60},
  		   			{ text: "是否进水",dataIndex: 'inWaterDesc',sortable: false,width:60},
  		   			{ text: "评估价格",dataIndex: 'assesAmount',sortable: false,width:60},
  		   			{ text: "销售价格",dataIndex: 'saleAmount',sortable: false,width:60},
  		   			{ text: "当前状态",dataIndex: 'statusDesc',sortable: false,width:60},
  		   	 		{ text: "创建日期",dataIndex: 'createDate',width:80},
  		   	   		{ text: "备注", dataIndex: 'remark',sortable: false}
  		   	   	],
    		   	tbar:[
 		    		  {pressed :true,icon:'/Unieap/unieap/js/common/images/edit.png',
	                		tooltip:'修改产品状态',text:'修改产品状态',xtype:'button',id:'Product_Modify_Status',hidden:true,
	 		            	handler : function(){
	 		            		var rec = productdatagrid.getSelectionModel().getSelection();
	 		            		if(rec.length==0){
	 		            			Ext.MessageBox.show({
	 		            	           title: '<%=UnieapConstants.getMessage("comm.tipInfo")%>',
	 		            	           msg: '<%=UnieapConstants.getMessage("comm.select.oneRecord")%>',
	 		            	           buttons: Ext.MessageBox.OK,
	 		            	           icon: Ext.MessageBox.INFO
	 		            	       });
	 		            		}else{
	 		            			statusModifyShowForm(productdatagrid.getStore().getAt(rec[0].index));
	 		            		}
	 		            	}
	 		    		 },'-',
	    		   	     {xtype: 'fieldcontainer',layout: 'hbox', id:'queryFields',
						    items: 
						    [
						        { xtype:'textfield',labelWidth:80, width:200,labelAlign: 'right',name:'productCode',fieldLabel: '产品编码'}
						    ]
						 },'-',
						 { xtype:'button',text:'查询',iconAlign:'right',pressed :true,icon:'/Unieap/unieap/js/common/images/search-trigger.png',
		    	                handler: function (){
		    	                	query();
		    	                }
		                 },'-',
		                 { xtype:'button',text:'重置',iconAlign: 'right',pressed :true,icon :'/Unieap/unieap/js/common/images/clear-trigger.gif',
	    	                handler: function ()
	    	                {
	    	                	var queryFields = Ext.getCmp('queryFields').items.items;
	    	                	queryFields[0].setValue('');
	    	                }
		                 }
    		   	],
  	    	   	bbar:new Ext.PagingToolbar(
  	    	   	{ store :productgridstore,displayInfo: true})
  	        	
  	    });
  	   productdatagrid.render();
  	   productgridstore.loadPage(1);
       productdatagrid.store.on("load",function(){ 
    	    if(productdatagrid.getStore().getCount()==1){
    	    	productdatagrid.getSelectionModel().selectRange(1,1); 
    	    }
       });
       /*****query function begin****************************************************/
       function query(){
    	  var queryFields = Ext.getCmp('queryFields').items.items;
          var productCode=queryFields[0].getValue();
          queryPara = 
          	{
          		productCode:productCode
           	};
          productgridstore.load({params:queryPara});
       }
       /*****query function end****************************************************/
       var modelStore = new Ext.data.Store({
            proxy: {
                type: 'ajax',
                url: 'MdmController.do?method=getCommDicList&groupId=5065',
                reader: {type: 'json'}
            },
            fields: ['dicCode','dicName'],
            autoLoad:false
        });
	    modelStore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,{'whereby':' parent_code = \''+ Ext.getCmp('combo_brand').getValue()+'\''});
        }); 
	    var colorStore = new Ext.data.Store({
            proxy: {
                type: 'ajax',
                url: 'MdmController.do?method=getCommDicList&groupId=5064',
                reader: {type: 'json'}
            },
            fields: ['dicCode','dicName'],
            autoLoad:false
        });
	    colorStore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,{'whereby':' parent_code = \''+ Ext.getCmp('combo_model').getValue()+'\''});
        }); 
	    var memeryStore = new Ext.data.Store({
            proxy: {
                type: 'ajax',
                url: 'MdmController.do?method=getCommDicList&groupId=5467',
                reader: {type: 'json'}
            },
            fields: ['dicCode','dicName'],
            autoLoad:false
        });
	    memeryStore.on('beforeload', function (store, options){
            Ext.apply(store.proxy.extraParams,{'whereby':' parent_code = \''+ Ext.getCmp('combo_model').getValue()+'\''});
        });
	   
	   var productDataWin = null;
       var productDataForm = null;
       var productOperType = '';
       function productShowForm(status,selectedRecord){
    	   productOperType = status;
            if (productDataWin==null){
            	productDataForm = Ext.widget('form',
            	{
                    defaults:{labelAlign: 'left', anchor: '100%'},
                    bodyPadding:5,
                    items:
                    [
                    	{xtype:'fieldset', title:'产品信息',
	                        items:
	                        [
	                        	{ xtype:'textfield',hidden: true, name:'customerId'},
	                        	{ xtype:'textfield',hidden: true, name:'productId'},
	                        	{ xtype:'textfield',hidden: true, name:'createDate'},
	                        	{ xtype:'textfield',hidden: true, name:'modifyDate'},
	                        	{ xtype:'textfield',hidden: true, name:'createBy'},
	                        	{ xtype:'textfield',hidden: true, name:'modifyBy'},
	                        	{ xtype:'textfield', labelWidth:80, width:350, name:'productCode',fieldLabel:'编码<font color=red>*</font>',allowBlank:false},
	                        	{ xtype:'textfield', labelWidth:80, width:350, name:'productName',fieldLabel:'产品名称',allowBlank:true},
	                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, editable:false,allowBlank:false,
	                        		id:'combo_brand',name:'brand',fieldLabel:'品牌<font color=red>*</font>',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._5060Opt}),
	                                listeners:{
	                            	     select:function(combo, record,index){
		                            	    	var comboIcloudStatus=Ext.getCmp('combo_icloudStatus');
		                            	    	if(combo.value=='iphone'){
		                            	    		comboIcloudStatus.show();
		                            	    	}else{
		                            	    		comboIcloudStatus.hide();
		                            	    	}
			                        			var comboModel=Ext.getCmp('combo_model');
			                        			comboModel.clearValue(); 
			                        			modelStore.load({  
	                                            url: "MdmController.do?method=getCommDicList&groupId=5065",  
	                                            params:
	                                            {  
	                                        	   whereby:' parent_code = \''+combo.value+'\''
	                                            }  
	                                       });
	                            	  }
                              	 	}
								},
								{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false,
									id:'combo_model',name:'model',fieldLabel:'型号<font color=red>*</font>',displayField:'dicName',valueField:'dicCode',
	                                store:modelStore,
                                	listeners:{
                            	     	select:function(combo, record,index){
	 		                        			var comboColor=Ext.getCmp('combo_color');
	 		                        			comboColor.clearValue(); 
	 		                        			colorStore.load({  
		                                           url: "MdmController.do?method=getCommDicList&groupId=5064",  
		                                           params:
		                                           {  
		                                        	    whereby:' parent_code = \''+combo.value+'\''
		                                           }  
		                                        });
	 		                        			var comboMemery=Ext.getCmp('combo_memery');
	 		                        			comboMemery.clearValue(); 
	 		                        			memeryStore.load({  
		                                           url: "MdmController.do?method=getCommDicList&groupId=5467",  
		                                           params:
		                                           {  
		                                        	    whereby:' parent_code = \''+combo.value+'\''
		                                           }  
		                                        });
		                            	  }
		                               }
								},
								{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false, mode:"local",
	                                id:'combo_color',name:'color',fieldLabel:'颜色<font color=red>*</font>',displayField:'dicName',valueField:'dicCode',triggerAction: 'all',
	                                store:colorStore
								},
								{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false,
									id:'combo_memery',name:'memery',fieldLabel:'内存<font color=red>*</font>',displayField:'dicName',valueField:'dicCode',
	                                store:memeryStore
								},
								{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false,
									id:'combo_version',name:'version',fieldLabel:'网络型号<font color=red>*</font>',displayField:'dicName',valueField:'dicCode',
									store:Ext.create('Ext.data.Store', 
	 		                        { fields : ['dicCode', 'dicName'],data:UnieapDicdata._5473Opt})

								},
								{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false,
									id:'combo_warranty',name:'warranty',fieldLabel:'保修期<font color=red>*</font>',displayField:'dicName',valueField:'dicCode',
									store:Ext.create('Ext.data.Store', 
	 		 		                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._5474Opt})
								},
								{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false,
									id:'combo_maintainance',name:'maintainance',fieldLabel:'维修情况<font color=red>*</font>',displayField:'dicName',valueField:'dicCode',
									store:Ext.create('Ext.data.Store', 
	 		 		 		        { fields : ['dicCode', 'dicName'],data:UnieapDicdata._5475Opt})
								},
								{ xtype:'combo', hidden: true,labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:true,
	                                id:'combo_icloudStatus',name:'icloudStatus',fieldLabel:'iCloud是否可以解锁<font color=red>*</font>',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._1001Opt})
								},
								{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false,
	                                name:'productProfile',fieldLabel:'产品品相<font color=red>*</font>',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._5477Opt})
								},
								{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false,
	                                name:'inWater',fieldLabel:'是否进水<font color=red>*</font>',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._1001Opt})
								},
	                        	{ xtype:'numberfield', labelWidth:80, width:350, name:'assesAmount',fieldLabel:'评估价格',allowBlank:true},
	                        	{ xtype:'numberfield', labelWidth:80, width:350, name:'saleAmount',fieldLabel:'销售价格',allowBlank:true},
	                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false,
	                                name:'status',fieldLabel:'当前状态<font color=red>*</font>',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._5478Opt})
								},
	                        	{ xtype:'textareafield',labelWidth:80, width:350,name:'remark',fieldLabel:'备注',growMin:60,growMax:100,allowBlank:true}
	                        ]
	                    }
                    ],
                    buttons: 
                    [
	                    { text: '取消',
	                        handler: function() 
	                        {
	                        	productDataForm.getForm().reset();
	                        	productDataWin.hide();
	                        }
	                    }, 
	                    { text: '提交',
	                        handler: function() {
	                        	var form = productDataForm.getForm();
	                        	 if (form.isValid()){
	                                 form.submit({
	                                     clientValidation: true,
	                                     method: 'POST',
	                                     params:{'operType':productOperType},
	                                     url: 'ReuseController.do?method=productDeal',
	                                     success: function(form, action) {
	                                    	var result = Ext.JSON.decode(action.response.responseText);
						                    if(result.isSuccess == 'failed')
						                    {
						                    	Ext.MessageBox.show({title: 'Status',msg:result.message,
		                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
						                    }else{
 		                                    	Ext.MessageBox.show({title: 'Status',msg:'保存成功',fn: productShowResult,
	 		                               			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
						                    }
	                                     },
	                                     failure: function(form, action) {
	                                    	 Ext.MessageBox.show({title: 'Status',msg:action.response.responseText,
	                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                                     }
	                                 });
	                        	 }
	                        }
	                    }
                    ]
                });
                productDataWin = Ext.widget('window', 
                { title: '数据', closeAction: 'hide', width: 400, height: 580, layout: 'fit', modal: true, items: productDataForm,defaultFocus: 'productName' });
	           }
	           if(productOperType=='Modify'){
		            
		            productDataWin.show();
		            modelStore.load({  
	                  url: "MdmController.do?method=getCommDicList&groupId=5065",  
	                  params:
	                  {  
	               	   whereby:' parent_code = \''+productSelectedRecord.get("brand")+'\''
	                  }  
	                });
		            colorStore.load({  
	                  url: "MdmController.do?method=getCommDicList&groupId=5064",  
	                  params:
	                  {  
	               	    whereby:' parent_code = \''+productSelectedRecord.get("model")+'\''
	                  }  
	               });
	     		   memeryStore.load({  
	                   url: "MdmController.do?method=getCommDicList&groupId=5467",  
	                   params:
	                   {  
	                	    whereby:' parent_code = \''+productSelectedRecord.get("model")+'\''
	                   }  
	                });
	     		  productDataForm.getForm().setValues(productSelectedRecord.data);
	     		  var comboIcloudStatus=Ext.getCmp('combo_icloudStatus');
	            	var comboBrand=Ext.getCmp('combo_brand');
	       	    	if(comboBrand.value=='iphone'){
	       	    		comboIcloudStatus.show();
	       	    	}else{
	       	    		comboIcloudStatus.hide();
	       	    	}
		            	
	            }else{
	            	productDataWin.show();
	            }
	    }
	    function statusModifyShowForm(selectedProductRecordForStatus){
            if (statusModifyWin==null){
            	statusModifyForm = Ext.widget('form',
            	{
                    defaults:{labelAlign: 'left', labelWidth: 90, anchor: '100%'},
                    bodyPadding:5,
                    items:
                    [
                    	{xtype:'fieldset', title:'<%=UnieapConstants.getMessage("comm.data")%>',
	                        items:
	                        [
	                        	{ xtype:'textfield',hidden: true, name:'productId'},
	                        	{ xtype:'textfield',labelWidth:80, width:350,name:'productCode',fieldLabel:'编码'},
	                        	{ xtype:'textfield',labelWidth:80, width:350,name:'productName',fieldLabel:'产品名称'},
	                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false,
	                                name:'status',fieldLabel:'当前状态',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._5478})
								},
								{ xtype:'textfield',labelWidth:80, width:350,name:'referNo',fieldLabel:'单号'},
								{ xtype:'textareafield',labelWidth:80, width:350,name:'remark',fieldLabel:'备注',growMin:60,growMax:100,allowBlank:true}
	                        ]
	                    }
                    ],
                    buttons: 
                    [
	                    {text: '<%=UnieapConstants.getMessage("comm.cancel")%>',
	                        handler: function() 
	                        {
	                        	statusModifyForm.getForm().reset();
	                        	statusModifyWin.hide();
	                        }
	                    }, 
	                    {text: '<%=UnieapConstants.getMessage("comm.submit")%>',
	                        handler: function() {
	                        	var form = statusModifyForm.getForm();
	                        	var referNo = statusModifyForm.getForm().findField('referNo').getValue();
	                        	 if (form.isValid()){
	                                 form.submit({
	                                     clientValidation: true,
	                                     method: 'POST',
	                                     params:{'operType':'Modify_Status','referNo':referNo},
	                                     url: 'ReuseController.do?method=productDeal',
	                                     success: function(form, action) {
	                                    	var result = Ext.JSON.decode(action.response.responseText);
						                    if(result.isSuccess == 'failed')
						                    {
						                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
		                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
						                    }else{
 		                                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:'<%=UnieapConstants.getMessage("comm.success.save")%>',fn: statusModifyShowResult,
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
            	statusModifyWin = Ext.widget('window',
                {id:'productWin', title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height: 260, layout: 'fit', modal: true, items: statusModifyForm,defaultFocus: 'status' });
            }
        	statusModifyForm.getForm().reset();
            statusModifyWin.show();
            statusModifyForm.getForm().setValues(selectedProductRecordForStatus.data);
            statusModifyForm.getForm().findField('productCode').setReadOnly(true);
            statusModifyForm.getForm().findField('productName').setReadOnly(true);
            statusModifyForm.getForm().findField('remark').setValue('');
    	}
	    function statusModifyShowResult(btn)
	    {
		    	statusModifyWin.hide();
		    	productgridstore.reload();
		    	var rec = productdatagrid.getSelectionModel().getSelection();
		    	if(rec.length==0){
		    		trackinggridstore.load({params:{productId:'-1'}});
		    	}else{
			    	trackinggridstore.load({params:{productId:rec[0].get('productId')}});
		    	}
	    } 
   		function productRemoveDatas(btn)
    	{
     		if(btn=='yes'){
	        	var productId= productSelectedRecord.get("productId");
	        	Ext.Ajax.request({
	                url: 'ReuseController.do?method=productDeal',
	                params:{'operType':"Delete","productId":productId},
	                success: function(response, opts) 
	                {
	                	Ext.MessageBox.show({title: 'Status',msg:'删除成功',
             			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
	                	productgridstore.reload();
	                },
	                failure: function(response, opts) 
	                {
	                	Ext.MessageBox.show({title: 'Status',msg:response.responseText,
             			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                }
	             });
     	}
    	}
	    function productShowResult(btn)
	    {
	     	productDataWin.hide();
	     	productgridstore.reload();
	     	if(rec.length==0){
	    		trackinggridstore.load({params:{productId:-1}});
	    	}else{
		    	trackinggridstore.load({params:{productId:rec[0].get('productId')}});
	    	}
	    }
	    /*********product tracking begin*************************************************/
	    Ext.define('trackingdatamodel', {
	          extend: 'Ext.data.Model',
	          fields:
	          [
	          	'trackingId','productId','status','statusDesc','referNo','updatedBy','remark','createDate','modifyDate','modifyBy','createBy'
	          ],
	          idProperty: 'trackingId'
	    });
	  	var trackinggridstore = Ext.create('Ext.data.Store', {
	          model: 'trackingdatamodel',
	          pageSize: 15,
	          remoteSort: true,
	          proxy: 
	          { type: 'ajax', url: 'ReuseController.do?method=producTrackingtGrid',
	              reader: 
	              {root: 'rows', totalProperty: 'totalCount'},
	              simpleSortMode: true
	          },
	          sorters: 
	          [
	          	{ property: 'createDate', direction: 'ASC'}
	          ]
	    });
	  	trackinggridstore.on('beforeload', function (store, options) {
	  		var rec = productdatagrid.getSelectionModel().getSelection();
  			if(rec.length==0){
	    		Ext.apply(store.proxy.extraParams,{productId:-1});
	    	}else{
		    	Ext.apply(store.proxy.extraParams,{productId:rec[0].get("productId")});
	    	}
	    });
	  	var trackingSelectedRecord;
	   	var trackingModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single'});
  		var trackingdatagrid = Ext.create('Ext.grid.Panel', 
  	        {layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
  		   	 	selModel:trackingModel,
  	   	   		store : trackinggridstore,
  		   	   	columns:
  		   	   	[
  		   	   		{ text: "ID", dataIndex: 'trackingId',width:50},
  		   	   		{ text: "状态", dataIndex: 'statusDesc',sortable:true,width:120},
  		   	   		{ text: "快递单号", dataIndex: 'referNo',sortable:true,width:120},
	  		   	   	{ text: "创建日期",dataIndex: 'createDate',width:150},
		   	   		{ text: "创建人",dataIndex: 'createBy',sortable: false},
		   	   		{ text: "备注", dataIndex: 'remark',sortable: false,width:150}
  		   	   	],
  	    	   	bbar:new Ext.PagingToolbar(
  	    	   	{ store :trackinggridstore,displayInfo: true})
  	        });
	    /*******************Display tab beign****************/
  	    var tabPanel = Ext.create('Ext.tab.Panel',{
  	     	renderTo:'displayTab',activeTab:0,layout: 'fit',
  	        bodyStyle: 'padding:0px',
  	        items:[
  	               {xtype: 'panel',title:'状态变更历史',items : [trackingdatagrid],closable: false},
  	               {xtype: 'panel',title:'其他属性',items : [],closable: false}
  	        ]
  	    });
  	   /*******************Display tab end****************/
	  });
    </script>
</head>
<body>
    <div id="datagrid"></div>
    <div id="displayTab"></div>
</body>
</html>
