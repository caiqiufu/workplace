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
		    fields: ['id','parentId','path','isLeaf','text','dicCode','dicName',
		    'createBy','createDatetime','modifyBy','modifyDatetime','activeFlag','activeFlagDesc','remark','beId']
		});
       var dicTreeStore = Ext.create('Ext.data.TreeStore', {
            model: 'dataTree',
            proxy: {
            	type: 'ajax',
                url: 'MdMController.do?method=dicTree'
            },
            root:{id:-1,text:'Root',leaf:false,expanded:true,allowDrag:false},
            nodeParam : "id",autoLoad:true,folderSort:true
        });
        var addAction = Ext.create('Ext.Action', {
        	icon   : '/Unieap/unieap/js/common/images/add.png',
	        text: 'Add',
	        disabled: false,
	        handler: function(widget,event) 
	        {	
	        	var node = dicTreePanel.getSelectionModel().selected.items[0];
	            showForm('Add',node);
	        }
	    });
	    var modifyAction = Ext.create('Ext.Action', {
	    	icon   : '/Unieap/unieap/js/common/images/edit.png',
	        text: 'Modify',
	        disabled: false,
	        handler: function(widget,event) 
	        {
	        	var node = dicTreePanel.getSelectionModel().selected.items[0];
            	showForm('Modify',node);
	        }
	    });
	    var deleteAction = Ext.create('Ext.Action', {
	    	icon   : '/Unieap/unieap/js/common/images/delete.png',
	        text: 'Delete',
	        disabled: false,
	        handler: function(widget,event) 
	        {
	            var node = dicTreePanel.getSelectionModel().selected.items[0];
	            if(node.get('id')==-1){
	            	Ext.MessageBox.alert('Status','Root object can not remove');
	            	return;
	            }
	            if(node.hasChildNodes()==true){
	        		Ext.MessageBox.alert('Status','Include sub dic, can not remove');
	        	}else{
		            showForm('Delete',node);
	        	}
	        }
	    });
	    var contextMenu = Ext.create('Ext.menu.Menu', {
	        items: [addAction,modifyAction,deleteAction]
	    });
	    var targetNode = null;
	    var dragData = null;
        var dicTreePanel = Ext.create('Ext.tree.Panel', {
            title: 'Dic tree',renderTo: 'tree-div',
            store: dicTreeStore,
            viewConfig: {
                plugins: {
                   ptype: 'treeviewdragdrop',
                   enableDrag: false,
                   enableDrop: false,
                   appendOnly: true
                },
                listeners:{
	                	itemcontextmenu: function(view, rec, node, index, e){
		                    e.stopEvent();
		                    contextMenu.showAt(e.getXY());
		                    return false;
		                }
	            	}
            }
        });
        var dataWin = null;
	    var dataForm = null;
        var operType = '';
        var selectedNode = null;
        function showForm(status,node){
        	operType = status;
        	selectedNode = node;
            if (dataWin==null){
            	dataForm = Ext.widget('form',
            	{
                    defaults:{labelAlign: 'left', labelWidth: 90, anchor: '100%'},
                    bodyPadding:5,
                    items:
                    [
                    	{xtype:'fieldset', title:'Dic Basic Info',
	                        items:
	                        [
	                        	{ xtype:'hiddenfield', name:'id'},
	                        	{ xtype:'hiddenfield', name:'parentId'},
	                        	{ xtype:'hiddenfield', name:'path'},
	                        	{ xtype:'hiddenfield', name:'isLeaf'},
	                        	{ xtype:'hiddenfield', name:'beId'},
	                        	{ xtype:'hiddenfield', name:'createBy'},
	                        	{ xtype:'hiddenfield', name:'modifyBy'},
	                        	{ xtype:'hiddenfield', name:'createDatetime'},
	                        	{ xtype:'hiddenfield', name:'modifyDatetime'},
	                        	{ xtype:'textfield', name:'dicCode',fieldLabel:'Dic Code',allowBlank:false,
	                        		validateOnChange:false, validateOnBlur :true,
								    validator :function(value)
								    	{
							    			var error = true; 
							    			if(operType == 'Modify' || value==''||value ==null)
							    			{
							    				return true;
							    			}
							    			var checkExistType = null;
							    			var parentId = dataForm.getForm().findField("parentId").getValue(); 
							    			if(parentId == '-1'){
							    				checkExistType = 'checkGroupExist';
							    			}else{
							    				checkExistType = 'checkDicExist';
							    			}
								    		Ext.Ajax.request({
								                url: 'MdMController.do?method=dicDeal',
								                async:false,
								                params:{'operType':checkExistType,"dicCode":value,'parentId':parentId},
								                success: function(response, opts) 
								                {
								                	var result = Ext.JSON.decode(response.responseText);
								                    if(result.isSuccess == 'success')
								                    {
								                		error = true; 
								                    }else
								                    {
								                    	error = value+ ' already exist, please change to another';
								                    }
								                },
								                failure: function(response, opts) 
								                {
								                	error='Check Code exist failed,error message:'+response.responseText;
								                }
								             });
								             return error;
								    	}
	                        	},
	                        	{ xtype:'textfield',name:'dicName', fieldLabel:'Dic Name', allowBlank:false},
	                        	{ xtype:'combo', forceSelection: true, emptyText: 'Select an Option',editable:false,allowBlank:false,
	                                fieldLabel:'Active Flag',name:'activeFlag',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata.ActiveFlag})
								}
	                        ]
	                    },
	                    {xtype:'fieldset',title:'Dic detail info',
	                        items:
	                        [
		                        { xtype:'textareafield', name:'remark',fieldLabel:'Remark',growMin:60,growMax:100,allowBlank:true}
	                        ]
                    	}
                    ],
                    buttons: 
                    [
	                    {id:'formCancel', text: 'Cancel',
	                        handler: function() 
	                        {
	                        	dataForm.getForm().reset();
	                        	dataWin.hide();
	                        }
	                    }, 
	                    {id:'formSubmit',text: 'Submit',
	                        handler: function() {
	                        	var form = dataForm.getForm();
	                        	 if (form.isValid()){
	                                 form.submit({
	                                     clientValidation: true,
	                                     waitMsg: 'Processing...',
	                                     method: 'POST',
	                                     params:{'operType':operType},
	                                     url: 'MdMController.do?method=dicDeal',
	                                     success: function(form, action) {
	                                     	var result = Ext.JSON.decode(action.response.responseText);
	                                     	if(operType=='Add'){
		                                     	selectedNode.appendChild(Ext.JSON.decode(result.dicData));
		                                     	selectedNode.data.expanded = true;
	                                     	} 
	                                     	if(operType=='Modify'){
	                                     		selectedNode.updateInfo(true,Ext.JSON.decode(result.dicData));
	                                     	}
	                                    	Ext.MessageBox.alert('Status', 'Save successfully.',showResult);
	                                     },
	                                     failure: function(form, action) {
	                                    	 Ext.MessageBox.alert('Status', 'Save failed, please retry.',showResult);
	                                     }
	                                 });
	                        	 }
	                        }
	                    }
                    ]
                });
                dataWin = Ext.widget('window', 
                { title: 'Data', closeAction: 'hide', width: 400, height: 400, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'dicCode' });
            }
            if(operType=='Add')
            {
            	dataForm.getForm().reset();
            	dataWin.show();
            	//dataForm.getForm().findField('dicCode').setReadOnly(false);
            	dataForm.getForm().findField('parentId').setValue(selectedNode.get('id'));
            }else if(operType=='Modify')
            {
            	dataWin.show();
            	dataForm.getForm().setValues(selectedNode.data);
            	//dataForm.getForm().findField('dicCode').setReadOnly(true);
            }else
            {	
                Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?', removeDatas);
            }
        }
        function removeDatas(btn)
        {
        	if(btn=='yes'){
	        	var selectedNode = dicTreePanel.getSelectionModel().selected.items[0];
	        	Ext.Ajax.request({
	                url: 'MdMController.do?method=dicDeal',
	                params:{'operType':"Delete",'id':selectedNode.get('id'),'dicId':selectedNode.get('dicId'),'parentId':selectedNode.get('parentId')},
	                success: function(response, opts) 
	                {
	                	Ext.MessageBox.alert('Status', 'Remove data successfully.');
	                	selectedNode.remove();
	                },
	                failure: function(response, opts) 
	                {
	                	Ext.MessageBox.alert('Status', 'Remove data failed.Error:'+response.status);
	                }
	             });
	        	return true;
        	}else{
        		return false;
        	}
        }
        function showResult(btn)
        {
        	dataWin.hide();
        }
    });
</script>
</head>
<body>
	<div id="tree-div" style='width:100%;height:100%;'></div>
	<div id="dataForm" style='width:100%;height:100%;'></div>
</body>
</html>
