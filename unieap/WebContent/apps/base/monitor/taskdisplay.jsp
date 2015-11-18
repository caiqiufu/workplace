<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<html>
<head>
<title>TaskDisplay</title>
    <script type="text/javascript">
    	Ext.onReady(function(){
    		Ext.QuickTips.init();
    		Ext.define('Task', {
		        extend: 'Ext.data.Model',
		        fields: [
		        	'id','parentId','path','isLeaf','text','taskName','taskNameRemark','taskGroup','startDate','startTime','duration','completePer',
		        	'endDate','endTime','status','statusDesc'
		        ]
		    });
		    var store = Ext.create('Ext.data.TreeStore', {
		        model: 'Task',
		        proxy: {
		            type: 'ajax',
		            url: 'MonitorController.do?method=taskDisplayTree'
		        },
		        root:{id:-1,text:'Root',leaf:false,expanded:true,allowDrag:false},
		        nodeParam:"id",autoLoad:true,folderSort:true
		   });
		    var cellEditing = Ext.create('Ext.grid.plugin.CellEditing', {
		        clicksToEdit: 1
		    });
		   var tree = Ext.create('Ext.tree.Panel', {
		        title: 'Project Task List',
		       	layout: 'fit',
		       	height: 500,
		        renderTo: Ext.getBody(),
		        collapsible: false,
		        useArrows: true,
		        rootVisible: false,
		        store: store,
		        multiSelect: false,
		        columns: [{
		            xtype: 'treecolumn',
		            text: 'Task Name',
		            width: 200,
		            sortable: false,
		            dataIndex: 'text',
		            locked: true
		        },{
		            text: 'Task Group',
		            width: 60,
		            dataIndex: 'taskGroup',
		            sortable: false
		        },{
		            text: 'Task Desc',
		            width: 120,
		            dataIndex: 'taskNameRemark',
		            sortable: false
		        },{
		            text: 'Status',
		            width: 100,
		            dataIndex: 'dicName',
		            renderer: function(v, cellValues, rec){
		                return rec.get('statusDesc');
		            },
		            editor: { xtype:'combo',forceSelection: true,emptyText: '', editable:false, 
                                name:'status',displayField:'dicName',valueField:'dicCode',
                                store:Ext.create('Ext.data.Store', 
                                {fields : ['dicCode', 'dicName'],data:UnieapDicdata.TaskStatus}),
                                listeners: {
								                'select':function (combo, record){
								                	//combo.fireEvent('blur');
								                	if(combo.getValue()!=''){
								                	    var rec = tree.getSelectionModel().getSelection();
								                	    if(rec.length>0)
									               		{
											            	Ext.Ajax.request({
												                url: 'MonitorController.do?method=taskDeal',
												                params:{'operType':"UpdateStatus",'taskId':rec[0].get('id'),'status':combo.getValue()},
												                success: function(response, opts) 
												                {
												                	Ext.MessageBox.alert('Status', 'Update data successfully.');
																	store.reload();
												                },
												                failure: function(response, opts) 
												                {
												                	Ext.MessageBox.alert('Status', 'Update data failed.Error:'+response.status);
												                }
												             });
									               		}
								                		
								                	}
								                }
								            }
							},
		            sortable: false
		        },{
		            text: 'CompletePer',
		            width: 80,
		            dataIndex: 'completePer',
		             editor: {
		                xtype: 'numberfield',minValue: 0,maxValue:100,
		                listeners:{
			            	'blur': function (field){
			            		 var rec = tree.getSelectionModel().getSelection();
			            		 if(rec.length>0)
			            		 {
			            		 	Ext.Ajax.request({
						                url: 'MonitorController.do?method=taskDeal',
						                params:{'operType':"UpdatePer",'taskId':rec[0].get('id'),'completePer':field.getValue()},
						                success: function(response, opts) 
						                {
						                	Ext.MessageBox.alert('Status', 'Update data successfully.');
						                	store.reload();
						                },
						                failure: function(response, opts) 
						                {
						                	Ext.MessageBox.alert('Status', 'Update data failed.Error:'+response.status);
						                }
						             });
			            		 }
			            	}
			            }
		            },
		            sortable: false
		        },{
		            text: 'Start Time',
		            width: 120,
		            dataIndex: 'startDate',
		            renderer: function(v, cellValues, rec) {
		                return rec.get('startDate') + ' ' + rec.get('startTime');
		            },
		            sortable: false
		        }, {
		            text: 'End Date',
		            width: 120,
		            dataIndex: 'endDate',
		            renderer: function(v, cellValues, rec) {
		                return rec.get('endDate') + ' ' + rec.get('endTime');
		            },
		            sortable: false
		        },{
		            text: 'Duration',
		            width: 60,
		            dataIndex: 'duration',
		            sortable: false
		        }],
		        plugins: [cellEditing]
		    });
    	});
    </script>
</head>
<body>
	<div id="datagrid" style='width:100%;height:100%;'></div>
</body>
</html>
