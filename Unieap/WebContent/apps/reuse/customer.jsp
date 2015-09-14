<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>User</title>
    <script type="text/javascript">
    Ext.onReady(function(){
    	Ext.QuickTips.init();
    	Ext.tip.QuickTipManager.init();
    	/*******************customer begin**********************************/
    	Ext.define('datamodel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'customerId','customerCode','customerName','sex','sexDesc','phone','email','wechat','remark','createDate','modifyDate','modifyBy','createBy'
            ],
            idProperty: 'customerId'
        });
    	var gridstore = Ext.create('Ext.data.Store', {
            model: 'datamodel',
            pageSize: 15,
            remoteSort: true,
            proxy: 
            { type: 'ajax', url: 'ReuseController.do?method=customerGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: 
            [
            	{ property: 'customerCode', direction: 'ASC'}
            ]
        });
    	var operationItems = [];
    	var selectedRecord;
    	var queryPara;
	   	if(UnieapButton.Customer_Modify!=null&&UnieapButton.Customer_Modify.abled==true){
	   		operationItems.push({iconCls :'',tooltip:''});
	   		operationItems.push({
	      	   icon   : '/Unieap/unieap/js/common/images/edit.png',
	      	   tooltip: '修改',id:'Customer_Modify',
	            handler:function(grid, rowIndex, colIndex)
	            {	
	          	   	selectedRecord = grid.getStore().getAt(rowIndex);
	         		showForm('Modify',selectedRecord);
	            }
	         });
	      }
    	var selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single',listeners:{
		  		select:function(model,record,index)
		  			{
		  				productgridstore.load({params:{customerId:record.get('customerId')}});
		  				addressgridstore.load({params:{customerId:record.get('customerId')}});
		  			}
	  			}
	  		});
    	var datagrid = Ext.create('Ext.grid.Panel', 
    	        {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
		    		listeners:
			        {afterRender:function(thisForm, options){
				        	if(UnieapButton.Customer_Add!=null&&UnieapButton.Customer_Add.abled== true){
				        		Ext.getCmp('Customer_Add').show();
				        	}
			        		this.keyNav = Ext.create('Ext.util.KeyNav', this.el,{
			        			enter: function(){
			        				query();
			        			}
			        		})
			            }
			        },
    		   	 	selModel:selModel,title: '用户列表',
    	   	   		store : gridstore,
    		   	   	columns:
    		   	   	[
    		   	   		{ sortable: false, xtype: 'actioncolumn', text: "操作",width:50,items:operationItems},
    		   	   		{ text: "用户账号", dataIndex: 'customerCode'},
    		   	   		{ text: "用户名称", dataIndex: 'customerName'},
    		   	   		{ text: "Email", dataIndex: 'email',sortable: false,width:120},
    		   	   		{ text: "手机",dataIndex: 'phone',sortable: false,width:120},
    		   	   		{ text: "微信号",dataIndex: 'wechat',sortable: false,width:120},
    		   	   		{ text: "性别",dataIndex: 'sexDesc',sortable: false,width:60},
    		   	 		{ text: "创建日期",dataIndex: 'createDate',width:120},
    		   	   		{ text: "创建人",dataIndex: 'createBy',sortable: false},
    		   	   		{ text: "修改时间",dataIndex: 'modifyDate',width:120,sortable: false},
    		   	   		{ text: "修改人",dataIndex: 'modifyBy',sortable: false},
    		   	   		{ text: "备注", dataIndex: 'remark',sortable: false}
    		   	   	],
	    		   	tbar:[
	    		   	      {pressed :true,icon:'/Unieap/unieap/js/common/images/add.png',
	                		tooltip:'增加',text:'增加',xtype:'button',id:'Customer_Add',hidden:true,
	 		            	handler : function(){showForm('Add',null);}
	 		    		 },'-',
	    		   	     {xtype: 'fieldcontainer',layout: 'hbox', id:'queryFields',
						    items: 
						    [
						        { xtype:'textfield',labelWidth:80, width:200,labelAlign: 'right',name: 'customerCode',fieldLabel: '用户账号'},
						        { xtype:'textfield',labelWidth:80, width:200,labelAlign: 'right',name:'phone',fieldLabel: '手机号'},
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
	    	                	queryFields[1].setValue('');
	    	                	queryFields[2].setValue('');
	    	                }
		                }
	    		   	],
    	    	   	bbar:new Ext.PagingToolbar(
    	    	   	{ store : gridstore,displayInfo: true})
    	        	
    	        });
    	   datagrid.render();
 	       gridstore.loadPage(1);
 	       datagrid.store.on("load",function(){ 
 	    	    if(datagrid.getStore().getCount()==1){
 	    	    	datagrid.getSelectionModel().selectRange(1,1); 
 	    	    }
 	       }); 
 	       function query(){
 	    	  var queryFields = Ext.getCmp('queryFields').items.items;
          	  var customerCode=queryFields[0].getValue(); 
              var phone=queryFields[1].getValue(); 
              var productCode=queryFields[2].getValue();
              queryPara = 
              	{
              		customerCode:customerCode,
              		phone:phone,
              		productCode:productCode
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
 	                    defaults:{labelAlign: 'left', anchor: '100%'},
 	                    bodyPadding:5,
 	                    items:
 	                    [
 	                    	{xtype:'fieldset', title:'用户注册信息',
 		                        items:
 		                        [
 		                        	{ xtype:'textfield',hidden: true, name:'customerId'},
 		                        	{ xtype:'textfield',hidden: true, name:'createDate'},
 		                        	{ xtype:'textfield',hidden: true, name:'modifyDate'},
 		                        	{ xtype:'textfield',hidden: true, name:'createBy'},
 		                        	{ xtype:'textfield',hidden: true, name:'modifyBy'},
 		                        	{ xtype:'textfield', labelWidth:80, width:350, name:'customerCode',fieldLabel:'用户账号<font color=red>*</font>',allowBlank:false},
 		                        	{ xtype:'textfield', labelWidth:80, width:350,name:'customerName',fieldLabel:'用户名称<font color=red>*</font>',allowBlank:false},
 		                        	{ xtype:'textfield', labelWidth:80, width:350,name:'email',fieldLabel:'Email',vtype: 'email',allowBlank:true},
 		                        	{ xtype:'textfield', labelWidth:80, width:350,name:'phone',fieldLabel:'手机<font color=red>*</font>',allowBlank:false},
 		                        	{ xtype:'textfield', labelWidth:80, width:350,name:'wechat',fieldLabel:'微信号',allowBlank:true},
 		                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true,editable:false,allowBlank:false,
		                                name:'sex',fieldLabel:'性别',displayField:'dicName',valueField:'dicCode',
		                                store:Ext.create('Ext.data.Store', 
		                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._5745})
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
 		                        	dataForm.getForm().reset();
 		                        	dataWin.hide();
 		                        }
 		                    }, 
 		                    { text: '提交',
 		                        handler: function() {
 		                        	var form = dataForm.getForm();
 		                        	 if (form.isValid()){
 		                                 form.submit({
 		                                     clientValidation: true,
 		                                     waitMsg: 'Processing...',
 		                                     method: 'POST',
 		                                     params:{'operType':operType},
 		                                     url: 'ReuseController.do?method=customerDeal',
 		                                     success: function(form, action) {
 		                                    	var result = Ext.JSON.decode(action.response.responseText);
							                    if(result.isSuccess == 'failed')
							                    {
							                    	Ext.MessageBox.show({title: 'Status',msg:result.message,
			                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
							                    }else{
	 		                                    	Ext.MessageBox.show({title: 'Status',msg:'保存成功',fn: showResult,
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
 	                dataWin = Ext.widget('window', 
 	                { title: '数据', closeAction: 'hide', width: 400, height: 350, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'customerCode' });
 	            }
 	            if(operType=='Add')
 	            {
 	            	dataForm.getForm().reset();
 	            	dataForm.getForm().setValues({enable:'1',locked:'0',expired:'0'});
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
	        	var customerId= productSelectedRecord.get("customerId");
	        	Ext.Ajax.request({
	                url: 'ReuseController.do?method=customerDeal',
	                params:{'operType':"Delete","customerId":customerId},
	                success: function(response, opts) 
	                {
	                	Ext.MessageBox.show({title: 'Status',msg:'删除成功',
                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
	                	gridstore.reload();
	                },
	                failure: function(response, opts) 
	                {
	                	Ext.MessageBox.show({title: 'Status',msg:response.responseText,
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
 	   /*******************customer end**********************************/
 	   
 	  /**********************product begin************************************/
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
	  	productgridstore.on('beforeload', function (store, options) {
	  		var rec = datagrid.getSelectionModel().getSelection();
  			if(rec.length==0){
	    		Ext.apply(store.proxy.extraParams,{customerId:-1});
	    	}else{
		    	Ext.apply(store.proxy.extraParams,{customerId:rec[0].get("customerId")});
	    	}
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
	   	var productModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single'});
    	var productdatagrid = Ext.create('Ext.grid.Panel', 
    	        {layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
		    		listeners:
			        {afterRender:function(thisForm, options){
				        	if(UnieapButton.Product_Add!=null&&UnieapButton.Product_Add.abled== true){Ext.getCmp('Product_Add').show();};
				        	if(UnieapButton.Product_Modify_Status!=null&&UnieapButton.Product_Modify_Status.abled== true){Ext.getCmp('Product_Modify_Status').show();};
			            }
			        },
    		   	 	selModel:productModel,
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
	    		   	      {pressed :true,icon:'/Unieap/unieap/js/common/images/add.png',
	                		tooltip:'增加',text:'增加',xtype:'button',id:'Product_Add',hidden:true,
	 		            	handler : function(){productShowForm('Add',null);}
	 		    		  },'-',
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
		 		    		 }
	    		   	],
    	    	   	bbar:new Ext.PagingToolbar(
    	    	   	{ store :productgridstore,displayInfo: true})
    	        	
    	        });
    		
    	   /*********product dic list*************************************************/
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
		                            	    	if(comboIcloudStatus.getValue()=='iphone'){
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
  		                        	var customer = datagrid.getSelectionModel().getSelection();
  		      	  	    			if(customer.length==0){
  		      	  	          			Ext.MessageBox.show({
  		      	  	          	           title: '提示',
  		      	  	          	           msg: '请选择用户信息',
  		      	  	          	           buttons: Ext.MessageBox.OK,
  		      	  	          	           icon: Ext.MessageBox.INFO
  		      	  	          	       });
  		      	  	          			return;
  		      	  	          		}
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
  	            if(productOperType=='Add')
  	            {
  	            	var customer = datagrid.getSelectionModel().getSelection();
	  	    		if(customer.length==0){
	  	          			Ext.MessageBox.show({
	  	          	           title: '提示',
	  	          	           msg: '请选择用户信息',
	  	          	           buttons: Ext.MessageBox.OK,
	  	          	           icon: Ext.MessageBox.INFO
	  	          	       });
	  	          	}else{
	  	            	productDataForm.getForm().reset();
	  	            	productDataWin.show();
	  	            	productDataForm.getForm().findField('customerId').setValue(customer[0].get("customerId"));
	  	          	}
  	            }else if(productOperType=='Modify')
  	            {
  	            	productDataForm.getForm().setValues(productSelectedRecord.data);
  	            	productDataWin.show();
  	            	var comboIcloudStatus=Ext.getCmp('combo_icloudStatus');
  	            	var comboBrand=Ext.getCmp('combo_brand');
        	    	if(comboBrand.value=='iphone'){
        	    		comboIcloudStatus.show();
        	    	}else{
        	    		comboIcloudStatus.hide();
        	    	}
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
  	            	
  	            }else
  	            {
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
 									}
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
		                        	 if (form.isValid()){
		                                 form.submit({
		                                     clientValidation: true,
		                                     method: 'POST',
		                                     params:{'operType':'Modify_Status'},
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
	                { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height: 200, layout: 'fit', modal: true, items: statusModifyForm,defaultFocus: 'status' });
	            }
            	statusModifyForm.getForm().reset();
	            statusModifyWin.show();
	            statusModifyForm.getForm().setValues(selectedProductRecordForStatus.data);
	            statusModifyForm.getForm().findField('productCode').setReadOnly(true);
	            statusModifyForm.getForm().findField('productName').setReadOnly(true);
	    }
  	    function statusModifyShowResult(btn)
	    {
  	    	statusModifyWin.hide();
  	    	productgridstore.reload();
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
         }
  	   /*************************product end*********************************************/
  	   
  	  /**********************address begin************************************/
 	  	Ext.define('addressdatamodel', {
	          extend: 'Ext.data.Model',
	          fields:
	          [
	          	'addressId','customerId','type','typeDesc','defaultFlag','defaultFlagDesc','province','provinceDesc','city','cityDesc','district','districtDesc','street','streetDesc','postcode','contact','phone','address1','address2','createDate','modifyDate','createBy','modifyBy'
	          ],
	          idProperty: 'addressId'
	      });
	  	var addressgridstore = Ext.create('Ext.data.Store', {
	          model: 'addressdatamodel',
	          pageSize: 15,
	          remoteSort: true,
	          proxy: 
	          { type: 'ajax', url: 'ReuseController.do?method=addressGrid',
	              reader: 
	              {root: 'rows', totalProperty: 'totalCount'},
	              simpleSortMode: true
	          },
	          sorters: 
	          [
	          	{ property: 'addressId', direction: 'ASC'}
	          ]
	      });
	  	addressgridstore.on('beforeload', function (store, options) {
	  		var rec = datagrid.getSelectionModel().getSelection();
  			if(rec.length==0){
	    		Ext.apply(store.proxy.extraParams,{customerId:-1});
	    	}else{
		    	Ext.apply(store.proxy.extraParams,{customerId:rec[0].get("customerId")});
	    	}
	    });
	  	var addressOperationItems = [];
    	var addressSelectedRecord;
    	var addressQueryPara;
	   	if(UnieapButton.Address_Modify!=null&&UnieapButton.Address_Modify.abled==true){
	   		addressOperationItems.push({iconCls :'',tooltip:''});
	   		addressOperationItems.push({
	      	   icon   : '/Unieap/unieap/js/common/images/edit.png',
	      	   tooltip: '修改',id:'Address_Modify',
	           handler:function(grid, rowIndex, colIndex)
	           {	
	        	    addressSelectedRecord = grid.getStore().getAt(rowIndex);
	        	    addressShowForm('Modify',addressSelectedRecord);
	           }
	         });
        }
	   	if(UnieapButton.Address_Delete!=null&&UnieapButton.Address_Delete.abled==true){
	   		addressOperationItems.push({iconCls :'',tooltip:''});
	   		addressOperationItems.push({
	     	   icon   : '/Unieap/unieap/js/common/images/delete.png',
	           tooltip: '删除',
	           handler:function(grid, rowIndex, colIndex)
	           {	
	        	   addressSelectedRecord = grid.getStore().getAt(rowIndex);
		           Ext.MessageBox.confirm('Confirm', '请确认是否需要删除该数据?', addressRemoveDatas);
	           }
	        });
    	}
	   	if(UnieapButton.Address_DefaultFlag!=null&&UnieapButton.Address_DefaultFlag.abled==true){
	   		addressOperationItems.push({iconCls :'',tooltip:''});
	   		addressOperationItems.push({
	     	   icon   : '/Unieap/unieap/js/common/images/drop-yes.gif',
	           tooltip: '默认地址',
	           handler:function(grid, rowIndex, colIndex)
	           {	
	        	   addressSelectedRecord = grid.getStore().getAt(rowIndex);
		           Ext.MessageBox.confirm('Confirm', '请确认是否需要设置为默认地址?', addressDefaultFlag);
	           }
	        });
    	}
	   	var addressModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single'});
    	var addressdatagrid = Ext.create('Ext.grid.Panel', 
    	        {layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
		    		listeners:
			        {afterRender:function(thisForm, options){
				        	if(UnieapButton.Address_Add!=null&&UnieapButton.Address_Add.abled== true){Ext.getCmp('Address_Add').show();}
			            }
			        },
    		   	 	selModel:addressModel,
    	   	   		store : addressgridstore,
    		   	   	columns:
    		   	   	[
    		   	   		{ menuDisabled: true,sortable: false, xtype: 'actioncolumn',width:120, text: "操作",items:addressOperationItems},
    		   	   		{ text: "地址ID", dataIndex: 'addressId',width:80},
    		   	   		{ text: "地址类型", dataIndex: 'typeDesc',sortable:true,width:80},
    		   	   		{ text: "默认地址",dataIndex: 'defaultFlagDesc',sortable: false,width:80},
    		   	   		{ text: "省",dataIndex: 'provinceDesc',sortable: false,width:120},
    		   	  		{ text: "市",dataIndex: 'cityDesc',sortable: false,width:120},
    		   			{ text: "区",dataIndex: 'districtDesc',sortable: false,width:120},
    		   			{ text: "街道",dataIndex: 'streetDesc',sortable: false,width:120},
    		   	   		{ text: "联系人",dataIndex: 'contact',sortable: false,width:120},
    		   			{ text: "联系电话",dataIndex: 'phone',sortable: false,width:120},
    		   	   		{ text: "邮编",dataIndex: 'postcode',sortable: false,width:50},
    		   			{ text: "详细地址",dataIndex: 'address1',sortable: false,width:60}
    		   	   	],
	    		   	tbar:[
	    		   	      {pressed :true,icon:'/Unieap/unieap/js/common/images/add.png',
	                		tooltip:'增加',text:'增加',xtype:'button',id:'Address_Add',hidden:true,
	 		            	handler : function(){addressShowForm('Add',null);}
	 		    		 }
	    		   	]
    	        	
    	        });
    	        
    	   /****provice dic data****************************************/ 
    	   var proviceStore = new Ext.data.Store({
	            proxy: {
	                type: 'ajax',
	                url: 'MdmController.do?method=getAddressDicData&level=1',
	                reader: {type: 'json'}
	            },
	            fields: ['dicCode','dicName','level']
	        });
    	   proviceStore.load();  
   	       
    	   var cityStore = new Ext.data.Store({
	            proxy: {
	                type: 'ajax',
	                url: 'MdmController.do?method=getAddressDicData&level=2',
	                reader: {type: 'json'}
	            },
	            fields: ['dicCode','dicName','level'],
	            autoLoad:false
	        });
    	   cityStore.on('beforeload', function (store, options){
	            Ext.apply(store.proxy.extraParams,{'code':Ext.getCmp('combo_province').getValue()});
	        }); 
    	   
    	   var districtStore = new Ext.data.Store({
	            proxy: {
	                type: 'ajax',
	                url: 'MdmController.do?method=getAddressDicData&level=3',
	                reader: {type: 'json'}
	            },
	            fields: ['dicCode','dicName','level'],
	            autoLoad:false
	        });
    	   districtStore.on('beforeload', function (store, options){
	            Ext.apply(store.proxy.extraParams,{'code':Ext.getCmp('combo_city').getValue()});
	        }); 
    	   var streetStore = new Ext.data.Store({
	            proxy: {
	                type: 'ajax',
	                url: 'MdmController.do?method=getAddressDicData&level=4',
	                reader: {type: 'json'}
	            },
	            fields: ['dicCode','dicName','level'],
	            autoLoad:false
	        });
   	  	    streetStore.on('beforeload', function (store, options){
	            Ext.apply(store.proxy.extraParams,{'code':Ext.getCmp('combo_district').getValue()});
	        }); 
    	   var addressDataWin = null;
  	       var addressDataForm = null;
  	       var addressOperType = '';
  	       function addressShowForm(status,selectedRecord){
  	    	    addressOperType = status;
  	            if (addressDataWin==null){
  	            	addressDataForm = Ext.widget('form',
  	            	{
  	                    defaults:{labelAlign: 'left', anchor: '100%'},
  	                    bodyPadding:5,
  	                    items:
  	                    [
  	                    	{xtype:'fieldset', title:'产品信息',
  		                        items:
  		                        [
  		                        	{ xtype:'textfield',hidden: true, name:'addressId'},
  		                        	{ xtype:'textfield',hidden: true, name:'customerId'},
  		                        	{ xtype:'textfield',hidden: true, name:'createDate'},
  		                        	{ xtype:'textfield',hidden: true, name:'modifyDate'},
  		                        	{ xtype:'textfield',hidden: true, name:'createBy'},
  		                        	{ xtype:'textfield',hidden: true, name:'modifyBy'},
  		                        	{ xtype:'textfield',hidden: true, name:'defaultFlag'},
  		                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true,editable:false,allowBlank:false, mode:"local",
 		                                name:'type',fieldLabel:'地址类型',displayField:'dicName',valueField:'dicCode',triggerAction: 'all',value:'receive_address',
 		                                store:Ext.create('Ext.data.Store', 
 		                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._5209})
 									},
  		                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false,mode:"local",
 		                                id:'combo_province',name:'province',fieldLabel:'省',displayField:'dicName',valueField:'dicCode',
 		                                store:proviceStore,
 		                                listeners:{
 		                            	     select:function(combo, record,index){
 	 		                        			var comboCity=Ext.getCmp('combo_city');
 	 		                        			comboCity.clearValue(); 
 		                        				cityStore.load({  
 		                                           url: "MdmController.do?method=getAddressDicData&level=2",  
 		                                           params:
 		                                           {  
 		                                        	 	code:combo.value  
 		                                           }  
 		                                       });
 		                            	  }
 		                               }
 									},
 									{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false, mode:"local",
 		                                id:'combo_city',name:'city',fieldLabel:'市',displayField:'dicName',valueField:'dicCode',triggerAction: 'all',
 		                                store:cityStore,
 		                                listeners:{
		                            	     select:function(combo, record,index){
	 	 		                        			var comboDistrict=Ext.getCmp('combo_district');
	 	 		                        			comboDistrict.clearValue(); 
	 	 		                        			districtStore.load({  
	 		                                           url: "MdmController.do?method=getAddressDicData&level=3",  
	 		                                           params:
	 		                                           {  
	 		                                        	 	code:combo.value  
	 		                                           }  
	 		                                       });
	 		                            	  }
	 		                               }
 		                               
 									},
 									{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false, mode:"local",
 		                                id:'combo_district',name:'district',fieldLabel:'区',displayField:'dicName',valueField:'dicCode',triggerAction: 'all',
 		                                store:districtStore,
 		                                listeners:{
		                            	     select:function(combo, record,index){
	 	 		                        			var comboStreet=Ext.getCmp('combo_street');
	 	 		                        			comboStreet.clearValue(); 
	 	 		                        			streetStore.load({  
	 		                                           url: "MdmController.do?method=getAddressDicData&level=4",  
	 		                                           params:
	 		                                           {  
	 		                                        	 	code:combo.value  
	 		                                           }  
	 		                                       });
	 		                            	  }
	 		                               }
 									},
 									{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, emptyText: '...',editable:false,allowBlank:false, mode:"local",
 		                                id:'combo_street',name:'street',fieldLabel:'街道',displayField:'dicName',valueField:'dicCode',triggerAction: 'all',
 		                                store:streetStore
 									},
  		                        	{ xtype:'textareafield',labelWidth:80, width:350,name:'address1',fieldLabel:'详细地址',growMin:60,growMax:100,allowBlank:true},
  		                        	{ xtype:'numberfield', labelWidth:80, width:350, name:'phone',fieldLabel:'联系电话',allowBlank:false},
  		                        	{ xtype:'textfield', labelWidth:80, width:350, name:'contact',fieldLabel:'联系人',allowBlank:false},
  		                        	{ xtype:'numberfield', labelWidth:80, width:350, name:'postcode',fieldLabel:'邮编',allowBlank:true}
  		                        ]
  		                    }
  	                    ],
  	                    buttons: 
  	                    [
  		                    { text: '取消',
  		                        handler: function() 
  		                        {
  		                        	addressDataForm.getForm().reset();
  		                        	addressDataWin.hide();
  		                        }
  		                    }, 
  		                    { text: '提交',
  		                        handler: function() {
  		                        	var customer = datagrid.getSelectionModel().getSelection();
  		      	  	    			if(customer.length==0){
  		      	  	          			Ext.MessageBox.show({
  		      	  	          	           title: '提示',
  		      	  	          	           msg: '请选择用户信息',
  		      	  	          	           buttons: Ext.MessageBox.OK,
  		      	  	          	           icon: Ext.MessageBox.INFO
  		      	  	          	       });
  		      	  	          			return;
  		      	  	          		}
  		                        	var form = addressDataForm.getForm();
  		                        	 if (form.isValid()){
  		                                 form.submit({
  		                                     clientValidation: true,
  		                                     waitMsg: 'Processing...',
  		                                     method: 'POST',
  		                                     params:{'operType':addressOperType},
  		                                     url: 'ReuseController.do?method=addressDeal',
  		                                     success: function(form, action) {
  		                                    	var result = Ext.JSON.decode(action.response.responseText);
 							                    if(result.isSuccess == 'failed')
 							                    {
 							                    	Ext.MessageBox.show({title: 'Status',msg:result.message,
 			                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
 							                    }else{
 	 		                                    	Ext.MessageBox.show({title: 'Status',msg:'保存成功',fn: addressShowResult,
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
  	            	addressDataWin = Ext.widget('window', 
  	                { title: '数据', closeAction: 'hide', width: 400, height:400, layout: 'fit', modal: true, items: addressDataForm,defaultFocus: 'province' });
  	            }
  	            if(addressOperType=='Add')
  	            {
  	            	var customer = datagrid.getSelectionModel().getSelection();
	  	    		if(customer.length==0){
	  	          			Ext.MessageBox.show({
	  	          	           title: '提示',
	  	          	           msg: '请选择用户信息',
	  	          	           buttons: Ext.MessageBox.OK,
	  	          	           icon: Ext.MessageBox.INFO
	  	          	       });
	  	          	}else{
	  	          		addressDataForm.getForm().reset();
	  	          		addressDataWin.show();
	  	        		addressDataForm.getForm().findField('customerId').setValue(customer[0].get("customerId"));
	  	        		addressDataForm.getForm().findField('defaultFlag').setValue('0');
	  	        		
	  	          	}
  	            }else if(addressOperType=='Modify')
  	            {
  	            	addressDataWin.show();
  	            	cityStore.load({  
                         url: "MdmController.do?method=getAddressDicData&level=2",  
                         params:
                         {  
                      	 	code:addressSelectedRecord.get("province")  
                         }  
                     });
  	            	
  	            	districtStore.load({  
                         url: "MdmController.do?method=getAddressDicData&level=3",  
                         params:
                         {  
                      	 	code:addressSelectedRecord.get("city") 
                         }  
                     });
  	            	streetStore.load({  
                         url: "MdmController.do?method=getAddressDicData&level=4",  
                         params:
                         {  
                      	 	code:addressSelectedRecord.get("district")  
                         }  
                     });
  	            	addressDataForm.getForm().setValues(addressSelectedRecord.data);
  	            }else
  	            {
  	            	addressDataWin.show();
  	            }
  	    }
       	function addressRemoveDatas(btn)
         {
         	if(btn=='yes'){
 	        	var addressId= addressSelectedRecord.get("addressId");
 	        	Ext.Ajax.request({
 	                url: 'ReuseController.do?method=addressDeal',
 	                params:{'operType':"Delete","addressId":addressId},
 	                success: function(response, opts) 
 	                {
 	                	Ext.MessageBox.show({title: 'Status',msg:'删除成功',
                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
 	                	addressgridstore.reload();
 	                },
 	                failure: function(response, opts) 
 	                {
 	                	Ext.MessageBox.show({title: 'Status',msg:response.responseText,
                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
 	                }
 	             });
         	}
         }
       	function addressDefaultFlag(btn)
        {
        	if(btn=='yes'){
	        	var addressId= addressSelectedRecord.get("addressId");
	        	var customerId= addressSelectedRecord.get("customerId");
	        	Ext.Ajax.request({
	                url: 'ReuseController.do?method=addressDeal',
	                params:{'operType':"UpdateDefaultFlag","addressId":addressId,'customerId':customerId},
	                success: function(response, opts) 
	                {
	                	Ext.MessageBox.show({title: 'Status',msg:'修改成功',
                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.INFO});
	                	addressgridstore.reload();
	                },
	                failure: function(response, opts) 
	                {
	                	Ext.MessageBox.show({title: 'Status',msg:response.responseText,
                			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	                }
	             });
        	}
        }
  	    function addressShowResult(btn)
         {
  	    	addressDataWin.hide();
  	    	addressgridstore.reload();
         }
  	   /*************************address end*********************************************/
  	   
  	   
  	  /*******************Display tab beign****************/
  	   var tabPanel = Ext.create('Ext.tab.Panel',{
  	     	renderTo:'displayTab',activeTab:0,layout: 'fit',
  	        bodyStyle: 'padding:0px',
  	        items:[
  	               {xtype: 'panel',title:'产品信息',items : [productdatagrid],closable: false},
  	               {xtype: 'panel',title:'地址信息',items : [addressdatagrid],closable: false}
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
