<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Dic</title>
    <script type="text/javascript">
Ext.onReady(function(){
     Ext.tip.QuickTipManager.init();
    	
     Ext.define('DataTree', {
            extend: 'Ext.data.Model',
            idProperty: 'id',
            fields: [
				{name: 'id',     type: 'string'},
				{name: 'text',     type: 'string'},
                {name: 'parentId',  type: 'string'},
                {name: 'extendAttri'}
            ]
        });
   	 var dicTreestore = Ext.create('Ext.data.TreeStore', {
            model: 'DataTree',
            autoLoad : false,
            proxy: {
            	type: 'ajax',
            	reader: 'json',
                url: 'MdmController.do?method=getDicTreeData'
            },
            nodeParam : "id",
            root:{  
                id:1,  
                text:'Root',  
                leaf:false,  
                expanded:false  
            },
            folderSort: true
        });
   	 
	   	var dicTreePanel = Ext.create('Ext.tree.Panel', {
	   		title: '<%=UnieapConstants.getMessage("mdm.dic.data.title.list")%>',
	   		layout:'fit',
	        collapsible: false,
	        useArrows: true,
	        rootVisible: true,
	        store: dicTreestore,
	        //multiSelect: false,
	        listeners:{
	        	itemcontextmenu: function(view, record, node, index, e){
	        		var rightMenu = new Ext.menu.Menu({
	                    items: [{
	                    	iconCls : 'add',
	                        text: '<%=UnieapConstants.getMessage("comm.add")%>',
	                        handler: Ext.bind(function(btn){
	                        	rightMenu.hide();
	                        	addNode(record);
	                        },this)
	                    }, {
	                    	iconCls : 'edit',
	                        text: '<%=UnieapConstants.getMessage("comm.modify")%>',
	                        handler: Ext.bind(function(){
	                        	rightMenu.hide();
	                        	editNode(record);
	                        },this)
	                    }]

	        		});
	        		e.stopEvent();
	        		rightMenu.showAt(e.getXY());
	                return false;
	        	},
	        	itemclick:function(view, record, node, index, e, eOpts){
	        		roleGridStore.load({params:{dicCode:record.data.extendAttri.dicCode}});
	        	}
	        }
	   	})
	   	
	   	
	   	Ext.define('roleModel', {
            extend: 'Ext.data.Model',
            fields:
            [
            	'roleId','roleCode','roleName','createDate','modifyDate','modifyBy','createBy','remark','activeFlag','activeFlagDesc'
            ],
            idProperty: 'roleId'
        });
    	var roleGridStore = Ext.create('Ext.data.Store', {
            model: 'roleModel',
            pageSize: 15,
            remoteSort: true,
            proxy:{ type: 'ajax', url: 'MdmController.do?method=dicRoleGrid',
                reader: 
                {root: 'rows', totalProperty: 'totalCount'},
                simpleSortMode: true
            },
            sorters: [{ property: 'roleCode', direction: 'ASC'}]
        });
    	roleGridStore.on('beforeload', function (store, options){
	   			var rec = roleDatagrid.getSelectionModel().getSelection();
	   	        Ext.apply(store.proxy.extraParams,{dicCode:'-1'});
	   	     });
    	var selModel = Ext.create('Ext.selection.CheckboxModel',{mode:'single'});
    	var roleDatagrid = Ext.create('Ext.grid.Panel', 
    	        {layout: 'fit',columnLines: true,autoScroll:true,autoExpandColumn:'action',
    		   	 	selModel:selModel,title: '<%=UnieapConstants.getMessage("mdm.role.title.list")%>',
    	   	   		store : roleGridStore,
    		   	   	columns:
    		   	   	[
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleId")%>",dataIndex: 'roleId'},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleCode")%>", dataIndex: 'roleCode'},
    		   	   		{ text: "<%=UnieapConstants.getMessage("mdm.role.display.roleName")%>", dataIndex: 'roleName'},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.activeFlag")%>",dataIndex: 'activeFlagDesc',sortable: false},
    		   	   		{ text: "<%=UnieapConstants.getMessage("comm.remark")%>", dataIndex: 'remark',sortable: false}
    		   	   	]
    	        });
    	var viewport = Ext.create('Ext.Viewport', {
    		el : 'datalayou',
            layout: 'column',
            items: [{
                columnWidth: 1/2,
                padding: '5 0 5 5',
                items:[dicTreePanel]
            },{
                columnWidth: 1/2,
                padding: '5 0 5 5',
                items:[roleDatagrid]
            }]
        });
	  function addNode(record){
		   showForm('Add',record);
	  }
	  function editNode(record){
		   showForm('Modify',record);
	  }
	  var dataWin = null;
      var dataForm = null;
      var operType = '';
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
                        	{ xtype:'textfield',hidden: true, name:'dicId'},
                        	{ xtype:'textfield',hidden: true, name:'parentId'},
                        	{ xtype:'textfield',hidden: true, name:'createDate'},
                        	{ xtype:'textfield',hidden: true, name:'modifyDate'},
                        	{ xtype:'textfield',hidden: true, name:'createBy'},
                        	{ xtype:'textfield',hidden: true, name:'modifyBy'},
                        	{ xtype:'textfield',labelWidth:80, width:350, name:'dicCode',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mdm.dic.display.dicCode")%>',maxLength:45,allowBlank:false},
                        	{ xtype:'textfield',labelWidth:80, width:350, name:'dicName',fieldLabel:'<font color=red>*</font><%=UnieapConstants.getMessage("mdm.dic.display.dicName")%>',maxLength:128,allowBlank:false},
                        	{ xtype:'numberfield', labelWidth:80, width:350,name:'seq',fieldLabel:'<%=UnieapConstants.getMessage("mdm.dic.display.sequence")%>',minValue: 0,maxValue:1000,allowBlank:false},
                        	{ xtype:'textfield', labelWidth:80, width:350,name:'href',fieldLabel:'<%=UnieapConstants.getMessage("mdm.dic.display.href")%>'},
                        	{ xtype:'combo', labelWidth:80, width:350,forceSelection: true, editable:false,allowBlank:true,
                                name:'activeFlag',fieldLabel:'<%=UnieapConstants.getMessage("comm.activeFlag")%>',displayField:'dicName',valueField:'dicCode',value:'Y',
                                store:Ext.create('Ext.data.Store', 
                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata._1001})
							},
                        	{ xtype:'textareafield',labelWidth:80, width:350, name:'remark',fieldLabel:'<%=UnieapConstants.getMessage("comm.remark")%>',growMin:60,growMax:100,maxLength:255,allowBlank:true}
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
                        handler: function() {
                        	var form = dataForm.getForm();
                        	 if (form.isValid()){
                                 form.submit({
                                     clientValidation: true,
                                     method: 'POST',
                                     params:{'operType':operType},
                                     url: 'MdmController.do?method=dicDataDeal',
                                     success: function(form, action) {
                                    	var result = Ext.JSON.decode(action.response.responseText);
					                    if(result.isSuccess == 'failed'){
						                    	Ext.MessageBox.show({title: '<%=UnieapConstants.getMessage("comm.status")%>',msg:result.message,
		                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
						                    }else{
						                    	var dicId = result.id;
						                    	var dicCode = form.findField('dicCode').getValue();
						                    	var dicName = form.findField('dicName').getValue();
						                    	var text = '['+dicCode+']'+dicName;
						                    	var seq = form.findField('seq').getValue();
						                    	var href = form.findField('href').getValue();
						                    	var activeFlag = form.findField('activeFlag').getValue();
						                    	var remark = form.findField('remark').getValue();
						                    	var extendAttri = {dicId:dicId,dicCode:dicCode,dicName:dicName,seq:seq,href:href,activeFlag:activeFlag,remark:remark};
						                    	if(operType=='Add'){
							                    	createNode(record,{id:dicId,text:text,draggable: false,leaf: true},extendAttri);
						                    	}else if(operType=='Modify'){
						                    		record.set('text',text);
						                    		record.data.extendAttri = extendAttri;
						                    	}
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
               { title: '<%=UnieapConstants.getMessage("comm.data")%>', closeAction: 'hide', width: 400, height: 300, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'dicName' });
           }
           if(operType=='Add'){
	           	dataForm.getForm().reset();
	           	dataWin.show();
	           	dataForm.getForm().setValues({parentId:record.data.id});
	           	dataForm.getForm().findField('dicCode').setReadOnly(false);
	           	dataForm.getForm().findField('dicCode').inputEl.removeCls('readonly_field');
           		dataForm.getForm().findField('dicName').setReadOnly(false);
           		dataForm.getForm().findField('dicName').inputEl.removeCls('readonly_field');
           		dataForm.getForm().findField('seq').setReadOnly(false);
           		dataForm.getForm().findField('seq').inputEl.removeCls('readonly_field');
           		dataForm.getForm().findField('href').setReadOnly(false);
           		dataForm.getForm().findField('href').inputEl.removeCls('readonly_field');
           		dataForm.getForm().findField('activeFlag').setReadOnly(false);
           		dataForm.getForm().findField('activeFlag').inputEl.removeCls('readonly_field');
           		dataForm.getForm().findField('remark').setReadOnly(false);
           		dataForm.getForm().findField('remark').inputEl.removeCls('readonly_field');
           }else if(operType=='Modify'){
	           	dataWin.show();
	           	if(record.data.extendAttri.createBy=='unieap'){
	           		dataForm.getForm().findField('dicCode').setReadOnly(true);
	           		dataForm.getForm().findField('dicCode').inputEl.addCls('readonly_field');
	           		dataForm.getForm().findField('dicName').setReadOnly(true);
	           		dataForm.getForm().findField('dicName').inputEl.addCls('readonly_field');
	           		dataForm.getForm().findField('seq').setReadOnly(true);
	           		dataForm.getForm().findField('seq').inputEl.addCls('readonly_field');
	           		dataForm.getForm().findField('href').setReadOnly(true);
	           		dataForm.getForm().findField('href').inputEl.addCls('readonly_field');
	           		dataForm.getForm().findField('activeFlag').setReadOnly(true);
	           		dataForm.getForm().findField('activeFlag').inputEl.addCls('readonly_field');
	           		dataForm.getForm().findField('remark').setReadOnly(true);
	           		dataForm.getForm().findField('remark').inputEl.addCls('readonly_field');
	           	}
	           	dataForm.getForm().setValues(record.data.extendAttri);
           }else{
           		dataWin.show();
           }
   	  }
      function showResult(btn){
      		dataWin.hide();
	  }
     function createNode(record,config,extendAttri) {
          var child = record.appendChild(config);
          child.data.extendAttri = extendAttri;
          record.set('leaf',false);  
          record.expand();
      }	   
	   	
	});
    </script>
</head>
<body>
    <div id="datalayou"></div>
</body>
</html>
