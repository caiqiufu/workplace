<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Dic</title>
    <script type="text/javascript">
    Ext.onReady(function(){
       var operType = '';
       Ext.tip.QuickTipManager.init();
       Ext.define('dataTree', {
		    extend: 'Ext.data.Model',
		    fields: ['id','parentId','path','text','dicCode','dicName', 'activeFlag','isdata']
		});
       var dicTreeStore = Ext.create('Ext.data.TreeStore', {
            model: 'dataTree',
            proxy: {
            	type: 'ajax',
                url: 'MdMController.do?method=dicTree'
            },
            root:{  
                id:-1,  
                text:'Root',  
                leaf:false,  
                expanded:true 
            },
            nodeParam : "id",
            autoLoad :true,
            folderSort: true
        });
       var dicTreePanel = Ext.create('Ext.tree.Panel', {
            title: 'Dic tree',
            //width: 500,
            //height: '100%',
            layout: 'fit',
            el : 'treeresdata',
       		//layout: 'fit',
            collapsible: false,
            rootVisible:true,
            useArrows: true,
            enableDrag: true,
            enableDrop: true,
            store: dicTreeStore,
            animate: true
        });
        dicTreePanel.on({
            'itemclick' : function(thisTree, record, item, index, e, options) {
            	operType = 'Modify';
             	var parentNode = record.parentNode;
             	//alert('operType='+operType);
                dataForm.getForm().setValues(record.data);
                dataForm.getForm().findField('dicCode').setReadOnly(true);
                dataForm.getForm().findField('parentId').setValue(parentNode.getId());
                //dataForm.getForm().getFields().each(function(field){  
			    //    field.setReadOnly(true);    
			    //});  
            	//if(record.get('leaf') == false){
            	//}else{
            	//	Ext.MessageBox.alert('Status', 'Please select one group data ');
            	//}
           }
        });
		var dataForm = Ext.create('Ext.form.Panel', {
				title: 'Dic data',
		        renderTo: 'dataForm',
		        frame: true,
		        title: 'Date Range',
		        bodyPadding: '5 5 0',
		        //layout: 'fit',
		        width: '100%',
		        fieldDefaults: {
		            labelWidth: 125,
		            msgTarget: 'side',
		            autoFitErrors: false
		        },
		        listeners:
		        {
		        	afterRender:function(thisForm, options)
		        	{
			        	if(UnieapButton.Dic_Save!=null&&UnieapButton.Dic_Save.disabled== true)
			        	{
				        	Ext.getCmp('Dic_Save').hide();
			        	}
		        	}
		        },
		        //defaultType: 'datefield',
		        buttons: 
		        [
			        {
			        	id:'Dic_Save',
	                    text: 'Save',
	                    cls: 'contactBtn',
	                    handler: function() {
	                    	var form = dataForm.getForm();
	                    	var parentId = dataForm.getForm().findField('parentId').getValue();
	                    	if(parentId==''||parentId == null){
	                    		Ext.MessageBox.alert('Status', 'Please select one parent data.');
	                    		return;
	                    	}
                        	if (form.isValid()){
                                 form.submit({
                                     clientValidation: true,
                                     waitMsg: 'Processing...',
                                     method: 'POST',
                                     params:{'operType':operType},
                                     url: 'MdMController.do?method=dicDeal',
                                     success: function(form, action) {
                                    	 Ext.MessageBox.alert('Status', 'Save successfully.',showResult);
                                     },
                                     failure: function(form, action) {
                                    	 Ext.MessageBox.alert('Status', 'Save failed, please retry.',showResult);
                                     }
                                 });
                        	 }
	                    }
	                }
                ],
		        items: [
		        {
		        	xtype: 'fieldcontainer',
				 	layout: 'hbox',
				    items:
				    [
				    	{
				        	xtype: 'button',
				        	cls: 'contactBtn',
		                	width: 100,
		                    text: 'Add Child Folder',
		                    handler: function() {
		                        var parentId = dataForm.getForm().findField('id').getValue();
		                    	if(parentId==''||parentId == null){
		                    		Ext.MessageBox.alert('Status', 'Please select one parent data.');
		                    		return;
		                    	}
		                        if(parentId!='-1'){
		                    		Ext.MessageBox.alert('Status', 'Only add subfolder in Root folder');
		                    		return;
		                    	}
		                    	/**
		                    	var isdata = dataForm.getForm().findField('isdata').getValue();
		                    	if(isdata=='Y'){
		                    		Ext.MessageBox.alert('Status', 'This leaf object, can not add sub folder');
		                    		return;
		                    	}
		                    	var node = dicTreePanel.getStore().getNodeById(parentId); 
		                    	if(node.isLeaf()){
		                    		Ext.MessageBox.alert('Status', 'This leaf object, can not add sub folder');
		                    		return;
		                    	}
		                    	if(node.id!='-1'){
		                    		Ext.MessageBox.alert('Status', 'Only add subfolder in Root folder');
		                    		return;
		                    	}
		                    	if(node.hasChildNodes()){
		                    		node.eachChild(function(child){
		                    			if(child.isLeaf()){
				                    		Ext.MessageBox.alert('Status', 'This folder already have leaf, can not add sub folder');
				                    		return;
		                    			}
		                    		})
		                    	}*/
		                        operType = 'AddGroup';
		                        dataForm.getForm().findField('dicCode').setReadOnly(false);
		                        dataForm.getForm().setValues({'dicCode':'','dicName':'','activeFlag':'Y','parentId':parentId});
		                    }
			            },{
				        	xtype: 'button',
				        	cls: 'contactBtn',
		                	width: 100,
		                    text: 'Add Child Data',
		                    handler: function() {
		                        operType = 'AddData';
		                        dataForm.getForm().findField('dicCode').setReadOnly(false);
		                        var parentId = dataForm.getForm().findField('id').getValue();
		                        dataForm.getForm().setValues({'dicCode':'','dicName':'','activeFlag':'Y','parentId':parentId});
		                    }
			            }
				    ] 
		        },{
		        	xtype     : 'textfield',
		            name      : 'id',
		            fieldLabel: 'id',
		            hidden: true
		        },{
		        	xtype     : 'textfield',
		            name      : 'parentId',
		            fieldLabel: 'parentId',
		            hidden: true
		        },{
		        	xtype     : 'textfield',
		            name      : 'leaf',
		            fieldLabel: 'leaf',
		            hidden: true
		        },{
		        	xtype     : 'textfield',
		            name      : 'path',
		            fieldLabel: 'path',
		            hidden: true
		        },{
		        	xtype     : 'textfield',
		            name      : 'isdata',
		            fieldLabel: 'isdata',
		            hidden: true
		        },{
		        	xtype     : 'textfield',
		        	allowBlank:     false,
		            name      : 'dicCode',
		            fieldLabel: 'Dic Code'
		        }, {
		        	xtype     : 'textfield',
		        	allowBlank:     false,
		            name      : 'dicName',
		            fieldLabel: 'Dic Name'
		        },{
                         xtype:          'combo',
                         forceSelection: true,
                         emptyText: 'Select an Option',
                         editable:       false,
                         allowBlank:     false,
                         fieldLabel:     'Active Flag',
                         name:           'activeFlag',
                         displayField:   'dicName',
                         valueField:     'dicCode',
                         store:          Ext.create('Ext.data.Store', {
                             fields : ['dicCode', 'dicName'],
                             data:UnieapDicdata.ActiveFlag
                          })
					}
		        ]
		    });	


    new Ext.panel.Panel({
        renderTo: 'tree-div',
        layout: 'fit',
        //width: 600,
        //height: '100%',
        layout: {
            type: 'hbox',
            align: 'stretch'
        },
        defaultType: 'panel',
        items: 
        [
        	{
			    //title: 'Grid resource data',
			    width: '50%',
			    items : [dicTreePanel]
	        },{
			    //title: 'Grid resource data',
			    width: '50%',
			    items : [dataForm]
	       }
	     ]
    });
      function showResult(btn){
      	dataForm.getForm().reset();
      	dicTreeStore.load();
      }
    });
</script>
</head>
<body>
	<div id="tree-div" style='width:100%;height:100%;'></div>
	<div id="dataForm" style='width:100%;height:100%;'></div>
</body>
</html>
