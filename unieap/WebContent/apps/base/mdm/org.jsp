<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>Org</title>
    <script type="text/javascript">
    Ext.onReady(function(){
       var operType = '';
       Ext.tip.QuickTipManager.init();
       Ext.define('dataTree', {
		    extend: 'Ext.data.Model',
		    fields: ['id','parentId','path','isLeaf','text','orgId','orgCode','orgName','contactUser','telephone','mobile','email',
		    'createBy','createDatetime','modifyBy','modifyDatetime','activeFlag','activeFlagDesc','remark','beId']
		});
       var orgTreeStore = Ext.create('Ext.data.TreeStore', {
            model: 'dataTree',
            proxy: {
            	type: 'ajax',
                url: 'MdMController.do?method=orgTree'
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
	        	var node = orgTreePanel.getSelectionModel().selected.items[0];
	            showForm('Add',node);
	        }
	    });
	    var modifyAction = Ext.create('Ext.Action', {
	    	icon   : '/Unieap/unieap/js/common/images/edit.png',
	        text: 'Modify',
	        disabled: false,
	        handler: function(widget,event) 
	        {
	        	var node = orgTreePanel.getSelectionModel().selected.items[0];
            	showForm('Modify',node);
	        }
	    });
	    var deleteAction = Ext.create('Ext.Action', {
	    	icon   : '/Unieap/unieap/js/common/images/delete.png',
	        text: 'Delete',
	        disabled: false,
	        handler: function(widget,event) 
	        {
	            var node = orgTreePanel.getSelectionModel().selected.items[0];
	            if(node.get('id')==-1){
	            	Ext.MessageBox.alert('Status','Root object can not remove');
	            	return;
	            }
	            if(node.isLeaf()!=true){
	        		Ext.MessageBox.alert('Status','Include sub org, can not remove');
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
        var orgTreePanel = Ext.create('Ext.tree.Panel', {
            title: 'Org tree',renderTo: 'tree-div',
            store: orgTreeStore,
            viewConfig: {
                plugins: {
                   ptype: 'treeviewdragdrop',
                   enableDrag: true,
                   enableDrop: true,
                   appendOnly: true
                },
                listeners:{
	                	itemcontextmenu: function(view, rec, node, index, e){
		                    e.stopEvent();
		                    contextMenu.showAt(e.getXY());
		                    return false;
		                },
		                beforedrop: function(node,data,modal,dropPosition,dropFunction){
	                        var dropNode = data.records[0];
                    	 	var targetNode;
							//dropFunction.cancleDrop = function(){};
							//dropFunction.processDrop = function(){};
							if (modal.data.leaf){
								targetNode = modal.parentNode;
							}else{
								targetNode = modal;
							}
							return saveDropNode(dropNode,dropNode.parentNode,targetNode);
		                	//return false;
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
                    	{xtype:'fieldset', title:'Org Basic Info',
	                        items:
	                        [
	                        	{ xtype:'hiddenfield', name:'orgId'},
	                        	{ xtype:'hiddenfield', name:'parentId'},
	                        	{ xtype:'hiddenfield', name:'path'},
	                        	{ xtype:'hiddenfield', name:'isLeaf'},
	                        	{ xtype:'hiddenfield', name:'beId'},
	                        	{ xtype:'hiddenfield', name:'createBy'},
	                        	{ xtype:'hiddenfield', name:'modifyBy'},
	                        	{ xtype:'hiddenfield', name:'createDatetime'},
	                        	{ xtype:'hiddenfield', name:'modifyDatetime'},
	                        	{ xtype:'textfield', name:'orgCode',fieldLabel:'Org Code',allowBlank:false,
	                        		validateOnChange:false, validateOnBlur :true,
								    validator :function(value)
								    	{
							    			var error = true; 
							    			if(operType == 'Modify' || value==''||value ==null)
							    			{
							    				return true;
							    			}
								    		Ext.Ajax.request({
								                url: 'MdMController.do?method=orgDeal',
								                async:false,
								                params:{'operType':"checkExist","orgCode":value},
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
								                	error='Check Org Code exist failed,error message:'+response.responseText;
								                }
								             });
								             return error;
								    	}
	                        	},
	                        	{ xtype:'textfield',name:'orgName', fieldLabel:'Org Name', allowBlank:false},
	                        	{ xtype:'combo', forceSelection: true, emptyText: 'Select an Option',editable:false,allowBlank:false,
	                                fieldLabel:'Active Flag',name:'activeFlag',displayField:'dicName',valueField:'dicCode',
	                                store:Ext.create('Ext.data.Store', 
	                                { fields : ['dicCode', 'dicName'],data:UnieapDicdata.ActiveFlag})
								},
	                        	{ xtype:'textareafield', name:'remark',fieldLabel:'Remark',growMin:60,growMax:100,allowBlank:true}
	                        ]
	                    },
	                    {xtype:'fieldset',title:'Org detail info',
	                        items:
	                        [
		                        { xtype:'textfield',name:'email',fieldLabel: 'Email Address',vtype: 'email'},
		                        { xtype:'numberfield',name:'mobile',fieldLabel: 'Mobile'},
		                        { xtype:'numberfield',name:'telephone',fieldLabel: 'Telephone'}
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
	                                     url: 'MdMController.do?method=orgDeal',
	                                     success: function(form, action) {
	                                     	var result = Ext.JSON.decode(action.response.responseText);
	                                     	if(operType=='Add'){
		                                     	if(selectedNode.isLeaf()==true)
		                                     	{
										        	selectedNode.data.leaf=false;
										        	selectedNode.data.expanded = true;
									        	}
		                                     	selectedNode.appendChild(Ext.JSON.decode(result.orgData));
	                                     	} 
	                                     	if(operType=='Modify'){
	                                     		selectedNode.updateInfo(true,Ext.JSON.decode(result.orgData));
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
                { title: 'Data', closeAction: 'hide', width: 400, height: 400, layout: 'fit', modal: true, items: dataForm,defaultFocus: 'orgCode' });
            }
            if(operType=='Add')
            {
            	dataForm.getForm().reset();
            	dataWin.show();
            	dataForm.getForm().findField('orgCode').setReadOnly(false);
            	dataForm.getForm().findField('parentId').setValue(selectedNode.get('id'));
            }else if(operType=='Modify')
            {
            	dataWin.show();
            	dataForm.getForm().setValues(selectedNode.data);
            	dataForm.getForm().findField('orgCode').setReadOnly(true);
            }else
            {	
                Ext.MessageBox.confirm('Confirm', 'Are you sure you want to do that?', removeDatas);
            }
        }
        function removeDatas(btn)
        {
        	if(btn=='yes'){
	        	var selectedNode = orgTreePanel.getSelectionModel().selected.items[0];
	        	Ext.Ajax.request({
	                url: 'MdMController.do?method=orgDeal',
	                params:{'operType':"Delete",'orgId':selectedNode.get('orgId'),'parentId':selectedNode.get('parentId')},
	                success: function(response, opts) 
	                {
	                	Ext.MessageBox.alert('Status', 'Remove data successfully.');
						var parentNode = selectedNode.parentNode;
	                	selectedNode.remove();
						if(!parentNode.hasChildNodes()){
							parentNode.updateInfo(true,{leaf:true});
                        }
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
        	//orgTreeStore.reload();
        }
        function saveDropNode(drapOrgNode,drapParentNode,targetOrgNode){
        	var resultFlag = true;
        	Ext.MessageBox.show({
				           title: 'Please wait',msg: 'Syncing org data...',progressText: 'Syncing...',
				           width:300,progress:true,closable:false,animateTarget: 'tree-div'
				       });
        	Ext.Ajax.request({
	                url: 'MdMController.do?method=drapOrgNode',
	                params:{'operType':"Drap",'drapOrgId':drapOrgNode.get('orgId'),'targetOrgId':targetOrgNode.get('orgId')},
	                success: function(response, opts) 
	                {
                		Ext.MessageBox.alert('Status', 'Sync resource data successfully.');
                		var drapOrgNodePath = targetOrgNode.get('path')+'/'+drapOrgNode.get('orgId');
	                	drapOrgNode.updateInfo(true,{parentId:targetOrgNode.get('orgId'),path:drapOrgNodePath});
	                	if(drapOrgNode.isLeaf()==false){
	                		drapOrgNode.data.expanded = false;
	                	}
	                	if(!drapParentNode.hasChildNodes()){
	                		drapParentNode.updateInfo(true,{leaf:true});
	                	}
	                	resultFlag = true;
	                },
	                failure: function(response, opts) 
	                {
	                	Ext.MessageBox.alert('Status', 'Drap data failed.Error:'+response.status);
	                	resultFlag =  false;
	                }
	             });
	        Ext.MessageBox.hide();
            return resultFlag;
        }
    });
</script>
</head>
<body>
	<div id="tree-div" style='width:100%;height:100%;'></div>
	<div id="dataForm" style='width:100%;height:100%;'></div>
</body>
</html>
