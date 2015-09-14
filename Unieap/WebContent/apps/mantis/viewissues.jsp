<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01//EN" "http://www.w3.org/TR/html4/strict.dtd">
<%@ include file="/unieap/unieap.jsp" %>
<%
	String project = (String)request.getAttribute("project");
%>
<html>
<head>
<title>Report Issue</title>
 	<style type="text/css">
        .td_title {
        	background-color:#B5BCBB;
        	font-weight:bolder;
        	height:20px;
        	
        }
        .td_value {
        	background-color:#E9EAE2;
        	height:20px;
        	width:100px;
        	align:left;
        }
        .new-row .x-grid-cell {
            background-color: #FDBDB2;
        }
        .assign-row .x-grid-cell {
            background-color: #BDC5B5;
        }
        .confirmed-row .x-grid-cell {
            background-color: #ECEF91;
        }
        .pendingfix-row .x-grid-cell {
            background-color: #F4FAF9;
        }
        .reject-row .x-grid-cell {
            background-color: #F7210B;
        }
        .pendingretest-row .x-grid-cell {
            background-color: #F4FAF9;
        }
        .resolve-row .x-grid-cell {
            background-color: #9ECCC1;
        }
    </style>
	<script type="text/javascript">
			var project = "<%=project%>";
			Ext.onReady(function() {
		    	Ext.tip.QuickTipManager.init();
		    	function exportExcel(obj,handlerId){
			    	var parameters = Ext.encode(obj);
			    	window.location.href = "UploadController.do?method=export&handlerId="+handlerId+"&parameters="+parameters;
			    };
			    /***Query Form begin*****/
			    var repoter = new Ext.data.Store({
		            proxy: {
		                type: 'ajax',
		                url: 'MantisController.do?method=getReporterList&operType=ViewIssue',
		                reader: {type: 'json'}
		            },
		            fields: ['dicCode','dicName']
		        });
		    	repoter.on('beforeload', function (store, options){
			            Ext.apply(store.proxy.extraParams,{'project':project});
			        });
		    	repoter.load();
			    
				var queryform = Ext.create('Ext.form.Panel',{
			        layout: 'fit',
			        width : '100%',
		     	   	frame : true,
		     	   	el:'queryform',
		     	   	id:'query_from',
		     	    items:[
		     	           { id:'queryFieldset', xtype:'fieldset',title: 'Search',
		  	                 startCollapsed: true,collapsible: true, defaultType: 'textfield',
		  	                	items:
		  	                		[
										{ xtype: 'fieldcontainer',layout: 'hbox',
										    items: 
										    [
										        { name: 'repoter',fieldLabel: 'Repoter', labelWidth:70,
							    	                xtype: 'combo', 
							    	                emptyText:'any',
							    	                displayField:'dicName',
						                            valueField:'dicCode',
						                            store:repoter
						    	                },
						    	                { name: 'assignto',fieldLabel: 'Assignto', labelWidth:70,
							    	                xtype: 'combo', 
							    	                emptyText:'any',
							    	                displayField:'dicName',
						                            valueField:'dicCode',
						                            store:repoter
						    	                },
						    	                { name: 'testStream',fieldLabel: 'Test Stream', labelWidth:70,
							    	                xtype: 'combo', 
							    	                emptyText:'any',
							    	                displayField:'dicName',
						                            valueField:'dicCode',
						                            store:Ext.create('Ext.data.Store', 
						        			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1006Opt}),
						    	                },
						    	                { name: 'severity',fieldLabel: 'Severity', labelWidth:70,
							    	                xtype: 'combo', 
							    	                emptyText:'any',
							    	                displayField:'dicName',
						                            valueField:'dicCode',
						                            store:Ext.create('Ext.data.Store', 
						        			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1002Opt}),
						    	                },
										        { xtype:'textfield',name:'title',fieldLabel: 'Title',labelWidth:70},
										        { xtype:'textfield',name:'tcId',fieldLabel: 'Test Case ID',labelWidth:70}
										    ]
										},
										{ xtype: 'fieldcontainer',layout: 'hbox',
											items:[
													{ name: 'status',fieldLabel: 'Status', labelWidth:70,
													    xtype: 'combo', 
													    emptyText:'any',
													    displayField:'dicName',
													    valueField:'dicCode',
													    store:Ext.create('Ext.data.Store', 
													    		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1003Opt}),
													},
													{ name: 'hideStatus',fieldLabel: 'Hide Status', labelWidth:70,
													    xtype: 'combo', 
													    emptyText:'any',
													    displayField:'dicName',
													    valueField:'dicCode',
													    store:Ext.create('Ext.data.Store', 
													    		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1003Opt}),
													},
													{ name: 'prodVersion',fieldLabel: 'Prod Version', labelWidth:70,
													    xtype: 'combo', 
													    emptyText:'any',
													    displayField:'dicName',
													    valueField:'dicCode',
													    store:Ext.create('Ext.data.Store', 
													    		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1005Opt}),
													},
													{ name: 'priority',fieldLabel: 'Priority', labelWidth:70,
													    xtype: 'combo', 
													    emptyText:'any',
													    displayField:'dicName',
													    valueField:'dicCode',
													    store:Ext.create('Ext.data.Store', 
													    		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1004Opt}),
													},
													{ name: 'testPlan',fieldLabel: 'Test Plan', labelWidth:70,
													    xtype: 'combo', 
													    emptyText:'any',
													    displayField:'dicName',
													    valueField:'dicCode',
													    store:Ext.create('Ext.data.Store', 
													    		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1008Opt}),
													},
										        	{ xtype:'button',text:'Search',iconAlign: 'right',width:'60px',
								    	                handler: function (){
								    	                	var queryform =Ext.getCmp("query_from");
								    	                    var repoter=queryform.getForm().findField("repoter").getValue();
								    	                    var assignto=queryform.getForm().findField("assignto").getValue();
								    	                    var testStream=queryform.getForm().findField("testStream").getValue();
								    	                    var severity=queryform.getForm().findField("severity").getValue();
								    	                    var title=queryform.getForm().findField("title").getValue();
								    	                    var tcId=queryform.getForm().findField("tcId").getValue();
								    	                    var status=queryform.getForm().findField("status").getValue();
								    	                    var hideStatus=queryform.getForm().findField("hideStatus").getValue();
								    	                    var prodVersion=queryform.getForm().findField("prodVersion").getValue();
								    	                    var priority=queryform.getForm().findField("priority").getValue();
								    	                    var testPlan=queryform.getForm().findField("testPlan").getValue();
								    	                    var queryPara = 
								    	                    	{
								    	                    		'project':project==null?"":project,
								    	                    		'repoter':repoter==null?"":repoter,
								    	                         	'assignto':assignto==null?"":assignto,
								    	                         	'testStream':testStream==null?"":testStream,
								    	                         	'severity':severity==null?"":severity,
								    	                         	'title':title==null?"":title,
								    	                         	'tcId':tcId==null?"":tcId,
								    	                         	'status':status==null?"":status,
								    	                         	'hideStatus':hideStatus==null?"":hideStatus,
								    	                         	'prodVersion':prodVersion==null?"":prodVersion,
								    	                         	'priority':priority==null?"":priority,
								    	                         	'testPlan':testPlan==null?"":testPlan
								   	                        	};
								    	                    gridstore.load({params:queryPara});
								    	                }
									                },{width:'10px'},
									                { xtype:'button',text:'Reset',iconAlign: 'right',width:'60px',
								    	                handler: function ()
								    	                {
								    	                	Ext.getCmp("query_from").getForm().reset(); 
								    	                }
									                }
											       ]
										}
							  	     ]
							     }
		     	           ]
				});
				Ext.getCmp('queryFieldset').collapse(true);
		        queryform.render();
		        /***function****************************************************/
		        function status(value, metadata, record, rowIndex, colIndex, store) {
			        return val;
			    };
			    function idRUL(value, metadata, record, rowIndex, colIndex, store){
			    	if(UnieapButton.issue_edit!=null&&UnieapButton.issue_edit.abled==true){
				    	return '<a href="MantisController.do?method=issueDetail&defectId='+value+'" target="_self" title="View Issues">' + value + '</a>';
			    	}else{
			    		return value;
			    	}
			    }
			    var dataWin = null;
			    var projectObj = Ext.create('Ext.form.field.ComboBox', {
			    	valueField:'dicCode',
			        displayField: 'dicName',
			        fieldLabel: 'Project:',
			        labelWidth:45,
			        forceSelection: true,
			        width:280,
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1014}),
			        queryMode: 'local'
			    });
			    var severityObj = Ext.create('Ext.form.field.ComboBox', {
			        valueField:'dicCode',
			        displayField: 'dicName',
			        fieldLabel: 'Severity:',
			        labelWidth:45,
			        forceSelection: true,
			        width:280,
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1002Opt}),
			        queryMode: 'local'
			    });
			    var priorityObj = Ext.create('Ext.form.field.ComboBox', {
			        valueField:'dicCode',
			        displayField: 'dicName',
			        fieldLabel: 'Priority:',
			        labelWidth:45,
			        forceSelection: true,
			        width:280,
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1004Opt}),
			        queryMode: 'local'
			    });
		    	var assigntoObj = Ext.create('Ext.form.field.ComboBox', {
			        valueField:'dicCode',
			        displayField: 'dicName',
			        fieldLabel: 'Assign To:',
			        labelWidth:65,
		            width:260,
		            queryMode: 'local',
			        forceSelection: true,
		            store: repoter
		        });
		    	var statusObj = Ext.create('Ext.form.field.ComboBox', {
			        valueField:'dicCode',
			        displayField: 'dicName',
			        fieldLabel: 'Status:',
			        labelWidth:45,
			        width:280,
			        store: Ext.create('Ext.data.Store', 
			        		{fields : ['dicCode', 'dicName'],data:UnieapDicdata._1003}),
			        queryMode: 'local',
			        forceSelection: true
			    });
		    	var nodeObj = new Ext.form.TextArea({
		    	    anchor:'100% 100%',
		    	    growMin:300,
		    	    growMax:800
		    	});
		        /***data grid********************************************/
		        var operationItems = [];
		        if(UnieapButton.issue_edit!=null&&UnieapButton.issue_edit.abled==true){
			    	operationItems.push({
			     	   icon   : '/Unieap/unieap/js/common/images/edit.png',
			     	   tooltip: 'Edit',
			           handler:function(grid, rowIndex, colIndex)
			           {	
			         	   	selectedRecord = grid.getStore().getAt(rowIndex);
			         	    window.location.href = "MantisController.do?method=defectModify&defectId="+selectedRecord.get('defectId');
			           }
			        });
		    	}
		    	Ext.define('datamodel', {
		            extend: 'Ext.data.Model',
		            fields:
		            	[
		             	'defectId','prodVersionDesc','testStreamDesc','severityDesc','priorityDesc','subStreamDesc','testPlanDesc','tcId',
		             	'title','descpt','remark','steps','createDate','modifyDate','modifyBy','createBy','createByName','status','statusDesc','assignto','assigntoName',
		             	'project','projectDesc'
		             ],
		             idProperty: 'defectId'
		        });
		        var gridstore = Ext.create('Ext.data.Store', {
		            model: 'datamodel',
		            remoteSort: true,
		            proxy: 
		            { type: 'ajax', url:'MantisController.do?method=viewisGrid&operType=ViewIssues',
		                reader: 
		                {root: 'rows', totalProperty: 'totalCount'},
		                simpleSortMode: true
		            },
		            sorters: 
		            [
		            	{ property: 'defectId', direction: 'DESC'}
		            ]
		        });
		        gridstore.on('beforeload', function (store, options){
		            Ext.apply(store.proxy.extraParams,{'project':project});
		        });
		        var selModel = Ext.create('Ext.selection.CheckboxModel');
		        var datagrid = Ext.create('Ext.grid.Panel', 
		        {el : 'datagrid',layout: 'fit',columnLines: true,autoScroll:true,
			   	 	selModel:selModel,
			   	    id:'issuesgrid',
		   	   		store : gridstore,
		   	   	    forceFit: true,
		   	   	    autoExpandColumn:'title',
			   	   	columns:
			   	   	[
						{ menuDisabled: true,sortable: false, xtype: 'actioncolumn', text: "Edit",width:30,items:operationItems},
						{ text: "ID",dataIndex: 'defectId',width:50,renderer:idRUL},
						{ text: "Repoter", dataIndex: 'createByName',width:80},
						{ text: "Test Stream",dataIndex: 'testStreamDesc',width:150},
						{ text: "Severity",dataIndex: 'severityDesc',width:80},
						{ text: "Status",dataIndex: 'statusDesc',width:100},
						{ text: "modifyDate",dataIndex: 'modifyDate',width:100},
						{ text: "Title", dataIndex: 'title',width:300}
			   	   	],
			   	 	viewConfig:{getRowClass:function(record,rowIndex,rowParams,store){ 
			   	 			var status = record.get('status');
			   	 			if(status=='0'){
			   	 				return 'new-row';
			   	 			}
				   	 		if(status=='1'){
			   	 				return 'assign-row';
			   	 			}
					   	 	if(status=='2'){
			   	 				return 'confirmed-row';
			   	 			}
					   	 	if(status=='3'){
			   	 				return 'pendingfix-row';
			   	 			}
					   	 	if(status=='4'){
			   	 				return 'reject-row';
			   	 			}
					   	 	if(status=='5'){
			   	 				return 'pendingretest-row';
			   	 			}
					   	 	if(status=='6'){
			   	 				return 'resolve-row';
			   	 			}
			   	 			
                     }},
			        tbar:[queryform],
		    	   	bbar:[
		    	   	      { name: 'batchUpdate',id:'batchUpdate',fieldLabel: 'Selected To:', labelWidth:65,
		    	                xtype: 'combo', anchor:'95%',
		    	                emptyText:'any',
	                            valueField:'dicCode',
		    	                displayField:'dicName',
	                            store:Ext.create('Ext.data.Store', {
	                                fields : ['dicCode', 'dicName'],
	                                data:UnieapDicdata._1009Opt
	                             })
	    	                },
	    	                '-',
			    	   	   {xtype:'button',text:'OK',iconAlign: 'right',width:'80px',
		    	                handler: function (){
		    	                	//var records = datagrid.getSelectionModel().getSelection();
		    	                	var records = Ext.getCmp("issuesgrid").getSelectionModel().getSelection();
		    	                	var batchUpdateType = Ext.getCmp("batchUpdate").getValue();
		    	                	if(records.length==0){
		    	                		Ext.MessageBox.show({
						        	           title: 'Informational',
						        	           msg: 'Please choose records first.',
						        	           buttons: Ext.MessageBox.OK,
						        	           icon: Ext.MessageBox.INFO
						        	      });
							    		return;
		    	                	}else if(batchUpdateType==''||batchUpdateType == null){
		    	                		Ext.MessageBox.show({
						        	           title: 'Informational',
						        	           msg: 'Please choose one change type.',
						        	           buttons: Ext.MessageBox.OK,
						        	           icon: Ext.MessageBox.INFO
						        	      });
							    		return;
		    	                	}else{
		    	                		if (dataWin==null){
		    	        	            	dataForm = Ext.widget('form',
		    	        	            	{
		    	        	                    defaults:{labelAlign: 'left',anchor: '100%'},
		    	        	                    bodyPadding:1,
		    	        	                    items:[projectObj,severityObj,priorityObj,assigntoObj,statusObj,nodeObj],
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
		    	        		                        	var batchUpdateType = Ext.getCmp("batchUpdate").getValue();
		    	        		                        	if(batchUpdateType=='0'&&projectObj.getValue()=='-1'){
		    	        		    	                		Ext.MessageBox.show({
		    	        						        	           title: 'Informational',
		    	        						        	           msg: "Can't move to All.",
		    	        						        	           buttons: Ext.MessageBox.OK,
		    	        						        	           icon: Ext.MessageBox.INFO
		    	        						        	      });
		    	        							    		return;
		    	        		    	                	 };
		    	        		    	                	 var updateValue = '';
		    	        		    	                	 if(batchUpdateType =='0'){
		    	        		    	                		 updateValue = projectObj.getValue();
		    	        		    	                	 }
		    	        		    	                	 if(batchUpdateType =='2'){
		    	        		    	                		 updateValue = assigntoObj.getValue();
		    	        		    	                	 }
		    	        		    	                	 if(batchUpdateType =='5'){
		    	        		    	                		 updateValue = severityObj.getValue();
		    	        		    	                	 }
		    	        		    	                	 if(batchUpdateType =='6'){
		    	        		    	                		 updateValue = priorityObj.getValue();
		    	        		    	                	 }
		    	        		    	                	 if(batchUpdateType =='7'){
		    	        		    	                		 updateValue = statusObj.getValue();
		    	        		    	                	 }
		    	        		    	                	 if(batchUpdateType =='8'){
		    	        		    	                		 updateValue = nodeObj.getValue();
		    	        		    	                	 }
		    	        		                        	 var datas = [];
		    	        										Ext.Array.each(Ext.getCmp("issuesgrid").getSelectionModel().getSelection(), function(record){
		    	        											datas.push(record.data); 
		    	        										});
		    	        		                        	 var form = dataForm.getForm();
	    	        		                                 form.submit({
	    	        		                                     clientValidation: true,
	    	        		                                     waitMsg: 'Processing...',
	    	        		                                     method: 'POST',
	    	        		                                     params:{'operType':batchUpdateType,'updateValue':updateValue,'datas':Ext.JSON.encode(datas)},
	    	        		                                     url: 'MantisController.do?method=batchUpdate',
	    	        		                                     success: function(form, action) {
	    	        		                                    	var result = Ext.JSON.decode(action.response.responseText);
	    	        							                    if(result.isSuccess == 'failed')
	    	        							                    {
	    	        							                    	Ext.MessageBox.show({title: 'Status',msg:result.message,
	    	        			                                 			buttons: Ext.MessageBox.OK,icon:Ext.MessageBox.ERROR});
	    	        							                    }else{
	    	        							                    	dataForm.getForm().reset();
	    	        							                    	dataWin.hide();
	    	        	 		                                    	Ext.MessageBox.show({title: 'Status',msg:'Successfull',fn: showReloadResult,
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
		    	        	                    ]
		    	        	                });
		    	        	                dataWin = Ext.widget('window', 
		    	        	                { title: 'Info', closeAction: 'hide', width: 300, height:200, layout: 'fit', modal: true, items: dataForm});
		    	        	            };
			    	            		dataWin.show();
			    	            		projectObj.hide();
			    	            		severityObj.hide();
			    	            		priorityObj.hide();
			    	            		assigntoObj.hide();
			    	            		statusObj.hide();
			    	            		nodeObj.hide();
			    	            		dataWin.setWidth(300);
		    	            			dataWin.setHeight(200);
		    	            			dataWin.center();
			    	            		//dataForm.doLayout();
			    	            		var batchUpdateValue = Ext.getCmp("batchUpdate").getValue();
			    	            		if(batchUpdateValue == '0'){
			    	            			projectObj.show();
			    	            			//dataForm.add(projectObj);
			    	            			//dataForm.items.items = [projectObj];
			    	            			dataForm.doLayout();
			    	            		}else if(batchUpdateValue == '2'){
			    	            			assigntoObj.show();
			    	            		}else if(batchUpdateValue == '5'){
			    	            			severityObj.show();
			    	            		}else if(batchUpdateValue == '6'){
			    	            			priorityObj.show();
			    	            		}else if(batchUpdateValue == '7'){
			    	            			statusObj.show();
			    	            		}else if(batchUpdateValue == '8'){
			    	            			nodeObj.show();
			    	            			dataWin.setWidth(700);
			    	            			dataWin.setHeight(300);
			    	            			dataWin.center();
			    	            		}else{
			    	            			//dataForm.remove();
			    	            		};
		    	                	};
		    	                }
			                },
		    	   	      '-',{ xtype:'button',text:'Export Excel',iconAlign: 'right',
		    	                handler: function ()
		    	                {
		    	                	var repoter=queryform.getForm().findField("repoter").getValue();
		    	                    var assignto=queryform.getForm().findField("assignto").getValue();
		    	                    var testStream=queryform.getForm().findField("testStream").getValue();
		    	                    var severity=queryform.getForm().findField("severity").getValue();
		    	                    var title=queryform.getForm().findField("title").getValue();
		    	                    var tcId=queryform.getForm().findField("tcId").getValue();
		    	                    var status=queryform.getForm().findField("status").getValue();
		    	                    var hideStatus=queryform.getForm().findField("hideStatus").getValue();
		    	                    var prodVersion=queryform.getForm().findField("prodVersion").getValue();
		    	                    var priority=queryform.getForm().findField("priority").getValue();
		    	                    var testPlan=queryform.getForm().findField("testPlan").getValue();
		    	                    var queryPara = 
		    	                    	{
		    	                    		'project':project==null?"":project,
		    	                    		'repoter':repoter==null?"":repoter,
		    	                         	'assignto':assignto==null?"":assignto,
		    	                         	'testStream':testStream==null?"":testStream,
		    	                         	'severity':severity==null?"":severity,
		    	                         	'title':title==null?"":title,
		    	                         	'tcId':tcId==null?"":tcId,
		    	                         	'status':status==null?"":status,
		    	                         	'hideStatus':hideStatus==null?"":hideStatus,
		    	                         	'prodVersion':prodVersion==null?"":prodVersion,
		    	                         	'priority':priority==null?"":priority,
		    	                         	'testPlan':testPlan==null?"":testPlan
		   	                        	};
		    	                    exportExcel(queryPara,'1'); 
		    	                }
			                },'-',
		    	   	      new Ext.PagingToolbar(
		    	    	  { store : gridstore,displayInfo: true, displayMsg: 'Displaying topics {0} - {1} of {2}', emptyMsg: "No topics to display"})]
		        	
		        });
		        datagrid.render();
		        gridstore.loadPage(1);
		        function showReloadResult(btn){
		        	var repoter=queryform.getForm().findField("repoter").getValue();
                    var assignto=queryform.getForm().findField("assignto").getValue();
                    var testStream=queryform.getForm().findField("testStream").getValue();
                    var severity=queryform.getForm().findField("severity").getValue();
                    var title=queryform.getForm().findField("title").getValue();
                    var tcId=queryform.getForm().findField("tcId").getValue();
                    var status=queryform.getForm().findField("status").getValue();
                    var hideStatus=queryform.getForm().findField("hideStatus").getValue();
                    var prodVersion=queryform.getForm().findField("prodVersion").getValue();
                    var priority=queryform.getForm().findField("priority").getValue();
                    var testPlan=queryform.getForm().findField("testPlan").getValue();
                    var queryPara = 
                    	{
                    		'project':project==null?"":project,
                    		'repoter':repoter==null?"":repoter,
                         	'assignto':assignto==null?"":assignto,
                         	'testStream':testStream==null?"":testStream,
                         	'severity':severity==null?"":severity,
                         	'title':title==null?"":title,
                         	'tcId':tcId==null?"":tcId,
                         	'status':status==null?"":status,
                         	'hideStatus':hideStatus==null?"":hideStatus,
                         	'prodVersion':prodVersion==null?"":prodVersion,
                         	'priority':priority==null?"":priority,
                         	'testPlan':testPlan==null?"":testPlan
                       	};
                    gridstore.load({params:queryPara});
		    	}
		    });
			
	</script>
</head>
<body>
		<div id="queryform" style='height:200px'></div>
		<div id="datagrid" style='height:500px'></div>
		<p>
		<table class="grouble_table" align="center">
			<tr >
				<td style="background-color:#FDBDB2;width:200px">new</td>
				<td style="background-color:#BDC5B5;width:200px">assign</td>
				<td style="background-color:#ECEF91;width:200px">confirmed</td>
				<td style="background-color:#F4FAF9;width:200px">pending fix</td>
				<td style="background-color:#F7210B;width:200px">reject</td>
				<td style="background-color:#F4FAF9;width:200px">pending retest</td>
				<td style="background-color:#9ECCC1;width:200px">resolve</td>
			</tr>
		</table>
</body>
</html>
